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
	<bean:define id="section" value="attributes"/>
	<bean:define id="helpsection" value="display_attributes"/>
	<bean:define id="pagetitle" value="Display Attributes"/>
	<bean:define id="tabId" value="displayAttributes"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	
	<p>From here you can change the attributes that will be displayed on result assets for a number of different areas of Asset Bank. Use the tabs to select the area you are interested in and adjust the attributes accordingly.</p>
	<p>Any areas that aren't specifically mentioned with a tab will show the display attributes from the search tab.</p>

	<p>If you are modifying display attributes, then when you have finished you should <a href="../action/viewReindexStatus">reindex existing assets</a>.</p>
	
	<bean:parameter id="selectedGroupId" name="daGroupId" value="1" />
	<p class="tabHolderPopup">
		<logic:iterate name='displayAttributeForm' property='displayAttributeGroups' id='group'>
				<c:if test="${group.id != 4 || categoryExtensionAssetsEnabled}">
					<a href="viewManageDisplayAttributes?daGroupId=<bean:write name='group' property='id' />" <c:if test='${group.id == selectedGroupId}'>class="active"</c:if>><bean:write name='group' property='value' /></a>
				</c:if>
		</logic:iterate>
	</p>
	<div id="tabContent">
  	<logic:notEmpty name="displayAttributeForm" property="displayAttributes">
		<table cellspacing="0" class="admin" summary="List of Attributes">
			<tr>
				<th>Attribute</th>
				<th>Display Length</th>
				<th>Show Label?</th>
				<th>Is Link?</th>
			<c:if test="${assetEntitiesEnabled && useParentMetadata}">
				<th>Show on Child?</th>
			</c:if>
				<th colspan="4">&nbsp;</th>
			</tr>
			
			<logic:iterate name="displayAttributeForm" property="displayAttributes" id="dispAttribute" indexId="index">
			
				<tr>
					<td>
						<bean:write name="dispAttribute" property="attribute.label"/>
					</td>
					<td>
						<c:choose><c:when test="${dispAttribute.displayLength > 0}"><bean:write name="dispAttribute" property="displayLength"/> character(s)</c:when><c:otherwise>No restriction</c:otherwise></c:choose>
					</td>
					<td>
						<c:choose><c:when test="${dispAttribute.showLabel}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose>
					</td>
					<td>
						<c:choose><c:when test="${dispAttribute.isLink}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose>
					</td>
				<c:if test="${assetEntitiesEnabled && useParentMetadata}">
					<td>
						<c:choose><c:when test="${dispAttribute.showOnChild}">Yes</c:when><c:otherwise><c:choose><c:when test="${dispAttribute.attribute.static}">n/a</c:when><c:otherwise>No</c:otherwise></c:choose></c:otherwise></c:choose>
					</td>
				</c:if>
					<td class="action">
						[<c:if test="${index != 0}"><a href="moveDisplayAttribute?id=<bean:write name='dispAttribute' property='attribute.id'/>&daGroupId=<bean:write name='dispAttribute' property='groupId'/>&up=1"></c:if>up<c:if test="${index != 0}"></a></c:if>]
					</td>
					<td class="action">
						[<c:if test="${index != (displayAttributeForm.noOfSortAttributes-1)}"><a href="moveDisplayAttribute?id=<bean:write name='dispAttribute' property='attribute.id'/>&daGroupId=<bean:write name='dispAttribute' property='groupId'/>"></c:if>down<c:if test="${index != (displayAttributeForm.noOfSortAttributes-1)}"></a></c:if>]
					</td>
					<td class="action">
						[<a href="viewDisplayAttribute?id=<bean:write name='dispAttribute' property='attribute.id'/>&daGroupId=<bean:write name='dispAttribute' property='groupId'/>">edit</a>]
					</td>
					<td class="action">
						[<a href="deleteDisplayAttribute?id=<bean:write name='dispAttribute' property='attribute.id'/>&daGroupId=<bean:write name='dispAttribute' property='groupId'/>" onclick="return confirm('Are you sure you want to remove this display attribute?');" title="Delete this display attribute">X</a>]
					</td>
				</tr>
			
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<logic:empty name="displayAttributeForm" property="displayAttributes">
		<p>There are currently no display attributes defined.
	</logic:empty>
	</div>
	


	<logic:notEmpty name="displayAttributeForm" property="errors">
			<div class="error">
			<logic:iterate name="displayAttributeForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
			</div>
	</logic:notEmpty>
	<p>
		<form name="addDisplayAttributeForm" action="saveDisplayAttribute" method="post" class="floated">
			<input type="hidden" name="new" value="1"/>
			<input type="hidden" name="displayAttribute.groupId" value="<bean:write name='selectedGroupId' />" />

			<label for="chooseAttrib">Attribute to display:</label>
			
			<html:select name="displayAttributeForm" property="displayAttribute.attribute.id" size="1" styleId="chooseAttrib">
				<option value="0">[Please select]</option>
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

			<input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-save" filter="false" />" />

		</form>
	</p>
	

	<div class="hr"></div>
	
	<br/><h3>Name Attributes</h3>

	<p>Please select from the list below the attributes you would like to use for your <bright:cmsWrite identifier="item" filter="false" /> names.</p>

	<p>The <bright:cmsWrite identifier="item" filter="false" /> names are displayed on certain reports and used to construct certain filenames. Descriptive attributes such as title are best used for the <bright:cmsWrite identifier="item" filter="false" /> names. The first selected name attribute that has a value will be used by an <bright:cmsWrite identifier="item" filter="false" />.</p>
	<p>If you don't select a name attribute then the id will be used.</p>
	<br/>
	<form name="nameAttributeForm" action="setNameAttribute" method="get">
	
		<input type="hidden" name="daGroupId" value="<bean:write name='selectedGroupId' />" />
		<!-- html:select name="displayAttributeForm" property="nameAttribute" size="1" -->
			<logic:iterate name="displayAttributeForm" property="nameAttributes" id="att" indexId="index">
				<input type="checkbox" value="<bean:write name='att' property='id'/>" name="nameAtt<bean:write name='index'/>" id="nameAtt<bean:write name='index'/>" <c:if test='${att.isNameAttribute}'>checked</c:if>/><label for="nameAtt<bean:write name='index'/>"><bean:write name='att' property='label'/></label><br/>
			</logic:iterate>
		<br/><br/><input type="submit" name="submit" value="Set name attributes &raquo;" class="button"/>
	
	</form>
	
	<c:if test="${canCreateSearchRss}">
		<div class="hr"></div>
		
		<br/><h3>Description Attribute</h3>
	
		<p>Please select from the list below the attribute you would like to use for your <bright:cmsWrite identifier="item" filter="false" /> description.</p>
	
		<p>The <bright:cmsWrite identifier="item" filter="false" /> description is included as the description field in RSS feeds created from saved searches. </p>
		<br/>
		<form name="descriptionAttributeForm" action="setDescriptionAttribute" method="get">
			<html:select name="displayAttributeForm" property="descriptionAttribute" size="1">
				<html:option value="-1">[none]</html:option>
				<logic:iterate name="displayAttributeForm" property="nameAttributes" id="att">
					<option value="<bean:write name='att' property='id'/>" <c:if test="${att.id == displayAttributeForm.descriptionAttribute}">selected</c:if>><bean:write name='att' property='label'/></option>
				</logic:iterate>
			</html:select>&nbsp;&nbsp;<input type="submit" name="submit" value="Set as description &raquo;" class="button"/>
		
		</form>
	</c:if>
		
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>
