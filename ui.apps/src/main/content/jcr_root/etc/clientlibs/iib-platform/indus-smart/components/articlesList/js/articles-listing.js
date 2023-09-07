var staticCount = parseInt($('#myButton').attr('data-attr'));
var articlesListingMode = ($('#divValues').attr('data-articlesListingMode'));
var parentPagePath = ($('#divValues').attr('data-parentPath'));
var variableCount = staticCount;
var getthelist;
var rawTemplate = document.getElementById("autoScript").innerHTML;

function createHTML(commentsData) {

	var compiledTemplate = Handlebars.compile(rawTemplate);
	var ourGeneratedHTML = compiledTemplate(commentsData);
	var cmtsContainer = document.getElementById("autodiv");
	cmtsContainer.innerHTML = ourGeneratedHTML;
}

$(document).ready(function() {

	getthelist = function() {
		$.ajax({
			type: "POST",
			url: "/bin/loadMore",
			data: {
				parentPagePath : parentPagePath,
				articlesListingMode : articlesListingMode
			},
			success: function(data) {
				var arr = [];
				var json = JSON.parse(data);
				var totalLength = json.length
				var limit = 0;
				
				json.sort(function compare(a, b) {
					var dateA = new Date(a.date);
					var dateB = new Date(b.date);
					return dateB - dateA;
				});
				
				if (variableCount > totalLength) {
					limit = totalLength;
				} else {
					limit = variableCount;
				}
				
				for (var i=0; i<limit; i++) {
					arr.push({
						thumbnailImagePath: json[i].thumbnailImagePath,
						formattedDate: json[i].formattedDate,
						title: json[i].title,
						pageUrl: json[i].pageUrl,
						tagImagePath : json[i].tagImagePath,
					});				
				}

				createHTML(arr);
				if (totalLength <= variableCount) {
					$('#myButton').hide();
				}
			},
			error: function(error) {
				console.log("Something really bad happened " + error);
			}
		});

	}
	getthelist();

});

$('#myButton').click(function() {
	variableCount = variableCount + staticCount
	getthelist();
});