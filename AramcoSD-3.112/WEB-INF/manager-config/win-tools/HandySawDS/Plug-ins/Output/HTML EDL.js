//-------------------------------------------------------------------------------
// Output plugin for HandySaw DS
// (C) Davis Software, 2009
//
// FileName: HTML EDL.js
// Version: 2 
//
// Description: Creates EDL list in  HTML format
//
// Scale variable  determines view picture size in document.
// For example if Scale=2, then pictures will be shown in half size.
//
//-------------------------------------------------------------------------------
    var Str, k, OutFolder, ShortOutFolder, Rate, TCType, Scale, ScenesPerPage;
    var Columns = new Array(8);
    
    Columns[0] = DInfo.VariableValue("Columns0");
    Columns[1] = DInfo.VariableValue("Columns1");
    Columns[2] = DInfo.VariableValue("Columns2");
    Columns[3] = DInfo.VariableValue("Columns3");
    Columns[4] = DInfo.VariableValue("Columns4");
    Columns[5] = DInfo.VariableValue("Columns5");
    Columns[6] = DInfo.VariableValue("Columns6");
    Columns[7] = DInfo.VariableValue("Columns7");
    
    Scale = parseFloat(DInfo.VariableValue("Scale"));
    if (Scale == NaN)
    {
    	Scale = 8;
    }
    
    ScenesPerPage = parseInt(DInfo.VariableValue("ScenesPerPage"));
    if (ScenesPerPage == NaN)
    {
    	ScenesPerPage = 100;
    }
    
    Rate = DInfo.FrameRate;
    TCType = 1;
    Str = "";
    OutFolder = DInfo.DefaultReportName.slice(0, -4) + "_files\\";
    ShortOutFolder = GetFileName(DInfo.DefaultReportName.slice(0, -4)) + "_files\\";
    DInfo.ProgressMax = DInfo.ScenesCount;
    if (DInfo.ScenesCount <= ScenesPerPage)
    {
        CreateReport(-1);
    }
    else
    {
        for (k = 0; k < (DInfo.ScenesCount / ScenesPerPage) && !DInfo.Abort; k++)
        {
            CreateReport(k);
        }
    } 
    
    DInfo.Report = "";
    DInfo.ReportName = "";

//-------------------------------------------------------------------------------
function CreateReport(PageIndex)
{
    var Str, TmpStr, i, j, MaxIndex;
    var StartIndex;
    var PageCount;

    StartIndex = PageIndex * ScenesPerPage;
    if (StartIndex < 0)
    {
        StartIndex = 0;
    }

    PageCount = Math.ceil(DInfo.ScenesCount / ScenesPerPage); 
    
    Str = "<html>\r\n";
    Str += "<head>\r\n";
    Str += "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\r\n";
    Str += "</head>\r\n";
    Str += "<body>\r\n";

//    Str += "<p>File: <a href=\"" + DInfo.VideoName + "\" target=\"blank\">" + DInfo.VideoName + "</a></p>\r\n"; 
    Str += "<p>File: " + DInfo.VideoName + "</p>\r\n"; 
    if (PageCount > 1)
    {
        Str += "<p>Pages: ";
        for (i = 0; i < PageCount; i++)
        {
            if (i == PageIndex)
            {
                Str += (i + 1) + "  ";
                continue;
            }
            Str += "<a href=\"" + CreateFileName(i + 1) +"\">" + (i + 1) + "</a>  ";
        }
        Str += "</p>\r\n"
    }
    Str += "<table border=\"1\" cellpadding=\"0\" cellspacing=\"0\">\r\n";
    Str += "<THEAD>\r\n\<TR>\r\n";
    
    for (i = 0; i < Columns.length; i++)
    {
    	Str += "<TH>" + Columns[i] + "</TH>\r\n";
    }
    Str += "</TR>\r\n\</THEAD>\r\n<TBODY>\r\n";
    
    
    MaxIndex = StartIndex + ScenesPerPage;
    for (i = StartIndex; i < DInfo.ScenesCount && !DInfo.Abort && (i < MaxIndex); i++)
    {
    	DInfo.ProgressPosition = (i + 1);
    	Str += "<tr>\r\n";
    	//N
    	Str += "<td>" + (i + 1) + ".</td>\r\n";
    	//Scene
    	Str += "<td>" + DInfo.ScenesName(i);
    	Str += "\r\n<br>" + "Hr.: "
    		+ DInfo.Frame2Time(DInfo.ScenesStop(i) - DInfo.ScenesStart(i) + 1, Rate, TCType);
    	Str += "\r\n<br>" + "TC: "
    		+ DInfo.Frame2Time(DInfo.TapeStartTimeCode + DInfo.ScenesStart(i), Rate, TCType)
    		+ ".</td>\r\n";
    	//FirstFrame
    	DInfo.SaveJPEG(DInfo.ScenesStart(i), OutFolder + DInfo.ScenesStart(i) + ".jpg", DInfo.Width, DInfo.Height, 100);
    	Str += "<td>";
    	Str += "<a href=\"" + ShortOutFolder + DInfo.ScenesStart(i) + ".jpg" + "\" target=\"blank\">";
    	Str += "<img src=\"" + ShortOutFolder + DInfo.ScenesStart(i) + ".jpg" + "\" width=\""
    		+ DInfo.Width / Scale + "\" height=\"" + DInfo.Height / Scale + "\"></a></td>\r\n";
    	//Markers
    	Str += "<td>";
    	j = DInfo.ScenesStart(i);
    	TmpStr = "";
    
    	while ((j = FindMarker(i, j + 1)) != -1)
    	{
    		DInfo.SaveJPEG(j, OutFolder + j + ".jpg", DInfo.Width, DInfo.Height, 100);
    		TmpStr += "<img src=\"" + ShortOutFolder + j + ".jpg" + "\" width=\"" + DInfo.Width / Scale
    			+ "\" height=\"" + DInfo.Height / Scale + "\">&nbsp;\r\n";
    	}
    
    	if (TmpStr.length == 0)
    	{
    		Str += "&nbsp;";
    	}
    	else
    	{
    		Str += TmpStr;
    	}
    	Str += "</td>\r\n";
    	//LastFrame
    	DInfo.SaveJPEG(DInfo.ScenesStop(i), OutFolder + DInfo.ScenesStop(i) + ".jpg", DInfo.Width, DInfo.Height, 100);
    	Str += "<td>";
    	Str += "<a href=\"" + ShortOutFolder + DInfo.ScenesStop(i) + ".jpg" + "\" target=\"blank\">";
    	Str += "<img src=\"" + ShortOutFolder + DInfo.ScenesStop(i) + ".jpg" + "\" width=\""
    		+ DInfo.Width / Scale + "\" height=\"" + DInfo.Height / Scale + "\"></a></td>\r\n";
    	//Description
    	Str += "<td>" + "&nbsp;" + DInfo.ScenesComment(i) + "</td>\r\n";
    	//Sound
    	Str += "<td>" + "&nbsp;" + "</td>\r\n";
    	//Remarks
    	Str += "<td>" + "&nbsp;" + "</td>\r\n";
    
    	Str += "</tr>\r\n";
    }
    Str += "</TBODY></table>\r\n";
    Str += "</body>\r\n</html>\r\n";
    
    if (PageIndex == -1)
    {
        SaveToFile(DInfo.DefaultReportName.slice(0, -4) + ".html", Str);
    }
    else
    {
        SaveToFile(CreateFileName(PageIndex + 1), Str);
    }
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
function FindMarker(Scene, StartFrame)
{
	var i, retval;

	retval = -1;

	if (StartFrame < DInfo.ScenesStart(Scene))
	{
		return -1;
	}

	if (StartFrame > DInfo.ScenesStop(Scene))
	{
		return -1;
	}

	for (i = 0; i < DInfo.MarkersCount; i++)
	{
		if (DInfo.Markers(i) < StartFrame)
		{
			continue;
		}

		if (DInfo.Markers(i) > DInfo.ScenesStop(Scene))
		{
			break;
		}
		retval = DInfo.Markers(i);
		break;
	}
	return retval;
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
function CreateFileName(Index)
{
	return DInfo.DefaultReportName.slice(0, -4) + "." + Index + ".html";
}
//-------------------------------------------------------------------------------
