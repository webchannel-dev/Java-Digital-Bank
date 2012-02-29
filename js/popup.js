/*
Version Date			Developer			Comment
----------------------------------------------------------------------
d1		16-Jun-2003		James Home			Created
d2		06-Mar-2006		Martin Wilson		Changed popupViewImage
d3		23-Oct-2006		Adam Bones			Created popupCropImage
d4		23-Jan-2007		Kevin Bennett		Added popupTerms
d5		23-Feb-2007		Steve Bryan			Added functions for popup help link event handling
d6		28-Feb-2007		Matt Stevenson		Added audio popup
d7		28-Feb-2007		Matt Stevenson		Added popupPreview
d8		02-Apr-2008		Matt Woollard		Created	popupAssetAudit
d9 		02-May-2008		Matt Woollard		Added versionNumber to popupAssetAudit
d10		10-Jul-2008		Matt Woollard		Added popupViewAgreement
d11		10-Jul-2008		Matt Woollard		Added popupAddAgreement
----------------------------------------------------------------------
*/


/*
	The following functions handle the event of clicking on a help link
	They depend on a listener defined in head-elements.jsp
*/
function event_help_popup(e) 
{
  popupHelpForLink(e.currentTarget);
  e.preventDefault();
}

function popupHelpForLink(elem)
{
	sURL = elem.getAttribute('href');
	mywindow=open(sURL,'viewHelp','resizable=yes,scrollbars=yes,toolbar=no,location=no,width=500,height=500');
	mywindow.location.href = sURL;
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}



function displayPopup(a_sURL)
/*
----------------------------------------------------------------------
	d1		16-Jun-2003		James Home			Created
----------------------------------------------------------------------
*/
{	
	mywindow=open(a_sURL ,'myname','resizable=no,scrollbars=yes,width=460,height=290');
	mywindow.location.href = a_sURL;
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}

function popupViewAgreement(a_lAgreementId)
/*
----------------------------------------------------------------------
	d10		10-Jul-2008		Matt Woollard		Created
----------------------------------------------------------------------
*/
{
	mywindow=open('../action/viewAgreement?id=' + a_lAgreementId,'viewAgreement','resizable=yes,scrollbars=yes,toolbar=no,width=500,height=500');

	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}

function popupAddAgreement()
/*
----------------------------------------------------------------------
	d11		11-Jul-2008		Matt Woollard		Created
----------------------------------------------------------------------
*/
{
	mywindow=open('../action/addAgreementPopup','viewAgreement','resizable=yes,scrollbars=no,toolbar=no,width=800,height=600');

	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}

function popupViewImage(a_lImageId)
/*
----------------------------------------------------------------------
	d1		23-May-2005		James Home			Created
----------------------------------------------------------------------
*/
{
	mywindow=open('../action/viewFullSizedImage?id=' + a_lImageId,'viewImage','resizable=yes,scrollbars=no,toolbar=no,width=500,height=500');
	
	// Is this required? If so for which browsers and can we only run it when necessary?
	// mywindow.location.href = '../action/viewFullSizedImage?id=' + a_lImageId;
	
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}

function popupViewImage(a_lImageId, imageWidth, imageHeight, hasMultipleLayers)
/*
----------------------------------------------------------------------
	d1		08-Jun-2005		James Home			Created
	d2		06-Mar-2006	Martin Wilson		Changed to specify size to viewFullSizedImage
----------------------------------------------------------------------
*/
{

	var screenWidth = imageWidth;
	var screenHeight = imageHeight;

	// Work out the size (largest of 2 dimensions):
	var iSize = imageWidth;

	if (imageHeight > imageWidth)
	{
		iSize = imageHeight;
	}
	// Limit the size of the popup window
	if(screenWidth>750)
	{
		screenWidth=750;
	}
	if(screenHeight>550)
	{
		screenHeight=550;
	}
	else if(hasMultipleLayers)
	{
		screenHeight+=60;
	}
	
	mywindow=open('../action/viewFullSizedImage?id=' + a_lImageId + "&size=" + iSize + '&layer=1','viewImage','resizable=yes,scrollbars=yes,toolbar=no,width=' + screenWidth + ',height=' + screenHeight);
	
	// Is this required? If so for which browsers and can we only run it when necessary?
	// mywindow.location.href = '../action/viewFullSizedImage?id=' + a_lImageId;
	
	mywindow.focus();

	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}

function popupSearchHelp()
/*
----------------------------------------------------------------------
	d1		07-Jun-2005		James Home			Created
----------------------------------------------------------------------
*/
{
	mywindow=open('../action/viewSearchHelp','viewSearchHelp','resizable=yes,scrollbars=yes,toolbar=no,width=500,height=500');

	mywindow.location.href = '../action/viewSearchHelp';
	
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}

function popupFormatHelp()
/*
----------------------------------------------------------------------
	d1		20-Jun-2006		Ben Browning		Created
----------------------------------------------------------------------
*/
{
	mywindow=open('../action/viewFormatHelp','HighResHelp','resizable=yes,scrollbars=yes,toolbar=no,width=500,height=500');

	mywindow.location.href = '../action/viewFormatHelp';
	
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}

function popupConditions(a_sAction)
{
	return (popupConditions(a_sAction,'false'));
}

function popupConditions(a_sAction,a_sExtra)
/*
----------------------------------------------------------------------
	d1		07-Jun-2005		James Home			Created
----------------------------------------------------------------------
*/
{
	mywindow=open('../action/' + a_sAction + '?extra=' + a_sExtra,'viewConditions','resizable=yes,scrollbars=yes,toolbar=no,width=500,height=500');
	
	mywindow.location.href = a_sAction + '?extra='+ a_sExtra;
	
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}

function popupAssetUse(a_lAssetId)
/*
----------------------------------------------------------------------
	d1		24-Jun-2005		James Home			Created
----------------------------------------------------------------------
*/
{
	mywindow=open('../action/viewAssetUsage?id=' + a_lAssetId,'viewAssetUsage','resizable=yes,scrollbars=yes,toolbar=no,width=590,height=500');

	mywindow.location.href = '../action/viewAssetUsage?id=' + a_lAssetId;
	
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}

function popupAssetAudit(a_lAssetId, a_lVersionNumber)
/*
----------------------------------------------------------------------
	d8		02-Apr-2008		Matt Woollard		Created	
	d9 		02-May-2008		Matt Woollard		Added versionNumber to popupAssetAudit
----------------------------------------------------------------------
*/
{
	mywindow=open('../action/viewAssetAudit?id=' + a_lAssetId + '&versionNumber=' + a_lVersionNumber,'viewAssetUsage','resizable=yes,scrollbars=yes,toolbar=no,width=590,height=500');

	mywindow.location.href = '../action/viewAssetAudit?id=' + a_lAssetId + '&versionNumber=' + a_lVersionNumber;
	
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}

function popupHelp(section_name)
/*
----------------------------------------------------------------------
	d1		07-Jun-2005		James Home			Created
	d2		23-Feb-2007		Steve Bryan			I think this is redundant now - see handler at top of file
----------------------------------------------------------------------
*/
{
	mywindow=open('../action/viewHelp?helpsection=' + section_name,'viewHelp','resizable=yes,scrollbars=yes,toolbar=no,location=no,width=500,height=500');

	mywindow.location.href = '../action/viewHelp?helpsection=' + section_name;
	
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}

function popupTerms(option_id)
/*
----------------------------------------------------------------------
	d1		07-Jun-2005		James Home			Created
----------------------------------------------------------------------
*/
{
	mywindow=open('../action/viewTerms?id=' + option_id,'viewTerms','resizable=yes,scrollbars=yes,toolbar=no,location=no,width=500,height=500');

	mywindow.location.href = '../action/viewTerms?id=' + option_id;
	
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}


function attributePopup(id)
/*
----------------------------------------------------------------------
	d1		28-Feb-2007		Matt Stevenson			Created
----------------------------------------------------------------------
*/
{
	mywindow=open('../action/viewAttributeValue?id=' + id,'AttributeValue','resizable=yes,scrollbars=yes,toolbar=no,location=no,width=500,height=250');

	mywindow.location.href = '../action/viewAttributeValue?id=' + id;
	
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}


function videoPopup(a_sURL)
/*
----------------------------------------------------------------------
	d1		11-Aug-2006		Ben Browning			Created
----------------------------------------------------------------------
*/
{	
	mywindow=open(a_sURL ,'myname','toolbar=no,location=no,resizable=yes,scrollbars=yes,width=558,height=400');
	mywindow.location.href = a_sURL;
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}



function audioPopup(a_sURL)
/*
----------------------------------------------------------------------
	d6		28-Feb-2007		Matt Stevenson		Created
----------------------------------------------------------------------
*/
{	
	mywindow=open(a_sURL ,'myname','toolbar=no,location=no,resizable=yes,scrollbars=yes,width=420,height=200');
	mywindow.location.href = a_sURL;
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}

function popupPreview(a_sURL,a_iWidth,a_iHeight)
/*
----------------------------------------------------------------------
	d7		05-Feb-2008		James Home		Created
----------------------------------------------------------------------
*/
{	
	if(a_iWidth<=0)
	{
		a_iWidth = 800;
	}
	if(a_iHeight<=0)
	{
		a_iHeight = 600;
	}
	
	mywindow=open(a_sURL ,'myname','toolbar=no,location=no,resizable=yes,scrollbars=yes,width=' + (a_iWidth+50) + ',height=' + (a_iHeight+80));
	mywindow.location.href = a_sURL;
	mywindow.focus();
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}



function expand_content(item)
/*
----------------------------------------------------------------------
	d1		03-Jan-2007		Ben Browning			Created
----------------------------------------------------------------------
*/
{
	if( document.getElementById && document.getElementsByTagName ){
  		if( document.getElementById(item) ){
   		var check = document.getElementById(item);
   			document.getElementById('expandLink').style.display = 'none';
				document.getElementById('collapseLink').style.display = 'block';
   			check.style.display = 'block';
   			return false;
   		}
 	}
}
	
function collapse_content(item)
/*
----------------------------------------------------------------------
	d1		03-Jan-2007		Ben Browning			Created
----------------------------------------------------------------------
*/
{
	if( document.getElementById && document.getElementsByTagName ){
  		if( document.getElementById(item) ){
    		var check = document.getElementById(item);
   			document.getElementById('expandLink').style.display = 'block';
   			check.style.display = 'none';
   			return false;
   		}
	}
}



/*
	The following functions use Ajax to dynamically displaying a medium size image on rollover of a thumbnail.
	They make use of the prototype.js library
*/

// global variable for timeout timer so can cancel it in hide function
var timer1

function showPreview(divId,imgSrc) 
/*
----------------------------------------------------------------------
	d1		22-Jan-2008		Ben Browning			Created
----------------------------------------------------------------------
*/
{
	var url = '../jsp/standard/en/inc/preview_medium_image.jsp?src=' + imgSrc;
	var myAjax = new Ajax.Updater(divId, url, {method: 'get'});
	//show the div after a small delay
	timer1 = setTimeout('showPreviewDiv('+ divId +')',400);
}

function showPreviewDiv(strDivId) 
{	
	document.getElementById(strDivId).style.display = 'block';
}

function hidePreview(divId) 
/*
----------------------------------------------------------------------
	d1		22-Jan-2008		Ben Browning			Created
----------------------------------------------------------------------
*/
{
	clearTimeout(timer1);
	$(divId).style.display = 'none';
}


function addToLightBox(assetId) 
/*
----------------------------------------------------------------------
	d1		18-Aug-2008		Matt Woollard			Created
----------------------------------------------------------------------
*/
{
	
	var divId = 0;
	var url = 'addToAssetBox?id=' + assetId + '&forward=Ajax';
	var myAjax = new Ajax.Updater(divId, url, {method: 'get'});
	
	var numItems = document.getElementById("numItemsInLightBox").innerHTML.stripTags();
	
	// if there are currently 0 items, now there's 1 so use different text
	if (numItems==0)
	{
		document.getElementById("itemsInLightbox").innerHTML = document.getElementById("itemInLightBoxText").value;
	}
	else
	{
		document.getElementById("itemsInLightbox").innerHTML = document.getElementById("itemsInLightBoxText").value;
	}
	
	// update the number of items in the lightbox
	document.getElementById("numItemsInLightBox").innerHTML = eval(numItems)+1;
	
	// update the link to show that it is in the lightbox
	document.getElementById("ajaxAddToLightBox"+assetId).className = "in";
	document.getElementById("ajaxAddToLightBox"+assetId).removeAttribute("href");
	document.getElementById("ajaxAddToLightBox"+assetId).innerHTML = document.getElementById("inLightBoxText").value;
}
