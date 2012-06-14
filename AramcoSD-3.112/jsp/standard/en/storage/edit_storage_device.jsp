<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		21-Oct-2008		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Edit Storage Device</title>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="storage"/>
	<bean:define id="pagetitle" value="Storage Devices"/>
	<bean:define id="helpsection" value="storage-devices"/>
	<bean:define id="tabId" value="marketingGroups"/>
	
	<script type="text/javascript">
	<!--
	
	<%--
	 * Called when the media type is changed.
	 * ctrl should be the select element containing the asset type options
	 --%>
	function typeChanged(ctrl)
	{
		var typeId = ctrl.value;
		checkForSystemType(typeId);
	}
	
	function checkForSystemType(typeId)
	{
		// System types are 1,4,5
		if (typeId == 1 || typeId == 4 || typeId == 5)
		{
			// Show warning
			changeStyleDisplay('warningDiv', "block");
		}
		else
		{
			// Hide warning
			changeStyleDisplay('warningDiv', "none");
		}
	}
	
	function changeStyleDisplay(elementName, state)
	{
		if(document.getElementById(elementName))
		{
			document.getElementById(elementName).style.display = state;
		}
	}
	
	-->
	</script>
	
</head>
<body id="adminPage" onload="checkForSystemType(<c:out value='${storageDeviceForm.device.typeId}'/>);">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	
	<h2><c:if test="${storageDeviceForm.device.id>0}">Edit</c:if><c:if test="${storageDeviceForm.device.id<=0}">Add</c:if> <bean:write name="storageDeviceForm" property="device.deviceTypeName"/> Storage Device</h2>
	
	<c:if test="${storageDeviceForm.device.id>0}">
		<p>Note that changing the storage type will not affect existing files stored on the device, but will change which files can be stored in the device from now on.</p>
		<p>Also be aware that you should only change an existing device's path if the device is empty or the existing files have already been moved/copied to the new location.</p>
	</c:if>
	
	<div id="warningDiv" class="warning">
		For performance reasons we strongly recommend that Storage Devices used for 'System' files are on a local drive, or one to which the server has very fast read/write access.
	</div>
	
	<logic:equal name="storageDeviceForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="storageDeviceForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form action="saveStorageDevice" method="post" styleClass="floated" >
		<html:hidden name="storageDeviceForm" property="device.id" />
		<label for="name">Name: <span class="required">*</span></label>	
		<html:text styleClass="text" name="storageDeviceForm" styleId="name" property="device.name" maxlength="40"/>
		<input type="hidden" name="mandatory_device.name" value="Please enter a Name for the device."/>
		<br />
		
		<label for="deviceType">Type: 
			<c:if test="${storageDeviceForm.device.id==0 || (storageDeviceForm.device.type.id!=5 && storageDeviceForm.device.type.id!=7)}">
				<span class="required">*</span>
			</c:if>
		</label>
		<html:select styleClass="text" styleId="deviceType" name="storageDeviceForm" property="device.typeId" onchange="typeChanged(this)">
			<html:option value="0">- select -</html:option>
			<html:option value="1">System</html:option>
			<html:option value="2">Assets</html:option>
			<html:option value="3">Thumbnails</html:option>
			<html:option value="4">System &amp; Thumbnails</html:option>
			<html:option value="5">System, Assets &amp; Thumbnails</html:option>
			<html:option value="6">Assets &amp; Thumbnails</html:option>
			<html:option value="7">Repurposing</html:option>
		</html:select>
		<br />
	
		<label for="path">Local Path: <span class="required">*</span></label>
		
		<html:text styleClass="text" name="storageDeviceForm"  styleId="path" property="device.localBasePath" maxlength="255"/>
		<input type="hidden" name="mandatory_device.localBasePath" value="Please enter a value for the Local Path."/>
		<span class="comment">*If the path does not start with a slash or drive letter, it is assumed to be relative to the application path</span>
		<br />
		
		<label for="path">Http Base Url: <c:if test="${storageDeviceForm.storageDeviceRemote}"><span class="required">*</span></c:if></label>
		
		<html:text styleClass="text" name="storageDeviceForm"  styleId="path" property="device.httpBaseUrl" maxlength="255"/>
		<span class="comment">*If left bank, files will be served by tomcat</span>
		<br />
		
		<c:if test="${storageDeviceForm.storageDeviceRemote}">
			
			<label for="authenticationId">Authentication ID: <span class="required">*</span></label>
			
			<html:text styleClass="text" name="storageDeviceForm"  styleId="path" property="device.authenticationId" maxlength="255"/>
			<input type="hidden" name="mandatory_device.authenticationId" value="Please enter a value for the Authentication ID"/>
			<br />
			
			<label for="authenticationKey">Authentication Key: <span class="required">*</span></label>
			
			<html:text styleClass="text" name="storageDeviceForm"  styleId="path" property="device.authenticationKey" maxlength="255"/>
			<input type="hidden" name="mandatory_device.authenticationKey" value="Please enter a value for the Authentication Key."/>
			<br />
			
		</c:if>
	
		<label for="maxSpace">Max space: <span class="required">*</span></label>
		<html:text styleClass="text" styleId="maxSpace" name="storageDeviceForm" property="device.maxSpaceInMb" maxlength="255" style="width:auto;" size="4"/> <span>Mb &nbsp;(leave blank or enter 0 for 'no limit')</span>
		<br />	
		
		<label for="locked">Locked: <span class="required">*</span></label>
		<html:select styleClass="text" styleId="locked" name="storageDeviceForm" property="device.locked" style="width:auto;">
			<html:option value="true">Yes</html:option>
			<html:option value="false">No</html:option>
		</html:select>
		<br />
		
		<div class="hr"></div>
	
		<input type="submit" class="button flush" value="Save"> 
		<a href="viewStorageDevices" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>
	
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>