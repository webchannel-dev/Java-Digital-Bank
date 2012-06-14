<%@include file="../inc/doctype_html.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier="title-asset-file-editor"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value=""/>
	<bean:define id="helpsection" value="client-side-edit"/>
	<bean:parameter id="assetId" name="assetId"/>
	<bean:parameter id="returnUrl" name="returnUrl"/>
	<bean:parameter id="checkedOutToUserId" name="checkedOutToUserId" value="0"/>
	<bean:parameter id="step" name="step" value="0"/>
	<bean:parameter id="isPreviousStep" name="isPreviousStep" value="false"/>
	
</head>

<body id="clientSideEditPage">

	<%@include file="../inc/body_start.jsp"%>

	<h1 class="underline"><bright:cmsWrite identifier="subhead-file-check-out"/></h1>

	<p><bright:cmsWrite identifier="snippet-edit-file-in-applet"/></p>

	<applet code="com.bright.assetbank.clientsideedit.applet.ui.AssetEditorLauncher" name="AssetEditorLauncher" archive= "../tools/assetsourceeditor/assetsourceeditor.jar,../tools/assetsourceeditor/asset-bank-rest-api-reprs-1.0.0-SNAPSHOT.jar,../tools/assetsourceeditor/jersey-core-1.2.jar,../tools/assetsourceeditor/jersey-client-1.2.jar,../tools/assetsourceeditor/swing-worker-1.2.jar,../tools/assetsourceeditor/jsr311-api-1.1.1.jar,../tools/assetsourceeditor/jaxb-api.jar,../tools/assetsourceeditor/jaxb-impl.jar,../tools/assetsourceeditor/mail.jar,../tools/assetsourceeditor/stax-api-1.0-2.jar,../tools/assetsourceeditor/jsr250-api.jar" width="600" height="250">
		<%--
		Disable codebase lookup.
		This makes the applet JVM load classes and resources from the JARs specified above only, and stops it from
		falling back to the "codebase" and making HTTP requests for files that don't exist on the server, which caused
		errors in the server logs like:
		ERROR org.apache.struts.action.RequestProcessor - Invalid path was requested /com/bn2web/common/service/GlobalApplication.class
		see http://docs.oracle.com/javase/6/docs/technotes/guides/plugin/developer_guide/special_attributes.html
		--%>
		<param name="codebase_lookup" value="false">

		<param name = "logDebug" value = "false"/>
		<param name = "assetId" value = "<c:out value="${assetId}"/>"/>
		<param name = "userId" value = "<c:out value="${userprofile.user.id}"/>"/>
		<param name = "checkedOutToUserId" value = "<c:out value="${checkedOutToUserId}"/>"/>
		<param name = "checkoutLabel" value = "Check out &amp; Edit File"/>
		<param name = "checkinLabel" value = "Check in File"/>
		<param name = "openFileToViewLabel" value = "Open File (view only)"/>
		<param name = "resumeEditLabel" value = "Open File (resume editing)"/>
		<param name = "versioningCheckboxLabel" value = "Check in File as a new version"/>
		<param name = "cancelLabel" value = "Cancel"/>
		<param name = "checkedOutAllowed" value = "<c:out value="${checkedOutAllowed}"/>"/>
		<param name = "versioningAllowed" value = "<c:out value="${versioningAllowed}"/>"/>
		<param name = "fontName" value = "Dialog"/>
		<param name = "fontSize" value = "12"/>
		<div class="error">
			<p><bright:cmsWrite identifier="snippet-applet-error"/></p>
		</div>
	</applet>

	<html:form action="clientSideEditPrepAction" enctype="multipart/form-data" method="post">
		
		<div style="min-height:45px">
		<input type="hidden" name="assetId" value="<bean:write name='assetId'/>" />
		<input type="hidden" name="step" value="<bean:write name='step'/>"/>
		<input type="hidden" name="checkedOutToUserId" value="<bean:write name='checkedOutToUserId'/>"/>
		<input type="hidden" name="returnUrl" value="<bean:write name='returnUrl'/>"/>
		
		</div>
		<div class="hr"></div>

		<a href="../action/viewAsset?id=<bean:write name='assetId'/>"class="backLink"><bright:cmsWrite identifier="link-back-to" filter="false"/> <bright:cmsWrite identifier="item"/></a>
		<c:if test="${isPreviousStep}">
			<input type="submit" class="button flush floated" name="back" value="Prev" /> 
		</c:if>

		<input type="submit" class="button flush floated" name="next" value="Next" disabled="disabled" /> 

		
		<br />
		
	</html:form>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>