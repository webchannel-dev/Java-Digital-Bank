<%@include file="../inc/doctype_html.jsp" %>


<%-- History:
	 d1		Tamora James	29-Jul-2005		Created.
	 d2      Ben Browning   14-Feb-2006    HTML/CSS tidy up
	 d3     Matt Woollard   12-Feb-2009    Added additional user approval fields
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="useOrgUnits" settingName="orgunit-use" />
<bright:applicationSetting id="hideOrgUnitsSelection" settingName="org-unit-hide-selection-on-registration-page" />
<bright:applicationSetting id="useBrands" settingName="multiple-brands" />
<bright:applicationSetting id="ecommerce" settingName="ecommerce" />
<bright:applicationSetting id="showForgottenPassword" settingName="show-forgotten-password"/>
<bright:applicationSetting id="usernameIsEmailaddress" settingName="username-is-emailaddress"/>
<bright:applicationSetting id="showConditionsOnRegister" settingName="showConditionsOnRegister"/>
<bright:applicationSetting id="useRegMessage" settingName="users-have-reg-message" />
<bright:applicationSetting id="showAddressFields" settingName="users-register-with-address" />
<bright:applicationSetting id="useTitle" settingName="users-have-title" />
<bright:applicationSetting id="useTaxNumber" settingName="users-have-tax-number" />
<bright:applicationSetting id="marketingGroupsEnabled" settingName="marketing-groups-enabled" />
<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage" />
<bright:applicationSetting id="additionalUserApprovalEnabled" settingName="additional-user-approval-enabled" />
<bright:applicationSetting id="automaticBrandRegistration" settingName="automatic-brand-registration"/>
<bright:applicationSetting id="localUserEmailDomain" settingName="local-registration-email-domain"/>
<bright:applicationSetting id="show-captcha-on-registration" settingName="show-captcha-on-registration"/>

<head>
	<title><bright:cmsWrite identifier="title-register" filter="false"/></title> 
	<bean:define id="section" value="login"/>
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/JavaScript">

	// give the username field the focus once the dom is ready
	$j(function () {
		$j('#name').focus();
	});
	
	function switchCustomFields (a_sOrgUnitId)
	{
		var elementSet = document.getElementsByTagName("body")[0].getElementsByTagName("*");
		for (i=0; i<elementSet.length; i++)
		{
			var element = elementSet[i];
			//alert("ID: "+element.id);
			if ((element.id.indexOf("fieldRow_"+a_sOrgUnitId) >= 0) ||
				(element.id.indexOf("fieldRow_0") >= 0))
			{
				element.style.display = '';
			}
			else if (element.id.indexOf("fieldRow") >= 0)
			{
				element.style.display = 'none';
			}
		}
	}

	</script>
</head>

<%-- Check whether the registration page has been used for update user profile --%>
<logic:notPresent name="UpdateProfile">
	<bean:define id="UpdateProfile" value="false" />
</logic:notPresent>

<c:choose>
	<c:when test="${!UpdateProfile}">
		<bean:define id="userForm" name="registerForm" />
		<bean:parameter id="headingText" name="headingText" value="heading-register" />
		<bean:parameter id="formAction" name="formAction" value="registerUser" />
		<bean:parameter id="buttonText" name="buttonText" value="button-register" />
	</c:when>
	<c:otherwise>
		<bean:define id="userForm" name="changeProfileForm" />
		<bean:parameter id="headingText" name="headingText" value="heading-update-profile" />
		<bean:parameter id="formAction" name="formAction" value="updateProfile" />
		<bean:parameter id="buttonText" name="buttonText" value="button-save-continue" />
	</c:otherwise>
</c:choose>

<bean:parameter id="orgUnitId" name="orgUnitId" value="0"/>


<body id="registrationPage" lang="en" <c:if test="${orgUnitId > 0}">onload="switchCustomFields('<c:out value="${orgUnitId}"/>');"</c:if>>

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
			
			<h1>
				<bright:cmsWrite identifier="${headingText}" filter="false" />
				<c:if test="${not empty param.externalUser}">
					<bright:cmsWrite identifier="snippet-as-external-user" filter="false" />
				</c:if>
			</h1> 
			
			<%@include file="../customisation/user/register_intro.jsp"%>

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

			
			<html:form action="${formAction}" method="post">
				<input type="hidden" name="mandatory_user.forename" value="Please enter your forename." />
				<input type="hidden" name="mandatory_user.surname" value="Please enter your surname." />
				<input type="hidden" name="mandatory_user.emailAddress" value="Please enter your email address." />
				<input type="hidden" name="externalUser" value="<c:out value="${param.externalUser}"/>" />
				
				<html:hidden name="userForm" property="user.invitedByUserId"/>
				<c:if test="${UpdateProfile}">
					<html:hidden name="userForm" property="user.id"/>
				</c:if>
				<table class="form" cellspacing="0" cellpadding="0" summary="Registration form">
					
					
				<c:if test="${useTitle}">																
					<tr>
						<th><label for="title"><bright:cmsWrite identifier="label-title" filter="false"/></label></th>
						<td colspan="2">
							<html:text styleClass="text" styleId="title" name="userForm" property="user.title" size="16" maxlength="200"/>
						</td>													
					</tr>
				</c:if>
					
					<tr>
						<th><label for="name"><bright:cmsWrite identifier="label-forename" filter="false"/></label><span class="required">*</span> </th>
						<td colspan="2">
							<html:text styleClass="text" styleId="name" name="userForm" property="user.forename" maxlength="100"/>
						</td>
					</tr>
					<tr>
						<th><label for="surname"><bright:cmsWrite identifier="label-surname" filter="false"/></label><span class="required">*</span> </th>
						<td colspan="2">
							<html:text styleClass="text" styleId="surname" name="userForm" property="user.surname" maxlength="100"/>
						</td>
					</tr>
				
				<c:if test="${!usernameIsEmailaddress || !UpdateProfile}">	
					<tr>
						<th><label for="email"><bright:cmsWrite identifier="label-email" filter="false"/></label><span class="required">*</span> </th>
						<td colspan="2">
							<c:choose>
								<c:when test="${ param.externalUser || useBrands || empty localUserEmailDomain }">
									<html:text styleClass="text" styleId="email" name="userForm" property="user.emailAddress" maxlength="255"/>
								</c:when>
								<c:otherwise>
									<html:text styleClass="text" style="width: 100px !important; text-align: right" styleId="email" name="userForm" property="user.emailAddress" maxlength="150"/>
									@<bean:write name="localUserEmailDomain"/>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:if>
				
				<c:if test="${!usernameIsEmailaddress && !UpdateProfile}">
					<tr>
						<th><label for="username"><bright:cmsWrite identifier="label-username"/></label><span class="required">*</span> </th>
						<td colspan="2">
							<input type="hidden" name="mandatory_user.username" value="Please enter a username." />
							<html:text styleClass="text" styleId="username" name="userForm" property="user.username" maxlength="100"/>
						</td>
					</tr>					
				</c:if>

				<%@include file="../user-admin/inc_user_job_fields.jsp"%>
				
				<c:if test="${showAddressFields}">
					<%@include file="../user-admin/inc_user_address_fields.jsp"%>					
				</c:if>
				<c:if test="${!UpdateProfile}">
					<bean:define name="userForm" property="orgUnitList" id="orgUnits"/>
					<bean:size id="numOrgUnits" name="orgUnits" />
					<c:choose>
						<c:when test="${useOrgUnits && numOrgUnits > 0 && !hideOrgUnitsSelection}">
							<tr>
								<th><label for="orgUnit"><bright:cmsWrite identifier="label-org-unit"/></label></th> 
								<td colspan="2">
									<html:select name="userForm" styleId="orgUnit" property="selectedOrgUnit" onchange="switchCustomFields(this.value);">
										<option value="0">- None -</option>
										<logic:iterate name="orgUnits" id="orgUnit">
											<option value="<bean:write name='orgUnit' property='id'/>" <c:if test="${orgUnitId == orgUnit.id}">selected</c:if>><bean:write name='orgUnit' property='category.name'/></option>
										</logic:iterate>
									</html:select>	
								</td>													
							</tr>										
						</c:when>
						<c:otherwise>
							<%-- Leave selected org unit id as default 0 if there are none to choose from --%>
						</c:otherwise>
					</c:choose>	
				</c:if>
				<c:if test="${!(useBrands && automaticBrandRegistration) && !UpdateProfile}">
					<bean:define name="userForm" property="registerGroupList" id="groups"/>
					<bean:size id="numGroups" name="groups" />
					<c:choose>
						<c:when test="${useBrands && numGroups > 0}">
							<tr>
								<th><label for="group">Group:</label><span class="required">*</span></th>
								<td colspan="2">								
									<html:select name="userForm" styleId="group" property="requestedGroupId">
										<option value="0">- Please select -</option>
										<html:options collection="groups" property="id" labelProperty="name"/>
									</html:select>									
								</td>													
							</tr>										
						</c:when>
						<c:otherwise>
							<%-- Leave selected group id as default 0 if there are none to choose from --%>
						</c:otherwise>
					</c:choose>
				</c:if>
						
				<c:if test="${useTaxNumber}">																
					<tr>
						<th><label for="taxnumber"><bright:cmsWrite identifier="label-tax-number" filter="false"/></label></th>
						<td colspan="2">
							<html:text styleClass="text" styleId="taxnumber" name="userForm" property="user.taxNumber" size="16" maxlength="100"/>
						</td>													
					</tr>
				</c:if>

				<c:if test="${supportMultiLanguage}">	
				<tr>
					<bean:define name="userForm" property="languages" id="languages"/>
					
							<th><label for="language"><bright:cmsWrite identifier="label-language" filter="false"/></label><span class="required">*</span></th>
							<td colspan="2">
								<html:select name="userForm" styleId="language" property="user.language.id" style="width:auto;">
									<html:options collection="languages" property="id" labelProperty="nativeName"/>
								</html:select>
								<c:if test="${marketingGroupsEnabled}">
									<input type="hidden" id="reloadPage" name="_reloadPage_" value="true"/>
									<html:submit styleClass="button" style="margin-left:0px;" styleId="reloadButton" property="reloadPage"><bright:cmsWrite identifier="button-go" filter="false"/></html:submit>
									<script type="text/javascript">
										function changeLanguage()
										{
											document.getElementById('reloadPage').name='reloadPage';
											this.form.submit();
										}
										document.getElementById('language').onchange=changeLanguage;
										document.getElementById('language').style.width='';
										document.getElementById('reloadButton').style.display='none';
										document.getElementById('buttonText').style.display='none';
									</script>
								</c:if>
							</td>																										
				</tr>
				</c:if>

				<c:set var="isCustomFieldSubtype" value="${param.externalUser}"/>
				<logic:notEmpty name="userForm" property="customFields">
					<bean:define id="customFields" name="userForm" property="customFields"/>
					<logic:notEmpty name="userForm" property="user.customFieldValues">
						<bean:define id="selectedValueSet" name="userForm" property="user.customFieldValues"/>
					</logic:notEmpty>
					<bean:define id="registering" value="true"/>
					<%@include file="../inc/custom_fields.jsp"%>
				</logic:notEmpty>
				
				
				
				<c:if test="${additionalUserApprovalEnabled}">	
				</table>
				
				<bright:cmsWrite identifier="user-to-verify-with" filter="false"/><br/>
				
				<table class="form" cellspacing="0" cellpadding="0" summary="Registration form">															
					<tr>
						<th><label for="verifyName"><bright:cmsWrite identifier="label-name" filter="false"/><span class="required">*</span></label></th>
						<td colspan="2">
							<html:text styleClass="text" styleId="verifyName" name="userForm" property="verifyName" size="16" maxlength="100"/>
						</td>													
					</tr>
					<tr>
						<th><label for="verifyEmail"><bright:cmsWrite identifier="label-email" filter="false"/><span class="required">*</span></label></th>
						<td colspan="2">
							<html:text styleClass="text" styleId="verifyEmail" name="userForm" property="verifyEmail" size="16" maxlength="100"/>
						</td>													
					</tr>					
				</c:if>
				
				
				
				
				<c:if test="${useRegMessage}">
					<tr>
						<th><label for="intendedUse"><bright:cmsWrite identifier="label-user-reg-message" filter="false"/></label><span class="required">*</span></th>
						<td colspan="2">
						<html:textarea name="userForm" property="user.registrationInfo" rows="3" cols="40"></html:textarea>
						</td>								
					</tr>
					<input type="hidden" name="mandatory_user.registrationInfo" value="Please enter a value for <bright:cmsWrite identifier="label-user-reg-message" filter="false"/>." />
					<input type="hidden" name="maxlength_user.registrationInfo" value="500" />
					<input type="hidden" name="maxlengthmessage_user.registrationInfo" value="The <bright:cmsWrite identifier="label-user-reg-message" filter="false"/> field can contain no more than 500 characters." />
				</c:if>	
				
				<c:if test="${marketingGroupsEnabled}">				
					<c:set var="marketingGroupForm" value="${userForm}"/>
					<%@include file="../inc/inc_marketing_group_subscription.jsp"%>
				</c:if>
					
				<c:if test="${showConditionsOnRegister}">
					<tr>
						<th>&nbsp;</th>
						<td class="checkbox">
							<html:checkbox name="userForm" property="conditionsAccepted" styleId="accept" styleClass="checkbox"/> 
						</td>
						<td>	
							<label for="accept"><bright:cmsWrite identifier="snippet-register-tsandcs" filter="false"/></label>
						</td>
					</tr>
				</c:if>
				
				<c:if test="${!UpdateProfile}">
					<logic:equal name="show-captcha-on-registration" value="true">
						<tr>
							<th style="padding-top:10px;"><bright:cmsWrite identifier="label-captcha-security-check"/></th>
							<td colspan="2" style="padding-top:10px;">
								
								<img src="../captchaImg" style="width:203px; margin-bottom:0.4em; display:block;"/> 
								<label for="capAnswer"><bright:cmsWrite identifier="snippet-enter-captcha-letters" filter="false"/></label>
								<input name="capAnswer" id="capAnswer" style="margin-top:0.4em;"/>
							</td>
						</tr>
					</logic:equal> 
				</c:if>
				              
               <tr>
                  <td style="padding-top:10px;">&nbsp;</td>
                  <td colspan="2" style="padding-top:10px;">
                  			
							<input class="button flush floated" type="submit" value="<bright:cmsWrite identifier="${buttonText}" filter="false" />"  />
							<a href="../action/viewHome" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
                  </td>
                  
               </tr>   
				</table>
				
				<%@include file="../customisation/inc_profile_custom.jsp"%>
				
				</html:form>
				
					<c:if test="${!UpdateProfile}">
						<c:if test="${showForgottenPassword}">	
							<div class="hr"></div>
							<p>
								<a href="../action/viewPasswordReminder"><bright:cmsWrite identifier="link-forgot-password" filter="false" /></a>
							</p>
						</c:if>
					</c:if>

					<%@include file="../customisation/register_bottom.jsp"%>
		        </div>    <!-- End of loginForm -->
		   
		   </div>   <!-- end of loginPanel -->
		</div>
	</div>
</body>
</html>
