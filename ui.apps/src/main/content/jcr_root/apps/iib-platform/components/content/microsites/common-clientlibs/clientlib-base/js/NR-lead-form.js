$(document).ready(function() {
    resetForm();
    
    var dropdown = $('#Countryofresidence');
    
    
    //dropdown.empty();
    dropdown.append('<option selected="true" value="">Country of residence*</option>');
    dropdown.prop('selectedIndex', 0);
    var url = '/etc/scripts/nr-lead-refer-form/js/countries.json';
    // Populate dropdown with list of provinces
    $.getJSON(url, function (data) {
      $.each(data, function (key, entry) {
        dropdown.append($('<option></option>').attr('value', entry.name).text(entry.name).attr('title',entry.dial_code));
      })
      
      })
      customSelect();
      
     countryChange();
});
function countryChange() {
	var ISDNumberMobile = $('#ISDNumberMobile');
		var ISDNumberLandline = $('#ISDNumberLandline');
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
}
function resetForm() {
	$('input[type=text]').val('');
	$("select").val($("option:first").val());
	$("#acceptFlag").prop("checked", false);
}
function nrLeadFormData() {
		$('#loader, .overlay1').show();
    	$('#submitBtn').hide();
		var LandlineNo;
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
		var preferredTime = $('#preferredTime').val();
    	var ip=userip ;

	$.ajax({
		type : "POST",
		url : "/bin/nrleadform/posteddata",
		data : {
			titleJs : title,
			firstNameJs : firstName,
			lastNameJs : lastName,
            CountryofresidenceJs:Countryofresidence,
            ISDNumberJs:ISDNumber,
			LandlineNoJs:LandlineNo,
            emailidJs:emailid,
			preferredTimeJs:preferredTime,
            ipaddressJs:ip
		},
		success : function(msg) { 
			if(msg=='RecordInserted'){
				$('#loader, .defaultloader').hide();
				$('.descText').text('We shall be contacting you at your preferred timings within next 48 hours (Sundays excluded)');
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