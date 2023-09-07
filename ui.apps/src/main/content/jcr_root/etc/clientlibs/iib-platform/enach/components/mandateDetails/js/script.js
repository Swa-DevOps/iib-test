var umrnNumber = "";
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

$(document).ready(function() {
    add_loader();

	let countin = sessionStorage.getItem("countManageDTime");
 	if(countin=="" || countin == "null" || countin == null )
    {
		sessionStorage.setItem("countManageDTime",makeid());
       
    }else
    {
		 window.location.assign("/content/enach/welcome.html");
    }






	var num = getBrowserId();
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
	    umrnNumber = urlParams.get('umrn');
	} else if (num == -1) {
		umrnNumber = $.urlParam('umrn');
	}


 	var V4 = sessionStorage.getItem("JSESSIONID");  
     if(V4=="" || V4 == "null" || V4 == null ){
			window.location.assign('/content/enach/welcome.html');	
    }


    var bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vmbn1").toString(), V4);
	var V1   = bytes.toString(CryptoJS.enc.Utf8);



	 let formData1 = {
			umrnNumber : umrnNumber,
			V1 : V1,
	};

      let temkey ="";
      temkey=makeid();
	  let encryptFormData=encryptMessage(JSON.stringify(formData1), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
      encryptFormData=encryptFormData+":"+temkey;




	$.ajax({
		url: "/bin/getMandateDetails", 
		type: "POST",
		//data: {umrnNumber : umrnNumber,V1 : V1},
        headers: {"X-AUTH-SESSION" : V4,
        		"X-AUTH-TOKEN" : encryptFormData},
		success: function (res) {
			if(res !=""){
            res=decryptMessage(res,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
            var resObj = JSON.parse(res);
			var emandateMaster = resObj.mandateDetails;
            var transDetails = resObj.transaction.response;
            var transKeys = Object.keys(transDetails);
			console.log(emandateMaster);
			if((emandateMaster != null) || (emandateMaster != undefined)){
				$('.bankName').html(emandateMaster.bankName);
				$('.AcNo').html(emandateMaster.benfNum);
				$('.status').html(emandateMaster.status);
				$('.amount').html(emandateMaster.amount);
				$('.umrn').html(emandateMaster.umrn);
				$('.startdate').html(emandateMaster.startDate);
				$('.endDate').html(emandateMaster.endDate);
				$('.freq').html(emandateMaster.freq);
				$('.toAccount').html(emandateMaster.accountnumber);
				$('.remarks').html(emandateMaster.referenceNo);

                $("#transDetails").html("");
				for(var i=0;i< transKeys.length;i ++)
                {


                            var htmlString="<tr class='dark'><td align='center'>"+transDetails[transKeys[i]].settleDt+"</td><td align='center'>Rs."+ transDetails[transKeys[i]].clmAmt+"</td><td align='center'>"+transDetails[transKeys[i]].transDesc+"</td></tr>";
							$("#transDetails").append(htmlString);



                }
            }
                remove_loader();
			}else {
				console.log(resObj);
                remove_loader();
				//window.location.assign('/content/enach/login/manage.html');
			}
		},
		error: function (e) {
			console.log("Error in Extracting Response :: "+ e);
            remove_loader();
		}
	});
});