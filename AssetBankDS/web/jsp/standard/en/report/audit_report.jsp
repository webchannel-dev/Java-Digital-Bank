<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Woollard		04-Apr-2008       Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />
<bright:applicationSetting id="showThumbnails" settingName="show-thumbnails-in-asset-reports" />

<%@include file="../inc/set_this_page_url.jsp"%>
<c:set scope="session" var="imageDetailReturnUrl" value="${thisUrl}"/>
<c:set scope="session" var="imageDetailReturnName" value="Report"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Audit Report</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<link rel="stylesheet" type="text/css" href="../css/standard/print.css" media="print" />
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Reports"/>
	<script type="text/javascript">
		<!--
			function openFullQuery(content)
			{
				var generator=window.open('','name','height=200,width=500');
  
				generator.document.write('<head><title><bright:cmsWrite identifier="company-name" filter="false" /> | Search Report | Full Query</title><link href="../css/popup-standard.css" rel="stylesheet" type="text/css" media="all"></head>');
				generator.document.write('<body><div class="rule"></div><p>'+content+'</p><div class="rule"></div><p><a href="javascript:window.close();">&laquo; Close Window</a></p><div class="rule"></div></body></html>');
				generator.document.close();
			}
		-->
	</script>
</head>
<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	
	<div class="head">
		<a href="../action/viewAuditReportPage?startDateString=<bean:write name='auditReportForm' property='startDateString'/>&endDateString=<bean:write name='auditReportForm' property='endDateString'/>&ipAddress=<bean:write name='auditReportForm' property='ipAddress'/>&username=<bean:write name='auditReportForm' property='username'/>&includeViewsDownloads=<bean:write name='auditReportForm' property='includeViewsDownloads'/>" class="bold">&laquo; Back to Reports</a>
	</div>

	<h2>Audit Report</h2>
	
	<logic:empty name="auditReportForm" property="reportLines">
		<p>There are no audit logs for the date range you provided.</p>
	</logic:empty>
	
	<logic:notEmpty name="auditReportForm" property="reportLines">
		<h3>
			<c:choose>
				<c:when test="${auditReportForm.startDate != null || auditReportForm.endDate != null}"> 
					Report date range: 
					<c:choose>
						<c:when test="${auditReportForm.startDate != null && auditReportForm.endDate == null}"> 
							transactions after <fmt:formatDate value="${auditReportForm.startDate}" pattern="${dateFormat}" />
						</c:when>
						<c:when test="${auditReportForm.startDate == null && auditReportForm.endDate != null}">
							transactions before <fmt:formatDate value="${auditReportForm.endDate}" pattern="${dateFormat}" />
						</c:when>
						<c:otherwise>
							<fmt:formatDate value="${auditReportForm.startDate}" pattern="${dateFormat}" /> to <fmt:formatDate value="${auditReportForm.endDate}" pattern="${dateFormat}" />
						</c:otherwise>
					</c:choose>
				</c:when>
			</c:choose>
		</h3>
	
		<div class="hr"></div>
		
		<table cellspacing="0" class="report" summary="Search report" style="width: 80%;">		
			<tr>
				<c:if test="${showThumbnails}">
					<th>&nbsp;</th>
				</c:if>
				<th>
					Date & Time
				</th>
				<th>
					Id
				</th>
				<th>
					Username
				</th>
				<th>
					IP address
				</th>
				<th>
					Type
				</th>
			</tr>
			<logic:iterate name="auditReportForm" property="reportLines" id="reportLine" indexId="index">
				<tr>
					<c:if test="${showThumbnails}">
						<td>
							<a href="viewAsset?id=<bean:write name='reportLine' property='assetId'/>">
								<img class="<c:if test="${reportLine.hasIcon}">icon</c:if><c:if test="${not reportLine.hasIcon}">image</c:if>" src="../servlet/display?file=<c:out value="${reportLine.rc4EncryptedThumbnailPath}"/>" border="0">
							</a>
						</td>
					</c:if>
					<td>
						<bean:write name="reportLine" property="date" filter="false"/>
					</td>
					<td>
						<c:choose>
							<c:when test="${reportLine.assetId == 0}">
								<bean:write name="reportLine" property="identifier" filter="false"/>		
							</c:when>
							<c:otherwise>
								<a href="viewAsset?id=<bean:write name="reportLine" property="assetId" filter="false"/>"><bean:write name="reportLine" property="assetId" filter="false"/></a>
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						<bean:write name="reportLine" property="username" filter="false"/>
					</td>			
					<td>
						<c:choose>
							<c:when test="${reportLine.ipAddress == null}">
								N/A
							</c:when>
							
							<c:otherwise>
								<bean:write name="reportLine" property="ipAddress" filter="false"/>
							</c:otherwise>
						</c:choose>	
						
					</td>		
					<td>
						<c:choose>
							<c:when test="${reportLine.type == 'Modified' || reportLine.type == 'Agreement Change'}">
								<a href="viewAssetAudit?xmlLog=<bean:write name="reportLine" property="id"/>" target="_blank"><bean:write name="reportLine" property="type"/></a>
							</c:when>
							
							<c:otherwise>
								<bean:write name="reportLine" property="type"/>
							</c:otherwise>
						</c:choose>
					</td>	
				</tr>
			</logic:iterate>
		</table>
	</logic:notEmpty>
	
	<div class="hr"></div>
	<p>
	<a href="../action/viewAuditReportPage?startDateString=<bean:write name='auditReportForm' property='startDateString'/>&endDateString=<bean:write name='auditReportForm' property='endDateString'/>&ipAddress=<bean:write name='auditReportForm' property='ipAddress'/>&username=<bean:write name='auditReportForm' property='username'/>&includeViewsDownloads=<bean:write name='auditReportForm' property='includeViewsDownloads'/>" class="bold">&laquo; Back to Reports</a>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>