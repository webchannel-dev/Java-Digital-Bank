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
		
		<bean:define id="tabId" value="database"/>
		
		<%@include file="inc_breadcrumb.jsp" %>
		
		<h1>Edit Database Settings</h1>
	
		<p>Changes will not take effect until you click Submit at the end of the Setup Wizard.</p>
	
		<html:form action="setupWizardStepDatabase" method="post">
			<html:hidden property="datasourceConfig.updated" value="true" />
		

			<label>Driver:</label> <html:text property="datasourceConfig.driverParam" /> <br/>
			<label>DB URL:</label> <html:text property="datasourceConfig.urlParam" /> <br/>
			<label>User:</label> <html:text property="datasourceConfig.userParam" /> <br/>
			<label>Password:</label> <html:text property="datasourceConfig.passwordParam" /> 	<br/>	

			
			<br />
			
			<input type="submit" class="button" value="Save" /> 
			<a  class="" href="setupWizardStepDatabase">Cancel</a> 
		
		</html:form>
	
	</div>         
	
</body>
</html>