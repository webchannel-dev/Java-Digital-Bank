
	<%@include file="../asset-admin/inc_workflow_selector.jsp"%>


	<c:set var="totalSize" value="${assetWorkflowForm.approvalList.totalSize}" />
	<c:set var="returnSize" value="${assetWorkflowForm.approvalList.returnSize}" />
	
	<c:if test="${returnSize == 0}">
		<p>
			<bright:cmsWrite identifier="snippet-no-submitted-items" filter="false"/> 
		</p>
	</c:if>

	
	<c:if test="${returnSize > 0}">
		<p>	
			<bright:cmsWrite identifier="snippet-number-uploaded-items" filter="false" replaceVariables="true" /><c:choose><c:when test="${returnSize != totalSize}"><bright:cmsWrite identifier="snippet-number-uploaded-items2" filter="false" replaceVariables="true" /></c:when><c:otherwise>.</c:otherwise></c:choose><br /> 
		</p>
	
					
		<logic:present  name="assetWorkflowForm">
			<logic:equal name="assetWorkflowForm" property="hasErrors" value="true">
				<div class="error">
					<logic:iterate name="assetWorkflowForm" property="errors" id="error">
							<bean:write name="error" filter="false"/><br />
					</logic:iterate>
				</div>
			</logic:equal>
		</logic:present>		
	
		<bean:define id="workflows" name="assetWorkflowForm" property="approvalList.workflowList" />
		<bean:size id="sizeWorkflows" name="workflows" />
				
		<%--  TODO - in the case there's more than one workflow and one variation, we could loop twice, 
		the first time to generate tabs for workflow+variations and the second to show the states --%>
		<logic:iterate name="workflows" id="workflow" indexId="index">
			<bean:define id="variations" name="workflow" property="variationList" />				
			<bean:size id="sizeVariations" name="variations" />
		
			<%--  Only show workflow names if theres more than one --%>
			<c:if test="${sizeWorkflows gt 1}">
				<h3>Workflow: <c:out value="${workflow.workflowName}" /></h3>
			</c:if>
			
			<logic:iterate name="variations" id="variation" indexId="index2">
				<bean:define id="states" name="variation" property="stateList" />				
				<bean:size id="sizeStates" name="states" />
		
				<c:if test="${sizeVariations gt 1}">
					<h3>Variation: <c:out value="${variation.variationName}" /></h3>
				</c:if>

				<%-- Show links to the states, if there's more than one state in total--%>
				<c:if test="${sizeStates gt 1}">
								
					<ul>
					<logic:iterate name="states" id="stateHolder" indexId="index3">
						
						<bean:define id="assets" name="stateHolder" property="assetList" />				
						<bean:define id="state" name="stateHolder" property="state" />				
			
						<c:if test="${!empty assets}">	
						<li>
							<a href="#<c:out value="${state.name}" />">
								<c:out value="${state.description}" />		
								(<c:out value="${stateHolder.numberAssets}"/><c:if test="${stateHolder.numberAssets lt stateHolder.numberAssetsActual}"> of <c:out value="${stateHolder.numberAssetsActual}"/></c:if>)
							</a>
							<br/>
						</li>
						</c:if>						
					</logic:iterate>	
					</ul>						
				</c:if>

				<%--  Now the lists --%>
				<logic:iterate name="states" id="stateHolder" indexId="index3">
					
					<div class="hr clearing"></div>	
		
					<bean:define id="assets" name="stateHolder" property="assetList" />				
					<bean:define id="state" name="stateHolder" property="state" />				
		
					<c:if test="${!empty assets}">	
					
						<%--  anchor --%>
						<a name="<c:out value="${state.name}" />"></a>
						
						<h3>
							<c:out value="${state.description}" />						
							(<c:out value="${stateHolder.numberAssets}"/><c:if test="${stateHolder.numberAssets lt stateHolder.numberAssetsActual}"> of <c:out value="${stateHolder.numberAssetsActual}"/></c:if>)
						</h3>
						
						<c:choose>
							<c:when test="${state.visibleToOwner}">
								<p>
									<c:out value="${state.helpText}" />
									<a href="<c:out value='${sBatchAction}?state=${state.name}&variation=${variation.variationName}&workflow=${workflow.workflowName}&selectedUserId=${assetWorkflowForm.selectedUserId}' />"><bright:cmsWrite identifier="link-batch-revise-assets" filter="false"/></a>
								</p>
							</c:when>
							<c:otherwise>
								<p>
									<bright:cmsWrite identifier="snippet-workflow-items-await-review" />
								</p>						
								<ul class="lightbox">	
							</c:otherwise>
						</c:choose>
						
						<logic:iterate name="assets" id="ais" indexId="index3">
					
							<c:if test="${ais.assetTypeId!=2}">
								<c:set var="resultImgClass" value="icon"/>
							</c:if>
							<c:if test="${ais.assetTypeId==2}">
								<c:set var="resultImgClass" value="image"/>
							</c:if>
							<c:if test="${state.visibleToOwner}">
								<ul class="lightbox">
							</c:if>	
                            <li>
                                <div class="detailWrapper">
                                    <c:set var="thumbSrc" value="../servlet/display?file=${ais.thumbnailImageFile.path}"/>
                                    <c:choose>
                                        <c:when test="${state.visibleToOwner}">
                                            <a href="viewAsset?id=<c:out value='${ais.assetId}' />" class="thumb"><img class="image" src="<bean:write name='thumbSrc'/>" alt="<bean:write name='ais' property='assetId'/>" /></a><br/>
                                        </c:when>
                                        <c:otherwise>
                                            <img class="image" src="<bean:write name='thumbSrc'/>" alt="<bean:write name='ais' property='assetId'/>" /><br/>
                                        </c:otherwise>
                                    </c:choose>
										
                                    ID: <c:out value="${ais.assetId}" /><br />
                                    Date added: <fmt:formatDate value="${ais.dateAdded}" pattern="${dateFormat}" />										
                                </div>
                                <c:if test="${state.visibleToOwner}">
                                    <p class="action">
                                            <a href="viewAsset?id=<c:out value='${ais.assetId}' />" class="view"><bright:cmsWrite identifier="link-view-edit-details" filter="false" /></a>
                                    </p>
                                </c:if>
                            </li>
					
							
							<!-- Transitions - only if visible to owner -->	
							<c:if test="${state.visibleToOwner}">
								</ul>
								<c:set var="onsubmit" value="return doConfirmation(${ais.assetId});" />
								<form name="form_<c:out value='${ais.assetId}' />" method="post" action="<c:out value='${sChangeStateAction}'/>" onsubmit="<c:out value="${onsubmit}" escapeXml="false" />" class="approvalState">
									<input type="hidden" name="assetId" value="<c:out value="${ais.assetId}" />" />
									<input type="hidden" name="selectedUserId" value="<c:out value="${assetWorkflowForm.selectedUserId}" />" />
									<input type="hidden" name="workflowName" value="<c:out value='${assetWorkflowForm.selectedWorkflow.name}'/>" />
									<ul class="radioList">
										<logic:iterate name="state" property="transitionList" id="transition" indexId="index4">	
											<li>
												<input type="hidden" id="text_<c:out value='${ais.assetId}' />_<c:out value='${transition.transitionNumber}' />" value="<c:out value='${transition.confirmationText}' />" />
												<input  type="radio" class="radio" id="radio_<c:out value='${ais.assetId}' />_<c:out value='${transition.transitionNumber}' />" name="transition" value="<c:out value="${transition.transitionNumber}" />" <c:if test="${index4 == 0}">checked="checked"</c:if> />	
													
												<label for="radio_<c:out value='${ais.assetId}' />_<c:out value='${transition.transitionNumber}' />">
													<c:out value="${transition.description}" />
													<span><c:if test="${state.transitionsHaveHelpText}"><c:out value="${transition.helpText}" /></c:if></span>
													<c:choose>
														<c:when test="${transition.messageMandatory}">
															<span><em>(<bright:cmsWrite identifier="snippet-message-required" />)</em></span><span class="required">*</span>
														</c:when>
														<c:otherwise>
															<c:if test="${transition.hasMessage}"><span><em>(<bright:cmsWrite identifier="snippet-message-optional" />)</em></span></c:if>
														</c:otherwise>
													</c:choose>
												</label>
											</li>	
										</logic:iterate>
									</ul>
									
									<c:if test="${ais.auditEntry.username!=null}">
										<strong><bright:cmsWrite identifier="snippet-message-from-previous" filter="false"/></strong> 
										<p class="approvalMessage">	
											<c:out value="${ais.auditEntry.message}" />
											<span class="userDate"><c:out value="${ais.auditEntry.name}" /> (<c:out value="${ais.auditEntry.username}" />) -  <bean:write name="ais" property="auditEntry.dateAdded" format="dd/MM/yyyy HH:mm:ss"/></span>
										</p>
									</c:if>
									
									<c:if test="${state.transitionsHaveMessages}">
										<label for="newMessage_<c:out value='${ais.assetId}'/>"><bright:cmsWrite identifier="snippet-message-to-next" filter="false"/></label>
										<br />
										<textarea name="message" rows="3" id="newMessage_<c:out value='${ais.assetId}'/>"></textarea>									
									</c:if>
									
									<input type="submit" value="Submit" class="button flush" />
								</form>
								<div class="clearing"><!-- &nbsp; --></div>
								
							</c:if>
							
							
						</logic:iterate>
						<c:if test="${!state.visibleToOwner}">
							</ul>	
						</c:if>	
						
						<div class="clearing"><!-- &nbsp; --></div>
						
					</c:if>
					
				</logic:iterate>
				
			</logic:iterate>
	
		</logic:iterate>
			
	</c:if>