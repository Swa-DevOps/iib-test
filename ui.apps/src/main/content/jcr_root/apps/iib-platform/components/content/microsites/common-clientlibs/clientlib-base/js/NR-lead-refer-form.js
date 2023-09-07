$(document).ready(function() {
		resetForm();
		
		var dropdown = $('#Countryofresidence');
		var ISDNumberMobile = $('#ISDNumberMobile');
		var ISDNumberLandline = $('#ISDNumberLandline');
		
		dropdown.append('<option selected="true" value="">Country of residence*</option>');
		dropdown.prop('selectedIndex', 0);
		var url = '/etc/scripts/nr-lead-refer-form/js/countries.json';

		$.getJSON(url, function (data) {
		  $.each(data, function (key, entry) {
			dropdown.append($('<option></option>').attr('value', entry.name).text(entry.name).attr('title',entry.dial_code));
		  })
		  
		  })
		  customSelect();
		  
		  $("#Countryofresidence").on('change', function() {
			  if($(this).val() != 'Others') {
			  var optionSelected = $('option:selected', this).attr('title');
			   ISDNumberMobile.parents('.fieldBox').addClass('isValid');
			   ISDNumberMobile.prev('.placeholder').addClass('active');
			   ISDNumberMobile.parents('.fieldBox').removeClass('error-field');
			   ISDNumberMobile.val(optionSelected);
			   ISDNumberMobile.prop('readonly', true);
			   
			   ISDNumberLandline.parents('.fieldBox').addClass('isValid');
			   ISDNumberLandline.prev('.placeholder').addClass('active');
			   ISDNumberLandline.parents('.fieldBox').removeClass('error-field');
			   ISDNumberLandline.val(optionSelected);
			   ISDNumberLandline.prop('readonly', true);
			   }
			   else {
			   	   var optionSelected = $('option:selected', this).attr('title');
				   ISDNumberMobile.parents('.fieldBox').removeClass('isValid');
				   ISDNumberMobile.prev('.placeholder').addClass('active');
				   ISDNumberMobile.val('+');
				   ISDNumberMobile.prop('readonly', false);
				   
				   ISDNumberLandline.parents('.fieldBox').removeClass('isValid');
				   ISDNumberLandline.prev('.placeholder').addClass('active');
				   ISDNumberLandline.val('+');
				   ISDNumberLandline.prop('readonly', false);
			   }
		 });
});


function resetForm() {
	$('input[type=text]').val('');
	$("select").val($("option:first").val());
    $("#acceptFlag").prop("checked", false);
}


function nrLeadReferFormData() {
		$('#loader, .overlay1').show();
    	$('#submitBtn').hide();

    	var title = $('#title').val();
		var firstName = $('#firstName').val();
		var lastName = $('#lastName').val();
		var Countryofresidence = $('#Countryofresidence').val();
		var ISDNumberMobile = $('#ISDNumberMobile').val();
		var mobileno = $('#MobileNumber').val();
		var ISDNumber = ISDNumberMobile + mobileno;
		var ISDNumberLandline = $('#ISDNumberLandline').val();
		var STDNumber = $('#STDNumber').val();
		var LandlineNumber = $('#LandlineNumber').val();
		if (LandlineNumber!= '') {
			LandlineNo = ISDNumberLandline + STDNumber + LandlineNumber;
		}
		else {
			LandlineNo = '';
		}
    	var emailid = $('#Email').val();
		var accountNo = $('#accAmount').val();
    	var ip=userip;

	$.ajax({
		type : "POST",
		url : "/bin/nrleadreferform/posteddata",
		data : {
			titleJs : title,
			firstNameJs : firstName,
			lastNameJs : lastName,
            CountryofresidenceJs:Countryofresidence,
            ISDNumberJs:ISDNumber,
			LandlineNoJs:LandlineNo,
            emailidJs:emailid,
			account:accountNo,
            ipaddressJs:ip
		},
		success : function(msg) { 

			var displayMsg=msg.split("$");
            //Rakesh comment msg only for test
			if(displayMsg[0]=='TalismaSuccess'){
				 $('#loader, .defaultloader').hide();
				$('.descText').text("Your form is submitted successfully. Your opportunity id is "+ displayMsg[1] + " for future reference.");
				$('#generalPopup').show();
				$('.close').addClass('ClosePopup');
			}		
			else{
				 $('#loader, .defaultloader').hide();
				$('.descText').text('Dear Customer, we are facing a technical issue at our end. We request you to kindly try again after sometime');
				$('.close').removeClass('ClosePopup');
				$('#generalPopup').show();
			}
            
		},

		error : function(xhr) {
			 $('#loader, .defaultloader').hide();
			 $('.descText').text('Dear Customer, we are facing a technical issue at our end. We request you to kindly try again after sometime');
			 $('.close').removeClass('ClosePopup');
           $('#generalPopup').show();
		}
	});
}
