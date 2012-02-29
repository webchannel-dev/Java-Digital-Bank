<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	18-May-2005		Tidied presentation to fit with Demo UI design
	 d3		Ben Browning	22-Feb-2006		HTML/CSS tidy up
	 d4      Steve Bryan    20-Apr-2006    Include parent Id in the form
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<c:set scope="session" var="editAssetReturnAction" value="../action/viewUpdateCategory?categoryId=${categoryForm.category.id}&parentId=${categoryForm.category.parentId}"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Update Category</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="category"/>
	<bean:define id="pagetitle" value="Categories"/>
		

	
	<%@include file="../inc/inc_mce_editor.jsp"%>
	<script type="text/JavaScript">
		// give the categoryName field the focus once the dom is ready
		$j(function () {
  			$j('#categoryName').focus();
 		});
	</script>			
</head>

<body id="adminPage"> 

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:present  name="categoryForm">
		<logic:equal name="categoryForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="categoryForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<h2>Update Category</h2>
	
	<html:form action="updateCategory" method="post" enctype="multipart/form-data">
	
		<%@include file="../category/inc_update_category_fields.jsp"%>
		
	</html:form>

	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>