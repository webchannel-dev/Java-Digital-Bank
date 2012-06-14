<%@include file="../../../standard/en/inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Francis Devereux	01-Mar-2011		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting settingName="supportMultiLanguage" id="supportMultiLanguage"/>

<head>	
	<title>Tomcat Working Directory</title>
	<%@include file="../../../standard/en/inc/head-elements.jsp"%>
	<bean:define id="section" value="sysadmin"/>
</head>

<body id="inDesignJobsPage">
	<%@include file="../../../standard/en/inc/body_start.jsp"%>
		
	<h1>Tomcat Working Directory</h1>

	<%-- Avoid information leak if the user is not an admin - not sufficient
		 to check in an action before this page because users can go to JSPs
		 directly by editing the URL in their browser. --%>
	<c:if test="${not empty userprofile and userprofile.isAdmin}">
		<p>JSP classes are loaded from <%= getClass().getProtectionDomain().getCodeSource().getLocation() %></p>
	</c:if>
		
	<%@include file="../../../standard/en/inc/body_end.jsp"%>
</body>
</html>