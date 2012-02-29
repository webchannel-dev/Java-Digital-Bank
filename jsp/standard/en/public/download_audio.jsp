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
<bright:applicationSetting id="canSelectMultipleUsageTypes" settingName="can-select-multiple-usage-types"/>

<head>
	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="downloadVideo"/>
	<title><bright:cmsWrite identifier="title-download-audio" filter="false"/></title> 
	
	<%@include file="../inc/head-elements.jsp"%>	
	<%@include file="inc_download_js.jsp"%>	
	<script src="../js/toggle-button.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		<!--
			function setFormat(ctrl)
			{
				if (document.getElementById('advancedOptions'))
				{
					if(document.getElementById('repurpose') && document.getElementById('repurpose')==ctrl && ctrl.checked)
					{
						document.getElementById('advancedOptions').style.display='block';					
					}
					else
					{
						document.getElementById('advancedOptions').style.display='none';
					}
				}
			}
		-->
	</script>
</head>
		
<body  id="downloadPage" onload="init(); setFormat(document.getElementById('repurpose')); doAlternativeMethodSelection(document.getElementById('repurpose')); <c:if test='${downloadForm.audioSampleRate != null || downloadForm.startOffset > 0 || downloadForm.duration > 0}'>if (document.getElementById('advOptions')) { document.getElementById('advOptions').style.display = 'block'; }</c:if>">

	<%@include  file="inc_download_form_settings.jsp"%>

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-download-audio" filter="false" /></h1> 			

		<c:set var="assetTypeName"><bright:cmsWrite identifier="audio" case="mixed" filter="false"/></c:set>
      <%@include file="inc_download_top.jsp"%>				 
         
      <div>	
			<bean:define id="asset" name="downloadForm" property="asset" />
			<bean:define id="resultImgClass" value="icon" />
			<bean:define id="ignoreCheckRestrict" value="yes"/>							
			<%@include file="../inc/view_thumbnail.jsp"%>
			<%@include file="../inc/download_attributes.jsp"%>								
		</div>
		
		<bean:define id="isAudio" value="true"/>
		<bean:define id="action" value='viewDownloadAudio'/>
		
		<c:if test="${!userprofile.isFromCms}">
			<bean:define id="actionUrl" value="../action/downloadAudio"/>
		</c:if>
		<c:if test="${userprofile.isFromCms}">
			<bean:define id="actionUrl" value="../action/cmsDownloadAudio"/>
		</c:if>
		
		<bean:parameter id="repurposeAsset" name="repurposeAsset" value="false"/>

		<%-- Quick download and Advanced download tabs --%>
		<bean:parameter id="returnUrl" name="returnUrl" value=""/>
		<logic:equal name="showTabs" value="true">
			
			<c:if test="${userprofile.isAdmin || downloadForm.asset.showAdvancedDownloadOptions}">
				<p class="tabHolderPopup">
					<logic:equal name="advanced" value="false">
						<a class="active" href="../action/<bean:write name='action'/>?id=<bean:write name='downloadForm' property='asset.id'/>&amp;assetUse.usageTypeId=<c:out value="${downloadForm.selectedUsageType.id}" />&amp;advanced=false&amp;parentId=<c:out value="${downloadForm.parentId}"/>&amp;repurpose=<bean:write name="downloadForm" property="repurpose"/>&amp;repurposeAsset=<bean:write name="repurposeAsset"/>&amp;returnUrl=<bright:write name="returnUrl" encodeForUrl="true"/>"><bright:cmsWrite identifier="tab-quick-download" filter="false"/></a>
						<a href="../action/<bean:write name='action'/>?id=<bean:write name='downloadForm' property='asset.id'/>&amp;assetUse.usageTypeId=<c:out value="${downloadForm.selectedUsageType.id}" />&amp;advanced=true&amp;parentId=<c:out value="${downloadForm.parentId}"/>&amp;repurpose=<bean:write name="downloadForm" property="repurpose"/>&amp;repurposeAsset=<bean:write name="repurposeAsset"/>&amp;returnUrl=<bright:write name="returnUrl" encodeForUrl="true"/>"><bright:cmsWrite identifier="tab-advanced-download" filter="false"/></a>
					</logic:equal>
					<c:if test="${advanced == true}">
						<a href="../action/<bean:write name='action'/>?id=<bean:write name='downloadForm' property='asset.id'/>&amp;assetUse.usageTypeId=<c:out value="${downloadForm.selectedUsageType.id}" />&amp;advanced=false&amp;parentId=<c:out value="${downloadForm.parentId}"/>&amp;repurpose=<bean:write name="downloadForm" property="repurpose"/>&amp;repurposeAsset=<bean:write name="repurposeAsset"/>&amp;returnUrl=<bright:write name="returnUrl" encodeForUrl="true"/>"><bright:cmsWrite identifier="tab-quick-download" filter="false"/></a>
						<a class="active" href="../action/<bean:write name='action'/>?id=<bean:write name='downloadForm' property='asset.id'/>&amp;assetUse.usageTypeId=<c:out value="${downloadForm.selectedUsageType.id}" />&amp;advanced=true&amp;parentId=<c:out value="${downloadForm.parentId}"/>&amp;repurpose=<bean:write name="downloadForm" property="repurpose"/>&amp;repurposeAsset=<bean:write name="repurposeAsset"/>&amp;returnUrl=<bright:write name="returnUrl" encodeForUrl="true"/>"><bright:cmsWrite identifier="tab-advanced-download" filter="false"/></a>
					</c:if>
				</p>
				<div id="tabContent">
			</c:if>
		</logic:equal>
		<%--  --%>

     	<form action="<bean:write name='actionUrl'/>" method="get" name="downloadForm" id="downloadForm">
     		
			<input type="hidden" name="advanced" value="<c:out value='${advanced}'/>"/>
			<input type="hidden" name="repurposeAsset" value="<c:out value='${repurposeAsset}'/>"/>	
     		<html:hidden name="downloadForm" property="asset.id"/>
     		<html:hidden name="downloadForm" property="asset.typeId"/>
     		
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
						<html:checkbox name="downloadForm" property="compress" styleId="compress"  onclick="doAlternativeMethodSelection(this); "/> <bright:cmsWrite identifier="label-compress-file" filter="false"/></label>
					</c:if>
					<c:if test="${imagesAreEmailable && userprofile.userCanEmailAssets}">
						<label class="wrapping">
						<html:checkbox name="downloadForm" property="email" styleId="sendemail"  onclick="doAlternativeMethodSelection(this);"/> <bright:cmsWrite identifier="label-email-file" filter="false"/></label>
					</c:if>
					<c:if test="${repurposeAsset && assetRepurposingEnabled && userprofile.userCanRepurposeAssets}">
						<label class="wrapping">
						<html:checkbox name="downloadForm" property="repurpose" styleId="repurpose" onclick="doAlternativeMethodSelection(this); setFormat(this);"/> <bright:cmsWrite identifier="label-repurpose" filter="false"/></label>
					</c:if>
				</c:if>

				
				<%@include file="inc_download_original.jsp"%>
				<c:if test="${assetRepurposingEnabled || advanced}">
					<div>
						<div class="dialogPanel" style="margin-bottom:0;">
								<bean:define id="imagesAreCroppable" value="false"/>
								<%@include file="inc_media_download_options.jsp"%>

								<h3 class="expand" id="expandLink"><a href="javascript:;" onclick="expand_content('advOptions');"><bright:cmsWrite identifier="subhead-adv-options" filter="false" /></a></h3>

								<div id="advOptions">
									
									<h3 class="collapse" id="collapseLink"><a href="javascript:;" onclick="collapse_content('advOptions');"><bright:cmsWrite identifier="subhead-adv-options" filter="false" /></a></h3>

									<bright:cmsWrite identifier="audio-conversion-advanced-copy" filter="false" replaceVariables="true" />
									
									<label for="audioBitrate">Bitrate:</label>
									<div id="audioBitrateDiv">
										<select name="audioBitrate" id="audioBitrate" size="1">
											<option value=""><bright:cmsWrite identifier="snippet-please-select" filter="false"/></option>
											<option value="96000" <c:if test="${downloadForm.audioBitrate == 96000}">selected</c:if>><bright:cmsWrite identifier="snippet-low" filter="false"/> (96 kbps, ~FM Radio Quality)</option>
											<option value="128000" <c:if test="${downloadForm.audioBitrate == 128000}">selected</c:if>><bright:cmsWrite identifier="snippet-standard" filter="false"/> (128 kbps, Standard MP3)</option>
											<option value="256000" <c:if test="${downloadForm.audioBitrate == 256000}">selected</c:if>><bright:cmsWrite identifier="snippet-high" filter="false"/> (256 kbps, Near CD Quality)</option>
										</select>
									</div>

									<label for="startOffset">Start offset (seconds):</label>
									<input type="text" name="startOffset" id="startOffset" size="10" <c:if test="${downloadForm.startOffset > 0}">value="<bean:write name='downloadForm' property='startOffset'/>"</c:if>/><br/>
									<label for="duration">Clip duration (seconds):</label>
									<input type="text" name="duration" id="duration" size="10" <c:if test="${downloadForm.duration > 0}">value="<bean:write name='downloadForm' property='duration'/>"</c:if>/>
									
									<!-- 
									<br />
									<label for="audioSampleRate"><bright:cmsWrite identifier="label-audioSampleRate" filter="false"/> </label>
									
									<div id="audioRateDiv">
										<select name="audioSampleRate" id="audioSampleRate" size="1">
											<option value=""><bright:cmsWrite identifier="snippet-please-select" filter="false"/></option>
											<option value="standard" <c:if test="${downloadForm.audioSampleRate == 'standard'}">selected</c:if>><bright:cmsWrite identifier="snippet-standard" filter="false"/></option>
											<option value="high" <c:if test="${downloadForm.audioSampleRate == 'high'}">selected</c:if>><bright:cmsWrite identifier="snippet-high" filter="false"/></option>
										</select>
									</div>
									-->

									
									<br />
									<br/>
								</div>
								<div class="hr"></div>
							
								<html:submit styleClass="button flush" property="b_download" styleId="submit_button"><bright:cmsWrite identifier="button-download-now" filter="false" /></html:submit>
							<br />
						</div>
					</div>
				</c:if>
			</c:if>
		</form>	

		<%-- End of quick download/advanced download tabs --%>
	    <logic:equal name="showTabs" value="true">
	        <c:if test="${userprofile.isAdmin || downloadForm.asset.showAdvancedDownloadOptions}">
	            </div> <!-- end of tabContent -->
	        </c:if>
		</logic:equal>
		<%-- --%>
	
	    <br />	
	

			
	<%@include file="../inc/body_end.jsp"%>

	</body>
</html>