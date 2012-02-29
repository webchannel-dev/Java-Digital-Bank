<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Steve Bryan		14-Sep-2005		Created.
	d2		Tamora James	03-Oct-2005		Added to demo.
	d3    Ben Browning   20-Feb-2006    HTML/CSS tidy up		
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>

<head>
	
	<title><bright:cmsWrite identifier="title-request-approval" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="lightbox"/>

</head>

<body id="approvalPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline">
		<c:choose>
			<c:when test="${ecommerce}">
				<bright:cmsWrite identifier="e-ecommerce-offline-option-name" filter="false"/> Purchase
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="heading-request-approval" filter="false" />
			</c:otherwise>
		</c:choose>
	</h1> 

	<bright:cmsWrite identifier="intro-request-approval-success" filter="false" />
	

	<p><a href="../action/viewAssetBox"><bright:cmsWrite identifier="link-view-your-lightbox" filter="false" replaceVariables="true" /></a></p>

		
	
				
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>