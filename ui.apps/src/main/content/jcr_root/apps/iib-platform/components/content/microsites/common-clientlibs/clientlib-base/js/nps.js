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
  if("http://m.indusind.com/content/smart-phone/personal-banking/products/investments/demat-account.html" !=null && "http://m.indusind.com/content/smart-phone/personal-banking/products/investments/demat-account.html" != ""){


	if(urlSearchparam == -1) {
	 	window.location.href="http://m.indusind.com/content/smart-phone/personal-banking/products/investments/demat-account.html";
	 }
      else if(urlLocation == aadhaarURL) {
          alert("You are redirected");
		 window.location.href="http://m.indusind.com/content/smart-phone/personal-banking/products/investments/demat-account.html";	
      }
      else if(urlLocation == aadhaarURLS) {
          alert("You are redirected1");
		 window.location.href="http://m.indusind.com/content/smart-phone/personal-banking/products/investments/demat-account.html";	
      }
	 else {
         //setTimeout(function(){
             var alterPath="http://m.indusind.com/content/smart-phone/personal-banking/products/investments/demat-account.html";
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
    if("http://m.indusind.com/content/smart-phone/personal-banking/products/investments/demat-account.html" !=null && "http://m.indusind.com/content/smart-phone/personal-banking/products/investments/demat-account.html" != ""){
window.location.href="http://m.indusind.com/content/smart-phone/personal-banking/products/investments/demat-account.html";
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
    
    var jsonObj = [];
	$(document).ready(function() {
						$("#save").click(function() {
											$("label[class=featureValue]").each(
															function(index) {

																var id = $(this).text().trim();
																var featureValue = $("#featurevalue_"+ (index + 1)).val();
																item = {}
																item["featureName"] = id;
																item["featureValue"] = featureValue;

																jsonObj.push(item);
															});
												$.ajax({
													type : "POST",
													 url : "/bin/indusind/save",
													data : {
															features : JSON.stringify(jsonObj),
																	pagePath : window.location.href
															}
															}).done(function() {
																alert("Data submitted successfully");
															}).fail(
															function() {
																$("#message").html("Internal server error occurred, data could not be loaded");
															});
														});
										       $.ajax({
										    	   type : "GET",
                                                    url : "/bin/indusind/load",
			                                       data : {
									           				pagePath : window.location.href
														}
													}).done(function(response) {
														$(response).each(function(index) {
																$("#featurevalue_"+ (index + 1)).val(this.featureValue);
															});
													}).fail(function() {
														$("#message").html("Internal server error occurred, data could not be loaded");
														});
                        });
                        
                        var key="I'm interested in";
                        key=key.replace(/ /g,"");
                        key=key.replace("'","");
                        $('.innerPrimNav').find('#'+key).addClass('current');

                        splitWrapedParsys("innerPrimNav")
                        splitWrapedParsys("innerPrimNavWrap")
                        splitWrapedParsys("innerBannerWrapper");

                        if(window.location.toString().toLowerCase().indexOf("script") > 0) window.location = "https://www.indusind.com/personal-banking/products/insurance/life-insurance.html";


                        $(document).ready(function(){
                            $('#personal .detailApply').after('<a href="javascript:;.html?product=nps" class="detailContri" title="Click for Apply Second">Click for Subsequent Contribution</a>')
                            
                            $("#personal .detailApply").click(function(){
                            $("#npsdiv, .overlay").show();
                            
                            })
                            
                            $('#personal .detailContri').click(function(){
                                $("#npsContri, .overlay").show();
                             })
                        
                        $(".closeBtn").click(function(){
                        $(".npspopup, .overlay").hide();
                        
                        });
                        });
                        
                        
                        //by Ashish
                        function getAllKarvyParams() {
                        
                            $.ajax({
                                type : "POST",
                                url : "/bin/karvyintegeration",
                                data : {
                                    data1 : "Ashish",
                                    flagData:"Y"
                                },
                                success : function(msg) { 
                        
                                    console.log(msg);
                                   // alert(msg);
                                    var fields = msg.split('$');
                                    var UniqueTranCodeval = fields[0];
                                    var CRACodeval =fields[1]
                                    var EncUniqueKeyval =fields[2]
                                    var RandomNoval =fields[3]
                        
                                    console.log(msg);
                                    //$('#npsdiv').hide();
                                    OpenInNewTabWinBrowser("https://cra.karvy.com/POPONLINE/POPAuthenticate",UniqueTranCodeval,CRACodeval,EncUniqueKeyval,RandomNoval);
                        
                                },
                                error : function(xhr) {
                                    alert("error");
                                }
                        
                        
                            });
                        
                        
                        }
                        
                        
                        function OpenInNewTabWinBrowser(url,UniqueTranCodeval,CRACodeval,EncUniqueKeyval,RandomNoval)
                        {
                         var param = { 'UniqueTranCode' : UniqueTranCodeval, 'CRACode': CRACodeval, 'EncUniqueKey': EncUniqueKeyval, 'RandomNo': RandomNoval };
                         OpenWindowWithPost(url, "NewFile", param);
                        }
                         
                         
                        function OpenWindowWithPost(url, name, params)
                        {
                         var form = document.createElement("form");
                         form.setAttribute("method", "post");
                         form.setAttribute("action", url);
                         form.setAttribute("target", name);
                         for (var i in params)
                         {
                           if (params.hasOwnProperty(i))
                           {
                             var input = document.createElement('input');
                             input.type = 'hidden';
                             input.name = i;
                             input.value = params[i];
                             form.appendChild(input);
                           }
                         }
                         document.body.appendChild(form);
                        
                         window.open(url, name);
                         form.submit();
                         document.body.removeChild(form);
                        }
                        
                        //Prashant
                        function getAllNPSKarvyParams() {
                            $.ajax({
                                type : "POST",
                                url : "/bin/npskarvyintegeration",
                                data : {
                                    data1 : "Ashish",
                                    flagData:"Y"
                                },
                                success : function(msg) { 
                        
                                    console.log(msg);
                                   // alert(msg);
                                    var fields = msg.split('$');
                                    //UniqueTranCode + "$" + CRACode + "$" + encUniqueKey + "$" + ClienData + "$" + ContFlag + "$" + String.valueOf(randomNumber)
                                    var UniqueTranCodeval = fields[0];
                                    var CRACodeval =fields[1]
                                    var EncUniqueKeyval =fields[2]
                                    var ClienData=fields[3]
                                    var ContFlag=fields[4]
                                    var RandomNoval =fields[5]
                        
                                    console.log(msg);
                                    //$('#npsdiv').hide();
                                    OpenInNewTabWinBrowsernps("https://cra.karvy.com/poponline/POPAuthContribution",UniqueTranCodeval,CRACodeval,EncUniqueKeyval,ClienData,ContFlag,RandomNoval);
                        
                                },
                                error : function(xhr) {
                                    alert("error");
                                }
                        
                        
                            });
                        
                        
                        }
                        
                        
                        function OpenInNewTabWinBrowsernps(url,UniqueTranCodeval,CRACodeval,EncUniqueKeyval,ClienData,ContFlag,RandomNoval)
                        {
                         var param = { 'UniqueTranCode' : UniqueTranCodeval, 'CRACode': CRACodeval, 'EncUniqueKey': EncUniqueKeyval,'ClienData':ClienData,'ContFlag':ContFlag ,'RandomNo': RandomNoval };
                         OpenWindowWithPostnps(url, "NewFile", param);
                        }
                         
                         
                        function OpenWindowWithPostnps(url, name, params)
                        {
                         var form = document.createElement("form");
                         form.setAttribute("method", "post");
                         form.setAttribute("action", url);
                         form.setAttribute("target", name);
                         for (var i in params)
                         {
                           if (params.hasOwnProperty(i))
                           {
                             var input = document.createElement('input');
                             input.type = 'hidden';
                             input.name = i;
                             input.value = params[i];
                             form.appendChild(input);
                           }
                         }
                         document.body.appendChild(form);
                        
                         window.open(url, name);
                         form.submit();
                         document.body.removeChild(form);
                        }

                        $(document).ready(function(){

                            var previousPage=getParameterByName('page');
                            var redirect="false";
                            updateHTML();
                            
                            if ( redirect=="true" ) {
                                //document.getElementById('personal').style.display = "none";
                                //document.getElementById('ge').style.display = "block";
                            } 
                            else {
                                //document.getElementById('ge').style.display = "none";
                                //document.getElementById('personal').style.display = "block";
                    
                                function getCookie(cname)
                                {
                                    var name = cname + "=";
                                    var ca = document.cookie.split(';');
                                    for(var i=0; i<ca.length; i++)
                                    {
                                        var c = ca[i].trim();
                                        if (c.indexOf(name)==0)
                                        {
                                            return c.substring(name.length,c.length);
                                        }
                                    }
                                    return "notFound";
                                }
                                var va="Investments";
                                var name= getCookie(va);
                                if(name!="notFound"){
                                    var splitVal=name.split(',');
                                    if(splitVal.length <= 4){
                                        $("#"+va+" span").html(splitVal.length-1);
                                    }
                                    for(var j=0;j<splitVal.length;j++){
                                        $('input:checkbox').each(function(){
                                            if(splitVal[j]===$(this).attr("value")){
                                                $(this).parent().addClass("checked") ;
                                                $(this).prop("checked",true);
                                                $(this).parents(".compare").find("label").hide();
                                                $(this).parents(".compare").find(".comparebtn").show();
                                            }
                                        });
                                    }
                                }
                            }
                        });
                    
                        function getParameterByName(name) {
                            name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
                            var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
                                results = regex.exec(location.search);
                            return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
                        }
                    
                        $(document).on("click",'.compare input:checkbox', function(){
                            if($(this).is(":checked")){
                    
                                function setCookie(name,Value)
                                {
                                    document.cookie = name + "=" +Value+";path=/";
                    
                                }
                    
                                function getCookie(cname)
                                {
                    
                                    var name = cname + "=";
                                    var ca = document.cookie.split(';');
                                    for(var i=0; i<ca.length; i++)
                                    {
                                        var c = ca[i].trim();
                                        if (c.indexOf(name)==0)
                                        {
                                            return c.substring(name.length,c.length);
                                        }
                    
                                    }
                                    return "notFound";
                                }
                    
                    
                                var cookiename=$(this).attr("class");
                                var ifExists=getCookie(cookiename);
                                var splitCookie=ifExists.split(',');
                                if(splitCookie.length <= 3){
                                    $('.compare').find(".comparebtn span").html(splitCookie.length);
                                }
                                var cookieValue=$(this).attr("value");
                                if(splitCookie.length >= 4){
                                    $('.ratingLightbox').find('p').text("You can compare only three products");
                                    openLightbox('ratingLightbox');
                                    $(this).prop("checked",false);
                                    $(this).parent().removeClass("checked") ;
                                    $(this).parents(".compare").find(".comparebtn").hide();
                                    $(this).parents(".compare").find("label").show();
                                }
                                else{
                                    if(ifExists==="notFound"){
                                        var cookie=cookieValue+",";
                                        setCookie(cookiename,cookie);
                                    }
                                    else{
                                        ifExists+=cookieValue+",";
                                        setCookie(cookiename,ifExists)
                                    }
                    
                                }
                    
                            }
                            else{
                    
                                function deletecook(cookiename) {
                                    var d = new Date();
                                    document.cookie = cookiename+"=;path=/;expires=" + d.toGMTString() + ";" + ";";
                                }
                    
                    
                                function setCookies(name,Value)
                                {
                                    document.cookie = name + "=" +Value+",;path=/";
                                }
                    
                                function getCookie(cname)
                                {
                    
                                    var name = cname + "=";
                                    var ca = document.cookie.split(';');
                                    for(var i=0; i<ca.length; i++)
                                    {
                                        var c = ca[i].trim();
                                        if (c.indexOf(name)==0)
                                        {
                                            return c.substring(name.length,c.length);
                                        }
                    
                                    }
                                    return "notFound";
                                }
                    
                    
                                var cookiename=$(this).attr("class");
                                var ifExists=getCookie(cookiename);
                                var splitCookie=ifExists.split(',');
                                var cookieValue=$(this).attr("value");
                    
                    
                                if(splitCookie.length == 2){
                                    deletecook(cookiename);
                                }
                                else{
                                    var value;
                                    cookieValue+=",";
                                    ifExists=ifExists.replace(cookieValue,"");
                                    ifExists=ifExists.slice(0,-1);
                                    setCookies(cookiename,ifExists);
                                }
                    
                    
                    
                            }
                    
                        });
                    
                        function onClickCompare(divid) {
                    
                            var checkboxArray=getCookie(divid);
                            var array=checkboxArray.split(',');
                            function getCookie(cname)
                            {
                    
                                var name = cname + "=";
                                var ca = document.cookie.split(';');
                                for(var i=0; i<ca.length; i++)
                                {
                                    var c = ca[i].trim();
                                    if (c.indexOf(name)==0)
                                    {
                                        return c.substring(name.length,c.length);
                                    }
                    
                                }
                                return "notFound";
                            }
                            checkboxArray.indexOf(",on");
                            checkboxArray = checkboxArray.replace(",on", "");
                            var dropdownvalue = $('#dropdown').val();
                            var count=array.length-1;
                            if(count==2)
                                var flags="false";
                            else
                                var flags="true";
                    
                                if(count>=2){
                                $.ajax({
                                    type : "POST",
                                    url : "/bin/pageprop/posteddata",
                                    data : {
                                        value : checkboxArray,
                                        dropdown : dropdownvalue,
                                        count:count,
                                        flags:flags
                                    },
                                    success : function(msg) {
                                        window.location.href = msg;
                                    },
                                    error : function(xhr) {
                    
                                    }
                            });
                            }else{
                                $('.formMessageLightbox').find('p').text("Compare atleast two products");
                                openLightbox('formMessageLightbox');
                            }
                        }
                    
                          function updateHTML(elmId, value) {
                      var elem = document.getElementById(elmId);
                      if(typeof elem !== 'undefined' && elem !== null) {
                        elem.innerHTML = value;
                      }
                    }

                    splitWrapedParsys("prdDetailLeft");

                    function downloadFile(filePath){
                        window.open(filePath);
                    }
                    function getTwitter(currentpath){
                        window.open("http://twitter.com/home?status="+document.title+"+"+currentpath);
                    }



                    var appId = '';
(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId="+appId;
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));

splitWrapedParsys("tabWrapper ");
splitWrapedParsys("tabContWrapper");

function jump(form) {
    var myindex = form.menu.selectedIndex
    if (form.menu.options[myindex].value != "Other Online Services") {
        window.open(form.menu.options[myindex].value,"_blank");
    }
}


$(document).ready(function() {
    var url=window.location.hash;
try {
     if (url.indexOf('#review') !== -1 ){
                $("#review").trigger("click");
    }
       var actCount_rating = 0, deActCount_rating = 0;

    $("#rating-class-rating div").each(function(){
        var $this = $(this);
         if($this.find("span").attr("class") == "actRating"){
            actCount_rating++;
        } else {
            deActCount_rating++;
        }

    });

      $("#rating-class-rating div").mouseover(function() {

        $("#rating-class-rating div").each(function(i) {
            $(this).find("span").removeClass('actRating').addClass('blank');
        });

         var indexmine = $(this).index("#rating-class-rating div");
        indexmine = indexmine + 1;


       $("#rating-class-rating div:lt("+indexmine+")").find("span").removeClass('blank').addClass('actRating');

    }).mouseout(function() {
        $("#rating-class-rating div span").removeClass('actRating').addClass('blank');
        revertOriginalRating(actCount_rating);
    });

    function revertOriginalRating(currActiveRating) {
        actCount_rating = currActiveRating;
        var loopCount = 0;
        $("#rating-class-rating div").each(function(){
            if(loopCount < currActiveRating){
                $(this).find("span").removeClass('blank').addClass('actRating');
                loopCount++;
            } else {

            }
        });
    }
var updatedrate;
    var updatedtotaiuser;
var flags;
    $("#rating-class-rating div").click(function(){
         var sel = this.id;
         var nodePath="/content/home/personal-banking/products/investments/nps/jcr:content/mainpar/rating";
         var nodeName="rating"
         var reset = "";
         var statusClass = "rate-Prd-status";
         var rateNumberClass = "rate-number";
         var createidforpage="";
         var idforpage = "persdemataccount";

         $.ajax({
             type : "POST",
             url : "/bin/rating/posteddata",
             data : {
                 selectedOption : sel.charAt(0),
                 nodePath	   : nodePath,
                 statusClass : statusClass,
                 rateNumberClass : rateNumberClass,
                 nodeName		: nodeName,
                 createidforpage       : createidforpage,
                 idforpage  : idforpage
             },
             success : function(listItems) {
                 if(listItems != ""){
                     var obj=jQuery.parseJSON(listItems);

                     if(obj[0].resultMsg != 'Error'){
                         revertOriginalRating(parseInt(obj[1].avgRating));
                     for (var i=1; i<= 5; i++){
                         if(i<=obj[1].avgRating){
                             if($('#'+i+'-'+obj[5].nodeName+" span").hasClass('blank')){
                                 $('#'+i+'-'+obj[5].nodeName+" span").removeClass('blank');
                                 $('#'+i+'-'+obj[5].nodeName+" span").addClass('actRating');
                             }
                         }else{
                            if($('#'+i+'-'+obj[5].nodeName+" span").hasClass('actRating')){
                                $('#'+i+'-'+obj[5].nodeName+" span").removeClass('actRating');
                                $('#'+i+'-'+obj[5].nodeName+" span").addClass('blank');
                            }
                         }
                         }
                         flags='yes';
                     }
                         var content = '';
                         content += '<p class="'
                         content += obj[3].statusClass;
                         content += '">';
                         content +=  'Overall Rating ';
                            content += '<span class="';
                            content += obj[4].rateNumberClass;
                            content += '">';
                         content += obj[1].avgRating;
                          content += '</span>';
                          content += ', Based on ';
                          content += obj[2].totalUsers;
                          content += ' votes </p>';

                            $('#pointid').html(obj[1].avgRating);
                            $('.usertotal').html(obj[2].totalUsers);
                              $('#rating-res-'+obj[5].nodeName).empty();
                              $('#rating-res-'+obj[5].nodeName).append(content);

                     if(flags == 'yes')
                     {
                  updatedrate=(obj[1].avgRating);
                  updatedtotaiuser=(obj[2].totalUsers);
                         flags='no';
                     }

                          if(obj[0].resultMsg == 'Error'){
                              /* $('#rwrapper-int-'+obj[5].nodeName).show();
                              $('#show-error-'+obj[5].nodeName).trigger('click'); */
                               /* openLightbox('ratingLightbox1');  */
                            if( flags=='no')
                            {
                            $('#pointid').empty();
                              $('#pointid').append(updatedrate);
                            $('.usertotal').empty();
                            $('.usertotal').append(updatedtotaiuser);
                            }

                          }
                          else
                              {
                              openLightbox('ratingLightbox');

                              }
                 }

             },
             error : function(xhr) {
             }
       })
     })

    }catch(err)
    {
    }
});

function validation() {

    $.validator.addMethod("lettersonly", function(value, element) {

        return this.optional(element) || /^[a-z ]+$/i.test(value);

    }, "Letters only please");

    $.validator
            .addMethod(
                    "extraEmail",
                    function(value, element) {

                        return this.optional(element)
                                || /^[A-Z0-9._-]+@[A-Z0-9.-]+\.(?:[A-Z]{2}|com|org|net|edu|gov|mil|biz|info|mobi|name|aero|asia|jobs|museum|inc)$/i
                                        .test(value);

                    }, "Letters only please");

    $.validator.addMethod("valueNotEquals", function(value, element, arg) {

        return arg != value;

    }, "Value must not equal arg.");

    var validator = $("#reviewForm")
            .validate(
                    {

                        rules : {

                            name : {

                                required : true,

                                lettersonly : true,

                                minlength : 1,

                                maxlength : 50

                            },

                            title : {

                                required : true,
                                minlength : 2,
                                maxlength : 20

                            },

                            comment : {

                                required : true,
                                minlength : 2,
                                maxlength : 500

                            },

                            cqcaptchapp : {

                                required : true,
                                minlength : 5,
                                maxlength : 5

                            }

                        },

                        errorElement : "div",

                        messages : {

                            name : {

                                required : "Enter name",

                                lettersonly : "Enter only aplhabets",

                                minlength : "The minimum length  is 1",

                                maxlength : "Your name has exceeded the maximum length 50"

                            },

                            title : {

                                required : "Enter title",
                                minlength : "Title is too short",
                                maxlength : "The title should be less than 20 Characters"

                            },

                            comment : {

                                required : "Enter comments",
                                minlength : "Your comment is too short",
                                maxlength : "The comment should be less than 500 characters"

                            },

                            cqcaptchapp : {

                                required : "Enter the above characters form the image ",
                                minlength : "Captcha should have atleast 5 Characters",
                                maxlength : "Captcha has only 5 Characters"

                            }

                        }

                    });

    if (validator.form()) {

        commentDeliver();

    }

}
function ShowModeration(id){
    var temp=id
    if (id.indexOf("comment-delete") >= 0){
        alert("the show is for deleting comment ");
        temp=id.replace("comment-delete", "editcommentshow");
        alert("the value of temp is"+temp);
    }
    if(id.indexOf("editcommentshow") >= 0){
        alert("the show is for editing comment ");
        temp=id.replace("editcommentshow", "comment-delete");
        alert("the value of temp is"+temp);
    }
    $("#"+temp).hide();
    $("#"+id).show();
}

$("#review").on("click", function(){
    var pagePathjs = $("#PagePath").val();
    var contentPathjs = $('#currentPagePath').val();
     $.ajax({
         type : "POST",
         url : "/bin/reviewcomments/posteddata",
         data : {
             contentPath : contentPathjs,
            pagePath : pagePathjs,
         },
         success : function(msg) {
             var myObj; 
             var txt = "";
                myObj = JSON.parse(msg);

                //txt += "<table border='1'>";
                //txt += "<tr><th>Name</th><th>Title</th><th>Comment</th><th>Date & Time</th></tr>";
                for (x of myObj) {
                    console.log(x)
                    //  txt += "<h3>" + x.title + "</h3>"
                   // txt += "<tr><td>" + myObj[x].name + "</td><td>" + myObj[x].title + "</td><td>" + myObj[x].comments + "</td><td>" + myObj[x].publisheddate + "</td></tr>";
                    txt += "<h3>" + x.title + "</h3><p class='reviewTxt'> Review by <span>" +x.name+ "</span>, <span>" + x.publisheddate+"</span></p><p>"+x.comments+"</p>"
                }
                    //txt += "</table>" ;
             //document.write(txt);
                document.getElementById("reviewProductPublished").innerHTML = txt;
               // $('.lightbox.history, #historytable').show();
         },
         error : function(xhr) {
         }
   });
 });


 splitWrapedParsys('ratePrdWrapper');

 $(document).ready(function() {
    var pagePathjs = $("#PagePath").val();
    var contentPathjs = $('#currentPagePath').val();

     $.ajax({
         type : "POST",
         url : "/bin/reviewcommentscount/posteddata",
         data : {
             contentPath : contentPathjs,
            pagePath : pagePathjs,
         },
         success : function(msg) {
             var msgsplit=msg.split("$");
             var totalNoOfYes=msgsplit[0];
             var commentCount=msgsplit[1];
             var reviewPercentage=msgsplit[2];
             document.getElementById("recommendtotal").innerHTML = totalNoOfYes;
             document.getElementById("recommendtotaldesc").innerHTML=totalNoOfYes+ " out of "+ commentCount+"("+reviewPercentage+"%) reviewers recommend this product";
         },
         error : function(xhr) {
         }
   });
});

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
    