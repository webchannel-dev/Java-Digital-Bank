<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		02-Feb-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
	 d3		Ben Browning	17-Feb-2006		HTML/CSS Tidy up
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="title-about" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="about"/>
</head>

<body>
	<%@include file="../inc/body_start.jsp"%>
	
	<bright:applicationSetting id="version" settingName="version"/>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-about" filter="false" /></h1> 
	
	<h3>Asset Bank Version <bean:write name="version"/></h3>
	<p>
		Asset Bank is a <a href="http://www.assetbank.co.uk/" target="_blank">digital asset management system</a> developed by <a href="http://www.bright-interactive.com" target="_blank">Bright Interactive</a>.<br />
		
		For more information, and to read the online documentation, please visit <a href="http://www.assetbank.co.uk" target="_blank">http://www.assetbank.co.uk</a>.
	</p>
	
	<%@include file="../customisation/about_copy.jsp"%>
	


	<%@include file="../inc/body_end.jsp"%>

</body>
</html>