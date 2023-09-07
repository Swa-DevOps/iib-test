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

<!-- $(document).ready(function(){
		$('.loaderImg').attr('src','/etc/scripts/desktop/images/indusindloader.gif')
	})

	$(window).load(function(){
		$(".se-pre-con").fadeOut("fast", function(){
			$('.loaderImg').attr('src','')
		});
	})
    </script>
    <div class="se-pre-con"><img src="" class="loaderImg"/></div> -->

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
})(window,document,'script','dataLayer','GTM-568FL2R');</script>
<!-- End Google Tag Manager -->

	<script type="text/javascript" src="/etc/scripts/desktop/common.js"></script>
	<link rel="stylesheet" href="/etc/designs/indusind/docroot/clientlib.min.css" type="text/css">
<script type="text/javascript" src="/etc/designs/indusind/docroot/clientlib.min.js">

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

$(document).ready(function(){
	$(":input").inputmask();
	$("#aadharNumber").inputmask({"mask": "9999/9999/9999"});

})

$(document).ready(function(){
        $('.closeBtn').on('click', function() {
             window.location.href="/content/home/important-links/aadhaar-seeding-status.html";
        });
    })

    var aadhaarNum = $('#aadharNumber').val().replace(/\//g, '');
    $("#aadharNumber").blur(function(){
        if(aadhaarNum == '') {
			$('.aadhaarError').hide();
        }
	}); 


function isNumberKey(evt)
  {
	 var charCode = (evt.which) ? evt.which : event.keyCode
	 if (charCode > 31 && (charCode < 48 || charCode > 57))
		return false;
	 return true;
  }

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

function aadhaarValidate(){
    var aadhaarNum = $('#aadharNumber').val().replace(/\//g, '');
	var validator = $("#aadhaar_seeding_submit").validate({
			rules : {
				aadharNumber : {
					required : true,
					minlength : 12
				}
			},
			errorElement : "div",
			messages : {
				aadharNumber : {
					required : "Please enter your Aadhaar number",
					minlength : "Please enter 12 digit Aadhaar number"
				}
			},
			errorPlacement: function(error, element) {
        		  error.insertAfter(element);
        	}
			
		});
		if (validator.form()) {
			if (aadhaarNum!= "" && !checkUID(aadhaarNum) && aadhaarNum.length == 12) {
				$('.aadhaarError').text("Please enter a valid 12 digits Aadhaar number").show();
				return false;
			}
			else {
                	$('#aadhaarError').hide();
                	$('#aadhaar_submit, #aadharNumber').prop("disabled", true);
		   			$('#aadhaar_submit, #aadharNumber').attr('disabled','disabled');
		   			$('#aadhaar_submit').hide();
					callGenerateOTPAadhaarServlet();                	
				}
		}
}


function otpValidate(){
	var otpInput = $('#otpNumber').val();

    if(otpInput == '') {
		$('.otpError').text('Please enter your OTP number').show()
    }
    else if(otpInput.length < 6) {
		$('.otpError').text('Please enter 6 digit OTP number').show()
    }
    else {
		$('.otpError').hide();
        $('#aadhaar_submit, #otpNumber').prop("disabled", true);
		$('#validateOtpBtn, #otpNumber').attr('disabled','disabled');		
		callValidateOTPAadhaarServlet();
    }
}
function callGenerateOTPAadhaarServlet() {
   var aadhaarNumber = $('#aadharNumber').val().replace(/\//g, '');
		$.ajax({
			type : "POST",
			url : "/bin/aadhaarstatusotpgeneration/posteddata",
			data : {
				aadhaarNumber : aadhaarNumber
			},
			success : function(msg) {
				var fields = msg.split('$');
				var dispayMsg = fields[0];
				var responseCode= fields[1];
				var aadharNumber=fields[2];
				var txn= fields[3];
				var RRN= fields[4];
				$('#responseCodeForOTP').val(responseCode);
	            $('#aadharNumberForOTP').val(aadharNumber);
	            $('#txnForOTP').val(txn);
	            $('#RRNForOTP').val(RRN);
				if(dispayMsg == "GenerateOTPServiceSuccess"){
		            $('.otpBox').show();
		            $('.btnBox').show();
	            }else if(dispayMsg == "GenerateOTPServiceError"){
		            $('#aadharResponse').empty();
	                $('#aadharResponse').append("<li><strong>Generate OTP Service is temporarily down.<br>Please Try after some time.</strong></li>");
                    showAadharData();
	            }
			},
			error : function(xhr) {
			}
		});
	}
function callValidateOTPAadhaarServlet() {
		var aadhaarNumber = $('#aadharNumber').val().replace(/\//g, '');
		var otpNumber = $('#otpNumber').val();
		
	    var responseCodeForOTP = $("#responseCodeForOTP").val(); 
        var txnForOTP = $("#txnForOTP").val();
        var RRNForOTP = $("#RRNForOTP").val();
		$.ajax({
			type : "POST",
			url : "/bin/aadhaarstatusotpvalidation/posteddata",
			data : {
				aadhaarNumber : aadhaarNumber,
				aadhaarOTP : otpNumber, 
				responseCodeForOTPJs : responseCodeForOTP,
                txnForOTPJs : txnForOTP,
                RRNForOTPJs : RRNForOTP,
			},
			success : function(msg) {			
				//if(msg=='InvalidOTPFromEKYC'){
					//alert("Invalid OTP");
             	if(msg == "ValidateOTPServiceError"){
		            $('#aadharResponse').empty();
	                $('#aadharResponse').append("<li><strong>OTP validation failed</strong></li>");
                    showAadharData();
	            }
             	else if(msg === "NOTMAPPED"){
             		
             		$('#aadharResponse').empty();
	                $('#aadharResponse').append("<li><strong>Aadhaar Not mapped in NPCI Mapper</strong></li>");
                    showAadharData();
             	}
				else if(msg === "UNKNOWN"){
             		
             		$('#aadharResponse').empty();
	                $('#aadharResponse').append("<li><strong>Unknown Error</strong></li>");
                    showAadharData();
             	}
				else{
	            	$('#aadharNumber').val("");
					$('#mobileNumber').val("");
		            $('#aadharResponse').empty();
		            for (var key in msg) {
			            if(msg[key] != null){
			            	$('#aadharResponse').append("<li><strong>"+ key+":</strong>"+msg[key] +"</li>");
			            }
			        }
		            if(msg != "" && msg != null){
	            		showAadharData();
		            }
	            }
			},
			error : function(xhr) {
			}
		});
	}
function callAadhaarServlet() {
	var aadhaarNumber = $('#aadharNumber').val().replace(/\//g, '');
		var mobileNumber = $('#mobileNumber').val();
		$.ajax({
			type : "POST",
			url : "/bin/aadhaarstatus/posteddata",
			data : {
				aadhaarNumber : aadhaarNumber,
				mobileNumber : mobileNumber
			},
			success : function(msg) {
				$('#aadharNumber').val("");
				$('#mobileNumber').val("");
	            $('#aadharResponse').empty();
	            for (var key in msg) {
		            if(msg[key] != null){
		            	$('#aadharResponse').append("<li><strong>"+ key+":</strong>"+msg[key] +"</li>");
		            }
		        }
	            if(msg != "" && msg != null){
            		showAadharData();
	            }
	            if(msg == "NPCIError"){
		            $('#aadharResponse').empty();
	                $('#aadharResponse').append("<li><strong>This Service is temporarily down.<br>Please Try after some time.</strong></li>");
                    showAadharData();
	            }
			},
			error : function(xhr) {
			}
		});
	}
	function showAadharData() {
		var clientWidth = $(window).width();
		var clientHeight = $(window).height();
		//var currScrollTop = $(document).scrollTop();
		$(".overlay").height($(document).height());
		$(".srLightboxAadhar").css("left",
				(clientWidth - $(".srLightboxAadhar").width()) / 2 + "px");
		$(".srLightboxAadhar").css("top",
				(clientHeight - $(".srLightboxAadhar").height()) / 2 + "px");
		$(".srLightboxAadhar").fadeIn()
		$('.overlay').fadeIn();
		setTimeout(function() {
			$(".srLightbox").find('h2').text("");
		}, 2000)
	}

splitWrapedParsys("securityTip");

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

	
