/*
Version Date			Developer			Comment
----------------------------------------------------------------------
d1		20-Nov-2007		Ben Browning		Created from old nav-standard.js
*/
/*
NOTES:

This script now manipulates class names instead of style properties so that the presentation of the drop downs
is customisable through css alone.

NB: Makes use of prototype.js library methods
*/

//global variable
var toggleDownArrow = 1;


function showMenu(menu_id) {
	var menu = $(menu_id);
	var link = $(menu_id + '_link');
	if ((menu.style.display == 'none') || (menu.style.display == ''))
	{
		menu.style.display='block';
		if (toggleDownArrow) {
			link.addClassName('open');
			link.removeClassName('openHover');
		}

	}
	else
	{
		menu.style.display='none';
		link.removeClassName('open');
		link.addClassName('openHover');
	}
}

function hoverMenu(menu_id,toggleVisibility) {
	var menu = $(menu_id);
	var link = $(menu_id + '_link');
	var admin = $('adminPage') || $('importPage');
	
	if (!(admin)) {
		if ((menu.style.display == 'none') || (menu.style.display == '')) {
			
			if (toggleVisibility && toggleDownArrow) {
				link.addClassName('openHover');
			}
			else {
				link.removeClassName('openHover');
			}
		}
	}
}