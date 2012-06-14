
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
	
	<h1><bright:cmsWrite identifier="batch-releases" filter="false" case="mixed" /></h1> 
	
	<c:set var="tabId" value="br-reports" />
	<%@include file="inc_manage_tabs.jsp"%>
	

			<p><a href="viewBatchReleaseAcknowledgementReport"><bright:cmsWrite identifier="tab-outstanding-acknowledgements" filter="false"/></a><br/>
			<bright:cmsWrite identifier="snippet-outstanding-acknowledgements-description" filter="false" /></p>

			<p><a href="viewBatchReleaseAcknowledgementMatrix"><bright:cmsWrite identifier="subhead-acknowledgements-matrix" /></a><br/>
			<bright:cmsWrite identifier="snippet-acknowledgement-matrix-description" filter="false" /></p>

			<p><a href="viewBatchReleaseAcknowledgementSummary"><bright:cmsWrite identifier="subhead-acknowledgements-summary" /></a><br/>
			<bright:cmsWrite identifier="snippet-acknowledgement-summary-description" filter="false" /></p>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>


