<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="highlightIncompleteAssets" settingName="highlight-incomplete-assets"/>
<bright:applicationSetting id="highlightRestrictedAssets" settingName="highlight-restricted-assets"/>

<%-- set default values for this group of assets if they havent been provided by whichever page uses this include --%>
<logic:notPresent name="numResults">
	<c:set var="numResults" value="${browseItemsForm.searchResults.numResults}" />
</logic:notPresent>

<logic:notPresent name="results">
	<c:set var="results" value="${browseItemsForm.searchResults.searchResults}" />
</logic:notPresent>

<%-- output the assets in the panel --%>				
<c:choose>
	<c:when test='${ numResults > 0 }'>
		
		<c:set var="formBean" value="${browseItemsForm}"/>
		<div class="toolbar">
			<logic:present name="panel" property="header">
				<h2>
					<bean:write name="panel" property="header" filter="false"/> 
					<span>(<c:out value="${numResults}" /> <c:if test="${numResults==1}"><bright:cmsWrite identifier="item" filter="false"  /></c:if><c:if test="${numResults!=1}"><bright:cmsWrite identifier="items" filter="false"  /></c:if>)</span>
				</h2>
			</logic:present>				
			
			<ul class="toolbarActions">
				
				<bright:addItemLink categoryId="${browseItemsForm.category.id}" categoryTreeId="${browseItemsForm.category.categoryTypeId}" itemName="${panel.header}" boolCheck="${panel.canAddItem && !browseItemsForm.showGlobalAddItemLink && !noLinks}" extraParameters="${panel.addItemParameters}" extensionEntityId="${browseItemsForm.extensionEntityId}" />
				
			</ul>
			
			<div class="clearing"></div>
		</div>
		

		<%-- finally decide whether to show in list view --%>
		<c:choose>
			<c:when test="${panel.listView}">
				
				<c:choose>
					<c:when test="${panel.showExtraDescriptions}">
						<bright:refDataList componentName="DisplayAttributeManager" methodName="getBrowseListDisplayAttributeLabelsIncExtraDescriptions"	id="displayAttributes" transactionManagerName="DBTransactionManager" passUserprofile="true" argumentValue="${browseItemsForm.category.id}" argument2Value="${browseItemsForm.category.categoryTypeId}"/>
					</c:when>
					<c:otherwise>
						<bright:refDataList componentName="DisplayAttributeManager" methodName="getBrowseListDisplayAttributeLabels"	id="displayAttributes" transactionManagerName="DBTransactionManager" passUserprofile="true" argumentValue="${browseItemsForm.category.id}" argument2Value="${browseItemsForm.category.categoryTypeId}"/>
					</c:otherwise>
				</c:choose>

				<table cellspacing="0" class="lightboxList stripey">
					<thead>
						<tr>
							<th>&nbsp;</th>
							<logic:notEmpty name='displayAttributes'>
								<logic:iterate name='displayAttributes' id='dispAtt'>
									<th>
										<bean:write name='dispAtt' />
									</th>
								</logic:iterate>
							</logic:notEmpty>
							<th class="actions">Actions</th>
						</tr>
					</thead>
					<tbody>	
			</c:when>	
			<c:otherwise>	
				<ul class="lightbox clearfix">
			</c:otherwise>
		</c:choose>
		
		<c:if test="${panel.showExtraDescriptions}">
			<bright:extraDescriptionsFromPlugin id="extraDescriptions" parameters="categoryId:${browseItemsForm.category.id},categoryTypeId:${browseItemsForm.category.categoryTypeId}" pluginSetting="extra-descriptions-plugins-browse"/>
		</c:if>

		<logic:iterate name='results' id='item' indexId='idx'>
			<%@include file="inc_browse_asset.jsp"%>
		</logic:iterate>
		
		<c:choose>
			<c:when test="${panel.listView}">
					</tbody>
				</table>	
			</c:when>	
			<c:otherwise>	
				</ul>
			</c:otherwise>
		</c:choose>

		<%@include file="../public/inc_entity_key.jsp"%>	
		<c:if test="${!panel.isPartOfMultiPanelSet}"><%@include file="../inc/pager.jsp"%></c:if>

	</c:when>

	<c:otherwise>
		
		<%-- Show a message if there are no items in this panel --%>
		<c:if test="${browseItemsForm.category.id gt 0}">
			<div class="toolbar">
				<c:if test="${not empty panel.header}">
					<h2 style="margin-top: 1em;" ><bean:write name="panel" property="header" filter="false"/></h2>
					<ul class="toolbarActions">
						<bright:addItemLink categoryId="${browseItemsForm.category.id}" categoryTreeId="${browseItemsForm.category.categoryTypeId}" itemName="${panel.header}" boolCheck="${true && !noLinks}" extraParameters="${panel.addItemParameters}" extensionEntityId="${browseItemsForm.extensionEntityId}" />
					</ul>	
				</c:if>
				<div class="clearing"></div>
			</div>
			<bright:cmsWrite identifier="no-items-to-browse" filter="false" replaceVariables="true" />
		</c:if>

	</c:otherwise>
		
</c:choose>