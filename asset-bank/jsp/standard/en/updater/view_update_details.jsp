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

<logic:notPresent name="validUpdaterLicense">
	<bean:define id="validUpdaterLicense" value="false" />
</logic:notPresent>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="system"/>
	<bean:define id="helpsection" value="updates"/>
	<bean:define id="tabId" value="update"/>

</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 
	<%@include file="../updater/inc_system_tabs.jsp"%>

	<c:if test="${!validUpdaterLicense}">
		<div class="warning"> You cannot perform an upgrade at this stage. </div>
		<p>If you are within Asset Banks Support and Upgrades Programme then please email your existing license to <a href="mailto:support@assetbank.co.uk">support@assetbank.co.uk</a> to enable it to be updated for upgrades.</p>
		<p>The license file can be found here:<br />
		[tomcat home]\webapps\asset-bank\WEB-INF\classes\license.lic</p>
		<p>If you are not currently part of our Support and Upgrades Programme, but would like more information, then please contact us: <a href="mailto:support@assetbank.co.uk">support@assetbank.co.uk</a></p>
		<div class="hr"></div>	
	</c:if>


	<h3>The following updates are available.</h3>
			<logic:iterate name="applicationUpdateForm" property="versionDetails" id="versionDetail">
				<logic:equal name="versionDetail" property="private" value="false">
					<p>
					<strong>Version <bean:write name="versionDetail" property="versionNumber"/></strong>
					<br />
					<logic:iterate name="versionDetail" property="changeDescriptions"  id="changeDesc">
							<bean:write name="changeDesc"/>
					</logic:iterate>
					</p>
				</logic:equal>
			</logic:iterate>	


			<div class="hr"></div>
	<div class="info">
		<strong>To perform the update please:</strong>
				<ul>
					<li>
				Follow the instructions in our knowledge base article: <a href="http://www.assetbank.co.uk/go/knowledgebase/upgrade-asset-bank.html" target="_new">How do I upgrade Asset Bank?</a>
					</li>
				</ul> 		
			</div>
			<p>
				If you are not sure then contact Bright Interactive for assistance.
		Call us on&nbsp; <strong>01273 923 150</strong> or email us at&nbsp; <a href="mailto:support@assetbank.co.uk">support@assetbank.co.uk</a>.				
			</p>
					
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>