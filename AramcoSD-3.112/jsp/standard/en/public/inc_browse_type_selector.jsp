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
						<h3 class="js-enabled-show progressLoader" style="margin-top:10px"><img src="../images/standard/misc/ajax_loader.gif" width="24" height="24" alt="Ajax Loader" />Loading explorer, <span>please wait.</span></h3>
					</div>
					<div><!-- dummy div to cater for leftCol end -->
				</c:when>
				<c:when test="${onePanelPerCategory == true}">
					<div class="onePerCategoryWrap">
						<!-- Show one panel per top level category  -->
						<logic:iterate name='browserCategories' id='category' indexId='idx'>
							<c:set var="browserCategories" value="${category.childCategories}" />
							<bean:size id="browserNumCategories" name="category" property="childCategories"/>
							<c:set var="browserTitle"><a href="browseItems?categoryId=<c:out value="${category.id}"/>&categoryTypeId=1"><c:out value="${category.name}"/></a></c:set>
							<c:set var="browserHasImages" value="${category.atLeastOneChildHasImage}" />
							<c:set var="browserCanHaveTabs" value="false" />
							<%@include file="../inc/standard-browser.jsp"%>
						</logic:iterate>
					</div>
				</c:when>
				<c:otherwise>
					<!-- Standard browsing  -->
					<%@include file="../inc/standard-browser.jsp"%>			
				</c:otherwise>		
			</c:choose> 
