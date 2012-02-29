<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Stevenson		08-Feb-2008		Created
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
	<bean:define id="pagetitle" value="Users"/>
	<bean:define id="tabId" value="messageUsers"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../user-admin/inc_user_tabs.jsp"%>
	<p>
		Below is a preview of the message you are about to send. If you are happy with it click the 'Send' button, otherwise click 'Cancel' to go back and make changes.
	</p>
	<p><em>Your message will be sent to <bean:write name="messageUsersForm" property="userCount"/> users</em></p>
	
	<div class="hr"></div>
	
	<form method="post" action="messageUsers" name="messageUsers">
		<fieldset class="subtle">
			<table cellspacing="0" cellpadding="0" class="form">
			<tr>
				<th>			
					<label for="subject">Subject:</label>
				</th>
				<td class="padded">			
					<html:hidden name="messageUsersForm" property="messageSubject"/>
					<bean:write name="messageUsersForm" property="messageSubject" filter="false"/><br/>
				</td>		
			</tr>
			<tr>
				<th>									
					<label for="body">Body:</label>
				</th>
				<td class="padded">			
					<html:hidden name="messageUsersForm" property="messageBody"/>
					<bean:write name="messageUsersForm" property="completeBody" filter="false"/><br/>
				</td>		
			</tr>
			</table>		
		</fieldset>
		
		<html:hidden name="messageUsersForm" property="toUser"/>
		<logic:iterate name="messageUsersForm" property="groupIds" id="groupId" indexId="index">
			<input type="hidden" name="group<bean:write name='index'/>" value="<bean:write name='groupId'/>">
		</logic:iterate>

			
		<input type="submit" name="sendMessage" value="Send &raquo;" class="button flush floated" />
		

	</form>

	<form name="cancelForm" action="viewMessageUsers" method="post">
		<html:hidden name="messageUsersForm" property="messageSubject"/>
		<html:hidden name="messageUsersForm" property="messageBody"/>
		<html:hidden name="messageUsersForm" property="toUser"/>
		<logic:iterate name="messageUsersForm" property="groupIds" id="groupId" indexId="index">
			<input type="hidden" name="group<bean:write name='index'/>" value="<bean:write name='groupId'/>">
		</logic:iterate>
		<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-back" filter="false" />" class="button floated"  />	
	</form>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>