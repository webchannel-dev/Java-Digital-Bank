<html:hidden name="savedSearchForm" property="savedSearch.keywords"/>
<html:hidden name="savedSearchForm" property="savedSearch.sortAttributeId"/>
<html:hidden name="savedSearchForm" property="savedSearch.descending"/>
<html:hidden name="savedSearchForm" property="savedSearch.criteriaFile"/>
<html:hidden name="savedSearchForm" property="recent"/>
<html:hidden name="savedSearchForm" property="savedSearch.id"/>

	<c:if test="${!userprofile.isAdmin}">
		<input type="hidden" name="savedSearch.availableToAll" value="false"/>
		<input type="hidden" name="savedSearch.featured" value="false"/>
	</c:if>

	<label for="searchName"><bright:cmsWrite identifier="label-name" filter="false" /></label>
	<html:text name="savedSearchForm" property="savedSearch.name" maxlength="200" styleId="searchName" />
	<br />

	<c:if test="${savedSearchForm.savedSearch.criteriaFile != null && savedSearchForm.savedSearch.criteriaFile != ''}">
	
		<label for="alertMe"><bright:cmsWrite identifier="label-saved-search-alert" filter="false" /></label>
		<html:checkbox name="savedSearchForm" property="savedSearch.alert" styleClass="checkbox" styleId="alertMe" />
		<br />
	</c:if>
	
	<c:if test="${userprofile.isAdmin}">
		<label>Type:</label>
		<c:if test="${savedSearchForm.savedSearch.id<1}">
			<ul class="radioList narrow" style="float:left">
				<li><html:radio name="savedSearchForm" property="savedSearch.type" value="1" onclick="checkPersonalTypeFields();" styleClass="radio" styleId="radPersonal"  /><label for="radPersonal">Personal</label></li>
				<li><html:radio name="savedSearchForm" property="savedSearch.type" value="2" onclick="checkPersonalTypeFields();" styleClass="radio" styleId="radRecommended" /><label for="radRecommended">Recommended</label></li>
				<li><html:radio name="savedSearchForm" property="savedSearch.type" value="3" onclick="checkPersonalTypeFields();" styleClass="radio"  styleId="radFeatured" /><label for="radFeatured">Featured</label></li>
			</ul>
		</c:if>
		<c:if test="${savedSearchForm.savedSearch.id>=1}">
			<c:choose>
				<c:when test="${savedSearchForm.savedSearch.type == 1}">Personal</c:when>
				<c:when test="${savedSearchForm.savedSearch.type == 2}">Recommended</c:when>
				<c:when test="${savedSearchForm.savedSearch.type == 3}">Featured</c:when>
			</c:choose>
			<html:hidden name="savedSearchForm" property="savedSearch.type"/>
		</c:if>
		
		<br />
		
		<div id="descriptionFields">
			<label>Description:</label><html:textarea name="savedSearchForm" property="savedSearch.description"/><br />
		</div>
		
	</c:if>
	
	<div id="personalTypeFields">
		<label for="showHome"><bright:cmsWrite identifier="label-show-on-homepage" filter="false" /></label>
		<html:checkbox name="savedSearchForm" property="savedSearch.promoted" styleClass="checkbox" styleId="showHome" />
		<br />
	</div>
		
	<c:if test="${userprofile.isAdmin}">
		
		<logic:notEmpty name="savedSearchForm" property="savedSearch.image">

			<bean:define id="uploadImageLabel" value="New image"/>
			<html:hidden name="savedSearchForm" property="savedSearch.image"/>

			<label><bright:cmsWrite identifier="label-current-image" filter="false" /></label>
			<div class="holder">
				<img src="../<bean:write name='savedSearchForm' property='savedSearch.image'/>" alt="Image thumbnail for search" />
			</div>
			<br />

			<label for="removeExisting"><bright:cmsWrite identifier="label-remove-current-image" filter="false" /></label>
			<html:checkbox styleId="removeExisting" styleClass="checkbox" name="savedSearchForm" property="removeImage" />
			<br />
		</logic:notEmpty>

		<label><bright:cmsWrite identifier="label-image" filter="false" /></label>
		<html:file name="savedSearchForm" property="image"/>
		<br />
	</c:if>
	
	<c:if test="${canCreateSearchRss}">
		<label for="rssFeed">RSS feed?</label>
		<html:checkbox name="savedSearchForm" property="savedSearch.rssFeed" styleClass="checkbox" styleId="rssFeed" />
		<span class="comment">(Note, the name you provide above will be the title of the RSS feed and will also be used to identify it)</span>	
		<br />
	</c:if>


<div class="hr"></div>

<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-save" filter="false" />"> 

<script type="text/javascript">
var isAdmin = ${userprofile.isAdmin};

function checkPersonalTypeFields() {
	
	var isAdmin = ${userprofile.isAdmin};
	
	var personalTypeFields = document.getElementById( "personalTypeFields" );
	var descriptionFields = document.getElementById( "descriptionFields" );
	var savedSearchType = $j("input[name='savedSearch.type']:checked").val();
	
	if( savedSearchType == 1 || ! isAdmin ) 
	{
		personalTypeFields.style.display = '';
	}
	else 
	{
		personalTypeFields.style.display = 'none';
		$j("input[name='savedSearch.promoted']").attr('checked', false);
	}
	
	if( isAdmin )
	{
		if( savedSearchType == 3 ) 
		{
			descriptionFields.style.display = '';
		}
		else 
		{
			descriptionFields.style.display = 'none';
		}
	}
}

// call once on load to hide or show fields, initially
checkPersonalTypeFields();

</script>
