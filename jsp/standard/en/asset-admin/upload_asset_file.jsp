<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		29-Nov-2005		Created.
	 d2		Ben Browning		17-Feb-2006		HTML/CSS Tidy up
	 d3		Matt Stevenson		18-Oct-2007		Added switch for empty assets
	 d4		Francis Devereux	29-Jan-2009		Fixed upload with Java applet (was previously uploading to the bulk directory instead of the single directory)
	 d5		Steve Bryan			03-Jun-2009		Replaced allowEmptyAssets setting with group permission
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="enableTemplates" settingName="enable-attribute-templates"/>
<bright:applicationSetting id="assetEntitiesEnabled" settingName="asset-entities-enabled"/>
<c:set var="allowEmptyAssets" value="${userprofile.userCanAddEmptyAssets}" />


<%-- logic:notPresent name='entityId'>
	<bean:parameter name='entityId' id="tempEntityId" value="0"/>
	<c:if test="${tempEntityId > 0}">
		<bean:define id="entityId" name="tempEntityId"/>
	</c:if>
</logic:notPresent --%>				

<head>
	
	<title><bright:cmsWrite identifier="title-step1-upload" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script src="../js/category.js" type="text/javascript"></script>
	<script src="../js/calendar.js" type="text/javascript"></script>
	
	<script src="../js/lib/swfupload/AC_OETags.js" type="text/javascript"></script>
	<%@include file="../inc/flashuploader_version_check-js.jsp" %>
	
<logic:equal name="assetForm" property="uploadToolOption" value="advanced">
	<script language="JavaScript" type="text/javascript">
		<!--
		// Version check based upon the values entered above in "Globals"
		var hasRequestedFlashVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);
	
		// Check to see if the version meets the requirements for playback
		var sWinLoc = "";
		if (hasRequestedFlashVersion) {
			sWinLoc = "viewUploadAssetFile?uploadToolOption=flash";
		} else {
			sWinLoc = "viewUploadAssetFile?uploadToolOption=applet";
		}
		
		<c:if test="${assetForm.parentId>0}">
			sWinLoc += '&parentId=<c:out value="${assetForm.parentId}"/>';
		</c:if>
		
		<c:if test="${assetForm.peerId>0}">
			sWinLoc += '&peerId=<c:out value="${assetForm.peerId}"/>';
		</c:if>
		
		<%-- c:if test="${entityId>0}">
			sWinLoc += '&entityId=<c:out value="${entityId}"/>';
		</c:if --%>
		
		window.location = sWinLoc;
		// -->
	</script>
</logic:equal>

<c:if test="${assetForm.uploadToolOption=='flash' || assetForm.uploadToolOption=='advanced'}">
	<c:set var="singleUpload" value="${true}"/>
	<%@ include file="../inc/flashuploader-js.jsp"%>
</c:if>
	
	<script type="text/javascript">
	<!-- 
	function savePressed()
	{
		document.getElementById('savingDiv').style.display="block";
	}

	function changeButtonText (bChecked)
	{
	<c:if test="${empty assetForm.uploadToolOption || assetForm.uploadToolOption=='basic'}">
		if (!bChecked)
		{
			document.getElementById('submitButton').value = 'Upload';
			document.getElementById('file').disabled = false;
			document.getElementById('fileBlock').style.display = 'block';
		}
		else
		{
			document.getElementById('submitButton').value = 'Next';
			document.getElementById('file').disabled = true;
			document.getElementById('fileBlock').style.display = 'none';
		}
	</c:if>
	}
	
	function reloadPage()
	{
		document.location.reload();
	}
	
	//-->
	</script>
	<%@ include file="../inc/inc_entities_js.jsp"%>
	


	<bean:define id="section" value="upload"/>
</head>

<body id="importPage" onload="applyEntitySelection();<c:if test="${assetForm.uploadToolOption=='flash' || assetForm.uploadToolOption=='advanced'}"> uploader();</c:if>">

	<%@include file="../inc/body_start.jsp"%>
	
	<script type="text/javascript">
		preventTimeout();
	</script>
	
	<h1 class="underline">
		<bright:cmsWrite identifier="heading-step-1-upload" filter="false" />
		<c:if test="${not empty assetForm.asset.entity.name && !assetForm.hasErrors}">
			(<bean:write name="assetForm" property="asset.entity.name" filter="false"/>)
		</c:if>
	</h1> 
	
	<c:set var="sIsImport" value="false"/>
	<logic:equal name="assetForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="assetForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>
			<logic:notEqual name="assetForm" property="noUploadFileSpecified" value="true">
				<br /><bright:cmsWrite identifier="snippet-browse-file-again" filter="false"/>
			</logic:notEqual>
		</div>
	</logic:equal>

	<c:if test="${assetForm.uploadToolOption=='flash' || assetForm.uploadToolOption=='applet' || assetForm.uploadToolOption=='advanced'}">
		
		<c:choose>
			<c:when test="${not empty assetForm.entities && !assetForm.deferEntitySelection}">
				<p><bright:cmsWrite identifier="snippet-single-flash-upload-types" filter="false"/></p>
			</c:when>
			<c:otherwise>
				<p><bright:cmsWrite identifier="snippet-single-flash-upload" filter="false"/></p>
			</c:otherwise>
		</c:choose>
		
		<!-- Display applet if specified in properties -->
		<logic:equal name="assetForm" property="uploadToolOption" value="applet">
			<br />
			<APPLET CODE="wjhk.jupload2.JUploadApplet" NAME="JUpload" ARCHIVE="../tools/jupload/wjhk.jupload.jar" WIDTH="640" HEIGHT="200" MAYSCRIPT></XMP>
			   <PARAM NAME = CODE VALUE = "wjhk.jupload2.JUploadApplet" >
			   <PARAM NAME = ARCHIVE VALUE = "../tools/jupload/wjhk.jupload.jar" >
			   <PARAM NAME = "type" VALUE = "application/x-java-applet;version=1.5">
			   <PARAM NAME = "postURL" VALUE = "../servlet/upload?single=true">
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
		<c:if test="${assetForm.uploadToolOption=='flash' || assetForm.uploadToolOption=='advanced'}">
			<form id="form1" action="index.php" method="post" enctype="multipart/form-data">

				<div id="uploadContainer">
					<fieldset class="flash" id="fsUploadProgress1">
						<h3><bright:cmsWrite identifier="subhead-upload-file" filter="false"/></h3>
					</fieldset>
				</div>
				<div style="position:absolute;">
					<span id="spanButtonPlaceholder"></span>
				</div>
				<input type="button"  value="<bright:cmsWrite identifier="button-upload-file" filter="false"/>" onclick="upload1.selectFiles()" style="width:100px"/>
				<input id="btnCancel1" type="button" value="<bright:cmsWrite identifier="button-cancel-upload" filter="false"/>"  onclick="cancelQueue(upload1);" disabled="disabled" style="width:100px"/>
				<input id="btnDelete" type="button" value="<bright:cmsWrite identifier="button-remove-file" filter="false"/>" disabled="disabled" onclick="reloadPage();" style="width:100px"/><br />

			</form>
		</c:if>
		
		<br/>
		<c:set var="url">viewUploadAssetFile?uploadToolOption=basic<c:if test="${assetForm.parentId>0}">&parentId=<c:out value="${assetForm.parentId}"/></c:if><c:if test="${assetForm.peerId>0}">&peerId=<c:out value="${assetForm.peerId}"/></c:if></c:set>
		<bright:cmsWrite identifier="snippet-use-simple-upload" filter="false" replaceVariables="true"/>
		
	</c:if>
	
	<c:if test="${empty assetForm.uploadToolOption || assetForm.uploadToolOption=='basic'}">
		<bright:cmsWrite identifier="intro-single-upload" filter="false" />
		<logic:equal name="allowEmptyAssets" value="true"><p><bright:cmsWrite identifier="snippet-add-file-later" filter="false"/></p></logic:equal>
	</c:if>
	
	<c:if test="${not empty assetForm.possibleFileFormats}">
		<div class="warning"><bright:cmsWrite identifier="snippet-select-file-type" filter="false"/></div>
	</c:if>
	
	<html:form enctype="multipart/form-data" action="uploadAssetFile" method="post" styleClass="floated vsmallLabel">
		<bright:refDataList componentName="AttributeManager" methodName="getStaticAttribute" argumentValue="file" id="attFile"/>
		
		<input type="hidden" name="forceEmptyAsset" id="forceEmptyAsset" value="false"/>
		
		<c:if test="${!allowEmptyAssets}">
			<html:hidden name="assetForm" property="emptyAsset"/>
		</c:if>
		
		<html:hidden name="assetForm" property="deferEntitySelection"/>
		<html:hidden name="assetForm" property="parentId"/>
		<html:hidden name="assetForm" property="peerId"/>
		<html:hidden name="assetForm" property="entityPreSelected"/>
		<html:hidden name="assetForm" property="asset.entity.id"/>
		<html:hidden name="assetForm" property="asset.entity.name"/>
		<html:hidden name="assetForm" property="uploadToolOption"/>
		
		<%-- hidden fields for pre selecting category when uploading an asset from the browse page --%>
		<html:hidden name="assetForm" property="addingFromBrowseCatId" />
		<html:hidden name="assetForm" property="addingFromBrowseTreeId" />

		<%@include file="inc_cat_extension_hiddens.jsp"%>

			<c:if test="${assetForm.catExtensionCatId > 0}"><div class="info"><bright:cmsWrite identifier="snippet-category-extension-asset" filter="false" /></div></c:if>
				
			<%-- Entity field --%>
			<c:if test="${not empty assetForm.entities && !assetForm.deferEntitySelection}">
				<bean:size name="assetForm" property="entities" id="numEntities"/>
				<logic:equal name="numEntities" value="1">
					<html:hidden name="assetForm" property="selectedAssetEntityId"/>
				</logic:equal>
				<logic:greaterThan name="numEntities" value="1">
					
					<label for="entity"><bright:cmsWrite identifier="label-asset-type" filter="false"/></label>
					
					<bean:define id="entities" name="assetForm" property="entities"/>
					<bean:parameter id="selectedEntityId" name="selectedEntityId" value="-1"/>
					<c:if test="${selectedEntityId<0 && assetForm.selectedAssetEntityId>0}"><c:set var="selectedEntityId" value="${assetForm.selectedAssetEntityId}"/></c:if>
					<html:select name="assetForm" property="selectedAssetEntityId" styleId="entity" style="width:auto;" onchange="applyEntitySelection();">
						<logic:iterate name='entities' id='currentEntity'>
							<option value="<bean:write name='currentEntity' property='id'/>" <c:if test="${selectedEntityId > 0 && selectedEntityId == currentEntity.id}">selected</c:if>><bean:write name='currentEntity' property='name'/></option>
						</logic:iterate>
					</html:select>
					<script type="text/javascript">
						var allowFiles = [];
						<logic:iterate name="allowFiles" id="allowFile">
							allowFiles = allowFiles.concat(<c:out value="${allowFile}"/>);
						</logic:iterate>
					</script>
					<br />
				</logic:greaterThan>
			</c:if>
			<c:if test="${empty assetForm.entities}">
				<html:hidden name="assetForm" property="selectedAssetEntityId"/>
			</c:if>
	
			<c:if test="${empty assetForm.possibleFileFormats && allowEmptyAssets}">
				<label for="emptyAsset"><bright:cmsWrite identifier="label-add-file-later" filter="false"/></label>
				<input type="checkbox" name="emptyAsset" id="emptyAsset" value="true" onclick="changeButtonText(this.checked);" class="checkbox" />
				<br />
			</c:if>
			
			<c:if test="${not empty assetForm.possibleFileFormats}">
				<label><bean:write name="attFile" property="label"/>: </label>
				<div class="holder"><bean:write name="assetForm" property="uploadedFilename"/></div>
				<br />
				
				<label for="fileFormat"><bright:cmsWrite identifier="label-file-format" filter="false"/></label>
				<html:hidden name="assetForm" property="tempFileLocation"/>
				<bean:define id="fileFormats" name="assetForm" property="possibleFileFormats"/>
				<html:select name="assetForm" property="asset.format.id" styleId="fileFormat">
					<html:optionsCollection name="fileFormats" value="id" label="name"/>
				</html:select>
				<br />
			</c:if>
			
			<%-- File field --%>
			<c:if test="${empty assetForm.possibleFileFormats && (empty assetForm.uploadToolOption || assetForm.uploadToolOption=='basic')}">
				<div id="fileBlock"><label for="file"><bean:write name="attFile" property="label"/>:</label>
				<html:file name="assetForm" property="file" styleClass="file" styleId="file" />
				<c:set var="url">viewUploadAssetFile?uploadToolOption=advanced<c:if test="${assetForm.parentId>0}">&parentId=<c:out value="${assetForm.parentId}"/></c:if><c:if test="${assetForm.peerId>0}">&peerId=<c:out value="${assetForm.peerId}"/></c:if></c:set>
				<div id="uploaderLink">
					<span id="alternativeUploadClicker" onclick="var sLoc = 'viewUploadAssetFile?uploadToolOption=flash&peerId=<c:out value='${assetForm.peerId}'/><c:if test="${assetEntitiesEnabled}">&selectedEntityId='+document.getElementById('entity').value;</c:if><c:if test="${!assetEntitiesEnabled}">';</c:if> location=sLoc; return false;"><bright:cmsWrite identifier="snippet-use-alternative-upload" filter="false" replaceVariables="true"/></span>
				</div>
				</div>
				<br />
			</c:if>
			
			<bean:parameter name="conditionsAccepted" id="tsandcs" value="false"/>
			<bright:applicationSetting id="readTsCs" settingName="mustReadTsCsBeforeUpload"/>
			<c:choose>
				<c:when test="${empty assetForm.possibleFileFormats && readTsCs=='true'}">
					<br />
					<c:if test="${tsandcs == true}">
						<input type="checkbox" name="conditionsAccepted" id="accept" checked="checked" />
					</c:if> 
					<c:if test="${tsandcs == false}">
						<html:checkbox name="assetForm" styleClass="checkbox" property="conditionsAccepted" styleId="accept" />
					</c:if> 
					<bright:refDataList id="copyItem" componentName="ListManager" methodName="getListItem" argumentValue="tsandcs-checkbox-upload" /> 
					<label for="accept" class="after" style="width:auto !important; padding-top:1px"><bean:write	name="copyItem" property="body" filter="false" /></label>
					<br />
				</c:when>
				<c:otherwise>
					<html:hidden name="downloadForm" property="conditionsAccepted" value="1" />
				</c:otherwise>
			</c:choose>
			
		<c:if test="${enableTemplates && not empty assetForm.templates}">
			<label for=""><bright:cmsWrite identifier="label-template" filter="false"/></label>
				
			<select name="templateId" id="templateSelect" style="width:88px">
				<option value="-1"><bright:cmsWrite identifier="snippet-choose-template" filter="false"/></option>
				<logic:iterate name="assetForm" property="templates" id="template">
					<option value="<bean:write name='template' property='id'/>"><bean:write name="template" property="name" filter="false"/></option>
				</logic:iterate>
			</select>
			<noscript><input type="submit" name="submit" value="Go" class="button flush" /></noscript>
			<script type="text/javascript">
				// non javascript version is narrower
				document.getElementById('templateSelect').style.width = "118px";
			</script>
			<br />
		</c:if>
			

		<div class="hr">&nbsp;</div>
		<input type="submit" name="saveButton" class="button flush" id="submitButton" value="<c:if test="${empty assetForm.uploadToolOption || assetForm.uploadToolOption=='basic'}"><bright:cmsWrite identifier="button-upload" filter="false" /></c:if><c:if test="${not (empty assetForm.uploadToolOption || assetForm.uploadToolOption=='basic')}"><bright:cmsWrite identifier="button-next" filter="false" /></c:if>" onclick="savePressed();" />
		<c:if test="${assetForm.parentId>0}">
			<a href="../action/viewAsset?id=<bean:write name='assetForm' property='parentId'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		</c:if>
		
		<div id="savingDiv" style="text-align:center; clear:left; display:none;">
			<bright:cmsWrite identifier="snippet-please-wait-upload" filter="false"/>
		</div>
	</html:form>
	

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>
