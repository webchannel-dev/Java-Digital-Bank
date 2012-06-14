<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Woollard		07-Jul-2008		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	<title><bright:cmsWrite identifier="title-agreements" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="agreements"/>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-agreements" filter="false"/></h1> 

	<logic:present name="agreementsForm">
		<logic:equal name="agreementsForm" property="hasErrors" value="true">
			<logic:iterate name="agreementsForm" property="errors" id="error">
				<div class="error">
					<bright:writeError name="error" />
				</div>
			</logic:iterate>
		</logic:equal>
	</logic:present>											
	
	<h2><bright:cmsWrite identifier="subhead-manage-agreements" filter="false"/></h2>
	
	<table cellspacing="0" class="admin">
		<tr>
			<th><bright:cmsWrite identifier="label-agreement-title" filter="false"/></th>
			<th><bright:cmsWrite identifier="label-agreement-expiry" filter="false"/></th>
			<th colspan="2"><bright:cmsWrite identifier="label-agreement-actions" filter="false"/></th>
		</tr>
		<logic:iterate name="agreementsForm" property="agreements" id="agreement">
			</tr>
				<td><bean:write name="agreement" property="title"/></td>
				<td>
					<c:choose>
						<c:when test="${not empty agreement.expiryString}"><bean:write name="agreement" property="expiryString"/></c:when>
						<c:otherwise><span class="disabled">n/a</span></c:otherwise>
					</c:choose>
				</td>
				<td class="action">
					<c:choose>
					<%-- Users/org admin can edit if not shared across ou. only admin can edit shared agreements --%>
					<c:when test="${((userprofile.isOrgUnitAdmin || userprofile.canUpdateAssets) && !agreement.isAvailableToAll) || userprofile.isAdmin}">
						[<a href="editAgreement?id=<bean:write name='agreement' property='id'/>"><bright:cmsWrite identifier="link-edit" filter="false"/></a>]
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
					</c:choose>
				</td>
				<td class="action">
					<c:choose>
					<%-- Admin can delete if shared across all OUs, OU admin can delete if restricted to one OU --%>
					<c:when test="${(userprofile.isAdmin) || (userprofile.isOrgUnitAdmin && !agreement.isAvailableToAll)}">
						[<a href="deleteAgreement?id=<bean:write name='agreement' property='id'/>" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-delete-agreement" filter="false"/>');" title="Delete this agreement" >X</a>]
					</c:when>
					<c:otherwise>
						&nbsp;
					</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</logic:iterate>
	</table>		
	
	<div class="hr"></div>
	
		<p><strong><a href="editAgreement"><bright:cmsWrite identifier="link-add-agreement" filter="false"/></a></strong></p>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>