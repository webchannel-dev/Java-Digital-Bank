<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	21-Aug-2010		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="title-default-related-assets" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="helpsection" value="default-relationships"/>		

</head>

<body id="detailsPage" class="assetSelectPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-default-related-assets" filter="false"/></h1>

	<bright:cmsWrite identifier="default-related-assets-main" filter="false"/>

	<logic:equal name="assetForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="assetForm" property="errors" id="errorText">
				<bright:writeError name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<div class="hr"></div>
	
	<h3><bright:cmsWrite identifier="label-child-assets" filter="false"/>:</h3>
		
		
	<html:form action="saveAssetRelationships" styleId="selectableAssets" method="post">
	
		<%@include file="inc_asset_relationship_hiddens.jsp"%> 	
		<html:hidden name="assetForm" property="peerId" />
		<html:hidden name="assetForm" property="parentId" />
		<html:hidden name="assetForm" property="asset.peerAssetIdsAsString"/>
		<html:hidden name="assetForm" property="asset.childAssetIdsAsString"/>
		<html:hidden name="assetForm" property="asset.parentAssetIdsAsString"/>
		<html:hidden name="assetForm" property="addingFromBrowseCatId" />
		<html:hidden name="assetForm" property="addingFromBrowseTreeId" />
		<bean:parameter name="add" id="add" value="false" />
		<input type="hidden" name="add" value="<bean:write name='add' />" />

		<logic:notEmpty name="assetForm" property="childDefaultRelatedAssets">
			<ul class="lightbox clearfix">
				<logic:iterate name="assetForm" property="childDefaultRelatedAssets" id="asset">
					<bean:define id="assetId" name="asset" property="id" />
					<li class="selectable">						
					
						<div class="detailWrapper">
							<%@include file="../inc/result-resultimgclass.jsp"%>					
							<bean:define id="disablePreview" value="true"/>	
							<%@include file="../inc/view_thumbnail.jsp"%>					
							<br />		
							<c:set var="item" value="${asset}"/>	
							<%@include file="../inc/result_asset_descriptions.jsp"%>
							<%@include file="../inc/result-video-audio-icon.jsp"%>					
						</div>		
								
						<html:multibox name="assetForm" property="childAssetIdArray" styleClass="checkbox" styleId="<%=\"relateChildAsset\"+assetId%>">
							<bean:write name="assetId" />
						</html:multibox>
					</li>
				
				
				</logic:iterate>
			</ul>	
		</logic:notEmpty>
		<logic:empty name="assetForm" property="childDefaultRelatedAssets">	
			<bright:cmsWrite identifier="default-related-assets-empty-child" filter="false"/>
		</logic:empty>

		<div class="hr"></div>
		
		<h3><bright:cmsWrite identifier="label-peer-assets" filter="false"/>:</h3>

		<logic:notEmpty name="assetForm" property="peerDefaultRelatedAssets">
			<ul class="lightbox clearfix">
				<logic:iterate name="assetForm" property="peerDefaultRelatedAssets" id="asset">
					
					<bean:define id="assetId" name="asset" property="id" />
					<li class="selectable">						
						<div class="detailWrapper">
							<%@include file="../inc/result-resultimgclass.jsp"%>					
							<bean:define id="disablePreview" value="true"/>	
							<%@include file="../inc/view_thumbnail.jsp"%>					
							<br />		
							<c:set var="item" value="${asset}"/>	
							<%@include file="../inc/result_asset_descriptions.jsp"%>
							<%@include file="../inc/result-video-audio-icon.jsp"%>					
						</div>		
						<html:multibox name="assetForm" property="peerAssetIdArray" styleClass="checkbox" styleId="<%=\"relatePeerAsset\"+assetId%>">
							<bean:write name="assetId" />
						</html:multibox>
					</li>
				</logic:iterate>
			</ul>
		</logic:notEmpty>
		<logic:empty name="assetForm" property="peerDefaultRelatedAssets">	
			<bright:cmsWrite identifier="default-related-assets-empty-peer" filter="false"/>
		</logic:empty>
		
		
		
		<div class="hr"></div>
		
		<c:choose>
			<c:when test="${empty assetForm.peerDefaultRelatedAssets && empty assetForm.childDefaultRelatedAssets}">
				<bean:define id="asset" name="assetForm" property="asset" />
				<%@include file="../inc/inc_asset_type.jsp"%>
				<a href="viewAsset?id=<bean:write name='assetForm' property='asset.id'/>" class="backLink"><bright:cmsWrite identifier="link-back-image-details" filter="false" replaceVariables="true" /></a>
			</c:when>
			<c:otherwise>
				<input type="submit" name="saveButton" class="button flush floated" id="submitButton" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
				<a href="viewAsset?id=<bean:write name='assetForm' property='asset.id'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			</c:otherwise>
		</c:choose>

		<br />
			
	</html:form>



	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>