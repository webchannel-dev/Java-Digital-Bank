<!-- Quick search -->
<div class="leftShadow">
	<div class="rightShadow">
		<div id="qsPanel"
			<c:if test="${homepageForm.userPromotedSearchCount > 0 || homepageForm.globalPromotedSearchCount > 0}">
					class="middle"
			</c:if>
		>
			
			<html:form action="quickSearch" method="get" styleClass="">
				<input type="hidden" name="newSearch" value="true"/>
				<input type="hidden" name="quickSearch" value="true"/>
				
				<label for="qkeywords" class="search"><bright:cmsWrite identifier="label-search" filter="false" /></label>
				<div class="inner"> 

					<html:text styleId="qkeywords" styleClass="text" name="searchForm" property="keywords" size="28" />

	  				<input class="button" type="submit" value="<bright:cmsWrite identifier="button-go" filter="false" />" />
	  				
					<a href="../action/viewLastSearch" class="advanced"><bright:cmsWrite identifier="link-advanced-search" filter="false" /></a>
					
					<logic:notEmpty name="quickSearchEntities">
		  				<bean:size name="quickSearchEntities" id="numEntities"/>
		  				<logic:greaterThan name="numEntities" value="1">
			  				<select name="selectedEntities">
			  					<c:if test="${anyAssetEntityAppearsFirst}">
			  						<option value="-999">[<bright:cmsWrite identifier="snippet-any-type" filter="false"/>]</option>
			  					</c:if>
			  					<logic:iterate name="quickSearchEntities" id="entity">
			  						<option value="<bean:write name="entity" property="id"/>"><bean:write name="entity" property="name" filter="false"/></option>
			  					</logic:iterate>
			  					<c:if test="${not anyAssetEntityAppearsFirst}">
			  						<option value="-999">[<bright:cmsWrite identifier="snippet-any-type" filter="false"/>]</option>
			  					</c:if>
			  				</select>
			  			</logic:greaterThan>
			  			<logic:equal name="numEntities" value="1">
			  				<logic:iterate name="quickSearchEntities" id="entity">
		  						<input type="hidden" name="entityId" value="<bean:write name="entity" property="id"/>">
		  					</logic:iterate>
			  			</logic:equal>
	  				</logic:notEmpty>

					<c:if test="${showCategoriesOnQuickSearch}">
						<ul class="radioList">
							<li><input type="radio" class="radio" id="all" name="descriptiveCategoryForm.selectedCategories" value="" checked="checked"/>
							<label for="all"><strong>All Categories</strong></label></li>
					
							<c:set var="browserCategories" value="${homepageForm.descriptiveCategories}" />
							<logic:iterate name="browserCategories" id="category">
								<li><input type="radio" class="radio" id="<bean:write name='category' property='id'/>" name="descriptiveCategoryForm.selectedCategories" value="<bean:write name='category' property='id'/>"/>
								<label for="<bean:write name='category' property='id'/>"><bean:write name='category' property='name'/></label></li>
							</logic:iterate>
						</ul>
					</c:if>

				</div>
				 
			</html:form>
		</div>  
		<logic:notEmpty name="homepageForm" property="globalPromotedSearches">
			<div class="commonSaved <logic:notEmpty name="homepageForm" property="userPromotedSearches">middle</logic:notEmpty>">
				<h3><bright:cmsWrite identifier="heading-recommended-searches" filter="false"/></h3> 
				<ul>
					<logic:iterate name="homepageForm" property="globalPromotedSearches" id="search">
						<li><c:if test="${search.image != null && search.image != ''}"><a href="runSavedSearch?id=<bright:write name="search" property="id"/>" class="thumb"><img src="../<bright:write name='search' property='image'/>" alt=""/></a></c:if>
							<a href="runSavedSearch?id=<bright:write name="search" property="id"/>"><bean:write name='search' property='name'/></a>
						</li>
					</logic:iterate>
				</ul>
			</div>
		</logic:notEmpty>
		<logic:notEmpty name="homepageForm" property="userPromotedSearches">
			<div class="commonSaved">
				<h3><bright:cmsWrite identifier="heading-my-searches" filter="false"/></h3> 
				<c:set var="userSearchCount" value="${homepageForm.userPromotedSearchCount}" />
				<ul>
					
					<%
					// Work out how many categories to put in a row:
					int iNumCols = 3;  //Default

					Integer intNumSearches = (Integer)pageContext.getAttribute("userSearchCount");
					Double dRowCount = new Double(java.lang.Math.ceil(intNumSearches.intValue()/(double)iNumCols));
					pageContext.setAttribute("rowCount", dRowCount);
				   
				   %>
					<logic:iterate name="homepageForm" property="userPromotedSearches" id="search" indexId="idx">
						<li>
							<c:if test="${search.image != null && search.image != ''}"><a href="runSavedSearch?id=<bright:write name="search" property="id"/>" class="thumb"><img src="../<bright:write name='search' property='image'/>" alt=""/></a></c:if>
							<a href="runSavedSearch?id=<bright:write name='search' property='id'/>"><bean:write name='search' property='name'/></a></li>

						<c:if test="${ (idx+1==rowCount || idx+1==(2*rowCount)) }">
							</ul>
							<ul class="right">
						</c:if>
								
					</logic:iterate>
					
				</ul>
			</div>
		</logic:notEmpty>
		        
	</div>
</div>