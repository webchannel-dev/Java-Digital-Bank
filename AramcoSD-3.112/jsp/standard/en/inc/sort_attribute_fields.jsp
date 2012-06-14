	<bright:applicationSetting id="rating" settingName="ratings" />
	<bright:applicationSetting id="ratingsAreVotes" settingName="ratings-are-votes"/>
	<label for="sortSelect"><bright:cmsWrite identifier="label-sort-by" filter="false"/></label>
	<html:select name="sortForm" property="sortAttributeId" styleId="sortSelect">
		<optgroup label="------------------------------">	
			<c:choose>
				<c:when test="${!sortForm.customSortFieldsDefined}">
					<html:option value="0"><bright:cmsWrite identifier="label-relevance" filter="false"/></html:option>
				</c:when>
				<c:otherwise>
					<html:option value="0"><bright:cmsWrite identifier="label-default-ordering" filter="false"/></html:option>
				</c:otherwise>
			</c:choose>
		</optgroup>
		<optgroup label="------------------------------">
			<html:option value="-1"><bright:cmsWrite identifier="label-num-views" filter="false"/></html:option>
			<html:option value="-2"><bright:cmsWrite identifier="label-num-downloads" filter="false"/></html:option>
			<c:if test="${rating && ratingsAreVotes}"><html:option value="-3"><bright:cmsWrite identifier="label-num-votes" filter="false"/></html:option></c:if>
		</optgroup>
		<optgroup label="------------------------------">
			<html:optionsCollection name="sortForm" property="sortAttributes" label="label" value="id"/>
		</optgroup>
	</html:select>
	<html:select name="sortForm" property="sortDescending">
		<html:option value="false"><bright:cmsWrite identifier="snippet-ascending" filter="false"/></html:option>
		<html:option value="true"><bright:cmsWrite identifier="snippet-descending" filter="false"/></html:option>
	</html:select>
	<input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-go" filter="false" />" />
