			<form action="<bean:write name='actionUrl'/>" method="get">
				<input type="hidden" name="id" value="<bean:write name='downloadForm' property='asset.id'/>"/>
				<html:hidden name="downloadForm" property="asset.id"/>
				<html:hidden name="downloadForm" property="height"/>
				<html:hidden name="downloadForm" property="width"/>
				<html:hidden name="downloadForm" property="imageFormat"/>
				<html:hidden name="downloadForm" property="rotationAngle"/>
				<html:hidden name="downloadForm" property="assetUse.usageTypeId"/>
				<html:hidden name="downloadForm" property="assetUse.usageOther"/>
				<html:hidden name="downloadForm" property="agreementAccepted"/>
				<html:hidden name="downloadForm" property="conditionsAccepted"/>
				<html:hidden name="downloadForm" property="presetMaxDimension"/>
				<html:hidden name="downloadForm" property="usageTypeFormatId"/>
				<html:hidden name="downloadForm" property="compress"/>
				<html:hidden name="downloadForm" property="convertToRGB"/>
				<html:hidden name="downloadForm" property="applyStrip"/>	
				<html:hidden name="downloadForm" property="alreadyLoggedUse"/>	
				<html:hidden name="downloadForm" property="density"/>
				<html:hidden name="downloadForm" property="jpegQuality"/>
				<html:hidden name="downloadForm" property="cropX"/>
				<html:hidden name="downloadForm" property="cropY"/>
				<html:hidden name="downloadForm" property="cropWidth"/>
				<html:hidden name="downloadForm" property="cropHeight"/>
				<html:hidden name="downloadForm" property="cropMask"/>
				<html:hidden name="downloadForm" property="cropMaskId"/>
				<html:hidden name="downloadForm" property="cropMaskColour"/>
				<html:hidden name="downloadForm" property="layerToDownload"/>
				<html:hidden name="downloadForm" property="email"/>
				<html:hidden name="downloadForm" property="tempLocation"/>
				<html:hidden name="downloadForm" property="watermarkImageOption"/>
				<html:hidden name="downloadForm" property="repurpose"/>
				<html:hidden name="downloadForm" property="selectedColorSpaceId"/>
				
				<c:if test="${downloadForm.parentId>0}">
					<html:hidden name="downloadForm" property="parentId"/>
				</c:if>

				<bean:parameter id="repurposeAsset" name="repurposeAsset" value="false"/>
				<input type="hidden" name="repurposeAsset" value="<bean:write name='repurposeAsset'/>"/>
	
				<%-- cropper fields to pass back to the download page so that the previously select crop can be highlighted --%>
				<bean:parameter id="valReloadCropX" name="reloadCropX" value=""/>
				<bean:parameter id="valReloadCropY" name="reloadCropY" value=""/>
				<bean:parameter id="valReloadCropWidth" name="reloadCropWidth" value=""/>
				<bean:parameter id="valReloadCropHeight" name="reloadCropHeight" value=""/>
				<bean:parameter id="tint" name="tint" value=""/>
				<input type="hidden" name="reloadCropX" id="reloadCropX" value="<bean:write name='valReloadCropX'/>"/>
				<input type="hidden" name="reloadCropY" id="reloadCropY" value="<bean:write name='valReloadCropY'/>"/>
				<input type="hidden" name="reloadCropWidth" id="reloadCropWidth" value="<bean:write name='valReloadCropWidth'/>"/>
				<input type="hidden" name="reloadCropHeight" id="reloadCropHeight" value="<bean:write name='valReloadCropHeight'/>"/>

				<bean:parameter id="valReloadMaskX" name="reloadMaskX" value=""/>
				<bean:parameter id="valReloadMaskY" name="reloadMaskY" value=""/>
				<bean:parameter id="valReloadMaskWidth" name="reloadMaskWidth" value=""/>
				<bean:parameter id="valReloadMaskHeight" name="reloadMaskHeight" value=""/>
				<input type="hidden" name="reloadMaskX" id="reloadMaskX" value="<bean:write name='valReloadMaskX'/>"/>
				<input type="hidden" name="reloadMaskY" id="reloadMaskY" value="<bean:write name='valReloadMaskY'/>"/>
				<input type="hidden" name="reloadMaskWidth" id="reloadMaskWidth" value="<bean:write name='valReloadMaskWidth'/>"/>
				<input type="hidden" name="reloadMaskHeight" id="reloadMaskHeight" value="<bean:write name='valReloadMaskHeight'/>"/>

				<input type="hidden" name="showTabs" value="<bean:write name="showTabs"/>"/>
				<input type="hidden" name="tint" value="<bean:write name="tint"/>"/>
				
				<input type="hidden" name="advanced" value="<bean:write name='advanced'/>" />
				
				<bean:parameter name="returnUrl" id="returnUrl" value=""/>
				<input type="hidden" name="returnUrl" value="<c:out value='${returnUrl}'/>"/>
				
				<%-- Pass through simpleConvertOptionsForDownload --%>
				<bean:parameter id="simpleConvertOptionsForDownload" name="simpleConvertOptionsForDownload" value="false" />
				<input type="hidden" name="simpleConvertOptionsForDownload" value="<c:out value='${simpleConvertOptionsForDownload}'/>"/>			
				
				<html:submit styleClass="button cancel flush" property="b_cancel"><bright:cmsWrite identifier="button-back" filter="false" /></html:submit>

				<c:if test="${!userprofile.isFromCms}">
				  <c:choose>
		  		  <c:when test="${downloadForm.email}">
		  		    <html:submit styleClass="button" property="b_download"><bright:cmsWrite identifier="button-email-now" filter="false" /></html:submit>
		  		  </c:when>
		  		  <c:when test="${downloadForm.repurpose}">
		  		    <html:submit styleClass="button" property="b_download"><bright:cmsWrite identifier="button-create-now" filter="false" /></html:submit>
		  		  </c:when>
		  		  <c:otherwise>
		  		    <html:submit styleClass="button" property="b_download"><bright:cmsWrite identifier="button-download-now" filter="false" /></html:submit>
		  		  </c:otherwise>
		  		</c:choose>
				</c:if>
				<c:if test="${userprofile.isFromCms}">
					<html:submit styleClass="button" property="b_download"><bright:cmsWrite identifier="button-select-for-cms" filter="false" /></html:submit>
				</c:if>
				<logic:equal name="downloadForm" property="allowAddAssetFromExisting" value="true"> 
					<c:if test="${userprofile.canUpdateAssets}">
					<html:submit styleClass="button" value="Add as new asset" property="b_save"/>
					</c:if>
				</logic:equal>
			</form>	