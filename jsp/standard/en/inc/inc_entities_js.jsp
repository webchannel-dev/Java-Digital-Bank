<script type="text/javascript">
	<!-- 
		function applyEntitySelection()
		{
			<c:if test="${empty assetForm.uploadToolOption || assetForm.uploadToolOption=='basic'}">
			if(document.getElementById('entity'))
			{
				var bAllowFiles = allowFiles[document.getElementById('entity').selectedIndex];
				document.getElementById('file').disabled = !bAllowFiles;
				document.getElementById('uploaderLink').style.display = (bAllowFiles ? 'block' : 'none');
				if(document.getElementById('emptyAsset'))
				{
					document.getElementById('emptyAsset').checked=!bAllowFiles;
					document.getElementById('emptyAsset').disabled=!bAllowFiles;
				}
				document.getElementById('forceEmptyAsset').value=!bAllowFiles;
				changeButtonText(!bAllowFiles);
			}
			</c:if>
		}
	//-->
</script>

