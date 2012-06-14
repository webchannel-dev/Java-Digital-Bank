/*
 * Bright Interactive, bulk-approve.js
 *
 * Copyright 2006 Bright Interactive, All Rights Reserved.
 *
 * make selecting a radio buttons for approval workflow transitions (e.g. epprove, reject etc) 
 * show a relevant dialogue and submit the form. 
 */
/*
Ver   Date	        Who					Comments
--------------------------------------------------------------------------------
d1    7-Mar-2012   	Ben Browning		Refactored from various approval jsps.
--------------------------------------------------------------------------------
*/

var bulkApprove = {
	
	config : {
			messages : true			// default to showing message box
	},

	init : function(options) {
		
		// provide for custom configuration via init()
		$j.extend(bulkApprove.config, options);
		
		//Set up some variables
		bulkApprove.$actionRadios = $j('#workflowActions input[type="radio"]');
		bulkApprove.$actionButtons = $j('#workflowActions label');
		
		// Setup up modal dialogue box containing message field
		if (bulkApprove.config.messages) {
			$j("#userMessageForm").dialog({
				autoOpen: false,
				height: 340,
				width: 380,
				modal: true,
				buttons: {
					"Submit": function() {
						$j(this).parent().prependTo($j("#bottomOfForm"));	//move fields back into main form so fields get included.
						$j('#mainForm').submit();							//submit form
					},
					Cancel: function() {
						$j(this).dialog( "close" );
						bulkApprove.$actionButtons.removeClass('on');
					}
				}
			});
		
		}
		// Click event on action radio buttons
		bulkApprove.$actionRadios.click(function() {
			bulkApprove.clickActionButton($j(this));
		});
		
		
	},
	
	clickActionButton : function($this) {
		// First check some assets have actually been selected:
		if ($j('#itemsWrapper li input:checked').length > 0) {
			// Highlight clicked multiline button
			bulkApprove.$actionButtons.removeClass('on');
			$this.parent().addClass('on');
			
			// get confirmation text and add to popup dialogue
			var confirmText = $this.parent().prev().val();
			$j('#confirmationText').empty().append(confirmText);
			
			// either show modal dialgue with message field or simple js confirm
			if ($this.hasClass('showMessage')) {
				$j("#userMessageForm").dialog("open");
			}
			else {
				var proceed = confirm(confirmText);
				if (proceed) {
					$j('#mainForm').submit();
				}
				
			}
			
		} else {
			alert(baWarningText);
		}
		
	}
	
	
}