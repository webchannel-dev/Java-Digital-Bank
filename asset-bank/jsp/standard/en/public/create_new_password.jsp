<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="showRegisterLink" settingName="showRegisterLink"/>
<bright:applicationSetting id="allowAutoLoginUsingCookie" settingName="allowAutoLoginUsingCookie"/>
<bright:applicationSetting id="forceStrongPassword" settingName="force-strong-password" />
<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>


<!-- When this attribute is present means that we are creating/updating an user profile -->
<logic:notPresent name="UpdateProfile">
	<bean:define id="UpdateProfile" value="false" />
</logic:notPresent>

<c:choose>
	<c:when test="${!UpdateProfile}">
		<bean:parameter id="actionPath" name="actionPath" value="/createNewPassword" />
	</c:when>
	<c:otherwise>
		<bean:parameter id="actionPath" name="actionPath" value="/updateUserSecurity" />
	</c:otherwise>
</c:choose>


<head>
	<title><bright:cmsWrite identifier="title-create-new-password" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>

<body id="loginPage">
	<c:if test="${supportMultiLanguage}">
		<%@include file="../inc/language-strip.jsp"%>
	</c:if>
	
<div class="leftShadow" style="width:540px">
	<div class="rightShadow">
	   <div id="loginPanel">
	
		<div class="logo">
			<%@include file="../customisation/logo_link.jsp"%>
		</div>


		<div class="loginForm">

			<logic:present name="changePasswordForm">
				<logic:equal name="changePasswordForm" property="hasErrors" value="true">
					<div class="error">
						<logic:iterate name="changePasswordForm" property="errors" id="error">
							<bright:writeError name="error" /><br />
						</logic:iterate>
					</div>
				</logic:equal>
			</logic:present>
			
			<html:form action="${actionPath}" method="post" styleClass="floated wideLabel">
				<html:hidden name="changePasswordForm" property="user.id"/>
				<html:hidden name="changePasswordForm" property="user.username"/>
				
			
			
				<c:if test="${UpdateProfile}">
				
					
					<h1><bright:cmsWrite identifier="heading-define-security-questions" filter="false" /></h1>
					
					<div class="infoInline"><bright:cmsWrite identifier="label-your-username" filter="false" /> <strong><bean:write name="changePasswordForm" property="user.username"/></strong></div> 
					
					<bright:cmsWrite identifier="intro-define-security-questions" filter="false" />
					
					<label for="firstQuestion" style="width: 14em"><bright:cmsWrite identifier="label-security-questions-one" filter="false"/></label><br />
					<html:select styleId="securityQuestion" property="selectedFirstQuestionId" >
						<option value="0">-- <bright:cmsWrite identifier="snippet-please-select" filter="false" /> --</option>
						<logic:iterate name="securityQuestions" id="securityQuestion">
							<c:if test="${securityQuestion.group.id == 1}">
								<option value="<bean:write name='securityQuestion' property='id'/>" <c:if test="${changePasswordForm.selectedFirstQuestionId == securityQuestion.id}">selected</c:if>>
									<bean:write name='securityQuestion' property='question'/></option>
							</c:if>
						</logic:iterate>
					</html:select><br />
					<label><bright:cmsWrite identifier="label-your-answer" filter="false" /></label>
					<html:text styleClass="text" styleId="answer" property="firstSecurityAnswer" size="16" maxlength="200"/>
					<br />
					
					<label for="secondQuestion" style="width: 14em"><bright:cmsWrite identifier="label-security-questions-two" filter="false"/></label><br />
					<html:select styleId="securityQuestion" property="selectedSecondQuestionId" >
						<option value="0">-- <bright:cmsWrite identifier="snippet-please-select" filter="false" /> --</option>
						<logic:iterate name="securityQuestions" id="securityQuestion">
							<c:if test="${securityQuestion.group.id == 2}">
								<option value="<bean:write name='securityQuestion' property='id'/>" <c:if test="${changePasswordForm.selectedSecondQuestionId == securityQuestion.id}">selected</c:if>>
									<bean:write name='securityQuestion' property='question'/></option>
							</c:if>
						</logic:iterate>
					</html:select><br />
					<label><bright:cmsWrite identifier="label-your-answer" filter="false" /></label>
					<html:text styleClass="text" styleId="answer" property="secondSecurityAnswer" size="16" maxlength="200"/>
					<br />
					
					<div class="hr"></div>
				</c:if>

		

				<h1><bright:cmsWrite identifier="heading-create-new-password" filter="false" /></h1> 
				
				<c:if test="${forceStrongPassword}">
					<div class="info">
						<bright:cmsWrite identifier="strong-password-guidance" filter="false" />
					</div>
				</c:if>
				
				<c:if test="${!UpdateProfile}">
					<p><div class="infoInline"><bright:cmsWrite identifier="label-your-username" filter="false" /> <strong><bean:write name="changePasswordForm" property="user.username"/></strong></div></p>
				</c:if>
				
				
				<label for="new"><bright:cmsWrite identifier="label-new-password" filter="false"/></label>
				<html:password styleClass="text" styleId="new" property="newPassword" size="16" maxlength="16" redisplay="false"/>
				<br />
		
				<label for="confirmNew"><bright:cmsWrite identifier="label-confirm-password" filter="false"/></label>
				<html:password styleClass="text" styleId="confirmNew" property="confirmNewPassword" size="16" maxlength="16" redisplay="false"/>
				<br />
		
				<div class="hr"></div>
				<input type="submit" class="button flush" id="submitButton" value="<bright:cmsWrite identifier="button-apply-and-close" filter="false" />"  />
		
				<a href="viewHome" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
				<br />
				
			</html:form>
		</div>
	
		</div>
	</div>
</div>

</body>
</html>