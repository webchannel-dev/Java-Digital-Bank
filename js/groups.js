

// Element names
var ksDownloadNoneStem = "catPermissionNone";
var ksDownloadPermissionStem = "catPermission";
var ksDownloadOriginalStem = "catDownloadOriginal";
var ksDownloadAdvancedStem = "catDownloadAdvanced";
var ksReviewAssets = "catReviewAssets";
var ksViewRestrictedAssets = "catViewRestrictedAssets";
var ksHighResApproval = "catHighResApproval";


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

			// Do a check if it is permission none
			if ( typeof elt == 'object' )
			{	
				var sIdName = elt.id;

				// Permission none control
				if ( sIdName != null && sIdName.indexOf( ksDownloadNoneStem ) == 0 )
				{				
					checkPermissionNone(elt, groupId);
				}
			}
		}	
	}
}


/*
	If a download permission set is changed then go through to do checks.
*/
function changeDownloadPermissionSet(elt)
{
	// Do a check if it is download permission element
	if ( typeof elt == 'object' && elt.type == 'radio' )
	{	
		var sName = elt.name;

		if ( sName != null && sName.indexOf( ksDownloadPermissionStem ) == 0 )
		{		
			// Look through the elements of this name to find the no permission one		
			var aElements = document.getElementsByName(sName);
			
			checkArray(aElements);				
		}
	}
}


/*
	Check the given element and set dependents.
*/
function checkPermissionNone(elt, groupId)
{
	// Check if the given element is download permission none.
	// If so and it is checked then disable the advanced options
	if ( typeof elt == 'object' && elt.type == 'radio' )
	{	
		var sIdName = elt.id;
	
		if ( sIdName != null && sIdName.indexOf( ksDownloadNoneStem ) == 0 )
		{				
			sCatId = sIdName.substr( ksDownloadNoneStem.length );
			var eltDownloadOriginal = document.getElementById(ksDownloadOriginalStem + sCatId);
			var eltDownloadAdvanced = document.getElementById(ksDownloadAdvancedStem + sCatId);
			var eltReviewAssets = document.getElementById(ksReviewAssets + sCatId);
			var eltViewRestrictedAssets = document.getElementById(ksViewRestrictedAssets + sCatId);
			var eltHighResApproval = document.getElementById(ksHighResApproval + sCatId);
			
			// first do ones that are group specific...
			if (elt.checked || groupId == 2)
			{
				if(eltReviewAssets)
				{
					eltReviewAssets.checked = false;
					eltReviewAssets.disabled = true;
				}
			}
			else
			{
				if(eltReviewAssets)
				{
					eltReviewAssets.disabled = false;
				}
			}


			// If selected, then disable the advanced permission controls
			if (elt.checked)
			{
				eltDownloadAdvanced.checked = false;
				eltDownloadAdvanced.disabled = true;
				eltDownloadOriginal.checked = false;
				eltDownloadOriginal.disabled = true;
				eltHighResApproval.checked = false;
				eltHighResApproval.disabled = true;
				
				if (eltViewRestrictedAssets)
				{
					eltViewRestrictedAssets.checked = false;
					eltViewRestrictedAssets.disabled = true;
				}
			}
			// If not selected then enable the advanced controls
			else
			{
				eltDownloadOriginal.disabled = false;
				eltDownloadAdvanced.disabled = false;
				eltHighResApproval.disabled = false;
				
				if (eltViewRestrictedAssets)
				{
					eltViewRestrictedAssets.disabled = false;
				}
			}						
		}
	}
}

