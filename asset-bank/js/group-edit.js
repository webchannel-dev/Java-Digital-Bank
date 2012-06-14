

//encapsulate the js functionality on the asset approval pages in an object literal
var assetApproval = {
	init : function(settings) {
		//defaul config
		assetApproval.config = {
			$wrapper : $j('#assetsForApproval'),
			$masterContainer : $j('#assetsForApproval li.first'),
			$checkArray : $j('#assetsForApproval input.checkbox'),
			$copyLink : $j('#copyValuesLink'),
			highlightClass : 'selected'
		};
		//allow overriding of the default config
		$j.extend(assetApproval.config, settings);

        assetApproval.setup(); 
	},

	setup : function() {
		//set up click event on copy values link
		assetApproval.config.$copyLink.click( function() {
			assetApproval.copyMasterValues();
			return false;
		});
		
		if (assetApproval.config.$checkArray.length) {
			//click event on checkboxes (to highlight their container)
			assetApproval.config.$checkArray.click(function() {
					assetApproval.highlight($j(this));
			});
			//highlight any existing checked items
			$j('#assetsForApproval input:checked').each(function() {
					assetApproval.highlight($j(this),true);
			});
		}
	},
	
	// method to copy form values from the first item (the 'master') to all other items
	copyMasterValues : function() {
		var $masterCheckBox = assetApproval.config.$masterContainer.find('input[type="checkbox"]');
		// does this page have checkboxes
		if ($masterCheckBox.length) {
			//get status of master checkbox and copy to other checkboxes
			var $masterCheckboxStatus = $masterCheckBox.is(':checked'),
				$otherCheckboxes = assetApproval.config.$wrapper.find('li:not(.first) input[type="checkbox"]');
			if ($masterCheckboxStatus) {
				$otherCheckboxes
					.attr('checked', true)
					.each( function() {	
						assetApproval.highlight($j(this),$masterCheckboxStatus);
					});
			} else {
				$otherCheckboxes
					.removeAttr('checked')
					.each( function() {	
						assetApproval.highlight($j(this),$masterCheckboxStatus);
					});
			}	
		};	
		var dateInputValue = assetApproval.config.$masterContainer.find('input.date').val();
		var selectValue = assetApproval.config.$masterContainer.find('select').val();
		var textareaValue = assetApproval.config.$masterContainer.find('textarea').val();
		assetApproval.config.$wrapper.find('input.date').val(dateInputValue);
		assetApproval.config.$wrapper.find('select').val(selectValue);
		assetApproval.config.$wrapper.find('textarea').val(textareaValue);
	},
	
	// method to highlight the parent li of a checkbox when it is clicked. Can force a highlight
	// on or off using the optional parameter bOnOff (should be true or false)
	highlight : function($element,bOnOff) {
		if (typeof bOnOff === "undefined") {	
			$element.parents('li:eq(0)').toggleClass('selected');
		} else {
			if (bOnOff) {
				$element.parents('li:eq(0)').addClass('selected');
			} else {
				$element.parents('li:eq(0)').removeClass('selected');
			}
			
		};
	}
	
};	

