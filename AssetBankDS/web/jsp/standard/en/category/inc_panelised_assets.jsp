<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
	<bright:applicationSetting id="categoryExtensionAssetsEnabled" settingName="category-extension-assets-enabled"/>
	
	<logic:notPresent name="explorer">
		<c:set var="explorer" value="false" />
	</logic:notPresent>
	
	<%-- Extension asset block - shows category extension display attributes at the top of categories when they have an extension asset --%>
	<c:if test="${categoryExtensionAssetsEnabled && browseItemsForm.extensionAsset != null}">
		<c:set var="displayAttributeGroupOld" value="${displayAttributeGroup}" />
		<c:set var="displayAttributeGroup" value="4" scope="request" />
		<div id="extensionasset">
			<bean:define id="item" name="browseItemsForm" property="extensionAsset" />
			<bean:define id="firstIsHeader" value="true" />
			<%@include file="../inc/result_asset_descriptions.jsp" %>
			<c:choose>
				<c:when test="${explorer}">
					<c:set var="imageDetailReturnUrl" scope="session" value="/action/viewHome?categoryId=${browseItemsForm.category.id}&categoryTypeId=${browseItemsForm.category.categoryTypeId}"/>
				</c:when>
				<c:otherwise>
					<c:set var="imageDetailReturnUrl" scope="session" value="/action/browseItems?categoryId=${browseItemsForm.category.id}&categoryTypeId=${browseItemsForm.category.categoryTypeId}"/>
					<c:set var="imageDetailReturnName" scope="session" value="Browse" />
				</c:otherwise>
			</c:choose>
			<p><a href="viewAsset?id=<bean:write name='item' property='id' />"><bright:cmsWrite identifier="link-view-details" filter="false" /></a></p>
			<c:set var="displayAttributeGroup" value="${displayAttributeGroupOld}" scope="request" />
		</div>
	</c:if>
	
	<%-- control panel - sorting / paging & links --%>
	
	<c:set var="browseAction" value="browseItems"/>
	
	<c:set var="linkUrl" value="browseItems?categoryId=${browseItemsForm.category.id}&categoryTypeId=${browseItemsForm.category.categoryTypeId}&allCats=${allCats}&sortAttributeId=${browseItemsForm.sortAttributeId}&sortDescending=${browseItemsForm.sortDescending}"/>
	<bean:size id="noOfPanels" name="browseItemsForm" property="panels" />
	
	<%@include file="inc_browse_category_controls.jsp" %>
	
	<%-- browse panels - collections of assets organised into panels --%>

	<logic:iterate name="browseItemsForm" property="panels" id="panel" indexId="index">

		<c:if test="${panel.populated || panel.visibilityStatus == 3}">
			<%-- the panel is populated or we have been told to show it when its empty --%>
			<c:choose>
				<c:when test="${panel.assets != null}">
					<bean:size name="panel" property="assets" id="numResults" />
				</c:when>
				<c:otherwise>
					<bean:define value="0" id="numResults" />
				</c:otherwise>
			</c:choose>
			<c:set var="results" value="${panel.assets}" />	
			<bean:define id="firstIsHeader" value="false" />

			<div class="panel">
				<%@include file="../inc/browse.jsp"%>
			</div>

		</c:if>
	</logic:iterate>