<logic:notPresent name="displayAttributeGroup">
	<%-- default to the group of search display attributes --%>
	<c:set var="displayAttributeGroup" value="1" scope="request" />
</logic:notPresent>

<logic:notPresent name="firstIsHeader">
	<c:set var="firstIsHeader" value="false" />
</logic:notPresent>

<bean:define id="descriptions" name="item" property='<%= "descriptions(" + String.valueOf(request.getAttribute("displayAttributeGroup")) + ")" %>' />
<c:set var="setHeader" value="false" />
<logic:notEmpty name="descriptions">
	<ul class="attributeList">
		<logic:iterate name="descriptions" id="description" >
			<c:if test="${(not empty description && not empty description.description) && empty description.iconFile}">
				<li>
				
					<c:if test="${firstIsHeader && !setHeader}">
						<h2>
					</c:if>
				
					<logic:equal name="description" property="isLink" value="true">
						<a href="viewAsset?<c:out value='${viewUrlParams}' />" title="View asset details">
					</logic:equal>
				
					<bean:write name="description" property="description" filter="false"/>
				
								  
					<logic:equal name="description" property="isLink" value="true">
						</a>
					</logic:equal>
				
					<c:choose>
						<c:when test="${firstIsHeader && !setHeader}"></h2><c:set var="setHeader" value="true" /></c:when>
						<c:otherwise><br/></c:otherwise>
					</c:choose>
					
				</li>
			</c:if>
		</logic:iterate>

		<%@include file="../inc/result_extra_descriptions.jsp"%>
	</ul>


</logic:notEmpty>


