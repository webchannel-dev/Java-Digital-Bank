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

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Manage Email Template</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="content"/>
	<bean:define id="pagetitle" value="Edit Email Template"/>
	<bean:define id="helpsection" value="email"/>
	<bean:define id="tabId" value="content"/>

	<%@include file="../inc/inc_mce_editor.jsp"%>
	<script type="text/JavaScript">
		// give the fromField field the focus once the dom is ready
		$j(function () {
  			$j('#fromField').focus();
 		});
	</script>	
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>
	
	WARNING: Text such as #email# is automatically replaced by the system.<br/>
	Do not edit such fields unless you are certain you wish to prevent the system from entering them automatically.<br/>
	Click 'Help' for further information on customising email templates.<br/><br/>

	<logic:present  name="emailTemplateForm">
		<logic:equal name="emailTemplateForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="emailTemplateForm" property="errors" id="error">
						<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>	
	
	<html:form action="saveEmailTemplate" method="post">
		<html:hidden name="emailTemplateForm" property="emailTemplate.code"/>
		<html:hidden name="emailTemplateForm" property="emailTemplate.textId"/>
		<html:hidden name="emailTemplateForm" property="emailTemplate.language.id"/>
		<input type="hidden" name="typeId" value="1"/>
		<input type="hidden" name="listOrder" value="2"/>
		<table cellspacing="0" class="form" summary="Form for list item details">
			<tr>
				<th>Title:</th>
				<td>
					<bean:write name="emailTemplateForm" property="emailTemplate.code"/>
				</td>
			</tr>
			<tr>
				<th>Identifier:</th>
				<td>
					<bean:write name="emailTemplateForm" property="emailTemplate.textId"/>
				</td>
			</tr>
			<tr>
				<th>Language:</th>
				<td>
					<bean:write name="emailTemplateForm" property="emailTemplate.language.name"/>
				</td>
			</tr>
			<tr>
				<th>Enabled?</th>
				<td>
					<html:checkbox styleClass="checkbox" styleId="notifyUser" name="emailTemplateForm" property="emailTemplate.enabled"/>
				</td>
			</tr>
			<tr>
				<th><label for="fromField">From:</label></th>
				<td>
					<input type="hidden" name="mandatory_emailTemplate.addrFrom" value="Please enter a from address." />
					<html:text name="emailTemplateForm" property="emailTemplate.addrFrom" size="35" styleId="fromField" maxlength="250" />
				</td>
			</tr>
			<tr>
				<th><label for="addrTo">To:</label></th>
				<td>
					<input type="hidden" name="mandatory_emailTemplate.addrTo" value="Please enter a to address." />
					<html:text name="emailTemplateForm" property="emailTemplate.addrTo" styleId="addrTo" size="35" maxlength="250" />
				</td>
			</tr>
			<tr>
				<th><label for="addrCc">CC:</label></th>
				<td>
					<html:text name="emailTemplateForm" property="emailTemplate.addrCc" styleId="addrCc" size="35" maxlength="250" />
				</td>
			</tr>			
			<tr>
				<th><label for="addrBcc">BCC:</label></th>
				<td>
					<html:text name="emailTemplateForm" property="emailTemplate.addrBcc" styleId="addrBcc" size="35" maxlength="250" />
				</td>
			</tr>			
			<tr>	
			<tr>
				<th><label for="addrSubject">Subject:</label></th>
				<td>
					<input type="hidden" name="mandatory_emailTemplate.addrSubject" value="Please enter a subject." />
					<input type="text" name="emailTemplate.addrSubject" size="35" maxlength="250" value="<bean:write name="emailTemplateForm" property="emailTemplate.addrSubject" filter="false"/>" id="addrSubject" />
				</td>
			</tr>			
			<tr>				
								
				<input type="hidden" name="maxlength_emailTemplate.addrBody" value="4000" />
				
				<th><label for="addrBody">Message:</label></th>
				<td>
					<input type="hidden" name="mandatory_emailTemplate.addrBody" value="Please enter a message body." />
					<textarea class="editor" style="width:80%;" name="emailTemplate.addrBody" id="addrBody" cols="90" rows="20" ><bean:write name="emailTemplateForm" property="emailTemplate.addrBody" filter="false"/></textarea>								
				</td>
			</tr>
		</table>

		<div class="hr"></div>
		
	
		<input type="submit" class="button flush floated" value="Save" /> 
		<a href="manageEmailTemplates?typeId=1" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>