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


<bright:applicationSetting settingName="publishing-file-copy-jail-directory" id="jailDirectory" />


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Publishing Admin</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="publishing"/>
	<bean:define id="pagetitle" value="Publishing"/>	
	<style type="text/css" media="screen">
		#adminPage table.form th {
		    width: 126px;
		}
	</style>
	<script type="text/JavaScript">
		$j(function() {
			if ('${publishingForm.fileTransferType}' == 'SimpleCopy' || '${publishingForm.fileTransferType}' == '') {
				$j('#simpleFTPOrSCPMode0').hide();
				$j('#simpleFTPOrSCPMode1').hide();
				$j('#simpleFTPOrSCPMode2').hide();
				$j('#simpleFTPOrSCPMode3').hide();
				$j('#mandatory_FTPSCP').hide();
				document.getElementById("serverName").value="-1";
				document.getElementById("username").value="-1";
				document.getElementById("password").value="-1";
				document.getElementById("plAsterix").style.display = "inline";
			}
		});
		
		
		function updatePage(selectedOption) {
			switch(selectedOption.value) {
				case "FTP":
					$j('#jailDriectory').hide();
				case "SCP":
					$j('#jailDriectory').hide();
					$j('#simpleFTPOrSCPMode0').show();
					$j('#simpleFTPOrSCPMode1').show();
					$j('#simpleFTPOrSCPMode2').show();
					$j('#simpleFTPOrSCPMode3').show();
					document.getElementById("serverName").value="";
					document.getElementById("username").value="";
					document.getElementById("password").value="";
					document.getElementById("plAsterix").style.display = "none";
					if (selectedOption.value == "SCP")
					{
						document.getElementById("portNumber").value="22";
					}
					else 
					{
						document.getElementById("portNumber").value="21";
					}
					break;
				case "SimpleCopy":
					$j('#jailDriectory').show();
				default:
					$j('#simpleFTPOrSCPMode0').hide();
					$j('#simpleFTPOrSCPMode1').hide();
					$j('#simpleFTPOrSCPMode2').hide();
					$j('#simpleFTPOrSCPMode3').hide();
					document.getElementById("serverName").value="-1";
					document.getElementById("username").value="-1";
					document.getElementById("password").value="-1";
					document.getElementById("portNumber").value="-1";
					document.getElementById("plAsterix").style.display = "inline";
					break;
			}
		}
	</script>	
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>

	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
 
	<h2>Edit Publishing Action</h2> 
 

	<logic:equal name="publishingForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="publishingForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>


	<c:if test="${empty jailDirectory}">
		<p>You don't currently have a jail directory set for local file copy publishing actions. It is strongly recommended to set the 'publishing-file-copy-jail-directory' setting in ApplicationSettings.properties to make sure that admin users can't publish files to any location on the Asset Bank server's local disk.</p>
	</c:if>

		
	<html:form styleId="create-publishing-action-form" action="viewAddPublishingAction" method="post">

		<input type="hidden" name="mandatory_accessLevelId" value="Please select an access level.">
		<input type="hidden" name="mandatory_name" value="Please enter a name.">
		<input type="hidden" name="mandatory_serverName" value="Please select a server ip address.">
		<input type="hidden" name="mandatory_username" value="Please select the user's name.">
		<input type="hidden" name="mandatory_password" value="Please select the password.">
		<input type="hidden" name="mandatory_portNumber" value="Please enter the port number.">

		<table cellspacing="0" class="form">
			<html:hidden name="publishingForm" property="id"/>
			<tr>
				<th><label>Transfer Method:<span class="required">*</span></label></th>
				<td>
					<html:select name="publishingForm" property="fileTransferType" styleId="fileTransferType" onchange="updatePage(this)">
						<option value="SimpleCopy" <c:if test="${publishingForm.fileTransferType== 'SimpleCopy'}">selected</c:if>>Simple Copy</option>
						<option value="FTP" <c:if test="${publishingForm.fileTransferType == 'FTP'}">selected</c:if>>FTP</option>
						<option value="SCP" <c:if test="${publishingForm.fileTransferType == 'SCP'}">selected</c:if>>SCP</option>
					</html:select>
				</td>
			</tr>
			<tr>
				<th><label>Name for this action:<span class="required">*</span></label></th>
	 			<td><html:text styleClass="text" name="publishingForm" styleId="name-input" property="name" maxlength="40"/></td>
	 		</tr>
	 		<tr id="simpleFTPOrSCPMode0">
				<th><label>Server:<span class="required">*</span></label></th>
	 			<td><html:text styleClass="text" name="publishingForm" styleId="serverName" property="serverName" maxlength="40"/></td>
			</tr>
			<tr id="simpleFTPOrSCPMode1">
				<th><label>Username:<span class="required">*</span></label></th>	
	 			<td><html:text styleClass="text" name="publishingForm" styleId="username" property="username" maxlength="40"/></td>
 			</tr>
 			<tr id="simpleFTPOrSCPMode2">
				<th><label>Password:<span class="required">*</span></label></th>	
	 			<td><html:password styleClass="text" name="publishingForm" property="password" maxlength="100" size="25" styleId="password" /></td>
 			</tr>
			<tr>
				<th><label>Publish Location: <span class="required" id="plAsterix" style="display: none;">*</span></label></th>
	 			<td><c:if test="${not empty jailDirectory}"><em id="jailDriectory"><bean:write name="jailDirectory" /></em> </c:if><html:text styleClass="text" name="publishingForm" styleId="path-input" property="path" maxlength="256"/></td>
	 		</tr>
	 		<tr>
	 			<th><label>Access Level:<span class="required">*</span></label></th>
	 			<td>
					<html:select name="publishingForm" property="accessLevelId" styleId="access-level-select" >
		 				<html:option value="" >- Please Select an Access Level -</html:option>
						<html:optionsCollection name="publishingForm" property="accessLevelsList" label="name" value="id"/>							
					</html:select>
				</td>
			</tr>
			<tr>
	 			<th><label>Usage Type for Resizing:</label></th>
	 			<td>
					<html:select name="publishingForm" property="usageTypeId" styleId="access-level-select" >
		 				<html:option value="" >- Select -</html:option>
						<html:optionsCollection name="publishingForm" property="usageTypes" label="description" value="id"/>							
					</html:select>
				</td>
			</tr>
			<tr>
	 			<th><label>Attribute used for Subdirectory Name:</label></th>
	 			<td>
					<html:select name="publishingForm" property="attributeId" styleId="access-level-select" >
		 				<html:option value="" >- Select -</html:option>
						<html:optionsCollection name="publishingForm" property="attributeIds" label="label" value="id"/>							
					</html:select>
				</td>
			</tr>
			<tr>
				<th><label>Run Daily:</label></th>	
	 			<td><html:checkbox styleClass="checkbox" styleId="run-daily-cb" name="publishingForm" property="runDaily" value="1" /></td>
			</tr>
			<tr id="simpleFTPOrSCPMode3">
				<th><label>Port Number:<span class="required">*</span></label></th>	
	 			<td><html:text styleClass="text" name="publishingForm" styleId="portNumber" property="portNumber" maxlength="10"/></td>
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