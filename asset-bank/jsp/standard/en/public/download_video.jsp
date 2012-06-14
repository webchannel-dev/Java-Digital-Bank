<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		09-Jan-2008		Created from download_image.jsp
	 d2		Matt Stevenson		15-Jan-2008		Implementation work
	 d3		Matt Stevenson		16-Jan-2008		Worked on advanced options
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="imagesAreEmailable" settingName="images-are-emailable"/>
<bright:applicationSetting id="imagesAreCompressable" settingName="can-compress-download"/>
<bright:applicationSetting id="assetRepurposingEnabled" settingName="asset-repurposing-enabled"/>
<bright:applicationSetting id="advancedVideoConversion" settingName="enable-video-conversions"/>
<bright:applicationSetting id="previewDuration" settingName="video-preview-duration"/>
<bright:applicationSetting id="canSelectMultipleUsageTypes" settingName="can-select-multiple-usage-types"/>

<head>
	
	<title><bright:cmsWrite identifier="title-download-video" filter="false"/></title> 
	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="downloadVideo"/>
	<%@include file="../inc/head-elements.jsp"%>	
	<script src="../js/toggle-button.js" type="text/javascript"></script>
	<script src="../js/download-form.js" type="text/javascript"></script>
	
	<%@include file="inc_download_js.jsp"%>		
	
	<script type="text/javascript">
		<!--
			
			function setFormat(ctrl)
			{
				if(document.getElementById('repurpose') && document.getElementById('repurpose')==ctrl && ctrl.checked)
				{
					for(i=0;i<document.getElementById('fileFormat').options.length; i++)
					{
						if(document.getElementById('fileFormat').options[i].value=='flv' && document.getElementById('fileFormat').selectedIndex!=i)
						{ 
							document.getElementById('fileFormat').selectedIndex=i;
						}
					}
					setFormat('flv');
					document.getElementById('previewButton').style.display='none';
					if (document.getElementById('advancedOptions'))
					{
						document.getElementById('advancedOptions').style.display='block';	
					}	
					document.getElementById('convertPanel').style.display='block';
					document.getElementById('convertBox').style.display='none';
				}
				else
				{
					if (document.getElementById('convertPanel')) { document.getElementById('convertPanel').style.display='block'; }
					if (document.getElementById('convertBox')) { document.getElementById('convertBox').style.display='block'; }
					if (document.getElementById('previewButton')) { document.getElementById('previewButton').style.display='inline'; }
					<c:if test="${!advancedVideoConversion}">
					if (document.getElementById('advancedOptions'))
					{					
						document.getElementById('advancedOptions').style.display='none';
					}
					</c:if>
				}
			}
		-->
	</script>
    
	
</head>

		
<body  id="downloadPage" onload="init();  hideContent(); setFormat(document.getElementById('repurpose')); <c:if test='${(downloadForm.frameRate != null && downloadForm.frameRate != \'\') || (downloadForm.audioSampleRate != null && downloadForm.audioSampleRate != \'\') || downloadForm.startOffset > 0 || downloadForm.duration > 0}'>document.getElementById('advOptions').style.display = 'block';</c:if>">

	<%@include  file="inc_download_form_settings.jsp"%>

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-download-video" filter="false" /></h1> 			

			<c:set var="assetTypeName"><bright:cmsWrite identifier="video" case="mixed" filter="false"/></c:set>
         <%@include file="inc_download_top.jsp"%>				 
         
         <div>	
			<bean:define id="asset" name="downloadForm" property="asset" />
			<bean:define id="resultImgClass" value="icon" />
			<bean:define id="ignoreCheckRestrict" value="yes"/>	
			<bean:define id="disablePreview" value="true"/>								
			<%@include file="../inc/view_thumbnail.jsp"%>
			<%@include file="../inc/download_attributes.jsp"%>								
		</div>
		
		<bean:define id="isVideo" value="true"/>
		<bean:define id="action" value='viewDownloadVideo'/>
		
		<c:if test="${!userprofile.isFromCms}">
			<bean:define id="actionUrl" value="../action/downloadVideo"/>
		</c:if>
		<c:if test="${userprofile.isFromCms}">
			<bean:define id="actionUrl" value="../action/cmsDownloadVideo"/>
		</c:if>
		
		<bean:parameter id="repurposeAsset" name="repurposeAsset" value="false"/>

     	<form action="<bean:write name='actionUrl'/>" method="get" name="downloadForm" id="downloadForm">
     		
			<input type="hidden" name="advanced" value="<c:out value='${advanced}'/>"/>
			<input type="hidden" name="repurposeAsset" value="<c:out value='${repurposeAsset}'/>"/>	
     		<html:hidden name="downloadForm" property="asset.id"/>
     		
			<br/>				
			<c:set var="msgDownload" value="" />
			<%@include file="inc_usage_selection.jsp"%>
						
			<c:if test="${!usageIsSelected}">
				<html:hidden name="downloadForm" property="repurpose"/>
			</c:if>
			<%-- Show the form if there is a usage selected. --%>
			<c:if test="${usageIsSelected}">
							
				<%-- display secondary usage types if necessary --%>
				<c:if test="${canSelectMultipleUsageTypes}">
					<%@include file="inc_additional_usage_selection.jsp"%>
				</c:if>
				
				<%@include file="inc_tscs_selection.jsp"%>

				<c:if test="${!userprofile.isFromCms}">
					<c:if test="${imagesAreCompressable}">
						<label class="wrapping">
						<html:checkbox name="downloadForm" property="compress" styleId="compress" style="display: inline; margin-bottom: 0px;" onclick="doAlternativeMethodSelection(this); setFormat(this);"/> <bright:cmsWrite identifier="label-compress-file" filter="false"/></label>
					</c:if>
					<c:if test="${imagesAreEmailable && userprofile.userCanEmailAssets}">
						<label class="wrapping">
						<html:checkbox name="downloadForm" property="email" styleId="sendemail" style="display: inline; margin-bottom: 0px;" onclick="doAlternativeMethodSelection(this); setFormat(this);"/> <bright:cmsWrite identifier="label-email-file" filter="false"/></label>
					</c:if>
					<c:if test="${repurposeAsset && assetRepurposingEnabled && userprofile.userCanRepurposeAssets}">
						<label class="wrapping">
						<html:checkbox name="downloadForm" property="repurpose" styleId="repurpose" style="display: inline; margin-bottom: 0px;" onclick="doAlternativeMethodSelection(this); setFormat(this);"/> <bright:cmsWrite identifier="label-repurpose" filter="false"/></label>
					</c:if>
				</c:if>

				
				<%@include file="inc_download_original.jsp"%>
			
				<%-- Show videoUserOptions if exist --%>				
				<c:choose>
				<c:when test="${not empty downloadForm.videoUserOptions}">
					<div class="dialogPanel" style="margin-bottom:0;">
						<bean:define id="imagesAreCroppable" value="false"/>
						<%@include file="inc_video_user_download_options.jsp"%>
						
						<h3 class="expand" id="expandLink"><a href="javascript:;" onclick="expand_content('advOptions');"><bright:cmsWrite identifier="subhead-adv-options" filter="false" /></a></h3>
						<div id="advOptions">
							<h3 class="expand collapse" id="collapseLink"><a href="javascript:;" onclick="collapse_content('advOptions');"><bright:cmsWrite identifier="subhead-adv-options" filter="false" /></a></h3>
							<label for="startOffset">Start offset (seconds):</label>
							<input type="text" name="startOffset" id="startOffset" size="10" <c:if test="${downloadForm.startOffset > 0}">value="<bean:write name='downloadForm' property='startOffset'/>"</c:if> class="small text" /><br/>
							<label for="duration">Clip duration (seconds):</label>
							<input type="text" name="duration" id="duration" size="10" <c:if test="${downloadForm.duration > 0}">value="<bean:write name='downloadForm' property='duration'/>"</c:if> class="small text" />
							<br />
						</div>
						
						<div class="hr"></div>
						
						<%@include file="inc_download_buttons.jsp"%>
						<br />
					</div>	
				</c:when>
				<c:otherwise>
				<c:if test="${advancedVideoConversion || assetRepurposingEnabled}">
					<div id="advancedOptions">
						<div class="dialogPanel" style="margin-bottom:0;">
													
								<bright:refDataList componentName="VideoAssetManagerImpl" methodName="getDownloadableVideoFormats" id="downloadFormats"/>
								<bean:define id="imagesAreCroppable" value="false"/>
								<%@include file="inc_media_download_options.jsp"%>

								<h3 class="expand" id="expandLink"><a href="javascript:;" onclick="expand_content('advOptions');"><bright:cmsWrite identifier="subhead-adv-options" filter="false" /></a></h3>

								<div id="advOptions">
									
									<h3 class="expand collapse" id="collapseLink"><a href="javascript:;" onclick="collapse_content('advOptions');"><bright:cmsWrite identifier="subhead-adv-options" filter="false" /></a></h3>

									<bright:cmsWrite identifier="video-conversion-advanced-copy" filter="false" replaceVariables="true" />
									
									<label for="startOffset">Start offset (seconds):</label>
									<input type="text" name="startOffset" id="startOffset" size="10" <c:if test="${downloadForm.startOffset > 0}">value="<bean:write name='downloadForm' property='startOffset'/>"</c:if> class="small text" /><br/>
									<label for="duration">Clip duration (seconds):</label>
									<input type="text" name="duration" id="duration" size="10" <c:if test="${downloadForm.duration > 0}">value="<bean:write name='downloadForm' property='duration'/>"</c:if> class="small text" />
									<br />
									<label for="frameRate"><bright:cmsWrite identifier="label-frameRate" filter="false"/></label>
									

									<div id="frameRateDiv">
										<select name="frameRate" id="frameRate" size="1">
											<option value=""><bright:cmsWrite identifier="snippet-please-select" filter="false"/></option>
											<option value="standard" <c:if test="${downloadForm.frameRate == 'standard'}">selected</c:if>><bright:cmsWrite identifier="snippet-standard" filter="false"/></option>
											<option value="high" <c:if test="${downloadForm.frameRate == 'high'}">selected</c:if>><bright:cmsWrite identifier="snippet-high" filter="false"/></option>
										</select>
									</div>

									<br />
									<label for="audioSampleRate"><bright:cmsWrite identifier="label-audioSampleRate" filter="false"/> </label>
									
									<div id="audioRateDiv">
										<select name="audioSampleRate" id="audioSampleRate" size="1">
											<option value=""><bright:cmsWrite identifier="snippet-please-select" filter="false"/></option>
											<option value="standard" <c:if test="${downloadForm.audioSampleRate == 'standard'}">selected</c:if>><bright:cmsWrite identifier="snippet-standard" filter="false"/></option>
											<option value="high" <c:if test="${downloadForm.audioSampleRate == 'high'}">selected</c:if>><bright:cmsWrite identifier="snippet-high" filter="false"/></option>
										</select>
										<br />
										
									</div>
					
								</div>
								<div class="hr"></div>
							
							<%@include file="inc_download_buttons.jsp"%>
							<br />
						</div>
					</div>
				</c:if>					
				</c:otherwise>
				</c:choose>
			</c:if>
		</form>	
      <br />	
			
	<%@include file="../inc/body_end.jsp"%>

	</body>
</html>