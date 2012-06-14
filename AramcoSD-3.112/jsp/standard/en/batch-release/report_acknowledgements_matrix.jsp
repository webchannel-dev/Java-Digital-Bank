<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="title-acknowledgements-matrix" filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<link rel="stylesheet" type="text/css" href="../css/standard/print.css" media="print" />
	<bean:define id="section" value="batchReleases"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="batch-releases" filter="false" case="mixed" /></h1> 
	
	<c:set var="tabId" value="br-reports" />
	
	<%@include file="inc_manage_tabs.jsp"%>
	
	<h2><bright:cmsWrite identifier="subhead-acknowledgements-matrix" /></h2>
	
	<p><bright:cmsWrite identifier="snippet-acknowledgement-matrix-intro" /></p>

	<logic:empty name="matrix" property="rows">
		<p><i><bright:cmsWrite identifier="snippet-no-oustanding-acknowledgements" filter="false" /></i></p>
	</logic:empty>
	<logic:notEmpty name="matrix" property="rows">
		<table class="list" cellspacing="0">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<logic:iterate name="matrix" property="headers" id="header">
						<th><bean:write name="header" /></th>
					</logic:iterate>
				</tr>
			</thead>
			<logic:iterate name="matrix" property="rows" id="row">
				<tr>
					<td><bean:write name="row" property="label" /></td>
					<logic:iterate name="row" property="entries" id="entry">
						<td  style="text-align:center"><c:choose><c:when test="${empty entry}">-</c:when><c:when test="${entry}"><img src="../images/standard/icon/tick.png" width="16" height="16" alt="Tick" /></c:when><c:otherwise><img src="../images/standard/icon/cross.png" width="16" height="16" alt="Tick" /></c:otherwise></c:choose></td>
					</logic:iterate>
				</tr>
			</logic:iterate>

			<tr>
				<td><strong><bright:cmsWrite identifier="label-totals" /></strong></td>
				<logic:iterate name="matrix" property="totals" id="total">
					<td  style="text-align:center"><strong><bean:write name="total" /></strong></td>
				</logic:iterate>
			</tr>
		</table>	
	</logic:notEmpty>
	
	<div class="hr"></div>

	<p><a href="viewBatchReleaseReports"><bright:cmsWrite identifier="link-back-to-reports" filter="false" /></a>&nbsp;|&nbsp;<a href="downloadBatchReleaseReport?type=matrix">Download this report</a></p>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>