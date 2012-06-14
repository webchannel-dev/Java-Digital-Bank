	

	<bean:parameter name="assetUse.usageTypeId" id="usageTypeId" value="-1"/>
	<bean:parameter name="conditionsAccepted" id="tsandcs" value="false"/>

	<bright:applicationSetting id="readTsCs" settingName="mustReadTsCsBeforeDownload"/>
	<bright:applicationSetting id="adminExempt" settingName="adminUsersExemptFromDownloadTsCs"/>
	<bright:applicationSetting id="showUseDropdown" settingName="showUseDropdownBeforeDownload"/>
	<bright:applicationSetting id="userMustSelectLeafUsageForDownload" settingName="userMustSelectLeafUsageForDownload" />
	<bright:applicationSetting id="simpleConvertOptionsForDownload" settingName="simpleConvertOptionsForDownload" />
		
	<%-- Show the tabs if the simpleConvertOptionsForDownload is enabled, and if the user has advanced permission. --%>
	<%-- Set advanced parameter accordingly --%>
	<bean:define id="showTabs" value="false"/>	
	<c:choose>
		<c:when test="${simpleConvertOptionsForDownload && downloadForm.userCanDownloadAdvanced}">
			<logic:notEqual parameter="showTabs" value="false">
				<bean:define id="showTabs" value="true"/>
			</logic:notEqual>
			<bean:parameter name="advanced" id="advanced" value="false"/>
		</c:when>	
		<c:otherwise>
			<bean:define id="advanced" value="false" />			
		</c:otherwise>
	</c:choose>
