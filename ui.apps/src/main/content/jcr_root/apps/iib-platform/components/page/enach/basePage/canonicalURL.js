'use strict';
use(function () {

    var pageUrl = ''; 
	var replace ="/content/indusind-corporate/"
	var replaceWith ="/in/";
    var domainToappend="https://bank.indusind.com"
    var page = currentPage.getPath()+".html";
    page=page.replace(replace,replaceWith);
    page=domainToappend+page;

    pageUrl = page;



	return pageUrl;
});
