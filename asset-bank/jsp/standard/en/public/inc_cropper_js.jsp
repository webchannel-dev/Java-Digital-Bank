<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<c:set var="cropUndefined"><bright:cmsWrite identifier="snippet-crop-undefined" filter="false" /></c:set>
<c:set var="maskUndefined">Mask undefined</c:set>
<script type="text/javascript">
<!--
	// check dom ready
	$j(function() {
	
		// click event on mask popup link	
		$j('#maskLauncher').click(function(event) {
			
			var assetId = <bean:write name='downloadForm' property='asset.id'/>;
			
			//popup options
			var options = "resizable=yes,scrollbars=yes,toolbar=no,width=550,height=600";

			//set up url for mask popup
			var sLoc = 'viewMask?id=' + assetId + "&size=400&maskId=" + $j('#cropMaskId').attr('value')+'&canChooseMask='+$j('#canChooseMask').attr('value')+'&canChooseMaskColour='+$j('#canChooseMaskColour').attr('value');
			
			//check if reload properties have been set (i.e. we are reopening the popup)
			if ($j('#reloadMaskY').attr("value") && $j('#reloadMaskX').attr("value") && $j('#reloadMaskWidth').attr("value") && $j('#reloadMaskHeight').attr("value"))	{
				sLoc = sLoc + "&maskTop=" + parseInt($j('#reloadMaskY').attr("value"));
				sLoc = sLoc + "&maskLeft=" + parseInt($j('#reloadMaskX').attr("value"));
				sLoc = sLoc + "&maskWidth=" + parseInt($j('#reloadMaskWidth').attr("value"));
				sLoc = sLoc + "&maskHeight=" + parseInt($j('#reloadMaskHeight').attr("value"));
			}
			
			if ($j('#cropMaskColour').attr("value")) {
				sLoc = sLoc + "&maskColour=" + $j('#cropMaskColour').attr("value");
			}
	
			var cropWindow = window.open(sLoc,'maskPopup',options);
			if (window.focus) {cropWindow.focus();}

			return false;	

  		});
		
	});
	
	
	function initialiseCropper ()
	{
		if (document.getElementById('cropX').value &&
			document.getElementById('cropY').value &&
			document.getElementById('cropWidth').value &&
			document.getElementById('cropHeight').value &&
			document.getElementById('reloadCropX').value &&
			document.getElementById('reloadCropY').value &&
			document.getElementById('reloadCropWidth').value &&
			document.getElementById('reloadCropHeight').value)
		{
			//all the variables are present - we have returned here from the preview page or a validation failure
			//change the message that is shown for the cropper
			var sValue = "";
			var sValue = document.getElementById('cropDefinedPrefix').value + ': X: '+document.getElementById('cropX').value+' Y: '+document.getElementById('cropY').value+' Width: '+document.getElementById('cropWidth').value+' Height: '+document.getElementById('cropHeight').value;
			document.getElementById('cropMessage').innerHTML = sValue;
			//hide 'select crop area...' link and show
			document.getElementById('selectCrop').style.display = 'none';
			document.getElementById('changeCrop').style.display = 'inline';

			
			// Show clear crop area link
			if (document.getElementById('clearCrop'))
			{
				document.getElementById('clearCrop').style.display = 'inline';
			}
		} 
	}
	
	function initialiseMasks ()
	//BB Created based on initialiseCropper()
	{
		if (document.getElementById('cropX').value &&
			document.getElementById('cropY').value &&
			document.getElementById('cropWidth').value &&
			document.getElementById('cropHeight').value &&
			document.getElementById('reloadMaskX').value &&
			document.getElementById('reloadMaskY').value &&
			document.getElementById('reloadMaskWidth').value &&
			document.getElementById('reloadMaskHeight').value)
		{
			
			//all the variables are present - we have returned here from the preview page or a validation failure
			//BB Also update Masks message
			var sUpdateMessage = ""
			
			sUpdateMessage = sUpdateMessage + document.getElementById('maskDefinedPrefix').value + ': X: '+document.getElementById('cropX').value+' Y: '+document.getElementById('cropY').value+' Width: '+document.getElementById('cropWidth').value+' Height: '+document.getElementById('cropHeight').value;
			
			if (document.getElementById('cropMaskColour').value) {
				sUpdateMessage = sUpdateMessage + '<br />Mask colour: #'  +  document.getElementById('cropMaskColour').value;
			}
			
			document.getElementById('maskMessage').innerHTML = sUpdateMessage;
			
			// Show clear mask link
			if (document.getElementById('clearMask'))
			{
				document.getElementById('clearMask').style.display = 'inline';
			}
		}
	}

	function clearCrop ()
	// Clears any crop settings and resets the crop panel
	{
		if($j('#cropperContent').length>0) {
			document.getElementById('cropMessage').innerHTML = "<c:out value='${cropUndefined}'/>";
			document.getElementById('selectCrop').style.display = 'block';
			document.getElementById('changeCrop').style.display = 'none';
		}
		if (document.getElementById('clearCrop'))
		{
			document.getElementById('clearCrop').style.display = 'none';
		}

		//clear the values...
		document.getElementById('cropX').value = "";
		document.getElementById('cropY').value = "";
		document.getElementById('cropWidth').value = "";
		document.getElementById('cropHeight').value = "";
		document.getElementById('reloadCropX').value = "";
		document.getElementById('reloadCropY').value = "";
		document.getElementById('reloadCropWidth').value = "";
		document.getElementById('reloadCropHeight').value = "";
	}
	
	function clearMask (bFullClear)
	// Clears any mask settings and resets the mask panel. There are 2 levels of 'clearing' depending
	// on whether bFullClear is set to true (this is generally only done using the 'clear mask' link)
	{
		document.getElementById('maskMessage').innerHTML = "<c:out value='${maskUndefined}'/>";

		if (document.getElementById('clearMask'))
		{
			document.getElementById('clearMask').style.display = 'none';
		}

		//clear the values...
		document.getElementById('cropX').value = "";
		document.getElementById('cropY').value = "";
		document.getElementById('cropWidth').value = "";
		document.getElementById('cropHeight').value = "";
		document.getElementById('reloadMaskX').value = "";
		document.getElementById('reloadMaskY').value = "";
		document.getElementById('reloadMaskWidth').value = "";
		document.getElementById('reloadMaskHeight').value = "";
		if(bFullClear) {
			document.getElementById('cropMaskId').value = "";
			document.getElementById('cropMaskColour').value = "";		
		}		
	}
	
	// following function is called when you click the 'masks' tab
	function switchOnMask(element) {
		//switch active tab
		switchTab(element);
		//update hidden field
		document.getElementById('cropMask').value = "true";
		//check if reload values are set
		if (
			document.getElementById('reloadMaskX').value != "" &&
			document.getElementById('reloadMaskY').value != "" &&
			document.getElementById('reloadMaskWidth').value != "" &&
			document.getElementById('reloadMaskHeight').value != "") {
			//they are set so update the necessary hidden fields using a function in download-form.js
			downloadForm.onSwitchMask(
				{"left":$j('#reloadMaskX').val(),"top":$j('#reloadMaskY').val()}, 
				{"width":$j('#reloadMaskWidth').val(),"height":$j('#reloadMaskHeight').val()}
				);
		}
		//show relevant content
		$j('#cropperContent').hide();
		$j('#maskContent').show();
	}
	// following function is called when you click the 'crop' tab
	function switchOnCropper(element) {
		//switch active tab
		switchTab(element);
		//update hidden field
		document.getElementById('cropMask').value = "false";
		//check if reload values are set
		if (
			document.getElementById('reloadCropX').value != "" &&
			document.getElementById('reloadCropY').value != "" &&
			document.getElementById('reloadCropWidth').value != "" &&
			document.getElementById('reloadCropHeight').value != "") {
			//they are set so update the necessary hidden fields using a function in download-form.js
			downloadForm.onSwitchCrop(
				{"left":$j('#reloadCropX').val(),"top":$j('#reloadCropY').val()}, 
				{"width":$j('#reloadCropWidth').val(),"height":$j('#reloadCropHeight').val()}
				);
		}	
		//show relevant content
		$j('#cropperContent').show();
		$j('#maskContent').hide();
	}
	
-->
</script>