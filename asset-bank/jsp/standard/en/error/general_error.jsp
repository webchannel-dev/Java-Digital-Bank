<%@ page isErrorPage="true" %>
<%@ page import="com.bn2web.common.exception.Bn2Exception" %>
<%@ page import="com.bn2web.common.service.GlobalApplication" %>


<% response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); %>

<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<%-- There could be an exception for us to show in 2 places:
	  1) the exception variable, put there by the Servlet/JSP container (see isErrorPage in the JSP spec)
	  2) the org.apache.struts.action.EXCEPTION variable, put there by struts
	 We look in each of these 2 places in turn. --%>
<%
	Throwable exceptionToShow = null;
	if (exception != null)
	{
		exceptionToShow = exception;
	}
	else
	{
		exceptionToShow = (Throwable) pageContext.findAttribute("org.apache.struts.action.EXCEPTION");
	}
	
	boolean bExistsException = (exceptionToShow != null);
	pageContext.setAttribute("bExistsException", bExistsException);
	
	if (bExistsException)
	{
		String sError = Bn2Exception.getStackTrace(exceptionToShow);
		sError = sError.replaceAll("\\\\[^:]*\\\\|/[^:]*/","[Path info removed]");  // Remove path information, for security reasons
		pageContext.setAttribute("sError", sError);
	}
	
	// Check if app is starting up or initialisation error
	if (!GlobalApplication.getInstance().getFinished() || GlobalApplication.getInstance().getExistsError())
	{
		boolean bInitError = true;
		pageContext.setAttribute("bInitError", bInitError);
	}
%>

<bright:applicationSetting id="webmasterEmail" settingName="webmaster-email"/>
<bright:applicationSetting id="setupRunWizard" settingName="setup-run-wizard"/>

<%--  In the case of initialisation or setup error, don't show pretty page, just link to not initialised page and give plaintext help --%>
<%--  This shouldn't happen often, eg there is a jsp error in the init pages, eg setup wizard --%>
<c:choose>
	<c:when test="${setupRunWizard}">
		<h1 class="underline">Setup Error</h1> 
		<p>There has been an error during setup of the application.</p>
		<ul>
			<li><a href="applicationNotInitialised">Click here</a> for initialisation information</li>
			<li>Manually check your settings files and restart Tomcat</li>
			<li>Change setup-run-wizard=false in your settings file</li>
		</ul>
		<c:if test="${bExistsException}">
			<p>
				For further help with this error, please copy the text shown below and email it to <a href="mailto:<c:out value='${webmasterEmail}' />"><c:out value='${webmasterEmail}' /></a>.
			</p>
			<div class="errorBox">
				<c:out value="${sError}" />
			</div>
		</c:if>
	</c:when>
	<c:when test="${bInitError}">
		<h1 class="underline">Initialisation Error</h1> 
		<p>There has been an error during initialisation of the application.</p>
		<ul>
			<li><a href="applicationNotInitialised">Click here</a> for initialisation information</li>
			<li>Manually check your database connection and settings files and restart Tomcat</li>
		</ul>

		<c:if test="${bExistsException}">
			<p>
				If this problem persists, please copy the text shown below and email it to <a href="mailto:<c:out value='${webmasterEmail}' />"><c:out value='${webmasterEmail}' /></a>.
			</p>
			<div class="errorBox">
				<c:out value="${sError}" />
			</div>
		</c:if>
		</p>
	</c:when>
	
	<c:otherwise>
	
		<%-- App is initialised, so we'll show a pretty page --%>
		<head>			
			<title><bright:cmsWrite identifier="company-name" filter="false" /> | Error</title> 
			<%@include file="../inc/head-elements.jsp"%>
			<bean:define id="section" value="error"/>
		</head>
		<body id="errorPage">
			<%@include file="../inc/body_start.jsp"%>
	
			<h1 class="underline">Error</h1> 	
				
			<c:set var="sErrorText"><bright:cmsWrite identifier="error-intro" filter="false" /></c:set>
			<c:choose>
				<c:when test="${!empty sErrorText}">
					<c:out value="${sErrorText}" escapeXml="false" />
				</c:when>
				<c:otherwise>
					<p>Sorry, a database error has occurred. Please inform your IT support team or use the <a href="viewContact">contact page</a>.</p>
				</c:otherwise>
			</c:choose>
						
			<c:if test="${bExistsException}">
				<div class="errorBox">
					<c:out value="${sError}" />
				</div>
			</c:if>
			
		
			<%@include file="../inc/body_end.jsp"%>		
		</body>
	
	</c:otherwise>
</c:choose>
		
</html>
