<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3      Ben Browning   14-Feb-2006    HTML/CSS tidy up
	 d4     Matt Woollard   13-Nov-2007    Added suspended account error
	 d5		Steve Bryan		25-Nov-2008		Factored out openid login form	
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="allowAutoLoginUsingCookie" settingName="allowAutoLoginUsingCookie"/>
<bright:applicationSetting id="showForgottenPassword" settingName="show-forgotten-password"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="showConditionsOnLogin" settingName="showConditionsOnLogin"/>
<bright:applicationSetting id="usernameIsEmail" settingName="username-is-emailaddress"/>
<bright:applicationSetting id="ssoEnabled" settingName="sso-enabled"/>
<bright:applicationSetting id="ssoPlugin" settingName="sso-plugin-class"/>
<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>
<c:set var="showSSOLink" value="${ssoEnabled && ssoPlugin != 'WIASSOPlugin' && ssoPlugin != 'CookieTokenSSOPlugin' && ssoPlugin != 'EncryptedUrlSSOPlugin'}" />

<bean:parameter name="forwardUrl" id="forwardUrl" value=""/>
<c:if test="${empty forwardUrl}"><c:set var="forwardUrl" value="${loginForm.forwardUrl}"/></c:if>
						
<head>
	<title><bright:cmsWrite identifier="title-login-page" filter="false"/></title> 
	<bean:define id="section" value="login"/>
	<%@include file="../inc/head-elements.jsp"%>

	<script type="text/JavaScript">
		// give the username field the focus once the dom is ready
		$j(function () {
  			$j('#username').focus();
 		});
	</script>	
</head>

<body id="loginPage">
	<c:if test="${supportMultiLanguage}">
		<%@include file="../inc/language-strip.jsp"%>
	</c:if>
	<div class="leftShadow">
		<div class="rightShadow">
		
			<div id="loginPanel">
				
				<div class="logo">
					<%@include file="../customisation/logo_link.jsp"%>
				</div>
				
				<div class="loginForm">
					
					<h1><bright:cmsWrite identifier="login-title" filter="false"/></h1> 

					<c:choose>
						<c:when test="${loginForm.loggedOut}">
							<div class="error">
								<bright:cmsWrite identifier="snippet-logged-out" filter="false" replaceVariables="true"/>
							</div>
						</c:when>
						<c:when test="${loginForm.usernameInUse}">
							<div class="error">
								<bright:cmsWrite identifier="snippet-username-in-use" filter="false" replaceVariables="true"/>
							</div>
						</c:when>
						<c:when test="${loginForm.showAccessMessage}">
							<div class="error">
								<bright:cmsWrite identifier="login-as-no-permission" filter="false"/>
							</div>
						</c:when>
					</c:choose>
					
					<logic:present parameter="UpdateProfile">
						<div class="confirm">
							<bright:cmsWrite identifier="heading-profile-updated" filter="false"/>
						</div>
					</logic:present>
					<logic:present parameter="ForcePasswordChange">
						<div class="confirm">
							<bright:cmsWrite identifier="heading-password-updated" filter="false"/>
						</div>
					</logic:present>
					<logic:present parameter="invalidLink">
						<div class="error">
							<bright:cmsWrite identifier="failedValidationResetPasswordAuthId" filter="false"/>
						</div>
					</logic:present>
					
					<!-- intro text -->
					<c:choose>
						<c:when test="${not empty loginForm && loginForm.canHaveExternalUsers}">
							<bright:cmsWrite identifier="login-page-copy-external-users" filter="false"/>
						</c:when>
						<c:otherwise>
							<bright:cmsWrite identifier="login-page-copy" filter="false"/>					
						</c:otherwise>
					</c:choose>
					
					<logic:present name="loginForm"> 
						<logic:equal name="loginForm" property="loginFailed" value="true">
							<div class="error"><bright:cmsWrite identifier="snippet-incorrect-login-details" filter="false"/></div>
						</logic:equal>
						<logic:equal name="loginForm" property="accountSuspended" value="true">
							<div class="error">
								<bright:cmsWrite identifier="snippet-account-suspended" filter="false"/>
							</div>
						</logic:equal>
						<logic:equal name="loginForm" property="hasErrors" value="true"> 
							<div class="error">
								<logic:iterate name="loginForm" property="errors" id="errorText">
									<bright:writeError name="errorText" /><br />
								</logic:iterate>
			            	</div> 
						</logic:equal>
					</logic:present>
					
					<html:form action="/login" method="post" styleClass="floated clearfix" styleId="loginForm">
						<logic:notPresent parameter="forwardUrl">
							<html:hidden name="loginForm" property="forwardUrl" />
						</logic:notPresent>	
						<logic:present parameter="forwardUrl">
							<c:if test="${not empty forwardUrl}">
								<input type="hidden" name="forwardUrl" value="<bean:write name="forwardUrl" />"/>
							</c:if>
						</logic:present>
							
						<html:hidden name="loginForm" property="loggedOut" />
	
						<label for="username"><bright:cmsWrite identifier="label-username" filter="false"/></label>
						<html:text styleClass="text" styleId="username" property="username" maxlength="50" /><br />
						
						<label for="password"><bright:cmsWrite identifier="label-password" filter="false"/></label> 
						<html:password styleClass="text" styleId="password" property="password" maxlength="50" redisplay="false" /><br />
						
						<logic:equal name="showConditionsOnLogin" value="true">	
							<span class="empty">&nbsp;</span>
							<html:checkbox property="conditionsAccepted" styleId="accept" styleClass="checkbox"/> 						
							<label for="accept" class="after" style="padding-top:0"><bright:cmsWrite identifier="label-agree-to-terms" filter="false"/></label><br />
						</logic:equal>
						
						<logic:equal name="allowAutoLoginUsingCookie" value="true">	
							<label>&nbsp;</label>
							<label class="after"><html:checkbox styleClass="checkbox" property="setCookieForAutoLogin" /><bright:cmsWrite identifier="label-auto-login" filter="false"/></label> <br />
						</logic:equal>
						
						<label>&nbsp;</label><html:submit styleClass="button flush" property="submit"><bright:cmsWrite identifier="button-login" filter="false" /></html:submit><br />
					
					</html:form>


					
					<c:if test="${showForgottenPassword || showSSOLink}">
						<div class="hr"></div>
					</c:if>
					
					<c:if test="${showForgottenPassword && !showSSOLink}">
						<p>
							<a href="../action/viewPasswordReminder"><bright:cmsWrite identifier="link-forgot-password" filter="false" /></a>
						</p>
					</c:if>

					<c:if test="${showSSOLink}">
						<p>
							<a href="ssoAuthenticate<c:if test="${not empty forwardUrl}">&forwardUrl=<bean:write name="forwardUrl" filter="false"/></c:if>"><bright:cmsWrite identifier="link-sso-login" filter="false" /> &raquo;</a>
						</p>
					</c:if>
					
					<%@include file="../customisation/login_bottom.jsp"%>
					
				</div>	<!-- end of loginForm -->
		
			</div>	<!-- end of loginPanel -->
	
		</div>
	</div>

</body>
</html>