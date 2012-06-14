			<c:set var="assetId" value="${asset.id}" />	
			
			
				<div class="formFields" >
						<%-- Show the usage drop-downs if enabled. --%>
						<c:set var="usageIsSelected" value="false" />
						<c:set var="lastSelectionName" value="" />
						
						<c:choose>
							<c:when test="${showUseDropdown && downloadForm.usageTypesAvailable}">
								
								
								<%-- Get usage type lists from the form --%>
								<bean:size id="numLevels" name="downloadForm" property="usageTypeLists" />
								<c:set var="usageHidden" value="false"/>
								<input type="hidden" name="forceClearCropMask" id="forceClearCropMask" value="0"/>
								<logic:iterate name="downloadForm" property="usageTypeLists" id="usageList" indexId="level">
								
									<bean:size id="numTypes" name="usageList" />

									<c:if test="${numTypes==1}">
										<c:if test="${level==numLevels-1}">
											<html:hidden name="downloadForm" property="assetUse.usageTypeId"/>
											<c:set var="usageIsSelected" value="true" />
											<c:set var="lastSelectionName" value="${downloadForm.selectedUsageType.name}" />																			
										</c:if>
										<c:set var="usageHidden" value="true"/>
									</c:if>
									<c:if test="${numTypes > 1}">
											
										<c:choose>
											<c:when test="${level == 0}">
												<label for="ctrlUsage"><bright:cmsWrite identifier="label-select-usage" filter="false"/>:</label><br />
											</c:when>
											<c:when test="${level > 0 && not empty lastSelectionName}">
												Select a sub-type for '<c:out value="${lastSelectionName}" />': <br />
											</c:when>
											<c:otherwise>
												<label for="ctrlUsage"><bright:cmsWrite identifier="label-select-usage" filter="false"/>:</label><br />
											</c:otherwise>
										</c:choose>		
																	
										<select id="ctrlUsage" name="usage_<c:out value='${level}' />" style="display:inline; width:200px;" onchange="document.getElementById('forceClearCropMask').value = 1; document.getElementById('b_subtypes_<c:out value="${level}"/>').click();">
											<option value="0">- <bright:cmsWrite identifier="snippet-select"/> -</option>
											<logic:iterate name="usageList" id="usage" indexId="index">
												
												<c:if test="${usage.selected}">
													<c:set var="lastSelectionName" value="${usage.name}" />
												</c:if>
												
												<option value="<c:out value='${usage.id}'/>"
													<c:if test="${usage.selected}">selected="selected"</c:if>													
													>
													<bean:write name="usage" property="name" filter="false"/>
												</option>
									
											</logic:iterate>
										</select>	
										
										<c:if test="${downloadForm.userMustRequestApprovalForHighRes && !downloadForm.userHasApprovalForHighRes}">
												<bright:cmsWrite identifier="snippet-request-high-res-download" filter="false" replaceVariables="true" />
										</c:if>														

										<%-- Show a button to get subtypes --%>									
										<input type="submit" name="b_subtypes_<c:out value="${level}" />" class="button js-enabled-hide" value="<bright:cmsWrite identifier="button-go-arrow" filter="false" />" style="display: inline;" id="b_subtypes_<c:out value="${level}"/>"/>
											
										<script type="text/javascript">
										<!--
											// BB - Note sure what this does? Is it still necessary? - have added a try..catch to stop 
											// an error occuring in IE7 after upgrading the prototype library.
											try {
												if(HTMLElement)
												{
													HTMLElement.prototype.click = function() 
													{
														var evt = this.ownerDocument.createEvent('MouseEvents');
														evt.initMouseEvent('click', true, true, this.ownerDocument.defaultView, 1, 0, 0, 0, 0, false, false, false, false, 0, null);
														this.dispatchEvent(evt);
													}
												}
											}
											catch(err){
												//do nothing 
											}
										-->
										</script>
											
									<br />
									</c:if>									
									
								</logic:iterate>	
		
								<%-- Show usage type message if there is one --%>
								<c:if test="${not empty downloadForm.selectedUsageType.usageType.message}">
									<div class="info" style="margin-top:1em;"><bean:write name="downloadForm" property="selectedUsageType.usageType.message" filter="false"/></div>
								</c:if>
																					
								<%-- Other description (details) --%>
								<c:choose>
								
									<c:when test="${downloadForm.selectedUsageType.usageType.canEnterDetails && downloadForm.assetUse.editable && ((numUsageTypeFormats > 0 || downloadForm.userCanDownloadOriginal) || downloadForm.downloadOriginalCount > 0)}">
									
										<br />
										<bright:cmsWrite identifier="label-intended-usage-details" filter="false"/>
										<c:if test="${downloadForm.selectedUsageType.usageType.detailsMandatory}"><span class="required">*</span></c:if>
										<br/>
										<textarea name="assetUse.usageOther" rows="3" cols="50" id="usageOther"><bean:write name="downloadForm" property="assetUse.usageOther" filter="false"/></textarea>
										<br />

									</c:when>
									<c:when test="${!downloadForm.assetUse.editable}">
										<html:hidden name="downloadForm" property="assetUse.usageOther"/>
									</c:when>
									<c:otherwise>
										<html:hidden name="downloadForm" property="assetUse.usageOther" value=""/>
									</c:otherwise>
								</c:choose>

								<%-- Message when there is a usage selected --%>
								<%-- Make sure user has selected a leaf if necessary --%>
								<noscript>
									<c:if test="${downloadForm.selectedUsageType.id > 0}">
										<c:if test="${!userMustSelectLeafUsageForDownload || downloadForm.selectedUsageType.leaf}">
	
											<bright:refDataList id="selectedUsageSummary" componentName="ListManager" methodName="getListItem" argumentValue="usage-selected-summary"/>	
		
											<c:if test="${!usageHidden}">
												<bean:write name="selectedUsageSummary" property="body" filter="false"/><strong>'<bean:write name="downloadForm" property="selectedUsageType.name" filter="false"/>'</strong>.
												<bean:write name="msgDownload" filter="false"/>	
											</c:if>
											<c:set var="usageIsSelected" value="true" />	
											<div class="hr"></div>	
										</c:if>						
									</c:if>		
								</noscript>		
								
								<%-- Set the asset use in the form --%>
								<input type="hidden" name="assetUse.usageTypeId" value="<c:out value='${downloadForm.selectedUsageType.id}' />"/>

								
							</c:when>
									
							<c:otherwise>
								<%-- Hardcode the value for asset use to null --%>
								<html:hidden name="downloadForm" property="assetUse.usageTypeId" value="0"/>
								<html:hidden name="downloadForm" property="assetUse.usageOther" value=""/>
								<c:set var="usageIsSelected" value="true" />
							</c:otherwise>
									
						</c:choose>
									
						<%-- Pass through flags for validation --%>
						<html:hidden name="downloadForm" property="validateUsageType" />
						<html:hidden name="downloadForm" property="validateUsageDescription" />
						
						<html:hidden name="downloadForm" property="returnUrl"/>
																					
					</div>
