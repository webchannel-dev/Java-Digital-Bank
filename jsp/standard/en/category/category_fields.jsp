<%-- 
	Included in pages using the categories. 
	
	Either a javascript control or a checkbox control is created.

	Required JSP beans per control - set in inc_asset_category_fields
		topLevelCategories - containing the top-level category list
		subCategoryIterator - containing a row for the max num of possible sub categories.
		flatCategoryList - used for checkbox version of control
		ctrlTree - name of the tree ('desc' or 'perm')
		ctrlTitle - title for the control
		ctrlAvailableSubtitle - subtitle for the available section
		ctrlIncludedSubtitle - subtitle for the included textbox
		ctrlPropertyName - the property of the form we are writing to
		section - extra section added for 'search'

	Optional JSP beans for customising control - set in calling page:
		ctrlHeading - optional control heading	
		ctrlIsCheckboxControl - 1 to force a checkbox control
		ctrlHideAndSelectSingleCategory - 1 to do what it says if there is only one category

	 
	Outputs:
		${ctrlPropertyName} - comma delimited string of Ids

	History:
	 d1		Martin Wilson	23-Oct-2003		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3		Chris Preager	27-May-2005		Made non-JS friendly
	 d4      Steve Bryan    19-Dec-2005    Changed to be used by both descriptive and permission categories.
	 d5     Matt Woollard   23-May-2008     Changes for attribute templates
--%>
<bright:applicationSetting id="maxNumCatsForNonJS" settingName="maxNumCatsForNonJS"/>
<bright:applicationSetting id="showEmptyCategories" settingName="show-empty-categories"/>
<bean:size id="numCats" name="flatCategoryList" />



<c:choose>
	<c:when test="${ctrlHideAndSelectSingleCategory == 1 && !showSingleAccessLevel && ( (userprofile.isAdmin && numCats == 2) || (!userprofile.isAdmin && numCats==1))}">
		<%-- For search don't include the control at all, otherwise select it --%>
		<c:if test="${!bIsSearch}">
			<logic:iterate name="flatCategoryList" id="item">
				<c:if test="${item.id != -1}">
					<c:set var="catId" value="${item.id}" />
				</c:if>
			</logic:iterate>
			
			<%-- Force type to checkbox control so that mandatory validation will work --%>
			<c:set var="ctrlIsCheckboxControl" value="1" />
			<html:hidden name="assetForm" property="${ctrlPropertyName}" value="${catId}" />
		</c:if>
		
	</c:when>
	
	<c:otherwise>
		<c:if test="${ctrlIsCheckboxControl == 0}">

		<script type="text/javascript">
		/* <![CDATA[ */
		
		
		function doEmptyCategoryFilter(a_listBox, iChangedListNumber, tree)
		{
			<logic:equal name="userprofile" property="isAdmin" value="false">
				<logic:equal name="showEmptyCategories" value="false">
			iNewListNumber = iChangedListNumber + 1;
			subListControl = document.getElementById("cat" + tree + iNewListNumber);
			if (subListControl)
			{
				if(subListControl.options.length > 0)
				{
					for(i=subListControl.options.length-1; i>=1; i--)
					{
						if(isCategoryEmpty(subListControl.options[i].value))
						{
							subListControl.options[i] = null;
						}
					}
					
				}
			}
				</logic:equal>
			</logic:equal>
		}
		
		<logic:equal name="userprofile" property="isAdmin" value="false">
			function isCategoryEmpty(i)
			{
				return !(false<logic:iterate name="flatCategoryList" id="item" indexId="i">||i==<bean:write name="item" property="id"/></logic:iterate>);
			}
		</logic:equal>
		
		document.write('<table class="searchCats" cellspacing="0" cellpadding="4">');
		
		<c:if test="${ctrlHeading != null || ctrlTitle != null}">
			document.write('	<tr>');
			document.write('		<th>');
		
			<logic:notEmpty name="ctrlHeading">
				document.write('<bean:write name='ctrlHeading' filter='false'/> ');
			</logic:notEmpty>
		
			<logic:notEmpty name="ctrlTitle">
				document.write(' <bean:write name='ctrlTitle' filter='false' /> ');
			</logic:notEmpty>
		
			<%-- Determine whether to show categories/access levels as mandatory --%>
			<c:if test="${
			(ctrlTree == 'perm' && !bIsSearch) ||
			(ctrlTree == 'desc' && !sIsImport && bValidateMandatories && categoriesMandatory && !bIsSearch) ||
			(ctrlTree == 'desc' && sIsImport && bValidateMandatories && categoriesMandatoryBulk && !bIsSearch)
			}">
				document.write(' <span class=\'required\'>*</span>');
			</c:if>

			document.write('		<\/th>');
			document.write('		<td><\/td>');
			document.write('	<\/tr>');
		</c:if>
		document.write('	<tr>');
		document.write('		<td colspan="2">');
		document.write('			<label for=\"cat<bean:write name='ctrlTree' />-1\">');
		document.write('				<bean:write name='ctrlAvailableSubtitle' filter='false'/>');
		document.write('			<\/label>');
		document.write('		<\/td>');
		document.write('	<\/tr>');
		document.write('	<tr>');
		document.write('		<td colspan="2">');
		document.write('			<div class="categoryListBox" id="topLevelListBox">');
		document.write('				<select class="category" id="cat<bean:write name='ctrlTree' />-1" name="cat<bean:write name='ctrlTree' />-1" onchange="changedCategory(this, -1, \'<bean:write name='ctrlTree' />\');doEmptyCategoryFilter(this, -1, \'<bean:write name='ctrlTree' />\');">');
		document.write('					<option value="0">- <bright:cmsWrite identifier="snippet-please-select" filter="false" removeLinebreaks="true"/> -<\/option>');
		
										<logic:iterate name="topLevelCategories" id="category">
											<c:if test="${!bIsSearch || category.isBrowsable}">
												document.write('<option value="<bean:write name='category' property='id'/>"><bean:write name='category' property='javaScriptEncodedName' filter='false'/><\/option>');
											</c:if>
										</logic:iterate>
		
		document.write('				<\/select>');
		document.write('				<input type="button" class="button" value="<bright:cmsWrite identifier='button-add' filter='false' />" onclick="addSelectedCategory(\'cat<bean:write name='ctrlTree' />-1\', \'<bean:write name='ctrlTree' />\'); return false;">');
		document.write('			<\/div>');
		document.write('		<\/td>');
		document.write('	<\/tr>');
		
									<logic:iterate name="subCategoryIterator" id="temp" indexId="subIndex">
									
		document.write('	<tr>');
		document.write('		<td colspan="2">');
		document.write('				<div class="categoryListBox" id="divcat<bean:write name='ctrlTree' /><bean:write name='subIndex'/>">');
		document.write('					<p style="margin-bottom:5px;"><bright:cmsWrite identifier="subcategory-root" filter="false"/> <c:out value="${subIndex+1}" />:<\/p>');
		document.write('					<select class="category" id="cat<bean:write name='ctrlTree' /><bean:write name='subIndex'/>" onchange="changedCategory(this, <bean:write name='subIndex'/>, \'<bean:write name='ctrlTree' />\');doEmptyCategoryFilter(this, <bean:write name='subIndex'/>, \'<bean:write name='ctrlTree' />\');">     ');
		document.write('						<option value="0">- <bright:cmsWrite identifier="snippet-please-select" filter="false"/> -<\/option>');						   
		document.write('					<\/select>');						   
		document.write('					<input type="button" class="button" value="<bright:cmsWrite identifier='button-add' filter='false' />" onclick="addSelectedCategory(\'cat<bean:write name='ctrlTree' /><bean:write name='subIndex'/>\', \'<bean:write name='ctrlTree' />\'); return false;">  ');
		document.write('				<\/div>');
		document.write('		<\/td>');
		document.write('	<\/tr>');
									</logic:iterate>
			
		document.write('	<tr>');
		document.write('		<td style="padding-top: 8px;" colspan="2">');
		document.write('			<label for=\"cat<bean:write name='ctrlTree' />-1\">');
		document.write('				<bean:write name='ctrlIncludedSubtitle' />');
		document.write('			<\/label>');
		document.write('		<\/td>');
		document.write('	<\/tr>');
		document.write('	<tr>');
		document.write('		<td class="chosen">');
		document.write('			<select id="chosenCategoryList<bean:write name='ctrlTree' />" size="4" class="category"><\/select>');
		document.write('		<\/td>');
		document.write('		<td>');
		document.write('			<input type="button" class="button" value="<bright:cmsWrite identifier='button-remove' filter='false' />" onclick="removeSelectedCategory(\'<bean:write name='ctrlTree' />\'); return false;">			');
		document.write('		<\/td>');
		document.write('	<\/tr>');
				
		<c:if test="${section=='search' && bShowOrMessage}">
		document.write('			<tr>');
		document.write('				<td style="padding-bottom: 8px;" colspan="2"> ');
		document.write('					<bright:cmsWrite identifier="search-no-cats-selected" filter="false" replaceVariables="true" removeLinebreaks="true" />');
		document.write('				<\/td> ');
		document.write('			<\/tr> ');
		</c:if>
		
		document.write('	<\/table> ');
		
		/* ]]> */
		</script>

		<logic:present name="searchForm">
			<c:if test="${searchForm.refineSearch || searchForm.searchingCategory}">
				<script type="text/javascript">
					/* <![CDATA[ */
						<c:choose>
							<c:when test="${ctrlTree eq 'desc'}">
								<logic:iterate name="userprofile" property="searchCriteria.descriptiveCategoriesToRefine" id="refineCat">
									addCategory(<c:out value='${refineCat}'/>,'desc');
								</logic:iterate>
								setCatIdsField('desc');
							</c:when>
						
							<c:otherwise>
								<logic:iterate name="userprofile" property="searchCriteria.permissionCategoriesToRefine" id="refineCat">
									addCategory(<c:out value='${refineCat}'/>,'perm');
								</logic:iterate>
								setCatIdsField('perm');
							</c:otherwise>
						</c:choose>
						
					/* ]]> */
				</script>
			</c:if>					
		</logic:present>
			
		<logic:present name="searchForm">
			<c:if test="${searchForm.selectedFilter != null && searchForm.selectedFilter.categoryIds != null && searchForm.selectedFilter.categoryIds != ''}">
				<script type="text/javascript">
					/* <![CDATA[ */
						<logic:iterate name="searchForm" property="selectedFilter.selectedCategoryIds" id="refineCat">
							addCategory(<c:out value='${refineCat}'/>,'desc');
						</logic:iterate>
						setCatIdsField('desc');
					/* ]]> */
				</script>
			</c:if>		
		</logic:present>
		
		<noscript>
		</c:if>
		
		<c:if test="${ctrlHeading != null || ctrlTitle != null}">
			<h4 style="margin: 1em 0 0.5em 0">
			<logic:notEmpty name="ctrlHeading">
				<bean:write name='ctrlHeading' filter='false'/>
			</logic:notEmpty>
						
			<logic:notEmpty name="ctrlTitle">		
				<bean:write name='ctrlTitle' filter='false'/>
			</logic:notEmpty>

			</h4>
		</c:if>	

		<%-- The following list could be long - that's why I've removed all white space (to try to reduce the size of the page so it loads more quickly)--%>
		<logic:notPresent name='uploading'>
			<bean:define id="uploading" value="false"/>
		</logic:notPresent>

		<div class="checkboxPanel">
		<c:set var="checkboxIndex" value="0"/>
		
		<%-- Get the number of top level categories (used to sort into columns) --%>
		<c:set var="numTopLevelCats" value="0"/>
		<logic:iterate name="flatCategoryList" id="item">
			<c:if test="${item.parentId < 0}">
				<c:set var="numTopLevelCats" value="${numTopLevelCats+1}"/>
			</c:if>
		</logic:iterate>
		<%-- Subtract 1 to discount the very top level cat --%>
		<c:set var="numTopLevelCats" value="${numTopLevelCats-1}"/>
		
		<logic:iterate name="flatCategoryList" id="item" indexId="i">
			<c:if test="${item.depth != 0 && (numCats lt maxNumCatsForNonJS || item.depth < 2)}">
				<c:if test="${item.depth == 1}">
					<c:set var="checkboxIndex" value="${checkboxIndex + 1}"/>
				</c:if>
				<c:set var="refiningCategory" value="false"/>
				<div style="margin-left: <c:out value="${12 * (item.depth-1)}"/>px;">
											
					<bean:define id="testCatId" name="item" property="id"/>
								
					<logic:present name="searchForm">
						<c:if test="${searchForm.refineSearch || searchForm.searchingCategory || searchForm.hasErrors}">
							<bean:define id="refiningCategory" name="userprofile" property="<%= \"searchCriteria.catgeoryToBeRefined(\" + testCatId + \")\" %>"/>
						</c:if>
					</logic:present>

					<c:if test="${!refiningCategory}">
						<logic:present name="searchForm">
							<c:if test="${searchForm.selectedFilter != null && searchForm.selectedFilter.accessLevelIds != null && searchForm.selectedFilter.accessLevelIds != ''}">
								<bean:define id="refiningCategory" name="searchForm" property="<%= \"selectedFilter.hasSelectedAccessLevel(\" + testCatId + \")\" %>"/>
							</c:if>
						</logic:present>
					</c:if>
					<c:choose>
						<c:when test="${uploading}">
							<c:choose>
								<c:when test="${item.assignable}">
									<c:choose>
										<c:when test="${assetForm.template.id > 0}">
											<html:multibox name="assetForm" property="${ctrlPropertyName}" styleId="id${item.id}" styleClass="checkbox">
												<bean:write name="item" property="id" />
											</html:multibox>
										</c:when>			
										<c:otherwise>
											<c:set var="isSelected" value="false"/>
											<logic:present name="catForm">
												<bean:define id="catId" name="item" property="id"/>
												<bean:define id="isSelected" name="catForm" property="<%= \"hasSelectedCategory(\" + catId + \")\" %>"/>
											</logic:present>
											<input type="checkbox" name="<bean:write name='ctrlPropertyName'/>" value="<bean:write name='item' property='id'/>" id="id<bean:write name='item' property='id'/>" class="checkbox" <c:if test="${item.selectedOnLoad || isSelected || (not assetForm.hasErrors && assetForm.asset.entity.defaultCategoryId==item.id)}">checked="checked"</c:if>/>
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<input type="checkbox" class="checkbox" disabled="disabled" styleId="id${item.id}"/>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>	
							<c:choose>
								<c:when test="${bIsSearch}">
									<input type="checkbox" name="<bean:write name='ctrlPropertyName'/>" value="<bean:write name='item' property='id'/>" id="id<bean:write name='item' property='id'/>" class="checkbox" <logic:present name="refiningCategory"><c:if test="${refiningCategory}">checked="checked"</c:if></logic:present>/>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${item.assignable || not empty searchForm || not empty searchBuilderForm}">
											<html:multibox name="assetForm" property="${ctrlPropertyName}" styleId="id${item.id}" styleClass="checkbox">
												<bean:write name="item" property="id" />
											</html:multibox>
										</c:when>
										<c:otherwise>
											<input type="checkbox" class="checkbox" disabled="disabled" styleId="id${item.id}"/>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
							
						</c:otherwise>
					</c:choose>
					<label <c:if test="${not (item.assignable || not empty searchForm || not empty searchBuilderForm)}">class="disabled"</c:if> for="id<bean:write name='item' property='id'/>"><bean:write name="item" property="name" filter="false"/></label><br/>

				</div>
				
				<fmt:parseNumber var="checkboxColLength" value="${(numTopLevelCats/3)+0.4}" integerOnly="true" />
				<c:if test="${((checkboxIndex) == checkboxColLength) || ((checkboxIndex) == (checkboxColLength*2))}">
					<c:if test="${numTopLevelCats > 8}">
						</div>
						<div class="checkboxPanel" >
					</c:if>
				</c:if>
			</c:if>
		</logic:iterate>
		</div>	<!-- End of .checkboxPanel -->					
		<div class="clearing"></div>
		<c:if test="${section=='search' && bShowOrMessage}">
			<p style="padding: 8px 0;"><bright:cmsWrite identifier="snippet-no-cats-selected" filter="false"/></p>
		</c:if>

		<c:if test="${ctrlIsCheckboxControl == 0}">	
			</noscript>
		</c:if>
			
	</c:otherwise>
</c:choose>
