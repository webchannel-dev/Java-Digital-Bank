<script type="text/JavaScript">
	// once the dom is ready
	$j(function () {
		//give the newCatName field the focus 
		$j('#newCatName').focus();
		
		var $radioGroup = $j('input[name="extendedCategory"]');
	
		$radioGroup.click(toggleCatType);
		
		toggleCatType();
		
		function toggleCatType() {
			if ($radioGroup.filter(':checked').val()=='true') {
				$j('#standardCatHolder').hide('fast');
				$j('#extendedCatHolder').show();
			} else {
				$j('#standardCatHolder').show('fast');
				$j('#extendedCatHolder').hide();
			}
		}
		
	});
</script>	