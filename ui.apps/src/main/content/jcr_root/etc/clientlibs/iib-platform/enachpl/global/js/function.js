//Loader Event
$(window).load(function(){
	$(".se-pre-con").fadeOut("fast");
});

/* Footer Functionality START */
$(document).ready(function() {	
	$('.importantNotice').hide();
	/*CC Read more 25/5/2016*/
	$(".expandParaTwo").click(function(){
		//$(".CCpara-two").slideToggle(500);
		if($(this).hasClass('active')){
			$(".CCpara-two").slideUp();
			$(this).text('Read More');
			$(this).removeClass('active');
		}
		else {
			$(".CCpara-two").slideDown();
			$(this).text('Show Less');
			$(this).addClass('active');
		}
	})

	/*Changes done on 24/03/2014 END*/
	$('.atmsLink').click(function(){
		$(this).addClass('active').siblings().removeClass('active');
		$('.atmSearchContainer').show();
		$('.atmContWrapper').show();
		$('.branchContWrapper').hide();
	});

	$('.branchesLink').click(function(){
		$(this).addClass('active').siblings().removeClass('active');
		$('.atmSearchContainer').show();
		$('.atmContWrapper').hide();
		$('.branchContWrapper').show();
	});
	/*Changes done on 24/03/2014 END*/



	$('.fLink li a').click(function(){
		$(this).parent().toggleClass('sel').siblings().removeClass('sel');
		$(this).parent().children('div').slideToggle();
		$(this).parent().siblings().children('div').slideUp('slow',function(){
			$('.iWantLocate').show().siblings().not('.closeBtn').hide();
		});
	});

	$('.footerLocateus .closeBtn').click(function(){
		$('.footerLocateus').slideUp('slow',function(){
			$('.secondFooter ul li').removeClass('sel');
			$('.iWantLocate').show().siblings().not('.closeBtn').hide();
		});
	});

	$('.exPopup .closeBtn').click(function(){
		$('.exPopup').slideUp('slow',function(){
			$('.secondFooter ul li').removeClass('sel');
		});
	});


	$('.atms').click(function(){
		$('.locateLabel').hide();
		$('.locateAtm').show().siblings().not('.closeBtn').hide();
	});

	$('.locNo').click(function(){
		$('.ipConflict').show().siblings().not('.closeBtn').hide();
	});

	$('.branches').click(function(){
		$('.locateLabel').hide();
		$('.locateBranch').show().siblings().not('.closeBtn').hide();
	});

	$('.locNoBranch').click(function(){
		$('.ipConflictBranch').show().siblings().not('.closeBtn').hide();
	});


});
/* Footer Functionality END */


function dsment() {
	$("#fadests").slideToggle();
}

function dsmentclose() {
	$("#fadests").hide();
}


$(window).load(function(){
	$(".pre-loader").fadeOut("fast");
})


$(document).ready(function(){
	$('.mobicon').click(function(){
		$('.homelink').slideToggle();
		$('#myCanvasNav').toggleClass('overlay-width');
		$('#myCanvasNav').toggleClass('overlay-width1');
	});
	$('.overlay3, .closebtn1').click(function(){		
		$('#myCanvasNav').removeClass('overlay-width');
		$('#myCanvasNav').addClass('overlay-width1');
		$('.homelink').slideUp();
	});
});



/*$(document).ready(function(){
	$('.mobright').click(function(){
		$('#mobtrn').slideToggle();
	});
});*/

//Not using anywhere!
/*$(document).ready(function() {
              var owl = $('.owl-carousel');
              owl.owlCarousel({
                margin: 10,
                nav: true,
                loop: false,
				autoHeight: true,
				URLhashListener:true,
        		autoplayHoverPause:true,
        		startPosition: 'URLHash',
                responsive: {
                  0: {
                    items: 1
                  },
                  700: {
                    items: 2
                  },
                  1000: {
                    items: 2
                  }
                }

              });
            });*/



function callmodal(){
	$(".modalwrapper").fadeIn(500,function(){
		$(".modal").css({"transform":"scale(1,1)"});	
	});
	$(".crossbtn").on("click",function(){
		$(".modal").css({"transform":"scale(0,0)"});	
		setTimeout(function(){
			$(".modalwrapper").fadeOut(500);
		},700);
	})

	$(".modalwrapper").on("click",function(e){
		if (!$(e.target).is('.modal, .modal *')) {
			$(".modal").css({"transform":"scale(0,0)"});	
			setTimeout(function(){
				$(".modalwrapper").fadeOut(500);
			},700);
		}

	})
}