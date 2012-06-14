<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- Developed by bright interactive www.bright-interactive.com 
	d1			Steve Bryan		30-May-2011		created to work without managers or DB.	
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<%--  NOTE: This page should work when manager initialisation and DB connection has failed 
	- so please do NOT add references to CMS items or other manager features! Could just add some CSS styling... --%>

<head>	
	<title>Asset Bank</title>

	<%@include file="inc_styles.jsp" %>
	
</head>

<body id="errorPage">
    
	<div class="wrapper">  
	
		<bean:define id="tabId" value="finished"/>
		<%@include file="inc_breadcrumb.jsp" %>
		
		<h1>Updated Your Settings</h1>
	
		<p>Your settings have been updated.</p>
		<p>In order to test the new settings you should now <a href="<bean:write name="setupWizardForm" property="tomcatReloadUrl"/>">restart the application </a>.</p>
		<p>
			You will be prompted for your Tomcat Manager username and password, if you have not previously entered and saved them in this browser.<br/>
			When the application has successfully restarted you should see the response 
			<p>
				&quot;OK - Reloaded application at context path...&quot;
			</p>
			Upon seeing this you can access the application via the usual URL.
		</p>
		<p>
			Alternatively, you can restart the Tomcat service.
		</p>

	</div>         
	
</body>
</html>