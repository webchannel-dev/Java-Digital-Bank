<bright:applicationSetting id="showDownloadLinks" settingName="show-download-link-on-asset-panels"/>

	<%-- Download link --%>
	<c:if test="${showDownloadLinks}">
			
		<c:choose>	
			<c:when test="${item.isImage}">
				<c:if test="${item.isDirectDownloadFileType}">
					<c:set var="downloadActionURL" value="../action/downloadImage"/>
				</c:if>
				<c:if test="${!(item.isDirectDownloadFileType)}">
					<c:set var="downloadActionURL" value="../action/viewDownloadImage"/>
				</c:if>
			</c:when>
			<c:when test="${item.isVideo}">
				<c:if test="${item.isDirectDownloadFileType}">
					<c:set var="downloadActionURL" value="../action/downloadFile"/>
				</c:if>
				<c:if test="${!(item.isDirectDownloadFileType)}">
					<c:set var="downloadActionURL" value="../action/viewDownloadVideo"/>
				</c:if>
			</c:when>
			<c:when test="${item.isAudio}">
				<c:if test="${item.isDirectDownloadFileType}">
					<c:set var="downloadActionURL" value="../action/downloadFile"/>
				</c:if>
				<c:if test="${!(item.isDirectDownloadFileType)}">
					<c:set var="downloadActionURL" value="../action/viewDownloadAudio"/>
				</c:if>
			</c:when>
			<c:otherwise>
				<c:if test="${item.hasFile}">
					<c:if test="${item.isDirectDownloadFileType}">
						<c:set var="downloadActionURL" value="../action/downloadFile"/>
					</c:if>
					<c:if test="${!(item.isDirectDownloadFileType)}">
						<c:set var="downloadActionURL" value="../action/viewDownloadFile"/>
					</c:if>
				</c:if>
			</c:otherwise>
		</c:choose>	
			
		<a href="<c:out value='${downloadActionURL}' />?b_downloadOriginal=true&id=<c:out value='${item.id}' />" class="directdl"><bright:cmsWrite identifier="snippet-download" filter="false" /></a>
		
	</c:if>