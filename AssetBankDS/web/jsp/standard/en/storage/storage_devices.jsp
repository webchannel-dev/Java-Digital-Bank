<%@include file="../inc/doctype_html_admin.jsp" %>

<bright:applicationSetting id="safetyMarginSystem" settingName="storage-device-safety-margin-system"/>
<bright:applicationSetting id="safetyMarginAssets" settingName="storage-device-safety-margin-assets"/>
<bright:applicationSetting id="displayDatetimeFormat" settingName="display-datetime-format"/>
<bright:applicationSetting id="storageDevicesLocked" settingName="storage-device-edit-lock"/>
<bright:applicationSetting id="storageDevicesUsageRefreshPeriod" settingName="storage-device-usage-update-period-minutes"/>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		28-Sep-2007		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="company-name" filter="false"/> | Storage Devices</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="storage"/>
	<bean:define id="pagetitle" value="Storage Devices"/>
	<bean:define id="helpsection" value="storage-devices"/>
	<bean:define id="tabId" value="storage"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" filter="false"/></h1> 
	
	<%@include file="../updater/inc_system_tabs.jsp"%>

	<p>This page lists the current storage devices used by the application. A storage device may be used for storing <em>Asset files</em> (the original asset files), 
	and/or <em>System files</em> (generated thumbnail and preview images, and temporary files used by the application). </p>
	<p>Note that the order of the devices is significant in that the application will use the first available device in this list when storing a system or asset file. 
	A device is considered available when it is not locked and has free space beyond the configured safety margins (<bean:write name="safetyMarginSystem" format="###,###,###"/>Mb
	for system files and <bean:write name="safetyMarginAssets" format="###,###,###"/>Mb for asset files).
	Ensure that there is sufficient available storage space allocated for both Asset files and System files.</p>
	<p>
	<em>Type</em>: what resources will be stored in the device (system files or asset files).<br/>
	<em>Path</em>: the filesystem path of the device.<br/>
	<em>Is Relative</em>: whether the Path is relative to the Asset Bank web application root ([tomcat]/webapps/asset-bank), or an absolute path.<br/>
	<em>Max</em>: A maximum size for the device, configured by you.<br/>
	<em>Used</em>: A calculated value for the space used on this device.<br/>
	<em>Last Usage Update</em>: When the usage was last recalculated (this is done every <c:out value="${storageDevicesUsageRefreshPeriod}"/> minutes).<br/>
	<em>Status</em>: Whether this device is available for Asset Bank to use for storage of new system or asset files.<br/>
	</p>
	
	<c:choose>
		<c:when test="${storageDevicesLocked}">
			<p><strong>Note:</strong><br/>The storage devices are currently locked for editing. To use this screen to add or update storage devices, reset the setting 'storage-device-edit-lock' in ApplicationSettings.properties, and restart Tomcat, before revisting this page.</p>
		</c:when>
		<c:otherwise>
			<p>If you would like to lock the storage device configuration, and make this screen read only, then set the setting 'storage-device-edit-lock' in ApplicationSettings.properties to true and restart Tomcat.</p>
		</c:otherwise>
	</c:choose>
	
	<br/>
	
	<logic:equal name="storageDeviceForm" property="hasErrors" value="true">
		<p class="error">
			<logic:iterate name="storageDeviceForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</p>
	</logic:equal>
	
	
	<table cellspacing="2" border="0" class="admin compact" summary="List of storage devices">		
		<tr>
			<th>Type</th>
			<th>Name</th>
			<th>Used for</th>
			<th>Local Path</th>
			<th>Max (Mb)</th>
			<th>Used (Mb)</th>
			<th>Local (Mb)</th>
			<th>Last Usage Update</th>
			<th>Status</th>
			<th>&nbsp;</th>
		</tr>
		<bean:size id="numDevices" name="storageDeviceForm" property="devices"/>
		<logic:iterate name="storageDeviceForm" property="devices" id="device" indexId="index">
			<tr>
				<td><bean:write name="device" property="deviceTypeName" filter="false"/></td>
				<td><bean:write name="device" property="name" filter="false"/></td>
				<td>
					<c:choose>
						<c:when test="${device.type.id==1}">System</c:when>
						<c:when test="${device.type.id==2}">Assets</c:when>
						<c:when test="${device.type.id==3}">Thumbnails</c:when>
						<c:when test="${device.type.id==4}">System &amp; Thumbnails</c:when>
						<c:when test="${device.type.id==5}">System, Assets &amp; Thumbnails</c:when>
						<c:when test="${device.type.id==6}">Assets &amp; Thumbnails</c:when>
						<c:when test="${device.type.id==7}">Repurposing</c:when>
					</c:choose>
				</td>
				<td>
					<c:set var="path" value="${device.localBasePath}"></c:set>
					<c:if test="${empty path}">
						<c:set var="path" value="${device.httpBaseUrl}"></c:set>
					</c:if>
					
					<c:set var="pathLength"><%= ((String)pageContext.getAttribute("path")).length() %></c:set>
					<c:if test="${pathLength>15}">
						<a style="font-weight:normal; cursor: help;" title="<bean:write name="path" filter="false"/>"><bright:write name="path" filter="false" maxLength="15" endString="..."/></a>
					</c:if>
					<c:if test="${pathLength<=15}">
						<bean:write name="path" filter="false"/>
					</c:if>
				</td>
				<td>
					<c:choose>
						<c:when test="${device.maxSpaceInMb>0}">
							<bean:write name="device" property="maxSpaceInMb" format="###,###,###"/>
						</c:when>
						<c:otherwise>
							<span class="disabled">no limit</span>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:set var="usedSpace" value="${device.usedSpaceInBytes/(1024*1024)}"/>
					<c:choose>
						<c:when test="${usedSpace>=0}">
							<bean:write name="usedSpace" format="###,###,##0.0"/>
						</c:when>
						<c:otherwise>
							<span class="disabled">n/a</span>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:set var="usedLocalSpace" value="${device.usedLocalSpaceInBytes/(1024*1024)}"/>
					<c:choose>
						<c:when test="${usedLocalSpace>=0}">
							<bean:write name="usedLocalSpace" format="###,###,##0.0"/>
						</c:when>
						<c:otherwise>
							<span class="disabled">n/a</span>
						</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${not empty device.lastUsageScan && device.lastUsageScan.time>0}">
							<bean:write name="device" property="lastUsageScan" format="<%= (String)pageContext.getAttribute(\"displayDatetimeFormat\") %>"/>
						</c:when>
						<c:otherwise>not yet run</c:otherwise>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${device.locked}"><span style="color:red;">Locked</span></c:when>
						<c:when test="${device.maxSpaceInMb>0 && !device.hasFreeSpace}"><span style="color:red;">Full</span></c:when>
						<c:otherwise><span style="color:green;">Available</span></c:otherwise>
					</c:choose>
				</td>
				<td>
				<c:if test="${!storageDevicesLocked}">
					[<a href="viewEditStorageDevice?id=<bean:write name="device" property="id"/>">edit</a>]
					<c:choose>
						<c:when test="${index>0}">
							[<a href="reorderStorageDevices?id=<bean:write name="device" property="id"/>&up=true">up</a>]
						</c:when>
						<c:otherwise>
							[<span class="disabled"><strong>up</strong></span>]
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${index<numDevices-1}">
							[<a href="reorderStorageDevices?id=<bean:write name="device" property="id"/>&up=false">down</a>]
						</c:when>
						<c:otherwise>
							[<span class="disabled"><strong>down</strong></span>]
						</c:otherwise>
					</c:choose>
					[<a href="viewDeleteStorageDevice?id=<bean:write name="device" property="id"/>">X</a>]
				</c:if>	
				</td>
			</tr>
		</logic:iterate>
	</table>
	
	<div class="hr"></div>
	
	<c:if test="${!storageDevicesLocked}">
	<html:form action="viewAddStorageDevice">
		Add a new 
		<html:select name="storageDeviceForm" property="factoryClassName">
			<html:optionsCollection name="storageDeviceForm" property="factories" label="displayName" value="factoryClassName"/>
		</html:select>
		storage device
		<html:submit value="Add &raquo;" styleClass="button"/>
	</html:form>
	</c:if>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>