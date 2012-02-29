<%@include file="../inc/doctype_html_admin.jsp" %>

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

<c:set var="pagetitle"><bright:cmsWrite identifier="e-title-orders" filter="false" replaceVariables="true" /></c:set>

<head>
	<title><bean:write name="pagetitle" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/calendar.js" type="text/javascript"></script>
	
	<bean:define id="section" value="orders"/>
	<bean:define id="tabId" value="orders"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	
	<%@include file="../ecommerce/inc_order_tabs.jsp"%>
	
	
	<p><bright:cmsWrite identifier="e-heading-order-administration" filter="false"/></p>										
	<p>
		<ul>									
			<c:if test="${showPriceBands}">
				<li>
					<bright:cmsWrite identifier="e-view-personal-orders" filter="false"/>
				</li>
			</c:if>
			<c:if test="${showCommercialOptions}">
				<li>
					<bright:cmsWrite identifier="e-view-commercial-orders" filter="false"/>
				</li>
			</c:if>	
		</ul>
	</p>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>