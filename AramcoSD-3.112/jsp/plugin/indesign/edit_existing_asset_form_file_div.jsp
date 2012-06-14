<%--
	Asset form file div include for InDesign plugin.
	Gets included by the <abplugin:include-form-extensions> tag in jsp/standard/en/asset-admin/inc_fields.jsp
--%>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<c:if test="${not empty indesign_inDAssetEntity and indesign_inDAssetEntity.document}">
	<div id="fileInfo" class="infoInline">
		<bright:cmsWrite identifier="snippet-indesign-edit-file-info" filter="false"/>
	</div>

</c:if>