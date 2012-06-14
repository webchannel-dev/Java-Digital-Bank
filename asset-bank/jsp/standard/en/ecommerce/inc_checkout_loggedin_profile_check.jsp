
		<%-- If logged in then do an email check, and address check if mandatory --%>
		<c:set var="bLoginProfileValid" value="true" />	
			
		<c:if test="${userprofile.isLoggedIn}">
			<logic:empty name="userprofile" property="user.emailAddress">
				<c:set var="bLoginProfileValid" value="false" />
				<div class="error">
					<bright:cmsWrite identifier="e-profile-invalid-email" filter="false"/>
				</div>
				<p>
					<bright:cmsWrite identifier="e-update-non-valid-email" filter="false"/>
				</p>
			</logic:empty>
			
			<c:if test="${ecommerceUserAddressMandatory}">
				<c:if test="${userprofile.user.homeAddress.country.id == 0}">
					<c:set var="bLoginProfileValid" value="false" />
					<div class="error">
						<bright:cmsWrite identifier="e-profile-non-valid-country" filter="false"/>
					</div>
					<p>
						<bright:cmsWrite identifier="e-update-non-valid-country" filter="false"/>						 
					</p>
				</c:if>				
			</c:if>
		</c:if>	

		<c:if test="${bValidationError}">
			<c:set var="bLoginProfileValid" value="false" />
			<div class="error">
					${sValidationErrorMessage}
			</div>
		</c:if>	