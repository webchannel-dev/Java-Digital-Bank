<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		14-Jan-2008		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="title-video-conversion-status" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="downloadVideo"/>

</head>

<body>
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-video-conversion-status" filter="false" /></h1> 

	<c:if test="${!downloadForm.hasErrors}">
		<p><bright:cmsWrite identifier="snippet-no-video-conversion" filter="false"/></p>
	</c:if>
	<c:if test="${downloadForm.hasErrors}">
		<div class="error">
			<bright:cmsWrite identifier="snippet-video-conversion-error" filter="false"/>
		</div>
	</c:if>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>