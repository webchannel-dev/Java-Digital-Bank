<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Chris Preager	10-May-2005		Created.
	 d2		Matt Stevenson	07-Oct-2005		Copied in category column code
    d3      Ben Browning   14-Feb-2006    HTML/CSS tidy up
    d4      Matt Woollard  03-Jun-2009    Added option to show filters as dropdowns
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<logic:present parameter="page">
	<bean:parameter id="p_page" name="page"/>
</logic:present>
<logic:notPresent parameter="page">
	<bean:define id="p_page" value="0"/>
</logic:notPresent>

<%@include file="../inc/set_this_page_url.jsp"%>
<bean:parameter id="categoryTypeId" name="categoryTypeId" value="1" />
<bean:parameter name="allCats" id="allCats" value="0"/>
		

<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="titleMaxLength" settingName="browse-title-max-length"/>
<bright:applicationSetting id="descriptionMaxLength" settingName="browse-description-max-length"/>

<bright:applicationSetting id="showCategoryItemCounts" settingName="showCategoryItemCounts"/>
<bright:applicationSetting id="hideThumbnails" settingName="hide-thumbnails-on-browse-search"/>
<bright:applicationSetting id="bShowAddAllToLightbox" settingName="show-add-all-to-lightbox"/>
<bright:applicationSetting id="browseFiltersAreDropdowns" settingName="browse-filters-are-dropdowns"/>
<bright:applicationSetting id="enableSlideshow" settingName="enable-slideshow"/>

<c:choose>
	<c:when test="${categoryTypeId == 1}">
		<bean:define id="section" value="browse"/>
		<c:set var="browseableName"><bright:cmsWrite identifier="category-node" filter="false" /></c:set>
		<c:set var="browseablesName"><bright:cmsWrite identifier="category-nodes" filter="false" /></c:set>
	</c:when>
	<c:otherwise>
		<bean:define id="section" value="browse"/>
		<c:set var="browseableName"><bright:cmsWrite identifier="access-level-node" filter="false" /></c:set>
		<c:set var="browseablesName"><bright:cmsWrite identifier="access-level-nodes" filter="false" /></c:set>
	</c:otherwise>
</c:choose>

<bean:define id="catForName" name="browseItemsForm" property="category" />
<%@include file="inc_category_name.jsp" %>
<c:set var="categoryName"><bean:write name='catName' /></c:set>
<head>
	
	<title><bright:cmsWrite identifier="title-browse" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<script type="text/JavaScript">
			
		$j(function() {		// when page has loaded

			//clicking anywhere in the page will close the action dropdown (except anywhere that 
			// has .stopPropagation() in its click event)
			$j(document).click(function(){
				$j("#mainCol ul.dropOptions").hide();
				$j("#mainCol a.dropButton").removeClass('on');
				$j("#mainCol a.dropLink").removeClass('on');
			});
			
			//click event on dropdown button or link expander
			$j('#mainCol .dropHolder a.dropLink').click(function(e) {
				//stop sliding up again
				e.stopPropagation();
				$j(this).toggleClass('on').siblings('ul.dropOptions').toggle();
				return false;
			});

	
		});

	</script>
	
</head>
<body id="browsePage">
	<c:set var="displayAttributeGroup" value="2" scope="request" />
	<%@include file="../inc/body_start.jsp"%>



	<h1>
		<bright:cmsWrite identifier="heading-browse" filter="false" /> 
	</h1> 
	
	<div class="warning printOnlyIE67">
		<p><strong>Note:</strong> For optimum printing results we recommend using an up-to-date browser such as Internet Explorer 8, Firefox, or Google Chrome.</p>
	</div>	
	<bean:define id="tabId" value="browseCategories"/>
	<%@include file="../public/inc_browse_tabs.jsp"%>
	

	
	
	<logic:equal name="userprofile" property="isAdmin" value="true">
		<c:set var="browseableCategories" value="${browseItemsForm.browseableCategoriesForAdmin}" />
	</logic:equal>
	<logic:equal name="userprofile" property="isAdmin" value="false">
		<c:set var="browseableCategories" value="${browseItemsForm.browseableCategoriesForNonAdmin}" />
	</logic:equal>
			
		
	<%-- Set and show Breadcrumb trail --%>
	<c:set scope="session" var="breadcrumbTrail" value="${browseItemsForm.breadcrumbTrail}" />	
	<div class="head <c:if test="${tabsPresent}">tabsAbove</c:if> <c:if test='${ (browseItemsForm.searchResults.numResults > 0)  && (empty browseableCategories)}'>stripBelow</c:if>" >
		<c:if test="${enableSlideshow && browseItemsForm.containsImages}">
			<script type="text/javascript">
				<!--
					document.write("<a href='viewBrowseSlideshow?pageSize=<bean:write name='userprofile' property='selectedPageSize'/>&page=<bean:write name='userprofile' property='selectedPage'/>&returnUrl=<c:out value='${thisUrlForGet}'/>' class='slideshow'><bright:cmsWrite identifier='link-this-page-as-slideshow' filter='false'/></a>");
				-->
			</script>
		</c:if>
		<c:choose>
			<c:when test="${allCats > 0}">
				<a href="viewAlphabeticCategories"><bright:cmsWrite identifier="link-back-all-cats" filter="false" /></a>
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="browse-intro" filter="false"/>
				
		   		<bean:size id="trailSize" name="breadcrumbTrail" />
					<bright:cmsWrite identifier="you-are-here" filter="false" />&nbsp;
					<logic:iterate name="breadcrumbTrail" id="entry" indexId="id">
						<c:choose>
							<c:when test="${id < (trailSize - 1)}">
								<a href="<c:out value='${entry.value}' />"><bean:write name="entry" property="key" filter="false"/></a>
								&raquo;
							</c:when>
							<c:otherwise>
								<bean:write name="entry" property="key" filter="false"/>	
							</c:otherwise>
						</c:choose>
					</logic:iterate>  
	   		</c:otherwise>
		</c:choose>
   	
   	
	</div><!-- end of div.head -->
	
   
   <%-- Error (generally validation) reporting --%>
   	
   <logic:equal name="browseItemsForm" property="hasErrors" value="true">
		<div class="error">
	   	<logic:iterate name="browseItemsForm" property="errors" id="error">
	   		<bean:write name="error" filter="false"/><br />
	   	</logic:iterate>
		</div>
   </logic:equal>
	

	<%-- Category copy, if there is any --%>
	
	<logic:notEmpty name="browseItemsForm" property="category.description">
		<div class="clearedContainer">
			<bean:write name="browseItemsForm" property="category.description" filter="false"/>
		</div>
	   <div class="hr"></div>   
	</logic:notEmpty>
   

	<%-- Subcategory list --%>

   <c:if test="${allCats < 1}">
	
		
		<c:if test="${(!empty browseableCategories) || (empty browseItemsForm.filters)}">


			<c:choose>
				<c:when test="${browseItemsForm.category.depth == 0}">
					<h2><bean:write name='catName'/></h2>
				</c:when>
				<c:otherwise>
					<c:if test='${!empty browseableCategories}'>
						<h3><bright:cmsWrite identifier="subhead-subcats-cats" filter="false" replaceVariables="true" /></h3>
					</c:if>
				</c:otherwise>
			</c:choose>

			<c:if test='${!empty browseableCategories}'>
   			<div class="categoryList clearfix underline">
					<bright:applicationSetting id="catImageWidth" settingName="category-image-width"/>
					<bright:applicationSetting id="catImageHeight" settingName="category-image-height"/>
   	
				
					<ul class="left <c:if test='${browseItemsForm.categoriesHaveImages}'>catThumbs</c:if>" >
						<c:set var="index" value="0"/>
						<logic:iterate name="browseableCategories" id="category">
							<%-- If required, get the number of items in the category--%>
							<logic:equal name="showCategoryItemCounts" value="true">
								<bright:itemCount id="itemCount" name="category"/>
							</logic:equal>
							<logic:equal name="category" property="isBrowsable" value="true">
								<c:set var="index" value="${index + 1}"/>
								<c:choose>
										<c:when test="${not empty category.imageUrl}">
											<!-- category image exists -->
											<li>
												<table cellspacing="0" cellspadding="0">
													<tr>
														<td style="padding-right:5px"><a href="../action/browseItems?categoryId=<bean:write name='category' property='id' />&amp;categoryTypeId=<bean:write name='category' property='categoryTypeId' />&allCats=<bean:write name='allCats'/>"><img src="../<bean:write name='category' property='imageUrl'/>" width="<bean:write name='catImageWidth'/>" height="<bean:write name='catImageHeight'/>" alt="Image thumbnail for category" /></a></td>
														<td><a href="../action/browseItems?categoryId=<bean:write name='category' property='id' />&amp;categoryTypeId=<bean:write name='category' property='categoryTypeId' />&allCats=<bean:write name='allCats'/>"><bean:write name='category' property='name' filter="false"/></a><logic:present name="itemCount">&nbsp;<bdo dir="ltr">(<bean:write name="itemCount"/>)</bdo></logic:present>												
														</td>
													</tr>
												</table>
											</li>
										</c:when>
										<c:otherwise>
											<!-- no image -->
											<li>
												<a href="../action/browseItems?categoryId=<bean:write name='category' property='id' />&amp;categoryTypeId=<bean:write name='category' property='categoryTypeId' />"><bean:write name='category' property='name' filter="false"/></a><logic:present name="itemCount">&nbsp;<bdo dir="ltr">(<bean:write name="itemCount"/>)</bdo></logic:present>
											</li>
										</c:otherwise>
									</c:choose>		
									<c:if test="${((index) == browseItemsForm.colLength) || ((index) == (browseItemsForm.colLength*2))}">
										</ul>
										<ul class="left <c:if test='${browseItemsForm.categoriesHaveImages}'>catThumbs</c:if>" >
									</c:if>
								</logic:equal>
						</logic:iterate>
					</ul>
					<div class="clearing"></div>

   			</div>
   			
   			<c:if test="${browseItemsForm.category.id <= 0}">
					<bright:applicationSetting id="allCatsLink" settingName="show-all-categories-link"/>
					<c:if test="${allCatsLink}">
						<br/><p><a href="viewAlphabeticCategories"><bright:cmsWrite identifier="link-view-all-cats" filter="false" /></a></p>
					</c:if>
				</c:if>
			
   		</c:if>


   	</c:if>
   </c:if>
   
	<c:choose>
		<c:when test="${!browseFiltersAreDropdowns && (empty browseItemsForm.selectedFilter || browseItemsForm.selectedFilter.id <= 0)}">
			<logic:notEmpty name="browseItemsForm" property="filters">
				<div class="clearing"></div> 
				<br />		
				<h3><bright:cmsWrite identifier="heading-filter" filter="false"/> <bean:write name='catName'/></h3>
				<div class="categoryList clearfix underline">
					
					<ul class="left">
					<c:set var="index" value="0"/>
					<logic:iterate name="browseItemsForm" property="filters" id="filter">
						<c:set var="index" value="${index+1}"/>
						<li><a href="browseItems?categoryId=<bean:write name='browseItemsForm' property='category.id'/>&categoryTypeId=<bean:write name='browseItemsForm' property='category.categoryTypeId'/>&filterId=<bean:write name='filter' property='id'/>"><bean:write name='filter' property='name'/></a></li>
						<c:if test="${((index) == browseItemsForm.filterColLength) || ((index) == (browseItemsForm.filterColLength*2))}">
							</ul>
							<ul class="left" >
						</c:if>
					</logic:iterate>
					</ul>
					<div class="clearing"></div> 
				</div>
			</logic:notEmpty>
		</c:when>
		<c:otherwise>
			<logic:notEmpty name="browseItemsForm" property="filters">
				<h3><bright:cmsWrite identifier="heading-filter" filter="false"/> <bean:write name='catName'/></h3>
				<form action="browseItems" method="get">
					<input type="hidden" name="categoryId" value="<bean:write name='browseItemsForm' property='category.id'/>"/>
					<input type="hidden" name="categoryTypeId" value="<bean:write name='browseItemsForm' property='category.categoryTypeId'/>"/>
					<select name="filterId">
							<option value="0">[<bright:cmsWrite identifier="snippet-no-filtering" filter="false"/>]</option>
							<c:set var="index" value="0"/>
							<logic:iterate name="browseItemsForm" property="filters" id="filter">
								<option value="<bean:write name='filter' property='id'/>"<c:if test="${filter.id==browseItemsForm.selectedFilter.id}"> selected</c:if>><bean:write name='filter' property='name'/></option>
							</logic:iterate>
					</select>
					<input type="submit" name="submit" class="button flush" value="Go">
				</form>
				<br/>
			</logic:notEmpty>
		</c:otherwise>
	</c:choose>
	
	
	
	

   <%-- Images within --%>	   
	<%@include file="inc_panelised_assets.jsp"%>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>
