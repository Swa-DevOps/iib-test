var num = getBrowserId();
var documentId = '';
var interactionId = '';
var cancel ='';
/*$(document).ready(function(){

	if (document.cookie.length != 0) {
		var namevaluepair = document.cookie.split("; ");
		for (var i = 0; i < namevaluepair.length; i++) {
			var namevaluearray = namevaluepair[i].split("=");
			if (namevaluearray[0] == "interactionId") {
				interactionId = JSON.parse(namevaluearray[1]);
				$('#interactionSuccessId').html(interactionId);
			}
		}
	};

});*/

interactionId = decryptMessage( sessionStorage.getItem("_vrresd"), sessionStorage.getItem("JSESSIONID"), sessionStorage.getItem("JSESSIONID"));
cancel=  decryptMessage( sessionStorage.getItem("_vrtype"), sessionStorage.getItem("JSESSIONID"), sessionStorage.getItem("JSESSIONID"));


function decryptMessage(encryptedMessage,key,iv) {
    // key=key.substring(0,15);
    //    iv=iv.substring(0,15);
	return CryptoJS.AES.decrypt(encryptedMessage,key, {
    iv: iv,
    padding: CryptoJS.pad.Pkcs7,
    mode: CryptoJS.mode.CBC
	}).toString(CryptoJS.enc.Utf8)
}

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
} else if (num == -1) {
	documentId = $.urlParam('documentId');
}

if (!documentId) {
	window.location.assign('/content/enach/welcome/login/create.html');
} else {

    if(cancel == 'cancel'){

        $('.sucessbox').hide();
        urmn= decryptMessage( sessionStorage.getItem("_vbcncl"), sessionStorage.getItem("JSESSIONID"), sessionStorage.getItem("JSESSIONID"));
		//	urmn = "IDIB0w05445w5t"
		$('#cancelsuccessMessage').html("Thank you! Mandate(s) registered with Number(s) "+ urmn + " have been initiated for cancellation. ");	
 		$('#interactionCancelId').html(interactionId);
		sessionStorage.clear();
       	$('.cancelsucessbox').show();

    }

        else{    
	$('.redcolor').html("Your E-NACH application with DOCUMENT NO. "+documentId+" has been Successfully submitted.");
    $('#interactionSuccessId').html(interactionId);
        }
}