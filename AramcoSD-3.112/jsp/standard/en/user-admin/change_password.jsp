<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
	 d3		Ben Browning	21-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="forceStrongPassword" settingName="force-strong-password" />

<head>
	
	<title><bright:cmsWrite identifier="title-your-password" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="profile"/>
	<script type="text/JavaScript">
		// give the current password field the focus once the dom is ready
		$j(function () {
  			$j('#current').focus();
		});
	</script>	
</head>

<body id="profilePage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-your-password" filter="false" /></h1> 

	<bright:cmsWrite identifier="intro-change-password" filter="false" />
	
	<c:if test="${forceStrongPassword}">
		<div class="info">
			<bright:cmsWrite identifier="strong-password-guidance" filter="false" />
		</div>
	</c:if>
	
	<logic:present name="changePasswordForm">
		<logic:equal name="changePasswordForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="changePasswordForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<html:form action="/changePassword" method="post" styleClass="floated">

		<label for="current"><bright:cmsWrite identifier="label-current-password" filter="false"/></label>
		<html:password styleClass="text" styleId="current" property="oldPassword" size="16" maxlength="16" redisplay="false"/>
		<br />

		<label for="new"><bright:cmsWrite identifier="label-new-password" filter="false"/></label>
		<html:password styleClass="text" styleId="new" property="newPassword" size="16" maxlength="16" redisplay="false"/>
		<br />

		<label for="confirmNew"><bright:cmsWrite identifier="label-confirm-password" filter="false"/></label>
		<html:password styleClass="text" styleId="confirmNew" property="confirmNewPassword" size="16" maxlength="16" redisplay="false"/>
		<br />

		<div class="hr"></div>
		<input type="submit" class="button flush" id="submitButton" value="<bright:cmsWrite identifier="button-save" filter="false" />"  />

		<a href="viewChangeProfile" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		<br />
	</html:form>
	

	


	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>