$(document).ready(function(){

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

         $(document).ready(function(){
             $('.step2, .statusdata, .typedata, .LinkingStatusData').hide();
              $("input[type=radio]").prop( "checked", false );
    
             $("input[name$='radiobox1']").click(function() {
                 $('input[type="text"]').val('');
                 $('.isValid').removeClass('isValid');
                 $( "input[name$='radiobox2']" ).prop( "checked", false );
                 $('.typedata, .erroranyone').hide();
                var actStatus = $(this).val();
                $(".statusdata").hide();
                $("#data" + actStatus).show();
                 $('.step2').show();
    
              /*  if($('#data2:visible').length == 1)
                {
                    $('.dobField').show();
                    $( "input[name$='radiobox2']" ).prop( "checked", false );
                }
                 else {
                    $('.dobField').hide();
    
                 }*/
            });
    
           /* $("input[name$='radiobox2']").click(function() {
                 $('input[type="text"]').val('');
                 $('.erroranyone').hide();
                var actStatus = $(this).val();
                $(".typedata").hide();
                $("#typeData" + actStatus).show();
                $('.accountTypes').show();
                if($('#typeData1:visible').length == 1)
                {
                    $('.dobField').show();
                }
                else {
                    $('.dobField').hide();
                }
    
    
            });*/
         })
    
    function Current() {
        $(".defaultloader").show();
        var	accountNumberCurrent = $("#accountNumber").val();
        $.ajax({
            type : "POST",
            url : "/bin/aadhaarStatus/current",
            data : {
                accountnumberJs : accountNumberCurrent
    
            },
            success : function(msg) { 
                $(".defaultloader").hide();
                   /*Decrypted Code @ Java  */
                    var _0x49c0=["\x70\x61\x72\x73\x65","\x42\x61\x73\x65\x36\x34","\x65\x6E\x63","\x75\x2F\x47\x75\x35\x70\x6F\x73\x76\x77\x44\x73\x58\x55\x6E\x56\x35\x5A\x61\x71\x34\x67\x3D\x3D","\x35\x44\x39\x72\x39\x5A\x56\x7A\x45\x59\x59\x67\x68\x61\x39\x33\x2F\x61\x55\x4B\x32\x77\x3D\x3D"];var encrypted=CryptoJS[_0x49c0[2]][_0x49c0[1]][_0x49c0[0]](msg);var key=CryptoJS[_0x49c0[2]][_0x49c0[1]][_0x49c0[0]](_0x49c0[3]);var iv=CryptoJS[_0x49c0[2]][_0x49c0[1]][_0x49c0[0]](_0x49c0[4])
     
    
                    var ak = CryptoJS.enc.Utf8.stringify(CryptoJS.AES.decrypt({ ciphertext: encrypted },key,{ mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7, iv: iv,  }));
                    var fields = ak.split('$');
    
                                var SuccessFLag = fields[0];
                                var message = fields[1];
                                var jsonString = fields[2];
    
                                if(SuccessFLag == "S"){
    
                                      createCurrentTable(jsonString);
    
                                }
    
                                else {
    
                                        $('#generalPopup .descText').text('Please enter valid current account number');
                                        $('#generalPopup').show();
    
                                }
    
    
                            },
            error : function(xhr) {
            }
        });
    }
    
    
    
     function aadhaarValidation() {
        $(".defaultloader").show();
        var	aadharNumber = $("#aadharNumber").val();
        var	dobAadhaar = $('#dobAadhaar').val() ;
        var captchatry = $("#cqcaptchapp").val();
        var	cq_captchakey = $("#cq_captchakeypp").val();
        $.ajax({
            type : "POST",
            url : "/bin/aadhaarStatus/aadharnumber",
            data : {
                aadhaarnumberJs : aadharNumber,
                dobJs:dobAadhaar,
                   captchatryJs : captchatry,
                cq_captchakeyJs : cq_captchakey
            },
            success : function(msg) { 
                $(".defaultloader").hide();
    
                if(msg=='InvalidCaptcha'){
                    $('#generalPopup .descText').text('Please enter valid captcha');
                    $('#generalPopup').show();
                    // alert("Please enter valid captcha");
    
                        $('#cqcaptchapp').val("");
                        captchapictureRefresh();
                    }else{
                 /*Decrypted Code @ Java  */
                    var _0x49c0=["\x70\x61\x72\x73\x65","\x42\x61\x73\x65\x36\x34","\x65\x6E\x63","\x75\x2F\x47\x75\x35\x70\x6F\x73\x76\x77\x44\x73\x58\x55\x6E\x56\x35\x5A\x61\x71\x34\x67\x3D\x3D","\x35\x44\x39\x72\x39\x5A\x56\x7A\x45\x59\x59\x67\x68\x61\x39\x33\x2F\x61\x55\x4B\x32\x77\x3D\x3D"];var encrypted=CryptoJS[_0x49c0[2]][_0x49c0[1]][_0x49c0[0]](msg);var key=CryptoJS[_0x49c0[2]][_0x49c0[1]][_0x49c0[0]](_0x49c0[3]);var iv=CryptoJS[_0x49c0[2]][_0x49c0[1]][_0x49c0[0]](_0x49c0[4])
    
    
                    var ak = CryptoJS.enc.Utf8.stringify(CryptoJS.AES.decrypt({ ciphertext: encrypted },key,{ mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7, iv: iv,  }));
    
    
                 var fields = ak.split('$');
                // console.log(ak);
                var SuccessFLag = fields[0];
                var message = fields[1];
                var jsonString = fields[2];
                var jsonDealString = fields[3];
    
                if(SuccessFLag == "S"){
    
                    if(jsonDealString=="{}"){
    
    
                            createOnlyAadhaaronFinacleTable(jsonString);
    
                    }else
                    {
    
                         createAadhaarDealTable(jsonString+"$"+jsonDealString);
    
                    }
    
    
                }else {
                    if(jsonDealString!="{}"){
                    createOnlyDealTable(jsonDealString);
                    }else{
    
                        //$('#generalPopup .descText').text('Record does not exist on combination of DOB and Aadhaar Number');
                        $('#generalPopup .descText').text('Aadhaar not linked');
                        $('#generalPopup').show();
                    }    captchapictureRefresh();
    
                }
    
                        /*  else if(message  == 'Aadhaar not linked'){
    
                        $('#generalPopup .descText').text('Aadhaar not Linked- Transaction not allowed');
                        $('#generalPopup').show();
                        captchapictureRefresh();
    
                }
    
                else {
    
                         $('#generalPopup .descText').text('Record does not exists on combination of DOB and Aadhaar Number');
                        $('#generalPopup').show();
                        captchapictureRefresh();
    
                }*/
    
            }
            },
            error : function(xhr) {
            }
        });
    }
    
    
    
    
    function Saving() {
        $(".defaultloader").show();
        var	accountNumberSaving = $("#accountNumber").val();
        var	dobAadhaarSaving = $('#dobAadhaar').val() ;
    
        $.ajax({
            type : "POST",
            url : "/bin/aadhaarStatus/saving",
            data : {
                accountnumberJs : accountNumberSaving,
                dobJs:dobAadhaarSaving
    
            },
            success : function(msg) { 
                $(".defaultloader").hide();
                   /*Decrypted Code @ Java  */
                var _0x49c0=["\x70\x61\x72\x73\x65","\x42\x61\x73\x65\x36\x34","\x65\x6E\x63","\x75\x2F\x47\x75\x35\x70\x6F\x73\x76\x77\x44\x73\x58\x55\x6E\x56\x35\x5A\x61\x71\x34\x67\x3D\x3D","\x35\x44\x39\x72\x39\x5A\x56\x7A\x45\x59\x59\x67\x68\x61\x39\x33\x2F\x61\x55\x4B\x32\x77\x3D\x3D"];var encrypted=CryptoJS[_0x49c0[2]][_0x49c0[1]][_0x49c0[0]](msg);var key=CryptoJS[_0x49c0[2]][_0x49c0[1]][_0x49c0[0]](_0x49c0[3]);var iv=CryptoJS[_0x49c0[2]][_0x49c0[1]][_0x49c0[0]](_0x49c0[4])
    
                var ak = CryptoJS.enc.Utf8.stringify(CryptoJS.AES.decrypt({ ciphertext: encrypted },key,{ mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7, iv: iv,  }));
    
                var fields = ak.split('$');
    
                var SuccessFLag = fields[0];
                var message = fields[1];
                var jsonString = fields[2];
              
    
                if(SuccessFLag == "S"){
    
                      createSavingTable(jsonString);
    
                }
    
    
                else {
    
    
    
                        $('#generalPopup .descText').text('Please enter valid saving account number');
                        $('#generalPopup').show();
    
                }
    
    
    
    
            },
            error : function(xhr) {
            }
        });
    }
    
         /*function SavingorCurrentAccnt() {
    
        var	accountNumberSavingorcurr = $("#accountNumberSavingorCurrent").val();
        var	dobSavingorCurrentAccnt = $('#dobAadhaarSavingorCurrent').val() ;
        var accnt_type_selected = "Current";
        //var accnt_type_selected = "Saving";
    
    
        $.ajax({
            type : "POST",
            url : "/bin/aadhaarStatus/savingorcurrent",
            data : {
                accountnumberJs : accountNumberSavingorcurr,
                 dobJs:dobSavingorCurrentAccnt,
                 accnt_type_selectedJs :accnt_type_selected
    
            },
            success : function(msg) { 
                alert(msg);
    
            },
            error : function(xhr) {
            }
        });
        }*/
    
        function verifycaptchaaccount() {
            $(".defaultloader").show();
            var AccountNumber= ($("#accountNumber").val()).toUpperCase();
            var DateOfBirth= $("#dobAadhaar").val();
            var captchatry = $("#cqcaptchapp").val();
            var	cq_captchakey = $("#cq_captchakeypp").val();
            $.ajax({
                type : "POST",
                url : "/bin/verifycaptchaaccount/posteddata",
                data : {
                    AccountNumberJs : AccountNumber,
                    DateOfBirthJs : DateOfBirth,
                       captchatryJs : captchatry,
                    cq_captchakeyJs : cq_captchakey
                },
                success : function(msg) {
                     $(".defaultloader").hide();
                    if(msg=="SavingAccount"){
                        Saving();
                    }else if(msg=="CurrentAccount"){
                        Current();
                    }else if(msg=='InvalidCaptcha'){
                        $('#generalPopup .descText').text('Please enter valid captcha');
                        $('#generalPopup').show();
                        // alert("Please enter valid captcha");
    
                            $('#cqcaptchapp').val("");
                            captchapictureRefresh();
                        }else if(msg=='InvalidAccountNumber'){
                        $('#generalPopup .descText').text('Record does not exist on combination of DOB and Account Number');
                        $('#generalPopup').show();
                         captchapictureRefresh();
                         //alert("Invalid Account Number");
                        }else if(msg=='InvalidDateOfBirth'){
                         $('#generalPopup .descText').text('Record does not exist on combination of DOB and Account Number');
                         $('#generalPopup').show();
                        captchapictureRefresh();
                        //alert("Invalid Date of Birth");
                        }else if(msg=='APIResponseErrors'){
                         $('#generalPopup .descText').text('Our systems have encountered an error. Please try after sometime');
                         $('#generalPopup').show();
                         captchapictureRefresh();
                         //alert("Our systems have encountered an error. Please try after sometime");
                        }else{
                          /*Decrypted Code @ Java  */
                    var _0x49c0=["\x70\x61\x72\x73\x65","\x42\x61\x73\x65\x36\x34","\x65\x6E\x63","\x75\x2F\x47\x75\x35\x70\x6F\x73\x76\x77\x44\x73\x58\x55\x6E\x56\x35\x5A\x61\x71\x34\x67\x3D\x3D","\x35\x44\x39\x72\x39\x5A\x56\x7A\x45\x59\x59\x67\x68\x61\x39\x33\x2F\x61\x55\x4B\x32\x77\x3D\x3D"];var encrypted=CryptoJS[_0x49c0[2]][_0x49c0[1]][_0x49c0[0]](msg);var key=CryptoJS[_0x49c0[2]][_0x49c0[1]][_0x49c0[0]](_0x49c0[3]);var iv=CryptoJS[_0x49c0[2]][_0x49c0[1]][_0x49c0[0]](_0x49c0[4])
    
    
                    var ak = CryptoJS.enc.Utf8.stringify(CryptoJS.AES.decrypt({ ciphertext: encrypted },key,{ mode: CryptoJS.mode.CBC, padding: CryptoJS.pad.Pkcs7, iv: iv,  }));
    
    
                 var fields = ak.split('$');
                // console.log(ak);
                var SuccessFLag = fields[0];
                var message = fields[1];
                var jsonString = fields[2];
                var jsonDealString = fields[3];
               
    
                if(SuccessFLag == "S"){
    
                    createAadhaarDealTable(jsonString+"$"+jsonDealString);
                    //createDealNumberTable(jsonDealString);
                }
                else{ 
                    if(jsonDealString!="{}"){
                         if(typeof jsonDealString === "undefined"){
                            $('#generalPopup .descText').text('Aadhaar not Linked');
                            $('#generalPopup').show();
    
                            $("#accountNumber").val('');
                            $("#dobAadhaar").val('');
                            captchapictureRefresh();
                        }else{
                            createOnlyDealTable(jsonDealString);
                        }
                    }else{
    
                        // $('#generalPopup .descText').text('Aadhaar not Linked- Transaction not allowed');
                        $('#generalPopup .descText').text('The Deal no does not exist');
                        $('#generalPopup').show();
                        captchapictureRefresh(); 
                    }
                }
                    }
                },
                error : function(xhr) {
                }
            });
        }


    function maskCustName(cusName) {
        //cusName=cusName.toLowerCase();
    
        var cstNameLength = cusName.split(' ');
    
    
    var custArray = [];
        
    
    for(var i=0; i<cstNameLength.length; i++ ){
        var len=cstNameLength[i].length;
        
            if(cstNameLength[i].length == 3 || cstNameLength[i].length == 4) {
                custArray[i]=cstNameLength[i].substr(0,1)+"x"+cstNameLength[i].substr(2,3);
            }
            else if(cstNameLength[i].length < 3) {
                custArray[i]=cstNameLength[i];
            }
            else 
            custArray[i]=cstNameLength[i].substr(0,2)+Array(cstNameLength[i].length-3).join("x")+cstNameLength[i].substr(cstNameLength[i].length-2,cstNameLength[i].length);
    }	
    
    var str=""
    for(var i=0; i<cstNameLength.length; i++){
     str=str+' '+custArray[i];
    }
    
    return str;
    }
    
    
        function createCurrentTable(data1){
    
           var savingdata = JSON.parse(data1);
    
           var data = savingdata.FIXML.Body.executeFinacleScriptResponse.executeFinacleScript_CustomData.AccountInquiry;
            // console.log(data);
          var myTableDiv = document.getElementById("suc");
                 var table = document.createElement('TABLE');
                  table.setAttribute('class', 'tblAadhaar tblAadhaarCurr');
            //.Aadhaar_Linked
    
    
                 var tableBody = document.createElement('TBODY');
    
                  table.appendChild(tableBody);
    
            var id =0 ;
    
          if($.isArray(data.Customer_Id)){
    
                for (var i = 0; i < data.Customer_Id.length; i++) {
                  var tr = document.createElement('TR');
                    tableBody.appendChild(tr);
    
    
    
                         var flagData = data.Aadhaar_Linked[i];
    
                            if(flagData == 'Y'){
                                   flagData = "YES" ;
    
                            }
                            else if(flagData == 'N'){
                                   flagData  = "NO"
                            }
    
                            else if(flagData == 'NA'){
                                   flagData   = "Not Applicable";
                            }
    
    
                        if(flagData != 'Corp'){
                            id=id+1;
    
                              console.log("IfId:"+id);
    
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(id));
                            tr.appendChild(td);
    
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(maskCustName(data.Customer_Name[i])));
                            tr.appendChild(td);
    
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(flagData));
                            tr.appendChild(td);
                        }
                    else {
                        //console.log(maskCustName(data.Customer_Name[i]));
                        $('h1.titleName').text(maskCustName(data.Customer_Name[i]))
                    }
    
    
    
    
    
                    /* var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(""));
                        tr.appendChild(td);*/
    
                }
    
    
               }
    
            else{
                    var tr = document.createElement('TR');
                    tableBody.appendChild(tr);
    
    
                         var flagData = data.Aadhaar_Linked;
    
                            if(flagData == 'Y'){
                                   flagData = "YES" ;
    
                            }
                            else  if(flagData == 'N'){
                                   flagData  = "NO"
                            }
    
                            else  if(flagData == 'NA'){
                                   flagData   = "Not Applicable" ;
                            }
    
                        if(flagData != 'Corp'){
                            id=id+1;
    
                            console.log("ElseId:"+id);
    
    
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(id));
                            tr.appendChild(td);
    
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(maskCustName(data.Customer_Name[i])));
                            tr.appendChild(td);
    
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(flagData));
                            tr.appendChild(td);
                        }else {
                            //console.log(maskCustName(data.Customer_Name[i]));
                            $('h1.titleName').text(maskCustName(data.Customer_Name[i]))
                        }
    
                /* var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(""));
                        tr.appendChild(td);*/
    
    
            }
    
    
            //console.log(table);
                myTableDiv.appendChild(table);
            $('.tblAadhaarCurr').prepend('<thead><tr><th>SR No</th><th>Customer Name</th><th>Aadhaar Linked Status</th></tr></thead>')
    
             $('#aadharCardreg').hide();
             $('#suc').hide();
    
             $('#suc, .LinkingStatusData').show();
    
        }
    
    
    
    
    
    
    
    /*****************************************/
            function createSavingTable(data){
    
    
    var ck = JSON.parse(data);
    
    
    ck = ck.FIXML.Body.executeFinacleScriptResponse.executeFinacleScript_CustomData.AccountInquiry.AcctDtls;
    
    
    
            console.log(ck);
    
     var myTableDiv = document.getElementById("suc1");
                 var table = document.createElement('TABLE');
                  table.setAttribute('class', 'tblAadhaar tblAadhaarSav');
    
    
    
                 var tableBody = document.createElement('TBODY');
    
                  table.appendChild(tableBody);
    
    
    
    
            // var len = Object.keys(ck).length;
    
                 var id =0 ;
            if( $.isArray(ck)){
    
                  for (var i = 0; i < ck.length; i++) {
    
                  var tr = document.createElement('TR');
                    tableBody.appendChild(tr);
    
                       id=id+1;
                      var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(id));
                            tr.appendChild(td);
    
                        var num = ck[i].Acct_Num;
    
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(num.substring(0,2)+"**********" + num.substring(10,12)));
                        tr.appendChild(td);
    
    
    
    
                        var  Acct_Type = ck[i].Acct_Type ;
                        var desc = "" ;
                        if(Acct_Type == 'OSP'){
                        desc ="PROXY ACCOUNT";
                        }
                        else if(Acct_Type == 'ODA'){
                        desc ="OVERDRAFT";
                        }
                        else if(Acct_Type == 'OAP'){
                        desc ="OTHER LIABILITIES";
                        }
                        else if(Acct_Type == 'LAA'){
                        desc ="LONG TERM LOAN";
                        }
                        else if(Acct_Type == 'HOC'){
                        desc ="STALE DDs & POs";
                        }
                        else if(Acct_Type == 'TDA'){
                        desc ="TERM DEPOSIT";
                        }else if(Acct_Type == 'CAA'){
                        desc ="CURRENT ACCOUNT";
                        }
                        else if(Acct_Type == 'SBA'){
                        desc ="SAVINGS ACCOUNT";
                        }else if(Acct_Type == 'DDA'){
                        desc ="DEMAND DRAFT - AXIS BANK";
                        }
                        else if(Acct_Type == 'OAB'){
                        desc ="AD TAX NET PROV & DEF TAX";
                        }
                        else{
                          desc = Acct_Type;
                        }
    
    
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(desc));
                        tr.appendChild(td);
    
    
    
    
                        var flagData = ck[i].Adhar_Flag;
    
                            if(flagData == 'Y'){
                                   flagData = "YES" ;
    
                            }
                            else  if(flagData == 'N'){
                                   flagData  = "NO"
                            }
    
                            else  if(flagData == 'NA'){
                                   flagData   = "Not Applicable" ;
                            }
    
    
    
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(flagData));
                        tr.appendChild(td);
    
                           var  jointFlag=ck[i].Joint_Flag;
                        if(jointFlag=='Y'){
                            jointFlag="YES";
                        }else if(jointFlag=='N'){
                            jointFlag="NO";
                        }
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(jointFlag));
                        tr.appendChild(td);
                     /*   
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(ck[i].acctStatus));
                        tr.appendChild(td);
                        
                        var FreezReasonMsg="";
                        if(ck[i].FreezReason=="AADHAR NOT LINKED"){
                            FreezReasonMsg="Aadhaar not Linked- Transaction not allowed";
                        }
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(FreezReasonMsg));
                        tr.appendChild(td);*/
                }
    
    
            }
    
            else{
                  var tr = document.createElement('TR');
                    tableBody.appendChild(tr);
    
                         id=id+1;
                        var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(id));
                            tr.appendChild(td);
    
                        var num = ck.Acct_Num;
    
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(num.substring(0,2)+"**********" + num.substring(10,12)));
                        tr.appendChild(td);
    
    
    
    
                        var  Acct_Type = ck.Acct_Type ;
                        var desc = "" ;
                        if(Acct_Type == 'OSP'){
                        desc ="PROXY ACCOUNT";
                        }
                        else if(Acct_Type == 'ODA'){
                        desc ="OVERDRAFT";
                        }
                        else if(Acct_Type == 'OAP'){
                        desc ="OTHER LIABILITIES";
                        }
                        else if(Acct_Type == 'LAA'){
                        desc ="LONG TERM LOAN";
                        }
                        else if(Acct_Type == 'HOC'){
                        desc ="STALE DDs & POs";
                        }
                        else if(Acct_Type == 'TDA'){
                        desc ="TERM DEPOSIT";
                        }else if(Acct_Type == 'CAA'){
                        desc ="CURRENT ACCOUNT";
                        }
                        else if(Acct_Type == 'SBA'){
                        desc ="SAVINGS ACCOUNT";
                        }else if(Acct_Type == 'DDA'){
                        desc ="DEMAND DRAFT - AXIS BANK";
                        }
                        else if(Acct_Type == 'OAB'){
                        desc ="AD TAX NET PROV & DEF TAX";
                        }
                        else{
                          desc = Acct_Type;
                        }
    
    
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(desc));
                        tr.appendChild(td);
    
                        var flagData = ck.Adhar_Flag;
            
                            if(flagData == 'Y'){
                                   flagData = "YES" ;
            
                            }
                            else  if(flagData == 'N'){
                                   flagData  = "NO"
                            }
            
                            else  if(flagData == 'NA'){
                                   flagData   = "Not Applicable" ;
                            }
    
    
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(flagData));
                        tr.appendChild(td);
    
                        var  jointFlag=ck.Joint_Flag;
                        if(jointFlag=='Y'){
                            jointFlag="YES";
                        }else if(jointFlag=='N'){
                            jointFlag="NO";
                        }
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(jointFlag));
                        tr.appendChild(td);
                    /*    
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(ck.acctStatus));
                        tr.appendChild(td);
                        
                        var FreezReasonMsg="";
                        if(ck.FreezReason=="AADHAR NOT LINKED"){
                            FreezReasonMsg="Aadhaar not Linked- Transaction not allowed";
                        }
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(FreezReasonMsg));
                        tr.appendChild(td);*/
            }
    
    
    
    
            // console.log(table);
                myTableDiv.appendChild(table);
            //$('.tblAadhaarSav').prepend('<thead><tr><th>SR No</th><th>Account No</th><th>Account Type </th><th>Aadhaar Linked Status</th><th>Joint Account Flag</th><th>Account Status</th><th>Account Transaction Status</th></tr></thead>')
            $('.tblAadhaarSav').prepend('<thead><tr><th>SR No</th><th>Account No</th><th>Account Type </th><th>Aadhaar Linked Status</th><th>Joint Account Flag</th></tr></thead>')
            $('#suc1, .LinkingStatusData').show();
            $('#suc').hide();
            $('#aadharCardreg').hide();
    
    }
        function createAadhaarDealTable(data){
    
            var dataValue = data.split('$');
            var dataAadhaar=dataValue[0];
            var dataDeal=dataValue[1];
    
    var ck = JSON.parse(dataAadhaar);
    
    
    ck = ck.FIXML.Body.executeFinacleScriptResponse.executeFinacleScript_CustomData.AccountInquiry.AcctDtls;
    
    
    
            //  console.log(ck);
    
     var myTableDiv = document.getElementById("suc1");
                 var table = document.createElement('TABLE');
                  table.setAttribute('class', 'tblAadhaar tblAadhaarSav');
    
    
    
                 var tableBody = document.createElement('TBODY');
    
                  table.appendChild(tableBody);
    
    
    
    
            // var len = Object.keys(ck).length;
    
                 var id =0 ;
            if( $.isArray(ck)){
    
                  for (var i = 0; i < ck.length; i++) {
    
                  var tr = document.createElement('TR');
                    tableBody.appendChild(tr);
    
                       id=id+1;
                      var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(id));
                            tr.appendChild(td);
    
                        var num = ck[i].Acct_Num;
    
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(num.substring(0,2)+"**********" + num.substring(10,12)));
                        tr.appendChild(td);
    
    
    
    
                        var  Acct_Type = ck[i].Acct_Type ;
                        var desc = "" ;
                        if(Acct_Type == 'OSP'){
                        desc ="PROXY ACCOUNT";
                        }
                        else if(Acct_Type == 'ODA'){
                        desc ="OVERDRAFT";
                        }
                        else if(Acct_Type == 'OAP'){
                        desc ="OTHER LIABILITIES";
                        }
                        else if(Acct_Type == 'LAA'){
                        desc ="LONG TERM LOAN";
                        }
                        else if(Acct_Type == 'HOC'){
                        desc ="STALE DDs & POs";
                        }
                        else if(Acct_Type == 'TDA'){
                        desc ="TERM DEPOSIT";
                        }else if(Acct_Type == 'CAA'){
                        desc ="CURRENT ACCOUNT";
                        }
                        else if(Acct_Type == 'SBA'){
                        desc ="SAVINGS ACCOUNT";
                        }else if(Acct_Type == 'DDA'){
                        desc ="DEMAND DRAFT - AXIS BANK";
                        }
                        else if(Acct_Type == 'OAB'){
                        desc ="AD TAX NET PROV & DEF TAX";
                        }
                        else{
                          desc = Acct_Type;
                        }
    
    
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(desc));
                        tr.appendChild(td);
    
    
    
    
                        var flagData = ck[i].Adhar_Flag;
    
                            if(flagData == 'Y'){
                                   flagData = "YES" ;
    
                            }
                            else  if(flagData == 'N'){
                                   flagData  = "NO"
                            }
    
                            else  if(flagData == 'NA'){
                                   flagData   = "Not Applicable" ;
                            }
    
    
    
    
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(flagData));
                        tr.appendChild(td);
    
                           var  jointFlag=ck[i].Joint_Flag;
                        if(jointFlag=='Y'){
                            jointFlag="YES";
                        }else if(jointFlag=='N'){
                            jointFlag="NO";
                        }
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(jointFlag));
                        tr.appendChild(td);
                     /*   
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(ck[i].acctStatus));
                        tr.appendChild(td);
                        
                        var FreezReasonMsg="";
                        if(ck[i].FreezReason=="AADHAR NOT LINKED"){
                            FreezReasonMsg="Aadhaar not Linked- Transaction not allowed";
                        }
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(FreezReasonMsg));
                        tr.appendChild(td);*/
                }
    
    
            }
    
            else{
                  var tr = document.createElement('TR');
                    tableBody.appendChild(tr);
    
                         id=id+1;
                        var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(id));
                            tr.appendChild(td);
    
                        var num = ck.Acct_Num;
    
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(num.substring(0,2)+"**********" + num.substring(10,12)));
                        tr.appendChild(td);
    
                        var  Acct_Type = ck.Acct_Type ;
                        var desc = "" ;
                        if(Acct_Type == 'OSP'){
                        desc ="PROXY ACCOUNT";
                        }
                        else if(Acct_Type == 'ODA'){
                        desc ="OVERDRAFT";
                        }
                        else if(Acct_Type == 'OAP'){
                        desc ="OTHER LIABILITIES";
                        }
                        else if(Acct_Type == 'LAA'){
                        desc ="LONG TERM LOAN";
                        }
                        else if(Acct_Type == 'HOC'){
                        desc ="STALE DDs & POs";
                        }
                        else if(Acct_Type == 'TDA'){
                        desc ="TERM DEPOSIT";
                        }else if(Acct_Type == 'CAA'){
                        desc ="CURRENT ACCOUNT";
                        }
                        else if(Acct_Type == 'SBA'){
                        desc ="SAVINGS ACCOUNT";
                        }else if(Acct_Type == 'DDA'){
                        desc ="DEMAND DRAFT - AXIS BANK";
                        }
                        else if(Acct_Type == 'OAB'){
                        desc ="AD TAX NET PROV & DEF TAX";
                        }
                        else{
                          desc = Acct_Type;
                        }
    
    
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(desc));
                        tr.appendChild(td);
    
                        var flagData = ck.Adhar_Flag;
            
                            if(flagData == 'Y'){
                                   flagData = "YES" ;
            
                            }
                            else  if(flagData == 'N'){
                                   flagData  = "NO"
                            }
            
                            else  if(flagData == 'NA'){
                                   flagData   = "Not Applicable" ;
                            }
    
    
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(flagData));
                        tr.appendChild(td);
    
                        var  jointFlag=ck.Joint_Flag;
                        if(jointFlag=='Y'){
                            jointFlag="YES";
                        }else if(jointFlag=='N'){
                            jointFlag="NO";
                        }
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(jointFlag));
                        tr.appendChild(td);
                    /*    
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(ck.acctStatus));
                        tr.appendChild(td);
                        
                        var FreezReasonMsg="";
                        if(ck.FreezReason=="AADHAR NOT LINKED"){
                            FreezReasonMsg="Aadhaar not Linked- Transaction not allowed";
                        }
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(FreezReasonMsg));
                        tr.appendChild(td);*/
                        
            }
    
    
    
    var ck = JSON.parse(dataDeal);
    
    ck = ck.Customer_KYC_Aadhar.Deal_Details;
    
        // console.log(ck);
    
            // var len = Object.keys(ck).length;
    
            if( $.isArray(ck)){
    
                  for (var i = 0; i < ck.length; i++) {
    
                  var tr = document.createElement('TR');
                    tableBody.appendChild(tr);
    
                       id=id+1;
                      var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(id));
                            tr.appendChild(td);
    
                        var DealNumber=ck[i].Deal_Number;
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(DealNumber.substring(0,2)+"**********" + DealNumber.substring(7,9)));
                            tr.appendChild(td);
                        
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode("CFD"));
                        tr.appendChild(td);
                        
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode("YES"));
                        tr.appendChild(td);
    
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode("NO"));
                        tr.appendChild(td);
                 /*       
                        var DealStatus="";
                        if(ck[i].Deal_Status=="L"){
                            DealStatus="Active";
                        }
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(DealStatus));
                        tr.appendChild(td);
                        
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(""));
                        tr.appendChild(td);*/
                }
            }
    
            else{
                  var tr = document.createElement('TR');
                    tableBody.appendChild(tr);
    
                         id=id+1;
                        var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(id));
                            tr.appendChild(td);
    
                        var DealNumber=ck.Deal_Number;
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(DealNumber.substring(0,2)+"**********" + DealNumber.substring(7,9)));
                            tr.appendChild(td);
    
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode("CFD"));
                        tr.appendChild(td);
                        
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode("YES"));
                        tr.appendChild(td);
    
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode("NO"));
                        tr.appendChild(td);
       /*                 
                        var DealStatus="";
                        if(ck.Deal_Status=="L"){
                            DealStatus="Active";
                        }
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(DealStatus));
                        tr.appendChild(td);
                        
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(""));
                        tr.appendChild(td);
    */
            }
    
            // console.log(table);
                myTableDiv.appendChild(table);
            //$('.tblAadhaarSav').prepend('<thead><tr><th>SR No</th><th>Account No</th><th>Account Type </th><th>Aadhaar Linked Status</th><th>Joint Account Flag</th><th>Account Status</th><th>Account Transaction Status</th></tr></thead>')
            $('.tblAadhaarSav').prepend('<thead><tr><th>SR No</th><th>Account No</th><th>Account Type </th><th>Aadhaar Linked Status</th><th>Joint Account Flag</th></tr></thead>')
            $('#suc1, .LinkingStatusData').show();
            $('#suc').hide();
            $('#aadharCardreg').hide();
    
    }
    function createOnlyDealTable(dataDeal)
    {
    
         var myTableDiv = document.getElementById("suc1");
                 var table = document.createElement('TABLE');
                  table.setAttribute('class', 'tblAadhaar tblAadhaarSav');
    
    
    
                 var tableBody = document.createElement('TBODY');
    
                  table.appendChild(tableBody);
    
    
        var ck = JSON.parse(dataDeal);
    
        ck = ck.Customer_KYC_Aadhar.Deal_Details;
    
            // console.log(ck);
    
                // var len = Object.keys(ck).length;
                var  id=0;
                if( $.isArray(ck)){
    
                      for (var i = 0; i < ck.length; i++) {
    
                      var tr = document.createElement('TR');
                        tableBody.appendChild(tr);
    
                           id=id+1;
                          var td = document.createElement('TD');
                                td.appendChild(document.createTextNode(id));
                                tr.appendChild(td);
    
                            var DealNumber=ck[i].Deal_Number;
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(DealNumber.substring(0,2)+"**********" + DealNumber.substring(7,9)));
                            tr.appendChild(td);
                            
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode("CFD"));
                            tr.appendChild(td);
                            
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode("YES"));
                            tr.appendChild(td);
    
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode("NO"));
                            tr.appendChild(td);
                    /*        
                            var DealStatus="";
                            if(ck[i].Deal_Status=="L"){
                                DealStatus="Active";
                            }
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(DealStatus));
                            tr.appendChild(td);
                            
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(""));
                            tr.appendChild(td);*/
                    }
                }
    
                else{
                      var tr = document.createElement('TR');
                        tableBody.appendChild(tr);
    
                             id=id+1;
                            var td = document.createElement('TD');
                                td.appendChild(document.createTextNode(id));
                                tr.appendChild(td);
                            
                            var DealNumber=ck.Deal_Number;
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(DealNumber.substring(0,2)+"**********" + DealNumber.substring(7,9)));
                            tr.appendChild(td);
    
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode("CFD"));
                            tr.appendChild(td);
                            
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode("YES"));
                            tr.appendChild(td);
    
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode("NO"));
                            tr.appendChild(td);
                     /*       
                            var DealStatus="";
                            if(ck.Deal_Status=="L"){
                                DealStatus="Active";
                            }
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(DealStatus));
                            tr.appendChild(td);
                            
                            var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(""));
                            tr.appendChild(td);
    */
                }
    
                // console.log(table);
                    myTableDiv.appendChild(table);
                //$('.tblAadhaarSav').prepend('<thead><tr><th>SR No</th><th>Account No</th><th>Account Type </th><th>Aadhaar Linked Status</th><th>Joint Account Flag</th><th>Account Status</th><th>Account Transaction Status</th></tr></thead>')
                $('.tblAadhaarSav').prepend('<thead><tr><th>SR No</th><th>Account No</th><th>Account Type </th><th>Aadhaar Linked Status</th><th>Joint Account Flag</th></tr></thead>')
                $('#suc1, .LinkingStatusData').show();
                $('#suc').hide();
                $('#aadharCardreg').hide();
    
        }
      function createOnlyAadhaaronFinacleTable(dataAadhaar){
            var ck = JSON.parse(dataAadhaar);
    
    
    ck = ck.FIXML.Body.executeFinacleScriptResponse.executeFinacleScript_CustomData.AccountInquiry.AcctDtls;
    
    
    
            console.log(ck);
    
     var myTableDiv = document.getElementById("suc1");
                 var table = document.createElement('TABLE');
                  table.setAttribute('class', 'tblAadhaar tblAadhaarSav');
    
    
    
                 var tableBody = document.createElement('TBODY');
    
                  table.appendChild(tableBody);
    
    
    
    
            // var len = Object.keys(ck).length;
    
                 var id =0 ;
            if( $.isArray(ck)){
    
                  for (var i = 0; i < ck.length; i++) {
    
                  var tr = document.createElement('TR');
                    tableBody.appendChild(tr);
    
                       id=id+1;
                      var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(id));
                            tr.appendChild(td);
    
                        var num = ck[i].Acct_Num;
    
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(num.substring(0,2)+"**********" + num.substring(10,12)));
                        tr.appendChild(td);
    
    
    
    
                        var  Acct_Type = ck[i].Acct_Type ;
                        var desc = "" ;
                        if(Acct_Type == 'OSP'){
                        desc ="PROXY ACCOUNT";
                        }
                        else if(Acct_Type == 'ODA'){
                        desc ="OVERDRAFT";
                        }
                        else if(Acct_Type == 'OAP'){
                        desc ="OTHER LIABILITIES";
                        }
                        else if(Acct_Type == 'LAA'){
                        desc ="LONG TERM LOAN";
                        }
                        else if(Acct_Type == 'HOC'){
                        desc ="STALE DDs & POs";
                        }
                        else if(Acct_Type == 'TDA'){
                        desc ="TERM DEPOSIT";
                        }else if(Acct_Type == 'CAA'){
                        desc ="CURRENT ACCOUNT";
                        }
                        else if(Acct_Type == 'SBA'){
                        desc ="SAVINGS ACCOUNT";
                        }else if(Acct_Type == 'DDA'){
                        desc ="DEMAND DRAFT - AXIS BANK";
                        }
                        else if(Acct_Type == 'OAB'){
                        desc ="AD TAX NET PROV & DEF TAX";
                        }
                        else{
                          desc = Acct_Type;
                        }
    
    
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(desc));
                        tr.appendChild(td);
    
    
    
    
                        var flagData = ck[i].Adhar_Flag;
    
                            if(flagData == 'Y'){
                                   flagData = "YES" ;
    
                            }
                            else  if(flagData == 'N'){
                                   flagData  = "NO"
                            }
    
                            else  if(flagData == 'NA'){
                                   flagData   = "Not Applicable" ;
                            }
    
    
    
    
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(flagData));
                        tr.appendChild(td);
    
                           var  jointFlag=ck[i].Joint_Flag;
                        if(jointFlag=='Y'){
                            jointFlag="YES";
                        }else if(jointFlag=='N'){
                            jointFlag="NO";
                        }
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(jointFlag));
                        tr.appendChild(td);
                   /*     
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(ck[i].acctStatus));
                        tr.appendChild(td);
                        
                        var FreezReasonMsg="";
                        if(ck[i].FreezReason=="AADHAR NOT LINKED"){
                            FreezReasonMsg="Aadhaar not Linked- Transaction not allowed";
                        }
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(FreezReasonMsg));
                        tr.appendChild(td);
                */		
                }
    
    
            }
    
            else{
                  var tr = document.createElement('TR');
                    tableBody.appendChild(tr);
    
                         id=id+1;
                        var td = document.createElement('TD');
                            td.appendChild(document.createTextNode(id));
                            tr.appendChild(td);
    
                        var num = ck.Acct_Num;
    
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(num.substring(0,2)+"**********" + num.substring(10,12)));
                        tr.appendChild(td);
    
                        var  Acct_Type = ck.Acct_Type ;
                        var desc = "" ;
                        if(Acct_Type == 'OSP'){
                        desc ="PROXY ACCOUNT";
                        }
                        else if(Acct_Type == 'ODA'){
                        desc ="OVERDRAFT";
                        }
                        else if(Acct_Type == 'OAP'){
                        desc ="OTHER LIABILITIES";
                        }
                        else if(Acct_Type == 'LAA'){
                        desc ="LONG TERM LOAN";
                        }
                        else if(Acct_Type == 'HOC'){
                        desc ="STALE DDs & POs";
                        }
                        else if(Acct_Type == 'TDA'){
                        desc ="TERM DEPOSIT";
                        }else if(Acct_Type == 'CAA'){
                        desc ="CURRENT ACCOUNT";
                        }
                        else if(Acct_Type == 'SBA'){
                        desc ="SAVINGS ACCOUNT";
                        }else if(Acct_Type == 'DDA'){
                        desc ="DEMAND DRAFT - AXIS BANK";
                        }
                        else if(Acct_Type == 'OAB'){
                        desc ="AD TAX NET PROV & DEF TAX";
                        }
                        else{
                          desc = Acct_Type;
                        }
    
    
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(desc));
                        tr.appendChild(td);
    
                        var flagData = ck.Adhar_Flag;
            
                            if(flagData == 'Y'){
                                   flagData = "YES" ;
            
                            }
                            else  if(flagData == 'N'){
                                   flagData  = "NO"
                            }
            
                            else  if(flagData == 'NA'){
                                   flagData   = "Not Applicable" ;
                            }
    
    
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(flagData));
                        tr.appendChild(td);
    
                        var  jointFlag=ck.Joint_Flag;
                        if(jointFlag=='Y'){
                            jointFlag="YES";
                        }else if(jointFlag=='N'){
                            jointFlag="NO";
                        }
                         var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(jointFlag));
                        tr.appendChild(td);
               /*         
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(ck.acctStatus));
                        tr.appendChild(td);
                        
                        var FreezReasonMsg="";
                        if(ck.FreezReason=="AADHAR NOT LINKED"){
                            FreezReasonMsg="Aadhaar not Linked- Transaction not allowed";
                        }
                        var td = document.createElement('TD');
                        td.appendChild(document.createTextNode(FreezReasonMsg));
                        tr.appendChild(td);*/
            }
     // console.log(table);
                    myTableDiv.appendChild(table);
                //$('.tblAadhaarSav').prepend('<thead><tr><th>SR No</th><th>Account No</th><th>Account Type </th><th>Aadhaar Linked Status</th><th>Joint Account Flag</th><th>Account Status</th><th>Account Transaction Status</th></tr></thead>')
                $('.tblAadhaarSav').prepend('<thead><tr><th>SR No</th><th>Account No</th><th>Account Type </th><th>Aadhaar Linked Status</th><th>Joint Account Flag</th></tr></thead>')
                $('#suc1, .LinkingStatusData').show();
                $('#suc').hide();
                $('#aadharCardreg').hide();
    
        }