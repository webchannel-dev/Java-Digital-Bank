<%@include file="../inc/doctype_html.jsp" %>

<%-- History:
	 d1		Martin Wilson		28-Sep-2005		Created.
	 d2		Ben Browning		17-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<head>
	
	<title><bright:cmsWrite identifier="title-upload-success" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="upload"/>
</head>

<body id="uploadSuccessPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-update-success" filter="false" /></h1> 
	<bean:parameter id="current" name="current" value="false"/>
	<bean:parameter id="owner" name="owner" value="true"/>
	
	<logic:equal name="userprofile" property="isAdmin" value="false">
		<c:choose>
			<c:when test="${!owner}">
				<bright:cmsWrite identifier="intro-update-result-1-3" filter="false"/>
			</c:when>
			<c:when test="${current}">
				<bright:cmsWrite identifier="intro-update-result-1-2" filter="false"/>
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="intro-update-result-1" filter="false"/>
			</c:otherwise>
		</c:choose>
	</logic:equal>
	
	<logic:equal name="userprofile" property="isAdmin" value="true">
		<p>The <bright:cmsWrite identifier="item" filter="false" /> now has the status 'awaiting approval' - please note that non-admin users will not be able to see this <bright:cmsWrite identifier="item" filter="false" /> until it is approved by an admin user.</p>
	</logic:equal>
		
	<bright:cmsWrite identifier="intro-update-result-2" filter="false"/>
	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>