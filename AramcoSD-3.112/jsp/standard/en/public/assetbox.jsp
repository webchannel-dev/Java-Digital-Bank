<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
	 d3     Steve Bryan		20-Sep-2005		Changed to show downloadable images followed by view only, 
											for compatability with approvals functionality.
	 d4    	Ben Browning	14-Feb-2006		HTML/CSS tidy up	
	 d5		Matt Stevenson	14-Mar-2006		Added link to relate assets
	 d6		Matt Stevenson	15-Mar-2006		Minor change to message presentation
	 d7		Matt Woollard	02-May-2008		Changed  on cd button to use listitem
	 d8		Matt Woollard	12-Jun-2008		Changed to prevent compiler error
	 d9     Matt Woollard   21-Jan-2009     Added drop down of actions on selected assets
	 d10    Matt Woollard   22-Jan-2009     Added scriptaculous import for ajax functionality
	 d11		Ben Browning	26-Apr-2010		UI rework, and jQuery switchover
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="showRequestOnCd" settingName="show-request-on-cd"/>
<bright:applicationSetting id="showBulkUpdate" settingName="show-bulk-update"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="usePriceBands" settingName="price-bands"/>
<bright:applicationSetting id="useCommercialOptions" settingName="commercial-options"/>

<bright:applicationSetting id="hideThumbnails" settingName="hide-thumbnails-on-browse-search"/>
<bright:applicationSetting id="multipleLightboxes" settingName="multiple-lightboxes"/>
<bright:applicationSetting id="getRelatedAssets" settingName="get-related-assets"/>
<bright:applicationSetting id="marketingEnabled" settingName="marketing-enabled"/>
<bright:applicationSetting id="assetEntitiesEnabled" settingName="asset-entities-enabled"/>
<bright:applicationSetting id="cacheLargeImage" settingName="cache-large-image"/>
<bright:applicationSetting id="enableSlideshow" settingName="enable-slideshow"/>
<bright:applicationSetting id="sortingEnabled" settingName="user-driven-lightbox-sorting"/>
<bright:applicationSetting id="slideshowRepurposingEnabled" settingName="slideshow-repurposing-enabled"/>

<c:if test="${not userprofile.isLoggedIn}"><c:set var="multipleLightboxes" value="${false}" scope="request"/></c:if>

<c:set var="pageTitle"><c:if test="${multipleLightboxes}"><bright:cmsWrite identifier="a-lightbox" filter="false"/>: <bean:write name="userprofile" property="assetBox.name"/></c:if><c:if test="${!multipleLightboxes}"><bright:cmsWrite identifier="my-lightbox" filter="false"/></c:if></c:set>

<%@include file="../inc/set_this_page_url.jsp"%>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewAssetBox"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="a-lightbox" filter="false"/></c:set>

<head>
	<c:set var="lightBoxName" value="${userprofile.assetBox.name}" scope="request"/>
	<title><c:out value="${pageTitle}" /></title>

	<jsp:include flush="true" page="../inc/head-elements.jsp"/>
	
	<bean:define id="section" value="lightbox" toScope="request"/>
	<c:set var="tabId" value="currentAssetBox" scope="request"/>
	<script type="text/javascript" src="../js/lib/scriptaculous.js"></script>
	<script type="text/javascript">
		//Global variable which keeps count of the number of assets selected
		var checkedCount = 0;
		//Store text for confirming removal from assetbox
		var confirmRemoveMessage = "<bright:cmsWrite identifier='js-confirm-remove-selected-lightbox' filter='false'/>"
	</script>	
	<script type="text/javascript" src="../js/assetbox.js"></script>
</head>

<body id="lightboxPage" class="assetSelectPage">
	<c:set var="displayAttributeGroup" value="3" scope="request" />
	<%@include file="../inc/body_start.jsp"%>
	
	<c:set var="assetBoxForm" scope="request" value="${assetBoxForm}"/>
	<c:set var="assetBoxDownloadForm" scope="request" value="${assetBoxDownloadForm}"/>
	
	<jsp:include page="inc_lightbox_tabs.jsp"/> 
	<jsp:include page="inc_assetbox_messages.jsp"/>	
	
	<bean:define id="assetBoxes" name="userprofile" property="assetBoxes" toScope="request" />
	<bean:parameter id="type" name="type" value=""/>
	<bean:parameter id="count" name="count" value=""/>
	<bean:parameter id="newABName" name="newABName" value=""/>
	<bean:parameter id="newABId" name="newABId" value=""/>
	
	<html:form action="viewAssetBox" method="get">
		<div class="head stripBelow <c:if test="${tabsPresent}">tabsAbove</c:if>">
			
			<c:choose>
				<c:when test="${type=='copy'}">
					<div class="confirm">
					
						<p>
							<strong><c:out value="${count}"/></strong> 
							<c:if test="${count==1}">
								<bright:cmsWrite identifier="snippet-copied-asset"/>
							</c:if>
							<c:if test="${count>1}">
								<bright:cmsWrite identifier="snippet-copied-assets"/>
							</c:if>
							<a href="../action/viewAssetBox?currentAssetBoxId=${newABId}"><c:out value="${newABName}"/></a>
						</p>
					</div>
				</c:when>
				<c:when test="${type=='move'}">
					<div class="confirm">
						<p>
							<strong><c:out value="${count}"/></strong> 
							<c:if test="${count==1}">
								<bright:cmsWrite identifier="snippet-moved-asset"/>
							</c:if>
							<c:if test="${count>1}">
								<bright:cmsWrite identifier="snippet-moved-assets"/>
							</c:if>
							<a href="../action/viewAssetBox?currentAssetBoxId=${newABId}"><c:out value="${newABName}"/></a>
						</p>
					</div>
				</c:when>		
			</c:choose>
			
			<bright:cmsWrite identifier="lightbox-intro" filter="false"/>
			
			<c:if test="${multipleLightboxes && userprofile.numAssetBoxes>1}">
					<c:choose>
						<c:when test="${assetBoxForm.publicPage!=1}">
							<bright:cmsWrite identifier="a-lightbox"/>:
							<html:select  name="assetBoxForm" property="currentAssetBoxId" onchange="this.form.submit();">
								<html:options collection="assetBoxes" labelProperty="name" property="id" filter="false"/>
							</html:select>
							<input class="button" type="submit" value="<bright:cmsWrite identifier='button-go' filter='false'/>" style="margin-left:0px;" id="SwitchAssetBox"/>
							<script type="text/javascript">
								<!--
								document.getElementById('SwitchAssetBox').style.display='none';
								-->
							</script>
						</c:when>
						<c:otherwise>
							'<bean:write name="userprofile" property="assetBox.name"/>'			
						</c:otherwise>
					</c:choose>
					
			</c:if>
			<c:if test="${userprofile.numAssetBoxes==1}">
				<bright:cmsWrite identifier="my-lightbox" filter="false" /> 
			</c:if>
			<c:set var="numAssetsLb" value="${userprofile.assetBox.numAssets}" scope="request"/>
			<c:if test="${userprofile.assetBox.numAssets==1}">
				<bright:cmsWrite identifier="snippet-contains-x-item" filter="false" replaceVariables="true" />
			</c:if>
			<c:if test="${userprofile.assetBox.numAssets!=1}">
				<bright:cmsWrite identifier="snippet-contains-x-items" filter="false" replaceVariables="true" />
			</c:if>

			<c:if test="${userprofile.isAdmin && userprofile.assetBox.numAssets>0 && marketingEnabled}">
				|
				<a href="../action/viewSendMarketingEmail?assetBoxId=<bean:write name="assetBoxForm" property="currentAssetBoxId"/>">Send in marketing email &raquo;</a>
			</c:if>

			<c:if test="${userprofile.assetBox.numAssets>0 && userprofile.canClearAssetBox}">
				|
				<a href="../action/clearAssetBox" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-remove-all-lightbox" filter="false" replaceVariables="true" />');" class="empty"><bright:cmsWrite identifier="snippet-empty" filter="false"/></a>
			</c:if>
			<c:if test="${assetBoxForm.publicPage==1}">
				|
				<a href="../action/viewPublicAssetBoxes"><bright:cmsWrite identifier="link-back" filter="false"/></a>
			</c:if>
			
			<br />
			
			
		</div>
	</html:form>
	
	<c:if test="${sortingEnabled && userprofile.assetBox.numAssets > 0}">
		<div class="sortStrip" id="sortStrip" <c:if test="${assetBoxForm.sortingAttribute == -1}">style="display:none;"</c:if>>
			<logic:notEmpty name="assetBoxForm" property="sortingAttributes">
		
				<form name="sortForm" action="sortAssetBox" method="get">
					<input type="hidden" name="id" value="<bean:write name='assetBoxForm' property='currentAssetBoxId' />" />
					<label for=""><bright:cmsWrite identifier="label-sort-by" /></label>
					<c:choose>
						<c:when test="${assetBoxForm.sortingAttribute > 0}">
							<c:set var="matchAtt" value="${assetBoxForm.sortingAttribute}"/>
							<c:set var="ascend" value="${!assetBoxForm.sortDescending}"/>
						</c:when>
						<c:otherwise>
							<c:set var="matchAtt" value="${userprofile.lastAssetBoxSortAttributeId}"/>
							<c:set var="ascend" value="${userprofile.lastAssetBoxSortAscending}"/>
						</c:otherwise>
					</c:choose>
					<select name="sortingAttribute" size="1">
						<option value="-1"><bright:cmsWrite identifier="label-please-choose"/></option>
						<option value="-2" <c:if test="${assetBoxForm.sortingAttribute == -2}">selected</c:if>><bright:cmsWrite identifier="label-date-added-to-lightbox"/></option>
						<logic:iterate name="assetBoxForm" property="sortingAttributes" id="attribute">
							<option value="<bean:write name='attribute' property='id'/>" <c:if test="${matchAtt == attribute.id}">selected</c:if>><bean:write name='attribute' property='label'/></option>
						</logic:iterate>
					</select> 
					<select name="sortDescending" id="sortDescending" size="1">
						<option value="false" <c:if test="${ascend}">selected</c:if>><bright:cmsWrite identifier="snippet-ascending" filter="false"/></option>
						<option value="true" <c:if test="${!ascend}">selected</c:if>><bright:cmsWrite identifier="snippet-descending" filter="false"/></option>
					</select>
					
					<input type="submit" name="submit" class="button flush" value="<bright:cmsWrite identifier='button-go' filter='false' />" />
				</form>

			</logic:notEmpty>
		</div>
	</c:if>			
	<a href="#" onclick="$j('#sortStrip').slideToggle('fast'); $j(this).toggleClass('collapse_up'); return false;" class=" sortToggle js-enabled-show expand white_small <c:if test="${assetBoxForm.sortingAttribute != -1}">collapse_up</c:if>"><bright:cmsWrite identifier="link-sorting-options" filter="false"/></a>
	
	<%-- Other sections --%>
	
         <bean:size id="canDownloadSize" name="assetBoxForm" property="listCanDownload" />
         <bean:size id="approvedSize" name="assetBoxForm" property="listApprovalApproved" />
         <bean:size id="approvalRequiredSize" name="assetBoxForm" property="listApprovalRequired" />
         <bean:size id="approvalPendingSize" name="assetBoxForm" property="listApprovalPending" />
			<bean:size id="approvalRejectedSize" name="assetBoxForm" property="listApprovalRejected" />
			
			<c:if test="${assetBoxForm.listWithPriceBands!=null && assetBoxForm.listPurchaseRequiredWithoutPriceBands!=null}">
				<bean:size id="withPriceBandsSize" name="assetBoxForm" property="listWithPriceBands" />
				<bean:size id="purchaseRequiredWithoutPriceBandsSize" name="assetBoxForm" property="listPurchaseRequiredWithoutPriceBands" />
			</c:if>
			
			<bean:size id="viewOnlySize" name="assetBoxForm" property="listViewOnly" />
	
			<%-- price bands to buy --%>
         <c:if test="${usePriceBands && withPriceBandsSize > 0}">
         	<div class="toolbar" >
         		<h2 class="section">
						<bright:cmsWrite identifier="subhead-my-purchase" filter="false" />
					</h2>
					<div class="group">
						<strong>
							<c:if test="${!userprofile.assetBox.hasCommercialUsage && useCommercialOptions}">
								<bright:cmsWrite identifier="e-total-price-excl" filter="false"/>						
								&nbsp;
								<bean:write name="userprofile" property="assetBox.basketTotal.displayAmount" filter="false" />

								<c:if test="${userprofile.maxDiscount > 0}">
									<br/><bright:cmsWrite identifier="e-label-discount" filter="false"/> <bean:write name="userprofile" property="maxDiscount"/>%
									<br/><bright:cmsWrite identifier="e-total" filter="false"/> <bean:write name="userprofile" property="totalWithDiscountPriceBands.displayAmount" filter="false" /><br/><br/>
								</c:if>
							</c:if>								
						</strong>
					</div>
					<div class="group">
						<form action="../action/viewCheckout" method="get">			
							<input type="submit" class="button" name="checkout" value="Checkout" />
						</form>
					</div>
					<div class="clearing"></div>
				</div>	
				
				
			 
				<p>
					<bright:cmsWrite identifier="e-click-checkout" filter="false"/><br />
					<c:if test="${useCommercialOptions && userprofile.assetBox.hasCommercialUsage}"><bright:cmsWrite identifier="e-commercial use price" filter="false"/></c:if><br/>
				</p>
							
         	<ul class="lightbox clearfix cart">
         		<li class="headers">
         			<div class="asset-thumb">&nbsp;</div>
         			<div class="asset-name"><bright:cmsWrite identifier="e-asset" filter="false"/></div>
         			
         			<div class="asset-details">
         			<span class="asset-format"><bright:cmsWrite identifier="e-format" filter="false"/></span>
         			<span class="asset-price"><bright:cmsWrite identifier="e-price" filter="false"/></span>
         			
         			</div>
          		</li>
         		<logic:iterate name="assetBoxForm" property="listWithPriceBands" id="assetinlist" indexId="index">
         			<bean:define id="asset" name="assetinlist" property="asset" toScope="request" />				
         			<%@include file="../public/inc_assetcart_asset.jsp"%>	 		
         		</logic:iterate>
         		        
         	</ul>
				
			</c:if>	
						
				
			<%--  Can download --%>		
         <c:if test="${canDownloadSize + approvedSize > 0}">

			
			<form action="../action/actionOnSelectedAssets" method="post" id="actionOnSelected">
				<input type="hidden" name="returnUrl" value="<c:out value='${thisUrlForGet}'/>"/>
				<c:set scope="session" var="subheadReadyDownload"><bright:cmsWrite identifier="subhead-ready-download" filter="false"/></c:set>
				
         	<h2 class="section"><bean:write name="subheadReadyDownload" filter="false"/></h2>
				
				<noscript>
					<!-- Show old controls if javascript is not enabled -->
					<div class="toolbar">
         	   	<div class="group first">
         		     	<a href="../action/viewDownloadAssetBox" class="js-enabled-hide"><bright:cmsWrite identifier="button-download-all" filter="false" /></a>
         	   	</div>	
         	   
			      	<logic:equal name="showRequestOnCd" value="true">
				      	<div class="group">
					      	<a href="../action/viewRequestAssetBox" class="js-enabled-hide"><bright:cmsWrite identifier="button-request-cd" filter="false"/></a>
				      	</div>
			      	</logic:equal>
			
				   	<div class="group">	
					   	<html:select name="assetBoxForm" property="actionOnSelectedAssets">
						   	<html:option value="0"><bright:cmsWrite identifier="snippet-actions" filter="false"/></html:option>
						   	<html:option value="1"><bright:cmsWrite identifier="snippet-download" filter="false"/></html:option>
						   	<c:if test="${cacheLargeImage}">
							   	<html:option value="2"><bright:cmsWrite identifier="snippet-compare" filter="false"/></html:option>
						   	</c:if>
						
						   	<html:option value="3"><bright:cmsWrite identifier="snippet-remove-from-lightbox" filter="false"/></html:option>
						
						   	<c:if test="${showBulkUpdate && userprofile.canUpdateAtAll}">
							   	<html:option value="5"><bright:cmsWrite identifier="heading-bulk-update" filter="false"/></html:option>
						   	</c:if>
						   	<c:if test="${userprofile.canUpdateAtAll && getRelatedAssets && !assetEntitiesEnabled && userprofile.assetBox.numAssets>1}">
							   	<html:option value="6"><bright:cmsWrite identifier='button-link-assets' filter='false'/></html:option>
						   	</c:if>
						   	<c:if test="${multipleLightboxes && userprofile.numAssetBoxes>1}">
						   		<html:option value="9"><bright:cmsWrite identifier="heading-copy-move"/></html:option>
					   		</c:if>
					   	</html:select>	
					
					
					   	<input type="submit" class="button flush" value="<bright:cmsWrite identifier='button-go' filter='false'/>" />
					
				   	</div>	
				   	<div class="clearing"></div>
				   
				   </div><!-- end of div.toolbar -->
				   
				</noscript>

				<input type="hidden" name="actionOnSelectedAssets" id="actionOnSelectedAssets" />
				<div class="toolbar js-enabled-show" id="lbTools">
         		
					<div class="group first">
	         		<p><bright:cmsWrite identifier="snippet-you-have" filter="false"/> <strong id="selectedAssetCount">0</strong> <bright:cmsWrite identifier="snippet-items-selected" filter="false"/></p>	
         			
	         		<div id="dummyButtons">
	         			<a href="#" class="linkButton" onclick="return false;"><bright:cmsWrite identifier="button-lightbox-download"/></a>	         		
	         			<a href="#" class="linkButton dropButton last" onclick="return false;"><bright:cmsWrite identifier="button-lightbox-more-actions"/></a>
	         		</div>
         			<div id="dropButtonSelected">
         				
         				<a href="../action/viewDownloadAssetBox" class="linkButton" id="downloadSelected" onclick="return false;"><bright:cmsWrite identifier="button-lightbox-download"/></a>
         				
         				<div class="dropHolder">
         					<a href="#" class="linkButton dropButton last" title="<bright:cmsWrite identifier="snippet-actions-on-selected-title-attr" filter="false"/>" onclick="return false;"><bright:cmsWrite identifier="button-lightbox-more-actions"/></a>
         			
							<ul class="dropOptions">
								<c:if test="${cacheLargeImage}">
									<li><a href="#" onclick="submitLbAction(2)" title="<bright:cmsWrite identifier="snippet-contact-selected-title-attr" filter="false"/>"><bright:cmsWrite identifier="snippet-compare" filter="false"/></a></li>
								</c:if>
								<li><a href="#" onclick="submitLbAction(3)" title="<bright:cmsWrite identifier="snippet-remove-selected-title-attr" filter="false"/>"><bright:cmsWrite identifier="snippet-remove-from-lightbox" filter="false"/></a></li>
								<c:if test="${showBulkUpdate && userprofile.canUpdateAtAll}">
									<li><a href="#" onclick="submitLbAction(5)" title="<bright:cmsWrite identifier="snippet-bulk-selected-title-attr"/>"><bright:cmsWrite identifier="heading-bulk-update" filter="false"/></a></li>
								</c:if>
								<c:if test="${userprofile.canUpdateAtAll && getRelatedAssets && !assetEntitiesEnabled && userprofile.assetBox.numAssets>1}">
									<li><a href="#" onclick="submitLbAction(6)" title="<bright:cmsWrite identifier="snippet-link-selected-title-attr" />"><bright:cmsWrite identifier='button-link-assets' filter='false'/></a></li>
								</c:if>
								<c:if test="${enableSlideshow && userprofile.assetBox.containsImages}">
									<li><a href="#" onclick="submitLbAction(8)" title="<bright:cmsWrite identifier="snippet-view-selected-slideshow-title-attr" />"><bright:cmsWrite identifier="snippet-view-as-slideshow" filter="false"/></a></li>
								</c:if>
								<c:if test="${multipleLightboxes && userprofile.numAssetBoxes>1}">
									<li><a href="#" onclick="submitLbAction(9)" title="<bright:cmsWrite identifier="snippet-copy-move-title-attr" />"><bright:cmsWrite identifier="link-copy-move-items" filter="false"/></a></li>
								</c:if>
								<c:if test="${enableSlideshow && userprofile.assetBox.containsImages && slideshowRepurposingEnabled && userprofile.isAdmin}">
									<li><a href="#" onclick="submitLbAction(11)"><bright:cmsWrite identifier="snippet-create-custom-slideshow" filter="false"/></a></li>
								</c:if>
							</ul>
         
         				</div>	
         			</div>
         		</div>
         		
         		<div class="group">

						<a href="../action/viewDownloadAssetBox" class="linkButton first"><bright:cmsWrite identifier="button-download-all" filter="false" /></a>
						<c:if test="${showRequestOnCd}">
							<a href="../action/viewRequestAssetBox" class="linkButton"><bright:cmsWrite identifier="button-request-cd" filter="false"/></a>
						</c:if>	
       
         		</div>
         		<div class="clearing"></div>
         	</div>
         	
				
         	<p class="js-enabled-show"><bright:cmsWrite identifier="snippet-select" filter="false"/>: <a href="#" onclick="lbSelectItems(true,'lightboxUl'); return false;"><bright:cmsWrite identifier="snippet-all-keywords" filter="false"/></a> | <a href="#" onclick="lbSelectItems(false,'lightboxUl'); return false;"><bright:cmsWrite identifier="snippet-none" filter="false" case="mixed"/></a></p>
         	
         	<bright:cmsWrite identifier="lightbox-downloadable" filter="false" />
				<c:if test="${approvedSize > 0 }">
        			<bright:cmsWrite identifier="lightbox-downloadable-with-approval" filter="false" />
        		</c:if>
        		
					<ul class="lightbox clearfix" id="lightboxUl">
						<logic:iterate name="assetBoxForm" property="listCanDownload" id="assetinlist" indexId="index" scope="request">
							<c:set var="index" value="${index}" scope="request"/>
							<c:set var="assetinlist" value="${assetinlist}" scope="request"/>	
							<c:set var="asset" value="${assetinlist.asset}" scope="request"/>	
							<c:set var="assetState" value="1,2" scope="request"/>
							<c:set var="listTotal" value="${assetBoxForm.noCanDownload + assetBoxForm.noApprovalApproved}" scope="request"/>
							<c:set var="collection" value="${imageDetailReturnName}: ${subheadReadyDownload}" scope="request"/>
							<jsp:include page="../public/inc_assetbox_asset.jsp"/>	 		
						</logic:iterate>
						
						<%-- Fix to ensure index carries on incrementing above existing index (otherwise links dont work) --%>
						<logic:notEmpty name="assetBoxForm" property="listCanDownload">
							<c:set var="index_previous" value="${index+1}" scope="request"/>
						</logic:notEmpty>
						
						<logic:iterate name="assetBoxForm" property="listApprovalApproved" id="assetinlist" indexId="index2">
							<c:set var="index" value="${index_previous+index2}" scope="request"/>
							<c:set var="assetinlist" value="${assetinlist}" scope="request"/>	
							<c:set var="asset" value="${assetinlist.asset}" scope="request"/>
							<c:set var="assetState" value="1,2" scope="request"/>
							<c:set var="listTotal" value="${assetBoxForm.noCanDownload + assetBoxForm.noApprovalApproved}" scope="request"/>
							<c:set var="collection" value="${imageDetailReturnName}: ${subheadReadyDownload}" scope="request"/>
							<jsp:include page="../public/inc_assetbox_asset.jsp"/> 		
						</logic:iterate>
					</ul>
					
				</form>	
				<div class="hr"></div>		
			
			</c:if>




			 

	         
	         			
			<%-- Request approval - Not price bands --%>
         <c:if test="${!usePriceBands && approvalRequiredSize > 0}">

				<c:choose>
					<c:when test="${ecommerce}">
						<div class="toolbar" >
							<c:set scope="session" var="subheadApprovalRequired"><bright:cmsWrite identifier="subhead-purchase-required"/></c:set>
         				<h2 class="section">
								<bean:write name="subheadApprovalRequired" filter="false" />
							</h2>
							<div class="group">
								<strong>
									<c:choose>
										<c:when test="${userprofile.assetBox.price.excludesTax}">
											<bright:cmsWrite identifier="e-total-price" filter="false"/> (<bright:cmsWrite identifier="e-term-excluding-tax" filter="false"/>):						
										</c:when>
										<c:otherwise>
											<bright:cmsWrite identifier="e-total-price" filter="false"/> (<bright:cmsWrite identifier="e-term-including-tax" filter="false"/>): 
										</c:otherwise>
									</c:choose>
									&nbsp;
									<%-- bean:write name="userprofile" property="assetBox.price.totalAmount.displayAmount" filter="false" / --%>
									<bean:write name="userprofile" property="totalWithDiscount.displayAmount" filter="false" /><c:if test="${userprofile.maxDiscount > 0}"> (inc. <bean:write name="userprofile" property="maxDiscount"/>% discount)</c:if>
								</strong>
							</div>
							<div class="group">
								<form action="../action/viewCheckout" method="get">			
									<input type="submit" class="button" name="checkout" value="Checkout" />
								</form>
							</div>
							<div class="clearing"></div>
						</div>	
				
						
						
	
					 
						<p>
							<bright:cmsWrite identifier="e-click-checkout" filter="false"/><br />
						</p>
															
					</c:when>
					<c:otherwise>

						<c:set scope="session" var="subheadApprovalRequired"><bright:cmsWrite identifier="subhead-approval-required"/></c:set>
						<div class="toolbar">
         	   		<h2 class="section" style="float:left; margin-top: 0;"><bean:write name="subheadApprovalRequired" filter="false" /></h2>
							<%-- User can only request approval if they are logged in --%>
							<c:choose>
								<c:when test="${userprofile.isLoggedIn}">
									<div class="group">
										<form action="../action/viewRequestApproval" method="get">			
											<input type="submit" class="button" name="approval" value="<bright:cmsWrite identifier="button-request-approval" filter="false" />" />
										</form>		
									</div>							
								</c:when>
								<c:otherwise>
									<%-- No message --%>
								</c:otherwise>
							</c:choose>
							<div class="clearing"></div>
         	   	</div><!-- end of div.toolbar -->
      
						<bright:cmsWrite identifier="lightbox-require-approval" filter="false"/>

						<c:if test="${!userprofile.isLoggedIn}">
							<bright:cmsWrite identifier="snippet-login-request-approval" filter="false"/>					
						</c:if>
		
					</c:otherwise>
				</c:choose>
								            
	        	<ul class="lightbox clearfix">
         		<logic:iterate name="assetBoxForm" property="listApprovalRequired" id="assetinlist" indexId="index">
         			<c:set var="index" value="${index}" scope="request"/>
					<bean:define id="assetinlist" name="assetinlist" toScope="request"/>
         			<bean:define id="asset" name="assetinlist" property="asset" toScope="request" />				
         			<bean:define id="assetState" value="3" toScope="request" />
         			<bean:define id="listTotal" name="assetBoxForm" property="noApprovalRequired" toScope="request" />
         			<c:set var="collection" value="${imageDetailReturnName}: ${subheadApprovalRequired}" scope="request"/>
					<jsp:include page="../public/inc_assetbox_asset.jsp"/>	 		
         		</logic:iterate>	
         	</ul>
				
				<div class="hr"></div>
			</c:if>	
				
				
			<%--  Approval pending --%>		
         <c:if test="${approvalPendingSize > 0}">

            <div class="buttons">
      		</div>
		
         	<c:set scope="session" var="subheadPendingApproval"><bright:cmsWrite identifier="subhead-pending-approval"/></c:set>
			<h2 class="section"><bean:write name="subheadPendingApproval" filter="false" /></h2>
         
         	<bright:cmsWrite identifier="lightbox-pending-approval" filter="false" />
				
				<c:if test="${ecommerce}">
					<p><bright:cmsWrite identifier="e-check-order-status" filter="false"/></p>
				</c:if>
            
        
	        	<ul class="lightbox clearfix">
         		
         		<logic:iterate name="assetBoxForm" property="listApprovalPending" id="assetinlist" indexId="index">
         			<c:set var="index" value="${index}" scope="request"/>
					<bean:define id="assetinlist" name="assetinlist" toScope="request"/>
         			<bean:define id="asset" name="assetinlist" property="asset" toScope="request" />				
         			<bean:define id="assetState" value="4" toScope="request" />
         			<bean:define id="listTotal" name="assetBoxForm" property="noApprovalPending" toScope="request" />
         			<c:set var="collection" value="${imageDetailReturnName}: ${subheadPendingApproval}" scope="request"/>
					<jsp:include page="../public/inc_assetbox_asset.jsp"/>	 		
         		</logic:iterate>
         		
         	</ul>
				<div class="hr"></div>
         </c:if>
	         
	         
			<%-- Purchased prints/recently viewed --%>
			<c:if test="${usePriceBands && purchaseRequiredWithoutPriceBandsSize > 0}">
				
		
		
				<c:set scope="session" var="subheadPrevAdded"><bright:cmsWrite identifier="subhead-prev-added"/></c:set>
				<h2 class="section">
					<bean:write name="subheadPrevAdded" filter="false" />
				</h2>
			
				<p>
					<bright:cmsWrite identifier="e-previously-added-items" filter="false" replaceVariables="true" /> <br />
					
					<c:if test="${userprofile.isLoggedIn}">
					<bright:cmsWrite identifier="e-recently-purchased-prints" filter="false"/><br />						
					</c:if>
					<bright:cmsWrite identifier="e-add-remove-basket" filter="false"/>
					</p>
			
				<ul class="lightbox clearfix">
					<logic:iterate name="assetBoxForm" property="listPurchaseRequiredWithoutPriceBands" id="assetinlist" indexId="index">
						<c:set var="index" value="${index}" scope="request"/>
						<bean:define id="assetinlist" name="assetinlist" toScope="request"/>
						<bean:define id="asset" name="assetinlist" property="asset" toScope="request" />				
						<bean:define id="assetState" value="-1" toScope="request" />
         				<bean:define id="listTotal" value="-1" toScope="request" />
         				<c:set var="collection" value="${imageDetailReturnName}: ${subheadPrevAdded}" scope="request"/>
						<jsp:include page="../public/inc_assetbox_asset.jsp"/>	 		
					</logic:iterate>		
				</ul>
				<div class="hr"></div>
		  </c:if>					
       
         
			<%-- Approval rejected --%>
         <c:if test="${approvalRejectedSize > 0}">

				<c:choose>
					<c:when test="${ecommerce}">
						
		         	<c:set scope="session" var="subheadApprovalRejected"><bright:cmsWrite identifier="subhead-purchase-rejected"/></c:set>
					<h2 class="section">
		         		<bean:write name="subheadApprovalRejected" filter="false" />
		         	</h2>
		         
		         	<bright:cmsWrite identifier="lightbox-approval-rejected" filter="false"/>
						
					</c:when>
					<c:otherwise>
								
		            <div class="buttons">
							<form action="../action/viewRequestApproval" method="get">			
								<input type="submit" class="button" name="appeal" value="<bright:cmsWrite identifier="button-resubmit" filter="false" />" />
							</form>
		      		</div>
				
		         	<c:set scope="session" var="subheadApprovalRejected"><bright:cmsWrite identifier="subhead-approval-rejected"/></c:set>
					<h2 class="section">
		         		<bean:write name="subheadApprovalRejected" filter="false" />
		         	</h2>
		         
		         	<bright:cmsWrite identifier="lightbox-approval-rejected" filter="false" />
							
					</c:otherwise>
				</c:choose>            
         
         	<ul class="lightbox clearfix">
         		<logic:iterate name="assetBoxForm" property="listApprovalRejected" id="assetinlist" indexId="index">
         			<c:set var="index" value="${index}" scope="request"/>
					<bean:define id="assetinlist" name="assetinlist" toScope="request"/>
         			<bean:define id="asset" name="assetinlist" property="asset" toScope="request" />				
         			<bean:define id="assetState" value="5" toScope="request" />
         			<bean:define id="listTotal" name="assetBoxForm" property="noApprovalRejected" toScope="request" />
         			<c:set var="collection" value="${imageDetailReturnName}: ${subheadPurchaseRejected}" scope="request"/>
					<jsp:include page="../public/inc_assetbox_asset.jsp"/>	 		
         		</logic:iterate>		
         	</ul>
				<div class="hr"></div>
        </c:if>
         
	
			<%-- View only --%>         
         <c:if test="${viewOnlySize > 0}">
         	<c:set scope="session" var="subheadViewOnly"><bright:cmsWrite identifier="subhead-view-only"/></c:set>
			<h2 class="section">
         		<bean:write name="subheadViewOnly" filter="false" />
         	</h2>
         
         	<bright:cmsWrite identifier="lightbox-view-only" filter="false" />
         
         	<ul class="lightbox clearfix">
         		<logic:iterate name="assetBoxForm" property="listViewOnly" id="assetinlist" indexId="index">
         			<c:set var="index" value="${index}" scope="request"/>
					<bean:define id="assetinlist" name="assetinlist" toScope="request"/>
         			<bean:define id="asset" name="assetinlist" property="asset" toScope="request" />				
         			<bean:define id="assetState" value="6" toScope="request" />
         			<bean:define id="listTotal" name="assetBoxForm" property="noViewOnly" toScope="request" />
         			<c:set var="collection" value="${imageDetailReturnName}: ${subheadViewOnly}" scope="request"/>
					<jsp:include page="../public/inc_assetbox_asset.jsp"/>	 		
         		</logic:iterate>		
         	</ul>
				<div class="hr"></div>				
         </c:if>
		 
	<script>
		function toggleAssetBoxSelection(lAttributeId,element)
		{
			var url = 'toggleAssetBoxSelection';
			var spanId = 'dataLookupCode';
			var checked = element.checked;
			var sParams = "id=" + lAttributeId+"&checked="+checked;
		
			var myAjax = new Ajax.Updater(spanId, url, {method: 'post', parameters: sParams, evalScripts: true});	
		}
	</script>
			
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>

