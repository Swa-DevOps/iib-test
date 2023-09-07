var num = getBrowserId();
var documentId = '';
var interactionId = '';


function getBrowserId() {
	var
	aKeys = ["MSIE", "Firefox", "Safari", "Chrome", "Opera"],
	sUsrAg = navigator.userAgent,
	nIdx = aKeys.length - 1;
	for (nIdx; nIdx > -1 && sUsrAg.indexOf(aKeys[nIdx]) === -1; nIdx--);
	return nIdx;
}

$.urlParam = function(name) {
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	if (results == null) {
		return null;
	} else {
		return decodeURI(results[1]) || 0;
	}
}

if (num != -1) {
	urlParams = new URLSearchParams(window.location.search);
	documentId = urlParams.get('documentId');
    //interactionId =  urlParams.get('interactionId');

} else if (num == -1) {
	documentId = $.urlParam('documentId');
   // interactionId = $.urlParam('interactionId');
}

if (!documentId) {
	window.location.assign('/content/cfdenach/welcome.html');
} else {
	$('.redcolor').html("Your E-NACH application with DOCUMENT NO. "+documentId+" has been Successfully submitted.");
    //$('#interactionSuccessId').html(interactionId);
}




$(document).ready(function() {
	/*if (document.cookie.length != 0) {
		var namevaluepair = document.cookie.split("; ");
		for (var i = 0; i < namevaluepair.length; i++) {
			var namevaluearray = namevaluepair[i].split("=");
			if (namevaluearray[0] == "interactionId") {
				interactionId = JSON.parse(namevaluearray[1]);                
				$('#interactionFailId').html(interactionId);
			}
		}
	};*/


    let countin = sessionStorage.getItem("redirectSession");




    if (countin == "" || countin == "null" || countin == null) {
 				// Nothing to perform
    } else {

		$("#ssohide1").text("You will be redirected to originated app in 5 seconds"); 
		sessionStorage.clear();	
        setTimeout(function(){
            window.location.href = countin ;
         }, 5000);

    }

});


 function Redirect() 
    {  
        window.location.assign(countin); //window.location=countin; 
    } 