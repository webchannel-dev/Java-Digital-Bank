<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Publishing Admin</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="publishing"/>
	<bean:define id="pagetitle" value="Publishing"/>	
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
 
	<h2>Edit Publishing Action</h2> 
  
	<div class="hr"></div> 

		<logic:equal name="publishingForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="publishingForm" property="errors" id="error">
						<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
		
	<html:form styleId="create-publishing-action-form" action="viewAddPublishingAction" method="post" onsubmit="return onFormSubmit();">

		<input type="hidden" name="mandatory_accessLevelId" value="Please select an access level.">
		<input type="hidden" name="mandatory_path" value="Please enter a publish location.">
		<input type="hidden" name="mandatory_name" value="Please enter a name.">

		<table cellspacing="0" class="form">
			<html:hidden name="publishingForm" property="id"/>
			<tr>
				<th><label>Name:</label></th>
	 			<td><html:text styleClass="text" name="publishingForm" styleId="name-input" property="name" maxlength="40"/></td>
	 		</tr>
			<tr>
				<th><label>Publish Location:</label></th>
	 			<td><html:text styleClass="text" name="publishingForm" styleId="path-input" property="path" maxlength="256"/></td>
	 		</tr>
	 		<tr>
	 			<th><label>Access Level:</label></th>
	 			<td>
					<html:select name="publishingForm" property="accessLevelId" styleId="access-level-select" >
		 				<html:option value="" >- Please Select an Access Level -</html:option>
						<html:optionsCollection name="publishingForm" property="accessLevelsList" label="name" value="id"/>							
					</html:select>
				</td>
			</tr>
			<tr>
				<th><label>Run Daily:</label></th>	
	 			<td><html:checkbox styleClass="checkbox" styleId="run-daily-cb" name="publishingForm" property="runDaily" value="1" /></td>
			</tr>
	    </table>    
	    
		<div class="hr"></div> 
		<input id="save-link" type="submit" class="button flush floated" value="Save" /> 
		<a id="cancel-link" href="viewPublishing" class="cancelLink">Cancel</a> 
		<br />
	</html:form>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>