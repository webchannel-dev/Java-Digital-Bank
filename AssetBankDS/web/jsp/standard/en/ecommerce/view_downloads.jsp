<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
    d1      Steve Bryan    16-Nov-2005    Created for ecommerce	      
	d2		Ben Browning	12-Apr-2006		Converted to new jsp   
	d3      Matt Woollard       11-Feb-2008     Replaced content with list items                           
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="showCommercialOptions" settingName="commercial-options"/>
<bright:applicationSetting id="showPriceBands" settingName="price-bands"/>
<bright:applicationSetting id="hideThumbnails" settingName="hide-thumbnails-on-browse-search"/>


<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewDownloads"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="e-my-downloads" filter="false"/></c:set>


<head>
	<title><bright:cmsWrite identifier="e-title-downloads" filter="false" replaceVariables="true" /></title> 
	<bean:define id="section" value="purchases"/>
	<%@include file="../inc/head-elements.jsp"%>
	<script src="../js/calendar.js" type="text/javascript"></script>
		<bean:define id="tabId" value="myDownloads"/>
</head>

<body id="lightboxPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="e-my-downloads" filter="false"/></h1>
	
	<%@include file="../ecommerce/inc_my_purchase_tabs.jsp"%>
	
	
	<%@include file="../customisation/approval/approved_items_intro.jsp"%>	

<%-- Always show the date range form --%>
	<div style="padding:1em 0;">
		<logic:present name="assetApprovalListForm"> 
			<logic:equal name="assetApprovalListForm" property="hasErrors" value="true"> 
				<div class="error">
					<logic:iterate name="assetApprovalListForm" property="errors" id="errorText">
						<bean:write name="errorText" filter="false"/><br />
					</logic:iterate>
				</div>
			</logic:equal>
		</logic:present>
		
		<html:form action="viewDownloads" method="get">
		
		<p><span class="inlineLabel"><bright:cmsWrite identifier="e-label-from" filter="false"/></span> <html:text styleClass="small text" name="assetApprovalListForm" property="startDateString" size="20"/> 
		
		<script type="text/javascript">
			document.write('<a href="javascript:;" title="Date chooser"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Date chooser" onclick="openDatePicker(document.getElementsByName(\'startDateString\')[0])" width="16" height="15" style="padding-right: 0;" ><\/a>');
		</script>
		
		&nbsp;
		<span class="inlineLabel"><bright:cmsWrite identifier="label-to" filter="false"/></span> <html:text styleClass="small text" name="assetApprovalListForm" property="endDateString" size="20"/> 
		
		<script type="text/javascript">
			document.write('<a href="javascript:;" title="Date chooser"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Date chooser" onclick="openDatePicker(document.getElementsByName(\'endDateString\')[0])" width="16" height="15" style="padding-right: 0;" ><\/a>');
		</script>
		
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
				<bright:cmsWrite identifier="e-no-downloadable-items" filter="false" replaceVariables="true" />
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="e-no-approved-items" filter="false" replaceVariables="true" />
			</c:otherwise>
		</c:choose>
	</p>
</c:if>


<c:if test="${approvedSize > 0}">
	<p>
		<c:choose>
			<c:when test="${ecommerce}">
				<bright:cmsWrite identifier="e-purchased-items" filter="false" replaceVariables="true" />
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="e-approved-items" filter="false" replaceVariables="true" />
			</c:otherwise>
		</c:choose>		
	</p>
	<br />
	<%@include file="../customisation/approval/approved_items_help_text.jsp"%>	


	<div class="hr"></div>	

	<ul class="lightbox clearfix">
		
		<logic:iterate name="assetApprovalListForm" property="approvalList"  id="assetapproval" indexId="index">
			<bean:define id="asset" name="assetapproval" property="asset" />				

			<c:if test="${asset.typeId!=2}">
				<c:set var="resultImgClass" value="icon"/>
			</c:if>
			<c:if test="${asset.typeId==2}">
				<c:set var="resultImgClass" value="image"/>
			</c:if>
	
			<li <logic:equal name="hideThumbnails" value="true">class="noThumb clearfix"</logic:equal>>
				<div class="detailWrapper">
					<a class="thumb" href="viewAsset?id=<bean:write name='asset' property='id'/>">						
						<bean:define id="ignoreCheckRestrict" value="yes"/>
						<bean:define id="disablePreview" value="true"/>	
						<%@include file="../inc/view_thumbnail.jsp"%>
					
					</a>
					
					<a href="viewAsset?id=<bean:write name='asset' property='id'/>"><bright:cmsWrite identifier="e-id" filter="false" replaceVariables="true" />: <bean:write name="asset" property="idWithPadding"/></a>
					<br />
				
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
				</div>	<!-- end of detailWrapper -->
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