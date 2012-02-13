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



<c:set var="pagetitle" value="System" />		



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="system"/>
	<bean:define id="helpsection" value="updates"/>
	<bean:define id="tabId" value="update"/>

</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../updater/inc_system_tabs.jsp"%>
	<p>The following updates are available.</p>



			<logic:iterate name="applicationUpdateForm" property="versionDetails" id="versionDetail">
				<logic:equal name="versionDetail" property="private" value="false">
					<p>
					<strong>Version <bean:write name="versionDetail" property="versionNumber"/></strong>
					<logic:equal name="versionDetail" property="updaterChanged" value="true">
					<span class="error">
							(requires restart)
					</span>
					
					</logic:equal>
					<br />
					<logic:iterate name="versionDetail" property="changeDescriptions"  id="changeDesc">
							<bean:write name="changeDesc"/>
					</logic:iterate>
					</p>
				</logic:equal>
			</logic:iterate>	


	<p>
		<form name="applicationUpdateForm" action="startApplicationUpdate" method="get">
			<div class="hr"></div>
			<div class="warning">
				<strong>Before beginning the update please note:</strong>
				<ul>
					<li>
						Failed updates can stop <bright:cmsWrite identifier="app-name" filter="false"/> from working properly. 
						Make sure that recent backups of the <bright:cmsWrite identifier="app-name" filter="false"/> database and asset files have been taken, 
						so that the previous state can be restored in case of a failed update.<br/>
						&nbsp;a) Backup the <bright:cmsWrite identifier="app-name" filter="false"/> database schema using your preferred tool for your database.<br/>
						&nbsp;b) Take a backup of the webapp folder <em>[tomcat]/webapps/asset-bank</em>. 
						You can omit the <em>files</em> subdirectory if it is large, since updates will not affect this data.
						However you should ensure you have a recent backup of the files data in any case.<br/>
						<strong>If the update fails, then you should:</strong><br/>
						&nbsp;a) Restore the database using the backup.<br/>
						&nbsp;b) Copy the backed up webapp folder over <em>[tomcat]/webapps/asset-bank</em>, overwriting where necessary.
					</li>
					<li>
						You should have the username and password used to access the Tomcat Manager associated with this <bright:cmsWrite identifier="app-name" filter="false"/> (needed for the application restart) 
						or you are able to restart the Tomcat service.
					</li>
					<li>
						Automatic reloading by Tomcat of the <bright:cmsWrite identifier="app-name" filter="false"/> application must be DISABLED.<br/>
						See <a href="http://www.assetbank.co.uk/support/documentation/install/install_windows.html" target="_new">http://www.assetbank.co.uk/support/documentation/install/install_windows.html</a>, section 5.
					</li>
				</ul> 		
			</div>
			<p>
				If you are not sure then contact Bright Interactive for assistance.
			Call us on&nbsp; <strong>01273 669 490</strong> or email us at&nbsp; <a href="mailto:support@assetbank.co.uk">support@assetbank.co.uk</a>.				
			</p>
			<bean:parameter name="version" id="version" value=""/>
			<input type="hidden" name="version" value="<bean:write name='version'/>"/>
			<input type="submit" value="Start Update" class="button flush" id="startButton" onclick="return confirm('Are you sure you wish to begin the update?');"></input>
		</form>
	</p>
					
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>