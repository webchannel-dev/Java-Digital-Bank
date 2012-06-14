<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Stevenson		21-Feb-2006		Created
	d2		Matt Stevenson		22-Feb-2006		Changes to text and layout
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Search Report</title> 
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
	
	<h1><bean:write name="pagetitle" /></h1> 
	
	<div class="head">
		<a href="../action/viewSearchReportPage?startDateString=<bean:write name='searchReportForm' property='startDateString'/>&endDateString=<bean:write name='searchReportForm' property='endDateString'/>" class="bold">&laquo; Back to Reports</a>
	</div>

	<h2>Search Report</h2>
	
	<c:if test="${searchReportForm.startDate != null || searchReportForm.endDate != null}">
	<h3>Report date range: 
		<c:choose>
			<c:when test="${searchReportForm.startDate != null && searchReportForm.endDate == null}"> 
				searches performed after <fmt:formatDate value="${searchReportForm.startDate}" pattern="${dateFormat}" />
			</c:when>
			<c:when test="${searchReportForm.startDate == null && searchReportForm.endDate != null}">
				searches performed before <fmt:formatDate value="${searchReportForm.endDate}" pattern="${dateFormat}" />
			</c:when>
			<c:otherwise>
				<fmt:formatDate value="${searchReportForm.startDate}" pattern="${dateFormat}" /> to <fmt:formatDate value="${searchReportForm.endDate}" pattern="${dateFormat}" />
			</c:otherwise>
		</c:choose>
	</h3>
	</c:if>
	<%-- logic:equal name="searchReportForm" property="groupedReport" value="false">
		<p>Click the + link next to a search term to view the full search query that was used.</p>
	</logic:equal --%>

	<div class="hr"></div>
	
	<table cellspacing="0" class="report" summary="Search report" style="width: 50%;">		
		<tr>
			<logic:equal name="searchReportForm" property="groupedReport" value="false">
				<th>
					Date of Search
				</th>
			</logic:equal>
			<th>
				Search Term
			</th>
			<logic:equal name="searchReportForm" property="groupedReport" value="false">
				<th>
					Successful?
				</th>
			</logic:equal>
			<logic:equal name="searchReportForm" property="groupedReport" value="true">
				<th>
					No. of <c:if test="${searchReportForm.successType == 1}">Successful</c:if>
						   <c:if test="${searchReportForm.successType == 2}">Failed</c:if>
					Searches
				</th>
			</logic:equal>
		</tr>
		<logic:iterate name="searchReportForm" property="reportLines" id="reportLine" indexId="index">
			<tr>
				<logic:equal name="searchReportForm" property="groupedReport" value="false">
					<td>
						<fmt:formatDate value="${reportLine.date}" pattern="${dateFormat}" />
					</td>
				</logic:equal>
				<td>
					<%-- logic:equal name="searchReportForm" property="groupedReport" value="false"><a href="javascript:openFullQuery('<bean:write name='reportLine' property='luceneQuery'/>');">+</a>&nbsp;</logic:equal --%><bean:write name="reportLine" property="searchTerm"/>
				</td>
				<logic:equal name="searchReportForm" property="groupedReport" value="false">
					<td class="uses" style="text-align: left;">
						<c:if test="${reportLine.successful == true}">Yes</c:if>
						<c:if test="${reportLine.successful == false}">No</c:if>
					</td>
				</logic:equal>
				<logic:equal name="searchReportForm" property="groupedReport" value="true">
					<td>
						<bean:write name="reportLine" property="resultCount"/>
					</td>
				</logic:equal>
			</tr>
		</logic:iterate>
	</table>
	
	<p>
		<a href="downloadSearchReport?groupedReport=<bean:write name="searchReportForm" property="groupedReport"/>&successType=<bean:write name="searchReportForm" property="successType"/>&startDateString=<bean:write name="searchReportForm" property="startDateString"/>&endDateString=<bean:write name="searchReportForm" property="endDateString"/>&contentType=application/vnd.ms-excel">Download as excel file...</a>
	</p>
	
	<div class="hr"></div>
	<p><a href="../action/viewSearchReportPage?startDateString=<bean:write name='searchReportForm' property='startDateString'/>&endDateString=<bean:write name='searchReportForm' property='endDateString'/>&groupedReport=<bean:write name='searchReportForm' property='groupedReport'/>&successType=<bean:write name='searchReportForm' property='successType'/>" class="bold">&laquo; Back to Reports</a></p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>