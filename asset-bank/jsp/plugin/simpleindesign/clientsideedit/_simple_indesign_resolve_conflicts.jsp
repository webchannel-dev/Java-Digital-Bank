
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<style type="text/css">
	form.floated {
		overflow: visible;
	}
</style>

<script type="text/javascript">
	$j(function() {
		$j('#viewDuplicates').click(function() {
			$j('#selectableAssets').toggle();
		});
		
		<c:if test="${conflictsAlreadyResolved}">
			$j('#selectableAssets').hide();
		</c:if>
	});
</script>

<div class="assetSelectPage">
	<h1 class="underline"><bright:cmsWrite identifier="title-asset-file-editor"/></h1>

	<c:if test="${!lightboxIsEmpty}">

		<label class="wrapping2">
			<html:checkbox name="clientSideEditPrepForm" property="ext(simple_indesign_download_lightbox)" /> 
			
			<strong><bright:cmsWrite identifier="label-simple-indesign-include-lightbox-contents"/></strong> 
			<bright:cmsWrite identifier="snippet-simple-indesign-include-lightbox-contents"/>
		</label>
		<br /><br />
		
	</c:if>

	<logic:notEmpty name="duplicateAssetsMap">

		<logic:present name="failedValidationMessage">
			<div class="error">
				<bright:writeError name="failedValidationMessage" />
			</div>
		</logic:present>

		<c:choose>
			<c:when test="${conflictsAlreadyResolved}">
				<p class="infoInline"><bright:cmsWrite identifier="snippet-simple-indesign-resolved-conflicts"/> | <a href="#" id="viewDuplicates"><bright:cmsWrite identifier="link-simple-indesign-show-duplicate-assets"/></a></p>
			</c:when>
			<c:otherwise>
				<div class="warning">
					<bright:cmsWrite identifier="duplicateAssetsWarningOnEdit" filter="false"/>
				</div>				
			</c:otherwise>
		</c:choose>

		<div id="selectableAssets">
		<logic:iterate name="duplicateAssetsMap" id="linkedResource">
	
		
	    	<h3>${linkedResource.leafname}</h3> 
	    	<ul class="lightbox clearfix " style="margin-top: 1em;" rel="radio">
		    	<logic:iterate name="linkedResource" property="allMatching" id="item" indexId="index">
					<li class="selectable">
						<div class="selector">
							<label>
								<html:radio name="clientSideEditPrepForm" property="ext(simple_indesign_asset_${linkedResource.safeLeafname})" value="${item.id}" styleClass="checkbox" styleId="id${index}"/> Select item
							</label>
						
						</div>	
						<div class="detailWrapper">
							<logic:empty name="item" property="displayHomogenizedImageFile.path">
								<c:set var="resultImgClass" value="icon"/>
							</logic:empty>
							<logic:notEmpty name="item" property="displayHomogenizedImageFile.path">
								<c:set var="resultImgClass" value="image"/>
							</logic:notEmpty>
			
							<%-- There is always a thumbnail for images --%>
							<c:if test="${item.typeId==2}">
								<c:set var="resultImgClass" value="image"/>
							</c:if>
							<a class="thumb" href="#">
								<%@include file="../../../standard/en/inc/view_thumbnail.jsp"%>
							</a>
							<div class="metadataWrapper">
								<%@include file="../../../standard/en/inc/result_asset_descriptions.jsp"%>
							</div>
							
						</div>
					</li>
		    	</logic:iterate>
		    </ul>
		</logic:iterate>
		
		</div>
	</logic:notEmpty>

</div>
