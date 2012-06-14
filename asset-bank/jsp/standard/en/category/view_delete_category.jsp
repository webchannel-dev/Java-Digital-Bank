<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		07-Mar-2006		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Confirm Category Deletion</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="category"/>
	<bean:define id="pagetitle" value="Confirm Category Deletion"/>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<html:form action="deleteCategory" method="post" styleClass="floated">
		<html:hidden name="deleteCategoryForm" property="categoryId" />
		<html:hidden name="deleteCategoryForm" property="categoryIdToDelete" />
		
		<div class="warning">
			Are you sure that you want to delete the category <strong><bean:write name="deleteCategoryForm" property="category.name" /></strong>?
		</div>
		<c:if test="${deleteCategoryForm.category.numChildCategories > 0}">
			<p>
				This category and all of its child categories will be deleted. As this category has children, you cannot move any of the <bright:cmsWrite identifier="items" filter="false" /> that are currently in this category into another category. If you want to do this then you must delete/move all of the child categories individually. 
			</p>
		</c:if>
		<c:if test="${deleteCategoryForm.category.numChildCategories <= 0}">
			<p>
				If you want, you can move all of the <bright:cmsWrite identifier="items" filter="false" /> that are in this category into another category. To do this, select the category you want to move them into from the list below. If you click 'Delete' without selecting a category below then the category will be deleted without moving the <bright:cmsWrite identifier="items" filter="false" />. Note that deleting a category does not delete the <bright:cmsWrite identifier="items" filter="false" /> that are in it.
			</p>
			<p>
				Before deleting <bean:write name="deleteCategoryForm" property="category.name" />, move <bright:cmsWrite identifier="items" filter="false" /> to: <br />
				<logic:iterate name="deleteCategoryForm" property="flatCategoryList.categories" id="item" indexId="i">
					<logic:notEqual name='item' property='depth' value='0'>
						<div style="margin-left: <c:out value="${12 * item.depth}"/>px;">

							<input type="radio" class="checkbox" id="moveto<c:out value='${item.id}'/>" name="categoryIdToMoveTo" value="<c:out value='${item.id}'/>" <c:if test="${item.id == deleteCategoryForm.categoryIdToDelete}">disabled="disabled"</c:if>/> <label for="moveto<c:out value='${item.id}'/>" class="after <c:if test='${item.id == deleteCategoryForm.categoryIdToDelete}'>disabled</c:if>"><bean:write name="item" property="name" /></label>

							<br />
						</div>
					</logic:notEqual>
				</logic:iterate>
			</p>

		</c:if>

		<div class="hr"></div>

		<input type="submit" name="delete" value="Delete Category" class="button flush" />
		<a href="viewCategoryAdmin?categoryId=<bean:write name='deleteCategoryForm' property='categoryId' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>


	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>