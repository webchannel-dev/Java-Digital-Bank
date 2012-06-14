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
	<title><bright:cmsWrite identifier="company-name" /> | Edit Storage Device</title>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="assetEntity"/>
	<bean:define id="pagetitle" value="Asset Type"/>
	<bean:define id="helpsection" value="storage-devices"/>
	<bean:define id="tabId" value="storageDevices"/>
</head>
<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<c:set var="device" value="${storageDeviceForm.device}"/>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	
	<h2>Remove Storage Device</h2>
	
	<c:choose>
		<c:when test="${device.isDefault}">
			<div class="error">
				It is not permitted to delete the default storage device, since it contains inbuilt icons.
			</div>
			<c:set var="bCannotDelete" value="true" />
		</c:when>
		<c:when test="${storageDeviceForm.requiredForStorage}">
			<div class="error">
				You cannot remove this storage device because it would leave the system with inadequate storage. Please add or reconfigure one or more alternative storage devices first.
			</div>
			<c:set var="bCannotDelete" value="true" />
		</c:when>
		<c:otherwise>
		<p>
			You can remove this storage device by clicking Delete below. Note that this will not remove any files or directories from the filesystem, but will prevent 
			the system from seeing or using the storage device for new file storage requirements.
		</p>
		<c:if test="${device.type.id==1 || device.type.id==4 || device.type.id==5}">
			<p>
				Note also that removing a system storage device may not immediately prevent the device from being used for temporary files. For instance, the file conversion cache 
				directory may remain in use until the application server is restarted. For this reason it is highly inadvisable to delete or move the corresponding storage area on the 
				filesystem until after a restart.
			</p>
		</c:if>
		<c:choose>
			<c:when test="${device.numStoredAssets>0 || device.usedSpaceInBytes>0 || (empty device.lastUsageScan && device.type.id==1)}">
				<br/>
				<div class="warning">
					Be aware that this storage device 
					<c:choose>
						<c:when test="${device.numStoredAssets>0}">
							appears to be used by <strong><c:out value="${device.numStoredAssets}"/></strong> assets or asset versions. Continuing will prevent these assets' files from being displayed or downloaded, and cannot be undone!
							<c:if test="${device.type.id==1 || device.type.id==4 || device.type.id==5}">
								As it is also used also for temporary system files (including any shared/emailed assets) you should probably not remove it without first locking it for a while. 
								Note also that the corresponding filesystem may still be used by the file conversion cache until the next system restart. 
							</c:if>
						</c:when>
						<c:when test="${empty device.lastUsageScan && device.type.id==1}">
							has not been scanned for usage yet, so it may be being currently used for system file storage.
						</c:when>
						<c:otherwise>
							<c:set var="usedSpace" value="${device.usedSpaceInBytes/(1024*1024)}"/>
							contains 
							<c:choose>
								<c:when test="${device.usedSpaceInBytes>0}">
									<bean:write name="usedSpace" format="###,###,##0.0"/>
								</c:when>
								<c:otherwise>
									<bean:write name="usedSpace" format="0"/>
								</c:otherwise>
							</c:choose>
							Mb of files.  
							<c:choose>
								<c:when test="${device.type.id==2}">
									However, these files are not directly used by assets and so are likely to be unused. 
								</c:when>
								<c:when test="${device.type.id==1 || device.type.id==3}">
									These files are not asset files, but since this device is used for temporary system files (including any shared/emailed assets) you should probably not remove it 
									without first locking it for a while. Note also that the corresponding filesystem may still be used by the file conversion cache until the next system restart.
								</c:when>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</div>
			</c:when>
		</c:choose>
	</c:otherwise>
	</c:choose>
		
	<logic:equal name="storageDeviceForm" property="hasErrors" value="true">
		<div class="error noIcon">
			<logic:iterate name="storageDeviceForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form action="deleteStorageDevice" method="get" styleClass="floated">
		<input type="hidden" name="id" value="<c:out value="${device.id}"/>"/>
		
		<label for="name">Name: </label>	
		<bean:write name="storageDeviceForm" property="device.name"/>
		<br />
		
		<label for="deviceType">Used for: </label>
		<c:choose>
			<c:when test="${device.type.id==1}">System</c:when>
			<c:when test="${device.type.id==2}">Assets</c:when>
			<c:when test="${device.type.id==3}">Thumbnails</c:when>
			<c:when test="${device.type.id==4}">System &amp; Thumbnails</c:when>
			<c:when test="${device.type.id==5}">System, Assets &amp; Thumbnails</c:when>
			<c:when test="${device.type.id==6}">Assets &amp; Thumbnails</c:when>
			<c:when test="${device.type.id==7}">Repurposing</c:when>
		</c:choose>
		<br />
	
		<label for="path">Path: </label>	
		<bean:write name="storageDeviceForm"  property="device.localBasePath"/>
		<br />

		<label for="isRelative">Path Is Relative:</label>
		<c:choose>
			<c:when test="${device.localBasePathRelative}">Yes</c:when>
			<c:otherwise>No</c:otherwise>
		</c:choose>
		<br />
	
		<label for="maxSpace">Max space:</label>
		<bean:write name="storageDeviceForm" property="device.maxSpaceInMb"/> Mb
		<br />	
		
		<label for="maxSpace">Used space:</label>
		<c:set var="usedSpace" value="${device.usedSpaceInBytes/(1024*1024)}"/>
		<c:choose>
			<c:when test="${device.usedSpaceInBytes>0}">
				<bean:write name="usedSpace" format="###,###,##0.0"/> Mb
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${empty device.lastUsageScan}">
						Unknown
					</c:when>
					<c:otherwise>
						<bean:write name="usedSpace" format="0"/> Mb
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
		<br/>
		
		<label for="maxSpace">Last Usage Scan:</label>
		<c:if test="${empty device.lastUsageScan}">Not yet run</c:if>
		<bean:write name="storageDeviceForm" property="device.lastUsageScan" format="<%= (String)pageContext.getAttribute(\"displayDatetimeFormat\") %>"/>
		<br/>
		
		<label for="locked">Locked: </label>
		<c:choose>
			<c:when test="${device.locked}">Yes</c:when>
			<c:otherwise>No</c:otherwise>
		</c:choose>
		<br />
		
		<div class="hr"></div>
		
	
		<input type="submit" class="button flush" value="Delete" onclick="return confirm('Are you sure you want to remove this device?');" <c:if test="${bCannotDelete}">disabled='disabled'</c:if>> 
		<a href="viewStorageDevices" class="cancelLink"><bright:cmsWrite identifier="button-cancel" /></a>
	</html:form>
	
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>