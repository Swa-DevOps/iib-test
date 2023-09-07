var user = '';
var userUnParsed;

let sessionCheck = sessionStorage.getItem("EPLSESSIONID");
    if(sessionCheck=="" || sessionCheck == "null" || sessionCheck == null )
    {
        sessionCheck ="";
        // window.location.assign("/content/enach-pl/home/registration-pl.html");
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


$(document).ready(function() {
    let queryString = window.location.search;
	reason = queryString.substring(queryString.lastIndexOf('&')+1);
	let foo = getUrlVars()["session"];
	if(foo)
    {
    let vals= decryptMessage(foo.substring(32),CryptoJS.enc.Utf8.parse(foo.substring(0,16)),CryptoJS.enc.Utf8.parse(foo.substring(16,32)));
	vals=vals.split("|");
	$("#mandateReason").html(" " + vals[0]);
    }else if(reason){

		$("#mandateReason").html(" " +decodeURI(reason));
    }else{
       sessionStorage.clear();
       window.location.assign("/content/enach-pl/home/registration-pl.html");
    }
});

