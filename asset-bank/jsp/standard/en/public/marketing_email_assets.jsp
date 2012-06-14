<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Feb-2008		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="hideThumbnails" settingName="hide-thumbnails-on-browse-search"/>

<logic:notEmpty name="sendMarketingEmailForm" property="marketingEmail.name">
	<c:set scope="session" var="imageDetailReturnName"><bean:write name="sendMarketingEmailForm" property="marketingEmail.name" filter="false"/></c:set>
</logic:notEmpty>
<logic:empty name="sendMarketingEmailForm" property="marketingEmail.name">
	<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="items" filter="false"/></c:set>
</logic:empty>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />
<bean:parameter name="emailId" id="emailId"/>
<c:set scope="session" var="imageDetailReturnUrl">/action/viewEmailAssets?emailId=<c:out value="${emailId}"/></c:set>


<head>
	<title><bright:cmsWrite identifier="title-marketing-email-assets" filter="false" replaceVariables="true" /></title>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="home"/>
	<bean:define id="tabId" value="currentAssetBox"/>
</head>

<body id="lightboxPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<c:if test="${empty sendMarketingEmailForm.marketingEmail.assets}">	
		<div class="error"><bright:cmsWrite identifier="snippet-no-assets-visible-for-email" filter="false"/></div>
	</c:if>
	
	<c:if test="${not empty sendMarketingEmailForm.marketingEmail.assets}">
        
      <logic:notEmpty name="sendMarketingEmailForm" property="marketingEmail.name">	
     		<h1><bean:write name="sendMarketingEmailForm" property="marketingEmail.name" filter="false"/></h1>
     	</logic:notEmpty>
     	
     	<logic:notEmpty name="sendMarketingEmailForm" property="marketingEmail.introduction">
	     	<div style="margin-bottom: 12px;">
	     		<bean:write name="sendMarketingEmailForm" property="marketingEmail.introduction" filter="false"/>
	     	</div>
	     </logic:notEmpty>
     	
     	<ul class="lightbox clearfix">
     		<logic:iterate name="sendMarketingEmailForm" property="marketingEmail.assets" id="assetinlist" indexId="index">
     			<bean:define id="asset" name="assetinlist" property="asset" />				
     			
     			
     			
     		<c:if test="${asset.typeId!=2}">
				<c:set var="resultImgClass" value="icon"/>
			</c:if>
			<c:if test="${asset.typeId==2}">
				<c:set var="resultImgClass" value="image"/>
			</c:if>
	
			<li <logic:equal name="hideThumbnails" value="true">class="noThumb clearfix"</logic:equal>>
				<div class="detailWrapper">					
					<a class="thumb" href="viewAsset?id=<bean:write name='asset' property='id'/>">
						<bean:define id="disablePreview" value="false"/>	
						<%@include file="../inc/view_thumbnail.jsp"%>								
					</a>
					
					<bean:define id="item" name="assetinlist"/>
					<c:set var="viewUrlParams" value="id=${item.id}" />															
					<%@include file="../inc/result_asset_descriptions.jsp"%>
					
					<%-- Only show price info if not paid for --%>
				
					<bean:define id="bShowApprovalNotes" value="false"/>

					<c:choose>
						<c:when test='${ecommerce}'>
							
							<%-- Only show price info if not paid for --%>
							<c:if test="${! (assetinlist.isApprovalPending || assetinlist.isApprovalApproved || assetinlist.isApprovalRejected) }">
								<c:choose>
									<c:when test="${!usePriceBands}">															
										Price: <bright:writeMoney name='asset' property='price.displayAmount' /><br />				
									</c:when>
								</c:choose>							
							</c:if>
							
							<c:set var="sApprovalGranted" value="Paid" />
							<c:set var="sApprovalPending" value="Processing request" />
							<c:set var="sApprovalRejected" value="Request rejected" />
							<c:set var="sApprovalNotes"><bright:cmsWrite identifier="link-approval-details" filter="false" /></c:set>
						</c:when>
						<c:otherwise>
							<c:set var="sApprovalGranted" value="Approval granted" />
							<c:set var="sApprovalPending" value="Pending approval" />
							<c:set var="sApprovalRejected" value="Approval rejected" />
							<c:set var="sApprovalNotes"><bright:cmsWrite identifier="link-approval-details" filter="false" /></c:set>
						</c:otherwise>				
					</c:choose>
				
					<%-- Approval messages are not shown if the asset has become downloadable anyway --%>				
					<logic:notEqual name="assetinlist" property="isDownloadable" value="true">
						<logic:equal name="assetinlist" property="isApprovalApproved" value="true">
								<em><c:out value="${sApprovalGranted}" /></em>	
								<bean:define id="bShowApprovalNotes" value="true"/>
						</logic:equal>	
						
						<logic:equal name="assetinlist" property="isApprovalPending" value="true">
							<c:set var="existItemsBeingProcessed" value="true" />
							<logic:notEmpty name="assetinlist" property="adminNotes">
								<bean:define id="bShowApprovalNotes" value="true"/>										
							</logic:notEmpty>
							<em><c:out value="${sApprovalPending}" /></em>					
						</logic:equal>	
						
						<logic:equal name="assetinlist" property="isApprovalRejected" value="true">
							<em class="error"><c:out value="${sApprovalRejected}" /></em>									
							<bean:define id="bShowApprovalNotes" value="true"/>		
						</logic:equal>	
					</logic:notEqual>				
				</div>	<!-- end of detailWrapper -->		
					
				<p class="action">
					<bean:define id="item" name="asset"/>
					<c:set var="forwardParams">forward=/action/viewEmailAssets&emailId=<c:out value="${emailId}"/></c:set>
					<%@include file="../inc/add_to_lightbox.jsp"%>
				</p>
			</li>
		
     		</logic:iterate>		
     	</ul>
			
	</c:if>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>
