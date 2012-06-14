<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		28-Sep-2007		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>

<head>
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Add Marketing Group</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="marketing"/>
	<bean:define id="pagetitle" value="Marketing"/>
	<bean:define id="helpsection" value="marketing-group-edit"/>
	<bean:define id="tabId" value="marketingGroups"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 

	<%@include file="../marketing-admin/inc_marketing_tabs.jsp"%>
	
	<h2>Add Marketing Group</h2>
	
	<logic:equal name="marketingGroupForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="marketingGroupForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form action="addMarketingGroup" method="post">
		<table cellspacing="0" class="form" summary="List of marketing groups">		
			<tr>
				<th style="vertical-align:top; padding-top: 3px;">Name</th>
				<td><html:text styleClass="text" name="marketingGroupForm" property="group.name" maxlength="40"/></td>
			</tr>
			<logic:notEmpty name="marketingGroupForm" property="group.translations">
				<logic:iterate name="marketingGroupForm" property="group.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="groupName<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<html:hidden name="marketingGroupForm" property="group.translations[${tIndex}].language.id"/>
								<html:hidden name="marketingGroupForm" property="group.translations[${tIndex}].language.name"/>
								<input type="text" class="text" name="group.translations[<bean:write name='tIndex'/>].name" maxlength="40" id="groupName<bean:write name='tIndex'/>" value="<bean:write name="translation" property="name" />"/>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
			<tr>
				<th style="vertical-align:top; padding-top: 3px;">Description<br/><span class="disabled" style="font-weight:normal;">(visible to users)</span></th>
				<td><html:textarea name="marketingGroupForm" property="group.description" rows="3" cols="60"/></td>
			</tr>
			<logic:notEmpty name="marketingGroupForm" property="group.translations">
				<logic:iterate name="marketingGroupForm" property="group.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="groupDescription<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<html:hidden name="marketingGroupForm" property="group.translations[${tIndex}].language.id"/>
								<html:hidden name="marketingGroupForm" property="group.translations[${tIndex}].language.name"/>
								<textarea name="group.translations[<bean:write name='tIndex'/>].description" rows="3" cols="60" id="groupDescription<bean:write name='tIndex'/>"><bean:write name="translation" property="description" filter="false"/></textarea>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
			<tr>
				<th style="vertical-align:top; padding-top: 3px;" nowrap="nowrap">Notes<br/><span class="disabled" style="font-weight:normal;">(not visible to users)</span></th>
				<td><html:textarea name="marketingGroupForm" property="group.purpose" rows="3" cols="60"/></td>
			</tr>
			<c:if test="${supportMultiLanguage}">
				<tr>
					<th>Hidden in default language?</th>
					<td><html:checkbox style="width:auto;" name="marketingGroupForm" property="group.hiddenInDefaultLanguage"/></td>
				</tr>
			</c:if>	
		</table>
	
		<c:if test="${supportMultiLanguage}">
			<p>Note, if a translated name is not provided for a particular language, the marketing group will not be shown to users viewing the application in that language.</p>
		</c:if>
		
		<div class="hr"></div>
		
		<input type="submit" class="button floated flush" value="Save"> 
		<a href="viewMarketingGroups" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>