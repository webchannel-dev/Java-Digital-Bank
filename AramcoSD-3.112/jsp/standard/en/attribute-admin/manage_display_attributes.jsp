<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	02-Jul-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@page import="org.apache.lucene.search.SortField"%>
<%@page import="com.bright.assetbank.attribute.bean.SortAttribute"%>


<bright:applicationSetting id="ecommerce" settingName="ecommerce" />
<bright:applicationSetting id="canCreateSearchRss" settingName="can-create-search-rss" />
<bright:applicationSetting id="assetEntitiesEnabled" settingName="asset-entities-enabled"/>
<bright:applicationSetting id="useParentMetadata" settingName="include-metadata-from-parents-for-search"/>
<bright:applicationSetting id="categoryExtensionAssetsEnabled" settingName="category-extension-assets-enabled"/>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Display Attributes</title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<script type="text/javascript">

		$j(function() {
		
			// init sorting
			initSorting('relocateDisplayAttribute');
		
			//$j('table.sortTest tbody').sortable();
		});

	</script>
	
	<bean:define id="section" value="attributes"/>
	<bean:define id="helpsection" value="display_attributes"/>
	<bean:define id="pagetitle" value="Display Attributes"/>
	<bean:define id="tabId" value="displayAttributes"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1>Attributes</h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	
	<p>This section allows you to choose which attributes are visible in different areas of Asset Bank, and the order in which they appear.</p>
	
	<p>Any areas that aren't specifically mentioned with a tab will show the display attributes from the search tab.</p>

	<p>If you are modifying display attributes, then when you have finished you should <a href="../action/viewReindexStatus">reindex existing assets</a>.<br />
		It is not necessary to do this if you have not yet added any <bright:cmsWrite identifier="items" filter="false" />, or if you are just reordering existing display attributes.
	</p>
	
	<bean:parameter id="selectedGroupId" name="daGroupId" value="8" />
	<p class="tabHolderPopup">
		
		<!-- TODO; this is much too 'hardcoded', needs to be more dynamic (but probably requires database changes for this) -->
		<a href="viewManageDisplayAttributes?daGroupId=8" <c:if test='${selectedGroupId == 8}'>class="active"</c:if>>View/edit</a>
		<a href="viewManageDisplayAttributes?daGroupId=2" <c:if test='${selectedGroupId == 2 || selectedGroupId == 5 || selectedGroupId == 4}'>class="active"</c:if>>Browsing</a>
		<a href="viewManageDisplayAttributes?daGroupId=6" <c:if test='${selectedGroupId == 6 || selectedGroupId == 7 || selectedGroupId == 1}'>class="active"</c:if>>Searching</a>
		<a href="viewManageDisplayAttributes?daGroupId=3" <c:if test='${selectedGroupId == 3}'>class="active"</c:if>>Lightbox</a>
		<a href="viewManageDisplayAttributes?daGroupId=10" <c:if test='${selectedGroupId == 10}'>class="active"</c:if>>Printing</a>
		<a href="viewManageDisplayAttributes?daGroupId=9" <c:if test='${selectedGroupId == 9}'>class="active"</c:if>>Downloading</a>
		
		<!-- <logic:iterate name='displayAttributeForm' property='displayAttributeGroups' id='group'>
				<c:if test="${group.id != 4 || categoryExtensionAssetsEnabled}">
					<a href="viewManageDisplayAttributes?daGroupId=<bean:write name='group' property='id' />" <c:if test='${group.id == selectedGroupId}'>class="active"</c:if>><bean:write name='group' property='value' /></a>
				</c:if>
		</logic:iterate> -->
	</p>
	<div id="tabContent" style="overflow:hidden;">
		<c:choose>
			<c:when test="${selectedGroupId == 2 || selectedGroupId == 5 || selectedGroupId == 4}">

				<ul class="linkList">
					<li class="first <c:if test='${selectedGroupId == 2}'>active</c:if>"><a href="viewManageDisplayAttributes?daGroupId=2">Browsing (panel view)</a></li>
					<li <c:if test='${selectedGroupId == 5}'>class="active"</c:if>><a href="viewManageDisplayAttributes?daGroupId=5">Browsing (list view)</a></li>
					<c:if test="${categoryExtensionAssetsEnabled}">
						<li <c:if test='${selectedGroupId == 4}'>class="active"</c:if>><a href="viewManageDisplayAttributes?daGroupId=4">Extended categories</a></li>
					</c:if>
				</ul>	

			</c:when>
			<c:when test="${selectedGroupId == 6 || selectedGroupId == 7 || selectedGroupId == 1}">

				<ul class="linkList">
					<li class=" first <c:if test='${selectedGroupId == 6}'>active</c:if>"><a href="viewManageDisplayAttributes?daGroupId=6">Advanced search form</a></li>
					<li <c:if test='${selectedGroupId == 7}'>class="active"</c:if>><a href="viewManageDisplayAttributes?daGroupId=7">Search builder form</a></li>
					<li <c:if test='${selectedGroupId == 1}'>class="active"</c:if>><a href="viewManageDisplayAttributes?daGroupId=1">Search results</a></li>
				</ul>	

			</c:when>
		</c:choose>	
		<c:choose>
			<c:when test="${selectedGroupId == 1}">
				<!-- Search results -->
				<p>Change which attributes to display (and in which order) on asset panels that appear in search results.</p>
			</c:when>
			<c:when test="${selectedGroupId == 2}">
				<!-- Browse (panel view) -->
				<p>Change which attributes to display (and in which order) on asset panels that appear when browsing.</p>
			</c:when>
			<c:when test="${selectedGroupId == 3}">
				<!-- Lightbox -->
				<p>Change which attributes to display (and in which order) on asset panels that appear when viewing a lightbox.</p>
			</c:when>
			<c:when test="${selectedGroupId == 4}">
				<!-- Category extensions -->
				<p>Change which attributes to display (and in which order) when viewing an extended category.</p>
			</c:when>
			<c:when test="${selectedGroupId == 5}">
				<!-- Browse (list view) -->
				<p>Change which attributes to display (and in which order) when browsing in list view.</p>
			</c:when>
			<c:when test="${selectedGroupId == 6}">
				<!-- Search form -->
				<p>Change which attributes are shown (and in which order) when viewing the Advanced Search page (i.e. which attributes will be available for searching).</p>
			</c:when>
			<c:when test="${selectedGroupId == 7}">
				<!-- Search builder -->
				<p>Change which attributes to display (and in which order) when viewing the Search Builder page (i.e. which attributes will be available for constructing a search).</p>
			</c:when>
			<c:when test="${selectedGroupId == 8}">
				<!-- View / edit page -->
				<p>Change which attributes are shown (and in which order) when viewing or editing a single asset.</p>
			</c:when>
			<c:when test="${selectedGroupId == 9}">
				<!-- Download Filename page -->
				<p>Change which attributes are used to construct the filename that will be used when an asset is downloaded.</p>
			</c:when>
			<c:when test="${selectedGroupId == 10}">
				<!-- Print Details page -->
				<p>Change which attributes are shown (and in which order) when viewing the <bright:cmsWrite identifier="heading-contact-sheet" filter="false" /> and <bright:cmsWrite identifier="heading-print-image" filter="false" /> page.</p>
			</c:when>
		</c:choose>
		
		<form name="addDisplayAttributeForm" action="saveDisplayAttribute" method="post" class="floated addForm" style="">
			<input type="hidden" name="new" value="1"/>
			<input type="hidden" name="displayAttribute.groupId" value="<bean:write name='selectedGroupId' />" />
			<img src="../images/standard/misc/arrow_panel_left.gif" width="33" height="39" alt="" class="arrow" />
			
			<bean:size id="displayAttributesLeft" name="displayAttributeForm" property="attributes" />
			
			<h3>Add an attribute to display</h3>
			
			<c:choose>
				<c:when test="${displayAttributesLeft>0}">
					<html:select name="displayAttributeForm" property="displayAttribute.attribute.id" size="1" styleId="chooseAttrib" >
						<option value="0">[Please select an attribute]</option>
						<logic:iterate name="displayAttributeForm" property="attributes" id="attribute" indexId="index">
							<option value="<bean:write name='attribute' property='id'/>">
								<c:choose>
									<c:when test="${attribute.label == ''}">
										[No Label]
									</c:when>
									<c:otherwise>
										<bean:write name="attribute" property="label"/>
									</c:otherwise>
								</c:choose>
							</option>
						</logic:iterate>
					</html:select>
					<br />
					
					<%@include file="inc_display_attribute_fields.jsp"%>

					<input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-add" filter="false" />" />
				</c:when>	
				<c:otherwise>
					<p><em>There are no attributes left to display.</em></p> 
					<p>You can create new attributes on the <a href="../action/viewManageAttributes">Manage Attributes</a> page
					</p>
				</c:otherwise>
			</c:choose>
			


			

		</form>
		
		
  	<logic:notEmpty name="displayAttributeForm" property="displayAttributes">

		<table cellspacing="0" class="list" id="<bean:write name='selectedGroupId' />" summary="List of Attributes">
			<thead>
				<tr>
					<th>Attribute</th>
					<c:if test="${showLength}">
						<th>Display Length</th>
					</c:if>
					<c:if test="${showLabel}">
						<th>Show Label?</th>
					</c:if>
					<c:if test="${showLink}">
						<th>Is Link?</th>
					</c:if>
				<c:if test="${showOnChild && assetEntitiesEnabled && useParentMetadata}">
					<th>Show on Child?</th>
				</c:if>
					<th colspan="4">Actions</th>
				</tr>
			</thead>
			
			<logic:iterate name="displayAttributeForm" property="displayAttributes" id="dispAttribute" indexId="index">
				<c:if test="${!(dispAttribute.attribute.fieldName=='price' && ecommerce!='true')}" >
			
					<tr class="id<bean:write name="dispAttribute" property="attribute.id"/>">
						<td class="sort" style="width:160px;">
							<c:choose>
								<c:when test="${dispAttribute.attribute.isGroupHeading}">
									<strong><bean:write name="dispAttribute" property="attribute.label"/></strong>
								</c:when>
								<c:otherwise>
									<bean:write name="dispAttribute" property="attribute.label"/>
								</c:otherwise>
							</c:choose>
						</td>
						<c:if test="${showLength}">
							<td>
								<c:choose><c:when test="${dispAttribute.displayLength > 0}"><bean:write name="dispAttribute" property="displayLength"/> character(s)</c:when><c:otherwise>No restriction</c:otherwise></c:choose>
							</td>
						</c:if>
						<c:if test="${showLabel}">
							<td>
								<c:choose><c:when test="${dispAttribute.showLabel}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose>
							</td>
						</c:if>
						<c:if test="${showLink}">
							<td>
								<c:choose><c:when test="${dispAttribute.isLink}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose>
							</td>
						</c:if>
					<c:if test="${showOnChild && assetEntitiesEnabled && useParentMetadata}">
						<td>
							<c:choose><c:when test="${dispAttribute.showOnChild}">Yes</c:when><c:otherwise><c:choose><c:when test="${dispAttribute.attribute.static}">n/a</c:when><c:otherwise>No</c:otherwise></c:choose></c:otherwise></c:choose>
						</td>
					</c:if>
						<td class="action js-enabled-hide">
							[<c:if test="${index != 0}"><a href="moveDisplayAttribute?id=<bean:write name='dispAttribute' property='attribute.id'/>&daGroupId=<bean:write name='dispAttribute' property='groupId'/>&up=1"></c:if>up<c:if test="${index != 0}"></a></c:if>]
	
							[<c:if test="${index != (displayAttributeForm.noOfSortAttributes-1)}"><a href="moveDisplayAttribute?id=<bean:write name='dispAttribute' property='attribute.id'/>&daGroupId=<bean:write name='dispAttribute' property='groupId'/>"></c:if>down<c:if test="${index != (displayAttributeForm.noOfSortAttributes-1)}"></a></c:if>]
						</td>
						<c:if test="${showEdit}">
							<td class="action">
								[<a href="viewDisplayAttribute?id=<bean:write name='dispAttribute' property='attribute.id'/>&daGroupId=<bean:write name='dispAttribute' property='groupId'/>">edit</a>]
							</td>
						</c:if>
						<c:choose>
							<c:when test="${dispAttribute.canBeDeleted}">
								<td class="action">
									[<a href="deleteDisplayAttribute?id=<bean:write name='dispAttribute' property='attribute.id'/>&daGroupId=<bean:write name='dispAttribute' property='groupId'/>" onclick="return confirm('Are you sure you want to remove this display attribute?');" title="Delete this display attribute">X</a>]
								</td>
							</c:when>
							<c:otherwise>
								<td class="action">&nbsp;</td>
							</c:otherwise>
						</c:choose>
					</tr>
			
				</c:if>
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<logic:empty name="displayAttributeForm" property="displayAttributes">
		<p><em>There are currently no display attributes defined.</em></p>
	</logic:empty>
		

	<logic:notEmpty name="displayAttributeForm" property="errors">
			<div class="error">
			<logic:iterate name="displayAttributeForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
			</div>
	</logic:notEmpty>
		
		<br />

	</div>
	




		
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>
