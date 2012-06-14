<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Chris Preager		24-May-2004		Imported from Image Manager
	 d2		Martin Wilson		06-Sep-2005		Changed for ftp
	 d3		Ben Browning		17-Feb-2006		HTML/CSS Tidy up
	 d4		Matt Stevenson		15-Mar-2006		Added switch to setup asset links
	 d5		James Home			02-Feb-2007		Added flag for using filename as title
	 d6     Matthew Woollard    17-Sep-2007     Added upload applet
	 d7     Matt Woollard       26-Nov-2007     Directories can be processed after bulk upload
	 d8     Matt Woollard       10-Dec-2007     Added flash uploader
	 d9     Matt Woollard       29-Jun-2009		Allow uploading into existing assets
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="ftpUsername" settingName="ftp-username"/>
<bright:applicationSetting id="ftpPassword" settingName="ftp-password"/>
<bright:applicationSetting id="ftpHost" settingName="ftp-host"/>
<bright:applicationSetting id="useFtp" settingName="use-ftp"/>
<bright:applicationSetting id="showFtpHelp" settingName="start-import-show-ftp-help"/>
<bright:applicationSetting id="enableTemplates" settingName="enable-attribute-templates"/>
<bright:applicationSetting id="autoCompleteEnabled" settingName="auto-complete-enabled"/>
<bright:applicationSetting id="exportFilenameFormat" settingName="exported-asset-filename-format"/>
<bright:applicationSetting id="reembedMetadataOnSave" settingName="reembed-metadata-on-save"/>
<bright:applicationSetting id="showPopulateNameFromFilename" settingName="show-populate-name-from-filename"/>
<bright:applicationSetting id="ssoEnabled" settingName="sso-enabled"/>
<bright:applicationSetting id="ssoPluginClass" settingName="sso-plugin-class"/>


<head>
	
	<title><bright:cmsWrite identifier="title-start-import" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script src="../js/category.js" type="text/javascript"></script>
	
	<script src="../js/keywordChooser.js" type="text/javascript"></script>
	<script src="../js/lib/swfupload/AC_OETags.js" type="text/javascript"></script>
	<script src="../js/agreements.js" type="text/javascript"></script>
	<script src="../js/asset-upload.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		function hideContent() {
			document.getElementById('expandLink').style.display = 'block';
			document.getElementById('ftpHelp').style.display = 'none';
		}
	</script>

	<%@include file="../inc/flashuploader_version_check-js.jsp" %>

	<%-- Set up category javascript and jsp variables --%>
	<bean:define id="assetForm" name="importForm"/>	

	<c:set var="ctrlIsCheckboxControl" value="0" scope="request"/>
	<%@include file="../category/inc_asset_category_head_js.jsp"%>

	<bean:define id="section" value="import"/>
	<bean:parameter id="entityId" name="entityId" value="0"/>
	
	<logic:equal name="importForm" property="uploadToolOption" value="browser">
	
		<script language="JavaScript" type="text/javascript">
			<!--
			// Version check based upon the values entered above in "Globals"
			var hasReqestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);
	
			// Check to see if the version meets the requirements for playback
			var sWinLoc = "";
			if (hasReqestedVersion) {
				sWinLoc = "viewStartImport?uploadToolOption=flash";
			} else {
				sWinLoc = "viewStartImport?uploadToolOption=applet";
			}
	
			<c:if test="${importForm.parentId>0}">
				sWinLoc += '&parentId=<c:out value="${importForm.parentId}"/>';
			</c:if>
	
			<c:if test="${entityId>0}">
				sWinLoc += '&entityId=<c:out value="${entityId}"/>';
			</c:if>
	
			<c:if test="${importForm.importFilesToExistingAssets}">
				sWinLoc += '&importFilesToExistingAssets=true';
			</c:if>
	
			<c:if test="${importForm.importChildAssets}">
				sWinLoc += '&importChildAssets=true';
			</c:if>
	
			window.location = sWinLoc;
		

			// -->
		</script>
	
	</logic:equal>	

	<c:if test="${importForm.importFilesToExistingAssets}">
		<c:set var="importFilesToExistingAssets" value="${true}"/>
	</c:if>

	<logic:equal name="importForm" property="uploadToolOption" value="flash">
		<%@ include file="../inc/flashuploader-js.jsp"%>
	</logic:equal>

	<%@ include file="../inc/inc_entities_js.jsp"%>
	
	<script type="text/javascript" charset="utf-8">
		//Set up global variables for any map attributes
		var sLat = "";
		var wLng = "";
		var nLat = "";
		var eLng = "";
		
		var $whichAttribute = ""; 			//This is a jquery element that acts as a referenece for which attribute we are dealing with
		
		$j(function(){
			popupMapInit();
			initDatePicker();
			

			//check if sso enabled and ssoplugin class may break upload
			//check if the version of flash is greater than 9.0.24
			<c:if test="${ssoEnabled && ssoPluginClass == 'WIASSOPlugin' }">
			
				var playerVersion = swfobject.getFlashPlayerVersion();

				if (playerVersion.major == 9 && playerVersion.minor == 0 && playerVersion.release < 24) 
				{				
					$j('#outdatedFlash').removeClass('js-enabled-hide');
				} 
				else if (playerVersion.major < 9) 
				{
					$j('#outdatedFlash').removeClass('js-enabled-hide');
				}
				
			</c:if>					
	

			if (swfobject.hasFlashPlayerVersion("9.0.0")) {
			  // has Flash
			
			}
			else {
			  // no Flash
				$j('#noFlashError').attr('style', 'display:block !important');
				$j('#uploadButtons').hide();
			}
			
		})
		
	</script>

</head>

<body id="importPage" onload="setDescSelectedCategories(); setPermSelectedCategories(); setCatIdsFields(); <logic:equal name="importForm" property="uploadToolOption" value="ftp"> hideContent();</logic:equal><logic:equal name="importForm" property="uploadToolOption" value="flash"> uploader();</logic:equal> showHideAgreementType(); syncAgreementPreviewButton();">

	<%@include file="../inc/body_start.jsp"%>
	
	<script type="text/javascript">
		preventTimeout();
	</script>
	<div id="container"></div>
	<div id="dataLookupCode"></div>

	<logic:equal name="importForm" property="success" value="false">
		<h1 class="underline">
			<bright:cmsWrite identifier="heading-start-import" filter="false" />
			<c:if test="${not empty importForm.asset.entity && not empty importForm.asset.entity.name}">
				(<bean:write name="importForm" property="asset.entity.name" />)
			</c:if>
		</h1> 

		<bean:size name="importForm" property="allTopLevelFilesList" id="allTopLevelFilesListSize"/>
		<c:if test="${allTopLevelFilesListSize > 0}">
			<div class="info">
				<bright:cmsWrite identifier="snippet-bulk-upload-existing-files" filter="false" />
				<ul>
					<logic:iterate name="importForm" property="allTopLevelFilesList" id="fileName">
						<li><bean:write name="fileName"/></li>
					</logic:iterate>
				</ul>
			</div>
		</c:if>
		
		<!-- Display applet if specified in properties -->
		<logic:equal name="importForm" property="uploadToolOption" value="applet">
	
			<bright:cmsWrite identifier="intro-bulk-upload-1" filter="false" />

			<br />
			<APPLET CODE="wjhk.jupload2.JUploadApplet" NAME="JUpload" ARCHIVE="../tools/jupload/wjhk.jupload.jar" WIDTH="640" HEIGHT="200" MAYSCRIPT></XMP>
			   <PARAM NAME = CODE VALUE = "wjhk.jupload2.JUploadApplet" >
			   <PARAM NAME = ARCHIVE VALUE = "../tools/jupload/wjhk.jupload.jar" >
			   <PARAM NAME = "type" VALUE = "application/x-java-applet;version=1.5">
			   <PARAM NAME = "postURL" VALUE = "../servlet/upload">
				<PARAM NAME = "lookAndFeel" VALUE = "system">
			   <PARAM NAME = "nbFilesPerRequest" VALUE = "1">	
			   <PARAM NAME = "maxChunkSize" VALUE = "10000000">		
				<PARAM NAME = "serverProtocol" VALUE = "HTTP/1.1">
				<PARAM NAME = "showLogWindow" VALUE = "false">
				<PARAM NAME = "showStatusBar" VALUE = "false">
				<PARAM NAME = "lang" VALUE = "en">
				<PARAM NAME = "debugLevel" VALUE = "0">
				<PARAM NAME = "allowHttpPersistent" VALUE = "false">
				Java 1.4 or higher plugin required.
			</APPLET>
		</logic:equal>
		
		<!-- Flash upload -->
	<logic:equal name="importForm" property="uploadToolOption" value="flash">

		<bright:cmsWrite identifier="intro-bulk-upload-1" filter="false" />

			<div id="uploadContainer"  style="margin-bottom:1em;">
				<fieldset class="flash" id="fsUploadProgress1">
					<div id="flashTitle">
						<h3><bright:cmsWrite identifier="subhead-upload-files" filter="false"/></h3>
					</div>
				</fieldset>
			</div>
			
			<div id="outdatedFlash" class="error js-enabled-hide">
				<bright:cmsWrite identifier="snippet-upload-outdated-flash" filter="false"/>
			</div>			
			
			<div id="noFlashError" class="error js-enabled-hide">
				<bright:cmsWrite identifier="snippet-upload-no-flash" filter="false"/>
			</div>	
			
			<div style="position:absolute;">
				<span id="spanButtonPlaceholder"></span>
			</div>
			<form id="uploadButtons">
			<input type="button" class="button flush" value="<bright:cmsWrite identifier="button-upload-files" filter="false"/>" onclick="upload1.selectFiles()" style="width:110px;" id="btnUploadFiles"/>
			<input id="btnCancel1" type="button" class="button" value="<bright:cmsWrite identifier="button-cancel-uploads" filter="false"/>"  onclick="cancelQueue(upload1);" disabled="disabled" style="width:100px"/><br />
			</form>
			
	</logic:equal>	
	
	<!-- If applet turned off, display FTP instructions -->
	
	<logic:equal name="importForm" property="uploadToolOption" value="ftp">
	<c:set var="FTPPath" value="${importForm.FTPPath}"/>
	<bright:cmsWrite identifier="intro-bulk-upload-1-noapp" filter="false" replaceVariables="true" />
	<br/>
	<logic:equal name="useFtp" value="true">
		<bright:cmsWrite identifier="bulk-otherwise-ftp" filter="false" />
	</logic:equal>
	<logic:equal name="useFtp" value="false">
		<bright:cmsWrite identifier="bulk-otherwise-win" filter="false" />
	</logic:equal>
	</p>

	<logic:equal name="showFtpHelp" value="true">

	<p id="expandLink">
		<a href="#" onclick="expand_content('ftpHelp');">
		<logic:equal name="useFtp" value="true">
			<bright:cmsWrite identifier="link-show-help-ftp" filter="false" />
		</logic:equal>
		<logic:equal name="useFtp" value="false">
			<bright:cmsWrite identifier="link-show-help" filter="false" />
		</logic:equal>		
		</a>
	</p> 

	<!-- Start FTP help -->
	<div id="ftpHelp">
		<p id="collapseLink"><a href="#" onclick="collapse_content('ftpHelp');"><bright:cmsWrite identifier="link-hide-help" filter="false" /></a></p>
		<div class="hr"></div>
		<h2><bright:cmsWrite identifier="subhead-upload-help" filter="false" /></h2>
		
		<logic:equal name="useFtp" value="true">
			<bright:cmsWrite identifier="bulk-upload-help-ftp1" filter="false" />
		</logic:equal>
		
		<bright:cmsWrite identifier="bulk-upload-help" filter="false" replaceVariables="true" />
		
		
		<logic:equal name="useFtp" value="true">
			<bright:cmsWrite identifier="bulk-upload-help-ftp2" filter="false" replaceVariables="true" />
		</logic:equal>
	</div>
	<!-- End FTP help -->
	<br />
	
	
	</logic:equal>
	</logic:equal>	

	<%-- If this is adding to existing go straight to processing of the queue --%>
	<c:if test="${importForm.importFilesToExistingAssets}">
		<form action="startImport" method="post">
	</c:if>
	
	<c:if test="${!importForm.importFilesToExistingAssets}">
		<form action="viewStartImport?uploaded=1" method="post">
	</c:if>
		
		
		<c:if test="${not empty importForm.entities}">
			<bean:size name="importForm" property="entities" id="numEntities"/>
			<logic:equal name="numEntities" value="1">
				<html:hidden name="importForm" property="selectedAssetEntityId"/>
			</logic:equal>
			<logic:greaterThan name="numEntities" value="1">
				<div class="hr"></div>
				<h2><bright:cmsWrite identifier="heading-choose-type" filter="false"/></h2>
				<table class="admin">
					<tr>
						<td>
							<bright:cmsWrite identifier="label-asset-type" filter="false"/>
						</td>
						<td>
							<bean:define id="entities" name="importForm" property="entities"/>
							<html:select name="importForm" property="selectedAssetEntityId" styleId="entity" style="width:auto;" onchange="applyEntitySelection();">
								<html:optionsCollection name="entities" value="id" label="name"/>
							</html:select>
						</td>
					</tr>
					<c:if test="${not empty importForm.formatOptions}">
						<tr>
							<td>
								<bright:cmsWrite identifier="label-format-preference" filter="false"/>
							</td>
							<td>
								<c:set var="formatOptions" value="${importForm.formatOptions}"/>
								<html:select name="importForm" property="chosenFileFormat" style="width:auto;">
									<html:optionsCollection name="formatOptions" value="id" label="name"/>
								</html:select>
							</td>
						</tr>
					</c:if>
				</table>
			</logic:greaterThan>
		</c:if>
		<c:if test="${not empty importForm.formatOptions && (empty numEntities || numEntities<=1)}">
			<div class="hr"></div>
			<br/>
			<bright:cmsWrite identifier="label-format-preference" filter="false"/>
			<c:set var="formatOptions" value="${importForm.formatOptions}"/>
			<html:select name="importForm" property="chosenFileFormat" style="width:auto;">
				<html:optionsCollection name="formatOptions" value="id" label="name"/>
			</html:select>
			<br/><br/>
		</c:if>
		
		<html:hidden name="importForm" property="parentId"/>
		<input type="hidden" name="entityId" value="<bean:write name="entityId"/>"/>
		<input type="hidden" name="bulkUploadFirst" value="1"/>

		<c:if test="${importForm.importFilesToExistingAssets}">
			<input type="hidden" name="importFilesToExistingAssets" value="true" />
		</c:if>
		
		<c:if test="${importForm.importChildAssets}">
			<input type="hidden" name="importChildAssets" value="true" />
		</c:if>

		<div class="hr"></div>

				<input type="submit" name="submit" class="button floated flush" value="<bright:cmsWrite identifier="button-next" filter="false"/>" id="nextButton"/> 
				
				<a href="viewDataImport" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false"/></a>
				<div id="filesUploading">Files are currently being uploaded</div>
		</form>

	
	</logic:equal>
	
	<!-- Once applet has finished, or user has manually updated files, display form below -->
	<logic:equal name="importForm" property="success" value="true">
	
	<h1>
		<bright:cmsWrite identifier="heading-start-import" filter="false" />
		<c:if test="${not empty importForm.asset.entity && not empty importForm.asset.entity.name}">
			(<bean:write name="importForm" property="asset.entity.name" />)
		</c:if>
	</h1>  
	
	<div class="head">
		<a href="viewDataImport?1<c:if test="${importForm.parentId>0}">&parentId=<c:out value="${importForm.parentId}"/></c:if><c:if test="${entityId>0}">&entityId=<c:out value="${entityId}"/></c:if>" class="bold"><bright:cmsWrite identifier="link-upload-more-files" filter="false" /></a>
	</div>
	
	<logic:equal name="importForm" property="hasErrors" value="true">
		<div class="error">
			<bright:cmsWrite identifier="snippet-errors-starting-import" filter="false"/> <br />
			<logic:iterate name="importForm" property="errors" id="errorText">
				<bright:writeError name="errorText" /><br />
			</logic:iterate>		
		</div>
	</logic:equal>
	
	<c:choose>
		<c:when  test="${importForm.topLevelDirectoryCount == 0 && importForm.topLevelFileCount >= 1 && importForm.topLevelFileCountIncludeZips == importForm.topLevelFileCount}">
			<%-- <h2><bright:cmsWrite identifier="heading-start-import" filter="false" /></h2> --%>
		</c:when>
		<c:otherwise>
			<bright:cmsWrite identifier="intro-bulk-upload-2" filter="false" />
		</c:otherwise>
	</c:choose>
	
	<c:if test="${enableTemplates && not empty importForm.templates}">
		<bright:cmsWrite identifier="label-use-template" filter="false"/> &nbsp;&nbsp;
		<form name="templateForm" action="viewStartImport" id="templateForm" method="get" style="display: inline;">
			<select name="templateId" onchange="document.getElementById('templateForm').submit();" id="templateSelect" style="width:88px">
				<option value="-1"><bright:cmsWrite identifier="snippet-choose-template" filter="false"/></option>
				<logic:iterate name="importForm" property="templates" id="template">
					<option value="<bean:write name='template' property='id'/>" <c:if test='${template.id == importForm.currentTemplateId}'>selected <c:set var="found" value="true"/></c:if>><bean:write name="template" property="name" /></option>
				</logic:iterate>
				<logic:notPresent name="found">
					<c:set var="found" value="false"/>
				</logic:notPresent>
			</select>
			<input type="hidden" name="uploaded" value="1">
			<noscript><input type="submit" name="submit" value="Go" class="button flush" /></noscript>
		</form>
		<script type="text/javascript">
			// non javascript version is narrower
			document.getElementById('templateSelect').style.width = "118px";
		</script>
		<br/><br/>
	</c:if>
	
	<html:form action="startImport" styleId="updateForm" enctype="multipart/form-data" method="post">				
	
		<html:hidden name="importForm" property="selectedAssetEntityId"/>
		<html:hidden name="importForm" property="parentId"/>
		<html:hidden name="importForm" property="chosenFileFormat"/>
		<html:hidden name="importForm" property="importChildAssets"/>
		
		

		<c:if test="${importForm.topLevelFileCountIncludeZips>0 || importForm.topLevelDirectoryCount>0}">
			<p <c:if test="${importForm.topLevelDirectoryCount == 0 && importForm.topLevelFileCount >= 1 && importForm.topLevelFileCountIncludeZips == importForm.topLevelFileCount}">style="position:absolute; left: -9999px"</c:if>>
				<c:choose>
					<c:when test="${importForm.topLevelFileCountIncludeZips-importForm.topLevelFileCount>=1}">
						<html:radio property="importFileOption" value="directory_files" styleId="importAllFromDir" />
					</c:when>
					<c:otherwise>
						<input type="hidden" name="importFileOption" value="directory_files"/>
					</c:otherwise>
				</c:choose>
				<label for="importAllFromDir"><bright:cmsWrite identifier="label-import-from-directory" filter="false"/></label>
				
				<!-- if there are only top level dirs then make them be selected -->
				
				<html:select name="importForm" property="directoryPath">
					<option value="">- <bright:cmsWrite identifier="snippet-select" filter="false"/> -</option>
					<c:if test="${importForm.topLevelFileCountIncludeZips>0 || importForm.topLevelDirectoryCount>0}">
						<option value="#toplevel#"
						<logic:empty name="importForm" property="directoryList">selected="selected"</logic:empty>
							>
							<c:set var="numFiles" value="${importForm.topLevelFileCountIncludeZips}" />
							<c:choose>		
								<c:when test="${importForm.topLevelFileCount>1}">
									<bright:cmsWrite identifier="snippet-top-level-x-files" filter="false" replaceVariables="true" />
								</c:when>
								<c:otherwise>
									<bright:cmsWrite identifier="snippet-top-level-1-file" filter="false" replaceVariables="true" />
								</c:otherwise>
							
							</c:choose>		
							</option>
						</c:if>
						<logic:iterate name="importForm" property="directoryList" id="dir">
							<option value="<bean:write name='dir'/>"
							<c:if test="${importForm.directoryPath == dir}">selected="selected"</c:if>
							><bean:write name="dir"/></option>
						</logic:iterate>	
				</html:select>		
			</p>
		</c:if>
				
		<!-- only display if zip files present -->	
		<c:if test="${importForm.topLevelFileCountIncludeZips-importForm.topLevelFileCount>=1}">
			<p>
				<c:choose>
					<c:when test="${importForm.topLevelFileCountIncludeZips>=1}">
						<html:radio property="importFileOption" value="zip_files" styleId="extractZips" />
					</c:when>
					<c:otherwise>
						<input type="hidden" name="importFileOption" value="zip_files"/>
					</c:otherwise>
				</c:choose>
				<label for="extractZips"><bright:cmsWrite identifier="label-extract-import-zip" filter="false"/></label>
				<html:select name="importForm" property="zipPath" styleId="zipPath">
					<option value="">- <bright:cmsWrite identifier="snippet-select" filter="false"/> -</option>
					<logic:iterate name="importForm" property="zipFileList" id="file">
						<option value="<bean:write name='file'/>"
						<c:if test="${importForm.zipPath == file}">selected="selected"</c:if>
						><bean:write name="file"/></option>
					</logic:iterate>
				</html:select>
			</p>
		</c:if>	
	</table>

		
		<table class="form" style="width: auto;" cellspacing="0" cellpadding="0" border="0" summary="Bulk upload form">
			<tr>
			<c:choose>
				<c:when test="${showPopulateNameFromFilename}">
					<th><label for="useFilenameAsTitle"><bright:cmsWrite identifier="label-populate-name" filter="false"/></label></th>
					<td class="checkbox"><html:checkbox name="importForm" property="useFilenameAsTitle" value="true" styleId="useFilenameAsTitle" styleClass="checkbox" /></td>
				</c:when>
				<c:otherwise>
					<html:hidden name="importForm" property="useFilenameAsTitle" />
				</c:otherwise>
			</c:choose>
			
				<c:if test="${importForm.idAppearsInFilenames}">
					<logic:match name="exportFilenameFormat" value="i">
						<c:if test="${showPopulateNameFromFilename}"><td style="padding: 0 15px"><img src="../images/standard/misc/vdots_dark.gif"></td></c:if>
						<th><label for="removeIdFromFilename"><bright:cmsWrite identifier="label-remove-id" filter="false"/></label></th>
						<td class="checkbox"><html:checkbox name="importForm" property="removeIdFromFilename" value="true" styleId="removeIdFromFilename" styleClass="checkbox" /></td>
					</logic:match>
				</c:if>
			</tr>
		</table>
	
		<c:set var="sIsImport" scope="request" value="true"/>
		<c:set var="uploading" scope="request" value="true"/>
		<c:set var="assetForm" scope="request" value="${importForm}"/>
		<c:set var="bulkUploadFirst" scope="request" value="${importForm.bulkUploadFirst}"/>
		<jsp:include page="inc_fields.jsp"/>
		
		<bright:applicationSetting id="getRelatedAssets" settingName="get-related-assets"/>
		<bright:applicationSetting id="showLinkItems" settingName="show-link-items"/>
		<bright:applicationSetting id="showDeferThumbnailCreation" settingName="show-defer-thumbnail-creation"/>

		
		<div class="hr"></div>

		<c:if test="${!showLinkItems}">
			<span style='display: none'>
		</c:if>

		<c:if test="${getRelatedAssets && (importForm.asset.entity.id<=0 || importForm.asset.entity.allowPeers) && !importForm.importChildAssets}">	
			<table class="form" cellspacing="0" cellpadding="0" border="0" summary="Bulk upload link assets form">
				<tr>
					<th style="width: 180px;"><label for="linkAssets"><bright:cmsWrite identifier="label-link-items" filter="false"/></label></th>
					<td><html:checkbox name="importForm" property="linkAssets" value="true" style="width: 20px;" styleId="linkAssets"/></td>
				</tr>
			</table>
			<bright:cmsWrite identifier="snippet-linking-items-note" filter="false"/>
		</c:if>
			
		<c:if test="${!showLinkItems}">
			</span>
		</c:if>		
		
		<c:if test="${!showDeferThumbnailCreation}">
			<span style='display: none'>
		</c:if>

		<c:if test="${!reembedMetadataOnSave}">
			<table class="form" cellspacing="0" cellpadding="0" border="0" summary="Bulk upload defer thumbnail generation form">
				<tr>
					<th style="width: 180px;"><label for="deferThumbnails"><bright:cmsWrite identifier="label-defer-thumbnails" filter="false"/></label></th>
					<td><html:checkbox name="importForm" property="deferThumbnailGeneration" value="true" style="width: 20px;" styleId="deferThumbnails"/></td>
				</tr>
			</table>
			
			<bright:cmsWrite identifier="snippet-defer-thumbnails-note" filter="false"/>
			
			<div class="hr"></div>
		</c:if>

		<c:if test="${!showDeferThumbnailCreation}">
			</span>
		</c:if>
			
		<div class="centered">
			<input type="submit" class="button" id="submitButton" value="<bright:cmsWrite identifier="button-start-import" filter="false" />" /> 
		</div
	</html:form>

	</logic:equal>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>
