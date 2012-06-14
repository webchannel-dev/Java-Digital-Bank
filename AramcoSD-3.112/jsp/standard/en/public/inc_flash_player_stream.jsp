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
	
	<script type='text/javascript' src='../js/lib/jwplayer.js'></script>
                   
	<script type="text/javascript">
		$j(function(){
			jwplayer('flashcontent').setup({
				flashplayer : '../tools/jwplayer/player.swf',
				file : '<%= request.getContextPath() %>/servlet/stream?file=<bean:write name="file"/>',
				image : '<c:out value="${splashImage}"/>',
				provider : 'http',
				height: <bean:write name="height"/>,
				width: <bean:write name="width"/>,
				backcolor : "000000",
				frontcolor : "EEEEEE",
				lightcolor : "FFFFFF",
				controlbar: 'bottom'
			}).play(true).pause(true);
			                           
			var settings = {
				assetId: <c:out value='${assetForm.asset.id}' />,
				validationMessages: {
					keywords: 'Please enter something in the "Keywords" field.\n',
					start: 'Please enter a start point.\n',
					end: 'Your end point nust be after your start point.'

				}
			}
			vidKeywords.init(settings);
		});
        

	</script>