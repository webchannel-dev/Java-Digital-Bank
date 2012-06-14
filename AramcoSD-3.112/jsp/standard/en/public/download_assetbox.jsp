<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3     	Ben Browning    14-Feb-2006     HTML/CSS tidy up
	 d4		Matt Stevenson	27-Feb-2006		Changed to facilitate usage type format
 	 d5		Matt Stevenson	28-Feb-2006		Futher changes for usage type formats
	 d6		Matt Stevenson	06-Mar-2006		Changed simple download
	 d7		Ben Browning	09-Mar-2006		Implemented tabbed download options
	 d8      Steve Bryan    18-May-2006    Added support for sub-usage types
	 d9     Matt Woollard   21-Jan-2009    Added support for downloading selected assets
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="imagesAreEmailable" settingName="images-are-emailable"/>
<bright:applicationSetting id="watermarkOptionOnDownload" settingName="watermark-option-on-download"/>

<bean:define id="downloadForm" name="assetBoxDownloadForm"/>

<head>
	
	<title><bright:cmsWrite identifier="title-download-lightbox" filter="false"/></title>
	<script src="../js/lib/prototype.js" type="text/javascript"></script>
	<script src="../js/popup.js" type="text/javascript"></script>
	<script src="../js/toggle-button.js" type="text/javascript"></script>
	<%@include file="../inc/head-elements.jsp"%>

	<%@include file="inc_download_js_variables.jsp"%>
	<script type="text/javascript">
		<!--  

      function setFormElementVisibility(ctrl,sId)
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
		
		//-->      
	</script>

	<bean:define id="section" value="download"/>
	<bean:define id="helpsection" value="download_lightbox"/>
</head>
<body id="downloadLightbox">

	<%@include file="inc_download_form_settings.jsp"%>

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-download" filter="false" /> <bright:cmsWrite identifier="my-lightbox" filter="false" /></h1>
	
         <div class="head">
         	<a href="../action/viewAssetBox"><bright:cmsWrite identifier="link-back-lightbox" filter="false"/></a>
         </div>
         
         <logic:equal name="downloadForm" property="hasErrors" value="true"> 
         	<br />
         	<div class="error">
         		<logic:iterate name="downloadForm" property="errors" id="errorText">
         			<bean:write name="errorText" filter="false"/><br />
         		</logic:iterate>
         	</div>
         </logic:equal>
         
         <h2><bright:cmsWrite identifier="subhead-dl-lightbox-zip" filter="false"/></h2>				 
			<%-- Show warning messages for a mixed bag --%>
			 <c:if test="${!downloadForm.canDownloadAllAssets}"> 
				<bright:cmsWrite identifier="subhead-zip-perm-items" filter="false"/>
			 </c:if>
			 <c:if test="${downloadForm.userCanDownloadOriginalAny && !downloadForm.userCanDownloadOriginal}"> 
				<bright:cmsWrite identifier="subhead-zip-noperm-original" filter="false"/>
			 </c:if>
			 <c:if test="${downloadForm.userCanDownloadAdvancedAny && !downloadForm.userCanDownloadAdvanced}"> 
				<bright:cmsWrite identifier="subhead-zip-noperm-advanced" filter="false"/>
			 </c:if>
			<bright:refDataList id="downloadLightboxText" transactionManagerName="DBTransactionManager" componentName="ListManager" methodName="getListItem" argumentValue="download-lightbox"/>	
			<bean:write name="downloadLightboxText" property="body" filter="false"/>
			<br/>
         
			<c:set var="showTitle" value="true"/>
			<logic:notEmpty name="downloadForm" property="assetApprovals">
				<logic:iterate name="downloadForm" property="assetApprovals" id="approval">
					<logic:notEmpty name="approval" property="adminNotes">
						<c:if test="${showTitle}"><b><bright:cmsWrite identifier="label-download-approval-notes" filter="false"/></b><br/><c:set var="showTitle" value="false"/></c:if>
						<bean:define id="assetId" name="approval" property="asset.id"/>
						<bean:define id="asset" name="downloadForm" property="<%= \"assetWithId(\" + assetId + \")\" %>"/>
						<em><bright:cmsWrite identifier="snippet-notes-for" filter="false"/> <bean:write name="asset" property="name"/>:</em> <bean:write name="approval" property="adminNotes"/><br/><br/>
					</logic:notEmpty>
				</logic:iterate>
			</logic:notEmpty>
			
			<logic:equal name="showTabs" value="true">
				<p class="tabHolder clearfix">
					<logic:equal name="advanced" value="false">
						<strong class="active"><bright:cmsWrite identifier="tab-quick-download" filter="false"/></strong>
						<strong><a href="../action/viewDownloadAssetBox?advanced=true&assetUse.usageTypeId=<c:out value='${downloadForm.selectedUsageType.id}' />"><bright:cmsWrite identifier="tab-advanced-download" filter="false"/></a></strong>	
					</logic:equal>
					<logic:equal name="advanced" value="true">
						<strong><a href="../action/viewDownloadAssetBox?advanced=false&assetUse.usageTypeId=<c:out value='${downloadForm.selectedUsageType.id}' />"><bright:cmsWrite identifier="tab-quick-download" filter="false"/></a></strong>
						<strong class="active"><bright:cmsWrite identifier="tab-advanced-download" filter="false"/></strong>
					</logic:equal>
				</p>
				<div id="tabContent">
			</logic:equal>           	
						
        	<form name="downloadForm" action="../action/downloadAssetBox" method="post" id="downloadForm">
	     		<input type="hidden" name="advanced" value="<c:out value='${advanced}'/>"/>
				<input type="hidden" name="downloadingImages" value="<c:out value='${downloadForm.containsImages}'/>"/>
				<input type="hidden" name="assetsHaveAgreement" value="<c:out value='${downloadForm.assetsHaveAgreement}'/>"/>
				<html:hidden name="downloadForm" property="onlyDownloadSelected"/>	

					<c:set var="msgDownload" value="Now choose your required format below and click 'Download'." />
					<%@include file="inc_usage_selection.jsp"%>
								
					<%-- Show the form if there is a usage selected. --%>
					<c:if test="${usageIsSelected}">
							
						<%@include file="inc_tscs_selection.jsp"%>
						<c:if test="${userprofile.isLoggedIn && imagesAreEmailable && userprofile.userCanEmailAssets}">
							<html:checkbox name="downloadForm" property="email" style="display: inline; margin-bottom: 0px;" styleId="sendemail"/> <label for="compress"><bright:cmsWrite identifier="label-email-file" filter="false"/></label><br/>
						</c:if>	
						
						<c:if test="${watermarkOptionOnDownload && !advanced}">
							<html:checkbox name="downloadForm" property="watermarkImageOption" styleId="watermarkImage" style="display: inline; margin-bottom: 0px;"/> <label for="watermarkImage"><bright:cmsWrite identifier="label-watermark-email-asset" filter="false"/></label> 
						</c:if>
													
						<%-- Download original option --%>							
					   <%-- Show this if user has permission, and if advanced/not simple or usage type is a download type	--%>				
						<c:if test="${ downloadForm.userCanDownloadOriginal && ( !simpleConvertOptionsForDownload || advanced || downloadForm.selectedUsageType.usageType.downloadOriginal)}">																		
							<div class="dialogPanel">
								<h3><bright:cmsWrite identifier="subhead-download-originals" filter="false"/></h3>
								<table class="form" cellspacing="0" cellpadding="0">
									<tr>
										<th>
											<bright:cmsWrite identifier="label-file-formats" filter="false"/> 
										</th>
										<td class="padded">
											<bean:write name="downloadForm" property="fileFormats"/> 
										</td>
									</tr>
									<tr>
										<th>
											<bright:cmsWrite identifier="label-total-size" filter="false"/> 
										</th>
										<td class="padded">
											<c:set var="fileSize" value="${downloadForm.totalFileSize/(1024*1024)}"/>
											<logic:greaterEqual name="fileSize" value="1">
												<bean:write name="fileSize" format="0.00"/><bright:cmsWrite identifier="snippet-mb" filter="false"/> (<bright:cmsWrite identifier="snippet-uncompressed" filter="false"/>)
											</logic:greaterEqual>
											<logic:lessThan name="fileSize" value="1">
												<c:set var="fileSize" value="${fileSize*1024}"/>
												<bean:write name="fileSize" format="0.00"/><bright:cmsWrite identifier="snippet-kb" filter="false"/> (<bright:cmsWrite identifier="snippet-uncompressed" filter="false"/>)
											</logic:lessThan>
										</td>
									</tr>
									<tr>
										<th>&nbsp;
										</th>
										<td class="padded">
											<html:submit styleClass="button flush" property="b_downloadOriginal" styleId="download_originals_button"><bright:cmsWrite identifier="button-download-originals" filter="false" /></html:submit>
										</td>
									</tr>
								</table>
							</div>
						</c:if>
						
					   <%-- In the case of simple convert and simple tab and download original usage type and can download original then do not show the convert --%>
					   <c:if test="${ !(simpleConvertOptionsForDownload && !advanced && downloadForm.selectedUsageType.usageType.downloadOriginal && downloadForm.userCanDownloadOriginal) }">
							<c:if test="${downloadForm.containsImages || !advanced}"> 
							<div class="dialogPanel">	
								<h3><c:choose>
										<c:when test="${downloadForm.containsImages}">
											<bright:cmsWrite identifier="subhead-download-convert-images" filter="false"/>
										</c:when>
										<c:otherwise>
											<bright:cmsWrite identifier="heading-download" filter="false" />
										</c:otherwise>
									</c:choose>
								</h3>			
									<table class="form" cellspacing="0" cellpadding="4">
								<c:if test="${downloadForm.containsImages}"> 
								<c:choose>
									<c:when test="${advanced || !simpleConvertOptionsForDownload}">
										<%-- The user can specify format/height/width etc --%>
										<tr>
											<th>
												<label for="format"><bright:cmsWrite identifier="label-file-format" filter="false"/> </label> 
											</th>
											<td>
												<bright:refDataList componentName="ImageAssetManagerImpl" methodName="getDownloadableImageFormats" id="downloadFormats"/>
												<html:select name="downloadForm" property="imageFormat" size="1" styleId="format">
													<html:options collection="downloadFormats" property="fileExtension" labelProperty="description"/>
												</html:select>
											</td>
										</tr>		
										<tr>
											<th>
												<label for="maxwidth"><bright:cmsWrite identifier="label-max-width" filter="false"/></label> 
											</th>
											<td>
												<logic:equal name="downloadForm" property="width" value="0">
													<html:text name="downloadForm" property="width" size="8" styleClass="small text" value="" styleId="maxwidth" /> 
												</logic:equal>
												<logic:notEqual name="downloadForm" property="width" value="0">
													<html:text name="downloadForm" property="width" size="8" styleClass="small text" styleId="maxwidth" /> 
												</logic:notEqual>
												(<bright:cmsWrite identifier="pixels" filter="false"/>)
											</td>
										</tr>
										<tr>
											<th>
												<label for="maxheight"><bright:cmsWrite identifier="label-max-height" filter="false"/></label>
											</th>
											<td>
												<logic:equal name="downloadForm" property="height" value="0">
													<html:text name="downloadForm" property="height" size="8" styleClass="small text" value="" styleId="maxheight" />
												</logic:equal>
												<logic:notEqual name="downloadForm" property="height" value="0">
													<html:text name="downloadForm" property="height" size="8" styleClass="small text" styleId="maxheight" /> 
												</logic:notEqual>
												(<bright:cmsWrite identifier="pixels" filter="false"/>)
											</td>
										</tr>	
										<logic:equal name="downloadForm" property="notAllRGB" value="true">
										<tr>
											<th>
												<label for="convertRGB"><bright:cmsWrite identifier="label-convert-rgb" filter="false"/></label>
											</th> 
											<td class="checkbox">
												<html:checkbox name="downloadForm" property="convertToRGB" styleClass="checkbox" styleId="convertRGB"/>
											</td>
										</tr>	
										</logic:equal>									
									</c:when>
					  
									<c:otherwise>
										<%-- The user can just pick from a dropdown of predefined sizes --%>
										<html:hidden name="downloadForm" property="imageFormat" value="jpeg"/>
										
										<tr>
											<th style="width:40px;">
												<label for="size"><bright:cmsWrite identifier="label-size" filter="false"/></label>
											</th>
											<td>
												<html:select name="downloadForm" property="usageTypeFormatId" size="1" style="width:auto;">
													<logic:iterate name="downloadForm" property="usageTypeFormats" id="format">
														<option value="<bean:write name='format' property='id'/>" <c:if test="${format.id == downloadForm.usageTypeFormatId}">selected</c:if>><bean:write name="format" property="description"/></option>
													</logic:iterate>
												</html:select>
											</td>
										</tr>	
										<logic:equal name="downloadForm" property="notAllRGB" value="true">
										<tr>
											<th>
												<label for="convertRGB"><bright:cmsWrite identifier="label-convert-rgb" filter="false"/></label>
											</th> 
											<td class="checkbox">
												<html:checkbox name="downloadForm" property="convertToRGB" styleClass="checkbox" styleId="convertRGB"/>
											</td>
										</tr>	
										</logic:equal>
									</c:otherwise>
								</c:choose>
								</c:if>
									<tr>
										<td style="width:40px;">&nbsp;
										</td>
										<td>
											<html:submit styleClass="button flush" property="b_download" styleId="submit_button"><bright:cmsWrite identifier="button-download-now" filter="false" /></html:submit> 
										</td>
									</tr>
								</table>
							</div>   <!-- End of dialogue panel -->
							</c:if>
						</c:if>
						
					</c:if> <!-- End if usage types enabled -->
					
        		<logic:equal name="showTabs" value="true">
					</div>	<!-- End of tab-content -->
				</logic:equal>
        	</form>	
         <br />	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>
