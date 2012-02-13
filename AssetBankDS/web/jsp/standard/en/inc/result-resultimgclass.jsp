
					<logic:empty name="asset" property="homogenizedImageFile.path">
						<c:set var="resultImgClass" value="icon"/>
					</logic:empty>
					<logic:notEmpty name="asset" property="homogenizedImageFile.path">
						<c:set var="resultImgClass" value="image"/>
					</logic:notEmpty>
		
					<%-- There's always a thumbnail for images --%>
					<c:if test="${asset.typeId==2}">
						<c:set var="resultImgClass" value="image"/>
					</c:if>
