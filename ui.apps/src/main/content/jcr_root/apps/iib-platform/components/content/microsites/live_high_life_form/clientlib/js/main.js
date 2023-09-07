$(document).ready(function() {

    $('input[type=text],input[type=tel],input[type=email]').val('');

    $('input.letters-only').on('input', function() {
        letters($(this));
    });

    $('input[type="email"]').on('input', function() {
        email($(this));
    });

   $('input[name="pan"]').on('input', function() {
        panVal($(this));
    });

    $('input[name="address1"]').on('input', function() {
       var inputVal = $(this).val();

      if(inputVal.length < 0) {
         $(this).parent().find('.error').show();
     } else {
         $(this).parent().find('.error').hide();
     }
    })
	$('input[name="address2"]').on('input', function() {
       var inputVal = $(this).val();

      if(inputVal.length < 0) {
         $(this).parent().find('.error').show();
     } else {
         $(this).parent().find('.error').hide();
     }
    });


    $(document).on('change','select', function() {
        // var inputVal = $(this).val();
        if($(this).val() == '') {
            $(this).parent().find('.error').show();
        } else {
            $(this).parent().find('.error').hide();
        }
    });


    $('input.num-only').on({
        keypress: function(e) {
            if (e.which != 8 && e.which != 0 && (e.which < 48 || e.which > 57)) {
                return false;
             }
        }
    });

    $('input[name="mobile"]').on('input', function() {
        lengthCheck($(this), 10);
    });

	$('input[name="pin-code"], input[name="otp"]').on('input', function() {
        lengthCheck($(this), 6);
    });


    $('.pop-up .cancel').on('click', function() {
        if ($('.cancel').hasClass('ClosePopup')) {
           window.location.reload()
        }
        else {
			 $('.pop-up, .overlay').fadeOut();
        }
    });


    // Submit Button Starts
    $('.submit').on('click', function() {
        var empty = true;
        $('.formDiv .input-wrp').each(function() {
            var inputVal = $(this).find('input').not('input[type="hidden"]').val();
            var inputError = $(this).find('.error');
            var inputReq = $(this).find('input[required], select[required]');
          

           if($('.input-wrp').find('.error').is(':visible') || inputReq.val() == '') {
               $('.btn-wrp .error').show();
             
               empty = false;
               return false; 
           } else {
               
               $('.btn-wrp .error').hide();
           }
 });
 

    });
    // Submit Button Ends




    $('.input-wrp input, select').each(function() {
        var placeholderVal = $(this).attr('placeholder');
        var errorName = 'Please Enter ' + placeholderVal;

        $(this).parent().find('.error').text(errorName);
    });


    //DOB Starts
    $("#dob").birthdayPicker({
        monthFormat: "number",
        sizeClass: 'span3'
    });
   //DOB Ends





    var dropdown = $('#state-dropdown');
        dropdown.empty();
        dropdown.append('<option value="">STATE*</option>');
        dropdown.prop('selectedIndex', 0);
        $.each(myData, function(key, entry) {
            if(entry.parent_id == '0') {
                dropdown.append($('<option></option>').attr('value', entry.id).text(entry.name));
            }
    });

    $(document).on('change', '#state-dropdown', function(){
        var state_id = $(this).val();
        var cdropdown = $('#city-dropdown');
        cdropdown.empty();
        cdropdown.append('<option value="">CITY*</option>');
        $.each(myData, function(key, entry) {
            if(entry.parent_id == state_id) {
                cdropdown.append($('<option></option>').attr('value', entry.id).text(entry.name));
                $('#teamMember').val(entry.teamMember);
            }
        });
    });

    $(document).on('change', '#city-dropdown', function(){
        var cityName = $('#city-dropdown').find(":selected").text();
        $.each(myData, function(key, entry) {
        if(cityName == entry.name) {
            $('#teamMember').val(entry.teamMember);
        }
        });
    });

    $('select:first-child').css('color','#727573')


});



function letters(input) {
    var inputVal = input.val();
    var reg = /^[a-zA-Z0-9\-]+$/;

    if(!reg.test(inputVal) && inputVal.length > 0) {
        input.parent().find('.error').show();
    } else {
        input.parent().find('.error').hide();
    }
}


function email(input) {
    var inputVal = input.val();
    var reg = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	

    if(!reg.test(inputVal) && inputVal.length > 0) {
        input.parent().find('.error').show();
    } else {
        input.parent().find('.error').hide();
    }
}


function panVal(input) {
    var inputVal = input.val();
    var reg = /[a-zA-z]{5}\d{4}[a-zA-Z]{1}/;

    if(!reg.test(inputVal) && inputVal.length > 0) {
        input.parent().find('.error').show();
    } else {
        input.parent().find('.error').hide();
    }
}



function lengthCheck(input, n) {
    var inputVal = input.val();

    if(!(inputVal.length == n) && inputVal.length > 0) {
        input.parent().find('.error').show();
    } else {
        input.parent().find('.error').hide();
    }
}




// Json

var myData =[
    {
       "id":"1",
       "name":"Maharashtra",
       "parent_id":"0",
       "teamMember":"Atul"
    },
    {
       "id":"2",
       "name":"Mumbai",
       "parent_id":"1",
       "teamMember":"Bhuvan"
    },
	  {
       "id":"3",
       "name":"Pune",
       "parent_id":"1",
       "teamMember":"Bhuvan"
    },
    {
       "id":"4",
       "name":"Delhi",
       "parent_id":"0",
       "teamMember":"Bhuvan"
    },
    {
       "id":"5",
       "name":"Delhi",
       "parent_id":"4",
       "teamMember":"Bhuvan"
    },
    {
       "id":"6",
       "name":"Punjab",
       "parent_id":"0",
       "teamMember":"Bhuvan"
    },
    {
       "id":"7",
       "name":"Chandigarh",
       "parent_id":"6",
       "teamMember":"Bhuvan"
    },
    {
       "id":"8",
       "name":"Rajasthan",
       "parent_id":"0",
       "teamMember":"Bhuvan"
    },
    {
       "id":"9",
       "name":"jaipur",
       "parent_id":"8",
       "teamMember":"Bhuvan"
    },
    {
       "id":"10",
       "name":"West Bengal",
       "parent_id":"0",
       "teamMember":"Bhuvan"
    },
    {
       "id":"11",
       "name":"Kolkata",
       "parent_id":"10",
       "teamMember":"Bhuvan"
    },
    {
       "id":"12",
       "name":"Telangana",
       "parent_id":"0",
       "teamMember":"Bhuvan"
    },
    {
       "id":"13",
       "name":"Hyderabad",
       "parent_id":"12",
       "teamMember":"Bhuvan"
    },
    {
       "id":"14",
       "name":"Karnataka",
       "parent_id":"0",
       "teamMember":"Bhuvan"
    },
	{
       "id":"15",
       "name":"Begaluru",
       "parent_id":"14",
       "teamMember":"Bhuvan"
    },
    {
       "id":"16",
       "name":"Tamilnadu",
       "parent_id":"0",
       "teamMember":"Bhuvan"
    },
    {
       "id":"17",
       "name":"Chennai",
       "parent_id":"16",
       "teamMember":"Bhuvan"
    }
 ]