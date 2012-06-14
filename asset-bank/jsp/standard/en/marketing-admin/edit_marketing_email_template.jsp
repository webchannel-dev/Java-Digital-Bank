<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard	05-Sep-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting settingName="supportMultiLanguage" id="supportMultiLanguage"/>
<bright:applicationSetting settingName="shared-lightboxes" id="sharedLightboxes"/>
<bright:applicationSetting settingName="email-user-upon-lightbox-share" id="emailUsersOnLightboxShare"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Edit Marketing Email Template</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="marketing"/>
	<bean:define id="pagetitle" value="Marketing"/>
	<bean:define id="helpsection" value="marketing-email-template-edit"/>
	<bean:define id="tabId" value="emailTemplates"/>

	<%@include file="../inc/inc_mce_editor.jsp"%>

</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 
	<%@include file="../marketing-admin/inc_marketing_tabs.jsp"%>
	
	<h2><c:if test="${emailTemplateForm.addingTemplate}">Add</c:if><c:if test="${!emailTemplateForm.addingTemplate}">Edit</c:if> Marketing Email Template</h2>
	
	<p>WARNING: The variable <strong>#recipients#</strong> is automatically replaced by the system with the addresses of the target users. 
	You may move this variable between address fields, but do not remove it completely.<p/>
	
	<p>You can use the variables #forename#, #surname# and #username# in order to personalise the message for the receiving user.</p>
	
	<c:if test="${sharedLightboxes && emailUsersOnLightboxShare}">
		<p>When creating a template for shared-lightbox emails, adding a variable <strong>#message#</strong> within the message body will allow a custom message to be added before sending.
		The variable <strong>#url#</strong> should be placed where a link to a shared assetbox is required.</p>
	</c:if>
	
	<br/>

	<logic:present  name="emailTemplateForm">
		<logic:equal name="emailTemplateForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="emailTemplateForm" property="errors" id="error">
						<bright:writeError name="error" />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>	
	
	<html:form action="saveMarketingEmailTemplate" method="post">
		<input type="hidden" name="listOrder" value="2"/>
		<input type="hidden" name="typeId" value="2"/>
		<input type="hidden" name="languageId" value="1"/>
		<html:hidden name="emailTemplateForm" property="addingTemplate"/>
		
		<logic:iterate name="emailTemplateForm" property="emailTemplates" id="template" indexId="tIndex">
	
			<logic:greaterThan name="template" property="language.id" value="0">
				
				<html:hidden name="emailTemplateForm" property="emailTemplates[${tIndex}].language.id"/>
				<html:hidden name="emailTemplateForm" property="emailTemplates[${tIndex}].language.name"/>
				<html:hidden name="emailTemplateForm" property="emailTemplates[${tIndex}].language.default"/>
				
				<c:if test="${not emailTemplateForm.addingTemplate}">
					<html:hidden name="emailTemplateForm" property="emailTemplates[${tIndex}].textId"/>
				</c:if>
					
				<table cellspacing="0" class="form" summary="Form for list item details">
					<c:if test="${supportMultiLanguage}">
						<tr>
							<th>Language:</th>
							<td>
								<bean:write name="template" property="language.name"/>
							</td>
						</tr>
					</c:if>
					<c:if test="${template.language.default}">
						<tr>
							<th>Title:</th>
							<td>
								<input type="text" class="text" name="emailTemplates[<bean:write name='tIndex'/>].code" maxlength="250" size="35" id="code<bean:write name='tIndex'/>" value="<bean:write name="template" property="code" />"/>
								<input type="hidden" name="mandatory_emailTemplates[<bean:write name='tIndex'/>].code" value="Please enter a Title" />
							</td>
						</tr>
						<tr>
							<th>From:</th>
							<td>
								<input type="text" class="text" name="emailTemplates[<bean:write name='tIndex'/>].addrFrom" maxlength="250" size="35" id="from<bean:write name='tIndex'/>" value="<bean:write name="template" property="addrFrom" />"/>
								<input type="hidden" name="mandatory_emailTemplates[<bean:write name='tIndex'/>].addrFrom" value="Please enter a From address." />
							</td>
						</tr>
						<tr>
							<th>To:</th>
							<td>
								<c:choose>
									<c:when test="${empty template.addrTo && empty template.addrCc && empty template.addrBcc}">
										<c:set var="toAddress">#recipients#</c:set>
									</c:when>
									<c:otherwise>
										<c:set var="toAddress"><bean:write name="template" property="addrTo" filter="false"/></c:set>
									</c:otherwise>
								</c:choose>
								<input type="text" class="text" name="emailTemplates[<bean:write name='tIndex'/>].addrTo" maxlength="250" size="35" id="to<bean:write name='tIndex'/>" value="<bean:write name="toAddress" />"/>
								<input type="hidden" name="mandatory_emailTemplates[<bean:write name='tIndex'/>].addrTo" value="Please enter a To address." />
							</td>
						</tr>
						<tr>
							<th>CC:</th>
							<td>
								<input type="text" class="text" name="emailTemplates[<bean:write name='tIndex'/>].addrCc" maxlength="250" size="35" id="from<bean:write name='tIndex'/>" value="<bean:write name="template" property="addrCc" />"/>
							</td>
						</tr>			
						<tr>
							<th>BCC:</th>
							<td>
								<input type="text" class="text" name="emailTemplates[<bean:write name='tIndex'/>].addrBcc" maxlength="250" size="35" id="from<bean:write name='tIndex'/>" value="<bean:write name="template" property="addrBcc" />"/>
							</td>
						</tr>
					</c:if>
								
					<tr>
						<th>Subject:</th>
						<td>
							<c:if test="${template.language.default}">
								<input type="hidden" name="mandatory_emailTemplates[<bean:write name='tIndex'/>].addrSubject" value="Please enter a Subject." />
							</c:if>
							<input type="text" name="emailTemplates[<bean:write name='tIndex'/>].addrSubject" size="35" maxlength="250" value="<bean:write name="template" property="addrSubject" />" />
						</td>
					</tr>			
					<tr>				
						<input type="hidden" name="maxlength_emailTemplates[<bean:write name='tIndex'/>].addrBody" value="4000" />
						<th>Body:</th>
						<td>
							<c:if test="${template.language.default}">
								<input type="hidden" name="mandatory_emailTemplates[<bean:write name='tIndex'/>].addrBody" value="Please enter a message Body." />
							</c:if>
							<textarea class="editor" style="width:100%;height: 600px;" name="emailTemplates[<bean:write name='tIndex'/>].addrBody" cols="90" rows="20" ><bean:write name="template" property="addrBody" filter="false"/></textarea>								
						</td>
					</tr>
					<tr><td>&nbsp;</td></tr>
				</table>
			</logic:greaterThan>
		</logic:iterate>

		<div class="hr"></div>
		
		<input type="submit" class="button flush floated" value="Save" /> 
		<a href="manageMarketingEmailTemplates?typeId=2&amp;languageId=1" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>
	

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>