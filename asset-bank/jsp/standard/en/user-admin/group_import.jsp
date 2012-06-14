<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Group Import</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="groups"/>
	<bean:define id="helpsection" value="importingGroups"/>
	<bean:define id="pagetitle" value="Group Import"/>
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
	
	<html:form enctype="multipart/form-data" action="importGroups" method="post" styleClass="floated">
	
		<p>Please select the data file from which you want to import groups. This should be a tab-delimited file, containing the following:</p>
		<ul>
			<li>A header row, containing an identifier for each column. The identifiers can be in any order, and can be one of: Name, Description. Name is a mandatory column.</li>
			<li>A row for each group, containing appropriate data for each column.</li>
		</ul>		
		<br />
		
		<label for="file">Data File: <span class="required">*</span></label>
		<html:file name="metadataImportForm" property="file" styleClass="file" styleId="file" size="35" />
		<br />

		<div class="hr"></div>

		<input type="submit" name="saveButton" class="button flush" id="submitButton" value="<bright:cmsWrite identifier="button-submit" filter="false" />" />
		<a href="listGroups" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	
	</html:form>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>