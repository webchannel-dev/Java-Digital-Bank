<html:hidden name="savedSearchForm" property="savedSearch.keywords"/>
<html:hidden name="savedSearchForm" property="savedSearch.sortAttributeId"/>
<html:hidden name="savedSearchForm" property="savedSearch.descending"/>
<html:hidden name="savedSearchForm" property="savedSearch.criteriaFile"/>
<html:hidden name="savedSearchForm" property="recent"/>

<c:if test="${!userprofile.isAdmin}">
	<input type="hidden" name="savedSearch.availableToAll" value="false"/>
</c:if>

	<label for="searchName"><bright:cmsWrite identifier="label-name" filter="false" /></label>
	<html:text name="savedSearchForm" property="savedSearch.name" maxlength="200" styleId="searchName" />
	<br />

	<label for="showHome"><bright:cmsWrite identifier="label-show-on-homepage" filter="false" /></label>
	<html:checkbox name="savedSearchForm" property="savedSearch.promoted" styleClass="checkbox" styleId="showHome" />
	<br />
	<c:if test="${savedSearchForm.savedSearch.criteriaFile != null && savedSearchForm.savedSearch.criteriaFile != ''}">
	
		<label for="alertMe"><bright:cmsWrite identifier="label-saved-search-alert" filter="false" /></label>
		<html:checkbox name="savedSearchForm" property="savedSearch.alert" styleClass="checkbox" styleId="alertMe" />
		<br />
	</c:if>
	<c:if test="${userprofile.isAdmin}">

		<label for="showForAll"><bright:cmsWrite identifier="label-available-to-all" filter="false" /></label>
		<html:checkbox name="savedSearchForm" property="savedSearch.availableToAll" styleClass="checkbox" styleId="showForAll" />
		<br />
		
		<logic:notEmpty name="savedSearchForm" property="savedSearch.image">

			<bean:define id="uploadImageLabel" value="New image"/>
			<html:hidden name="savedSearchForm" property="savedSearch.image"/>

			<label><bright:cmsWrite identifier="label-current-image" filter="false" /></label>
			<div class="holder"
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

<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-save" />"> 
