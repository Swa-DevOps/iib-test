var interactionId = '';
let countin="";
$(document).ready(function() {
	/*if (document.cookie.length != 0) {
		var namevaluepair = document.cookie.split("; ");
		for (var i = 0; i < namevaluepair.length; i++) {
			var namevaluearray = namevaluepair[i].split("=");
			if (namevaluearray[0] == "interactionId") {
				interactionId = JSON.parse(namevaluearray[1]);                
				$('#interactionFailId').html(interactionId);
			}
		}
	};*/


    countin = sessionStorage.getItem("redirectSession");

    if (countin == "" || countin == "null" || countin == null) {
 				// Nothing to perform
    } else {

        $("#ssohide1").text(""); 
        $("#ssohide2").text("You will be redirected to originated app in 5 seconds"); 
       	sessionStorage.clear();
		setTimeout(function(){
            window.location.href = countin ;
         }, 5000);

    }

});
