<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		27-Aug-2007		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="multipleLightboxes" settingName="multiple-lightboxes"/>
<bright:applicationSetting id="marketingGroupsEnabled" settingName="marketing-groups-enabled"/>
<bright:applicationSetting id="emailUsersOnShare" settingName="email-user-upon-lightbox-share"/>
<bright:applicationSetting id="usersHaveStructuredAddress" settingName="users-have-structured-address"/> 



<head>
	
	<title><bright:cmsWrite identifier="title-share-lightbox" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="lightbox"/>
	<c:set var="helpsection" value="share-lightbox-add-user"/>
	<bean:define id="tabId" value="manageAssetBoxes"/>
	<style type="text/css">
		td,th { padding-right: 7px; }
	</style>
	<script type="text/javascript">

		function showHideEmailFields()
		{
			if(document.getElementById('sendEmail'))
			{
				if(document.getElementById('sendEmail').checked)
				{
					document.getElementById('emailFields').style.display='block';
				}
				else
				{
					document.getElementById('emailFields').style.display='none';
				}
			}
		}

		function doFormSubmit(ctrl)
		{
			var oldAction = ctrl.form.action;
			var oldTarget = ctrl.form.target;
			ctrl.form.action='../action/viewAddAssetboxUsersEmailPreview'; 
			ctrl.form.target='_blank'; 
			ctrl.form.submit();
			ctrl.form.action=oldAction;
			ctrl.form.target=oldTarget;
		}

		// give the forename field the focus once the dom is ready
		$j(function () {
			$j('#forename').focus();
		});
	</script>
</head>
<body onLoad="showHideEmailFields();">
	<%@include file="../inc/body_start.jsp"%>
	
	<%@include file="inc_lightbox_tabs.jsp"%>

	<logic:present name="shareAssetBoxForm">
		<logic:equal name="shareAssetBoxForm" property="hasErrors" value="true"> 
    		<div class="error">
    			<logic:iterate name="shareAssetBoxForm" property="errors" id="errorText">
    				<bean:write name="errorText" filter="false"/><br />
    			</logic:iterate>
    		</div>
    	</logic:equal>
	</logic:present>
         
	<c:set var="lightboxName" value="${shareAssetBoxForm.name}" />
	<bright:cmsWrite identifier="snippet-share-lb-other-users" filter="false" replaceVariables="true" />
	
	<div class="hr"></div>
	
	<c:set var="findUsersForm" value="${shareAssetBoxForm}" />
	<form action="<c:out value='${findUsersAction}' />" method="post">
	
		<input type="hidden" name="assetBoxId" value="<bean:write name='shareAssetBoxForm' property='assetBoxId'/>" />
		<input type="hidden" name="name" value="<bean:write name='shareAssetBoxForm' property='name'/>" />
		
		<%@include file="../inc/find-users-form.jsp" %>

		<input type="submit" class="button flush" value="Find &raquo;" />
		
	</form>

	<c:if test="${not empty shareAssetBoxForm.users}">
		<div class="hr"></div>
		<h3><bright:cmsWrite identifier="heading-matching-users" filter="false"/></h3>
	</c:if>
	<bean:define id="noSearch" value="false"/>
	<logic:empty name="shareAssetBoxForm" property="users">
		<logic:equal name="shareAssetBoxForm" property="searchCriteria.empty" value="false">
			<div class="hr"></div>
			<p><bright:cmsWrite identifier="snippet-no-users-found" filter="false"/></p>
		</logic:equal>
		<logic:equal name="shareAssetBoxForm" property="searchCriteria.empty" value="true">
			<bean:define id="noSearch" value="true"/>
		</logic:equal>
	</logic:empty>
	<logic:notEmpty name="shareAssetBoxForm" property="users">
		<html:form action="addAssetBoxUsers" method="post">
			<html:hidden name="shareAssetBoxForm" property="assetBoxId"/>  
			
			<c:set var="reorderingAction" value="viewAddAssetBoxUsers" />
			<%@include file="../inc/find-users-results.jsp" %>

			<table cellspacing="0" class="admin" summary="Share details">
				<c:if test="${emailUsersOnShare}">
					<tr>
						<td>
							<html:checkbox name="shareAssetBoxForm" property="sendEmailOnShare" styleId="sendEmail" styleClass="checkbox" onclick="showHideEmailFields();"/> <label for="sendEmail">Send an email to users not already sharing this <bright:cmsWrite identifier="a-lightbox" filter="false"/></label></p>
						</td>
					</tr>
					<tr>
						<td>
							<table id="emailFields" class="form" cellspacing="0" style="margin-top:10px;" summary="Email details">
								<tr>
									<td colspan="2" style="padding-bottom: 10px;">
										Note, you may leave either the subject or message blank. If you do then defaults will be used.
										<logic:notEmpty name="shareAssetBoxForm" property="emailContent.translations">
											This applies to translations also (for example, if the subject is blank for a particular language, the subject within the template
											will be used for that language).
										</logic:notEmpty>
									</td>
								</tr>
								<c:if test="${userprofile.isAdmin && marketingGroupsEnabled}">
									<c:if test="${not empty shareAssetBoxForm.emailTemplates}">
										<tr>
											<th>
												Email Template:
											</th>
											<td>
												<bean:define id="templates" name="shareAssetBoxForm" property="emailTemplates"/>
						 						<html:select name="shareAssetBoxForm" property="emailTemplateId">
						 							<html:option value="">[default]</html:option>
						 							<html:options collection="templates" property="textId" labelProperty="code"/>
						 						</html:select>
												<br/><br/>
												Please ensure that any template you select has a <strong>#url#</strong> variable within the template body.
											</td>
										</tr>
									</c:if>
								</c:if>
								<tr>
									<th>
										Email Subject:
									</th>
									<td>
				 						<html:text name="shareAssetBoxForm" property="emailContent.subject" maxlength="80" size="40"/>
				 					</td>
								</tr>
								<logic:notEmpty name="shareAssetBoxForm" property="emailContent.translations">
									<logic:iterate name="shareAssetBoxForm" property="emailContent.translations" id="translation" indexId="tIndex">
										<logic:greaterThan name="translation" property="language.id" value="0">
											<tr>
												<th class="translation">
													<label for="subject<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label>
												</th>
												<td>
													<html:hidden name="shareAssetBoxForm" property="emailContent.translations[${tIndex}].language.id"/>
													<html:hidden name="shareAssetBoxForm" property="emailContent.translations[${tIndex}].language.name"/>
													<input type="text" name="emailContent.translations[<bean:write name='tIndex'/>].subject" maxlength="80" size="40" id="subject<bean:write name='tIndex'/>" value="<bean:write name="translation" property="subject" filter="false"/>"/>
												</td>
											</tr>
										</logic:greaterThan>
									</logic:iterate>	
								</logic:notEmpty>
								<tr>
									<th>
										Email Message:
									</th>
									<td>
										<html:textarea name="shareAssetBoxForm" property="emailContent.body" rows="3" cols="80"/>
									</td>
								</tr>
								<logic:notEmpty name="shareAssetBoxForm" property="emailContent.translations">
									<logic:iterate name="shareAssetBoxForm" property="emailContent.translations" id="translation" indexId="tIndex">
										<logic:greaterThan name="translation" property="language.id" value="0">
											<tr>
												<th class="translation">
													<label for="body<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label>
												</th>
												<td>
													<html:hidden name="shareAssetBoxForm" property="emailContent.translations[${tIndex}].language.id"/>
													<html:hidden name="shareAssetBoxForm" property="emailContent.translations[${tIndex}].language.name"/>
													<textarea class="text" name="emailContent.translations[<bean:write name='tIndex'/>].body"  rows="3" cols="80" id="body<bean:write name='tIndex'/>"><bean:write name="translation" property="body" filter="false"/></textarea>
												</td>
											</tr>
										</logic:greaterThan>
									</logic:iterate>	
								</logic:notEmpty>
								<c:if test="${userprofile.isAdmin && marketingGroupsEnabled && (not empty shareAssetBoxForm.emailTemplates)}">
								<tr id="previewButton" style="visibility:hidden;">
									<th>&nbsp;</th>
									<td>
										<input class="button flush" type="button" value="<bright:cmsWrite identifier="button-preview" filter="false"/>" onclick="doFormSubmit(this);">
									</td>
								</tr>
								<script type="text/javascript">
									document.getElementById('previewButton').style.visibility='visible';
								</script>
								</c:if>
							</table>
						</td>
					</c:if>
				<tr>
					<td>
						<input type="checkbox" name="canEdit" id="canEdit" class="checkbox" value="true"/> <label for="canEdit"><bright:cmsWrite identifier="snippet-can-edit" filter="false"/><bright:cmsWrite identifier="a-lightbox" filter="false"/></label></p>
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" value="Add Selected Users" class="button flush" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-add-users" filter="false"/>');" style="margin-top:0.7em" />
					</td>
				</tr>
			</table>
		</html:form>
	</logic:notEmpty>
	
	<div class="hr"></div>
	<p>
		<a href ="../action/viewShareAssetBox?assetBoxId=<c:out value="${shareAssetBoxForm.assetBoxId}"/>"><bright:cmsWrite identifier="link-back" filter="false"/></a>
	</p>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>