/*
 * Bright Interactive, download-form.js
 *
 * Copyright 2006 Bright Interactive, All Rights Reserved.
 *
 * Form behaviours: dynamically update image dimensions, create a popup window in which the user can select a crop area.
 */
/*
Ver   Date	        Who					Comments
--------------------------------------------------------------------------------
d1    24-Oct-2003   Adam Bones			Created
d2    30-Oct-2003   Adam Bones			Changed to sort out event fire order problems in IE
d3	  24-Jan-2008	Matt Stevenson		Added fixed crop ratio
d4	  14-Apr-2010	Ben Browning		Added code for Masks popup
--------------------------------------------------------------------------------
*/

var DownloadForm = Class.create();
DownloadForm.prototype = {
  // Set up a form for this image id
  initialize: function(id, realWidth, realHeight) {
	if($('cropLauncher'))
    {
    	$('cropLauncherDiv').style.display = 'inline'; // js on, so enable cropping
    }
    this.id = id;
    this.realWidth = realWidth;
    this.realHeight = realHeight;
	this.scale = this.getScale(realWidth, realHeight);
    
    // Get initial values for all inputs
    this.saveFieldValues();
	//Set up click events
	this.registerCallbacks();
     
    // kludge: store a ref to this object so that the popup can reference it at window.opener.downloadForm...
    window.downloadForm = this;
  },
  
  saveFieldValues: function() {
    $A(document.getElementsByTagName("input")).each(function(element) {
      if (element.id) {
        this[element.id] = element.value;
      }
    }.bind(this));
  },
  
  // Find the scale to use in the popup window, such that the image fits in 720x400
  // BB changed to fit in 400 by 400 box to make consistent with masks popup
  getScale: function(realWidth, realHeight) {
    var xScale = 1, yScale = 1;
    if (realWidth > 400) {
      xScale = 400 / realWidth;
    }
    if (realHeight > 400) {
      yScale = 400 / realHeight;
    }
    return Math.min(xScale, yScale);
  },
  
  registerCallbacks: function() {
    
	// Add callbacks on each input
    $A(document.getElementsByTagName("input")).each(function(element) {
      if (element.type == "text") {
		Event.observe(element, 'change', this.onChangeField.bind(this));
      }
    }.bind(this));
    
	if ($('cropLauncher')) {
		Event.observe('cropLauncher', 'click', this.onCropLauncherClick.bind(this));
	}
  },
  
  // Validate a field and dispatch to a specific action handler
  // Define handlers in the form onChangeId
  onChangeField: function(event) {
    var element = Event.element(event);

	var bCheck = true;
	if ((element.id=='startOffset' && (element.value == '' || element.value == '0')) || (element.id=='duration' && element.value==''))
	{
		bCheck = false;
	}

	if (element.id!='usageOther' && bCheck) 
	{ // the 'usageOther' field is exempt since it is non-numeric
      if (parseInt(element.value)) { // look for a specific handler for this element
        if(this[('onChange-' + element.id).camelize()]) {
          var callback = this[('onChange-' + element.id).camelize()].bind(this);
          if (callback) {
            callback(element);
          }
	    }
        this[element.id] = element.value; // value ok, so save it
      } else {
        if(this[element.id]){
        	element.value = this[element.id]; // value invalid, restore the saved value
        }
      }
    }
  },

  // Validate that the field contains an int, then update other fields:
  onChangeWidth: function() {
    if ($('lockAspectRatio').checked || $('lockAspectRatio').value=='checked') {
      var delta = $('width').value / this.width;
      this.height = $('height').value = Math.floor($('height').value * delta);
    }
  },
  
  onChangeHeight: function() {
	if ($('lockAspectRatio').checked || $('lockAspectRatio').value=='checked') {
      var delta = $('height').value / this.height;
      this.width = $('width').value = Math.floor($('width').value * delta);
    }
  },
  
  // Mirror changes to crop size in image size
  onChangeCropWidth: function() {
    $('width').value = $('cropWidth').value;
  },
  
  onChangeCropHeight: function() {
    $('height').value = $('cropHeight').value;
  },
  
  onCropLauncherClick: function(event) {
	
	//work out the dimensions of the resized image
	var popupImgWidth = Math.floor(this.scale * this.realWidth);
    var popupImgHeight = Math.floor(this.scale * this.realHeight);
    
    var options = {
      resizable: 'yes',
      scrollbar: 'yes',
      toolbar: 'no',
      width: popupImgWidth + 40,
      height: popupImgHeight + 175
    };

    var sOptions = '';
    for (prop in options) {
      sOptions += prop + '=' + options[prop] + ',';
    }
    sOptions = sOptions.slice(0, -1);
	//BB hardcoded size param to 400 
	var sLoc = 'viewCrop?id=' + this.id + "&size=400" + "&fixedRatioX=" + $('fixedRatioX').value + "&fixedRatioY=" + $('fixedRatioY').value;
	
	if($('layerToConvert'))
	{
		sLoc += "&layer=" + $('layerToConvert').value;
	}
	
	if ($('reloadCropX').value && $('reloadCropY').value && $('reloadCropWidth').value && $('reloadCropHeight').value)
	{
		var x2 = parseInt($('reloadCropX').value) + parseInt($('reloadCropWidth').value);
		var y2 = parseInt($('reloadCropY').value) + parseInt($('reloadCropHeight').value);
		sLoc = sLoc + "&x1=" + $('reloadCropX').value;
		sLoc = sLoc + "&x2=" + x2;
		sLoc = sLoc + "&y1=" + $('reloadCropY').value;
		sLoc = sLoc + "&y2=" + y2;
	}
	
	this.cropWindow = open(
  	  sLoc,
  	  'viewImage',
  	  sOptions
  	);
  	
  	Event.stop(event);
  },
  
  // Update the crop fields after an interactive session in the popup
  onEndCrop: function(coords, dimensions) {
	$('cropX').value = this.getRealDim(coords.x1);
  	$('cropY').value = this.getRealDim(coords.y1);
  	$('reloadCropX').value = coords.x1;
	$('reloadCropY').value = coords.y1;
	$('reloadCropWidth').value = dimensions.width;
	$('reloadCropHeight').value = dimensions.height;
	$('cropWidth').value = this.cropWidth = this.width = this.getRealDim(dimensions.width);
  	$('cropHeight').value = this.cropHeight = this.height = this.getRealDim(dimensions.height);
	
	//update the standard image width and height fields 
	if ($('width'))
	{
		$('width').value = $('cropWidth').value
	}
	if ($('height'))
	{
		$('height').value = $('cropHeight').value
	}

	//show the 'defined' message...
	$('cropMessage').innerHTML = $('cropDefinedPrefix').value + ': X: '+$('cropX').value+' Y: '+$('cropY').value+' Width: '+$('cropWidth').value+' Height: '+$('cropHeight').value;
	$('selectCrop').style.display = 'none';
	$('changeCrop').style.display = 'inline';

	if ($('clearCrop'))
	{
		$('clearCrop').style.display = 'inline';
  	}
  },  
  
   // Update the mask  fields after an interactive session in the popup
  onEndMask: function(maskId, maskPosition, maskSize, maskColour) {
	$('cropX').value = this.getRealDim(maskPosition.left);
  	$('cropY').value = this.getRealDim(maskPosition.top);
  	$('reloadMaskX').value = maskPosition.left;
	$('reloadMaskY').value = maskPosition.top;
	$('reloadMaskWidth').value = maskSize.width;
	$('reloadMaskHeight').value = maskSize.height;
	$('cropWidth').value = this.cropWidth = this.width = this.getRealDim(maskSize.width);
  	$('cropHeight').value = this.cropHeight = this.height = this.getRealDim(maskSize.height);
	$('cropMaskId').value = maskId;
	
	//update the standard image width and height fields 
	if ($('width')) {
		$('width').value = $('cropWidth').value
	}
	if ($('height')) {
		$('height').value = $('cropHeight').value
	}
	
	var sUpdateMessage = ""
	
	//show the 'defined' message...
	sUpdateMessage = $('maskDefinedPrefix').value + ': X: '+$('cropX').value+' Y: '+$('cropY').value+' Width: '+$('cropWidth').value+' Height: '+$('cropHeight').value;
	
	if (maskColour) {
		$j('#cropMaskColour').attr('value', maskColour);
		sUpdateMessage = sUpdateMessage + '<br />Mask colour: #'  +  maskColour;
	}
	
	$('maskMessage').innerHTML = sUpdateMessage;
	
	//show clear mask link
	if ($('clearMask')) {
		$('clearMask').style.display = 'inline';
  	}
  },   
  
   // Update the crop fields after an interactive session in the popup
  onSwitchCrop: function(coords, dimensions) {
	$('cropX').value = this.getRealDim(coords.left);
  	$('cropY').value = this.getRealDim(coords.top);
	$('cropWidth').value = this.cropWidth = this.width = this.getRealDim(dimensions.width);
  	$('cropHeight').value = this.cropHeight = this.height = this.getRealDim(dimensions.height);
	
	//update the standard image width and height fields 
	if ($('width'))
	{
		$('width').value = $('cropWidth').value
	}
	if ($('height'))
	{
		$('height').value = $('cropHeight').value
	}

  },   
  
    // Update some fields when switching to the Masks tab
  onSwitchMask: function(maskPosition, maskSize) {
	$('cropX').value = this.getRealDim(maskPosition.left);
  	$('cropY').value = this.getRealDim(maskPosition.top);
	$('cropWidth').value = this.cropWidth = this.width = this.getRealDim(maskSize.width);
  	$('cropHeight').value = this.cropHeight = this.height = this.getRealDim(maskSize.height);
	
	//update the standard image width and height fields 
	if ($('width')) {
		$('width').value = $('cropWidth').value
	}
	if ($('height')) {
		$('height').value = $('cropHeight').value
	}
	
  },   
  
  getRealDim: function(screenDim) {
  	return Math.round(screenDim * (1 / this.scale));
  }
}