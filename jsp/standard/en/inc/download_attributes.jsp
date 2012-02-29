<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="showLabel" settingName="show-attribute-label-on-download"/>

<c:if test="${not empty downloadForm.downloadAttributes}">
<div class="downloadAttributes">
	<c:set var="introText"><bright:cmsWrite identifier="copy-download-attributes" filter="false"/></c:set>
	<c:if test="${not empty introText}">
		<div><bean:write name="introText" filter="false"/></div>
	</c:if>
	<bean:size id="numAttributes" name="downloadForm" property="downloadAttributes"/>
	<c:choose>	
		<c:when test="${numAttributes>1}">
			<table class="form stripey" cellpadding="0" cellspacing="0" border="0">
		</c:when>
		<c:otherwise>
			<table class="singleAttribute" cellpadding="0" cellspacing="0" border="0">
		</c:otherwise>
	</c:choose>
		<logic:iterate id="attributeValue" name="downloadForm" property="downloadAttributes">
			<c:set var="attributeValue" scope="request" value="${attributeValue}"/>
			<c:set var="reqHideLabels" scope="request" value="${hideLabels || (numAttributes==1 && !showLabel)}"/>
			<c:set var="assetIdForAttributes" scope="request" value="${downloadForm.asset.id}"/>
			<jsp:include flush="true" page="../public/inc_view_attribute_field.jsp"/>
		</logic:iterate>
	</table>

</div>
</c:if>