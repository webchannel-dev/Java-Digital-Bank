<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<head>
	<title><bright:cmsWrite identifier="title-request-details" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="requests"/>

</head>


<body> 

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-request-details" /></h1> 
	
	<logic:present name="requestForm">
		<logic:equal name="requestForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="requestForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<bean:parameter id="managing" name="managing" value="false" />
	<logic:notEmpty name="requestDetails" >
		<div class="hr"></div>
			<%@include file="inc_request_fields.jsp"%>
		<div class="hr"></div>
	</logic:notEmpty>
	
	<p>
		<c:choose>
			<c:when test="${managing}">
				<bean:parameter id="stateName" name="stateName"/>
				<a href="viewManageRequests?stateName=<c:out value='${stateName}' />"><bright:cmsWrite identifier="link-back-to-all-requests" filter="false"/></a>
			</c:when>
			<c:otherwise>
				<a href="viewMyRequests"><bright:cmsWrite identifier="link-back-to-my-requests" filter="false" /></a>
			</c:otherwise>
		</c:choose>
	</p>
	
	<logic:notEmpty name="requestDetails" >
		<html:form action="saveRequest" method="post" styleClass="form">
			<html:hidden property="id" value="${requestDetails.id}" />
			<html:hidden property="managing" value="${managing}"/>
			<html:hidden property="stateName" value="${stateName}"/>
			
			<table class="form" cellspacing="0" cellpadding="0">
				<tr>
					<th>
						<label for="description"><bright:cmsWrite identifier="label-description" />:</label>
					</th>
					<td class="padded">
						<html:textarea name="requestForm" property="description" value="${requestDetails.description}" rows="10" cols="50" />
						<bright:cmsWrite identifier="snippet-request-instructions" filter="false" />
					</td>
				</tr>
			</table>
			
			<input type="submit" class="button flush floated" id="submitButton" value="<bright:cmsWrite identifier="button-save" filter="false" />"  />
		</html:form>
	</logic:notEmpty>

	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>