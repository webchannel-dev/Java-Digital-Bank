	<bright:applicationSetting id="getRelatedAssets" settingName="get-related-assets"/>
	<bean:define id="showRelatedAssets" value="false" />
	<c:if test="${getRelatedAssets}">
		<c:choose>
			<c:when test="${groupedAssets}">
				<logic:notEmpty name="assetForm" property="asset.groupedRelatedAssets">
					<c:set var="showRelatedAssets" value="true"/>
				</logic:notEmpty>
			</c:when>
			<c:otherwise>
				<logic:notEmpty name="assetForm" property="asset.relatedAssets">
					<c:set var="showRelatedAssets" value="true"/>
				</logic:notEmpty>
			</c:otherwise>
		</c:choose>
	</c:if>	