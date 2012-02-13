<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan		20-Feb-2006		Created
	 d2		Ben Browning	22-Feb-2006		HTML/CSS tidy up
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
		
	<c:set var="rule" value="${attributeRulesForm.changeAttributeValueRule}" />
	
	<c:choose>
		<c:when test="${rule.id == 0}">	
			<bean:define id="pagetitle" value="Add Change Attribute Value Rule"/>
		</c:when>
		<c:otherwise>
			<bean:define id="pagetitle" value="Edit Change Attribute Value Rule"/>			
		</c:otherwise>
	</c:choose>

</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
		
	<logic:equal name="attributeRulesForm" property="hasErrors" value="true"> 
		<div class="error">
		<logic:iterate name="attributeRulesForm" property="errors" id="errorText">
			<bean:write name="errorText" /><br />
		</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form action="saveChangeAttributeValueRule" method="post" styleClass="floated">
		<input type="hidden" name="attributeId" value="<c:out value='${attributeRulesForm.changeAttributeValueRule.attributeRef.id}' />" />
		<html:hidden name="attributeRulesForm" property="changeAttributeValueRule.id"/>
		<html:hidden name="attributeRulesForm" property="changeAttributeValueRule.attributeRef.id"/>
		
		<input type="hidden" name="mandatory_changeAttributeValueRule.name" value="Please enter a name for the rule." />
		<input type="hidden" name="mandatory_changeAttributeValueRule.newValue" value="Please enter the new attribute value." />
	
		<p>
			Enter a short name for this rule (eg 'Hide on expiry') to indicate what it does,
			and link to the attribute to change. <br />
			For dropdown types, the new value to set must match exactly the desired value from the drop down list of attribute values.
		</p>

		
		<label>Enabled:</label>
		<html:checkbox styleClass="checkbox" name="attributeRulesForm" styleId="enabled" property="changeAttributeValueRule.enabled"/> 
		<label for="enabled" class="after">Enabled?</label>
		<br />

		<label for="name">Name: <span class="required">*</span></label>
		<html:text styleClass="text" styleId="name" name="attributeRulesForm" property="changeAttributeValueRule.name" maxlength="50"/>
		<br />

		<label for="attrchange">Attribute to change:</label>
		<html:select name="attributeRulesForm"  styleId="attrchange" property="changeAttributeValueRule.attributeToChange.id">
			<html:optionsCollection name="attributeRulesForm" property="allAttributes" value="id" label="name"/>
		</html:select>
		<br />

		<label for="valuetoset">Value to set: <span class="required">*</span></label>
		<html:text styleClass="text" styleId="valuetoset" name="attributeRulesForm" property="changeAttributeValueRule.newValue" maxlength="100"/>
		<br />

		<label for="actions">Action on assets:</label>
		<html:select name="attributeRulesForm" property="changeAttributeValueRule.actionOnAssetId" styleId="actions">
			<html:option value="0">[none]</html:option>
			<html:optionsCollection name="attributeRulesForm" property="actionsOnAssets" value="id" label="name"/>
		</html:select>
		<br />			
		

		<div class="hr"></div>
		<input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-save" filter="false" />" />
		<a href="../action/viewManageAttributeRules?attributeId=<c:out value='${attributeRulesForm.changeAttributeValueRule.attributeRef.id}' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
	</html:form>


	<%@include file="../inc/body_end.jsp"%>
</body>
</html>