<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1      Matt Woollard       11-Feb-2008     Replaced content with list items
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="showCommercialOptions" settingName="commercial-options"/>
<bright:applicationSetting id="showPriceBands" settingName="price-bands"/>


<head>
	<title><bright:cmsWrite identifier="e-title-purchases" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/calendar.js" type="text/javascript"></script>
	
	<bean:define id="section" value="purchases"/>
	<bean:define id="tabId" value="myPurchases"/>
</head>

<body>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="e-subhead-purchases" filter="false"/></h1> 

	<%@include file="../ecommerce/inc_my_purchase_tabs.jsp"%>
	
	<p>
		<bright:cmsWrite identifier="e-previous-purchases" filter="false"/>
	</p>	
	<ul>									
		<li>
			<bright:cmsWrite identifier="e-link-downloads" filter="false"/>
		</li>
		<li>
			<bright:cmsWrite identifier="e-link-orders" filter="false"/>
		</li>
	</ul>
	<br/>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>