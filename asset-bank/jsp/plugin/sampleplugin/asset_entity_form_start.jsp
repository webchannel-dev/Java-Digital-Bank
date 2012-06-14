<%--
	Form start include for asset entity description plugin.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>
			<tr>
				<th style="vertical-align:top; padding-top: 3px;"><label for="ext_sampleplugin_description">Description:</label></th>
				<td>
					<html:text styleClass="text" name="assetEntityForm" styleId="ext_sampleplugin_description" property="ext(sampleplugin_description)" maxlength="255"/>
				</td>
			</tr>
