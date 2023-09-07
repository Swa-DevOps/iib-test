var tid = "";
var status = "";
var mobile = '';
var mobileRegex = /(6|7|8|9)\d{9}$/;
var urldata;
var landingUrl = localStorage.getItem('cfdurl');
var sessionUID="";
var param ="";
let softEnachUrl = "https://loans.indusind.com/SNACH/SNach/Soft_Nach"


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
        $("#headingLine").html("View your existing E-Mandates");
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

$( "#custname" ).change(function() {
    mobile = $('.mobile').val();
	custcode=$('#custname').val();
	let formData = {
            mobile : mobile,
            otp	:"111111",
        	service:"cfd",
        	custcode:custcode

        }

    	let temkey ="";
        temkey=makeid();
		let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
    	encryptFormData=encryptFormData+":"+temkey;

    	$.ajax({
			url: "/bin/cfd/enachVerify",//?mobile=" + mobile + "&otp=" + otpverify,
			type: "POST",
            headers: {"X-AUTH-SESSION" : sessionUID,
        			"X-AUTH-TOKEN" : encryptFormData},
			success: function (result) {
               
                let v1 = decryptMessage(result,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
				 	if(v1.substring(0,8) == "passtoGo") {
					$(".overlay").show();
						var resObj = JSON.parse(v1.substring(8));
							$(".overlay").hide();
							if(resObj.success == 'true' && resObj.hasOwnProperty("single")) {
								//document.cookie = "flag=false; path=/";
                                sessionStorage.setItem("vfn2", CryptoJS.AES.encrypt("true", sessionUID));
                                sessionStorage.setItem("vfndeal", result+temkey);
								window.location.assign(landingUrl);
							}  else if(resObj.success == 'true' && resObj.hasOwnProperty("deals")) {

								let cust=(resObj.deals).replace(new RegExp('"', 'g'),'');
                                cust=cust.replace('[','');
                                cust=cust.replace(']','');
                                cust=cust.split(',');
								let i=0;
                                $("#dealno").html("");
                                 $("#dealno").append('<option value="">Please Select the Deal To Proceed</option>');
                                while (i < cust.length) { 
                                    $("#dealno").append('<option value="'+cust[i]+'">'+cust[i]+'</option>');
                                i++; 
                           		 }

                                $('.dealSelect').removeAttr('hidden');
                                $('.dealFetchBtn').removeAttr('hidden');
                            } else if(resObj.success == 'false' && resObj.hasOwnProperty("errormsg")) {

                                $('#alertMessage').html(resObj.errormsg);
                                $('#alertBox').show();


                            }else if(resObj.success == 'action' && resObj.hasOwnProperty("single")) {

                                sessionStorage.setItem("vfn2", CryptoJS.AES.encrypt("true", sessionUID));
                                sessionStorage.setItem("vfndeal", result+temkey);
                                $('#alertMessageA').html((resObj.single).substring(((resObj.single).indexOf(":"))+1));
								$('.white_content').hide();
                                $('.white_content_action').show();
                                $('#alertBox').show();



                            } else
                            {
								$('#alertMessage').html("Some Issues Occured !! Try after some time");
                                $('#alertBox').show();
                            }
                    }
                },
			error: function (e) {
				console.log("Error in Deal Fetch:: " + e);
			}
		});
});


$(document).on('click', '.otpSms', function () {
	mobile = $('.mobile').val();
    sessionStorage.setItem("vmbn1", CryptoJS.AES.encrypt(mobile, sessionUID));
	var otpverify = $('.otpverify').val();
	let formData = {
            mobile : mobile,
            otp	:otpverify,
        	service:"cfd"
        }

        let temkey ="";
        temkey=makeid();
		let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
    	encryptFormData=encryptFormData+":"+temkey;





	if (tid != "") {
		$.ajax({
			url: "/bin/cfd/enachVerify",//?mobile=" + mobile + "&otp=" + otpverify,
			type: "POST",
            headers: {"X-AUTH-SESSION" : sessionUID,
        			"X-AUTH-TOKEN" : encryptFormData},
			success: function (result) {
                debugger;
                     let v1 = decryptMessage(result,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
				 	if(v1.substring(0,8) == "passtoGo") {
					$(".overlay").show();
						var resObj = JSON.parse(v1.substring(8));
							$(".overlay").hide();
							if(resObj.success == 'true' && resObj.hasOwnProperty("single")) {
                                sessionStorage.setItem("vfn2", CryptoJS.AES.encrypt("true", sessionUID));
                                sessionStorage.setItem("vfndeal", result+temkey);
								window.location.assign(landingUrl);
							}  else if(resObj.success == 'true' && resObj.hasOwnProperty("customer")) {

								let cust=(resObj.customer).replace(new RegExp('"', 'g'),'');
                                cust=cust.replace('[','');
                                cust=cust.replace(']','');
                                cust=cust.split(',');
								let i=0;
                                 $("#custname").append('<option value="">Please Select the Customer Code To Proceed</option>');
                                while (i < cust.length) { 
                                    $("#custname").append('<option value="'+cust[i].substring(0,cust[i].indexOf(":"))+'">'+cust[i]+'</option>');
                                i++; 
                           		 }
                                $('.loginForm').hide(); 
								$('.custdealForm').removeAttr('hidden');
                                $('.custSelect').removeAttr('hidden');


							}else if(resObj.success == 'true' && resObj.hasOwnProperty("deals")) {
								let cust=(resObj.deals).replace(new RegExp('"', 'g'),'');
                                cust=cust.replace('[','');
                                cust=cust.replace(']','');
                                cust=cust.split(',');
								let i=0;
                                 $("#dealno").append('<option value="">Please Select the Deal To Proceed</option>');
                                while (i < cust.length) { 
                                    $("#dealno").append('<option value="'+cust[i]+'">'+cust[i]+'</option>');
                                i++; 
                           		 }
								$('.loginForm').hide();
                          		$('#custDealTitle').html("Select Your Deal Code To Proceed Further")
                                $('.custdealForm').removeAttr('hidden');
                                $('.dealSelect').removeAttr('hidden');
                                $('.dealFetchBtn').removeAttr('hidden');
							} else if (resObj.success == 'action') {

                                 sessionStorage.setItem("vfn2", CryptoJS.AES.encrypt("true", sessionUID));
                                sessionStorage.setItem("vfndeal", result+temkey);
                               $('#alertMessageA').html((resObj.single).substring(((resObj.single).indexOf(":"))+1));
								$('.white_content').hide();
                                $('.white_content_action').show();
                                $('#alertBox').show();

							}  else {
									$('#alertMessage').html(resObj.errormsg);
                                $('#alertBox').show();
								//$('#alertMessage').html('Error Fetching details');

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
            service:"cfd"
        }

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
				tid = real.TID;
				$('.Otp').removeAttr('hidden');
				//Start the timer
				$('.resendTimerBox').show();
				document.getElementById('resendTimer').innerHTML = 01 + ":" + 00;
				startTimer();

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
            service:"cfd"
        }

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
				var res = JSON.parse(result);
               if ( res.hasOwnProperty("TIDI"))
                   {
					$('#sendOtpText').hide();
                $('#alertBox').show();
				$('#alertMessage').html("You have exceeded maximum number of re-attempts for sending OTP");

                   }
                   else{		
				$('#alertBox').show();
				$('#alertMessage').html("OTP has been successfully sent");
				//Start the timer
				$('.resendTimerBox').show();
				document.getElementById('resendTimer').innerHTML = 01 + ":" + 00;
				startTimer();
            }
			},
			error: function (e) {
				console.log("Error in Send OTP:: " + e);
			}
		});
	}
});


$(document).on('click', '#okayButtonA', function () {

		window.location.assign(softEnachUrl);
});


$(document).on('click', '#proceedButtonA', function () {

			window.location.assign(landingUrl);
});



//Fetch Verify AJAX
$(document).on('click', '.dealFetchBtn', function () {
	let cust=$('#custname').val();
    let deal=$('#dealno').val()

    var dob = [cust, deal].join(':');
    sessionStorage.setItem("vdbn2", CryptoJS.AES.encrypt(dob, sessionUID));
	//sessionStorage.setItem("userDob", dob);
 	let formData = {
            mobile : mobile,
            dealno:deal
        }

        let temkey ="";
        temkey=makeid();
		let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
    	encryptFormData=encryptFormData+":"+temkey;



	if (dealno != "") {
		$.ajax({
			url: "/bin/cfd/dealVerify",
			type: "POST",

            headers: {"X-AUTH-SESSION" : sessionUID,
        			"X-AUTH-TOKEN" : encryptFormData},
			success: function (result) {
                let res=result;
                result=decryptMessage(result,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
                var resObj = JSON.parse(result.substring(result.indexOf("{")));
                if(resObj.success == 'false' && resObj.hasOwnProperty('error')) {
					$('#alertBox').show();
					$('#alertMessage').html(resObj.error); 
					//$('#alertMessage').html('Facing Some Issue Fetching Deal !!! Please try after Sometime');
				} else if(resObj.success == 'action' && resObj.hasOwnProperty("error")) {

                                sessionStorage.setItem("vfn2", CryptoJS.AES.encrypt("true", sessionUID));
                                sessionStorage.setItem("vfndeal", res+temkey);
                                $('#alertMessageA').html((resObj.error));
								$('.white_content').hide();
                                $('.white_content_action').show();
                                $('#alertBox').show();



                }else {
					sessionStorage.setItem("vfn2", CryptoJS.AES.encrypt("true", sessionUID));
                    sessionStorage.setItem("vfndeal", res+temkey);
					window.location.assign(landingUrl);

				}

			},
			error: function (e) {
				console.log("Error in E-NACH DOB:: " + e);
			}
		});
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

