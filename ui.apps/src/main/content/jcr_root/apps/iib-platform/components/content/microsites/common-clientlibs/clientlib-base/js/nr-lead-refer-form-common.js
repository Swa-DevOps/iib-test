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


    //functions that setups everything    
    readyCalls();


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
        if ($('.close').hasClass('ClosePopup')) {
           window.location.reload()
        }
        else {
			$(this).closest(".lBoxMaster").fadeOut();
            $("body").removeAttr("style").removeClass("lBoxOpen");
            $(window).scrollTop(cScroll);
        }
    });

   /* $(document).on('click', '.lBox .close, .overlay', function (e) {
        alert(1);
        $(this).closest(".lBoxMaster").fadeOut();
        $("body").removeAttr("style").removeClass("lBoxOpen");
        $(window).scrollTop(cScroll);
    });*/
    //LightBox - End


     $('.overlay').on( "click", function() {
		e.preventdefault();
     })

});


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

