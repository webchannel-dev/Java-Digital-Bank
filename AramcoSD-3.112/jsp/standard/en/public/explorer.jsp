<%-- History:
	 d1		Ben Browning		13-Apr-2011		Created to enable lazy loading of explorer.	
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/ab-tag.tld" prefix="ab" %>

<bright:applicationSetting settingName="useCategoryExplorer" id="categoryExplorerType" />

<c:set var="type"><bright:cmsWrite identifier="category-node"/></c:set>
<c:set var="head"><bright:cmsWrite identifier="category-root" filter="false" /></c:set>
<c:if test="${categoryExplorerType == 'accesslevels'}">
	<c:set var="type"><bright:cmsWrite identifier="access-level-node"/></c:set>
	<c:set var="head"><bright:cmsWrite identifier="access-level-root" filter="false" /></c:set>
</c:if>
<div class="homepageExplorer">
	
	<div id="fadeBottom"></div>
	<div class="explorerCatWrapper">
		<c:set var="catName"><bright:cmsWrite identifier="category-root" filter="false" /></c:set>
		<h3><bright:cmsWrite identifier="heading-browse" filter="false"/> <bean:write name='head' />:</h3>
		<c:choose>
			<c:when test="${userprofile.selectedCategoryId > 0 && categoryExplorerType == 'categories'}">
				<bean:define name="userprofile" property="selectedCategoryId" id="categoryIdBean"/>
			</c:when>
			<c:when test="${userprofile.selectedAccessLevelId > 0 && categoryExplorerType == 'accesslevels'}">
				<bean:define name="userprofile" property="selectedAccessLevelId" id="categoryIdBean"/>
			</c:when>
			<c:otherwise>
				<bean:define id="categoryIdBean" value="0"/>
			</c:otherwise>
		</c:choose>
		<ab:categoryExplorer link="browseExplorerItems?categoryId=&categoryTypeId=" className="explorer" categoryTypeId="${categoryExplorerType == 'accesslevels'?2:1}" selectedCategoryIdBean="categoryIdBean" />
		<br /><br />
	</div>
</div>
<div id="categoryContent" class="homePageBrowse">
	<h3><bright:cmsWrite identifier="snippet-category-explorer" filter="false"/></h3>
</div>