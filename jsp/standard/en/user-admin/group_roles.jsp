<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	24-May-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="notUsingActiveDirectory" settingName="suspend-ad-authentication"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="useIPMapping" settingName="users-have-IP"/>
<bright:applicationSetting id="useURLMapping" settingName="users-have-URL"/>



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Group roles</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/javascript" src="../js/groups.js"></script>
	<bean:define id="section" value="groups"/>
</head>

<body id="adminPage" >
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline">Group roles</h1> 

	<logic:present  name="rolesForm">
		<logic:equal name="rolesForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="rolesForm" property="errors" id="error">
						<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>	
	
	<p>Roles give groups of users permission to use certain aspects of admin functionality within <bright:cmsWrite identifier="app-name" filter="false"/>.</p>
	<p>Select the permissions that you would like this group to have from the list below, then click save:</p><br/>
	
	<form id="group_edit" action="saveGroupRoles" method="post"  class="floated">
		<input type="hidden" name="id" value="<bean:write name='rolesForm' property='group.id'/>"/>
		<input type="hidden" name="name" value="<c:out value="${param.name}"/>"/>
		<input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
		<input type="hidden" name="pageSize" value="<c:out value="${param.pageSize}"/>"/>
		
		<logic:notEmpty name="rolesForm" property="roles">
			<logic:iterate name="rolesForm" property="roles" id="role">
				<input type="checkbox" name="role<bean:write name='role' property='id'/>" id="role<bean:write name='role' property='id'/>" value="<bean:write name='role' property='id'/>" class="checkbox" <logic:match name="rolesForm" property="group.hasRole[${role.id}]" value="true">checked</logic:match>/>
				<label for="role<bean:write name='role' property='id'/>"><bean:write name="role" property="name" filter="false"/> <em>(<bean:write name="role" property="description" filter="false"/>)</em></label><br/>
			</logic:iterate>
		</logic:notEmpty>
		
		<div class="hr"></div>
		
		<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-save" filter="false" />" />
		<a href="listGroups?name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a> 
			
	</form>
		
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>