<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="showUpdates" settingName="application-updater"/>



<head>
	<bean:define id="pagetitle" value="System"/>

	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle"/> </title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value="system"/>
	<bean:define id="helpsection" value="developer"/>
	<bean:define id="tabId" value="developer"/>

</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../updater/inc_system_tabs.jsp"%>
	
	<h3>Developer Tools</h3>										

				<p>
				Any files you have customised within <bright:cmsWrite identifier="app-name" filter="false"/> need to be placed, with their existing directory structure, into the <em>'WEB-INF/manager-config/updater/customise/'</em> directory.</p>
				<p>After an update the actual application files are overwritten using those in your customise directory.</p>
				<p>To save you having to make the changes twice you can make the changes in the cutomise directory only
				and then manually overwrite the application files by clicking the button below.
				</p>

					<form name="applicationUpdateForm" action="resetCustomisations" method="get">
						<input type="submit" value="Overwrite" class="button flush" id="overwriteButton" onclick="return confirm('Are you sure you wish to overwrite using the files in your customise directory?');"></input>
					</form>


	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>