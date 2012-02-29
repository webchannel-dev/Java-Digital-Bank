<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson	21-Oct-2005		Created
	 d2 		Ben Browning	21-Feb-2006		HTML/CSS Tidy Up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Group Attribute Exclusions</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="groupAtrributeExclusion"/>
	<bean:define id="pagetitle" value="Group Attribute Exclusions"/>
</head>
<body id="adminPage">
	<bean:define id="section" value="groupAttributeExclusion"/>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	<bean:parameter id="groupName" name="groupName"/>
	<h2>Manage Attribute Exclusions for '<bean:write name="groupName"/>'</h2>
	
	<html:form action="saveGroupAttributeExclusion" method="post" >
	
		<html:hidden name="groupAttributeExclusionForm" property="groupId"/>
		<input type="hidden" name="name" value="<c:out value="${param.name}"/>"/>
		<input type="hidden" name="page" value="<c:out value="${param.page}"/>"/>
		<input type="hidden" name="pageSize" value="<c:out value="${param.pageSize}"/>"/>
		
			<p>This page enables you to exclude access to certain <bright:cmsWrite identifier="items" filter="false" /> based on asset attribute values.
			</p>
	
			<p>Note that if a user is a member of another group for which the attribute exclusion settings allow the asset to be seen, 
				then that takes priority and the user will be able to see the asset.</p>
	
			<c:if test="${!userprofile.isAdmin}">
				<p>If you are making changes to your own group to allow you to see more assets, then you will need to log in again
					for the changes to take effect.</p>				
			</c:if>
	
			<p><bright:cmsWrite identifier="items" filter="false" case="mixed" /> with the attribute values selected below will <em>not</em> be visible to members of this group:</p>
			
			<div class="hr"></div>
			
			<table cellspacing="0" class="form" summary="Table of attribute exclusions"  width="100%">
				<logic:iterate name="groupAttributeExclusionForm" property="attributeList" id="attribute">
				<%-- Only allow exclusions on dropdown lists --%>
				<logic:equal name="attribute" property="typeId" value="4">
				<tr>
					<th>
						<bean:write name="attribute" property="label"/>
					</th>
					<td style="padding-top:2px;">
						<table cellpadding="0" cellspacing="0" width="100%">
						<logic:iterate name="attribute" property="listOptionValues" id="attValue" indexId="opIndex">
							<%-- See if this value if currently selected --%>
							<bean:define id="bSelected" value="false"/>
							<logic:iterate name="groupAttributeExclusionForm" property="excludedList" id="excluded">
								<c:if test="${excluded.itemId==attribute.id && excluded.value==attValue.value}">
									<bean:define id="bSelected" value="true"/>
								</c:if>
							</logic:iterate>
	
								<tr>
									<td style="width: 50%;"><bean:write name="attValue" property="value"/></td>
									<td><input type="checkbox" class="checkbox" name="excluded_<bean:write name='attribute' property='id'/>_<bean:write name='opIndex'/>" <logic:equal name="bSelected" value="true">checked="checked"</logic:equal> value="<bean:write name='attValue' property='value'/>"></td>
								</tr>
						</logic:iterate>
						</table>
					</td>
				</tr>
				</logic:equal>
				</logic:iterate>
			</table>
		
		<div class="hr"></div>	
		
		<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		<a href="listGroups?name=<c:out value="${param.name}"/>&page=<c:out value="${param.page}"/>&pageSize=<c:out value="${param.pageSize}"/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>