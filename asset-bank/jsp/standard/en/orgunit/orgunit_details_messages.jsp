<%@include file="../inc/doctype_html_admin.jsp" %>
<%-- History:
	 d1	Matt Stevenson		17-Sep-2010		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | View Organisational Unit Details</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="orgunits"/>
	<bean:define id="pagetitle" value="Organisational Unit Details"/>
	<bean:define id="tabId" value="messages"/>
	<bean:define id="helpsection" value="orgunit-details"/>

</head>

<bright:applicationSetting id="useStructuredAddress" settingName="users-have-structured-address" />
<bean:parameter name="returnUrl" id="returnUrl" value="" />

<body <c:if test="${empty returnUrl}">id="adminPage"</c:if>>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 
	
	<%@include file="inc_orgunit_details_tabs.jsp"%>

	<h3>Messages</h3>
	
	<bean:parameter id="mgroup" name="mgroup" value="CURRENT"/>

	<form name="filterForm" id="filterForm" method="get" action="viewOrgUnitMessages">
		<input type="hidden" name="ouid" value="<c:out value='${orgUnitForm.orgUnit.id}' />" />
		<input type="hidden" name="returnUrl" value="<c:out value='${returnUrl}' />" />
		<select name="mgroup" size="1" onchange="document.getElementById('filterForm').submit();">
			<option value="CURRENT" <c:if test="${mgroup == 'CURRENT'}">selected</c:if>>Current</option>
			<option value="ARCHIVE" <c:if test="${mgroup == 'ARCHIVE'}">selected</c:if>>Archived</option>
		</select>
		<noscript>
			&nbsp;<input type="submit" name="submit" value="Go &raquo;" />
		</noscript>
	</form>

	<div class="hr"></div>

	<bright:refDataList id="messageSummaries" transactionManagerName="DBTransactionManager" componentName="MessageManager" methodName="getMessageSummariesForOrgUnit" argumentValue="${mgroup}" argument2Value="${orgUnitForm.orgUnit.id}" />
	
	<c:set scope="session" var="messageDetailReturnUrl" value="../action/viewOrgUnitMessages?mgroup=${mgroup}&ouid=${orgUnitForm.orgUnit.id}"/>	
	<c:set var="hideActions" value="true" />
	<logic:notEmpty name="messageSummaries">
		<%@include file="../public/inc_messages_table.jsp"%>
	</logic:notEmpty>
	<logic:empty name="messageSummaries">
		<p><bright:cmsWrite identifier="snippet-no-message-items" filter="false"/></p>
		<div class="hr"></div>
	</logic:empty>

	<%@include file="inc_orgunit_details_back.jsp"%>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>