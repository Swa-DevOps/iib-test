var arr = [];
var mobileNo = "";
var bankList;
var bytes = "";
var sessionID = "";
var interactID = "";
var customerCode = "";
var isEditEnabled = "0";
var isIFSCIMatched = "1";
var ifsciChecked = "false";
var maxAmtLimit=1000000;

$(document).keydown(function(e) {
    var code = e.keyCode || e.which;
    if (code == 27) $('#alertBox').hide();
    if (code == 9) {
        if (($("#alertBox").css('display')) == "block")
            e.preventDefault();
    }
});

/*window.onbeforeunload = function (evt) {
  var message = 'Are you sure you want to leave?';
  if (typeof evt == 'undefined') {
    evt = window.event;
  }
  if (evt) {
    evt.returnValue = message;
  }
  return message;
}*/


$(document).ready(function() {

    function encryptMessage(message, key, iv) {
        // key=key.substring(0,15);
        // iv=iv.substring(0,15);
        return CryptoJS.AES.encrypt(message, key, {
            iv: iv,
            padding: CryptoJS.pad.Pkcs7,
            mode: CryptoJS.mode.CBC
        });
    }

    function decryptMessage(encryptedMessage, key, iv) {
        // key=key.substring(0,15);
        //    iv=iv.substring(0,15);
        return CryptoJS.AES.decrypt(encryptedMessage, key, {
            iv: iv,
            padding: CryptoJS.pad.Pkcs7,
            mode: CryptoJS.mode.CBC
        }).toString(CryptoJS.enc.Utf8)
    }

    function makeid() {
        var text = "";
        var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (var i = 0; i < 16; i++)
            text += possible.charAt(Math.floor(Math.random() * possible.length));

        return text;
    }



    function eCustomerName() {
        alert("toucher");
    }

    let countin = sessionStorage.getItem("countCreateTime");
    if (countin == "" || countin == "null" || countin == null) {
        sessionStorage.setItem("countCreateTime", makeid());




    } else {
        // window.location.assign("/content/cfdenach/welcome.html");
    }

    sessionID = sessionStorage.getItem("JSESSIONID");
    if (sessionID == "" || sessionID == "null" || sessionID == null) {
        window.location.assign("/content/cfdenach/welcome.html");
    }




    $(document).ready(function() {
            var currentDate = new Date();
            $("#sdate").datepicker('setDate', new Date());
            $('input[name=startDate]').prop("disabled", true);
            sessionID = sessionStorage.getItem("JSESSIONID");
            let bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vmbn1").toString(), sessionID);
            mobileNo = bytes.toString(CryptoJS.enc.Utf8);
            let sessiondata = sessionStorage.getItem("vfndeal");
            let d1 = sessiondata.substring(0, (sessiondata.length - 16));
            let keyto = sessiondata.substring(sessiondata.length - 16);
            d1 = decryptMessage(d1, CryptoJS.enc.Utf8.parse(keyto), CryptoJS.enc.Utf8.parse(keyto));
            let datatoFill = JSON.parse(d1.substring(8))
            let dealNoFor = "";
            if (datatoFill.hasOwnProperty("single")) {

                if (datatoFill.hasOwnProperty("dealsA")) {
                    datatoFill = (JSON.parse(datatoFill.dealsA));
                    /*dealNoFor = datatoFill.Deal_No;
                    $('input[name="accountNo"]').val(datatoFill.Deal_No).prop("readOnly", true);
                    let maxEmi = datatoFill.Max_EMI_Amount;
                    maxEmi = maxEmi * 1;
                    maxEmi = maxEmi.toFixed(0);
                    $('input[name="emi_maximum_amount"]').val(maxEmi).prop("readOnly", true);
                    maxEmi = datatoFill.Max_EMI_Amount * 1.5;
                    maxEmi = maxEmi.toFixed(0);
                    if (maxEmi > maxAmtLimit)
                        maxEmi = maxAmtLimit;
                    $('input[name="maximum_amount"]').val(maxEmi).prop("readOnly", true);
                    let fre = (datatoFill.Frequency).toUpperCase();
                    $('input[name="transferFrequencyText"]').val(fre).prop("readOnly", true);

                    let endDate = datatoFill.Max_EMI_Date;
                    endDate = moment(endDate, "DD-MM-YYYY hh:mm:ss").format("YYYY-MM-DD");
                    $("#edate").datepicker('setDate', endDate);*/
                    datatofill(datatoFill);
                    $("#editMode").hide();
                    $("#woeditSection").hide();
                    $('input[name=customerName]').val("");
                    $('input[name=destinationBank]').val("");
                    $('input[name=destinationBankId]').val("");
                    $('input[name=accountNumber]').val("");
                    $("#editSection").show();
                    isEditEnabled == "1";


                } else {
                    datatoFill = (JSON.parse(datatoFill.single.substring(datatoFill.single.indexOf(":") + 1)));
                  //  console.log(datatoFill.Frequency);
                   /* customerCode = datatoFill.Customer_Code;
                    dealNoFor = datatoFill.Deal_No;
                    $('input[name="accountNo"]').val(datatoFill.Deal_No).prop("readOnly", true);
                    $('input[name="customerName"]').val(datatoFill.Customer_Name).prop("readOnly", true);
                    $('input[name="destinationBank"]').val(datatoFill.Customer_Bank).prop("readOnly", true);
                    $('input[name="accountNumber"]').val(datatoFill.Account_No).prop("readOnly", true);
                    $('input[name="destinationBankId"]').val(datatoFill.IFSC_Code) //.prop("readOnly", true);
                    let maxEmi = datatoFill.Max_EMI_Amount;
                    maxEmi = maxEmi * 1;
                    maxEmi = maxEmi.toFixed(0);
                    $('input[name="emi_maximum_amount"]').val(maxEmi).prop("readOnly", true);
                    maxEmi = datatoFill.Max_EMI_Amount * 1.5;
                    maxEmi = maxEmi.toFixed(0);
                    if (maxEmi > maxAmtLimit)
                        maxEmi = maxAmtLimit;
                    $('input[name="maximum_amount"]').val(maxEmi).prop("readOnly", true);
                    let fre = (datatoFill.Frequency).toUpperCase();
                    $('input[name="transferFrequencyText"]').val(fre).prop("readOnly", true);

                    let endDate = datatoFill.Max_EMI_Date;
                    endDate = moment(endDate, "DD-MM-YYYY hh:mm:ss").format("YYYY-MM-DD");
                    $("#edate").datepicker('setDate', endDate);*/
                    datatofill(datatoFill);

                }
            } else {

                if (datatoFill.hasOwnProperty("dealresultA")) {
                    datatoFill = (JSON.parse(datatoFill.dealresultA));
                   /* dealNoFor = datatoFill.Customer_Dt[0].Deal_No;
                    $('input[name="accountNo"]').val(datatoFill.Customer_Dt[0].Deal_No).prop("readOnly", true);
                    let maxEmi = datatoFill.Customer_Dt[0].Max_EMI_Amount;
                    maxEmi = maxEmi * 1;
                    maxEmi = maxEmi.toFixed(0);
                    $('input[name="emi_maximum_amount"]').val(maxEmi).prop("readOnly", true);
                    maxEmi = datatoFill.Customer_Dt[0].Max_EMI_Amount * 1.5;
                    maxEmi = maxEmi.toFixed(0);
                    if (maxEmi > maxAmtLimit)
                        maxEmi = maxAmtLimit;
                    $('input[name="maximum_amount"]').val(maxEmi).prop("readOnly", true);
                    let fre = (datatoFill.Customer_Dt[0].Frequency).toUpperCase();
                    $('input[name="transferFrequencyText"]').val(fre).prop("readOnly", true);
                    let endDate = datatoFill.Customer_Dt[0].Max_EMI_Date;
                    endDate = moment(endDate, "DD-MM-YYYY hh:mm:ss").format("YYYY-MM-DD");
                    $("#edate").datepicker('setDate', endDate);*/
                    datatofill(datatoFill.Customer_Dt[0]);
                    $("#editMode").hide();
                    $("#woeditSection").hide();
                    $('input[name=customerName]').val("");
                    $('input[name=destinationBank]').val("");
                    $('input[name=destinationBankId]').val("");
                    $('input[name=accountNumber]').val("");
                    $("#editSection").show();
                    isEditEnabled = "1";



                } else {

                    datatoFill = (JSON.parse(datatoFill.dealresult));
                    //  console.log(datatoFill.Customer_Dt[0].Frequency);
                    datatofill(datatoFill.Customer_Dt[0]);
                  /*  customerCode = datatoFill.Customer_Dt[0].Customer_Code;
                    dealNoFor = datatoFill.Customer_Dt[0].Deal_No;
                    $('input[name="accountNo"]').val(datatoFill.Customer_Dt[0].Deal_No).prop("readOnly", true);
                    $('input[name="customerName"]').val(datatoFill.Customer_Dt[0].Customer_Name).prop("readOnly", true);
                    $('input[name="destinationBank"]').val(datatoFill.Customer_Dt[0].Customer_Bank).prop("readOnly", true);
                    $('input[name="accountNumber"]').val(datatoFill.Customer_Dt[0].Account_No).prop("readOnly", true);
                    $('input[name="destinationBankId"]').val(datatoFill.Customer_Dt[0].IFSC_Code) //.prop("readOnly", true);
                    let maxEmi = datatoFill.Customer_Dt[0].Max_EMI_Amount;
                    maxEmi = maxEmi * 1;
                    maxEmi = maxEmi.toFixed(0);
                    $('input[name="emi_maximum_amount"]').val(maxEmi).prop("readOnly", true);
                    maxEmi = datatoFill.Customer_Dt[0].Max_EMI_Amount * 1.5;
                    maxEmi = maxEmi.toFixed(0);
                    if (maxEmi > maxAmtLimit)
                        maxEmi = maxAmtLimit;
                    $('input[name="maximum_amount"]').val(maxEmi).prop("readOnly", true);
                    let fre = (datatoFill.Customer_Dt[0].Frequency).toUpperCase();
                    $('input[name="transferFrequencyText"]').val(fre).prop("readOnly", true);
                    let endDate = datatoFill.Customer_Dt[0].Max_EMI_Date;
                    endDate = moment(endDate, "DD-MM-YYYY hh:mm:ss").format("YYYY-MM-DD");
                    $("#edate").datepicker('setDate', endDate);*/
                }
            }



            let formData = {
                mobileNo: mobileNo,
                dealno: dealNoFor,
                session:sessionID,
                action: "createform"
            }

            let temkey = makeid();

            let encryptFormData = encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey)); //CryptoJS.AES.encrypt(formData.toString(), sessionID);

            $.ajax({
                    url: "/bin/cfd/digioCreateMandate",
                    type: "POST",
                    //data: formData,
                    headers: {
                        "X-AUTH-TOKEN": encryptFormData,
                        "X-AUTH-SESSION": temkey
                    },
                    success: function(res) {

                        console.log();//To do any action

                },
                error: function(e) {
                    //To do any action

                }
            });



    });


    function datatofill(datatofill)  {

					customerCode = datatofill.Customer_Code;
                    dealNoFor = datatofill.Deal_No;
                    $('input[name="accountNo"]').val(datatofill.Deal_No).prop("readOnly", true);
                    $('input[name="customerName"]').val(datatofill.Customer_Name).prop("readOnly", true);
                    $('input[name="destinationBank"]').val(datatofill.Customer_Bank).prop("readOnly", true);
                    $('input[name="accountNumber"]').val(datatofill.Account_No).prop("readOnly", true);
                    $('input[name="destinationBankId"]').val(datatofill.IFSC_Code) //.prop("readOnly", true);
                    let maxEmi = datatofill.Max_EMI_Amount;
                    maxEmi = maxEmi * 1;
                    maxEmi = maxEmi.toFixed(0);
                    $('input[name="emi_maximum_amount"]').val(maxEmi).prop("readOnly", true);
                    maxEmi = datatofill.Max_EMI_Amount * 1.5;
                    maxEmi = maxEmi.toFixed(0);
                    if (maxEmi > maxAmtLimit)
                        maxEmi = maxAmtLimit;
                    $('input[name="maximum_amount"]').val(maxEmi).prop("readOnly", true);
                    let fre = (datatofill.Frequency).toUpperCase();
                    $('input[name="transferFrequencyText"]').val(fre).prop("readOnly", true);
                    let endDate = datatofill.Max_EMI_Date;
                    endDate = moment(endDate, "DD-MM-YYYY hh:mm:ss").format("YYYY-MM-DD");
                    $("#edate").datepicker('setDate', endDate);
    }


function getDate(element) {
    var date;
    try {
        date = $.datepicker.parseDate("yy-mm-dd", element.value);
    } catch (error) {
        date = null;
    }

    return date;
}

var $startDate = $('#sdate'),
    $endDate = $('#edate');

$startDate.datepicker({
    dateFormat: "yy-mm-dd",
    yearRange: "c-150:c+150",
    changeYear: true,
    changeMonth: true,
    minDate: moment(moment(), "YYYY-MM-DD").add(0, 'days').format('YYYY-MM-DD'),
    maxDate: moment(moment(), "YYYY-MM-DD").add(90, 'days').format('YYYY-MM-DD')
}).on("change", function() {
    var g1 = new Date($('input[name=startDate]').val());
    var g2 = new Date(moment(moment(), "YYYY-MM-DD").add(6, 'days').format('YYYY-MM-DD'));
    if (g1.getTime() > g2.getTime()) {
        let status = $('#ucancel').is(':checked');
        var endDate = moment(moment(getDate(this)), "YYYY-MM-DD").add(10, 'years').subtract(1, 'days').format('YYYY-MM-DD');
        //endDate = moment(moment(endDate), "YYYY-MM-DD").subract(1, 'day').format('YYYY-MM-DD');
        $endDate.datepicker("option", "maxDate", endDate);
        updateDates($('.transferFrequency').val(), $(this).val());
        if (!status) {
            $endDate.datepicker("setDate", endDate);
        }
    } else {
        $('#alertMessage').html("Start Date cannot be prior to Today's Date !!");
        $('#alertBox').show();
        $startDate.datepicker("setDate", moment(moment(), "YYYY-MM-DD").add(7, 'days').format('YYYY-MM-DD'));
    }

}); $endDate.datepicker({
    dateFormat: "yy-mm-dd",
    yearRange: "c-150:c+150",
    changeYear: true,
    changeMonth: true,
    minDate: moment(moment(), "YYYY-MM-DD").add(2, 'months').add(7, 'days').format('YYYY-MM-DD'),
    maxDate: moment(moment(), "YYYY-MM-DD").add(3651, 'days').add(7, 'days').format('YYYY-MM-DD')
}).on("change", function() {
    let endDate1 = $('input[name=endDate]').val();
    if (endDate1 != "") {
        var g1 = new Date($('input[name=startDate]').val());
        // (YYYY-MM-DD)
        var g2 = new Date($('input[name=endDate]').val());
        if (g1.getTime() > g2.getTime()) {
            $('#alertMessage').html("End Should be Greater than Start Date !!");
            $('#alertBox').show();

            var endDate = moment(moment(getDate(this)), "YYYY-MM-DD").add(1, 'months').add(1, 'days').format('YYYY-MM-DD');
            $endDate.datepicker("setDate", endDate);
        }

    }



});

document.getElementById("createform").reset(); $('input[name=maximum_amount]').val("10000"); $('select option:contains("Monthly")').prop('selected', true);
var input = document.getElementById("awesomeHu");


var awesomplete = new Awesomplete(input, {
    minChars: 2,
    maxItems: 10, //how many items are needed to be displayed
    autoFirst: false
});

$.ajax({
    url: "/bin/bankList",
    type: "POST",
    data: null,
    success: function(res) {
        var response = JSON.parse(res);
        bankList = response;
        //console.log(response);
        awesomplete.list = response.bankList
    },
    error: function(e) {
        console.log("Error in Bank List :: " + e);
    }
});

var input = $('input[name=destinationBank]');

//Character Validation
function characterValidation(arg) {
    $(arg).on('paste', function(e) {
        e.preventDefault();
    });

    $(arg).keypress(function(e) {
        var regex = new RegExp(/^[a-zA-Z\s]+$/);
        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
            return true;
        } else {
            e.preventDefault();
            return false;
        }
    });
}

//Character Validation
function characterValidationName(arg) {
    $(arg).on('paste', function(e) {
        e.preventDefault();
    });

    $(arg).keypress(function(e) {
        var regex = new RegExp(/^[a-zA-Z\s'.-]+$/);
        var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
        if (regex.test(str)) {
            return true;
        } else {
            e.preventDefault();
            return false;
        }
    });
}


characterValidationName('.customerName'); characterValidation('.bankName');

//Numeric Validation
function numericValidation(arg) {
    $(arg).on('paste', function(e) {
        e.preventDefault();
    });

    $(arg).keydown(function(e) {
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110]) !== -1 ||
            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) ||
            (e.keyCode >= 35 && e.keyCode <= 40)) {
            return;
        }
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
}


function alphanumericValidation(argAN) {
    $(argAN).on('paste', function(e) {
        e.preventDefault();
    });

    $(argAN).keydown(function(e) {
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110]) !== -1 ||
            (e.keyCode === 65 && (e.ctrlKey === true || e.metaKey === true)) ||
            (e.keyCode >= 35 && e.keyCode <= 40)) {
            return;
        }
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 90)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });

}
alphanumericValidation('.accountNumber'); alphanumericValidation('.confirmAccountNo'); numericValidation('.maximum_amount'); numericValidation('.aadharId');

var accountArrays = [];
var resFlag = "false";

//Niket  bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vfn2").toString(), sessionID);
//niket  resFlag  = bytes.toString(CryptoJS.enc.Utf8);

/*if (document.cookie.length != 0) {
	var namevaluepair = document.cookie.split("; ");
	for (var i = 0; i < namevaluepair.length; i++) {
		var namevaluearray = namevaluepair[i].split("=");
		if (namevaluearray[0] == "flag") {
			resFlag = namevaluearray[1];
		}
	}
}*/

//	alert(resFlag);
if (resFlag.toString() === "true") {
    //niket bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vmbn1").toString(), sessionID);
    var userMobNo = ""; //niket bytes.toString(CryptoJS.enc.Utf8);
    var dob = "" //niket bytes.toString(CryptoJS.enc.Utf8);
    mobileNo = userMobNo;

    let res = sessionStorage.getItem("vfndob");
    sessionStorage.setItem("vfndob", "");
    let temkey = res.substring(res.length - 16);
    res = res.substring(0, res.length - 16);
    if ((res != "")) {
        res = decryptMessage(res, CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey));
        var response = JSON.parse(res);
        //console.log(response);
        for (var i = 0; i < response.length; i++) {
            accountArrays.push(response[i].Account_Number);
            var myvalue = "XXXX-XXXX-" + response[i].Account_Number.substring(8, 13) + " ( " + response[i].AccountType + " )";
            $('.accountNo').append('<option id=' + response[i].Banking_Cif_Id + '##' + response[i].Account_Number + '>' + myvalue + '</option>')
        }
    } else {
      //  console.log("Session expired or unauthorized access");
        //window.location.assign("/content/enach/welcome.html");
    }

} else {
    //niket   bytes = CryptoJS.AES.decrypt(sessionStorage.getItem("vmbn1").toString(), sessionID);
    var userMobNo = ""; //niket bytes.toString(CryptoJS.enc.Utf8)
    mobileNo = userMobNo;
    let formData = {
        mobile: userMobNo,
    }
    let res = ""; //niket sessionStorage.getItem("vfndob");
    //niket sessionStorage.setItem("vfndob","");
    let temkey = ""; //niket res.substring(res.length-16);
    res = ""; //niket res.substring(0,res.length-16);
    if ((res != "")) {
        res = decryptMessage(res, CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey));
        res = res.substring(8);
        let response1 = JSON.parse(res);
        let test = response1.accountsFromDOB;
        //console.log(test);
        //console.log(test.replace(/\//g, '') );

        var response = JSON.parse(response1.accountsFromDOB);
        //console.log(response);
        for (var i = 0; i < response.length; i++) {
            accountArrays.push(response[i].Account_Number);
            var myvalue = "XXXX-XXXX-" + response[i].Account_Number.substring(8, 13) + " ( " + response[i].AccountType + " )";
            $('.accountNo').append('<option id=' + response[i].Banking_Cif_Id + '##' + response[i].Account_Number + '>' + myvalue + '</option>')
        }
    } else {
        console.log("Session expired or unauthorized access");
        //window.location.assign("/content/enach/welcome.html");
    }

}
var updateDates = function(transferFrequency, startDate) {
    if (!startDate) {
        startDate = moment(moment(), "YYYY-MM-DD").add(7, 'days').format('YYYY-MM-DD');
    }
    if (transferFrequency === "Weekly") {
        $endDate.datepicker("option", "minDate", moment(startDate, "YYYY-MM-DD").add(14, 'days').format('YYYY-MM-DD'));
    } else if (transferFrequency === "Monthly") {
        $endDate.datepicker("option", "minDate", moment(startDate, "YYYY-MM-DD").add(2, 'months').format('YYYY-MM-DD'));
    } else if (transferFrequency === "Yearly") {
        $endDate.datepicker("option", "minDate", moment(startDate, "YYYY-MM-DD").add(1, 'years').format('YYYY-MM-DD'));
    }
}

$('.cmp-date-time-picker').click(function() {
    var maximum_amount = parseFloat($('input[name=maximum_amount]').val());
    var startDate = $('input[name=startDate]').val();

    caluclateEndDate(startDate);
    var endDate = $('input[name=endDate]').val();
    var transferFrequency = $('.transferFrequency').val();

    if (transferFrequency == "Adhoc") {
        $('input[name=endDate]').prop("disabled", true);
        endDate = startDate;
    } else {
        $('input[name=endDate]').prop("disabled", false);
    }


    if (maximum_amount && startDate && endDate) {
        calcTotalAmountAndInvestments();
    }
});


$('#editA').click(function() {

    $("#editMode").hide();
    $("#woeditSection").hide();
    $('input[name=customerName]').val("");
    $('input[name=destinationBank]').val("");
    $('input[name=destinationBankId]').val("");
    $('input[name=accountNumber]').val("");
    $("#editSection").show();
    isEditEnabled = "1";

});


$('#ucancel').click(function() {
    var untilCancelStstus = $('#ucancel').is(':checked');

    if ($('#ucancel').is(':checked')) {
        $('#eDate').hide();
    } else {
        $('#eDate').show();
        $('input[name=endDate]').prop("disabled", true);
    }
});


$('input[name=edestinationBank]').on('keyup', function(e) {

    $('input[name=edestinationBankId]').val("");
});

$('.transferFrequency, .maximum_amount').change(function() {



    var maximum_amount = parseFloat($('input[name=maximum_amount]').val());
    var startDate = $('input[name=startDate]').val();


    var endDate = $('input[name=endDate]').val();

    var transferFrequency = $('.transferFrequency').val();

    if (transferFrequency == "Adhoc") {
        $('input[name=endDate]').prop("disabled", true);
        endDate = startDate;
    } else {
        $('input[name=endDate]').prop("disabled", false);
    }
    updateDates(transferFrequency, startDate);

    if (maximum_amount && startDate && endDate) {
        calcTotalAmountAndInvestments();
    }
});


/**** Edit Sectioc Script*/


$('input[name=eaccountNumber]').focusout(function() {
    var accountNumber = $('input[name=eaccountNumber]').val();


    alphanumericValidation('.eaccountNumber');


});

//For Mobile Authentication and Validation
$('#ecustomerName').on('keyup', function(e) {

    //for android chrome keycode fix
    if (navigator.userAgent.match(/Android/i)) {

        var inputValue = this.value;
        var regex = new RegExp("^[a-zA-Z \s]+$");
        if (!regex.test(inputValue)) {

            this.value = inputValue.substring(0, inputValue.length - 1);

        }


    }
});


$('#eaccountNumber').on('keyup', function(e) {

    //for android chrome keycode fix
    if (navigator.userAgent.match(/Android/i)) {

        var inputValue = this.value;
        var regex = new RegExp("^[a-zA-Z0-9]+$");
        if (!regex.test(inputValue)) {


            this.value = inputValue.substring(0, inputValue.length - 1);

        } else if (inputValue.length > 18) {
            this.value = inputValue.substring(0, inputValue.length - 1);
        }


    }
});

//For Mobile Authentication and Validation end


$('input[name=ecustomerName]').keypress(function(e) {
    var regex = new RegExp("^[a-zA-Z \s]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    } else {
        e.preventDefault();
        return false;
    }
});

$('input[name=customerName]').keypress(function(e) {
    var regex = new RegExp("^[a-zA-Z \s]+$");
    var str = String.fromCharCode(!e.charCode ? e.which : e.charCode);
    if (regex.test(str)) {
        return true;
    } else {
        e.preventDefault();
        return false;
    }
});


$('input[name=ecustomerName]').focusout(function() {
    var customerName = $('input[name=ecustomerName]').val();

    characterValidationName('.ecustomerName');

});

$('input[name=edestinationBankId]').on('keyup', function(e) {

    if (navigator.userAgent.match(/Android/i)) {

        var inputValue = this.value;
        var regex = new RegExp("^[a-zA-Z0-9]+$");
        if (!regex.test(inputValue)) {


            this.value = inputValue.substring(0, inputValue.length - 1);

        } else if (inputValue.length > 11) {
            this.value = inputValue.substring(0, inputValue.length - 1);

        }


    }

    var lengthval = $('input[name=edestinationBankId]').val();
    if (((lengthval.length == 11) || (e.keyCode == 9)) && (!(e.keyCode == 27))) {
        var destinationBankId = $('input[name=edestinationBankId]').val();
        var destinationBank = $('input[name=edestinationBank]').val();
        // var reg = new RegExp('/[A-Z|a-z]{4}[0][\d]{6}$/');
        var reg = new RegExp('/[A-Z|a-z]{4}[0-9]{7}$/');
        // alert(destinationBankId + reg.test(destinationBankId) + (destinationBankId.match(/[A-Z|a-z]{4}[0][0-9]{6}$/)) + destinationBankId.length  );

        if (destinationBankId.length == 11 && destinationBankId == (destinationBankId.match(/[A-Z|a-z]{4}[0][0-9]{6}$/))) {


            let formData = {
                ifsc: destinationBankId,
                repaymentBankName: destinationBank,
                appname: "CFDENACH"
            }

            let temkey = "";
            temkey = makeid();
            let encryptFormData = encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey)); //CryptoJS.AES.encrypt(formData.toString(), sessionID);
            encryptFormData = encryptFormData + ":" + temkey;



            $.ajax({
                type: "POST",
                url: "/bin/ifscVerify",
                /*data : {
                	ifsc : confirmIfsc,
                	repaymentBankName : bankName
                },*/
                headers: {
                    "X-AUTH-SESSION": sessionID,
                    "X-AUTH-TOKEN": encryptFormData
                },

                success: function(data) {
                    let v1 = decryptMessage(data, CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey));
                    var res = JSON.parse(v1);
                    //console.log(res);
                    if (res.success == 'true') {
                        //to do
                        isIFSCIMatched = "1";
                        ifsciChecked = "true";
                    } else {
                        isIFSCIMatched = "0";
                        ifsciChecked = "false";
                        e.preventDefault();
                        $('#alertBox').show();
                        $('#alertMessage').html("Please enter a valid IFSC code");
                        //  $('input[name=edestinationBankId]').focus();

                    }
                },
                error: function(error) {
                    console.log("Something really bad happened " + error);
                }
            });


        } else {

            if ((e.keycode == 8) || (e.keycode == 27)) {} else {
                isIFSCIMatched = "0";
                ifsciChecked = "false";
                e.preventDefault();
                $('#alertBox').show();
                $('#alertMessage').html("Please enter a valid IFSC code");
                //  $('input[name=edestinationBankId]').focus();

            }
        }
    }

});


$('input[name=edestinationBank]').focusout(function() {
    var destinationBank = $('input[name=edestinationBank]').val()
    var bankString = bankList.bankList;
    isEditEnabled = "1";
    if (!bankString.includes(destinationBank)) {
        $('#alertBox').show();
        $('#alertMessage').html("Please select Valid Bank from List");
    }
    /* var destinationBankId = $('input[name=edestinationBankId]').val();
     if(destinationBankId.length == 11){
     let result = validateIFSCICode(destinationBank,destinationBankId);
     if (result=="false") {
         ifsciChecked = "false";
         isIFSCIMatched="0";
     }else
     {
       ifsciChecked = "true";
       isIFSCIMatched="1";
     }
     }else {
     ifsciChecked = "false";
     isIFSCIMatched="0";
     }*/

});



/****End Edit Sction Script*/

$('#openRD').change(function() {
    var transferFrequency = $('.transferFrequency').val();
    var startDate = $('input[name=startDate]').val();
    var endDate = $('input[name=endDate]').val();
    var rdAmount = $('input[name=maximum_amount]').val();

    $('.rdTransferFrequency').html(transferFrequency);
    $('.rdAmount').html(rdAmount ? rdAmount : "NA");
    $('.rdStartDate').html(startDate ? startDate : "NA");
    $('.rdEndDate').html(endDate ? endDate : "NA");
    $(".recurringWrapper").toggle();
});

$('input[name=maximum_amount]').focusout(function() {
    var amount = $('input[name=maximum_amount]').val();
    if (amount != "") {
        if (amount < 1 || amount > 100000) {
            $('#alertBox').show();
            $('#alertMessage').html("Amount to transfer should be between 1000 to 100000");
        }
    }
});




$('input[name=destinationBankId]').on('keyup', function(e) {

    if (navigator.userAgent.match(/Android/i)) {

        var inputValue = this.value;
        var regex = new RegExp("^[a-zA-Z0-9]+$");
        if (!regex.test(inputValue)) {


            this.value = inputValue.substring(0, inputValue.length - 1);

        } else if (inputValue.length > 11) {
            this.value = inputValue.substring(0, inputValue.length - 1);

        }


    }
    var lengthval = $('input[name=destinationBankId]').val();
    if (((lengthval.length == 11) || (e.keyCode == 9)) && (!(e.keyCode == 27))) {



        var destinationBankId = $('input[name=destinationBankId]').val();
        var destinationBank = $('input[name=destinationBank]').val();
        var reg = new RegExp('/[A-Z|a-z]{4}[0-9]{7}$/');
        if (destinationBankId.length == 11 && destinationBankId == (destinationBankId.match(/[A-Z|a-z]{4}[0][0-9]{6}$/))) {

            let formData = {
                ifsc: destinationBankId,
                repaymentBankName: destinationBank,
                appname: "CFDENACH"
            }

            let temkey = "";
            temkey = makeid();
            let encryptFormData = encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey)); //CryptoJS.AES.encrypt(formData.toString(), sessionID);
            encryptFormData = encryptFormData + ":" + temkey;



            $.ajax({
                type: "POST",
                url: "/bin/ifscVerify",
                /*data : {
                	ifsc : confirmIfsc,
                	repaymentBankName : bankName
                },*/
                headers: {
                    "X-AUTH-SESSION": sessionID,
                    "X-AUTH-TOKEN": encryptFormData
                },

                success: function(data) {
                    let v1 = decryptMessage(data, CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey));
                    var res = JSON.parse(v1);
                    //console.log(res);
                    if (res.success == 'true') {
                        isIFSCIMatched = "1";
                        ifsciChecked = "true";
                        //to do
                    } else {
                        isIFSCIMatched = "0";
                        ifsciChecked = "false";
                        e.preventDefault();
                        $('#alertBox').show();
                        $('#alertMessage').html("Please enter a valid IFSC code");
                        //  $('input[name=destinationBankId]').focus();

                    }
                },
                error: function(error) {
                    console.log("Something really bad happened " + error);
                }
            });


        } else {
            if ((e.keycode == 8) || (e.keycode == 27)) {} else {
                isIFSCIMatched = "0";
                ifsciChecked = "false";
                e.preventDefault();
                $('#alertBox').show();
                $('#alertMessage').html("Please enter a valid IFSC code");
                //$('input[name=destinationBankId]').focus();

            }
        }
    }

});



$('input[name=destinationBank]').focusout(function() {
    var destinationBank = $('input[name=destinationBank]').val()
    var bankString = bankList.bankList;
    if (!bankString.includes(destinationBank)) {
        $('#alertBox').show();
        $('#alertMessage').html("Please select Valid Bank from List");
    }
    /*  var destinationBankId = $('input[name=destinationBankId]').val();
      if(destinationBankId.length == 11){
      let result = validateIFSCICode(destinationBank,destinationBankId);
      if (result=="false") {
          ifsciChecked = "false";
          isIFSCIMatched="0";
      }else
      {
        ifsciChecked = "true";
        isIFSCIMatched="1";
      }
      }else {
      ifsciChecked = "false";
      isIFSCIMatched="0";
      }*/

});

$('input[name=customerEmail]').focusout(function() {
    var customerEmail = $('input[name=customerEmail]').val();
    // var re = new RegExp(/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/);
    var re = new RegExp(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/);
    //	alert(re.test(customerEmail) + "----" + customerEmail );
    if (!re.test(customerEmail)) {
        // e.preventDefault();
        $('#alertBox').show();
        //$('#alertMessage').html("Please enter correct Registered E-mail ID");
        $('#alertMessage').html("Please enter valid E-mail ID");
        //$('input[name=customerEmail]').setFocus();
    }

});

$('input[name=customerAccountNo]').focusout(function() {
    var customerAccountNo = $('input[name=customerAccountNo]').val();
    var accountNumber = $('input[name=accountNumber]').val();

    if (accountNumber != customerAccountNo) {
        $('#alertBox').show();
        $('#alertMessage').html("Account numbers did not match, Please re-enter the account numbers.");
        //$('input[name=customerAccountNo]').setFocus();
    }

});


$('input[name=customerAccountNo]').on('keydown', function(e) {
    if (e.keyCode == 9) {

        var customerAccountNo = $('input[name=customerAccountNo]').val();
        var accountNumber = $('input[name=accountNumber]').val();

        if (accountNumber != customerAccountNo) {
            e.preventDefault();
            $('#alertBox').show();
            $('#alertMessage').html("Account numbers did not match, Please re-enter the account numbers.");

        } else {
            // $('input[name=destinationBankId]').setFocus();
        }
    }
});


$('input[name=aadharId]').focusout(function() {
    var aadhar = $('input[name=aadharId]').val();
    if (aadhar != "") {
        if (aadhar.length < 12 || aadhar.length > 16) {
            $('#alertBox').show();
            $('#alertMessage').html("Aadhaar ID should be 12 to 16 characters");
        }
    }
});

$('.confirmbutton').click(function() {
    var logoURL = "http://bank.indusind.com/content/dam/iibPlatform/cfdenach/images/logo.jpg";
    var options = {
        // "environment":"production",
        "callback": function(t) {

            let temkey = makeid(); //sessionID.replace(/-/g, "");
            if (t.hasOwnProperty('error_code')) {

                var formData = {
                    mandateId: t.digio_doc_id,
                    error_code: t.error_code,
                    message: t.message,
                    step: "duringdigoIssue",
                    action: "statusupdate"
                }
                let encryptFormData = encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey)); //CryptoJS.AES.encrypt(formData.toString(), sessionID);

                $.ajax({
                    url: "/bin/cfd/digioCreateMandate",
                    type: "POST",
                    headers: {
                        "X-AUTH-TOKEN": encryptFormData,
                        "X-AUTH-SESSION": temkey
                    },
                    success: function(res) {

                        window.location.assign("/content/cfdenach/welcome/login/fail.html?failureMessage=" + t.message); //+ "&interactionId=" + interactID);

                    },
                    error: function(e) {
                        //console.log("Error in submitte Digio status:: " + e);
                    }
                });




            } else {

                var formData = {
                    mandateId: t.digio_doc_id,
                    error_code: t.txn_id,
                    message: t.message,
                    step: "digiojourmeycomplete",
                    action: "statusupdate"
                }
                let encryptFormData = encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey)); //CryptoJS.AES.encrypt(formData.toString(), sessionID);

                $.ajax({
                    url: "/bin/cfd/digioCreateMandate",
                    type: "POST",
                    headers: {
                        "X-AUTH-TOKEN": encryptFormData,
                        "X-AUTH-SESSION": temkey
                    },
                    success: function(res) {

                        window.location.assign("/content/cfdenach/welcome/login/success.html?documentId=" + t.digio_doc_id); //+ "&interactionId=" + interactID);
                    },
                    error: function(e) {
                        //console.log("Error in submitte Digio status:: " + e);
                    }
                });



            }
        },


        "logo": encodeURIComponent(logoURL),
    };

    var customerNameLabel = $('.customerNameLabel').html().split("<")[0];
    var bankNameLabel = $('.bankNameLabel').html().split("<")[0];
    var accountNumberLabel = $('.accountNumberLabel').html().split("<")[0];
    var confirmAccountLabel = $('.accountNumberLabel').html().split("<")[0];
    var ifscLabel = $('.ifscLabel').html().split("<")[0];
    var maximumAmountLabel = $('.maximumAmountLabel').html().split("<")[0];
    var transferFreqLabel = $('.transferFreqLabel').html().split("<")[0];
    var startDateLabel = $('.startDateLabel').html().split("<")[0];
    var endDateLabel = $('.endDateLabel').html().split("<")[0];
    var totalAmountLabel = $('.totalAmountLabel').html().split("<")[0];
    var totalInstallmentsLabel = $('.totalInstallmentsLabel').html().split("<")[0];
    var aadharLabel = "aadhar";
    var referralCodeLabel = $('.referralCodeLabel').html().split("<")[0];
    var creditAccountLabel = $('.creditAccountLabel').html().split("<")[0];
    var customerEmailLabel = $('.customerEmailLabel').html().split("<")[0];
    var customerName = $('input[name=customerName]').val() ? $('input[name=customerName]').val() : $('input[name=ecustomerName]').val();
    var customerEmail = $('input[name=customerEmail]').val() ? $('input[name=customerEmail]').val() : "";
    var destinationBank = $('input[name=destinationBank]').val() ? $('input[name=destinationBank]').val() : $('input[name=edestinationBank]').val();
    var destinationBankId = $('input[name=destinationBankId]').val() ? $('input[name=destinationBankId]').val() : $('input[name=edestinationBankId]').val();
    var customerAccountNo = $('input[name=accountNumber]').val() ? $('input[name=accountNumber]').val() : $('input[name=eaccountNumber]').val();
    var confirmAccountNo = $('input[name=accountNumber]').val() ? $('input[name=accountNumber]').val() : $('input[name=eaccountNumber]').val();
    var maximum_amount = $('input[name=maximum_amount]').val();
    var emi_amount = $('input[name=emi_maximum_amount]').val();

    var transferFrequency = $('input[name=transferFrequencyText]').val(); //$('.transferFrequency').val();transferFrequencyText
    var startDate = $('input[name=startDate]').val();
    var endDate = "";
    var appid = "direct";
    if ($('#ucancel').is(':checked'))
        endDate = "";
    else
        endDate = $('input[name=endDate]').val();

    var totalAmount = $('.totalAmount').val();
    var totalInstallments = $('.totalInstallments').val();
    var aadharId = "1";
    var aadharNo = "";
    var accLastDigits = $('.accountNo').val().substr(10, 14);
    var cifid = $('.accountNo').children(":selected").attr("id");
    var referralCode = $('input[name=referralCode]').val();
    var accountNo = "";
    for (let i = 0; i < accountArrays.length; i++) {
        let n = accountArrays[i].includes(accLastDigits);
        if (n == true) {
            accountNo = accountArrays[i];
            break;
        }
    }
    accountNo = $('.accountNo').val();
    cifid = customerCode;
    var formData = {
        destinationBank: destinationBank,
        destinationBankId: destinationBankId,
        customerAccountNo: confirmAccountNo,
        maximum_amount: maximum_amount,
        mobileNo: mobileNo,
        customerName: customerName, // To pick from API/Cookie
        customerEmail: customerEmail,
        transferFrequency: transferFrequency,
        startDate: startDate,
        endDate: endDate,
        accountNo: accountNo,
        confirmAccountNo: confirmAccountNo,
        totalAmount: totalAmount,
        totalInstallments: totalInstallments,
        referralCode: referralCode,
        aadharNo: aadharNo,
        customerNameLabel: customerNameLabel,
        bankNameLabel: bankNameLabel,
        accountNumberLabel: accountNumberLabel,
        confirmAccountLabel: confirmAccountLabel,
        ifscLabel: ifscLabel,
        maximumAmountLabel: maximumAmountLabel,
        transferFreqLabel: transferFreqLabel,
        startDateLabel: startDateLabel,
        endDateLabel: endDateLabel,
        totalAmountLabel: totalAmountLabel,
        totalInstallmentsLabel: totalInstallmentsLabel,
        aadharLabel: aadharLabel,
        referralCodeLabel: referralCodeLabel,
        creditAccountLabel: creditAccountLabel,
        customerEmailLabel: customerEmailLabel,
        cifid: cifid,
        emi_amount: emi_amount,
        step: "digioclicked",
        appid: "direct",
        action: "submitclicked"

    }

    /*var accountNumber = $('input[name=accountNumber]').val();
    var confirmAccountNumber = $('input[name=customerAccountNo]').val();
    var confirmAccountNumberLength = $('input[name=customerAccountNo]').val().length;
    var accountNumberLength = $('input[name=accountNumber]').val().length;*/
    var bankStringnew = bankList.bankList;
    let emailValid = "1";



    //debugger;

    if (!(customerEmail == "")) {
        emailValid = "0";
        //var re = new RegExp(/^[a-zA-Z0-9.!#$%&’*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/);
        var re = new RegExp(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/);
        if (!re.test(customerEmail)) {
            emailValid = "0";

        } else {
            emailValid = "1";
        }
    }

    if (emailValid == "0") {
        $('#alertBox').show();
        $('#alertMessage').html("Please enter a correct email id");
    } else if (customerName == "") {
        $('#alertBox').show();
        $('#alertMessage').html("Please enter a valid Customer Name");
    } else if ((confirmAccountNo == "") || (confirmAccountNo.length < 8) || (confirmAccountNo.lenght > 18)) {
        $('#alertBox').show();
        $('#alertMessage').html("Please enter a valid Account Number");
    } else if ((destinationBank == "") || (((isEditEnabled == "1") && (!bankStringnew.includes(destinationBank))))) {
        $('#alertBox').show();
        $('#alertMessage').html("Please select a valid Bank Name");
    } else if ((!destinationBankId.match(/[A-Z|a-z]{4}[a-zA-Z0-9]{7}$/)) || (ifsciChecked == "false") || (isIFSCIMatched == "0")) {
        $('#alertBox').show();
        $('#alertMessage').html("Please enter a valid IFSC Code");
    } else if (startDate == "") {
        $('#alertBox').show();
        $('#alertMessage').html("Start-Date should not be blank");
    } else if ((endDate == "") && !($('#ucancel').is(':checked'))) {
        $('#alertBox').show();
        $('#alertMessage').html("End-Date should not be blank");
    } else {
        $(this).attr("disabled", "disabled");
        $(":input").prop("disabled", true);
        $('#editA').off('click');
        var digio = new Digio(options);
        var temkey = sessionID.replace(/-/g, "");
        temkey = makeid();

        var encryptFormData = encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey)); //CryptoJS.AES.encrypt(formData.toString(), sessionID);


        digio.init();
        $.ajax({
            url: "/bin/cfd/digioCreateMandate",
            type: "POST",
            //data: formData,
            headers: {
                "X-AUTH-TOKEN": encryptFormData,
                "X-AUTH-SESSION": temkey
            },
            success: function(res) {
                var response = JSON.parse(res);
                //console.log(res);
                interactID = response.interactionId;
                if (response.id) {
                    //digio.esign(response.id,mobileNo);
                    digio.submit(response.id, mobileNo);
                } else {
                    digio.cancel();
                }
            },
            error: function(e) {
                console.log("Error in Digio Create Mandate Ajax :: " + e);
            }
        });
    }
});

$(".alertBtn").click(function() {
    $("#alertBox").hide();
});




function calcTotalAmountAndInvestments() {
    var maximum_amount = parseFloat($('input[name=maximum_amount]').val());
    var transferFrequency = $('.transferFrequency').val();
    var startDate = $('input[name=startDate]').val();
    var endDate = $('input[name=endDate]').val();
    if (transferFrequency == "Monthly") {
        var noOfMonths = calculateMonthsInBetween(startDate, endDate);
        $('#totalAmount').val(maximum_amount * noOfMonths);
        $('#totalInstallments').val(noOfMonths);
    } else if (transferFrequency == "Weekly") {
        var noOfWeeks = calculateWeeksInBetween(startDate, endDate);
        $('#totalAmount').val(maximum_amount * noOfWeeks);
        $('#totalInstallments').val(noOfWeeks);
    } else if (transferFrequency == "Yearly") {
        var noOfYears = calculateYearsInBetween(startDate, endDate);
        $('#totalAmount').val(maximum_amount * noOfYears);
        $('#totalInstallments').val(noOfYears);
    } else if (transferFrequency == "Adhoc") {
        var noOfAdhoc = 1;
        $('#totalAmount').val(maximum_amount * noOfAdhoc);
        $('#totalInstallments').val(noOfAdhoc);
    }
}

function calculateMonthsInBetween(startDate, endDate) {
    from = moment(startDate, 'YYYY-MM-DD');
    to = moment(endDate, 'YYYY-MM-DD');
    duration = to.diff(from, 'months');
    return duration;
}

function calculateWeeksInBetween(startDate, endDate) {
    from = moment(startDate, 'YYYY-MM-DD');
    to = moment(endDate, 'YYYY-MM-DD');
    duration = to.diff(from, 'weeks');
    return duration;
}

function calculateYearsInBetween(startDate, endDate) {
    from = moment(startDate, 'YYYY-MM-DD');
    to = moment(endDate, 'YYYY-MM-DD');
    duration = to.diff(from, 'years');
    return duration;
}

function caluclateEndDate(startDate) {

    // from = moment(startDate, 'YYYY-MM-DD');
    enddate = moment(startDate, "YYYY-MM-DD").add(10, 'years');
    enddate = moment(enddate, "YYYY-MM-DD").subtract(1, 'day');
    enddate = moment(enddate, 'YYYY-MM-DD');

}


function capturedropoff(action) {
    var customerNameLabel = $('.customerNameLabel').html().split("<")[0];
    var bankNameLabel = $('.bankNameLabel').html().split("<")[0];
    var accountNumberLabel = $('.accountNumberLabel').html().split("<")[0];
    var confirmAccountLabel = $('.accountNumberLabel').html().split("<")[0];
    var ifscLabel = $('.ifscLabel').html().split("<")[0];
    var maximumAmountLabel = $('.maximumAmountLabel').html().split("<")[0];
    var transferFreqLabel = $('.transferFreqLabel').html().split("<")[0];
    var startDateLabel = $('.startDateLabel').html().split("<")[0];
    var endDateLabel = $('.endDateLabel').html().split("<")[0];
    var totalAmountLabel = $('.totalAmountLabel').html().split("<")[0];
    var totalInstallmentsLabel = $('.totalInstallmentsLabel').html().split("<")[0];
    var aadharLabel = "aadhar";
    var referralCodeLabel = $('.referralCodeLabel').html().split("<")[0];
    var creditAccountLabel = $('.creditAccountLabel').html().split("<")[0];
    var customerEmailLabel = $('.customerEmailLabel').html().split("<")[0];
    var customerName = $('input[name=customerName]').val() ? $('input[name=customerName]').val() : $('input[name=ecustomerName]').val();
    var customerEmail = $('input[name=customerEmail]').val() ? $('input[name=customerEmail]').val() : "";
    var destinationBank = $('input[name=destinationBank]').val() ? $('input[name=destinationBank]').val() : $('input[name=edestinationBank]').val();
    var destinationBankId = $('input[name=destinationBankId]').val() ? $('input[name=destinationBankId]').val() : $('input[name=edestinationBankId]').val();
    var customerAccountNo = $('input[name=accountNumber]').val() ? $('input[name=accountNumber]').val() : $('input[name=eaccountNumber]').val();
    var confirmAccountNo = $('input[name=accountNumber]').val() ? $('input[name=accountNumber]').val() : $('input[name=eaccountNumber]').val();
    var maximum_amount = $('input[name=maximum_amount]').val();
    var emi_amount = $('input[name=emi_maximum_amount]').val();

    var transferFrequency = $('input[name=transferFrequencyText]').val(); //$('.transferFrequency').val();transferFrequencyText
    var startDate = $('input[name=startDate]').val();
    var endDate = "";
    var appid = "direct";
    //mobileNo = $('#custMob').val();
    if ($('#ucancel').is(':checked') || ($('#eidate').text() == "1900-01-01"))
        endDate = "";
    else
        endDate = $('#eidate').text(); //$('input[name=endDate]').val();

    let totalAmount = $('.totalAmount').val();
    let totalInstallments = ""; //$('.totalInstallments').val();
    let aadharId = "1";
    let aadharNo = "";
    let cifid = $('#custcif').val(); //$('.accountNo').children(":selected").attr("id");
    let referralCode = $('input[name=referralCode]').val();
    let accountNo = $('#accountNo').text();
    let formData = {
        destinationBank: destinationBank,
        destinationBankId: destinationBankId,
        customerAccountNo: confirmAccountNo,
        maximum_amount: maximum_amount,
        mobileNo: mobileNo,
        customerName: customerName, // To pick from API/Cookie
        customerEmail: customerEmail,
        transferFrequency: transferFrequency,
        startDate: startDate,
        endDate: endDate,
        accountNo: accountNo,
        confirmAccountNo: confirmAccountNo,
        // totalAmount: totalAmount,
        totalInstallments: totalInstallments,
        referralCode: referralCode,
        aadharNo: aadharNo,
        customerNameLabel: customerNameLabel,
        bankNameLabel: bankNameLabel,
        accountNumberLabel: accountNumberLabel,
        confirmAccountLabel: confirmAccountLabel,
        ifscLabel: ifscLabel,
        maximumAmountLabel: maximumAmountLabel,
        transferFreqLabel: transferFreqLabel,
        startDateLabel: startDateLabel,
        endDateLabel: endDateLabel,
        totalAmountLabel: totalAmountLabel,
        totalInstallmentsLabel: totalInstallmentsLabel,
        aadharLabel: aadharLabel,
        referralCodeLabel: referralCodeLabel,
        creditAccountLabel: creditAccountLabel,
        customerEmailLabel: customerEmailLabel,
        cifid: cifid,
        emi_amount: emi_amount,
        step: "cancelled",
        appid: "direct",
        action: "cancelbutton"

    }
    let redirectURL = "/content/cfdenach/welcome.html";
    var temkey = sessiond; //sessionID.replace(/-/g, "");
    temkey = makeid();

    var encryptFormData = encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey)); //CryptoJS.AES.encrypt(formData.toString(), sessionID);

    $.ajax({
        url: "/bin/cfd/digioCreateMandate",
        type: "POST",
        //data: formData,
        headers: {
            "X-AUTH-TOKEN": encryptFormData,
            "X-AUTH-SESSION": temkey
        },
        success: function(res) {
            if (action == "cancel") {

                sessionStorage.clear();
                window.location.assign(redirectURL);

            }
        },
        error: function(e) {
            if (action == "cancel") {
                sessionStorage.clear();
                window.location.assign(redirectURL);
            }

        }
    });

}

function validateIFSCICode(bankname, bankIfscCode) {
    let valid = "false";
    let formData = {
        ifsc: bankIfscCode,
        repaymentBankName: bankname,
        appname: "CFDENACH"
    }

    let temkey = "";
    temkey = makeid();
    let encryptFormData = encryptMessage(JSON.stringify(formData), CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey)); //CryptoJS.AES.encrypt(formData.toString(), sessionID);
    encryptFormData = encryptFormData + ":" + temkey;



    $.ajax({
        type: "POST",
        url: "/bin/ifscVerify",
        /*data : {
        	ifsc : confirmIfsc,
        	repaymentBankName : bankName
        },*/
        headers: {
            "X-AUTH-SESSION": sessionID,
            "X-AUTH-TOKEN": encryptFormData
        },

        success: function(data) {

            let v1 = decryptMessage(data, CryptoJS.enc.Utf8.parse(temkey), CryptoJS.enc.Utf8.parse(temkey));
            var res = JSON.parse(v1);
            //console.log(res);
            if (res.success == 'true') {
                valid = "true";
                isIFSCIMatched = 1;
                return valid;
            } else {
                isIFSCIMatched = 0;
                valid = "false";
                return valid;
            }
        },
        error: function(error) {
            console.log("Something really bad happened " + error);
            return valid;
        }
    });



}




});