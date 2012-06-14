<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<script type="text/javascript">
	function hideContent() {
		eltExpandLink = document.getElementById('expandLink');
		eltAdvOpts = document.getElementById('advOptions');
		if (eltExpandLink != null){
		document.getElementById('expandLink').style.display = 'block';
		}
		if (eltAdvOpts != null){
		document.getElementById('advOptions').style.display = 'none';
		}
	}
</script>
<!-- <bean:write name='downloadForm' property='advanced'/> : <bean:write name='downloadForm' property='selectedUsageType.id'/> : <bean:write name='downloadForm' property='usageTypesAvailable'/> -->
<bright:applicationSetting id="showUseDropdownBeforeDownload" settingName="showUseDropdownBeforeDownload"/>
<bright:applicationSetting id="repurposingEnabled" settingName="asset-repurposing-enabled"/>
<%@include file="inc_download_js_variables.jsp"%>
<script type="text/javascript">
	
	function init()
	{
	
		<%-- Do not change the button text if in CMS mode as email asset is disabled and it messes up the CMS button text --%>
		<c:if test="${!userprofile.isFromCms}">
			swapButtonText(document.getElementById("sendemail"),sEmailText,sDownloadText);
		</c:if>
		
		<c:if test="${repurposingEnabled}"> 
			if(document.getElementById("repurpose"))
			{
				swapButtonText(document.getElementById("repurpose"),sRepurposeText,sDownloadText);
			
				if(document.getElementById('downloadOriginal') && document.getElementById("repurpose").checked)
				{
					document.getElementById('downloadOriginal').style.display='none';
				}
			}
		</c:if>
		
		<c:if test="${(downloadForm.selectedUsageType.id != 0 || !downloadForm.usageTypesAvailable || !showUseDropdownBeforeDownload) && !downloadForm.asset.isAudio}">
			// create an instance of the DownloadForm object (see download-form.js) which will automatically run
			// its initialise method
			new DownloadForm(
				<bean:write name='downloadForm' property='asset.id'/>,
				<bean:write name='downloadForm' property='asset.width'/>,
				<bean:write name='downloadForm' property='asset.height'/>);
				
		</c:if>
		
		<c:if test="${!downloadForm.asset.isAudio}">
		 	updateLayer();
		</c:if>
	}
	
	<c:if test="${!downloadForm.asset.isAudio}">
	
	function updateLayer()
	{
		<logic:notEmpty name="downloadForm" property="usageTypeFormats">
		
			ctrlFormat = document.getElementById('usagetypeformat');
			ctrlLayer = document.getElementById('layerToConvert');
			
			if(ctrlLayer)
			{
				switch(ctrlFormat.options[ctrlFormat.selectedIndex].value)
				{
					<logic:iterate name="downloadForm" property="usageTypeFormats" id="format">
						<c:if test="${format.supportsMultiLayers || not empty format.preserveFormatList}">
							case '<bean:write name='format' property='id'/>':
							{
								if(<c:if test="${format.supportsMultiLayers}">true || </c:if>'<bean:write name="format" property="preserveFormatList" filter="false"/>'.indexOf('<bean:write  name="downloadForm" property="asset.format.fileExtension" filter="false"/>')>=0)
								{
									if(ctrlLayer.options[ctrlLayer.selectedIndex].value=='1')
									{
										ctrlLayer.selectedIndex=ctrlLayer.options.length-1;
									}
								}
								break;
							}
						</c:if>
					</logic:iterate>
					default:
					{
						if(ctrlLayer.options[ctrlLayer.selectedIndex].value=='0')
						{
							ctrlLayer.selectedIndex=0;
						}
					}
				}
			}
		</logic:notEmpty>
	}
	</c:if>
	
</script> 