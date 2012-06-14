<label for="label"> 
	<c:if test="${!showNativeLanguages || empty translation.language.nativeName}">
		(<bean:write name="translation" property="language.name"/>)
	</c:if>
	<c:if test="${showNativeLanguages && not empty translation.language.nativeName}">
		(<bean:write name="translation" property="language.nativeName" />)
	</c:if>
:</label>