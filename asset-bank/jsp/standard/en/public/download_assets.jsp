<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3     	Ben Browning    14-Feb-2006     HTML/CSS tidy up
	 d4		Matt Stevenson	27-Feb-2006		Changed to facilitate usage type format
 	 d5		Matt Stevenson	28-Feb-2006		Futher changes for usage type formats
	 d6		Matt Stevenson	06-Mar-2006		Changed simple download
	 d7		Ben Browning	09-Mar-2006		Implemented tabbed download options
	 d8      Steve Bryan    18-May-2006    Added support for sub-usage types
	 d9     Matt Woollard   21-Jan-2009    Added support for downloading selected assets
	 d10     Matt Woollard       01-Apr-2009     Changed to support downloading of child assets
	 d11     Matt Woollard       04-Jun-2009     Added warning for mixture of high and low res approved assets
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="imagesAreEmailable" settingName="images-are-emailable"/>
<bright:applicationSetting id="watermarkOptionOnDownload" settingName="watermark-option-on-download"/>
<bright:applicationSetting id="lightboxMaxDownloadSize" settingName="lightbox-max-download-size"/>
<bright:applicationSetting id="showRequestOnCd" settingName="show-request-on-cd"/>
<bright:applicationSetting id="showLabelOnDownload" settingName="show-attribute-label-on-download"/>
<bright:applicationSetting id="canSelectMultipleUsageTypes" settingName="can-select-multiple-usage-types"/>

<bean:define id="downloadForm" name="assetBoxDownloadForm"/>

<c:choose>
	<c:when test="${downloadForm.downloadingLightbox}">
		<c:set var="viewURL" value="../action/viewDownloadAssetBox"/>
		<c:set var="downloadURL" value="../action/downloadAssetBox"/>
		<c:set var="title"><bright:cmsWrite identifier="title-download-lightbox" filter="false"/></c:set>
	</c:when>
	<c:when test="${downloadForm.downloadingChildAssets}">
		<c:set var="viewURL" value="../action/downloadChildAssets"/>
		<c:set var="title"><bright:cmsWrite identifier="title-download-child-assets" filter="false"/></c:set>
	</c:when>
</c:choose>	
			

<head>
	
	<title><c:out value='${title}'/></title>
	<script src="../js/lib/prototype.js" type="text/javascript"></script>
	<script src="../js/popup.js" type="text/javascript"></script>
	<script src="../js/toggle-button.js" type="text/javascript"></script>
	<%@include file="../inc/head-elements.jsp"%>

	<%@include file="inc_download_js_variables.jsp"%>
	<script type="text/javascript">
		<!--  

      function setFormElementVisibility(ctrl,sId)
      {
      	if(ctrl.selectedIndex==ctrl.options.length-1)
      	{
      		document.getElementById(sId).style.display="block";
      	}
      	else
      	{
      		document.getElementById(sId).style.display="none";
      	}
      }
		
		//-->      
	</script>

	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="download_lightbox"/>
</head>
<body id="downloadLightbox">
		
	<bean:define id="isAssetBox" value="true"/>
	<%@include file="inc_download_form_settings.jsp"%>

	<%@include file="../inc/body_start.jsp"%>
	<h1>
		<c:choose>
			<c:when test="${downloadForm.downloadingLightbox}">
				<bright:cmsWrite identifier="heading-download" filter="false" /> <bright:cmsWrite identifier="my-lightbox" filter="false" />
			</c:when>
			<c:when test="${downloadForm.downloadingChildAssets}">
				<bright:cmsWrite identifier="heading-download-child-assets" filter="false"/>
			</c:when>
		</c:choose>	
	</h1>
	
         <bean:size id="numUsageTypeFormats" name="downloadForm" property="usageTypeFormats"/>
		
		 <div class="head">
         	<c:choose>
				<c:when test="${downloadForm.downloadingLightbox}">
					<a href="../action/viewAssetBox"><bright:cmsWrite identifier="link-back-lightbox" filter="false"/></a>
				</c:when>
				<c:when test="${downloadForm.downloadingChildAssets}">
					<a href="../action/viewChildAssets?id=<c:out value='${downloadForm.parentId}'/>"><bright:cmsWrite identifier="link-back-to-children" filter="false"/></a>
				</c:when>
			</c:choose>
         </div>
         
         <logic:equal name="downloadForm" property="hasErrors" value="true"> 
         	<br />
         	<div class="error">
         		<logic:iterate name="downloadForm" property="errors" id="errorText">
         			<bright:writeError name="errorText" /><br />
         		</logic:iterate>
         	</div>
         </logic:equal>
         
         <h2>
         	<c:choose>
					<c:when test="${downloadForm.downloadingLightbox}">
						<bright:cmsWrite identifier="subhead-dl-lightbox-zip" filter="false"/>
					</c:when>
					<c:when test="${downloadForm.downloadingChildAssets}">
						<bright:cmsWrite identifier="subhead-download-child-assets" filter="false"/>
					</c:when>
				</c:choose>	
			</h2>				 
			<%-- Show warning messages for a mixed bag --%>
			 <c:if test="${!downloadForm.canDownloadAllAssets}"> 
				<bright:cmsWrite identifier="subhead-zip-perm-items" filter="false"/>
			 </c:if>
			 <c:if test="${downloadForm.userCanDownloadOriginalAny && downloadForm.downloadOriginalCount <= 0}"> 
				<bright:cmsWrite identifier="subhead-zip-noperm-original" filter="false"/>
			 </c:if>
			 <c:if test="${downloadForm.userCanDownloadAdvancedAny && !downloadForm.userCanDownloadAdvanced}"> 
				<bright:cmsWrite identifier="subhead-zip-noperm-advanced" filter="false"/>
			 </c:if>
			 
			 <c:if test="${downloadForm.someAssetsRequireHighResApproval}"> 
				<div class="warning"><bright:cmsWrite identifier="snippet-mixed-res-lightbox" filter="false"/></div>
			 </c:if>
			 
			<bright:cmsWrite identifier="download-lightbox" filter="false"/>
			<br/>
         
			<c:set var="showTitle" value="true"/>
			<logic:notEmpty name="downloadForm" property="assetApprovals">
				<logic:iterate name="downloadForm" property="assetApprovals" id="approval">
					<logic:notEmpty name="approval" property="adminNotes">
						<c:if test="${showTitle}"><strong><bright:cmsWrite identifier="label-download-approval-notes" filter="false"/></strong><br/><c:set var="showTitle" value="false"/></c:if>
						<bean:define id="assetId" name="approval" property="asset.id"/>
						<bean:define id="asset" name="downloadForm" property="<%= \"assetWithId(\" + assetId + \")\" %>"/>
						<em><bright:cmsWrite identifier="snippet-notes-for" filter="false"/> <bean:write name="asset" property="name"/>:</em> <bean:write name="approval" property="adminNotes"/><br/><br/>
					</logic:notEmpty>
				</logic:iterate>
			</logic:notEmpty>
			
			<c:if test="${downloadForm.maxDownloadAttributes > 0}">
				<c:set var="attributeIntroText"><bright:cmsWrite identifier="copy-download-attributes-lightbox" filter="false"/></c:set>
				<c:if test="${not empty attributeIntroText}">
					<div><bean:write name="attributeIntroText" filter="false"/></div>
				</c:if>
				<div class="lightboxDownloadAttributes">
					<logic:iterate name="downloadForm" property="assets" id="asset" indexId="assetIndex">
						<h4><c:out value="${asset.asset.name}"/></h4>
						<c:choose>	
							<c:when test="${downloadForm.maxDownloadAttributes>1}">
								<table class="form stripey stripey<c:out value="${assetIndex}"/>" cellpadding="0" cellspacing="0" border="0">
							</c:when>
							<c:otherwise>
								<table class="singleAttribute" cellpadding="0" cellspacing="0" border="0">
							</c:otherwise>
						</c:choose>
						<logic:iterate id="attributeValue" name="asset" property="downloadAttributes">
							<c:set var="attributeValue" scope="request" value="${attributeValue}"/>
							<c:set var="reqHideLabels" scope="request" value="${hideLabels || (downloadForm.maxDownloadAttributes==1 && !showLabelOnDownload)}"/>
							<c:set var="assetIdForAttributes" scope="request" value="${downloadForm.asset.id}"/>
							<c:set var="showEmptyAtts" scope="request" value="false"/>
							<c:set var="showEmptyValueText" scope="request" value="true"/>
							<jsp:include flush="true" page="../public/inc_view_attribute_field.jsp"/>
						</logic:iterate>
						</table>

					</logic:iterate>
				</div>
			</c:if>
			
			<logic:equal name="showTabs" value="true">
				<p class="tabHolder">
					<logic:equal name="advanced" value="false">
						<a class="active" href="<c:out value='${viewURL}'/>?advanced=false&assetUse.usageTypeId=<c:out value='${downloadForm.selectedUsageType.id}' />&id=<c:out value='${downloadForm.parentId}'/>&onlyDownloadSelected=<c:out value='${downloadForm.onlyDownloadSelected}' />"><bright:cmsWrite identifier="tab-quick-download" filter="false"/></a>
						<a href="<c:out value='${viewURL}'/>?advanced=true&assetUse.usageTypeId=<c:out value='${downloadForm.selectedUsageType.id}' />&id=<c:out value='${downloadForm.parentId}'/>&onlyDownloadSelected=<c:out value='${downloadForm.onlyDownloadSelected}' />"><bright:cmsWrite identifier="tab-advanced-download" filter="false"/></a>
					</logic:equal>
					<logic:equal name="advanced" value="true">
						<a href="<c:out value='${viewURL}'/>?advanced=false&assetUse.usageTypeId=<c:out value='${downloadForm.selectedUsageType.id}' />&id=<c:out value='${downloadForm.parentId}'/>&onlyDownloadSelected=<c:out value='${downloadForm.onlyDownloadSelected}' />"><bright:cmsWrite identifier="tab-quick-download" filter="false"/></a>
						<a class="active" href="<c:out value='${viewURL}'/>?advanced=true&assetUse.usageTypeId=<c:out value='${downloadForm.selectedUsageType.id}' />&id=<c:out value='${downloadForm.parentId}'/>&onlyDownloadSelected=<c:out value='${downloadForm.onlyDownloadSelected}' />"><bright:cmsWrite identifier="tab-advanced-download" filter="false"/></a>
					</logic:equal>
				</p>
				<div id="tabContent">
			</logic:equal>           	
						
						
									
        	<form name="downloadForm" action="<c:out value='${downloadURL}'/>" method="post" id="downloadForm">
	     		<input type="hidden" name="advanced" value="<c:out value='${advanced}'/>"/>
				<input type="hidden" name="downloadingImages" value="<c:out value='${downloadForm.containsImages}'/>"/>
				<input type="hidden" name="assetsHaveAgreement" value="<c:out value='${downloadForm.assetsHaveAgreement}'/>"/>
				<input type="hidden" name="id" value="<c:out value='${downloadForm.parentId}'/>"/>
				<html:hidden name="downloadForm" property="onlyDownloadSelected"/>	
				<html:hidden name="downloadForm" property="downloadingLightbox"/>	
				<html:hidden name="downloadForm" property="downloadingChildAssets"/>
				<input name="powerpointTemplateFile" value="" type="hidden" />
				
				<c:set var="msgDownload" value="Now choose your required format below and click 'Download'." />
				<%@include file="inc_usage_selection.jsp"%>	
							
				<%-- Show the form if there is a usage selected. --%>
				<c:if test="${usageIsSelected}">
					<%-- display secondary usage types if necessary --%>
					<c:if test="${canSelectMultipleUsageTypes}">
						<%@include file="inc_additional_usage_selection.jsp"%>
					</c:if>


					<%-- High res approval statuses --%>
					<c:set var="bSelectedUsageTypeIsHighRes" value="${downloadForm.selectedUsageType.usageType.highResolution}" />
					<c:set var="bNeedsApprovalForHighResUsage" value="${bSelectedUsageTypeIsHighRes && downloadForm.userMustRequestApprovalForHighRes && !downloadForm.highResOptionsDisabled}" />
					<c:set var="bRequiresHighResApproval" value="${bNeedsApprovalForHighResUsage && !downloadForm.userHasApprovalForHighRes}" />
					<c:set var="bHasHighResApproval" value="${bNeedsApprovalForHighResUsage && downloadForm.userHasApprovalForHighRes}" />
						
					<c:set var="downloadingOriginal" value="${ downloadForm.downloadOriginalCount > 0 && ( !simpleConvertOptionsForDownload || advanced || downloadForm.selectedUsageType.usageType.downloadOriginal)}"/>

					<c:if test="${downloadingOriginal || numUsageTypeFormats gt 0}">
						<%@include file="inc_tscs_selection.jsp"%>
						
						<%--  Don't show options if the user needs high res approval --%>
						<c:if test="${!bRequiresHighResApproval}">
						 
							<c:if test="${downloadForm.canDownloadAsPPT}">
								<c:set var="helpText" scope="request"><bright:cmsWrite identifier="tooltip-download-as-ppt" /></c:set>
								<label class="wrapping">
									<html:checkbox name="downloadForm" property="downloadAsPowerPoint" styleId="downloadPPT"/> <bright:cmsWrite identifier="label-download-as-ppt" /><jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
								</label>
							</c:if>
		
							<c:if test="${userprofile.isLoggedIn && imagesAreEmailable && userprofile.userCanEmailAssets}">
								<label class="wrapping">
								<html:checkbox name="downloadForm" property="email"  styleId="sendemail"/> <bright:cmsWrite identifier="label-email-file" filter="false"/></label>
							</c:if>	
							
							<c:if test="${watermarkOptionOnDownload && !advanced}">
								<label class="wrapping">
								<html:checkbox name="downloadForm" property="watermarkImageOption" styleId="watermarkImage" style="display: inline; margin-bottom: 0px;"/> <bright:cmsWrite identifier="label-watermark-email-asset" filter="false"/></label> 
							</c:if>
							
						</c:if>

					</c:if>

					<%-- Check for case of requesting approval for high-res usage --%>
					<c:choose>
						<c:when test="${bRequiresHighResApproval}">
							<html:submit styleClass="button flush" value="Request approval" property="b_requestApproval" styleId="request_button"/>
						</c:when>
						
						<c:otherwise>
										
							<%-- Download original option --%>							
						   <%-- Show this if user has permission, and if advanced/not simple or usage type is a download type	--%>				
							<c:if test="${downloadingOriginal}">																		
								<c:if test="${downloadForm.excludedFromDownloadOriginalCount > 0}">
									<bean:define id="count" name="downloadForm" property="excludedFromDownloadOriginalCount"/>
									<br/><div class="warning"><bright:cmsWrite identifier="assetsExcludedFromDownload" filter="false" replaceVariables="true"/></div>
									<html:hidden name="downloadForm" property="excludedIds"/>
								</c:if>
								<div class="dialogPanel">
									<h3><bright:cmsWrite identifier="subhead-download-originals" filter="false"/></h3>
									<table class="form" cellspacing="0" cellpadding="0">
										<tr>
											<th>
												<bright:cmsWrite identifier="label-file-formats" filter="false"/> 
											</th>
											<td class="padded">
												<bean:write name="downloadForm" property="fileFormats"/> 
											</td>
										</tr>
										<tr>
											<th>
												<bright:cmsWrite identifier="label-total-size" filter="false"/> 
											</th>
											<td class="padded">
												<c:set var="fileSize" value="${downloadForm.totalFileSize/(1024*1024)}"/>
												<logic:greaterEqual name="fileSize" value="1">
													<bean:write name="fileSize" format="0.00"/><bright:cmsWrite identifier="snippet-mb" filter="false"/> (<bright:cmsWrite identifier="snippet-uncompressed" filter="false"/>)
												</logic:greaterEqual>
												<logic:lessThan name="fileSize" value="1">
													<c:set var="fileSize" value="${fileSize*1024}"/>
													<bean:write name="fileSize" format="0.00"/><bright:cmsWrite identifier="snippet-kb" filter="false"/> (<bright:cmsWrite identifier="snippet-uncompressed" filter="false"/>)
												</logic:lessThan>
											</td>
										</tr>
										<tr>
											<th>&nbsp;
											</th>
											<td class="padded">
												<c:choose>
													<c:when test="${lightboxMaxDownloadSize>0 && downloadForm.totalFileSize>lightboxMaxDownloadSize && !userprofile.isAdmin}">		
														<bright:cmsWrite identifier="snippet-lightbox-too-large" filter="false"/>
														<c:if test="${showRequestOnCd}">
															<bright:cmsWrite identifier="snippet-lightbox-too-large-request-cd" filter="false"/>
														</c:if>
													</c:when>
													<c:otherwise>
														<html:submit styleClass="button flush" property="b_downloadOriginal" styleId="download_originals_button"><bright:cmsWrite identifier="button-download-originals" filter="false" /></html:submit>
													</c:otherwise>
												</c:choose>
											</td>
										</tr>
									</table>
								</div>
							</c:if>
	
						   <%-- In the case of simple convert and simple tab and download original usage type and can download original then do not show the convert --%>
						    <c:if test="${ (!simpleConvertOptionsForDownload || advanced || downloadForm.hasUsageTypeFormats || downloadForm.downloadOriginalCount <= 0) }">
							<%-- c:if test="${ !(simpleConvertOptionsForDownload && !advanced && downloadForm.selectedUsageType.usageType.downloadOriginal && downloadForm.downloadOriginalCount > 0) }" --%>
								<c:if test="${downloadForm.containsImages || !advanced}"> 
								<div class="dialogPanel">	
									<c:choose>
										<c:when test="${numUsageTypeFormats == 0}">
											<bright:cmsWrite identifier="noPermissionUsageType" filter="false"/>
										</c:when>
										<c:otherwise>		
											<h3><c:choose>
													<c:when test="${downloadForm.containsImages}">
														<bright:cmsWrite identifier="subhead-download-convert-images" filter="false"/>
													</c:when>
													<c:otherwise>
														<bright:cmsWrite identifier="heading-download" filter="false" />
													</c:otherwise>
												</c:choose>
											</h3>			
												<table class="form" cellspacing="0" cellpadding="4">
											<c:if test="${downloadForm.containsImages}"> 
											<c:choose>
												<c:when test="${advanced || !simpleConvertOptionsForDownload}">
													<%-- The user can specify format/height/width etc --%>
													<tr>
														<th>
															<label for="format"><bright:cmsWrite identifier="label-file-format" filter="false"/> </label> 
														</th>
														<td>
															<bright:refDataList componentName="ImageAssetManagerImpl" methodName="getDownloadableImageFormats" id="downloadFormats"/>
															<html:select name="downloadForm" property="imageFormat" size="1" styleId="format">
																<html:options collection="downloadFormats" property="fileExtension" labelProperty="description"/>
															</html:select>
														</td>
													</tr>		
													<tr>
														<th>
															<label for="maxwidth"><bright:cmsWrite identifier="label-max-width" filter="false"/></label> 
														</th>
														<td>
															<logic:equal name="downloadForm" property="width" value="0">
																<html:text name="downloadForm" property="width" size="8" styleClass="small text" value="" styleId="maxwidth" /> 
															</logic:equal>
															<logic:notEqual name="downloadForm" property="width" value="0">
																<html:text name="downloadForm" property="width" size="8" styleClass="small text" styleId="maxwidth" /> 
															</logic:notEqual>
															(<bright:cmsWrite identifier="pixels" filter="false"/>)
														</td>
													</tr>
													<tr>
														<th>
															<label for="maxheight"><bright:cmsWrite identifier="label-max-height" filter="false"/></label>
														</th>
														<td>
															<logic:equal name="downloadForm" property="height" value="0">
																<html:text name="downloadForm" property="height" size="8" styleClass="small text" value="" styleId="maxheight" />
															</logic:equal>
															<logic:notEqual name="downloadForm" property="height" value="0">
																<html:text name="downloadForm" property="height" size="8" styleClass="small text" styleId="maxheight" /> 
															</logic:notEqual>
															(<bright:cmsWrite identifier="pixels" filter="false"/>)
														</td>
													</tr>	
													
													<bean:size id="numColorSpaces" name="downloadForm" property="colorSpaces"/>
	
													<%-- If the colorspace dropdown isn't enabled show the convert to rgb if necessary --%>
													<c:if test="${numColorSpaces==1}"> 
														<logic:equal name="downloadForm" property="notAllRGB" value="true">
														<tr>
															<th>
																<label for="convertRGB"><bright:cmsWrite identifier="label-convert-rgb" filter="false"/></label>
															</th> 
															<td class="checkbox">
																<html:checkbox name="downloadForm" property="convertToRGB" styleClass="checkbox" styleId="convertRGB"/>
															</td>
														</tr>	
														</logic:equal>
													</c:if>
													
	
													<%-- Show the colorspace dropdown --%>
													<c:if test="${numColorSpaces>1}"> 
														<tr>
															<th>													
																<label class="narrow"><bright:cmsWrite identifier="label-color-space" filter="false"/></label>
															</th>
															<td>
															<bean:define id="colorSpaces" name="downloadForm" property="colorSpaces"/>												
															<html:select name="downloadForm" property="selectedColorSpaceId" size="1" style="width:auto">
																<html:option value="0"><bright:cmsWrite identifier="snippet-no-conversion"/></html:option>
																<html:options collection="colorSpaces" labelProperty="description" property="id" filter="false"/>
															</html:select>
															</td>
													</c:if>
																			
												</c:when>
								  
												<c:otherwise>
													<%-- The user can just pick from a dropdown of predefined sizes --%>
													<html:hidden name="downloadForm" property="imageFormat" value="jpeg"/>
													
													<tr>
														<th style="width:40px;">
															<label for="size"><bright:cmsWrite identifier="label-size" filter="false"/></label>
														</th>
														<bean:size id="numSizes" name="downloadForm" property="usageTypeFormats"/>
														<td <c:if test="${numSizes==1}">class="padded"</c:if>>
															<c:choose>
																<c:when test="${numSizes==1}">
																	<logic:iterate name="downloadForm" property="usageTypeFormats" id="format">
																		<bean:write name="format" property="description"/>
																		<input type="hidden" name="usageTypeFormatId" value="<bean:write name='format' property='id'/>"/>
																	</logic:iterate>																
																</c:when>
																<c:otherwise>
																	<html:select name="downloadForm" property="usageTypeFormatId" size="1" style="width:auto;" onchange="document.getElementById('submitUsageTypeFormat').click();">
																		<logic:iterate name="downloadForm" property="usageTypeFormats" id="format">
																			<option value="<bean:write name='format' property='id'/>" <c:if test="${format.id == downloadForm.usageTypeFormatId}">selected</c:if>><bean:write name="format" property="description"/></option>
																		</logic:iterate>
																	</html:select>	
																	
																	<input type="submit" name="b_usageTypeFormat" id="submitUsageTypeFormat" class="button js-enabled-hide" value="<bright:cmsWrite identifier="button-go-arrow" filter="false" />" style="display: inline;" />
																															
																</c:otherwise>
															</c:choose>		
														</td>
													</tr>	
													
													<bean:size id="numColorSpaces" name="downloadForm" property="colorSpaces"/>
													
													<%-- If the colorspace dropdown isn't enabled show the convert to rgb if necessary --%>
													<c:if test="${numColorSpaces==1 && downloadForm.notAllRGB && downloadForm.selectedUsageTypeFormat.colorSpace==0}">
														<tr>
															<th>
																<label for="convertRGB"><bright:cmsWrite identifier="label-convert-rgb" filter="false"/></label>
															</th> 
															<td class="checkbox">
																<html:checkbox name="downloadForm" property="convertToRGB" styleClass="checkbox" styleId="convertRGB"/>
															</td>
														</tr>	
													</c:if>
	
	
													<%-- Derermine whether to show the colorspace dropdown --%>
													<c:if test="${downloadForm.selectedUsageTypeFormat.colorSpace==0 && numColorSpaces>1}">	
														<tr>
															<th>
																<label class="narrow"><bright:cmsWrite identifier="label-color-space" filter="false"/></label>
															</th>
															<td>
										
																<bean:define id="colorSpaces" name="downloadForm" property="colorSpaces"/>												
																<html:select name="downloadForm" property="selectedColorSpaceId" size="1" style="width:auto">
																	<html:option value="0"><bright:cmsWrite identifier="snippet-no-conversion"/></html:option>
																	<html:options collection="colorSpaces" labelProperty="description" property="id" filter="false"/>
																</html:select>
															</td>
													</c:if>
													
													<%-- Force a colorspace if one selected for the usage type size --%>
													<c:if test="${downloadForm.selectedUsageTypeFormat.colorSpace!=0}">
															<input type="hidden" name="selectedColorSpaceId" value="<c:out value='${downloadForm.selectedUsageTypeFormat.colorSpace}'/>"/>	
													</c:if>
													
												</c:otherwise>
											</c:choose>
											</c:if>
												<tr>
													<td style="width:40px;">&nbsp;
													</td>
													<td>
														<html:submit styleClass="button flush" property="b_download" styleId="submit_button"><bright:cmsWrite identifier="button-download-now" filter="false" /></html:submit> 
													</td>
												</tr>
											</table>
										</c:otherwise>
									</c:choose>
								</div>   <!-- End of dialogue panel -->
								</c:if>
							</c:if>
							
						</c:otherwise>
					</c:choose>
						
				</c:if> <!-- End if ${usageIsSelected} -->
					
        		<logic:equal name="showTabs" value="true">
					</div>	<!-- End of tab-content -->
				</logic:equal>
				
				
				
        	</form>	
         <br />	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>
