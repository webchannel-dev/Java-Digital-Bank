<bean:size name="searchForm" property="languages" id="numLanguages"/>
<c:if test="${supportMultiLanguage && numLanguages>0}">
	<tr>
		<th>
			<label for="language"><bright:cmsWrite identifier="label-language" filter="false"/></label>
		</th>
		<td>
			<bean:define name="searchForm" property="languages" id="languages"/>
			<html:select styleId="language" name="searchForm" property="language">
				<html:option value="">[<bright:cmsWrite identifier="snippet-current" filter="false"/>]</html:option>
				<c:choose>
					<c:when test="${userprofile.currentLanguage.default}">
						<html:options collection="languages" property="code" labelProperty="name"/>
					</c:when>
					<c:otherwise>
						<html:options collection="languages" property="code" labelProperty="nativeName"/>
					</c:otherwise>
				</c:choose>
			</html:select>
		</td>
	</tr>
</c:if>