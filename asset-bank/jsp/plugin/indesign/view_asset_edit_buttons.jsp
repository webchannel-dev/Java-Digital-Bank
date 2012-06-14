<%--
	Asset edit buttonsinclude for InDesign plugin.
	Gets included by the <abplugin:include-view-extensions in inc_view_asset_buttons.jsp
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<bright:applicationSetting id="batchReleasesEnabled" settingName="batch-releases-enabled"/>

<c:if test="${not empty indesign_inDDAsset}">
	<c:if test="${indesign_inDDAsset.templateCanBeOverridden}">
		<li>
			<form action="../action/inDesignOverrideTemplate" method="get">
				<bright:cmsSet var="submitText" identifier="button-indesign-override-template"/>
				<bright:submitButton disabled="${backgroundEditInProgress}" styleId="inDesignOverrideTemplateButton" value="${submitText}" onclick="return confirmOverrideTemplate();"/>

				<input type="hidden" name="id" value="<bean:write name='assetForm' property='asset.id'/>" />
			</form>
			<script type="text/javascript">
				function confirmOverrideTemplate()
				{
					return confirm('<bright:cmsWrite identifier="js-confirm-indd-override-template" filter="false"/>');
				}
			</script>
		</li>
	</c:if>

	<c:if test="${indesign_inDDAsset.canMakeTemplated}">
		<li>
			<form action="../action/inDesignViewMakeTemplated" method="get">
				<bright:cmsSet var="submitText" identifier="button-indesign-make-templated"/>
				<bright:submitButton disabled="${backgroundEditInProgress}" styleId="inDesignMakeTemplatedButton" value="${submitText}"/>

				<input type="hidden" name="assetId" value="<bean:write name='assetForm' property='asset.id'/>" />
			</form>
		</li>
	</c:if>

	<li>
		<form action="../action/inDesignRegeneratePDF" method="get">
			<bright:cmsSet var="submitText" identifier="button-indesign-regenerate-pdf"/>
			<bright:submitButton disabled="${backgroundEditInProgress}" styleId="inDesignRegeneratePDFButton" value="${submitText}"/>

			<input type="hidden" name="id" value="<bean:write name='assetForm' property='asset.id'/>" />
		</form>
	</li>
	<li>
		<form action="../action/inDesignDownloadInDesignDocument" method="get">
			<input id="inDesignDownloadInDesignDocumentButton" class="button" type="submit" value="<bright:cmsWrite identifier="button-indesign-download-indd"/>"/>
			<input type="hidden" name="id" value="<bean:write name='assetForm' property='asset.id'/>" />
		</form>
	</li>
</c:if>
