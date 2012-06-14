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

<head>
	
	<title><bright:cmsWrite identifier='title-manage-batch-releases' filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="system"/>
	<bean:define id="helpsection" value="batchReleases" />
	<bean:define id="tabId" value="brJobs"/>
	
</head>

<body id="adminPage"> 

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="batch-releases" filter="false" case="mixed" /></h1> 
	<%@include file="../updater/inc_system_tabs.jsp"%>

	<c:choose>
		<c:when test="${empty batchReleaseJobs}">
			<p><bright:cmsWrite identifier="snippet-no-jobs-running" /></p>
		</c:when>
		<c:otherwise>
			<table class="list highlight wide" cellspacing="0">
				<thead>
					<tr>
						<th><bright:cmsWrite identifier="label-name" /></th>
						<th><bright:cmsWrite identifier="label-started-by" /></th>
						<th><bright:cmsWrite identifier="label-start-time" /></th>
						<th><bright:cmsWrite identifier="label-type" /></th>
						<th><bright:cmsWrite identifier="label-job-status" /></th>
						<th><bright:cmsWrite identifier="label-actions" />:</th>
					</tr>
				</thead>
				<tbody>
				<logic:iterate name='batchReleaseJobs' id='status'>
					<tr>
						<td><bean:write name='status' property='job.batchRelease.name' /></td>
						<td><bean:write name='status' property='job.user.forename' /> <bean:write name='status' property='job.user.surname' /></td>
						<td><bean:write name='status' property='job.startTime' format="dd/MM/yyyy HH:mm:ss" /></td>
						<td><c:choose><c:when test="${status.job.dependencyJob}"><bright:cmsWrite identifier="snippet-dependency-job" case="mixed" /></c:when><c:otherwise><bright:cmsWrite identifier="snippet-batch-release-validation" case="mixed" /></c:otherwise></c:choose></td>
						<td><c:choose><c:when test="${status.inProgress}"><bright:cmsWrite identifier="snippet-in-progress" /></c:when><c:otherwise><bright:cmsWrite identifier="snippet-pending" /></c:otherwise></c:choose></td>
						<td><c:if test="${status.inProgress}">[<a href="viewBatchReleaseJobLog?id=<c:out value='${status.job.jobId}' />&dependency=<c:out value='${status.job.dependencyJob}' />"><bright:cmsWrite identifier="link-view-log" case="lower" /></a>]</c:if></td>
					</tr>
				</logic:iterate>
				</tbody>
			</table>

		</c:otherwise>
	</c:choose>
	
	<p><a href="viewBatchReleaseJobs"><bright:cmsWrite identifier="button-refresh-view" filter="false" /> &raquo;</a></p>	

	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>