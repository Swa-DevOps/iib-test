	Handlebars.registerHelper('json', function(context) {
	  return JSON.stringify(context);
	});

	Handlebars.registerHelper('ifPos', function(context) {
	  var pos = false;


	  if (context.includes("Cancel")) {
	    pos = false;
	  } else {
	    pos = true;
	  }

	  return pos;
	});


	function getMandateDetails(handle) {
	  if (handle.umrn) {
	    add_loader();
	    window.location.assign('/content/enach/welcome/login/details.html?umrn=' + handle.umrn);
	  }
	}

	function encryptMessage(message, key, iv) {
	  return CryptoJS.AES.encrypt(message, key, {
	    iv: iv,
	    padding: CryptoJS.pad.Pkcs7,
	    mode: CryptoJS.mode.CBC
	  });
	}

	function decryptMessage(encryptedMessage, key, iv) {
	  return CryptoJS.AES.decrypt(encryptedMessage, key, {
	    iv: iv,
	    padding: CryptoJS.pad.Pkcs7,
	    mode: CryptoJS.mode.CBC
	  }).toString(CryptoJS.enc.Utf8)
	}



	function makeid() {
	  var text = "";
	  var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	  for (var i = 0; i < 16; i++)
	    text += possible.charAt(Math.floor(Math.random() * possible.length));

	  return text;
	}

	function cancelNewMandate() {

	  var selected_value = []; // initialize empty array 
	  var selected_data = []
	  $("input:checkbox[name=cancelme]:checked").each(function() {
	    selected_value.push($(this).val());
	    selected_data.push($(this).attr("data"));
	  });

	  var consent = $("input:checkbox[name=consent]:checked").length;


	  if (consent == 1 && selected_value.length > 0) {
	    cancelMandate(selected_value, selected_data);
	  } else if (consent == 0) {
	    $('#alertMessage').html("Please accept Terms and Condtions")
	    $('#alertBox').show();

	  } else if (selected_value.length == 0) {

	    $('#alertMessage').html("Select atleast one Mandate to Cancel")
	    $('#alertBox').show();
	  }


	};

	function cancelMandate(handle, handledata) {
	  var mobileNo = "";
	  let formData = {
	    mandate_id: handle.join()
	  };

	  sessionStorage.setItem("_vbcncl", encryptMessage(handle.join(), sessionStorage.getItem("JSESSIONID"), sessionStorage.getItem("JSESSIONID")));
	  $('#alertBoxYesNo').show();

	  $('.alertCancleBtn').on('click', function(e) {

	    $('#alertBoxYesNo').hide();

	  });

	  $('.alertCancleOTPBtn').on('click', function(e) {

	    $('#cancelotp').val('');
	    $('#alertBoxOTP').hide();

	  });

	  $('.alertBtnVO').on('click', function(e) {

	    sessionUID = sessionStorage.getItem("JSESSIONID");

	    mobile1 = sessionStorage.getItem("vmbn11");
	    check = decryptMessage(mobile1, sessionUID, sessionUID);

	    var otpverify = $('#cancelotp').val();
	    let formData = {
	      mobile: check,
	      otp: otpverify,
	      mode: "cancel"
	    }

	    let temkey = "";
	    temkey = makeid();
	    let encryptFormData = encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey)); //CryptoJS.AES.encrypt(formData.toString(), sessionID);
	    encryptFormData = encryptFormData + ":" + temkey;

	    $.ajax({
	      url: "/bin/enachVerify", //?mobile=" + mobile + "&otp=" + otpverify,
	      type: "POST",
	      headers: {
	        "X-AUTH-SESSION": sessionUID,
	        "X-AUTH-TOKEN": encryptFormData
	      },
	      success: function(result) {
	        let v1 = decryptMessage(result, CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey));
	        if (v1.substring(0, 8) == "passtoGo") {

	          $('#alertBoxOTP').hide();



	          mobile1 = decryptMessage(sessionStorage.getItem("vmbn11"), sessionStorage.getItem("JSESSIONID"), sessionStorage.getItem("JSESSIONID"));
	          check = decryptMessage(sessionStorage.getItem("_vbcncl"), sessionStorage.getItem("JSESSIONID"), sessionStorage.getItem("JSESSIONID"));
	          //)sessionStorage.getItem("_accvfo")

	          let formData = {
	            mandate_id: check,
	            mobile: mobile1,
	            accountNo: decryptMessage(sessionStorage.getItem("_accvfo"), sessionStorage.getItem("JSESSIONID"), sessionStorage.getItem("JSESSIONID")),
	            details: handledata.join()

	          };
	          add_loader();


	          let temkeycancel = "";
	          temkeycancel = makeid();
	          let encryptFormDataCancel = encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkeycancel), CryptoJS.enc.Utf8.parse(temkeycancel)); //CryptoJS.AES.encrypt(formData.toString(), sessionID);
	          encryptFormDataCancel = encryptFormDataCancel + ":" + temkeycancel;



	          $.ajax({
	            url: "/bin/digioCancelMandate", //?mobile=" + mobile + "&otp=" + otpverify,
	            type: "POST",
	            headers: {
	              "X-AUTH-SESSION": sessionUID,
	              "X-AUTH-TOKEN": encryptFormDataCancel
	            },
	            success: function(result) {

	              let res = decryptMessage(result, CryptoJS.enc.Utf8.parse(temkeycancel), CryptoJS.enc.Utf8.parse(temkeycancel));
	              let response = JSON.parse(res);

	              // console.log(res);

	              if (response.message == "success") {

	                sessionStorage.setItem("_vrtype", encryptMessage("cancel", sessionStorage.getItem("JSESSIONID"), sessionStorage.getItem("JSESSIONID")));
	                sessionStorage.setItem("_vrresd", encryptMessage(response.crmId, sessionStorage.getItem("JSESSIONID"), sessionStorage.getItem("JSESSIONID")));
	                window.location.assign('/content/enach/welcome/login/success.html?documentId=' + response.crmId);

	              } else {
	                remove_loader();
	                $('#alertMessage').html("Issue in Mandate Cancelation !! Please Try Again")
	                $('#alertBox').show();
	              }

	            },
	            error: function(e) {
	              console.log("Error in Digio Cancel Mandate Ajax :: " + e);
	            }
	          });

	        } else {
	          $('#opterror').show();
	        }
	      },
	      error: function(e) {
	        console.log("Error in Verify OTP:: " + e);
	      }
	    });





	  });


	  $('.alertBtn').on('click', function(e) {
	    console.log('clicked');
	    $('#alertBoxYesNo').hide();

	    sessionUID = sessionStorage.getItem("JSESSIONID");

	    mobile1 = sessionStorage.getItem("vmbn11");
	    check = decryptMessage(mobile1, sessionUID, sessionUID);


	    let formData = {
	      mobile: check,
	      mode: "cancel"
	      //  gcaptcha:gcaptcahsumbitot

	    }

	    let temkey = "";
	    temkey = makeid();
	    let encryptFormData = encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey)); //CryptoJS.AES.encrypt(formData.toString(), sessionID);
	    encryptFormData = encryptFormData + ":" + temkey;


	    $.ajax({
	      url: "/bin/generateOtp", //?mobile=" + mobile + "&mode="+ param,
	      type: "POST",
	      headers: {
	        "X-AUTH-SESSION": sessionUID,
	        "X-AUTH-TOKEN": encryptFormData
	      },
	      success: function(result) {
	        // result=decryptMessage(result,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
	        var real = JSON.parse(result);
	        tid = real.TID;
	        //$('.Otp').removeAttr('hidden');
	        //Start the timer
	        $('.resendTimerBox').show();
	        //document.getElementById('resendTimer').innerHTML = 01 + ":" + 00;
	        //startTimer();

	        $('#alertBoxOTP').show();

	      },
	      error: function(e) {
	        console.log("Error in Generate OTP:: " + e);
	      }
	    });




	  });
	};

	$(document).ready(function() {
	  add_loader();
	  var bytes, V1, V2, V3, V5;
	  var V4 = sessionStorage.getItem("JSESSIONID");

	  let countin = sessionStorage.getItem("countManageTime");
	  let countinFD = sessionStorage.getItem("countManageDTime");


	  if (countin == "" || countin == "null" || countin == null) {
	    sessionStorage.setItem("countManageTime", makeid());
	  } else {
	    if (countinFD == "" || countinFD == "null" || countinFD == null)
	      console.log("revisit");
	    // window.location.assign("/content/enach/welcome.html");
	    else
	      sessionStorage.removeItem("countManageDTime");
	  }


	  if (V4 == "" || V4 == "null" || V4 == null) {
	    window.location.assign('/content/enach/welcome.html');
	  }
	  bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vmbn1").toString(), V4);
	  V1 = bytes.toString(CryptoJS.enc.Utf8);
	  if (null != sessionStorage.getItem("vdbn2")) {
	    bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vdbn2").toString(), V4);
	    V2 = bytes.toString(CryptoJS.enc.Utf8);
	  } else {
	    V2 = "";
	  }
	  if (null != sessionStorage.getItem("vfn2")) {
	    bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vfn2").toString(), V4);
	    V3 = bytes.toString(CryptoJS.enc.Utf8);
	  } else {
	    V3 = "";
	  }

	  if (null != sessionStorage.getItem("_accvfo")) {
	    //bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vfn2").toString(), V4);
	    V5 = decryptMessage(sessionStorage.getItem("_accvfo"), sessionStorage.getItem("JSESSIONID"), sessionStorage.getItem("JSESSIONID")); //sessionStorage.getItem("_accvfo");//bytes.toString(CryptoJS.enc.Utf8);
	  } else {
	    V5 = "";
	  }

	  let formData1 = {
	    V1: V1,
	    V2: V2,
	    V3: V3,
	    V4: V4,
	    V5: V5
	  };

	  let temkey = "";
	  temkey = makeid();
	  let encryptFormData = encryptMessage(JSON.stringify(formData1), CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey)); //CryptoJS.AES.encrypt(formData.toString(), sessionID);
	  encryptFormData = encryptFormData + ":" + temkey;


	  var rawTemplate = $('#mandateRows').html();

	  $.ajax({
	    url: "/bin/manageMandateData",
	    type: "POST",
	    headers: {
	      "X-AUTH-SESSION": V4,
	      "X-AUTH-TOKEN": encryptFormData
	    },
	    success: function(res) {
	      if (res != "") {
	        res = decryptMessage(res, CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey));
	        var response = JSON.parse(res);
	        var mandateData = JSON.parse(response.mandate);

	        if (response.success = 'true') {
	          extractResponse(mandateData);
	        }
	      }
	      remove_loader();
	    },
	    error: function(e) {
	      console.log("Error in Extracting Response :: " + e);
	      remove_loader();
	    }
	  });


	  function extractResponse(mandateData) {
	    if (mandateData.length > 0) {
	      $('.errorMsg').hide();
	      var compiledTemplate = Handlebars.compile(rawTemplate);
	      var ourGeneratedHTML = compiledTemplate(mandateData);
	      var dataContainer = document.getElementById("mandateBody");
	      dataContainer.innerHTML = ourGeneratedHTML;
	    }
	  }



	  $("#cancelMnd").click(function() {
	    var selected_value = []; // initialize empty array 
	    $("#cancelme:checked").each(function() {
	      selected_value.push($(this).val());
	    });

	    alert("Handler for .click() called." + selected_value);
	  });





	});