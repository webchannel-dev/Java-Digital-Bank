<div class="adminTabs">
	<c:choose>
		<c:when test="${tabId == 'marketingGroups'}">
			<h2 class="current">Marketing Groups</h2>
		</c:when>
		<c:otherwise>
			<h2><a href="viewMarketingGroups">Marketing Groups</a></h2>
		</c:otherwise>		
	</c:choose>
	<c:choose>
		<c:when test="${tabId == 'emailTemplates'}">
			<h2 class="current">Email Templates</h2>		
		</c:when>
		<c:otherwise>
			<h2><a href="manageMarketingEmailTemplates?typeId=2&languageId=1">Email Templates</a></h2>
		</c:otherwise>		
	</c:choose>
	<c:choose>
		<c:when test="${tabId == 'sendEmail'}">
			<h2 class="current">Send Email</h2>		
		</c:when>
		<c:otherwise>
			<h2><a href="viewSendMarketingEmail">Send Email</a></h2>
		</c:otherwise>		
	</c:choose>
	<div class="tabClearing">&nbsp;</div>
</div>


	