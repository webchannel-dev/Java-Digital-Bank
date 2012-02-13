<c:if test="${assetWorkflowForm.noOfWorkflows > 1}">
	<form action="<bean:write name='actionName'/>" method="get" id="workflowSwitchForm" >
		<input type="hidden" name="selectedUserId" value="<bean:write name='assetWorkflowForm' property='selectedUserId'/>"/>
		<p><bright:cmsWrite identifier="label-selected-workflow" filter="false" /></p>
		<ul class="radioList">
			<logic:iterate name="assetWorkflowForm" property="workflows" id="workflow" indexId="counter">
			   <li><input type="radio" name="workflowName" id="workflow_<c:out value='${counter}'/>" class="radio" <c:if test="${workflow.name == assetWorkflowForm.selectedWorkflow.name}">checked="checked"</c:if> value="<c:out value='${workflow.name}'/>" onclick="<c:if test="${workflow.name != assetWorkflowForm.selectedWorkflow.name}">$j('#loading').show(); </c:if>if (this.value != '0') { document.getElementById('workflowSwitchForm').submit(); }" /><label for="workflow_<c:out value='${counter}'/>"><c:out value='${workflow.description}'/><c:if test="${workflow.name != assetWorkflowForm.selectedWorkflow.name}"> <span style="display:none" id="loading"><bright:cmsWrite identifier="snippet-loading" filter="false"/></span></c:if></label></li>
			</logic:iterate>
		</ul>	   

		<noscript>
			<input type="submit" name="submit" class="button flush" value=" Go&raquo;"/>
		</noscript>
	</form>
</c:if>