<%@ page import="com.bn2web.common.exception.Bn2Exception" %>
<% response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); %>
<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- Developed by bright interactive www.bright-interactive.com 
	d1		Chris Preager 	16-May-2005		Created
	d2		Ben Browning	22-Feb-2006		HTML/CSS Tidy up
	d3		Martin Wilson	05-Dev-2006		Changed to show just error message rather than stack trace
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="webmasterEmail" settingName="webmaster-email"/>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Error</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="error"/>
	<bean:define id="pagetitle" value="Error"/>
</head>
<body id="errorPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<p>
		Sorry, an error has occurred.
	</p>
		<%@ page language="java" isErrorPage="true" %>

				<logic:present name="org.apache.struts.action.EXCEPTION">
				<bean:define id="exception2" name="org.apache.struts.action.EXCEPTION" type="java.lang.Exception"/>
				<logic:notEmpty name="exception2">
					<p>
						If this problem persists, please copy the text shown below and email it to <a href="mailto:<c:out value='${webmasterEmail}' />"/><c:out value='${webmasterEmail}' /></a>.
					</p>
					<%
						String sError = Bn2Exception.getStackTrace(exception2);
						sError = sError.replaceAll("\\\\[^:]*\\\\|/[^:]*/","[Path info removed]");  // Remove path information, for security reasons
					%>
					<div class="errorBox">
						<%=sError%>
					</div>	
				</logic:notEmpty>
				</logic:present>
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>