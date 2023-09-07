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
  if("https://m.indusind.com/content/smart-phone/aadhaar-seeding-request-form.html" !=null && "https://m.indusind.com/content/smart-phone/aadhaar-seeding-request-form.html" != ""){


	if(urlSearchparam == -1) {
	 	window.location.href="https://m.indusind.com/content/smart-phone/aadhaar-seeding-request-form.html";
	 }
      else if(urlLocation == aadhaarURL) {
          alert("You are redirected");
		 window.location.href="https://m.indusind.com/content/smart-phone/aadhaar-seeding-request-form.html";	
      }
      else if(urlLocation == aadhaarURLS) {
          alert("You are redirected1");
		 window.location.href="https://m.indusind.com/content/smart-phone/aadhaar-seeding-request-form.html";	
      }
	 else {
         //setTimeout(function(){
             var alterPath="https://m.indusind.com/content/smart-phone/aadhaar-seeding-request-form.html";
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
    if("https://m.indusind.com/content/smart-phone/aadhaar-seeding-request-form.html" !=null && "https://m.indusind.com/content/smart-phone/aadhaar-seeding-request-form.html" != ""){
window.location.href="https://m.indusind.com/content/smart-phone/aadhaar-seeding-request-form.html";
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

function failure(){


         var val =document.getElementById("yes").value ;
            //window.location.href="https://www.indusind.com/content/home/important-links/aadhaar-failure.html?q="+val;
            window.location.href="/content/home/important-links/aadhaar-failure.html?q="+val;


    }


     function linkedAadhaar(){
    	 //window.location.href="https://www.indusind.com/content/home/important-links/aadhaar-linked.html";
        var d = new Date();
  		var n = d.getTime();
 		 window.location.href="/content/home/important-links/aadhaar-linked.html?_t=" +n;
    }

    function normalFlow(){


      $('#first').hide();
      $('#generalPopup').hide();
       $('.hideclass').hide();
	   $('#first').hide();
       $('#second').show();
       $('.condChk2').hide();

    }


    function aadhaarmulti(){
    	//window.location.href="https://www.indusind.com/content/home/important-links/aadhaarmulti.html";
         window.location.href="/content/home/important-links/aadhaarmulti.html";

    }
function callAccountLatestServlet() {
    var AccountNumber= $("#AccountNumber").val();
    var DateOfBirth= $("#DateOfBirth").val();
	var captchatry = $("#cqcaptchapp").val();
	var	cq_captchakey = $("#cq_captchakeypp").val();
	$.ajax({
		type : "POST",
		url : "/bin/accountseedinglatest/posteddata",
		data : {
            AccountNumberJs : AccountNumber,
            DateOfBirthJs : DateOfBirth,
       	    captchatryJs : captchatry,
        	cq_captchakeyJs : cq_captchakey
		},
		success : function(msg) {
			var fields = msg.split('$');
			var dispayMsg = fields[0];
            console.log("displayMsg:"+dispayMsg);
			var accountSuccess=fields[1];
			var CIF_ID=fields[2];
			var custName=fields[3];
			var yearofbirth=fields[4];
			var mobileNumber=fields[5];
			var SCHM_TYPE=fields[6];
			var INDIVIDUAL=fields[7];
            var uidToken =fields[10];
			$('#SCHM_TYPE').val(SCHM_TYPE).blur();
			$('#accountSuccess').val(accountSuccess).blur();
			$('#CIF_ID').val(CIF_ID).blur();
			$('#custName').val(custName).blur();
			$('#YearOfBirth').val(yearofbirth).blur();
			$('#mobileNumber').val(mobileNumber).blur();

            $('#INDIVIDUAL').val(INDIVIDUAL).blur();

	         localStorage.setItem("SCHM_TYPE", SCHM_TYPE);
             localStorage.setItem("acc", accountSuccess);
             localStorage.setItem("mobileNumber", mobileNumber);
             localStorage.setItem("CIF_ID", CIF_ID);
             localStorage.setItem("yearofbirth", yearofbirth);
             localStorage.setItem("custName", custName);
             localStorage.setItem("custName", custName);
             localStorage.setItem("INDIVIDUAL", INDIVIDUAL);
             localStorage.setItem("uidToken",uidToken);
             localStorage.setItem("DOB", $("#DateOfBirth").val());

        document.getElementById("yes").value="y";

               if(dispayMsg=='SuccessMsg'){

                   document.getElementById("linking").value="doLinking";
                      if(SCHM_TYPE == "CAA" && INDIVIDUAL == "Non Individual"){
                          /*           
                   		document.getElementById("yes").value="y";
                     $('#generalPopup .descText').html('There are multiple signatories in this account, you can submit aadhaar details for Self and for other authorised signatories also. <br><div class="fieldBox"> <button class="btn center popbtn"  onclick="normalFlow()" type="submit">For Self </button> </div><div class="fieldBox"> <button class="btn center popbtn" onclick="aadhaarmulti()" type="submit">For</br>All</button> </div> ');
                     $('#generalPopup').show();
                     */
                     $('#generalPopup .descText').text('Aadhaar seeding is not allowed for Current Account');
                     $('#generalPopup').show();
              
               }  

              else{

                  document.getElementById("yes").value="n";

                    $('.condChk1').hide();
                     var IIN  =  fields[8];

                  IIN =   IIN.slice(1, -1);
                  var string = IIN.split(",");
               for( var i = 0 ; i < string.length ;i++)
                     {
                         if(i%2 == 0 ) {
                  var optn = document.createElement("OPTION");
                     optn.value = string[i];

                     }

                   else {
                     optn.text = string[i];
                        }

                     document.getElementById('prefix').options.add(optn);
                     console.log( string[i]);

                     }

                       document.getElementById("yes").value="n";
                        $('#first').hide();
                        $('#second').show();


                    }


            }


        

            if(dispayMsg=='InvalidCaptcha'){
				$('#generalPopup .descText').text('Please enter valid captcha');
                $('#generalPopup').show();
                // alert("Please enter valid captcha");

                	$('#cqcaptchapp').val("");
                	captchapictureRefresh();
				}
             if(dispayMsg=='InvalidAccountNumber'){
                $('#generalPopup .descText').text('Invalid Account Number');
                $('#generalPopup').show();
                 captchapictureRefresh();
                 //alert("Invalid Account Number");

				}
            if(dispayMsg=='InvalidDateOfBirth'){
                 $('#generalPopup .descText').text('Invalid Date of Birth');
                 $('#generalPopup').show();
                captchapictureRefresh();
                //alert("Invalid Date of Birth");

				}
             if(dispayMsg=='APIResponseErrors'){
                 $('#generalPopup .descText').text('Our systems have encountered an error. Please try after sometime');
                 $('#generalPopup').show();
                 captchapictureRefresh();
                

				}
             if(dispayMsg=='AadhaarAlreadyAvail'){



                  if( INDIVIDUAL == "Non Individual" && SCHM_TYPE == "CAA"){
                 document.getElementById("yes").value="y";
/*
                 $('#generalPopup .descText').html('Your Aadhaar number is already available with us.Since There are multiple authorised signatories in this account. Please click below for submitting aadhaar details for other authorised signatories. <br><div class="fieldBox center popbtn"> <button class="btn center" onclick="aadhaarmulti()" type="submit">Click here</button> </div> ');
                  $('#generalPopup').show();
				  */
				   $('#generalPopup .descText').text('Aadhaar seeding is not allowed for Current Account');
                     $('#generalPopup').show();
                 captchapictureRefresh();
                 }

                  else{

                      document.getElementById("yes").value="n";

                      $('.condChk1').hide();



           var aadhaarNumber  =  fields[8];
             document.getElementById("aadharNumber").value = aadhaarNumber;
             document.getElementById("aadharNumber").disabled = true;

            $('#aadharNumber').prev().addClass('active');

			var IIN  =  fields[9];

                      //   console.log(IIN);
          IIN =   IIN.slice(1, -1);

              var string = IIN.split(",");
             localStorage.setItem("aadhaarNumber", aadhaarNumber);
             localStorage.setItem("IIN", IIN);
             localStorage.setItem("vid", fields[10]);
             
             for( var i = 0 ; i < string.length ;i++)
             {
                 if(i%2 == 0 ) {
          var optn = document.createElement("OPTION");
             optn.value = string[i];

             }

           else {
             optn.text = string[i];
                }

             document.getElementById('prefix').options.add(optn);
                 //console.log( string[i]);

             }

            $('#generalPopup .descText').html('Your Aadhaar is already Linked to your IndusInd Bank account. <br/>Do you want to continue Seeding your Aadhaar with IndusInd Bank to receive DBT benefits/subsidies from Govt. of India<br><div class="fieldBox"> <button class="btn center popbtn" onclick="linkedAadhaar()" type="submit">Yes</button> </div><div class="fieldBox"> <button class="btn center popbtn" onclick="homepage()" type="submit">No</button> </div>');
                  $('#generalPopup').show();
                 captchapictureRefresh();


 }


 }
            if(dispayMsg=='Otherthanspecifiedresponse'){
                 $('#generalPopup .descText').html('Account number or Date of birth entered is invalid');
                  $('#generalPopup').show();
                captchapictureRefresh();

				}
		},
		error : function(xhr) {
		}
	});
}




 /*******************************/

    //Aadhaar Consent Function 


function callAadhaarConsentServlet() {


    var	flag = $('input[name=radiobox]:checked').val();
    if(flag === undefined){
      flag = "NO" ;
     }

        flag =flag.trim();

    var	CIF_ID = $("#CIF_ID").val();
    var	BANK_iin = $('#prefix').val() ;
  		BANK_iin =  BANK_iin.trim();


	$.ajax({
		type : "POST",
		url : "/bin/aadhaarconsent/posteddata",
		data : {
			CIF_IDJs : CIF_ID,
            IINjs:BANK_iin,
            flagData:flag
		},
		success : function(msg) { 
			var fields = msg.split('$');
			var dispayMsg = fields[0];

            //alert(fields);
            //alert(dispayMsg);            
		},
		error : function(xhr) {
		}
	});
}


    /*******************************/

function callAadhaarLatestServlet() {

  $("#boxYesNo").hide();
  $("#lboxOTP").show();

   otpTimer();
    //var accountNumber= $("#accountSuccess").val(); 
    var accountNumber= $("#AccountNumber").val();   
	var	aadhaarNumber = $("#aadharNumber").val();
	var	CIF_ID = $("#CIF_ID").val();
    $.ajax({
		type : "POST",
		url : "/bin/ekycaadhaarlatest/posteddata",
		data : {
			accountNumberJs : accountNumber,
			aadhaarNumberJs : aadhaarNumber,
			CIF_IDJs : CIF_ID

		},

		success : function(msg) { 
			var fields = msg.split('$');
			var dispayMsg = fields[0];
			var responseCode= fields[1];
			var aadharNumber=fields[2];
			var txn= fields[3];
			var RRN= fields[4];
			var accountForOTP=fields[5];
			var CIFIDForOTP=fields[6];
            $('#responseCodeForOTP').val(responseCode);
            $('#aadharNumberForOTP').val(aadharNumber);
            $('#txnForOTP').val(txn);
            $('#RRNForOTP').val(RRN);
            $('#accountForOTP').val(accountForOTP);
            $('#CIFIDForOTP').val(CIFIDForOTP);
            if(dispayMsg=='successMsg'){
				$('#first').hide();
            	$('#second').show();
            	$('#lboxOTP.lBoxMaster').show();
            }

             if(dispayMsg=='InvalidAadhaarNumber'){
                    $('#generalPopup .descText').text('Invalid Aadhaar number');
                    $('#generalPopup').show();
                 //alert("Invalid aadhaar number");

				}
             if(dispayMsg=='APIResponseErrors'){
                  $('#generalPopup .descText').text('Our systems have encountered an error. Please try after sometime');
                  $('#generalPopup').show();
                 // alert("Our systems have encountered an error. Please try after sometime");
				}
		},
		error : function(xhr) {
		}
	});
}





function validateOTPAadhaarServlet() {


  document.getElementById("scope").value="no";
   $(".defaultloader").show();

   $("#lboxOTP1").hide();
  var	BANK_iin = $('#prefix').val() ;
         BANK_iin  =  BANK_iin.trim();

   var bankname = $("#prefix option:selected").html();
       bankname =bankname.trim();


    var	flag = $('input[name=radiobox]:checked').val();

    var seedingRequest="";


     if(flag === undefined){
      flag = "NO" ;
      seedingRequest= "N"; 
     } 
      else if(flag == "NO"){

      seedingRequest= "N";
     } 
      else if(flag == "YES"){

      seedingRequest= "Y";
      }


    console.log("seedingRequest"+seedingRequest);




        flag =flag.trim();

        var DateOfBirth= $("#DateOfBirth").val();



        var linking= $("#linking").val();
        var accountNumber= $("#accountSuccess").val();
	    var	aadharNumber = $("#aadharNumber").val();
    	var otpNumber = $("#otpNumber").val();
        var responseCodeForOTP = $("#responseCodeForOTP").val();
         //var aadharNumberForOTP = $("#aadharNumberForOTP").val();  
		var	aadharNumberForOTP = $("#aadharNumber").val(); 
        var txnForOTP = $("#txnForOTP").val();
        var RRNForOTP = $("#RRNForOTP").val();
        //var accountForOTP=$('#accountForOTP').val();  
		var accountForOTP= $("#AccountNumber").val(); 
        var	CIF_ID = $("#CIF_ID").val();
        var mobileNo= $("#mobileNumber").val();
        var SCHM_TYPE= $("#SCHM_TYPE").val(); 
		var custName=$('#custName').val();
		var	YearOfBirth=$('#YearOfBirth').val();
		var INDIVIDUAL=$('#INDIVIDUAL').val();

		$.ajax({
			type : "POST",
			url : "/bin/validateotpaadhar/posteddata",
			data : {
           	 	otpNumberJs : otpNumber,
                responseCodeForOTPJs : responseCodeForOTP,
                aadharNumberForOTPJs : aadharNumberForOTP,
                txnForOTPJs : txnForOTP,
                RRNForOTPJs : RRNForOTP,
                accountNumberJs :accountForOTP,
                CIF_IDJs :CIF_ID,
                mobileNoJs :mobileNo,
                custNameJs :custName,
                YearOfBirthJs: DateOfBirth,
                SCHM_TYPEJs :SCHM_TYPE,
                seedingRequestJs :seedingRequest,
                INDIVIDUALJs :INDIVIDUAL,
                BANKIINjs :BANK_iin ,
                BANKNAMEjs :bankname,
                flagData :flag,
                isLinking :linking

			},


			success : function(msg) {
                $(".defaultloader").hide();
                var response = msg.split("$");
                console.log("OTPMSg:"+response[0]+":"+response[1]);
                localStorage.setItem("interactonId", response[1]);
                 document.getElementById("scope").value="yes";

                if(response[0]=='SuccessMsg'){
                    $('#first').hide();
                    $('#second').hide();
					$('#third').hide();
                    $('#lboxOTP1').hide();
                    $('#lboxOTP.lBoxMaster').hide();
                    $('#lboxOTP1.lBoxMaster').hide();

                    callAadhaarConsentServlet();
                     var val =document.getElementById("yes").value ;
                     //window.location.href="https://www.indusind.com/content/home/important-links/aadhaar-thank-you.html?q="+val;
                     window.location.href="/content/home/important-links/aadhaar-thank-you.html?q="+val;


             	}
                if(response[0]=='FailureMsg'){
                    $('#generalPopup .descText').text('Your Aadhaar Number couldn\'t be updated at this moment. Please try after some time.');
                    $('#generalPopup').show();
                    //alert("Your Aadhar Number couldn't be updated at this moment. Please try after some time.");
                    $('#first').show();
                    $('#second').hide();
					$('#third').hide();
                    $('#lboxOTP.lBoxMaster').hide();
                    $("#lboxOTP1").show();
             	}
				if(response[0]=='InvalidOTPFromEKYC'){
					increment();
                    var	counterval = $("#counter").val();
                    if(counterval < 3){
                         $('#generalPopup .descText').text('Please enter valid OTP');
                    	 $('#generalPopup').show();
                        //alert("Please enter valid OTP");
                    }
                    if(counterval=='3'){
                    $('#first').hide();
                    $('#second').hide();
                    $('#lboxOTP1').show();    
					//$('#third').show();
                    $('#lboxOTP.lBoxMaster').hide();
                    $('#AccountNumberDemo').val(accountForOTP).blur();
                    $('#AadharNumberDemo').val(aadharNumberForOTP).blur();
                    }
             	} 
				if(response[0]=='AppIssueInOTPFEKYC'){
                    $('#lboxOTP').hide();
                    $('#lboxOTP1').show();
                    

				}
			},
			error : function(xhr) {
			}
		});
	}

  function callDBTpage(){
                $("#boxYesNo").hide();
                $('#generalPopup').hide();
                $('#first').hide();
            	$('#second').show();

    }

    function homepage(){

    	//window.location.href ="https://www.indusind.com/content/home/important-links/aadhaar-seeding-request-form.html" ;
           window.location.href ="/content/home/important-links/aadhaar-seeding-request-form.html" ;


    }


 function callDemoAuthLatestServlet() {

    //var accountForOTP=$('#accountForOTP').val();  

     //var aadharNumberForOTP = $("#aadharNumberForOTP").val();  
         $("#third").hide();
         $("#lboxOTP").hide();
 		 $('#second').show();
		 $('#third,#first').hide();
         $('#lboxOTP1').hide();
         $(".defaultloader").show();
     var	BANK_iin = $('#prefix').val() ;
         BANK_iin  =  BANK_iin.trim();

   var bankname = $("#prefix option:selected").html();
       bankname =bankname.trim();


   var	flag = $('input[name=radiobox]:checked').val();

   var linking= $("#linking").val();

   var seedingRequest="";


     if(flag === undefined){
      flag = "NO" ;
      seedingRequest= "N"; 
     } 
      else if(flag == "NO"){

      seedingRequest= "N";
     } 
      else if(flag == "YES"){

      seedingRequest= "Y";
      }
   flag =flag.trim();
   

   $(".defaultloader").show();
    var DateOfBirth= $("#DateOfBirth").val();
    var	CIF_ID = $("#CIF_ID").val();
    var	custName = $("#custName").val();    

	var accountNumberDemo= $("#AccountNumber").val(); 
    var aadharNumberDemo= $("#aadharNumber").val();


     /*
     if(accountNumberDemo == ""){
           accountNumberDemo= $("#accountSuccess").val();
     }


     if(aadharNumberDemo == ""){
           aadharNumberDemo= $("#aadharNumberForOTP").val();
     }

     if( (aadharNumberDemo == "") || (accountNumberDemo == "") || ( DateOfBirth == "") ){


         location.reload();

     }
     */


    var yearOfBirth= $("#YearOfBirth").val(); 
    var mobileNo= $("#mobileNumber").val();
    var SCHM_TYPE= $("#SCHM_TYPE").val(); 
    var INDIVIDUAL=$('#INDIVIDUAL').val();

	$.ajax({
		type : "POST",
		url : "/bin/demoauthseedinglatest/posteddata",
		data : {
            accountNumberDemoJs : accountNumberDemo,
            aadharNumberDemoJs : aadharNumberDemo,
            yearOfBirthJs : DateOfBirth,
            CIF_IDJs : CIF_ID,
            custNameJs :custName,
            mobileNoJs :mobileNo,
            SCHM_TYPEJs :SCHM_TYPE,
            seedingRequestJs :seedingRequest,
            INDIVIDUALJs :INDIVIDUAL,
            BANKIINjs :BANK_iin ,
            BANKNAMEjs :bankname,
            flagData :flag,
            isLinking :linking
		},
		success : function(msg) {
            $(".defaultloader").hide();
            var response = msg.split("$");
            console.log("DemoMSg:"+response[0]+":"+response[1]);
            localStorage.setItem("interactonId", response[1]);
            $('#iterIdForFileUpload').val(response[1]);

            if(response[0]=='SuccessMsg'){
               callAadhaarConsentServlet();
             var val =document.getElementById("yes").value ;
            	
             //window.location.href="https://www.indusind.com/content/home/important-links/aadhaar-thank-you.html?q="+val;
                window.location.href="/content/home/important-links/aadhaar-thank-you.html?q="+val;
           }
            if(response[0]=='BothOTPDemoFail'){
                $('#fileUploadBox').show();

                //alert("The Aadhar number could not be updated here. Please email your e-aadhaar card with its pin code or a scanned copy of your aadhaar card along with your account number to reachus@indusind.com or visit your nearest Branch along with your Aadhaar card");
            }
            if(response[0]=='DemoAuthAPIFailure'){
                $('#generalPopup .descText').text('The Aadhaar linking failed. Please try again later.');
                $('#generalPopup').show();
               
				}
             if(response[0]=='APIResponseErrors'){
                  $('#generalPopup .descText').text('Our systems have encountered an error. Please try after sometime');
                  $('#generalPopup').show();
                 
				}
		},
		error : function(xhr) {
		}
	});
}
 function increment() {
	    $('#counter').val( function(i, oldval) {
	        return ++oldval;
	    });
	}

   var timer=null;    
    function otpTimer(){
        //console.log("123Inside");
    var aadharNumberForOTP = $("#aadharNumberForOTP").val();
    var accountForOTP=$('#accountForOTP').val();

    var sec = 60
    timer = setInterval(function() { 
        $('.timerText').text('(' + sec-- + 'secs)');
		if (sec == 55) {
			 $('.linkText').css('visibility','visible');
		}
        if (sec == -1) {
             clearInterval(timer);
             $("#lboxOTP").hide();




           var val =document.getElementById("scope").value ;
            if(val == "yes" ) {
                $(".defaultloader").hide();
                $("#lboxOTP1").show();
            }


           
        } 
    }, 1000);
    }

$(function(){
    $('#lboxOTP .close').click(function(){
        // console.log("timer working")
		 clearInterval(timer);

    })
	$('.linkText').on("click", function() {
         var aadharNumberForOTP = $("#aadharNumberForOTP").val();
         var accountForOTP=$('#accountForOTP').val();  
		 clearInterval(timer);

    	 $('#AccountNumberDemo').val(accountForOTP).blur();
    	 $('#AadharNumberDemo').val(aadharNumberForOTP).blur();
    })

     $('#confirmSubmit').click(function(){
         // console.log("1234Inside");
		var aadharNumberForOTP = $("#aadharNumberForOTP").val();
		var accountForOTP=$('#accountForOTP').val();    
			 // console.log("stop")

		$('#AccountNumberDemo').val(accountForOTP).blur();
		$('#AadharNumberDemo').val(aadharNumberForOTP).blur();    
			//clearInterval(timer);

    }) 

    $(document).on('change', '.selectContainer select', function() {
        $(this).parents('.selectContainer').find('.selectText').text($(this).val());
        var selectVal = $(this).val();
    });

    $('.submitContinue').click(function(){
		
    });


    $('#checkboxtwo').click(function() {
        if ($(this).is(":checked")) {
            $('.innerContainer select#prefix').removeAttr('disabled');
        }
    })
    $('#checkboxthree').click(function() {
        $('.errorselect').hide()
        if ($(this).is(":checked")) {
            $('.innerContainer select#prefix').attr('disabled', 'disabled');
            $('.innerContainer select#prefix').find('option:eq(0)').prop('selected', true).change();
        }
    })

	$('#NoSubmit').click(function(){
		$('#acceptFlag1,#acceptFlag2,#checkboxtwo,#checkboxthree').each(function(){ 
			this.checked = false; 
		}); 
		$('.innerContainer select#prefix').find('option:eq(0)').prop('selected', true).change();
	})
})


  function initFileOnlyAjaxUpload() {
		var	BANK_iin = $('#prefix').val() ;
         BANK_iin  =  BANK_iin.trim();

   var bankname = $("#prefix option:selected").html();
       bankname =bankname.trim();


   var	flag = $('input[name=radiobox]:checked').val();

   var linking= $("#linking").val();

   var seedingRequest="";


     if(flag === undefined){
      flag = "NO" ;
      seedingRequest= "N"; 
     } 
      else if(flag == "NO"){

      seedingRequest= "N";
     } 
      else if(flag == "YES"){

      seedingRequest= "Y";
      }
   flag =flag.trim();

    var DateOfBirth= $("#DateOfBirth").val();
    var	CIF_ID = $("#CIF_ID").val();
    var	custName = $("#custName").val();    

	var accountNumberDemo= $("#AccountNumber").val(); 
    var aadharNumberDemo= $("#aadharNumber").val();

    var yearOfBirth= $("#YearOfBirth").val(); 
    var mobileNo= $("#mobileNumber").val();
    var SCHM_TYPE= $("#SCHM_TYPE").val(); 
    var INDIVIDUAL=$('#INDIVIDUAL').val();

        var formData = new FormData();

        // FormData only has the file
        var fileInput = document.getElementById('fileupload');
        var file = fileInput.files[0];
        
        var fileName = $('#filename span').text();
        
        formData.append('our-file', file);
        formData.append('our-file', fileName);
        formData.append('our-file', accountNumberDemo);
         formData.append('our-file', aadharNumberDemo);
         formData.append('our-file', DateOfBirth);
         formData.append('our-file', CIF_ID);
         formData.append('our-file', custName);
         formData.append('our-file', mobileNo);
         formData.append('our-file', SCHM_TYPE);
         formData.append('our-file', seedingRequest);
         formData.append('our-file', INDIVIDUAL);
         formData.append('our-file', linking);
         formData.append('our-file', bankname);
         formData.append('our-file', flag);
         formData.append('our-file', BANK_iin);
         


        // Code common to both variants
        sendXHRequest(formData);
        $(".defaultloader").show();
        
    }

    
    function sendXHRequest(formData) {
        var test = 0;
        $.ajax({
            type: 'POST',
            url: '/bin/fileuploadaadhaar/posteddata',
            processData: false,
            contentType: false,
            data: formData,
            success: function(msg) {
            	var response = msg.split("$");           
            	 localStorage.setItem("interactonId", response[1]);
                if (response[0] == 'mailsuccess') {
                    $('#generalPopup .descText').text('Thank you for providing your Aadhaar Copy. We will validate the same with the information available with UIDAI and revert to you with the results');
					$('#generalPopup .close, #fileUploadBox, #loader').hide();
                 	$('#generalPopup .closeRedirection').show();
                	$('#generalPopup').show();
                } 
            }
        });
    }

