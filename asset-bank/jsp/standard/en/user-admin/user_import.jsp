<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		06-Jul-20046	Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | User Import</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="users"/>
	<bean:define id="helpsection" value="importingUsers"/>
	<bean:define id="pagetitle" value="User Import"/>
</head>

<body id="adminPage">
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	
	<logic:equal name="metadataImportForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="metadataImportForm" property="errors" id="error">
				<bright:writeError name="error" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form enctype="multipart/form-data" action="importUsers" method="post" styleClass="floated">
	
	
	
		<p>Please select the data file from which you want to import users. This should be a tab-delimited file, containing the following:</p>
		
		<ol>
			<li>A header row containing an identifier for each column. The identifiers can be in any order, and can be one of: <br /> <strong>Username, Forename, Surname, EmailAddress, Password, Groups</strong> <br />
			Note, these column headers have to be exactly as shown above. 'Username' is the only column that is mandatory.
			</li>
			<li>
			A row for each user, containing appropriate data for each column. A 'group' in the <strong>Groups</strong> column can be specified as either the ID of the Asset Bank group or the full name of the group. Multiple groups should be delimited with a semicolon ';'. <br />
			Note, if the group to which you want to add the user is within an Org Unit then add the full name of the Org Unit, followed by a space, followed by the full name of the group.	
			</li>
		</ol>
		<br />
		
		
		<label for="file">Data File: <span class="required">*</span></label>
		<html:file name="metadataImportForm" property="file" styleClass="file" styleId="file" size="35" />
		<br />

		<div class="hr"></div>

		<input type="submit" name="saveButton" class="button flush" id="submitButton" value="<bright:cmsWrite identifier="button-submit" filter="false" />" />
		<a href="viewUserAdmin" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>