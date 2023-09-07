var previousPage = document.referrer;
var url = document.location.href;
var urlLocation= window.location.href;
var urlSearchparam = (urlLocation.indexOf('?'));
var aadhaarURL = "http://www.indusind.com/content/home/AadhaarBranches.html"
var aadhaarURLS = "https://www.indusind.com/content/home/AadhaarBranches.html"


if(previousPage.indexOf("?view=desktop") > 0 && url.indexOf("?view=desktop") < 0 ){
window.location.href = document.location.href+"?view=desktop";
}
if((previousPage.indexOf("?view=desktop") > 0) || (url.indexOf("?view=desktop") > 0)){
}
else{
var userAgent = navigator.userAgent;
var isIPad = (userAgent.indexOf("iPad") > 0);
var isWebkit = (userAgent.indexOf("AppleWebKit") > 0);
var isIOS = (userAgent.indexOf("iPhone") > 0 || userAgent.indexOf("iPod") > 0);
var isAndroid = (userAgent.indexOf("Android")  > 0);
var isNewBlackBerry = (userAgent.indexOf("AppleWebKit") > 0 && userAgent.indexOf("BlackBerry") > 0);
var isWebOS = (userAgent.indexOf("webOS") > 0);
var isWindowsMobile = (userAgent.indexOf("IEMobile") > 0);
var isSmallScreen = (((screen.width < 672) || (isAndroid && screen.width < 672)) && screen.availHeight < 672);
var isUnknownMobile = (isWebkit && isSmallScreen);
var isMobile = (isIOS || isAndroid || isNewBlackBerry || isWebOS || isWindowsMobile || isUnknownMobile);

if( /Android|webOS|iPhone|iPad|iPod|BlackBerry|IEMobile|Opera Mini/i.test(navigator.userAgent) ) {
  if("" !=null && "" != ""){


	if(urlSearchparam == -1) {
	 	window.location.href="";
	 }
      else if(urlLocation == aadhaarURL) {
          alert("You are redirected");
		 window.location.href="";	
      }
      else if(urlLocation == aadhaarURLS) {
          alert("You are redirected1");
		 window.location.href="";	
      }
	 else {
         //setTimeout(function(){
             var alterPath="";
             var alterURL=urlLocation.split('?')[1];
             window.location.href=alterPath + '?' + alterURL;
         //},5000)

	}


    }else{


window.location.href="http://www.indusind.com/content/smart-phone.html";
    }
}
    /*var isTablet = (isIPad || (isMobile && !isSmallScreen));
if ( isMobile && isSmallScreen ){
    if("" !=null && "" != ""){
window.location.href="";
    }else{
window.location.href="http://www.indusind.com/content/smart-phone.html";
    }
    }*/

}

if (top != self)
                top.location.href = self.location.href
                
                var b
      var a = document.createElement('style'),
          b = 'body{opacity:0 !important;filter:alpha(opacity=0) !important;background:none !important;}',

      h = document.getElementsByTagName('head')[0];
      a.setAttribute('id', '_nv_hm_hidden');
      a.setAttribute('type', 'text/css');
      if (a.styleSheet) a.styleSheet.cssText = b;
      else a.appendChild(document.createTextNode(b));
      h.appendChild(a); 


      setTimeout(function(){
        if(document.getElementById('_nv_hm_hidden')){
          document.getElementById('_nv_hm_hidden').innerHTML = '';
        }
      },5000);


      $(window).load(function(){
		$(".se-pre-con").fadeOut("fast");
    })
    

    (function (n, o, t, i, f, y) {
        n[i] = function () {
            (n[i].q = n[i].q || []).push(arguments)
        };
        n[i].l = new Date;
        n[t] = {};
        n[t].auth = { bid_e : '0E41E7D373FEC37B1DB0826FBA5235EB', bid : '1845', t : '420'};  //random
        n[t].async = false;
        (y = o.createElement('script')).type = 'text/javascript';
        y.src = "https://cdnhm.notifyvisitors.com/js/notify-visitors-heatmap-1.0.js";
        (f = o.getElementsByTagName('script')[0]).parentNode.insertBefore(y, f);
    })(window, document, '_nv_hm', 'nvheat');


    (function(w,d,s,l,i){w[l]=w[l]||[];w[l].push({'gtm.start':
new Date().getTime(),event:'gtm.js'});var f=d.getElementsByTagName(s)[0],
j=d.createElement(s),dl=l!='dataLayer'?'&l='+l:'';j.async=true;j.src=
'https://www.googletagmanager.com/gtm.js?id='+i+dl;f.parentNode.insertBefore(j,f);
})(window,document,'script','dataLayer','GTM-568FL2R');

!function(f,b,e,v,n,t,s){if(f.fbq)return;n=f.fbq=function(){n.callMethod?
    n.callMethod.apply(n,arguments):n.queue.push(arguments)};if(!f._fbq)f._fbq=n;
    n.push=n;n.loaded=!0;n.version='2.0';n.queue=[];t=b.createElement(e);t.async=!0;
    t.src=v;s=b.getElementsByTagName(e)[0];s.parentNode.insertBefore(t,s)}(window,
    document,'script','//connect.facebook.net/en_US/fbevents.js');
    
    fbq('init', '471253179717522');
    fbq('track', 'PageView');


    var History = window.History;
   var	State = History.getState();
    var testUrl = window.location.href;
    var replaceUrl = testUrl.split("?")[0];
    testUrl = testUrl.split("?")[1];
    if(testUrl=="q=related"){
         History.pushState("State",document.title, replaceUrl);
    }

    $(document).ready(function() {
		$('#SearchValue').keypress(function(e) {
			if (e.which == 13) {
				$('#searchBtn').click();
			}
		});
    });
    
    var searchpage = "/content/home/search-results.html";
	function redirectSearchPage() {
		var searchdata = $("#SearchValue").val();
		if (/^[a-zA-Z0-9- ]*$/.test(searchdata) == false) {
			alert('Your String Contains illegal Characters.');
		} else {
			var searchdata =searchdata.split(' ').join('+');
			var redirectpath = searchpage + "?q=" + searchdata;
			window.location.assign(redirectpath);
		}

    }
    
    $( document ).ready(function() {
        captchapictureRefresh();
   });

   $(document).ready(function(){
    captchapictureRefresh();
    $(":input").on("keypress", AvoidKeyDoardScriptTags);
});
var captchaStartTime = 0;
function captchapictureRefresh() {
   $('#cqcaptchapp').val("");
   var captchakey = ("" + Math.random()).substring(3, 8);
   var captchaimg = document.getElementById("cqcaptchaimgpp");
   var captchakeyelem = document.getElementById("cq_captchakeypp");
   captchaimg.src = captchaimg.src.split("?")[0] + "?id=" + captchakey;
   captchakeyelem.value = captchakey;
   captchaStartTime = new Date().getTime();
}
function AvoidKeyDoardScriptTags(e) {
    var unicode = e.keyCode ? e.keyCode : e.charCode
if (unicode == 60 || unicode == 62) {
   e.preventDefault();
   return false;
}
}

function callServlet() {
    var contactName = $('#contactName').val();
    var mobileNumber = $('#mobileNumber').val();
    var location = $('#location').val();
    var product = $('#product').val();
    var companyName = $('#companyName').val();
    var captchatry = $("#cqcaptchapp").val();
    var	cq_captchakey = $("#cq_captchakeypp").val();
    $.ajax({
        type : "POST",
        url : "/bin/salaryaccountopening/posteddata",
        data : {
            contactNameJs:contactName,
            mobileNumberJs:mobileNumber,
            locationJs:location,
            productJs:product,
            companyNameJs:companyName,
            captchatryJs:captchatry,
            cq_captchakeyJs:cq_captchakey
        },
        success : function(msg) {  
             if (msg=='InvalidCaptcha'){
                $('.formMessageLightbox').find('p').text("Invalid Captcha");
                openLightbox('formMessageLightbox');
            }else if(msg=='talismaError'){
                $('.formMessageLightbox').find('p').text("Our systems have encountered an error. Please try after sometime");
                openLightbox('formMessageLightbox');
            }else{
                $('.formMessageLightbox').find('p').text(msg);
                formReset();
                openLightbox('formMessageLightbox');
            }
        },
        error : function(xhr) {
           $('.formMessageLightbox').find('p').text("Problem Submiting Your information");
                    openLightbox('formMessageLightbox');
        }
    });
}
     function formReset() {
     var validator = $("#salaryAccountForm").validate();
     validator.resetForm();
     neatformReset();
    }
 function neatformReset()
 {
     captchapictureRefresh();
     document.getElementById("salaryAccountForm").reset();
     $('#contactName').val("");
     $('#selectlocation').html("Select");
     $('#selectproduct').html("Select");
    $('#mobileNumber').val("");
    $('#location').val("");
    $('#companyName').val("");
    $('#cqcaptchapp').val("");
 }

 function validation() {
    var mobileNo=$('#mobileNumber').val();
    if(mobileNo=='0000000000'){
        alert("Mobile number should not be 0000000000");
        return false;
    }
    $.validator.addMethod("mobileNumber", function(value, element) {
        return this.optional(element) || /^[789]\d{9}$/i.test(value); 
    }, "Numbers only please");
     $.validator.addMethod("lettersonly", function(value, element) {
        return this.optional(element) || /^[a-z\s']+$/i.test(value);
    }, "Letters only please");
     $.validator.addMethod("extraEmail", function(value, element) {
        return this.optional(element) || /^[A-Z0-9._-]+@[A-Z0-9.-]+\.(?:[A-Z]{2}|com|org|net|edu|gov|mil|biz|info|mobi|name|aero|asia|jobs|museum|inc)$/i.test(value);
    }, "Letters only please");
     $.validator.addMethod("valueNotEquals", function(value, element, arg){
         return arg != value;
        }, "Value must not equal arg.");      

    var validator = $("#salaryAccountForm").validate({
        rules : {
             contactName:{
                 required: true, 
                 lettersonly:true
            },
            mobileNumber:{
                required: true,
                maxlength : 12,
                number:true
            },
            location:{
                 required: true,
                 valueNotEquals:"Select"
            },
            companyName : {
                required: true,
                lettersonly:true

            },
        cqcaptchapp:{
             required: true,
             minlength : 5,
             maxlength : 5
        }
        },
        errorElement: "div",
        messages : {
             contactName :{
                required : "Please enter Name",
                lettersonly:"Enter letters only"
            },
            mobileNumber :{
                required: "Please enter your Mobile Number",
                maxlength : "Enter Only 12 Digits",
                number : "Only numbers are allowed"
            },
            location:{
                valueNotEquals: "Select Location"
            },
             companyName : {
                required : "Please enter Company Name",
                lettersonly:"Enter letters only"

            },
            cqcaptchapp:{
                required: "Enter the below characters form the image ",
                minlength : "Captcha should have atleast 5 Characters",
                maxlength : "Captcha has only 5 Characters"
           }
        }
    });
    if (validator.form()) {
        callServlet();
    }
}

var atm;
	var branch;
	var registeredOffice;
	var corporateOffice;
    var representativeOffice;
	var geocoder=null;
	var selectedValue
	var mapDisplaypath="http://www.indusind.com/locate-us"
	function setTheSelection(selection) {
		if (selection == 'atm') {
			atm = true;
		}
		if (selection == 'branch') {
			branch = true;
			//getLocation();
		}
		if (selection == 'corporateoffice') {
			corporateOffice = true;
			redirectToMapPage();
		}
		if (selection == 'registeredoffice') {
			registeredOffice = true;
			redirectToMapPage();
		}
        if (selection == 'representativeoffice') {
			representativeOffice = true;
			redirectToMapPage();
		}
	}
	function redirectToMapPage() {
		var locationToFind="";
		if(corporateOffice){
		  selectedValue = "corporate";
		}
		else if(registeredOffice){
		  selectedValue = "registered";
		}else if(representativeOffice){
		  selectedValue = "representative";
		}
		else if(atm){
		  selectedValue = "atms";
		  locationToFind = $("#enteredlocation").val();
		}else{
			selectedValue = "branches";
		    locationToFind = $("#enteredlocationBranch").val();
		}
		
		var redirectPath = mapDisplaypath + ".html?q1="+locationToFind+"&q2="+selectedValue;
		window.location.assign(redirectPath);

	}
	function redirectingToMapPage(locationToFind) {
	    if(atm == true){
		 var selectedValue = "atms"
	    }
	    if(branch == true){
	        var selectedValue = "branches"
	    }
        // console.log(locationToFind)
        //console.log(selectedValue)
		var redirectPath = mapDisplaypath + ".html?q1="+locationToFind+"&q2="+selectedValue;
		window.location.assign(redirectPath);
	}
	
	function showPosition(position)
	  {
	      geocoder=new google.maps.Geocoder;

	      var lat =position.coords.latitude;
	      var lng=position.coords.longitude;
	       var latlng = new google.maps.LatLng(lat, lng);
          // console.log(lat) ;
          // console.log(lng) ;
	      geocoder.geocode({'latLng': latlng}, function(results, status) {
	    if (status == google.maps.GeocoderStatus.OK) {
	        if (results[1]) {
	            redirectingToMapPage(results[1].formatted_address);
	        }
	        else{
	        	redirectToMapPage();
				//console.log("no result found");
	        }
	    }
      else{
          //console.log("geocoder failed");
	    }
	  });
	  }
	
	function showError(error)
	  {
	  switch(error.code) 
	    {
	    case error.PERMISSION_DENIED:
	    	//console.log("User denied the request for Geolocation.");
	      redirectToMapPage();
	      break;
	    case error.POSITION_UNAVAILABLE:
	    	//console.log("Location information is unavailable.");
	      redirectToMapPage();
	      break;
	    case error.TIMEOUT:
	    	//console.log("The request to get user location timed out.");
	      redirectToMapPage();
	      break;
	    case error.UNKNOWN_ERROR:
	    	//console.log("An unknown error occurred.");
	      redirectToMapPage();
	      break;
	    }
	  }
	
	function getLocation()
	{

	if (navigator.geolocation)
	  {
		/*
		if(/chrom(e|ium)/.test(navigator.userAgent.toLowerCase())){
	   		navigator.geolocation.getCurrentPosition(showPosition, showError);
		} else {
			redirectToMapPage();
            }*/
        navigator.geolocation.getCurrentPosition(showPosition, showError);


	  }
	    else{
			//console.log("browser does not support geolocation");
	    }
    }
    
    function atmsearchValidation() {
    	$.validator.addMethod("lettersonly", function(value, element) {
            return this.optional(element) || /^[a-z0-9, -]+$/i.test(value);
        }, "Letters only please");
        $.validator.addMethod("valueNotEquals", function(value, element, arg) {
            return arg != value;
        }, "Value must not equal arg.");

        var validator = $("#atmSearch").validate({
            onfocusout : true,
            rules : {

                atmsSearch : {
                    required : true,
                    lettersonly:true,
                    valueNotEquals : "Enter the location"

                }
            },
            messages : {

                atmsSearch : {
                    required : "Enter the Location",
                    valueNotEquals : "Enter the Location",
                    lettersonly:"Enter the name of the place"
                }
            },
            submitHandler : function(form) {
                redirectToMapPage();
            }

        });
    }
    
    function branchsearchValidation() {
    	$.validator.addMethod("lettersonly", function(value, element) {
            return this.optional(element) || /^[a-z0-9, -]+$/i.test(value);
        }, "Letters only please");
        $.validator.addMethod("valueNotEquals", function(value, element, arg) {
            return arg != value;
        }, "Value must not equal arg.");

        var validator = $("#branchSearch").validate({
            onfocusout : true,
            rules : {

            	branchesSearch : {
                    required : true,
                    lettersonly:true,
                    valueNotEquals : "Enter the location"

                }
            },
            messages : {

            	branchesSearch : {
                    required : "Enter the Location",
                    valueNotEquals : "Enter the Location",
                    lettersonly:"Enter the name of the place"
                }
            },
            submitHandler : function(form) {
                redirectToMapPage();
            }

        });
    }
    splitWrapedParsys("secondFooterWrapper");
    splitWrapedParsys("impLinks");
   splitWrapedParsys("primaryFooter");


   $(document).ready(function() {
    if($('#cqcaptchapp').length){
            captchapictureRefresh();
    }
});
var captchaStartTime = 0;
function captchapictureRefresh() {
    $('#cqcaptchapp').val("");
    var captchakey = (""+ Math.random()).substring(3, 8);
    var captchaimg = document.getElementById("cqcaptchaimgpp");
    var captchakeyelem = document.getElementById("cq_captchakeypp");
    captchaimg.src = captchaimg.src.split("?")[0] + "?id=" + captchakey;
    captchakeyelem.value = captchakey;
    captchaStartTime = new Date().getTime();
}

function formSubmit(){
    var img='/etc/designs/indusind/docroot/clientlib/img/1.JPG';
    var chaptcha='<div class="clear"></div><div class="Comment-image-slt"><input type="hidden" name="cq_captchakeypp" id="cq_captchakeypp" value="" /><div class="form_captcha_image" style="background-image: url('+img+'); background-repeat: no-repeat;"><img id="cqcaptchaimgpp" src="/etc/indusind/indusinddata/footer.captcha.png?id=123" alt="" style="height: 30px;" /></div><div id="cq_captchatimer" class="form_captchatimer"></div><div class="Refresh-btn-Cmt-wrap btnStrct"><input type="button" onclick="captchapictureRefresh()" id="btnCaptchaRefreshpicture" name="btnCaptchaRefreshpicture" value="Refresh" class="Refresh-btn-Cmt" style="cursor: pointer;"></div><div class="clear"></div><div class="Comment-slot-40 "><div class="label-box fldSet lftMrg"><label for="cqcaptchapp">Enter the characters from image<font color="red">*</font>:</label> <input class="noltmargin" type="text" id="cqcaptchapp" name="cqcaptchapp" maxlength="5" class="shadow-inbox-100" autocomplete="off"/></div></div></div><div class="clear"></div><div class="btnStrct applybtn btnStrctAadhar noLeftPadding"><input type="button" onclick="captchavalidation()" value="SUBMIT" id="aadhaar_submit"></div>';
    $('.getNewUpdateLightBox').find('.insertCaptchaBox').html(chaptcha);
    openLightbox('getNewUpdateLightBox');
    captchapictureRefresh()
    $('p.MsgPara').hide()
}
function captchavalidation(){
    var validator = $("#chaptcha_submit").validate({
        rules : {
          cqcaptchapp:{
               required: true,
                minlength : 5,
            maxlength : 5
            }
        },
        errorElement : "div",
        messages : {  
          cqcaptchapp:{
                 required: "Please enter the above characters form the image ",
                  minlength : "Captcha should have atleast 5 characters",
                maxlength : "Captcha has only 5 characters"
         }
        }
    });
    if (validator.form()) {
        callEmailServlet();
    }
}

function callEmailServlet(){
    var email = document.getElementById('validemail');
    var captchatry = $("#cqcaptchapp").val();
    var	cq_captchakey = $("#cq_captchakeypp").val();
        $('.succes').empty();
        $.ajax({
            type : "POST",
            url : "/bin/footeremail/posteddata",
            data : {
                emailjs : email.value,
                captchatryJs:captchatry,
                cq_captchakeyJs:cq_captchakey
            },
            success : function(msg) {
                 $('p.MsgPara').show();
                 $('.captchaFooter').hide()
                 if(msg=='InvalidCaptcha'){
                    $('.getNewUpdateLightBox').find('p.MsgPara').text("Please enter valid captcha");
                    openLightbox('getNewUpdateLightBox');
                     formSubmit();
                    $('#cqcaptchapp').val("");
                    captchapictureRefresh();
                }
                else if(msg=="exist"){
                    $('.getNewUpdateLightBox').find('p.MsgPara').text("Your Email is already registered");
                    openLightbox('getNewUpdateLightBox');
                    email.value='';
                }
                else if(msg=='emptyEmail'){
                    $('.getNewUpdateLightBox').find('p.MsgPara').text("Please provide a valid email address");
                    openLightbox('getNewUpdateLightBox');
                    email.value='';
                }
                else{
                    $('.getNewUpdateLightBox').find('p.MsgPara').text("Your Email is successfully registered");
                    openLightbox('getNewUpdateLightBox');
                    email.value='';
                }
            },
            error : function(xhr) {
            }
        });
}
function checkEmail() {
    var email = document.getElementById('validemail');
    //var filter = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;;
    var filter = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/


    if (!filter.test(email.value)) {
        $('.getNewUpdateLightBox').find('p.MsgPara').text("Please provide a valid email address");
        openLightbox('getNewUpdateLightBox');
        email.focus;
        return false;
    }else{
        formSubmit();
    } 

}

var _gaq = _gaq || [];
	  _gaq.push(['_setAccount', 'UA-17661972-1']);
	  _gaq.push(['_setDomainName', 'indusind.com']);
	  _gaq.push(['_trackPageview']);
	
	  (function() {
	    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
	    ga.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'stats.g.doubleclick.net/dc.js';
	    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
      })();
      
      /* <![CDATA[ */
	var google_conversion_id = 981680506;
	var google_custom_params = window.google_tag_params;
	var google_remarketing_only = true;
	/* ]]> */