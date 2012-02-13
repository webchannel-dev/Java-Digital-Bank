
<c:if test="${userprofile.userOsIsWindows}">
	<bean:write name='assetForm' property='fileInStorageDevice.pathFormatted'/>
</c:if>
<c:if test="${!userprofile.userOsIsWindows}">
	<a href="smb://<bean:write name='assetForm' property='fileInStorageDevice.driveOrServer'/>:139<bean:write name='assetForm' property='fileInStorageDevice.pathWithoutDriveFormatted'/>">smb://<bean:write name='assetForm' property='fileInStorageDevice.driveOrServer'/>:139<bean:write name='assetForm' property='fileInStorageDevice.pathWithoutDriveFormatted'/></a>
</c:if>