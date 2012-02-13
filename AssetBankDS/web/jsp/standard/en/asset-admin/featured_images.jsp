<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Ben Browning	06-Ju-2006		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="showImageTitle" settingName="show-image-title-on-browse"/>
<bright:applicationSetting id="titleMaxLength" settingName="browse-title-max-length"/>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewFeaturedAssets"/>
<c:set scope="session" var="imageDetailReturnName" value="Featured Images"/>



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> Featured Images</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="content"/>
	<bean:define id="helpsection" value="featured_images"/>
	<bean:define id="tabId" value="content"/>
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline">Featured Images</h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>
	
	<bean:size id="numAssets" name="assetListForm" property="assets" />
	<p>
		There are <bean:write name="numAssets" /> featured images. 
		View then edit an image if you want to remove it from the featured set (using the checkbox on the edit image screen).
	</p>

	<div class="clearing">&nbsp;</div>

	<ul class="lightbox clearfix">

	<logic:iterate name='assetListForm' property='assets' id='item' indexId='idx'>

		<li>
			<div class="detailWrapper">		
				<a href="../action/viewAsset?id=<bean:write name='item' property='id'/>" class="thumb">
					<bean:define id="resultImgClass" value="image"/>			
					<bean:define id="asset" name="item"/>
					<bean:define id="disablePreview" value="true"/>	
					<%@include file="../inc/view_thumbnail.jsp"%>
				</a>
			
				<!-- Show ID if image, title if other document -->
				<a href="../action/viewAsset?id=<bean:write name='item' property='id'/>" class="image">
				<c:choose>
				<c:when test="${item.typeId==2 && !showImageTitle}">
					ID: <bean:write name='item' property='id'/>
				</c:when>
				<c:otherwise>
					<bright:writeWithTruncateTag name="item" property="name" maxLengthBean="titleMaxLength" endString=" ..."/>
				</c:otherwise>
				</c:choose>
				</a>
	
				<c:if test="${ecommerce}">
					<c:set var="price" value="${item.price}" />
					<c:set var="typeId" value="${item.typeId}" />
					<%@include file="../inc/price.jsp"%>
				</c:if>				
			</div>	<!-- end of detailWrapper -->
			
			<p class="action">
				<a class="view" href="../action/viewAsset?id=<bean:write name='item' property='id'/>">
				<bright:cmsWrite identifier="link-view-details" filter="false" />
				</a><br />
			</p>
		</li>
	</logic:iterate>
  
  	</ul>

	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>