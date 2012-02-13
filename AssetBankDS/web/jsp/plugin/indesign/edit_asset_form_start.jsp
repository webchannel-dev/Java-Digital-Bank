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
<c:if test="${indesign_inDAssetEntity.document}">
	<tr>
		<th><label for="ext_indesign_pdfQuality"><bright:cmsWrite identifier="label-indesign-pdf-quality"/>:</label></th>
		<td></td>
		<td>
			<html:select styleClass="text" name="assetForm" styleId="ext_indesign_pdfQuality" property="ext(indesign_pdfQuality)">
				<html:option value="-1"><bright:cmsWrite identifier="snippet-default-option"/></html:option>
				<html:optionsCollection name="indesign_pdfQualities" label="name" value="id"/>
			</html:select>
		</td>
	</tr>
	<%-- BBKTODO replace this file upload field with auto edit in InDesign --%>
	<tr>
		<th><label for="ext_indesign_documentFile">InDesign Document:</label></th>
		<td></td>
		<td>
			<html:file name="assetForm" property="ext(indesign_documentFile)" styleClass="file" size="35" styleId="ext_indesign_documentFile"/>
			(<bright:cmsWrite identifier="current-file"/>: <bean:write name="assetForm" property="ext(indesign_documentFilename)"/>)
		</td>
	</tr>
</c:if>