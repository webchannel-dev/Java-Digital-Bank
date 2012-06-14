<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%-- following code sets the value of asset to be item when we dont have an asset - solves the problem that has been caused by using item in some places and asset in others --%>
<logic:notPresent name="setAsset">
	<bean:define id="setAsset" value="false"/>
	<logic:notPresent name="asset">
		<bean:define id="setAsset" value="true"/>
	</logic:notPresent>
</logic:notPresent>
<logic:equal name="setAsset" value="true">
	<c:set var="asset" value="${item}"/>
</logic:equal>
<logic:notPresent name="disablePreview">
	<bean:define id="disablePreview" value="false"/>
</logic:notPresent>

<bright:applicationSetting id="restrictThumb" settingName="restricted-image-thumbnail"/>
<bright:applicationSetting id="sensitiveThumb" settingName="sensitive-image-thumbnail"/>
<bright:applicationSetting id="showHover" settingName="search-results-hover-preview"/>
<bright:applicationSetting id="hideRestrictedAssetImages" settingName="hide-restricted-asset-images"/>


<%@include file="../inc/restrict_preview_check.jsp"%>
<logic:notPresent name="resultImgClass">
	<bean:define id="resultImgClass" value="image"/>
</logic:notPresent>
<c:set var="showSensitive" value="false"/>
<logic:match name="userprofile" property="clickedOnSensitiveImage[${asset.id}]" value="true">
	<c:set var="showSensitive" value="true"/>
</logic:match>
<c:choose>
	<c:when test="${restrict == true}">
		<c:set var="thumbSrc" value="..${restrictThumb}"/>
		<c:set var="resultImgClass" value="icon"/>
	</c:when>
	<c:when test="${asset.isImage && !userprofile.isAdmin && asset.isSensitive && !showSensitive}">
		<c:set var="thumbSrc" value="..${sensitiveThumb}"/>
	</c:when>
	<c:otherwise>
		<c:set var="thumbSrc" value="../servlet/display?file=${asset.displayThumbnailImageFile.path}"/>
	</c:otherwise>
</c:choose>

<c:set var="renderHover" value="${showHover && asset.hasConvertedImages && !downloadAsDocument && !disablePreview && (!hideRestrictedAssetImages || userprofile.isAdmin || !asset.isRestricted) && (userprofile.isAdmin || !asset.isSensitive)}"/>

<img class="<bean:write name='resultImgClass'/>" src="<bean:write name='thumbSrc'/>" alt="<bean:write name='asset' property='searchName'/>" <c:if test="${renderHover}">onmouseover="showPreview(this,'<bean:write name="asset" property="id"/>','<bean:write name="asset" property="displayPreviewImageFile.path"/>'); return false;" onmouseout="hidePreview('<bean:write name="asset" property="id"/>'); return false;"</c:if> />	


<c:if test="${renderHover}">
	<span class="loading" id="load_<bean:write name='asset' property='id'/>">
		<img src="../images/standard/misc/ajax_loader_small.gif" alt="" class="loading"  />
	</span>	
	<span class="larger">
		<span class="inner" id="id_<bean:write name='asset' property='id'/>"></span>
	</span>
</c:if>

<logic:present name="downloadForm">
	<logic:notEmpty name="downloadForm" property="assetApproval">
		<logic:notEmpty name="downloadForm" property="assetApproval.adminNotes">
			<p style="margin-top: 10px;"><strong><bright:cmsWrite identifier="label-download-approval-notes" filter="false"/></strong><br/><bean:write name="downloadForm" property="assetApproval.adminNotes"/></p>
		</logic:notEmpty>
	</logic:notEmpty>
</logic:present>
