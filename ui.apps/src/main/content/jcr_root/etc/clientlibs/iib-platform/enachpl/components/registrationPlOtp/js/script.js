var count = 0;
var user = '';
var userUnParsed;
var mobileNumber = sessionStorage.getItem("EPLTID");

let sessionCheck = sessionStorage.getItem("EPLSESSIONID");
    if(sessionCheck=="" || sessionCheck == "null" || sessionCheck == null )
    {
        sessionCheck ="";
         window.location.assign("/content/enach-pl/home/registration-pl.html");
    }

$(document).ready(function() {
	$(".otpBox").keydown(function (e) {
	    if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110]) !== -1 ||
	        (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
	        (e.keyCode >= 35 && e.keyCode <= 40)) {
	             return;
	    }
	    if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
	        e.preventDefault();
	    }
	});
});

$(document).on('click', '.confirmbutton', function(event) {
	if (count < 3) {
		count = count + 1;
		console.log(count)
		const otp = $('.enachofferInput').val();
		if (!otp) {

			$('.eNACHAlert').html("Please Enter the Otp");
			$('#enachAlert').show();
			$('.hvr-btn').on('click', function(e) {
				$('#enachAlert').hide();
			});

		} else {

			let formData = {
                    otp: otp,
					mobile: mobileNumber
                }
        
                let temkey ="";
                temkey=makeid();
                let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
                encryptFormData=encryptFormData+":"+temkey;
				let sessionUID = sessionStorage.getItem("EPLSESSIONID");





			$.ajax({
				url: '/bin/verifyotp',
				type: 'POST',
				/*data: {
					otp: otp,
					mobile: mobileNumber
				},*/
				 headers: {"X-AUTH-SESSION" : sessionUID,
        						  "X-AUTH-TOKEN" : encryptFormData},

				success: function(msg) {

                    	console.log(msg);
                            let v1 = msg.substring(0,8) + decryptMessage(msg.substring(8),CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
                        //    console.log(v1);
                    if (v1.substring(0,9) == "Verified{") {

                        sessionStorage.setItem("EPLTIDDDS",temkey+msg);


						window.location.assign('/content/enach-pl/home/personal-loan-details.html');
					} else {
						$('.eNACHAlert').html("Invalid OTP");
						$('#enachAlert').show();
						$('.hvr-btn').on('click', function(e) {
							$('#enachAlert').hide();
						});
					}

				},
				error: function(e) {
					console.log(e);
				}

			})
		}
	} else {
		$('.eNACHAlert').html("You have exceeded the Otp Limit, You will be redirected to Home Page");
		$('#enachAlert').show();
		$('.hvr-btn').on('click', function(e) {
			window.location.assign('/content/enach-pl/home/registration-pl.html')
		});

	}
});
