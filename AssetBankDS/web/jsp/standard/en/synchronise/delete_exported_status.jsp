<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		02-Jun-2008		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Export</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="export"/>
	<bean:define id="pagetitle" value="Export"/>
	<bean:define id="helpsection" value="export"/>
</head>

<body id="resultsPage">

	<%@include file="../inc/body_start.jsp"%>
   
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<meta HTTP-EQUIV="refresh" CONTENT="10;URL=viewDeleteExportedAssetsStatus?filename=<bean:write name='deleteExportedStatusForm' property='fileLocation'/>"></meta>
	
   <div>
		
		<p>The exported assets are being deleted...</p>
		<p>This page will automatically refresh every 10 seconds until the assets have been deleted.</p>
   	
   </div>
	      
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>