<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<head>
	<title><bright:cmsWrite identifier="title-request-details" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="requests"/>
	
	<script type="text/javascript" src="../js/workflow-transitions/confirm-transition.js"></script>
	<script type="text/javascript" src="../js/group-edit.js"></script>
	 

</head>


<body> 

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-request-not-found" filter="false" /></h1> 

	<p><bright:cmsWrite identifier="snippet-request-not-found" filter="false" /></p>
	


	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>