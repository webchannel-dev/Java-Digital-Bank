<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title>Select PowerPoint Template</title>
	<jsp:include flush="true" page="../inc/head-elements.jsp"/>
	<bean:define id="section" value=""/>
</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>

	<h1>Cancel Checkout</h1>		
	
	<p>Select the PPT template etc...</p>

	
	<form action="../action/downloadAssetBox">
		<input type="hidden" name="powerpointTemplateFile" value="test.ppt"/>
		<html:hidden name="assetBoxDownloadForm" property="downloadAsPowerPoint"/>
		<html:hidden name="assetBoxDownloadForm" property="notAllRGB"/>
		<html:hidden name="assetBoxDownloadForm" property="canDownloadAllAssets"/>
		<html:hidden name="assetBoxDownloadForm" property="assetUse.usageTypeId"/>
		<html:hidden name="assetBoxDownloadForm" property="onlyDownloadSelected"/>
		<html:hidden name="assetBoxDownloadForm" property="imageFormat"/>
		<html:hidden name="assetBoxDownloadForm" property="advanced"/>
		<html:hidden name="assetBoxDownloadForm" property="height"/>
		<html:hidden name="assetBoxDownloadForm" property="width"/>
		<html:hidden name="assetBoxDownloadForm" property="fileName"/>
		<html:hidden name="assetBoxDownloadForm" property="excludedIds"/>		
		<html:hidden name="assetBoxDownloadForm" property="highResDirectDownload"/>
		<html:hidden name="assetBoxDownloadForm" property="usageTypeFormatId"/>
		<html:hidden name="assetBoxDownloadForm" property="convertToRGB"/>
		<html:hidden name="assetBoxDownloadForm" property="selectedColorSpaceId"/>
		<html:hidden name="assetBoxDownloadForm" property="downloadingLightbox"/>
		<html:hidden name="assetBoxDownloadForm" property="email"/>
		<html:hidden name="assetBoxDownloadForm" property="parentId"/>
		<html:hidden name="assetBoxDownloadForm" property="validateUsageType"/>
		<html:hidden name="assetBoxDownloadForm" property="validateUsageDescription"/>
		<html:hidden name="assetBoxDownloadForm" property="conditionsAccepted"/>
		<html:hidden name="assetBoxDownloadForm" property="agreementAccepted"/>

		<logic:iterate name="secondaryUsageTypeIds" id="secondaryUsageId">
			<input type="hidden" name="secondary_<c:out value='${secondaryUsageId}'/>" value="<c:out value='${secondaryUsageId}'/>"/>
		</logic:iterate>

		<input class="button flush floated" type="submit" value="Select" />
		<br />
	</form>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>