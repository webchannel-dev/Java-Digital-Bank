	<logic:notPresent name="autoplay">
		<bean:define id="autoplay" value="true"/>
	</logic:notPresent>
	<logic:notPresent name="marginTop">
		<bean:define id="marginTop" value="0"/>
	</logic:notPresent>
	<logic:notPresent name="marginBottom">
		<bean:define id="marginBottom" value="10"/>
	</logic:notPresent>
	<bright:applicationSetting id="scaleVideoPreviews" settingName="scale-video-previews"/>
	
	<script type='text/javascript' src='../js/lib/jwplayer.js'></script>  
	
	<div style="margin-top:<c:out value='${marginTop}'/>px; margin-bottom: <c:out value='${marginBottom}'/>px; <c:if test="${floatFlashPlayerLeft}">float: left;</c:if>" >
		<div id="flashcontent">
		<h2>Media not supported</h2>
		<p>You will require the <a href="http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash" target="_blank">flash plugin</a> to be installed and Javascript enabled for this page to work.</p>
		</div>
	</div> 
                                                                                       
               
	
	<script type="text/javascript">
		$j(function(){
			jwplayer('flashcontent').setup({
				'flashplayer' : '../tools/jwplayer/player.swf',
				'file' : '<%= request.getContextPath() %>/servlet/display?file=<bean:write name="file"/>',
				'image' : '<c:out value="${splashImage}"/>',
				'autostart' : '<c:out value="${autoplay}"/>',
				'stretching' : '<c:choose><c:when test="${scaleVideoPreviews}">exactFit</c:when><c:otherwise>uniform</c:otherwise></c:choose>',				
				'provider' : 'video',
				'height' : '<bean:write name="height"/>',
				'width' : '<bean:write name="width"/>',
				'backcolor' : "000000",
				'frontcolor' : "EEEEEE",
				'lightcolor' : "FFFFFF",
				'controlbar': 'bottom'
			});
		})

	</script>  
	

	
	


	
	
 