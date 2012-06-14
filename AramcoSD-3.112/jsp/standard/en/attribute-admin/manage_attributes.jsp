<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		25-Oct-2005		Created
	 d2		Ben Browning		22-Feb-2006		HTML/CSS tidy up
	 d3		Matt Stevenson		27-Nov-2006		Added link to sorting
	 d4		Matt Stevenson		06-Mar-2007		Added link to keywords
	 d5		Francis Devereux	30-Oct-2009		Use constants instead of hard coded IDs
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<bright:applicationSetting id="ecommerce" settingName="ecommerce" />
<bright:applicationSetting id="canCreateAssetVersions" settingName="can-create-asset-versions" />
<bright:applicationSetting id="showSensitivityFields" settingName="show-sensitivity-fields"/>
<bright:applicationSetting id="rating" settingName="ratings" />
<bright:applicationSetting id="agreementsEnabled" settingName="agreements-enabled" />
<bright:applicationSetting id="auditLoggingEnabled" settingName="enable-audit-logging" />
<bright:applicationSetting id="autoCompleteEnabled" settingName="auto-complete-enabled"/>

<%-- Create a Map containing the constants defined in AttributeConstants --%>
<un:useConstants var="attributeConstants" className="com.bright.assetbank.attribute.constant.AttributeConstants" />

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Attributes</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="attributes"/>
	<bean:define id="pagetitle" value="Attributes"/>
	<bean:define id="tabId" value="manageAttributes"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>

	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	
	<p>This page enables you to change the attributes that are associated with each asset.</p>
	<p>Please note: </p>
	
	<ul class="normal">
		<li>To avoid missing data you should configure the attributes <em>before</em> you start loading <bright:cmsWrite identifier="items" filter="false" /> into  <bright:cmsWrite identifier="app-name" filter="false" />.</li>
		<li>If you are modifying attributes or renaming attribute values (in lists), then when you have finished you should <a href="../action/viewReindexStatus">reindex existing <bright:cmsWrite identifier="items" filter="false" /></a>.<br /> It is not necessary to do this if you have not yet added any <bright:cmsWrite identifier="items" filter="false" />, or if you are just renaming existing attributes.</li>
		<c:if test="${autoCompleteEnabled}">
		<li>If you turn on "Auto complete" or "Include in keyword searches" for an attribute you should <a href="../action/viewACReindexStatus">reindex the auto complete index</a> afterwards.</li>
		</c:if>
	</ul>

	
	

	
		<bright:refDataList componentName="AttributeManager" methodName="getAttributePositionList" id="attPositionList"/>
	
		<table cellspacing="0" class="list highlight" summary="List of Attributes">
			<thead>
				<tr>
					<th>Attribute</th>
					<th>Type</th>
					<th colspan="6">Actions &nbsp;</th>
				</tr>
			</thead>
			<tbody>
				<logic:iterate name="attPositionList" id="attribute" indexId="index">
			
					<%-- Don't show keywords attribute unless enabled --%>
					<%-- Don't show price unless ecommerce --%>
					<c:if test="${!(attribute.fieldName=='price' && ecommerce!='true') && !(attribute.fieldName=='version' && canCreateAssetVersions!='true') && !((attribute.id==attributeConstants.k_lAttributeId_SensitivityNotes || attribute.fieldName=='sensitive') && !showSensitivityFields) && !(attribute.fieldName=='rating' && rating!='true') && !(attribute.fieldName=='agreements' && !agreementsEnabled) && !(attribute.fieldName=='audit' && !auditLoggingEnabled)}">	
						<tr class="id<bean:write name="attribute" property="id"/>">
							<td style="width:200px;">
								<c:choose>
									<c:when test="${attribute.isGroupHeading}">
										<strong><bean:write name="attribute" property="label"/></strong>
									</c:when>
									<c:otherwise>
										<bean:write name="attribute" property="label"/>
									</c:otherwise>
								</c:choose>
							</td>
							<td>
							<%@include file="inc_attribute_types.jsp"%>
							</td>
							<td class="action">
								<c:if test="${!attribute.static}">
									[<a href="viewEditAttribute?attributeId=<c:out value='${attribute.id}'/>">edit</a>]
								</c:if>
								<c:if test="${attribute.static}">
									[<a href="viewEditStaticAttribute?attributeId=<c:out value='${attribute.id}'/>">edit</a>]
								</c:if>
							</td>	
							<td class="action">
								<c:if test="${(attribute.typeId==attributeConstants.k_lAttributeType_DatePicker || attribute.typeId==attributeConstants.k_lAttributeType_DateTime) && !attribute.static}">
								[<a href="viewManageAttributeRules?attributeId=<bean:write name='attribute' property='id'/>">edit rules</a>]
								</c:if>
								<c:if test="${attribute.typeId==attributeConstants.k_lAttributeType_Dropdown || attribute.typeId==attributeConstants.k_lAttributeType_CheckList || attribute.typeId==attributeConstants.k_lAttributeType_OptionList}">
								[<a href="viewAttributeListValues?attributeId=<bean:write name='attribute' property='id'/>">edit list values</a>]
								</c:if>
								<c:if test="${attribute.typeId==attributeConstants.k_lAttributeType_KeywordPicker}">
								[<a href="viewKeywordAdmin?categoryTypeId=<bean:write name='attribute' property='treeId'/>&amp;attributeId=<bean:write name='attribute' property='id'/>">edit keywords</a>]
								</c:if>
								<c:if test="${attribute.typeId!=attributeConstants.k_lAttributeType_DatePicker && attribute.typeId!=attributeConstants.k_lAttributeType_Dropdown && attribute.typeId!=attributeConstants.k_lAttributeType_CheckList}">
								-
								</c:if>
							</td>
							<td class="action">
								<c:choose>
									<%-- Do not allow static attributes or the "special" sensitivity notes attribute to be deleted --%>
									<c:when test="${attribute.typeId != 0 && attribute.id != attributeConstants.k_lAttributeId_SensitivityNotes}">
										[<a href="confirmDeleteAttribute?attributeId=<bean:write name='attribute' property='id'/>&amp;attributeDescription=<bean:write name='attribute' property='label'/>&amp;typeId=<bean:write name='attribute' property='typeId'/>" title="Delete this attribute">X</a>]
									</c:when>
									<c:otherwise>
										-
									</c:otherwise>
								</c:choose>
							</td>	
						</tr>
					</c:if>
				</logic:iterate>
			</tbody>	
		</table>
	

	
	<p><a href="viewEditAttribute?attributeId=0">Add a new attribute &raquo;</a></p>
		
	
	<p><a href="viewEditAttribute?attributeId=0&amp;attribute.typeId=<c:out value="${attributeConstants.k_lAttributeType_GroupHeading}"/>">Add a new group heading &raquo;</a></p>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>
