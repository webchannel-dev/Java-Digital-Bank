<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan		25-Apr-2006		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Move Category</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<logic:equal name="changeCategoryParentForm" property="categoryTypeId" value="1">
		<bean:define id="section" value="category"/>
		<bean:define id="pagetitle" value="Move Category"/>
	</logic:equal>
	<logic:equal name="changeCategoryParentForm" property="categoryTypeId" value="2">
		<bean:define id="section" value="permcats"/>
		<bean:define id="pagetitle" value="Move Access Level"/>
	</logic:equal>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:present  name="changeCategoryParentForm">
		<logic:equal name="changeCategoryParentForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="changeCategoryParentForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>

	<logic:equal name="changeCategoryParentForm" property="categoryTypeId" value="2">
	<p>
		Please note: after moving you should check the permissions for all groups on this access level (and any of its children). The permissions may be changed by the move, if necessary to maintain consistency with the new parent.
	</p>
	</logic:equal>

	<html:form action="changeCategoryParent" method="post" styleClass="floated">
		<html:hidden name="changeCategoryParentForm" property="categoryTypeId" />
		<html:hidden name="changeCategoryParentForm" property="parentId" />
		<html:hidden name="changeCategoryParentForm" property="categoryIdToMove" />
		<html:hidden name="changeCategoryParentForm" property="category.id" />
		<html:hidden name="changeCategoryParentForm" property="category.name" />
		
		<p>Move <bean:write name="changeCategoryParentForm" property="category.name" /> to new parent: </p>
			
		<logic:iterate name="changeCategoryParentForm" property="flatCategoryList.categories" id="item" indexId="i">
			<div style="margin-left: <c:out value="${12 * item.depth}"/>px;">
				<input type="radio" class="checkbox"  id="moveto<c:out value='${item.id}'/>" name="newParentId" value="<c:out value='${item.id}'/>" 
					<c:if test="${item.id == changeCategoryParentForm.categoryIdToMove}">disabled="disabled"</c:if>/> 
				<label for="moveto<c:out value='${item.id}'/>" class="after <c:if test='${item.id == changeCategoryParentForm.categoryIdToMove}'>disabled</c:if>"><bean:write name="item" property="name" /></label>
				<br />						
				
			</div>
		</logic:iterate>
	

		<div class="hr"></div>

		<input type="submit" name="move" value="Move Category" class="button flush" />
		<a href="viewCategoryAdmin?categoryId=<bean:write name='changeCategoryParentForm' property='parentId' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>		
	</html:form>


	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>