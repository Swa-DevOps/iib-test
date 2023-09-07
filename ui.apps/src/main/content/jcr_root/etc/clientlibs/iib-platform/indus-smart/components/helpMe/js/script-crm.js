
$(document).ready(function() {

	$(document).on('click', '#acceptBtn', function(e){
		$('#exampleCheck1').prop("checked", true);
	});

    $('#InputName').focusout(function(){
        if( $('#InputName').val()!="" || $('#InputMobile').val()!="" || $('#exampleInputEmail1').val()!="" ) {

       		 $('#heplmesubmit').removeClass("disable")
        }
        else if(($('#InputName').val())=="" && ($('#InputMobile').val())=="" && ($('#exampleInputEmail1').val())=="" ){
            $('#heplmesubmit').addClass("disable")
        }
    });

    $('#InputMobile').focusout(function(){
        if(($('#InputName').val())!="" || ($('#InputMobile').val())!="" || ($('#exampleInputEmail1').val())!="" ) {

       		 $('#heplmesubmit').removeClass("disable")
        }
        else if(($('#InputName').val())=="" && ($('#InputMobile').val())=="" && ($('#exampleInputEmail1').val())=="" ){
            $('#heplmesubmit').addClass("disable")
        }
    });

    $('#exampleInputEmail1').focusout(function(){
        if(($('#InputName').val())!="" || ($('#InputMobile').val())!="" || ($('#exampleInputEmail1').val())!="" ) {

       		 $('#heplmesubmit').removeClass("disable")
        }
        else if(($('#InputName').val())=="" && ($('#InputMobile').val())=="" && ($('#exampleInputEmail1').val())=="" ){
            $('#heplmesubmit').addClass("disable")
        }
    });


    $(document).on('click', '#declineBtn', function(e){
        $('.termsConditionsText').html("Please enter your Email-ID");
		$('#exampleCheck1').prop("checked", false);
	});

    //Numeric Validation
    $('#numberDiv').on('paste',function (e) {
		e.preventDefault();
	});
    
    $("#numberDiv").keydown(function (e) {
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110]) !== -1 ||
            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) || 
            (e.keyCode >= 35 && e.keyCode <= 40)) {
                 return;
        }
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });

    //Character Validation
    $('.username').on('paste',function (e) {
		e.preventDefault();
	});
    
    $('.username').keypress(function (e) {
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

	$(document).on('click', '.helpMeBtn', function(e) {
	
		var customerName = $('.username').val();
		var mobileNumber = $('.mobileNumber').val();
		var emailId = $('.emailId').val();
		var mobileRegex = /(6|7|8|9)\d{9}$/;
		var emailRegex = /^([0-9a-zA-Z]([-_\\.]*[0-9a-zA-Z]+)*)@([0-9a-zA-Z]([-_\\.]*[0-9a-zA-Z]+)*)[\\.]([a-zA-Z]{2,9})$/;
		var termsAndConditionCheckBox = $('.termsConditions').is(':checked');
		
		if(customerName == '') {
			$("#termsConditions").modal();
			$('.termsConditionsText').html("Please enter your Name");
		} 
		else if(mobileNumber == '' || !mobileRegex.test(mobileNumber)) {
			$("#termsConditions").modal();
			$('.termsConditionsText').html("Please enter your Mobile Number");
		}
		else if(emailId == '' || !emailRegex.test(emailId)) {
			$("#termsConditions").modal();
			$('.termsConditionsText').html("Please enter your Email-ID");
		}
		else if(termsAndConditionCheckBox == false) {
			$("#termsConditions").modal();
			$('.termsConditionsText').html("You must agree with the Terms and Conditions");
		}
		else {
			$('.se-pre-con').css('display','block');
			helpMeAPICall(customerName, mobileNumber, emailId);
		}
	});
});



/*function helpMeAPICall(customerName, mobileNumber, emailId) {
	
	var successPageUrl = $('.pageUrlDiv').attr('data-successUrl');
	var data = {
		customerName : customerName,
		mobileNumber : mobileNumber,
		emailId : emailId
	}
	// Help Me Button Ajax CALL
	$.ajax({
		url: "/bin/helpMeApi",
		data: data,
		type: 'POST',
		success: function(res) {
			$('.se-pre-con').css('display','none');
			var output = JSON.parse(res);
			var interactionId = output.interactionId;
			if(output.status == "Success") {
				window.location.href = successPageUrl + "?interactionId="+interactionId;
			}
			else {
				console.log("Request Time out");
			}
		},
		error: function(err) {
			console.log("Error in Help Me API" + err);
		}
	});*/

function helpMeAPICall(customerName, mobileNumber, emailId) {


	var successPageUrl = $('.pageUrlDiv').attr('data-successUrl');
	var data = {

       // customerName : customerName,
	//	mobileNumber : mobileNumber,
	//	emailId : emailId

       // LeadSourceID:"Internal Website",
       // ISD__Code_Lead:"91",
       // Team_Name_Lead:"Banking Contact Centre - OutBound",
        ////Existing_Customer_Lead:"No",
       // LayoutID:"111213",
       // Lastname:"0",
       // ProductID:"Mutual Fund",
        //RatingID:"Cold",
        //StatusCodeID:"100003",
        Customer_Name_Lead :customerName,
        MobilePhone:mobileNumber,
        Email:emailId

	}
	// Help Me Button Ajax CALL
	/*$.ajax({
		url: "/bin/helpMeApi",
		data: data,
		type: 'POST',
		success: function(res) {
			$('.se-pre-con').css('display','none');
			var output = JSON.parse(res);
			var interactionId = output.interactionId;
			if(output.status == "Success") {
				window.location.href = successPageUrl + "?interactionId="+interactionId;
			}
			else {
				console.log("Request Time out");
			}
		},
		error: function(err) {
			console.log("Error in Help Me API" + err);
		}
	});*/


	var url = '/bin/crmnext/crmallservices';

			 $.ajax({
              type: 'POST',
              url: url,
              data :"action=indusSaveObject&data="+JSON.stringify(data)+"&data1=indusSaveObject",
             headers: {"X-AUTH-SESSION" : "",
        			   "X-AUTH-TOKEN" : ""},

              success: function( resultData ) {
                  let res = JSON.parse(resultData);

                if (( res.responseMSG === "Item has been created successfully." ) || (res.responseMSG === "Item has been updated successfully." )){
                    //$("#message-success").css("display", "block");
                    //$("#res_number").html(resultData.id);
                    window.location.href = successPageUrl + "?interactionId="+res.responseID;
					//window.location.href= "/content/ipb/en/success.html?interactionId=" + res.responseID;
                   // $("#message-error").css("display", "none");
                } else {

                    console.log("Request Time out");
                    $('.se-pre-con').css('display','none');
                    /*$("#message-success").css("display", "none");
                    $("#res_number").html("");
                    $("#message-error").html(res.responseMSG);
                    $("#message-error").css("display", "block");*/
                }
              }
        });

}