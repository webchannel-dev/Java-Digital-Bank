<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
    d3      Ben Browning   14-Feb-2006    HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/search?cachedCriteria=1"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="snippet-search-results" filter="false" /></c:set>


<%@include file="../inc/set_this_page_url.jsp"%>
<bright:applicationSetting id="bShowAddAllToLightbox" settingName="show-add-all-to-lightbox"/>
<bright:applicationSetting id="bAddAllToLightboxLimit" settingName="add-all-to-assetbox-limit"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="hideLightbox" settingName="hide-lightbox"/>
<bright:applicationSetting id="usePriceBands" settingName="price-bands"/>
<bright:applicationSetting id="titleMaxLength" settingName="results-title-max-length"/>
<bright:applicationSetting id="descriptionMaxLength" settingName="results-description-max-length"/>
<bright:applicationSetting id="hideThumbnails" settingName="hide-thumbnails-on-browse-search"/>
<bright:applicationSetting id="savedSearchesEnabled" settingName="saved-searches-enabled"/>
<bright:applicationSetting id="highlightIncompleteAssets" settingName="highlight-incomplete-assets"/>
<bright:applicationSetting id="highlightRestrictedAssets" settingName="highlight-restricted-assets"/>
<bright:applicationSetting id="relationshipLimit" settingName="select-all-relationship-limit"/>
<bright:applicationSetting id="userDrivenSorting" settingName="user-driven-sorting-enabled"/>
<bright:applicationSetting id="enableSlideshow" settingName="enable-slideshow"/>
<bright:applicationSetting id="orgUnitAdminsCanExport" settingName="org-unit-admins-can-export"/>


<head>
	
	<title><bright:cmsWrite identifier="title-search-results" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="search"/>
	<bean:define id="helpsection" value="search_results"/>
	<c:choose>
		<c:when test="${searchPage=='savedSearches'}">
			<c:set var="fromPage"><bright:cmsWrite identifier="tab-saved-searches" filter="false"/></c:set>
			<c:set var="fromUrl" value="viewSavedSearches"/>
			<c:set var="tabId" value="savedSearches"/>
		</c:when>
		<c:when test="${searchPage=='batchrelease'}">
			<bean:parameter id="brUrl" name="brUrl" value="" />
			<c:set var="fromUrl" value="..${brUrl}" />
			<c:set var="fromPage"><bright:cmsWrite identifier="batch-release" filter="false" case="mixed" /></c:set>
			<c:set var="requestUrl">
				<%= request.getParameter("brUrl").replaceAll("\\?", "%3F").replaceAll("&", "%26") %>
			</c:set>
			<c:set scope="session" var="imageDetailReturnUrl">
				<c:out value="${imageDetailReturnUrl}" />&brUrl=<c:out value="${requestUrl}" />
			</c:set>
		</c:when>
		<c:otherwise>
			<c:set var="fromPage"><bright:cmsWrite identifier="tab-advanced-search" filter="false"/></c:set>
			<c:set var="fromUrl" value="viewLastSearch"/>
			<c:set var="tabId" value="advancedSearch"/>
		</c:otherwise>
	</c:choose>
	<logic:present name="searchBuilderForm">
		<bean:define id="searchForm" name="searchBuilderForm"/>
	</logic:present>
</head>

<body id="resultsPage">

	<%@include file="../inc/body_start.jsp"%>
   
	<c:choose>
		<c:when test="${savedSearchesEnabled && userprofile.isLoggedIn}">
			<%@include file="inc_search_tabs.jsp"%>
		</c:when>
		<c:otherwise>
			<h1>
				<bright:cmsWrite identifier="heading-search-results" filter="false" />
				<c:if test="${not empty searchForm.relationName}">
					(<bright:cmsWrite identifier="link-find" filter="false"/> <bean:write name="searchForm" property="relationName" filter="false"/>)
				</c:if>
			</h1>
		</c:otherwise>
	</c:choose>
	
	<div class="warning printOnlyIE67">
		<p><strong>Note:</strong> For optimum printing results we recommend using an up-to-date browser such as Internet Explorer 9, Firefox, or Google Chrome.</p>
	</div>	
	
	<logic:notEmpty name="assetForm">
		<logic:equal name="assetForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="assetForm" property="errors" id="error">
					<bean:write name="error" filter="false"/>
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:notEmpty>
	
   <div class="head stripBelow tabsAbove">
		<c:if test="${enableSlideshow && searchForm.containsImages && searchPage != 'batchrelease'}">
			<script type="text/javascript">
				<!--
					document.write("<a href='viewSearchSlideshow?pageSize=<bean:write name='searchForm' property='pageSize'/>&page=<bean:write name='searchForm' property='searchResults.pageIndex'/>&returnUrl=<c:out value='${thisUrlForGet}'/>' class='slideshow'><bright:cmsWrite identifier='link-this-page-as-slideshow' filter='false'/></a>");
				-->
			</script>
		</c:if>
		<c:if test="${not empty fromUrl}">
			<p><bright:cmsWrite identifier="you-are-here" filter="false" />&nbsp;<a href="<bean:write name="fromUrl" filter="false"/>"><bean:write name="fromPage" filter="false"/></a> &raquo; <bright:cmsWrite identifier="heading-search-results" filter="false"/></p>
		</c:if>
		
	
		
		<c:set var="sQuery"><bean:write name="searchForm" property="keywords" /></c:set>
		<c:set var="numberHits" value="${searchForm.searchResults.numResults}" />
		
		<c:choose>
			<c:when test="${searchForm.searchResults.numResults gt 0}">
				<div class="floatLeft">
					<c:choose>
						<c:when test="${not empty sQuery}">
							<c:choose>
								<c:when test="${searchForm.searchResults.numResults!=1}">
									<c:choose>
										<c:when test="${searchForm.searchResults.maxResultsExceeded == true}">
											<bright:cmsWrite identifier="search-results-query-max" filter="false" replaceVariables="true" />							
										</c:when>		
										<c:otherwise>
											<bright:cmsWrite identifier="search-results-query" filter="false" replaceVariables="true" />				
										</c:otherwise>
									</c:choose>				
								</c:when>				
								<c:otherwise>
									<bright:cmsWrite identifier="search-results-query-one" filter="false" replaceVariables="true" />				
								</c:otherwise>
							</c:choose>						
						</c:when>
											
						<c:otherwise>
							<c:choose>
								<c:when test="${searchForm.searchResults.numResults!=1}">
									<c:choose>
										<c:when test="${searchForm.searchResults.maxResultsExceeded == true}">
											<bright:cmsWrite identifier="search-results-max" filter="false" replaceVariables="true" />			
										</c:when>				
										<c:otherwise>
											<bright:cmsWrite identifier="search-results" filter="false" replaceVariables="true" />						
										</c:otherwise>
									</c:choose>						
								</c:when>			
								<c:otherwise>
									<bright:cmsWrite identifier="search-results-one" filter="false" replaceVariables="true" />					
								</c:otherwise>
							</c:choose>			
						</c:otherwise>
					</c:choose>
				</div>
				<c:if test="${searchPage != 'batchrelease'}">
					<span class="divider floatLeft">|</span> <a href="viewLastSearch" class="floatLeft"><bright:cmsWrite identifier="link-search-again" filter="false" /></a>
					<c:if test="${savedSearchesEnabled && userprofile.isLoggedIn}">
						<span class="divider floatLeft">|</span>
						<a href="viewSaveSearch" class="floatLeft"><bright:cmsWrite identifier="link-save-search" filter="false" /></a>
					</c:if>
					<span class="divider floatLeft">|</span>
					<a href="viewLastSearch?refineSearch=1" class="floatLeft"><bright:cmsWrite identifier="refine-search-results" filter="false" /></a>
					
					<c:if test="${bShowAddAllToLightbox && !userprofile.isFromCms && !hideLightbox && empty searchForm.selectAssetUrl}">
						<span class="divider floatLeft">|</span>
						<a href="addAllSearchToAssetBox?forward=/action/search&amp;cachedCriteria=1"
						<c:choose>
							<c:when test="${searchForm.searchResults.numResults > bAddAllToLightboxLimit}">
								<bean:define id="count" name="bAddAllToLightboxLimit"/>
								 onclick="return confirm('<bright:cmsWrite identifier="js-add-x-search-lightbox" filter="false" replaceVariables="true"/>');" class="floatLeft">
							</c:when>
							<c:otherwise>
								onclick="return confirm('<bright:cmsWrite identifier="js-add-search-lightbox" filter="false"/>');" class="floatLeft">
							</c:otherwise>
						</c:choose>
						<bright:cmsWrite identifier="link-add-all" filter="false" /></a>
					</c:if>
				</c:if>
				
			
				
			</c:when>
			
			<c:otherwise>
				<div class="floatLeft">
					<c:choose>
						<c:when test="${not empty sQuery}">
							<bright:cmsWrite identifier="no-search-results-query" filter="false" replaceVariables="true" />
						</c:when>
						<c:otherwise>
							<bright:cmsWrite identifier="no-search-results" filter="false"/>
						</c:otherwise>
					</c:choose>	
				</div>
				<span class="divider floatLeft">|</span>
				<a href="viewLastSearch" class="floatLeft"><bright:cmsWrite identifier="link-search-again" filter="false" /></a>	
				<span class="divider floatLeft">|</span>
				<a href="viewLastSearch?refineSearch=1" class="floatLeft"><bright:cmsWrite identifier="refine-search-results" filter="false" /></a>
				
				<%-- Show extra cms text for no results --%>
				<bright:refDataList id="searchNoResultsCopyItem" componentName="ListManager" methodName="getListItem" argumentValue="search-no-results-copy"/>	
				<c:if test="${not empty searchNoResultsCopyItem}">
					<div class="clearing">&nbsp;</div>
					<span class="floatLeft"><bean:write name="searchNoResultsCopyItem" property="body" filter="false"/></span>
				</c:if>
			</c:otherwise>
		</c:choose>

		
   </div><!-- end of head -->		


   <c:if test="${searchForm.searchResults.numResults gt 0}">		

      <div class="sortStrip">

      	<%-- Options for number of results per page --%>
			<logic:notEmpty name="searchForm" property="pageSizeOptions">
				<html:form action="search" styleId="numResultsForm" method="get">
					<input type="hidden" name="cachedCriteria" value="1"/>
					<c:if test="${searchPage == 'batchrelease'}">
						<input type="hidden" name="brId" value="<c:out value='${brId}' />" />
						<input type="hidden" name="searchPage" value="batchrelease" />
						<input type="hidden" name="brUrl" value="<c:out value='${brUrl}' />" />
					</c:if>
					<label for="pageSize"><bright:cmsWrite identifier="label-results-per-page" filter="false"/></label> 				
					<select name="pageSize" onchange="$j('#numResultsForm').submit();" id="pageSize">
						<logic:iterate name="searchForm" property="pageSizeOptions" id="pageSizeOption">
							<option <c:if test="${searchForm.pageSize == pageSizeOption}">selected</c:if>><bean:write name='pageSizeOption' filter='false'/></option>
						</logic:iterate>			
					</select>
					<input class="button flush js-enabled-hide" type="submit" value="<bright:cmsWrite identifier="button-go" filter="false" />" />
				</html:form>
			</logic:notEmpty>
			<c:set var="formBean" value="${searchForm}"/>
			<c:set var="linkUrl" value="search?cachedCriteria=1&sortAttributeId=${searchForm.sortAttributeId}&sortDescending=${searchForm.sortDescending}"/>
			<c:if test="${searchPage == 'batchrelease'}">
				<c:set var="linkUrl" value="${linkUrl}&brId=${brId}&searchPage=batchrelease&brUrl=${requestUrl}" />
			</c:if>
			<c:set var="styleClass" value="pager searchPager"/>
			<%@include file="../inc/pager.jsp"%>	

			<c:choose>
				<c:when test="${userDrivenSorting}">
					<div class="searchResultSorting">
						<bean:define id="sortForm" name="searchForm"/>
						<html:form action="search" styleId="sortForm" method="get">
							<input type="hidden" name="cachedCriteria" value="1"/>
							<c:if test="${searchPage == 'batchrelease'}">
								<input type="hidden" name="brId" value="<c:out value='${brId}' />" />
								<input type="hidden" name="searchPage" value="batchrelease" />
								<input type="hidden" name="brUrl" value="<c:out value='${brUrl}' />" />
							</c:if>
							<%@include file="../inc/sort_attribute_fields.jsp" %>
						</html:form>
					</div>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="searchForm.sortAttributeId" value="0"/>
				</c:otherwise>
			</c:choose>
		
		</div><!-- end of div.sortStrip -->
		
		<!--  bean:write name="searchForm" property="description" /-->
		<c:if test="${ not empty searchDescription }">
			<div class="featuredSearchContent">
				<bean:write name="searchDescription" scope="request" />
			</div>	
		</c:if>
		
		<ul class="lightbox clearfix" style="margin-top: 1em;">
		
			<logic:iterate name="searchForm" property="searchResults.searchResults" id="item" indexId="index">

				<li class="assetList <logic:equal name="hideThumbnails" value="true">noThumb clearfix </logic:equal><c:if test="${not empty item.entity.name}"><bean:write name="item" property="entity.compactName"/> </c:if><c:if test="${highlightIncompleteAssets && not item.complete}">incomplete </c:if><c:if test="${highlightRestrictedAssets && (item.isRestricted || item.agreementTypeId == 3)}">restricted </c:if><c:if test="${item.agreementTypeId == 2}">agreementApplies</c:if><c:if test="${item.agreementTypeId == 1}">unrestricted</c:if>">		
						
					<logic:empty name="item" property="displayHomogenizedImageFile.path">
						<c:set var="resultImgClass" value="icon"/>
					</logic:empty>
					<logic:notEmpty name="item" property="displayHomogenizedImageFile.path">
						<c:set var="resultImgClass" value="image"/>
					</logic:notEmpty>
		
					<%-- There's always a thumbnail for images --%>
					<c:if test="${item.typeId==2}">
						<c:set var="resultImgClass" value="image"/>
					</c:if>
				
					<%-- Set up variables for URLs --%>
					<c:set var="viewUrlParams" value="id=${item.id}&index=${item.position}&total=${searchForm.searchResults.numResults}&view=viewSearchItem" />
					<c:set var="forwardParams" value="forward=/action/search&cachedCriteria=1&sortAttributeId=${searchForm.sortAttributeId}&sortDescending=${searchForm.sortDescending}" />
					
					<%@include file="../inc/result_asset.jsp"%>
				
					<%-- Action links/buttons --%>
					<logic:notEqual name="hideThumbnails" value="true">
						<c:if test="${empty searchForm.selectAssetUrl}">
							<p class="action">
								<%@include file="../inc/add_to_lightbox.jsp"%>
							</p>
						</c:if>	
						<c:if test="${not empty searchForm.selectAssetUrl}">
							<div style="text-align: center;">
								<form action="<bean:write name="searchForm" property="selectAssetUrl"/>">
									<input type="hidden" name="selectedAssetId" value="<bean:write name="item" property="id"/>"/>
									<input type="hidden" name="entityId" value="<bean:write name="item" property="entity.id"/>"/>
									<logic:iterate name="searchForm" property="selectAssetParamNames" id="paramName" indexId="paramIndex">
										<logic:notEmpty name="paramName">
											<input type="hidden" name="<c:out value="${searchForm.selectAssetParamNames[paramIndex]}"/>" value="<c:out value="${searchForm.selectAssetParamValues[paramIndex]}"/>"/>
										</logic:notEmpty>
									</logic:iterate>
									<input type="submit" class="button" value="<bright:cmsWrite identifier="button-select" filter="false"/>" style="margin-bottom:6px;">
								</form>
							</div>
						</c:if>
					</logic:notEqual>
				</li>
			</logic:iterate>
		</ul>

		
		<%@include file="inc_entity_key.jsp"%>		
		
		<c:if test="${not empty searchForm.selectAssetUrl}">
			
			<c:choose>
				<c:when test="${searchForm.searchResults.numResults <= relationshipLimit}">
					<form action="<bean:write name="searchForm" property="selectAssetUrl"/>" method="post" style="float: left;">
						<input type="hidden" name="all" value="1"/>
						<logic:iterate name="searchForm" property="selectAssetParamNames" id="paramName" indexId="paramIndex">
							<logic:notEmpty name="paramName">
								<input type="hidden" name="<c:out value="${searchForm.selectAssetParamNames[paramIndex]}"/>" value="<c:out value="${searchForm.selectAssetParamValues[paramIndex]}"/>"/>
							</logic:notEmpty>
						</logic:iterate>
						<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-select-all-results" filter="false"/>">
					</form>
				</c:when>
				<c:otherwise>
					<p><bright:cmsWrite identifier="snippet-relationships-limit" filter="false" /></p>
				</c:otherwise>
			</c:choose>

			<form action="<bean:write name="searchForm" property="selectAssetUrl"/>" method="post" <c:choose><c:when test="${searchForm.searchResults.numResults > relationshipLimit}">style="float: left;"</c:when><c:otherwise>style="float: left; margin-left: 3px;"</c:otherwise></c:choose>>
				<logic:iterate name="searchForm" property="searchResults.searchResults" id="result">
					<input type="hidden" name="selectedAssetId" value="<bean:write name="result" property="id"/>"/>
				</logic:iterate>
				<logic:iterate name="searchForm" property="selectAssetParamNames" id="paramName" indexId="paramIndex">
					<logic:notEmpty name="paramName">
						<input type="hidden" name="<c:out value="${searchForm.selectAssetParamNames[paramIndex]}"/>" value="<c:out value="${searchForm.selectAssetParamValues[paramIndex]}"/>"/>
					</logic:notEmpty>
				</logic:iterate>
				<%-- id="submitSelectAllOnPageButton" is used by Selenium tests --%>
				<input id="submitSelectAllOnPageButton" type="submit" class="button" value="<bright:cmsWrite identifier="button-select-all-on-page" filter="false"/>">
				<input type="submit" class="button" name="b_cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false"/>">
			</form>
			<div class="clearing"></div>
			
		</c:if>
	
		<div class="foot">
			<%@include file="../inc/pager.jsp"%>
			<c:if test="${searchPage != 'batchrelease'}">
				<a href="viewLastSearch" class="floatLeft"><bright:cmsWrite identifier="link-search-again" filter="false" /></a>
				<c:if test="${savedSearchesEnabled && userprofile.isLoggedIn}">
					<span class="divider floatLeft">|</span>
					<a href="viewSaveSearch" class="floatLeft"><bright:cmsWrite identifier="link-save-search" filter="false" /></a>
				</c:if>
				<span class="divider floatLeft">|</span>
				<a href="viewLastSearch?refineSearch=1" class="floatLeft"><bright:cmsWrite identifier="refine-search-results" filter="false" /></a>
				
				<c:if test="${empty searchForm.selectAssetUrl && (userprofile.isAdmin || (orgUnitAdminsCanExport && userprofile.isOrgUnitAdmin))}">
					<span class="divider floatLeft">|</span>
					<a href="exportFromSearch" class="floatLeft">Export all results...</a>
				</c:if>
			</c:if>
		</div>
		
   </c:if>
	      
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>