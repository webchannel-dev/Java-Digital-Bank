/** 
 * Bright Interactive, category.js
 *
 * Copyright 2003 Bright Interactive, All Rights Reserved.
 * 
 * Contains JavaScript functions used by the pages displaying categories.
 *
 */
/*
Ver  Date	        Who					Comments
--------------------------------------------------------------------------------
d1  08-Oct-2003     Martin Wilson       Created.
d2  19-Dec-2005     Steve Bryan         Added tree parameter to functions - 'desc' or 'perm'
d3  01-Jun-2006     Martin Wilson       Changed to make JS more efficient.
--------------------------------------------------------------------------------
*/


/**
* Called when a category dropdown is changed. Populates the sub-category list (if
* relevant) and hides/shows the other dropdowns.
* 
* @param a_listBox - the dropdown that has changed
* &param - the number of the list box. The first list, showing root cats, has an number of -1.
*		     the sub cats are number from 0.
* @param tree - the name of the tree the control is for
*/
function changedCategory(a_listBox, iChangedListNumber, tree) 
/*
------------------------------------------------------------------------
 d1   08-Oct-2003   Martin Wilson		Created.
------------------------------------------------------------------------
*/
{
	iNewListNumber = iChangedListNumber + 1;
	lParentId = a_listBox.options[a_listBox.selectedIndex].value;
	var subCats;
	
	// If something was selected then get the information about it:
	if (lParentId > 0)
	{
		subCats = getSubCategories(lParentId);
	}
	
	// If something was selected and there are sub items then add to the list:
	if (lParentId > 0 && subCats.length > 0)
	{
		// Get the list to populate:
		subListControl = document.getElementById("cat" + tree + iNewListNumber);
		
		// Clear the list:
		subListControl.options.length = 0;
	
		// Add the first item:
		subListControl.options[0] = new Option('- Please Select -',0);
	
		// Add the new items:
		for (i = 0; i < subCats.length; i++)
		{
			subListControl.options[subListControl.options.length] = 
				new Option(subCats[i].name,
							subCats[i].id);

		}
	}
	else
	{
		// Just show as far as the changed list:
		iNewListNumber = iChangedListNumber;

	}
	
	// Hide all list boxes that aren't relevant:
	// TODO make this independent of tree names
	var iMaxSubListNumber;
	if (tree == "desc")
	{
		iMaxSubListNumber = iDescMaxSubListNumber;
	}
	else
	{
		iMaxSubListNumber = iPermMaxSubListNumber;
	}
	
	for (i= 0; i <= iMaxSubListNumber; i++)
	{
		// Get the control:
		var elementId = "divcat" + tree + i;
		divToHide = document.getElementById(elementId);

		// Hide or show it:
		if (i <= iNewListNumber)
		{
			// Show it:
			divToHide.style.display = "block";
		}
		else
		{
			// Hide it:
			divToHide.style.display = "none";
		}
	}

}


/**
* Adds the selected category to the list of chosen categories.
* 
* @param a_sDropdownName - the dropdown that has changed
* @param tree - the name of the tree the control is for
*/
function addSelectedCategory(a_sDropdownName, tree) 
/*
------------------------------------------------------------------------
 d1   08-Oct-2003   Martin Wilson		Created.
------------------------------------------------------------------------
*/
{
	// Get the dropdown that was selected:
	lbDropdown = document.getElementById(a_sDropdownName);

	// Get the category id selected:
	lCatId = lbDropdown.options[lbDropdown.selectedIndex].value;

	if (lCatId > 0)
	{
		if (categoryIsInList(lCatId, tree))
		{
			//category already selected
			alert("This category is already selected.");
		}
		else
		{
			addCategory(lCatId, tree);
			
			if (ancestorIsInList(lCatId, tree))
			{
				alert("You have already selected a parent of this category. Please check that you meant to include the parent and child category.");
			}
		}

		// Rebuild the comma separated list (which holds the chosen category ids):
		setCatIdsField(tree);
	}
}

/**
* Adds the category with a_lCategory id to the list of chosen categories.
* 
* @param a_lCategory - the id of the category.
*/
function addCategory(a_lCategory, tree) 
/*
------------------------------------------------------------------------
 d1   08-Oct-2003   Martin Wilson		Created.
------------------------------------------------------------------------
*/
{
	// Get the info about the selected category:
	var catInfo = getCategory(a_lCategory);

	// Add to the list:
	lbChosenCatList = document.getElementById("chosenCategoryList" + tree);

	if (lbChosenCatList)
	{
		lbChosenCatList.options[lbChosenCatList.options.length] = 
		new Option(getFullName(catInfo.id), catInfo.id);
	}
}

/**
* Adds the selected category to the list of chosen categories.
* 
* @param a_listBox - the dropdown that has changed
* &param - the number of the list box. The first list, showing root cats, has an number of -1.
*		     the sub cats are number from 0.
*/
function removeSelectedCategory(tree) 
/*
------------------------------------------------------------------------
 d1   08-Oct-2003   Martin Wilson		Created.
------------------------------------------------------------------------
*/
{
	// Get the list:
	lbChosenCatList = document.getElementById("chosenCategoryList" + tree);

	// Remove the selected item:
	if (lbChosenCatList.selectedIndex != -1)
	{
		lbChosenCatList.options[lbChosenCatList.selectedIndex] = null;
	}

	// Rebuild the comma separated list (which holds the chosen category ids):
	setCatIdsField(tree);

}


/**
* Sees whether a category is already in the list.
* 
* @param - id of the category to check.
*/
function categoryIsInList(a_lCategoryId, tree) 
/*
------------------------------------------------------------------------
 d2   17-May-2005   Chris Preager		Created.
------------------------------------------------------------------------
*/
{
	bInList = false;

	var lbChosenCatList = document.getElementById("chosenCategoryList" + tree);

	// See if this is is already in the list:		
	for (j=0; j< lbChosenCatList.options.length; j++)
	{
		current = lbChosenCatList.options[j];

		if (a_lCategoryId == current.value)
		{
			bInList = true;
			break;
		}
	}

	return (bInList);
}


/**
* Sees whether the parent of a category is already in the list. If so, gives a warning.
* 
* @param - the dropdown list containing the selected category.
*/
function ancestorIsInList(a_lCategoryId, tree) 
/*
------------------------------------------------------------------------
 d1   08-Oct-2003   Martin Wilson		Created.
------------------------------------------------------------------------
*/
{
	bInList = false;

	// Get the info object for the selected category:
	var catInfo = catInfoArray['id' + a_lCategoryId];

	var lbChosenCatList = document.getElementById("chosenCategoryList" + tree);

	// See if an ancestor is already in the list:		
	for (j=0; j< lbChosenCatList.options.length; j++)
	{
		current = lbChosenCatList.options[j];

		if (isAncestor(current.value, a_lCategoryId))
		{
			bInList = true;
			break;
		}
	}

	return (bInList);
}

/*
	Sets the field that returns the cat ids as a string.
*/
function setCatIdsField(tree)
/*
 d1   01-Nov-2002  Martin Wilson        Created.
*/   
{
	var lbChosenCatList = document.getElementById("chosenCategoryList" + tree);
	
	if (lbChosenCatList == null)
	{
		return;
	}
	var sCatIds = "";
	
	for (i=0; i< lbChosenCatList.options.length; i++)
	{
		current = lbChosenCatList.options[i];

		sCatIds = sCatIds + current.value;

		// Add a comma if not the last one:
		if (i != (lbChosenCatList.options.length - 1))
		{
			sCatIds = sCatIds + ",";
		}

	}
	
	// Set the hidden field:
	var hidChosenCatIds;
	if (tree == "desc")
	{
		hidChosenCatIds = document.getElementsByName("descriptiveCategoryForm.categoryIds")[0];
	}
	else
	{
		hidChosenCatIds = document.getElementsByName("permissionCategoryForm.categoryIds")[0];
	}
		
	hidChosenCatIds.value = sCatIds;
}

/**
* Returns the category with id a_lId
*/
function getCategory(a_lId)
/*
------------------------------------------------------------------------
 d3   -1-Jun-2006   Martin Wilson		Created.
------------------------------------------------------------------------
*/
{
	// Go through all the categories, until we find the one we want:
	for (i = 0; i < catInfoArray.length; i++)
	{
		if (catInfoArray[i].id == a_lId)
		{
			return (catInfoArray[i]);
		}
	}
}

/**
* Returns the sub-categories of a_lParentId
*/
function getSubCategories(a_lParentId)
/*
------------------------------------------------------------------------
 d3   -1-Jun-2006   Martin Wilson		Created.
------------------------------------------------------------------------
*/
{
	var subCatsArray = new Array();
	var iCount = 0;
	
	// Go through all the categories, adding all those that have the required cat as their parent:
	for (i = 0; i < catInfoArray.length; i++)
	{	
		if (catInfoArray[i])
		{
			if (catInfoArray[i].parentId == a_lParentId)
			{
				subCatsArray[iCount] = catInfoArray[i];
				iCount++;
			}
		}
	}

	return (subCatsArray);
}

/**
* Returns the full name of the category with id a_lId
*/
function getFullName(a_lId)
/*
------------------------------------------------------------------------
 d3   -1-Jun-2006   Martin Wilson		Created.
------------------------------------------------------------------------
*/
{
	var sName = "";
	
	// Get this category:
	var catInfo = getCategory(a_lId);

	// See if it has a parent:
	if (catInfo.parentId > 0)
	{
		// Add the parent category's full name:
		if ( typeof( window[ 'iRootId' ] ) != "undefined" ) 
		{
			if (iRootId <= 0 || iRootId != catInfo.parentId)
			{
			 	sName = getFullName(catInfo.parentId) + "/";
			}
		}
		else
		{
			sName = getFullName(catInfo.parentId) + "/";
		}
	}

	// Add this category's name to the end:
	sName += catInfo.name;

	return (sName);
}

/**
* Returns true if a_lAncestorId is an ancestor of a_lDescendantId
*/
function isAncestor(a_lAncestorId, a_lDescendantId)
/*
------------------------------------------------------------------------
 d3   -1-Jun-2006   Martin Wilson		Created.
------------------------------------------------------------------------
*/
{
	// Get the category we are currently checking:
	var catInfo = getCategory(a_lDescendantId);

	// See if it has a parent:
	if (catInfo.parentId > 0)
	{
		// See if the parent is the same as a_lAncestorId:
		if (catInfo.parentId == a_lAncestorId)
		{
			return (true);
		}
		
		// Otherwise, check the parent:
		return (isAncestor(a_lAncestorId, catInfo.parentId));

	}

	// If no parent then it is not a descendant:
	return (false);

}