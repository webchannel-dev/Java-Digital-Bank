<%--
	Asset Entity form start include for InDesign plugin.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>
<tr>
	<th style="vertical-align:top; padding-top: 3px;"><label for="ext_indesign_inDesignAssetEntityTypeId">InDesign Type:</label></th>
	<td>
		<html:select styleClass="text" name="assetEntityForm" styleId="ext_indesign_inDesignAssetEntityTypeId" property="ext(indesign_inDesignAssetEntityTypeId)">
			<html:option value="-1">None</html:option>
			<html:optionsCollection name="indesign_assetEntityTypes" label="name" value="id"/>
		</html:select>
	</td>
</tr>
