<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		28-Sep-2007		Created.
	d2		Matt Woollard	01-Apr-2009		Added can download children from parent option
	d3		Matt Stevenson	18-Aug-2010		Refactored relationships section to new page	
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://www.assetbank.co.uk/taglib/abplugin" prefix="abplugin" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>
<bright:applicationSetting id="agreementsEnabled" settingName="agreements-enabled"/>
<bright:applicationSetting id="categoryExtensionAssetsEnabled" settingName="category-extension-assets-enabled"/>
<bright:applicationSetting id="canCopyAssets" settingName="can-create-asset-copies" />

<%-- Create a Map containing the constants defined in AttributeConstants --%>
<un:useConstants var="attributeConstants" className="com.bright.assetbank.attribute.constant.AttributeConstants" />

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | <c:if test="${assetEntityForm.entity.id>0}">Edit</c:if><c:if test="${assetEntityForm.entity.id<=0}">Add</c:if> Asset Type</title>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="assetEntity"/>
	<bean:define id="pagetitle" value="Asset Type"/>
	<bean:define id="helpsection" value="asset-entities"/>
	
	<script type="text/javascript">
	//<!--

	var oldBackColor = "";
		
	function onFormSubmit()
	{
		<c:if test="${assetEntityForm.entity.id>0}">
			return confirm('Are you sure that you want to save this Asset Type? If you have removed attributes from the asset type then there is the potential for data to be lost.');
		</c:if>
		<c:if test="${assetEntityForm.entity.id<=0}">
			return true;
		</c:if>
	}

	function showHideAgreementFields(ctrl)
	{
		if(ctrl.value=='400' && ctrl.checked)
		{
			document.getElementById('agreementFields').style.display='block';
		}
		else if(ctrl.value=='400' && !ctrl.checked)
		{
			document.getElementById('agreementFields').style.display='none';
		}
	}
	

	$j(function () {
		// give the name field the focus once the dom is ready
		$j('#name').focus();

		
		$j('#catExtensionType').click( function() {
			//alert('test');
			$j('#catAttribute').toggle();
		});
		
		if ($j('#catExtensionType').attr('checked')) {
			$j('#catAttribute').hide();
		}
		
	});
		
	// -->
	</script>
</head>
<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	
	<h2><c:if test="${assetEntityForm.entity.id>0}">Edit</c:if><c:if test="${assetEntityForm.entity.id<=0}">Add</c:if> Asset Type</h2> 

	<c:if test="${assetEntityForm.entity.id>0}">
		<div class="info">Changing the following elements of this asset entity will require a reindex to be run before the changes will be reflected on the front end:
		<ul>
			<li>Attributes</li>
			<li>Show when browsing descendant categories</li>
			<li>Thumbnail file</li>
		</ul>
		If you would like to launch an immediate reindex of all instances of this asset entity in the system after saving, check the box at the bottom of the form prior to saving. Alternatively, you can leave the box unchecked and run a manual reindex to propogate the changes at a later time.</div>
	</c:if>
	

	<c:set var="printHeight" value="Hello World!"/>

	<logic:equal name="assetEntityForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="assetEntityForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form action="saveAssetEntity" method="post" onsubmit="return onFormSubmit();">
		<html:hidden name="assetEntityForm" property="entity.id"/>
		<html:hidden name="assetEntityForm" property="entity.childRelationshipFromName"/>
		<html:hidden name="assetEntityForm" property="entity.childRelationshipFromNamePlural"/>
		<html:hidden name="assetEntityForm" property="entity.childRelationshipToName"/>
		<html:hidden name="assetEntityForm" property="entity.childRelationshipToNamePlural"/>
		<html:hidden name="assetEntityForm" property="entity.peerRelationshipToName"/>
		<html:hidden name="assetEntityForm" property="entity.peerRelationshipToNamePlural"/>
		<html:hidden name="assetEntityForm" property="entity.termForSibling"/>
		<html:hidden name="assetEntityForm" property="entity.termForSiblings"/>
		<html:hidden name="assetEntityForm" property="entity.mustHaveParent"/>
		<c:if test="${!categoryExtensionAssetsEnabled}">
			<html:hidden name="assetEntityForm" property="entity.isCategoryExtension"/>
		</c:if>
		<c:if test="${!canCopyAssets}">
			<html:hidden name="assetEntityForm" property="entity.canCopyAssets"/>
		</c:if>
		<logic:notEmpty name="assetEntityForm" property="entity.translations">
			<logic:iterate name="assetEntityForm" property="entity.translations" id="translation" indexId="tIndex">
				<logic:greaterThan name="translation" property="language.id" value="0">
					<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].language.id"/>
					<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].language.name"/>
					<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].childRelationshipFromName"/>
					<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].childRelationshipFromNamePlural"/>
					<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].childRelationshipToName"/>
					<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].childRelationshipToNamePlural"/>
					<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].peerRelationshipToName"/>
					<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].peerRelationshipToNamePlural"/>
					<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].termForSibling"/>
					<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].termForSiblings"/>
				</logic:greaterThan>
			</logic:iterate>
		</logic:notEmpty>
	

		<table cellspacing="0" class="form" summary="Add/Edit Asset Type">		
		<%-- Include form start JSPs from extensions --%>
		<abplugin:include-form-extensions extensibleEntity="assetEntity" verb="edit" position="start"/>
			<tr>
				<th style="vertical-align:top; padding-top: 3px;"><label for="name">Name:</label><span class="required">*</span></th>
				<td>
					<html:text styleClass="text" name="assetEntityForm" styleId="name" property="entity.name" maxlength="40"/>
					<input type="hidden" name="mandatory_entity.name" value="Please enter a value for the Name field."/>
				</td>
			</tr>
			<logic:notEmpty name="assetEntityForm" property="entity.translations">
				<logic:iterate name="assetEntityForm" property="entity.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="entityName<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].language.id"/>
								<html:hidden name="assetEntityForm" property="entity.translations[${tIndex}].language.name"/>
								<input type="text" class="text" name="entity.translations[<bean:write name='tIndex'/>].name" maxlength="40" id="entityName<bean:write name='tIndex'/>" value="<bean:write name="translation" property="name" filter="false"/>"/>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
			<tr>
				<th><label for="thumbnailFilename">Thumbnail File:</label></th>
				<td>
					<html:text styleClass="text" styleId="thumbnailFilename" name="assetEntityForm" property="entity.thumbnailFilename" maxlength="100"/>
					<span class="comment">(Files should be placed in <strong>/files/thumbnails</strong> - reindex required if changed)</span>
				</td>
			</tr>
			<tr>
				<th style="padding-top: 25px;">Media Types:</th>
				<td>
					<div class="hr"></div>
					<ul>	
						<logic:iterate name="assetEntityForm" property="assetTypes" id="assetType" indexId="index">
							<bean:define id="assetTypeId" name="assetType" property="id"/>
							<li>
									<html:multibox name="assetEntityForm" property="allowableAssetTypeIds" styleClass="checkbox" styleId='<%= "assetTypeCheckbox" + assetTypeId %>'>
										<bean:write name="assetType" property="id" />
									</html:multibox>
								<label for="<%= "assetTypeCheckbox" + assetTypeId %>" class="after"><bean:write name="assetType" property="description" /></label>
							</li>
						</logic:iterate>
						

					</ul>
				</td>
			</tr>
			<tr>
				<th nowrap="nowrap"><label for="incFormats">Included File Formats:</label></th>
				<td>
					<html:text styleClass="text" name="assetEntityForm" property="entity.includedFileFormats" maxlength="200" styleId="incFormats" />
					<span class="comment">(leave empty to denote 'all except those excluded')</span>
				</td>
			</tr>
			<tr>
				<th nowrap="nowrap"><label for="excludedFileFormats">Excluded File Formats:</label></th>
				<td>
					<html:text styleClass="text" styleId="excludedFileFormats" name="assetEntityForm" property="entity.excludedFileFormats" maxlength="200"/>
				</td>
			</tr>
			<c:if test="${categoryExtensionAssetsEnabled}">
			<tr>
				<th style="padding-top: 28px;"><label for="catExtensionType">Is category extension?</label></th>
				<td>
					<div class="hr"></div>
					<html:checkbox name="assetEntityForm" property="entity.isCategoryExtension" styleClass="checkbox" styleId="catExtensionType" />
				</td>
			</tr>
			</c:if>
			<tr>
				<th style="padding-top: 26px;">Attributes:</th>
				<td>
					<div class="hr"></div>
					<ul>
						<logic:iterate name="assetEntityForm" property="attributes" id="attribute" indexId="index">
							<%-- Don't show sensitivity notes attribute regardless of whether sensitivity fields are enabled - if the setting is on then the sensitivity fields are shown for ALL attribute types --%>
							<c:if test="${attribute.id != attributeConstants.k_lAttributeId_SensitivityNotes}">
							<bean:define id="attrId" name="attribute" property="id"/>
								<c:set var="fieldName" value="" />
								<logic:notEmpty name='attribute' property='staticFieldName' >
									<c:set var="fieldName" value="${attribute.staticFieldName}" />
								</logic:notEmpty>
								<li <c:if test="${fieldName == 'categories'}">id="catAttribute"</c:if>>
			
									<c:if test="${attribute.id==400}"><div class="hr"></div></c:if>
										<html:multibox name="assetEntityForm" property="allowableAttributeIds" styleClass="checkbox" styleId="attr_${index}" onchange="showHideAgreementFields(this);">
											<bean:write name="attribute" property="id" />
										</html:multibox>
										<label for="<c:out value='attr_${index}'/>" class="after"><bean:write name="attribute" property="name" /></label>
									<c:if test="${agreementsEnabled && attribute.id==400}">
										<table cellspacing="0" style="margin-top:12px;" id="agreementFields">
											<tr><td>Unrestricted Agreement Id:&nbsp;</td><td><html:text name="assetEntityForm" property="entity.unrestrictedAgreementId" styleClass="text" style="width:auto;" size="4" maxlength="11"/></td></tr>
											<tr><td>Restricted Agreement Id:</td><td><html:text name="assetEntityForm" property="entity.restrictedAgreementId" styleClass="text" style="width:auto;" size="4" maxlength="11"/></td></tr>
										</table>
										<script type="text/javascript">
											showHideAgreementFields(document.getElementById('<c:out value="attr_${index}"/>'));
										</script>
									</c:if>

								</li>
							</c:if>
						</logic:iterate>
					</ul>
				</td>
			</tr>
			<tr>
				<th style="padding-top: 28px;"><label for="showLabels">Show attribute labels?</label></th>
				<td>
					<div class="hr"></div>
					<html:checkbox name="assetEntityForm" property="entity.showAttributeLabels" styleClass="checkbox" styleId="showLabels" />
				</td>
			</tr>
			<tr>
				<th style="padding-top: 28px;"><label for="matchOnAttributeId">Match attribute</label></th>
				<td>
					<div class="hr"></div>
					
					<html:select name="assetEntityForm" property="entity.matchOnAttributeId" styleId="matchOnAttributeId" >
					
						<%--  Show Id, original filename attribute at top, then autoincrement/text types --%>
						<%--  Assumes ID is attribute id=2, and originalFilename id=6 --%>
						<html:option value="0" >-- None --</html:option>
						<html:option value="2" >ID</html:option>
						<html:option value="6" >Original Filename</html:option>
						<logic:iterate name="assetEntityForm" property="attributes" id="attribute" indexId="index">
							<c:if test="${attribute.typeId == 11 || attribute.typeId == 1}">
							
								<html:option value="${attribute.id}" >
									<c:out value="${attribute.name}" />
								</html:option>
														
							</c:if>

						</logic:iterate>
					</html:select><br/><br/>
					<span class="comment">
						(When importing files into existing asset records, or when adding files to parent assets, this attribute will be used to match against the filenames.
						You may usually leave this as None, unless you want to support these operations.)
					<span class="comment">
					
				</td>
			</tr>
			<tr>
				<th style="padding-top: 28px;"><label for="defaultCatId">Default category id:</label></th>
				<td>
					<div class="hr"></div>
					<html:text name="assetEntityForm" property="entity.defaultCategoryId" styleClass="text" style="width:auto;" size="4" maxlength="11" styleId="defaultCatId" />
				</td>
			</tr>
			<c:if test="${canCopyAssets}">
			<tr>
				<th style="padding-top: 28px;"><label for="copies">Can create copies?</label></th>
				<td>
					<div class="hr"></div>
					<html:checkbox name="assetEntityForm" property="entity.canCopyAssets" styleClass="checkbox" styleId="copies" />
				</td>
			</tr>
			</c:if>
			<tr>
				<th style="padding-top: 28px;"><label for="children">Can download children from parent page?</label></th>
				<td>
					<div class="hr"></div>
					<html:checkbox name="assetEntityForm" property="entity.canDownloadChildrenFromParent" styleClass="checkbox" styleId="children"/>
				</td>
			</tr>
			<tr>
				<th style="padding-top: 28px;"><label for="descendantCats">Show when browsing descendant categories?</label></th>
				<td>
					<div class="hr"></div>
					<html:checkbox name="assetEntityForm" property="entity.showOnDescendantCategories" styleClass="checkbox" styleId="descendantCats"/>
				</td>
			</tr>
			<tr>
				<th style="padding-top: 28px;"><label for="searchable">Searchable?</label></th>
				<td>
					<div class="hr"></div>
					<html:checkbox name="assetEntityForm" property="entity.searchable" styleClass="checkbox" styleId="searchable" />
					<span class="comment">(If unchecked, items of this type will not be returned in searches unless all are unchecked)</span>
				</td>
			</tr>
			<tr>
				<th><label for="quickSearchable">Quick Searchable?</label></th>
				<td>
					<html:checkbox name="assetEntityForm" property="entity.quickSearchable" styleClass="checkbox" styleId="quickSearchable" />
					<span class="comment">(If unchecked, items of this type will not be returned in quick searches unless all are unchecked)</span>
				</td>
			</tr>
		</table>
		
		<div class="hr"></div>
		<c:if test="${assetEntityForm.entity.id>0}">
		<p><label for="reindexer"><b>Run immediate reindex of instances of this entity: </b></label><input type="checkbox" id="reindexer" name="reindex" value="1" /></p>
		<div class="hr"></div></c:if>
		
		<input type="submit" class="button flush floated" value="Save" /> 
		<a href="viewAssetEntities" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		<br />
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>