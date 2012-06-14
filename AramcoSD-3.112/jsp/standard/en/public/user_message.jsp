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
	<bean:define id="pagetitle" value="My Messages"/>
	<bean:define id="tabId" value="current"/>
	<bean:define id="helpsection" value="mymessages"/>	
</head>

<body id="messagePage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-my-messages" filter="false"/></h1>
	
	<div class="head">
		<a href="<c:out value='${messageDetailReturnUrl}' />"><bright:cmsWrite identifier="link-back" filter="false"/></a>
	</div>	
	<logic:present name="messageForm">
		<logic:equal name="messageForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="messageForm" property="errors" id="error">
						<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>	

	<c:set var="refUrl" value="${messageForm.referenceLink}&messageId=${messageForm.id}&messageGroup=${messageForm.messageGroup}" />
	
	<html:form action="updateMessage" method="post">
		<html:hidden name="messageForm" property="id"/>
		<html:hidden name="messageForm" property="referenceLink"/>
		<html:hidden name="messageForm" property="referenceName"/>
		<html:hidden name="messageForm" property="messageGroup"/>
		<html:hidden name="messageForm" property="acknowledgementRequired"/>
		<h2>
			<bean:write name="messageForm" property="message.subject"/><c:if test="${not empty messageForm.referenceName}"> - <a href="<c:out value='${refUrl}' />"><bean:write name="messageForm" property="referenceName"/></a></c:if>
		</h2>
		<p><fmt:formatDate type="both" dateStyle="default" timeStyle="short" value="${messageForm.message.dateCreated}"/></p>
		<p>
			<bean:write name="messageForm" property="message.body" filter="false"/>
		</p>
		<c:if test="${!messageForm.acknowledged && messageForm.acknowledgementRequired}">
			<p>
				<label class="wrapping"><html:checkbox styleClass="checkbox" styleId="notifyUser" name="messageForm" property="acknowledged"/>	<bright:cmsWrite identifier="snippet-message-acknowledgement-text" filter="false" />
				'<bean:write name="messageForm" property="message.subject"/><c:if test="${not empty messageForm.referenceName}"> - <a href="<c:out value='${refUrl}' />"><bean:write name="messageForm" property="referenceName"/></a></c:if>'</label>
			</p>
		</c:if>
		<div class="hr"></div>
		<c:choose>
			<c:when test="${messageForm.archived}">
				<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-delete" filter="false" />" />
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${messageForm.acknowledgementRequired}">
						<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-acknowledge" filter="false" />" />
					</c:when>
					<c:otherwise>
						<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-archive" filter="false" />" />
					</c:otherwise>		
				</c:choose>
			</c:otherwise>					
		</c:choose>
		<a href="<c:out value='${messageDetailReturnUrl}' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>
		
	<%@include file="../inc/body_end.jsp"%>	
</body>
</html>