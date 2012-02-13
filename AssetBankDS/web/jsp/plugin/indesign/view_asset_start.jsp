<%--
	Asset view start include for InDesign plugin.
	Gets included by the <abplugin:include-view-extensions> tag in jsp/standard/en/public/inc_attribute_fields_with_extensions.jsp
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<c:if test="${indesign_inDAsset.document}">
	<table class="form stripey" cellspacing="0" cellpadding="0">
		<tr>
			<th><bright:cmsWrite identifier="label-indesign-template-asset"/>:</th>
			<td>
				<c:choose>
					<c:when test="${empty indesign_templateName}">
						<bright:cmsWrite identifier="snippet-asset-is-indesign-template"/>
					</c:when>
					<c:otherwise>
						<a href="<%= request.getContextPath() %>/action/viewAsset?id=<c:out value="${indesign_inDAsset.templateAssetId}"/>">
						<c:out value="${indesign_templateName}"/></a>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<th><bright:cmsWrite identifier="snippet-indesign-pdf-status"/>:</th>
			<td>
				<c:out value="${indesign_inDAsset.pdfStatus}"/>
			</td>
		</tr>
	</table>
	<p>&nbsp;</p>
</c:if>