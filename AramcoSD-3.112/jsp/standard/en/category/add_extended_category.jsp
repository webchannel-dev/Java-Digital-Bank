<%@include file="../inc/doctype_html_admin.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="catExtensionAssetsEnabled" settingName="category-extension-assets-enabled" />

<c:choose>
	<c:when test="${orgUnitCategoryAdminForm.categoryTreeId == 2}">
		<c:set var="catName" value="access level" />
		<bean:define id="section" value="permcats"/>
		<c:set var="addAction" value="addPermissionCategory" />
	</c:when>
	<c:otherwise>
		<c:set var="catName" value="category" />
		<bean:define id="section" value="category"/>
		<c:set var="addAction" value="addCategory" />
	</c:otherwise>
</c:choose>
	
<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Category Admin</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="pagetitle" value="Categories"/>
	

	<style type="text/css">
	<!--
		form.floated label {
			width: 120px;
		}
	-->
	</style>
	
	<script type="text/JavaScript">
		// once the dom is ready

		$j(function () {
			//give the newCatName field the focus 
			$j('#newCatName').focus();

		});
	</script>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:equal name="orgUnitCategoryAdminForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="orgUnitCategoryAdminForm" property="errors" id="error">
				<bean:write name="error" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<bean:define id='currentCategoryName' name='orgUnitCategoryAdminForm' property='categoryName'  type="java.lang.String"/>
	<bean:parameter id="catExtensionReturnLocation" name="catExtensionReturnLocation" value="" />
	<bean:define id="forceExtensionAsset" value="true" />
	<bean:define id="categoryAdminForm" name="orgUnitCategoryAdminForm" />
	<%@include file="inc_add_category_form.jsp"%>
	
	

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>