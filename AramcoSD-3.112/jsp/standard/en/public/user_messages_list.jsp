<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Jon Harvey		26-Jan-2011		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting settingName="supportMultiLanguage" id="supportMultiLanguage"/>

<head>	
	<title><bright:cmsWrite identifier="title-my-messages" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="current"/>
	<bean:define id="helpsection" value="mymessages"/>
</head>

<body id="messageSummariesPage">
	<%@include file="../inc/body_start.jsp"%>
		
	<h1><bright:cmsWrite identifier="heading-my-messages" filter="false"/></h1>
	
	<bean:parameter id="mgroup" name="mgroup"/>
	
	<%@include file="../public/inc_user_messages_tabs.jsp"%>
	<bright:refDataList id="messageSummaries" transactionManagerName="DBTransactionManager" componentName="MessageManager" methodName="getMessageSummariesForUser" argsAreBeans="true" argumentValue="mgroup" passUserprofile="true"/>
	<c:set var="showFrom" value="true"/>
	<c:set scope="session" var="messageDetailReturnUrl" value="../action/viewUserMessages?mgroup=${mgroup}"/>	
	<logic:notEmpty name="messageSummaries">
		<%@include file="../public/inc_messages_table.jsp"%>
	</logic:notEmpty>
	<logic:empty name="messageSummaries">
		<p><bright:cmsWrite identifier="snippet-no-message-items" filter="false"/></p>
	</logic:empty>
		
	<%@include file="../inc/body_end.jsp"%>	
</body>
</html>