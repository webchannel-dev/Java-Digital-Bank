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
	<bean:define id="pagetitle" value="Publishing Action"/>	
	<style type="text/css" media="screen">
		#adminPage table.form th {
		    width: 126px;
		}
	</style>
	
	<script type="text/JavaScript">
	
		function updatePage(selectedOption) {
			switch(selectedOption.value) {
				case "FTP":
					$j('#jailDirectory').hide();
				case "SCP":
					$j('#jailDirectory').hide();
					$j('#simpleFTPOrSCPMode0').show();
					$j('#simpleFTPOrSCPMode1').show();
					$j('#simpleFTPOrSCPMode2').show();
					$j('#simpleFTPOrSCPMode3').show();
					document.getElementById("serverName").value="";
					document.getElementById("username").value="";
					document.getElementById("password").value="";
					document.getElementById("plAsterix").style.display = "none";
					if (selectedOption.value == "SCP")	{
						document.getElementById("portNumber").value="22";
					}
					else {
						document.getElementById("portNumber").value="21";
					}
					break;
				case "SimpleCopy":
					$j('#jailDirectory').show();
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
		
		function criteriaSelected() {
			// criteria has been selected - toggle message panels
			$j('#criteriaSelected').show();
			$j('#criteriaNotSelected').hide();
		}
		
		function optionModified() {
			// do nothing if it's new publish action
			if ('${publishingForm.id}' <= 0)
			{
				return;
			}
			
			// check whether one of the options (which could potentially create an inconsistent published state) has been modified
			if (document.getElementById("pathInput").value == document.getElementById("current_path").value &&
				document.getElementById("usageTypeIdSelect").value == document.getElementById("current_usageTypeId").value &&
				document.getElementById("attributeIdSelect").value == document.getElementById("current_attributeId").value)
			{
				$j('#optionsModified').hide();
			}
			else
			{
				$j('#optionsModified').show();
			}
			
		}
	
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
			else if ('${publishingForm.fileTransferType}' == 'FTP' || '${publishingForm.fileTransferType}' == 'SCP') {
				$j('#jailDirectory').hide();
			}
			
			// Ajax search criteria popup
			$j('.launchCedriteria').click(function() {
	            var url = this.href;
	            // show a spinner or something via css
	            var dialog = $j('<div style="display:none" class="loading"></div>').appendTo('body');
	            // open the dialog
	            dialog.dialog({
	                // add a close listener to prevent adding multiple divs to the document
	                close: function(event, ui) {
	                    // remove div with all data and events
	                    dialog.remove();
	                },
	                modal: true
	            });
	            // load remote content
	            dialog.load(
	                url, 
	                {}, // omit this param object to issue a GET request instead a POST request, otherwise you may provide post parameters within the object
	                function (responseText, textStatus, XMLHttpRequest) {
	                    // remove the loading class
	                    dialog.removeClass('loading');
	                }
	            );
	            //prevent the browser to follow the link
	            return false;

			})
			
		});
		
		

	</script>	
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>

	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
 
	
 	


	<c:if test="${empty jailDirectory}">
		<p>You don't currently have a jail directory set for local file copy publishing actions. It is strongly recommended to set the 'publishing-file-copy-jail-directory' setting in ApplicationSettings.properties to make sure that admin users can't publish files to any location on the Asset Bank server's local disk.</p>
	</c:if>
	
	<h2>1. Search Criteria</h2> 
	
	<p>The search criteria will define which assets will be published.</p>
	<c:set var="attributeName" value="${searchCriteriaSessionKey}" />

	<c:choose>
		<c:when test="${publishingForm.beforeValidation}">
			<c:set var="notSelectedClass" value="info"/>
		</c:when>
		<c:otherwise>
			<c:set var="notSelectedClass" value="error"/>
		</c:otherwise>
	</c:choose>
	<div id="criteriaNotSelected" class="${notSelectedClass} <c:if test="${publishingForm.criteriaSelected}">hidden</c:if>">	
		<div class="toolbar noTopMargin">
			<div class="group first">No search criteria specified!</div>		
			<div class="group">
				<a target="_blank" class="popup-link button" href="viewSelectCriteria?selectedCriteriaSessionKey=${publishingForm.searchCriteriaSessionKey}">Specify search criteria</a>
			</div> 
		</div>
	</div>
	
	<div id="criteriaSelected" class="confirm <c:if test="${not publishingForm.criteriaSelected}">hidden</c:if>">
		<div class="toolbar noTopMargin">
			<div class="group first">Search criteria specified</div>
			<div class="group"><a target="_blank" class="popup-link button" href="viewSelectCriteria?refineSearch=1&selectedCriteriaSessionKey=${publishingForm.searchCriteriaSessionKey}">View/Change criteria</a></div>
		</div>				
		
	</div>
	
	<br />

	<h2>2. Publishing Options</h2> 
	<logic:equal name="publishingForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="publishingForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form styleId="create-publishing-action-form" action="viewAddPublishingAction" method="post">
		
		<input type="hidden" name="mandatory_name" value="Please enter a name.">
		<input type="hidden" name="mandatory_serverName" value="Please select a server ip address.">
		<input type="hidden" name="mandatory_username" value="Please select the user's name.">
		<input type="hidden" name="mandatory_password" value="Please select the password.">
		<input type="hidden" name="mandatory_portNumber" value="Please enter the port number.">

		<table cellspacing="0" class="form">
			<html:hidden name="publishingForm" property="id"/>
			<html:hidden name="publishingForm" property="searchCriteriaSessionKey"/>
			
			<tr>
				<th><label>Name for this action:<span class="required">*</span></label></th>
	 			<td><html:text styleClass="text" name="publishingForm" styleId="name-input" property="name" maxlength="40"/></td>
	 		</tr>
	 		
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
	 			<td>
	 				<html:text styleClass="text" name="publishingForm" property="path" styleId="pathInput" onkeyup="optionModified();" maxlength="256"/>
	 				<c:if test="${not empty jailDirectory}"><div id="jailDirectory">The path you enter will be relative to <strong><bean:write name="jailDirectory" /></strong> unless you enter a network path beginning with <strong>\\</strong><br/><em>See help page for how to change this.</em></div></c:if>
	 			</td>
	 			<input type="hidden" id="current_path" value="${publishingForm.path}" />
	 		</tr>	 		
			<tr>
	 			<th><label>Usage Type for Resizing:</label></th>
	 			<td>
					<html:select name="publishingForm" property="usageTypeId" styleId="usageTypeIdSelect" onchange="optionModified();">
		 				<html:option value="" >- No Resizing -</html:option>
						<html:optionsCollection name="publishingForm" property="usageTypes" label="description" value="id"/>							
					</html:select>
					<input type="hidden" id="current_usageTypeId" value="${publishingForm.usageTypeId}" />
				</td>
			</tr>
			<tr>
	 			<th><label>Attribute used for Subdirectory Name:</label></th>
	 			<td>
					<html:select name="publishingForm" property="attributeId" styleId="attributeIdSelect" onchange="optionModified();">
		 				<html:option value="" >- No Subdirectory -</html:option>
						<html:optionsCollection name="publishingForm" property="attributeIds" label="label" value="id"/>							
					</html:select>
					<input type="hidden" id="current_attributeId" value="${publishingForm.attributeId}" />
				</td>
			</tr>
			<tr>
				<th><label>Run Daily:</label></th>
	 			<td class="padded"><html:checkbox styleClass="checkbox" styleId="run-daily-cb" name="publishingForm" property="runDaily" value="1" /></td>
			</tr>
			<tr>
				<c:set var="helpText" value="Should files that no longer match the search criteria be unpublished (i.e. deleted) when this publish action runs?" scope="request"/>
				<th><label>Unpublish:<jsp:include page="../asset-admin/inc_attribute_help.jsp"/></label></th>
	 			<td class="padded"><html:checkbox styleClass="checkbox" styleId="run-daily-cb" name="publishingForm" property="autoUnpublish" value="1" /></td>
			</tr>
			<tr id="simpleFTPOrSCPMode3">
				<th><label>Port Number:<span class="required">*</span></label></th>	
	 			<td><html:text styleClass="text" name="publishingForm" styleId="portNumber" property="portNumber" maxlength="10"/></td>
			</tr>
	    </table>    

		<div id="optionsModified" class="warning hidden" >
			<strong>Note: </strong>Your changes will modify the directory structure of your publish location which could lead to an inconsistent state with previously published assets.
			<br />You will need to run [<strong>publish all matching assets</strong>], after saving this action, to apply the changes to all the assets published by this action. 
		</div>
	    
		<div class="hr"></div> 
		<input id="save-link" type="submit" class="button flush floated" value="Save" /> 
		<a id="cancel-link" href="cancelEditPublishingAction?sessionKey=${publishingForm.searchCriteriaSessionKey}" class="cancelLink">Cancel</a> 
		<br />
	</html:form>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>