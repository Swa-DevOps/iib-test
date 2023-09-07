Handlebars.registerHelper('json', function(context) {
	return JSON.stringify(context);
});

function getMandateDetails(handle) {
	if(handle.umrn){
		window.location.assign('/content/enach/welcome/login/details.html?umrn='+ handle.umrn);	
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



function cancelMandate(handle) {
	var mobileNo = "";
	if (document.cookie.length != 0) {
		var namevaluepair = document.cookie.split("; ");
		for (var i = 0; i < namevaluepair.length; i++) {
			var namevaluearray = namevaluepair[i].split("=");
			if (namevaluearray[0] == "userMobNo") {
				mobileNo = JSON.parse(namevaluearray[1]);
			}
		}
	};

	let formData = {
			destinationBank : handle.bankName,
			destinationBankId : 'INDB0000006',
			mobileNo : mobileNo,
			mandate_id : handle.umrn,
	};
	
	//console.log(formData);

	$('#alertMessage').html('Proceed to CANCEL mandate ?');
	$('#alertBox').show();
	$('.alertBtn').on('click', function(e) {
		console.log('clicked');
		$('#alertBox').hide();
		$.ajax({
			url: "/bin/digioCancelMandate", 
			type: "POST",
			data: formData,
			success: function (res) {
				var response = JSON.parse(res);
				console.log(res);
			},
			error: function (e) {
				console.log("Error in Digio Cancel Mandate Ajax :: " + e);
			}
		});
	});
};

$(document).ready(function() {
    add_loader();
	var bytes,V1,V2,V3;
	var V4 = sessionStorage.getItem("JSESSIONID"); 

    let countin = sessionStorage.getItem("countManageTime");
	let countinFD = sessionStorage.getItem("countManageDTime");


 	if(countin=="" || countin == "null" || countin == null)
    {
		sessionStorage.setItem("countManageTime",makeid());
    }else
    {
        if (countinFD=="" || countinFD == "null" || countinFD == null)
		 window.location.assign("/content/enach/welcome.html");
        else
		 sessionStorage.removeItem("countManageDTime");
    }


    if(V4=="" || V4 == "null" || V4 == null ){
			window.location.assign('/content/enach/welcome.html');	
    }
    bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vmbn1").toString(), V4);
	V1   = bytes.toString(CryptoJS.enc.Utf8);
    if(null != sessionStorage.getItem("vdbn2")){
    bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vdbn2").toString(), V4);
        V2 = bytes.toString(CryptoJS.enc.Utf8);}
    else{
        V2="";
    }
	if(null != sessionStorage.getItem("vfn2")){
    bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vfn2").toString(), V4);
    V3 =bytes.toString(CryptoJS.enc.Utf8);
    }else
    {
		V3="";
    }

    let formData1 = {
			V1 : V1,
			V2 : V2,
			V3 : V3,
			V4 : V4
	};

      let temkey ="";
      temkey=makeid();
	  let encryptFormData=encryptMessage(JSON.stringify(formData1), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
      encryptFormData=encryptFormData+":"+temkey;


	var rawTemplate = $('#mandateRows').html();

	$.ajax({
		url: "/bin/manageMandateData", 
		type: "POST",
        headers: {"X-AUTH-SESSION" : V4,
        			"X-AUTH-TOKEN" : encryptFormData},
		success: function (res) {
            if(res !=""){
            res=decryptMessage(res,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
			var response = JSON.parse(res);
			var mandateData = JSON.parse(response.mandate);
			
			if(response.success = 'true') {
				extractResponse(mandateData);
			}
          }
             remove_loader();
		},
		error: function (e) {
			console.log("Error in Extracting Response :: "+ e);
			 remove_loader();
		}
	});


	function extractResponse(mandateData) {
		if(mandateData.length > 0){
			$('.errorMsg').hide();
			var compiledTemplate = Handlebars.compile(rawTemplate);
			var ourGeneratedHTML = compiledTemplate(mandateData);
			var dataContainer = document.getElementById("mandateBody");
			dataContainer.innerHTML = ourGeneratedHTML;
		}		
	}
});