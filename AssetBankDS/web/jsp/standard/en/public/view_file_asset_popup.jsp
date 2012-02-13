<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	04-Jan-2006		Created from view_file_asset
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | View File</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
	<bean:define id="pagetitle" value="File Details"/>
</head>

<body id="detailsPage">

<bean:define id="popups" value="1"/>
<%@include file="../inc/head.jsp"%>

<br />
<div class="head">
	<a href="#" onclick="window.close();">&laquo; Close Window</a>
</div>

<h2><bean:write name="assetForm" property="asset.searchName"/></h2>				 
<div class="clearfix">
	
	<div class="thumb">
		<bean:define id="asset" name="assetForm" property="asset" />
		<bean:define id="resultImgClass" value="icon" />
		<%@include file="../inc/view_thumbnail.jsp"%>	
	</div>
	
	<div class="actions">
		
		<logic:equal name="assetForm" property="asset.hasExpired" value="true">
			<p>This image cannot be downloaded by standard users as it has expired.</p>
		</logic:equal>
		<logic:equal name="assetForm" property="userCanDownloadAsset" value="true">
			<form action="../action/viewDownloadFile" method="get">
				<input type="hidden" name="id" value="<bean:write name='assetForm' property='asset.id'/>">
				<input class="button" type="Submit" value="<bright:cmsWrite identifier="button-download" filter="false" />" />
			</form>
		</logic:equal>
		<c:if test="${!assetForm.userCanDownloadAsset && assetForm.userCanDownloadAssetWithApproval}">
			<c:if test="${!assetForm.assetInAssetBox}">
				<p>You can request permission to download this image by adding it to your <bright:cmsWrite identifier="a-lightbox" filter="false"/></p>
			</c:if>
			<c:if test="${assetForm.assetInAssetBox}">
				<p>You can request permission to download this image from your <a href="../action/viewAssetBox"><bright:cmsWrite identifier="a-lightbox" filter="false"/></a>.</p>
			</c:if>
		</c:if>		

		<logic:equal name="assetForm" property="userCanUpdateAsset" value="true">
			<form name="updateForm" action="../action/viewUpdateAsset" method="get">
				<input class="button" type="submit" value="<bright:cmsWrite identifier="button-edit" filter="false" />" />
				<input type="hidden" name="id" value="<bean:write name='assetForm' property='asset.id'/>">
			</form>
		</logic:equal>
		<logic:equal name="assetForm" property="userCanDeleteAsset" value="true">
			<form name="deleteForm" action="../action/deleteAsset" method="get">
				<input class="button" type="submit" value="<bright:cmsWrite identifier="button-delete" filter="false" />" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-delete-asset" filter="false"/>');" />
				<input type="hidden" name="id" value="<bean:write name='assetForm' property='asset.id'/>" />
			</form>
		</logic:equal>		
	
		<div class="spacer">&nbsp;</div>
		
		<ul>	
			<logic:notEmpty name="assetForm" property="asset.format.contentType">
				<li>
					<a href="../servlet/display?file=<bean:write name='assetForm' property='encryptedFilePath'/>&contentType=<bean:write name='assetForm' property='asset.format.contentType'/>&filename=<bean:write name='assetForm' property='asset.name'/>.<bean:write name='assetForm' property='asset.format.fileExtension'/>">view file</a>
				</li>
			</logic:notEmpty>
			<c:if test="${assetForm.assetInAssetBox && !userprofile.assetBox.shared || userprofile.assetBox.editable}">
				<li>
					<a href="../action/addToAssetBox?id=<bean:write name='assetForm' property='asset.id'/>&amp;forward=/action/viewAsset" class="lbAdd">
						<c:if test="${userprofile.numAssetBoxes>1}"><bright:cmsWrite identifier="link-add-to-lightbox" filter="false" /></c:if>
						<c:if test="${userprofile.numAssetBoxes<=1}"><bright:cmsWrite identifier="link-add-to-mylightbox" filter="false" /></c:if>
					</a> 
				</li>
			</c:if>
			<c:if test="${!assetForm.assetInAssetBox && assetForm.canRemoveAssetFromAssetBox}">
				<li>
					<a href="../action/removeFromAssetBox?id=<bean:write name='assetForm' property='asset.id'/>&amp;forward=/action/viewAsset" class="lbRemove"><bright:cmsWrite identifier="link-remove-lightbox" filter="false" /></a>
				</li>
			</c:if>
			
		</ul>
	</div>

</div>

<%@include file="inc_attribute_fields_with_extensions.jsp"%>

<%@include file="../inc/foot.jsp"%>	
</body>
</html>