

// Element names
var ksDownloadNoneStem = "catPermissionNone";
var ksDownloadPermissionStem = "catPermission";
var ksDownloadOriginalStem = "catDownloadOriginal";
var ksDownloadAdvancedStem = "catDownloadAdvanced";
var ksReviewAssets = "catReviewAssets";
var ksViewRestrictedAssets = "catViewRestrictedAssets";
var ksHighResApproval = "catHighResApproval";

// Download permissions
var iDownloadNone = 0;
var iDownloadView = 1;
var iDownloadDAP = 5;
var iDownloadDownload = 2;
var iDownloadApprove = 7;

var aCheckedArrays = new Array();

/*
Sets all the categories to the same permission.
*/
function setAll (a_sNamePrefix, 
				 a_sValuePrefix,
				 a_iPermissionLevel, 
				 a_lGroupId)
{

	// Go through all radio buttons:
	for (var i=0; i<document.forms['group_edit'].length; i++)
	{
		current = document.forms['group_edit'].elements[i];

		if (current.type == 'radio' 
				&& current.name.indexOf(a_sNamePrefix) >= 0
				&& current.value.indexOf(a_sValuePrefix + a_iPermissionLevel) >= 0)
		{
			current.checked = true;

		}
	}
	
	enableControls(a_lGroupId);
}


function setAllCheckbox (a_sNamePrefix, a_bChecked)
{

	// Go through all checkboxes :
	for (var i=0; i<document.forms['group_edit'].length; i++)
	{
		current = document.forms['group_edit'].elements[i];

		if (current.type == 'checkbox' 
				&& current.name.indexOf(a_sNamePrefix) >= 0)
		{
			if (!current.disabled)
			{
				current.checked = a_bChecked;
			}
		}
	}
}



/*
	Enable/disable checkboxes.
*/
function enableControls(groupId)
{
	
	// Check all form elements 
	var aElements = document.forms['group_edit'].elements;	
	
	checkArray(aElements, groupId);
}



/*
	Check through the given array checking controls and setting dependent controls.
*/
function checkArray(aElements, groupId)
{
	if (typeof aElements == 'object')
	{
		for (i=0; i < aElements.length; i++)
		{
			var elt = aElements[i];		

			if ( typeof elt == 'object' )
			{	
				// Do a check if it is a download permission 
				var sName = elt.name;

				if ( sName != null && sName.indexOf( ksDownloadPermissionStem ) == 0 && elt.checked )
				{				
					setDownloadPermission(elt, groupId);
				}
								
			}
		}	
	}
}


/*
	If a download permission set is changed then go through to do checks.
*/
function changeDownloadPermissionSet(elt, groupid)
{
	// Do a check if it is download permission element
	if ( typeof elt == 'object' && elt.type == 'radio' )
	{	
		var sName = elt.name;

		if ( sName != null && sName.indexOf( ksDownloadPermissionStem ) == 0 )
		{		
			// Look through the elements of this name to find the no permission one		
			var aElements = document.getElementsByName(sName);
			
			checkArray(aElements, groupid);				
		}
	}
}


/*
	Set dependents of element.
*/
function setDownloadPermission(elt, groupId)
{
	// Confirm the element is a download permission checkbox
	if ( typeof elt == 'object' && elt.type == 'radio' && elt.name != null && elt.name.indexOf( ksDownloadPermissionStem ) == 0)
	{	
		var sValue = elt.value;
		
		if (sValue != null)
		{
			var iIndex1 = sValue.indexOf('!');
			var iIndex2 = sValue.indexOf(':');
			
			var sCatId = 0;
			var sLevel = 0;
			
			if (iIndex1 > 0 && iIndex2 > 0)
			{
				sCatId = sValue.substr(0, iIndex1);
				sLevel = sValue.substr(iIndex2+1);
			}		
			
			// alert('cat=' + sCatId + ' level=' + sLevel);
		
			// Get the advanced perm elements
			var eltDownloadOriginal = document.getElementById(ksDownloadOriginalStem + sCatId);
			var eltDownloadAdvanced = document.getElementById(ksDownloadAdvancedStem + sCatId);
			var eltReviewAssets = document.getElementById(ksReviewAssets + sCatId);
			var eltViewRestrictedAssets = document.getElementById(ksViewRestrictedAssets + sCatId);
			var eltHighResApproval = document.getElementById(ksHighResApproval + sCatId);
			
			// Arrays of elements to enable/disable
			var toEnable=new Array(5);
			var toDisable=new Array(5);
						
			var iLevel = parseInt(sLevel);
			switch (iLevel)
			{
				case 0: // iDownloadNone:
					toDisable = [eltDownloadOriginal, eltDownloadAdvanced, eltReviewAssets, eltViewRestrictedAssets, eltHighResApproval];
					break;
					
				case iDownloadView:
					toDisable = [eltDownloadOriginal, eltDownloadAdvanced, eltHighResApproval];
					toEnable = [eltViewRestrictedAssets, eltReviewAssets];
					break;
				
				case iDownloadDAP:
				case iDownloadApprove:
					toDisable = [eltHighResApproval];
					toEnable = [eltDownloadOriginal, eltDownloadAdvanced, eltViewRestrictedAssets, eltReviewAssets];
					break;

				case iDownloadDownload:
					toEnable = [eltDownloadOriginal, eltDownloadAdvanced, eltViewRestrictedAssets, eltReviewAssets, eltHighResApproval];
					break;
			}
			
			if (groupId == 2)
			{
				toDisable['extra'] = eltReviewAssets;
			}
			
			for (index in toEnable)
			{
				var elt = toEnable[index]; 
				if (elt && typeof elt == 'object')
				{
					elt.disabled = false;
				}
			}
			
			for (index in toDisable)
			{
				var elt = toDisable[index];
				
				if (elt && typeof elt == 'object')
				{
					elt.checked = false;
					elt.disabled = true;
				}
			}				
			
		}
	}
}

function checkUploadPermission(elt,catId) {
	$elt = $j(elt);
	if ( $elt.is(':checked') ) {
		if ( $elt.attr('id').indexOf('catUploadWA') >= 0) {
			// uncheck the alternate upload permission checkbox
			$j('#catUploader'+catId).attr('checked', false);

		} else {
			$j('#catUploadWA'+catId).attr('checked', false);
		}
	}
	
}
function checkEditPermission(elt,catId) {
	$elt = $j(elt);
	if ( $elt.is(':checked') ) {
		if ( $elt.attr('id').indexOf('catUpdateWA') >= 0) {
			// uncheck the alternate upload permission checkbox
			$j('#catUpdater'+catId).attr('checked', false);

		} else {
			$j('#catUpdateWA'+catId).attr('checked', false);
		}
	}
	
}
function setUltimateUploadPermissions(elt,catId) {
	$elt = $j(elt);
	if ( $elt.is(':checked') ) {
		$j('#catUploadWA'+catId).attr('disabled', true);
		$j('#catUploader'+catId).attr('disabled', true);
		$j('#catUpdater'+catId).attr('disabled', true);
		$j('#catUpdateWA'+catId).attr('disabled', true);
		$j('#catDelete'+catId).attr('disabled', true);
	} else {
		$j('#catUploadWA'+catId).attr('disabled', false);
		$j('#catUploader'+catId).attr('disabled', false);
		$j('#catUpdater'+catId).attr('disabled', false);
		$j('#catUpdateWA'+catId).attr('disabled', false);
		$j('#catDelete'+catId).attr('disabled', false);
	}	
}
function disableAll(bDisableorEnable) {
	$j('input.canDisable').attr('disabled', bDisableorEnable);
}

