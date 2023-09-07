var repaymentBankCode='';
var flag = false;
var bankName;
var mobNo;



$(document).ready(function() {	
$(document).on('click', '#proceedBtn', function (event) {
	let bankCode = $('#bankIFSCcode').text();
	let iAgree = $('#c')[0].checked;
	let session = $('#session').text();
	let iAgreeConfirmed = "true";
    let clickedFreq = $('#freqDropdown').text();
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

    if (iAgree == false ||  iAgreeConfirmed == false)
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

		//let sessionUID = sessionStorage.getItem("EPLSESSIONID");

		let formData = {
                    ifsc: bankCode,
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
		sessionStorage.setItem("cfdplUID",encryptFormData);
		encryptFormData=encryptMessage(session, CryptoJS.enc.Utf8.parse(temkey),CryptoJS.enc.Utf8.parse(temkey));//CryptoJS.AES.encrypt(formData.toString(), sessionID);
        encryptFormData=encryptFormData+":"+temkey;
		sessionStorage.setItem("cfd-ses-ret",encryptFormData);
        let pageurl='/content/enach-pl/home/cfdconfirm-personal-loan-details.html';

       // window.history.pushState({path:pageurl},'',pageurl);
        window.location.assign(pageurl);    

	}
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

var user = "";
var userUnParsed = {};


/*let details= sessionStorage.getItem("EPLTIDDDS");
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

}*/