<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Woollard		11-Feb-2009
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | User approval</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="users"/>
</head>
<body>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-user-approval" filter="false"/></h1> 
	<c:choose>
		<c:when test="${userForm.approved}">
			<bright:cmsWrite identifier="thank-you-for-approving" filter="false"/>
		</c:when>
		
		<c:otherwise>
			<bright:cmsWrite identifier="thank-you-for-rejecting" filter="false"/>
		</c:otherwise>
		
	</c:choose>
	
	<br/><br/>
	<p>
		<a href="../action/viewHome"><bright:cmsWrite identifier="link-back-to-homepage" filter="false"/></a>
	</p>
	
	
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>