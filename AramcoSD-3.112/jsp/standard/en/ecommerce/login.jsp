<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Steve Bryan		16-Nov-2005		Created.
	d2		Ben Browning	28-Mar-2006		HTML/CSS tidy up
	d3      Matt Woollard       11-Feb-2008     Replaced content with list items
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="ecommerceOfflineOptionName" settingName="ecommerce-offline-option-name"/>
<bright:applicationSetting id="usernameIsEmailaddress" settingName="username-is-emailaddress"/>

<head>
	<title><bright:cmsWrite identifier="e-title-login" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="checkout"/>
</head>

<body id="checkout">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="ecommerceOfflineOptionName" /> <bright:cmsWrite identifier="e-login" filter="false"/></h1> 

	<div class="head">
		<a href="../action/viewCheckout">&laquo; <bright:cmsWrite identifier="e-back-to-checkout" filter="false"/></a>
	</div>	
	

	<logic:equal name="loginForm" property="hasErrors" value="true"> 
		<div class="error">
		<logic:iterate name="loginForm" property="errors" id="errorText">
			<bean:write name="errorText" /><br />
		</logic:iterate>
		</div>
	</logic:equal>
	
	<%@include file="../customisation/user/login_offline_intro.jsp"%>				
	
	<c:if test="${loginForm.loginFailed}">
		<br/>
		<div class="error">
			<bright:cmsWrite identifier="e-login-details-incorrect" filter="false"/>
		</div>
	</c:if>
				
	<c:if test="${loginForm.passwordReminderFailed}">
		<br/>
		<div class="error">
			<bright:cmsWrite identifier="e-reminder-not-sent" filter="false"/>
		</div>
	</c:if>
	<c:if test="${loginForm.passwordReminderSuccess}">
		<br/>
		<div class="confirm">
			<bright:cmsWrite identifier="e-password-remind-sent" filter="false"/>
		</div>
	</c:if>
											
	<fieldset>
	<legend><bright:cmsWrite identifier="e-existing-customers" filter="false"/></legend>
		<html:form action="/offlinePaymentLogin" method="post" style="margin-top:10px;">

		<p><bright:cmsWrite identifier="e-sign-in" filter="false"/></p> 
		<table class="form">
			<tr>
				<th><label for="user" ><bright:cmsWrite identifier="label-username" filter="false"/></label> </th>
				<td><html:text styleClass="text" property="username" size="25" maxlength="50" styleId="user" /></td>
			</tr>
			<tr>
				<th><label for="password"><bright:cmsWrite identifier="label-password" filter="false"/></label></th>
				<td><html:password styleClass="text" property="password" size="25" maxlength="16" redisplay="false" styleId="password" /></td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td><input type="submit" class="button flush" name="login" value="<bright:cmsWrite identifier="e-button-sign-in" filter="false"/>" /></td>
			</tr>
		</table>
		</html:form>
		
		
		<div class="hr"></div>
		<html:form action="/offlinePaymentLogin" method="post">		
		<input type="hidden" name="trans_id" value="<c:out value='${trans_id}' />" />

		<p>
			<bright:cmsWrite identifier="e-login-forgotten-password" filter="false"/>
		</p>			
		
		<table class="form">
			<c:if test="${!usernameIsEmailaddress}">
				<tr>
					<th><label for="username" ><bright:cmsWrite identifier="label-username" filter="false"/></label></th>
					<td><html:text styleClass="text" property="forgottenUsername" size="25" maxlength="50" styleId="username" /></td>
				</tr>
			</c:if>
			<tr>
				<th><label for="email" ><bright:cmsWrite identifier="label-email" filter="false"/></label></th>
				<td><html:text styleClass="text" property="forgottenEmail" size="25" maxlength="50" styleId="email" /> <input type="submit" class="button" name="forgotten" value="<bright:cmsWrite identifier="e-button-password-reminder" filter="false"/>"/></td>
			</tr>
		</table>
		</html:form>	
	</fieldset>
			
									
<%@include file="../inc/body_end.jsp"%>
</body>
</html>