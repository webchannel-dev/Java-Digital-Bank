<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>
<bright:applicationSetting id="newsPanelPosition" settingName="news-panel-position"/>
<bright:applicationSetting id="enableSlideshow" settingName="enable-slideshow"/>
<bright:applicationSetting id="enableSlideshowRepurposing" settingName="slideshow-repurposing-enabled"/>


<c:set var="newsEnabled" value="${newsPanelPosition=='middle' || newsPanelPosition=='right'}"/>

<c:if test="${supportMultiLanguage || newsEnabled}">
	<div class="adminTabs">
		<c:choose>
			<c:when test="${tabId == 'content'}">
				<h2 class="current">Content</h2>
			</c:when>
			<c:otherwise>
				<h2><a href="manageLists">Content</a></h2>
			</c:otherwise>		
		</c:choose>
		
		<c:if test="${userprofile.isAdmin}">
			<c:if test="${newsEnabled}">
				<c:choose>
					<c:when test="${tabId == 'news'}">
						<h2 class="current">News</h2>
					</c:when>
					<c:otherwise>
						<h2><a href="manageNewsItems">News</a></h2>
					</c:otherwise>		
				</c:choose>
			</c:if>
			<c:if test="${supportMultiLanguage}">
				<c:choose>
					<c:when test="${tabId == 'languages'}">
						<h2 class="current">Languages</h2>		
					</c:when>
					<c:otherwise>
						<h2><a href="viewLanguages">Languages</a></h2>
					</c:otherwise>		
				</c:choose>
			</c:if>
			<c:if test="${enableSlideshow && enableSlideshowRepurposing}">
				<c:choose>
					<c:when test="${tabId == 'repurposeSlideshows'}">
						<script type="text/javascript">
							<!--
								document.write('<h2 class="current">Embeddable Slideshows</h2>');
							-->
						</script>
					</c:when>
					<c:otherwise>
						<script type="text/javascript">
							<!--
								document.write('<h2><a href="viewRepurposedSlideshows">Embeddable Slideshows</a></h2>');
							-->
						</script>
					</c:otherwise>		
				</c:choose>
			</c:if>
			
			<c:choose>
				<c:when test="${tabId == 'export'}">
					<h2 class="current">Export</h2>		
				</c:when>
				<c:otherwise>
					<h2><a href="viewContentExport">Export</a></h2>
				</c:otherwise>		
			</c:choose>
			
			<c:choose>
				<c:when test="${tabId == 'import'}">
					<h2 class="current">Import</h2>		
				</c:when>
				<c:otherwise>
					<h2><a href="viewContentImport">Import</a></h2>
				</c:otherwise>		
			</c:choose>

		</c:if>
		<div class="tabClearing">&nbsp;</div>
	</div>
</c:if>

	