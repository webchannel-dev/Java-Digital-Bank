<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Woollard		07-Jul-2008		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	<title><bright:cmsWrite identifier="heading-agreements" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="agreements"/>
	
</head>

<body id="conditionsPopup">

	<c:set var="heading"><bright:cmsWrite identifier="heading-view-agreement" filter="false"/></c:set>
	
	<c:if test="${not empty heading}">
		<h2 class="underline"><bright:cmsWrite identifier="heading-view-agreement" filter="false"/></h2> 										
	</c:if>
	
	<h2><bean:write name="agreementsForm" property="agreement.title" filter="false"/></h2>
	
	<div class="copy">
	
	<c:choose>
		<c:when test="${agreementsForm.agreement.bodyHtml}">	
			<bean:write name="agreementsForm" property="agreement.body" filter="false"/>
		</c:when>
		<c:otherwise>
			<bright:write name="agreementsForm" property="agreement.body" filter="false" formatCR="true"/>
		</c:otherwise>
	</c:choose>
	
	<div style="text-align:right;">
		<script type="text/javascript">
			document.write('<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button" id="submitButton" onclick="window.close();">');
		</script>
	</div>
	
	</div>
		
</body>
</html>