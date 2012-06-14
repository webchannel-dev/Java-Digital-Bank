<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	11-Feb-2009		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Group Filter Exclusions</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="approval"/>
	<bean:define id="pagetitle" value="Group Filter Exclusions"/>
</head>
<body id="adminPage">
	<bean:define id="section" value="groups"/>
	<bean:define id="helpsection" value="filter_exclusions"/>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	<bean:parameter id="groupName" name="groupName"/>
	<h2>Manage Filter Exclusions for '<bean:write name="groupName"/>'</h2>
	
	<html:form action="saveGroupFilterExclusion" method="post" styleClass="floated">
	
		<html:hidden name="groupFilterExclusionForm" property="groupId"/>
		<input type="hidden" name="name" value="<c:out value="${param.name}"/>"/>
		<input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
		<input type="hidden" name="pageSize" value="<c:out value="${param.pageSize}"/>"/>
	
		<p>This page enables you to exclude certain filters from being accessible for users in the group.</p>

		<p>Note that if a user is a member of another group for which the exclusion settings allow the filter to be seen, then that takes priority and the user will be able to see the filter.</p>

		<c:if test="${!userprofile.isAdmin}">
		<p>If you are making changes to your own group to allow you to see more filters, then you will need to log in again for the changes to take effect.</p>				
		</c:if>

		<p>Filters selected below will <em>not</em> be visible to members of this group:</p>
		
		<div class="hr"></div>
		
		<bean:define id="excludedList" name="groupFilterExclusionForm" property="excludedList"/>
		
		<h3>Global Filters</h3>
		<logic:notEmpty name="groupFilterExclusionForm" property="globalFilters">
			<bean:define id="excludableItems" name="groupFilterExclusionForm" property="globalFilters"/>
			<%@include file="inc_group_exclusions.jsp"%>				
		</logic:notEmpty>
		<logic:empty name="groupFilterExclusionForm" property="globalFilters">
			<p>There are no global filters currently in the system.</p>
		</logic:empty>
		
		<div class="hr"></div>
		<h3>Category/Access Level Linked Filters</h3>
		<logic:notEmpty name="groupFilterExclusionForm" property="linkedFilters">
			<bean:define id="excludableItems" name="groupFilterExclusionForm" property="linkedFilters"/>
			<%@include file="inc_group_exclusions.jsp"%>				
		</logic:notEmpty>
		<logic:empty name="groupFilterExclusionForm" property="linkedFilters">
			<p>There are no catergory/access level linked filters currently in the system.</p>
		</logic:empty>
	
		<div class="hr"></div>
	
		<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		<a href="listGroups?name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a> 
		<br />
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>