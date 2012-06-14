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

<bright:applicationSetting id="highlightIncompleteAssets" settingName="highlight-incomplete-assets"/>
<bright:applicationSetting id="highlightRestrictedAssets" settingName="highlight-restricted-assets"/>

			<c:if test="${asset.typeId!=2}">
				<c:set var="resultImgClass" value="icon"/>
			</c:if>
			<c:if test="${asset.typeId==2}">
				<c:set var="resultImgClass" value="image"/>
			</c:if>
			<logic:notPresent name="collection">
				<bean:define id="collection" value=""/>
			</logic:notPresent>
			
		
			<li class="<logic:equal name='hideThumbnails' value='true'>noThumb clearfix </logic:equal><logic:present name='asset' property='entity'><c:if test='${not empty asset.entity.name}'><bean:write name='asset' property='entity.compactName'/> </c:if></logic:present><c:if test='${highlightRestrictedAssets && asset.isRestricted}'>restricted</c:if><c:if test="${assetinlist.requiresHighResApproval && assetinlist.approvalStatus.id!=4 && assetinlist.approvalStatus.id!=1}"> low-res-only</c:if><c:if test="${assetinlist.approvalStatus.id==4}"> approved-for-high-res</c:if><c:if test="${assetinlist.requiresHighResApproval && assetinlist.approvalStatus.id==1}"> high-res-pending</c:if>" <c:if test="${assetState=='1,2'}">id="draggable<bean:write name='asset' property='id'/>"</c:if>>
				
				<c:if test="${assetState=='1,2'}">
					<div class="selector">
						<label for="selectAsset<bean:write name='asset' property='id'/>">
							<input type="checkbox" name="selectedAssetIds" class="checkbox" id="selectAsset<bean:write name='asset' property='id'/>" value="<bean:write name='asset' property='id'/>" <c:if test="${assetinlist.isSelected}">checked="checked"</c:if> onclick="toggleAssetBoxSelection('<bean:write name="asset" property="id"/>',this);"/>
							<bright:cmsWrite identifier="label-select-item" filter="false"/>
						</label>
					</div>
				</c:if>
			
				<div class="detailWrapper<c:if test="${assetState=='1,2'}"> draggable</c:if>">			
					<a class="thumb" href="viewAsset?id=<bean:write name='asset' property='id'/>&amp;total=<bean:write name='listTotal'/>&amp;index=<c:out value="${index}"/>&amp;assetState=<c:out value="${assetState}"/>&amp;collection=<bean:write name='collection'/>">
						<bean:define id="disablePreview" value="true"/>	
						<c:set var="resultImgClass" value="icon"/>
						<logic:notEmpty name="asset" property="displayHomogenizedImageFile.path">
							<c:set var="resultImgClass" value="image"/>
						</logic:notEmpty>
						<%@include file="../inc/view_thumbnail.jsp"%>								
					</a>
					<div class="metadataWrapper">
						<c:choose>
							<c:when test="${asset.isVideo}">
								<%-- Icon for video  --%>
								<img src="../images/standard/icon/video.gif" border="0" width="15" height="11" alt="Video" class="media_icon" />
							</c:when>
							<c:when test="${asset.isAudio}">
								<%-- Icon for audio  --%>
								<img src="../images/standard/icon/audio.gif" border="0" width="13" height="12" alt="Audio" class="media_icon" />
							</c:when>
						</c:choose>	
						<bean:define id="item" name="assetinlist"/>
						<c:set var="viewUrlParams" value="id=${item.id}" />															
						<%@include file="../inc/result_asset_descriptions.jsp"%>
						<bean:define id="item" name="assetinlist" property="asset"/> <%-- Define the item as the actual asset  as we need average rating and canBeRated. --%>
						<%@include file="../inc/inc_result_views_ratings.jsp"%>
		
						<%-- Only show price info if not paid for --%>
				
						<bean:define id="bShowApprovalNotes" value="false"/>

						<c:choose>
							<c:when test='${ecommerce}'>
							
								<%-- Only show price info if not paid for --%>
								<c:if test="${! (assetinlist.isApprovalPending || assetinlist.isApprovalApproved || assetinlist.isApprovalRejected) }">
									<c:choose>
										<c:when test="${!usePriceBands}">															
											Price: <bean:write name='asset' property='price.displayAmount' filter="false" /><br />				
										</c:when>
									</c:choose>							
								</c:if>
							
								<c:set var="sApprovalGranted"><bright:cmsWrite identifier="e-paid" filter="false"/></c:set>
								<c:set var="sApprovalPending"><bright:cmsWrite identifier="e-processing-request" filter="false"/></c:set>
								<c:set var="sApprovalRejected"><bright:cmsWrite identifier="e-request-rejected" filter="false"/></c:set>
								<c:set var="sApprovalNotes"><bright:cmsWrite identifier="link-approval-details" filter="false" /></c:set>
								<br />
							</c:when>
							<c:otherwise>
								<c:set var="sApprovalGranted"><bright:cmsWrite identifier="approval-granted" filter="false"/></c:set>
								<c:set var="sApprovalPending"><bright:cmsWrite identifier="pending-approval" filter="false"/></c:set>
								<c:set var="sApprovalRejected"><bright:cmsWrite identifier="approval-rejected" filter="false"/></c:set>
								<c:set var="sApprovalNotes"><bright:cmsWrite identifier="link-approval-details" filter="false" /></c:set>
							</c:otherwise>				
						</c:choose>
					
						<%-- Approval messages are not shown if the asset has become downloadable anyway --%>				
						<logic:notEqual name="assetinlist" property="isDownloadable" value="true">
							<logic:equal name="assetinlist" property="isApprovalApproved" value="true">
									<em><c:out value="${sApprovalGranted}" /></em><br />	
									<bean:define id="bShowApprovalNotes" value="true"/>
							</logic:equal>	
						
							<logic:equal name="assetinlist" property="isApprovalPending" value="true">
								<c:set var="existItemsBeingProcessed" value="true" />
								<logic:notEmpty name="assetinlist" property="adminNotes">
									<bean:define id="bShowApprovalNotes" value="true"/>										
								</logic:notEmpty>
								<em><c:out value="${sApprovalPending}" /></em><br />					
							</logic:equal>	
						
							<logic:equal name="assetinlist" property="isApprovalRejected" value="true">
								<em class="error"><c:out value="${sApprovalRejected}" /></em><br />									
								<bean:define id="bShowApprovalNotes" value="true"/>		
							</logic:equal>	
							
						</logic:notEqual>	
						
						<%-- Do the high res approval messages --%>
						<c:choose>
							<c:when test="${assetinlist.approvalStatus.id==4}">		
								<em><bright:cmsWrite identifier="snippet-approved-for-high-res" filter="false"/></em><br />	
								<bean:define id="bShowApprovalNotes" value="true"/>	
							</c:when>					
							<c:when test="${assetinlist.requiresHighResApproval && assetinlist.approvalStatus.id==1}">		
								<em><bright:cmsWrite identifier="snippet-high-res-pending" filter="false"/></em><br />		
							</c:when>
							<c:when test="${assetinlist.requiresHighResApproval && assetinlist.approvalStatus.id==3}">		
								<em><bright:cmsWrite identifier="snippet-rejected-for-high-res" filter="false"/></em><br />		
								<bean:define id="bShowApprovalNotes" value="true"/>	
							</c:when>
							<c:when test="${assetinlist.requiresHighResApproval && !assetinlist.isNeverDownloadable}">		
								<em><bright:cmsWrite identifier="snippet-low-res-only" filter="false"/></em><br />		
							</c:when>						
						</c:choose>						
							
						<logic:equal name="bShowApprovalNotes" value="true">
							(<a href="../action/viewApprovalNotes?id=<bean:write name='asset' property='id'/>" class="note"><c:out value="${sApprovalNotes}" /></a>)						
						</logic:equal>
							
					</div>	<!-- end of metadatWrapper -->
					
						<%-- BB - added this c:if to tie in with conditions under which the asset is given a draggable id, 
						else you get a javascript error when you dont have any assets available to download (i.e. Approval 
						required) --%>
						<c:if test="${assetState=='1,2'}">
							<script type="text/javascript">
							
									new Draggable('draggable<bean:write name='asset' property='id'/>', { revert: true });
						 	
									Droppables.add('draggable<bean:write name='asset' property='id'/>', {
										hoverclass: 'hover',
										onDrop : function(droppedElement)
										{ 
											currentElement = document.getElementById('draggable<bean:write name='asset' property='id'/>');
											document.getElementById('lightboxUl').insertBefore(droppedElement, currentElement);
											moveAssetInLightbox(droppedElement.id.replace("draggable",""), <bean:write name='asset' property='id'/>);
										}
									});
							
							</script>	
						</c:if>	
				
					
				
						  
				</div>	<!-- end of detailWrapper -->	
				<%-- Output any attributes with icons here --%>
			    <div class="iconAttrWrapper">
					<logic:iterate name="descriptions" id="description" >
						<c:if test="${not empty description && not empty description.description}">

							<c:if test="${ not empty description.iconFile }">
								<p class="iconAttr <c:out value='${description.classIdentifier}'/>">
									<logic:equal name="description" property="isLink" value="true">
										<a href="viewAsset?<c:out value='${viewUrlParams}' />#<c:out value='${description.classIdentifier}'/>" title="View asset details">
									</logic:equal>
										<bean:write name="description" property="description" filter="false"/> <img src="../servlet/display?file=${description.iconFile}" /><logic:equal name="description" property="isLink" value="true"></a></logic:equal></p>
							</c:if>
						</c:if>
					</logic:iterate>  
				</div>	
				<p class="action">	
					<a class="view" href="viewAsset?id=<bean:write name='asset' property='id'/>&amp;total=<bean:write name='listTotal'/>&amp;index=<c:out value="${index}"/>&amp;assetState=<c:out value="${assetState}"/>&amp;collection=<bean:write name='collection'/>"><bright:cmsWrite identifier="link-view-details" filter="false" /></a>
					<c:choose>
						<c:when test="${!userprofile.assetBox.shared || assetinlist.addedToAssetBoxByUserId==userprofile.user.id}">
							<a class="remove" href="../action/removeFromAssetBox?id=<bean:write name='asset' property='id'/>&amp;forward=/action/viewAssetBox"><bright:cmsWrite identifier="link-remove" filter="false" /></a>
						</c:when>
						<c:otherwise>
							<span class="disabled">Added by other user</span>
						</c:otherwise>
					</c:choose>
				</p>

				<div style="" class=" upDownArrows js-enabled-hide">
					<c:if test="${index>0}">
						<a href="moveAssetInAssetBox?up=true&amp;id=<bean:write name='asset' property='id'/>" class="up" title="move up">Up</a> 			
					</c:if>
					<c:if test="${index<userprofile.assetBox.numAssets-1}">
						<a href="moveAssetInAssetBox?up=false&amp;id=<bean:write name='asset' property='id'/>" class="down" title="move down">Down</a>
					</c:if>
				</div>

			</li>
		


