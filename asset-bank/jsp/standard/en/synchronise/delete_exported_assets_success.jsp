<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
    d3      Ben Browning   14-Feb-2006    HTML/CSS tidy up
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
   
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

   <div>
		
		<bean:parameter id="errors" name="errors"/>
		<bean:parameter id="successful" name="successful"/>
		
		<c:if test="${errors==0}">
			<p>You have successfully deleted your exported <bright:cmsWrite identifier="items" filter="false" />.</p>
		</c:if>
		<c:if test="${errors>0}">
			<p><bean:write name="successful"/> <c:if test="${successful>1 || successful==0}"><bright:cmsWrite identifier="items" filter="false" /> were</c:if>
			<c:if test="${successful==1}"><bright:cmsWrite identifier="item" filter="false" /> was</c:if> deleted, <bean:write name="errors"/> 
			<c:if test="${errors>1}">were</c:if><c:if test="${errors==1}">was</c:if> not deleted due to errors  - please see the logs for details.</p>
		</c:if>
		
		<br/>
   	
   </div>
	      
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>