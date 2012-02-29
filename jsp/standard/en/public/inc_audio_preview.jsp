<object id="MediaPlayer" style="height:145px;" classid="CLSID:22D6f312-B0F6-11D0-94AB-0080C74C7E95" standby="Loading Windows Media Player components..." type="application/x-oleobject" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,7,1112"> 

	<param name="filename" value="../servlet/display?file=<bean:write name='file'/>"/>
	<param name="Showcontrols" value="True"/>
	<param name="autoStart" value="False"/>
	<param name="WindowlessVideo" value="1"/>
	<param name="AudioStream" value="1"/>
	<param name="ShowDisplay" value="0"/> 
	<param name="currentPosition" value="1"/> 
	<param name="uiMode" value="full" /> 

	<embed src="../servlet/display?file=<bean:write name='file'/>&contentType=<bean:write name='contentType'/>" windowlessVideo="true" audioStream="true" showDisplay="false" currentPosition="true" autostart="false"  style="height:45px"></embed>
</object>

<br /><br />
