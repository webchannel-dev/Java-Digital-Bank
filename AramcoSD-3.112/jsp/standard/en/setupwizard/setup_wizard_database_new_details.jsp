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
	
		<p>Edit your new datasource configuration. Changes will not take effect until you click Submit at the end of the Setup Wizard.</p>
	
		<html:form action="setupWizardStepDatabase" method="post">
		
			<%--  Check if we are editing the new config or adding new config. In any case set updated flag=true --%>
			<c:set var="editing" value="${setupWizardForm.datasourceConfig.updated}" />
			<html:hidden property="datasourceConfig.updated" value="true" />
	
			<c:set var="code" value="${setupWizardForm.datasourceConfig.friendlyConfig.code}" />
			<c:set var="default" value="${setupWizardForm.datasourceConfig.friendlyConfig}" />
			
			<%-- If we are not editing, then refresh the default for the type --%>
			<c:if test="${!editing}">
				<c:forEach var="type" items="${setupWizardForm.datasourceConfig.types}">
					<c:if test="${type.code == code}">
						<c:set var="default" value="${type}" />
					</c:if>
				</c:forEach>
			</c:if>

			Database Type: <strong><c:out value="${default.displayType}" /></strong><br/><br/>
			<input type="hidden" name="datasourceConfig.friendlyConfig.displayType" value="<c:out value='${default.displayType}' />" />

		
			<label>IP:</label> <input type="text" name="datasourceConfig.friendlyConfig.ip" value="<c:out value='${default.ip}' />" /> <span class="hint">Database server IP</span> <br/>
			<label>Port:</label> <input type="text" name="datasourceConfig.friendlyConfig.port" value="<c:out value='${default.port}' />" /> <span class="hint">Database server port</span> <br/>


			<c:if test="${code != 'oracle'}">
				<label>Schema:</label> <input type="text" name="datasourceConfig.friendlyConfig.schema" value="<c:out value='${default.schema}' />" /> <span class="hint">Schema name</span> <br/>
			</c:if>

			<c:if test="${code != 'mysql'}">
				<label>Instance:</label> <input type="text" name="datasourceConfig.friendlyConfig.instance" value="<c:out value='${default.instance}' />" /> <span class="hint">Database instance name</span>	<br/>	
			</c:if>


			<label>User:</label> <input type="text" name="datasourceConfig.friendlyConfig.user" value="<c:out value='${default.user}' />" /> <span class="hint">Database user to use for the connection</span> <br/>
			<label>Password:</label> <input type="text" name="datasourceConfig.friendlyConfig.password" value="<c:out value='${default.password}' />" />	<br/>	
			
			<br/>
			

			<input type="submit" class="button flush floated" value="Save" /> 
			<a href="setupWizardStepDatabase">Cancel</a> 
		</html:form>
	
	</div>         
	
</body>
</html>