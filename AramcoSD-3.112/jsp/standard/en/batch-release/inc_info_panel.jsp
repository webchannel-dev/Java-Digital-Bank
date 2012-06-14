<logic:notEmpty name="release">
	
	<%-- define the variables used by the content snippets --%>
	<bean:define id="brId" name="release" property="id" />
	<bean:define id="assetId" name="brAsset" property="id" />
	<bean:define id="brName" name="release" property="name" />
	<c:set var="brReleaseDate"><bean:write name="release" property="releaseDate" format="dd/MM/yyyy" /></c:set>

	<div class="info noIcon">
		<logic:notEmpty name="release" property="releaseDate">
			<bright:cmsWrite identifier="snippet-in-batch-release-released" filter="false" replaceVariables="true" />
		</logic:notEmpty>
		<logic:empty name="release" property="releaseDate">
			<bright:cmsWrite identifier="snippet-in-batch-release" filter="false" replaceVariables="true" />
		</logic:empty>
	</div>
</logic:notEmpty>