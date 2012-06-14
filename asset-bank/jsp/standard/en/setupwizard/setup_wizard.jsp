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
	<h1>Start Setup Wizard</h1>
	
	<c:choose>

		<c:when test="${!setupWizardForm.wizardEnabled}">
		
			<div class="errorBox">
				You need to be a server administrator to use the Setup Wizard. 
				To enable it, change setup-run-wizard=false in the settings file and restart the application.
			</div>
			
		</c:when>
		
		<c:otherwise>
		
			<p>The Setup Wizard allows you to review and update your configuration settings.</p>
		
			<html:form action="startSetupWizard" method="post">

			<c:choose>
			
				<c:when test="${!setupWizardForm.isAuthenticated}">
					<%--  Security based on password  --%>
					<c:if test="${setupWizardForm.authenticationError}">
						<div class="errorBox">Password invalid</div>
					</c:if>
					<p>Please enter the password for running the Setup Wizard:</p>
					<html:password property="password" />
				</c:when>
		
				<c:otherwise>
			
					<c:choose>
						<c:when test="${setupWizardForm.canSetPassword}">
							<%-- This is effectively first time round, so allow password to be set --%>
							<p>
							You should now choose a password for the Setup Wizard, to ensure that it cannot be accessed by non-administrator users. 
							You will be able to run the wizard in future using this password.
							</p>
							
							<html:password property="password" />
						</c:when>
						<c:otherwise>
							<p>Click Next to begin.</p>
						</c:otherwise>
					</c:choose>
					
				</c:otherwise>
				
			</c:choose>
			
			<div class="wizard-bottom-nav">
				<input type="submit" class="button flush floated" value="Next &gt;" /> <a href="viewHome">Cancel</a>			
			</div>					
			</html:form>

		</c:otherwise>
	</c:choose>
	

	</div>         
	
</body>
</html>