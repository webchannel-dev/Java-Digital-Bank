<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		05-Oct-2005		Created.
	 d2		Ben Browning		21-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="useOrgUnits" settingName="orgunit-use" />

<bean:define name="requestUserUpdateForm" property="orgUnitList" id="orgUnits"/>
<bean:size id="numOrgUnits" name="orgUnits" />



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Your Profile</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="profile"/>
	<bean:define id="pagetitle" value="Request More Groups or Permissions"/></head>

<body id="profilePage">

	<%@include file="../inc/body_start.jsp"%>

	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 	 

	<logic:present name="requestUserUpdateForm">
		<logic:equal name="requestUserUpdateForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="requestUserUpdateForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>

	
	<bright:cmsWrite identifier="request-user-permissions-update-intro" filter="false"/>
			
	<p>
		Here are your current details:
	</p>
		
	<table class="form" cellpadding="0" cellspacing="0" summary="Current profile details.">
		<tr>
			<th><bright:cmsWrite identifier="label-username" filter="false"/></th>
			<td class="padded">
				<bean:write name="requestUserUpdateForm" property="user.username" />
			</td>
		</tr>
		<tr>
			<th><bright:cmsWrite identifier="label-email" filter="false"/></th>
			<td class="padded">
				<bean:write name="requestUserUpdateForm" property="user.emailAddress" />
			</td>
		</tr>
		<tr>
			<th><bright:cmsWrite identifier="label-groups" filter="false"/></th>
			<td class="padded">
				<logic:notEmpty name="requestUserUpdateForm" property="user.groups">
					<logic:iterate name="requestUserUpdateForm" property="user.groups" id="userGroup" indexId="i">
						<c:if test="${i > 0}"><br/></c:if>
						<bean:write name="userGroup" property="nameWithOrgUnit" />
					</logic:iterate>
				</logic:notEmpty>
			</td>
		</tr>
		
		<c:if test="${useOrgUnits && numOrgUnits > 0}">
			<tr>
				<th><bright:cmsWrite identifier="label-org-units" filter="false"/></th>
				<td class="padded">
					<logic:notEmpty name="requestUserUpdateForm" property="userOrgUnits">
						<logic:iterate name="requestUserUpdateForm" property="userOrgUnits" id="userOrgUnit" indexId="i">
							<c:if test="${i > 0}"><br/></c:if>
							<bean:write name="userOrgUnit" property="category.name" />
						</logic:iterate>
					</logic:notEmpty>
				</td>
			</tr>
		</c:if>
		
	</table>
	
	<div class="hr"></div>

	<html:form action="/requestUserUpdate" method="post">
		<html:hidden name="requestUserUpdateForm" property="user.id"/>
		
	<p>
		<c:if test="${useOrgUnits && numOrgUnits > 0}">
			Please select the appropriate Organisational Unit from the list below.<br />
		</c:if>
		
		You can enter a message for the administrator in the box provided.
	</p>

	
	<table class="form" cellpadding="0" cellspacing="0" summary="Current profile details.">
			<c:choose>
				<c:when test="${useOrgUnits && numOrgUnits > 0}">
					<tr>
						<th><label for="orgUnit"><bright:cmsWrite identifier="label-org-unit" filter="false"/></label></th>
						<td class="padded">
							<html:select name="requestUserUpdateForm" styleId="orgUnit" property="selectedOrgUnit">
								<option value="0">- None -</option>
								<html:options collection="orgUnits" property="id" labelProperty="category.name"/>
							</html:select>	
						</td>													
					</tr>										
				</c:when>
				<c:otherwise>
					<%-- Leave selected org unit id as default 0 if there are none to choose from --%>
				</c:otherwise>
			</c:choose>

				<tr>
					<th class="textarea">
					<bright:cmsWrite identifier="label-request-notes" filter="false"/>
					</th>
					<td>		
						<html:textarea name="requestUserUpdateForm" property="regInfo" />		
					</td>
				</tr>

			<tr>
				<th>&nbsp;</th>
				<td>
					<input type="submit" class="button flush" id="submitButton" value="Send Request &raquo;">
				</td>
			</tr>
		</table>
	</html:form>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>