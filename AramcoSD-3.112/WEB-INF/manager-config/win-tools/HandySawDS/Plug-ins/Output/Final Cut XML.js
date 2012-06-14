//-------------------------------------------------------------------------------
//  Output plugin for HandySaw DS
//  (C) Davis Software, 2009
//
//	File Name: Final Cut XML.js
//	Version: 0.10
//
//  Generates Final Cut XML file
//
//------------------------------------------------------------------------------
    var Str, StrV, StrA, i, j, CurFrame, FileRate, gl_timebase, gl_ntsc, gl_realrate, fix, IsEDIUS;
    
    CurFrame = 0;
    Rate = DInfo.FrameRate;
    
    DInfo.ProgressMax = DInfo.ScenesCount;
    DInfo.ProgressPosition = 0;
    
    FileRate = DInfo.FrameRate;
    FileName = DInfo.VideoName;
    
    IsEDIUS = false;
    
    if (DInfo.VariableValue("TargetHost") == "EDIUS")
    {
        IsEDIUS = true;    
    }
    
    if (DInfo.VariableValue("TargetOS") != "Windows")
    {
        //  Reformat filename
        var WinPre = DInfo.VariableValue("WinPrefix");
        var MacPre = DInfo.VariableValue("MacPrefix");
        if (FileName.substr(0, WinPre.length) == WinPre)
        {
            FileName = MacPre + FileName.substr(WinPre.length);
            var re = /\\/g;
	        FileName = FileName.replace(re, "/"); //replacing all \ with /  
            re = /\s/g;
	        FileName = FileName.replace(re, "\%20"); //replacing all spaces with %20  
        }
    }
    
    Str = '<?xml version="1.0" encoding="UTF-8"?>\n\
            <!DOCTYPE xmeml>\n\
            <xmeml version="4">\n\
            <sequence id="' + GetFileName(DInfo.VideoName) + '">\n\
            <name>' + GetFileName(DInfo.VideoName) + '</name>\n\
            <in>-1</in>\n\
            <out>-1</out>\n';
    
    //  set sequence rate
    gl_ntsc    = "FALSE";
    switch(DInfo.VariableValue("SequenceRate"))
    {
        case "24P(23.97)":
            gl_timebase = "24";
            gl_ntsc     = "TRUE";
            gl_realrate = 23.976;
            break;
        case "Film(24)":
            gl_timebase = "24";
            gl_realrate = 24.;
            break;
        case "PAL(25)":
            gl_timebase = "25";
            gl_realrate = 25.;
            break;
        case "NTSC(29.97)":
            gl_timebase = "30";
            gl_ntsc     = "TRUE";
            gl_realrate = 29.97;
            break;
        case "NTSC(30)":
            gl_timebase = "30";
            gl_realrate = 30.;
            break;
        case "50":
            gl_timebase = "50";
            gl_realrate = 50.;
            break;
        case "59.94":
            gl_timebase = "60";
            gl_ntsc     = "TRUE";
            gl_realrate = 59.94;
            break;
        case "60":
            gl_timebase = "60";
            gl_realrate = 60.;
            break;
        
        case "As file":
        default:
            gl_realrate = FileRate;
            gl_timebase = new Number(DInfo.FrameRate.toString(10).substring(0,5));
            if (gl_timebase != Math.floor(gl_timebase))
            {
                gl_timebase = Math.floor(gl_timebase + 0.5);
                gl_ntsc     = "TRUE";
            }
            gl_timebase = String(gl_timebase);
    }
    
    
    Str += '<rate>\n\
            <ntsc>' + gl_ntsc + '</ntsc>\n\
            <timebase>' + gl_timebase + '</timebase>\n\
            </rate>\n';

    //  Sequence duration
    var TotalFrames = 0;
    for (i = 0; i < DInfo.ScenesCount && !DInfo.Abort; i++)
    {
        TotalFrames += (DInfo.ScenesStop(i) - DInfo.ScenesStart(i) + 1);
    }    
    if (IsEDIUS)
    {
        TotalFrames--;  
    }
    Str += '<duration>' + ConvertFrame(TotalFrames) + '</duration>\n';
    
    // set sequence video format
    Str += '<media>\n\
            <video>\n\
            <format>\n\
            <samplecharacteristics>\n\
            <width>' + DInfo.Width + '</width>\n\
            <height>' + DInfo.Height + '</height>\n\
            <rate>\n\
            <ntsc>' + gl_ntsc + '</ntsc>\n\
            <timebase>' + gl_timebase + '</timebase>\n\
            </rate>\n\
            <pixelaspectratio>square</pixelaspectratio>\n\
            <fielddominance>none</fielddominance>\n\
            <codec>\n\
            <name>Apple Desktop</name>\n\
            </codec>\n\
            </samplecharacteristics>\n\
            </format>\n\
            <track>\n';        
    
        //  video
    StrV = '';
    CurFrame = 0;
    fix = 0;
    for (i = 0; i < DInfo.ScenesCount && !DInfo.Abort; i++)
    {
        if (i == DInfo.ScenesCount - 1 && IsEDIUS)
        {
            //  last scene fix for EDIUS
            fix = 1;
        }
        CurEnd = CurFrame + (DInfo.ScenesStop(i) - DInfo.ScenesStart(i) + 1);
        StrV += '<clipitem id="Clip ' + (i + 1) + '">\n\
                <name>' + DInfo.ScenesName(i) + '</name>\n\
                <rate>\n\
                <ntsc>' + gl_ntsc + '</ntsc>\n\
                <timebase>' + gl_timebase + '</timebase>\n\
                </rate>\n\
                <subclipinfo>\n\
                    <startoffset>' + ConvertFrame(DInfo.ScenesStart(i)) + '</startoffset>\n\
                    <endoffset>' + (ConvertFrame(DInfo.ScenesStop(i) + 1) - fix) + '</endoffset>\n\
                </subclipinfo>\n\
                <duration>' + (ConvertFrame(DInfo.ScenesStop(i) - DInfo.ScenesStart(i) + 1) - fix) + '</duration>\n';
                
        if (IsEDIUS)
        {
            StrV += '<in>' + ConvertFrame(DInfo.ScenesStart(i)) + '</in>\n\
                    <out>' + (ConvertFrame(DInfo.ScenesStop(i) + 1) - fix) + '</out>\n';
        }
        else
        {
            StrV += '<in>0</in>\n\
                    <out>' + (ConvertFrame(DInfo.ScenesStop(i) - DInfo.ScenesStart(i) + 1) - fix) + '</out>\n';
        }
        StrV +='<start>' + ConvertFrame(CurFrame) + '</start>\n\
                <end>' + (ConvertFrame(CurEnd) - fix) + '</end>\n';

        //  markers
    	j = DInfo.ScenesStart(i) - 1;

    	while ((j = FindMarker(i, j + 1)) != -1)
    	{
            var pos = j - DInfo.ScenesStart(i);
            if (IsEDIUS)
            {
                pos = j;
            }

    	   StrV += '<marker>\n\
                    <name>unnamed</name>\n\
                    <in>' + ConvertFrame(pos) + '</in>\n\
                    <out>' + ConvertFrame(pos) + '</out>\n\
                    </marker>\n';    	   
    	}
        

		if (i == 0)
		{
            ntsc     = "FALSE";
            timebase = new Number(DInfo.FrameRate.toString(10).substring(0,5));
            if (timebase != Math.floor(timebase))
            {
                timebase = Math.floor(timebase + 0.5);
                ntsc     = "TRUE";
            }
            timebase = String(timebase);
           		
            StrV += '<file id="TheFile">\n\
                    <name>' + GetFileName(DInfo.VideoName) + '</name>\n\
                    <pathurl>' + FileName + '</pathurl>\n\
                    <rate>\n\
                    <ntsc>' + ntsc + '</ntsc>\n\
                    <timebase>' + timebase + '</timebase>\n\
                    </rate>\n\
                    <duration>' + DInfo.FramesCount + '</duration>\n\
                    <media>\n\
                    <video>\n\
                    <samplecharacteristics>\n\
                    <width>' + DInfo.Width + '</width>\n\
                    <height>' + DInfo.Height + '</height>\n\
                    </samplecharacteristics>\n\
                    </video>\n';

            if (DInfo.Audio)
            {
                StrV += '<audio>\n\
                        <samplecharacteristics>\n\
                        <samplerate>' + DInfo.AudioFreq + '</samplerate>\n\
                        <depth>' + DInfo.AudioBits + '</depth>\n\
                        </samplecharacteristics>\n\
                        <channelcount>' + DInfo.AudioChannels + '</channelcount>\n\
                        </audio>\n';
            }

            StrV += '</media>\n\
                    </file>\n';
		}
		else
		{
            StrV += '<file id="TheFile"/>\n';		      
		}

        StrV += '<sourcetrack>\n\
                <mediatype>video</mediatype>\n\
                </sourcetrack>\n\
                <link>\n\
                <linkclipref>Clip ' + (i + 1) + '</linkclipref>\n\
                <mediatype>video</mediatype>\n\
                <trackindex>1</trackindex>\n\
                <clipindex>' + (i + 1) + '</clipindex>\n\
                </link>\n';

	    if (DInfo.Audio)
        {
            for (j = 0; j < DInfo.AudioChannels; j++)
            {

                StrV += '<link>\n\
                        <linkclipref>ClipA' + (j + 1) + ' ' + (i + 1) + '</linkclipref>\n\
                        <mediatype>audio</mediatype>\n\
                        <trackindex>' + (j + 1) + '</trackindex>\n\
                        <clipindex>' + (i + 1) + '</clipindex>\n\
                        </link>\n';
			}
        }
        StrV += '<logginginfo>\n\
                <scene>' + DInfo.ScenesComment(i) + '</scene>\n\
                </logginginfo>\n\
                </clipitem>\n';
    
        CurFrame = CurEnd;
        DInfo.ProgressPosition = i + 1;
    }
    
    Str += StrV;
    Str += '<enabled>TRUE</enabled>\n\
            <locked>FALSE</locked>\n\
            </track>\n\
            </video>\n';


    //  Audio
    StrA = '';
    if (DInfo.Audio)
    {
        for (j = 0; j < DInfo.AudioChannels; j++)
        {   
            CurFrame = 0;
            StrA += '<track>\n';
            for (i = 0; i < DInfo.ScenesCount && !DInfo.Abort; i++)
            {                 
                fix = 0;
                if (i == DInfo.ScenesCount - 1 && IsEDIUS)
                {
                    //  last scene fix for EDIUS
                    fix = 1;
                }
                CurEnd = CurFrame + (DInfo.ScenesStop(i) - DInfo.ScenesStart(i) + 1);
                StrA += '<clipitem id="ClipA' + (j + 1) + ' ' + (i + 1) + '">\n\
                        <name>' + DInfo.ScenesName(i) + '</name>\n\
                        <rate>\n\
                        <ntsc>' + gl_ntsc + '</ntsc>\n\
                        <timebase>' + gl_timebase + '</timebase>\n\
                        </rate>\n\
                        <subclipinfo>\n\
                            <startoffset>' + ConvertFrame(DInfo.ScenesStart(i)) + '</startoffset>\n\
                            <endoffset>' + (ConvertFrame(DInfo.ScenesStop(i) + 1) - fix) + '</endoffset>\n\
                        </subclipinfo>\n\
                        <duration>' + (ConvertFrame(DInfo.ScenesStop(i) - DInfo.ScenesStart(i) + 1) - fix) + '</duration>\n';
                        
                if (IsEDIUS)
                {
                    StrA += '<in>' + ConvertFrame(DInfo.ScenesStart(i)) + '</in>\n\
                            <out>' + (ConvertFrame(DInfo.ScenesStop(i) + 1) - fix) + '</out>\n';
                }
                else
                {
                    StrA += '<in>0</in>\n\
                            <out>' + (ConvertFrame(DInfo.ScenesStop(i) - DInfo.ScenesStart(i) + 1) - fix) + '</out>\n';
                }
                StrA +='<start>' + ConvertFrame(CurFrame) + '</start>\n\
                        <end>' + (ConvertFrame(CurEnd) - fix) + '</end>\n\
                        <file id="TheFile"/>\n\
                        <sourcetrack>\n\
                        <mediatype>audio</mediatype>\n\
                        <trackindex>' + (j + 1) + '</trackindex>\n\
                        </sourcetrack>\n\
                        <link>\n\
                        <linkclipref>Clip ' + (i + 1) + '</linkclipref>\n\
                        <mediatype>video</mediatype>\n\
                        <trackindex>1</trackindex>\n\
                        <clipindex>' + (i + 1) + '</clipindex>\n\
                        </link>\n';

                for (k = 0; k < DInfo.AudioChannels; k++)
                {
                    StrA += '<link>\n\
                            <linkclipref>ClipA' + (k + 1) + ' ' + (i + 1) + '</linkclipref>\n\
                            <mediatype>audio</mediatype>\n\
                            <trackindex>' + (k + 1) + '</trackindex>\n\
                            <clipindex>' + (i + 1) + '</clipindex>\n\
                            </link>\n';
                }
                    StrA += '</clipitem>\n';
                CurFrame = CurEnd;                    
            }
            StrA += '<enabled>TRUE</enabled>\n\
                    <locked>FALSE</locked>\n\
                    <outputchannelindex>' + (j + 1) + '</outputchannelindex>\n\
                    </track>\n';
		}
    } 
    if (DInfo.Audio)
    {
        Str += '<audio>\n\
                <format>\n\
                <samplecharacteristics>\n\
                <samplerate>' + DInfo.AudioFreq + '</samplerate>\n\
                <depth>' + DInfo.AudioBits + '</depth>\n\
                </samplecharacteristics>\n\
                </format>\n\
                <outputs>\n\
                <group>\n\
                <index>1</index>\n\
                <numchannels>' + DInfo.AudioChannels + '</numchannels>\n\
                <downmix>0</downmix>\n';
        for (j = 0; j < DInfo.AudioChannels; j++)
        {          
            Str += '<channel>\n\
                    <index>' + (j + 1) + '</index>\n\
                    </channel>\n';
        }
            Str += '</group>\n\
        			</outputs>\n\
        			<in>-1</in>\n\
        			<out>-1</out>\n';
        Str += StrA;
        Str += '</audio>\n';
    }
    Str += '</media>\n\
            <ismasterclip>FALSE</ismasterclip>\n\
            </sequence>\n\
            </xmeml>\n';
   
    //  Own writing
    DInfo.Report = "";
    DInfo.ReportName = "";
    //  Using ADO object to encode into UTF-8 and write to text file
    ADOWriteFile(DInfo.DefaultReportName.slice(0, -4) + ".xml", "utf-8", FormatXML(Str));

//-------------------------------------------------------------------------------
function GetFileName(String)
{
	var Str, pos;
	pos = String.lastIndexOf("\\");
	Str = String.substr(pos + 1);
	return Str;
}
//-------------------------------------------------------------------------------
function ConvertFrame(Frame)
{
    if (FileRate == gl_realrate)
    {
        return Frame;
    }    
    var NewFrame = 0.;
    NewFrame = (Frame / FileRate) * gl_realrate;
    return Math.floor(NewFrame + 0.5);
}
//-------------------------------------------------------------------------------
function FormatXML(InStr)
{
    var Str, i, j;
    Strings = InStr.split('\n');
    // remove spaces at begin
    for (i = 0; i < Strings.length; i++)
    {
        for (j = 0; j < Strings[i].length - 1; j++)
        {
            if (Strings[i].charAt(j) != " " && Strings[i].charAt(j) != "\t")
            {
                break;
            } 
        }
        if (j >= Strings[i].length)
        {
            continue;
        }        
        Strings[i] = Strings[i].substr(j);
    }
    //  insert tabs
    Level = 0;
    Ident = '';
    for (i = 4; i < Strings.length; i++)
    {
        if (Strings[i].substr(0, 2) != '</')
        {
            Level++;
            Ident += "\t";
        }
        Strings[i] = Ident + Strings[i];
        if (Strings[i].indexOf('</') != -1 || Strings[i].indexOf('/>') != -1)
        {
            Level--;
            Ident = Ident.substr(1);
        }
    }
    Str = ''; 
    for (i = 0; i < Strings.length; i++)
    {
        Str += Strings[i] + '\n';
    }
    return Str;
}
//-------------------------------------------------------------------------------
function ADOWriteFile(Filename, Charset, FileText) 
{
    var Stream1 = new ActiveXObject("ADODB.Stream");
    Stream1.Type = 2; //text
    Stream1.Mode = 3;
    Stream1.Charset = Charset;
    Stream1.Open();
    Stream1.WriteText(FileText);
    Stream1.SaveToFile(Filename, 2);
    Stream1.Close();
    return;
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
