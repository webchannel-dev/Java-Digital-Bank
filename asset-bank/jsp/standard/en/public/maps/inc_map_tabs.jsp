
<bean:size name="mapForm" property="typesSupported" id="numberMaps" />
<c:if test="${numberMaps > 1}">
	<p class="tabHolder noTopMargin">
		<%--  Generate tabs from the supported map of types, display name is just the code in mixed case --%>
		<%--  Each entry in typesSupported is a map entry with key=code, value=display name --%>
		<c:forEach var="typeEntry" items="${mapForm.typesSupported}">
			<a class="<c:if test='${mapForm.mapType == typeEntry.key}'>active</c:if>" href="viewSpatialSearchMap?attributeId=<c:out value='${mapForm.attributeId}' />&amp;point=<c:out value='${mapForm.singlePointOnly}'/>&amp;map=<c:out value='${typeEntry.key}'/><c:if test="${viewOnly}">&amp;view=true</c:if>">
				<c:out value="${typeEntry.value}" />
			</a>
		</c:forEach>
	</p>
</c:if>



