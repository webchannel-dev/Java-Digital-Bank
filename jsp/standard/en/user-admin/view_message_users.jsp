<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		19-Jan-2004		Created.
	d2		Matt Stevenson	06-May-2005		Removed presentation, changes to reflect 
											new user and group implementation
	d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	d4		Ben Browning	21-Feb-2006		HTML/CSS Tidy up
	d5      Matt Woollard   13-Nov-2007     Added box to email single user
	d6		Matt Stevenson	08-Feb-2008		Changed body to HTML control
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Message Users</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="users"/>
	<bean:define id="helpsection" value="message_users"/>
	<bean:define id="pagetitle" value="Users"/>
	<bean:define id="tabId" value="messageUsers"/>

	<%@include file="../inc/inc_mce_editor.jsp"%>
</head>
<body id="adminPage" onLoad="document.forms.messageUsers.messageSubject.focus()">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../user-admin/inc_user_tabs.jsp"%>
	<logic:present  name="messageUsersForm">
		<logic:equal name="messageUsersForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="messageUsersForm" property="errors" id="error">
						<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>			
	<p>
		From here you can send an e-mail to groups of users or individual users. <br/>Enter the subject and body for the message, select which groups you wish the message sent to and click 'Preview' to see how your email will look before sending.
	</p>
	<p><em><span class="required">*</span> denotes a required field</em></p>

	
	<form method="post" action="previewMessageUsers" name="messageUsers">
		<input type="hidden" name="mandatory_messageSubject" value="Please enter a message subject." />
		<input type="hidden" name="mandatory_messageBody" value="Please enter a message body." />
		<fieldset class="subtle">
			<h3>Compose message</h3>
			<table cellspacing="0" cellpadding="0" class="form">
			<tr>
				<th>			
					<label for="subject">Subject:<span class="required">*</span></label>
				</th>
				<td>			
					<input type="text" id="subject" name="messageSubject" value="<bean:write name="messageUsersForm" property="messageSubject" filter="false"/>"/><br/>
				</td>		
			</tr>
			<tr>
				<th>									
					<label for="body">Body:<span class="required">*</span></label>
				</th>
				<td>			
					<textarea id="body" name="messageBody" class="editor" cols="90" rows="20" style="width: 600px;"><bean:write name="messageUsersForm" property="messageBody" filter="false"/></textarea><br/>
				</td>		
			</tr>
			</table>		
		</fieldset>
		
				
		<%-- the 'normal' group is hidden and selected by default --%>
		<fieldset class="subtle">
			<h3>Send to group</h3>
			<table cellspacing="0" cellpading="0" class="form">
			<logic:iterate name="messageUsersForm" property="groups" id="group" indexId="index">
			
				<tr>
					<logic:equal name="index" value="0">
						<th>																	
							Select group(s):		
						</th>
					</logic:equal>
					<logic:notEqual name="index" value="0">
						<th>																	
							&nbsp;
						</th>
					</logic:notEqual>
					
					<td>
						<!-- check if the current group should be selected or not -->
						<logic:match name="messageUsersForm" property="selectedGroup[${group.id}]" value="true">
							<input type="checkbox" class="checkbox" name="group<bean:write name='index'/>" checked="checked" value="<bean:write name='group' property='id'/>">
						</logic:match>
						<logic:notMatch name="messageUsersForm" property="selectedGroup[${group.id}]" value="true">
							<input type="checkbox" class="checkbox" name="group<bean:write name='index'/>" value="<bean:write name='group' property='id'/>">
						</logic:notMatch>
						
						<bean:write name="group" property="nameWithOrgUnit"/><br/>
					</td>
				</tr>
			</logic:iterate>
			</table>
		</fieldset>
		<fieldset class="subtle">
			<h3>Or, send to user</h3>
			<table cellspacing="0" cellpading="0" class="form">
				<tr>
					<th>			
						<label for="toUser">Username:</label>
					</th>
					<td>			
						<input type="text" id="toUser" name="toUser" value="<bean:write name="messageUsersForm" property="toUser" filter="false"/>"/>
					</td>	
					<p>You can send an email to multiple users by separating them with a comma.</p>	
				</tr>
			</table>
		</fieldset>
		
		<input type="submit" name="sendMessage" value="Preview &raquo;" class="button flush" />

		
	</form>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>