
		<c:set var="count" value="1" />
		
		<ul class="wizard-menu">
			<li <c:if test="${tabId == 'database'}">class="active"</c:if>><a href="setupWizardStepDatabase">${count}. Database Settings</a></li>
			<c:set var="count" value="${count+1}" />
			<li <c:if test="${tabId == 'smtp'}">class="active"</c:if>><a href="setupWizardStepSmtp">${count}. SMTP settings</a></li>
			
			<c:if test="${setupWizardForm.ldapLicensed}">
				<c:set var="count" value="${count+1}" />
				<li <c:if test="${tabId == 'ldap'}">class="active"</c:if>><a href="setupWizardStepLdap">${count}. LDAP settings</a></li>
			</c:if>
					
			<c:set var="count" value="${count+1}" />
			<li <c:if test="${tabId == 'review'}">class="active"</c:if>><a href="setupWizardStepReview">${count}. Review your changes</a></li>
			<c:set var="count" value="${count+1}" />
			<li <c:if test="${tabId == 'finished'}">class="active"</c:if>>${count}. Finished!</li>
		
			<li class="cancel"><a href="viewHome">Cancel Wizard</a></li>
		</ul>
		