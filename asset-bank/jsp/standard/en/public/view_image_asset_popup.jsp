<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	04-Jan-2006		Created from view_file_asset
	 d2     Matt Woollard   12-Aug-2009      Moved image size calculations to an include
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | View Image</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
	<bean:define id="pagetitle" value="Image Details"/>

</head>

<body id="detailsPage" class="popup">

   <div id="pageWrapper">


		
		<h2><bean:write name="assetForm" property="asset.searchName"/></h2>				 
		<div class="clearfix">
			<bean:define id="asset" name="assetForm" property="asset" />
			<%@include file="../inc/view_preview.jsp"%>			
		</div>
		
		<%-- Image data --%>
		<c:set var="fileSize" value="${assetForm.asset.fileSizeInBytes/(1024*1024)}"/>
		<%@include file="inc_attribute_fields_with_extensions.jsp"%>


		<button onclick="window.close();" class="floatRight button"><bright:cmsWrite identifier="button-close"/></button>
		<br />
	</div>
</body>
</html>