$(document).ready(function() {

sessionStorage.clear();
    function clearconsole()
 { 
   console.log(window.console);  
   if(window.console )
   {    
     console.clear();  
   }
 }
	
	//Numeric Validation
	function numericValidation(arg) {
	    $(arg).keydown(function (e) {
	    	$(arg).on('paste',function (e) {
				e.preventDefault();
			});
	    	
	        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110]) !== -1 ||
	            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
	            (e.keyCode >= 35 && e.keyCode <= 40)) {
	                 return;
	        }
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
	            e.preventDefault();
	        }
	    });
	}
	numericValidation('#loanAccountNumber');
	numericValidation('#loanAmount');
});

$(document).on('click', '#continueBtn', function(event) {
	const loanAccountNumber = $('#loanAccountNumber').val();
	var loanAmount = $('#loanAmount').val();
	var confirmloanAmount = $('#loanAccountNumber').val();

	if (!loanAmount) {
		$('.eNACHAlert').html("Please Enter the Loan Account Number");
		$('#enachAlert').show();
		$('.hvr-btn').on('click', function(e) {
			$('#enachAlert').hide();
		});

	} else if (!confirmloanAmount) {
		$('.eNACHAlert').html("Please enter the Loan Account Number");
		$('#enachAlert').show();
		$('.hvr-btn').on('click', function(e) {
			$('#enachAlert').hide();
		});
		
	} else if (confirmloanAmount != loanAmount) {
		$('.eNACHAlert').html("The loan account number doesnt match");
		$('#enachAlert').show();
		$('.hvr-btn').on('click', function(e) {
			$('#enachAlert').hide();
		});
	} else {
		const captcha = document.querySelector('#g-recaptcha-response').value;
		$.ajax({
			url: '/bin/reCaptcha?g-recaptcha-response=' + captcha,
			type: 'GET',
			success: function(msg) {
				let v1 = decryptMessage(msg.substring(16),CryptoJS.enc.Utf8.parse(msg.substring(0,16)),CryptoJS.enc.Utf8.parse(msg.substring(0,16)));

				var result = v1;//JSON.parse(msg);
				var status = result.success;
            	var status = true;	
				console.log(status);
				if(status == true) {
            	let sessionUID = uuidv4();
    			sessionStorage.setItem("EPLSESSIONID", sessionUID);
				let formData = {
                    loanNumber : loanAccountNumber,
                }
        
                let temkey ="";
                temkey=makeid();
                let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
                encryptFormData=encryptFormData+":"+temkey;

					$.ajax({
						url: '/bin/loanApi',
						type: 'POST',
                        headers: {"X-AUTH-SESSION" : sessionUID,
        						  "X-AUTH-TOKEN" : encryptFormData},
						//data: {
						//	captcha: captcha,
						//	loanNumber: loanAccountNumber
						//},
						success: function(msg) {
            				//console.log(msg);
                            let v1 = decryptMessage(msg,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
                            //console.log(v1);
							let ms = JSON.parse(v1);
                            //console.log(ms.msg);
							if (ms.msg == "Success") {
                                sessionStorage.setItem("EPLTID",ms.TID);
								window.location.assign('/content/enach-pl/home/registration-pl-otp.html');
							} else {
								$('.eNACHAlert').html(ms.msg);
								$('#enachAlert').show();
								sessionStorage.removeItem("EPLSESSIONID");
								$('.hvr-btn').on('click', function(e) {
									window.location.assign('/content/enach-pl/home/registration-pl.html');
								});
							}
						},
						error: function(e) {
							console.log(e);
						}
					});
				} else {
					$('.eNACHAlert').html("Please verify Re-Captcha!");
					$('#enachAlert').show();
					$('.hvr-btn').on('click', function(e) {
						$('#enachAlert').hide();
					});
				}
			},
			error: function(e) {
				status = false;
				console.log(e);
			}
		});
	}
});