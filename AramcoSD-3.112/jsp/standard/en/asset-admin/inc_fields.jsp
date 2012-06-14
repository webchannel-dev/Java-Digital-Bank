<%@ page import="com.bright.assetbank.attribute.constant.AttributeConstants" %>
<%--
	Displays the form field for an attribute, according to the attribute type

	History:
	 d1		Martin Wilson		24-Oct-2005		Created.
	 d2		Matt Stevenson		05-Dec-2005		Added maxlength validation values
	 d3      Steve Bryan       20-Dec-2005		Factored out category fields into separate include
	 d4		Ben Browning		16-Feb-2006		Tidied up html
	 d5      Steve Bryan       18-Jul-2006		Changed id of keywords field to avoid clash with META tags
	 d6     Martin Wilson      26-Jul-2006		Added rotate dropdown
	 d7     Matt Woollard      12-Aug-2009      Moved image size calculations to an include
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://www.assetbank.co.uk/taglib/abplugin" prefix="abplugin" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<input type="hidden" name="maxlength_asset.description" value="4000">
<input type="hidden" name="maxlengthmessage_asset.description" value="Asset description has exceeded its maximum length of 4000 characters"/>

<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />
<bright:applicationSetting id="numPromoted" settingName="num-promoted-images-homepage"/>
<bright:applicationSetting id="iFeaturedImageWidth" settingName="featured-image-width"/>

<bright:applicationSetting id="usePriceBands" settingName="price-bands" />
<bright:applicationSetting id="canRestrictAssetPreview" settingName="can-restrict-assets"/>
<bright:applicationSetting id="showSensitivityFields" settingName="show-sensitivity-fields"/>
<bright:applicationSetting id="hideIncompleteAssets" settingName="hide-incomplete-assets"/>
<bright:applicationSetting id="allowSearchByCompleteness" settingName="allow-search-by-completeness"/>
<bright:applicationSetting id="enableAgreements" settingName="agreements-enabled"/>	
<bright:applicationSetting id="allowImageSubstitutes" settingName="allow-image-substitutes"/>
<bright:applicationSetting id="useBrands" settingName="multiple-brands"/>
<bright:applicationSetting id="advancedViewingEnabled" settingName="advanced-viewing-enabled"/>
<bright:applicationSetting id="useAgreements" settingName="agreements-enabled"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="batchReleasesEnabled" settingName="batch-releases-enabled"/>
<bright:applicationSetting id="showRotate" settingName="show-rotate"/>
<bright:applicationSetting id="showUnrelateAssets" settingName="show-unrelate-assets"/>

<%-- Create a Map containing the constants defined in AttributeConstants --%>
<un:useConstants var="attributeConstants" className="com.bright.assetbank.attribute.constant.AttributeConstants" />

	<%-- Some pages may pass in a sIsImport variable --%>
	<c:if test="${empty bIsImport}">
		<c:set var="bIsImport" value="${!empty sIsImport && sIsImport}" scope="request" />
	</c:if>
	
	<c:set var="bValidateMandatories" value="${!batchAssetForm.approvalMode && !bIsBulkUpdate}" scope="request" />
	
	<c:choose>
		<c:when test="${bValidateMandatories && !bIsImport}">
			<p>
				<bright:cmsWrite identifier="snippet-req-fields" filter="false"/>
				<c:if test="${(hideIncompleteAssets || allowSearchByCompleteness) }"><bright:cmsWrite identifier="snippet-req-for-completeness-fields" filter="false"/></c:if>
			</p>

		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${bIsBulkUpdate}">
					<bright:cmsWrite identifier="snippet-bulk-meta-instruct" filter="false" replaceVariables="true" />	
				</c:when>
				<c:when test="${bIsImport}">
					<bright:cmsWrite identifier="snippet-batch-meta-instruct" filter="false" replaceVariables="true" />		
				</c:when>
				<c:otherwise>
					<%-- approval batch --%>	
				</c:otherwise>
			</c:choose>
		</c:otherwise>
	</c:choose>
	 
	<table class="form" cellspacing="0" cellpadding="0" border="0">
		<html:hidden name="assetForm" property="asset.fileLocation"/>
		<html:hidden name="assetForm" property="asset.originalFileLocation"/>
		<html:hidden name="assetForm" property="asset.fileSizeInBytes"/>
		<html:hidden name="assetForm" property="asset.addedByUser.id"/>
		<html:hidden name="assetForm" property="asset.submittedByUserId"/>
		<html:hidden name="assetForm" property="asset.versionNumber"/>
		<html:hidden name="assetForm" property="asset.currentVersionId"/>
		<html:hidden name="assetForm" property="asset.entity.id"/>
		<html:hidden name="assetForm" property="asset.entity.name"/>
		<html:hidden name="assetForm" property="asset.entity.allowAssetFiles"/>
		<html:hidden name="assetForm" property="asset.originalFilename"/>
		<html:hidden name="assetForm" property="asset.hasSubstituteFile"/>
		<html:hidden name="assetForm" property="asset.currentBatchReleaseState" />
		<html:hidden name="assetForm" property="dateAdded"/>
		<html:hidden name="assetForm" property="dateLastModified"/>
		<html:hidden name="assetForm" property="dateSubmitted"/>
		<html:hidden name="assetForm" property="asset.hasNeverBeenApproved"/>
		<html:hidden name="assetForm" property="asset.allowFeedback"/>		
		
		<c:if test="${assetForm.asset.format.id>0}">
			<html:hidden name="assetForm" property="asset.format.id"/>
			<html:hidden name="assetForm" property="asset.format.assetTypeId"/>
			<html:hidden name="assetForm" property="asset.format.converterClass"/>
		</c:if>
		
		<c:if test="${not empty assetForm.asset.rawPreviewImageFile && assetForm.asset.surrogateAssetId<=0}">
			<html:hidden name="assetForm" property="asset.rawPreviewImageFile.path"/>
			<html:hidden name="assetForm" property="asset.rawThumbnailImageFile.path"/>
			<html:hidden name="assetForm" property="asset.rawHomogenizedImageFile.path"/>
			<html:hidden name="assetForm" property="asset.childPreviewImageFile.path"/>
			<html:hidden name="assetForm" property="asset.childThumbnailImageFile.path"/>
			<html:hidden name="assetForm" property="asset.childHomogenizedImageFile.path"/>
			<html:hidden name="assetForm" property="asset.previewClipLocation"/>
			<html:hidden name="assetForm" property="asset.embeddedPreviewClipLocation"/>
			<c:if test="${assetForm.asset.class.name=='ImageAsset' && not empty assetForm.asset.featuredImageFile.path}">
				<html:hidden name="assetForm" property="asset.featuredImageFile.path"/>
			</c:if>
		</c:if>
		
		<tr>
			<th></th><td></td><td></td>
		</tr>

		<c:if test="${batchReleasesEnabled}">
			<html:hidden name="assetForm" property="asset.currentBatchReleaseReleased" />
			<html:hidden name="assetForm" property="asset.currentBatchReleaseId" />
						
			<c:choose>
				<c:when test="${assetForm.asset.currentBatchReleaseState == 'approved' || assetForm.asset.currentBatchReleaseState == 'awaiting-approval' || assetForm.asset.currentBatchReleaseState == 'further-approval-required'}">
					<%-- just show the name of the currently selected release --%>
					<bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager" methodName="getBatchRelease" id="release" argumentValue="${assetForm.asset.currentBatchReleaseId}"/>
					<tr>
						<th><label for="batchRelease"><bright:cmsWrite identifier="batch-release" filter="false" case="sentence"/>:</label> <span class="required">*</span></th>
						<td></td>
						<td><input type="hidden" name="asset.assignedBatchReleaseId" value="<bean:write name='release' property='id' />" /><bean:write name='release' property='name' /></td>
					</tr>
				</c:when>
				<c:otherwise>
					<bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager" methodName="getBatchReleasesBeingCreated" id="releases"/>
			
					<%-- releases to select from --%>
					<c:if test="${not empty releases}">
						<tr>
							<th><label for="batchRelease"><bright:cmsWrite identifier="batch-release" filter="false" case="sentence"/>:</label> <span class="required">*</span></th>
							<td></td>
							<c:if test="${bIsBulkUpdate}">
								<td>
									<select name="update_batchrelease" id="update_batchrelease" style="width:auto;">
										<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
										<option value="replace" <logic:equal name='assetForm' property="updateType(batchrelease)" value="replace">selected</logic:equal>><bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
									</select>	
								</td>
								<td></td>
							</c:if>
							<td>
								<html:select name="assetForm" property="asset.assignedBatchReleaseId" size="1">
									<option value="none">- <bright:cmsWrite identifier="snippet-please-select" filter="false" /> -</option>
									<logic:iterate name="releases" id="release">
										<option value="<bean:write name='release' property='id' />" <c:if test="${(assetForm.asset.assignedBatchReleaseId > 0 && assetForm.asset.assignedBatchReleaseId == release.id) || (assetForm.asset.assignedBatchReleaseId <= 0 && assetForm.asset.currentBatchReleaseId == release.id)}">selected</c:if>><bean:write name='release' property='name' /></option>
									</logic:iterate>
								</html:select>
							</td>
						</tr>
					</c:if>
					<%-- no batch releases to select from (and not a bulk update) --%>
					<c:if test="${empty releases && !bIsBulkUpdate}">				
						<tr><td colspan="3"><div class="error"><bright:cmsWrite identifier="noBatchReleasesBeingCreated" filter="false" /></div></td></tr>
					</c:if>
				</c:otherwise>
			</c:choose>
		</c:if>

		<%-- Include form start JSPs from extensions --%>
		<abplugin:include-form-extensions extensibleEntity="asset" determineVerbFromId="${assetForm.asset.id}" position="start"/>

		<%-- Checkbox for using the asset as a brand template (admin only) --%>
		<bright:applicationSetting id="brandTemplatesEnabled" settingName="brand-templates-enabled"/>
		<c:if test="${!bIsBulkUpdate && userprofile.isAdmin && brandTemplatesEnabled && assetForm.hasBrandTemplateFileExtension}">
			<tr>
				<th class="checkbox">
					<label for="isBrandTemplate"><bright:cmsWrite identifier="label-is-brand-template" filter="false"/></label>
				</th>
				<td></td>
				<td>
					<html:checkbox name="assetForm" property="asset.isBrandTemplate" styleClass="checkbox" styleId="isBrandTemplate" />
				</td>
			</tr>
		</c:if>

		<c:if test="${!bIsImport}">
			<c:set var="fileSize" value="${assetForm.fileSizeInBytes/(1024*1024)}"/>
			<c:if test="${fileSize gt 0}">
				<tr>
					<th>
						<label for="title"><bright:cmsWrite identifier="label-size" filter="false"/></label>  									
					</th>
					<td></td>
					<td>
						<c:if test="${assetForm.asset.typeId==2}">
							<c:set var="height" value="${assetForm.height}" scope="request"/>
							<c:set var="width" value="${assetForm.width}" scope="request"/>
							<jsp:include page="../inc/image_size_calculations.jsp"/>
							<bean:write name="assetForm" property="width"/> x
							<bean:write name="assetForm" property="height"/> <bright:cmsWrite identifier="pixels" filter="false"/>;
						
							<logic:greaterThan name="assetForm" property="numLayers" value="1">
								<bean:write name="assetForm" property="numLayers"/> <bright:cmsWrite identifier="snippet-layers" filter="false"/>;
							</logic:greaterThan>
						</c:if>
						<logic:greaterEqual name="fileSize" value="1">
							<bean:write name="fileSize" format="0.00"/>Mb
						</logic:greaterEqual>
						<logic:lessThan name="fileSize" value="1">
							<c:set var="fileSize" value="${fileSize*1024}"/>
							<bean:write name="fileSize" format="0.00"/>Kb
						</logic:lessThan>
					</td>
				</tr>
			</c:if>
		</c:if>

		<jsp:include page="../inc/inc_mce_editor.jsp"/>

		<%-- Go through all the attributes: --%>
		<logic:iterate name="assetForm" property="assetAttributes" id="attribute">
		<c:if test="${!attribute.hidden}">
			<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
				<logic:notEmpty name="attribute" property="fieldName">
					<%-- Static attributes --%>
					<c:choose>
						<%-- File field --%>
						<c:when test="${attribute.fieldName == 'file'}">
							<c:if test="${(attribute.isVisible || assetForm.asset.id==0)}"><%-- Must show the file field on upload - otherwise, no point! --%>
								<c:if test="${(assetForm.asset.entity.id==0 || assetForm.asset.entity.allowAssetFiles) && !assetForm.emptyAsset}">
									<c:if test="${!bIsImport}">
										<tr>
											<th>
												<bean:write name="attribute" property="label" filter="false"/>:											
												<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
												<%-- File is always mandatory, unless this is an existing asset --%>
												<c:if test="${assetForm.asset.id<=0}">
													<span class="required">*</span>
												</c:if>
												<c:if test="${assetForm.asset.id>0}">
													<br /><span style="font-weight: normal"><bright:cmsWrite identifier="snippet-if-changed" filter="false"/></span>
												</c:if>
											</th>
									   <td></td>
											<td style="padding-top:4px; padding-bottom:8px;">
												<c:if test="${not empty assetForm.tempFileLocation}">
													<%-- The file was uploaded in a previous step --%>
													<html:hidden name="assetForm" property="tempFileLocation"/>
													<bean:write name="assetForm" property="tempFilename"/>
												</c:if>
												<c:if test="${empty assetForm.tempFileLocation}">
													<div id="file">
														<html:file name="assetForm" property="file" styleClass="file" size="35" styleId="file" onchange="fileSelectionChanged(this);"/> (<bright:cmsWrite identifier="current-file"/>: <bean:write name="assetForm" property="asset.originalFilename"/>)
														<abplugin:include-form-extensions extensibleEntity="asset" determineVerbFromId="${assetForm.asset.id}" position="fileDiv"/>
														<div id="alternativeUploader" style="display:none;">
															<c:set var="link"><a href="viewUploader?uploadToolOption=browser" target="uploader" onclick="return openUploader();"><bright:cmsWrite identifier="link-alternative-uploader" filter="false"/></a></c:set>
															<bright:cmsWrite identifier="snippet-upload-large-file" filter="false" replaceVariables="true"/>
														</div>
														<script type="text/javascript"><!--
															document.getElementById('alternativeUploader').style.display='block';
															function fileSelectionChanged(fileCtrl)
															{
																if($('generateThumbnailLabel'))
																{
																	if(fileCtrl.value=='')
																	{
																		$('generateThumbnailLabel').update('<bright:cmsWrite identifier="label-generate-thumbnail" filter="false"/>');
																	}
																	else
																	{
																		$('generateThumbnailLabel').update('<bright:cmsWrite identifier="label-remove-thumbnail" filter="false"/> (<bright:cmsWrite identifier="snippet-if-applicable" filter="false"/>)');
																	}
																}
															}
														//--></script>
													</div>
													<div id="uploadedFile" style="display: none; margin-top: 6px;">
														<input type="hidden" name="uploadedFilename" id="uploadedFilenameField" value=""/>
														<span id="uploadedFilenameText" style="font-weight: bold; margin-right:6px;">none</span>
														<noscript>
															[<a href="viewUpdateAsset?id=<c:out value="${assetForm.asset.id}"/>" onclick="clearUploadedFile();"><bright:cmsWrite identifier="link-cancel-file-update" filter="false"/></a>]
														</noscript>
														<script type="text/javascript"><!--
															document.write('[<a href="javascript:;" onclick="if(confirm(\'<bright:cmsWrite identifier="js-confirm-cancel-file-update" filter="false"/>\')){clearUploadedFile();}"><bright:cmsWrite identifier="link-cancel-file-update" filter="false"/></a>]');
														//--></script>
													</div>	
													<logic:notEmpty name="assetForm" property="uploadedFilename">
														<script type="text/javascript"><!--
															setUploadedFile('<bean:write name="assetForm" property="uploadedFilename"/>');
														//--></script>
													</logic:notEmpty>									
												</c:if>	
											</td>
										</tr>									
										<c:if test="${assetForm.asset.hasSubstituteFile || (allowImageSubstitutes && assetForm.asset.isImage)}">
											<tr>
												<th>
													<bright:cmsWrite identifier="label-working-file" filter="false"/>:
													<c:if test="${assetForm.asset.hasSubstituteFile}">
														<br /><span style="font-weight: normal"><bright:cmsWrite identifier="snippet-if-changed" filter="false"/></span>
													</c:if>
												</th>
												<td></td>
												<td><html:file name="assetForm" property="substituteFile" styleClass="file" size="35"/></td>
											</tr>
											<c:if test="${assetForm.asset.hasSubstituteFile}">
												<tr>
													<th>
														&nbsp;
													</th>
											   <td></td>
													<td style="padding-bottom:10px; padding-top:0px;">
														<html:checkbox name="assetForm" property="removeSubstitute" styleClass="checkbox" styleId="removeSubstitute" /><label for="removeSubstitute"><bright:cmsWrite identifier="label-remove-working-file" filter="false"/></label>
													</td>
												</tr>
											</c:if>
										</c:if>
									</c:if>
									<c:if test="${assetForm.asset.entity.id>0 && !assetForm.asset.entity.allowAssetFiles }">
										<html:hidden name="assetForm" property="tempFileLocation"/>
									</c:if>
									<c:if test="${showRotate && assetForm.asset.typeId == 2 && assetForm.asset.numPages == 1 && (assetForm.asset.entity.id==0 || assetForm.asset.entity.allowAssetFiles)}"> <%-- Images --%>					
										<tr>
											<th><bright:cmsWrite identifier="label-rotate" filter="false"/></th>
									   <td></td>
											<td>
												<html:select name="assetForm" property="rotationAngle">
													<jsp:include page="../customisation/rotate_image_options.jsp"/>
												</html:select>
												<c:if test="${assetForm.asset.autoRotate>0}">
													<c:set var="autoRotateAngle" value="${assetForm.asset.autoRotate}"/>
													&nbsp;<bright:cmsWrite identifier="label-autorotated" filter="false" replaceVariables="true"/> 
													<html:hidden name="assetForm" property="asset.autoRotate"/>
												</c:if>
											</td>
										</tr>
									</c:if>
								</c:if> <%-- Thumbnail can now be changed on bulk update --%>
									<c:if test="${!uploading && (assetForm.asset.typeId != 2 || (assetForm.asset.entity.id>0 && !assetForm.asset.entity.allowAssetFiles))}"> <%-- Non images can have a representative thumbnail image --%>		
										<tr>
											<th>
												<bright:cmsWrite identifier="label-thumbnail" filter="false"/> 
												<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
												<c:if test="${!bIsBulkUpdate}">
													<logic:empty name="assetForm" property="asset.rawPreviewImageFile.path">
														<br /><span style="font-weight: normal">(if required)</span>
													</logic:empty>
												</c:if>
												<logic:notEmpty name="assetForm" property="asset.rawPreviewImageFile.path">
													<br /><span style="font-weight: normal"><bright:cmsWrite identifier="snippet-if-changed" filter="false"/></span>
												</logic:notEmpty>
											</th>
											<td></td>
											<c:if test="${bIsBulkUpdate}">
												<td>
													<select name="update_substituteFile" id="update_substituteFile" style="width:auto;">
														<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
														<option value="replace" <logic:equal name='assetForm' property="updateType(substituteFile)" value="replace">selected</logic:equal>><bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
													</select>
												</td>
												<td></td>
											</c:if>
											<td>
												<html:file name="assetForm" property="substituteFile" styleClass="file" size="35"/>
												<span class="inline">&nbsp;<bright:cmsWrite identifier="snippet-thumbnail-bulk-update"/></span>
											</td>
										</tr>
									<c:if test="${!bIsImport}">	
										<c:if test="${not empty assetForm.previewImageFile.path}">
											<tr>
												<th>
													&nbsp;
												</th>
												<td></td>
												<td style="padding-bottom:10px; padding-top:0;">
													<html:checkbox name="assetForm" property="removeSubstitute" styleClass="checkbox" styleId="removeSubstitute" /><label for="removeSubstitute"><bright:cmsWrite identifier="label-remove-thumbnail" filter="false"/></label>
												</td>
											</tr>
										</c:if>
										<c:if test="${empty assetForm.previewImageFile.path && assetForm.asset.typeId==1 && not empty assetForm.asset.format.converterClass}">
											<tr>
												<th>
													&nbsp;
												</th>
												<td></td>
												<td style="padding-bottom:10px; padding-top:0;">
													<html:checkbox name="assetForm" property="removeSubstitute" styleClass="checkbox" styleId="removeSubstitute" /><label for="removeSubstitute" id="generateThumbnailLabel"><bright:cmsWrite identifier="label-generate-thumbnail" filter="false"/></label>
												</td>
											</tr>
										</c:if>
									</c:if>
								</c:if>
							</c:if>
						</c:when>					
					
						<c:when test="${useAgreements && attribute.fieldName == 'agreements'}">
							<c:choose>
								<c:when test="${attribute.isVisible}">
									<tr>
										<th>
											<bright:cmsWrite identifier="label-agreement-type" filter="false"/>
											<c:if test="${attribute.mandatory && bValidateMandatories}">
												<span class="required">*</span>
												<input type="hidden" name="mandatory_asset.agreementTypeId" value="<bright:cmsWrite identifier="failedValidationAgreementTypeRequired" filter="false"/>">
											</c:if>
										</th>
										<td></td>
										<c:if test="${bIsBulkUpdate}">
											<td>
												<select name="update_agreements" id="update_agreements" style="width:auto;">
													<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
													<option value="replace" <logic:equal name='assetForm' property="updateType(agreements)" value="replace">selected</logic:equal>><bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
												</select>	
											</td>
											<td></td>
										</c:if>
										<td style="padding-bottom: 5px;">
											<select name="asset.agreementTypeId" onchange="showHideAgreementType();" id="agreementTypeSelect">
												<option value="">
													- <bright:cmsWrite identifier="snippet-please-select" filter="false"/> -
												</option>
												<option value="1" <c:if test='${assetForm.asset.agreementTypeId == 1}'>selected="selected"</c:if>>
													<bright:cmsWrite identifier="snippet-unrestricted" filter="false"/>
												</option>
												<option value="2" <c:if test='${assetForm.asset.agreementTypeId == 2}'>selected="selected"</c:if>>
													<bright:cmsWrite identifier="snippet-agreement-applies" filter="false"/>
												</option>
												<option value="3" <c:if test='${assetForm.asset.agreementTypeId == 3}'>selected="selected"</c:if>>
													<bright:cmsWrite identifier="snippet-restricted" filter="false"/>
												</option>
											</select>
											<br/>
											
											<div id="agreementDropdown" style="display:none; margin-top:6px;">
												<html:select name="assetForm" property="asset.agreement.id" styleId="agreementSelect" onchange="syncAgreementPreviewButton(); if (this.value > 0) { document.getElementById('editAgreementLink').style.display = 'block'; } else { document.getElementById('editAgreementLink').style.display = 'none'; }">
													<option value="-1">
														- <bright:cmsWrite identifier="snippet-please-select" filter="false"/> -
													</option>
													<logic:iterate name="assetForm" property="agreements" id="agreement">
														<option value="<bean:write name='agreement' property='id'/>" <c:if test='${assetForm.asset.agreement.id == agreement.id}'>selected="selected"</c:if>>
															<bean:write name='agreement' property='title'/>
														</option>
													</logic:iterate>
												</html:select>
																		
												<script type="text/javascript">
													<!-- 
														document.write('<input type="button" class="button" id="previewButton" value="<bright:cmsWrite identifier="button-preview-agreement" filter="false"/>" onclick="popupViewAgreement((document.getElementsByName(\'asset.agreement.id\')[0]).value); return false;" onkeypress="((document.getElementsByName(\'asset.agreementId\')[0]).value); return false;" />');
														document.write('<br/>');
														document.write('<a href="../action/addAgreementPopup" target="_blank" onclick="popupAddAgreement(); return false;" /><bright:cmsWrite identifier="link-add-agreement" filter="false"/></a>');
														document.write('<br/>');
														document.write('<span id="editAgreementLink" <c:if test="${assetForm.asset.id <= 0}">style="display:none;"</c:if>><a href="#" onclick="popupEditAgreement(document.getElementById(\'agreementSelect\').options[document.getElementById(\'agreementSelect\').selectedIndex].value); return false;" /><bright:cmsWrite identifier="link-edit-agreement" filter="false"/></a></span>');
													-->
												</script>
											</div>
										</td>
									</c:when>
								<c:otherwise>
									<html:hidden name="assetForm" property="asset.agreementTypeId"/> 
									<html:hidden name="assetForm" property="asset.agreement.id"/> 
								</c:otherwise>
							</c:choose>		
						</c:when>
					

						<%-- Price. Only show if ecommerce is turned on: --%>
				   <%-- If we are using price bands, then options are free or to use price bands --%>
						<c:when test="${ecommerce && attribute.fieldName == 'price'}">
							<c:choose>
							<c:when test="${attribute.isVisible}">

								<tr>
									<th>
										<bean:write name="attribute" property="label" filter="false"/>
										(<bean:write name="assetForm" property="asset.price.currencySymbol" filter="false" />):
										<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
										<c:if test="${attribute.mandatory && bValidateMandatories}">
											<span class="required">*</span>
											<input type="hidden" name="mandatory_asset.price.formAmount" value="Please enter a value for the field: <bean:write name='attribute' property='label'/>.">
										</c:if>
									</th>
									<td></td>
									<%@include file="inc_attribute_bulk_update_mode.jsp"%>
									<c:choose>
										<c:when test="${usePriceBands}">
											<td style="padding-bottom: 8px;">
												<select name="asset.price.formAmount">
													<option value="0" <c:if test='${assetForm.asset.price.amount ge 0}'>selected="selected"</c:if> >
														Use price bands
													</option>
													<option value="-1" <c:if test='${assetForm.asset.price.amount lt 0}'>selected="selected"</c:if> >
														Free
													</option>
												</select>
											</td>
										</c:when>
										<c:otherwise>
											<td style="padding-bottom: 8px;">
												<html:text name="assetForm" property="asset.price.formAmount" styleClass="small text" size="20" maxlength="10" /> 
											</td>
										</c:otherwise>
									</c:choose>
								</tr>
							</c:when>
							<c:otherwise>
								<html:hidden name="assetForm" property="asset.price.formAmount"/> 
							</c:otherwise>
							</c:choose>					
						</c:when>
						
						<c:when test="${attribute.fieldName == 'categories'}">
							
							<c:set var="categoriesMandatory" value="${attribute.mandatory}" scope="request" />
							<c:set var="categoriesMandatoryBulk" value="${attribute.mandatoryBulkUpload}" scope="request" />
						
						</c:when>
						
					</c:choose>
				</logic:notEmpty>			
												
				<%-- User-defined attributes --%>
				<c:if test="${empty attribute.fieldName && attribute.id != attributeConstants.k_lAttributeId_SensitivityNotes}">
					<c:if test="${attribute.isVisible}">
						
						<c:if test="${importForm.template.id > 0}">
							<bean:define id="attributeId" name="attribute" property="id"/>
							<bean:define id="attributeValue" name="importForm" property="<%= \"template.attributeValueForAttribute(\" + attributeId + \")\" %>"/>
							<bean:define id="setAttributeValue" value="false"/>
							<bean:define id="editingFilter" value="true"/>
						</c:if>	
						
						<c:if test="${assetForm.template.id > 0}">
							<bean:define id="attributeId" name="attribute" property="id"/>
							<bean:define id="attributeValue" name="assetForm" property="<%= \"template.attributeValueForAttribute(\" + attributeId + \")\" %>"/>
							<bean:define id="setAttributeValue" value="false"/>
							<bean:define id="editingFilter" value="true"/>
						</c:if>						
						
						<%@include file="inc_attribute_field.jsp"%>
					</c:if>
					<c:if test="${!attribute.isVisible}">
						<%-- If the attribute is not visible, set the current value or default if currently has no value (defaults set in AttributeManager.populateMetadataDefaults) --%>
						<c:choose>
							<%-- Check list --%>
							<c:when test="${attribute.typeId==5}">
								<logic:iterate name="attribute" property="listOptionValues" id="optionValue">
									<c:if test="${optionValue.isSelected}">
										<input type="hidden"  name="attribute_<bean:write name='attribute' property='id'/>" value="<bean:write name='optionValue' property='id'/>">								
									</c:if>														
								</logic:iterate>
							</c:when>
							<%-- Dropdown list --%>
							<c:when test="${attribute.typeId==4}">
								<input type="hidden" name="attribute_<bean:write name='attribute' property='id'/>" value="<bean:write name='attribute' property='value.id'/>">
							</c:when>
							<%-- Autoincrement: if not relevant then omit. If relevant but not visible, then show default (which should be next value), or -1 if this is a bulk import --%>
							<c:when test="${attribute.isAutoincrement}">
								<c:choose>
									<c:when test="${!attribute.isRelevant}">
										<%-- Omit attributes that are not relevant --%>
									</c:when>
									<c:otherwise>
										<%-- Relevant but not visible --%>
										<c:choose>
											<c:when test="${bIsImport}">
												<%-- Set to -1 so it gets incremented --%>
												<input type="hidden" name="attribute_<bean:write name='attribute' property='id'/>" value="-1"/>
											</c:when>
											<c:otherwise>
												<%-- Set to the value, which is current value, or default (which should have been set as next autoinc value) --%>
												<input type="hidden" name="attribute_<bean:write name='attribute' property='id'/>" value="<bean:write name='attribute' property='value.value' filter="false"/>">
											</c:otherwise>
										</c:choose>											
									</c:otherwise>
								</c:choose>					
							</c:when>
							
							<%--  Default: Encrypt, so users can't see value in page source, and flag as such so we know to decrypt it in  --%>
							<c:otherwise>
								<input type="hidden" name="attribute_<bean:write name='attribute' property='id'/>_encrypted" value="<bean:write name='attribute' property='value.valueEncrypted' filter="false"/>">
							</c:otherwise>
						</c:choose>
					</c:if>			
				</c:if>
			</c:if>							
		</logic:iterate>
		
		<%-- Checkbox/textarea for asset sensitivity --%>
		<c:if test="${showSensitivityFields}">
		<bean:define id="attribute" name="assetForm" property="staticAttribute(sensitive)"/>
		<c:if test="${attribute.isVisible}">
			<tr>
				<th class="checkbox">
					<label for="sensitive"><bright:cmsWrite identifier="label-sensitive" filter="false"/></label>
				</th>
	        <td></td> 
				<c:if test="${bIsBulkUpdate}">
					<td>
						<select name="update_sensitive" id="update_sensitive" style="width:auto;" onclick="setSensitivityNotes();">
							<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
							<option value="replace" <logic:equal name='assetForm' property="updateType(sensitive)" value="replace">selected</logic:equal>><bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
						</select>
					</td>
					<td></td>
				</c:if>
				<td>
					<script type="text/javascript">
						function showHideSensitivityNotes(ctrl)
						{
							if(ctrl.checked)
							{
								document.getElementById('sensitivityNotes').style.display='block';
								document.getElementById('sensitivityNotesComment').style.display='inline';
							}
							else
							{
								document.getElementById('sensitivityNotes').style.display='none';
								document.getElementById('sensitivityNotesComment').style.display='none';
							}
							setSensitivityNotes();
						}
						
						function setSensitivityNotes()
						{
							if(document.getElementById('updateSensitivityNotes') && document.getElementById('update_sensitive'))
							{
								document.getElementById('updateSensitivityNotes').value = document.getElementById('update_sensitive').value;
							}
						}
					</script>
					<c:choose>
						<c:when test="${assetForm.asset.id<=0 && !assetForm.hasErrors}">
							<input type="checkbox" name="asset.isSensitive" class="checkbox" id="sensitive" onclick="showHideSensitivityNotes(this)"/>
						</c:when>
						<c:otherwise>
							<html:checkbox name="assetForm" property="asset.isSensitive" styleClass="checkbox" styleId="sensitive" onclick="showHideSensitivityNotes(this);"/> 
						</c:otherwise>
					</c:choose>
					<span id="sensitivityNotesComment"><bright:cmsWrite identifier="snippet-provide-sensitivity-notes" filter="false"/><span class="required">*</span></span>
					<div id="sensitivityNotes" style="margin-top:8px;">
						<% String sensitivityNotesProperty = "attributeById(" + AttributeConstants.k_lAttributeId_SensitivityNotes + ")"; %>
						<bean:define name="assetForm" property="<%= sensitivityNotesProperty %>" id="sensitivityNotes"/>
						<input type="hidden" id="updateSensitivityNotes" name="update_<bean:write name="sensitivityNotes" property="id"/>" value="skip"/>
						<c:if test="${not empty sensitivityNotes.value.translations}">
							<bright:cmsWrite identifier="term-for-english" filter="false"/>:<br/>
						</c:if>
						<input type="hidden" name="maxlength_attribute_<bean:write name='sensitivityNotes' property='id'/>" value="<bean:write name='sensitivityNotes' property='maxSize'/>">
						<input type="hidden" name="maxlengthmessage_attribute_<bean:write name='sensitivityNotes' property='id'/>" value="<bean:write name='sensitivityNotes' property='label'/> has exceeded its maximum length of 2000 characters"/>
						<textarea name="attribute_<bean:write name="sensitivityNotes" property="id"/>" cols="60" rows="4"><bean:write name="sensitivityNotes" property="value.value" filter="false"/></textarea>
						
						<c:if test="${not empty sensitivityNotes.value.translations}">
							<bean:define name="sensitivityNotes" property="value" id="attributeValue"/>
							<bean:define name="sensitivityNotes" id="attribute"/>
							<logic:iterate name="attributeValue" property="translations" id="translation" indexId="tIndex">
								<br/>
								<input type="hidden" name="maxlength_attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" value="2000">
								<input type="hidden" name="maxlengthmessage_attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" value="<bean:write name='attribute' property='label'/> (<bean:write name="translation" property="language.name"/>) has exceeded its maximum length of 2000 characters"/>
								<div style="margin-top: 5px;">
									<c:if test="${!showNativeLanguages || empty translation.language.nativeName}">
										<bean:write name="translation" property="language.name"/>:
									</c:if>
									<c:if test="${showNativeLanguages && not empty translation.language.nativeName}">
										<bean:write name="translation" property="language.nativeName" filter="false"/>:
									</c:if>
								</div>	
								<textarea name="attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" cols="40" rows="4" id="field<bean:write name='attribute' property='id'/>__<bean:write name="translation" property="language.id"/>"><bean:write name='translation' property='value' filter='false'/></textarea>
							</logic:iterate>
						</c:if>

					</div>
					<script type="text/javascript">
						showHideSensitivityNotes(document.getElementById('sensitive'));
					</script>
				</td>	
			</tr>
		</c:if>
		</c:if>
		
		<%-- Checkbox for add images to promoted set (admin only) --%>
		<c:if test="${ userprofile.isAdmin && numPromoted gt 0 }">
		<tr>
			<th class="checkbox">
				<label for="promoted"><bright:cmsWrite identifier="label-promoted-item" filter="false"/></label>
			</th>
			<td></td>
         <c:if test="${bIsBulkUpdate}"><td>
				<select name="update_promoted" id="update_promoted" style="width:auto;">
					<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
					<option value="replace" <logic:equal name='assetForm' property="updateType(promoted)" value="replace">selected</logic:equal>><bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
				</select>
			</td>
			<td></td>
		</c:if>
			<td>
				<%-- If it is a new asset and no validation error then default promoted to false --%>
				<c:choose>
					<c:when test="${assetForm.asset.id<=0 && !assetForm.hasErrors}">
						<input type="checkbox" name="asset.isPromoted" class="checkbox" id="promoted" />
					</c:when>
					<c:otherwise>
						<html:checkbox name="assetForm" property="asset.isPromoted" styleClass="checkbox" styleId="promoted" /> 
					</c:otherwise>
				</c:choose>
			</td>					
		</tr>
		</c:if>

		<%-- Checkbox for add images to featured set (admin only) --%>
		<c:if test="${ userprofile.isAdmin && assetForm.asset.typeId==2 && assetForm.asset.surrogateAssetId<=0 && iFeaturedImageWidth gt 0}">
		<tr>
			<th class="checkbox">
				<label for="featured"><bright:cmsWrite identifier="label-featured-image" filter="false"/> </label>
			</th>
			<td></td>
         <c:if test="${bIsBulkUpdate}"><td>
			
				<input type="checkbox" name="update_featured" class="checkbox" />
			
				<select name="update_featured" id="update_featured" style="width:auto;">
					<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
					<option value="replace" <logic:equal name='assetForm' property="updateType(featured)" value="replace">selected</logic:equal>><bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
				</select>
			</td>
			<td></td>
		</c:if>
			<td>
				<c:choose>
					<c:when test="${useBrands}">

						<%-- Show the list of brands for selection --%>
						<logic:notEmpty name="assetForm" property="brandList">
							<logic:iterate name="assetForm" property="brandList" id="brand">
								<bean:define id="brandId" name="brand" property="id" />
								<html:multibox name="assetForm" property="brandSelectedList" styleClass="checkbox" styleId="<%=\"brand_select_\"+brandId%>">
									<bean:write name="brand" property="id" />
								</html:multibox>
								
								<label for="brand_select_<bean:write name="brand" property="id" />"><bean:write name="brand" property="name" /></label><br />
	
							</logic:iterate>							
						</logic:notEmpty>
												
					</c:when>
					<c:otherwise>

						<%-- Show the isFeatured checkbox --%>
						<%-- If it is a new asset and no validation error then default to false --%>
						<c:choose>
							<c:when test="${assetForm.asset.id<=0 && !assetForm.hasErrors}">
								<input type="checkbox" name="asset.isFeatured" class="checkbox" id="featured" />
							</c:when>
							<c:otherwise>
								<html:checkbox name="assetForm" property="asset.isFeatured" styleClass="checkbox" styleId="featured" /> 
							</c:otherwise>
						</c:choose>
												
					</c:otherwise>
				</c:choose>
			</td>					
		</tr>
		</c:if>
										
		<%-- Checkbox for restricting asset preview (admin only) --%>
		<bright:applicationSetting id="nonAdminCanRestrict" settingName="non-admin-can-restrict"/>
		<c:if test="${ (userprofile.isAdmin || nonAdminCanRestrict) && canRestrictAssetPreview && !enableAgreements && (assetForm.asset.entity.id<=0 || assetForm.asset.entity.allowAssetFiles) }">
			<tr>
				<th class="checkbox">
					<label for="restricted"><bright:cmsWrite identifier="label-restricted" filter="false"/></label>
				</th>
				<td></td>
				<c:if test="${bIsBulkUpdate}">
					<td>
						<select name="update_previewrestricted" id="update_previewrestricted" style="width:auto;">
							<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
							<option value="replace" <logic:equal name='assetForm' property="updateType(previewrestricted)" value="replace">selected</logic:equal>><bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
						</select>
					</td>
					<td></td>
				</c:if>
				<td>
					<%-- If it is a new asset and no validation error then default restricted to false --%>
					<c:choose>
						<c:when test="${assetForm.asset.id<=0 && !assetForm.hasErrors}">
							<bright:applicationSetting id="defaultToRestricted" settingName="default-to-restricted"/>
							<c:choose>
								<c:when test="${defaultToRestricted && !userprofile.isAdmin}">	
									<input type="checkbox" name="asset.isRestricted" class="checkbox" id="restricted" checked/>
								</c:when>
								<c:otherwise>
									<input type="checkbox" name="asset.isRestricted" class="checkbox" id="restricted"/>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<html:checkbox name="assetForm" property="asset.isRestricted" styleClass="checkbox" styleId="restricted" /> 
						</c:otherwise>
					</c:choose>
				</td>					
			</tr>
		</c:if>

		<c:if test="${userprofile.isAdmin && advancedViewingEnabled}">
			<tr>
				<th class="checkbox">
					<label for="advancedViewing"><bright:cmsWrite identifier="label-advanced-viewing" filter="false"/> </label>
				</th>
				<td>
				</td>
				<c:if test="${bIsBulkUpdate}">
				<td>
					<select name="update_advancedViewing" id="update_advancedViewing" style="width:auto;">
						<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
						<option value="replace" <logic:equal name='assetForm' property="updateType(advancedViewing)" value="replace">selected</logic:equal>><bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
					</select>
				</td>
				<td></td>
				</c:if>
				<td>
					<html:checkbox name="assetForm" property="asset.advancedViewing" styleClass="checkbox" styleId="advancedViewing" /> 
				</td>						
			</tr>
		</c:if>

	</table>
	
	<c:set var="anyDescriptiveCategories" value="false" scope="request"/>
	<logic:notEmpty name="assetForm" property="descriptiveCategoryForm.topLevelCategories">
		<c:set var="anyDescriptiveCategories" value="true" scope="request"/>
	</logic:notEmpty>

	<c:if test="${!(hidePermissionCategories && (hideDescriptiveCategories || !anyDescriptiveCategories || !assetForm.areCategoriesVisible))}"><div class="hr"></div></c:if>	

	<%-- Note that currently the static attributes are ignored for editing categories and access levels! --%>																				
	<c:set scope="request" var="ctrlAvailableSubtitle"><bright:cmsWrite identifier="available-categories-label" filter="false" removeLinebreaks="true" /></c:set>
	<c:set scope="request" var="ctrlIncludedSubtitle"><bright:cmsWrite identifier="included-categories-label" filter="false" removeLinebreaks="true" /></c:set>
	
	<jsp:include page="../category/inc_asset_category_fields.jsp"/>
	
	<bright:applicationSetting id="categoriesDropdowns" settingName="categories-dropdowns"/>
	
	<c:if test="${categoriesDropdowns}">
		<!-- Hidden field to contain the ids of the selected categories: -->
		<script type="text/javascript">
		<!--
			document.write('<html:hidden name="assetForm" property="descriptiveCategoryForm.categoryIds"/>');
		-->
		</script>
	</c:if>	
	
	<bright:applicationSetting id="accessLevelsDropdowns" settingName="access-levels-dropdowns"/>
	
	<%-- multiboxfix: fixes java 1.6 error --%>
	
	<%-- only display if java dropdown option is on or not showing access levels --%>	
	<c:if test="${accessLevelsDropdowns}">
		<script type="text/javascript">
		<!--	
		document.write('<html:hidden name="assetForm" property="permissionCategoryForm.categoryIds"/>');
		-->
		</script>		
	</c:if>
	
	<%--  Rotate option for bulk update only --%>
	<c:if test="${bIsBulkUpdate}">
		<c:if test="${showRotate}">
			<div class="hr"></div>
			
			<strong><label for="rotateImages"><bright:cmsWrite identifier="label-rotate-images" filter="false"/></label></strong>&nbsp;
			<html:select name="bulkUpdateForm" property="rotateImagesAngle">
				<jsp:include page="../customisation/rotate_image_options.jsp"/>
			</html:select>
		</c:if>
		
		<c:if test="${showUnrelateAssets}">
			<div class="hr"></div>

			<strong><label for="removeRelationships"><bright:cmsWrite identifier="label-delete-peer-relationships" filter="false"/></label></strong>&nbsp;
			<html:checkbox name="bulkUpdateForm" property="unrelateAssets" styleClass="checkbox"/>
		</c:if>
		
	</c:if>
	
	<jsp:include page="inc_submit_options.jsp"/>
	<%@include file="../customisation/inc_update_asset_custom_fields.jsp"%>	
		
	