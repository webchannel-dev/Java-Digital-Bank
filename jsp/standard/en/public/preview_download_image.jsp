<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	11-May-2005		Integrated with UI design for Demo
	 d3		Ben Browning	22-Feb-2006		HTML/CSS tidy up
	 d4		Martin Wilson	26-May-2006		Removed downloadForm.encryptedDownloadPath as hidden field in form so that the download file is recreated when 'download' button is clicked. Ensures that it is in the right format (not necessarily RGB, which the preview always is) and that the download button can be clicked multiple times.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="watermarked" settingName="watermark-preview-download"/>


<head>
	
	<title><bright:cmsWrite identifier="title-preview-image" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="download"/>
	<bean:parameter name="advanced" id="advanced" value="false"/>
	<bean:parameter id="showTabs" name="showTabs" value="true"/>
	<bean:define id="rightClickMessage" value="Please do not attempt to download this image by using the right-click menu."/>
	<style type="text/css">
	<!--
		#content { margin-left:0;}
		#navCol { display: none;}
		#mainCol { margin-left:0; } 
		#footer { margin-left:0;}
		
	-->
	</style>
	
</head>
<body id="downloadPreviewPage">

	<%@include file="../inc/body_start.jsp"%>
         		
    	<h1><bright:cmsWrite identifier="heading-download-image-preview" filter="false" /></h1> 
   
		<div class="head">
	
			<c:if test="${!userprofile.isFromCms}">
				<bean:define id="actionUrl" value="../action/downloadImage"/>
			</c:if>
			<c:if test="${userprofile.isFromCms}">
				<bean:define id="actionUrl" value="../action/cmsDownloadImage"/>
			</c:if>
			
			<%@include file="inc_preview_buttons.jsp"%>
			
		</div>

		<img id="previewImage" alt="image preview" src="../servlet/display?file=<bean:write name='downloadForm' property='encryptedDownloadPath'/>" border="0" />
		<br />
		<br />


		<%@include file="inc_preview_buttons.jsp"%>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>
