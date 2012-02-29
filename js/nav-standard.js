var toggleDownArrow = 1;

function showMenu(menu_id) {
	var menu = document.getElementById(menu_id);
	var link = document.getElementById(menu_id + '_link');
	if ((menu.style.display == 'none') || (menu.style.display == ''))
	{
		menu.style.display='block';
		if (toggleDownArrow) {
			link.style.background = 'url(../images/standard/arrow/nav_down_arrow.gif) right center no-repeat';
		}

	}
	else
	{
		menu.style.display='none';
		link.style.background = 'none';
	}
}

function hoverMenu(menu_id,toggleVisibility) {
	var menu = document.getElementById(menu_id);
	var link = document.getElementById(menu_id + '_link');
	var admin = document.getElementById('adminPage') || document.getElementById('importPage');
	
	if (!(admin)) {
		if ((menu.style.display == 'none') || (menu.style.display == '')) {
			
			if (toggleVisibility && toggleDownArrow) {
				link.style.background = 'url(../images/standard/arrow/nav_down_arrow_hover.gif) right center no-repeat';
			}
			else {
				link.style.background = 'none';
			}
		}
	}
}