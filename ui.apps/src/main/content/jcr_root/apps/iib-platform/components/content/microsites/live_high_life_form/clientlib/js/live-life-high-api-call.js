$(document).ready(function() {

    $("#live-life-high-submit").click(function(event) {

        event.preventDefault(); //prevent default action
        var form_data = $('#quoteForm').serialize(); //Encode form elements for submission
        var action_type = "live_life_high_submit";
        var key = makeid();
        var iv = makeid();
        convertedData(form_data, actionType, key, iv);
    });

    $("#live-life-high-generate-otp").click(function(event) {

        var result = validate();
        var error = $('.error').is(':visible')
        if (result == true && error == false) {
            event.preventDefault(); //prevent default action
            var mobileNumber = $('#mobile').val();
            var common_data = {
                mobileNumber: mobileNumber
            };
            var action_type = "live_life_high_form_generate_otp";
            var key = makeid();
            var iv = makeid();
            convertedData(common_data, action_type, key, iv);
        }
    });

    $(".resend").click(function(event) {

        var result = validate();
        if (result == true) {
            event.preventDefault(); //prevent default action
            var mobileNumber = $('#mobile').val();
            var common_data = {
                mobileNumber: mobileNumber
            };
            var action_type = "live_life_high_form_generate_otp";
            var key = makeid();
            var iv = makeid();
            convertedData(common_data, action_type, key, iv);
        }
    });




  //  $(".validate").on('click', function() {

        $("#live-life-high-validate-otp").click(function(event){
            debugger;
        var fname = $('#first-name').val();
        var mname = $('#middle-name').val();
        var lname = $('#last-name').val();
        var dob = $('#datepicker').val();
        var mobile = $('#mobile').val();
        var email = $('#email').val();
        var pan = $('#pan').val();
        var address1 = $('#address1').val();
        var address2 = $('#address2').val();
        var pincode = $('#pin-code').val();
        var state = $('#state-dropdown').val();
        var statetext=$("#state-dropdown option:selected").text();
        state = state + " - "+statetext;   
        var city = $('#city-dropdown').val();
        var citytext=$("#city-dropdown option:selected").text();
        city = city + " - "+citytext; 
        var emptype = $('#employment-type').val();
        var companyName = $('#company-name').val();
        var monthlyIncome = $('#monthly-income').val();
        var otp = $('#otp').val();
        event.preventDefault(); //prevent default action
        var common_data = {
            fname: fname,
            mname: mname,
            lname: lname,
            dob: dob,
            mobile: mobile,
            email: email,
            pan: pan,
            address1: address1,
            address2: address2,
            pincode: pincode,
            state: state,
            city: city,
            emptype: emptype,
            companyName: companyName,
            otp: otp,
            monthlyIncome: monthlyIncome


        };
        var action_type = "live_life_high_form_verify_otp";
        var key = makeid();
        var iv = makeid();
        convertedData(common_data, action_type, key, iv);
    });


});




function convertedData(commonData, actionType, key, iv) {
    var convertedData = encryptMessage(JSON.stringify(commonData), CryptoJS.enc.Utf8.parse(key), CryptoJS.enc.Utf8.parse(iv));
    convertedData = convertedData.toString();
    convertedData = convertedData.split("+").join(":");
    var finalData = "action=" + actionType + "&v1=" + convertedData;
    ajaxCall(finalData, key, iv);

}

function ajaxCall(commonData, key, iv) {


    $.ajax({
        url: "/bin/microsites/apiCall",
        type: "POST",
        data: commonData,
        headers: {
            "X-AUTH-TOKEN": key,
            "X-AUTH-SESSION": iv
        },
        success: function(res) {

            var response = JSON.parse(res);
            if (response.SUCCESS) {

                var msg = response.SUCCESS;
                if (msg == 'SENT_OTP_SUCCESSFUL') {
                    $('.pop-up, .overlay').show();
                    $('.pop-up .thank-wrp').hide();
                }

                if (msg == 'OTP_VERIFIED_SUCCESSFULLY') {
                    $('.otpBox').hide();
                    $('.pop-up .cancel').addClass('ClosePopup');
                    $('.pop-up .thank-wrp').show();
                    $('.pop-up .wrapper').hide();
                }




            }
            if (response.ERROR) {
                var msg = response.ERROR;
                if (msg == 'OTP_VERIFIED_NOT_SUCCESSFULLY') {
                    $('.errorotpNum').show();
                    $('.errorotpNum').text("Please enter valid otp")
                }

            }
        },

        error: function(err) {
            console.log(err);
        },
    });


}




function makeid() {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for (var i = 0; i < 16; i++)
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
}

function encryptMessage(message, key, iv) {
    return CryptoJS.AES.encrypt(message, key, {
        iv: iv,
        padding: CryptoJS.pad.Pkcs7,
        mode: CryptoJS.mode.CBC
    });
}


function validate() {

    var test = true;
    $(".formDiv input").each(function() {
        if (($.trim($(this).val()).length == 0)) {

            if ($(this).attr('name') != "middle-name") {
                $(this).parent().find('.error').show();

                test = false;
            }
        } else {
            $(this).parent().find('.error').hide();

        }
    });

    $(".formDiv select").each(function() {
        if ($.trim($(this).val()).length == 0) {
            $(this).parent().find('.error').show();

            test = false;
        } else {
            $(this).parent().find('.error').hide();

        }
    });
    return test;
}
