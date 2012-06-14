<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

<h1>
	<c:if test="${empty assetForm.asset.entity.name}">
		<c:choose>
			<c:when test="${assetForm.asset.isBrandTemplate}">
				<bright:cmsWrite identifier="heading-brand-template-details" filter="false" />
			</c:when>
			<c:when test="${assetForm.asset.typeId==1}">
				<bright:cmsWrite identifier="heading-asset-details" filter="false" />
			</c:when>
			<c:when test="${assetForm.asset.typeId==2}">
				<bright:cmsWrite identifier="heading-image-details" filter="false" />
			</c:when>
			<c:when test="${assetForm.asset.typeId==3}">
				<bright:cmsWrite identifier="heading-video-details" filter="false" />
			</c:when>
			<c:when test="${assetForm.asset.typeId==4}">
				<bright:cmsWrite identifier="heading-audio-details" filter="false" />
			</c:when>
		</c:choose>
	</c:if>
	<c:if test="${not empty assetForm.asset.entity.name}">
		<bean:write name="assetForm" property="asset.entity.name" /> <bright:cmsWrite identifier="snippet-details" case="mixed" filter="false" />
	</c:if>
</h1> 