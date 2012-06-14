<%@include file="../inc/doctype_html.jsp" %>

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
	
	<title><bright:cmsWrite identifier="title-keywords" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="keyword"/>
</head>

<body id="popup">
	
	
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-move-keyword" filter="false" /></h1>
	
	<logic:present  name="changeCategoryParentForm">
		<logic:equal name="changeCategoryParentForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="changeCategoryParentForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<html:form action="changeKeywordParentPopup" method="post" styleClass="floated">
		<html:hidden name="changeCategoryParentForm" property="parentId" />
		<html:hidden name="changeCategoryParentForm" property="categoryIdToMove" />
		<html:hidden name="changeCategoryParentForm" property="category.id" />
		<html:hidden name="changeCategoryParentForm" property="category.name" />
		<html:hidden name="changeCategoryParentForm" property="category.categoryTypeId"/>
		
		<p>Move <em><bean:write name="changeCategoryParentForm" property="category.name" /></em> to new parent:</p>

		<logic:iterate name="changeCategoryParentForm" property="flatCategoryList.categories" id="item" indexId="i">
			<div style="margin-left: <c:out value="${12 * item.depth}"/>px;">
				<input type="radio" class="checkbox" id="moveto<c:out value='${item.id}'/>" name="newParentId" value="<c:out value='${item.id}'/>" 
					<c:if test="${item.id == changeCategoryParentForm.categoryIdToMove}">disabled="disabled"</c:if>/> 
				<label for="moveto<c:out value='${item.id}'/>" class="after <c:if test='${item.id == changeCategoryParentForm.categoryIdToMove}'> disabled</c:if>"><bean:write name="item" property="name" /></label>
				<br />						
				
			</div>
		</logic:iterate>
		
		<div class="hr"></div>
	
		<input type="submit" name="move" value="<bright:cmsWrite identifier="button-move-keyword" filter="false" />" class="button flush" />
			<a href="viewKeywordAdminPopup?categoryId=<bean:write name='changeCategoryParentForm' property='parentId' />&amp;categoryTypeId=<bean:write name='changeCategoryParentForm' property='category.categoryTypeId'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			
	</html:form>


			
</body>
</html>

