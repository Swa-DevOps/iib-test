$(document).ready(function() {

	var num = getBrowserId();
	console.log(num);
	var id;
	if (num != -1) {
		urlParams = new URLSearchParams(window.location.search);
		if (urlParams.has('interactionId')) {
			id = urlParams.get('interactionId');
		}
	} else if (num == -1) {
		if ($.urlParam('interactionId') != null) {
			id = $.urlParam('interactionId');
		}
	}

	if(id != null) {
		var msg = $('.interactionId').attr('data-success');
		$('.interactionId').html(msg + " " + id);
	}
});

$.urlParam = function(name) {
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	if (results == null) {
		return null;
	} else {
		return decodeURI(results[1]) || 0;
	}
}

function getBrowserId() {
	var
	aKeys = ["MSIE", "Firefox", "Safari", "Chrome", "Opera"],
	sUsrAg = navigator.userAgent,
	nIdx = aKeys.length - 1;
	for (nIdx; nIdx > -1 && sUsrAg.indexOf(aKeys[nIdx]) === -1; nIdx--);
	return nIdx
}