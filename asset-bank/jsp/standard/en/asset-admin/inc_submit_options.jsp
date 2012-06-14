
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="showWorkflowOptions" settingName="show-workflow-options"/>
<bright:applicationSetting id="batchReleasesEnabled" settingName="batch-releases-enabled" />

<c:if test="${!showWorkflowOptions}">
  <span style="display: none">
</c:if>

<c:choose>
	<c:when test="${!batchReleasesEnabled && assetForm.catExtensionCatId <= 0}">

		<%-- Options for submit to live, submit for approval or leave unsubmitted (for uploaded assets) --%>
		<c:set var="submitOptions" value="${assetForm.submitOptions}" />	 
		<c:set var="selectedSubmitOption" value="${assetForm.selectedSubmitOption}"/>
		<c:if test="${showWorkflowOptions && assetForm.fulfilledRequest.id <= 0 && (submitOptions.containsMoreThanOneOption || !assetForm.allWorkflowOptionsAreHidden)}">
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
						<c:when test="${bIsBulkUpdate}">
							
							<c:set var="preSelectReplace" value="" />
							<c:if test="${submitOptions.optionCount == 1}">
								<logic:equal name="submitOptions" property="contains[1]" value="true">
									<c:set var="preSelectReplace" >selected="selected"</c:set>
								</logic:equal>
							</c:if>
							
							<table class="form" cellspacing="0" cellpadding="0" border="0">	
								<tr>
									<th>
										<label for="submitoption"><bright:cmsWrite identifier="label-update-workflow-status" filter="false"/></label>
									</th>
									<td></td>
									<td> 
										<select name="update_approved" id="update_approved" style="width:auto;">
											<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
											<option value="replace" <c:out value="${preSelectReplace}"/> > <bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
										</select>
									</td>
									<td></td>
									<td>
										<%@include file="inc_submit_option_radios.jsp"%>
									</td>														
								</tr>
							</table>
						
						</c:when>
						<c:otherwise>						
							<%-- only display submit options if there is not a Request Workflow involved --%>
							<c:choose>
								<c:when test="${assetForm.fulfilledRequest.id <= 0 }">
									<c:if test="${!submitOptions.hasVisibleOptions}">
										<p><strong><bright:cmsWrite identifier="label-submit-action" filter="false"/>:</strong></p>
									</c:if>
									<%@include file="inc_submit_option_radios.jsp"%>
								</c:when>
								<c:otherwise>
									<input type="hidden" id="submitoption_2" name="selectedSubmitOption" value="2" />
								</c:otherwise>
							</c:choose>							
						</c:otherwise>			
					
					</c:choose>
					
					
						
				</c:otherwise>
			</c:choose>
			
				
			<%@include file="inc_submit_options_workflow.jsp"%>	
		</c:if>
	</c:when>
	<c:when test="${!batchReleasesEnabled && assetForm.catExtensionCatId > 0}">
		<%-- extension asset - always fully approve it --%>
		<input type="hidden" name="selectedSubmitOption" value="0" />
	</c:when>
	<c:otherwise>
		<%-- batch releases enabled - just mark as 'leave in current state' --%>
		<input type="hidden" name="selectedSubmitOption" value="5" />
	</c:otherwise>
</c:choose>

<c:if test="${!showWorkflowOptions}">
  </span>
</c:if>	