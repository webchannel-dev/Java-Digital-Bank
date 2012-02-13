<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Kevin Bennett		19-Jan-2006		Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="updateInProgress" settingName="application-update-in-progress"/>

<c:set var="pagetitle" value="System Update" />		

<%-- Note: consider carefully before adding functionality such as multilanguage to this page, since it should be kept
as simple as possible to avoid JSP errors --%>


<head>
	
	<title><bean:write name="pagetitle" filter="false"/></title> 
	<link href="../css/standard/default.css" rel="stylesheet" type="text/css" media="all" />
	<logic:equal name="applicationUpdateForm" property="updateInProgress" value="true">
		<meta HTTP-EQUIV="refresh" CONTENT="5;URL=viewApplicationUpdateProgress"></meta>
	</logic:equal>
	<bean:define id="section" value="system"/>
	<bean:define id="helpsection" value="updates"/>
	<bean:define id="tabId" value="update"/>
</head>

<body id="adminPage" style="text-align: left; padding: 20px;">
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:notEmpty name="applicationUpdateForm" property="updateProgressMessages">
		<p>Update progress...</p>
		
		<p>
			<logic:iterate name="applicationUpdateForm" property="updateProgressMessages" id="progressMessage">
				<bean:write name="progressMessage"/><br/>
			</logic:iterate>
		</p>
		<br/><br/>
	</logic:notEmpty>
	
	<c:choose>
		<c:when test="${applicationUpdateForm.updateSucceeded}">
			<h2>Update Succeeded</h2>
			<p>
				<bright:cmsWrite identifier="app-name" filter="false"/> update has completed successfully.  
			</p>
			<c:set var="hasPostMessages" value="false" />
			<ul>
				<logic:notEmpty name="applicationUpdateForm" property="versionDetails">
				<logic:iterate name="applicationUpdateForm" property="versionDetails" id="versionDetail">
					<logic:equal name="versionDetail" property="private" value="false">
						<logic:iterate name="versionDetail" property="successMessages"  id="successMsg">
							<li>
								<bean:write name="successMsg"/>
							</li>
							<c:set var="hasPostMessages" value="true" />
						</logic:iterate>
					</logic:equal>
				</logic:iterate>	
				</logic:notEmpty>
			</ul>
			<br/>
			<c:if test="${hasPostMessages}">
				<div class="warning">
					<strong>Please note down the instructions given above, or print this page for reference.</strong>
				</div>
			</c:if>
			<p>
				In order to complete the update you must <a href="<bean:write name="applicationUpdateForm" property="tomcatReloadUrl"/>">restart the application </a> immediately.<br/><br/>
				If you have not previously entered and saved them in this browser you will be prompted for your Tomcat Manager username and password.<br/>
				After you enter them if <bright:cmsWrite identifier="app-name" filter="false"/> has sucessfully restarted you should see the response 
				<p>
					&quot;OK - Reloaded application at context path <bean:write name="applicationUpdateForm" property="contextPath"/>&quot;
				</p>
				Upon seeing this you can log back in to <bright:cmsWrite identifier="app-name" filter="false"/> via the usual Url.
			</p>
			<p>
				Alternatively, you can restart the Tomcat service.
			</p>
			<logic:equal name="applicationUpdateForm" property="upToDate" value="false">		
				<div class="warning">
					There are still updates available for <bright:cmsWrite identifier="app-name" filter="false"/>.  Please return to the update section after restarting, to continue the update process.
				</div>
			</logic:equal>
		</c:when>
		<c:when test="${applicationUpdateForm.updateFailed}">
			<h2>Update Failed</h2>
			<p>
				The following errors occured during the <bright:cmsWrite identifier="app-name" filter="false"/> update
			</p>
			<p>
				<ul>
					<li>
						<bean:write name="applicationUpdateForm" property="failureError"/>
					</li>					
					<logic:iterate name="applicationUpdateForm" property="updateErrorMessages" id="errorMessage">
						<li>
							<bean:write name="errorMessage"/><br/>
						</li>
					</logic:iterate>					
				</ul>
			</p>
			<br/>
			<p>
				You should have taken a backup of your database and the webapp files. <br/>
				Restore the database backup using your database tools, and copy the backed up webapp over the folder  
				<em>[tomcat]/webapps/asset-bank</em>, overwriting where necessary.<br/><br/>
				If you require assistance please contact Bright Interactive:<br/>
				Call us on&nbsp; <strong>01273 669 490</strong> or
				email us at&nbsp; <a href="mailto:support@assetbank.co.uk">support@assetbank.co.uk</a>
			</p>
		</c:when>
		<c:when test="${updateInProgress && !applicationUpdateForm.updateInProgress}">
			<h2>Update In Progress</h2>
			<bright:applicationSetting id="updateType" settingName="updateType"/>
			<p>
				The <bright:cmsWrite identifier="app-name" filter="false"/> update is in progress. In order to continue, click the button below...    
			</p>
			<form name="applicationUpdateForm" action="startApplicationUpdate" method="get">
				<input type="hidden" name="version" value="<bean:write name='updateType'/>"/>
				<input type="submit" value="Continue Update" class="button" style="margin-left:0px;" id="startButton"/>
			</form>
		</c:when>
	</c:choose>
					
	
</body>
</html>