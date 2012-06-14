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



<bean:parameter id="ouid" name="ouid" value="0" />
<c:set scope="session" var="editAssetReturnAction" value="../action/viewUpdatePermissionCategory?categoryId=${categoryForm.category.id}&ouid=${ouid}&parentId=${categoryForm.category.parentId}"/>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Access Levels</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script type="text/javascript" src="../js/tiny_mce/tiny_mce.js"></script>
	<script type="text/javascript">
		<!-- 
		tinyMCE.init({
			mode : "specific_textareas",
			theme : "advanced",
			theme_advanced_buttons1 : "bold,italic,underline,forecolor,charmap,separator,justifyleft,justifycenter,justifyright, justifyfull,bullist,numlist,undo,redo,link,unlink",
			theme_advanced_buttons2 : "image,cleanup,removeformat,separator,formatselect,code",
			theme_advanced_buttons3 : "",
			theme_advanced_toolbar_location : "top",
			theme_advanced_toolbar_align : "left",
			theme_advanced_path_location : "bottom",
			content_css : "../css/standard/global.css",
			editor_selector : "editor"
		}); 
		//-->
	</script>

	<c:choose>
		<c:when test="${ouid > 0}">
			<bean:define id="section" value="orgunits"/>
			<bean:define id="pagetitle" value="Organisational Unit: Access Levels"/>						
		</c:when>
		<c:otherwise>
			<bean:define id="section" value="permcats"/>
			<bean:define id="pagetitle" value="Access Levels"/>			
		</c:otherwise>
	</c:choose>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:present  name="categoryForm">
		<logic:equal name="categoryForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="categoryForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
		
	<h2>Update Access Level</h2>
	
	<html:form action="updatePermissionCategory" method="post" focus="category.name" enctype="multipart/form-data">
		<input type="hidden" name="ouid" value="<c:out value="${ouid}"/>"/>

		<bean:define id="showSelectedOnLoadField" value="true"/>
		<bean:define id='returnAction' value='viewPermissionCategories'/>
		<%@include file="../category/inc_update_category_fields.jsp"%>
			
	</html:form>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>