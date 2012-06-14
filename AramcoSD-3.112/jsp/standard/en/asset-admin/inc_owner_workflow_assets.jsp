
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
				
		<%--  TODO - in the case theres more than one workflow and one variation, we could loop twice, 
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

				<%-- Show links to the states, if there is more than one state in total--%>
				<c:if test="${sizeStates gt 1}">

					<p class="tabHolderPopup clearfix">
						<logic:iterate id="stateHolder" name="states" indexId="index3">
				
							<bean:define id="state" name="stateHolder" property="state" />				
	
		
							<a href="?selectedUserId=<bean:write name='assetWorkflowForm' property='selectedUserId'/>&workflowName=<c:out value='${assetWorkflowForm.workflowName}'/>&stateName=<c:out value="${state.name}"/>" <c:if test="${ stateHolder.state.name == assetWorkflowForm.stateName }">class="active"</c:if>>
								<c:out value="${state.description}" />		
								(<c:out value="${stateHolder.numberAssets}"/><c:if test="${stateHolder.numberAssets lt stateHolder.numberAssetsActual}"> of <c:out value="${stateHolder.numberAssetsActual}"/></c:if>)
							</a>

						</logic:iterate>	
			
					</p>
			
					<div id="tabContent">
							
		
				</c:if>
			
				
				<!-- Transitions -->
				<%--c:set var="onsubmit" value="return doConfirmation(${ais.assetId});"  --%>
				<form name="form" id="mainForm" method="post" action="<c:out value='${sChangeStateAction}'/>" class="approvalState" style="width:100%">

					<input type="hidden" name="workflowName" value="<bean:write name='assetWorkflowForm' property='workflowName'/>"/>
					<input type="hidden" name="selectedUserId" value="<bean:write name='assetWorkflowForm' property='selectedUserId'/>"/>
					<input type="hidden" name="stateName" value="<bean:write name='assetWorkflowForm' property='stateName'/>"/>
				
					<%--  Now the state lists --%>
					
					<logic:iterate name="states" id="stateHolder" indexId="index3">
					
						<c:if test="${ stateHolder.state.name == assetWorkflowForm.stateName }">
						
							<bean:define id="state" name="stateHolder" property="state" />			
							
							<%-- action buttons and message field --%>
							<c:if test="${ stateHolder.state.visibleToOwner }">
								<div style="float:right; margin-left: 1.5em"> 
									<ul class="radioButtons" style="float:right;" id="workflowActions">
										<bean:size id="numActions" name="state" property="transitionList"/>
										<logic:iterate id="transition" name="state" property="transitionList" indexId="index4">	
											<c:if test="${ ! transition.hidden }">
												<li>
													<input type="hidden" id="text_<c:out value='${ais.assetId}' />_<c:out value='${transition.transitionNumber}' />" value="<c:out value='${transition.confirmationText}' />" />
													<label class="<c:if test="${index4+1 == numActions}">last</c:if> <c:if test="${transition.messageMandatory}">message</c:if>">
													
														<input type="radio" name="transition" id="radio_<c:out value='${ais.assetId}' />_<c:out value='${transition.transitionNumber}' />" value="<c:out value="${transition.transitionNumber}" />" <c:if test="${index4 == 0}">checked="checked"</c:if> />	
													
												
														<c:out value="${transition.description}" /><br />
														<span><c:if test="${state.transitionsHaveHelpText}"><c:out value="${transition.helpText}" /></c:if></span>
													
													</label>
												</li>	
											</c:if>
										</logic:iterate>
									</ul>
													
								
									<%--  Only show audit if there is at least one audit entry --%>								
									<c:if test="${ais.auditEntry.username!=null}">
										<a href="../action/viewWorkflowAudit?id=<c:out value='${ais.assetId}'/>" target="_blank" class="viewMessages" onclick="popupAssetWorkflowAudit(<c:out value='${ais.assetId}' />); return false;" title="View asset workflow audit in a new window"><bright:cmsWrite identifier="link-previous-messages" filter="false"/> &raquo;</a>
										<strong><bright:cmsWrite identifier="snippet-message-from-previous" filter="false"/></strong> 
										<p class="approvalMessage">	
											<c:out value="${ais.auditEntry.message}" />
											<span class="userDate"><c:out value="${ais.auditEntry.name}" /> (<c:out value="${ais.auditEntry.username}" />) -  <bean:write name="ais" property="auditEntry.dateAdded" format="dd/MM/yyyy HH:mm:ss"/></span>
										</p>
									</c:if>
								
									<%--  Show message if any of the transitions have one --%>
									<c:if test="${state.transitionsHaveMessages}">
										<div class="js-enabled-hide">
										<div id="userMessageForm" title="Message">
											<label for="newMessage_<c:out value='${ais.assetId}'/>"><bright:cmsWrite identifier="snippet-message-to-next" filter="false"/>
										
											<c:choose>
												<c:when test="${transition.messageMandatory}">
													<span class="required">*</span>(<bright:cmsWrite identifier="snippet-message-required" />)</span>
												</c:when>
												<c:otherwise>
													<c:if test="${transition.hasMessage}"><span>(<bright:cmsWrite identifier="snippet-optional" filter="false"/>)</span></c:if>
												</c:otherwise>
											</c:choose>
											</label>
											<textarea name="message" class="group_edit" rows="8" id="newMessage_<c:out value='${ais.assetId}'/>"></textarea>
										</div>
										</div>
									</c:if>
								
									<input type="submit" value="Submit" class="button flush js-enabled-hide" style="clear:right; float:right"/>
					
								</div>
							</c:if>
							
							<%-- end action buttons and message form  --%>
							
							
							
							<c:if test="${sizeStates == 1}">
								<h3><c:out value="${assetWorkflowForm.state.description}" /> (<c:out value="${stateHolder.numberAssets}"/>)</h3>
							</c:if>
							
							<p><c:out value="${assetWorkflowForm.state.helpText}" /></p>
					    	

							<c:if test="${ stateHolder.state.visibleToOwner }">

								<p>
									<bright:cmsWrite identifier="snippet-select" filter="false"/>: 
									<a href="javascript:selectItems( true, 'mainForm' )"><bright:cmsWrite identifier="snippet-all" filter="false"/></a> | 
									<a href="javascript:selectItems( false, 'mainForm' )"><bright:cmsWrite identifier="snippet-none" filter="false"/></a>
								</p>
							</c:if>

							<div class="clearing"></div>
							
								<div id="itemsWrapper">
								
									<%-- sort by user --%>
									<logic:iterate name="assetWorkflowForm" property="users" id="user" indexId="userIndex">
									
										<div id="user_<c:out value="${userIndex}"/>">
											
											<div class="hr"></div>
											
											<logic:iterate id="message" name="assetWorkflowForm" property="messagesForUser(${user.id})" indexId="index">
												<div id="user_<c:out value="${userIndex}"/>_message_<c:out value="${index}"/>">
													<div class="sectionSubHead">
															
														<%-- if there's at least one message we need to show dividers and messages --%>	
														<c:if test="${index > 0}"><div class="dividerLine"></c:if>
															<c:if test="${ not empty message }">
																<div class="approvalMessageBox">
																	<div class="approvalMessageBoxInner">
																		<span class="quote quoteLeft">&#147;</span>
																		<span class="messageContent"><c:out value="${ message }" /></span>
																		<span class="quote quoteRight">&#148;</span> 
																		<span class="quoteTL"></span>
																		<span class="quoteBL"></span>
																		<span class="quoteTR"></span>
																		<span class="quoteBR"></span>
																	</div>
																	<span class="quoteTail"></span>
																</div>
															</c:if>
														<c:if test="${index > 0}"></div></c:if>


														<c:if test="${ stateHolder.state.visibleToOwner }">
															<!-- BB commented out as seems unecessary and a bit messy <span>
																																	<c:if test="${ not empty messagesEntrySet }">&nbsp;-&nbsp;</c:if>
																																	<a href="javascript:selectItems( true, 'user_<c:out value="${userIndex}"/>_message_<c:out value="${status2.index}" />' )"><bright:cmsWrite identifier="snippet-all" filter="false"/></a> | 
																																	<a href="javascript:selectItems( false, 'user_<c:out value="${userIndex}"/>_message_<c:out value="${status2.index}" />' )"><bright:cmsWrite identifier="snippet-none" filter="false"/></a>
																																</span> -->
														</c:if>
														
													</div>
													
		
												
													
													<ul class="lightbox">
				
														<logic:iterate id="ais" name="assetWorkflowForm" property="assetsForUserAndMessage(${user.id}<>${message})">
														
															<c:if test="${ais.assetTypeId!=2}">
																<c:set var="resultImgClass" value="icon"/>
															</c:if>
															<c:if test="${ais.assetTypeId==2}">
																<c:set var="resultImgClass" value="image"/>
															</c:if>
	
															<li>
																<c:if test="${ stateHolder.state.visibleToOwner }">
																	<div class="selector">
																		<label>
																			<input type="checkbox" class="checkbox" name="assetId_<c:out value='${ais.assetId}' />" />
																			<bright:cmsWrite identifier="label-select-item" filter="false"/>
																		</label>	
																	</div>	
																</c:if>
																<div class="detailWrapper">
																
																	<c:set var="thumbSrc" value="../servlet/display?file=${ais.thumbnailImageFile.path}"/>
																	<a href="viewAsset?id=<c:out value='${ais.assetId}' />" class="thumb"><img class="image" src="<bean:write name='thumbSrc'/>" alt="<bean:write name='ais' property='assetId'/>" /></a>
																	<div class="metadataWrapper">
																		<ul class="attributeList">
																			<li><bright:cmsWrite identifier="label-id" filter="false"/> <c:out value="${ais.assetId}" /></li>
																			<li><bright:cmsWrite identifier="label-date-submitted"/>: <fmt:formatDate value="${ais.submittedDate}" pattern="${dateFormat}" /></li>
																			<li><bright:cmsWrite identifier="label-username" filter="false"/> <c:out value='${ais.user.displayName}' /></li>
																			<c:if test="${!empty ais.user.emailAddress}"><li>(<c:out value="${ais.user.emailAddress}" />)</li></c:if>
																		</ul>
																	</div>	
																		
																</div>
															
																<p class="action">
																	<c:choose>
																		<%--  Only show audit if there is at least one audit entry --%>	
																		<c:when test="${ais.auditEntry.username!=null}">
																			<a href="../action/viewWorkflowAudit?id=<c:out value='${ais.assetId}'/>" target="_blank" class="view" onclick="popupAssetWorkflowAudit(<c:out value='${ais.assetId}' />); return false;" title="<bright:cmsWrite identifier="tooltip-view-workflow-audit" filter="false"/>"><bright:cmsWrite identifier="link-approval-messages" filter="false"/></a>	
																		</c:when>
																		<c:otherwise>
																			<br />
																		</c:otherwise>
																	</c:choose>	
																	<a href="viewAsset?id=<c:out value='${ais.assetId}' />" class="view"><bright:cmsWrite identifier="link-view-edit-details" filter="false" /></a>
																	
																</p>
															</li>
														
														</logic:iterate>
												
													</ul>
												
												</div>
													
												<div class="clearing"></div>
															
											</logic:iterate>
											
										</div>
									
										<div class="clearing"></div>
										
									</logic:iterate>
									<%-- end user sorting --%>
								
								</div>
													
						</c:if>
						
					</logic:iterate>
					<%-- end of the state lists --%>
					
					<div id="bottomOfForm">
					</div>
				
				</form>
				
				
				<c:if test="${sizeStates gt 1}">
					</div>
				</c:if>
			</logic:iterate>
	
		</logic:iterate>
			
	</c:if>
