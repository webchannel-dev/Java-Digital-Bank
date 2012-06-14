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
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Attribute Rules</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="attributes"/>
	<bean:define id="pagetitle" value="Attribute Rules"/>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
		
	<c:set var="attributeId" value="${attributeRulesForm.attribute.id}" />		
	
	<h2>Manage Attribute Rules for '<c:out value="${attributeRulesForm.attribute.label}" />'</h2>		
	
	<p>This page enables you to add rules for date attributes. 
		Each rule is triggered automatically per <bright:cmsWrite identifier="item" filter="false" /> on a certain date, according to the value of the attribute on the asset.
		</p>
	
	<p>Rules on datepicker attributes are run at midnight on trigger dates, 
		and rules on datetime attributes are run at the exact time when one or more <bright:cmsWrite identifier="item" filter="false" /> datetime attributes trigger the rule.</p>
	
	<p>There are two types of rule:</p>
	
	<ul>
		<li><strong>Send email rule</strong> - an email is sent a given number of days before or after the set date. 
		For example this may be used to send an alert to a group of users when an <bright:cmsWrite identifier="item" filter="false" /> is near its expiry date.</li>
		<li><strong>Change attribute value rule</strong> - change the value of another <bright:cmsWrite identifier="item" filter="false" /> attribute when the set date is reached.
		For example this may be used to control the Active status for an <bright:cmsWrite identifier="item" filter="false" /> for given user groups, to make Activation date and Expiry date work.</li>
	</ul>
	<br />
	
	<div class="hr"></div>
	
	<h3>Send Email Rule</h3>
	
	<logic:empty name="attributeRulesForm" property="sendEmailRules">
		<p>There are currently no Send Email rules for this attribute.<br /><br /></p>
	</logic:empty>
	<logic:notEmpty name="attributeRulesForm" property="sendEmailRules">
		<table cellspacing="0" class="admin" summary="List of rules">
			<logic:iterate name="attributeRulesForm" property="sendEmailRules" id="rule" indexId="index">
				<tr>
					<td>
						<bean:write name="rule" property="name"/>
						<c:if test="${!rule.enabled}"><em>(disabled)</em></c:if>
					</td>
					<td class="action">
						[<a href="viewEditSendEmailRule?attributeId=<c:out value='${attributeId}' />&ruleId=<c:out value='${rule.id}' />">edit</a>]
					</td>
					<td class="action">
						[<a href="deleteSendEmailRule?attributeId=<c:out value='${attributeId}' />&ruleId=<c:out value='${rule.id}' />">delete</a>]
					</td>
				</tr>
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<p><a id="addNewSendEmailRule" href="viewEditSendEmailRule?attributeId=<c:out value='${attributeId}' />">Add a new Send Email rule &raquo;</a></p>
	

	<div class="hr"></div>
	
	<h3>Change Attribute Value Rule</h3>
	<logic:empty name="attributeRulesForm" property="changeAttributeValueRules">
		<p>There are currently no Change Attribute Value rules for this attribute.<br /><br /></p>
	</logic:empty>
	<logic:notEmpty name="attributeRulesForm" property="changeAttributeValueRules">
		<table cellspacing="0" class="admin" summary="List of rules">
		<logic:iterate name="attributeRulesForm" property="changeAttributeValueRules" id="rule" indexId="index">
			<tr>
				<td>
					<bean:write name="rule" property="name"/>
					<c:if test="${!rule.enabled}"><em>(disabled)</em></c:if>
				</td>
				<td class="action">
					[<a href="viewEditChangeAttributeValueRule?attributeId=<c:out value='${attributeId}' />&ruleId=<c:out value='${rule.id}' />">edit</a>]
				</td>
				<td class="action">
					[<a href="deleteChangeAttributeValueRule?attributeId=<c:out value='${attributeId}' />&ruleId=<c:out value='${rule.id}' />">delete</a>]
				</td>
			</tr>
		</logic:iterate>
	</table>
	</logic:notEmpty>
	<p><a id="addNewChangeAttributeValueRule" href="viewEditChangeAttributeValueRule?attributeId=<c:out value='${attributeId}' />">Add a new Change Attribute Value rule &raquo;</a></p>

	<div class="hr"></div>
	
	<p><a href="../action/viewManageAttributes">&laquo; Back to attributes</a></p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>