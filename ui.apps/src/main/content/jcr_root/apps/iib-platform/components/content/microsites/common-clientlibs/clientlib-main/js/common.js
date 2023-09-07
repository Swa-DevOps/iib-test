(function ($, window, document, undefined) {
    $.fn.addPlaceholder = function (options) {
        var settings = $.extend({
            events: true,
            IE: true
        }, options);


        function phBehaviour(_this,isShowLable,selfEvent){
            if(isShowLable == "true"){
                if(selfEvent == false){
                    $(_this).prev(".placeholder").addClass("active");
                    $(_this).siblings(".cc").fadeIn();
                }else{
                     $(_this).prev(".placeholder").removeClass("active");
                     $(_this).siblings(".cc").hide();
                }
            }else{
                if(selfEvent == false){
                    $(_this).prev(".placeholder").hide();
                     $(_this).siblings(".cc").show();
                }else{
                    $(_this).prev(".placeholder").show();
                     $(_this).siblings(".cc").hide();
                }
            }
        }

        function placeHolderFontSize(_this){
        	_this.prev(".placeholder").css("font-size",(parseInt(_this.css("font-size"))/10)+"rem");
        }



        return this.each(function () {
            var a = $(this).attr("placeholder");
            var showlabel = $(this).attr("data-label");

            $(this).attr("placeholder", "");
            //alert($(this).val());
            if($(this).val()==""){
                $(this).before("<div class='placeholder'>" + a + "</div>")
                if($(this).val()!=""){
                    phBehaviour(this,showlabel,true)                    
                }
            }else{
                $(this).before("<div class='placeholder'>" + a + "</div>");

                if(showlabel == "true"){
                	$(this).prev(".placeholder").addClass("active");
                	$(this).siblings(".cc").show();
                }else{
                	$(this).prev(".placeholder").hide();
                }
            }

            $(this).focus(function (e) {
                if ($(this).val() == "") {
                    phBehaviour(this,showlabel,false)  
                } else {
                    phBehaviour(this,showlabel,false)  
                }
            });

            $(this).blur(function (e) {
            	//console.log($(this))
                if ($(this).val() == "") {
                  /*IF its is a number field it does accept alphabets but 
                  inavalidate it as wrong characters while keeping value as blank. 
                  try commenting this line & inputing alphabets on "number" type field*/
                  $(this).val("");  
                   phBehaviour(this,showlabel,true);
                }
                else {
                   phBehaviour(this,showlabel,false);
                }
            });

            

        });
    }

})(jQuery);

function windowORdevice() {
    if (winW > 1024) {
        $("html").addClass("desktop").removeClass("device");
    } else {
        /*Device*/
        $("html").addClass("device").removeClass("desktop");
    }
}

function customSelect(){
    $("select").each(function(){
        if($(this).parents(".selectBox").find(".selectedValue").length == 0){
            $("<div class='selectedValue'></div>").insertBefore($(this))
        }
        $(this).siblings(".selectedValue").text($(this).find("option:selected").text())     
    });
    
    $(document).on('change', 'select', function (e) {
        $(this).siblings(".selectedValue").text($(this).find("option:selected").text()) 
    });
}

function readyCalls(){
    /*-------------------------------------------------------------------------------------*/
        // Loader - Start
    /*-------------------------------------------------------------------------------------*/
        loader = $(".loader span");

        if(loaded == false){
            loader.animate({width:lWidth+"%"},1000,function(){
                toutLoader = setInterval(function(){
                    if(loaded == false && lWidth != 100){
                        if(lWidth == 95){
                            toutLoaderTime=2000;
                        }
                        if(lWidth <= 98){
                            loader.css("width",++lWidth+"%")
                        }
                    }else{
                        loader.css("width","100%");
                        clearTimeout(toutLoader);
                    }
                    
                },toutLoaderTime);
            });
        }

    /*-------------------------------------------------------------------------------------*/
        // Loader - End 
    /*-------------------------------------------------------------------------------------*/



    /*-------------------------------------------------------------------------------------*/
        // Window Setup - Start
    /*-------------------------------------------------------------------------------------*/

        winW = $(window).width();
        winH = $(window).height();
        winL = window.location.href;

        if(ua.indexOf('MSIE') > 0 || ua.indexOf('Trident/') > 0 || ua.indexOf('Edge/') > 0){
            isIE = true;
        }

        isTouch = /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent);
        var isIOS =  /iPhone|iPad|iPod|Macintosh/i.test(navigator.userAgent);

        if(isTouch || $(window).width()<=1024){
            $("html").addClass("touchDevice")
        }else{
            $("html").addClass("nonTouch");
        }

        if(isIOS){
            $("html").addClass("iOS");
        }

        if (isIE == false) {
            $("html").addClass("otherBrowsers");
        }else{
            $("html").addClass("ieBrowser");
        }

        windowORdevice();

    /*-------------------------------------------------------------------------------------*/
        // Window Setup - End
    /*-------------------------------------------------------------------------------------*/


    /*-------------------------------------------------------------------------------------*/
        // For Form Elements - Start
    /*-------------------------------------------------------------------------------------*/
        $("input[type=text], input[type=password], input[type=number], textarea").addPlaceholder();

        customSelect();

        $(document).on('focus', 'input:text, select', function(e) {
            $(this).parent().addClass("focused");
        });

        $(document).on('blur', 'input:text, select', function(e) {
            $(this).parent().removeClass("focused");
        });
    /*-------------------------------------------------------------------------------------*/
        // For Form Elements - End
    /*-------------------------------------------------------------------------------------*/   
}

var ua = window.navigator.userAgent;
var isIE = false;
var winW,winH,winL,isTouch,cScroll;
var loader;
var lWidth = 60;
var toutLoader;
var toutLoaderTime=200;
var loaded = false;

$(function(){

   $('input').bind("cut copy paste",function(e) {
      e.preventDefault();
   });

   /* $(document).on('blur','input',function(){
        $(".aadharmultiCheck input, .aadharmulti input").filter(function () {
            if($.trim($(this).val()).length == 0){
				 $('.plusSign').addClass("disabled");
            }
            else {$('.plusSign').removeClass("disabled");}
        }).length == 0;
     })*/

    var inputID = 0;
         $(".plusSign").on('click', function() {
             if($(this).hasClass('disabled')){
                 e.preventDefault();}

             else {

				if ( inputID < 4){
                     inputID++;
                         $(this).prev().before('<p class="addAadhar negativeSign"></p><div class="aadharmulti"><div class="fieldBox requiredField center"><div class="inputBox"><input class="aadhaarIncr" data-label="true" type="text" placeholder="Enter your Aadhar number" data-validation="required,numbersOnly,aadharVal,checkEmpty,matchAadhaar" autocomplete="off" maxlength="12" rel="Aadhaar Number" /><span class="error">This field is required.</span> </div></div><div class="fieldBox requiredField center"><div class="inputBox datepickerInput"><input class="DOBInc datepicker DOBStyle" data-label="true" type="text" placeholder="Enter your Date Of Birth" data-validation="required,dateMulti,checkEmpty" autocomplete="off" rel="Date Of Birth" maxlength="10" /><span class="error">This field is required.</span></div></div></div>');
                         $(this).prev().prev().find('.aadhaarIncr').attr('name','aadhaar'+inputID)
                         $(this).prev().prev().find('.DOBInc').attr('name','dob'+inputID);
                         $(this).prev().prev().find('input').addPlaceholder();
                         datePickerFun();
                         DOBFun();
						$('.plusSign').addClass("disabled");
                 }
                if (inputID == 4){
                     $(".plusSign, .plusAlign").hide();

                 }

             }
     })

     $(document).on('click','.negativeSign',function(){
        $(this).next('.aadharmulti').remove(); 
        $(this).remove(); 
        --inputID
        $('.plusSign').removeClass("disabled")
        if ( inputID < 4){
			 $(".plusSign, .plusAlign").show();
        }

     })



	DOBFun();


    //functions that setups everything    
    readyCalls();



    /*$(".oBannerCarousel").owlCarousel({
        items:1,
        nav:true,
        pullDrag:false,
        loop:true,
        dots:true,
        responsive:{
            0:{
                items:1
            },
            640:{
                items:2
            },
            1400:{
                items:3
            }
        }
    });*/


    //LightBox - START
    $(document).on('click', '.viewLBox', function (e) {
        cScroll = $(window).scrollTop();
        $("body").css("top","-"+cScroll+"px");
        $("body").addClass("lBoxOpen");
        $(".lBoxMaster .container").html("");
        $(this).next(".hiddenContent").clone().appendTo($(".lBoxMaster .lBox .container"));
        $(".lBoxMaster").fadeIn(300);
    });

    $(document).on('click', '.lBox .close', function (e) {
        $(this).closest(".lBoxMaster").fadeOut();
        $("body").removeAttr("style").removeClass("lBoxOpen");
        $(window).scrollTop(cScroll);
    });

     $(document).on('change', 'input[type="file"]', function (e) {
          var f = this.files[0];  
          var name = f.name;
          $(this).parent().prev('.otp').text(name);

    });

   /* $(document).on('click', function (e) {
        e.stopPropagation();
        if($(e.target).closest(".lBox").length == 0 && $(e.target).hasClass("lBoxMaster")){
            $('.lBox:visible .close').click();
        }
    });*/
    //LightBox - End


    $(document).on("change", "input[name=payLimit]", function(event){
        if($("input[name=payLimit]:checked").attr("id")=="pay_oa"){
            $("#otAmt").removeClass("disabledField").find("input").removeAttr("disabled");
        }else{
            $("#otAmt").addClass("disabledField").find("input").attr("disabled","");
            $(".payType").removeClass("error-field");
        }
    });

    if($("input[name=accHolder]:checked").attr("id")=="accholder1"){
        $("#holderDetails1").slideDown('slow');
        $("#holderDetails2").slideUp('slow');
    }else if($("input[name=accHolder]:checked").attr("id")=="accholder2"){
        $("#holderDetails1").slideUp('slow');
        $("#holderDetails2").slideDown('slow');
    }
    
    $('input[name=accHolder]').click(function () {
        if (this.id == "accholder1") {
            $("#holderDetails1").slideDown('slow');
            $("#holderDetails2").slideUp('slow');
        } else {
            $("#holderDetails1").slideUp('slow');
            $("#holderDetails2").slideDown('slow');
        }
    }); 


   datePickerFun();

   /* $("#closeRedirect" ).on( "click", function() {
      console.log('click')
      window.location.href="http://10.24.121.111:4503//content/home/important-links/aadhaar-thank-you.html";
    })*/

     $(".tncOpenPopup" ).on( "click", function() {
          $('#tnCPopup').show();
    })

     $('.overlay').on( "click", function() {
		e.preventdefault();
     })

     $('#offlineBtn').on( "click", function() {
        $('.uploadBox').hide();
		$('.offlineBox').show();
     })



});

function datePickerFun() {
	 $(".datepicker").datepicker({
      showOn: "button",
      buttonImage: "/content/dam/indusind/IndusaadharSeeding/calendar.png",
      buttonImageOnly: true,
      buttonText: "Select date",
      changeMonth: true, 
      changeYear: true, 
      dateFormat: "dd/mm/yy", 
      yearRange: "-100:+0",
      maxDate: -1,
	   onSelect: function(dateText) {
		$(this).blur();
	  }
    });
}

function DOBFun(){
	$('.DOBStyle').bind('keyup','keydown', function(event) {
  	var inputLength = event.target.value.length;
    if (event.keyCode != 8){
      if(inputLength === 2 || inputLength === 5){
        var thisVal = event.target.value;
        thisVal += '/';
        $(event.target).val(thisVal);
    	}
    }
  })
}




$(window).on("load", function(){
    loaded = true;
    $(".loader span").stop().clearQueue().css("width","100%").closest(".loader").delay(1000).fadeOut(800);    
});


$(window).resize(function() {
    if (this.resizeTO) clearTimeout(this.resizeTO);
    this.resizeTO = setTimeout(function() {
        $(this).trigger('resizeEnd');
    }, 300);
});

$(window).bind('resizeEnd', function() {
    winW = $(window).width();
    winH = $(window).height();
    windowORdevice();

    /*$(".bannerSection").fadeIn();*/
});


$(window).on("scroll",function(){
    //Do Something On each Scroll

    if($(window).scrollTop() == 0){
        $("header").removeClass("scrolled");
    }else if(!$("header").hasClass("scrolled")){
        $("header").addClass("scrolled");
    }

    if (this.scrollTO) clearTimeout(this.scrollTO);
    this.scrollTO = setTimeout(function() {
        $(this).trigger('scrollEnd');
    }, 300);
})


$(window).bind('scrollEnd', function() {
    //Do Something After Scroll has stop.
});

