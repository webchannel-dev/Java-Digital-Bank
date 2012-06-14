<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
	 d3		Ben Browning	21-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="title-security-questions" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="profile"/>
	<script type="text/JavaScript">
		$j(function () {
  			$j('#firstQuestion').focus();
		});
	</script>	
</head>

<body id="profilePage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-define-security-questions" filter="false" /></h1> 

	<bright:cmsWrite identifier="intro-define-security-questions" filter="false" />
	
	<logic:present name="changePasswordForm">
		<logic:equal name="changePasswordForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="changePasswordForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	<html:form action="/changeSecurityQuestions" method="post" styleClass="floated wideLabel">

		<label for="firstQuestion"><bright:cmsWrite identifier="label-security-questions-one" filter="false"/>:</label>
		
		<html:select styleId="securityQuestion" property="selectedFirstQuestionId" >
			<option value="0">-- <bright:cmsWrite identifier="snippet-please-select" filter="false" /> --</option>
			<logic:iterate name="securityQuestions" id="securityQuestion">
				<c:if test="${securityQuestion.group.id == 1}">
					<option value="<bean:write name='securityQuestion' property='id'/>" <c:if test="${changePasswordForm.selectedFirstQuestionId == securityQuestion.id}">selected</c:if>>
						<bean:write name='securityQuestion' property='question'/></option>
				</c:if>
			</logic:iterate>
		</html:select>
		<br />
		<label><bright:cmsWrite identifier="label-your-answer" filter="false" /></label><html:text styleClass="text" styleId="answer" property="firstSecurityAnswer" size="16" maxlength="200"/>
		<br />
		
		<label for="secondQuestion"><bright:cmsWrite identifier="label-security-questions-two" filter="false"/>:</label>
		
		<html:select styleId="securityQuestion" property="selectedSecondQuestionId" >
			<option value="0">-- <bright:cmsWrite identifier="snippet-please-select" filter="false" /> --</option>
			<logic:iterate name="securityQuestions" id="securityQuestion">
				<c:if test="${securityQuestion.group.id == 2}">
					<option value="<bean:write name='securityQuestion' property='id'/>" <c:if test="${changePasswordForm.selectedSecondQuestionId == securityQuestion.id}">selected</c:if>>
						<bean:write name='securityQuestion' property='question'/></option>
				</c:if>
			</logic:iterate>
		</html:select>
		<br />
		<label><bright:cmsWrite identifier="label-your-answer" filter="false" /></label><html:text styleClass="text" styleId="answer" property="secondSecurityAnswer" size="16" maxlength="200"/>
		<br />

		<div class="hr"></div>
		<input type="submit" class="button flush" id="submitButton" value="<bright:cmsWrite identifier="button-save" filter="false" />"  />

		<a href="viewChangeProfile" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		<br />
	</html:form>
	

	


	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>