<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Matt Stevenson	06-May-2005		Removed presentation, hide generate button when no javascript
	 d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	 d4		Matt Stevenson	20-May-2005		Changes for password notifications
	 d5		Ben Browning	21-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Reset user password</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="users"/>
	<bean:define id="pagetitle" value="Reset Password"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:present name="changePasswordForm">
		<logic:equal name="changePasswordForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="changePasswordForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<p>Please provide a new password for <bean:write name="changePasswordForm" property="user.forename"/> <bean:write name="changePasswordForm" property="user.surname"/> (<bean:write name="changePasswordForm" property="user.username"/>)</p>
	
	<html:form action="changeUserPassword" method="post" styleClass="floated">
		<html:hidden name="changePasswordForm" property="searchCriteria.forename"/>
		<html:hidden name="changePasswordForm" property="searchCriteria.surname"/>
		<html:hidden name="changePasswordForm" property="searchCriteria.username"/>
		<html:hidden name="changePasswordForm" property="searchCriteria.emailAddress"/>
		<html:hidden name="changePasswordForm" property="searchCriteria.groupId"/>
		<input type="hidden" name="id" value='<bean:write name="changePasswordForm" property="user.id"/>'>
		<input type="hidden" name="template" value="password_change">
		<input type="hidden" name="email" value="<bean:write name='changePasswordForm' property='user.emailAddress'/>">
		<input type="hidden" name="name" value="<bean:write name='changePasswordForm' property='user.fullName'/>">
		<input type="hidden" name="username" value="<bean:write name='changePasswordForm' property='user.username'/>">
		<html:hidden name="changePasswordForm" property="user.id"/>
	
	
	
		<label for="pwd">New Password:</label>

		<html:text name="changePasswordForm" property="newPassword" styleClass="small" maxlength="50" size="25" styleId="pwd"/>
		<br />
		<label>&nbsp;</label>
		<html:checkbox styleClass="checkbox" styleId="notifyUser" name="changePasswordForm" property="notifyUser"/> <label for="notifyUser" class="after" >Send notification email to user (<bean:write name="changePasswordForm" property="user.emailAddress"/>)</label>
		<br />
			
		<div class="hr"></div>
		<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		<a href="findUser?searchCriteria.forename=<bean:write name='changePasswordForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='changePasswordForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='changePasswordForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='changePasswordForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='changePasswordForm' property='searchCriteria.groupId'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		<br />
	</html:form>
	


	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>