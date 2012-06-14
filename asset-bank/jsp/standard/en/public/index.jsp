<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design
	 d3     Steve Bryan		20-Dec-2005		Browse both descriptive and permissions categories
     d4     Ben Browning	07-Feb-2006		Tidy up html
	 d5		Matt Stevenson	04-Dec-2006		Work on category explorer
	 d6		Ben Browning	19-Feb-2007		HTML/CSS improvements
	 d7		Matt Stevenson	29-Nov-2007		Added 'not logged in' message	
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/ab-tag.tld" prefix="ab" %>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewHome"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="menu-home" /></c:set>

<bright:applicationSetting id="showCategoryItemCounts" settingName="showCategoryItemCounts"/>
<bright:applicationSetting id="iFeaturedImageWidth" settingName="featured-image-width"/>

<bright:applicationSetting id="hideSingleAccessLevelOnHomepage" settingName="hide-single-access-level"/>	
<bright:applicationSetting id="catImageWidth" settingName="category-image-width"/>
<bright:applicationSetting id="catImageHeight" settingName="category-image-height"/>
<bright:applicationSetting settingName="image-homogenized-max-height" id="thumbnailHeight"/>
<bright:applicationSetting settingName="image-homogenized-max-width" id="thumbnailWidth"/>
<bright:applicationSetting id="extraRecentlyAddedImages" settingName="num-more-recent-images"/> 
<bright:applicationSetting id="newsPanelPosition" settingName="news-panel-position"/>
<bright:applicationSetting id="autoCompleteQuickSearchEnabled" settingName="auto-complete-quick-search-enabled"/>

<bean:parameter id="categoryId" name="categoryId" value="0"/>
<%@include file="../inc/set_this_page_url.jsp"%>

<head>
	
	<title><bright:cmsWrite identifier="title-home-page" filter="false"/></title> 
	<bean:define id="section" value="home" toScope="request" />
	<c:set var="userprofile" value="${userprofile}" scope="request" />
	<jsp:include page="../inc/head-elements.jsp" />

	
	<c:set var="catPage" value="${userprofile.selectedPage}"/>
	<c:set var="pageSize" value="${userprofile.selectedPageSize}"/>
	
	<script type="text/javascript">
		<!--
			function pageCallback(response)
			{
				document.getElementById('categoryContent').innerHTML = response;
			}
		
			//var ajax = new GLM.AJAX();

			//Initialise autocomplete, give quick search the focus when dom is ready
			$j(function () {
  				$j('#qkeywords').focus();
  				<c:if test="${autoCompleteQuickSearchEnabled}">
					//initialise jquery autocompleter on  search field: BB-AutoComplete - hide this if quicksearch autocomplete disabled!
					initJQAutocompleter($j('#qkeywords'));
				</c:if>
				
				<c:if test="${categoryExplorerType == 'categories' || categoryExplorerType == 'accesslevels'}">
				
					// load explorer
					
					$j('#explorerCategories').load('../action/viewExplorerAjax', function(){
						bbExplorer.init();
					});
					
					
					// Code for drodpown action links -----
					//clicking anywhere in the page will close the action dropdown (except anywhere that 
					// has .stopPropagation() in its click event)
					$j(document).click(function(){
						$j("#mainCol ul.dropOptions").hide();
						$j("#mainCol a.dropButton").removeClass('on');
						$j("#mainCol a.dropLink").removeClass('on');
					});
			
					//click event on dropdown button or link expander
					$j('#explorerCategories').delegate('div.dropHolder a.dropLink', 'click', function(e) {
						//stop sliding up again
						e.stopPropagation();
						$j(this).toggleClass('on').siblings('ul.dropOptions').toggle();
						return false;
					});
					
					//variables needed for opening the correct page of the category explorer when displaying the homepage...
					bbExplorer.pagingParams = {
						page: <c:out value='${userprofile.selectedPage}' />,
						pageSize: <c:out value='${userprofile.selectedPageSize}' />
					}	
				
				</c:if>

				
			});			
			
		-->
	</script>
	
	<logic:present name="facebookLikeButton" >
  		<bright:applicationUrl/>
		<c:set var="fbCompleteUrl" 		scope="request" value="${applicationUrl}/action/viewHome" />
		<%@include file="../customisation/inc_logo_for_facebook.jsp"%>
		<c:set var="fbPreviewImageUrl" 	scope="request" value="${applicationUrl}${logoForFacebook}" />
		<c:set var="fbPageTitle"		scope="request" ><bright:cmsWrite identifier="company-name" /></c:set>
		<jsp:include flush="true" page="inc_facebook_like_button_meta.jsp"/>
	</logic:present>
</head>



<body id="homePage">
	<%@include file="../inc/body_start.jsp"%> 
         
   <div id="rightCol">
   	<%@include file="../customisation/homepage_content.jsp"%>
		<!-- Featured image, wrapped in div to prevent vertical whitespace in IE due to linebreak -->
		<bean:define id="rightClickMessage" value="Please do not attempt to download this image by using the right-click menu."/>
		
		<%--  We get the homepage-no-featured-image copy item here just to check for it existing  --%>
		<bright:refDataList id="featuredCopyItem" transactionManagerName="DBTransactionManager" componentName="ListManager" methodName="getListItem" argumentValue="homepage-no-featured-image"/>	
		<c:choose>
			<c:when test="${not empty homepageForm.featuredAsset}">
				<!-- Featured Image -->
				<div class="leftShadow">
					<div class="rightShadow">		
						<bean:define id="asset" name="homepageForm" property="featuredAsset" />
						<%@include file="../inc/view_featured_thumb.jsp"%>	
					</div>
				</div>
				<c:if test="${asset.isVideo}">
					<p class="featuredCopy"><a href="../action/viewAsset?id=<bean:write name='asset' property='id'/>"><bright:cmsWrite identifier="link-view-item" /> &raquo;</a></p>
				</c:if>
				<p><bright:cmsWrite identifier="label-featured-image-caption" filter="false"/></p>
			</c:when>  
			<c:when test="${empty homepageForm.featuredAsset && (categoryExplorerType == 'categories' || categoryExplorerType == 'accesslevels') && empty featuredCopyItem.body && categoryExplorerType == 'none'}">
				<div class="recent">
					<logic:iterate name="homepageForm" property="recentImages" id="image" indexId="index">
						<a class="imgWrapper squareThumb<c:out value="${index+1}"/>" href="../action/viewAsset?id=<bean:write name='image' property='id'/>" >
							<bean:define id="asset" name="image"/>
							<%@include file="../inc/view_homogenized_thumb.jsp"%>									
						</a>
					</logic:iterate>
					<div class="clearing"><!--  --></div>
				</div>
			</c:when>
			<c:when test="${iFeaturedImageWidth > 0}">
				<%@include file="../customisation/no_featured_image.jsp"%>	
			</c:when>				
		</c:choose>
	
		<!-- Promoted Assets -->
		<bean:size id="numPromoted" name="homepageForm" property="promotedAssets"/>
		<c:if test="${numPromoted >0}">
			<div class="promo">
				<h3><a href="../action/viewPromotedAssets"><bright:cmsWrite identifier="subhead-promoted-items" filter="false" /></a></h3>
				
				<logic:iterate name="homepageForm" property="promotedAssets" id="image" indexId="index">
					<a class="imgWrapper squareThumb<c:out value="${index+1}"/> <logic:empty name="image" property="displayHomogenizedImageFile.path">iconWrapper</logic:empty>" href="../action/viewAsset?id=<bean:write name='image' property='id'/>"  title="<c:out value='${image.searchName}'/>">
				
						<logic:empty name="image" property="displayHomogenizedImageFile.path">
							<bean:define id="asset" name="image" />
							<bean:define id="disablePreview" value="true"/>	
							<%@ include file="../inc/view_thumbnail.jsp" %>
						</logic:empty>
						<logic:notEmpty name="image" property="displayHomogenizedImageFile.path">
							<bean:define id="asset" name="image" />
							<%@ include file="../inc/view_homogenized_thumb.jsp" %>
						</logic:notEmpty>
									
					</a>
				</logic:iterate>  
				
				<p style="clear:both; height:1%;"><a href="../action/viewPromotedAssets"><bright:cmsWrite identifier="link-see-promoted" filter="false" /></a></p>
			</div>
		</c:if>
		
		
		<!-- Recent Assets -->
		<logic:notEmpty name="homepageForm" property="recentImages" > 
			<!-- only show recent images for non category explorer -->
			<c:if test="${categoryExplorerType == 'none'}">
				<div class="recent">
					<h3><a href="../action/viewRecentAssets"><bright:cmsWrite identifier="subhead-recently-added" filter="false" /></a></h3>
					<logic:iterate name="homepageForm" property="recentImages" id="image" indexId="index">
						<a class="imgWrapper squareThumb<c:out value="${index+1}"/>" href="../action/viewAsset?id=<bean:write name='image' property='id'/>" title="<c:out value='${image.searchName}'/>">
							<bean:define id="asset" name="image"/>
							<%@ include file="../inc/view_homogenized_thumb.jsp" %>
						</a>	
					</logic:iterate>
					<c:if test="${ extraRecentlyAddedImages > 0 }">
						<p style="clear:both; height:1%;"><a href="../action/viewRecentAssets"><bright:cmsWrite identifier="link-see-recent" filter="false" /></a></p>
					</c:if>
				</div>	
			</c:if>
		</logic:notEmpty>
		
		<c:if test="${newsPanelPosition=='right'}">
			<jsp:include page="../inc/inc_news_panel.jsp"/>
		</c:if>

		<!-- Facebook Like -->
		<logic:present name="facebookLikeButton" >
				<jsp:include flush="true" page="inc_facebook_like_button.jsp"/>
		</logic:present>

   </div>   <!-- End of rightcol -->
   
	<div id="leftCol">
	
		<c:choose>
			<c:when test="${not empty homepageForm.customWelcomeText}">
				<bean:write name="homepageForm" property="customWelcomeText" filter="false"/>
			</c:when>
			<c:otherwise>
				<!-- The welcome text: this comes from the database (change in the 'Content' area of Admin) -->
				<bright:cmsWrite identifier="homepage-main" filter="false" replaceVariables="true"/>
			</c:otherwise>			
		</c:choose>
		

		<c:if test="${!userprofile.isLoggedIn}">
			<bright:cmsWrite identifier="homepage-not-logged-in" filter="false" replaceVariables="true"/>
		</c:if>
		
		<c:if test="${userprofile.canManageBatchReleases}">
			<jsp:include page="../batch-release/inc_acknowledgement_summary.jsp"/>
		</c:if>
		
		<c:set scope="session" var="messageDetailReturnUrl" value="../action/viewHome"/>	
		<jsp:include page="inc_messages_panel.jsp"/>

		<c:set var="homepageForm" value="${homepageForm}" scope="request" />
		<c:set var="anyAssetEntityAppearsFirst" value="${anyAssetEntityAppearsFirst}" scope="request" />
		<jsp:include page="inc_quick_search.jsp"/>
		
		<jsp:include page="inc_slideshow_panels.jsp"/>
			
		<%@include file="inc_browse_panels.jsp"%>  

		

		<c:if test="${newsPanelPosition=='middle'}">
			<br/>
			<jsp:include page="../inc/inc_news_panel.jsp"/>
		</c:if>
		
		<bean:size id="numGlobalFeaturedSearches" name="homepageForm" property="globalFeaturedSearches"/>
		<c:if test="${numGlobalFeaturedSearches gt 0}">
		<div class="leftShadow">
			<div class="rightShadow">
				<div class="browsePanel">
					<logic:iterate id="globalFeaturedSearches" name="homepageForm" property="globalFeaturedSearches" indexId="index">
		
		
						<a href="runSavedSearch?id=<bright:write name="globalFeaturedSearches" property="key.id"/>" class="viewMore">
							<bright:cmsWrite identifier="homepage-view-more" filter="false"/>
						</a>
			
						<h3><bean:write name="globalFeaturedSearches" property="key.name"/></h3>

						<div class="imgStrip <c:if test="${index == numGlobalFeaturedSearches-1}">last</c:if>">
							<logic:iterate name="globalFeaturedSearches" property="value.searchResults" id="image">
								<a href="../action/viewAsset?id=<bean:write name='image' property='id'/>" title="<c:out value='${image.searchName}'/>">
									<bean:define id="asset" name="image" />

									<logic:empty name="image" property="displayHomogenizedImageFile.path">
										<bean:define id="asset" name="image" />
										<bean:define id="disablePreview" value="true"/>	
										<%@ include file="../inc/view_thumbnail.jsp" %>
									</logic:empty>
									<logic:notEmpty name="image" property="displayHomogenizedImageFile.path">
										<bean:define id="asset" name="image" />
										<%@ include file="../inc/view_homogenized_thumb.jsp" %>
									</logic:notEmpty>
							
									<logic:notEqual name='includeDims' value='true'>
										<style type="text/css">
											/*center images within box if retaining aspect ratios*/
											#homePage a.imgWrapper {  
												height:<c:out value='${thumbnailWidth + 6}'/>px !important;
												width:<c:out value='${thumbnailWidth + 6}'/>px !important;
												text-align: center;
												line-height:<c:out value='${thumbnailWidth + 6}'/>px !important;
												}	
			
											#homePage a.imgWrapper img {  
												margin-left: auto !important;
											   margin-right: auto !important;
												display:inline !important; 
												vertical-align:middle
												}	
										</style>
	
										<hr/>
									</logic:notEqual>
								</a>
				
							</logic:iterate>
	
						</div> <!-- End of .imageStrip -->
						<div class="clearing">&nbsp;</div>

					</logic:iterate>
				</div>
			</div>	
		</div>	
		</c:if>	
						
		<!-- Footer text -->
		<bright:refDataList id="footCopyItem" componentName="ListManager" methodName="getListItem" argumentValue="homepage-bottom"/>	
		<c:if test="${not empty footCopyItem}">
			<bean:write name="footCopyItem" property="body" filter="false"/>					     
		</c:if>

    </div>	<!-- end of left col -->
	<div class="clearing">&nbsp;</div>
	
	
	
	  
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>