<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Steve Bryan		19-Jan-2007		Created.
	d2      Matt Woollard       11-Feb-2008     Replaced content with list items
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%@include file="inc_checkout_settings.jsp"%>

<head>
	<title><bright:cmsWrite identifier="e-title-order-payment" filter="false" replaceVariables="true" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="purchases"/>
</head>

<body id="checkout">
	<%@include file="../inc/body_start.jsp"%>
	<%@include file="../customisation/payment_gateway_logo.jsp"%>
	
	<h1><bright:cmsWrite identifier="e-heading-order-payment" filter="false"/></h1> 
	
	<div class="head">
			<a href="../action/viewApprovedAssets"><bright:cmsWrite identifier="e-link-back-to-purchases" filter="false"/></a>
	</div>	
	<%@include file="../customisation/credit_card_logos.jsp"%>
	
<bright:cmsWrite identifier="e-already-paid" filter="false"/>
						
<%@include file="../inc/body_end.jsp"%>
</body>
</html>