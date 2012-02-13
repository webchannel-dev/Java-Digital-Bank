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



<c:set var="pagetitle" value="Updates" />		



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
	

	<div class="warn">You cannot perform an upgrade at this stage. </div>

	<p>If you are within Asset Banks Support and Upgrades Programme then please email your existing license to <a href="mailto:support@assetbank.co.uk">support@assetbank.co.uk</a> to enable it to be updated for upgrades.</p>

	<p>The license file can be found here:<br />
	[tomcat home]\webapps\asset-bank\WEB-INF\classes\ApplicationSettings.properties</p>

	<p>If you are not currently part of our Support and Upgrades Programme, but would like more information, then please contact us: <a href="mailto:support@assetbank.co.uk">support@assetbank.co.uk</a></p>	
	

					
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>