/**
 * Bright Interactive, usage-type-admin.js
 *
 * Copyright 2010 Bright Interactive, All Rights Reserved.
 */

function cropToFitChanged(a_bCropToFit)
{
	// N.B. the validation in this method must be kept in sync with the
	// server-side validation in SaveUsageTypeFormatAction.validateMaskFields()
	// Masking is allowed only if crop to fit is off
	var allowMaskingCheckbox = $j("#allowMasking");
	var bAllowMasking = !a_bCropToFit;
	if (bAllowMasking)
	{
		allowMaskingCheckbox.removeAttr("disabled");
	}
	else
	{
		allowMaskingCheckbox.removeAttr("checked");
		allowMaskingCheckbox.attr("disabled","disabled");
		allowMaskingChanged(bAllowMasking);
	}
}

function allowMaskingChanged(a_bAllowMasking)
{
	// N.B. the validation in this method must be kept in sync with the
	// server-side validation in SaveUsageTypeFormatAction.validateMaskFields()
	if (a_bAllowMasking)
	{
		$j("tr.maskOptions").show();
	}
	else
	{
		$j("tr.maskOptions").hide();
		$j("#presetMaskId").attr("value", 0);
		$j("#presetMaskColourId").attr("value", 0);
	}
}

function presetMaskIdChanged(a_sPresetMaskId)
{
	var iPresetMaskId = parseInt(a_sPresetMaskId, 10);
	if (iPresetMaskId > 0)
	{
		$j("#presetMaskColourId").removeAttr("disabled");
	}
	else
	{
		$j("#presetMaskColourId").attr("value", 0);
		$j("#presetMaskColourId").attr("disabled","disabled");
	}
}
