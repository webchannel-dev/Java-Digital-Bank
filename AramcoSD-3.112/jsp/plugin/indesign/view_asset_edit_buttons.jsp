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
	<c:if test="${indesign_inDDAsset.beingUpdated}">
		<script type="text/javascript" charset="utf-8">
			function refreshPage(){
				location.reload();
			}
			setInterval( refreshPage, 5000 );		// Refresh the page every 5 seconds
		</script>
	</c:if>

	<c:if test="${indesign_inDDAsset.templateCanBeOverridden}">
		<li>
			<form action="../action/inDesignOverrideTemplate" method="get">
				<c:choose>
					<c:when test="${indesign_inDDAsset.documentUpdateInProgress}">
						<input id="inDesignOverrideTemplateButton" class="button disabled" disabled="disabled" type="submit" value="<bright:cmsWrite identifier="button-indesign-override-template"/>" onclick="return confirmOverrideTemplate();"/>
						<span style="line-height:16px"><img src="../images/standard/misc/ajax_loader_small.gif" width="16" height="16" alt="Loading"  style="vertical-align:middle" /></span>
					</c:when>
					<c:otherwise>
						<input id="inDesignOverrideTemplateButton" class="button" type="submit" value="<bright:cmsWrite identifier="button-indesign-override-template"/>" onclick="return confirmOverrideTemplate();"/>
					</c:otherwise>
				</c:choose>

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
				<c:choose>
					<c:when test="${indesign_inDDAsset.documentUpdateInProgress}">
						<input id="inDesignMakeTemplatedButton" class="button disabled" disabled="disabled" type="submit" value="<bright:cmsWrite identifier="button-indesign-make-templated"/>" onclick="return confirmMakeTemplated();"/>
						<span style="line-height:16px"><img src="../images/standard/misc/ajax_loader_small.gif" width="16" height="16" alt="Loading"  style="vertical-align:middle" /></span>
					</c:when>
					<c:otherwise>
						<input id="inDesignMakeTemplatedButton" class="button" type="submit" value="<bright:cmsWrite identifier="button-indesign-make-templated"/>" onclick="return confirmMakeTemplated();"/>
					</c:otherwise>
				</c:choose>

				<input type="hidden" name="assetId" value="<bean:write name='assetForm' property='asset.id'/>" />
			</form>
			<script type="text/javascript">
				function confirmMakeTemplated()
				{
					return confirm('<bright:cmsWrite identifier="js-confirm-indd-make-templated" filter="false"/>');
				}
			</script>
		</li>
	</c:if>

	<li>
		<form action="../action/inDesignRegeneratePDF" method="get">
			<c:choose>
				<c:when test="${indesign_inDDAsset.pdfInProgress}">
					<input id="inDesignRegeneratePDFButton" class="button disabled" disabled="disabled" type="submit" value="<bright:cmsWrite identifier="button-indesign-regenerate-pdf"/>"/>
					<span style="line-height:16px"><img src="../images/standard/misc/ajax_loader_small.gif" width="16" height="16" alt="Loading"  style="vertical-align:middle" /> <c:out value="${indesign_inDDAsset.pdfStatus}"/></span>
				</c:when>
				<c:otherwise>
					<input id="inDesignRegeneratePDFButton" class="button" type="submit" value="<bright:cmsWrite identifier="button-indesign-regenerate-pdf"/>"/>
				</c:otherwise>
			</c:choose>
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
