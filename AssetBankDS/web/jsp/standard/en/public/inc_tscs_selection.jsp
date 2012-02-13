		
		<c:choose>
			<c:when test="${readTsCs=='true' && !(userprofile.isAdmin && adminExempt)}">
				<label class="wrapping">
				<c:if test="${tsandcs == true}">
					<span class="required">*</span><input type="checkbox" name="conditionsAccepted" id="accept" style="display: inline; margin-bottom: 0px;" checked="checked" /> 
				</c:if>
				<c:if test="${tsandcs == false}">
					<span class="required">*</span><html:checkbox name="downloadForm" property="conditionsAccepted" styleId="accept" style="display: inline; margin-bottom: 0px; "/> 
				</c:if>
					
				<%-- Determine which set of TCs to show --%>
				<c:choose>
					<c:when test="${downloadForm.isCommercialPurchase}">
						<bright:refDataList id="copyItem" componentName="ListManager" methodName="getListItem" argumentValue="tsandcs-commercial-checkbox"/>	
					</c:when>
					<c:otherwise>
						<%-- T&Cs depend on type of asset --%>
						<c:choose>
							<c:when test="${downloadForm.asset.typeId==3}">
								<bright:refDataList id="copyItem" componentName="ListManager" methodName="getListItem" argumentValue="tsandcs-video-checkbox"/>	
							</c:when>
							<c:when test="${downloadForm.asset.typeId==2}">
								<bright:refDataList id="copyItem" componentName="ListManager" methodName="getListItem" argumentValue="tsandcs-image-checkbox"/>	
							</c:when>
							<c:otherwise>
								<bright:refDataList id="copyItem" componentName="ListManager" methodName="getListItem" argumentValue="tsandcs-file-checkbox"/>	
							</c:otherwise>								
						</c:choose>
					</c:otherwise>
				</c:choose>
				
				<bright:write name="copyItem" property="body" filter="false"/>
				</label>
			</c:when>
			<c:otherwise>
				<html:hidden name="downloadForm" property="conditionsAccepted" value="1"/>
			</c:otherwise>
		</c:choose>

				
		<%-- Display agreements checkbox (single asset)--%>
		
		<bright:applicationSetting id="useAgreements" settingName="agreements-enabled"/>
		<c:if test="${useAgreements && empty assetBoxDownloadForm}">		
			<c:if test="${asset.agreement.id > 0}">
				<c:set var="AgreementId"><c:out value="${asset.agreement.id}" /></c:set>
				<span class="required">*</span><html:checkbox name="downloadForm" property="agreementAccepted" styleId="agreementAccept" style="display: inline; margin-bottom: 0px;" />
				<label for="agreementAccept"><bright:cmsWrite identifier="agreement-checkbox" filter="false" replaceVariables="true" /></label>
					<br />
			</c:if>
		</c:if>
		
		<%-- Display agreements checkbox (lightbox)--%>
		<c:if test="${useAgreements && not empty assetBoxDownloadForm}">		
			<c:if test="${downloadForm.assetsHaveAgreement}">
				<span class="required">*</span><html:checkbox name="downloadForm" property="agreementAccepted" styleId="agreementAccept" style="display: inline; margin-bottom: 0px;" /> 
				<label for="agreementAccept"><bright:cmsWrite identifier="agreement-checkbox-lightbox" filter="false"/></label>
				<br />
			</c:if>
		</c:if>
