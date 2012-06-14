<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		22-Feb-2011
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier='title-delete-asset' filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value="batchReleases"/>
</head>

<body>

	<%@include file="../inc/body_start.jsp"%>

	<h1><bright:cmsWrite identifier='heading-delete-asset' filter="false" /></h1>
	
	<div class="warning">
		<h3><bright:cmsWrite identifier="subhead-batch-release-asset-delete" filter="false" /></h3>
		<bright:cmsWrite identifier="snippet-delete-asset-batch-release-warning" filter="false" />

	</div>

	<bean:parameter id="assetId" name="id" value="-1" />
	<form action="deleteAsset" method="post">
		<input type="hidden" name="id" value="<bean:write name='assetId' />" />
		<input type="hidden" name="force" value="1" />
		<input type="submit" name="delete" value="Delete this asset anyway &raquo;" class="button flush floated" />
		<bean:parameter id="returnUrl" name="returnUrl" value="viewAsset?id=${assetId}" />
		<a href="..<c:out value='${returnUrl}' />" class="cancelLink">Cancel</a>
		
	</form>
	

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>