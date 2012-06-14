/**
* Disables the right-click menu, for example to stop a user from right-clicking and choosing
'save-as' to download an image. Not - they would still be able to do this by turning off JS.

To use: include the following attrbutes in the image tag:
onmouseup="right(event);"
onmousedown="right(event);	
*/
function disableRightMenu(e, sMessage) 
{
	if (e.button ==2) 
	{
		alert(sMessage);
		return false;
	}
	 
	return true;
}
