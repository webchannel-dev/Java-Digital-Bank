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
		
		<h1>SMTP settings</h1>
		
		<div class="section <c:if test="${setupWizardForm.smtpConfig.updated}">subtle</c:if>">
			<h2>Current SMTP settings:</h2>
			<p>
			    Server: <c:out value="${setupWizardForm.currentSmtpConfig.server}" /> <br/>
			    Port: <c:out value="${setupWizardForm.currentSmtpConfig.port}" /> <br/>
			    User: <c:out value="${setupWizardForm.currentSmtpConfig.username}" /> <br/>
			    Password: <c:out value="${setupWizardForm.currentSmtpConfig.password}" /> <br/>
			</p>
		</div>
		
		<c:choose>
			<c:when test="${setupWizardForm.smtpConfig.updated}">
				<div class="section">
					<div class="toolbar">
						<h2 style="margin-top:0">New SMTP settings</h2> 
						<a href="setupWizardStepSmtpEdit">edit</a> 
						<a href="setupWizardStepSmtp?removeSmtpConfig=true">discard</a>
					</div>
						
					<%@include file="inc_smtp_updates.jsp" %>
				
				</div>
			</c:when>
			<c:otherwise>
				<a href="setupWizardStepSmtpEdit">Edit these settings &raquo;</a><br/>
			</c:otherwise>
		</c:choose>
		

		<div class="wizard-bottom-nav">	
			<%@include file="inc_page_nav.jsp" %>
		</div>

	
	</div>         
	
</body>
</html>