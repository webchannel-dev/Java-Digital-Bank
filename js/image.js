function updateHeight(a_fWidthField)
{
	var downloadForm = a_fWidthField.form;

	if(!downloadForm.aspect || downloadForm.aspect.checked == true)
	{
		if(parseInt(downloadForm.width.value) > 0)
		{
			var fHeight = downloadForm.origHeight.value*((parseInt(downloadForm.width.value))/(downloadForm.origWidth.value));

			if(fHeight - parseInt(fHeight) > 0.5)
			{
				fHeight = fHeight + 1;
			}

			downloadForm.height.value = parseInt(fHeight);
		}
	}
}

function updateWidth(a_fHeightField)
{
	var downloadForm = a_fHeightField.form;

	if(!downloadForm.aspect || downloadForm.aspect.checked == true)
	{
		if(parseInt(downloadForm.height.value) > 0)
		{
			var fWidth = downloadForm.origWidth.value*((parseInt(downloadForm.height.value))/(downloadForm.origHeight.value));

			if(fWidth - parseInt(fWidth) > 0.5)
			{
				fWidth = fWidth + 1;
			}

			downloadForm.width.value = parseInt(fWidth);
		}
	}
}