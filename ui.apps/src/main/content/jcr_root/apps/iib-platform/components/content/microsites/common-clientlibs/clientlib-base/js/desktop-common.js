var autoTimer = 0;

function clearText(e) {
    "" == e.value ? e.value = e.defaultValue : e.value == e.defaultValue && (e.value = "")
}

function isIE(e, i) {
    var t = $('<div style="display:none;"/>').appendTo($("body"));
    t.html("\x3c!--[if " + (i || "") + " IE " + (e || "") + "]><a>&nbsp;</a><![endif]--\x3e");
    var n = t.find("a").length;
    return t.remove(), n
}

function auto() {
    var e = $(".inds_pagination ul li.active").index() + 1;
    autoTimer = setInterval(function() {
        e == $(".wrp_indsSlider img").size() && (e = 0), $(".wrp_indsSlider img").fadeOut(1e3), $(".wrp_indsSlider img").eq(e).fadeIn(1e3), $(".inds_pagination ul li").eq(e).addClass("active").siblings().removeClass("active"), e++
       }, 5e3)
}
$(window).load(function() {
    $(".rates").find("iframe").hasClass("personal") && $(".rates").find("iframe").attr("src", "/content/home/personal-banking/personalbankingtransferrate.html"), $(".rates").find("iframe").hasClass("nribanking") && $(".rates").find("iframe").attr("src", "/content/home/nri-banking/nritransferrates.html"), $(".rates").find("iframe").hasClass("corporate") && $(".rates").find("iframe").attr("src", "/content/home/corporate-banking/corporatetransferrate.html"), $(".rates").find("iframe").hasClass("government") && $(".rates").find("iframe").attr("src", "/content/home/government-and-financial-institutions/governmenttransferrate.html"), $(".rates").find("iframe").hasClass("business") && $(".rates").find("iframe").attr("src", "/content/home/rates-all-page/businessbankingtransferrate.html"), $(".siteMenu a h2").each(function() {
        $(this).is(":contains('nrolapPdf')") && $(this).hide()
    }), "http://www.indusind.com/Aadhaar" == window.location.href && (window.location.href = "http://www.indusind.com/content/dam/indusind/PDF/AadhaarNumberLinkingFormNI.pdf")
}), $(document).ready(function() {
	var url=window.location.href;
	var urlSplit = url.split('/').pop().split('#')[0].split('?')[0];
    if (urlSplit!= 'merchant-acquiring-form.html' && urlSplit!= 'DuoCardLaunch2.html' && urlSplit!='channel-blocking.html') {

     // alert(urlSplit);
       // chatBotPopup();
   		//chatBotPopupIndex();
    }


    $(".importantNotice").hide(), $(".expandParaTwo").click(function() {
        $(this).hasClass("active") ? ($(".CCpara-two").slideUp(), $(this).text("Read More"), $(this).removeClass("active")) : ($(".CCpara-two").slideDown(), $(this).text("Show Less"), $(this).addClass("active"))
    }), //$(".prdBox:contains('WelcomeKit')").parent().hide(), $(".rightLinkWrapper .expandDiv ul li a:contains('WelcomeKit')").parent().hide(), $(".prdBox:contains('Samsung Pay')").parent().hide(), $(".subCatTitle:contains('Samsung Pay Credit')").hide(), $(".prdBox:contains('IndusInd Bank PAYBACKK Credit Card')").parent().hide(), $(".rightLinkWrapper .expandDiv ul li a:contains('IndusInd Bank PAYBACKK Credit Card')").parent().hide(), $(".bxReadmore").click(function() {
		$(".prdBox:contains('WelcomeKit')").parent().hide(), $(".rightLinkWrapper .expandDiv ul li a:contains('WelcomeKit')").parent().hide(), $(".prdBox:contains('Samsung Pay')").parent().hide(), $(".subCatTitle:contains('Samsung Pay Credit')").hide(), $(".bxReadmore").click(function() {
        var e = $(this).parent().next().html();
        $(".scrollElem").html("");
        var i = $(window).width(),
            t = $(window).height();
        $(".overlay").height($(document).height()), $(".srLightboxTC").css("left", (i - $(".srLightboxTC").width()) / 2 + "px"), $(".srLightboxTC").css("top", (t - $(".srLightboxTC").height()) / 2 + "px"), $(".scrollElem").html(e), $(".srLightboxTC").show(), $(".overlay").fadeIn()
    }), $(".boxCont").hover(function() {
        $(this).find(".boxData").stop(!1, !0).animate({
            bottom: 0
        })
    }, function() {
        $(this).find(".boxData").stop(!1, !0).animate({
            bottom: "-100%"
        })
    }), $("select").change(function() {
        $(this).prev('div').html($(this).find("option:selected").text())
    }), $(".topButton").click(function() {
        $("html,body").animate({
            scrollTop: 0
        }, 1e3)
    }), $(".customerSpeaks table.offersCC").find("tr").eq(25).prevAll().show();
    var e = 25;
    $(".loadMore").click(function() {
        var i = $(".customerSpeaks table.offersCC").find("tr:visible").last().nextAll().length;
        e += 25, i <= 25 ? ($(".customerSpeaks table.offersCC").find("tr").show(), $(this).hide()) : $(".customerSpeaks table.offersCC").find("tr").eq(e).prevAll().show()
    }), $(".quikService .expandDiv li a").click(function(e) {
        if (!$(this).parents(".quikService").hasClass("creditService") && "QuickPay" != $(this).text() && "Bharat Bill Payment" != $(this).text() && "Book FD" != $(this).text() && "Quick Bill Payment" != $(this).text() && "Generate Debit Card PIN" != $(this).text() && "Aadhaar Seeding" != $(this).text()) {
            e.preventDefault();
            var i = $(this).text(),
                t = $(this).parent().find(".tooltip").text(),
                n = $(this).attr("href");
            $(".srLightbox").find("iframe").attr("src", n);
            var a = $(window).width(),
                s = $(window).height();
            $(".overlay").height($(document).height()), $(".srLightbox").css("left", (a - $(".srLightbox").width()) / 2 + "px"), $(".srLightbox").css("top", (s - $(".srLightbox").height()) / 2 + "px"), $(".srLightbox").fadeIn(), $(".overlay").fadeIn(), setTimeout(function() {
                $(".srLightbox").find("h2").text(i), $(".srLightbox").find(".srHelp").text(t)
            }, 2e3)
        }
    }), $(".srLightbox").hide(), $(".cmyk").click(function() {
        var e = $(window).width(),
            i = $(window).height();
        $(".overlay").height($(document).height()), $(".srLightbox").css("left", (e - $(".srLightbox").width()) / 2 + "px"), $(".srLightbox").css("top", (i - $(".srLightbox").height()) / 2 + "px"), $(".srLightbox").fadeIn(), $(".overlay").fadeIn()
    }), $(".callbkBtn").mouseover(function() {
        $(this).addClass("sel"), $(".callback").addClass("hover").animate({
            right: "0"
        }), clearTimeout()
    }), $(".callback").mouseleave(function() {
        $(".callbkBtn").removeClass("sel"), $(".callback").stop(!0).removeClass("hover").animate({
            right: "-320px"
        })
    }), $(".logoTabs li").click(function() {
        var e = $(this).index();
        $(this).addClass("active").siblings().removeClass("active"), $(".logoWrap").find(".logoCont").eq(e).show().siblings().hide()
    }).eq(0).click(), $(".logoThum").click(function() {
        $(".logoData").hide(), $(".logosInfo li").removeClass("sel"), $(this).parent().addClass("sel").siblings().removeClass("sel"), $(this).parent().next().show(), $("html,body").animate({
            scrollTop: $(this).offset().top
        }, 500)
    }), $(".logoData .clsBtn").click(function() {
        $(this).parent().hide(), $(this).parents(".logosInfo").find("li").removeClass("sel")
    }), $(".owl-vfapp").length >= 1 && (owl = $(".owl-vfapp"), owl.owlCarousel({
        items: 1,
        loop: !0,
        autoplay: !0,
        autoplayTimeout: 5e3,
        nav: !0,
        dots: !1
    })), $(".tablout table").bind("contextmenu", function(e) {
        e.preventDefault(), alert("Right Click is Disabled For Security Purpose")
    }), $(".formdda").click(function() {
        $("#DDAForm").show();
        var e = $("#DDAForm").offset().top;
        $("html,body").animate({
            scrollTop: e
        }, 1e3)
    }), isIE(8) && ($("body").addClass("ie8"), $(".loginWrapper").parents(".section").removeClass("section")), isIE(7) && $("body").addClass("ie7"), setTimeout(function() {
        $(":file").change(function() {
            $this = $(this), setTimeout(function() {
                var e = $this.val().split("\\"),
                    i = e[e.length - 1];
                $this.parents(".upload").find("span").html(i)
            }, 500)
        })
    }, 2e3);
    $(".ltLinkWrapper ul").find("li").index(), $(".ltLinkWrapper ul").find("li").index();



    $(".primaryNav > ul").find("li").hover(function() {
        $(this).index();
        $(this).addClass("sel").siblings().removeClass("sel"), $(this).find(".homeSubNavWrapper").slideDown().parent().siblings().find(".homeSubNavWrapper").hide(), $(this).find(".homeSubNavWrapper").find(".rtLinkWrapper > ul").eq(0).show(), $(this).find(".homeSubNavWrapper").find(".rtSubLinkWrapper > ul").eq(0).show(), $(this).find(".homeSubNavWrapper").find(".ltLinkWrapper > ul > li").eq(0).addClass("ltSelected")
    }, function() {
        $(this).removeClass("sel"), $(this).find(".homeSubNavWrapper").hide(), $(this).find(".ltLinkWrapper > ul > li").removeClass("ltSelected"), $(this).find(".rtLinkWrapper ul li").removeClass("rtSelected"), $(this).find(".rtLinkWrapper ul").hide(), $(this).find(".rtSubLinkWrapper ul").hide()
    }), $(".ltLinkWrapper > ul").find("li").hover(function() {
        var e = $(this).index();
        $(this).addClass("ltSelected").siblings().removeClass("ltSelected"), $(this).parents(".homeSubNavWrapper").find(".rtLinkWrapper ul").eq(e).show().siblings().hide(), $(".rtSubLinkWrapper div").hide(), $(".rtLinkWrapper").find("li").removeClass("rtSelected")
    }), $(".rtLinkWrapper > ul").find("li").hover(function() {
        var e = $(this).index(),
            i = $(this).parent("ul").index();
        $(this).addClass("rtSelected").siblings().removeClass("rtSelected"), $(this).parents(".homeSubNavWrapper").find(".rtSubLinkWrapper div").eq(i).show().siblings().hide(), $(this).parents(".homeSubNavWrapper").find(".rtSubLinkWrapper div").eq(i).find("ul").eq(e).show().siblings().hide()
    }), "NRI" == $(".primaryNav ul li.currentActive").find("h1").html() && ($(".secondFooter .fLink").append('<li><a title="Chat Online" href="/content/home/nri-banking/payment-services/nri-chat.html" target="_blank">Chat Online</a></li>'), 0 == $(".iWant > ul > li").eq(3).find(".subLink > ul > li").length && ($(".iWant > ul > li").eq(3).find(".subLink").remove(), $(".iWant > ul > li").eq(3).hover(function() {
        $(this).css({
            "background-color": "#fff"
        }), $(this).find("a").attr("href", "/content/home/nri-banking/payment-services/nri-chat.html")
    }))), "PERSONAL" != $(".primaryNav li.currentActive").find("h1").text() && "NRI" != $(".primaryNav li.currentActive").find("h1").text() && "BUSINESS & COMMERCIAL" != $(".primaryNav li.currentActive").find("h1").text() || $(".primaryNav > ul").append('<li class="videoBranch"><a href="/content/home/personal-banking/payment-service/services/video-branch.html" title="Video Branch"><img title="Video Branch" alt="Video Branch" src="/content/dam/indusind/desktop/video-branch-img.jpg"></a></li>'), $(".accCarousel").addClass("personalCarousel"), "CORPORATE & INSTITUTIONAL" == $(".primaryNav li.currentActive").find("h1").text() && $(".accCarousel").removeClass("personalCarousel"), -1 != navigator.userAgent.indexOf("Safari") && -1 == navigator.userAgent.indexOf("Chrome") && $("select").parents(".selectgen").addClass("safari"), $(".emailFormatter").each(function() {
        var e = $(this).text().split("{}");
        $(this).after("<a href='mailto:" + e[0] + "@" + e[1] + "' target='_blank'>" + e[0] + "@" + e[1] + "</a> "), $(this).remove()
    }), $(".prodcuctReviewWrapper .hiddencomments-customdiv").hide();
    var t = $(".prodcuctReview .hiddencomments-customdiv").length;
    x = 3, $(".prodcuctReview .hiddencomments-customdiv:lt(" + x + ")").show(), $("#showMoreComments").click(function() {
        var e = $(this);
        if (e.hasClass("lessComments")) {
            x = 3, $(".prodcuctReviewWrapper .hiddencomments-customdiv").hide(), $(".prodcuctReview .hiddencomments-customdiv:lt(" + x + ")").show(), $("#showMoreComments").removeClass("lessComments").html("See more");
            var i = e.parents(".prodcuctReview").offset().top - 74;
            $("body, html").animate({
                scrollTop: i
            }, 1e3)
        } else x = x + 3 <= t ? x + 3 : t, $(".prodcuctReview .hiddencomments-customdiv:lt(" + x + ")").show(), "block" == $(".prodcuctReviewWrapper .hiddencomments-customdiv").eq(t - 1).css("display") && $("#showMoreComments").addClass("lessComments").html("Less")
    }), $(".innerPrimNav li.premium").hover(function() {
        var e = $(".innerPrimNav li.premium img").attr("src").replace("premium-service-img1", "premium-service-img1_hover");
        $(".innerPrimNav li.premium").find("img").attr("src", e)
    }, function() {
        var e = $(".innerPrimNav li.premium img").attr("src").replace("premium-service-img1_hover", "premium-service-img1");
        $(".innerPrimNav li.premium").find("img").attr("src", e)
    }), $(".innerBtmBox:odd, .btmBox:odd").css("background-image", "none"), $(".faqsQuest:first").addClass("active"), $(".faqAns:first").show(), $(".faqsQuest").click(function() {
        $(this).hasClass("active") ? ($(this).next(".faqAns").slideUp(), $(this).removeClass("active")) : ($(this).next(".faqAns").slideDown().siblings(".faqAns").slideUp(500), $(this).addClass("active").siblings(".faqsQuest").removeClass("active"))
    }), $(".innerTopNav ul.navLink li.sel").hover(function() {
        $(this).find(".homeDropdown").stop(!1, !0).show()
    }, function() {
        $(this).find(".homeDropdown").stop(!1, !0).hide()
    });
    $(".innerPrimNavInner").find(".sel").index();
    $(".innerPrimNavInner li").click(function() {
        if (!$(this).hasClass("navigation")) {
            var e = $(this).index();
            $(".primeNavigation").eq(e).slideToggle().siblings().hide(), $(this).toggleClass("sel").siblings().removeClass("sel"), $(this).hasClass("sel")
        }
    }), $("ul.careerTab li:first").addClass("activetab"), $(".careerTab li").click(function() {
        var e = $(this).index();
        $(".careerContent").eq(e).show().siblings().hide(), $(this).animate({
            width: "279px"
        }, "fast").siblings().animate({
            width: "208px"
        }, "fast"), $(this).find("img").animate({
            width: "279px",
            height: "237px",
            "margin-top": "0px"
        }, "fast").parents("li").siblings().find("img").animate({
            width: "206px",
            height: "167px",
            "margin-top": "47px"
        }, "fast"), $(this).addClass("activetab").siblings().removeClass("activetab"), $(".careerContent:visible").find(".carrerLftNav li").eq(0).click()
    }).eq(0).click(), $(".carrerLftNav li").click(function() {
        var e = $(this).index();
        $(this).parent().next().find(".careerContentTab").eq(e).show().siblings().hide(), $(this).addClass("actleftTab").siblings().removeClass("actleftTab")
    }).eq(0).click(), $(".AccSlideContent").hide(), $(".expandAcc").click(function() {
        $(this).parents(".ListExpand").find(".AccSlideContent").slideToggle().parents(".ListExpand").siblings().find(".AccSlideContent").slideUp(), $(this).toggleClass("collapsAcc").parents(".ListExpand").siblings().find(".expandAcc").removeClass("collapsAcc")
    }), $(".applyNowSlide").click(function() {
        $(this).parents(".applyContainer").animate({
            right: "-22px"
        }, 800), $(this).removeClass("slideShow"), $(this).hide("slow"), $(this).hide(), $(this).parents(".applyContainer").find(".closeForm").show("slow")
    }), $(".closeForm").click(function() {
        $(this).parents(".applyContainer").animate({
            right: "-991px"
        }, "slow"), $(this).parents(".applyContainer").find(".applyNowSlide").show("slow"), $(this).hide()
    }), 
	
	
	
	
	$(".overlay").click(function() {
        $(".overlay.homepage, .aadharLinking").hide()
    }), $(".closeBtn-n").click(function() {
        $(".overlay.homepage, .aadharLinking").hide()}), $(".closeBtn").click(function() {
        $(".AccountLightBox, .callLightbox, .ratingLightbox, .srLightbox, .getNewUpdateLightBox, .formMessageLightbox, .reviewLightbox, .srLightboxAadhar, .srLightboxQuickpay, .srLightboxTC, .sundayblock, .aadharseedinfTnc, .aadharLinking, .sundayblock").hide(), $(".overlay").hide(), $(".fLink li").removeClass("sel"), $(".srLightbox").find("iframe").attr("src", ""), $(".srLightbox").find("h2").html(""), $(".srLightbox").find(".srHelp").html("")
    }), $(".closeQuickpay").click(function() {
        $(".srLightboxQuickpay, .overlayQuickpay").hide()
    }), $(".savingTab ul li").click(function() {
        var e = $(this).index(),
            i = $(this);
        $(".savingTabContent").eq(e).show().siblings().hide(), $(this).addClass("activetab").siblings().removeClass("activetab"), setTimeout(function() {
            i.parents(".savingTab").find(".otherRate .selectbg").removeClass("activetab")
        }, 100)
    }).eq(0).click(), $(".otherRate select").change(function() {

        var e = $(this);
        setTimeout(function() {
            e.parents(".selectbg").addClass("activetab"), e.parents(".savingTab").find("ul li").removeClass("activetab")
        }, 100)
    });
    var n = !1;
    $(".siteMenu a").click(function() {
        n = !0, setTimeout(function() {
            n = !1
        }, 200)
    }), $("ul.Mailsitemenu > li").click(function() {
        0 == n && ($(this).find(".subSitemap").slideToggle(), $(this).toggleClass("Expandmenu"))
    }), $("ul.subSitemap > li").click(function(e) {
        0 == n && (e.stopPropagation(), $(this).toggleClass("Expandmenu"), $(this).find(".subSubSitemap").slideToggle())
    }), $("ul.subSitemap > li > ul > li").click(function(e) {
        0 == n && (e.stopPropagation(), $(this).toggleClass("Expandmenu"), $(this).find(".lastLevel").slideToggle())
    }), $(".impLinks ul li:last").addClass("last");
    for (var a = Math.ceil($(this).find(".prdWrapper ul li").length / 4), s = 0; s < a; s++) {
        i = 4 * s;
        var r = ht2 = ht3 = ht4 = ht = 0;
        r = $(this).find(".prdWrapper ul li").eq(i + 0).find(".prdBox").height(), 0 != $(this).find(".prdWrapper ul li").eq(i + 1).length && (ht2 = $(this).find(".prdWrapper ul li").eq(i + 1).find(".prdBox").height()), 0 != $(this).find(".prdWrapper ul li").eq(i + 2).length && (ht3 = $(this).find(".prdWrapper ul li").eq(i + 2).find(".prdBox").height()), 0 != $(this).find(".prdWrapper ul li").eq(i + 3).length && (ht4 = $(this).find(".prdWrapper ul li").eq(i + 3).find(".prdBox").height()), r > ht2 ? ht = r : ht = ht2, ht3 > ht && (ht = ht3), ht4 > ht && (ht = ht4), $(".prdWrapper ul li").eq(i + 0).find(".prdBox").height(ht), $(".prdWrapper ul li").eq(i + 1).find(".prdBox").height(ht), $(".prdWrapper ul li").eq(i + 2).find(".prdBox").height(ht), $(".prdWrapper ul li").eq(i + 3).find(".prdBox").height(417)
    }
    if ($(".impLinks .mdlInner > ul").after("<p class='bestViewNote' style='position:absolute; top:167px; right:0px; font-size:1.1em; color:#848484'>This site is best viewed in IE9+, latest version of Firefox, Chrome, Opera & Safari browsers at 1024 x 768 resolution.</p><p class='regoffice'>Registered Office: IndusInd Bank, Pune Branch, 2401,Gen.Thimmayya Rd.(Cantonment), Pune &ndash; 411 001, India | Tel: +91 20 2623 4000 CIN: L65191PN1994PLC076333 | For any Shareholder's queries or grievances contact Mr. Raghunath Poojary at investor&#64;indusind.com</p>"), $(".contactWrapper").length && $(".contactWrapper").parents(".termCondition").removeClass("termCondition"), $(".faqHeading .exp").click(function() {
            $(this).toggleClass("active"), $(".faqInnerWrapper").slideToggle()
        }), $(".superSaverPack").length) {
        $(".innerBanner").html("");
        var o = $(".superSaverPack").html();
        $(".innerBanner").append(o), $(".superSaverPack").remove()
    }
    if ($(".wrp_indsSlider").length) {
        var l = "<ul>";
        $(".wrp_indsSlider img").each(function() {
            l += "<li></li>"
        }), l += "</ul>", $(".inds_pagination").html(l), $(".inds_pagination ul li").eq(0).addClass("active"), $(".wrp_indsSlider img").eq(0).show(), auto(), $(".inds_pagination ul li").click(function() {
            clearTimeout(autoTimer), $(".wrp_indsSlider img").fadeOut(1e3), $(".wrp_indsSlider img").eq($(this).index()).fadeIn(1e3), $(this).addClass("active").siblings().removeClass("active"), auto()
        })
    }
    $(".tabaccenture").length && ($(".innerPrimNav").remove(), $(".prdDeatilWrapper .prdDetailRight .loginWrapper").css({
        "margin-top": "0px"
    }), $(".impLinks").css({
        "margin-top": "-5px"
    }))
});
var cookieValArr = [];

function getCookie() {
    if ($(".personalised .pBox, .personalised .pBox .nav li, .personalised .pBox .navCont ul, .previewBox .box").hide(), $(".personalised .pBox").eq(5).show(), $(".personalised .pBox").eq(6).show(), $(".previewBox .box").eq(5).show(), $(".previewBox .box").eq(6).show(), $(".rates, .prevRateWrapper").removeClass("odd"), $(".personalised .activeBox").removeClass("activeBox"), $(".persBox .abc").removeClass("abc"), $(".persBox .checked").removeClass("checked"), null == $.cookie("personalizes")) return !1;
    var e = $.cookie("personalizes").split("##");
    cookieValArr = [];
    for (var i = $(".persBox").length, t = 0; t < i; t++) {
        var n = e[t].split("|");
        cookieValArr.push(n)
    }
    for (t = 0; t < i; t++)
        for (var a = cookieValArr[t].length, s = 0; s < a; s++)
            if (1 == parseInt(cookieValArr[t][s])) {
                var r = $(".personalised .pBox").eq(t);
                r.show().addClass("activeBox"), r.find(".nav").find("li").eq(s).show(), r.find(".navCont").find("ul").eq(s).show(), $(".previewBox .box").eq(t).show(), $(".persBox").eq(t).find("li").eq(s).find("label").click()
            }
    $(".personalised .activeBox").length % 2 != 0 && $(".rates, .prevRateWrapper").addClass("odd"), $(".pBox").each(function() {
        $(this).find(".nav li").click(function() {
            var e = $(this).index();
            $(this).css({
                border: "1px solid #bb4140"
            }), $(this).siblings().css({
                border: "1px solid transparent"
            }), $(this).parents(".pBox").find(".navCont ul").eq(e).show().siblings().hide()
        }).eq(0).click()
    }), applyBorder()
}

function applyBorder() {
    setTimeout(function() {
        $(".btmBrd").remove(), $(".pBox").removeClass("rtBrd"), $(".pBox:visible:odd").after("<div class='btmBrd'></div>"), $(".pBox:visible:odd").addClass("rtBrd")
    }, 1e3)
}

function getCookieQuickpayBanner() {
    null == $.cookie("QuickpayBanner") ? ($.cookie("QuickpayBanner", "true", {
        expires: 1,
        path: "/"
    }), "http://www.indusind.com/content/home/personal-banking.html" == url && ($(".overlayQuickpay, .srLightboxQuickpay").fadeIn(1e3), setTimeout(function() {
        $(".overlayQuickpay, .srLightboxQuickpay").fadeOut(1e3)
    }, 1e4))) : $(".overlayQuickpay, .srLightboxQuickpay").hide()
}

function openLightbox(e) {
    var i = $(window).width(),
        t = $(window).height();
    $(".overlay").height($(document).height()), $("." + e).css("left", (i - $("." + e).width()) / 2 + "px"), $("." + e).css("top", (t - $("." + e).height()) / 2 + "px"), "AccountLightBox" == e && $("." + e).css("top", (t - $("." + e).height()) / 2 + 50 + "px"), $("." + e).fadeIn(), $(".overlay").fadeIn()
}

function MM_openBrWindow(e, i, t) {
    window.open(e, i, t)
}

function sendMessageToUser(e, i) {
    $.ajax({
        type: "POST",
        url: "/bin/sendSMS/posteddata",
        data: {
            mobile: e,
            feedid: "344030",
            messageText: i
        },
        success: function(e) {}
    })
}

function chatBotPopup() {
    setTimeout(function() {
        console.log("testing2");
        var e = '<script src="https://chatbotuat.indusind.com/morfeuswebsdk/libs/websdk/sdk.js" id="webSdk"><\/script><script src="https://chatbotuat.indusind.com/morfeuswebsdk/js/index.js"><\/script>';
        console.log(e), $("body").append(e)
    }, 1e3)
}
$(function() {
    var e, i, t, n;
    applyBorder(), $(document).on("click", "#myRoundabout .roundabout-in-focus", function() {
        return !1
    }), $(window).on("popstate", function(e) {
        $(this);
        var i, t = window.location.href;
        i = t, $.ajax({
            headers: {
                "X-Content-Only": "true"
            },
            type: "GET",
            url: i,
            cache: !1
        }).done(function(e) {
            var i = $("#ajaxOutput", e).find("#hidden").val() - 1;
            $("#myRoundabout").find("li").eq(i).click(), $(".bannerTabs li").eq(i).click()
        })
    }), $(".bannerTabs li a").click(function(e) {
        e.preventDefault()
    }), $(".bannerTabs li").eq(0).addClass("bannerActive"), $(".bannerTabs li").click(function() {
        $(this).addClass("bannerSelTab").siblings().removeClass("bannerSelTab"), $(this).find("img").animate({
            "margin-left": "0px",
            "margin-top": "0px",
            width: "332px"
        }).parents("li").siblings().find("img").animate({
            "margin-left": "40px",
            "margin-top": "30px",
            width: "265px"
        }, function() {
            $(".bannerTabs li").removeClass("bannerActive")
        })
    }), $(".large").click(function() {
        $(document.body).css("font-size", "65%")
    }), $(".small").click(function() {
        $(document.body).css("font-size", "60%")
    }), $(".medium").click(function() {
        $(document.body).css("font-size", "62.5%")
    }), $(".secLink li").not(".subLink li").hover(function() {
        $(this).css("z-index", "1"), $(this).find(".subLink").show()
    }, function() {
        $(this).find(".subLink").hide(), $(this).css("z-index", "0")
    }), $(".search").not(".searchWrapper .search").click(function() {
        $(".searchDiv").slideToggle()
    }), $(document).on("change", ".checkbox input:checkbox", function() {
        $(this).is(":checked") ? $(this).parent().addClass("checked") : $(this).parent().removeClass("checked")
    }), $(document).on("change", ".radiobox input:radio", function() {
        $(this).is(":checked") ? $(this).parent().addClass("checked").siblings().removeClass("checked") : $(this).parent().removeClass("checked")
    }), $("input:radio").each(function() {
        $(this).is(":checked") ? $(this).parent().addClass("checked").siblings().removeClass("checked") : $(this).parent().removeClass("checked")
    }), $("input:checkbox").each(function() {
        $(this).is(":checked") ? $(this).parent().addClass("checked") : $(this).parent().removeClass("checked")
    });
    var a = $(".bannerImg li").length;
    1 != a ? $(".bannerImg li").each(function() {
        $(".pagination").append("<li></li>")
    }) : 1 == a && ($(".bannerBgContainer div").eq(0).show(), $(".bannerImg li").eq(0).show(), $(".bannerLink").attr("href", $(".bannerImg li").eq(0).find("a").attr("href")), clearInterval(n)), $(".bannerLink").css("left", ($(window).width() - $(".bannerLink").width()) / 2 + "px"), $(".pagination li").click(function() {
        clearInterval(n), $(this).addClass("sel").siblings().removeClass("sel");
        var e = $(this).index();
        $(".bannerLink").attr("href", $(".bannerImg li").eq(e).find("a").attr("href")), $(".bannerImg li").eq(e).stop(!1, !0).fadeIn(800).siblings().stop(!1, !0).fadeOut(800), $(".bannerBgContainer div").eq(e).stop(!1, !0).fadeIn(800).siblings().stop(!1, !0).fadeOut(800);
        var i = $(this).index(),
            t = $(".bannerImg li").eq(i).find("a").attr("onclick");
        $(".bannerImg li").eq(i).find("a").attr("onclick") ? $(".bannerLink").attr("onclick", t) : $(".bannerLink").removeAttr("onClick"), $(".bannerImg li").eq(e).find("a").attr("target") ? ($(".bannerLink").removeAttr("target"), $(".bannerLink").attr("target", "_blank")) : $(".bannerLink").removeAttr("target"), "#" == $(".bannerImg li").eq(e).find("a").attr("href") ? $(".bannerLink").css("cursor", "default") : $(".bannerLink").css("cursor", "pointer")
    }).eq(0).click(), 1 == a || (n = setInterval(function() {
        var e = $(".pagination li.sel").index();
        setTimeout(function() {
            var e = $(".pagination li.sel").index();
            "#" == $(".bannerImg li:visible").find("a").attr("href") ? $(".bannerLink").css("cursor", "default") : $(".bannerLink").css("cursor", "pointer"), $(".bannerImg li:visible").find("a").attr("target") ? $(".bannerLink").attr("target", "_blank") : $(".bannerLink").removeAttr("target");
            var i = $(".bannerImg li").eq(e).find("a").attr("onclick");
            $(".bannerImg li").eq(e).find("a").attr("onclick") ? $(".bannerLink").attr("onclick", i) : $(".bannerLink").removeAttr("onClick")
        }, 1e3), e == a - 1 ? ($(".pagination li").eq(0).addClass("sel").siblings().removeClass("sel"), $(".bannerLink").attr("href", $(".bannerImg li").eq(0).find("a").attr("href")), $(".bannerImg li").eq(0).stop(!1, !0).fadeIn(800).siblings().stop(!1, !0).fadeOut(800), $(".bannerBgContainer div").eq(0).stop(!1, !0).fadeIn(800).siblings().stop(!1, !0).fadeOut(800)) : ($(".pagination li").eq(e).next().addClass("sel").siblings().removeClass("sel"), $(".bannerLink").attr("href", $(".bannerImg li").eq(e).next().find("a").attr("href")), $(".bannerImg li").eq(e).next().stop(!1, !0).fadeIn(800).siblings().stop(!1, !0).fadeOut(800), $(".bannerBgContainer div").eq(e).next().stop(!1, !0).fadeIn(800).siblings().stop(!1, !0).fadeOut(800))
    }, 7e3)), "#" == $(".bannerImg li").eq(0).find("a").attr("href") ? $(".bannerLink").css("cursor", "default") : $(".bannerLink").css("cursor", "pointer");
    var s = $(".accCarousel li").length;
    $(".accCarousel li").each(function() {
        $(".carouselPagination").append("<span></span>")
    }), $(".accCarousel ul").css("width", 580 * s + "px"), $(".carouselPagination span").click(function() {
        var e = $(this).index();
        $(this).addClass("sel").siblings().removeClass("sel"), $(".accCarousel ul").animate({
            "margin-left": "-" + 580 * e + "px"
        })
    }).eq(0).click();
    var r = $(".newContentWrapper .newContent").length;
    $(".newContentWrapper .newContent").each(function() {
        $(".newsPagination").append("<span></span>")
    }), $(".newContentWrapper").css("width", 197 * r + "px"), $(".newsPagination span").click(function() {
        var e = $(this).index();
        $(this).addClass("sel").siblings().removeClass("sel"), $(".newContentWrapper").animate({
            "margin-left": "-" + 197 * e + "px"
        })
    }).eq(0).click();
    var o, l = $(".homepagenewsTicker ul li").length;
    $(".homepagenewsTicker ul").css("width", l * $(".homepagenewsTicker ul li").width() + "px"), $(".homepagenewsTickerWrapper .next").click(function() {
        if (!$(".homepagenewsTicker ul").is(":animated")) {
            $(".homepagenewsTicker ul li:first").clone().appendTo($(".homepagenewsTicker ul"));
            $(".homepagenewsTicker ul").css("margin-left");
            $(".homepagenewsTicker ul").animate({
                "margin-left": "-=983px"
            }, function() {
                $(".homepagenewsTicker ul").css("margin-left", "0px"), $(".homepagenewsTicker ul li:first-child").remove()
            })
        }
    }), $(".homepagenewsTickerWrapper .prev").click(function() {
        if ($(".homepagenewsTicker ul li").last().clone().prependTo($(".homepagenewsTicker ul")), $(".homepagenewsTicker ul li:last-child").remove(), $(".homepagenewsTicker ul").css("margin-left", "-983px"), !$(".homepagenewsTicker ul").is(":animated")) {
            $(".homepagenewsTicker ul").css("margin-left");
            $(".homepagenewsTicker ul").animate({
                "margin-left": "+=983px"
            })
        }
    }), o = setInterval(function() {
        if (!$(".homepagenewsTicker ul").is(":animated")) {
            $(".homepagenewsTicker ul li:first").clone().appendTo($(".homepagenewsTicker ul"));
            $(".homepagenewsTicker ul").css("margin-left");
            $(".homepagenewsTicker ul").animate({
                "margin-left": "-=983px"
            }, function() {
                $(".homepagenewsTicker ul").css("margin-left", "0px"), $(".homepagenewsTicker ul li:first-child").remove()
            })
        }
    }, 5e3), $(".homepagenewsTickerWrapper .prev, .homepagenewsTickerWrapper .next").click(function() {
        clearInterval(o)
    });
    var d, c = $(".tipsWrapper ul li").length;
    $(".tipsWrapper ul").css("width", c * $(".tipsWrapper ul li").width() + "px"), $(".securityTip .next").click(function() {
        if (!$(".tipsWrapper ul").is(":animated")) {
            $(".tipsWrapper ul li:first").clone().appendTo($(".tipsWrapper ul"));
            $(".tipsWrapper ul").css("margin-left");
            $(".tipsWrapper ul").animate({
                "margin-left": "-=380px"
            }, function() {
                $(".tipsWrapper ul").css("margin-left", "0px"), $(".tipsWrapper ul li:first-child").remove()
            })
        }
    }), $(".securityTip .prev").click(function() {
        if ($(".tipsWrapper ul li").last().clone().prependTo($(".tipsWrapper ul")), $(".tipsWrapper ul li:last-child").remove(), $(".tipsWrapper ul").css("margin-left", "-380px"), !$(".tipsWrapper ul").is(":animated")) {
            $(".tipsWrapper ul").css("margin-left");
            $(".tipsWrapper ul").animate({
                "margin-left": "+=380px"
            })
        }
    }), d = setInterval(function() {
        if (!$(".tipsWrapper ul").is(":animated")) {
            $(".tipsWrapper ul li:first").clone().appendTo($(".tipsWrapper ul"));
            $(".tipsWrapper ul").css("margin-left");
            $(".tipsWrapper ul").animate({
                "margin-left": "-=380px"
            }, function() {
                $(".tipsWrapper ul").css("margin-left", "0px"), $(".tipsWrapper ul li:first-child").remove()
            })
        }
    }, 5e3), $(".securityTip .prev, .securityTip .next").click(function() {
        clearInterval(d)
    }), $(".videoCallBranch").click(function() {
        var e = $(window).width(),
            i = $(window).height();
        $(".overlay").height($(document).height()), $(".videoBoxWrapper").css("left", (e - $(".videoBoxWrapper").width()) / 2 + "px"), $(".videoBoxWrapper").css("top", (i - $(".videoBoxWrapper").height()) / 2 + "px"), $(".videoBoxWrapper, .overlay").fadeIn()
    });
    var p = $(".videos li").length;
    $(".videos li, .playVideo").click(function() {
        t = $(this).find(".title").html();
        var e = $(window).width(),
            i = $(window).height();
        $(this).addClass("activeVideo").siblings().removeClass("activeVideo"), $(".overlay").height($(document).height()), $(".videoBoxWrapper").css("left", (e - $(".videoBoxWrapper").width()) / 2 + "px"), $(".videoBoxWrapper").css("top", (i - $(".videoBoxWrapper").height()) / 2 + "px"), $(".videoBoxWrapper, .overlay").fadeIn(), $(".videoBox").find("iframe").attr("src", $(this).find("img").attr("name") + "?wmode=transparent&rel=0"), $(this).hasClass("playVideo") && $(".videoBox").find("iframe").attr("src", $(this).attr("name") + "?wmode=transparent&rel=0"), $(".videoBox h2").html(t)
    }), $(".video_demo").click(function() {
        var e = $(window).width(),
            i = $(window).height();
        $(".overlay").height($(document).height()), $(".videoBoxWrapper").css("left", (e - $(".videoBoxWrapper").width()) / 2 + "px"), $(".videoBoxWrapper").css("top", (i - $(".videoBoxWrapper").height()) / 2 + "px"), $(".videoBoxWrapper, .overlay").fadeIn(), $(".videoBox").find("iframe").attr("src", "http://www.youtube.com/embed/wNk_fSPvcLQ?wmode=transparent&rel=0")
    }), $(".videoClose").click(function() {
        $(".videoBoxWrapper, .overlay, .hmvideocall").fadeOut(), $(".videoBox").find("iframe").attr("src", "")
    }), $(".nextVideo").click(function() {
        t = $(".activeVideo").next().find(".title").html(), $(".activeVideo").index() == p - 1 ? ($(".videos li").eq(0).addClass("activeVideo").siblings().removeClass("activeVideo"), i = $(".videos li").eq(0).find("img").attr("name"), $(".videoBox").find("iframe").attr("src", i + "?wmode=transparent&rel=0"), $(".videoBox h2").html($(".videos li").eq(0).find(".title").html())) : (i = $(".activeVideo").next().find("img").attr("name"), $(".videoBox").find("iframe").attr("src", i + "?wmode=transparent&rel=0"), $(".activeVideo").next().addClass("activeVideo").siblings().removeClass("activeVideo"), $(".videoBox h2").html(t))




    }), $(".prevVideo").click(function() {
        t = $(".activeVideo").prev().find(".title").html(), 0 == $(".activeVideo").index() ? ($(".videos li").eq(p - 1).addClass("activeVideo").siblings().removeClass("activeVideo"), i = $(".videos li").eq(p - 1).find("img").attr("name"), $(".videoBox").find("iframe").attr("src", i + "?wmode=transparent&rel=0"), $(".videoBox h2").html($(".videos li").eq(p - 1).find(".title").html())) : (i = $(".activeVideo").prev().find("img").attr("name"), $(".videoBox").find("iframe").attr("src", i + "?wmode=transparent&rel=0"), $(".activeVideo").prev().addClass("activeVideo").siblings().removeClass("activeVideo"), $(".videoBox h2").html(t))
    }), $(".drpdnVideoLink").click(function() {
        t = $(this).attr("title");
        var e = $(window).width(),
            i = $(window).height(),
            n = $(document).scrollTop();
        $(".overlay").height($(document).height()), $(".drpdnVideoBoxWrapper").css("left", (e - $(".drpdnVideoBoxWrapper").width()) / 2 + "px"), $(".drpdnVideoBoxWrapper").css("top", (i - $(".drpdnVideoBoxWrapper").height()) / 2 + n + "px"), $(".drpdnVideoBoxWrapper, .overlay").fadeIn(), $(".videoBox").find("iframe").attr("src", $(this).attr("name") + "?wmode=transparent&rel=0"), $(".videoBox h2").html(t)
    }), $(".videoClose").click(function() {
        $(".drpdnVideoBoxWrapper, .overlay").fadeOut(), $(".videoBox").find("iframe").attr("src", "")
    }), $(".accWrapper .videoLink a").click(function() {
        if ($(".hmvideocall").length) {
            t = $(this).attr("title");
            var e = $(window).width(),
                i = $(window).height(),
                n = $(document).scrollTop();
            $(".overlay").height($(document).height()), $(".hmpgVideoBoxWrapper").css("left", (e - $(".hmpgVideoBoxWrapper").width()) / 2 + "px"), $(".hmpgVideoBoxWrapper").css("top", (i - $(".hmpgVideoBoxWrapper").height()) / 2 + n + "px"), $(".hmpgVideoBoxWrapper, .overlay").fadeIn(), $(".videoBox").find("iframe").attr("src", $(this).attr("name") + "?wmode=transparent&rel=0"), $(".videoBox h2").html(t)
        }
    }), $(".videoClose").click(function() {
        $(".hmpgVideoBoxWrapper, .overlay").fadeOut(), $(".videoBox").find("iframe").attr("src", "")
    }), $(".goBottom").click(function() {
        $("html,body").animate({
            scrollTop: 465
        }, 1e3)
    }), $(".goTop").click(function() {
        $("html,body").animate({
            scrollTop: 0
        }, 1e3)
    }), $("select").each(function() {
        $(this).children("option").each(function() {
            $(this).attr("selected") && $(this).parents(".selectedvalue").html($(this).html())
        })
    }), 0 == $(".jet-wrapper").length && 0 == $(".laplandingrefform").length && $("select").change(function() {
        $(this).prev(".selectedvalue").html($(this).find("option:selected").text())
    }), $("#myselect").change(function() {
        $(this).prev().html($("#myselect option:selected").text())
    }), $(".personalize").click(function() {
        var e = $(window).width(),
            i = $(window).height();
        $(".overlay").height($(document).height()), $(".perLightbox").css("left", (e - $(".perLightbox").width()) / 2 + "px"), $(".perLightbox").css("top", (i - $(".perLightbox").height()) / 2 + "px"), $(".perLightbox, .overlay").fadeIn(), getCookie(), getCookie()
    }), $(".lightboxClose").click(function() {
        $(".perLightbox, .overlay").fadeOut(), getCookie(), getCookie()
    }), $(".login").find(".expandDiv").slideDown(), $(".quikService").find(".expand").removeClass("collapse"), $(".login").find(".expand").addClass("collapse"), $(".loginWrapper .expand").click(function() {
        var e = $(this).parent().index();
        3 == e ? "none" == $(this).next().css("display") ? ($(this).addClass("collapse").parent().siblings().find(".expand").removeClass("collapse"), $(this).next().slideToggle().parent().siblings().find(".expandDiv").slideUp(), $(this).parent().next().find(".expandDiv").slideUp()) : ($(".expandDiv").slideUp(), $(".login").find(".expand").addClass("collapse"), $(this).removeClass("collapse"), $(".login").find(".expandDiv").slideDown()) : 2 == e ? "none" == $(this).next().css("display") ? ($(this).addClass("collapse").parent().siblings().find(".expand").removeClass("collapse"), $(this).next().slideToggle().parent().siblings().find(".expandDiv").slideUp(), $(this).parent().next().find(".expandDiv").slideUp()) : ($(this).removeClass("collapse").parent().next().find(".expand").addClass("collapse"), $(this).next().slideUp(), $(this).parent().next().find(".expandDiv").slideDown()) : 0 == e ? "block" == $(this).next().css("display") ? ($(this).removeClass("collapse").parent().next().find(".expand").addClass("collapse"), $(this).next().slideUp(), $(this).parent().next().find(".expandDiv").slideDown()) : ($(this).addClass("collapse").parent().next().find(".expand").removeClass("collapse"), $(this).parent().siblings().find(".expand").removeClass("collapse"), $(this).next().slideDown(), $(this).parent().siblings().find(".expandDiv").slideUp()) : 1 == e && ("none" == $(this).next().css("display") ? ($(this).addClass("collapse").parent().siblings().find(".expand").removeClass("collapse"), $(this).next().slideToggle().parent().siblings().find(".expandDiv").slideUp(), $(this).parent().next().find(".expandDiv").slideUp()) : ($(".expandDiv").slideUp(), $(".login").find(".expand").addClass("collapse"), $(this).removeClass("collapse"), $(".login").find(".expandDiv").slideDown()))
    }), $(".loginWrapper .expandDiv .help").hover(function() {
        $(this).parents("li").css("z-index", "50"), $(this).find(".tooltip").stop(!0, !1).show()
    }, function() {
        $(this).parents("li").css("z-index", "10"), $(this).find(".tooltip").stop(!0, !1).hide()
    }), $(".rightLinkWrapper .expand").click(function() {
        $(this).toggleClass("collapse").siblings().not(".expandDiv").removeClass("collapse"), $(this).next().slideToggle().siblings().not(".expand").slideUp()
    }), $(".persBox label").click(function() {
        var e = $(this).parents("ul").attr("class");
        $(this).hasClass("active") ? $(this).removeClass("active") : $(this).addClass("active");
        var i = $(this).parents("ul").find(".active").length;
        $(".previewMdl").find("." + e).show(), 0 == i && $(".previewMdl").find("." + e).hide(), $(this).hasClass("active") ? $(".previewMdl").find("." + e).find(".prevDots").append("<span></span>") : $(".previewMdl").find("." + e).find(".prevDots").children("span:last").remove();
        var t = $(".selBox:visible").length;
        1 == t || 3 == t || 5 == t ? $(".prevRateWrapper").addClass("odd") : $(".prevRateWrapper").removeClass("odd")
    }), $(".personalizeBtn").click(function() {
        e = "", $(".persBox").each(function() {
            $(this).find(".active").parents("ul").attr("class"), $(this).find(".active").length;
            $(this).find("li").each(function() {
                $(this).find(".active").length ? e += "1|" : e += "0|"
            }), e += "##"
        }), $.cookie("personalizes", e, {
            expires: 1,
            path: "/"
        }), getCookie(), getCookie(), $(".perLightbox, .overlay").hide(), $("html,body").animate({
            scrollTop: 950
        }, 1e3), setTimeout(function() {
            $(".pBox").each(function() {
                var e = $(this).find(".nav li:visible").index();
                $(this).find(".navCont ul").eq(e).show().siblings().hide(), $(this).find(".navCont ul").each(function() {
                    "news" == $(this).parents(".pBox").attr("id") && (1 == $(this).find("img").width() || 0 == $(this).find("img").width()) || $(this).find("img").css({
                        width: "99px",
                        height: "66px"
                    })
                })
            })
        }, 1e3)
    }), $(".defaultBtn").click(function() {
        $(".perLightbox .persBox").each(function() {
            $(this).find("label").removeClass("active"), $(".previewBox .box").hide(), $(".previewBox .box").eq(5).show(), $(".previewBox .box").eq(6).show(), $(".prevRateWrapper").removeClass("odd"), $(".previewBox .box").find(".prevDots").html("")
        }), $(".whatsNew").removeClass("rtBrd"), $(".btmBrd").remove(), $(".rates").after("<div class='btmBrd'></div>"), $.cookie("personalizes", null, {
            expires: 1,
            path: "/"
        }), $(".perLightbox, .overlay").hide(), $("html,body").animate({
            scrollTop: 950
        }, 1e3), getCookie()
    }), $(".resetBtn").click(function() {
        $(".perLightbox .persBox").each(function() {
            $(this).find("label").removeClass("active"), $(".previewBox .box").hide(), $(".previewBox .box").eq(5).show(), $(".previewBox .box").eq(6).show(), $(".prevRateWrapper").removeClass("odd"), $(".previewBox .box").find(".prevDots").html("")
        })
    }), $(".fLink li a").click(function() {
        $(this).parent().toggleClass("sel").siblings().removeClass("sel"), $(this).parent().children("div").slideToggle(), $(this).parent().siblings().children("div").slideUp("slow", function() {
            $(".iWantLocate").show().siblings().not(".closeBtn").hide()
        })
    }), $(".footerLocateus .closeBtn").click(function() {
        $(".footerLocateus").slideUp("slow", function() {
            $(".secondFooter ul li").removeClass("sel"), $(".iWantLocate").show().siblings().not(".closeBtn").hide()
        })
    }), $(".exPopup .closeBtn").click(function() {
        $(".exPopup").slideUp("slow", function() {
            $(".secondFooter ul li").removeClass("sel")
        })
    }), $(".atms").click(function() {
        isIE(8) || isIE(7) ? ($(".locateLabel").show(), $(".ipConflict").show().siblings().not(".closeBtn").hide()) : ($(".locateLabel").hide(), $(".locateAtm").show().siblings().not(".closeBtn").hide())
    }), $(".locNo").click(function() {
        $(".ipConflict").show().siblings().not(".closeBtn").hide()
    }), $(".branches").click(function() {
        isIE(8) || isIE(7) ? ($(".locateLabel").show(), $(".ipConflictBranch").show().siblings().not(".closeBtn").hide()) : ($(".locateLabel").hide(), $(".locateBranch").show().siblings().not(".closeBtn").hide())
    }), $(".locNoBranch").click(function() {
        $(".ipConflictBranch").show().siblings().not(".closeBtn").hide()
    }), $(".atmsLink").click(function() {
        $(this).addClass("active").siblings().removeClass("active"), $(".atmSearchContainer").show(), $(".atmContWrapper").show(), $(".branchContWrapper").hide()
    }), $(".branchesLink").click(function() {
        $(this).addClass("active").siblings().removeClass("active"), $(".atmSearchContainer").show(), $(".atmContWrapper").hide(), $(".branchContWrapper").show()
    });
    $(".innerPrimNav").find(".current").index();
    $(".innerPrimNav li").not(".premium").click(function() {
        if (!$(this).hasClass("navigation"))
            if ($(this).parents(".innerPrimNav").hasClass("innerPagelink")) {
                var e = $(this).index();
                $(this).hasClass("sel"), "MediaRoom" == $(this).attr("id") ? $(this).find("a").attr("href", "/about-us/media-room.html") : ($(this).toggleClass("sel").siblings().removeClass("sel"), $(".primeNavigation").eq(e).slideToggle().siblings().hide())
            } else {
                if (0 == $(this).index()) return !1;
                0 != $(".primeNavigation:visible").length ? $(".innerPrimNav li.current").find(".arrow").show() : $(".innerPrimNav li.current").find(".arrow").hide();
                e = $(this).index();
                $(this).toggleClass("sel").siblings().removeClass("sel"), $(".primeNavigation").eq(e).slideToggle().siblings().hide()
            }
    }), $(".opnLightbox").click(function() {
        var e = $(window).width(),
            i = $(window).height(),
            t = $(document).scrollTop(),
            n = $(this).find("span").attr("class");
        $(".overlay").height($(document).height()), $("." + n).css("left", (e - 800) / 2 + "px"), $("." + n).css("top", (i - $("." + n).height()) / 2 + t + "px"), $(".overlay").fadeIn(), $("." + n).fadeIn(), $(".scroll-pane").jScrollPane({
            autoReinitialise: !0
        })
    }), $(".closeBtn").click(function() {
        $(".genericLightBox").find("iframe").attr("src", ""), $(".genericLightBox, .overlay").fadeOut()
    }), $(".giveCallWrapper").find("input:checkbox").click(function() {
        $(this).is(":checked") ? ($(this).parents(".compare").find(".comparebtn").show(), $(this).parents(".compare").find("label").hide()) : ($(this).parents(".compare").find(".comparebtn").hide(), $(this).parents(".compare").find("label").show())
    }), $(".tabWrapper li").click(function() {
        var e = $(this).index();
        $(this).addClass("selected").siblings().removeClass("selected"), $(".tabContWrapper").find(".tabCont").eq(e).show().siblings().hide(), $(".tabCont").eq(e).find(".faqsQuest").eq(0).addClass("active"), $(".tabCont").eq(e).find(".faqsQuest").eq(0).next().slideDown()
    }).eq(0).click(), $(".locateusLeft .accord").click(function() {
        $(this).addClass("active").parents("li").siblings().find(".accord").removeClass("active"), $(this).next().not(".accord").slideToggle(), $(this).parents("li").siblings().find(".addressCont").not(".accord").slideUp(), $(this).next().find(".locationbtn").removeClass("sel"), $(this).next().find(".lctExpand").hide()
    }).eq(0).click(), $(".locationbtn").click(function() {
        var e = $(this).index();
        $(this).parents(".addressCont").find(".lctExpand").eq(e).slideToggle().siblings().hide(), $(this).toggleClass("sel").siblings().removeClass("sel")
    }), $(".dirIcon").click(function() {
        $(this).addClass("sel").siblings().removeClass("sel")
    }), $(".atmSearchContainer > ul > li, .branchesContainer > ul > li").click(function() {
        var e = $(this).index();
        $(this).toggleClass("sel").siblings().removeClass("sel"), $(this).parent().next().find(".refineSearch").eq(e).slideToggle().siblings().hide()
    }), $(".swap").click(function() {
        var e = $(this).parent().find('input[name="From"]').val(),
            i = $(this).parent().find('input[name="To"]').val();
        $(this).parent().find('input[name="From"]').val(i), $(this).parent().find('input[name="To"]').val(e)
    }), $(".helpIcon").hover(function() {
        $(this).parent().find(".tooltip").stop(!0, !1).show()
    }, function() {
        $(this).parent().find(".tooltip").stop(!0, !1).hide()
    });
    var h = $(".timelineContainer ul li").length;
    $(".timelineContainer ul").css("width", 136 * h + "px"), $(".timelineContainer ul li").click(function() {
        var e = $(this).offset().left,
            i = $(this).index();
        if ($(".yearData").eq(i).fadeIn().siblings().hide(), !$(".timelineContainer ul").is(":animated")) {
            if ($(this).addClass("sel").siblings().removeClass("sel"), e > 853) {
                if ($(".timelineContainer ul li.sel").index() == h - 1) return $(".timelineWrapper .next").removeClass("active"), !1;
                $(".timelineContainer ul").animate({
                    "margin-left": "-=136px"
                })
            } else e < 315 && 0 == !parseInt($(".timelineContainer ul").css("margin-left")) && $(".timelineContainer ul").animate({
                "margin-left": "+=136px"
            });
            if ($(".timelineContainer ul li.sel").index() > 0) {
                if (h < 7) {
                    if ($(".timelineContainer ul li.sel").index() == h - 1) return $(".timelineWrapper .next").removeClass("active"), $(".timelineWrapper .prev").addClass("active"), !1;
                    $(".timelineWrapper .next").addClass("active")
                } else $(".timelineWrapper .next").addClass("active");
                $(".timelineWrapper .prev").addClass("active")
            } else 0 == $(".timelineContainer ul li.sel").index() && ($(".timelineWrapper .prev").removeClass("active"), $(".timelineWrapper .next").addClass("active"))
        }
    }).eq(0).click(), $(".timelineWrapper .next").click(function() {
        $(".timelineContainer ul li.sel").next().click()
    }), $(".timelineWrapper .prev").click(function() {
        $(".timelineContainer ul li.sel").prev().click()
    }), $(".boardofDirectCont ul li").click(function() {
        setTimeout(function() {
            var e = $(".roundabout-in-focus").index();
            $(".profile").eq(e).fadeIn("slow").siblings().not(".topArrow").hide()
        }, 1e3)
    });
    var f = $(".boardofDirectCont ul li").length;
    $(".profNext").click(function() {
        $(".roundabout-in-focus").index() == f - 1 ? $(".boardofDirectCont ul li").eq(0).click() : $(".roundabout-in-focus").next().click()



    }), $(".profPrev").click(function() {
        0 == $(".roundabout-in-focus").index() ? $(".boardofDirectCont ul li").eq(f - 1).click() : $(".roundabout-in-focus").prev().click()
    }), getCookie(), $(".viewReview").click(function() {
        var e = $(this).offset().top;
        $("html,body").animate({
            scrollTop: e - 31
        }, 1e3), $(".writeReviewWrapper").hide(), $(".prodcuctReviewWrapper").slideDown()
    }), $(".review").click(function() {
        var e = $(this).offset().top;
        $("html,body").animate({
            scrollTop: e - 10
        }, 1e3), $(".prodcuctReviewWrapper").hide(), $(".writeReviewWrapper").slideDown()
    }), $(".close").click(function() {
        $(this).parents(".reviewWrap").slideUp()
    })
}), $(window).load(function() {
    if (getCookieQuickpayBanner(), $(".hmvideocall").length) {
        var e = $(window).width();
        $(window).height();
        $(".overlay").height($(document).height()), $(".hmvideocall").css("left", (e - $(".hmvideocall").width()) / 2 + "px"), $(".hmvideocall").css("top", "50px"), $(".hmvideocall").fadeIn(), $(".overlay").fadeIn()
    }
    var i;
    (0 == $(".hmvideocall").length && ($(".accWrapper .videoLink a").attr("href", $(".accWrapper .videoLink a").attr("name")), $(".accWrapper .videoLink a").attr("target", "_blank")), $(".giveCallWrapper").find("ul li").each(function() {
        var e = $(this).find("a").attr("href");
        if (".html" == $(this).find("a").attr("href").split(".pdf")[1]) {
            var i = e.replace(".html", "");
            $(this).find("a").attr("href", i)
        }
    }), $(".importantNotice").find("ul li").each(function() {
        var e = $(this).find("a").attr("href");
        if (".html" == $(this).find("a").attr("href").split(".pdf")[1]) {
            var i = e.replace(".html", "");
            $(this).find("a").attr("href", i)
        }
    }), $(".otherRate").length) && (i = (i = window.location.href).split("?")[1], $(".savingTab ul li").eq(i - 1).click());
    $(".tabWrapper").length && (i = (i = window.location.href).split("?")[1], $(".tabWrapper li").eq(i - 1).click());
    0 == $(".innerPrimNav li.premium").find("a").length && $(".innerPrimNav li.premium").remove(), $(".rightLinkWrapper").find(".active").parents(".expandDiv").prev().click(), $(".scroll-pane").length && $(".scroll-pane").jScrollPane({
        autoReinitialise: !0
    }), $("#myRoundabout").css("visibility", "visible"), $("#myRoundabout").length && $("ul#myRoundabout").roundabout({
        minScale: .4,
        maxScale: 1,
        minOpacity: 0,
        minOpacity: 1
    });
    var t = $(".roundabout-in-focus").index();
    $(".profile").eq(t).show().siblings().not(".topArrow").hide(), 0 == $(".homeBanner").length && 0 == $(".termCondition").length && 0 == $(".formWrapper").length && 1 == $(".innerBanner").length && (0 == $(".innerPrimNav").length && 0 == $(".innerPrimNavInner").length ? $("html,body").animate({
        scrollTop: 0
    }, 10) : $("html,body").animate({
        scrollTop: 130
    }, 1e3)), 1 == $(".termCondition").length && 1 == $(".innerBanner").length && $("html,body").animate({
        scrollTop: 130
    }, 1e3), 0 == $(".innerBanner").length && $("html,body").animate({
        scrollTop: 0
    }, 10), setTimeout(function() {
        0 == $(".videoWrapper .videos").find("li").length && $(".btmBrd").last().css("background", "none")
    }, 800), $(".personalised").next().css("float", "left"), setTimeout(function() {
        $(".pBox").each(function() {
            var e = $(this).find(".nav li:visible").index();
            $(this).find(".navCont ul").eq(e).show().siblings().hide(), $(this).find(".navCont ul").each(function() {
                "news" == $(this).parents(".pBox").attr("id") && (1 == $(this).find("img").width() || 0 == $(this).find("img").width()) || $(this).find("img").css({
                    width: "99px",
                    height: "66px"
                })
            })
        })
    }, 1e3);
    var n = $(".prdWrapper").find("#hidden").val();
    if ($("#myRoundabout").find("ul li").eq(n).click(), $(".int-link-wrap > ul").eq(1).children("li").eq(1).children("a").attr("href", "/content/home/personal-banking/products/super-saver-pack.html"), $(".primeNavigation").eq(1).children().eq(6).find("li").eq(0).find("span").wrap('<a href="/content/home/personal-banking/products/super-saver-pack.html" title="Super Saver Pack"></a>'), $(".primaryNav > ul > li").eq(0).find(".rtSubLinkWrapper ul").eq(7).find("li").eq(0).before("<li><a title='Forex Cards' href='/content/home/personal-banking/products/cards/forex-card.html'>Forex Cards</a></li>"), "PERSONAL" == $(".primaryNav li.currentActive").find("h1").text() && $(".int-link-wrap > ul").eq(1).children().eq(3).find(".subLink").find("ul").eq(0).find("li").eq(0).before('<li><a title="Forex Cards" href="/content/home/personal-banking/products/cards/forex-card.html">Forex Cards</a></li>'), $(".carrerBoxes").find("li").eq(0).click(function() {
            "Genesis" == $(this).find(".prdTitle").text() && $.cookie("vision", "genesis", {
                expires: 1,
                path: "/"
            })
        }), "genesis" == $.cookie("vision")) {
        var a = $(".genesisnewern").offset().top;
        $("html,body").animate({
            scrollTop: a
        }, 800), setTimeout(function() {
            $.cookie("vision", "", {
                expires: 1,
                path: "/"
            })
        }, 500)
    }
}), $(window).load(function() {
    if (null == $.cookie("impNotice")) {
        $.cookie("impNotice", "yes", {
            expires: 1,
            path: "/"
        });
        $(".importantNotice").hide()
    }
});

//Start chatbot popup on load 15/jan/18

/*function chatBotPopup() {
    	 console.log('testing1');
         var chatBotHTML = '<script src="https://chatbotuat.indusind.com/morfeuswebsdk/libs/websdk/sdk.js" id="webSdk">';
		 //console.log(chatBotHTML);
         $('body').append(chatBotHTML);
}
function chatBotPopupIndex() {
    setTimeout(function () {
        console.log('testing2');
        var chatBotHTMLIndex = '<script src="https://chatbotuat.indusind.com/morfeuswebsdk/js/index.js"></script>';
       // console.log(chatBotHTMLIndex);
        $('body').append(chatBotHTMLIndex);
    }, 400);
};*/

// END chatbot popup on load


$(".tabWrapper li").click(function() {
        var e = $(this).index();
        $(this).addClass("selected").siblings().removeClass("selected"), $(".tabContWrapper").find(".tabCont").eq(e).show().siblings().hide(), $(".tabCont").eq(e).find(".faqsQuest").eq(0).addClass("active"), $(".tabCont").eq(e).find(".faqsQuest").eq(0).next().slideDown()
    }).eq(0).click();


$(document).ready(function() { 
//$('.subContent ul li a:contains("Email Indemnity")').hide();
//$('.iWant.secLink ul li a:contains("Email Indemnity")').hide();
$('.prdBox p:contains("InterMiles IndusInd Bank Odyssey Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("InterMiles IndusInd Bank Odyssey Credit Card")').parent().parent().next().find('.compare').hide();
$('.prdBox p:contains("InterMiles IndusInd Bank Voyage Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("InterMiles IndusInd Bank Voyage Credit Card")').parent().parent().next().find('.compare').hide();
$('.subContent ul li a:contains("Mutual Funds Disclosure")').hide();
$('.subLink ul li a:contains("Mutual Funds Disclosure")').hide();
$('.primaryNav ul li a:contains("IndusInd Bank PIONEER (UAE)")').hide();
$('.premService ul li a:contains("IndusInd Bank PIONEER (UAE)")').hide();
//$('.prdBox p:contains("DUO Card")').parent().parent().next().find('a').hide();
$('.prdBox p:contains("Legend Credit Card")').parent().parent().next().find('.applynow').hide();
//$('.prdBox p:contains("Nexxt Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("Iconia Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("Platinum Aura Edge Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("Platinum Aura Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("Jet Airways IndusInd Bank Odyssey Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("Jet Airways IndusInd Bank Voyage Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("IndusInd Bank PAYBACK Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("Platinum Select Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("Platinum Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("WorldMiles Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("Lifestyle Credit Cards")').parent().parent().next().find('.applynow').hide();	
$('.prdBox p:contains("Credit Card with traveling benefits")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("MMT Singapore Offer")').parent().parent().next().find('.applynow').text('Book Now');
$('.prdBox p:contains("IndusInd Bank Thomas Cook Signature Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("IndusInd Bank Gift Card")').parent().parent().next().find('.applynow').text('Cardholder Login');
$('.prdBox p:contains("MMT EMI Offer")').parent().parent().next().find('.applynow').text('Book Now');
$('.prdBox p:contains("GOIBIBO Offer")').parent().parent().next().find('.applynow').text('Book Now');
$('.prdBox p:contains("Legend Credit Card")').parent().parent().next().find('.applynow').hide();
$('.prdBox p:contains("PIONEER World Debit Card")').parent().parent().next().find('.applynow').text('Book Now');
$('.prdBox p:contains("MMT Gift Cards - Diwali Offer")').parent().parent().next().find('.applynow').text('Apply Now');
  //alert($('.exPopup ul li a').text());
  //$('.exPopup ul li a:contains("Feedback Form New")').hide();
});

 //setTimeout(function(){ 
    //        $('.exPopup ul li a:contains("Feedback Form New")').parent().css("display", "none");
   //    }, 1000);
   
   
   /* added for apply now*/
//var url = window.location.href;
//if(url == "https://www.indusind.com/personal-banking/products/cards/credit-cards.html")
//{	
  //alert(0);
 //$('.prdBox p:contains("DUO Card")').hide();
 
//}