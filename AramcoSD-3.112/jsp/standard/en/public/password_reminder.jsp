<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3      Ben Browning   14-Feb-2006    HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="usernameIsEmailaddress" settingName="username-is-emailaddress"/>

<head>
	
	<title><bright:cmsWrite identifier="title-forgotten-password" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/JavaScript">
		// give the username field the focus once the dom is ready
		$j(function () {
  			$j('#username').focus();
 		});
	</script>		
</head>

<body id="loginPage">

<div class="leftShadow">
	<div class="rightShadow">
	   <div id="loginPanel">
	
			<div class="logo">
				<%@include file="../customisation/logo_link.jsp"%>
			</div>

			<div class="loginForm">
				<h1><bright:cmsWrite identifier="heading-forgotten-password" filter="false" /></h1> 
			
				<logic:present name="loginForm">
					<logic:equal name="loginForm" property="hasErrors" value="true"> 
						<div class="error">
							<logic:iterate name="loginForm" property="errors" id="errorText">
								<bean:write name="errorText" /><br />
							</logic:iterate>
						</div>
					</logic:equal>
				</logic:present>
			
				<%@include file="../customisation/user/forgotten_pwd_intro.jsp"%>
		
				<html:form action="/passwordReminder"  method="post" styleClass="floated">
					<table class="form" cellspacing="0" cellpadding="0">			
						<c:if test="${!usernameIsEmailaddress}">
							<tr>
								<th><label for="username"> <bright:cmsWrite identifier="label-username" filter="false" /> </label></th>
								<td><html:text styleClass="text" property="forgottenUsername" styleId="username" size="20" maxlength="50"/></td>
							</tr>
						</c:if>
						<tr>
							<th><label for="email"> <bright:cmsWrite identifier="label-email" filter="false" /> </label></th>
							<td><html:text styleClass="text" property="forgottenEmail" styleId="email" size="20" maxlength="50"/></td>
						</tr>
						<tr>
							<th><label for="surname"> <bright:cmsWrite identifier="label-surname" filter="false" /> </label></th>
							<td><html:text styleClass="text" property="forgottenSurname" styleId="surname" size="20" maxlength="50"/></td>
						</tr>
					</table>
					
					<label>&nbsp;</label>
					<input type="submit" class="button flush" name="forgotten" value="<bright:cmsWrite identifier="button-next" filter="false" />" />
					<a href="../action/viewHome" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
				</html:form>
				
			</div>
		</div>
	</div>
</div>

</body>
</html>