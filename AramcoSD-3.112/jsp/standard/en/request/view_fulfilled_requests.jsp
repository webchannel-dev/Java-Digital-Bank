<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="title-all-requests" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="requests"/>
</head>

<body> 

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-requests" /></h1> 
	
	<bean:define id="tabId" value="fulfilled-requests" />
	<%@include file="inc_request_tabs.jsp"%>

	
	<logic:empty name="requests">
		<bright:cmsWrite identifier="snippet-no-requests-in-state" filter="false" />
	</logic:empty>
	
	<logic:notEmpty name="requests">
		<bean:size id="noOfRequests" name="requests"/>
		<bean:define id="showRequester" value="true" />
		<bean:define id="extraParams" value="&managing=true&stateName=fulfilled"/>
		<%@include file="inc_request_list.jsp"%>
	</logic:notEmpty>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>