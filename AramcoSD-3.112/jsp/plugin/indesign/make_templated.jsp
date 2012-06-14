<%@include file="/jsp/standard/en/inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<bean:define id="section" value=""/>
	<bean:define id="helpsection" value="indesign_make_templated"/>

	<c:set var="pagetitle"><bright:cmsWrite identifier="title-make-templated"/></c:set>
	<c:set var="pageheading"><bright:cmsWrite identifier="heading-make-templated"/></c:set>

	<title><c:out value="${pagetitle}"/></title>
	<%@include file="/jsp/standard/en/inc/head-elements.jsp"%>

	<script type="text/javascript">
		$j(document).ready(function() {
			<%-- output a JavaScript object literal with a mapping of entity ID to a list of template IDs that are allowed for that entity--%>
			<%-- escapeXml="false" because allowedTemplateIdsByEntityJson is an already-escaped Json string --%>
			var allowedTemplateIdsByEntity = <c:out value="${allowedTemplateIdsByEntityJson}" escapeXml="false"/>;
			var allTemplateOptions = $j("#templateAssetId > option");

			var restrictTemplatesByEntity = function() {
				var selectedAssetEntityId = $j("#assetEntityId").val();
				var allowedTemplateIds = allowedTemplateIdsByEntity[selectedAssetEntityId];
				if (allowedTemplateIds === undefined) {
					// not in the map at all
					allowedTemplateIds = [];
				}

				// Detach and re-attach the options instead of hiding/unhiding
				// because hiding/unhiding <option>s doesn't work in a lot of
				// browsers.
				detachAllTemplateOptions();
				appendAllowedTemplateOptions(allowedTemplateIds);

				showWarningIfNoTemplates(allowedTemplateIds);
			};

			var detachAllTemplateOptions = function() {
				allTemplateOptions.each(function() {
					$j(this).detach();
				});
			};

			var appendAllowedTemplateOptions = function(allowedTemplateIds) {
				var select = $j("#templateAssetId");
				allTemplateOptions.each(function() {
					var option = $j(this);
					var sTemplateId = option.attr("value");
					var templateId = parseInt(sTemplateId);

					if ($j.inArray(templateId, allowedTemplateIds) >= 0) {
						select.append(option);
					}
				});
			};

			var showWarningIfNoTemplates = function (allowedTemplateIds) {
				var noTemplatesWarning = $j("#noTemplatesWarning");
				if (allowedTemplateIds.length > 0)
				{
					noTemplatesWarning.addClass("hidden");
				}
				else
				{
					noTemplatesWarning.removeClass("hidden");
				}
			};

			restrictTemplatesByEntity();

			$j("#assetEntityId").change(restrictTemplatesByEntity);
		});
	</script>
</head>

<body>
	<%@include file="/jsp/standard/en/inc/body_start.jsp"%>

	<h1 class="underline"><bean:write name="pageheading"/></h1>

	<html:form action="inDesignMakeTemplated" method="POST" styleClass="floated">
		<html:hidden property="assetId"/>

		<html:errors />
		<label for="assetEntityId"><bright:cmsWrite identifier="label-asset-type"/></label>
		<html:select property="assetEntityId" styleId="assetEntityId" style="width:auto;">
			<html:optionsCollection name="documentEntities" value="id" label="name"/>
		</html:select>
		<br />

		<label for="templateAssetId"><bright:cmsWrite identifier="label-indesign-template-asset"/>:</label>
		<html:select property="templateAssetId" styleId="templateAssetId">
			<html:optionsCollection name="templateAssets" label="searchName" value="id"/>
		</html:select>
		<div id="noTemplatesWarning" class="warningInline hidden floatLeft"><bright:cmsWrite identifier="snippet-indesign-no-templates-for-asset-entity"/></div>

		<br />

		<div class="hr"></div>

		<input type="submit" class="button" value="<bright:cmsWrite identifier="button-save" filter="false" />" />
		<a href="viewAsset?id=<bean:write name="inDesignMakeTemplatedForm" property="assetId"/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel"/></a>

	</html:form>

	<%@include file="/jsp/standard/en/inc/body_end.jsp"%>
</body>
</html>
