<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="title-br-outstanding-acknowledgements-report" filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<link rel="stylesheet" type="text/css" href="../css/standard/print.css" media="print" />
	<bean:define id="section" value="batchReleases"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="subhead-acknowledgements-summary" /></h1> 
	
	<c:set var="tabId" value="br-reports" />
	
	<%@include file="inc_manage_tabs.jsp"%>
	
	<h2><bright:cmsWrite identifier="subhead-acknowledgements-summary" /></h2>
	<p><bright:cmsWrite identifier="snippet-acknowledgement-summary-intro" /></p>

	<logic:empty name="summary" property="rows">
		<p><i><bright:cmsWrite identifier="snippet-no-oustanding-acknowledgements" filter="false" /></i></p>
	</logic:empty>
	<logic:notEmpty name="summary" property="rows">
		<table class="list" cellspacing="0">
			<thead>
				<tr>
					<th>&nbsp;</th>
					<th><bright:cmsWrite identifier="subhead-total-outstanding-acknowledgments" /></th>
				</tr>
			</thead>
			<tbody>
				<logic:iterate name="summary" property="rows" id="row">
					<tr>
						<td><bean:write name="row" property="label" /></td>
						<td><bean:write name="row" property="count" /></td>
					</tr>
				</logic:iterate>

				<tr>
					<td><strong><bright:cmsWrite identifier="label-total" /></strong></td>
					<td><strong><bean:write name="summary" property="total" /></strong></td>
				</tr>
			</tbody>
		</table>
	</logic:notEmpty>
	
	<div class="hr"></div>
	<p><a href="viewBatchReleaseReports"><bright:cmsWrite identifier="link-back-to-reports" filter="false" /></a>&nbsp;|&nbsp;<a href="downloadBatchReleaseReport?type=summary">Download this report</a></p>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>