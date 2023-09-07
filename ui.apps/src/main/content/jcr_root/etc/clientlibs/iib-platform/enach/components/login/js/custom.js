var tid = "";
var status = "";
var mobile = '';
var mobileRegex = /(6|7|8|9)\d{9}$/;
var urldata;
var landingUrl = localStorage.getItem('url');
var sessionUID="";
var param ="";
var gcaptcahsumbitot="";
var siteKeyOT = "6LcZqO0UAAAAADGD7vdKR-xXSwnfGFNxmC34yNi-";


function encryptMessage(message, key, iv) {
    return CryptoJS.AES.encrypt(message, key, {
      iv: iv,
      padding: CryptoJS.pad.Pkcs7,
      mode: CryptoJS.mode.CBC
    });
}

function decryptMessage(encryptedMessage,key,iv) {
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

$('#date-input').datepicker({
  	dateFormat: "yy-mm-dd",
    defaultDate: "2000-01-11",
    yearRange: "c-150:c+150",
    changeYear: true,
    changeMonth: true,
    minDate: moment(moment(), "YYYY-MM-DD").subtract(100, 'years').format('YYYY-MM-DD')
});
$('#date-input').on('keydown', function(event) {
	if (event.keyCode === 8) {
        return false;
    }
})

var urlParam = function(name) {
	var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
	if (results == null) {
		return null;
	} else {
		return decodeURI(results[1]) || 0;
	}
}

$(document).ready(function(){

    $(".mobile").val("");
    $(".Otp").val("");
    $(".dobVerify").val("");

	sessionUID = uuidv4();
    sessionStorage.setItem("JSESSIONID", sessionUID);


    grecaptcha.ready(function() {
        grecaptcha.execute(siteKeyOT, {
            action: 'otpinitae'
        }).then(function(token) {
            //debugger;
            gcaptcahsumbitot = token;
        });
    });



	param = urlParam('mode');
	urldata = param;

   // alert(param);

    if (param=="create")
    {
		//to do something
        $("#headingLine").html("Create your E-Mandate");
    }
    else
    {
        $("#headingLine").html("View/Cancel your existing Mandate(s)");
    }

});

//Numeric Validation
function numericValidation(arg) {
	$(arg).on('paste',function (e) {
		e.preventDefault();
	});

	$(arg).on('input',function (e) {
		var mobile = $(arg).val();

		if(mobile.length==1 && mobile!=9 && mobile!=8 && mobile!=7 && mobile!=6){
			var mobilestr = mobile.substring(0, mobile.length-1);
			$(arg).val( mobilestr);
		}

		if(isNaN(mobile) || mobile.indexOf(".") > -1){
			var mobilestr = mobile.substring(0, mobile.length-1);
			$(arg).val( mobilestr);

		}
	});
}

function otpValidation(arg) {
	$(arg).on('paste',function (e) {
		e.preventDefault();
	});

	$(arg).on('input',function (e) {
		var otp = $(arg).val();
		var otpRe = /\d/g;

		if(isNaN(otp) || otp.indexOf(".") > -1 || otp.length>6){
			var otpstr = otp.substring(0, otp.length-1);
			$(arg).val(otpstr);

		}
	});
}
//---------------------------------------

numericValidation('.mobile');
otpValidation('.otpverify');

$(document).on('click', '.otpSms', function () {
	mobile = $('.mobile').val();
    sessionStorage.setItem("vmbn1", CryptoJS.AES.encrypt(mobile, sessionUID));
    sessionStorage.setItem("vmbn11", encryptMessage(mobile, sessionUID,sessionUID));
	var otpverify = $('#otpverify').val();
	let formData = {
            mobile : mobile,
            otp	:otpverify
        }

        let temkey ="";
        temkey=makeid();
		let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
    	encryptFormData=encryptFormData+":"+temkey;




	if (tid != "") {
		$.ajax({
			url: "/bin/enachVerify",//?mobile=" + mobile + "&otp=" + otpverify,
			type: "POST",
            headers: {"X-AUTH-SESSION" : sessionUID,
        			"X-AUTH-TOKEN" : encryptFormData},
			success: function (result) {
                let v1 = decryptMessage(result,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
				//console.log(v1.substring(0,8));
				//if (decryptMessage(result,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey)) == "passtoGo") {
                	if(v1.substring(0,8) == "passtoGo") {
					$(".overlay").show();
					/*$.ajax({
						url: "/bin/accountDetails", 
						type: "POST",
						//data: {mobile: mobile},
               			headers: {"X-AUTH-SESSION" : sessionUID,
        						  "X-AUTH-TOKEN" : encryptFormData},
						success: function (res) {
							//console.log(res.substring(0,9));
                            res=decryptMessage(res,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
							var resObj = JSON.parse(res);
							$(".overlay").hide();
							if(resObj.success == 'true' && resObj.isDobRequired=='false') {
								//document.cookie = "flag=false; path=/";
                                sessionStorage.setItem("vfn2", CryptoJS.AES.encrypt("false", sessionUID));
								window.location.assign(landingUrl);
							} else if(resObj.success == 'false' && resObj.isDobRequired=='true') {
								$('.loginForm').hide(); 
								$('.dobForm').removeAttr('hidden');
							} else {
								$('#alertBox').show();
								$('#alertMessage').html('Error Fetching details');
							}
						},
						error: function (e) {
							console.log("Error in Verify OTP:: " + e);
						}
					});*/

						//	console.log(v1.substring(8));
							var resObj = JSON.parse(v1.substring(8));
							$(".overlay").hide();
							if(resObj.success == 'true' && resObj.isDobRequired=='false') {
								//document.cookie = "flag=false; path=/";

								let ddd	= JSON.parse(resObj.accountsFromDOB);

                                console.log(ddd.length +ddd[0] );
                                sessionStorage.setItem("vfn2", CryptoJS.AES.encrypt("false", sessionUID));
                                sessionStorage.setItem("vfndob", result+temkey);
								sessionStorage.setItem("_accvfo",encryptMessage(ddd[0].Account_Number, sessionUID,sessionUID) );	
								window.location.assign(landingUrl);
							} else if(resObj.success == 'false' && resObj.isDobRequired=='true') {
								$('.loginForm').hide(); 
								$('.dobForm').removeAttr('hidden');
							} else {
								$('#alertBox').show();
								$('#alertMessage').html('Error Fetching details');
							}

				} else {
					$('#alertBox').show();
					$('#alertMessage').html("Invalid OTP");
				}
			},
			error: function (e) {
				console.log("Error in Verify OTP:: " + e);
			}
		});
	}

	else if (mobile == "") {
		$('#alertBox').show();
		$('#alertMessage').html("Please Enter Your Mobile Number");
	} else if(!mobileRegex.test(mobile)) {
		$('#alertBox').show();
		$('#alertMessage').html("Please Enter Correct Mobile Number!");
	} else {

		let formData = {
            mobile : mobile,
            mode	:param,
            gcaptcha:gcaptcahsumbitot

        }

      grecaptcha.ready(function() {
        grecaptcha.execute(siteKeyOT, {
            action: 'enachotpverify'
        }).then(function(token) {
            //debugger;
            gcaptcahsumbitot = token;
        });
    });


        let temkey ="";
        temkey=makeid();
		let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
    	encryptFormData=encryptFormData+":"+temkey;


		$.ajax({
			url: "/bin/generateOtp", //?mobile=" + mobile + "&mode="+ param,
			type: "POST",
            headers: {"X-AUTH-SESSION" : sessionUID,
        			"X-AUTH-TOKEN" : encryptFormData},
			success: function (result) {
               // result=decryptMessage(result,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
				var real = JSON.parse(result);
				var real = JSON.parse(result);

                if(real.hasOwnProperty("error") && real.error == "Mobile not exists")
                {
                    $('#alertBox').show();
					$('#alertMessage').html('This is not a registered Mobile Number. Please contact the IndusInd Branch');

                }else
                {
				tid = real.TID;
				$('.Otp').removeAttr('hidden');
				//Start the timer
				$('.resendTimerBox').show();
				document.getElementById('resendTimer').innerHTML = 01 + ":" + 00;
				startTimer();
                }

			},
			error: function (e) {
				console.log("Error in Generate OTP:: " + e);
			}
		});
	}
	return false;
});

$(document).on('click', '#sendOtpCall', function () {

	//var mobile = $('.mobile').val();
	if (mobile != "") {
		$.ajax({
			url: "/bin/soapCallOTP?mobile=" + mobile,
			type: "GET",
            headers: {"X-AUTH-SESSION" : sessionUID},
			success: function (result) {
				var res = JSON.parse(result);
				status = res.status;
			//	console.log("Status: " + res.status);
				if (status == "success") {
					$('#alertBox').show();
					$('#alertMessage').html('OTP has been sent!');
					//Start the timer
					$('.resendTimerBox').show();
					document.getElementById('resendTimer').innerHTML = 01 + ":" + 00;
					startTimer();
				}
			},
			error: function (e) {
				console.log("Error in SOAP Request OTP:: " + e);
			}
		});
	}
});

$(document).on('click', '#sendOtpText',function () {

        let formData = {
            mobile : mobile,
            mode	:param,
            gcaptcha:gcaptcahsumbitot

        }

           grecaptcha.ready(function() {
        grecaptcha.execute(siteKeyOT, {
            action: 'otpreinitae'
        }).then(function(token) {
            //debugger;
            gcaptcahsumbitot = token;
        });
    });
        let temkey ="";
        temkey=makeid();
		let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
    	encryptFormData=encryptFormData+":"+temkey;

	if ($('.mobile').val() == "") {
		$('#alertBox').show();
		$('#alertMessage').html("Please fill value");
	} else {
		$.ajax({
			url: "/bin/generateOtp", //?mobile=" + mobile + "&mode=" + param,
			type: "POST",
            headers: {"X-AUTH-SESSION" : sessionUID,
        			"X-AUTH-TOKEN" : encryptFormData},
			success: function (result) {
				$('#alertBox').show();
				$('#alertMessage').html("OTP has been successfully sent");
				//Start the timer
				$('.resendTimerBox').show();
				document.getElementById('resendTimer').innerHTML = 01 + ":" + 00;
				startTimer();
			},
			error: function (e) {
				console.log("Error in Send OTP:: " + e);
			}
		});
	}
});

//DOB Verify AJAX
$(document).on('click', '.dobBtn', function () {
	var day, month, year;
	var date = new Date($('#date-input').val());
	day = ((date.getDate())>=10)? (date.getDate()) : '0' + (date.getDate());
	month = ((date.getMonth()+1)>=10)? (date.getMonth()+1) : '0' + (date.getMonth()+1);
	year = date.getFullYear();
	var dob = [day, month, year].join('-');
    sessionStorage.setItem("vdbn2", CryptoJS.AES.encrypt(dob, sessionUID));
	//sessionStorage.setItem("userDob", dob);
 	let formData = {
            mobile : mobile,
            dob	:dob
        }

        let temkey ="";
        temkey=makeid();
		let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
    	encryptFormData=encryptFormData+":"+temkey;



	if (dob != "") {
		$.ajax({
			url: "/bin/dobVerify",
			type: "POST",

            headers: {"X-AUTH-SESSION" : sessionUID,
        			"X-AUTH-TOKEN" : encryptFormData},
			success: function (result) {
                let res=result;
                result=decryptMessage(result,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
				//console.log(result);
				if(JSON.parse(result).length == 0){
					$('#alertBox').show();
					$('#alertMessage').html('DOB doesn\'t exists!');
				} else if(JSON.parse(result).length > 1){



                    let acc= JSON.parse(result);
                    $("#custaccnumber").append("<option>Please select account number</option>");
                    for(i=0;i<acc.length;i++)
                    {
						$("#custaccnumber").append('<option value="'+acc[i].Account_Number+'">'+acc[i].Account_Number+'</option>');
                    }

					$(".dobForm").hide();
					$(".custSelect").show();		
					$("#cutomerdeal").show();


           		 }else {

					let acc= JSON.parse(result);
                   // document.cookie = "flag=true; path=/";
					sessionStorage.setItem("vfn2", CryptoJS.AES.encrypt("true", sessionUID));
					sessionStorage.setItem("_accvfo",encryptMessage(acc[0].Account_Number, sessionUID,sessionUID) );
                    sessionStorage.setItem("vfndob", res+temkey);
					window.location.assign(landingUrl);

				}

			},
			error: function (e) {
				console.log("Error in E-NACH DOB:: " + e);
			}
		});
	}
});

$(".dealFetchBtn").click(function () {


    let accnumber = $('#custaccnumber :selected').text();
    if(accnumber.includes("account")){
        alert("Please select the account number");
    }
	else
    {
    sessionStorage.setItem("vfn2", CryptoJS.AES.encrypt("true", sessionUID));
    sessionStorage.setItem("_accvfo", encryptMessage(accnumber, sessionUID,sessionUID));
    sessionStorage.setItem("vfndob", encryptMessage(accnumber, sessionUID,sessionUID));    
    window.location.assign(landingUrl);
    }


});


$(".alertBtn").click(function () {
	$("#alertBox").hide();
});

function startTimer() {
	$('.resendViaSms').hide();
	$('.resendViaCall').hide();
	var presentTime = document.getElementById('resendTimer').innerHTML;
	var timeArray = presentTime.split(/[:]+/);
	var m = timeArray[0];
	var s = checkSecond((timeArray[1] - 1));
	if (s == 59) {
		m = m - 1;
	};
	if (m < 0) {
		$('.resendTimerBox').hide();
		$('.resendViaSms').show();
		$('.resendViaCall').show();
	} else {
		document.getElementById('resendTimer').innerHTML = m + ":" + s;
		setTimeout(startTimer, 1000);
	}
}

function checkSecond(sec) {
	if (sec < 10 && sec >= 0) {
		sec = "0" + sec;
	}; // add zero in front of numbers < 10
	if (sec < 0) {
		sec = "59";
	};
	return sec;
}

function uuidv4() {
    var cryptoObj = window.crypto || window.msCrypto;
    return ([1e7]+-1e3+-4e3+-8e3+-1e11).replace(/[018]/g, function(c) {
    	return (c ^ cryptoObj.getRandomValues(new Uint8Array(1))[0] & 15 >> c / 4).toString(16)
    });
}

