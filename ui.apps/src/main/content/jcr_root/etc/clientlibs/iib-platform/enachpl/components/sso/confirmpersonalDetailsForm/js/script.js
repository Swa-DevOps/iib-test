var repaymentBankCode='';
var flag = false;
var bankName;
var mobNo;
let sessionTag="";
let defaultRetrunURL="https://www.indusind.com/in/en/personal.html"
let sessionCheck = sessionStorage.getItem("cfd-ses-ret");
    if(sessionCheck=="" || sessionCheck == "null" || sessionCheck == null )
    {
        sessionCheck ="";
        window.location.assign(defaultRetrunURL);
    }else
    {
        let d1= sessionCheck.substring(0,sessionCheck.indexOf(":"));
    	let keyto=sessionCheck.substring(sessionCheck.indexOf(":")+1);
        sessionTag=decryptMessage(d1,CryptoJS.enc.Utf8.parse(keyto),CryptoJS.enc.Utf8.parse(keyto));
    }


$(document).ready(function() {	

	let sessiondata = sessionStorage.getItem("cfdplUID");
  	let d1= sessiondata.substring(0,sessiondata.indexOf(":"));
    let keyto=sessiondata.substring(sessiondata.indexOf(":")+1);
    d1=decryptMessage(d1,CryptoJS.enc.Utf8.parse(keyto),CryptoJS.enc.Utf8.parse(keyto));


    let datatoFill = JSON.parse(d1);

	$("#userName").html(datatoFill.custName);
    $("#loanNumber").html(datatoFill.loanNumber);
    $("#bankAccountType").html(datatoFill.bankAccountType);
    $("#startDate").html(datatoFill.startDate);
    $("#EMI_amount").html(datatoFill.EMI_amount);
    $("#SI_amount").html(datatoFill.SI_amount);
    $("#Other_bank_account_number").html(datatoFill.Other_bank_account_number);
    $("#repaymentBankName").html(datatoFill.repaymentBankName);
    $("#bankIFSCcode").html(datatoFill.ifsc);
    $("#freqDropdown").html(datatoFill.frequency);
    $("#purposeOfMandate").html(datatoFill.purposeOfMandate);
    $("#utilityNumber").html(datatoFill.utilityNumber);
    $("#corporateName").html(datatoFill.corprateName);




});



$(document).on('click', '#cancelBtn', function (event) {

	sessionStorage.clear();
	window.location.assign(defaultRetrunURL);
});


$(document).on('click', '#proceedBtn', function (event) {
	var bankCode = $('#bankIFSCcode').text()
	var clickedFreq = $('#freqDropdown').text();
	var bankAccountType = $('#bankAccountType').text();

		$('.eNACHAlert').html("You will now be leaving the IndusInd Bank Ltd. Website and be taken to the BillDesk portal.");
		$('#enachAlert').show();

		$('.hvr-btn').on('click', function(e){

		let sessionUID = sessionTag;

		let formData = {
                    tid:sessionTag
                }
        
        let temkey ="";
        temkey=makeid();
        let encryptFormData=encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
        encryptFormData=encryptFormData+":"+temkey;
		$.ajax({
				type : "POST",
				headers: {"X-AUTH-SESSION" : sessionUID,
        			      "X-AUTH-TOKEN" : encryptFormData},

				url : "/bin/cfdpl/billDeskService",
				success : function(data) {
                    let url = decryptMessage(data,CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));
                    console.log(url);
                    if (url === '')
						window.location.assign(defaultRetrunURL);
                    else
						window.location.assign(url);
				},
				error : function(error) {
					console.log(error);
				}
			});
		});
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

/*var user = "";
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
	window.location.assign(defaultRetrunURL);
}*/

