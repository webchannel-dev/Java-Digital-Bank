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
		
		<bean:define id="tabId" value="smtp"/>
		<%@include file="inc_breadcrumb.jsp" %>
		
		<h1>Edit SMTP Settings</h1>
	
		<p>Changes will not take effect until you click Submit at the end of the Setup Wizard.</p>
		
		<html:form action="setupWizardStepSmtp" method="post">
			<html:hidden property="smtpConfig.updated" value="true" />

			<label>Server: </label><html:text property="smtpConfig.server" /> <span class="hint">Your SMTP server name or IP</span><br/>
			<label>Port:</label> <html:text property="smtpConfig.port" /> <span class="hint">Your SMTP server port (leave blank for default 25)</span><br/>
			<label>User:</label> <html:text property="smtpConfig.username" /> <span class="hint">Optional: SMTP authentication username </span><br/>
			<label>Password:</label> <html:text property="smtpConfig.password" /> 	<br/>	
	
			<br/>
			
			<input type="submit" class="button flush floated" value="Save" /> 
			<a href="setupWizardStepSmtp">Cancel</a> 
		</html:form>
	
	</div>         
	
</body>
</html>