	<!-- The text: this comes from the database (change in the 'Content' area of Admin) - or you can change here -->
	
<c:choose>
	<c:when test="${UpdateProfile}">
		<div class="warning">
			<bright:cmsWrite identifier="update-profile" filter="false" />
		</div>
	</c:when>
	<c:otherwise>
		<bright:cmsWrite identifier="registration" filter="false" />
	</c:otherwise>
</c:choose>
	