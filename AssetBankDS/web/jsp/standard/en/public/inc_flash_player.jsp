	<logic:notPresent name="autoplay">
		<bean:define id="autoplay" value="true"/>
	</logic:notPresent>
	<logic:notPresent name="marginTop">
		<bean:define id="marginTop" value="0"/>
	</logic:notPresent>
	
	<bright:applicationSetting id="scaleVideoPreviews" settingName="scale-video-previews"/>
	
	<div style="margin-top:<c:out value='${marginTop}'/>px; <c:if test="${floatFlashPlayerLeft}">float: left;</c:if>" >
		<div id="flashcontent">
		<h2>Media not supported</h2>
		<p>You will require the <a href="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" target="_blank">flash plugin</a> to be installed and Javascript enabled for this page to work.</p>
		</div>
	</div> 

	<script type="text/javascript">
		// <![CDATA[
 		var flashvars = {};
		flashvars.file = "<%= request.getContextPath() %>/servlet/display?file=<bean:write name='file'/>";
		flashvars.image = "<c:out value='${splashImage}'/>";
		flashvars.autostart = "<c:out value='${autoplay}'/>";
		flashvars.stretching = "<c:choose><c:when test="${scaleVideoPreviews}">exactFit</c:when><c:otherwise>uniform</c:otherwise></c:choose>";
		flashvars.provider = "video";
		flashvars.backcolor = "000000";
		flashvars.frontcolor = "EEEEEE";
		flashvars.lightcolor = "FFFFFF";
		var params = {};
		params.allowfullscreen = "true";
		params.allowscriptaccess = "sameDomain";
		var attributes = {};
		swfobject.embedSWF("../tools/jwplayer/player.swf", "flashcontent", "<bean:write name='width'/>", "<bean:write name='height'/>", "9.0.0", false, flashvars, params, attributes);
		// ]]>
	</script>  
	
	


	
	
 