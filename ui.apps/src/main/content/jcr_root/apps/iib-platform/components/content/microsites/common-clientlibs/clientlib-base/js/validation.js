var invalidCounter = 0;

function checkUID(aadharNumer) {
    var Verhoeff = {
        "d": [
            [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
            [1, 2, 3, 4, 0, 6, 7, 8, 9, 5],
            [2, 3, 4, 0, 1, 7, 8, 9, 5, 6],
            [3, 4, 0, 1, 2, 8, 9, 5, 6, 7],
            [4, 0, 1, 2, 3, 9, 5, 6, 7, 8],
            [5, 9, 8, 7, 6, 0, 4, 3, 2, 1],
            [6, 5, 9, 8, 7, 1, 0, 4, 3, 2],
            [7, 6, 5, 9, 8, 2, 1, 0, 4, 3],
            [8, 7, 6, 5, 9, 3, 2, 1, 0, 4],
            [9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
        ],
        "p": [
            [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
            [1, 5, 7, 6, 2, 8, 3, 0, 9, 4],
            [5, 8, 0, 3, 7, 9, 6, 1, 4, 2],
            [8, 9, 1, 6, 0, 4, 3, 5, 2, 7],
            [9, 4, 5, 3, 1, 2, 6, 8, 7, 0],
            [4, 2, 8, 6, 5, 7, 3, 9, 0, 1],
            [2, 7, 9, 3, 8, 0, 6, 4, 1, 5],
            [7, 0, 4, 6, 9, 1, 3, 2, 5, 8]
        ],
        "j": [0, 4, 3, 2, 1, 5, 6, 7, 8, 9],
        "check": function(str) {
            var c = 0;
            str.replace(/\D+/g, "").split("").reverse().join("").replace(/[\d]/g, function(u, i) {
                c = Verhoeff.d[c][Verhoeff.p[i % 8][parseInt(u, 10)]];
            });
            return c;
        },
        "get": function(str) {
            var c = 0;
            str.replace(/\D+/g, "").split("").reverse().join("").replace(/[\d]/g, function(u, i) {
                c = Verhoeff.d[c][Verhoeff.p[(i + 1) % 8][parseInt(u, 10)]];
            });
            return Verhoeff.j[c];
        }
    };
    String.prototype.verhoeffCheck = (function() {
        var d = [
            [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
            [1, 2, 3, 4, 0, 6, 7, 8, 9, 5],
            [2, 3, 4, 0, 1, 7, 8, 9, 5, 6],
            [3, 4, 0, 1, 2, 8, 9, 5, 6, 7],
            [4, 0, 1, 2, 3, 9, 5, 6, 7, 8],
            [5, 9, 8, 7, 6, 0, 4, 3, 2, 1],
            [6, 5, 9, 8, 7, 1, 0, 4, 3, 2],
            [7, 6, 5, 9, 8, 2, 1, 0, 4, 3],
            [8, 7, 6, 5, 9, 3, 2, 1, 0, 4],
            [9, 8, 7, 6, 5, 4, 3, 2, 1, 0]
        ];
        var p = [
            [0, 1, 2, 3, 4, 5, 6, 7, 8, 9],
            [1, 5, 7, 6, 2, 8, 3, 0, 9, 4],
            [5, 8, 0, 3, 7, 9, 6, 1, 4, 2],
            [8, 9, 1, 6, 0, 4, 3, 5, 2, 7],
            [9, 4, 5, 3, 1, 2, 6, 8, 7, 0],
            [4, 2, 8, 6, 5, 7, 3, 9, 0, 1],
            [2, 7, 9, 3, 8, 0, 6, 4, 1, 5],
            [7, 0, 4, 6, 9, 1, 3, 2, 5, 8]
        ];
        return function() {
            var c = 0;
            this.replace(/\D+/g, "").split("").reverse().join("").replace(/[\d]/g, function(u, i) {
                c = d[c][p[i % 8][parseInt(u, 10)]];
            });
            return (c === 0);
        };
    })();
    if (Verhoeff['check'](aadharNumer) === 0) {
        return true;
    } else {
        return false;
    }
}
var date1;
var month;
var year;
var EnteredDate;
var daysInMonth = function(m, y) {
    switch (m) {
        case 1:
            return (y % 4 == 0 && y % 100) || y % 400 == 0 ? 29 : 28;
        case 8:
        case 3:
        case 5:
        case 10:
            return 30;
        default:
            return 31
    }
};
var isValidDate = function(d, m, y) {
    //console.log("d--"+ d+ " m---"+m+ " y--"+y);
    if (parseInt(d) && parseInt(m) && parseInt(y) && y < 1900) return false;
    if (d.indexOf("/") > -1 || m.indexOf("/") > -1 || y.indexOf("/") > -1) return false;
    m = parseInt(m, 10) - 1;
    return m >= 0 && m < 12 && d > 0 && d <= daysInMonth(m, y);
};
var DOB = function() {
    var EnteredDate = document.getElementById("DateOfBirth").value;
    var date1 = EnteredDate.substring(0, 2);
    var month = EnteredDate.substring(3, 5);
    var year = EnteredDate.substring(6, 10);
    var myDate = new Date(year, month - 1, date1);
    var today = new Date();
    // console.log(myDate, today, myDate >= today)
    if (myDate >= today) {
        return true;
    } else {
        return false;
    }
}

function validateMe(_this) {
    //console.log($('#aadharNumber').val(),checkUID($('#aadharNumber').val()))
    $(_this).closest(".fieldBox").removeClass("isValid");
    var thisField = $(_this).attr("name");
    var validationInfo = $(_this).data("validation");
    var validationType = [];
    if ($(_this).is(":disabled")) {
        return false;
    }
    if (validationInfo.indexOf(",") != -1) {
        validationType = validationInfo.split(",");
    } else {
        validationType.push(validationInfo);
    }
    //var uid='307745258984'
    var validAlpha = /^[a-zA-Z]*$/;
    var validAlphaSpecial = /^[a-zA-Z'. ]*$/;
    var validNumeric = /^[0-9]+$/;
    var validEmail = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,15})$/;
    // var validDate =  /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/;
    var validDate = /^((((0[1-9]|1[0-9]|2[0-8])\/(02))|((0[1-9]|[12][0-9]|30)\/(04|06|09|11))|((0[1-9]|[12][0-9]|3[01])\/(01|03|05|07|08|10|12)))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3}))|((29\/02)\/(([0-9]{2}([02468][48]|[13579][26]|[2468]0))|(([02468][48]|[13579][26]|[2468]0)00)))$/
    var validAlphaNum = /^[a-zA-Z0-9 ]*$/;
    var validMobile = /^([9][1]|[0]){0,1}([7-9]{1})([0-9]{9})$/
    var validPan = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/
    var validCIF = /^([a-zA-Z]{3})(\d{5})([a-zA-Z]{1})$/
    var validLength = /^\d{12}(?:\d{4})?$/
    //GET Value
    if ($(_this).is(":checkbox")) {
        var inputVal = $(_this).is(":checked") ? true : false;
    } else if (_this.nodeName == "SELECT") {
        if ($(_this).find('option:selected').index() == 0) {
            var inputVal = $(_this).val();
        }
    } else {
        var inputVal = $(_this).val();
    }
    for (var i = 0; i < validationType.length; i++) {
        if (validationType[i] == "required" && (inputVal == "" || inputVal == false || inputVal == "-1")) { // for normal select dropdown 
            if ($(_this).closest(".checkBox").length && inputVal == false) {
                $(_this).closest(".checkBox").addClass("error-field");
            } else if ($(_this).closest(".selectBox").length && inputVal == "-1") { // for normal select dropdown 
                $(_this).closest(".fieldBox").addClass("error-field");
            } else {
                $(_this).closest(".fieldBox").addClass("error-field");
                if ($(this).siblings(".rs").length) {
                    $(this).siblings(".rs").addClass("error-text");
                }
            }
            if ($(_this).hasClass('aadhaarIncr') || $(_this).hasClass('DOBInc') || $(_this).hasClass('chkType')) {
                $(_this).siblings(".error").text($(_this).attr('rel') + " is required");
            } else {
                $(_this).siblings(".error").text(thisField + " is required");
            }
            if ($(_this).closest(".selectBox").length == 0) {
                $(_this).val("");
            }
            invalidCounter++;
            return false;
        }
        if (inputVal != "" && validationType[i].indexOf("minlength") != -1) {
            var splitMe = validationType[i].split("minlength");
            if (inputVal.length < parseInt(splitMe[1])) {
                $(_this).closest(".fieldBox").addClass("error-field");
                $(_this).siblings(".error").text($(_this).attr("name") + " should not be lesser than " + splitMe[1] + " characters");
                invalidCounter++;
                return false;
            }
        }
        if (inputVal != "" && validationType[i].indexOf("maxlength") != -1) {
            var maxLen = parseInt($(_this).attr("maxlength"));
            var typeOfField = validationType.indexOf("numbersOnly") != -1 ? "digits" : "letters";
            if (inputVal.length != maxLen) {
                $(_this).closest(".fieldBox").addClass("error-field");
                $(_this).siblings(".error").text(thisField + " should be exactly of " + maxLen + " " + typeOfField);
                invalidCounter++;
                return false;
            }
        }
        if (inputVal != "" && validationType[i] == "alphaSpecialVal" && validAlphaSpecial.test(inputVal) == false) {
            $(_this).closest(".fieldBox").addClass("error-field");
            $(_this).siblings(".error").text(thisField + " can only have alphabets");
            invalidCounter++;
            return false;
        }
        if (inputVal != "" && validationType[i] == "numbersOnly" && validNumeric.test(inputVal) == false && inputVal.indexOf(" ") == -1) {
            $(_this).closest(".fieldBox").addClass("error-field");
            if ($(_this).hasClass('aadhaarIncr') || $(_this).hasClass('YOBInc')) {
                $(_this).siblings(".error").text($(_this).attr('rel') + " can only have numbers");
            } else {
                $(_this).siblings(".error").text(thisField + " can only have numbers");
            }
            invalidCounter++;
            return false;
        }
        if (inputVal != "" && validationType[i] == "email" && validEmail.test(inputVal) == false) {
            $(_this).closest(".fieldBox").addClass("error-field");
            $(_this).siblings(".error").text("Please enter valid " + thisField);
            invalidCounter++;
            return false;
        }
        if (inputVal != "" && validationType[i] == "mobileVal" && validMobile.test(inputVal) == false) {
            $(_this).closest(".fieldBox").addClass("error-field");
            $(_this).siblings(".error").text("Please enter valid " + thisField);
            invalidCounter++;
            return false;
        }
        if (inputVal != "" && validDate.test(inputVal) == false && validationType[i] == "dateVal") {
            $(_this).closest(".fieldBox").addClass("error-field");
            $(_this).siblings(".error").text("Please enter valid " + thisField);
            invalidCounter++;
            return false;
        }
        if (inputVal != "" && validationType[i] == "date") {
            var boolval = DOB();
            if (boolval) {
                $(_this).closest(".fieldBox").addClass("error-field");
                $(_this).siblings(".error").text("Please enter valid " + thisField);
                invalidCounter++;
                return false;
            }
        }
        if (inputVal != "" && validationType[i] == "multiFieldVal") {
            if (inputVal.length == 9) {
                if (validCIF.test(inputVal) == false) {
                    $(_this).closest(".fieldBox").addClass("error-field");
                    $(_this).siblings(".error").text("Please enter correct Deal No.");
                    invalidCounter++;
                    return false;
                }
            } else if (inputVal.length == 12) {
                if (validNumeric.test(inputVal) == false && inputVal.indexOf(" ") == -1) {
                    $(_this).closest(".fieldBox").addClass("error-field");
                    $(_this).siblings(".error").text("Account Number can only have numbers");
                    invalidCounter++;
                    return false;
                }
            } else if (inputVal.length < 12 && inputVal.length < 9) {
                $(_this).closest(".fieldBox").addClass("error-field");
                $(_this).siblings(".error").text("Please enter correct " + thisField);
                invalidCounter++;
                return false;
            }
        }
        if (inputVal != "" && validationType[i] == "panVal" && validPan.test(inputVal) == false) {
            $(_this).closest(".fieldBox").addClass("error-field");
            $(_this).siblings(".error").text("Please enter correct " + thisField);
            invalidCounter++;
            return false;
        }
        if (inputVal != "" && validationType[i] == "dateMulti") {
            var EnteredDate = $(_this).val();
            var strDate = EnteredDate.toString()
            var date1 = strDate.substring(0, 2);
            var month = strDate.substring(3, 5);
            var year = strDate.substring(6, 10);
            var myDate = new Date(year, month - 1, date1);
            var today = new Date();
            // alert(3);
            var boolvalDOB = isValidDate(date1, month, year);
            console.log(boolvalDOB);
            if (boolvalDOB) {
                if (myDate > today) {
                    $(_this).closest(".fieldBox").addClass("error-field");
                    $(_this).siblings(".error").text("Please enter valid " + $(_this).attr('rel'));
                    invalidCounter++;
                    return false;
                }
            } else {
                $(_this).closest(".fieldBox").addClass("error-field");
                $(_this).siblings(".error").text("Please enter valid " + $(_this).attr('rel'));
                invalidCounter++;
                return false;
            }
            /*if(isValidDate(date1, month, year))
            	{
            		if (myDate > today) {
            				$(_this).closest(".fieldBox").addClass("error-field");
            				$(_this).siblings(".error").text("Please enter valid " + $(_this).attr('rel'));
            				
            				invalidCounter++; 
            				return false;
            			} 
            		}*/
        }
        if (inputVal != "" && validationType[i] == "yearBirth") {
            var yearOfBirth = parseInt($(_this).val());
            var d = new Date();
            var n = d.getFullYear();
            if (yearOfBirth >= n) {
                $(_this).closest(".fieldBox").addClass("error-field");
                $(_this).siblings(".error").text("Please enter valid Year");
                invalidCounter++;
                return false;
            }
        }
        if (inputVal != "" && validationType[i].indexOf("matchWith") != -1) {
            var toMatchFieldID = "#" + validationType[i].split("#")[1];
            if (inputVal != $(toMatchFieldID).val()) {
                $(_this).closest(".fieldBox").addClass("error-field");
                $(_this).siblings(".error").text(thisField + " should match with " + $(toMatchFieldID).attr("name"));
                invalidCounter++;
                return false;
            }
        }
        if (inputVal != "" && validationType[i] == "aadharVal" && !checkUID(inputVal) && inputVal.length == 12) {
            $(_this).closest(".fieldBox").addClass("error-field");
            $(_this).siblings(".error").text("Please enter a valid 12 digits Aadhaar number");
            invalidCounter++;
            return false;
        }
        if (inputVal != "" && validationType[i] == "lengthCount" && validLength.test(inputVal) == false) {
            // console.log(inputVal.length)
            $(_this).closest(".fieldBox").addClass("error-field");
            $(_this).siblings(".error").text("Please enter a valid Aadhaar number/VID number");
            invalidCounter++;
            return false;
        }
        if (inputVal != "" && validationType[i] == "checkEmpty") {
            $(".aadharmultiCheck input, .aadharmulti input").filter(function() {
                if ($.trim($(this).val()).length == 0) {
                    $('.plusSign').addClass("disabled");
                    return false;
                } else {
                    $('.plusSign').removeClass("disabled")
                }
            }).length == 0;
        }
        if (inputVal != "" && validationType[i] == "matchAadhaar") {
            var flag = false;
            var inputs = $('.aadhaarIncr');
            inputs.filter(function(i, el) {
                return inputs.not(this).filter(function() {
                    if (this.value === el.value) {
                        flag = true;
                    }
                    // return this.value === el.value;
                }).length !== 0;
            })
            if (flag == true) {
                // alert(1);
                $(_this).closest(".fieldBox").addClass("error-field");
                $(_this).siblings(".error").text("Please enter unique Aadhaar");
                invalidCounter++;
                return false;
            } else { //alert(2);
                flag = false;
            }
            $(".aadhaarIncr").each(function() {
                $(this).blur();
            });
            /*var map = [];
				$(".aadhaarIncr").each(function(){
                    map.push ($(this).val());
                });

				if ($.inArray(inputVal,map) > -1) {
                  alert("There's a match!");
               }*/
            //  invalidCounter++; 
            //return false;
        }
    }
    $(_this).closest(".error-field").removeClass("error-field");
    $(_this).closest(".fieldBox").addClass("isValid");
}
$(function() {
    $('input[type=radio][id=checkboxtwo]').on('change', function() {
        $('.condChk2 .erroranyone').hide();

    });

    $('#acceptFlag2').change(function() {
            if (!$(this).is(":checked")) {
         $(this).parent('.checkBox').find('.erroranyone').show();
     		} 
        else {
            $(this).parent('.checkBox').find('.erroranyone').hide();
        }     
    });





    /* $('#prefix').on('change', function() {
        if($(this).prev('.selectedValue').text()!='Select') {
			 $('.radioBox .erroranyone').hide();
        }

    });*/

    var validateTout;
    $("[data-validation]").each(function() {
        if ($(this).siblings(".error").length == 0) {
            if ($(this).attr("type") == "checkbox") {
                $('<div class="error"></div>').insertAfter($(this).next("label"));
            } else {
                $('<div class="error"></div>').insertAfter($(this));
            }
        }
    });
    $(document).on("keyup", "[data-validation]", function(e) {
        var code = e.keyCode || e.which;
        if (code != 9) {
            var _this = this;
            clearTimeout(validateTout);
            validateTout = setTimeout(function() {
                validateMe(_this);
            }, 1000);
        }
    });
    $(document).on("blur change", "[data-validation]", function(e) {
        var _this = this;
        clearTimeout(validateTout);
        validateTout = setTimeout(function() {
            validateMe(_this);
        }, 0);
    });
    $("form").submit(function(event) {
        var form = $(this);
        //if(form.find(":input[data-validation]") && (form.attr('name') != 'initFormDemo')){
        event.preventDefault();
        invalidCounter = 0;
        form.find(":input[data-validation]:visible").each(function() {
            validateMe(this);
        }).promise().done(function() {
            if (invalidCounter == 0) {
                submitForm(form.attr("id"));
            } else {
                invalidCounter = 0;
            }
        });
        // }
    });
});

function submitForm(formId) {
    if (formId == "UploadForm") {
        //var filetype = $('#fileupload').val().split('.')[1];
        var filetype = $('#fileupload').val();
        filetype = filetype.substring(filetype.lastIndexOf(".") + 1, filetype.length);
        if (filetype == 'jpg' || filetype == 'jpeg' || filetype == 'pdf') {
            $('#UploadForm .error').css('opacity', '0');
            initFileOnlyAjaxUpload();
        } else {
            $('#UploadForm .error').text('Please upload jpg or pdf only').css('opacity', '1');
            return false;
        }
    }
    if (formId == "frm_details") {
        callDemoAuthLatestServlet();
    }
    //Do anything on submit
    if (formId == "cinitForm") {
        callAadhaarCreditCardServlet();
        //$("#first").hide();
        //$("#second").show();
        // setTimeout(function() {$("#first").hide();  $('#second').show();}, 5000);
    }
    if (formId == "initForm") {
        callAccountLatestServlet();
        //$("#first").hide();
        //$("#second").show();
        // setTimeout(function() {$("#first").hide();  $('#second').show();}, 5000);
    }
    if (formId == "initFormAadhaar") {

        if ($('.condChk2').is(":visible")) {
            if (!$('#acceptFlag2').is(":checked")) {
                $('#acceptFlag2').parent('.checkBox').find('.erroranyone').show();
                return false;
            } 
            else {
                $('#acceptFlag2').parent('.checkBox').find('.erroranyone').hide();
            }

            if ($('#checkboxtwo').is(":checked")) {

                $('#checkboxtwo').parent('.radioBox').find('.erroranyone').hide();
            } 
            else {
                $('#checkboxtwo').parent('.radioBox').find('.erroranyone').show();
                return false;
            }
            if (!$('#acceptFlag4').is(":checked")) {
                $('.chk4Box').addClass('error-field')
                return false;
            } else  {
                $('.chk4Box').removeClass('error-field');
            }
        }
        if ($('.condChk1').is(":visible")) {
            if (!$('#acceptFlag').is(":checked")) {
                //alert('#acceptFlagNo')
                $('#acceptFlag').parent('.checkBox').find('.erroranyone').show();
                return false;
            } else if ($('#acceptFlag').is(":checked")) {
                //alert('#acceptFlagYes')
                $('#acceptFlag').parent('.checkBox').find('.erroranyone').hide();
            }
        }
        if ($('.showCheck').is(":visible")) {
            if (!$('#acceptFlag1').is(":checked")) {
                $('#acceptFlag1').parent('.checkBox').find('.erroranyone').show();
                return false;
            } else {
                $('#acceptFlag1').parent('.checkBox').find('.erroranyone').hide();
            }
        }
        var linking = $("#linking").val();
        var checked = $('input[name=radiobox]:checked').val();
        if ((checked == "NO") && (linking == "notlink")) {
            window.location.href = "/content/home/important-links/aadhaar-seeding-request-form.html";
        } else {
            var BANK_iin = $('#prefix').val();
            //alert(BANK_iin+checked);
           /* if($('.radioBox .selectedValue').text()=='Select') {
						  $('.radioBox .erroranyone').show();
						return false;
					}
					else {
						 $('.radioBox .erroranyone').hide();
					}*/
            if (checked == "YES") {
                $("#boxYesNo").show();
            } else if (checked == "NO") {
                //callAadhaarLatestServlet();
                callDemoAuthLatestServlet();
            } else {
                //callAadhaarLatestServlet();
                callDemoAuthLatestServlet();
            }
            //$("#boxYesNo").show();
        }
    }
    if (formId == "otpForm") {
        validateOTPAadhaarServlet();
        //window.location.href="http://10.24.121.111:4503//content/home/important-links/aadhaar-thank-you.html";
        //setTimeout(function() {$("#lboxOTP, #second").hide();  $('#third').show();}, 5000);
        //setTimeout(function(){ alert("Hello"); }, 3000);
    }
    if (formId == "aadhaarLinked") {
		if ($('#checkboxtwo').is(":checked")) {
			$('.condChk2 .erroranyone').hide();
		}
		else {
			 $('.condChk2 .erroranyone').show();
		}
		if (!$('#acceptFlag4').is(":checked")) {
                $('.chk4Box').addClass('error-field')
                return false;
           } 
		   else  {
                $('.chk4Box').removeClass('error-field')

           }
        if ($('#checkboxtwo').is(":checked")) {
            //alert('#checkboxtwoNo')

				var linking = $("#linking").val();
				var checked = $('input[name=radiobox]:checked').val();
					if ((checked == "NO") && (linking == "notlink")) {
						window.location.href = "/content/home/important-links/aadhaar-seeding-request-form.html";
						//window.location.href ="http://10.60.17.141:4502/content/home/important-links/aadhaar-seeding-request-form.html";
					} 
					else {
					var BANK_iin = $('#prefix').val();
                    /*if($('.selectedValue').text()=='Select') {
						 $('.condChk2 .erroranyone').show();
						return false;
					}
					else {
						 $('.condChk2 .erroranyone').hide();
					}*/
					//alert(BANK_iin+checked);
					if (checked == "YES") {
						$("#boxYesNo").show();
					} 
					else if (checked == "NO") {
						callAadhaarLatestServlet();
						//callDemoAuthLatestServlet();
					} else {
						callAadhaarLatestServlet();
						//callDemoAuthLatestServlet();
					}
					//$("#boxYesNo").show();
            }
        }
    }
    if (formId == "initFormDemo") {
        callDemoAuthLatestServlet();
        //setTimeout(function() {window.location.href="http://10.24.121.111:4503//content/home/important-links/aadhaar-thank-you.html";}, 5000);
    }
    if (formId == "aadhaarStatus") {
        if (!$("input[name$='radiobox1']").is(':checked')) {
            $('.f1Box').find('.erroranyone').show();
            //return false;
        } else if (!$("input[name$='radiobox2']").is(':checked')) {
            $('.f2Box').find('.erroranyone').show();
            //return false;
        } else {
            $('.erroranyone').hide()
        }
        if ($('#accRadio2').is(":checked")) {
            aadhaarValidation();
        }
        /*	if ($('#accTypeRadio1').is(":checked")) {
			Saving();
        }
		if ($('#accTypeRadio2').is(":checked") ){
			Current();
        }*/
        if ($('#accRadio1').is(":checked")) {
            verifycaptchaaccount();
        }
    }
}
