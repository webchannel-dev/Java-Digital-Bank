<%--
 Parameters:
	separatorBefore		If "true", then a vertical bar separator will be output before any other output from this include.
					 	Optional, defaults to "false".
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<bean:parameter id="separatorBefore" name="separatorBefore" value="false"/>
<c:if test="${assetForm.asset.extendsCategory.id > 0}">
	<c:set var="browseUrl" value="../action/browseItems" />
	<c:choose>
		<c:when test="${assetForm.asset.extendsCategory.categoryTypeId == 2}">
			<c:set var="returnUrl" value="/action/viewUpdatePermissionCategory?categoryId=${assetForm.asset.extendsCategory.id}&parentId=${assetForm.asset.extendsCategory.parentId}" />
			<c:if test="${categoryExplorerType == 'accesslevels'}">
				<c:set var="browseUrl" value="../action/viewHome" />
			</c:if>
		</c:when>
		<c:otherwise>
			<c:set var="returnUrl" value="/action/viewUpdateCategory?categoryId=${assetForm.asset.extendsCategory.id}&parentId=${assetForm.asset.extendsCategory.parentId}" />
			<c:if test="${categoryExplorerType == 'categories'}">
				<c:set var="browseUrl" value="../action/viewHome" />
			</c:if>
		</c:otherwise>
	</c:choose>
	<c:set var="catName" value="${assetForm.asset.extendsCategory.name}" />
	<c:set var="link" value="${browseUrl}?categoryId=${assetForm.asset.extendsCategory.id}&categoryTypeId=${assetForm.asset.extendsCategory.categoryTypeId}" />
	<c:if test="${separatorBefore == 'true'}">
		|
	</c:if>
	<bright:cmsWrite identifier="snippet-asset-is-category-extension" filter="false" replaceVariables="true" />
</c:if>
