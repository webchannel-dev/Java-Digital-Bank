<%@include file="../inc/doctype_html_admin.jsp" %>
<%-- History:
	 d1	Matt Stevenson		17-Sep-2010		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | View Organisational Unit Details</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="orgunits"/>
	<bean:define id="pagetitle" value="Organisational Unit Details"/>
	<bean:define id="tabId" value="users"/>
	<bean:define id="helpsection" value="orgunit-details"/>

</head>

<bright:applicationSetting id="useStructuredAddress" settingName="users-have-structured-address" />
<bean:parameter name="returnUrl" id="returnUrl" value="" />

<body <c:if test="${empty returnUrl}">id="adminPage"</c:if>>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	
	<%@include file="inc_orgunit_details_tabs.jsp"%>

	<h3>User details</h3>
	
	

	<logic:notEmpty name="orgUnitForm" property="userList">
		
		<div class="horizontalScroll">

			<table class="list stripey" cellspacing="0" summary="User details for <bean:write name="orgUnitForm" property="orgUnit.category.name"/> ">
				<thead>
					<tr>
						<th>Name</th>
						<th>Username</th>
						<th>Email</th>
						<th>Address</th>
						<th>Job Title</th>
						<th>Registered</th>
						<!-- Custom user field headings -->
						<logic:notEmpty name="orgUnitForm" property="customFields">
							<logic:iterate name="orgUnitForm" property="customFields" id="field">
								<c:if test="${not field.isSubtype || (field.isSubtype && isCustomFieldSubtype)}">
									<bean:define id="fieldId" name="field" property="id"/> 
									<th><label for="field<bean:write name='field' property='id'/>"><bean:write name="field" property="name"/></label></th>
								</c:if>
							</logic:iterate>
						</logic:notEmpty>
					</tr>
				</thead>
			
				<%@include file="inc_orgunit_details_users.jsp"%>

			</table>
		</div>
		<c:if test="${userprofile.isAdmin || userprofile.isOrgUnitAdmin}">
			<br /><p>You can add/edit/remove users by going to the <a href="viewUserAdmin">user management page</a></p>
		</c:if>
	</logic:notEmpty>
	<logic:empty name="orgUnitForm" property="userList">
		<p>This org unit doesn't currently have any users.</p>
		<div class="hr"></div>
	</logic:empty>
	
	<%@include file="inc_orgunit_details_back.jsp"%>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>