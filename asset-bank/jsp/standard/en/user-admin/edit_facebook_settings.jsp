<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Facebook Admin</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="facebook"/>
	<bean:define id="pagetitle" value="Facebook"/>
	<bean:define id="pagesubtitle" value="Edit 'like' button settings"/>
	
</head>
<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<h2><bean:write name="pagesubtitle" /></h2> 


	<logic:present name="facebookForm">
		<logic:equal name="facebookForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="facebookForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>


	<html:form action="saveFacebookAdmin" method="post" styleClass="wideLabel">

		
		<table cellspacing="0" border="0" class="form" summary="facebook" >
			<tr>
				<th>Enabled:</th>
				<td><html:checkbox styleClass="checkbox" name="facebookForm" property="likeButtonSettings.enabled" /></td>
			</tr>

			<tr>
				<th>Show Faces:</th>
				<td><html:checkbox styleClass="checkbox" name="facebookForm" property="likeButtonSettings.showFaces" /></td>
			</tr>

			<tr>
				<th>Show Send button:</th>
				<td><html:checkbox styleClass="checkbox" name="facebookForm" property="likeButtonSettings.showSendButton" /></td>
			</tr>
			
			<tr>
				<th>Button Layout:</th>
				<td>
					<html:select styleId="buttonLayoutId" name="facebookForm" property="buttonLayoutId" >
						<option value="0">-- <bright:cmsWrite identifier="snippet-please-select" filter="false" /> --</option>
						<logic:iterate name="buttonLayoutList" id="buttonLayoutElement">
								<option value="<bean:write name='buttonLayoutElement' property='id'/>" <c:if test="${facebookForm.likeButtonSettings.buttonLayout.id == buttonLayoutElement.id}">selected</c:if>>
									<bean:write name='buttonLayoutElement' property='name'/>
								</option>
						</logic:iterate>
					</html:select>
				</td>
			</tr>

			<tr>	
				<th>Verb To Display:</th>
				<td>		
					<html:select styleId="verbToDisplayId" name="facebookForm" property="verbToDisplayId" >
						<option value="0">-- <bright:cmsWrite identifier="snippet-please-select" filter="false" /> --</option>
						<logic:iterate name="verbToDisplayList" id="verbToDisplayElement">
								<option value="<bean:write name='verbToDisplayElement' property='id'/>" <c:if test="${facebookForm.likeButtonSettings.verbToDisplay.id == verbToDisplayElement.id}">selected</c:if>>
									<bean:write name='verbToDisplayElement' property='name'/>
								</option>
						</logic:iterate>
					</html:select>
				</td>
			</tr>
			
			<tr>	
				<th>Facebook account: <span class="required">*</span></th>
				<td>
					<table cellspacing="0" border="0">


						<tr>
							<c:set var="helpText" value="A comma-separated list of the Facebook IDs of page administrators. At a minimum, include only your own Facebook ID." scope="request"/>
							<td><label for="fbAdminIds" class="above">Facebook Admin ID(s):<jsp:include page="../asset-admin/inc_attribute_help.jsp"/></label>
								<html:text styleClass="text" styleId="fbAdminIds" name="facebookForm" property="likeButtonSettings.fbAdminIds" size="16" maxlength="500"/></td>
							<td style="width: 80px; vertical-align: middle; text-align: center"><strong>OR</strong></td>
							<c:set var="helpText" value="A Facebook Platform application ID." scope="request"/>
							<td><label for="fbAppId" class="above">Facebook Application ID:<jsp:include page="../asset-admin/inc_attribute_help.jsp"/></label><html:text styleClass="text" styleId="fbAppId" name="facebookForm" property="likeButtonSettings.fbApplicationId" size="16" maxlength="500"/></th>
						</tr>

					</table>
					

					
				</td>
			</tr>		
					
					
		</table>
		<p> <span class="required">*</span> <em>Please insert either the Facebook IDs of page administrators OR a Facebook Platform application ID.</em></p>

		<div class="hr"></div>
		
		<input type="submit" class="button flush floated" id="submitButton" value="<bright:cmsWrite identifier="button-save" filter="false" />"  />
		<a href="viewFacebookAdmin" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		<br />
	</html:form>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>