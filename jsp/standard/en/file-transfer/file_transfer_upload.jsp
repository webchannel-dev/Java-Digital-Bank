<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Francis Devereux	16-Jan-2009		Created from ../asset-admin/upload_asset_file.jsp
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier="title-file-transfer-step-1" /></title>
	<%@include file="../inc/head-elements.jsp"%>

	<script src="../js/lib/swfupload/AC_OETags.js" type="text/javascript"></script>
	<%@include file="../inc/flashuploader_version_check-js.jsp" %>
	
<logic:equal name="fileTransferForm" property="uploadToolOption" value="browser">
	<script language="JavaScript" type="text/javascript">
		<!--
		// Version check based upon the values entered above in "Globals"
		var hasReqestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);
	
		// Check to see if the version meets the requirements for playback
		var sWinLoc = "";
		if (hasReqestedVersion) {
			sWinLoc = "viewFileTransferUpload?uploadToolOption=flash";
		} else {
			sWinLoc = "viewFileTransferUpload?uploadToolOption=applet";
		}
		
		window.location = sWinLoc;
		// -->
	</script>
</logic:equal>
<logic:equal name="fileTransferForm" property="uploadToolOption" value="flash">
	<c:set var="singleUpload" value="${true}"/>
	<%@ include file="../inc/flashuploader-js.jsp"%>
</logic:equal>
	
	<script type="text/javascript">
	<!-- 
	function savePressed()
	{
		document.getElementById('savingDiv').style.display="block";
	}

	function changeButtonText (bChecked)
	{
	<c:if test="${empty fileTransferForm.uploadToolOption}">
		if (!bChecked)
		{
			document.getElementById('submitButton').value = 'Upload';
		}
		else
		{
			document.getElementById('submitButton').value = 'Next';
		}
	</c:if>
	}
	
	function reloadPage()
	{
		document.location.reload();
	}
	
	//-->
	</script>

	<bean:define id="section" value="file-transfer"/>
</head>

<body onLoad="<logic:equal name="fileTransferForm" property="uploadToolOption" value="flash"> uploader();</logic:equal>">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline">
		<bright:cmsWrite identifier="heading-file-transfer-step-1" />
	</h1> 
	<logic:equal name="fileTransferForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="fileTransferForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>
			<logic:notEqual name="fileTransferForm" property="noUploadFileSpecified" value="true">
				<br /><bright:cmsWrite identifier="snippet-browse-file-again" filter="false"/>
			</logic:notEqual>
		</div>
	</logic:equal>
	
	<c:if test="${fileTransferForm.uploadToolOption=='flash' || fileTransferForm.uploadToolOption=='applet'}">
		
		<bright:cmsWrite identifier="snippet-file-transfer-flashjava-upload-instructions" filter="false" />
		
		<!-- Display applet if specified in properties -->
		<logic:equal name="fileTransferForm" property="uploadToolOption" value="applet">
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
		<logic:equal name="fileTransferForm" property="uploadToolOption" value="flash">
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
		</logic:equal>
		
		<br/>
		<c:set var="url">viewFileTransferUpload</c:set>
		<bright:cmsWrite identifier="snippet-use-simple-upload" filter="false" replaceVariables="true"/>
		
		<div class="hr"></div>
	</c:if>
	
	<c:if test="${empty fileTransferForm.uploadToolOption}">
		<bright:cmsWrite identifier="snippet-file-transfer-simple-upload-instructions" filter="false"/>
	</c:if>
	
	<html:form enctype="multipart/form-data" action="fileTransferUpload" method="post">
		
		<input type="hidden" name="forceEmptyAsset" id="forceEmptyAsset" value="false"/>

		<html:hidden name="fileTransferForm" property="uploadToolOption"/>
		
		<table class="form" cellspacing="0" cellpadding="0" border="0">
			
			<%-- File field --%>
			<c:if test="${empty fileTransferForm.uploadToolOption}">
				<tr>
					<th style="width:30px;">
						<bright:cmsWrite identifier="label-file"/>
					</th>
					<td>
						<html:file name="fileTransferForm" property="file" styleClass="file" styleId="file" size="40"/>
						<c:set var="url">viewFileTransferUpload?uploadToolOption=browser</c:set>
						<span id="uploaderLink"><bright:cmsWrite identifier="snippet-use-alternative-upload" filter="false" replaceVariables="true"/></span>
					</td>
				</tr>
			</c:if>
			
			<bean:parameter name="conditionsAccepted" id="tsandcs" value="false"/>
			<bright:applicationSetting id="readTsCs" settingName="mustReadTsCsBeforeUpload"/>
			<c:choose>
				<c:when test="${readTsCs=='true'}">
					<tr>
						<th></th>
						<td>
							<c:if test="${tsandcs == true}">
								<input type="checkbox" name="conditionsAccepted" id="accept" style="width: auto; display: inline; margin-bottom: 0px;" checked="checked" />
							</c:if> 
							<c:if test="${tsandcs == false}">
								<html:checkbox name="fileTransferForm" property="conditionsAccepted" styleId="accept" style="width: auto; display: inline; margin-bottom: 0px; " />
							</c:if> 
							<label for="accept"><bright:cmsWrite identifier="tsandcs-checkbox-upload" filter="false"/></label>
						</td>						
					</tr>
				</c:when>
				<c:otherwise>
					<html:hidden name="fileTransferForm" property="conditionsAccepted" value="1" />
				</c:otherwise>
			</c:choose>
			
			<tr>
				<td></td>
				<td>
					<input type="submit" name="saveButton" class="button flush" id="submitButton" value="<c:if test="${empty fileTransferForm.uploadToolOption}"><bright:cmsWrite identifier="button-upload" filter="false" /></c:if><c:if test="${not empty fileTransferForm.uploadToolOption}"><bright:cmsWrite identifier="button-next" filter="false" /></c:if>" onclick="savePressed();">
				</td>
			</tr>	
	
		</table>
	
		<div id="savingDiv" style="margin-left: 46px; display:none;">
			<bright:cmsWrite identifier="snippet-please-wait-upload" filter="false"/>
		</div>
	</html:form>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>
