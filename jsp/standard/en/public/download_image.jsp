<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3     Ben Browning	14-Feb-2006		HTML/CSS tidy up
	 d4		Matt Stevenson	24-Feb-2006		Modified to make use of UsageTypeFormats
	 d5		Matt Stevenson	27-Feb-2006		Added non javascript support, fixed problem with checkbox
	 d7		Ben Browning	09-Mar-2006		Implemented tabbed download options
	 d8     Steve Bryan		18-May-2006		Changed to handle sub-usage types
	 d9		Adam Bones		23-Oct-2006		Re-write using Prototype, adding crop feature
	 d10    Adam Bones    	25-Oct-2006    	Added Email feature
	 d11    Matt Woollard   02-May-2008     Added option to watermark only when downloading converted version
	 d12	Matt Woollard	17-Dec-2008		Added support for converting to colorspaces
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="imagesAreCroppable" settingName="images-are-croppable"/>
<bright:applicationSetting id="imagesAreEmailable" settingName="images-are-emailable"/>
<bright:applicationSetting id="imagesAreCompressable" settingName="can-compress-download"/>
<bright:applicationSetting id="watermarkOptionOnDownload" settingName="watermark-option-on-download"/>
<bright:applicationSetting id="assetRepurposingEnabled" settingName="asset-repurposing-enabled"/>
<bright:applicationSetting id="canSelectMultipleUsageTypes" settingName="can-select-multiple-usage-types"/>

<head>

	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="downloadImage"/>
	<bean:parameter id="repurposeAsset" name="repurposeAsset" value="false"/>
	
	<title><bright:cmsWrite identifier="title-download-image" filter="false"/></title> 
	
	<%@include file="../inc/head-elements.jsp"%>	
	<script src="../js/cropper/cropper.js" type="text/javascript" ></script>
	<script src="../js/toggle-button.js" type="text/javascript"></script>
	<script src="../js/download-form.js" type="text/javascript"></script>

	<%@include file="inc_download_js.jsp"%>		
	<%@include file="inc_cropper_js.jsp"%>		

</head>

<%@include  file="inc_download_form_settings.jsp"%>

<body  id="downloadPage" onload="init(); hideContent();">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-download-image" filter="false" /></h1> 			

	<c:set var="assetTypeName"><bright:cmsWrite identifier="image" case="mixed" filter="false"/></c:set>
   <%@include file="inc_download_top.jsp"%>				 
       
    <div>	
		<bean:define id="asset" name="downloadForm" property="asset" />
		<bean:define id="resultImgClass" value="image" />								
		<bean:define id="ignoreCheckRestrict" value="yes"/>	
		<bean:define id="disablePreview" value="true"/>							
		<%@include file="../inc/view_thumbnail.jsp"%>
		<%@include file="../inc/download_attributes.jsp"%>
	</div>
	<br />
	<c:if test="${downloadForm.downloadAsDocument}">    
		<form action="viewDownloadFile?id=<c:choose><c:when test="${asset.surrogateAssetId>0}"><bean:write name="asset" property="surrogateAssetId"/></c:when><c:otherwise><bean:write name="asset" property="id"/></c:otherwise></c:choose>" method="post" class="js-enabled-show" id="downloadOption">
			
			<p>Please select how you want to download <em><bean:write name='downloadForm' property='asset.name'/></em>:</p>
			<ul class="radioList">
			   <li><input type="radio" name="dl_option" id="dl_file" class="radio"  onclick="$j('#loading').show(); $j('#downloadOption').submit();" /><span><label for="dl_file"><bright:cmsWrite identifier="label-download-as-file" filter="false"/> <span id="loading" style="display:none"><bright:cmsWrite identifier="snippet-loading" filter="false"/></span></label></span></li>
			   <li><input type="radio" name="dl_option" id="dl_image" class="radio" checked="checked" /><span><label for="dl_image"><bright:cmsWrite identifier="label-download-as-image" filter="false"/></label></span></li>
			</ul>
			
		</form>	
      <p class="js-enabled-hide">
          <a href="viewDownloadFile?id=<c:choose><c:when test="${asset.surrogateAssetId>0}"><bean:write name="asset" property="surrogateAssetId"/></c:when><c:otherwise><bean:write name="asset" property="id"/></c:otherwise></c:choose>"><bright:cmsWrite identifier="link-download-as-file" filter="false"/></a>
      </p>
   </c:if>
    
		<bean:define id="action" value='viewDownloadImage'/>
		<bean:parameter id="returnUrl" name="returnUrl" value=""/>
		<logic:equal name="showTabs" value="true">
			
			<c:if test="${userprofile.isAdmin || downloadForm.asset.showAdvancedDownloadOptions}">
				<p class="tabHolderPopup clearfix">
					<logic:equal name="advanced" value="false">
						<a class="active" href="../action/<bean:write name='action'/>?id=<bean:write name='downloadForm' property='asset.id'/>&amp;assetUse.usageTypeId=<c:out value="${downloadForm.selectedUsageType.id}" />&amp;advanced=false&amp;parentId=<c:out value="${downloadForm.parentId}"/>&amp;repurpose=<bean:write name="downloadForm" property="repurpose"/>&amp;repurposeAsset=<bean:write name="repurposeAsset"/>&amp;returnUrl=<bright:write name="returnUrl" encodeForUrl="true"/>"><bright:cmsWrite identifier="tab-quick-download" filter="false"/></a>
						<a href="../action/<bean:write name='action'/>?id=<bean:write name='downloadForm' property='asset.id'/>&amp;assetUse.usageTypeId=<c:out value="${downloadForm.selectedUsageType.id}" />&amp;advanced=true&amp;parentId=<c:out value="${downloadForm.parentId}"/>&amp;repurpose=<bean:write name="downloadForm" property="repurpose"/>&amp;repurposeAsset=<bean:write name="repurposeAsset"/>&amp;returnUrl=<bright:write name="returnUrl" encodeForUrl="true"/>"><bright:cmsWrite identifier="tab-advanced-download" filter="false"/></a>
					</logic:equal>
					<c:if test="${advanced == true}">
						<a href="../action/<bean:write name='action'/>?id=<bean:write name='downloadForm' property='asset.id'/>&amp;assetUse.usageTypeId=<c:out value="${downloadForm.selectedUsageType.id}" />&amp;advanced=false&amp;parentId=<c:out value="${downloadForm.parentId}"/>&amp;repurpose=<bean:write name="downloadForm" property="repurpose"/>&amp;repurposeAsset=<bean:write name="repurposeAsset"/>&amp;returnUrl=<bright:write name="returnUrl" encodeForUrl="true"/>"><bright:cmsWrite identifier="tab-quick-download" filter="false"/></a>
						<a class="active" href="../action/<bean:write name='action'/>?id=<bean:write name='downloadForm' property='asset.id'/>&amp;assetUse.usageTypeId=<c:out value="${downloadForm.selectedUsageType.id}" />&amp;advanced=true&amp;parentId=<c:out value="${downloadForm.parentId}"/>&amp;repurpose=<bean:write name="downloadForm" property="repurpose"/>&amp;repurposeAsset=<bean:write name="repurposeAsset"/>&amp;returnUrl=<bright:write name="returnUrl" encodeForUrl="true"/>"><bright:cmsWrite identifier="tab-advanced-download" filter="false"/></a>
					</c:if>
				</p>
				<div id="tabContent">
			</c:if>
		</logic:equal>
		
		<c:if test="${!userprofile.isFromCms}">
			<bean:define id="actionUrl" value="../action/downloadImage"/>
		</c:if>
		<c:if test="${userprofile.isFromCms}">
			<bean:define id="actionUrl" value="../action/cmsDownloadImage"/>
		</c:if>

		<bean:size id="numUsageTypeFormats" name="downloadForm" property="usageTypeFormats"/>
		
		<form action="<bean:write name='actionUrl'/>" method="get" name="downloadForm" id="downloadForm" >
	     		
			<input type="hidden" name="advanced" value="<c:out value='${advanced}'/>"/>	
     		<html:hidden name="downloadForm" property="asset.id"/>
     		<c:if test="${downloadForm.parentId>0}">
     			<html:hidden name="downloadForm" property="parentId"/>
     		</c:if>
     		<input type="hidden" name="showTabs" value="<bean:write name="showTabs"/>"/>
			<input type="hidden" name="repurposeAsset" value="<c:out value='${repurposeAsset}'/>"/>
			<input type="hidden" name="returnUrl" value="<c:out value='${returnUrl}'/>"/>
			<c:set var="msgDownload" value="" />
			<%@include file="inc_usage_selection.jsp"%>
			
			<bean:size id="numTints" name="downloadForm" property="tints"/>
						
			<%-- Show the form if there is a usage selected. --%>
			<c:if test="${!usageIsSelected}">
				<html:hidden name="downloadForm" property="repurpose"/>
			</c:if>
			<c:if test="${usageIsSelected}">
					
					<%-- display secondary usage types if necessary --%>
					<c:if test="${canSelectMultipleUsageTypes}">
						<%@include file="inc_additional_usage_selection.jsp"%>
					</c:if>
					
					
					<%-- don't display if there are no sizes and user can't download original --%>
					<c:if test="${numUsageTypeFormats > 0 || downloadForm.userCanDownloadOriginal || advanced}">
						
						<%@include file="inc_tscs_selection.jsp"%>
						

						<c:if test="${!userprofile.isFromCms}">
							<c:if test="${imagesAreCompressable && !(downloadForm.selectedUsageType.usageType.highResolution && downloadForm.userMustRequestApprovalForHighRes && !downloadForm.userHasApprovalForHighRes && !downloadForm.highResOptionsDisabled)}">
								<label class="wrapping">
								<html:checkbox name="downloadForm" property="compress" styleId="compress" onclick="doAlternativeMethodSelection(this);"/> <bright:cmsWrite identifier="label-compress-file" filter="false"/></label>
							</c:if>
							
							<c:if test="${imagesAreEmailable && userprofile.userCanEmailAssets && !(downloadForm.selectedUsageType.usageType.highResolution && downloadForm.userMustRequestApprovalForHighRes && !downloadForm.userHasApprovalForHighRes && !downloadForm.highResOptionsDisabled)}">
								<label class="wrapping">
								<html:checkbox name="downloadForm" property="email" styleId="sendemail" onclick="doAlternativeMethodSelection(this);"/> <bright:cmsWrite identifier="label-email-file" filter="false"/></label><br/>
							</c:if>
							
							<c:if test="${repurposeAsset && assetRepurposingEnabled && userprofile.userCanRepurposeAssets && (numUsageTypeFormats > 0 || advanced)}">
								<label class="wrapping">
								<html:checkbox name="downloadForm" property="repurpose" styleId="repurpose"  onclick="doAlternativeMethodSelection(this);"/> <bright:cmsWrite identifier="label-repurpose" filter="false"/></label><br/>
							</c:if>
							
							<c:if test="${watermarkOptionOnDownload && !advanced}">
								<label class="wrapping">
								<html:checkbox name="downloadForm" property="watermarkImageOption" styleId="watermarkImage" /> <bright:cmsWrite identifier="label-watermark-email-asset" filter="false"/></label> 
							</c:if>
						</c:if>
			
					</c:if>
					
					<%-- Download original option --%>			
					<%-- Show this if user has permission, and if advanced/not simple or usage type is a download type	--%>
					<c:if test="${(!downloadForm.userMustRequestApprovalForHighRes || downloadForm.userHasApprovalForHighRes) && (downloadForm.userCanDownloadOriginal && ( !simpleConvertOptionsForDownload || advanced || downloadForm.selectedUsageType.usageType.downloadOriginal))}">																		
						<%@include file="inc_download_original.jsp"%>
					</c:if> 

				   <%-- In the case of simple convert and simple tab and download original usage type and can download original and no sizes then do not show the convert --%>
				   <c:if test="${ !(simpleConvertOptionsForDownload && !advanced && downloadForm.selectedUsageType.usageType.downloadOriginal && downloadForm.userCanDownloadOriginal && numUsageTypeFormats==0) }">
						
						<div class="dialogPanel" style="margin-bottom:0;">
													
							<c:choose>
								<c:when test="${(advanced || !(simpleConvertOptionsForDownload)) && (userprofile.isAdmin || downloadForm.asset.showAdvancedDownloadOptions)}">
									
									<bright:refDataList componentName="ImageAssetManagerImpl" methodName="getDownloadableImageFormats" id="downloadFormats"/>
									<%@include file="inc_media_download_options.jsp"%>
									
									<h3 class="expand" id="expandLink"><a href="javascript:;" onclick="expand_content('advOptions');"><bright:cmsWrite identifier="subhead-adv-options" filter="false" /></a></h3>
									
									<div id="advOptions">
										<h3 class="collapse" id="collapseLink"><a href="javascript:;" onclick="collapse_content('advOptions');"><bright:cmsWrite identifier="subhead-adv-options" filter="false" /></a></h3>
										
										<label for="density"><bright:cmsWrite identifier="label-density" filter="false"/></label>
										<html:text name="downloadForm" property="density" size="8" styleClass="small text" styleId="density" />						
											<a href="../action/viewHelp?helpsection=imagedensity" target="_blank" title="Information about image density" class="help-popup"><img src="../images/standard/icon/help.gif" alt="What is density?" width="15" height="15" class="help" border="0" /></a>
										<br />
										<label for="jpegQuality"><bright:cmsWrite identifier="label-jpeg-quality" filter="false"/> </label>
										<html:text name="downloadForm" property="jpegQuality" size="8" styleClass="small text" styleId="jpegQuality"/>
										<br />
								
										<label for="rotateImage"><bright:cmsWrite identifier="label-rotate" filter="false"/></label>
										<html:select name="downloadForm" property="rotationAngle" styleClass="small" styleId="rotateImage">
												<%@include file="../customisation/rotate_image_options.jsp"%>
										</html:select>												
										<br />

										<label for="applyStrip"><bright:cmsWrite identifier="label-strip" filter="false"/></label>
										<html:checkbox name="downloadForm" property="applyStrip" styleClass="checkbox" styleId="applyStrip"/>
										<br />
										
										<bean:size id="numColorSpaces" name="downloadForm" property="colorSpaces"/>
									
										<%-- Show RGB checkbox if only one colorspace option --%>
										<c:if test="${numColorSpaces==1 && downloadForm.asset.colorSpace!=1}">
											<tr>
												<th>
													<label for="convertRGB"><bright:cmsWrite identifier="label-convert-rgb" filter="false"/></label>
												</th> 
												<td class="checkbox">
													<input type="checkbox" class="checkbox" name="selectedColorSpaceId" id="convertRGB" value="1" <c:if test='${downloadForm.selectedColorSpaceId==1}'>checked</c:if>/>
												</td>
											</tr>	
											<br/>
										</c:if>
										
										<%-- Derermine whether to show the colorspace dropdown --%>
										<c:if test="${numColorSpaces>1}">
											<label class="narrow"><bright:cmsWrite identifier="label-color-space" filter="false"/></label>
												<bean:define id="colorSpaces" name="downloadForm" property="colorSpaces"/>												
												<html:select name="downloadForm" property="selectedColorSpaceId" size="1" style="width:auto">
													<html:option value="0"><bright:cmsWrite identifier="snippet-no-conversion"/></html:option>
													<html:options collection="colorSpaces" labelProperty="description" property="id" filter="false"/>
												</html:select>
											<br />
										</c:if>
										
									</div>
									<div class="hr"></div>
								</c:when>

								<c:otherwise>
									<%-- The user can just pick from a dropdown of predefined sizes --%>
									<c:choose>
										<c:when test="${downloadForm.asset.format.name=='GIF'}">
											<html:hidden name="downloadForm" property="imageFormat" value="gif"/>
										</c:when>
										<c:when test="${downloadForm.asset.format.name=='PNG'}">
											<html:hidden name="downloadForm" property="imageFormat" value="png"/>
										</c:when>											
										<c:otherwise>
											<html:hidden name="downloadForm" property="imageFormat" value="jpg"/>
										</c:otherwise>
									</c:choose>
									<%-- Hide the size dropdown if it has only one entry --%>
									
									<c:choose>
										<c:when test="${numUsageTypeFormats == 0}">
											<bright:cmsWrite identifier="noPermissionUsageType" filter="false"/>
										</c:when>
										<c:when test="${numUsageTypeFormats == 1 && downloadForm.usageTypeFormats[0].cropToFit && !(downloadForm.selectedUsageType.usageType.downloadOriginal && downloadForm.userCanDownloadOriginal)}">
											<label for="usagetypeformat" class="narrow"><bright:cmsWrite identifier="label-size" filter="false"/></label> <bean:write name='downloadForm' property='usageTypeFormats[0].description' filter="false"/><br />
											<input type="hidden" name="usageTypeFormatId" value="<c:out value='${downloadForm.usageTypeFormats[0].id}'/>"/>
										</c:when>
										<c:when test="${numUsageTypeFormats == 1 && !downloadForm.usageTypeFormats[0].cropToFit && !(downloadForm.selectedUsageType.usageType.downloadOriginal && downloadForm.userCanDownloadOriginal)}">
											<input type="hidden" name="usageTypeFormatId" value="<c:out value='${downloadForm.usageTypeFormats[0].id}'/>"/>
										</c:when>

										<c:otherwise>
											<label for="usagetypeformat" class="narrow"><bright:cmsWrite identifier="label-size" filter="false"/></label> 
											<!-- <select name="usageTypeFormatId" style="width:auto;" id="usagetypeformat" onchange="updateCrops(this.value); clearCrop(); updateLayer(); "> -->
											<select name="usageTypeFormatId" style="width:auto;" id="usagetypeformat" onchange="document.getElementById('forceClearCropMask').value = 1; document.getElementById('submitUsageTypeFormat').click();">
											
												<logic:iterate name="downloadForm" property="usageTypeFormats" id="format">
													<option value="<bean:write name='format' property='id'/>" <c:if test="${format.id == downloadForm.usageTypeFormatId}">selected</c:if>><bean:write name="format" property="description" filter="false"/></option>
												</logic:iterate>
											</select>
											
											<input type="submit" name="b_usageTypeFormat" id="submitUsageTypeFormat" class="button js-enabled-hide" value="<bright:cmsWrite identifier="button-go-arrow" filter="false" />" style="display: inline;" />
											
											<br />
										</c:otherwise>
									</c:choose>
									
									<c:if test="${numUsageTypeFormats != 0}">
										<%@include file="inc_download_tints.jsp"%>	
									</c:if>
									
									<!-- croppper div -->
									<div id="cropper">
										
										<%@include file="inc_cropper.jsp"%>												
									</div>
									
									<!-- simple download colorspace -->
								
									<bean:size id="numColorSpaces" name="downloadForm" property="colorSpaces"/>
									
									<%-- Show RGB checkbox if only one colorspace option --%>
									<c:if test="${numColorSpaces==1 && downloadForm.selectedUsageTypeFormat.colorSpace==0 && downloadForm.asset.colorSpace!=1}">
										<tr>
											<th>
												<label for="convertRGB"><bright:cmsWrite identifier="label-convert-rgb" filter="false"/></label>
											</th> 
											<td class="checkbox">
												<input type="checkbox" class="checkbox" name="selectedColorSpaceId" id="convertRGB" value="1" <c:if test='${downloadForm.selectedColorSpaceId==1}'>checked</c:if>/>
											</td>
										</tr>	
										<br/>
									</c:if>
									
									<%-- Derermine whether to show the colorspace dropdown --%>
									<c:if test="${downloadForm.selectedUsageTypeFormat.colorSpace==0 && numColorSpaces>1}">
										<label class="narrow"><bright:cmsWrite identifier="label-color-space" filter="false"/></label>
										<div style="float:left">
											<bean:define id="colorSpaces" name="downloadForm" property="colorSpaces"/>												
											<html:select name="downloadForm" property="selectedColorSpaceId" size="1" style="width:auto">
												<html:option value="0"><bright:cmsWrite identifier="snippet-no-conversion"/></html:option>
												<html:options collection="colorSpaces" labelProperty="description" property="id" filter="false"/>
											</html:select>
										</div>
										<br />
									</c:if>
									
									<%-- Force a colorspace if one selected for the usage type size --%>
									<c:if test="${downloadForm.selectedUsageTypeFormat.colorSpace!=0}">
											<input type="hidden" name="selectedColorSpaceId" value="<c:out value='${downloadForm.selectedUsageTypeFormat.colorSpace}'/>"/>	
									</c:if>
									
									
									<c:if test="${downloadForm.asset.format.canConvertIndividualLayers && downloadForm.asset.numPages > 1}">
										<label for="layer" class="narrow"><bright:cmsWrite identifier="label-layer" filter="false"/> </label>
										<html:select name="downloadForm" property="layerToDownload" styleId="layerToConvert">
											<c:forEach begin="1" end="${downloadForm.asset.numPages}" var="layer">
												<option value="<c:out value="${layer}"/>" <c:if test="${layer==downloadForm.layerToDownload}">selected="selected"</c:if>><c:out value="${layer}"/></option>
											</c:forEach>
											<html:option value="0"><bright:cmsWrite identifier="snippet-all" filter="false" /></html:option>
										</html:select>
										<br />
									</c:if>
									
																			
								</c:otherwise>
							</c:choose>
							
							<c:if test="${numUsageTypeFormats > 0 || advanced}">
								<div style="height:1%">
								<%@include file="inc_download_buttons.jsp"%>
								</div>
							</c:if>
							<br />
						</div><!-- end of -->
					</c:if>
				</c:if>
			
     	</form>
    <logic:equal name="showTabs" value="true">
        <c:if test="${userprofile.isAdmin || downloadForm.asset.showAdvancedDownloadOptions}">
            </div> <!-- end of tabContent -->
        </c:if>
	</logic:equal>	
    <br />	
      

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>
