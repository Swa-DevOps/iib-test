var user = '';
var userUnParsed;


let sessionCheck = sessionStorage.getItem("EPLSESSIONID");
    if(sessionCheck=="" || sessionCheck == "null" || sessionCheck == null )
    {
        sessionCheck ="";
        // window.location.assign("/content/enach-pl/home/registration-pl.html");
    }


function getBrowserId() {

    var
        aKeys = ["MSIE", "Firefox", "Safari", "Chrome", "Opera"],
        sUsrAg = navigator.userAgent,
        nIdx = aKeys.length - 1;
    for (nIdx; nIdx > -1 && sUsrAg.indexOf(aKeys[nIdx]) === -1; nIdx--);
    return nIdx;
}


$.urlParam = function(name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
    if (results == null) {
        return null;
    } else {
        return decodeURI(results[1]) || 0;
    }
}
var num = getBrowserId();
var txnAmt = '';
var txnRefNo = '';
var bankRefNo = '';

$(document).ready(function() {

	let foo = getUrlVars()["session"];

    if(foo){
	//let key=foo.substring(0,16);
	//let iv=foo.substring(16,32);
    //foo=foo.substring(32);

    let vals= decryptMessage(foo.substring(32),CryptoJS.enc.Utf8.parse(foo.substring(0,16)),CryptoJS.enc.Utf8.parse(foo.substring(16,32)));
	vals=vals.split("|");

	/*let sessiondata = sessionStorage.getItem("confirmGUID");
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
    $("#corporateName").html(datatoFill.corprateName);*/

    $("#userName").html(vals[0]);
    $("#loanNumber").html(vals[12]);
    $("#bankAccountType").html(vals[1]);
    $("#startDate").html(vals[4]);
    $("#EMI_amount").html(vals[5]);
    $("#SI_amount").html(vals[6]);
    $("#Other_bank_account_number").html(vals[2]);
    $("#repaymentBankName").html(vals[3]);
    $("#bankIFSCcode").html(vals[8]);
    $("#freqDropdown").html(vals[7]);
    $("#purposeOfMandate").html(vals[9]);
    $("#utilityNumber").html(vals[11]);
    $("#corporateName").html(vals[10]);
    $("#mandateRef").html(vals[13]);
    }
    else
    {
       sessionStorage.clear();
       window.location.assign("/content/enach-pl/home/registration-pl.html");
    }

});
