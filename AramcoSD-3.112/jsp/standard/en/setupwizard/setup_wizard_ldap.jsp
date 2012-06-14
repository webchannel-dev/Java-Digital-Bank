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
		
		<bean:define id="tabId" value="ldap"/>
		<%@include file="inc_breadcrumb.jsp" %>
		
		<h1>LDAP Settings</h1>

		<c:choose>
			<c:when test="${!setupWizardForm.ldapLicensed}">
				<div class="info">You are not currently licensed for LDAP integration.</div>
			</c:when>
			<c:otherwise>
			
				<h2>Current LDAP settings:</h2>

				<c:choose>
					<c:when test="${setupWizardForm.currentLdapConfig.enabled && setupWizardForm.currentLdapConfig.configAdvanced}">
						<div class="info">The current configuration is advanced.</div>
					</c:when>
					<c:otherwise>
						<%--  Show ldap test and details for the current config --%>
						<c:set var="config" value="${setupWizardForm.currentLdapConfig}" scope="request" />
						<jsp:include page="inc_ldap_details.jsp" />
					</c:otherwise>
				</c:choose>	
													
				<c:choose>
					<c:when test="${setupWizardForm.ldapConfig.updated}">
						<div class="toolbar">
							<h2>New LDAP settings</h2> 
							<a href="setupWizardStepLdapEdit">edit</a>
							<a href="setupWizardStepLdap?removeLdapConfig=true">discard</a>
						</div>
						
						<%--  Show ldap test and details for the new config --%>
						<c:set var="config" value="${setupWizardForm.ldapConfig}" scope="request" />
						<jsp:include page="inc_ldap_details.jsp" />
									
					</c:when>
					<c:otherwise>
						<%--  Instructions for creating an edited set of settings --%>
						<c:choose>
							<c:when test="${setupWizardForm.currentLdapConfig.configAdvanced}">
								<p>To change advanced LDAP settings, please edit the ApplicationSettings.properties file directly, in the 'Active Directory Settings' section.</p>
							</c:when>
							<c:when test="${setupWizardForm.currentLdapConfig.enabled}">
								<a href="setupWizardStepLdapEdit">Edit these settings &raquo;</a><br/>
							</c:when>
							<c:otherwise>
								<p>
									The setup wizard can be used to configure integration with one Active Directory server (and optionally a backup server).
									If you have a different type of LDAP server, users on more than one server, or need advanced settings, then please edit the ApplicationSettings.properties file directly, in the 'Active Directory Settings' section.
								</p>
								<a href="setupWizardStepLdapEdit">Set up an Active Directory connection &raquo;</a><br/>						
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
			
			</c:otherwise>
		</c:choose>

		
		
		<div class="wizard-bottom-nav">	
			<%@include file="inc_page_nav.jsp" %>
		</div>
	
	</div>         
	
</body>
</html>