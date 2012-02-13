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
		<logic:notEmpty name="assetForm" property="asset.previewImageFile">
			<html:hidden name="assetForm" property="asset.previewImageFile.path"/>
			<html:hidden name="assetForm" property="asset.thumbnailImageFile.path"/>
			<html:hidden name="assetForm" property="asset.homogenizedImageFile.path"/>
			<html:hidden name="assetForm" property="asset.previewClipLocation"/>
			<html:hidden name="assetForm" property="asset.embeddedPreviewClipLocation"/>
		</logic:notEmpty>
		
		<c:if test="${not assetForm.asset.entity.allowChildren}">
			<html:hidden name="assetForm" property="asset.childAssetIdsAsString"/>
		</c:if>
		<c:if test="${not assetForm.asset.entity.allowPeers}">
			<html:hidden name="assetForm" property="asset.peerAssetIdsAsString"/>
		</c:if>