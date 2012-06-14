<%-- 
	Set up javascript and jsp variables for category editing.
	Set max sublist numbers.
	Create functions for body onload.
	
	Pass in:
	assetForm - Reference to a AssetForm.
	ctrlIsCheckboxControl - if this is going to be forced to be a checkbox control
	
	History:
		d1	19-Dec-2005		Steve Bryan		Created.
		d2  01-Jun-2006     Martin Wilson   Changed to make JS more efficient.
--%>

<%-- Retrieve required variables from the form --%>
<c:set var="descMaxNumOfSubCats" value="${assetForm.descriptiveCategoryForm.maxNumOfSubCats}" />
<c:set var="permMaxNumOfSubCats" value="${assetForm.permissionCategoryForm.maxNumOfSubCats}" />
<c:set var="descSelectedCategoryArray" value="${assetForm.categoryIdArray}" />
<c:set var="permSelectedCategoryArray" value="${assetForm.permissionCategoryIdArray}" />

<bean:size id="numDescCategories" name="assetForm" property="descriptiveCategoryForm.flatCategoryList"/>
<bean:size id="numPermCategories" name="assetForm" property="permissionCategoryForm.flatCategoryList"/>
<c:set var="totalCatNum" value="${numDescCategories + numPermCategories}" />

<script type="text/javascript">
<!--

	var catInfoArray = new Array(<c:out value="${totalCatNum}"/>);
	var iDescMaxSubListNumber;
	var iPermMaxSubListNumber;
	var k_iCategoryIndex = 0;
	var m_bPermCatsAreCheckboxes = false;
	
	// TODO - put this into an array or dictionary to make category.js more generic
	iDescMaxSubListNumber = <bean:write name="descMaxNumOfSubCats"/> -2;
	iPermMaxSubListNumber = <bean:write name="permMaxNumOfSubCats"/> -2;

	// Functions called by body onload:
	
	function setDescSelectedCategories()
	// Set the selected categories for the descriptive categories
	{
		<logic:iterate name='descSelectedCategoryArray' id='catid'>
			addCategory(<bean:write name='catid'/>, 'desc'); 
		</logic:iterate>	
	}

	function setPermSelectedCategories()
	// Set the selected categories for the permission categories
	{
		if (!m_bPermCatsAreCheckboxes)
		{
			<logic:iterate name='permSelectedCategoryArray' id='catid'>
				addCategory(<bean:write name='catid'/>, 'perm'); 
			</logic:iterate>
		}
		
	}

	function setCatIdsFields()
	// Populate the selected ids fields
	{
		setCatIdsField('desc'); 

		if (!m_bPermCatsAreCheckboxes)
		{
			setCatIdsField('perm');
		}
	}

	function setPermCatsAreCheckboxes(a_bAreCheckboxes)
	{
		m_bPermCatsAreCheckboxes = a_bAreCheckboxes;
	}	

//-->
</script>

<%-- The javascript info structures only written out if this is not a checkbox control --%>
<c:if test="${ctrlIsCheckboxControl == 0}">

		<%-- Create the javascript category information structures. This is done using an include, so it can be cached.
		The dummy parameter is used to refresh the cache if the categories have been changed.--%>
		<bright:refDataList componentName="CategoryManager" methodName="getCacheTimestamp" id="catCacheTimestamp"/>
		<script src="../action/generateCatJS.js?dummy=<bean:write name='catCacheTimestamp'/>&amp;lang=<bean:write name="userprofile" property="currentLanguage.id"/>" type="text/javascript"></script>
				
</c:if>


	
