<%@include file="../inc/doctype_html.jsp" %>


<%-- History:
	 d1		James Home		02-Feb-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design
	 d3    	Ben Browning   20-Feb-2006    HTML/CSS tidy up	
	 d4		Matt Woollard	03-Apr-2009		Made fields mandatory
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="useDivisions" settingName="users-have-divisions" />

<head>
	<title><bright:cmsWrite identifier="title-request-cd" filter="false"/></title>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="lightbox"/>
	
</head>

<body id="contact">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-request-cd" filter="false"/></h1> 

	<bright:cmsWrite identifier="intro-request-cd" filter="false"/>
	
	<logic:present name="requestAssetBoxForm">
	<logic:equal name="requestAssetBoxForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="requestAssetBoxForm" property="errors" id="errorText">
				<bean:write name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
	</logic:present>

	<form action="../action/requestAssetBox" method="post" name="contactForm">
	 	<input type="hidden" name="mandatory_name" value="<bright:cmsWrite identifier="failedValidationForName" filter="false"/>" />
		
		<h3><bright:cmsWrite identifier="subhead-your-details" filter="false"/></h3>

		<table class="form" cellspacing="0" cellpadding="0" summary="Form to request your <bright:cmsWrite identifier="a-lightbox" filter="false"/> on a cd">
			<tr>
				<th><label for="name"><bright:cmsWrite identifier="label-name" filter="false"/></label><span class="required">*</span></th>
				<td>
					<logic:equal name="userprofile" property="isLoggedIn" value="true">
						<input type="text" class="text" id="name" name="name" size="20" maxlength="100" value="<bean:write name='userprofile' property='user.fullName'/>" />
					</logic:equal>
					<logic:equal name="userprofile" property="isLoggedIn" value="false">
						<input type="text" class="text" name="name" id="name" size="20" maxlength="100" />
					</logic:equal>
				</td>			
			</tr>
			
		<c:if test="${useDivisions}">
			<input type="hidden" name="mandatory_divisionId" value="Please enter your division." />
			<tr>
				<th><label for="division"><bright:cmsWrite identifier="label-division" filter="false"/></label><span class="required">*</span></th>
				<td>
					<select id="division" name="divisionId">
							<option value="">- None -</option>
						<logic:iterate name="requestAssetBoxForm" property="divisionList" id="division">
							<option value="<bean:write name='division' property='id'/>"
								<c:if test="${division.id == userprofile.user.refDivision.id}">selected="selected"</c:if>
								>
								<bean:write name='division' property='description'/>
							</option>
							
						</logic:iterate>					
					</select>
					
				</td>
			</tr>
		</c:if>
		
			<tr>
				<th><label for="address"><bright:cmsWrite identifier="label-cd-address" filter="false"/><span class="required">*</span></label></th>
				<td>
					<input type="hidden" name="mandatory_address" value="<bright:cmsWrite identifier="failedValidationForAddress" filter="false"/>" />
					<textarea class="text" name="address" id="address" rows="4" cols="50"></textarea>
				</td>
			</tr>
			<tr>
				<th><label for="email"><bright:cmsWrite identifier="label-email" filter="false"/></label><span class="required">*</span></th>
				<input type="hidden" name="mandatory_address" value="Please enter your email address." />
				<td>
					<logic:equal name="userprofile" property="isLoggedIn" value="true">
						<input type="text" class="text" name="email" id="email" size="20" maxlength="100" value="<bean:write name='userprofile' property='user.emailAddress'/>">
					</logic:equal>
					<logic:equal name="userprofile" property="isLoggedIn" value="false">
						<input type="text" class="text" name="email" id="email" size="20" maxlength="100">
					</logic:equal>
				</td>
			</tr>
			<tr>
				<th><label for="customfield"><bright:cmsWrite identifier="label-cd-custom-field" filter="false"/>:</label><span class="required">*</span></th>
				<input type="hidden" name="mandatory_customField" value="<bright:cmsWrite identifier="failedValidationForCDCustomField" filter="false"/>" />
				<td>
					<input type="text" class="text" id="customField" name="customField" size="20" maxlength="100" value="">
				</td>
			</tr>
			<%@include file="../inc/request_on_cd_custom_fields.jsp"%>
			<tr>
				<th><label for="comments"><bright:cmsWrite identifier="label-add-comments" filter="false"/></label></th>
				<td>
					<textarea name="message" rows="4" cols="50" id="comments"></textarea>
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<input type="submit" class="button flush" id="submitButton" value="<bright:cmsWrite identifier="button-send" filter="false" />" />
				</td>
			</tr>
		</table>

		
	</form>
	<script type="text/javascript" language="JavaScript">
	  <!--
	  var focusControl = document.forms["contactForm"].elements["name"];
	
	  if (focusControl.type != "hidden") {
	     focusControl.focus();
	  }
	  // -->
	</script>							
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>