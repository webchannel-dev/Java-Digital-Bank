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
	
	<bean:define id="tabId" value="all-requests" />
	<%@include file="inc_request_tabs.jsp"%>

	<logic:notEmpty name="stateHolders">
		<ul class="linkList">
			<logic:iterate name="stateHolders" id="stateHolder" indexId="menuItems">
				<c:set var="state" value="${stateHolder.state}" />
				<li class="<c:if test="${menuItems == 0}">first</c:if> <c:if test="${state.name == selectedState}"><bean:define id="selectedStateDescription" name="state" property="description" />active</c:if>">
					<a href="viewManageRequests?stateName=<c:out value='${state.name}' />"><c:out value='${state.description}' /> <c:if test="${stateHolder.numOfItemsInState > 0}">(${stateHolder.numOfItemsInState})</c:if></a>
				</li>
			</logic:iterate>
		</ul>
	</logic:notEmpty>
	
	<logic:empty name="requests">
		<bright:cmsWrite identifier="snippet-no-requests-in-state" filter="false" />
	</logic:empty>
	
	<logic:notEmpty name="requests">
		<bean:define id="showRequester" value="true" />
		<bean:define id="canEdit" value="${userprofile.isAdmin}" />
		<bean:define id="extraParams" value="&managing=true&stateName=${selectedState}"/>
		<%@include file="inc_request_list.jsp"%>
	</logic:notEmpty>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>