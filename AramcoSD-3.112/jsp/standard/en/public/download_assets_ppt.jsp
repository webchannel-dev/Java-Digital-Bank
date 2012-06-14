<%@include file="../inc/doctype_html.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/search?cachedCriteria=1"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="snippet-search-results" filter="false" /></c:set>

<%@include file="../inc/set_this_page_url.jsp"%>
<bright:applicationSetting id="bShowAddAllToLightbox" settingName="show-add-all-to-lightbox"/>
<bright:applicationSetting id="bAddAllToLightboxLimit" settingName="add-all-to-assetbox-limit"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="hideLightbox" settingName="hide-lightbox"/>
<bright:applicationSetting id="usePriceBands" settingName="price-bands"/>
<bright:applicationSetting id="titleMaxLength" settingName="results-title-max-length"/>
<bright:applicationSetting id="descriptionMaxLength" settingName="results-description-max-length"/>
<bright:applicationSetting id="hideThumbnails" settingName="hide-thumbnails-on-browse-search"/>
<bright:applicationSetting id="savedSearchesEnabled" settingName="saved-searches-enabled"/>
<bright:applicationSetting id="highlightIncompleteAssets" settingName="highlight-incomplete-assets"/>
<bright:applicationSetting id="highlightRestrictedAssets" settingName="highlight-restricted-assets"/>
<bright:applicationSetting id="relationshipLimit" settingName="select-all-relationship-limit"/>
<bright:applicationSetting id="userDrivenSorting" settingName="user-driven-sorting-enabled"/>
<bright:applicationSetting id="enableSlideshow" settingName="enable-slideshow"/>
<bright:applicationSetting id="orgUnitAdminsCanExport" settingName="org-unit-admins-can-export"/>

<head>
	
	<title><bright:cmsWrite identifier="title-search-results" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="helpsection" value="search_results"/>
	
	<script type="text/javascript" charset="utf-8">
		$j(function() {									// when DOM has loaded
			document.getElementById("id0").checked="checked";
			
			// Set up some variables:
			var $checkArray = $j('input.radio');		//get reference to all checkboxes on the page
			var $checkedboxes = $j('li input:checked');	//get reference to all initially checked checkboxes on the page
			var $liArray = $j('ul.lightbox > li');
			// Highlight wrapping li of any already selected checkboxes:
			$checkedboxes.parents('li').addClass('selected');

			// Click event on panels:
			$liArray.click(function(event) {
				event.stopPropagation();	
				$liArray.removeClass('selected');
				// Select the radio button:
				$j(this).find('input.radio').attr('checked', 'checked');
				// Highlight surrounding li:
				$j(this).toggleClass('selected');
			});

		});
	</script>
</head>

<body id="resultsPage" class="assetSelectPage">

	<%@include file="../inc/body_start.jsp"%>
   
	<h1 class="underline">Select PowerPoint Template</h1>

	<logic:notEmpty name="assetForm">
		<logic:equal name="assetForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="assetForm" property="errors" id="error">
					<bean:write name="error" filter="false"/>
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:notEmpty>
	
	<bright:cmsWrite identifier="snippet-select-ppt-template-info" />
   <form name="downloadForm" action="downloadAssetBox" method="post">
   		<input name="powerpointTemplateFile" value="test.ppt" type="hidden" />
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
		<html:hidden name="assetBoxDownloadForm" property="downloadingImages"/>
  			
   		<ul class="lightbox clearfix" style="margin-top: 1em;">

				<logic:iterate name="assetBoxDownloadForm" property="PPTTemplates" id="item" indexId="index">

							<li>
								<div class="selector">
									<label>
										<html:radio name="item" property="id" value="${item.id}" styleClass="radio" styleId="id${index}"/>Select item
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
							
								<%@include file="../inc/view_thumbnail.jsp"%>								
								<%@include file="../inc/result_asset_descriptions.jsp"%>
								</div>
								
							</li>
				</logic:iterate>
		</ul>
		
		<div class="hr"></div>
		
		<input class="button flush floated" type="submit" value="<bright:cmsWrite identifier="button-select" />" /> <a href="viewAssetBox" class="cancelLink"><bright:cmsWrite identifier="button-cancel" /></a>
		
	</form>
	      
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>