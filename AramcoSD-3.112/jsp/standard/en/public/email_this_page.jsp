<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Stevenson		20-Feb-2009		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="title-email-this-page" filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<%@include file="../inc/inc_mce_editor.jsp"%>
	<bean:define id="section" value="home"/>
</head>
<body>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-email-this-page" filter="false" /></h1> 
	<p><a href='<bean:write name="sendEmailForm" property="backUrl"/>'><bright:cmsWrite identifier="button-back" filter="false" /></a></p>
	<bright:cmsWrite identifier="snippet-email-this-page" filter="false" />
	
	<logic:notEmpty name="sendEmailForm" property="errors">
		<div class="error">
		<logic:iterate name="sendEmailForm" property="errors" id="error">
			<bean:write name='error'/><br/>
		</logic:iterate>
		</div>
	</logic:notEmpty>

	<div class="hr"></div>
	
	<form method="post" action="emailThisPage" name="messageUsers" class="floated">
		<html:hidden name="sendEmailForm" property="redirectUrl"/>
		<html:hidden name="sendEmailForm" property="fromAddress"/>

		<label for="to"><bright:cmsWrite identifier="label-to" filter="false" /> <span class="required">*</span></label>
		<html:text name="sendEmailForm" property="toAddress" styleId="to"/>
		<br />


		<label for="subject"><bright:cmsWrite identifier="label-subject" filter="false" />:</label>
		<input type="text" name="subject" value="<bean:write name='sendEmailForm' property='email.subject'/>"/>
		<br />
					
		<label for="body"><bright:cmsWrite identifier="label-message" filter="false" /></label>
		<textarea name="body" style="width:500px !important" rows="20" cols="40" class="editor"><bean:write name='sendEmailForm' property='email.htmlMsg' filter="false"/></textarea>
		<br />
		<div class="hr"></div>
		<input type="submit" name="sendMessage" value="<bright:cmsWrite identifier="button-send" filter="false" />" class="button flush" />
		<a href='<bean:write name="sendEmailForm" property="backUrl"/>' class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
		<br />

	</form>

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>