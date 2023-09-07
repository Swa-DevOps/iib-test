var winW;
var winH; /*changes done for new section*/

$(function() {
    winW = $(window).width();
    winH = $(window).height(); /*changes done for new section*/
    window.onload = function() {
     const myInput = document.getElementById('datepicker');
     myInput.onpaste = function(e) {
       e.preventDefault();
     }
    }


    isTouch = /Android|webOS|iPhone|iPad|iPod|BlackBerry/i.test(navigator.userAgent);
    var isIOS = /iPhone|iPad|iPod|Macintosh/i.test(navigator.userAgent);

    if (isTouch || $(window).width() <= 1024) {
        $("html").addClass("touchDevice");
        $('#datepicker').attr('type','date');
		 dateMaxValue();

    } else {
        $("html").addClass("nonTouch");

        $( "#datepicker" ).datepicker({
          showOn: "button",
          buttonImage: "/content/dam/indusind/life-a-high-life/date-picker.png",
          buttonImageOnly: true,
          buttonText: "Select date",
          changeMonth: true,
          changeYear: true, 
          dateFormat: "dd/mm/yy", 
          yearRange: "-100:+0",
          maxDate: -1,
         onSelect: function(dateText) {
             $('.dateError').hide(); 
            //$(this).blur();
           // inputBlur();
         }
        });
    }


    /*changes done for new section*/

});



function dateMaxValue() {
	var dtToday = new Date();

    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate()-1;
    var year = dtToday.getFullYear();
    if(month < 10)
        month = '0' + month.toString();
    if(day < 10)
        day = '0' + day.toString();

    var maxDate = year + '-' + month + '-' + day;
    $('#datepicker').attr('max', maxDate);
}