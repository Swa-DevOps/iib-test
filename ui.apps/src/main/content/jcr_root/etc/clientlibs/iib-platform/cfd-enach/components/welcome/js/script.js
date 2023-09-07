$(document).ready(function() {
sessionStorage.clear();
});

$(document).on('click', '.buttonUrlDiv', function() {
	var url = $(this).attr('data-landingUrl');
	var mode = url.split("/").splice(-1).toString().split(".")[0];
	
	localStorage.setItem('cfdurl',url);

	if(mode === 'create') window.location.assign('/content/cfdenach/welcome/login.html?mode='+ mode);
	else window.location.assign('/content/cfdenach/welcome/login.html?mode='+ mode);

});
