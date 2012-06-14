<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
	
<bright:applicationSetting id="multipleLightboxesAdminOnly" settingName="multiple-lightboxes-admin-only"/>
<bright:applicationSetting id="multipleLightboxes" settingName="multiple-lightboxes"/>
<bright:applicationSetting id="publicLightboxes" settingName="public-lightboxes"/>
			
<c:choose>
	<c:when test="${userprofile.isLoggedIn && multipleLightboxes && (userprofile.isAdmin || !multipleLightboxesAdminOnly)}">
		<h1>
			<c:choose>
				<c:when test="${assetBoxForm.shared==true}">
					<bright:cmsWrite identifier="title-rename-lightbox" case="mixed" />
				</c:when>
				<c:when test="${assetBoxForm.publicPage==-1 || assetBoxForm.publicPage==0}">
					<bean:write name="userprofile" property="assetBox.name"/>
				</c:when>
				<c:when test="${assetBoxForm.publicPage==1 && not empty publicAssetBoxName}">
					<c:out value="${publicAssetBoxName}" />
				</c:when>
			</c:choose>
		</h1>
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
				<c:if test="${publicLightboxes}">
					<c:choose>
						<c:when test="${tabId == 'publicAssetBox'}">
							<h2 class="current"><bright:cmsWrite identifier="tab-public-lightboxes"/></h2>
						</c:when>
						<c:otherwise>
							<h2><a href="viewPublicAssetBoxes"><bright:cmsWrite identifier="tab-public-lightboxes"/></a></h2>
						</c:otherwise>		
					</c:choose>
				</c:if>
				<div class="tabClearing">&nbsp;</div>
		</div>
		<bean:define id="tabsPresent" value="true" toScope="request" />
	</c:when>
	<c:otherwise>
		<h1><bright:cmsWrite identifier="my-lightbox" filter="false"/></h1>
		<bean:define id="tabsPresent" value="false" toScope="request" />
	</c:otherwise>
	
</c:choose>