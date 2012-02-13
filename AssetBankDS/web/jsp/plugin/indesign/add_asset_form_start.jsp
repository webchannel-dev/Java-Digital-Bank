<%--
	Asset form start include for InDesign plugin.
	Gets included by the <abplugin:include-form-extensions> tag in jsp/standard/en/asset-admin/inc_fields.jsp
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>
<c:if test="${indesign_inDAssetEntity.document}">
	<tr>
		<th><label for="ext_indesign_templateAssetId"><bright:cmsWrite identifier="label-indesign-template-asset"/>:</label></th>
		<td></td>
		<td>
			<html:select styleClass="text" name="assetForm" styleId="ext_indesign_templateAssetId" property="ext(indesign_templateAssetId)">
				<html:option value="-1"><bright:cmsWrite identifier="snippet-indesign-create-new-template"/></html:option>
				<html:optionsCollection name="indesign_templateAssets" label="searchName" value="id"/>
			</html:select>
		</td>
	</tr>
</c:if>
<%@include file="edit_asset_form_start.jsp"%>
