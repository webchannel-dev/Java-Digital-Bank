<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	01-Mar-2007		Created
	 d2		Matt Stevenson	02-Mar-2007		Moved and continued implementation work
	 d3		Matt Stevenson	05-Mar-2007		Modified update links
	 d4		Matt Stevenson	08-Mar-2007		Changed to use parameters for directory, adding load indicator
	 d5		Matt Stevenson	30-Mar-2007		Changed to use flash indicator
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bean:parameter id="id" name="id" value="0"/>
		
<bright:applicationSetting id="showFlashVideoOnViewDetails" settingName="show-flash-video-on-view-details"/>

<head>
	
	<title><bright:cmsWrite identifier="title-edit-relationships" filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="helpsection" value="edit_related_assets"/>		

</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-edit-relationships" filter="false" /></h1>

	<logic:equal name="assetForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="assetForm" property="errors" id="errorText">
				<bright:writeError name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<div class="clearfix">
    	<div class="floatLeft">
			<logic:empty name="assetForm" property="asset.displayPreviewImageFile.path">
				<div class="thumbFile">
					<bean:define id="asset" name="assetForm" property="asset" />
					<bean:define id="resultImgClass" value="icon" />
					<%@include file="../inc/view_thumbnail.jsp"%>		
				</div>
			</logic:empty>
			<logic:notEmpty name="assetForm" property="asset.displayPreviewImageFile.path">
				<bean:define id="asset" name="assetForm" property="asset" />
				<bean:define id="resultImgClass" value="image floatLeft" />
				<bean:define id="overideLargeImageView" value="true"/>
				<%@include file="../inc/view_preview.jsp"%>				
			</logic:notEmpty>
		</div>
	</div>
	<br/>
			
	<c:set var="hidePromoted" value="true"/>
	<c:set var="hideFeatured" value="true"/>
	<%@include file="../public/inc_attribute_fields_with_extensions.jsp"%>
		
	<html:form action="saveAssetRelationships" method="post" styleClass="floated">
		
		
		<%@include file="inc_asset_relationship_hiddens.jsp"%> 	

		<%-- If you add a new field make sure that you copy it from the old to the new Asset object in ViewEditAssetRelationshipsAction when validation fails --%>	
		<c:if test="${assetForm.entityCanHaveParents}">
			<label for="parentIds">
				<c:if test="${not empty assetForm.asset.entity.childRelationshipFromName}"><bean:write name="assetForm" property="asset.entity.childRelationshipFromName" /></c:if>
				<c:if test="${empty assetForm.asset.entity.childRelationshipFromName}"><bright:cmsWrite identifier="label-parent" filter="false"/></c:if> <bright:cmsWrite identifier="label-ids" filter="false"/>
			</label>		
			<html:text name="assetForm" property="asset.parentAssetIdsAsString" styleId="parentIds" />
			<br />
		</c:if>

		<c:if test="${assetForm.asset.entity.allowChildren}">
	
			<label for="childIds"><bean:write name="assetForm" property="asset.entity.childRelationshipToName" /> <bright:cmsWrite identifier="label-ids" filter="false"/></label>
			<html:text name="assetForm" property="asset.childAssetIdsAsString" styleId="childIds" />
			<br />

		</c:if>
		<c:if test="${assetForm.asset.entity.allowPeers}">

			<label for="peerIds"><bean:write name="assetForm" property="asset.entity.peerRelationshipToName" /> <bright:cmsWrite identifier="label-ids" filter="false"/></label>
			<html:text name="assetForm" property="asset.peerAssetIdsAsString" styleId="peerIds" />
			<br />

		</c:if>

		
		<div class="hr"></div>	
			
		
		<input type="submit" name="saveButton" class="button flush" id="submitButton" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		<a href="viewAsset?id=<bean:write name='assetForm' property='asset.id'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		<br />
			
	</html:form>

	

	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>