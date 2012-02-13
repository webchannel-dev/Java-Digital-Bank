<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3      Ben Browning   14-Feb-2006    HTML/CSS tidy up
	 d4     Matt Woollard   13-Nov-2007    Added suspended account error
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
<bright:applicationSetting id="forceRemoteAuthentication" settingName="force-remote-authentication"/>

<bean:parameter name="forwardUrl" id="forwardUrl" value=""/>
<c:if test="${empty forwardUrl}"><c:set var="forwardUrl" value="${loginForm.forwardUrl}"/></c:if>


<head>
	<title><bright:cmsWrite identifier="title-login-page" filter="false"/></title> 
	<bean:define id="section" value="login"/>
	<%@include file="../inc/head-elements.jsp"%>
</head>

<body id="loginPage">

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
						<%--  Removed since coming up when forwarded from SSOAuthenticationFilter
						<c:when test="${not empty forwardUrl}">
							<div class="error">
								<bright:cmsWrite identifier="login-as-no-permission" filter="false"/>
							</div>
						</c:when>
						--%>
					</c:choose>
					
					<!-- intro text -->
					<bright:cmsWrite identifier="login-page-copy-sso" filter="false"/>
					
					<logic:present name="loginForm"> 
						<logic:equal name="loginForm" property="loginFailed" value="true">
							<div class="error"><bright:cmsWrite identifier="snippet-incorrect-login-details" filter="false"/></div>
						</logic:equal>
						<logic:equal name="loginForm" property="accountSuspended" value="true">
							<div class="error">
								Sorry, your account has been suspended.
							</div>
						</logic:equal>
						<logic:equal name="loginForm" property="hasErrors" value="true"> 
							<div class="error">
								<logic:iterate name="loginForm" property="errors" id="errorText">
									<bean:write name="errorText" filter="false"/><br />
								</logic:iterate>
			            </div> 
						</logic:equal>
					</logic:present>
					
					
					<html:form action="/ssoLogin" focus="username" method="post" styleClass="floated clearfix">
	
						<logic:notPresent parameter="forwardUrl">
							<html:hidden name="loginForm" property="forwardUrl" />
						</logic:notPresent>	
						<logic:present parameter="forwardUrl">
							<c:if test="${not empty forwardUrl}">
								<input type="hidden" name="forwardUrl" value="<bean:write name="forwardUrl" />"/>
							</c:if>
						</logic:present>

						<%--  openIdPrefix attribute is set in the plugin --%>
						<label for="username" style="width:auto; font-weight: normal">
							<c:if test="${not empty openIdPrefix}">
								<bean:write name="openIdPrefix"/>
							</c:if>
						</label>

						<!-- Not sure if these should apply for SSO login? -->
						<!--  
						<logic:equal name="showConditionsOnLogin" value="true">	
							<span class="empty">&nbsp;</span>
							<html:checkbox property="conditionsAccepted" styleId="accept" styleClass="checkbox"/> 						
							<label for="accept" class="after">I agree to comply with the <a href="../action/viewConditionsPopup?extra=true" class="help-popup" target="_blank" title="View Terms and Conditions in a new window">Terms and Conditions</a> of the use of <bright:cmsWrite identifier="app-name" filter="false" />.</label><br />
						</logic:equal>
						
						<logic:equal name="allowAutoLoginUsingCookie" value="true">	
							<span class="empty">&nbsp;</span>
							<html:checkbox styleClass="checkbox" property="setCookieForAutoLogin" styleId="autoLogin" />						
							<label for="autoLogin" class="after"><bright:cmsWrite identifier="label-auto-login" filter="false"/></label> <br />
						</logic:equal>
						-->
											
						<html:text styleClass="small" styleId="username" property="username" maxlength="200"/>
						<br />
						<html:submit styleClass="button flush" property="submit"><bright:cmsWrite identifier="button-login" filter="false" /></html:submit><br />

					</html:form>
					
					<div class="hr"></div>

					<c:if test="${!forceRemoteAuthentication}">
						<p>
							<a href="viewLogin?local=true<c:if test="${not empty forwardUrl}">&forwardUrl=<bean:write name="forwardUrl" filter="false"/></c:if>">Login using username and password &raquo;</a>
						</p>
					</c:if>
					
				</div>	<!-- end of loginForm -->
		
			</div>	<!-- end of loginPanel -->
	
		</div>
	</div>

</body>
</html>