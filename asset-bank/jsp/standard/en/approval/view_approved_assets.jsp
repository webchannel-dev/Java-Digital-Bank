<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
    d1      Steve Bryan    16-Nov-2005    Created for ecommerce	      
	 d2		Ben Browning	12-Apr-2006		Converted to new jsp                                 
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="showCommercialOptions" settingName="commercial-options"/>
<bright:applicationSetting id="showPriceBands" settingName="price-bands"/>


<%-- Items depending on whether this is ecommerce --%>	
<c:choose>
	<c:when test="${ecommerce}">
		<c:set var="pagetitle" value="My Purchases" />
	</c:when>
	<c:otherwise>
		<c:set var="pagetitle" value="Approved Assets" />		
	</c:otherwise>
</c:choose>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewApprovedAssets"/>
<c:set scope="session" var="imageDetailReturnName" value="${pagetitle}"/>



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <c:out value="${pagetitle}" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value="purchases"/>
	<script type="text/javascript">
		<!--  

		// when dom is ready
		$j(function () {
			initDatePicker();
		});
		//-->
	</script>	
	
</head>

<body id="lightboxPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1>
	
	<c:if test="${ecommerce}">

	<h2>
		<bright:cmsWrite identifier="e-my-downloads" filter="false"/>
		<c:choose>
			<c:when test="${showCommercialOptions}">
				&nbsp;|&nbsp;<a href="viewUserOrderOverview?orderWorkflow=0"><bright:cmsWrite identifier="e-subhead-orders" filter="false"/></a>
			</c:when>
			<c:otherwise>	
				&nbsp;|&nbsp;<a href="viewUserOrderOverview?orderWorkflow=1"><bright:cmsWrite identifier="e-subhead-orders" filter="false"/></a>
			</c:otherwise>
		</c:choose> 
	</h2>
	</c:if>
	
	<%@include file="../customisation/approval/approved_items_intro.jsp"%>	

<%-- Always show the date range form --%>
	<div style="padding:1em 0;">
		<logic:present name="assetApprovalListForm"> 
			<logic:equal name="assetApprovalListForm" property="hasErrors" value="true"> 
				<div class="error">
					<logic:iterate name="assetApprovalListForm" property="errors" id="errorText">
						<bright:writeError name="errorText" /><br />
					</logic:iterate>
				</div>
			</logic:equal>
		</logic:present>
		
		<html:form action="viewApprovedAssets" method="get">
		
		<p><span class="inlineLabel"><bright:cmsWrite identifier="e-label-from" filter="false"/></span> <html:text styleClass="small text date" name="assetApprovalListForm" property="startDateString" size="20"/> 
		

		
		&nbsp;
		<span class="inlineLabel"><bright:cmsWrite identifier="e-label-to" filter="false"/></span> <html:text styleClass="small text date" name="assetApprovalListForm" property="endDateString" size="20"/> 
		

		
		&nbsp;
		
		<input type="submit" name="submit" class="button inline" value="<bright:cmsWrite identifier="button-go-arrow" filter="false" />" />
		</p>
		
		</html:form>
	</div>

	<div class="hr"></div>	
	
<%-- Show the rest if there are no errors --%>
<logic:notEqual name="assetApprovalListForm" property="hasErrors" value="true"> 
	
<bean:size id="approvedSize" name="assetApprovalListForm" property="approvalList" />

<c:if test="${approvedSize == 0}">
	<p>
		<c:choose>
			<c:when test="${ecommerce}">
				<bright:cmsWrite identifier="e-no-downloadable-items" filter="false"/>
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="e-no-approved-items" filter="false"/>
			</c:otherwise>
		</c:choose>
	</p>
</c:if>


<c:if test="${approvedSize > 0}">
	<p>	
		<c:choose>
			<c:when test="${ecommerce}">
				<bright:cmsWrite identifier="e-purchased-items" filter="false"/><br />
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="e-approved-items" filter="false"/><br />
			</c:otherwise>
		</c:choose>		
	</p>
	<%@include file="../customisation/approval/approved_items_help_text.jsp"%>	


	<div class="hr"></div>	

	<%-- For ecommerce, the box is a bit taller --%>
	<ul class="lightbox clearfix">
		
		<logic:iterate name="assetApprovalListForm" property="approvalList"  id="assetapproval" indexId="index">
			<bean:define id="asset" name="assetapproval" property="asset" />				

			<c:if test="${asset.typeId!=2}">
				<c:set var="resultImgClass" value="icon"/>
			</c:if>
			<c:if test="${asset.typeId==2}">
				<c:set var="resultImgClass" value="image"/>
			</c:if>
	
			<li>
				<div class="detailWrapper">
					<a class="thumb" href="viewAsset?id=<bean:write name='asset' property='id'/>">
						<c:set var="ignoreCheckRestrict" value="yes"/>
						<bean:define id="disablePreview" value="true"/>	
						<%@include file="../inc/view_thumbnail.jsp"%>
					</a>
					<a href="viewAsset?id=<bean:write name='asset' property='id'/>">
						<bright:cmsWrite identifier="e-id" filter="false"/>: <bean:write name="asset" property="idWithPadding"/>
					</a><br />
					
					<c:if test="${ecommerce}">
						<logic:notEmpty name='assetapproval' property='dateApproved.displayDate'>
							<bright:cmsWrite identifier="e-purchased" filter="false"/> <bean:write name='assetapproval' property='dateApproved.displayDate' /><br />
						</logic:notEmpty>
					</c:if>
					
					<%-- Always show expiry --%>
					<c:if test="${assetapproval.expiresToday}">
						<strong style="color: red;">
					</c:if>
					<bright:cmsWrite identifier="e-expires" filter="false"/> <bean:write name='assetapproval' property='dateExpires.displayDate' /><br />
					<c:if test="${assetapproval.expiresToday}">
						</strong>
					</c:if>
				</div>
				<p class="action">
					<a class="view" href="viewAsset?id=<bean:write name='asset' property='id'/>"><bright:cmsWrite identifier="link-view-details" filter="false" /></a><br />
					<logic:equal name="asset" property="inAssetBox" value="true">
						<span class="added"><bright:cmsWrite identifier="link-in-lightbox" filter="false" /></span>
					</logic:equal>
					<logic:notEqual name="asset" property="inAssetBox" value="true">
						<a class="add" href="../action/addToAssetBox?id=<bean:write name='asset' property='id'/>&amp;forward=/action/viewApprovedAssets"><bright:cmsWrite identifier="link-add-to-lightbox" filter="false" /></a>
					</logic:notEqual>
				</p>
			</li>


		</logic:iterate>
		
	</ul>
</c:if>

</logic:notEqual>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>