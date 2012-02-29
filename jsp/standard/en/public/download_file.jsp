<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3      Ben Browning   14-Feb-2006    HTML/CSS tidy up
	 d4      Steve Bryan    18-May-2006    Changed to handle sub-usage types
	 d5      Adam Bones    25-Oct-2006    Added isEmail
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="imagesAreEmailable" settingName="images-are-emailable"/>
<bright:applicationSetting id="imagesAreCompressable" settingName="can-compress-download"/>
<bright:applicationSetting id="canSelectMultipleUsageTypes" settingName="can-select-multiple-usage-types"/>

<head>
	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="downloadFile"/>
	<title><bright:cmsWrite identifier="title-download-file" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/toggle-button.js" type="text/javascript"></script>
	<script src="../js/popup.js" type="text/javascript"></script>

	<%@include file="inc_download_js_variables.jsp"%>
	<script type="text/javascript">
		<!--  
      function setFormElementVisibility(ctrl,sId)
      {
			if (ctrl != null)
			{
	      	if(ctrl.selectedIndex==ctrl.options.length-1)
		   	{
					document.getElementById(sId).style.display="block";
				}
				else
				{
					document.getElementById(sId).style.display="none";
				}
			}
      }
		
		//-->
	</script>


</head>
<body id="downloadPage">

	<%@include  file="inc_download_form_settings.jsp"%>
	<%@include file="../inc/body_start.jsp"%>
	<h1><bright:cmsWrite identifier="heading-download-file" filter="false" /></h1> 	
	<div class="head">
		<bean:define id="typeName" value="File"/>
		<bean:define id="asset" name="downloadForm" property="asset" />
		<%@include file="../inc/inc_asset_type.jsp"%>
			
		<logic:empty name="downloadForm" property="returnUrl">
			<a href="../action/viewAsset?id=<bean:write name='downloadForm' property='asset.id'/>">
		</logic:empty>
		<logic:notEmpty name="downloadForm" property="returnUrl">
			<a href="../action/<bean:write name='downloadForm' property='returnUrl'/>">
		</logic:notEmpty>
			<c:if test="${empty downloadForm.asset.entity || empty downloadForm.asset.entity.name}">
				<bright:cmsWrite identifier="link-back-image-details" filter="false" replaceVariables="true"/>
			</c:if>
			<c:if test="${not empty downloadForm.asset.entity && not empty downloadForm.asset.entity.name}">
				<bright:cmsWrite identifier="link-back-to" filter="false"/> <bean:write name="downloadForm" property="asset.entity.name"/> <bright:cmsWrite identifier="snippet-details" filter="false"/>
			</c:if>
		</a>
	</div>
	
	<logic:equal name="downloadForm" property="hasErrors" value="true"> 
		<br />
		<div class="error">
			<logic:iterate name="downloadForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>

	<!-- Copy, if there is any -->
	<bright:cmsWrite identifier="download" filter="false"/>
	
	<h2><bean:write name='downloadForm' property='asset.name'/></h2>				 
	
	<div>	
		<bean:define id="asset" name="downloadForm" property="asset" />
		<bean:define id="resultImgClass"><c:if test="${asset.hasConvertedImages}">image</c:if><c:if test="${!asset.hasConvertedImages}">icon</c:if></bean:define>
		<bean:define id="downloadAsDocument" value="true"/>
		<bean:define id="ignoreCheckRestrict" value="yes"/>								
		<%@include file="../inc/view_thumbnail.jsp"%>
		<%@include file="../inc/download_attributes.jsp"%>
	</div>
	
	<c:if test="${!userprofile.isFromCms}">
		<bean:define id="actionUrl" value="../action/downloadFile"/>
	</c:if>
	<c:if test="${userprofile.isFromCms}">
		<bean:define id="actionUrl" value="../action/cmsDownloadFile"/>
	</c:if>
	<br />
	
	<c:if test="${asset.isImage}">
		<form action="viewDownloadImage?id=<c:choose><c:when test="${asset.surrogateAssetId>0}"><bean:write name="asset" property="surrogateAssetId"/></c:when><c:otherwise><bean:write name="asset" property="id"/></c:otherwise></c:choose>" method="post" class="js-enabled-show" id="downloadOption">
			<c:set var="assetName" value="${asset.name}"/>
			<p><bright:cmsWrite identifier="snippet-how-dl-pdf" filter="false" replaceVariables="true" /></p>
			<ul class="radioList">
			   <li><input type="radio" name="dl_option" id="dl_file" class="radio" checked="checked" /><span><label for="dl_file"><bright:cmsWrite identifier="label-download-as-file" filter="false"/></label></span></li>
			   <li><input type="radio" name="dl_option" id="dl_image" class="radio" onclick="$j('#loading').show();$j('#downloadOption').submit();"/><label for="dl_image"><bright:cmsWrite identifier="label-download-as-image" filter="false"/> <span id="loading" style="display:none"><bright:cmsWrite identifier="snippet-loading" filter="false"/></span></label></li>
			</ul>
			
		</form>
		<p class="js-enabled-hide">
			<a href="viewDownloadImage?id=<c:choose><c:when test="${asset.surrogateAssetId>0}"><bean:write name="asset" property="surrogateAssetId"/></c:when><c:otherwise><bean:write name="asset" property="id"/></c:otherwise></c:choose>"><bright:cmsWrite identifier="link-download-as-image" filter="false"/></a>
		</p>
	</c:if>	
	
	<form action="<bean:write name='actionUrl'/>" method="post" name="downloadForm" id="downloadForm">	
  		<html:hidden name="downloadForm" property="asset.id"/>
	      		
		<c:set var="msgDownload" value="Click 'Download' to download the file." />
		<%@include file="inc_usage_selection.jsp"%>
							
		<%-- Show the form if there is a usage selected. --%>
		<c:if test="${usageIsSelected}">
			
			<%-- display secondary usage types if necessary --%>
			<c:if test="${canSelectMultipleUsageTypes}">
				<%@include file="inc_additional_usage_selection.jsp"%>
			</c:if>
						
			<%@include file="inc_tscs_selection.jsp"%>

			<c:if test="${!userprofile.isFromCms}">
				<c:if test="${imagesAreCompressable}">
					<label class="wrapping">
					<html:checkbox name="downloadForm" property="compress" styleId="compress" /> <bright:cmsWrite identifier="label-compress-file" filter="false"/></label>
				</c:if>
				<c:if test="${imagesAreEmailable && userprofile.userCanEmailAssets}">
					<label class="wrapping">
					<html:checkbox name="downloadForm" property="email" styleId="sendemail"/> <bright:cmsWrite identifier="label-email-file" filter="false"/></label>
				</c:if>
			</c:if>


			<div class="clearing">&nbsp;</div>
					
			<div class="dialogPanel">
				<h3><bright:cmsWrite identifier="subhead-download-file" filter="false" /></h3>
				<table class="form" cellspacing="0" cellpadding="0">
					<tr>
						<th>
							<bright:cmsWrite identifier="label-file-format" filter="false"/>
						</th>
						<td class="padded">
							<bean:write name="downloadForm" property="asset.format.name"/> 
						</td>
					</tr>
					<tr>
						<th>
							<bright:cmsWrite identifier="label-size" filter="false"/> 
						</th>
						<td class="padded">
							<c:set var="fileSize" value="${downloadForm.asset.fileSizeInBytes/(1024*1024)}"/>
							<logic:greaterEqual name="fileSize" value="1">
								<bean:write name="fileSize" format="0.00"/>Mb
							</logic:greaterEqual>
							<logic:lessThan name="fileSize" value="1">
								<c:set var="fileSize" value="${fileSize*1024}"/>
								<bean:write name="fileSize" format="0.00"/>Kb
							</logic:lessThan>
						</td>
					</tr>
					<tr>
						<th>&nbsp;</th>
						<td>
							<c:if test="${!userprofile.isFromCms}">
								<html:submit styleClass="button flush" property="b_downloadOriginal" styleId="submit_button"><bright:cmsWrite identifier="button-download-now" filter="false" /></html:submit>
							</c:if>
							<c:if test="${userprofile.isFromCms}">
								<html:submit styleClass="button flush" property="b_downloadOriginal" styleId="submit_button"><bright:cmsWrite identifier="button-select-for-cms" filter="false" /></html:submit>
							</c:if>

						</td>
					</tr>
				</table>
			</div>
		</c:if>				
	</form>	

	

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>