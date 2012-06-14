<%@include file="../inc/doctype_html_admin.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<%@include file="../inc/set_this_page_url.jsp"%>
<c:set scope="session" var="imageDetailReturnUrl" value="${thisUrl}"/>
<c:set scope="session" var="imageDetailReturnName" value="Report"/>

<head>
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Reports</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<link rel="stylesheet" type="text/css" href="../css/standard/print.css" media="print" />
	<bean:define id="section" value="reports"/>
	<bean:define id="pagetitle" value="Asset Reports"/>
	<c:set var="reportForm" value="${assetReportForm}" />
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	<div class="hr"></div>	
	
	<h2>Total number of Assets</h2>
	
	<p>There is a total of <strong><bean:write name="reportForm" property="totalNumOfAssets"/></strong> assets in your Asset Bank.</p>
	
	<div class="hr"></div>	
	<p><%@include file="back_to_reports.jsp"%></p>
	<div class="hr"></div>	

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>