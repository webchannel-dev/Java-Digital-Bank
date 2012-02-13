/**
 * Bright Interactive, calendar.js
 *
 * Copyright 2003 Bright Interactive, All Rights Reserved.
 * 
 * Contains JavaScript functions used by the date selector window.
 *
 */
/*
Ver  Date	        Who					Comments
--------------------------------------------------------------------------------
d1  24-Oct-2003     Martin Wilson       Created.
d2  14-Jul-2004     Chris Preager       Changed to bring calendar window to focus if already open.
d3  30-Jun-2005     Martin Wilson		Changed to set the month to the value in the field
d4  24-Apr-2009     Francis Devereux	Fix bug where a date string of "1/1/09" results in "1/1/1909" in the popup instead of "1/1/2009"
										Use Date.getFullYear instead of Date.getYear because Date.getYear behaviour is inconsistent between browsers
--------------------------------------------------------------------------------
*/

var today = new Date();
var day   = today.getDate();
var month = today.getMonth();
var year  = today.getFullYear();
var dateFieldBeingChanged;

var bDaysBeforeMonths = true;

function padout(number) { return (number < 10) ? '0' + number : number; }

function restart() 
{
	if(bDaysBeforeMonths)
	{
		dateFieldBeingChanged.value = '' + padout(day) + '/' + padout(month - 0 + 1) + '/' + year;
	}
	else
	{
		dateFieldBeingChanged.value = '' + padout(month - 0 + 1)  + '/' + padout(day) + '/' + year;
	}

	// Fire a jQuery keyup on the field so that we group-edit triggers are fired
	$j("#" + dateFieldBeingChanged.id).keyup();

	mywindow.close();
}

function openDatePicker(a_dateField) 
{	
	openDatePickerSupportUS(a_dateField, true);
}

function openDatePickerSupportUS(a_dateField,a_bDaysBeforeMonths) 
{		
	bDaysBeforeMonths = a_bDaysBeforeMonths;

	// Set the date picker values to be the values in the field, if set:

	var dtStart;

	// Try to work out the current day, month, year:
	var sDate = a_dateField.value;
	var iPos = 0;
	var iLastPos = 0;
	var iSpacePos = 0;
	var sDay = "";
	var sMonth = "";
	var sYear = "";

	// Try to set the current month in the date picker to the month in the field:
	if (sDate && sDate.length>0)
	{
		// Get the occurrence of the first /:
		iPos = sDate.indexOf("/", 0);

		if (iPos>0)
		{
			if (a_bDaysBeforeMonths)
			{
				sDay = sDate.substring(0, iPos);
			}
			else
			{
				sMonth = sDate.substring(0, iPos);
			}
		}

		// Get the occurrence of the next /:
		iLastPos = iPos;
		iPos = sDate.indexOf("/", iLastPos+1);

		if (iPos>0)
		{
			if (a_bDaysBeforeMonths)
			{
				sMonth = sDate.substring(iLastPos+1, iPos);
			}
			else
			{
				sDay = sDate.substring(iLastPos+1, iPos);
			}

			// Finally, get the year:
			//Check if the time is included in the field (look for a space)
			iSpacePos = sDate.indexOf(" ",iPos);
			if (iSpacePos>0) 
			{
				sYear = sDate.substring(iPos+1, iSpacePos);
			} 
			else
			{
				sYear = sDate.substring(iPos+1, sDate.length);
			}
		}

	}	
	
	if (isValidNumber(sYear) && isValidNumber(sMonth) && isValidNumber(sDay))
	{
		var iYear = parseInt(sYear, 10);

		// If the user entered a two digit year, add 2000 to it, so for
		// example "09" becomes 2009.
		// This breaks when years from 0000 to 0099 are being entered as 4
		// digit years, but these years are converted to 1900-1999 by the
		// calendar component anyway (it probably passes the year straight to
		// the JS Date constructor).  If the calendar component is fixed,
		// delete this code and use the commented out code below
		if (iYear < 100)
		{
			iYear += 2000;
		}

//		// If year was specified as 1 or 2 digits, add 2000 to it
//		// This allows dates before 0100 to be specified, i.e. "0009" will be
//		// left unchanged but "09" will be changed to 2009
//		if (sYear.length < 3)
//		{
//			iYear += 2000;
//		}

		// Initialise the calendar to show the date:
		dtStart = new Date(iYear, sMonth-1, sDay);
	}
	else
	{
		// Otherwise set the calendar to this month:
		dtStart = new Date();
	}

	// set the starting values:
	day   = dtStart.getDate();
	month = dtStart.getMonth();
	year  = dtStart.getFullYear();

	dateFieldBeingChanged = a_dateField;

	// Close existing window, if it's open
	try {
		mywindow.close();
	}
	catch (error) {
		if (error instanceof ReferenceError || error instanceof TypeError) {
			//that's fine, nothing to do
		}
		else {
			alert("Unexpected error : " + error);
		}
	}

	mywindow=open('../calendar/calendar.html','myname','resizable=yes,status=no,width=300,height=230,left=100,top=30');
	mywindow.location.href = '../calendar/calendar.html';
	if (mywindow.opener == null) 
	{
		mywindow.opener = self;
	}
}

function isValidNumber(numval)
{
	if (numval==""){return false;}
	var myRegExp = new RegExp("^[/+|/-]?[0-9]*[/.]?[0-9]*$");
	return myRegExp.test(numval);
}

function writeDatePickerIcon(field) 
// document.write the right code to present the date picker icon
// field is the name of the text input that will receive the 
// selected date.
{
	document.write('<a href="javascript:;" title="Date chooser"><img class="icon" src="/calendar/calendar.gif" alt="Date chooser" onclick="this.value=\'\'; openDatePicker(document.getElementsByName(\'' + field + '\')[0])" width="16" height="15" border="0" style="padding-right: 0;" ><\/a>');
}