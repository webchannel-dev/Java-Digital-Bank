<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan		26-Jul-2006		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="usernameIsEmailaddress" settingName="username-is-emailaddress"/>

<bean:define id="userForm" name="subscriptionForm" />



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Subscriptions</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="subscription"/>
	<bean:define id="pagetitle" value="Subscribe"/>
</head>

<body id="profilePage">

	<%@include file="../inc/body_start.jsp"%>

	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 	 

	<logic:present name="subscriptionForm">
		<logic:equal name="subscriptionForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="subscriptionForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<p>
		Please enter your details in the form below. 
	
		<c:if test="${usernameIsEmailaddress}">
			Your email address will be used for your unique username.
		</c:if>		
	</p>
	
	<p>
		If you already have an account, then <a href="../action/viewLogin">log in here</a>. 
	</p>							

	<html:form action="/subscriptionRegister" focus="user.forename" method="post">
		<html:hidden name="userForm" property="user.id"/>
		
		<table class="form" cellpadding="0" cellspacing="0" summary="Subscription form">

			<%@include file="../user-admin/inc_user_fields.jsp"%>
			
		</table>
		
		<div class="hr"></div>
	
		<div class="buttonHolder">
			<input type="submit" class="button" value="Continue &raquo;"> 
		</div>		
	</html:form>
	<form name="cancelForm" action="viewHome" method="get">
		<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button" id="cancelButton">
	</form>
	

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>