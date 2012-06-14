<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Jon Harvey		24-Jan-2011		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Manage Message Template</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="content"/>
	<bean:define id="pagetitle" value="Edit Message Template"/>
	<bean:define id="helpsection" value="message"/>
	<bean:define id="tabId" value="content"/>

	<%@include file="../inc/inc_mce_editor.jsp"%>
	<script type="text/JavaScript">
		// give the subject field the focus once the dom is ready
		$j(function () {
  			$j('#subject').focus();
 		});
	</script>	
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>
	
	WARNING: Text such as #name# is automatically replaced by the system.<br/>
	Do not edit such fields unless you are certain you wish to prevent the system from entering them automatically.<br/>
	Click 'Help' for further information on customising message templates.<br/><br/>

	<logic:present  name="messageTemplateForm">
		<logic:equal name="messageTemplateForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="messageTemplateForm" property="errors" id="error">
						<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>	
	
	<html:form action="saveMessageTemplate" method="post">
		<html:hidden name="messageTemplateForm" property="messageTemplate.messageTemplatePK.typeId"/>
		<html:hidden name="messageTemplateForm" property="messageTemplate.messageTemplatePK.textId"/>
		<html:hidden name="messageTemplateForm" property="messageTemplate.messageTemplatePK.languageId"/>
		<html:hidden name="messageTemplateForm" property="messageTemplate.textId"/>
		<html:hidden name="messageTemplateForm" property="messageTemplate.code"/>
		<html:hidden name="messageTemplateForm" property="messageTemplate.language.id"/>
		<html:hidden name="messageTemplateForm" property="typeId"/>
		<table cellspacing="0" class="form" summary="Form for list item details">
			<tr>
				<th>Title:</th>
				<td>
					<bean:write name="messageTemplateForm" property="messageTemplate.code"/>
				</td>
			</tr>
			<tr>
				<th>Identifier:</th>
				<td>
					<bean:write name="messageTemplateForm" property="messageTemplate.textId"/>
				</td>
			</tr>
			<tr>
				<th>Language:</th>
				<td>
					<bean:write name="messageTemplateForm" property="messageTemplate.language.name"/>
				</td>
			</tr>
			<tr>
				<th>Enabled?</th>
				<td>
					<html:checkbox styleClass="checkbox" styleId="notifyUser" name="messageTemplateForm" property="messageTemplate.enabled"/>
				</td>
			</tr>
			<tr>
				<th>AcknowledgementRequired?</th>
				<td>
					<html:checkbox styleClass="checkbox" styleId="notifyUser" name="messageTemplateForm" property="messageTemplate.acknowledgementRequired"/>
				</td>
			</tr>		
			<tr>
				<th><label for="subject">Subject:</label></th>
				<td>
					<input type="hidden" name="mandatory_messageTemplate.subject" value="Please enter a subject." />
					<input type="text" name="messageTemplate.subject" size="35" maxlength="250" value="<bean:write name="messageTemplateForm" property="messageTemplate.subject" filter="false"/>" id="subject" />
				</td>
			</tr>			
			<tr>				
								
				<input type="hidden" name="maxlength_messageTemplate.body" value="4000" />
				
				<th><label for="body">Message:</label></th>
				<td>
					<input type="hidden" name="mandatory_messageTemplate.body" value="Please enter a message body." />
					<textarea class="editor" style="width:80%;" name="messageTemplate.body" id="body" cols="90" rows="20" ><bean:write name="messageTemplateForm" property="messageTemplate.body" filter="false"/></textarea>								
				</td>
			</tr>
		</table>

		<div class="hr"></div>
			
		<input type="submit" class="button flush floated" value="Save" /> 
		<a href="manageMessageTemplates?typeId=<bean:write name="messageTemplateForm" property="typeId"/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>