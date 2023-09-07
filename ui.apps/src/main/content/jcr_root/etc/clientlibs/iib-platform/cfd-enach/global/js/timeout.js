$(document).ready(function(){
var IdealTimeOut = 120; //120 seconds
var idleSecondsTimer = null;
var idleSecondsCounter = 0;
        document.onclick = function () { idleSecondsCounter = 0; };
        document.onmousemove = function () { idleSecondsCounter = 0; };
        document.onkeypress = function () { idleSecondsCounter = 0; };
        idleSecondsTimer = window.setInterval(CheckIdleTime, 12000);
 
        function CheckIdleTime() {
            idleSecondsCounter++;

          /*  var oPanel = document.getElementById("timeOut");
            if (oPanel) {
                oPanel.innerHTML = (IdealTimeOut - idleSecondsCounter);
            }*/
            if (idleSecondsCounter >= IdealTimeOut) {
                window.clearInterval(idleSecondsTimer);
               // alert("Your Session has expired. Please login again.");
               // alert(sessionStorage.getItem("JSESSIONID"));
                if (null != sessionStorage.getItem("JSESSIONID")){
                sessionStorage.clear();
                window.location.assign("/content/enach/welcome.html");
                }
            }
        }

function encryptMessage(message, key, iv) {
       // key=key.substring(0,15);
       // iv=iv.substring(0,15);
	return CryptoJS.AES.encrypt(message, key, {
      iv: iv,
      padding: CryptoJS.pad.Pkcs7,
      mode: CryptoJS.mode.CBC
    });
}

function decryptMessage(encryptedMessage,key,iv) {
    // key=key.substring(0,15);
    //    iv=iv.substring(0,15);
	return CryptoJS.AES.decrypt(encryptedMessage,key, {
    iv: iv,
    padding: CryptoJS.pad.Pkcs7,
    mode: CryptoJS.mode.CBC
	}).toString(CryptoJS.enc.Utf8)
}

function makeid()
{
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for( var i=0; i < 16; i++ )
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}


});