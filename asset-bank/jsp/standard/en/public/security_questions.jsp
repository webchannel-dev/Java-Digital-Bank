<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>

<head>
	<title><bright:cmsWrite identifier="title-security-questions" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
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

			<logic:present name="changePasswordForm">
				<logic:equal name="changePasswordForm" property="hasErrors" value="true">
					<div class="error">
						<logic:iterate name="changePasswordForm" property="errors" id="error">
							<bright:writeError name="error" /><br />
						</logic:iterate>
					</div>
				</logic:equal>
			</logic:present>
	
			<html:form action="/checkSecurityQuestions" method="post" styleClass="floated">
				<html:hidden name="changePasswordForm" property="user.id"/>
				<html:hidden name="changePasswordForm" property="user.username"/>
				<html:hidden name="changePasswordForm" property="firstSecurityQuestion"/>
				<html:hidden name="changePasswordForm" property="secondSecurityQuestion"/>
				
				
				
				
				<h1><bright:cmsWrite identifier="heading-security-questions" filter="false" /></h1> 
				
				<p><div class="infoInline"><bright:cmsWrite identifier="label-your-username" filter="false" /> <strong><bean:write name="changePasswordForm" property="user.username"/></strong></div> 
				</p>
				
				
				<label style="width:auto; font-weight:normal"><bean:write name="changePasswordForm" property="firstSecurityQuestion"/></label>
				
				<html:text styleClass="text" styleId="answer" property="firstSecurityAnswer" size="16" maxlength="200"/>
				<br />
				
				<label style="width:auto; font-weight:normal; margin-top: 0.5em"><bean:write name="changePasswordForm" property="secondSecurityQuestion"/></label>
				<br />
				<html:text styleClass="text" styleId="answer" property="secondSecurityAnswer" size="16" maxlength="200"/>
				<br />
				
				<div class="hr"></div>
				<input type="submit" class="button flush" id="submitButton" value="<bright:cmsWrite identifier="button-next" filter="false" />"  />
		
				<a href="viewHome" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
				<br />
				
			</html:form>

		</div>
	
		</div>
	</div>
</div>

</body>
</html>