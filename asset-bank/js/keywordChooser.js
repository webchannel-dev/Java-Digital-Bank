/**
 * Bright Interactive, keywordChooser.js
 *
 * Copyright 2003 Bright Interactive, All Rights Reserved.
 * 
 * Contains JavaScript functions used by the keyword chooser.
 *
 */
/*
Ver  Date	        Who					Comments
--------------------------------------------------------------------------------
d1	 24-Apr-2006	Steve Bryan       Created.
d2	 10-Jul-2006	Steve Bryan		  Add option to use from search (bBrowse = true)
d3	 07-Mar-2007	Matt Stevenson	  Modified to allow for different tree ids
d4	 01-Nov-2007	Matt Stevenson	  Added default filter to openKeywordChooser
d5	 22-Dec-2008	Steve Bryan			Added method for opening dictionary
d6	 13-Jan-2009	Francis Devereux	Don't add " and space when searching (browse=true) because this breaks searching for multiple keywords now that Lucene tokenisation has been changed
--------------------------------------------------------------------------------
*/



/*
	Open the chooser window
*/
function openKeywordChooser(bExpired, lCategoryTypeId, lAttributeId, iIndex, sDefaultFilter)
{
	if (typeof chooserwindow != 'undefined')
	{
		//chooserwindow.close();
	}

	sUrl = '../action/keywordChooser?categoryTypeId=' + lCategoryTypeId + '&attributeId=' + lAttributeId;
	
	if(iIndex>0)
	{
		sUrl += '&index='+iIndex;
	}

	if (bExpired)
	{
		sUrl += '&expired=true';
	}

	if (sDefaultFilter != "" && typeof sDefaultFilter != 'undefined')
	{
		sUrl = sUrl + "&filter=" + sDefaultFilter;
	}
	
	chooserwindow=open(sUrl,'keywordwindow','resizable=yes,width=560,height=450,left=100,top=30,scrollbars=yes');
	chooserwindow.location.href = sUrl;
	chooserwindow.opener = self;
}

/*
	Open the Master list
*/
function openMasterList(lCategoryTypeId)
{
	if (typeof listwindow != 'undefined')
	{
		//listwindow.close();
	}

	sUrl = '../action/viewAddKeywordPopup?categoryTypeId='+lCategoryTypeId;

	listwindow=open(sUrl,'listwindow','resizable=yes,width=560,height=480,left=100,top=30,scrollbars=yes');
	listwindow.location.href = sUrl;
	if (listwindow == null) 
	{
		listwindow.opener = self;
	}

}


/*
	Enable/disable buttons as appropriate.
*/
function showAddControls(a_sKeywordsField, a_sDelim)
{
	// Read the current keywords string
	var sKeywords = opener.document.getElementById(a_sKeywordsField).value;

	// Tokenize 
	var aKeywords = tokenize(sKeywords, a_sDelim);	

	// Disable appropriate buttons
	var aButtons = document.getElementsByName("addbutton");	

	if (typeof aButtons == 'object')
	{
		for (i=0; i < aButtons.length; i++)
		{
			var button = aButtons[i];
			var b = isInList(aButtons[i].id, aKeywords);
			
			if (b)
			{
				button.disabled = "true"
				
				// Firefox
				button.className = "inactive";
			}
		}	
	}

}



/*
	Tokenize the given string into an array of words.
	This is a copy of the logic in TaxonomyManager.tokenize.
*/
function tokenize(a_sWords, a_sDelim)
{
	var aWords = a_sWords.split(a_sDelim);
	
	// Remove quotes and trim leading and trailing spaces
	if (typeof aWords == 'object')
	{
		for (i=0; i < aWords.length; i++)
		{
			aWords[i] = aWords[i].replace(/\"/g, '');
			aWords[i] = aWords[i].replace(/^\s+/g, '');
			aWords[i] = aWords[i].replace(/\s+$/g, '');
		}
	}
	return aWords;
}


/*
	Return true if the given word is in the list.
*/
function isInList(a_sWord, a_aWordList)
{
	var b = false;
	
	if (typeof a_aWordList == 'object')
	{
		for (var i=0; i < a_aWordList.length; i++)
		{
			if ( a_sWord.toLowerCase() == a_aWordList[i].toLowerCase() )
			{
				b = true;
			}
		}
	}
	return b;
}


function addKeyword(a_lAttributeId, a_sWord, a_sDelim, iIndex)
{
	if (a_lAttributeId > 0)
	{
		var a_sKeywordsField = "field" + a_lAttributeId;
		
		if(iIndex>0)
		{
			a_sKeywordsField += "_" + iIndex;
		}
	}
	else
	{
		var a_sKeywordsField = "keywords_field";
	}
	
	// Append the keyword directly to the field on the opener
	var sKeywords = opener.document.getElementById(a_sKeywordsField).value;

	// Tokenize 
	var aKeywords = tokenize(sKeywords, a_sDelim);	

	// Only add it if the word is not already there
	if ( !isInList(a_sWord, aKeywords) )
	{	
		if (sKeywords.length > 0 && sKeywords.substr(-1) !=a_sDelim)
		{
			sKeywords += a_sDelim;
		}
		sKeywords += a_sWord + a_sDelim;
		
		opener.document.getElementById(a_sKeywordsField).value = sKeywords;
		
		// Now refresh the button statuses
		showAddControls(a_sKeywordsField, a_sDelim);
	}
}


/*
	Open an external dictionary window
*/
function openExternalDictionary(lAttributeId, sUrl, sDescriptor)
{
	sPopupUrl = sUrl + '?id=field' + lAttributeId + '&displayId=field_additional' + lAttributeId;
	
	dictwindow=open(sPopupUrl,'dictwindow', sDescriptor);
	dictwindow.location.href = sPopupUrl;
	dictwindow.opener = self;
}


