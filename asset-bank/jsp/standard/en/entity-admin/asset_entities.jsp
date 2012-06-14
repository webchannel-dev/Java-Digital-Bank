<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		28-Sep-2007		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Asset Types</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="assetEntity"/>
	<bean:define id="pagetitle" value="Asset Types"/>
	<bean:define id="helpsection" value="asset-entities"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<h2>Manage Asset Types</h2>

	<logic:equal name="assetEntityForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="assetEntityForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:equal>
	
	<c:if test="${empty assetEntityForm.entities}">
		<p>There are currently no Asset Types.</p>
	</c:if>
	<c:if test="${not empty assetEntityForm.entities}">

		<bean:size id="numEntities" name="assetEntityForm" property="entities"/>

		<table cellspacing="0" class="admin" summary="List of asset types">		
			<tr>
				<th>Name</th>
				<td>&nbsp;</td>
			</tr>
				
			<logic:iterate name="assetEntityForm" property="entities" id="entity" indexId="entityIndex">
				<tr>
					<td><bean:write name="entity" property="name"/></td>
					<td class="action">
						<c:if test="${entityIndex>0}">[<a href="reorderAssetEntities?id=<c:out value="${entity.id}"/>&up=true"><bright:cmsWrite identifier="link-up" filter="false"/></a>]</c:if>
						<c:if test="${entityIndex<=0}">[<strong class="disabled"><bright:cmsWrite identifier="link-up" filter="false"/></strong>]</c:if>
					</td>
					<td class="action"> 
						<c:if test="${entityIndex<numEntities-1}">[<a href="reorderAssetEntities?id=<c:out value="${entity.id}"/>&up=false"><bright:cmsWrite identifier="link-down" filter="false"/></a>]</c:if>
						<c:if test="${entityIndex>=numEntities-1}">[<strong class="disabled"><bright:cmsWrite identifier="link-down" filter="false"/></strong>]</c:if>
					</td>
					<td class="action">
						[<a href="../action/viewEditAssetEntity?id=<bean:write name='entity' property='id'/>">edit</a>]
					</td>
					<td class="action">
						[<a href="../action/viewEditAssetEntityRelationships?id=<bean:write name='entity' property='id'/>">relationships</a>]
					</td>
					<td class="action">
						[<a href="../action/deleteAssetEntity?id=<bean:write name='entity' property='id'/>" onclick="return confirm('Are you sure you want to delete this Asset Type?')">delete</a>]
					</td>
				</tr>
			</logic:iterate>
		</table>
	</c:if>
	
	<div class="hr"></div>
	
	<p><a href="../action/viewAddAssetEntity">Add a new Asset Type &raquo;</a></p>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>