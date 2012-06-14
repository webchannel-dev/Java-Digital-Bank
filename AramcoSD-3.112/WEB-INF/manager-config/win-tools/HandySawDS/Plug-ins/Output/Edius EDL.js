//-------------------------------------------------------------------------------
// Output plugin for HandySaw DS
// (C) Davis Software, 2005
//
// FileName: Edius EDL.js
//
// Generates Edius EDL file
//
// Be sure to check "Handle black clips as empty clips"
// in EDL Importer Detailed Settings in Edius before importing multipart EDLs
//-------------------------------------------------------------------------------
    var Str, j, CurFrame, Rate, TCType, n, ClipType, MAXCUTS;
    
    MAXCUTS = 998;
    CurFrame = 0;
    Rate = DInfo.FrameRate;
    TCType = 1;
    ClipType = "V";
    
    if (DInfo.Audio)
    { //There is audio channel
    	ClipType = "B";
    }
    DInfo.ProgressMax = DInfo.ScenesCount;
    
    for (j = 0; j < (DInfo.ScenesCount / MAXCUTS) && !DInfo.Abort; j++)
    {
    	Str = CreateEDL(j * MAXCUTS);
    
    	if (DInfo.ScenesCount <= MAXCUTS)
    	{
    		SaveToFile(DInfo.DefaultReportName.slice(0, -4) + ".EDL", Str);
    	}
    	else
    	{
    		SaveToFile(DInfo.DefaultReportName.slice(0, -4) + "." + (j + 1) + ".EDL", Str);
    	}
    }
    
    DInfo.Report = "";
    DInfo.ReportName = "";

//-------------------------------------------------------------------------------
function CreateEDL(Start)
{
	var Str, i, nn, delta;
	Str = "";
	Str += "@CREATED BY HandySaw DS FOR EDIUS (CMX-3600)\r\n";
	Str += "TITLE: " + RemoveSpaces(GetFileName(DInfo.VideoName)) + "\r\n\r\n";
	Str += "-------------------------------------------------------------------------------\r\n";

	if (Rate.toString(10).substring(0, 5) == "29.97")
	{
		Str += "FCM: NON-DROP FRAME\r\n";
	}
	delta = 0;

	if (CurFrame > 0)
	{
		delta = 1;
		Str += "001";
		Str += " " + "BLK";
		Str += " " + ClipType + " C        ";
		Str += DInfo.Frame2Time(0, Rate, TCType);
		Str += " " + DInfo.Frame2Time(CurFrame, Rate, TCType);
		Str += " " + DInfo.Frame2Time(0, Rate, TCType);
		Str += " " + DInfo.Frame2Time(CurFrame, Rate, TCType);
		Str += "\r\n";
	}

	for (i = Start; i < (Start + MAXCUTS) && i < DInfo.ScenesCount && !DInfo.Abort; i++)
	{
		DInfo.ProgressPosition = i + 1;
		nn = i + 1 - Start + delta;

		for (n = 0; n < (3 - nn.toString(10).length); n++)
		{
			Str += "0";
		}
		Str += nn;
		Str += " " + RemoveSpaces(GetFileName(DInfo.VideoName));
		Str += " " + ClipType + " C        ";
		Str += DInfo.Frame2Time(DInfo.ScenesStart(i), Rate, TCType);
		Str += " " + DInfo.Frame2Time(DInfo.ScenesStop(i) + 1, Rate, TCType);
		Str += " " + DInfo.Frame2Time(CurFrame, Rate, TCType);
		CurFrame += (DInfo.ScenesStop(i) - DInfo.ScenesStart(i) + 1);
		Str += " " + DInfo.Frame2Time(CurFrame, Rate, TCType);
		Str += "\r\n";
	}
	Str += "========================\r\n";

	return Str;
}

//-------------------------------------------------------------------------------
function GetFileName(String)
{
	var Str, pos;
	pos = String.lastIndexOf("\\");
	Str = String.substr(pos + 1);
	return Str;
}

//-------------------------------------------------------------------------------
function RemoveSpaces(String)
{
	var Str, re;
	re = / /g;
	Str = String.replace(re, "");
	return Str;
}

//-------------------------------------------------------------------------------
function SaveToFile(FileName, String)
{
	var fso = new ActiveXObject("Scripting.FileSystemObject");
	var file = fso.CreateTextFile(FileName, true);
	file.Write(String);
	file.Close();
	return;
}
//-------------------------------------------------------------------------------
