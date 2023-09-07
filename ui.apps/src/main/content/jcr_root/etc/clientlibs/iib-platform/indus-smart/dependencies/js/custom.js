(function($) {

	"use strict";
	// DETECT TOUCH DEVICE //
	function is_touch_device() {
		return !!('ontouchstart' in window) || (!!('onmsgesturechange' in window) && !!window.navigator.maxTouchPoints);
	}

	// STICKY //
	function sticky() {

		var sticky_point = $("#header-sticky").innerHeight() + 50;

		$(window).scroll(function() {

			if ($(window).scrollTop() > sticky_point) {
				$("#header-sticky").addClass("header-sticky").css({ "display": "block" }).fadeIn(500);a
				$("#header .top-navbar").css({ "visibility": "hidden" });
			} else {
				$("#header-sticky").removeClass("header-sticky").css({ "display": "block" });
				$("#header .top-navbar").css({ "visibility": "visible" });
			}

		});

	}

	// SHOW/HIDE SCROLL UP //
	function show_hide_scroll_top() {

		if ($(window).scrollTop() > $(window).height() / 2) {
			$("#scroll-up").fadeIn(300);
		} else {
			$("#scroll-up").fadeOut(300);
		}

	}

	// SCROLL UP //
	function scroll_up() {

		$("#scroll-up").on("click", function() {
			$("html, body").animate({
				scrollTop: 0
			}, 800);
			return false;
		});

	}

	// ANIMATIONS //
	function animations() {

		if (typeof WOW !== 'undefined') {

			animations = new WOW({
				boxClass: 'wow',
				animateClass: 'animated',
				offset: 100,
				mobile: false,
				live: true
			});

			animations.init();

		}

	}

	// SMOOTH SCROLLING //
	function smooth_scrolling() {

		$("#next-section").on("click", function() {

			if (location.pathname.replace(/^\//, '') === this.pathname.replace(/^\//, '') && location.hostname === this.hostname) {

				var target = $(this.hash);
				target = target.length ? target : $('[name=' + this.hash.slice(1) + ']');

				if (target.length) {
					$('html,body').animate({
						scrollTop: target.offset().top - 100
					}, 500);

					return false;

				}

			}

		});

	}


//	Change The tab of goal-calculater page
	$(window).load(function() {

		// Javascript to enable link to tab
		var url = document.location.toString();
		if (url.match('#')) {
			$('.nav-tabs a[href="#' + url.split('#')[1] + '"]').trigger('click');
	}
    });
	// end js


	// end js
	// DOCUMENT READY //


     // Loader Event
        $(window).load(function(){
            $(".se-pre-con").fadeOut("fast");
        });

	$(document).ready(function() {


		// home multiple section scroll
		$(window).scroll(function() {
			var sec1 = $('#sec1').offset().top,
			sec2 = $('#sec2').offset().top,
			sec3 = $('#sec3').offset().top,
			st = $(window).scrollTop(),
			scrollDistance = st + $(window).height() - 100;

			if (scrollDistance >= sec1 && scrollDistance < sec2) {
				// setInterval(function() { $("#sec1").addClass('nav-dark'); }, 2000);
				$("#sec1").addClass('nav-dark');
			} else if (scrollDistance >= sec2 && scrollDistance < sec3) {
				// setInterval(function() { $("#sec2").addClass('nav-dark'); }, 2000);
				$("#sec2").addClass('nav-dark');
				$("#sec1").removeClass('nav-dark');
			} else if (scrollDistance >= sec3) {
				// setInterval(function() { $("#sec3").addClass('nav-dark'); }, 2000);
				$("#sec3").addClass('nav-dark');
				$("#sec2").removeClass('nav-dark');
				$("#sec1").removeClass('nav-dark');
			} else {
				$("#sec3").removeClass('nav-dark');
				$("#sec2").removeClass('nav-dark');
				$("#sec1").removeClass('nav-dark');
			}

		});



		$('html,body').animate({
			scrollTop: $(window).scrollTop() + 1
		}, 1000);

		$(".owl-carousel.services-slider").owlCarousel({
			autoplay: false,
			autoplayTimeout: 3000,
			autoplayHoverPause: true,
			smartSpeed: 1200,
			loop: true,
			nav: true,
			navText: true,
			items: 2,
			dots: false,
			mouseDrag: true,
			touchDrag: true,
			center: false,
			responsive: {
				0: {
					items: 1
				},
				1280: {
					items: 2
				},
				900: {
					items: 1
				},
				767: {
					items: 1
				},
				640: {
					items: 2
				}
			}
		});
		$(".owl-carousel.articleOne").owlCarousel({
			autoplay: false,
			autoplayTimeout: 3000,
			autoplayHoverPause: true,
			smartSpeed: 1200,
			loop: true,
			nav: true,
			navText: true,
			items: 3,
			dots: false,
			mouseDrag: true,
			touchDrag: true,
			center: false,
			responsive: {
				0: {
					items: 1
				},
				1280: {
					items: 3
				},
				1024: {
					items: 2
				},
				768: {
					items: 1
				},
				568: {
					items: 2
				}
			}
		});

		// FEATURE CAROUSEL //
		$('.carosoul_video').slick({
			infinite: true,
			slidesToShow: 1,
			slidesToScroll: 1,
			arrows: false,
			dots: false,
			fade: true,
			asNavFor: '.carousel-home'
		});
		$('.carousel-home').slick({
			infinite: true,
			slidesToShow: 3,
			slidesToScroll: 1,
			asNavFor: '.carosoul_video',
			arrows: true,
			dots: false,
			speed: 2000,
			autoplay: true,
			autoplaySpeed: 2500,
			centerMode: true,
			focusOnSelect: true,
			responsive: [{
				breakpoint: 1280,
				settings: {
					centerMode: false,
					slidesToShow: 1
				}
			},
			{
				breakpoint: 767,
				settings: {
					centerMode: true,
					slidesToShow: 3,
					margin: 30,
				}
			},
			{
				breakpoint: 520,
				settings: {
					centerMode: false,
					slidesToShow: 1,
				}
			}
			]
		});


		// STICKY //
		sticky();


		// SHOW/HIDE SCROLL UP
		show_hide_scroll_top();

		// YOUTUBE PLAYER //
		if (typeof $.fn.mb_YTPlayer !== 'undefined') {

			$(".youtube-player").mb_YTPlayer();

		}

		// ANIMATIONS //
		animations();
		// SMOOTH SCROLLING
		smooth_scrolling();

	});

	// WINDOW SCROLL //
	$(window).scroll(function() {
		show_hide_scroll_top();

	});

    //prevent the "normal" behaviour which would be a "hard" jump
	$(document).ready(function() {
		$('html, body').hide();

		if (window.location.hash) {
			setTimeout(function() {
				$('html, body').scrollTop(10).show();
				$('html, body').animate({
					scrollTop: $(window.location.hash).offset().top - (2*$('#header-sticky').height())
				}, 1000)
			}, 0);
		} else {
			$('html, body').show();
		}
        $(document).on('click', 'a.help', function () {
            var hash = $(this).attr('href').split('#'),
            href = window.location.href;
            if(href.indexOf(hash[0]) > -1) {
                $('html, body').animate({
                	scrollTop: $('#'+hash[1]).offset().top - (2*$('#header-sticky').height())
                },1000);
            }
		});
	});

	// SCROLL UP //
	scroll_up();


/*For Mobile View*/

        function setStorage(name, value) {

        sessionStorage.setItem(name, value);
    }
    function getStorage(name){
        return sessionStorage.getItem(name)
    }
    function urlParam(name) {
        var results = new RegExp('[\?&]' + name + '=([^&#]*)').exec(window.location.href);
        if (results == null) {
            return null;
        } else {
            return decodeURI(results[1]) || 0;
        }
    }

    function checkWebview() {
        if(urlParam("isMobile")) {
            setStorage("isMobile", urlParam("isMobile"));
        }
        var isMobile=getStorage("isMobile");
        if (isMobile === "true") {
			$('.top-navbar,.tg-navbar-search, .tg-social-box,.morph-menu-button-label, footer').remove();
            $('#menu-item-4').remove();
            $('.breadcrumb').addClass('mt-4');
            var link =  $('.article-flex-box .article-content.content-right a, .services-slider .invest-now');
            link.each(function(index, element) {
                var $element = $(element);
                 $element.next('br').remove();
                $element.after("<div class='text-uppercase'> " + $element.text() +"</div>");
                $element.remove();
            });
            updateHamburgMenu('#menu-item-2', '#menu-item-3');
    		$('#menu-item-1').remove();
        }
    }

    function updateHamburgMenu(item1, item2) {
        var $item1 = $(item1),
            $item2 = $(item2);
		$item1.before($item2);
    } 
    checkWebview();


/* For Mobile View End */   


 $(document).ready(function(){


       		var noOfSlides =6;

           	var isMobile=getStorage("isMobile");


                if(isMobile=="true" && noOfSlides > 5 )

            {

              $('.carousel-home').slick('slickRemove',5);

              	noOfSlides--;

            }


              if(isMobile=="true" && noOfSlides >= 5 )


            {

              $('.carousel-home').slick('slickRemove',2);

              noOfSlides--;

            }


        });

})(window.jQuery);