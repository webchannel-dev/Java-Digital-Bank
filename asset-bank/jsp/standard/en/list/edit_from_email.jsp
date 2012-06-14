<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard	16-Jan-2008		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Change from email address</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="content"/>
	<bean:define id="pagetitle" value="Edit From Address"/>
	<bean:define id="helpsection" value="email"/>
	<bean:define id="tabId" value="content"/>

	<%@include file="../inc/inc_mce_editor.jsp"%>
	<script type="text/JavaScript">
		// give the emailTemplateForm field the focus once the dom is ready
		$j(function () {
  			$j('#emailTemplateForm').focus();
 		});
	</script>		
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	
		<p>Providing a new email address here changes the from address for every email template.</p>
		
	
		<logic:equal name="emailTemplateForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="emailTemplateForm" property="errors" id="error">
						<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	
	<html:form action="saveFromEmail" styleClass="floated">
		<intput type="hidden name="typeId" value="1"/>

		<label for="emailTemplateForm">From email:</label>
		<html:text name="emailTemplateForm" styleId="emailTemplateForm" property="emailTemplate.addrFrom" maxlength="100" size="45"/>
		<br />
		
		<div class="hr"></div>
		
		<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		<a href="manageEmailTemplates?typeId=1" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		<br />
	</html:form>
	

	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>