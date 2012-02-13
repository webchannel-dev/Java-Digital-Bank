<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson	25-Oct-2005		Created
	 d2		Ben Browning	22-Feb-2006		HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Attributes</title>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="attributes"/>
	<bean:define id="pagetitle" value="Attributes"/>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<h2>Delete Attribute</h2>		
	
	<bean:parameter id="attributeId" name="attributeId"/>
	<bean:parameter id="attributeDescription" name="attributeDescription"/>
	<bean:parameter id="typeId" name="typeId" value="0"/>

	<div class="warning">
		<c:if test="${typeId!=10}">
			<p>Are you sure you want to delete the attribute '<bean:write name="attributeDescription"/>'?</p>
			<p>Click 'Delete' to permenantly delete this attribute from the database. This action cannot be undone, and all data entered for this attribute for existing <bright:cmsWrite identifier="items" filter="false" /> will be lost.</p>
		</c:if>
		
		<c:if test="${typeId==10}">
			<p>Are you sure you want to delete the attribute group heading '<bean:write name="attributeDescription"/>'?</p>
		</c:if>
		
		<div class="hr"></div>

		<form action="../action/deleteAttribute" class="floated" style="margin:0;">
			<input type="hidden" name="attributeId" value="<bean:write name='attributeId'/>">
			<input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-delete" filter="false" />" />
			<a href="../action/viewManageAttributes" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			<br />
		</form>
	</div>
	

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>