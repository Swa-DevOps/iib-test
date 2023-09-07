package com.iib.platform.api.response;

public class ResponseHeader {

	/* Access-Control-Allow-Origin: * */
	public static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin";
	private String accessControlAllowOrigin;

	/* Access-Control-Allow-Origin: * */
	public static final String ACCESS_CONTROL_ALLOW_CREDENTIALS = "Access-Control-Allow-Credentials";
	private String accessControlAllowCredentials;

	/* Access-Control-Allow-Origin: * */
	public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
	private String accessControlExposeHeaders;

	/* Access-Control-Allow-Origin: * */
	public static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age";
	private String accessControlMaxAge;

	/* Access-Control-Allow-Origin: * */
	public static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods";
	private String accessControlAllowMethods;

	/* Access-Control-Allow-Origin: * */
	public static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers";
	private String accessControlAllowHeaders;

	/* Accept-Patch: text/example;charset=utf-8: * */
	public static final String ACCEPT_PATCH = "Accept-Patch";
	private String acceptPatch;

	/* Accept-Ranges:bytes */
	public static final String ACCEPT_RANGES = "Accept-Ranges";
	private String acceptRanges;

	/* Age: 12 */
	public static final String AGE = "Age";
	private String rage;

	/* Allow: GET, HEAD */
	public static final String ALLOW = "Allow";
	private String rallow;

	/* Alt-Svc: http/1.1="http2.example.com:8001"; ma=7200 */
	public static final String ALT_SVC = "Alt-Svc";
	private String altSvc;

	/* Cache-Control: max-age=3600 */
	public static final String CACHE_CONTROL = "Cache-Control";
	private String cacheControl;

	/* Connection: close */
	public static final String CONNECTION = "Connection";
	private String rconnection;

	/* Content-Disposition: attachment; filename="fname.ext" */
	public static final String CONTENT_DISPOSITION = "	Content-Disposition";
	private String contentDisposition;

	/* Content-Encoding: gzip */
	public static final String CONTENT_ENCODING = "Content-Encoding";
	private String contentEncoding;

	/* Content-Language: da */
	public static final String CONTENT_LANGUAGE = "	Content-Language";
	private String contentLanguage;

	/* Content-Length: 348 */
	public static final String CONTENT_LENGTH = "Content-Length";
	private String contentLength;

	/* Content-Location: /index.htm */
	public static final String CONTENT_LOCATION = "	Content-Location";
	private String contentLocation;

	/* Content-MD5: Q2hlY2sgSW50ZWdyaXR5IQ== */
	public static final String CONTENT_MD5 = "Content-MD5";
	private String contentMD5;

	/* Content-Range: bytes 21010-47021/47022 */
	public static final String CONTENT_RANGE = "Content-Range";
	private String contentRange;

	/* Content-Type: text/html; charset=utf-8 */
	public static final String CONTENT_TYPE = "Content-Type";
	private String contentType;

	/* Date: Tue, 15 Nov 1994 08:12:31 GMT */
	public static final String DATE = "Date";
	private String rdate;

	/* ETag: "737060cd8c284d8af7ad3082f209582d" */
	public static final String ETAG = "ETag";
	private String reTag;

	/* Expires: Thu, 01 Dec 1994 16:00:00 GMT */
	public static final String EXPIRES = "Expires";
	private String rexpires;

	/* Last-Modified: Tue, 15 Nov 1994 12:45:26 GMT */
	public static final String LAST_MODIFIED = "Last-Modified";
	private String lastModified;

	/* Link: </feed>; rel="alternate" */
	public static final String LINK = "Link";
	private String rlink;

	/* Location: http://www.w3.org/pub/WWW/People.html */
	public static final String LOCATION = "Location";
	private String rlocation;

	/*
	 * P3P:
	 * CP="This is not a P3P policy! See http://www.google.com/support/accounts/bin/answer.py?hl=en&answer=151657 for more info."
	 */
	public static final String P3P = "P3P";
	private String rp3p;

	/* Pragma: no-cache */
	public static final String PRAGMA = "Pragma";
	private String rpragma;

	/* Proxy-Authenticate: Basic */
	public static final String PROXY_AUTHENTICATE = "Proxy-Authenticate	";
	private String proxyAuthenticate;

	public static final String PUBLIC_KEY_PINS = "Public-Key-Pins";
	private String publicKeyPins;

	/* Retry-After: Fri, 07 Nov 2014 23:59:59 GMT */
	public static final String RETRY_AFTER = "Retry-After";
	private String retryAfter;

	/* Server: Apache/2.4.1 (Unix) */
	public static final String SERVER = "Server";
	private String rserver;

	/* Set-Cookie: UserID=JohnDoe; Max-Age=3600; Version=1 */
	public static final String SET_COOKIE = "Set-Cookie";
	private String setCookie;

	/* Strict-Transport-Security: max-age=16070400; includeSubDomains */
	public static final String STRICT_TRANSPORT_SECURITY = "Strict-Transport-Security";
	private String strictTransportSecurity;

	/* Trailer: Max-Forwards */
	public static final String TRAILER = "Trailer";
	private String rtrailer;

	/* Transfer-Encoding: chunked */
	public static final String TRANSFER_ENCODING = "Transfer-Encoding";
	private String transferEncoding;

	/* Tk: ? */
	public static final String TK = "Tk";
	private String rtk;

	/* Upgrade: HTTPS/1.3, IRC/6.9, RTA/x11, websocket */
	public static final String UPGRADE = "Upgrade";
	private String rupgrade;

	/* Vary: Accept-Language */
	public static final String VARY = "Vary";
	private String rvary;

	/* Via: 1.0 fred, 1.1 example.com (Apache/1.1) */
	public static final String VIA = "Via";
	private String rvia;

	/* Warning: 199 Miscellaneous warning */
	public static final String WARNING = "Warning";
	private String rwarning;

	/* WWW-Authenticate: Basic */
	public static final String WWW_AUTHENTICATE = "WWW-Authenticate";
	private String wwwAuthenticate;

	/* X-Frame-Options: deny */
	public static final String X_FRAME_OPTIONS = "X-Frame-Options";
	private String xFrameOptions;

	public String getAccessControlAllowCredentials() {
		return accessControlAllowCredentials;
	}

	public void setAccessControlAllowCredentials(String accessControlAllowCredentials) {
		this.accessControlAllowCredentials = accessControlAllowCredentials;
	}

	public String getAccessControlAllowOrigin() {
		return accessControlAllowOrigin;
	}

	public void setAccessControlAllowOrigin(String accessControlAllowOrigin) {
		this.accessControlAllowOrigin = accessControlAllowOrigin;
	}

	public String getAccessControlExposeHeaders() {
		return accessControlExposeHeaders;
	}

	public void setAccessControlExposeHeaders(String accessControlExposeHeaders) {
		this.accessControlExposeHeaders = accessControlExposeHeaders;
	}

	public String getAccessControlMaxAge() {
		return accessControlMaxAge;
	}

	public void setAccessControlMaxAge(String accessControlMaxAge) {
		this.accessControlMaxAge = accessControlMaxAge;
	}

	public String getAccessControlAllowMethods() {
		return accessControlAllowMethods;
	}

	public void setAccessControlAllowMethods(String accessControlAllowMethods) {
		this.accessControlAllowMethods = accessControlAllowMethods;
	}

	public String getAccessControlAllowHeaders() {
		return accessControlAllowHeaders;
	}

	public void setAccessControlAllowHeaders(String accessControlAllowHeaders) {
		this.accessControlAllowHeaders = accessControlAllowHeaders;
	}

	public String getAcceptPatch() {
		return acceptPatch;
	}

	public void setAcceptPatch(String acceptPatch) {
		this.acceptPatch = acceptPatch;
	}

	public String getAcceptRanges() {
		return acceptRanges;
	}

	public void setAcceptRanges(String acceptRanges) {
		this.acceptRanges = acceptRanges;
	}

	public String getAge() {
		return rage;
	}

	public void setAge(String age) {
		this.rage = age;
	}

	public String getAllow() {
		return rallow;
	}

	public void setAllow(String allow) {
		this.rallow = allow;
	}

	public String getAltSvc() {
		return altSvc;
	}

	public void setAltSvc(String altSvc) {
		this.altSvc = altSvc;
	}

	public String getCacheControl() {
		return cacheControl;
	}

	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}

	public String getConnection() {
		return rconnection;
	}

	public void setConnection(String connection) {
		this.rconnection = connection;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public String getContentEncoding() {
		return contentEncoding;
	}

	public void setContentEncoding(String contentEncoding) {
		this.contentEncoding = contentEncoding;
	}

	public String getContentLanguage() {
		return contentLanguage;
	}

	public void setContentLanguage(String contentLanguage) {
		this.contentLanguage = contentLanguage;
	}

	public String getContentLength() {
		return contentLength;
	}

	public void setContentLength(String contentLength) {
		this.contentLength = contentLength;
	}

	public String getContentLocation() {
		return contentLocation;
	}

	public void setContentLocation(String contentLocation) {
		this.contentLocation = contentLocation;
	}

	public String getContentMD5() {
		return contentMD5;
	}

	public void setContentMD5(String contentMD5) {
		this.contentMD5 = contentMD5;
	}

	public String getContentRange() {
		return contentRange;
	}

	public void setContentRange(String contentRange) {
		this.contentRange = contentRange;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getDate() {
		return rdate;
	}

	public void setDate(String date) {
		this.rdate = date;
	}

	public String geteTag() {
		return reTag;
	}

	public void seteTag(String eTag) {
		this.reTag = eTag;
	}

	public String getExpires() {
		return rexpires;
	}

	public void setExpires(String expires) {
		this.rexpires = expires;
	}

	public String getLastModified() {
		return lastModified;
	}

	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}

	public String getLink() {
		return rlink;
	}

	public void setLink(String link) {
		this.rlink = link;
	}

	public String getLocation() {
		return rlocation;
	}

	public void setLocation(String location) {
		this.rlocation = location;
	}

	public String getP3p() {
		return rp3p;
	}

	public void setP3p(String p3p) {
		this.rp3p = p3p;
	}

	public String getPragma() {
		return rpragma;
	}

	public void setPragma(String pragma) {
		this.rpragma = pragma;
	}

	public String getProxyAuthenticate() {
		return proxyAuthenticate;
	}

	public void setProxyAuthenticate(String proxyAuthenticate) {
		this.proxyAuthenticate = proxyAuthenticate;
	}

	public String getPublicKeyPins() {
		return publicKeyPins;
	}

	public void setPublicKeyPins(String publicKeyPins) {
		this.publicKeyPins = publicKeyPins;
	}

	public String getRetryAfter() {
		return retryAfter;
	}

	public void setRetryAfter(String retryAfter) {
		this.retryAfter = retryAfter;
	}

	public String getServer() {
		return rserver;
	}

	public void setServer(String server) {
		this.rserver = server;
	}

	public String getSetCookie() {
		return setCookie;
	}

	public void setSetCookie(String setCookie) {
		this.setCookie = setCookie;
	}

	public String getStrictTransportSecurity() {
		return strictTransportSecurity;
	}

	public void setStrictTransportSecurity(String strictTransportSecurity) {
		this.strictTransportSecurity = strictTransportSecurity;
	}

	public String getTrailer() {
		return rtrailer;
	}

	public void setTrailer(String trailer) {
		this.rtrailer = trailer;
	}

	public String getTransferEncoding() {
		return transferEncoding;
	}

	public void setTransferEncoding(String transferEncoding) {
		this.transferEncoding = transferEncoding;
	}

	public String getTk() {
		return rtk;
	}

	public void setTk(String tk) {
		this.rtk = tk;
	}

	public String getUpgrade() {
		return rupgrade;
	}

	public void setUpgrade(String upgrade) {
		this.rupgrade = upgrade;
	}

	public String getVary() {
		return rvary;
	}

	public void setVary(String vary) {
		this.rvary = vary;
	}

	public String getVia() {
		return rvia;
	}

	public void setVia(String via) {
		this.rvia = via;
	}

	public String getWarning() {
		return rwarning;
	}

	public void setWarning(String warning) {
		this.rwarning = warning;
	}

	public String getWwwAuthenticate() {
		return wwwAuthenticate;
	}

	public void setWwwAuthenticate(String wwwAuthenticate) {
		this.wwwAuthenticate = wwwAuthenticate;
	}

	public String getxFrameOptions() {
		return xFrameOptions;
	}

	public void setxFrameOptions(String xFrameOptions) {
		this.xFrameOptions = xFrameOptions;
	}

}
