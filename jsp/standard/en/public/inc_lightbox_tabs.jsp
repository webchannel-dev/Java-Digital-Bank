<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
	
<bright:applicationSetting id="multipleLightboxesAdminOnly" settingName="multiple-lightboxes-admin-only"/>
<bright:applicationSetting id="multipleLightboxes" settingName="multiple-lightboxes"/>

<c:choose>
	<c:when test="${userprofile.isLoggedIn && multipleLightboxes && (userprofile.isAdmin || !multipleLightboxesAdminOnly)}">
		<h1><bright:cmsWrite identifier="a-lightbox" filter="false" case="mixed" /></h1>
		<c:choose>
			<c:when test="${userprofile.numAssetBoxes>1}"> 
				<c:set var="assetboxTitle"><bright:cmsWrite identifier="tab-current-lightbox" filter="false"/></c:set>
			</c:when>
			<c:otherwise>
				<c:set var="assetboxTitle"><bright:cmsWrite identifier="my-lightbox" filter="false"/></c:set>
			</c:otherwise>
		</c:choose>			
		<div class="adminTabs">
			<c:choose>
				<c:when test="${tabId == 'currentAssetBox'}">
					<h2 class="current"><bean:write name="assetboxTitle" filter="false"/></h2>
				</c:when>
				<c:otherwise>
					<h2><a href="viewAssetBox"><bean:write name="assetboxTitle" filter="false"/></a></h2>
				</c:otherwise>		
			</c:choose>
			<c:choose>
				<c:when test="${tabId == 'manageAssetBoxes'}">
					<h2 class="current"><bright:cmsWrite identifier="tab-manage-lightboxes" filter="false"/></h2>
				</c:when>
				<c:otherwise>
					<h2><a href="viewManageAssetBoxes"><bright:cmsWrite identifier="tab-manage-lightboxes" filter="false"/></a></h2>
				</c:otherwise>		
			</c:choose>
			<div class="tabClearing">&nbsp;</div>
		</div>
		<bean:define id="tabsPresent" value="true" toScope="request" />
	</c:when>
	<c:otherwise>
		<h1><bright:cmsWrite identifier="my-lightbox" filter="false"/></h1>
		<bean:define id="tabsPresent" value="false" toScope="request" />
	</c:otherwise>
	
</c:choose>