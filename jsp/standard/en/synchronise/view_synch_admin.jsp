<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="canPublish" settingName="allow-publishing"/>
<bright:applicationSetting id="publishUrl" settingName="publish-to-url"/>
<bright:applicationSetting id="synchHour" settingName="synch-assets-hour-of-day"/>



<head>
	<bean:define id="pagetitle" value="System"/>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle"/> </title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value="system"/>
	<bean:define id="tabId" value="synch"/>

</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../updater/inc_system_tabs.jsp"%>
	
	<h3>Asset Synchronisation/Publishing.</h3>	
	
	<div class="warning">If you are about to run a substantial manual synchronisation then please backup both the sending and receiving databases. This will enable an easy rollback / recovery if there are any problems with the synchronisation.</div>
	
	<p>
		<bright:cmsWrite identifier="items" filter="false" case="mixed" /> within access levels flagged for syncronisation will be published to the <bright:cmsWrite identifier="app-name" filter="false"/> at <b><i><bean:write name="publishUrl"/></i></b> 
	</p>
	<c:choose>
		<c:when test="${synchHour < 0}">
			<p>
				Currently, automatic publishing of <bright:cmsWrite identifier="items" filter="false" /> has not been scheduled.  This can be set via the <em>synch-hour-of-day</em>  setting within ApplicationSettings.properties.
			</p>	
		</c:when>
		<c:otherwise>
			<p>
				Currently, automatic publishing of <bright:cmsWrite identifier="items" filter="false" /> has been scheduled to occur at <bean:write name="publishUrl"/>:00.  This can be changed via the <em>synch-hour-of-day</em>  setting within ApplicationSettings.properties.
			</p>	
		</c:otherwise>		
	</c:choose>
	<p>
		To manually publish the <bright:cmsWrite identifier="items" filter="false" /> you can click the button below.  Please be patient as the publishing process can take some time depending on the number of <bright:cmsWrite identifier="items" filter="false" /> and the speed of your connection.
	</p>
	<p>
		<form action="publishFlaggedAssets" method="get">
			<input type="submit" value="Publish" class="button flush" id="publishButton" onclick="return confirm('Are you sure you wish to manually publish flagged assets?');"></input>
		</form>
	</p>				
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>