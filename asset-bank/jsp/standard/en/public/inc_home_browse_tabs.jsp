<bright:refDataList id="keywordBrowsers" componentName="RefDataManager" methodName="getListApplicationSetting" argumentValue="keyword-browse-page-id" /> 

<bright:applicationSetting id="keywordBrowse" settingName="keyword-browse-page-id"/>
<bright:applicationSetting id="numMostPopularAssets" settingName="num-most-popular-assets"/>
<bright:applicationSetting id="numLeastPopularAssets" settingName="num-least-popular-assets"/>

<p class="tabHolder">
	<a href="viewHome?browseType=default" <c:if test="${tabId == 'browseCategories'}">class="active"</c:if>><bright:cmsWrite identifier="tab-browse-cats" filter="false"/></a>
	<bean:parameter id="browseCatId" name="id" value="3"/>

	<logic:notEmpty name="keywordBrowsers">
		<logic:iterate name="keywordBrowsers" id="browserId">
			<c:if test="${browserId > 0}">
				<c:set var="tabIdentifier" value="browse${browserId}"/>
				<c:set var="tempTabId" value="${tabId}${browseCatId}"/>
				<bright:refDataList id="treeName" transactionManagerName="DBTransactionManager" componentName="TaxonomyManager" methodName="getAttributeNameForKeywordTree" argumentValue="browserId" argsAreBeans="true"/> 
				
				<a href="viewHome?browseType=keyword&id=<bean:write name='browserId'/>" <c:if test="${tempTabId == tabIdentifier}">class="active"</c:if> ><bright:cmsWrite identifier="heading-browse" filter="false"/> <bean:write name='treeName'/></a>

			</c:if>
		</logic:iterate>
	</logic:notEmpty>
	
	<c:if test="${numMostPopularAssets>0 || numLeastPopularAssets>0}">
		<a href="viewHome?browseType=popular" <c:if test="${tabId == 'browseByPopularity'}">class="active"</c:if>><bright:cmsWrite identifier="tab-browse-popularity" filter="false"/></a>

	</c:if>

</p>




