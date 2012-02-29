function showHideAgreementType()
{
	if (document.getElementById('agreementTypeSelect') != null)
	{
		agreementTypeSelect = document.getElementById('agreementTypeSelect');
		
		if(agreementTypeSelect.value==2)
		{
			document.getElementById('agreementDropdown').style.display="block";
		}
		else
		{
			document.getElementById('agreementDropdown').style.display="none";
		}
	}
}
	
function syncAgreementPreviewButton() 
{ 
	if (document.getElementById('previewButton') != null)
	{
		document.getElementById('previewButton').disabled=!(document.getElementById('agreementSelect').value>0); 
	}
}	