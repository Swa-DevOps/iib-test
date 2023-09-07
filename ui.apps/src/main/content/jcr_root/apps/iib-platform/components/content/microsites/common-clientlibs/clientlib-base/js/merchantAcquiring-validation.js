var radioflag;
function radioValR() {
    $("input:radio").each(function(){
         if($("input:radio[name=radiobox]:checked").length == 0){

            radioflag = false;
        }
        else {
            radioflag = true;
        }
    });
}

function radioShowHide(){
    $("input[type=radio][name=radiobox]").on("click", function() {
        $('input[type=checkbox]').prop('checked', false);
        $('.radioBox .isValid').removeClass('isValid');
     });

}


var invalidCounter = 0;
function validateMe(_this){
    $(_this).closest(".fieldBox").removeClass("isValid");
        var thisField = $(_this).attr("name");
        var validationInfo = $(_this).data("validation");
        var validationType = []; 
        if($(_this).is(":disabled"))
        {
            return false;
        }

        if(validationInfo.indexOf(",") != -1){
           validationType = validationInfo.split(",");
        }else{
           validationType.push(validationInfo);
        }

    	//var uid='307745258984'

        var validAlpha =/^[a-zA-Z]*$/;
    	var validAlphaSpecial =/^[a-zA-Z'. ]*$/;
        var validNumeric = /^[0-9]+$/;
        var validEmail = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,15})$/;
   		var validDate =  /^((((0[1-9]|1[0-9]|2[0-8])\/(02))|((0[1-9]|[12][0-9]|30)\/(04|06|09|11))|((0[1-9]|[12][0-9]|3[01])\/(01|03|05|07|08|10|12)))\/([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]|[0-9][1-9][0-9]{2}|[1-9][0-9]{3}))|((29\/02)\/(([0-9]{2}([02468][48]|[13579][26]|[2468]0))|(([02468][48]|[13579][26]|[2468]0)00)))$/
        var validAlphaNum = /^[a-zA-Z0-9 ]*$/;
    	var validMobile = /^([9][1]|[0]){0,1}([6-9]{1})([0-9]{9})$/
        var validPan = /^([a-zA-Z]{5})(\d{4})([a-zA-Z]{1})$/


        //GET Value
        if($(_this).is(":checkbox")){
            var inputVal =  $(_this).is(":checked") ? true : false;
        }
    	else if($(_this).is(":radio")){
            var inputVal =  $(_this).is(":checked") ? true : false;
        }
    	else if(_this.nodeName == "SELECT"){
           if($(_this).find('option:selected').index() == 0){
                var inputVal =  $(_this).val();
           }
        }else{
            var inputVal =  $(_this).val();
        }

        for(var i=0; i < validationType.length; i++){
            if(validationType[i] =="required" &&  (inputVal=="" || inputVal== false || inputVal== "-1" )){ // for normal select dropdown 

                    if($(_this).closest(".checkBox").length && inputVal == false){

                        $(_this).closest(".checkBox").addClass("error-field"); 

                    }
                	else if($(_this).closest(".radioBox").length && inputVal == false){
                        $(_this).closest(".fieldBox").addClass("error-field"); 

                    }
                	else if($(_this).closest(".selectBox").length && inputVal == "-1"){ // for normal select dropdown 

                        $(_this).closest(".fieldBox").addClass("error-field");    

                    }

                    else{
                        $(_this).closest(".fieldBox").addClass("error-field");

                        if($(this).siblings(".rs").length){
                            $(this).siblings(".rs").addClass("error-text");
                        }  
                    }
                	if($(_this).hasClass('acceptFlag')){
						$(_this).siblings(".error").text($(_this).attr('rel'));
					}
					else
					{$(_this).siblings(".error").text(thisField+" is required");}


					//$(_this).siblings(".error").text(thisField+" is required");


                invalidCounter++; 
                return false;
            }



            if(inputVal != "" && validationType[i].indexOf("minlength") != -1){
                var splitMe= validationType[i].split("minlength");

                if(inputVal.length < parseInt(splitMe[1])){
                    $(_this).closest(".fieldBox").addClass("error-field");
                    $(_this).siblings(".error").text($(_this).attr("name")+" should not be lesser than "+splitMe[1]+" characters");

                    invalidCounter++; 
                    return false;
                }

            }


            if(inputVal != "" && validationType[i].indexOf("maxlength") != -1){

                var maxLen = parseInt($(_this).attr("maxlength"));
                var typeOfField = validationType.indexOf("numbersOnly") != -1 ? "digits" : "letters";

                if(inputVal.length != maxLen){
                    $(_this).closest(".fieldBox").addClass("error-field");
                    $(_this).siblings(".error").text(thisField+" should be exactly of "+maxLen+" "+typeOfField);

                    invalidCounter++; 
                    return false;

                }
            }

            if(inputVal != "" && validationType[i] == "alphaSpecialVal" && validAlphaSpecial.test(inputVal) == false){

                $(_this).closest(".fieldBox").addClass("error-field");
                $(_this).siblings(".error").text(thisField+" can only have alphabets");

                invalidCounter++; 
                return false; 
            }

            if(inputVal != "" && validationType[i] == "numbersOnly" && validNumeric.test(inputVal) == false && inputVal.indexOf(" ")==-1){

				 $(_this).closest(".fieldBox").addClass("error-field");
                if($(_this).hasClass('aadhaarIncr')|| $(_this).hasClass('YOBInc')){
					 $(_this).siblings(".error").text($(_this).attr('rel')+ " can only have numbers");
                }


                else {
					 $(_this).siblings(".error").text(thisField+" can only have numbers");
                }
                invalidCounter++; 
                return false; 
            }

            if(inputVal != "" && validationType[i] == "mobileVal" && validMobile.test(inputVal) == false){

					$(_this).closest(".fieldBox").addClass("error-field");
                    $(_this).siblings(".error").text("Please enter valid "+thisField);
                    invalidCounter++; 
                    return false;
            }
             if(inputVal != "" && validationType[i] == "email" && validEmail.test(inputVal) == false){
                $(_this).closest(".fieldBox").addClass("error-field");
                $(_this).siblings(".error").text("Please enter valid "+thisField);

                invalidCounter++; 
                return false;
            }
           if(validationType[i] == "anyOneRequired"){
                if(radioflag==false) {
                    $('.radioAlign.fieldBox').addClass("error-field");
                    $(".errRadio").text("Please select any one field");
                    invalidCounter++; 
                    return false;
                }
                else {
					radioShowHide();
                }

            }
        }

        $(_this).closest(".error-field").removeClass("error-field");  
        $(_this).closest(".fieldBox").addClass("isValid"); 
}


$(function(){  
    radioValR();
    var validateTout;
    $("[data-validation]").each(function(){
        if($(this).siblings(".error").length == 0 && !($(this).attr("type")=="radio")){
                $('<div class="error"></div>').insertAfter($(this));
        }
    });

    $(document).on("keyup", "[data-validation]", function(e){
        var code = e.keyCode || e.which;
        if (code != 9) {
            var _this = this;
            clearTimeout(validateTout);
            validateTout = setTimeout(function(){
                validateMe(_this);
            },1000);
        }
    });

      $(document).on("blur change", "[data-validation]", function(e){
        radioValR();
	    var _this = this;
        clearTimeout(validateTout);
        validateTout = setTimeout(function(){
            validateMe(_this);
        },0);


	  });


    $("form").submit(function(event){
         var form = $(this);
         event.preventDefault();
       		invalidCounter = 0;
               form.find(":input[data-validation]:visible").each(function(){
                validateMe(this);

                }).promise().done(function(){
                    if(invalidCounter == 0){
                       submitForm(form.attr("id"));

                    }else{
                        invalidCounter = 0; 
                    }      
                });

    });

});


function submitForm(formId){
    if(formId == "merchantForm"){
            merchantFormData();
    }
}