<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Cancel Checkout</title>
	<jsp:include flush="true" page="../inc/head-elements.jsp"/>
	<bean:define id="section" value=""/>
	<bean:define id="helpsection" value="view_asset"/>
</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>

	<h1><bright:cmsWrite identifier="title-cancel-checkout"/></h1>		
	
	<bean:parameter id="id" name="id"/>
	<bean:parameter id="returnUrl" name="returnUrl" value=""/>

	<logic:empty name="returnUrl">
		<bean:define id="returnUrl" value="../action/viewAsset?id=${id}"/>
	</logic:empty>

	<div class="warning">
			<p><bright:cmsWrite identifier="snippet-checkout-confirmation"/></p>
			<p><bright:cmsWrite identifier="snippet-checkout-changes-lost"/></p>
	</div>
	
	<form action="../action/cancelAssetCheckout">
		<input type="hidden" name="id" value="<bean:write name='id'/>">
		<input type="hidden" name="returnUrl" value="<bean:write name='returnUrl'/>">
		<input class="button flush floated" type="submit" value="Cancel checkout anyway" />
		<a href="<bean:write name='returnUrl'/>" class="cancelLink"><bright:cmsWrite identifier="link-back" filter="false"/></a>
		<br />
	</form>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>