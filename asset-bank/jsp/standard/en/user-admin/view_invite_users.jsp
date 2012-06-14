<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Woollard	11-Nov-08		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bright:cmsWrite identifier="heading-invite-users" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="inviteUsers"/>

	<%@include file="../inc/inc_mce_editor.jsp"%>
</head>
<body onLoad="document.forms.inviteUsers.to.focus()">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-invite-users" filter="false"/></h1> 
	<logic:present  name="inviteUsersForm">
		<logic:equal name="inviteUsersForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="inviteUsersForm" property="errors" id="error">
						<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>	
	
	
	<c:choose>
		<c:when test="${inviteUsersForm.invitesSent}">
			<h3><bright:cmsWrite identifier="snippet-invitations-sent" filter="false"/></h3>
			
			<p><a href="viewInviteUsers"><bright:cmsWrite identifier="link-invite-more-users" filter="false"/></a></p>
		</c:when>
		
		<c:otherwise>
			<p>
				<bright:cmsWrite identifier="invitations-intro" filter="false"/>
			</p>
			
			<form method="post" action="inviteUsers" name="inviteUsers">
				<input type="hidden" name="mandatory_messageBody" value="Please enter a message body." />
				<fieldset class="bottomborder">
					<h3><bright:cmsWrite identifier="heading-compose-invite" filter="false"/></h3>
					<table cellspacing="0" cellpadding="0" class="form">
					<tr>
						<th>			
							<label for="to"><bright:cmsWrite identifier="label-email-addresses" filter="false"/><span class="required">*</span></label>
						</th>
						<td>			
							<input type="text" id="to" name="to" value="<bean:write name="inviteUsersForm" property="to" />"/><br />
							<bright:cmsWrite identifier="snippet-separate-multiple-emails" filter="false"/>
						</td>	
					</tr>
					<tr>
						<th>									
							<label for="body"><bright:cmsWrite identifier="label-message" filter="false"/><span class="required">*</span></label>
						</th>
						<td>			
							<textarea id="body" name="messageBody" class="editor" cols="90" rows="10" style="width: 600px;"><bean:write name="inviteUsersForm" property="messageBody" filter="false"/></textarea><br/>
						</td>		
					</tr>
					</table>	
				<p><em><span class="required">*</span> <bright:cmsWrite identifier="snippet-denotes-required-field" filter="false"/></em></p>		
				</fieldset>

				<div class="buttonHolder">		
							<input type="submit" name="sendMessage" value="Send &raquo;" class="button flush" />
				</div>
			</form>
		</c:otherwise>
	
	</c:choose>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>