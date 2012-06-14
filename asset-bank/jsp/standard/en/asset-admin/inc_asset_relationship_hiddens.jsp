		<html:hidden name="assetForm" property="asset.fileLocation"/>
		<html:hidden name="assetForm" property="asset.originalFileLocation"/>
		<html:hidden name="assetForm" property="asset.id"/>
		<html:hidden name="assetForm" property="asset.entity.id"/>
		<html:hidden name="assetForm" property="asset.entity.name"/>
		<html:hidden name="assetForm" property="asset.entity.allowChildren"/>
		<html:hidden name="assetForm" property="asset.entity.allowPeers"/>
		<html:hidden name="assetForm" property="asset.entity.childRelationshipToName"/>
		<html:hidden name="assetForm" property="asset.entity.peerRelationshipToName"/>
		<html:hidden name="assetForm" property="asset.originalFilename"/>
		<html:hidden name="assetForm" property="parentRelationshipName"/>
		<logic:notEmpty name="assetForm" property="asset.rawPreviewImageFile">
			<html:hidden name="assetForm" property="asset.rawPreviewImageFile.path"/>
			<html:hidden name="assetForm" property="asset.rawThumbnailImageFile.path"/>
			<html:hidden name="assetForm" property="asset.rawHomogenizedImageFile.path"/>
			<html:hidden name="assetForm" property="asset.childPreviewImageFile.path"/>
			<html:hidden name="assetForm" property="asset.childThumbnailImageFile.path"/>
			<html:hidden name="assetForm" property="asset.childHomogenizedImageFile.path"/>
			<html:hidden name="assetForm" property="asset.previewClipLocation"/>
			<html:hidden name="assetForm" property="asset.embeddedPreviewClipLocation"/>
		</logic:notEmpty>
		
		<c:if test="${not assetForm.asset.entity.allowChildren}">
			<html:hidden name="assetForm" property="asset.childAssetIdsAsString"/>
		</c:if>
		<c:if test="${not assetForm.asset.entity.allowPeers}">
			<html:hidden name="assetForm" property="asset.peerAssetIdsAsString"/>
		</c:if>