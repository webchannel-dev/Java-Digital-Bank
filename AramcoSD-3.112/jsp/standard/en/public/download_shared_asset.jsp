<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Adam Bones			02-Nov-2006		Created.
	 d2		Francis Devereux	27-Jan-2009		Pass through notify parameter to downloadSharedAsset
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<head>
	
	<title><bright:cmsWrite identifier="title-download-file" filter="false"/></title> 
	<bean:define id="section" value="login"/>
	<%@include file="../inc/head-elements.jsp"%>
</head>

<body id="loginPage">

	<div class="leftShadow">
		<div class="rightShadow">
		
		   <div id="loginPanel">
			
		   	
				<div class="logo">
					<%@include file="../customisation/logo_link.jsp"%>
				</div>
			
				
				<div class="loginForm">

					<h1><bright:cmsWrite identifier="heading-download-file" filter="false" /></h1>

					<c:choose>

						<c:when test="${downloadSharedAssetForm.fileExpired}">

							<!-- Shared asset has expired -->
							<div class="error"><bright:cmsWrite identifier="snippet-emailed-asset-expired" filter="false" /></div>

						</c:when>
	
						<c:otherwise>

							<!-- Shared asset still available -->
							<c:if test="${!downloadSharedAssetForm.conditionsAccepted}">
								<div class="error"><bright:cmsWrite identifier="failedValidationConditions" filter="false" /></div>
							</c:if>

							<p><bright:cmsWrite identifier="snippet-emailed-asset-available" filter="false" /></p>
						
							<bright:applicationSetting id="readTsCs" settingName="mustReadTsCsBeforeDownloadEmailedAsset"/>
							<bean:parameter name="downloadFile" id="downloadFile"/>
							<bean:parameter name="notify" id="notify" value=""/>
							
							<c:choose>
								<c:when test="${readTsCs}">
	
									<!-- T&C's turned on - form with action to download the asset -->
									<form action="../action/downloadSharedAsset" method="get" name="downloadForm" id="downloadForm">
										<!-- Terms and Conditions checkbox and copy -->
									    <p>
									      <span class="required">*</span><input type="checkbox" name="conditionsAccepted" value="on" id="accept_ts_and_cs_cb" style="display: inline; margin-bottom: 0px;" />
										  <label id="accept_ts_and_cs_label" for="accept_ts_and_cs_cb"><bright:cmsWrite identifier="tsandcs-shared-file-checkbox" filter="false" /></label>
									    </p>
		
										<!--  Form action to validate and download the asset -->
										<input id="download_file_hidden_input" type="hidden" name="downloadFile" value="<bean:write name='downloadFile'/>" />
										<input id="notify_hidden_input" type="hidden" name="notify" value="<bean:write name='notify'/>" />
										<input id="download_now_button" type="submit" value="<bright:cmsWrite identifier="button-download-now" filter="false" />" class="button flush" />
									</form>
	
						    	</c:when>
								<c:otherwise>
	
									<!--  T&C's turned off - simple link to download the asset -->
									<a id="download_now_link" href="downloadSharedAsset?conditionsAccepted=on&downloadFile=<bean:write name='downloadFile'/>&notify=<bean:write name='notify'/>"><bright:cmsWrite identifier="link-download-now" filter="false" /></a>
	
								</c:otherwise>
							</c:choose>
							
						</c:otherwise>
					</c:choose>
			
				</div>	<!-- end of loginForm -->
	
			</div>   <!-- end of loginPanel -->
		</div>
	</div>
</body>
</html>