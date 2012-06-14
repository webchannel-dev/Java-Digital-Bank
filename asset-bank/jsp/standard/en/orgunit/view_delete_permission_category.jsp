<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	18-May-2005		Tidied presentation to fit with Demo UI design
	 d3     Ben Browning	22-Feb-2006		HTML/CSS tidy up
	 d4     Steve Bryan		20-Apr-2006		Include parent Id in the form
	 d5		Matt Stevenson	23-May-2007		Added selected on load field
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Confirm Access Level Deletion</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="category"/>
	<bean:define id="pagetitle" value="Confirm Access Level Deletion"/>
	<bean:parameter name="ouid" id="ouid" value="0"/>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
		
	<html:form action="deletePermissionCategory" method="post" styleClass="floated">
		<html:hidden name="deletePermissionCategoryForm" property="categoryId" />
		<html:hidden name="deletePermissionCategoryForm" property="categoryIdToDelete" />
		<input type="hidden" name="ouid" value="<c:out value="${ouid}"/>"/>

		<div class="warning">
			Are you sure that you want to delete the access level <strong><bean:write name="deletePermissionCategoryForm" property="category.name" /></strong>?
		</div>
		<c:if test="${deletePermissionCategoryForm.category.numChildCategories > 0}">
			<p>
				This access level and all of its child access levels will be deleted. As this access level has children, you cannot move any of the <bright:cmsWrite identifier="items" filter="false" /> that are currently in this access level into another access level. If you want to do this then you must delete/move all of the child access levels individually. 
			</p>
		</c:if>
		<c:if test="${deletePermissionCategoryForm.category.numChildCategories <= 0}">
			<p>
				If you want, you can move all of the <bright:cmsWrite identifier="items" filter="false" /> that are in this access level into another access level. To do this, select the access level you want to move them into from the list below. If you click 'Delete' without selecting an access level below then the access level will be deleted without moving the <bright:cmsWrite identifier="items" filter="false" />. Note that deleting an access level does not delete the <bright:cmsWrite identifier="items" filter="false" /> that are in it.
			</p>
			<p>
				Before deleting <bean:write name="deletePermissionCategoryForm" property="category.name" />, move <bright:cmsWrite identifier="items" filter="false" /> to: <br />
				<logic:iterate name="deletePermissionCategoryForm" property="flatCategoryList.categories" id="item" indexId="i">
					<logic:notEqual name='item' property='depth' value='0'>
						<div style="margin-left: <c:out value="${12 * item.depth}"/>px;">

							<input type="radio" class="checkbox" id="moveto<c:out value='${item.id}'/>" name="categoryIdToMoveTo" value="<c:out value='${item.id}'/>" <c:if test="${item.id == deletePermissionCategoryForm.categoryIdToDelete}">disabled="disabled"</c:if>/> <label for="moveto<c:out value='${item.id}'/>" class="after <c:if test='${item.id == deletePermissionCategoryForm.categoryIdToDelete}'>disabled</c:if>"><bean:write name="item" property="name" /></label>

							<br />
						</div>
					</logic:notEqual>
				</logic:iterate>
			</p>

		</c:if>

		<div class="hr"></div>

		<input type="submit" name="delete" value="Delete Access Level" class="button flush" />
		<a href="viewCategoryAdmin?categoryId=<bean:write name='deletePermissionCategoryForm' property='categoryId' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>