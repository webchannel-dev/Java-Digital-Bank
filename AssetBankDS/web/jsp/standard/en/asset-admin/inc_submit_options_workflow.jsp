<logic:notPresent name="initialising">
	<bean:define id="initialising" value="false"/>
</logic:notPresent>

<c:set var="first" value="true"/>
<logic:notEmpty name="assetForm" property="workflowOptions">
	<bean:size name="assetForm" property="workflowOptions" id="workflowCount"/>
	<c:choose>
		<c:when test="${assetForm.asset.isUnsubmitted && workflowCount == 1}">
			<bean:define id="reposition" value="true"/>
		</c:when>
		<c:otherwise>
			<bean:define id="reposition" value="false"/>
		</c:otherwise>
	</c:choose>
	
	<logic:iterate name="assetForm" property="workflowOptions" id="optionGroup">
		<c:if test="${!assetForm.allWorkflowOptionsAreHidden}">
			<c:if test="${first}">
				<c:if test="${!assetForm.asset.isUnsubmitted}"><p><strong><bright:cmsWrite identifier="label-workflow-submit-options" filter="false"/>:</strong></p></c:if>
				<c:set var="first" value="false"/>
			</c:if>
			<c:if test="${optionGroup.options.optionCount > 0 && (workflowCount > 1 || initialising)}">
				<p><em><bright:write name='optionGroup' property='title' case="mixed"/></em></p>
			</c:if>
		</c:if>
		<c:set var="submitOptions" value="${optionGroup.options}" />	 
		<c:set var="workflowName" value="${optionGroup.identifier}"/>
		<%@include file="inc_submit_option_radios.jsp"%>
	</logic:iterate>
</logic:notEmpty>