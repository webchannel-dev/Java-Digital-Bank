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

<c:if test="${not empty indesign_inDDAsset}">
	<table class="form stripey" cellspacing="0" cellpadding="0">
		<%-- assetForm.userCouldUpdateAssetIfBatchReleasesWereIgnored check is done so that the Template field is hidden from Supplier users in the Kenwood Bluebooks Asset Bank --%>
		<c:if test="${assetForm.userCouldUpdateAssetIfBatchReleasesWereIgnored}">
		<tr>
			<th><bright:cmsWrite identifier="label-indesign-template-asset"/>:</th>
			<td>
				<c:choose>
					<c:when test="${empty indesign_templateName}">
						<bright:cmsWrite identifier="snippet-asset-is-indesign-template"/>
					</c:when>
					<c:otherwise>
						<a href="<%= request.getContextPath() %>/action/viewAsset?id=<c:out value="${indesign_inDDAsset.templateAssetId}"/>">
						<c:out value="${indesign_templateName}"/></a>
						<c:if test="${indesign_inDDAsset.overridesTemplate}">
							<bright:cmsWrite identifier="snippet-indesign-asset-overrides-template"/>
						</c:if>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		</c:if>
		<tr>
			<th><bright:cmsWrite identifier="snippet-indesign-pdf-status"/>:</th>
			<td>
				<c:out value="${indesign_inDDAsset.pdfStatus}"/>	
			</td>
		</tr>
		<c:if test="${not empty indesign_inDDAsset.errorMessages}">
			<div id="pdfErrors">
				<c:forEach var="message" items="${indesign_inDDAsset.errorMessages}" varStatus="status">
					<tr>
						<th>
						<c:if test="${status.first}">
							<bright:cmsWrite identifier="snippet-indesign-pdf-errors"/>:
						</c:if>
						</th>
						<td>${message}</td>
					</tr>
				</c:forEach>
			</div>
		</c:if>
	</table>
	<p>&nbsp;</p>
</c:if>
