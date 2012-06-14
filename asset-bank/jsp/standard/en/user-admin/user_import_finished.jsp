<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		06-Jul-20046	Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | User Import Complete</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="users"/>
	<bean:define id="helpsection" value="metadata-import"/>
	<bean:define id="pagetitle" value="User Import Complete"/>
</head>

<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	<p>
	<bean:parameter name="numAdded" id="numAdded" value="0"/>
	The user import has completed. <strong><bean:write name="numAdded"/></strong> users were successfully added.
	<br/>
	</p>
	<p><a href="../action/viewUserAdmin">&laquo; Back to user admin</a></p>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>