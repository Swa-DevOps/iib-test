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
  if("https://m.indusind.com/content/smart-phone/merchant-acquiring-form.html" !=null && "https://m.indusind.com/content/smart-phone/merchant-acquiring-form.html" != ""){


	if(urlSearchparam == -1) {
	 	window.location.href="https://m.indusind.com/content/smart-phone/merchant-acquiring-form.html";
	 }
      else if(urlLocation == aadhaarURL) {
          alert("You are redirected");
		 window.location.href="https://m.indusind.com/content/smart-phone/merchant-acquiring-form.html";	
      }
      else if(urlLocation == aadhaarURLS) {
          alert("You are redirected1");
		 window.location.href="https://m.indusind.com/content/smart-phone/merchant-acquiring-form.html";	
      }
	 else {
         //setTimeout(function(){
             var alterPath="https://m.indusind.com/content/smart-phone/merchant-acquiring-form.html";
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
    if("https://m.indusind.com/content/smart-phone/merchant-acquiring-form.html" !=null && "https://m.indusind.com/content/smart-phone/merchant-acquiring-form.html" != ""){
window.location.href="https://m.indusind.com/content/smart-phone/merchant-acquiring-form.html";
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

<!-- function initMap() {

     } -->

<!-- 
	$(document).ready(function(){
		$('.loaderImg').attr('src','/etc/scripts/desktop/images/indusindloader.gif')
	})

	$(window).load(function(){
		$(".se-pre-con").fadeOut("fast", function(){
			$('.loaderImg').attr('src','')
		});
	}) -->

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

var History = window.History;
   var	State = History.getState();
    var testUrl = window.location.href;
    var replaceUrl = testUrl.split("?")[0];
    testUrl = testUrl.split("?")[1];
    if(testUrl=="q=related"){
         History.pushState("State",document.title, replaceUrl);
    }

function merchantFormData() {
		$('#loader').show();
    	$('#submitBtn').hide();

      	var utm_source = "null";
        var utm_medium = "null";
        var utm_campaign = "null";
        var utmparameters = "utm_source="+utm_source+"&"+"utm_medium="+utm_medium+"&"+"utm_campaign="+utm_campaign;

    	var name = $('#Name').val();
		var address1 = $('#Address1').val();
    	var address2 = $('#Address2').val();
    	var city = $('#city').val();
    	var mobileno = $('#MobileNumber').val();
    	var emailid = $('#Email').val();
    	var ip=userip ;
    	var isIndusind = $('input[name=radiobox]:checked').val();
    	var nameOfBusiness = $('#natureOfBusiness').val();
    	var PaymentSolution = $('#PaymentSolution').val();
    	var accountNo = $('#accAmount').val();

    	if (accountNo==''){
			accountNo="N.A"
        }



	$.ajax({
		type : "POST",
		url : "/bin/merchant/posteddata",
		data : {
			nameJs : name,
            address1js:address1,
            address2js:address2,
            cityJs:city,
            mobileNumberJs:mobileno,
            emailidJs:emailid,
            utmparametersJs:utmparameters,
            ipaddressJs:ip,
            isIndusind:isIndusind,
            nameOfBusiness:nameOfBusiness,
            PaymentSolution:PaymentSolution,
            account:accountNo


		},
		success : function(msg) { 
            $('#loader').hide();
            $('#submitBtn').show();

            
            window.location.href="https://www.indusind.com/content/home/merchant-acquiring-thank-you.html";


		},
		error : function(xhr) {
			 $('#loader').hide();
            
             window.location.href="https://www.indusind.com/content/home/merchant-acquiring-thank-you.html";

		
		}


	});


}


