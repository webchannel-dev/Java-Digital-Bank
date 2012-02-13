<bright:refDataList id="keywordBrowsers" componentName="RefDataManager" methodName="getListApplicationSetting" argumentValue="keyword-browse-page-id" /> 

<bright:applicationSetting id="keywordBrowse" settingName="keyword-browse-page-id"/>
<bright:applicationSetting id="numMostPopularAssets" settingName="num-most-popular-assets"/>
<bright:applicationSetting id="numLeastPopularAssets" settingName="num-least-popular-assets"/>

<c:choose>
	<c:when test="${keywordBrowse != null || numMostPopularAssets>0 || numLeastPopularAssets>0}">
		<div class="adminTabs">
			<c:choose>
				<c:when test="${tabId == 'browseCategories'}">
					<h2 class="current"><bright:cmsWrite identifier="tab-browse-cats" filter="false"/></h2>
				</c:when>
				<c:otherwise>
					<h2><a href="browseItems?categoryId=-1&categoryTypeId=1"><bright:cmsWrite identifier="tab-browse-cats" filter="false"/></a></h2>
				</c:otherwise>		
			</c:choose>
			<logic:notEmpty name="keywordBrowsers">
				<logic:iterate name="keywordBrowsers" id="browserId">
					<c:if test="${browserId > 0}">
						<c:set var="tabIdentifier" value="browse${browserId}"/>
						<bright:refDataList id="treeName" transactionManagerName="DBTransactionManager" componentName="TaxonomyManager" methodName="getAttributeNameForKeywordTree" argumentValue="browserId" argsAreBeans="true"/> 
						<c:choose>
							<c:when test="${tabId == tabIdentifier}">
								<h2 class="current"><bright:cmsWrite identifier="heading-browse" filter="false"/> <bean:write name='treeName'/></h2>
							</c:when>
							<c:otherwise>
								<h2><a href="keywordBrowser?categoryTypeId=<bean:write name='browserId'/>&name=<bean:write name='treeName'/>"><bright:cmsWrite identifier="heading-browse" filter="false"/> <bean:write name='treeName'/></a></h2>
							</c:otherwise>		
						</c:choose>
					</c:if>
				</logic:iterate>
			</logic:notEmpty>
			<c:if test="${numMostPopularAssets>0 || numLeastPopularAssets>0}">
				<c:choose>
					<c:when test="${tabId == 'browseByPopularity'}">
						<h2 class="current"><bright:cmsWrite identifier="tab-browse-popularity" filter="false"/></h2>
					</c:when>
					<c:otherwise>
						<h2><a href="browseAssetsByPopularity"><bright:cmsWrite identifier="tab-browse-popularity" filter="false"/></a></h2>
					</c:otherwise>		
				</c:choose>
			</c:if>
			<div class="tabClearing">&nbsp;</div>
		</div>
		<bean:define id="tabsPresent" value="true" toScope="request" />
	</c:when>
	<c:otherwise>
		<bean:define id="tabsPresent" value="false" toScope="request" />
	</c:otherwise>
</c:choose>