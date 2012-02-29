<bright:applicationSetting id="entityKey" settingName="asset-entity-key"/>
<c:if test="${entityKey}">
	<bright:refDataList id="entities" transactionManagerName="DBTransactionManager" componentName="AssetEntityManager" methodName="getEntities"/>	
	
	<ul id="key">
		
		<logic:iterate name='entities' id="entity">
			<li class="clearfix"><span class="<bean:write name='entity' property='name'/>">&nbsp;</span><bean:write name='entity' property='name'/></li>
		</logic:iterate>

	</ul>
	
</c:if>  