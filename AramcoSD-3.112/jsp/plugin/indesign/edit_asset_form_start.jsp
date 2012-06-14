<%--
	Asset form start include for InDesign plugin.
	Shared by add_asset_form_start.jsp and edit_existing_asset_form_start.jsp
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<%-- Include the PDF quality dropdown if we are adding OR if we are editing
and the asset being edited has a document. If we are adding then JavaScript
in add_asset_form_start.jsp will hide this dropdown if we are not adding a
template. If we are editing then no JavaScript is needed. --%>
<c:if test="${not empty indesign_inDAssetEntity and indesign_inDAssetEntity.document and (assetForm.asset.id <= 0 or indesign_hasDocument)}">
	<tr id="ext_indesign_pdfQuality" class="toggle">
		<th><label for="ext_indesign_pdfQuality"><bright:cmsWrite identifier="label-indesign-pdf-quality"/>:</label></th>
		<td></td>
		<td>
			<html:select styleClass="text" name="assetForm" styleId="ext_indesign_pdfQuality" property="ext(indesign_pdfQuality)">
				<html:option value="-1"><bright:cmsWrite identifier="snippet-default-option"/></html:option>
				<html:optionsCollection name="indesign_pdfQualities" label="name" value="id"/>
			</html:select>
		</td>
	</tr>
</c:if>
