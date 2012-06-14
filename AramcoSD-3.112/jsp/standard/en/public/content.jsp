<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Ben Browning	17-Jul-2006		Created
	 d2		Matt Stevenson	13-Dec-2007		Modified to allow for custom specified content
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%-- Set the company name for use in the page title --%>
<bean:parameter id="index" name="index" value="1"/>
<bean:parameter id="identifier" name="identifier" value=""/>

<c:set var="identifierToLoad" value="testing" />
<c:choose>
	<c:when test="${identifier != ''}">
		<c:set var="identifierToLoad" value="${identifier}" />
		<bean:define id="section" value='<%= "extracontent" + identifier %>'/>
	</c:when>
	<c:otherwise>
		<c:set var="identifierToLoad" value="content-page-${index}" />
		<bean:define id="section" value='<%= "extracontent" + index %>'/>
	</c:otherwise>
</c:choose>

<html>

<head>
	<title><bright:cmsWrite identifier="company-name" filter="false" /></title> 
	
	<%@include file="../inc/head-elements.jsp"%>
</head>
<bean:parameter id="openNav" name="openNav" value=""/>
<body <c:if test="${openNav!=''}">id="<bean:write name='openNav' />"</c:if>>
	<div id="customContent">
	<%@include file="../inc/body_start.jsp"%>
	<bean:define id="identifierToLoad" name="identifierToLoad"/>
	<bright:refDataList id="mainCopyItem" componentName="ListManager" methodName="getListItem" argumentValue='<%= String.valueOf(identifierToLoad) %>'/>	
	<bean:parameter id="showTitle" name="showTitle" value="false" />
	<c:if test="${showTitle}"><h1 class="underline"><bean:write name="mainCopyItem" property="title" filter="false"/></h1></c:if>
		
	<%-- This custom copy comes from the database (change in the 'Content' area of Admin) --%>

	<bright:cmsWrite identifier='<%= String.valueOf(identifierToLoad) %>' filter="false" />
	
	<%@include file="../inc/body_end.jsp"%>

	</div>
</body>
</html>