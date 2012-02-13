<%@include file="../inc/doctype_html.jsp" %>


<%-- History:
	 d1		Tamora James	29-Jul-2005		Created.
	 d2      Ben Browning   14-Feb-2006    HTML/CSS tidy up
	 d3      Matt Woollard       11-Feb-2008     Replaced content with list items
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="useDivisions" settingName="users-have-divisions" />
<bright:applicationSetting id="useOrgUnits" settingName="orgunit-use" />
<bright:applicationSetting id="ecommerce" settingName="ecommerce" />

<head>
	
	<title><bright:cmsWrite identifier="e-title-register" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="checkout"/> 
</head>

<%-- Add reference to form called userForm --%>
<bean:define id="userForm" name="registerForm" />

<body id="profilePage" lang="en">

	<%@include file="../inc/body_start.jsp"%>

	<h1 class="underline"><bright:cmsWrite identifier="e-heading-registration" filter="false"/></h1> 	 		
					
			<%@include file="../customisation/user/register_offline_intro.jsp"%>
			
			<p><bright:cmsWrite identifier="snippet-req-fields" filter="false"/></p>

			<logic:present name="userForm">
			<logic:equal name="userForm" property="hasErrors" value="true"> 
				<div class="error">
					<logic:iterate name="userForm" property="errors" id="errorText">
						<bean:write name="errorText" /><br />
					</logic:iterate>
				</div>
			</logic:equal>
			</logic:present>
	
			<html:form action="offlineRegister" method="post" focus="user.forename">
			
				<table class="form" cellspacing="0" cellpadding="0" summary="Registration form">

					<%-- Enforce strict validation for offline purchase --%>
					<c:set var="strictValidation" value="true"/>
					<%@include file="../user-admin/inc_user_fields.jsp" %>
					
				</table>
				
				<div class="hr"></div>
				
				<div style="text-align:right">
					<input type="submit" class="button" value="<bright:cmsWrite identifier="button-next" filter="false" />" /> 
				</div>
				
			</html:form>
			
	<form name="cancelForm" action="viewCheckout" method="get">
		<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button" id="cancelButton">
	</form>
          
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>