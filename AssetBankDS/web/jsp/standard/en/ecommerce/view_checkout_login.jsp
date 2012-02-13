<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Steve Bryan		08-Aug-2005		Created by splitting original checkout.
	d2      Matt Woollard       11-Feb-2008     Replaced content with list items
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%@include file="inc_checkout_settings.jsp"%>

<head>
	<title><bright:cmsWrite identifier="e-title-checkout" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="checkout"/>
</head>

<body id="checkout">
	<%@include file="../inc/body_start.jsp"%>
	<%@include file="../customisation/payment_gateway_logo.jsp"%>
	
	<h1><bright:cmsWrite identifier="e-heading-checkout" filter="false"/></h1> 
	
	<div class="head">
			<a href="../action/viewAssetBox"><bright:cmsWrite identifier="link-back-lightbox" filter="false" /></a>
	</div>	
	<%@include file="../customisation/credit_card_logos.jsp"%>
	
	<logic:empty  name="checkoutForm" property="approvalList">
		<p><bright:cmsWrite identifier="e-no-items" filter="false"/></p>	
		<c:if test="${userprofile.isLoggedIn}">
			<p>
				<bright:cmsWrite identifier="e-review-purchases" filter="false"/>
			</p>
		</c:if>
	</logic:empty>

	<logic:notEmpty name="checkoutForm" property="approvalList">
		
		<%@include file="inc_checkout_basket_summary.jsp"%>

		<h2><bright:cmsWrite identifier="e-subhead-login-register" filter="false"/></h2>
		<p>
			<bright:cmsWrite identifier="e-login-register" filter="false"/>
		</p>
		
		<c:if test="${checkoutForm.loginFailed}">
			<br/>
			<div class="error">
				<bright:cmsWrite identifier="e-login-error" filter="false"/>
			</div>
		</c:if>
		
		<c:if test="${checkoutForm.usernameExists}">
			<br/>
			<div class="error">
				<bright:cmsWrite identifier="e-email-exists-error" filter="false"/>
			</div>
			<p>
				<bright:cmsWrite identifier="e-forgotten-password" filter="false"/>
			</p>
		</c:if>
		<c:if test="${checkoutForm.invalidEmailFormat}">
			<br/>
			<div class="error">
				<bright:cmsWrite identifier="e-email-format-error" filter="false"/>
			</div>
		</c:if>
		
		<c:if test="${!checkoutForm.usernameExists && !checkoutForm.invalidEmailFormat}">			
			<c:if test="${checkoutForm.registerEmailsDontMatch}">
				<br/>
				<div class="error">
					<bright:cmsWrite identifier="e-emails-do-not-match" filter="false"/>
				</div>
			</c:if>
		</c:if>
				
		<c:if test="${checkoutForm.passwordReminderFailed}">
			<br/>
			<div class="error">
				<bright:cmsWrite identifier="e-reminder-not-sent-at-checkout" filter="false"/>
			</div>
		</c:if>
		<c:if test="${checkoutForm.passwordReminderSuccess}">
			<br/>
			<div class="error">
				<bright:cmsWrite identifier="e-reminder-sent" filter="false"/>
			</div>
		</c:if>
					
		<fieldset>
		<legend><bright:cmsWrite identifier="e-heading-new-customers" filter="false"/></legend>
			<html:form action="/loginCheckout" method="post" style="margin-top:10px;">				
			<p><bright:cmsWrite identifier="e-enter-email" filter="false"/></p>			
			<table class="form">
				<tr>
					<th><label for="email"><bright:cmsWrite identifier="label-email" filter="false"/></label> </th>
					<td><html:text styleClass="text" property="registerEmailAddress" size="25" maxlength="50" styleId="email" /></td>
				</tr>
				<tr>
					<th><label for="confirmemail"><bright:cmsWrite identifier="label-confirm-email" filter="false"/></label></th>
					<td><html:text styleClass="text" property="confirmRegisterEmailAddress" size="25" maxlength="50" styleId="confirmemail" /></td>
				</tr>
				<tr>
					<th>&nbsp;</th>
					<td><input type="submit" class="button flush" name="register" value="<bright:cmsWrite identifier="button-register" filter="false"/>"/></td>
				</tr>
			</table>
			</html:form>
		</fieldset>			

				
		<fieldset>
		<legend><bright:cmsWrite identifier="e-existing-customers" filter="false"/></legend>
			<html:form action="/loginCheckout" method="post" style="margin-top:10px;">
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
			<html:form action="/loginCheckout" method="post">		
			<p>
				<bright:cmsWrite identifier="e-login-forgotten-password" filter="false"/>
			</p>			
			
			<table class="form">
				<tr>
					<th><label for="username" ><bright:cmsWrite identifier="label-username" filter="false"/></label></th>
					<td><html:text styleClass="text" property="forgottenUsername" size="25" maxlength="50" styleId="username" /> <input type="submit" class="button" name="forgotten" value="<bright:cmsWrite identifier="e-button-password-reminder" filter="false"/>"/></td>
				</tr>
			</table>
			</html:form>	
		</fieldset>
						
		<br/>
		
	</logic:notEmpty>
					
<%@include file="../inc/body_end.jsp"%>
</body>
</html>