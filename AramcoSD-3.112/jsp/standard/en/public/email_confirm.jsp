<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1	Adam Bones		25-Oct-2006		Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier="title-email-sent" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="email"/>

</head>

<body id="searchPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-email-sent" /></h1> 
		
	<c:choose>
		<c:when test="${assetId == 0}">
			<div class="head">
		    	<bright:cmsWrite identifier="snippet-lightbox-links" filter="false" />
		    </div>
		</c:when>
		<c:otherwise>
			<div class="head">
		    	<a href="../action/viewAsset?id=<c:out value="${assetId}"/>"><bright:cmsWrite identifier="link-back-item" filter="false" /></a>
		    </div>
		</c:otherwise>
	</c:choose>
	
	<bright:cmsWrite identifier="snippet-email-sent" filter="false" />

	<c:if test="${messages != null}">
		<h3><bright:cmsWrite identifier="heading-upload-log" /></h3>
		<ul class="normal">
		<c:forEach items="${messages}" var="message">
			<li><c:out value="${message}" /></li>
		</c:forEach>
		</ul>
	</c:if>
								
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>