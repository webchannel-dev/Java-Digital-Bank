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
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Send Marketing Email</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="marketing"/>
	<c:set var="helpsection" value="marketing-send-email"/>
	<bean:define id="pagetitle" value="Marketing"/>
	<bean:define id="tabId" value="sendEmail"/>
	<style type="text/css">
		td,th { padding-right: 7px; }
	</style>
	<script type="text/javascript">
		//<!--

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

		//-->
			</script>
	<script type="text/JavaScript">
		$j(function() {
			// give the forename field the focus once the dom is ready
			$j('#forename').focus();
		});
	</script>
</head>
<body>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	
	<%@include file="../marketing-admin/inc_marketing_tabs.jsp"%>
	
	<h2>Send Marketing Email</h2>

	<logic:present name="sendMarketingEmailForm">
		<logic:equal name="sendMarketingEmailForm" property="hasErrors" value="true"> 
    		<div class="error">
    			<logic:iterate name="sendMarketingEmailForm" property="errors" id="errorText">
    				<bean:write name="errorText" filter="false"/><br />
    			</logic:iterate>
    		</div>
    	</logic:equal>
	</logic:present>
         
	<div class="hr"></div>
	
	<html:form action="viewSendMarketingEmail" method="post">
	
		<html:hidden name="sendMarketingEmailForm" property="assetBoxId"/>
		<bean:parameter id="orderId" name="orderId" value="1"/>								
	
		<h3><bright:cmsWrite identifier="subhead-find-users" filter="false"/></h3>
		<table cellspacing="0" style="width:auto; margin-bottom:1em;"  class="admin" summary="Form for searching for users">
			<tr>
				<th><label for="forename"><bright:cmsWrite identifier="label-forename" filter="false"/></label></th>
				<th><label for="surname"><bright:cmsWrite identifier="label-surname" filter="false"/></label></th>
				<th><label for="username"><bright:cmsWrite identifier="label-username" filter="false" replaceVariables="true" /></label></th>
				<th><label for="email"><bright:cmsWrite identifier="label-email" filter="false" replaceVariables="true" /></label></th>
				<c:if test="${usersHaveStructuredAddress}">
					<th><label for="country"><bright:cmsWrite identifier="label-country" filter="false" replaceVariables="true" /></label></th>
				</c:if>
				<th><label for="group"><bright:cmsWrite identifier="label-user-group" filter="false"/></label></th>
			</tr>
			<tr>
				<td>
					<input type="text" class="text" id="forename" name="searchCriteria.forename" maxlength="50" size="12" value="<bean:write name='sendMarketingEmailForm' property='searchCriteria.forename' filter='false'/>">
				</td>
				<td>
					<input type="text" class="text" id="surname" name="searchCriteria.surname" maxlength="50" size="12" value="<bean:write name='sendMarketingEmailForm' property='searchCriteria.surname' filter='false'/>">
				</td>		
				<td>
					<input type="text" class="text" id="username" name="searchCriteria.username" maxlength="50" size="12" value="<bean:write name='sendMarketingEmailForm' property='searchCriteria.username' filter='false'/>">			
				</td>
				<td>
					<input type="text" class="text" id="email" name="searchCriteria.emailAddress" maxlength="50" size="12" value="<bean:write name='sendMarketingEmailForm' property='searchCriteria.emailAddress' filter='false'/>">			
				</td>
				<c:if test="${usersHaveStructuredAddress}">
					<td>
						<bean:define id="countries" name="sendMarketingEmailForm" property="countries"/>
						<html:select styleId="country" name="sendMarketingEmailForm" property="searchCriteria.countryId" size="1">
							<option value="0">[any]</option>
							<html:options collection="countries" property="id" labelProperty="name" filter="false"/>
						</html:select>
					</td>
				</c:if>
				<td>
					<bean:define name="sendMarketingEmailForm" property="groups" id="groups"/>
					<html:select name="sendMarketingEmailForm" property="searchCriteria.groupId" styleId="group">
						<option value="0">[any]</option>
						<html:options collection="groups" property="id" labelProperty="nameWithOrgUnit" filter="false"/>
					</html:select>
				</td>
			</tr>
		</table>	
		
		<c:if test="${userprofile.isAdmin && marketingGroupsEnabled && not empty sendMarketingEmailForm.marketingGroups}">
			<table cellspacing="0" style="width:auto; margin-bottom:14px;" class="admin" summary="Form for searching for users">
				<tr>
					<th>
						<label for="group">Marketing Group Subscriptions:</label>
					</th>
				</tr>
				<tr>
					<td>
						
						<logic:iterate name="sendMarketingEmailForm" property="marketingGroups" id="group" indexId="groupIndex">
							<bean:define id="groupId" name="group" property="id" />
							<html:multibox name="sendMarketingEmailForm" property="searchCriteria.marketingGroupIds" styleClass="checkbox" styleId="<%=\"m_groups_\"+groupId%>">
								<bean:write name="group" property="id" />
							</html:multibox>
							<label for="m_groups_<bean:write name="group" property="id" />"><bean:write name="group" property="name" /></label>
							<br/>
						</logic:iterate>
						
					</td>
				</tr>
			</table>
		</c:if>
		
		<input type="submit" class="button flush" value="Find &raquo;" />	
	</html:form>

	<c:if test="${not empty sendMarketingEmailForm.users}">
		<div class="hr"></div>
		<h3><bright:cmsWrite identifier="heading-matching-users" filter="false"/></h3>
	</c:if>
	<bean:define id="noSearch" value="false"/>
	<logic:empty name="sendMarketingEmailForm" property="users">
		<logic:equal name="sendMarketingEmailForm" property="searchCriteria.empty" value="false">
			<div class="hr"></div>
			<p><bright:cmsWrite identifier="snippet-no-users-found" filter="false"/></p>
		</logic:equal>
		<logic:equal name="sendMarketingEmailForm" property="searchCriteria.empty" value="true">
			<bean:define id="noSearch" value="true"/>
		</logic:equal>
	</logic:empty>
	<logic:notEmpty name="sendMarketingEmailForm" property="users">
		
		<html:form action="editMarketingEmail" method="post">
			
			<html:hidden name="sendMarketingEmailForm" property="assetBoxId"/>
			<html:hidden name="sendMarketingEmailForm" property="searchCriteria.forename"/>
			<html:hidden name="sendMarketingEmailForm" property="searchCriteria.surname"/>
			<html:hidden name="sendMarketingEmailForm" property="searchCriteria.username"/>
			<html:hidden name="sendMarketingEmailForm" property="searchCriteria.emailAddress"/>
			<html:hidden name="sendMarketingEmailForm" property="searchCriteria.countryId"/>
			<html:hidden name="sendMarketingEmailForm" property="searchCriteria.groupId"/>
			<logic:notEmpty name="sendMarketingEmailForm" property="searchCriteria.marketingGroupIds">
				<logic:iterate name="sendMarketingEmailForm" property="searchCriteria.marketingGroupIds" id="marketingGroupId">
					<input type="hidden" name="searchCriteria.marketingGroupIds" value="<bean:write name="marketingGroupId"/>"/>
				</logic:iterate>
			</logic:notEmpty>
		
			<table cellspacing="0" class="admin" style="margin-bottom:12px;" summary="List of users">		
				<tr>
					<th>
						<bright:cmsWrite identifier="label-name-nc" filter="false"/>&nbsp;
						<logic:equal name="orderId" value="1">
							<img src="../images/standard/arrow/up_arrow_off.gif" border="0" width="5" height="9" alt="">
						</logic:equal>
						<logic:notEqual name="orderId" value="1">
							<a href="../action/viewAddAssetBoxUsers?searchCriteria.forename=<bean:write name='sendMarketingEmailForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='sendMarketingEmailForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='sendMarketingEmailForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='sendMarketingEmailForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='sendMarketingEmailForm' property='searchCriteria.groupId'/>&amp;orderId=1"><img src="../images/standard/arrow/up_arrow.gif" border="0" width="5" height="9" alt="up"></a>
						</logic:notEqual>
						<logic:equal name="orderId" value="2">
							<img src="../images/standard/arrow/down_arrow_off.gif" border="0" width="5" height="9" alt="">
						</logic:equal>
						<logic:notEqual name="orderId" value="2">
							<a href="../action/viewAddAssetBoxUsers?searchCriteria.forename=<bean:write name='sendMarketingEmailForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='sendMarketingEmailForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='sendMarketingEmailForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='sendMarketingEmailForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='sendMarketingEmailForm' property='searchCriteria.groupId'/>&amp;orderId=2"><img src="../images/standard/arrow/down_arrow.gif" border="0" width="5" height="9" alt="down"></a>
						</logic:notEqual>
					</th>
					<th>
						<bright:cmsWrite identifier="label-username-nc" filter="false"/>&nbsp;
						<logic:equal name="orderId" value="3">
							<img src="../images/standard/arrow/up_arrow_off.gif" border="0" width="5" height="9" alt="">
						</logic:equal>
						<logic:notEqual name="orderId" value="3">
							<a href="../action/viewAddAssetBoxUsers?searchCriteria.forename=<bean:write name='sendMarketingEmailForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='sendMarketingEmailForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='sendMarketingEmailForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='sendMarketingEmailForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='sendMarketingEmailForm' property='searchCriteria.groupId'/>&amp;orderId=3"><img src="../images/standard/arrow/up_arrow.gif" border="0" width="5" height="9" alt="up"></a>
						</logic:notEqual>
						<logic:equal name="orderId" value="4">
							<img src="../images/standard/arrow/down_arrow_off.gif" border="0" width="5" height="9" alt="">
						</logic:equal>
						<logic:notEqual name="orderId" value="4">
							<a href="../action/viewAddAssetBoxUsers?searchCriteria.forename=<bean:write name='sendMarketingEmailForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='sendMarketingEmailForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='sendMarketingEmailForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='sendMarketingEmailForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='sendMarketingEmailForm' property='searchCriteria.groupId'/>&amp;orderId=4"><img src="../images/standard/arrow/down_arrow.gif" border="0" width="5" height="9" alt="down"></a>
						</logic:notEqual>
					</th>
					<th>
						<bright:cmsWrite identifier="label-email-nc" filter="false"/>&nbsp;
						<logic:equal name="orderId" value="5">
							<img src="../images/standard/arrow/up_arrow_off.gif" border="0" width="5" height="9" alt="">
						</logic:equal>
						<logic:notEqual name="orderId" value="5">
							<a href="../action/viewAddAssetBoxUsers?searchCriteria.forename=<bean:write name='sendMarketingEmailForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='sendMarketingEmailForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='sendMarketingEmailForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='sendMarketingEmailForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='sendMarketingEmailForm' property='searchCriteria.groupId'/>&amp;orderId=5"><img src="../images/standard/arrow/up_arrow.gif" border="0" width="5" height="9" alt="up"></a>
						</logic:notEqual>
						<logic:equal name="orderId" value="6">
							<img src="../images/standard/arrow/down_arrow_off.gif" border="0" width="5" height="9" alt="">
						</logic:equal>
						<logic:notEqual name="orderId" value="6">
							<a href="../action/viewAddAssetBoxUsers?searchCriteria.forename=<bean:write name='sendMarketingEmailForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='sendMarketingEmailForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='sendMarketingEmailForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='sendMarketingEmailForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='sendMarketingEmailForm' property='searchCriteria.groupId'/>&amp;orderId=6"><img src="../images/standard/arrow/down_arrow.gif" border="0" width="5" height="9" alt="down"></a>
						</logic:notEqual>
					</th>
					<th>
						<bright:cmsWrite identifier="snippet-select" filter="false"/>
					</th>
				</tr>
				<c:set var="tests" value="${sendMarketingEmailForm.users}"/>
				<logic:iterate name="tests" id="user">
					<tr>
						<td>
							<bean:write name="user" property="surname" />,														
							<bean:write name="user" property="forename" />
						</td>
						<td>
							<bean:write name="user" property="username" />
						</td>
						<td>
							<c:if test="${empty user.emailAddress}"><span class="disabled">-</span></c:if><c:if test="${not empty user.emailAddress}"><bean:write name="user" property="emailAddress" /></c:if>
						</td>
						<td class="checkbox">
							<input type="checkbox" name="selectedUsers" value="<c:out value="${user.id}"/>" <c:if test="${not (sendMarketingEmailForm.hasErrors && empty selectedUsers)}">checked="checked"</c:if>/>
						</td>													
					</tr>
				</logic:iterate>
			</table>
			<table cellspacing="0" class="admin" summary="Share details">
				<c:if test="${not empty sendMarketingEmailForm.emailTemplates}">
					<tr>
						<td>
							<table id="emailFields" class="form" cellspacing="0" style="margin-top:10px;" summary="Email details">
								<tr>
									<th>
										Email Template:
									</th>
									<td>
										<bean:define id="templates" name="sendMarketingEmailForm" property="emailTemplates"/>
				 						<html:select name="sendMarketingEmailForm" property="emailTemplateId">
				 							<html:option value="">[none]</html:option>
				 							<html:options collection="templates" property="textId" labelProperty="code"/>
				 						</html:select>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</c:if>
				<tr>
					<td>
						<input type="submit" value="Create Email &raquo;" class="button flush" style="margin-top:0.7em" />
					</td>
				</tr>
			</table>
		</html:form>
	</logic:notEmpty>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>