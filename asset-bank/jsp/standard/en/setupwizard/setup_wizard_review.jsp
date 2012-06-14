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


		<bean:define id="tabId" value="review"/>
		<%@include file="inc_breadcrumb.jsp" %>
	
		<h1>Review Your Changes</h1>
	
		<p>Below are your updates.</p>
		
		<div class="section <c:if test="${not setupWizardForm.datasourceConfig.updated}">subtle</c:if>">
			<h2><a href="setupWizardStepDatabase">Database configuration</a></h2>
			<c:choose>
				<c:when test="${setupWizardForm.datasourceConfig.updated}">
					<%@include file="inc_database_updates.jsp" %>			
				</c:when>
				<c:otherwise>
					<p><em>No changes</em></p>	
				</c:otherwise>
			</c:choose>
		</div>
		<div class="section <c:if test="${not setupWizardForm.smtpConfig.updated}">subtle</c:if>">
			<h2><a href="setupWizardStepSmtp">SMTP configuration</a></h2>
			<c:choose>
				<c:when test="${setupWizardForm.smtpConfig.updated}">
					<%@include file="inc_smtp_updates.jsp" %>			
				</c:when>
				<c:otherwise>
					<p><em>No changes</em></p>	
				</c:otherwise>
			</c:choose>

		</div>
	

		<c:if test="${setupWizardForm.ldapLicensed}">
		
		<div class="section <c:if test="${not setupWizardForm.ldapConfig.updated}">subtle</c:if>">
			<h2><a href="setupWizardStepSmtp">LDAP configuration</a></h2>
			<c:choose>
				<c:when test="${setupWizardForm.ldapConfig.updated}">
					<%@include file="inc_ldap_updates.jsp" %>			
				</c:when>
				<c:otherwise>
					<p><em>No changes</em></p>	
				</c:otherwise>
			</c:choose>
		</div>
		</c:if>

	
		<div class="wizard-bottom-nav">
			<html:form action="submitSetupWizard" method="post">
		        <a href="setupWizardStepLdap" class="button ">&lt; Back</a>
		        <input type="submit" class="button confirm" value="Apply these changes &gt;" /> 
			</html:form>
		</div>
	

	</div>         
	
</body>
</html>