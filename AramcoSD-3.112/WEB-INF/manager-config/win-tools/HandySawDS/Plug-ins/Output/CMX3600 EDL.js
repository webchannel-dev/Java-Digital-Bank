//-------------------------------------------------------------------------------
// Output plugin for HandySaw DS
// (C) Davis Software, 2005
//
// FileName: CMX3600 EDL.js
//
// Generates CMX3600 EDL file as it does Adobe Premiere Pro 1.5
//
//-------------------------------------------------------------------------------
//
// If audio is in external WAV file and your NLE system cannot
// support it byself set HandleWAV to true.
// If your NLE automatically loads external WAV with video set it to false.
//
var ProcessWAV = true;
//-------------------------------------------------------------------------------
var Str, i, CurFrame, LastFrame, Rate, TCType, n, nn, ClipType, ClipType2;
//-------------------------------------------------------------------------------
    CurFrame = 0;
    Str = "";
    Str += "TITLE: " + GetFileName(DInfo.VideoName) + "\r\n\r\n";
    Rate = DInfo.FrameRate;
    TCType = 1;
    ClipType = "V";
    ClipType2 = "";
    
    if (DInfo.Audio)
    {     //There is audio channel
    	if (DInfo.ExternalAudio && ProcessWAV)
    	{ //Audio is in external WAV and we have to show it explicitly
    		ClipType = "V";
    
    		if (DInfo.AudioChannels == 1)
    		{
    			ClipType2 = "A";
    		}
    
    		if (DInfo.AudioChannels == 2)
    		{
    			ClipType2 = "AA";
    		}
    	}
    	else
    	{
    		if (DInfo.AudioChannels == 1)
    		{
    			ClipType = "B";
    		}
    
    		if (DInfo.AudioChannels == 2)
    		{
    			ClipType = "AA/V";
    		}
    	}
    }
    DInfo.ProgressMax = DInfo.ScenesCount;
    
    for (i = 0, nn = 1; i < DInfo.ScenesCount && !DInfo.Abort; i++, nn++)
    {
    	for (n = 0; n < (3 - nn.toString(10).length); n++)
    	{
    		Str += "0";
    	}
    	Str += nn;
    	Str += " " + GetReelName(GetFileName(DInfo.VideoName));
    	Str += " " + ClipType + " C        ";
    	Str += DInfo.Frame2Time(DInfo.ScenesStart(i), Rate, TCType);
    	Str += " " + DInfo.Frame2Time(DInfo.ScenesStop(i) + 1, Rate, TCType);
    	Str += " " + DInfo.Frame2Time(CurFrame, Rate, TCType);
    	LastFrame = CurFrame + (DInfo.ScenesStop(i) - DInfo.ScenesStart(i) + 1);
    	Str += " " + DInfo.Frame2Time(LastFrame, Rate, TCType);
    	Str += "\r\n";
    	Str += "* FROM CLIP NAME:  ";
    	Str += GetFileName(DInfo.VideoName) + "\r\n";
    	DInfo.ProgressPosition = i + 1;
    
    	if (ClipType2 != "")
    	{ // If we must to show WAV file
    		nn++;
    
    		for (n = 0; n < (3 - nn.toString(10).length); n++)
    		{
    			Str += "0";
    		}
    		Str += nn;
    		Str += " " + GetReelName(GetFileName(DInfo.AudioName));
    		Str += " " + ClipType2 + " C        ";
    		Str += DInfo.Frame2Time(DInfo.ScenesStart(i), Rate, TCType);
    		Str += " " + DInfo.Frame2Time(DInfo.ScenesStop(i) + 1, Rate, TCType);
    		Str += " " + DInfo.Frame2Time(CurFrame, Rate, TCType);
    		LastFrame = CurFrame + (DInfo.ScenesStop(i) - DInfo.ScenesStart(i) + 1);
    		Str += " " + DInfo.Frame2Time(LastFrame, Rate, TCType);
    		Str += "\r\n";
    		Str += "* FROM CLIP NAME:  ";
    		Str += GetFileName(DInfo.AudioName) + "\r\n";
    	}
    	CurFrame = LastFrame;
    }
    
    if (!DInfo.Abort)
    {
    	DInfo.Report = Str;
    	DInfo.ReportName = DInfo.DefaultReportName.slice(0, -4) + ".EDL";
    }

//===================================
function GetFileName(String)
{
	var Str, pos;
	pos = String.lastIndexOf("\\");
	Str = String.substr(pos + 1);
	return Str;
}

//===================================
// Reel name must be alphanumeric and 8 symbols length max
//===================================
function GetReelName(FileName)
{
	var Str, re;
	re = /\W|_/g;
	Str = FileName.replace(re, "A"); //replacing all bad symbols with "A"
	Str = Str.toUpperCase();
	Str = Str.substring(0, 8);
	return Str;
}
//===================================
