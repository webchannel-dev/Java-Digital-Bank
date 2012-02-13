<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		04-Aug-2008		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="title-step1-upload" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script src="../js/lib/swfupload/AC_OETags.js" type="text/javascript"></script>
	<%@include file="../inc/flashuploader_version_check-js.jsp" %>
	
<logic:equal name="assetForm" property="uploadToolOption" value="browser">
	<script language="JavaScript" type="text/javascript">
		<!--
		// Version check based upon the values entered above in "Globals"
		var hasReqestedVersion = DetectFlashVer(requiredMajorVersion, requiredMinorVersion, requiredRevision);
	
		// Check to see if the version meets the requirements
		var sWinLoc = "";
		if (hasReqestedVersion) {
			sWinLoc = "viewUploader?uploadToolOption=flash";
		} else {
			sWinLoc = "viewUploader?uploadToolOption=applet";
		}
		
		<c:if test="${assetForm.parentId>0}">
			sWinLoc += '&parentId=<c:out value="${assetForm.parentId}"/>';
		</c:if>
		
		<c:if test="${entityId>0}">
			sWinLoc += '&entityId=<c:out value="${entityId}"/>';
		</c:if>
		
		window.location = sWinLoc;
		// -->
	</script>
</logic:equal>
<logic:equal name="assetForm" property="uploadToolOption" value="flash">
	<c:set var="singleUpload" value="${true}"/>
	<%@ include file="../inc/flashuploader-js.jsp"%>
</logic:equal>

<script language="JavaScript" type="text/javascript">
<!--	

var uploadedFileName <c:if test="${not empty chosenFile}">='<bean:write name="chosenFile" filter="false"/>'</c:if>;

function localUploadCompleteHandler(file)
{
	uploadedFileName = file.name;
	document.getElementById('btnDelete').disabled=false;
	applySelection();
}

function applySelection()
{
	if(uploadedFileName!=null)
	{
		window.opener.setUploadedFile(uploadedFileName);
	}
	window.close();
}


//-->
</script>	

</head>

<body id="popup" onLoad="uploader(); this.focus();">
	
	<c:if test="${not empty chosenFile}">
		<script type="text/javascript">//<!--
			applySelection();
		//--></script>
	</c:if>
	<c:if test="${empty chosenFile}">
		<h2><bright:cmsWrite identifier="heading-upload" filter="false"/></h2>
		
		<c:if test="${assetForm.uploadToolOption=='flash' || assetForm.uploadToolOption=='applet'}">
			
			<!-- Display applet if specified in properties -->
			<logic:equal name="assetForm" property="uploadToolOption" value="applet">
				
				<script type="text/javascript">
					window.resizeTo(690,360);
				</script>
				
				<br />
				<APPLET CODE="wjhk.jupload2.JUploadApplet" NAME="JUpload" ARCHIVE="../tools/jupload/wjhk.jupload.jar" WIDTH="640" HEIGHT="200" MAYSCRIPT></XMP>
				   <PARAM NAME = CODE VALUE = "wjhk.jupload2.JUploadApplet" >
				   <PARAM NAME = ARCHIVE VALUE = "../tools/jupload/wjhk.jupload.jar" >
				   <PARAM NAME = "type" VALUE = "application/x-java-applet;version=1.5">
				   <PARAM NAME = "postURL" VALUE = "../servlet/upload?single=true">
					<PARAM NAME = "lookAndFeel" VALUE = "system">
				   <PARAM NAME = "nbFilesPerRequest" VALUE = "1">	
				   <PARAM NAME = "afterUploadURL" VALUE = "viewUploader?uploadToolOption=applet&fileChosen=true">
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
			<logic:equal name="assetForm" property="uploadToolOption" value="flash">
			
				<form id="form1" action="index.php" method="post" enctype="multipart/form-data">
					<div id="uploadContainer" style="margin-bottom:4px;">
						<fieldset class="flash" id="fsUploadProgress1">
							<h3><bright:cmsWrite identifier="subhead-upload-file" filter="false"/></h3>
						</fieldset>
					</div>
					<div style="position:absolute;">
						<span id="spanButtonPlaceholder"></span>
					</div>
					<input type="button"  value="<bright:cmsWrite identifier="button-upload-file" filter="false"/>" onclick="upload1.selectFiles()" style="width:100px"/>
					<input id="btnCancel1" type="button" value="<bright:cmsWrite identifier="button-cancel-upload" filter="false"/>"  onclick="cancelQueue(upload1);" disabled="disabled" style="width:100px"/>
					<input id="btnDelete" type="button" value="<bright:cmsWrite identifier="button-remove-file" filter="false"/>" disabled="disabled" onclick="window.location.reload();" style="width:100px"/><br />

				</form>
			</logic:equal>
	
			<div class="hr"></div>
		</c:if>
		
		<form>
			<input type="button" class="button flush" id="closeButton" style="display:inline;" value="<bright:cmsWrite identifier="button-close" filter="false"/>" onclick="applySelection();"/>
		</form>
	</c:if>
	
</body>
</html>
