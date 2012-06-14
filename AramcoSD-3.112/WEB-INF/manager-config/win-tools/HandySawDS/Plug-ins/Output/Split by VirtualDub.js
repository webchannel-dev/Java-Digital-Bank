//-------------------------------------------------------------------------------
// Output plugin for HandySaw DS
// (C) Davis Software, 2006
//
//	Version: 1.05
//
// Info:
// This script generates .syl file for VirtualDub (www.virtualdub.org).
// Executing this .syl script, VirtualDub will create a separate AVI file for each scene
// in "Direct Copy" mode.
//
//
// 1. RunVD=false
//	This plugin only creates .syl file for VirtualDub. Then you must run VirtualDub,
//	choose command "Run Script..." and select .syl file.
// 2. RunVD=true
//	In this case plugin creates .syl file and executes VirtualDub with this .syl file
//	as parameter. You must be sure that variable VDApp is equal to full VirtualDub
//	application pathname.
//
//-------------------------------------------------------------------------------
var RunVD;
var VDApp = "C:\\Program Files\\VirtualDub\\VirtualDub.exe";
var ShowWindow;
//-------------------------------------------------------------------------------
    Split();
//------------------------------------------------------------------------------
function Split()
{
    var Str, i, scenes, Report, folder;
    var SetAudioSrc;
    
    //Get user settings
    Str = DInfo.VariableValue("VDPath");
    
    if (Str != '')
    {
    	VDApp = Str;
    }
    
    if (!CheckApplicationExistence(VDApp))
    {
        return;
    }
        
    RunVD = true;
    Str = DInfo.VariableValue("WhatToDo");
    
    if (Str == "Only generate .SYL file")
    {
    	RunVD = false;
    }
    
    ShowWindow = 1;
    Str = DInfo.VariableValue("Show");
    
    if (Str == "No")
    {
    	ShowWindow = 0;
    }
    
    Report = "";
    Report += "VirtualDub.Open(\"";
    Report += DoubleSlashes(DInfo.VideoName);
    Report += "\",\"\",0);\r\n";
    
    if (DInfo.Audio && DInfo.ExternalAudio)
    { //External Audio in WAV file
    	SetAudioSrc = "VirtualDub.audio.SetSource(\"" + DoubleSlashes(DInfo.AudioName) + "\");\r\n";
    }
    else
    {
    	SetAudioSrc = "VirtualDub.audio.SetSource(1);\r\n";
    }
    Report += SetAudioSrc;
    Report
    	+= "VirtualDub.audio.SetMode(0);\r\n\
        VirtualDub.audio.SetInterleave(1,500,1,0,0);\r\n\
        VirtualDub.audio.SetClipMode(1,1);\r\n\
        VirtualDub.audio.SetConversion(0,0,0,0,0);\r\n\
        VirtualDub.audio.SetVolume();\r\n\
        VirtualDub.audio.SetCompression();\r\n\
        VirtualDub.audio.EnableFilterGraph(0);\r\n\
        VirtualDub.video.SetDepth(24,24);\r\n\
        VirtualDub.video.SetMode(0);\r\n\
        VirtualDub.video.SetFrameRate(0,1);\r\n\
        VirtualDub.video.SetIVTC(0,0,-1,0);\r\n\
        VirtualDub.video.SetRange(0,0);\r\n\
        VirtualDub.video.SetCompression();\r\n\
        VirtualDub.video.filters.Clear();\r\n\
        VirtualDub.audio.filters.Clear();\r\n";
    folder = GetFilePath(DInfo.DefaultReportName) + GetFileName(DInfo.VideoName) + ".scenes";
    DInfo.ProgressMax = DInfo.ScenesCount;
    
    for (i = 0; i < DInfo.ScenesCount && !DInfo.Abort; i++)
    {
    	DInfo.ProgressPosition = i + 1;
    
    	if (DInfo.ExternalAudio)
    	{
    		Report += SetAudioSrc;
    	}
    	Report += "VirtualDub.subset.Clear();\r\n";
    	Report += "VirtualDub.subset.AddRange(";
    	Report += DInfo.ScenesStart(i) + "," + (DInfo.ScenesStop(i) - DInfo.ScenesStart(i) + 1)
    		+ ");\r\n";
    
    	if (DInfo.ExternalAudio)
    	{
    		Report += "VirtualDub.SaveWAV(\"";
    		Str = folder + "\\";
    		Str += DInfo.ScenesName(i) + ".wav";
    		Report += DoubleSlashes(Str);
    		Report += "\");\r\n";
    		Report += "VirtualDub.audio.SetSource(0);\r\n";
    	}
    	Report += "VirtualDub.SaveAVI(\"";
    	Str = folder + "\\";
    	Str += DInfo.ScenesName(i) + ".avi";
    	Report += DoubleSlashes(Str);
    	Report += "\");\r\n";
    }
    
    if (!DInfo.Abort)
    {
    
    	Report += "VirtualDub.Close();";
    
    	var fso = new ActiveXObject("Scripting.FileSystemObject");
    	var WshShell = new ActiveXObject("WScript.Shell");
    
    	if (!fso.FolderExists(folder))
    	{
    		fso.CreateFolder(folder);
    	}
    
    	DInfo.Report = Report;
    	DInfo.ReportName = DInfo.DefaultReportName.slice(0, -4) + ".syl";
    
    	if (RunVD)
    	{
    		var file = fso.CreateTextFile(DInfo.ReportName, true);
    		Str = "\"" + VDApp + "\" /s\"" + DInfo.ReportName + "\" /x";
    		file.Write(Report);
    		file.Close();
    		UnblockFile(VDApp);
    		WshShell.Run(Str, ShowWindow, true);
    		fso.DeleteFile(DInfo.ReportName);
    		DInfo.ReportName = "";
    	}
    }
}
//-------------------------------------------------------------------------------
function DoubleSlashes(String)
{
	var Str, re;
	re = /\\/g;
	Str = String.replace(re, "\\\\");
	return Str;
}

//-------------------------------------------------------------------------------
function GetFilePath(String)
{
	var Str, pos;
	pos = String.lastIndexOf("\\");
	Str = String.substr(0, pos + 1);
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
function UnblockFile(FileName)
{
	fso = new ActiveXObject("Scripting.FileSystemObject");

	try
	{
		f1 = fso.OpenTextFile(
			FileName + ':Zone.Identifier', 2); // If the Zone Identifier does not exist ..
		f1.Close();
	}
	catch (e){}
}
//-------------------------------------------------------------------------------
function CheckApplicationExistence(FileName)
{
	var fso = new ActiveXObject("Scripting.FileSystemObject");
    if (fso.FileExists(FileName))
    {
        return true;
    }
    var WshShell = new ActiveXObject("WScript.Shell");
    var Str = "Error!\r\nCannot find file \"" + FileName + "\"\r\n";
    Str = Str + "\r\nMost likely you have not set up this plug-in properly\r\n";
    Str = Str + "Please do this:\r\n";
    Str = Str + "- download VirtualDub from www.virtualdub.org\r\n";
    Str = Str + "- extract files from archive in some folder\r\n";
    Str = Str + "- run VirtualDub.exe once, agree with license and close it\r\n";
    Str = Str + "- run HandySaw DS, open Preferences, go to Plug-ins tab, select \"Split by VirtualDub.js\" plug-in\r\n";
    Str = Str + "and set up it's parameters, at least \"Full pathname\"\r\n";
    
    WshShell.Popup(Str);
    return false;
}
//-------------------------------------------------------------------------------
