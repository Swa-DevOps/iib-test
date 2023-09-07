$(document).ready(function() {
sessionStorage.clear();
});

$(document).on('click', '.buttonUrlDiv', function() {
	var url = $(this).attr('data-landingUrl');
	var mode = url.split("/").splice(-1).toString().split(".")[0];
	
	localStorage.setItem('url',url);

	if(mode === 'create') window.location.assign('/content/enach/welcome/login.html?mode='+ mode);
	else window.location.assign('/content/enach/welcome/login.html?mode='+ mode);

});
