<logic:notPresent name="displayAttributeGroup">
	<%-- default to the group of search display attributes --%>
	<c:set var="displayAttributeGroup" value="1" scope="request" />
</logic:notPresent>

<logic:notPresent name="firstIsHeader">
	<c:set var="firstIsHeader" value="false" />
</logic:notPresent>

<bean:define id="descriptions" name="item" property='<%= "descriptions(" + String.valueOf(request.getAttribute("displayAttributeGroup")) + ")" %>' />

<logic:notEmpty name="descriptions">
	<ul class="attributeList">
		<logic:iterate name="descriptions" id="description" indexId="index">
			<li>
				<logic:notEmpty name="description">
					<c:if test="${firstIsHeader && index==0}"><h2></c:if><logic:equal name="description" property="isLink" value="true"><a href="viewAsset?<c:out value='${viewUrlParams}' />" title="View asset details"></logic:equal><bean:write name="description" property="description" filter="false"/><logic:equal name="description" property="isLink" value="true"></a></logic:equal><c:choose><c:when test="${firstIsHeader && index==0}"></h2></c:when><c:otherwise><br/></c:otherwise></c:choose>
				</logic:notEmpty>
			</li>
		</logic:iterate>
	</ul>
</logic:notEmpty>

