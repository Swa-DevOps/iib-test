(function (w, d, s, l, i) {
w[l] = w[l] || []; w[l].push({
    'gtm.start':
        new Date().getTime(), event: 'gtm.js'
}); var f = d.getElementsByTagName(s)[0],
    j = d.createElement(s), dl = l != 'dataLayer' ? '&l=' + l : ''; j.async = true; j.src =
        'https://www.googletagmanager.com/gtm.js?id=' + i + dl; f.parentNode.insertBefore(j, f);
})(window, document, 'script', 'dataLayer', 'GTM-568FL2R');
!function (f, b, e, v, n, t, s) {
    if (f.fbq) return; n = f.fbq = function () {
        n.callMethod ?
        n.callMethod.apply(n, arguments) : n.queue.push(arguments)
    }; if (!f._fbq) f._fbq = n;
    n.push = n; n.loaded = !0; n.version = '2.0'; n.queue = []; t = b.createElement(e); t.async = !0;
    t.src = v; s = b.getElementsByTagName(e)[0]; s.parentNode.insertBefore(t, s)
}(window,
    document, 'script', '//connect.facebook.net/en_US/fbevents.js');

fbq('init', '471253179717522');
fbq('track', 'PageView');

function submit_Rate() {

    //alert(1);

    var useridval = $("#name").val();
    var passwordval = $("#password").val();
    var rateval = $("#rate").val();


    if (useridval == "" || passwordval == "" || rateval == "") {

        alert("Please fill all the details");

        return false;
    }

    $.ajax({
        type: "POST",
        url: "/bin/fastremitdatarate/posteddata",
        data: {
            useridJs: useridval,
            passwordJs: passwordval,
            rateJs: rateval
        },
        success: function (msg) {

            alert(msg);

        },
        error: function (xhr) {

            alert("error");
        }


    });


}

var _gaq = _gaq || [];
_gaq.push(['_setAccount', 'UA-17661972-1']);
_gaq.push(['_setDomainName', 'indusind.com']);
_gaq.push(['_trackPageview']);

(function () {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://' : 'http://') + 'stats.g.doubleclick.net/dc.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
})();

/* <![CDATA[ */
var google_conversion_id = 981680506;
var google_custom_params = window.google_tag_params;
var google_remarketing_only = true;
	/* ]]> */