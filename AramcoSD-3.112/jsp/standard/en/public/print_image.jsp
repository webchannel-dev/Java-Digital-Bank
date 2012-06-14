<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard	20-Jun-2008		Created
	 d2     Matt Woollard      12-Aug-2009      Moved image size calculations to an include
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="printImageSize" settingName="print-image-size"/>

<head>
	<title><bright:cmsWrite identifier="title-print-image" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
	<script language="Javascript1.2">
		<!--
		function printpage() {
		window.print();  
		}
		//-->
	</script>
</head>
<body id="adminPage" onload="printpage();">
	
	<c:set var="assetForm" value="${printImageForm}" />
	<bean:define id="asset" name="printImageForm" property="asset" />
	
	
	<div style="width:<c:out value="${printImageSize}"/>px; margin:2em auto;">
		<img name="myImage" id="myImage" src="../servlet/display?file=<bean:write name='file'/>" alt="" style="display:block" />
	
	
		<%-- Image data --%>    
		<c:set var="fileSize" value="${assetForm.asset.fileSizeInBytes/(1024*1024)}"/>
		<c:set var="hideFeatured" value="${true}"/>
		<c:set var="hidePromoted" value="${true}"/>
		<c:set var="hideApprovalStatus" value="${true}"/>
		<c:set var="hideRestricted" value="${true}"/>
		
		<table class="form stripey" name="stripey" cellspacing="0" cellpadding="0">	
		
		<%@include file="inc_attribute_fields.jsp"%>	
		
	</div>
				
</body>
</html>