<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Francis Devereux	26-Jan-2009		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>

	<title><bright:cmsWrite identifier="title-file-transfer-step-2" /></title>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="file-transfer"/>
	<script type="text/JavaScript">
		// give the recipients field the focus once the dom is ready
		$j(function () {
  			$j('#recipients').focus();
 		});
	</script>			
</head>

<body>

	<%@include file="../inc/body_start.jsp"%>

	<h1 class="underline">
		<bright:cmsWrite identifier="heading-file-transfer-step-2" />
	</h1>

	<logic:equal name="fileTransferForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="fileTransferForm" property="errors" id="errorText">
				<bright:writeError name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>

	<html:form enctype="multipart/form-data" action="fileTransfer" method="post">
		<html:hidden name="fileTransferForm" property="filename"/>
		<html:hidden name="fileTransferForm" property="fileLocation"/>

		<table class="form" cellspacing="0" cellpadding="0">
			<tr>
				<th><label for="recipients"><bright:cmsWrite identifier="label-to" filter="false" /></label></th>
				<td>
					<html:text name="fileTransferForm" property="recipients" styleId="recipients"/>
					<legend><bright:cmsWrite identifier="snippet-separate-with-semicolon" /></legend>
				</td>
			</tr>
			<tr>
				<th><label for="message"><bright:cmsWrite identifier="label-message" filter="false" /></label></th>
				<td><html:textarea name="fileTransferForm" property="message" styleId="message"/></td>
			</tr>
			<tr>
				<th><label for="filename"><bright:cmsWrite identifier="label-filename" filter="false" /></label></th>
				<td id="filename" class="padded"><bean:write name="fileTransferForm" property="filename"/></td>
			</tr>
			<tr>
				<th><label for="notify"><bright:cmsWrite identifier="label-notify-when-downloaded"/></label></th>
				<td><html:checkbox name="fileTransferForm" property="notify" styleId="notify" styleClass="checkbox"/></td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td><input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-send" filter="false" />" /></td>
			</tr>
		</table>
	</html:form>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>

