<%@include file="../inc/doctype_html.jsp" %>

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
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Metadata Import</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="batch-metadataimport"/>
	<bean:define id="helpsection" value="metadata-import"/>
	<bean:define id="pagetitle" value="Metadata Import: Step 1"/>

	<script type="text/javascript">
	<!-- 
	function savePressed()
	{
		document.getElementById('savingDiv').style.display="block";
	}
	//-->
	</script>
</head>

<body>
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	
	<logic:equal name="metadataImportForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="metadataImportForm" property="errors" id="error">
				<bean:write name="error" filter="false"/>
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form enctype="multipart/form-data" action="metadataImportUpload" method="post">
	
		<p>Please select the data file from which you want to import the metadata. Click on 'Help' for information about the required format for this file.</p>
		<p>Once your file has been uploaded it will be checked for errors. If it does not contain any fatal errors you will then be given the option to go ahead with the import.</p>
		<table class="form" cellspacing="0" cellpadding="0" border="0">
			<tr>
				<th>
					Data File:<span class="required">*</span>
				</th>
				<td>
					<html:file name="metadataImportForm" property="file" styleClass="file" size="35"/>
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<html:checkbox name="metadataImportForm" property="addMissingAssets" style="width: auto;" styleClass="checkbox"/> Add any missing assets automatically
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<html:checkbox name="metadataImportForm" property="addMissingCategories" style="width: auto;" styleClass="checkbox"/> Add any missing categories automatically
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<html:checkbox name="metadataImportForm" property="removeFromExistingCategories" style="width: auto;" styleClass="checkbox"/> Remove assets from existing categories and access levels, if new ones are specified
				</td>
			</tr>
			<tr>
				<th>&nbsp;</th>
				<td>
					<html:checkbox name="metadataImportForm" property="skipCheck" style="width: auto;" styleClass="checkbox"/> Skip check (import the data immediately)
				</td>
			</tr>
		</table>

		<div class="centered">
			<p><input type="submit" name="saveButton" class="button" id="submitButton" value="<bright:cmsWrite identifier="button-submit" filter="false" />" onclick="savePressed();" /> </p>
		</div>

		<div id="savingDiv" style="text-align:center; margin: 10px; display:none;">
		<p>Please wait while your file is uploaded and checked.</p>
		</div>
	
	</html:form>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>