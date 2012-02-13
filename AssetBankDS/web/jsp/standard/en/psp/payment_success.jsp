<%@include file="../inc/doctype_html.jsp" %>

<%-- History:
	 d1		Steve Bryan		16-Nov-2004		Created.
	 d2		Ben Browning	24-Mar-2006		Tidied up HTML/CSS
	 d3		Ben Browning	20-Jun-2007		Use login page layout, and custom logo
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="subscription" settingName="subscription"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="applicationUrl" settingName="application-url"/>


<head>
	
	<title><bright:cmsWrite identifier="e-title-payment" filter="false" replaceVariables="true" /></title> 
	<bean:define id="section" value="lightbox"/>
	<%@include file="../inc/head-elements.jsp"%>
</head>



<body id="loginPage" >

<bean:parameter id="trans_id" name="trans_id" value="" />
<bean:parameter id="transId" name="transId" value="" />

	<div class="leftShadow">
		<div class="rightShadow">
		   <div id="loginPanel">
	
				<div class="logo">
					<%@include file="../customisation/logo_link.jsp"%>
				</div>
				
				<div class="loginForm">	
      <h1><bright:cmsWrite identifier="e-heading-payment-success" filter="false"/></h1> 
		
	
      <p><bright:cmsWrite identifier="e-payment-success" filter="false"/></p>
		
		<c:choose>
			<c:when test="${subscription}">
				<bright:cmsWrite identifier="e-return-to-subscriptions" filter="false" replaceVariables="true" />			
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="e-download-items-from-basket" filter="false" replaceVariables="true" />
			</c:otherwise>
		</c:choose>   
      			
		<p>
			<bright:cmsWrite identifier="app-name" filter="false"/> ID: <bean:write name='trans_id' /><br />
			PSP Transaction ID: <bean:write name='transId' /><br />
		</p> 

				</div>	<!-- end of loginForm -->
	
			</div>   <!-- end of loginPanel -->
		</div>
	</div>
</body>

</body>
</html>