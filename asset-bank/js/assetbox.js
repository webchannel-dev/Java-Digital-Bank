/*
	Bright Interactive, download-form.js

	Copyright 2010 Bright Interactive, All Rights Reserved.

	Deals with selecting assets, and applying actions to them.

	Ver   Date	        Who					Comments
	--------------------------------------------------------------------------------
	d4	  18-May-2010	Ben Browning		Created as assetbox js became more involved
	--------------------------------------------------------------------------------
*/
			
/*
Submits form if selected option is an action
*/		
function submitLbAction(value) {
	$j('#actionOnSelectedAssets').val(value);
	var $hiddenSelectedAction = $j('#actionOnSelectedAssets')
	if ( value > 0) {
		// if the selected option is to remove all assets, use a confirm box to check user is sure
		if ( value == "3") {
			var r = confirm(confirmRemoveMessage);
			if(r==true){
				//submit form
				$j('#actionOnSelected').submit();
			}
			else {
				// set drop down back to default
				$hiddenSelectedAction.val('0');
				return false;
			}	
		}	
		else {
			$j('#actionOnSelected').submit();
		}
	}	
	return false;
}
	
function switchOnButton() {
	$j('#dummyButtons').hide();
	$j('#dropButtonSelected').show();
}
function switchOffButton() {
	$j('#dummyButtons').show();
	$j('#dropButtonSelected').hide();
}

/*
Function to select all or select none assets
*/			
function lbSelectItems(b_trueOfFalse, containerId) {
	//call generic select/deselect all function in bright_util.js
	selectItems(b_trueOfFalse, containerId);
	//update count
	if (b_trueOfFalse) {
		checkedCount = $j('#'+containerId+' input.checkbox').length
		updateSelectedAssetsCount(checkedCount);
	} else {
		checkedCount = 0;
		updateSelectedAssetsCount(checkedCount);
	}	
}

/*
Function to keep count of the number of selected assets
*/			
function updateSelectedAssetsCount(intCount) {
	$j('#selectedAssetCount').text(intCount)
	//depending on count, switch on or off the action button
	if (intCount == 0) {
		switchOffButton();
	} else {
		switchOnButton();
	}
}
		

$j(function() {		// when page has loaded
		
	//Set up some variables
	var $checkArray = $j('input.checkbox');		//get reference to all checkboxes on the page
	var $checkedboxes = $j('li input:checked')	//get reference to all initially checked checkboxes on the page
			
	//update global variable with many are checked?
	checkedCount = $checkedboxes.length;
			
	//update count of selected items in page
	$j('#selectedAssetCount').text(checkedCount)
			
	//switch on actions dropdown button if necessary
	if (checkedCount>0) {
		switchOnButton();
	}
			
	//highlight wrapping li of any already selected checkboxes
	$checkedboxes.parents('li').addClass('selected');
			
	// click event on checkboxes
	$checkArray.click(function(event) {
		event.stopPropagation();	
		//highlight surrounding li
		$j(this).parents('li:eq(0)').toggleClass('selected');
		// update count of selected assets
		if (this.checked) {
			checkedCount = checkedCount + 1
		} else {
			checkedCount = checkedCount - 1
		}	
		updateSelectedAssetsCount(checkedCount);
					
	});

			
	//click event on download selected button
	$j('#dropButtonSelected a#downloadSelected').click(function(e) {
		submitLbAction(1);
	});

	/* BB - had an attempt at changing lightbox reordering to use jquery ui, but there are some rendering
	issues because the li's do not have an explicit height set. 

	// asset sorting
	$j('ul.lightbox').sortable({
		//placeholder : 'landing',
		handle: 'div.draggable',

		update: function(event, ui) {
			var droppedItemId = ui.item.attr('id');
			var nextsiblingId = ui.item.next('li').attr('id');		// BB - what if there is no next sibling?
			//console.log(droppedItemId+','+nextsiblingId);
			moveAssetInLightbox(droppedItemId.replace("draggable",""), nextsiblingId.replace("draggable",""));
		}
	});
	
	*/
		
});