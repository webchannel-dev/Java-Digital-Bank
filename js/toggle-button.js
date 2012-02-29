/**
 * Bright Interactive, download-form.js
 *
 * Copyright 2006 Bright Interactive, All Rights Reserved.
 *
 * Toggle submit button values depending on whether 'email' is checked
 */
/*
Ver   Date	        Who					Comments
--------------------------------------------------------------------------------
d1    3-Nov-2006   Adam Bones  Created
--------------------------------------------------------------------------------
*/

function swapButtonText(ctrl,onValue,offValue)
{
	if (ctrl!=null && ctrl.checked) {
		Buttons.each(function(id) {
	       if ($(id)) {
	          $(id).value = onValue + ButtonTextSuffix[Buttons.indexOf(id)];
	        }
	      });
    } else {
	    Buttons.each(function(id) {
	      if ($(id)) {
	        $(id).value = offValue + ButtonTextSuffix[Buttons.indexOf(id)];
	      }
	    });
	}
}

Event.observe(window, 'load', function() {
  if ($('sendemail')) {
    Event.observe('sendemail', 'click', function(event) {
      swapButtonText(this,sEmailText,sDownloadText);
    }.bind($('sendemail')));
  }
  if ($('repurpose')) {
    Event.observe('repurpose', 'click', function(event) {
      swapButtonText(this,sRepurposeText,sDownloadText);
    }.bind($('repurpose')));
  }
  if ($('compress')) {
    Event.observe('compress', 'click', function(event) {
    	if($('sendemail') && $('sendemail').checked)
    	{
    		swapButtonText(this,sEmailText,sEmailText);
    	}
    	else
    	{
    		swapButtonText(this,sRepurposeText,sDownloadText);
    	}
    }.bind($('repurpose')));
  }
});

function doAlternativeMethodSelection(ctrl)
{
	if (ctrl)
	{
		if($('sendemail')==ctrl && ctrl.checked)
		{ 
			if ($('repurpose')) 
			{
				$('repurpose').checked=false;
			}
			
			if($('downloadOriginal'))
			{
				$('downloadOriginal').style.display='block';
			}
		}
		else if($('repurpose')==ctrl && ctrl.checked)
		{ 
			$('sendemail').checked=false;
			
			if($('downloadOriginal'))
			{
				$('downloadOriginal').style.display='none';
			}
			
			if($('compress'))
			{
				$('compress').checked=false;
			}
		}
		else if($('repurpose')==ctrl && !ctrl.checked)
		{
			if($('downloadOriginal'))
			{
				$('downloadOriginal').style.display='block';
			}
		}
		else if($('compress')==ctrl && ctrl.checked)
		{
			if($('repurpose'))
			{
				$('repurpose').checked=false;
			}
			if($('downloadOriginal'))
			{
				$('downloadOriginal').style.display='block';
			}
		}
	}
}