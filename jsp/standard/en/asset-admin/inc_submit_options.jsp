
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="showWorkflowOptions" settingName="show-workflow-options"/>

<c:if test="${!showWorkflowOptions}">
  <span style="display: none">
</c:if>

	<%-- Options for submit to live, submit for approval or leave unsubmitted (for uploaded assets) --%>
	<c:set var="submitOptions" value="${assetForm.submitOptions}" />	 
	<c:set var="selectedSubmitOption" value="${assetForm.selectedSubmitOption}"/>
	<c:if test="${showWorkflowOptions && (submitOptions.containsMoreThanOneOption || !assetForm.allWorkflowOptionsAreHidden)}">
		<div class="hr"></div>
	</c:if>
	<c:if test="${empty batchAssetForm || !batchAssetForm.approvalMode}">	
				
		<%--  Pass through unsubmitted, this may also be reset --%>
		<html:hidden  name="assetForm" property="asset.isUnsubmitted" />

			
		<c:choose>
			<c:when test="${submitOptions.isEmpty && assetForm.noOfWorkflowOptions <= 0}">
				<input type="hidden" name="selectedSubmitOption" value="-1" />
			</c:when>
			
			<c:otherwise>

				<%--  Display the submit options --%>
				<%--  Bulk update is a table row, otherwise left align --%>		
				<c:choose>				
					<c:when 	test="${bIsBulkUpdate}">
						<table class="form" cellspacing="0" cellpadding="0" border="0">	
							<tr>
								<th>
									<label for="submitoption"><bright:cmsWrite identifier="label-update-workflow-status" filter="false"/></label>
								</th>
								<td></td>
								<td>
									<select name="update_approved" id="update_approved" style="width:auto;">
										<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
										<option value="replace"><bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
									</select>&nbsp;
									<logic:notEmpty name="assetForm" property="workflows">
									<bean:size id="noOfWorkflows" name="assetForm" property="workflows"/>
									<c:choose>
										<c:when test="${noOfWorkflows > 1}">
											<select name="selectedWorkflow" id="selectedWorkflow" style="width:auto;">
												<option value="">[Any]</option>
												<logic:iterate name="assetForm" property="workflows" id="workflow">
													<option value="<bean:write name='workflow' property='name'/>"><bean:write name='workflow' property='description'/></option>
												</logic:iterate>
											</select>
										</c:when>
										<c:otherwise>
											<input type="hidden" name="selectedWorkflow" value=""/>
										</c:otherwise>
									</c:choose>
									</logic:notEmpty>
								</td>
								<td></td>
								<td>
									<%@include file="inc_submit_option_radios.jsp"%>
								</td>														
							</tr>
						</table>

					</c:when>
					<c:otherwise>
					
						<c:if test="${!submitOptions.hasVisibleOptions}">
							<p><strong><bright:cmsWrite identifier="label-submit-action" filter="false"/>:</strong></p>
						</c:if>
						
						
						<%@include file="inc_submit_option_radios.jsp"%>
						
					</c:otherwise>			
				
				</c:choose>
				
				
					
			</c:otherwise>
		</c:choose>
		
			
		<%@include file="inc_submit_options_workflow.jsp"%>	
	</c:if>

<c:if test="${!showWorkflowOptions}">
  </span>
</c:if>	