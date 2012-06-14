<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Group Import Complete</title>
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="groups"/>
	<bean:define id="helpsection" value="metadata-import"/>
	<bean:define id="pagetitle" value="Group Import Complete"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>

	<h1 class="underline"><bean:write name="pagetitle" /></h1>

	<p>
		<bean:parameter name="numAdded" id="numAdded" value="0" />
		The group import has completed. <strong><bean:write name="numAdded" /></strong> groups were successfully added.
		<br />
	</p>
	<p><a href="../action/listGroups">&laquo; Back to group admin</a></p>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>