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
	<bean:define id="helpsection" value="metadata-import"/>
	<bean:define id="pagetitle" value="Metadata Import: Step 2"/>
</head>

<body>
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:equal name="metadataImportForm" property="fatalError" value="true">
		<p>Your data file has been checked, and contained one or more fatal errors, which are shown below. 
		Please correct these errors in the file and then go back and upload your file again.</p>
	</logic:equal>
	
	<c:if test="${metadataImportForm.messageCount <= 2}">
		<logic:equal name="metadataImportForm" property="fatalError" value="false">
			<p>Your data file has been checked, and does not contain any errors.</p>
		</logic:equal>
	</c:if>
	<c:if test="${metadataImportForm.messageCount > 2}">
		<logic:equal name="metadataImportForm" property="fatalError" value="false">
			<p>Your data file has been checked, and does not contain any fatal errors.</p>
			<p>However one or more non-fatal issues were found, which are shown below. You can either correct these issues in the file and then go back and upload your file again, or go ahead and process this file anyway. If you decide to proceed then any lines that contain errors will be skipped.
			</p>
		</logic:equal>
	</c:if>
	<ul class="normal">
		<logic:iterate name="metadataImportForm" property="messages" id="message">
			<li><bean:write name="message" /></li>
		</logic:iterate>
	</ul>
	<br/>
	<logic:equal name="metadataImportForm" property="fatalError" value="false">
		<div class="warning">
		Please be sure that you want to proceed with the update before clicking 'Start Update'. The update process will update all matching <bright:cmsWrite identifier="items" filter="false" /> and cannot be undone. 	
		</div>
	</logic:equal>


			<logic:equal name="metadataImportForm" property="fatalError" value="false">

				<html:form action="metadataStartImport" method="post" styleClass="">
					<html:hidden name="metadataImportForm" property="url"/>
					<html:hidden name="metadataImportForm" property="addMissingCategories"/>
					<html:hidden name="metadataImportForm" property="addMissingAssets"/>
					<html:hidden name="metadataImportForm" property="removeFromExistingCategories"/>
					<input type="submit" name="startButton" class="button floated flush" value="Start Update" />
				</html:form>

			</logic:equal>
			<a class="cancelLink">Cancel</a>
		</tr>
	</table>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>