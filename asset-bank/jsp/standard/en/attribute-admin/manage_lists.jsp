<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	09-May-2005		Created
	 d2		Matt Stevenson	10-May-2005		Added link to add group
	 d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	 d4		Ben Browning	22-Feb-2006		HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Lists</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="lists"/>
	<bean:define id="pagetitle" value="Lists"/>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<h2>Manage Lists</h2>		
	<p>Choose from the list below to edit the options for each data type.</p>
	
	<ul class="standard">
		<li><a href="viewAttributeListValues?attributeId=1">Author</a></li>
		<li><a href="../action/viewUsageTypes">Usage Type</a></li>
	</ul>
	
	<div class="hr"></div>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>