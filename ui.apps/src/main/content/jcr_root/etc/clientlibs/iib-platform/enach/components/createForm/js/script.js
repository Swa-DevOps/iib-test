var arr = [];
var mobileNo = "";
var bankList;
 var bytes="";
var sessionID="";
var interactID="";
let maxAmtLimit = 1000000;

$(document).ready(function() {

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


    $("#tnc").prop( "checked", true );


  $('#tnc').click(function() {   
        if(!$(this).is(':checked')){
            $(".confirmbutton").prop("disabled",true); 
            $("#tcerror").show(); 	
            $("#tcerror").html("Kindly select T&C to proceed further");  
            $("#tcerror").addClass('iError');  
        }else
        {
			     $(".confirmbutton").prop("disabled",false);
           $("#tcerror").removeClass('iError'); 
           $("#tcerror").hide();
           
        }
    });

	let countin = sessionStorage.getItem("countCreateTime");
 	if(countin=="" || countin == "null" || countin == null )
    {
		sessionStorage.setItem("countCreateTime",makeid());
    }else
    {
		 window.location.assign("/content/enach/welcome.html");
    }

    sessionID = sessionStorage.getItem("JSESSIONID");
    if(sessionID=="" || sessionID == "null" || sessionID == null )
    {
        window.location.assign("/content/enach/welcome.html");
    }


    function getDate( element ) {
      var date;
      try {
        date = $.datepicker.parseDate( "yy-mm-dd", element.value );
      } catch( error ) {
        date = null;
      }
 
      return date;
    }

    var $startDate = $('#sdate'),
        $endDate = $('#edate');

    $startDate.datepicker({
        dateFormat: "yy-mm-dd",
        yearRange: "c-150:c+150",
        changeYear: true,
        changeMonth: true,
        minDate: moment(moment(), "YYYY-MM-DD").add(7, 'days').format('YYYY-MM-DD')
	}).on( "change", function() {
        var endDate = moment(moment(getDate( this )), "YYYY-MM-DD").add(10, 'years').subtract(1,'days').format('YYYY-MM-DD');
        	//endDate = moment(moment(endDate), "YYYY-MM-DD").subract(1, 'day').format('YYYY-MM-DD');
          $endDate.datepicker( "option", "maxDate", endDate );
          $endDate.datepicker( "setDate", endDate );
        	updateDates($('.transferFrequency').val(), $(this).val());
        });
    $endDate.datepicker({
        dateFormat: "yy-mm-dd",
        yearRange: "c-150:c+150",
        changeYear: true,
        changeMonth: true,
        minDate: moment(moment(), "YYYY-MM-DD").add(2, 'months').add(7, 'days').format('YYYY-MM-DD'),
        maxDate: moment(moment(), "YYYY-MM-DD").add(3651, 'days').add(7, 'days').format('YYYY-MM-DD')
    });

    
    /*$('#edate').dateTimePicker({
        limitMax: moment(moment(), "YYYY-MM-DD").add(3651, 'days'),

    });*/



    //$("#createform").reset();
		document.getElementById("createform").reset();
		$('input[name=maximum_amount]').val("10000");
    	$('select option:contains("Monthly")').prop('selected',true);

	 var input = document.getElementById("awesomeHu");

	// console.log(input)

	    var awesomplete = new Awesomplete(input, {
	        minChars: 2,
	        maxItems: 10, //how many items are needed to be displayed 
	        autoFirst: false
	    });

	$.ajax({
		url: "/bin/bankList", 
		type: "POST",
		data: null,
		success: function (res) {
			var response = JSON.parse(res);
			bankList = response;
			//console.log(response);
			awesomplete.list = response.bankList
		},
		error: function (e) {
			console.log("Error in Bank List :: " + e);
		}
	});


	/*----------Create E-NACH AJAX----------
	$.ajax({
		url: "/bin/createEnach", 
		type: "POST",
		data: null,
		success: function (res) {
			var response = JSON.parse(res);
			if(response.success == 'true') {
				console.log("Success in Create E-NACH");
			}
		},
		error: function (e) {
			console.log("Error in Create E-NACH Ajax :: " + e);
		}
	});
	*///----------------------------------------

	var input = $('input[name=destinationBank]');

	//Character Validation
	function characterValidation(arg) {
		$(arg).on('paste',function (e) {
			e.preventDefault();
		});

		$(arg).keypress(function (e) {
			var regex = new RegExp(/^[a-zA-Z\s]+$/);
			var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
			if (regex.test(str)) {
				return true;
			}
			else {
				e.preventDefault();
				return false;
			}
		});
	}

    	//Character Validation
	function characterValidationName(arg) {
		$(arg).on('paste',function (e) {
			e.preventDefault();
		});

		$(arg).keypress(function (e) {
			var regex = new RegExp(/^[a-zA-Z\s'.-]+$/);
			var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
			if (regex.test(str)) {
				return true;
			}
			else {
				e.preventDefault();
				return false;
			}
		});
	}


	characterValidationName('.customerName');
	characterValidation('.bankName');

	//Numeric Validation
	function numericValidation(arg) {
		$(arg).on('paste',function (e) {
			e.preventDefault();
		});

		$(arg).keydown(function (e) {
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

       function alphanumericValidation(argAN) {
		$(argAN).on('paste',function (e) {
			e.preventDefault();
		});

		$(argAN).keydown(function (e) {
	        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110]) !== -1 ||
	            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
	            (e.keyCode >= 35 && e.keyCode <= 40)) {
	                 return;
	        }
	        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 90)) && (e.keyCode < 96 || e.keyCode > 105)) {
	            e.preventDefault();
	        }
	    });
	}
	alphanumericValidation('.accountNumber');
	alphanumericValidation('.confirmAccountNo'); 
	numericValidation('.maximum_amount'); 
	numericValidation('.aadharId');

	var accountArrays=[];
	var resFlag="";

    bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vfn2").toString(), sessionID);
	resFlag  = bytes.toString(CryptoJS.enc.Utf8);

	/*if (document.cookie.length != 0) {
		var namevaluepair = document.cookie.split("; ");
		for (var i = 0; i < namevaluepair.length; i++) {
			var namevaluearray = namevaluepair[i].split("=");
			if (namevaluearray[0] == "flag") {
				resFlag = namevaluearray[1];
			}
		}
	}*/

//	alert(resFlag);
	if(resFlag.toString()==="true"){
		 bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vmbn1").toString(), sessionID); 
			var userMobNo = bytes.toString(CryptoJS.enc.Utf8);
 		bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vdbn2").toString(), sessionID); 
			var dob = bytes.toString(CryptoJS.enc.Utf8);
			mobileNo=userMobNo;


        let res = sessionStorage.getItem("vfndob");
        sessionStorage.setItem("vfndob","");
		let temkey = res.substring(res.length-16);
        res=res.substring(0,res.length-16);
		if((res != ""))
                {
                res=decryptMessage(res,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
				var response = JSON.parse(res);
                //console.log(response);
				for(var i=0; i<response.length; i++) {
					accountArrays.push(response[i].Account_Number);
					var myvalue = "XXXX-XXXX-"+response[i].Account_Number.substring(8, 13) + " ( " +response[i].AccountType + " )" ;
					$('.accountNo').append('<option id='+response[i].Banking_Cif_Id+'##'+response[i].Account_Number+'>'+myvalue+'</option>')
				}
                }else
                {
                    console.log("Session expired or unauthorized access");
					window.location.assign("/content/enach/welcome.html");
                }

	} else {
         bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vmbn1").toString(), sessionID); 
		var userMobNo = bytes.toString(CryptoJS.enc.Utf8)
        mobileNo=userMobNo;
        let formData = {
            mobile : userMobNo,
        }
		let res = sessionStorage.getItem("vfndob");
        sessionStorage.setItem("vfndob","");
		let temkey = res.substring(res.length-16);
        res=res.substring(0,res.length-16);
		if((res != ""))
                {
                res=decryptMessage(res,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
                res=res.substring(8);
				let response1 = JSON.parse(res);
			 	let test=	response1.accountsFromDOB;
				//console.log(test);
                //console.log(test.replace(/\//g, '') );

				var response = JSON.parse(response1.accountsFromDOB);
                //console.log(response);
				for(var i=0; i<response.length; i++) {
					accountArrays.push(response[i].Account_Number);
					var myvalue = "XXXX-XXXX-"+response[i].Account_Number.substring(8, 13) + " ( " +response[i].AccountType + " )" ;
					$('.accountNo').append('<option id='+response[i].Banking_Cif_Id+'##'+response[i].Account_Number+'>'+myvalue+'</option>')
				}
                }else
                {
                    console.log("Session expired or unauthorized access");
					window.location.assign("/content/enach/welcome.html");
                }




      /*  let temkey ="";
        temkey=makeid();
		let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
    	encryptFormData=encryptFormData+":"+temkey;*/




	}
    var updateDates = function(transferFrequency, startDate) {
        if(!startDate) {
            startDate = moment(moment(), "YYYY-MM-DD").add(7, 'days').format('YYYY-MM-DD');
        }
        if(transferFrequency === "Weekly") {
			$endDate.datepicker( "option", "minDate", moment(startDate, "YYYY-MM-DD").add(14, 'days').format('YYYY-MM-DD') );
        }
        else if (transferFrequency === "Monthly") {
			$endDate.datepicker( "option", "minDate", moment(startDate, "YYYY-MM-DD").add(2, 'months').format('YYYY-MM-DD') );
        }
         else if (transferFrequency === "Yearly") {
			$endDate.datepicker( "option", "minDate", moment(startDate, "YYYY-MM-DD").add(1, 'years').format('YYYY-MM-DD') );
        }
    }

	$('.cmp-date-time-picker').click(function(){
		var maximum_amount = parseFloat($('input[name=maximum_amount]').val());
		var  startDate = $('input[name=startDate]').val();

        caluclateEndDate(startDate);

		var endDate = $('input[name=endDate]').val();	
        var transferFrequency = $('.transferFrequency').val();

		if (transferFrequency == "Adhoc")
        {
			$('input[name=endDate]').prop("disabled", true);
            endDate = startDate;
        }
        else
        {
			$('input[name=endDate]').prop("disabled", false);
        }


		if(maximum_amount && startDate && endDate){
			calcTotalAmountAndInvestments();
		}
	});

	$('.transferFrequency, .maximum_amount').change(function(){



		var maximum_amount = parseFloat($('input[name=maximum_amount]').val());
		var startDate = $('input[name=startDate]').val();


        var endDate = $('input[name=endDate]').val();		

		var transferFrequency = $('.transferFrequency').val();

		if (transferFrequency == "Adhoc")
        {
			$('input[name=endDate]').prop("disabled", true);
            endDate = startDate;
        }
        else
        {
			$('input[name=endDate]').prop("disabled", false);
        }
        updateDates(transferFrequency, startDate);

        if(maximum_amount && startDate && endDate){
			calcTotalAmountAndInvestments();
		}
	});

	$('#openRD').change(function() {
		var transferFrequency = $('.transferFrequency').val();
		var startDate = $('input[name=startDate]').val();
		var endDate = $('input[name=endDate]').val();
		var rdAmount = $('input[name=maximum_amount]').val();

		$('.rdTransferFrequency').html(transferFrequency);
		$('.rdAmount').html(rdAmount ? rdAmount : "NA");
		$('.rdStartDate').html(startDate ? startDate : "NA");
		$('.rdEndDate').html(endDate ? endDate : "NA");
		$(".recurringWrapper").toggle();
	});

	$('input[name=maximum_amount]').focusout(function(){
		var amount= $('input[name=maximum_amount]').val();
		if(amount!=""){
			if(!(amount>1 && amount <= maxAmtLimit)){
				$('#alertBox').show();
				$('#alertMessage').html("Amount to transfer should be between 1 to 1000000");
			}
		}
	});




    $('input[name=destinationBankId]').on('keydown', function(e) { 
  if (e.keyCode == 9) 
  {



      var destinationBankId= $('input[name=destinationBankId]').val();
		// var reg = new RegExp('/[A-Z|a-z]{4}[0][\d]{6}$/');
		var reg = new RegExp('/[A-Z|a-z]{4}[a-zA-Z0-9]{7}$/');
     // alert(destinationBankId + reg.test(destinationBankId) + (destinationBankId.match(/[A-Z|a-z]{4}[0][0-9]{6}$/)) + destinationBankId.length  );

        if (destinationBankId.length == 11 && destinationBankId == (destinationBankId.match(/[A-Z|a-z]{4}[0][a-zA-Z0-9]{6}$/)) ) {



           //  $('input[name=maximum_amount]').setFocus();

        } else {
				e.preventDefault();
                $('#alertBox').show();
				$('#alertMessage').html("Please enter a valid IFSC code");
            	$('input[name=destinationBankId]').focus();
        }
  }

	});


    $('input[name=destinationBank]').focusout(function(){
        var destinationBank= $('input[name=destinationBank]').val()
        var bankString=bankList.bankList;
		if(!bankString.includes(destinationBank))
        {
		$('#alertBox').show();
        $('#alertMessage').html("Please select Valid Bank from List");
        }
	});

 	$('input[name=customerEmail]').focusout(function(){
		var customerEmail= $('input[name=customerEmail]').val();
		var re = new RegExp(/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/);
		//	alert(re.test(customerEmail) + "----" + customerEmail );
        if(customerEmail=="" || !re.test(customerEmail)){
           // e.preventDefault();
			$('#alertBox').show();
			//$('#alertMessage').html("Please enter correct Registered E-mail ID");
            $('#alertMessage').html("Please enter valid E-mail ID");
			//$('input[name=customerEmail]').setFocus();
        }

	});

    $('input[name=customerAccountNo]').focusout(function(){
		var customerAccountNo= $('input[name=customerAccountNo]').val();
		var accountNumber= $('input[name=accountNumber]').val();

        if(accountNumber != customerAccountNo){
			$('#alertBox').show();
			$('#alertMessage').html("Account numbers did not match, Please re-enter the account numbers.");
			//$('input[name=customerAccountNo]').setFocus();
        }

	});


 $('input[name=customerAccountNo]').on('keydown', function(e) { 
     if (e.keyCode == 9) { 

         var customerAccountNo= $('input[name=customerAccountNo]').val();
		 var accountNumber= $('input[name=accountNumber]').val();

        if(accountNumber != customerAccountNo){
            e.preventDefault(); 
			$('#alertBox').show();
			$('#alertMessage').html("Account numbers did not match, Please re-enter the account numbers.");

        }else
        {
           // $('input[name=destinationBankId]').setFocus();
        }
       }
	});


	$('input[name=aadharId]').focusout(function(){
		var aadhar= $('input[name=aadharId]').val();
		if(aadhar!=""){
			if(aadhar.length<12 || aadhar.length>16){
				$('#alertBox').show();
				$('#alertMessage').html("Aadhaar ID should be 12 to 16 characters");
			}
		}
	});

	$('.confirmbutton').click(function() {
		var logoURL = "http://bank.indusind.com/content/dam/iibPlatform/enach/images/logo.jpg";
		var options = {
            	"environment":"production",
				"callback": function(t) {
					if(t.hasOwnProperty('error_code')) {
						window.location.assign("/content/enach/welcome/login/fail.html?failureMessage="+t.message+"&interactionId="+interactID);
					} else { 
						window.location.assign("/content/enach/welcome/login/success.html?documentId="+t.digio_doc_id+"&interactionId="+interactID);
					}
				},
				"logo":  encodeURIComponent(logoURL),      
		};



		var customerNameLabel = $('.customerNameLabel').html().split("<")[0];
		var bankNameLabel = $('.bankNameLabel').html().split("<")[0];
		var accountNumberLabel = $('.accountNumberLabel').html().split("<")[0];
		var confirmAccountLabel = $('.confirmAccountLabel').html().split("<")[0];
		var ifscLabel = $('.ifscLabel').html().split("<")[0];
		var maximumAmountLabel = $('.maximumAmountLabel').html().split("<")[0];
		var transferFreqLabel = $('.transferFreqLabel').html().split("<")[0];
		var startDateLabel = $('.startDateLabel').html().split("<")[0];
		var endDateLabel = $('.endDateLabel').html().split("<")[0];
		var totalAmountLabel = $('.totalAmountLabel').html().split("<")[0];
		var totalInstallmentsLabel = $('.totalInstallmentsLabel').html().split("<")[0];
		var aadharLabel ="aadhar";
		var referralCodeLabel = $('.referralCodeLabel').html().split("<")[0];
		var creditAccountLabel = $('.creditAccountLabel').html().split("<")[0];
        var customerEmailLabel = $('.customerEmailLabel').html().split("<")[0];

		var customerName = $('input[name=customerName]').val() ? $('input[name=customerName]').val() : "";
		var customerEmail = $('input[name=customerEmail]').val() ? $('input[name=customerEmail]').val() : "";
    	var destinationBank = $('input[name=destinationBank]').val();
		var destinationBankId = $('input[name=destinationBankId]').val();
		var customerAccountNo = $('input[name=accountNumber]').val();
		var confirmAccountNo = $('.confirmAccountNo').val();
		var maximum_amount = $('input[name=maximum_amount]').val();
		var transferFrequency = $('.transferFrequency').val();
		var startDate = $('input[name=startDate]').val();
		var endDate = $('input[name=endDate]').val();
		var totalAmount = $('.totalAmount').val();
		var totalInstallments = $('.totalInstallments').val();
		var aadharId = "1";
		var aadharNo = "";
		var accLastDigits = $('.accountNo').val().substr(10,14);
        var cifid =  $('.accountNo').children(":selected").attr("id");
		var referralCode = $('input[name=referralCode]').val();
		var accountNo="";
		for(let i=0; i<accountArrays.length; i++) {
			let n = accountArrays[i].includes(accLastDigits);
			if(n==true) {
				accountNo = accountArrays[i];
				break;
			}
		}
		accountNo = cifid.substring(cifid.lastIndexOf('#')+1);
        cifid = cifid.substring(0,cifid.indexOf('#'));
		var formData = {
				destinationBank : destinationBank,
				destinationBankId : destinationBankId,
				customerAccountNo : confirmAccountNo,
				maximum_amount : maximum_amount,
				mobileNo : mobileNo,
				customerName : customerName, // To pick from API/Cookie
            	customerEmail : customerEmail,
				transferFrequency : transferFrequency,
				startDate : startDate,
				endDate : endDate,
				accountNo : accountNo,
				confirmAccountNo : confirmAccountNo,
				totalAmount : totalAmount,
				totalInstallments : totalInstallments,
				referralCode : referralCode,
				aadharNo : aadharNo,
				customerNameLabel : customerNameLabel,
				bankNameLabel : bankNameLabel,
				accountNumberLabel : accountNumberLabel,
				confirmAccountLabel : confirmAccountLabel,
				ifscLabel : ifscLabel,
				maximumAmountLabel : maximumAmountLabel,
				transferFreqLabel : transferFreqLabel,
				startDateLabel : startDateLabel,
				endDateLabel : endDateLabel,
				totalAmountLabel : totalAmountLabel,
				totalInstallmentsLabel : totalInstallmentsLabel,
				aadharLabel : aadharLabel,
				referralCodeLabel : referralCodeLabel,
				creditAccountLabel : creditAccountLabel,
            	customerEmailLabel : customerEmailLabel,
           	 	cifid:cifid,
                tnc:"yes"

		}

		var accountNumber = $('input[name=accountNumber]').val();
		var confirmAccountNumber = $('input[name=customerAccountNo]').val();
		var confirmAccountNumberLength = $('input[name=customerAccountNo]').val().length;
		var accountNumberLength = $('input[name=accountNumber]').val().length;
    	var bankStringnew=bankList.bankList;


		if(customerName == ""){
			$('#alertBox').show();
			$('#alertMessage').html("Please enter a account holder name");
		}else if(customerEmail == ""){
			$('#alertBox').show();
			$('#alertMessage').html("Please enter a registered email id");
		}else if((destinationBank == "")||(!bankStringnew.includes(destinationBank))){
			$('#alertBox').show();
			$('#alertMessage').html("Bank name should be correct");
		}else if(accountNumberLength < 6 || accountNumberLength > 34){
			$('#alertBox').show();
			$('#alertMessage').html("Please enter a valid account number");
		}else if(accountNumber != confirmAccountNumber){
			$('#alertBox').show();
			$('#alertMessage').html("Account number missmatch, failed to confirm");
		}else if(maximum_amount == ""){
			$('#alertBox').show();
			$('#alertMessage').html("Amount should not be blank");
		}else if(startDate == ""){
			$('#alertBox').show();
			$('#alertMessage').html("Start-Date should not be blank");
		}else if(endDate == ""){
			$('#alertBox').show();
			$('#alertMessage').html("End-Date should not be blank");
		}else if(accountNumber != confirmAccountNumber){
			$('#alertBox').show();
			$('#alertMessage').html("Account numbers did not match, Please re-enter the account numbers.");
		}else if(!destinationBankId.match(/[A-Z|a-z]{4}[a-zA-Z0-9]{7}$/)){
			$('#alertBox').show();
			$('#alertMessage').html("Please enter a valid IFSC code");
		}else if(transferFrequency == "Monthly" && calculateMonthsInBetween(startDate,endDate) < 1) {
			$('#alertBox').show();
			$('#alertMessage').html("There Should be atleast one installment in between Start-Date and End-Date.");
		}else if(transferFrequency == "Weekly" && calculateWeeksInBetween(startDate,endDate) < 1) {
			$('#alertBox').show();
			$('#alertMessage').html("There Should be atleast one installment in between Start-Date and End-Date.");
		}else if(!(parseInt(maximum_amount) <= maxAmtLimit)) {
			$('#alertBox').show();
			$('#alertMessage').html("Please enter a transfer amount less than 10,00,000");
		}else if(!$("#tnc").is(':checked')){
			$('#alertBox').show();
			$('#alertMessage').html("Please select the Terms and Condition");
		}else{ 
            $(this).attr("disabled", "disabled");
			var digio = new Digio(options);
            var temkey = sessionID.replace(/-/g,"");
            temkey=makeid();

            var encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);

         
             digio.init();
			$.ajax({
				url: "/bin/digioCreateMandate", 
				type: "POST",
				//data: formData,
				headers: {"X-AUTH-TOKEN" : encryptFormData,
                		"X-AUTH-SESSION" : temkey},
				success: function (res) {
					var response = JSON.parse(res);
					console.log(res);
					interactID=response.interactionId;
					if(response.id) {
						//digio.esign(response.id,mobileNo);
							digio.submit(response.id,mobileNo);
					} else {
						digio.cancel();
					}
				},
				error: function (e) {
					console.log("Error in Digio Create Mandate Ajax :: " + e);
				}
			});
		}		
	});

	$(".alertBtn").click(function () {
		$("#alertBox").hide();
	});

	function calcTotalAmountAndInvestments() {
		var maximum_amount = parseFloat($('input[name=maximum_amount]').val());
		var transferFrequency = $('.transferFrequency').val();
		var startDate = $('input[name=startDate]').val();
		var endDate = $('input[name=endDate]').val();		
		if(transferFrequency == "Monthly"){
			var noOfMonths = calculateMonthsInBetween(startDate,endDate);
			$('#totalAmount').val(maximum_amount * noOfMonths);
			$('#totalInstallments').val(noOfMonths);
		}else if(transferFrequency == "Weekly"){
			var noOfWeeks = calculateWeeksInBetween(startDate,endDate);
			$('#totalAmount').val(maximum_amount * noOfWeeks);
			$('#totalInstallments').val(noOfWeeks);
		}else if(transferFrequency == "Yearly"){
			var noOfYears = calculateYearsInBetween(startDate,endDate);
			$('#totalAmount').val(maximum_amount * noOfYears);
			$('#totalInstallments').val(noOfYears);
		}else if(transferFrequency == "Adhoc"){
			var noOfAdhoc = 1;
			$('#totalAmount').val(maximum_amount * noOfAdhoc);
			$('#totalInstallments').val(noOfAdhoc);
		}
	}

	function calculateMonthsInBetween(startDate,endDate) {
		from = moment(startDate, 'YYYY-MM-DD'); 
		to = moment(endDate, 'YYYY-MM-DD');     
		duration = to.diff(from, 'months');
		return duration;
	}

	function calculateWeeksInBetween(startDate,endDate) {
		from = moment(startDate, 'YYYY-MM-DD'); 
		to = moment(endDate, 'YYYY-MM-DD');     
		duration = to.diff(from, 'weeks');
		return duration;
	}

    function calculateYearsInBetween(startDate,endDate) {
		from = moment(startDate, 'YYYY-MM-DD'); 
		to = moment(endDate, 'YYYY-MM-DD');     
		duration = to.diff(from, 'years');
		return duration;
	}

    function caluclateEndDate(startDate){

       // from = moment(startDate, 'YYYY-MM-DD');
        enddate=moment(startDate, "YYYY-MM-DD").add(10, 'years'); 
        enddate=moment(enddate, "YYYY-MM-DD").subtract(1, 'day');
		enddate = moment(enddate, 'YYYY-MM-DD');   
		
    }





});





