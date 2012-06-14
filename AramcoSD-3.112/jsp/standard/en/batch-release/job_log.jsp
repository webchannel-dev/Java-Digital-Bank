<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		09-Feb-2011		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bean:parameter id="id" name="id" value="-1" />
<bean:parameter id="dependency" name="dependency" value="false" />
			
<head>
	
	<title><bright:cmsWrite identifier='title-manage-batch-releases' filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="system"/>
	<bean:define id="helpsection" value="batchReleases"/>
	<bean:define id="tabId" value="brJobs"/>
	

	<logic:equal name="inProgress" value="true">
		<meta HTTP-EQUIV="refresh" CONTENT="10;URL=viewBatchReleaseJobLog?id=<c:out value='${id}' />&dependency=<c:out value='${dependency}' />"></meta>
	</logic:equal>
	
</head>

<body id="adminPage"> 

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="batch-releases" filter="false" case="mixed" /></h1> 
	<%@include file="../updater/inc_system_tabs.jsp"%>

	<p><bright:cmsWrite identifier="snippet-job-log-intro" /></p>

	<c:choose>
		<c:when test="${empty messages}">
			<p><bright:cmsWrite identifier="snippet-no-messages-for-job" /></p>
		</c:when>
		<c:otherwise>
			<ul>
				<logic:iterate name='messages' id='message'>
					<li><bean:write name='message' /></li>
				</logic:iterate>
			</ul>
			<br /><br />
			<p><a href="viewBatchReleaseJobs"><bright:cmsWrite identifier="link-back" filter="false" /></a><c:if test="${inProgress}">&nbsp;|&nbsp;<a href="viewBatchReleaseJobLog?id=<c:out value='${id}' />&dependency=<c:out value='${dependency}' />"><bright:cmsWrite identifier="link-refresh-the-log" /></a></c:if></p>
		</c:otherwise>
	</c:choose>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>