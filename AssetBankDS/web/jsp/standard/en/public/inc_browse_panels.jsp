		<bright:applicationSetting id="onePanelPerCategory" settingName="show-one-panel-per-category-on-homepage"/>	
			
		<!-- Browse Categories -->
		<c:set var="browserTitle"><bright:cmsWrite identifier="heading-browse" filter="false"/> <bright:cmsWrite identifier="category-root" filter="false" />:</c:set>
		<c:set var="browserHasImages" value="${homepageForm.descriptiveCategoriesHaveImages}" />
		<c:set var="browserNumCategories" value="${homepageForm.numDescriptiveCategories}" />
		<c:set var="browserCategories" value="${homepageForm.descriptiveCategories}" />
		<c:set var="showAllCatsLink" value="true"/>
		
	  	<c:if test="${browserNumCategories gt 0}">
			<c:set var="useCategoryExplorer" value="${categoryExplorerType == 'categories'}" />
	    	<c:set var="browserCanHaveTabs" value="true" />
			<%@include file="inc_browse_type_selector.jsp"%>
		</c:if>    
		<div class="clearLeft">&nbsp;</div>    
		
		<!-- Browse Access Levels -->           
		<c:set var="browserTitle"><bright:cmsWrite identifier="heading-browse" filter="false"/> <bright:cmsWrite identifier="access-level-root" filter="false" /></c:set>
		<c:set var="browserHasImages" value="${homepageForm.permissionCategoriesHaveImages}" />
		<c:set var="browserNumCategories" value="${homepageForm.numPermissionCategories}" />
		<c:set var="browserCategories" value="${homepageForm.permissionCategories}" />
		<c:set var="showAllCatsLink" value="false"/>
		
		<c:if test="${ ( accessLevelsOnHomepage && browserNumCategories gt 0 && ( !hideSingleAccessLevelOnHomepage || browserNumCategories gt 1 ) )}">	
			<c:set var="useCategoryExplorer" value="${categoryExplorerType == 'accesslevels'}" />
	    	<c:set var="browserCanHaveTabs" value="false" />
			<%@include file="inc_browse_type_selector.jsp"%>		
	 	</c:if>    


		<c:set var="homepageMessages"><bright:cmsWrite identifier="homepage-message" filter="false" /></c:set>
		
		<logic:notEmpty name="homepageMessages">	
			<div class="clearLeft">&nbsp;</div>    

			
			<div class="leftShadow">
				<div class="rightShadow">
					<div class="browsePanel">
						<bean:write name="homepageMessages" filter="false"/>
					</div>
				</div>
			</div>


		</logic:notEmpty>