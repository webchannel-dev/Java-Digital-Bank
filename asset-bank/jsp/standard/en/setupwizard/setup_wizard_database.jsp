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

		
		<h1>Database Settings</h1>
		<c:choose>
			<c:when test="${setupWizardForm.databaseError}">
				<div class="errorBox">
					<p>The Setup Wizard cannot connect to the datasource. This may be for the following reasons:</p>
					<ol>
						<li>The database service is not running, or cannot be accessed from the Asset Bank server.</li>
						<li>The Asset Bank schema has not been set up yet.</li>
						<li>The Asset Bank connection is not configured - check the connection details below.</li>
					</ol>
				</div>
			</c:when>
			<c:otherwise>

				<div class="info">The Setup Wizard can connect to the Asset Bank database with your current settings.</div>

			</c:otherwise>
		</c:choose>
		
		<div class="section <c:if test="${setupWizardForm.datasourceConfig.updated}">subtle</c:if>">
			<h2>Current database settings:</h2>
			<p>
				<c:if test="${!empty setupWizardForm.currentDatasourceConfig.displayTypeForRawConfig}">
					Database Type: <c:out value="${setupWizardForm.currentDatasourceConfig.displayTypeForRawConfig}" /><br/>
				</c:if>
				Driver: <c:out value="${setupWizardForm.currentDatasourceConfig.driverParam}" /> <br/>
				DB URL: <c:out value="${setupWizardForm.currentDatasourceConfig.urlParam}" /> <br/>
				User: <c:out value="${setupWizardForm.currentDatasourceConfig.userParam}" /> <br/>
				Password: <c:out value="${setupWizardForm.currentDatasourceConfig.passwordParam}" /> <br/>
			</p>
		</div>			
		<c:choose>
			<c:when test="${setupWizardForm.datasourceConfig.updated}">
				<div class="section">
					<div class="toolbar">
						<h2 style="margin-top:0">New database settings</h2> 
						<c:choose>
							<c:when test="${setupWizardForm.datasourceConfig.isNewConfig}"><a href="setupWizardStepDatabaseNewDetails">edit</a></c:when>
							<c:otherwise><a href="setupWizardStepDatabaseEdit">edit</a></c:otherwise>
						</c:choose>
						<a href="setupWizardStepDatabase?removeDatasourceConfig=true">discard</a>
					</div>
					<%@include file="inc_database_updates.jsp" %>
				</div>
			</c:when>
			<c:otherwise>
				<a href="setupWizardStepDatabaseEdit">Edit these settings &raquo;</a> |
				<a href="setupWizardStepDatabaseNewSelect">Create a new datasource configuration &raquo;</a><br/>	
			</c:otherwise>
		</c:choose>

			
		<div class="wizard-bottom-nav">	
			<%@include file="inc_page_nav.jsp" %>
		</div>

	</div>         
	
</body>
</html>