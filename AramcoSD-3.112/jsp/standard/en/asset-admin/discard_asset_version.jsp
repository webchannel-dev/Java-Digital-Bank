<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan	13-Oct-2011		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bean:parameter id="id" name="id" value="0"/>
		
<head>
	
	<title>Discard Most Recent Version</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value=""/>
	<bean:define id="helpsection" value="asset_metadata"/>	

</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline">Discard Most Recent Version</h1>
	
	<p>Permanently discard this version?</p>
	
	<c:if test="${bWillStripRels}">
		<p>Note that the relationships for this asset will be stripped if you do discard this version, 
		since the previous version does not have any stored relationships.</p>
	</c:if>	
	
	
	<form action="discardCurrentAssetVersion" class="floated">
		<input type="hidden" name="id" value="${id}" />
		<input type="submit" value="Discard" class="button"/>
		<a href="viewAsset?id=<c:out value="${id}" />" class="cancelLink">Cancel</a>

	</form>

	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>