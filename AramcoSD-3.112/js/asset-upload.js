function validateUrl(id, baseUrl, displayName)
{
	if (id.value=="")
	{	
		return;
	}
	// If it doesn't contain a newline just open the url
	else if(id.value.indexOf("\n")<0)
	{
		var url = id.value.split("==")[0];
		window.open(baseUrl.value+url);
	}
	// If it contains a newline pass the whole string to the following url
	else
	{
		displayPopup("validateHyperlink?hyperlink="+id.value.replace(/\n/g,"||")+"&baseurl="+baseUrl.value+"&linkname="+displayName.value);
	}
}

var hasChanged = false;

function setChanged () 
{
	hasChanged = true;
}
			
function prepForm ()
{
	//attach an event to every input in the form to make sure we know when something has changed...
	var elements = document.forms[1].getElementsByTagName("input");
	
	for (var i=0; i<elements.length; i++) 
	{
		setEvent(elements[i]);
	}

	elements = document.forms[1].getElementsByTagName("select");
	
	for (var i=0; i<elements.length; i++) 
	{
		setEvent(elements[i]);
	}

	elements = document.forms[1].getElementsByTagName("textarea");
	
	for (var i=0; i<elements.length; i++) 
	{
		setEvent(elements[i]);
	}
}

function setEvent (element)
{
	if (element.addEventListener) 
	{
		element.addEventListener("change", setChanged, false);
	}
	else if (element.attachEvent) 
	{
		element.attachEvent("onchange", setChanged);
	}
	else if (document.getElementById)
	{
		element.onchange=setChanged;
	}
}

function openUploader()
{
	var uploaderWindow = open('viewUploader?uploadToolOption=browser' ,'uploader','resizable=no,scrollbars=yes,width=440,height=250');
	uploaderWindow.location.href = 'viewUploader?uploadToolOption=browser';
	uploaderWindow.focus();
	if (uploaderWindow.opener == null) 
	{
		uploaderWindow.opener = self;
	}
	return false;
}

function setUploadedFile(filename)
{
	if(document.getElementById('uploadedFile'))
	{
		document.getElementById('file').style.display='none';
		document.getElementById('uploadedFile').style.display='block';
		document.getElementById('uploadedFilenameText').innerHTML=filename;
		document.getElementById('uploadedFilenameField').value=filename;
	}
}

function clearUploadedFile()
{
	if(document.getElementById('uploadedFile'))
	{
		document.getElementById('file').style.display='block';
		document.getElementById('uploadedFile').style.display='none';
		document.getElementById('uploadedFilenameText').innerHTML='';
		document.getElementById('uploadedFilenameField').value='';
	}
}

var dataLookupId = null;

function performDataLookup(lAttributeId, form)
{
	var url = 'runDataLookup';
	var sParams = 'id=' + lAttributeId;
	var spanId = 'dataLookupCode';
	var $messageDiv = $j('#dataLookupMessage' + lAttributeId );
	var $loaderDiv = $j('#dataLookupLoader' + lAttributeId );
	
	dataLookupId = lAttributeId;
	
	// show the "working" message 
	$loaderDiv.show();
	// reset message
	$messageDiv.empty();
	
	var sParams= {};
	//tack on all the form parameters...
	for (var i=0; i < form.elements.length; i++)
	{
		var element = form.elements[i];
		if (element.name != 'file' && element.name != 'substituteFile')
		{
			sParams[element.name] = element.value;
		}
	}
	// use jquery load method to do ajax call and insert respnse into $messageDiv
	$messageDiv.load(url,sParams,function(){
		$loaderDiv.hide();
	})
	//var myAjax = new Ajax.Updater(spanId, url, {method: 'post', parameters: sParams, evalScripts: true});	
}


function switchSaveType(iValue)
{
	var saveType = document.getElementById('saveType');

	if (iValue == 1 || iValue == 3)
	{
		saveType.style.display = "none";
	}
	else
	{
		saveType.style.display = "block";
	}
}	

