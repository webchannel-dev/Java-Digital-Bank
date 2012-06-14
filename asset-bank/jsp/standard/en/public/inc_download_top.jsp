<c:if test="${empty assetTypeName}"><c:set var="assetTypeName"><bright:cmsWrite identifier="item" case="mixed" filter="false"/></c:set></c:if>

<c:set var="returnId" value="${downloadForm.asset.id}"/>
<c:if test="${downloadForm.parentId>0}">
	<c:set var="returnId" value="${downloadForm.parentId}"/>
</c:if>
<div class="head">
	<logic:empty name="downloadForm" property="returnUrl">
		<a href="../action/viewAsset?id=<bean:write name='returnId'/>">
	</logic:empty>
	<logic:notEmpty name="downloadForm" property="returnUrl">
		<a href="../action/<bean:write name='downloadForm' property='returnUrl'/>">
	</logic:notEmpty>
		<c:choose>	
			<c:when test="${empty downloadForm.asset.entity || empty downloadForm.asset.entity.name || downloadForm.parentId>0}">
				<bright:cmsWrite identifier="link-back-image-details" filter="false" replaceVariables="true"/>
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="link-back-to" filter="false"/> <bean:write name="downloadForm" property="asset.entity.name"/> <bright:cmsWrite identifier="snippet-details" filter="false"/>
			</c:otherwise>
		</c:choose>
	</a>
</div>
 
 <logic:equal name="downloadForm" property="hasErrors" value="true">          	
	<div class="error">
		<logic:iterate name="downloadForm" property="errors" id="errorText">
			<bright:writeError name="errorText" /><br />
		</logic:iterate>
	</div>
 </logic:equal>

<!-- Copy, if there is any -->
<bright:cmsWrite identifier="download" filter="false" />

 
 <h2><bean:write name='downloadForm' property='asset.name'/></h2>