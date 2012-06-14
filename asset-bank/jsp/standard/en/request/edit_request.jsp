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
	<bean:parameter id="managing" name="managing" value="false" />
	<bean:parameter id="stateName" name="stateName" value="" />
	
	<div class="hr"></div>
	
	<logic:present name="requestForm">
		<logic:equal name="requestForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="requestForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<logic:notEmpty name="requestDetails" >
		<html:form action="saveRequest" method="post" styleClass="form">
			<html:hidden name="requestForm" property="id" />
			<html:hidden property="managing" value="${managing}"/>
			<html:hidden property="stateName" value="${stateName}"/>
			
			<c:set var="editing" value="true" />
			<%@include file="inc_request_fields.jsp"%>
			
			<%-- fulfillers dropdown (if already assigned) --%>
			<logic:present name="fulfillers">
				<table class="form" cellspacing="0" cellpadding="0">
					<logic:notEmpty name="fulfillers">
						<tr>
							<th></th>
							<td>
								<strong><label for="assignTo"><bright:cmsWrite identifier="label-assign-to" />:</label></strong>
								<html:select name="requestForm" property="fulfillmentUserId">
									<logic:iterate name="fulfillers" id="fulfiller">
										<option value="<bean:write name='fulfiller' property='id'  />" <c:if test="${fulfiller.id == requestDetails.fulfillmentUser.id}">selected="selected"</c:if> > 
											<bean:write name='fulfiller' property='forename' /> <bean:write name='fulfiller' property='surname' /> (<bean:write name='fulfiller' property='username' />)
										</option>
									</logic:iterate>
								</html:select>
							</td>
						</tr>
						<tr>
							<c:if test="${hasMessage}">
								<c:choose>
									<c:when test="${messageMandatory}">
										<c:set var="mandatoryOption"><span class="required">*</span></c:set>
									</c:when>
									<c:otherwise>
										<c:set var="mandatoryOption"><span>(<bright:cmsWrite identifier="snippet-optional" filter="false"/>)</span></c:set>
									</c:otherwise>
								</c:choose>
								<th></th>
								<td>
									<label for="message"><bright:cmsWrite identifier="snippet-message" />:${mandatoryOption}</label><br />
									<html:textarea name="requestForm" property="messageToFulfiller" rows="3" cols="50" />
								</td>
							</c:if>
						</tr>
	</logic:notEmpty>
					<logic:empty name="fulfillers">
						<div class="warning"><bright:cmsWrite identifier="snippet-no-fulfillers-defined" filter="false" /></div>
					</logic:empty>
				</table>
			</logic:present>
	
			
			
		<c:choose>
			<c:when test="${managing}">
				<bean:parameter id="stateName" name="stateName"/>
					<c:set var="cancelLink">viewManageRequests?stateName=${stateName}</c:set>
			</c:when>
			<c:otherwise>
					<c:set var="cancelLink">viewMyRequests</c:set>
			</c:otherwise>
		</c:choose>
	
			<table class="form" cellspacing="0" cellpadding="0">
				<tr>
					<th></th>
					<td>
						<input type="submit" class="button flush floated" id="submitButton" value="<bright:cmsWrite identifier="button-save" filter="false" />"  />
						<a href="${cancelLink}" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
					</td>
				</tr>
			</table>
		</html:form>
	</logic:notEmpty>

	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>