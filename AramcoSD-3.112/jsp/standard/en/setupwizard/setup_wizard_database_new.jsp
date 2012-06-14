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
		
		<h1>New Database Settings</h1>
	
		<p>Create a new datasource configuration.</p> 
	
		<html:form action="setupWizardStepDatabaseNewDetails" method="post">
	
			<label style="width:120px">Select database type:</label>
			<html:select property="datasourceConfig.friendlyConfig.code">
				<c:forEach items="${setupWizardForm.datasourceConfig.types}" var="typeConfig">
					<option value="<c:out value='${typeConfig.code}' />"><c:out value='${typeConfig.displayType}' /></option>
				</c:forEach>
			</html:select>
			<br /><br />
		
			<input type="submit" class="button flush floated" value="Next" /> 
			<a href="setupWizardStepDatabase">Cancel</a> 
		
		</html:form>
	
	</div>         
	
</body>
</html>