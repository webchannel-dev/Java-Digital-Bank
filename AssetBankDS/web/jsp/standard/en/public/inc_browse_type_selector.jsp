		<c:choose>
				<c:when test="${useCategoryExplorer == true}">
					<!-- Windows Explorer style browsing  -->
					<noscript>
						<style type="text/css">
							#explorerCategories { display: none; }
						</style>
						<%@include file="../inc/standard-browser.jsp"%>
					</noscript>
					</div><!-- end leftCol -->
					<div class="explorerBrowse" id="explorerCategories">    
	  					<c:set var="type"><bright:cmsWrite identifier="category-node"/></c:set>
						<c:set var="head"><bright:cmsWrite identifier="category-root" filter="false" /></c:set>
						<c:if test="${categoryExplorerType == 'accesslevels'}">
							<c:set var="type"><bright:cmsWrite identifier="access-level-node"/></c:set>
							<c:set var="head"><bright:cmsWrite identifier="access-level-root" filter="false" /></c:set>
						</c:if>
						<div class="homepageExplorer">
	  						<c:set var="catName"><bright:cmsWrite identifier="category-root" filter="false" /></c:set>
	  						<h3><bright:cmsWrite identifier="heading-browse" filter="false"/> <bean:write name='head' />:</h3>
							<c:choose>
								<c:when test="${categoryId > 0}">
									<bean:define name="categoryId" id="categoryIdBean"/>
								</c:when>
								<c:otherwise>
									<bean:define name="homepageForm" property="browseCategoryId" id="categoryIdBean"/>
								</c:otherwise>
							</c:choose>
							<ab:categoryExplorer link="browseExplorerItems?categoryId=&categoryTypeId=" className="explorer" categoryTypeId="${categoryExplorerType == 'accesslevels'?2:1}" selectedCategoryIdBean="categoryIdBean" />
						</div>
						<div id="categoryContent" class="homePageBrowse">
							<h3><bright:cmsWrite identifier="snippet-category-explorer" filter="false"/></h3>
						</div>
					</div>
					<div><!-- dummy div to cater for leftCol end -->
				</c:when>
				<c:when test="${onePanelPerCategory == true}">
					<!-- Show one panel per top level category  -->
					<logic:iterate name='browserCategories' id='category' indexId='idx'>
						<c:set var="browserCategories" value="${category.childCategories}" />
						<bean:size id="browserNumCategories" name="category" property="childCategories"/>
						<c:set var="browserTitle"><a href="browseItems?categoryId=<c:out value="${category.id}"/>&categoryTypeId=1"><c:out value="${category.name}"/></a></c:set>
						<c:set var="browserHasImages" value="${category.atLeastOneChildHasImage}" />
						<c:set var="browserCanHaveTabs" value="false" />
						<%@include file="../inc/standard-browser.jsp"%>
					</logic:iterate>
				</c:when>
				<c:otherwise>
					<!-- Standard browsing  -->
					<%@include file="../inc/standard-browser.jsp"%>			
				</c:otherwise>		
			</c:choose> 
