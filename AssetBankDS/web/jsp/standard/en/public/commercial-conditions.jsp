<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan		31-Jan-2006		Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Commercial Terms &amp; Conditions</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="conditions"/>
</head>

<body id="conditionsPage">

	<%@include file="../inc/body_start.jsp"%>

	<div class="copy">
	
	<%@include file="../customisation/commercial_conditions_copy.jsp"%>
				
	</div>								
		
	<%@include file="../inc/body_end.jsp"%>
				

</body>
</html>