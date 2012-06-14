<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan		20-Feb-2006		Created
	 d2		Ben Browning	28-Feb-2006		HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Attributes</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="attributes"/>
	<bean:define id="pagetitle" value="Attributes"/>
		
	<c:set var="rule" value="${attributeRulesForm.sendEmailRule}" />
	
	<c:choose>
		<c:when test="${rule.id == 0}">	
			<bean:define id="pagetitle" value="Add Send Email Rule"/>
		</c:when>
		<c:otherwise>
			<bean:define id="pagetitle" value="Edit Send Email Rule"/>			
		</c:otherwise>
	</c:choose>
                       
	<style type="text/css" media="screen">
		form.floated label.after {
			width: 400px !important;     
			padding-top:0;
		}
	</style>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
		
	<logic:equal name="attributeRulesForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="attributeRulesForm" property="errors" id="errorText">
				<bean:write name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form action="saveSendEmailRule" method="post" styleClass="floated wideLabel">
		<input type="hidden" name="attributeId" value="<c:out value='${attributeRulesForm.sendEmailRule.attributeRef.id}' />" />
		<html:hidden name="attributeRulesForm" property="sendEmailRule.id"/>
		<html:hidden name="attributeRulesForm" property="sendEmailRule.attributeRef.id"/>
		
		<input type="hidden" name="mandatory_sendEmailRule.name" value="Please enter a name for the rule" />
		
		<label>Enable this rule:</label>
		<html:checkbox styleClass="checkbox" styleId="enable" name="attributeRulesForm" property="sendEmailRule.enabled"/> 
		<label for="enable" class="after">Enabled?</label>
		<br />

		<label for="name">Short name for this rule:<br />(eg 'Expiry Alert') <span class="required">*</span></label>
		<html:text styleClass="text" styleId="name" name="attributeRulesForm" property="sendEmailRule.name" maxlength="50"/>
		<br />
		
		<label for="whenSend">When to send the email:</label>
		<html:text style="width:22px;" name="attributeRulesForm" styleId="whenSend" property="sendEmailRule.daysDifference.formNumber" maxlength="3"/>
		<span>days</span>
		<html:select name="attributeRulesForm" property="daysBeforeSelector" style="width:auto;">
			<html:option value="0">before</html:option>
			<html:option value="1">after</html:option>
		</html:select>
		<span>the attribute date.</span>
		<br />
		

		<label for="message">Message to send:</label>
		<textarea class="text" id="message" name="sendEmailRule.message" rows="4" style="width: 200px;"><bean:write name="attributeRulesForm" property="sendEmailRule.message" filter="false"/></textarea>
		<br />


		<div class="hr"></div>
		<p>
			<strong>Recipient groups:</strong><br />
		</p>
		<p>	
			Select one or more user groups from the list below to receive the emails:
		</p>	
		<div>
			<logic:iterate name="attributeRulesForm" property="allGroups" id="group" indexId="i">
				<%-- excludes Public group --%>
				<c:if test="${group.id != 2}">
				<bean:define id="groupId" name="group" property="id" />
				<html:multibox name="attributeRulesForm" property="groupSelectedList" styleClass="checkbox" styleId="<%=\"rec_groups_\"+groupId%>">
					<bean:write name="group" property="id" />
				</html:multibox>
				<label class="after" for="rec_groups_<bean:write name="group" property="id" />"><bean:write name="group" property="nameWithOrgUnit"/></label><br />
				</c:if>
			</logic:iterate>
		</div>		
		
		<div class="hr"></div>
		<p>
			<strong>Other recipients:</strong>
		</p>
		<div>
			<html:checkbox styleClass="checkbox" styleId="emailAdminUsers" name="attributeRulesForm" property="sendEmailRule.emailAdminUsers" /><label class="after" for="emailAdminUsers">Email admin users</label><br />

			<html:checkbox styleClass="checkbox" styleId="emailAssetDownloaders" name="attributeRulesForm" property="sendEmailRule.emailAssetDownloaders" /><label class="after" for="emailAssetDownloaders">Email any users who have downloaded the asset</label><br />
			
			<html:checkbox styleClass="checkbox" styleId="emailAssetUploader" name="attributeRulesForm" property="sendEmailRule.emailAssetUploader" /><label class="after" for="emailAssetUploader">Email user who uploaded the asset</label><br />
			
			<html:checkbox styleClass="checkbox" styleId="emailLastApprover" name="attributeRulesForm" property="sendEmailRule.emailLastApprover" /><label class="after" for="emailLastApprover">Email the last user who approved the asset</label><br />
		</div>

		<div class="hr"></div>

		<input id="saveButton" class="button flush" type="submit" value="<bright:cmsWrite identifier="button-save" filter="false" />" />
		<a href="../action/viewManageAttributeRules?attributeId=<c:out value='${attributeRulesForm.sendEmailRule.attributeRef.id}' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>

	</html:form>


	<%@include file="../inc/body_end.jsp"%>
</body>
</html>