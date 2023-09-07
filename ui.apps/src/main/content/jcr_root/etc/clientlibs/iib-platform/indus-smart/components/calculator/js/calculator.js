$(document).ready(function () {

$('[formatNumber]').keyup(function(event){
  var n = parseInt(this.value.replace(/\D/g,''),10);
  this.value = n? n.toLocaleString('en-IN'): '';
})

$('[formatNumberw0]').keyup(function(event){
   // debugger;
  var n = parseInt(this.value.replace(/[^\d]/,''));
  this.value = n? n.toLocaleString('en-IN'): '0';
})


	/*Goal Calculator Tell Me Click*/
	$('#calulate').click(function () {

		if ($('.cgoals').val() == '') {

		} else {

			$('.predictionAndOneTimeDiv').show();
			$('.goalDoYouKnow').show();
			$('.year-next').val(parseInt($('.years').val()) + presentyear);

			callthecalculator($('.years').val())
			$('.sipyears').val($('.years').val());
			$('#otiyears').val($('.years').val());

		}

		$('html, body').animate({
			scrollTop: $(".predictionAndOneTimeDiv").offset().top
		}, 'slow');
	});



	/*SIP Tell Me Click*/
	$('#calulateprincipal').click(function () {
		if(($('#principal').val() == '') && ($('#lumpsum').val() == '')) {

		}
		else {
			$('.systematicHiddenBox').show();
			$('.sipDoYouKnow').show();
			calaculatesip($('#principalyears').val());
			var sipDate = new Date();
			var curYear = sipDate.getFullYear();
			$('#lumpsumyears').val(parseInt($('#principalyears').val()) + curYear);
		}

		$('html, body').animate({
			scrollTop: $(".systematicHiddenBox").offset().top
		}, 'slow')
	});
});

function getParsedValue(value) {
    return value ? value.split(',').join(''): '';
}

var presentyear = (new Date()).getFullYear();

//Goal Calculator AJAX wrapped inside a Function
function callthecalculator(years, flag) {
	flag =! !flag;
	var dta = {
			costofgoals: getParsedValue($('.cgoals').val()),
			rate: $('.rate').val().split("%")[0],
			years: years,
			delayYears: $('.delayYears').val().split(" ")[0],
			callType: "tab1"
	}
	//console.log(dta);
	$.ajax({
		url: "/bin/calculatorCheck",
		data: {
			costofgoals: getParsedValue($('.cgoals').val()),
			rate: $('.rate').val().split("%")[0],
			years: years,
			delayYears: $('.delayYears').val().split(" ")[0],
			callType: "tab1"
		},
		type: 'POST',
		success: function (res) {
			var result = JSON.parse(res);
			var obj = {
					systematicAsString: result.systematicAsString,
					oneTimeAsString: result.oneTimeAsString,
					predictionValueAsString: result.predictionValueAsString
			}
			document.getElementById('sip').value = parseInt(obj.systematicAsString).toLocaleString('en-IN');
			document.getElementById('oti').value = parseInt(obj.oneTimeAsString).toLocaleString('en-IN');



			let sipTotalInvstment = parseInt(obj.systematicAsString)*parseInt($('[name=years]').val())*12;
			$('.sipTotalInvestment').val(sipTotalInvstment.toLocaleString('en-IN'));
			if (flag == false) {
				document.getElementById('predictionGoal').value = parseInt(obj.predictionValueAsString).toLocaleString('en-IN');
			}

            setInputWidth([$('.sipTotalInvestment'),$('#predictionGoal')]);
            setInputWidth([$('.sipyears'),$('#sip'),$('#otiyears'), $('.year-next'), $('#oti')]);

		},
		error: function (e) {
			console.log("Exception in Calculator:: " + e);
		}
	});
}

$('.delayYears').change(function () {
	var predictionYrs = $(this).val();
	predictionCalculate(predictionYrs);
});

//Prediction Goal Calculator
function predictionCalculate(yrs) {

	var delayYears = yrs;
	var splitDelay = delayYears.split(" ");

	var predData = {
			costofgoals: getParsedValue($('.cgoals').val()),
			rate: $('.rate').val().split("%")[0],
			years: $('.years').val(),
			delayYears: splitDelay[0],
			callType: "tab1"
	}

	$.ajax({
		url: "/bin/calculatorCheck",
		data: predData,
		type: 'POST',
		success: function (res) {
			let predictionGoalVal = JSON.parse(res).predictionValueAsString;
			$('#predictionGoal').val(parseInt(predictionGoalVal).toLocaleString('en-IN'));
		},
		error: function (e) {
			console.log("Exception in Calculator:: " + e);
		}
	});
}

/*SIP Calculator*/
function calaculatesip(principalyears, flag) {
	flag=!!flag;
	var data = {
			principal: getParsedValue($('#principal').val()),
			lumpsum: getParsedValue($('#lumpsum').val()),
			principalyears: principalyears,
			principalrate: $('#principalrate').val().split("%")[0],
			investedYears : parseInt($('.investYears').val()) + parseInt(principalyears),
			predictionRate : $('.predictionRate').val().split("%")[0],
			callType: "tab2"
	}

	var increasedPercent = $('.increasedPercent').attr('data-increasedPercent');
	var increasedPrincipal = (data.principal*(increasedPercent/100));

	$.ajax({
		url: "/bin/calculatorCheck",
		data: data,
		type: 'POST',
		success: function (res) {
			var result = JSON.parse(res);
			var obj = {
					totalInvstAsString: result.totalInvstAsString,
					lumptab2: result.oneTimeAsString,
					sipPredictionAsString: result.sipPredictionAsString
			}

			let totalInvstVal = parseInt(obj.totalInvstAsString) + parseInt(getParsedValue($('#lumpsum').val()));
			document.getElementById('totalInvstAsString').value = parseInt(totalInvstVal).toLocaleString('en-IN');
			document.getElementById('lumptab2').value = parseInt(obj.lumptab2).toLocaleString('en-IN');

			let sipPredVal = (obj.sipPredictionAsString - obj.lumptab2);
			$('#sipPrediction').val(parseInt(sipPredVal).toLocaleString('en-IN'));

			if(flag == false) {
				let sipVal = (obj.sipPredictionAsString - obj.lumptab2);
				$('#sipPrediction').val(parseInt(sipVal).toLocaleString('en-IN'));
			}

            setInputWidth([$('#sipPrediction')]);
            setInputWidth([$('#lumpsumyears'),$('#totalInvstAsString'),$('#lumptab2')]);
		},
		error: function (err) {
			console.log(err);
		}
	});
}

//Prediction Systematic Calculator Prediction Rate of Interest
$('.predictionRate').change(function () {
    var regex = new RegExp(',', 'g')
	var principal = getParsedValue($('#principal').val());
    principal = principal.replace(regex,'');
	var years = $('#principalyears').val();
	var rate = $('#principalrate').val().split("%")[0];
	var delYears = $('.delYears').val();
	var investYears = parseInt($('.investYears').val()) + parseInt(years);
	var lumpsum = $('#lumpsum').val();
	lumpsum = lumpsum.replace(regex,'');
	var predictionRate = $(this).val().split("%")[0];

	var data = {
			principal: principal,
			principalyears: years,
			principalrate: rate,
			delayYears: delYears,
			investedYears: investYears,
			lumpsum: lumpsum,
			predictionRate: predictionRate,
			callType: "tab2"
	}
	$.ajax({
		url: "/bin/calculatorCheck",
		method: "POST",
		data: data,
		success: function (result) {
			var res = JSON.parse(result);
			var obj = {
					sipPredictionAsString : res.sipPredictionAsString
			}
            var str=$('#lumptab2').val();//"2,33,622";

			str = str.replace(regex,'');
			//alert(str)

           // alert(parseInt(vale) + "--" + vale);
			let sipVal = (parseInt(obj.sipPredictionAsString) - parseInt(str));
			$('#sipPrediction').val(sipVal.toLocaleString('en-IN'));
		},
		error: function (err) {
			console.log(err);
		}
	});

});


//Prediction Systematic Calculator Invested Years
$('.investYears').change(function () {
	var regex = new RegExp(',', 'g')
	var principal = $('#principal').val();
	principal = principal.replace(regex,'');
	var years = $('#principalyears').val();
	var rate = $('#principalrate').val().split("%")[0];
	var investYears = parseInt($(this).val()) + parseInt(years);
	var lumpsum = $('#lumpsum').val();
    lumpsum = lumpsum.replace(regex,'');
	var predictionRate = $('.predictionRate').val().split("%")[0];

	var data = {
			principal: principal,
			principalyears: years,
			principalrate: rate,
			investedYears: investYears,
			lumpsum: lumpsum,
			predictionRate: predictionRate,
			callType: "tab2"
	}
	$.ajax({
		url: "/bin/calculatorCheck",
		method: "POST",
		data: data,
		success: function (result) {
			var res = JSON.parse(result);
			var obj = {
					sipPredictionAsString : res.sipPredictionAsString
			}
             var str=$('#lumptab2').val();//"2,33,622";

			str = str.replace(regex,'');
			let sipVal = (parseInt(obj.sipPredictionAsString) - parseInt(str));
			$('#sipPrediction').val(sipVal.toLocaleString('en-IN'));
		},
		error: function (err) {
			console.log(err)
		}
	});
});


var currentAge;
var monthlyExpense;
var retirementAge;
var yearsAfterRetirement;
var growthRate;
var predictionAge;
var presentValue;

//Retirement Calculator Function & AJAX
$('#retirementLogic').click(function () {
	if(('.monthlyExpense').val=='') {

	}
	else { 
		$('.retirementHiddenBox').show();
		$('.retirementDoYouKnow').show();
		var date = new Date();
		var currentYear = date.getFullYear();
		currentAge = $('.currentAge').val();
		monthlyExpense = getParsedValue($('.monthlyExpense').val());
		retirementAge = $('.retirementAge').val();
		var differenceAge = parseInt(retirementAge) - parseInt(currentAge);
		$('.retirementYr').val(differenceAge);
		$('.retirementNo').val(differenceAge);
		$('.oneTimeYear').val(differenceAge);
		presentValue = differenceAge;
		yearsAfterRetirement = $('.yearsAfterRetirement').val();
		growthRate = $('.growthRate').val().split("%")[0];
		predictionAge = $('.predictionAge').val();

		$.ajax({
			url: "/bin/calculatorCheck",
			method: "POST",
			data: {
				"currentAge": currentAge,
				"monthlyExpense": monthlyExpense,
				"retirementAge": retirementAge,
				"yearsAfterRetirement": yearsAfterRetirement,
				"growthRate": growthRate,
				"predictionAge": predictionAge,
				"callType": "tab3"
			},
			success: function (res) {
				var result = JSON.parse(res);
				var obj = {
						totalRetirement: result.totalRetirement,
						retirementSip: result.retirementSip,
						retirementOneTime: result.retirementOneTime,
						predictionRet: result.predictionRet
				}
				let predVal = ((obj.retirementSip)*differenceAge*12);
				$('#totalRetirement').val(parseInt(obj.totalRetirement).toLocaleString('en-IN'));
				$('#retirementSip').val(parseInt(obj.retirementSip).toLocaleString('en-IN'));
				$('#retirementOneTime').val(parseInt(obj.retirementOneTime).toLocaleString('en-IN'));
				$('#predictionRet').val(parseInt(obj.predictionRet).toLocaleString('en-IN'));
				$('#predictionInvestment').val(parseInt(predVal).toLocaleString('en-IN'));

                setInputWidth([$('#predictionInvestment'),$('.retirementYr'),$('#totalRetirement'),$('#retirementSip'),$('.retirementNo'),$('#retirementOneTime'),$('.oneTimeYear'),$('#predictionRet')]);

			},
			error: function (err) {
				console.log("Error in Retirement: " + err);
			}
		});
	}
		$('html, body').animate({
		scrollTop: $(".retirementHiddenBox").offset().top
	}, 'slow')
});

function setInputWidth($inputs) {
    $inputs.forEach(function($input) {
        $($input).attr("size", $($input).val().length );
    })
    
}

$('.retirementAge').change(function () {
	var lifeValue = $('.lifeValueDiv').attr('data-lifeExpectancy');
	var retirementAge = lifeValue - parseInt($(this).val())
	$('.yearsAfterRetirement').val(retirementAge)
	$('.oneTimeYear').val($(this).val())
});

//Prediction Retirement Age Calculations
$('.predictionAge').change(function () {
	var predAge = $('.predictionAge').val();
	var predInvest = $('#predictionInvestment').val();

	var data= {
			"currentAge": currentAge,
			"monthlyExpense": monthlyExpense,
			"retirementAge": retirementAge,
			"yearsAfterRetirement": yearsAfterRetirement,
			"growthRate": growthRate,
			"predictionAge": predAge,
			"callType": "tab3"
	}

	$.ajax({
		url: "/bin/calculatorCheck",
		method: "POST",
		data: data,
		success: function (res) {
			var result = JSON.parse(res);
			var obj = {
					predictionRet: result.predictionRet
			}
			$('#predictionRet').val(parseInt(obj.predictionRet).toLocaleString('en-IN'));

		},
		error: function (err) {
			console.log("Error in Retirement: " + err);
		}
	});

});