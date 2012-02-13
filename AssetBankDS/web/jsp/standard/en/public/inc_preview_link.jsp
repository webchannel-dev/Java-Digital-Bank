<logic:notPresent name="playPopup">
	<bean:define id="playPopup" value="videoPopup"/>
</logic:notPresent>

<logic:notPresent name="previewText">
	<c:set var="previewText"><bright:cmsWrite identifier="snippet-view-preview" filter="false"/></c:set> 
</logic:notPresent>

<logic:notPresent name="height">
	<bean:define id="height" value="300"/>
</logic:notPresent>

<li class="link">
	<script type="text/javascript">
	//<!-- 
		//Check if user has flash installed (use swfobject api)
		var flashVersion = swfobject.getFlashPlayerVersion();
		var flash=flashVersion.major;		//will return 0 if no flash plugin found
		var bPreviewDownloadNotEmbed = false;
		<c:if test="${previewDownloadNotEmbed}">
			bPreviewDownloadNotEmbed = true;
		</c:if>
		
		if (flash>0 && !bPreviewDownloadNotEmbed)
		{
			//we have flash - show the preview popup link...
			document.write("<a href='../action/playMedia?file=<c:out value='${assetForm.asset.encryptedEmbeddedPreviewClipLocation}'/>&height=<c:out value='${height}'/>' target='_blank' onclick='<c:out value='${playPopup}'/>(this); return false;' class='view'><c:out value='${previewText}' escapeXml='false'/></a>");
		}
		else
		{
			//no flash - show direct download link to the preview...
			document.write("<a href='../action/downloadDeferred?downloadFile=<c:out value='${assetForm.asset.encryptedPreviewClipLocation}'/>' class='download'><bright:cmsWrite identifier='snippet-download-preview' filter='false'/></a>"); 
		}
	//-->
	</script>
	<noscript>
		<a href="../action/downloadDeferred?downloadFile=<c:out value='${assetForm.asset.encryptedPreviewClipLocation}'/>" class="download"><bright:cmsWrite identifier="snippet-download-preview" filter="false"/></a>
	</noscript>
</li>