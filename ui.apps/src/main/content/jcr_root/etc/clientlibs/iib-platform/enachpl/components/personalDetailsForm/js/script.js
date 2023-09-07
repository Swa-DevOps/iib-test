var repaymentBankCode='';
var flag = false;
var bankName;
var mobNo;

let backFromConfirm = sessionStorage.getItem("backconfirmGUID");
let sessionCheck = sessionStorage.getItem("EPLSESSIONID");
    if(sessionCheck=="" || sessionCheck == "null" || sessionCheck == null )
    {
        sessionCheck ="";
       //  window.location.assign("/content/enach-pl/home/registration-pl.html");
    }else if (backFromConfirm=="" || backFromConfirm == "null" || backFromConfirm == null )
    {

		//window.location.assign("/content/enach-pl/home/registration-pl.html");

    }else
    {



        sessionStorage.removeItem("backconfirmGUID");
    }


$(document).ready(function() {	

	let sessionUID = sessionStorage.getItem("EPLSESSIONID");

		let formData = {
                   repaymentBankCode : repaymentBankCode
                }
        
        let temkey ="";
        temkey=makeid();
        let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
        encryptFormData=encryptFormData+":"+temkey;




	$.ajax({
   		type : "POST",
   		url : "/bin/repaymentBankVerify",
		 headers: {"X-AUTH-SESSION" : sessionUID,
        			   "X-AUTH-TOKEN" : encryptFormData},

   		success : function(data) {

            let v1 = decryptMessage(data,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
   			var result = JSON.parse(v1);
   			bankName = result.bankName;

   			if(result.success=='true') {
   				$('#repaymentBankName').html(bankName);
   				
   				$("#proceedBtn").attr('disabled','disabled');
   				$('#freqDropdown').val(result.frequencyType);
   				$('#freqDropdown').attr("disabled", true);
   			} else {
   				$('#repaymentBankName').html('NA');
   			}
   		},
   		error : function(error) {
   			console.log("Something really bad happened " + error);
   		}
   	});


});

$('input[name=bankIFSCcode]').change(function() {

$('#ifscMsg').hide();
$('#c').prop('checked',false);
});

$('input[name=confirmIFSC]').change(function() {
	var ifsc = $('#bankIFSCcode').val();
	var confirmIfsc = $('#confirmBankIFSCcode').val();
	//var ifscRegex = /^[A-Za-z]{4}\d{7}$/;
	var ifscRegex = /^[A-Za-z]{4}0[A-Z0-9a-z]{6}$/

   // alert((ifscRegex.test(ifsc)));
   // alert((ifscRegex.test(confirmIfsc)));



	
	if( (ifsc == confirmIfsc) && (ifscRegex.test(ifsc)) && (ifscRegex.test(confirmIfsc)) ) {
		$('#ifscMsg').hide();
		let sessionUID = sessionStorage.getItem("EPLSESSIONID");

		let formData = {
                    ifsc : confirmIfsc,
	   				repaymentBankName : bankName
                }
        
        let temkey ="";
        temkey=makeid();
        let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
        encryptFormData=encryptFormData+":"+temkey;



		$.ajax({
	   		type : "POST",
	   		url : "/bin/ifscVerify",
	   		/*data : {
	   			ifsc : confirmIfsc,
	   			repaymentBankName : bankName
	   		},*/
             headers: {"X-AUTH-SESSION" : sessionUID,
        			   "X-AUTH-TOKEN" : encryptFormData},

	   		success : function(data) {
				let v1 = decryptMessage(data,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
	   			var res = JSON.parse(v1);
	   			//console.log(res);
	   			if(res.success == 'true') {
	   				flag = true;
	   				$("#proceedBtn").removeAttr('disabled');
                    $( "#proceedBtn" ).removeClass( "disableconfirmbutton" ).addClass( "confirmbutton btnonlybox" );

	   			} else {

                    $('#ifscMsg').show();
	   				$('#ifscMsg').html('Please enter a valid IFSC code');
	   			}
	   		},
	   		error : function(error) {
	   			console.log("Something really bad happened " + error);
	   		}
	   	});
	} else {

		$( "#proceedBtn" ).removeClass( "confirmbutton btnonlybox" ).addClass( "disableconfirmbutton" );
		$("#proceedBtn").attr('disabled','disabled');
		$('#ifscMsg').show();
		$('#ifscMsg').html('Please enter a valid IFSC code');
	}
});

$(document).on('click', '#proceedBtn', function (event) {
	let bankCode = $('#bankIFSCcode').val()
	let confirmBankCode = $('#confirmBankIFSCcode').val()
	let iAgree = $('#c')[0].checked;
	//let iAgreeConfirmed = $('#d')[0].checked;
	let iAgreeConfirmed = "true";
    let clickedFreq = $('#freqDropdown').val();
	let bankAccountType = $('#bankAccountType').val();
    let custName = $('#userName').text();
	let loanNumber = $('#loanNumber').text();
	let startDate = $('#startDate').text();
    let EMI_amount = $('#EMI_amount').text();
	let SI_amount = $('#SI_amount').text();
	let Other_bank_account_number = $('#Other_bank_account_number').text();
	let repaymentBankName = $('#repaymentBankName').text();
	let corprateName = "INDUSIND BANK";
    let utilityNumber = "INDB00009999";
    let purposeOfMandate = "Loan Installment";



	if (!bankCode) {
		$('.eNACHAlert').html("Please Enter the IFSC Code");
		$('#enachAlert').show();
		$('.hvr-btn').on('click', function(e) {
			$('#enachAlert').hide();
		});

	} else if (!confirmBankCode) {
		$('.eNACHAlert').html("Please Enter the IFSC Code");
		$('#enachAlert').show();
		$('.hvr-btn').on('click', function(e) {
			$('#enachAlert').hide();
		});
	} else if (confirmBankCode != bankCode) {
		$('.eNACHAlert').html("The IFSC Code doesn't match");
		$('#enachAlert').show();
		$('.hvr-btn').on('click', function(e) {
			$('#enachAlert').hide();
		});
	} /*else if(flag==false) {
		$('.eNACHAlert').html("Please enter a valid IFSC code");
		$('#enachAlert').show();
		$('.hvr-btn').on('click', function(e) {
			$('#enachAlert').hide();
		});
	}*/
	else if (iAgree == false ||  iAgreeConfirmed == false)
	{
		$('.eNACHAlert').html("Please agree to confirm to all Conditions");
		$('#enachAlert').show();
		$('.hvr-btn').on('click', function(e) {
			$('#enachAlert').hide();
		});
	}
	else {

		//$('.eNACHAlert').html("You will now be leaving the IndusInd Bank Ltd. Website and be taken to the BillDesk portal.");
		//$('#enachAlert').show();

		//$('.hvr-btn').on('click', function(e){

		let sessionUID = sessionStorage.getItem("EPLSESSIONID");

		let formData = {
                    ifsc: confirmBankCode,
					frequency: clickedFreq,
					bankAccountType: bankAccountType,
                    tid:mobNo,
            		custName:custName,
                    loanNumber:loanNumber,
                    startDate:startDate,
                    EMI_amount:EMI_amount,
                    SI_amount:SI_amount,
                    Other_bank_account_number:Other_bank_account_number,
                    repaymentBankName:repaymentBankName,
                    corprateName:corprateName,
                    utilityNumber:utilityNumber,
            		purposeOfMandate:purposeOfMandate
                }
        
        let temkey ="";
        temkey=makeid();
        let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
        encryptFormData=encryptFormData+":"+temkey;
		sessionStorage.setItem("confirmGUID",encryptFormData);
        window.location.assign('/content/enach-pl/home/confirm-personal-loan-details.html');    


       /*  $.ajax({
            type : "post",
            url : "/content/enach-pl/home/confirm-personal-loan-details.html",
            data :  {X_AUTH_SESSION:sessionUID,
                     X_AUTH_TOKEN:encryptFormData},
			success: function (res) {
			location.href = "/content/enach-pl/home/confirm-personal-loan-details.html";

		},

		error: function (err) {
			console.log(err)
		},
		});


			$.ajax({
				type : "POST",
				/*data : {
					ifsc: confirmBankCode,
					frequency: clickedFreq,
					bankAccountType: bankAccountType,
                    tid:mobNo
				},
				headers: {"X-AUTH-SESSION" : sessionUID,
        			      "X-AUTH-TOKEN" : encryptFormData},

				url : "/bin/billDeskService",
				success : function(data) {


                    let url = decryptMessage(data,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
					//console.log(url);
                    if (url === '')
						window.location.assign('/content/enach-pl/home/registration-pl.html');
                    else

					window.location.assign(url);
				},
				error : function(error) {
					console.log(error);
				}
			});*/
		//});
	}
});

function addCommas(nStr) {
	nStr += '';
	var x = nStr.split('.');
	var x1 = x[0];
	var x2 = x.length > 1 ? '.' + x[1] : '';
	var rgx = /(\d+)(\d{3})/;
	while (rgx.test(x1)) {
		x1 = x1.replace(rgx, '$1' + ',' + '$2');
	}
	return x1 + x2;
}

var a = ['','One ','Two ','Three ','Four ', 'Five ','Six ','Seven ','Eight ','Nine ','Ten ','Eleven ','Twelve ','Thirteen ','Fourteen ','Fifteen ','Sixteen ','Seventeen ','Eighteen ','Nineteen '];
var b = ['', '', 'Twenty','Thirty','Forty','Fifty', 'Sixty','Seventy','Eighty','Ninety'];
function inWords (num) {
    if ((num = num.toString()).length > 9) return 'overflow';
    n = ('000000000' + num).substr(-9).match(/^(\d{2})(\d{2})(\d{2})(\d{1})(\d{2})$/);
    if (!n) return; var str = '';
    str += (n[1] != 0) ? (a[Number(n[1])] || b[n[1][0]] + ' ' + a[n[1][1]]) + 'Crore ' : '';
    str += (n[2] != 0) ? (a[Number(n[2])] || b[n[2][0]] + ' ' + a[n[2][1]]) + 'Lakh ' : '';
    str += (n[3] != 0) ? (a[Number(n[3])] || b[n[3][0]] + ' ' + a[n[3][1]]) + 'Thousand ' : '';
    str += (n[4] != 0) ? (a[Number(n[4])] || b[n[4][0]] + ' ' + a[n[4][1]]) + 'Hundred ' : '';
    str += (n[5] != 0) ? ((str != '') ? 'and ' : '') + (a[Number(n[5])] || b[n[5][0]] + ' ' + a[n[5][1]]) + 'Rupees ' : '';
    return str;
}

var user = "";
var userUnParsed = {};


let details= sessionStorage.getItem("EPLTIDDDS");
details = decryptMessage(details.substring(24),CryptoJS.enc.Utf8.parse(details.substring(0,16)),CryptoJS.enc.Utf8.parse(details.substring(0,16)));
let ms = JSON.parse(details);
mobNo=sessionStorage.getItem("EPLTID");;

var emiString = inWords(ms.EMI_amount);
var instAmountString = inWords(ms.Instructed_amt);

repaymentBankCode=ms.Bank_code;

if(details.length != 0) {

	$('#userName').html(ms.Name);
	$('#aadharCard').html(ms.AadharNo);

	$('#loanNumber').html(ms.Name);
	$('#startDate').html(ms.AadharNo);

	$('#loanNumber').html(ms.loanNumber);
	$('#startDate').html(ms.OriginalDate);

	$('#EMI_amount').html(addCommas(ms.EMI_amount) + " | " + emiString);
	$('#SI_amount').html(addCommas(ms.Instructed_amt + " | " + instAmountString));

	$('#Other_bank_account_number').html(ms.Other_bank_account_number);

	$('#bank').html(ms.Bank_short_name);

}else{
	//window.location.assign('/content/enach-pl/home/registration-pl.html');
}
