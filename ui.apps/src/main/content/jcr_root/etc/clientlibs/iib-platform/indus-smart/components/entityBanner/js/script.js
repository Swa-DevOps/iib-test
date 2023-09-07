function tprsvdeosw(head, htm) {
    $("#gettouchform").css("display", "block");
    $("#video").attr("src", htm);
    $("#head").html(head);
    $(".contact_form").removeClass("pop_hide");
    $(".contact_form").addClass("pop_show")
    jQuery('.home').addClass('overflow-hidden');
}

function gettouch_hide() {
    $(".contact_form").addClass("pop_hide");
    jQuery('.home').removeClass('overflow-hidden');
    setTimeout(function() {
        $("#gettouchform").css("display", "none")
    }, 300);
    location.reload();
}