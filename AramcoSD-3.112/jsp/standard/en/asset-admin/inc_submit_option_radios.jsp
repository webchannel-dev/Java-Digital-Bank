							<bright:applicationSetting settingName="can-create-asset-versions" id="versionsEnabled" />
							<bright:refDataList componentName="WorkflowManager" methodName="getWorkflows" id="systemWorkflows"/>
							<bean:size id="noOfWorkflows" name="systemWorkflows" />
							<c:set var="versionSwitch" value="${versionsEnabled && noOfWorkflows == 1}" />
		

							<c:set var="wfSuffix" value=""/>
							<logic:present name='workflowName'>
								<logic:notEmpty name='workflowName'>
									<c:set var="wfSuffix" value="_${workflowName}"/>
								</logic:notEmpty>
							</logic:present>

							<script type="text/javascript">
							<!--
								function checkUnsubmitted()
								{
									if (document.getElementById('submitoption_2'))
									{
										document.getElementById('submitoption_2').checked = false;
									}
								}

								function checkCanBeUnsubmitted(varRadio)
								{
									var elements = varRadio.form.elements;
									for (var n=0; n < elements.length; n++) 
									{
										if (elements[n].id.indexOf("submitoption_0") >= 0 || 
											elements[n].id.indexOf("submitoption_1") >= 0)
										{
											elements[n].checked = false;
										}
									}
								}
							-->
							</script>
							
							<logic:present name="submitOptions" property="hiddenOptions">
								<logic:notEmpty name="submitOptions" property="hiddenOptions">
									<logic:iterate name="submitOptions" property="hiddenOptions" id="hiddenOption">
										<input type="hidden" name="selectedSubmitOption<c:out value='${wfSuffix}'/>" value="<c:out value='${hiddenOption}'/>"/>
									</logic:iterate>
								</logic:notEmpty>
							</logic:present>
							
	
							<logic:present name="workflowName"><logic:notEmpty name="workflowName"><bean:define id="testWorkflowName" name="workflowName"/><bean:define id="selectedWorkflowLevel" name="assetForm" property='<%= "selectedWorkflowLevel(" + testWorkflowName + ")" %>'/></logic:notEmpty></logic:present>
							<ul class="radioList" style="width:340px; <c:if test='${reposition}'>margin-top: -10px;</c:if>">
								<!-- leave unnapproved option - basically does nothing to the asset state - leaves it as is, currently unnapproved -->
								<!-- leave live option - basically does nothing to the asset state - leaves it as is, currently live -->
								<c:set var="unsubSwitch" value="${wfSuffix != ''}" />
								<logic:equal name="submitOptions" property="contains[4]" value="true">
									<li>
										<input type="radio" class="radio" id="submitoption_4<c:out value='${wfSuffix}'/>" name="selectedSubmitOption<c:out value='${wfSuffix}'/>" value="4" <c:if test="${selectedWorkflowLevel == null || selectedWorkflowLevel < 0 || selectedWorkflowLevel == 4}">checked</c:if> <c:if test="${versionSwitch}">onclick="switchSaveType(this.value)"</c:if>/>	
										<span><label for="submitoption_4<c:out value='${wfSuffix}'/>"><bright:cmsWrite identifier="snippet-submit-option-live" filter="false"/></label></span>
									</li>
								</logic:equal>
								
								<logic:equal name="submitOptions" property="contains[0]" value="true">
									<li>
										<input type="radio" class="radio" id="submitoption_0<c:out value='${wfSuffix}'/>" name="selectedSubmitOption<c:out value='${wfSuffix}'/>" value="0" onclick="<c:if test='${unsubSwitch}'>checkUnsubmitted();</c:if> <c:if test='${versionSwitch}'>switchSaveType(this.value);</c:if>" <logic:present name='selectedWorkflowLevel'><c:if test="${selectedWorkflowLevel == 0}"> checked</c:if></logic:present><logic:notPresent name='selectedWorkflowLevel'><c:if test="${assetForm.selectedSubmitOption == 0}"> checked</c:if></logic:notPresent>/>	
										<span><label for="submitoption_0<c:out value='${wfSuffix}'/>"><bright:cmsWrite identifier="snippet-submit-option-live" filter="false"/>
										<c:if test="${!bIsBulkUpdate && submitOptions.hasMixedPermissions}"><em><bright:cmsWrite identifier="snippet-submit-option-live-if-permitted" filter="false"/></em></c:if></label></span>
									</li>
								</logic:equal>
								  
								<logic:equal name="submitOptions" property="contains[3]" value="true">
									<li>
										<input <c:if test="${versionSwitch}">onclick="switchSaveType(this.value)"</c:if> type="radio" class="radio" id="submitoption_3<c:out value='${wfSuffix}'/>" name="selectedSubmitOption<c:out value='${wfSuffix}'/>" value="3" <c:if test="${selectedWorkflowLevel == null || selectedWorkflowLevel < 0 || selectedWorkflowLevel == 3}">checked</c:if>/>	
										<span><label for="submitoption_3<c:out value='${wfSuffix}'/>"><bright:cmsWrite identifier="snippet-submit-option-approval" filter="false"/></label></span>
									</li>
								</logic:equal>
								
								<logic:equal name="submitOptions" property="contains[1]" value="true">
									<li>
										<input type="radio" class="radio" id="submitoption_1<c:out value='${wfSuffix}'/>" name="selectedSubmitOption<c:out value='${wfSuffix}'/>" value="1" onclick="<c:if test='${unsubSwitch}'>checkUnsubmitted();</c:if> <c:if test='${versionSwitch}'>switchSaveType(this.value);</c:if>" <logic:present name='selectedWorkflowLevel'><c:if test="${selectedWorkflowLevel == 1}"> checked</c:if></logic:present><logic:notPresent name='selectedWorkflowLevel'><c:if test="${assetForm.selectedSubmitOption == 1}"> checked</c:if></logic:notPresent>/>	
										<label for="submitoption_1<c:out value='${wfSuffix}'/>"><bright:cmsWrite identifier="snippet-submit-option-approval" filter="false"/></label>
									</li>
								</logic:equal>
	
								<logic:equal name="submitOptions" property="contains[2]" value="true">
									<li>
										<input type="radio" class="radio" id="submitoption_2<c:out value='${wfSuffix}'/>" name="selectedSubmitOption<c:out value='${wfSuffix}'/>" value="2" onclick="<c:choose><c:when test='${unsubSwitch}'>checkUnsubmitted();</c:when><c:otherwise>checkCanBeUnsubmitted(this);</c:otherwise></c:choose> <c:if test='${versionSwitch}'>switchSaveType(this.value);</c:if>" <logic:present name='selectedWorkflowLevel'><c:if test="${selectedWorkflowLevel == 2}"> checked</c:if></logic:present><logic:notPresent name='selectedWorkflowLevel'><c:if test="${assetForm.selectedSubmitOption == 2}"> checked</c:if></logic:notPresent>/>
										<label for="submitoption_2<c:out value='${wfSuffix}'/>"><bright:cmsWrite identifier="snippet-submit-option-unsubmitted" filter="false"/></label>
									</li>
								</logic:equal>		
							</ul>		

					
