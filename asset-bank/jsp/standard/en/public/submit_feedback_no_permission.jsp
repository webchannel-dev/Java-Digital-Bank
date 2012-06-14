<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
	
<bean:define id="displayCase" value="lower"/>
<c:set var="type">
	<%@include file="inc_feedback_snippet.jsp"%>
</c:set>
	
<head>
	
	<title><bright:cmsWrite identifier="title-submit-feedback" filter="false" replaceVariables="true"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
	<bean:define id="helpsection" value="view_asset"/>	

</head>

<body id="detailsPage" onload="document.getElementById('starsDiv').style.display = 'block';">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-submit-feedback" filter="false" replaceVariables="true"/></h1>

	<div class="info">
		<bright:cmsWrite identifier="snippet-no-permission-feedback" filter="false" replaceVariables="true"/>
	</div>
	 
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>