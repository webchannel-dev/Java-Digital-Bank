<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan		19-Jun-2009		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>




<head>
	
	<title><bright:cmsWrite identifier="title-start-import" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script src="../js/category.js" type="text/javascript"></script>
	<script src="../js/keywordChooser.js" type="text/javascript"></script>
	<script src="../js/agreements.js" type="text/javascript"></script>
	
	<bean:define id="assetForm" name="importForm"/>	

	<c:set var="ctrlIsCheckboxControl" value="0" scope="request"/>
	<%@include file="../category/inc_asset_category_head_js.jsp"%>

	<bean:define id="section" value="import"/>
	
	<%@ include file="../inc/inc_entities_js.jsp"%>


</head>

<body id="importPage" onload="setDescSelectedCategories(); setPermSelectedCategories(); setCatIdsFields();  showHideAgreementType(); syncAgreementPreviewButton();">

	<%@include file="../inc/body_start.jsp"%>
	
	
	<h1>
		<bright:cmsWrite identifier="heading-add-placeholders" filter="false" /> 
		<c:if test="${not empty importForm.asset.entity && not empty importForm.asset.entity.name}">
			(<bean:write name="importForm" property="asset.entity.name" filter="false"/>)
		</c:if>
	</h1>  
	
	<logic:equal name="importForm" property="hasErrors" value="true">
		<div class="error">
			<bright:cmsWrite identifier="snippet-errors-starting-import" filter="false"/> <br />
			<logic:iterate name="importForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>		
		</div>
	</logic:equal>
	
	<html:form action="runAddPlaceholders" enctype="multipart/form-data" method="post">				
	
		<html:hidden name="importForm" property="parentId"/>
		
		<%--  Select entity: may be preselected in which case do not show the dropdown, just pass through --%>
		<html:hidden name="importForm" property="entityPreSelected"/>
		
		<c:choose>
			<c:when test="${importForm.selectedAssetEntityId le 0}">
				<div class="hr"></div>
				<h2><bright:cmsWrite identifier="heading-choose-type" filter="false"/></h2>
				<table class="admin">
					<tr>
						<td>
							<bright:cmsWrite identifier="label-asset-type" filter="false"/>
						</td>
						<td>
							<bean:define id="entities" name="importForm" property="entities"/>
							<html:select name="importForm" property="selectedAssetEntityId" styleId="entity" style="width:auto;" onchange="applyEntitySelection();">
								<html:optionsCollection name="entities" value="id" label="name"/>
							</html:select>
						</td>
					</tr>
				</table>
			</c:when>
			<c:otherwise>
				<html:hidden name="importForm" property="selectedAssetEntityId"/>
			</c:otherwise>
		</c:choose>

		<%--  Number to add --%>
		<div class="hr"></div>
		<h2><bright:cmsWrite identifier="subhead-num-placeholders" filter="false"/></h2>
		<table class="admin">
			<tr>
				<td>
					<bright:cmsWrite identifier="label-num-placeholders" filter="false"/>
				</td>
				<td>
					<html:text name="importForm" property="numAssetsToAdd.formNumber" />
				</td>
			</tr>
		</table>

		<div class="hr"></div>
	
		<%--  Asset Metadata --%>				
		<c:set var="sIsImport" scope="request" value="true"/>
		<c:set var="uploading" scope="request" value="true"/>
		<c:set var="assetForm" scope="request" value="${importForm}"/>
		<jsp:include page="inc_fields.jsp"/>
		

		<div class="hr"></div>
		
		<div class="centered">
			<input type="submit" class="button" id="submitButton" value="<bright:cmsWrite identifier="button-start-import" filter="false" />" /> 
		</div>
	</html:form>
	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>
