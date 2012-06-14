<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan		23-Nov-2005		Created.
	 d2		Ben Browning	24-Mar-2006		Tidied up HTML/CSS
	 d3		Ben Browning	20-Jun-2007		Use login page layout, and custom logos
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%-- Populate the applicationUrl variable, so that it can be used by bright:cmsWrite replaceVariables="true --%>
<bright:applicationUrl/>

<head>
	
	<title><bright:cmsWrite identifier="e-title-payment" filter="false" replaceVariables="true" /></title> 
	<bean:define id="section" value="lightbox"/>
	<bean:define id="pagetitle" value="Payment"/>
	<%@include file="inc_styles.jsp"%>
</head>

<body id="loginPage">

	<div class="leftShadow">
		<div class="rightShadow">
		   <div id="loginPanel">
	
				<div class="logo">
					<%@include file="../customisation/logo_link.jsp"%>
				</div>
		
				
				<div class="loginForm">
				<bright:cmsWrite identifier="e-payment-invalid" filter="false" replaceVariables="true" />

				</div>	<!-- end of loginForm -->
	
			</div>   <!-- end of loginPanel -->
		</div>
	</div>
</body>

</body>
</html>