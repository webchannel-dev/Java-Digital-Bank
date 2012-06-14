<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		14-Jun-2005		Created.
	 d3 		Ben Browning		22-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	<title><bright:cmsWrite identifier="title-batch-update-finished" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script src="../js/category.js" type="text/javascript"></script>

	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="batch-batchupdate"/>
</head>

<body id="uploadPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-batch-update-finished" filter="false" /></h1> 
	
	
	<logic:present parameter="empty">
		<bright:cmsWrite identifier="intro-batch-finished-1" filter="false"/>
		<logic:present parameter="locked">
			<bright:cmsWrite identifier="intro-batch-finished-2" filter="false"/>
		</logic:present>
	</logic:present>
	<logic:notPresent parameter="empty">
		<bright:cmsWrite identifier="intro-batch-finished-3" filter="false"/>
	</logic:notPresent>
	
	<p><a href="../action/viewBatchUpdate"><bright:cmsWrite identifier="link-start-new-batch-update" filter="false"/></a></p>
	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>