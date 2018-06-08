package cn.songm.common.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class Browser {

	public static final String COOKIE_SESSIONID_KEY = "songmsso_sessionid";
	public static final String HEADER_SESSIONID_KEY = "Songmsso-Sessionid";

	/**
	 * 获取客户端唯一Id
	 * 
	 * @return
	 */
	public static String getSessionId(HttpServletRequest request) {
		Cookie c = CookieUtils.getCookieByName(request, COOKIE_SESSIONID_KEY);
		String sessionId = null;
		if (c != null) {
			sessionId = c.getValue();
		}
		if (sessionId == null) {
			sessionId = request.getParameter(COOKIE_SESSIONID_KEY);
		}
		if (sessionId == null) {
			sessionId = request.getHeader(HEADER_SESSIONID_KEY);
		}
		return sessionId;
	}
}
