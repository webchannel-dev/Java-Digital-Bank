<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

	<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
	<bright:applicationSetting id="subscription" settingName="subscription"/>
	<bright:applicationSetting id="useOrgUnits" settingName="orgunit-use" />
	<bright:applicationSetting id="useDivisions" settingName="users-have-divisions" />
	<bright:applicationSetting id="usePricing" settingName="show-pricing-section"/>
	<bright:applicationSetting id="hideLightbox" settingName="hide-lightbox"/>
	<bright:applicationSetting id="hideLightboxOnHomepage" settingName="hide-lightbox-on-homepage"/>
	<bright:applicationSetting id="noOfExtraContentPages" settingName="no-of-extra-content-pages"/>
	<bright:applicationSetting id="useOrders" settingName="show-order-section"/>
	<bright:applicationSetting id="usePriceBands" settingName="price-bands"/>
	<bright:applicationSetting id="orgUnitManageOrgUnit" settingName="orgunit-admin-manage-orgunit"/>
	<bright:applicationSetting id="orgUnitManageGroups" settingName="orgunit-admin-manage-groups"/>
	<bright:applicationSetting id="usingGroupRoles" settingName="use-group-roles"/>
	<bright:applicationSetting id="multipleLightboxes" settingName="multiple-lightboxes"/>
	<bright:applicationSetting id="marketingEnabled" settingName="marketing-enabled"/>
	<bright:applicationSetting id="assetEntitiesEnabled" settingName="asset-entities-enabled"/>
	<bright:applicationSetting id="useAgreements" settingName="agreements-enabled" />
	<bright:applicationSetting id="newsMenuEnabled" settingName="news-menu-enabled" />
	<bright:applicationSetting id="enableUnsubmittedAssets" settingName="workflow-enable-unsubmitted-assets" />
	<bright:applicationSetting id="userInvitations" settingName="user-invitations-enabled" />	
	<bright:applicationSetting id="fileTransferEnabled" settingName="file-transfer-enabled" />
	<bright:applicationSetting id="usersWithUpdateCanAddAssetsToWF" settingName="workflow-users-with-update-can-add-assets-to-workflow"/>
	<bright:applicationSetting id="extraContentAdminOnly" settingName="extra-content-pages-admin-only"/>
	<bright:applicationSetting id="extraContentLoggedInOnly" settingName="extra-content-pages-logged-in-only"/>
	<bright:applicationSetting id="batchReleasesEnabled" settingName="batch-releases-enabled"/>
	<bright:applicationSetting id="requestsEnabled" settingName="requests-enabled"/>

   <!-- Include left navigation column -->
   <logic:notPresent name="popups">
	<div id="navCol" class="clearfix">
		
		<%@include file="../customisation/navcol_top.jsp"%>
	
		<logic:present name="userprofile">
			<c:choose>	
				<c:when test="${userprofile.numAssetBoxes>1}">	
					<input type="hidden" name="inLightBoxText" id="inLightBoxText" value="<bright:cmsWrite identifier="link-in-lightbox" filter="false" />"/>
					<input type="hidden" name="addLightBoxText" id="addLightBoxText" value="<bright:cmsWrite identifier="link-add-to-lightbox" filter="false" />"/>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="inLightBoxText" id="inLightBoxText" value="<bright:cmsWrite identifier="link-in-my-lightbox" filter="false" />" />
					<input type="hidden" name="addLightBoxText" id="addLightBoxText" value="<bright:cmsWrite identifier="link-add-to-mylightbox" filter="false" />"/>
				</c:otherwise>
			</c:choose>
		</logic:present>
		
		<ul class="nav" id="main_nav">
			<li><a href="../action/viewHome" id="nav_home" <logic:equal name='section' value='home'>class="current"</logic:equal>><bright:cmsWrite identifier="menu-home" filter="false"/></a></li>

		   <logic:present name="userprofile">

		   <c:if test="${(!extraContentAdminOnly || userprofile.isAdmin) && (!extraContentLoggedInOnly || userprofile.isLoggedIn)}">
				<c:if test="${not empty noOfExtraContentPages && noOfExtraContentPages > 0}">
					
					<c:forEach begin="1" end="${noOfExtraContentPages}" var="index">
						<bean:define id="indexId" name="index"/>
						<li><a href="../action/viewContent?index=<c:out value='${index}'/>&showTitle=true" id="nav_content" <logic:equal name='section' value='<%= "extracontent" + indexId %>'>class="current"</logic:equal>><bright:cmsWrite identifier='<%="content-page-menu-"+indexId%>'/></a></li>
					
					</c:forEach>

				</c:if>
			</c:if>
		
			<logic:notEmpty name="userprofile" property="user">
			
				<logic:notEmpty name="userprofile" property="user.orgUnitExtraContent">
					<bean:size name="userprofile" property="user.orgUnitExtraContent" id="orgUnitMenuItemCount" />
					<c:if test="${orgUnitMenuItemCount > 1}">
						<li class="subholder"><a id="orgunit_content_nav_link" class="expanding"  href="#">Org Unit pages</a>
   							<ul id="orgunit_content_nav">
					</c:if>					
					<logic:iterate  name="userprofile" property="user.orgUnitExtraContent" id="content" indexId="contentindex">
							<logic:notEmpty name="content" property="menuListItemIdentifier">
								<bean:define id="menuIdentifier" name="content" property="menuListItemIdentifier" />
								<bean:define id="contentIdentifier" name="content" property="contentListItemIdentifier" />
								
								<bright:refDataList id="tempItem" componentName="ListManager" methodName="getListItem" argumentValue="<%= String.valueOf(menuIdentifier) %>" /> 
								<li <c:if test="${orgUnitMenuItemCount > 1 && (contentindex+1) == orgUnitMenuItemCount}">class="last"</c:if>><a href="viewContent?identifier=<c:out value='${content.contentListItemIdentifier}' />&amp;openNav=orgUnitPage" id="nav_orgUnitContent<c:out value='${contentindex}'/>" <logic:equal name='section' value='<%= "extracontent" + contentIdentifier %>'>class="current"</logic:equal>><bean:write name='tempItem' property='body' /></a></li>
							</logic:notEmpty>
					</logic:iterate>
					<c:if test="${orgUnitMenuItemCount > 1}">
							</ul>
						</li>
					</c:if>	
				</logic:notEmpty>
			</logic:notEmpty>
			
	

			<li><a href="../action/viewLastSearch?newSearch=true" id="nav_search" <logic:equal name='section' value='search'>class="current"</logic:equal>><bright:cmsWrite identifier="menu-search" filter="false"/></a></li>
		
			<c:choose>
				<c:when test="${categoryExplorerType == 'categories' || categoryExplorerType == 'accesslevels'}">
					<noscript>
						<%@include file="../inc/browse-link.jsp"%>
					</noscript>
				</c:when>
				<c:otherwise>
					<%@include file="../inc/browse-link.jsp"%>
				</c:otherwise>
			</c:choose>

		<c:if test="${!userprofile.isFromCms && !hideLightbox}">
			<li><a href="../action/viewAssetBox" id="nav_lightbox" <c:if test="${section == 'lightbox' || section == 'checkout'}" >class="current"</c:if>><c:if test="${multipleLightboxes}"><bright:cmsWrite identifier="a-lightbox" filter="false"/></c:if><c:if test="${!multipleLightboxes}"><bright:cmsWrite identifier="my-lightbox" filter="false"/></c:if></a></li>
		</c:if>         
			<logic:equal name="userprofile" property="isLoggedIn" value="true">
				<c:if test="${!batchReleasesEnabled}">
					<c:if test="${ecommerce}">
						<li><a href="../action/viewPurchases" id="nav_purchases" class="<logic:equal name='section' value='purchases'>current</logic:equal>"><bright:cmsWrite identifier="menu-purchases" filter="false"/></a></li>
					</c:if>
				</c:if>
					<c:choose>
						<c:when test="${userprofile.canApproveImageUploads && !batchReleasesEnabled}">
							<li><a href="../action/viewAssetUploadOrEditApproval?approvalType=uploads" id="nav_asset_approval" <logic:equal name='section' value='asset-approval'>class="current"</logic:equal>><bright:cmsWrite identifier="menu-approve" filter="false" case="mixed" /></a></li>
						</c:when>
						<c:otherwise>
							<c:if test="${userprofile.canApproveImages || (batchReleasesEnabled && userprofile.isAdmin)}">
								<li><a href="../action/viewAssetApproval?fromCatId=27&toCatId=1" id="nav_asset_approval" <logic:equal name='section' value='asset-approval'>class="current"</logic:equal>><bright:cmsWrite identifier="menu-approve" filter="false" case="mixed" /></a></li>
							</c:if>
						</c:otherwise>
					</c:choose>
				<c:if test="${userprofile.canUpdateAtAll}">
					<li><a href="../action/viewManageBatchUpdate" id="nav_batch_update" <logic:equal name='section' value='batch'>class="current"</logic:equal>><bright:cmsWrite identifier="menu-update-assets" filter="false" case="mixed" /></a></li>
				</c:if>
			</logic:equal>
		
			<logic:equal name="userprofile" property="isLoggedIn" value="true">
            <c:if test="${userprofile.canUploadAtAll}">
	            <li class="subholder"><a href="#" id="upload_nav_link" class="expanding"><bright:cmsWrite identifier="menu-upload" filter="false"/></a>
						<ul id="upload_nav">
							<li><a href="../action/viewUploadAssetFile" id="nav_upload" <logic:equal name='section' value='upload'>class="current"</logic:equal>><bright:cmsWrite identifier="menu-single" filter="false"/></a></li>
							<li <c:if test="${batchReleasesEnabled}">class="last"</c:if>><a href="../action/viewDataImport" id="nav_bulk_upload" <logic:equal name='section' value='import'>class="current"</logic:equal>><bright:cmsWrite identifier="menu-bulk" filter="false"/></a></li>
							<c:if test="${!batchReleasesEnabled}">
								<c:choose>
									<c:when test="${enableUnsubmittedAssets}">
										<li class="last"><a href="../action/viewUnsubmittedAssets" id="nav_unsubmitted" <logic:equal name='section' value='my-uploads'>class="current"</logic:equal>><bright:cmsWrite identifier="menu-my-uploads" filter="false"/></a></li>
									</c:when>
									<c:otherwise>
										<li class="last"><a href="../action/viewOwnerAssetApproval" id="nav_unsubmitted" <logic:equal name='section' value='my-uploads'>class="current"</logic:equal>><bright:cmsWrite identifier="menu-my-uploads" filter="false"/></a></li>
									</c:otherwise>
								</c:choose>
							</c:if>

						</ul>
	            </li>   
			</c:if>
			
			<c:if test="${userprofile.canSubmitRequests || userprofile.canManageRequests}"><li><a href="../action/<c:choose><c:when test='${userprofile.canSubmitRequests}'>viewMyRequests</c:when><c:otherwise>viewManageRequests</c:otherwise></c:choose>" id="nav_requests" <logic:equal name='section' value='requests'>class="current"</logic:equal>><bright:cmsWrite identifier="menu-requests" filter="false"/></a></li></c:if>

			<c:if test="${!userprofile.canUploadAtAll && userprofile.canUpdateAtAll}">
					<li><a href="../action/viewOwnerAssetApproval" id="nav_my_edits" <logic:equal name='section' value='my-uploads'>class="current"</logic:equal>><bright:cmsWrite identifier="menu-my-edits" filter="false"/></a></li>
			</c:if>
				
					<c:if test="${newsMenuEnabled}">
						<li>
							<a href="../action/viewNewsItems" id="nav_news" <logic:equal name='section' value='news'>class="current"</logic:equal>>
							<bright:cmsWrite identifier="menu-news" filter="false"/></a>
						</li>					
					</c:if>

					<c:if test="${fileTransferEnabled}">
						<li><a href="../action/viewFileTransferUpload" id="nav_file_transfer_upload" <logic:equal name='section' value='file-transfer'>class="current"</logic:equal>>
						<bright:cmsWrite identifier="menu-file-transfer" filter="false"/></a></li>
					</c:if>

					<li><a href="../action/viewContact" id="nav_contact" <logic:equal name='section' value='contact'>class="current"</logic:equal>>
					<bright:cmsWrite identifier="menu-contact" filter="false"/></a></li>

					
					<c:if test="${userInvitations && userprofile.userCanInviteUsers}"><li><a href="../action/viewInviteUsers" id="nav_invite" class="<logic:equal name='section' value='inviteUsers'>current</logic:equal>"><bright:cmsWrite identifier="heading-invite-users" filter="false"/></a></li></c:if>

					
			
				<c:catch var="e">
					<bean:define id="reportAdmin" name="userprofile" property="hasRole(reports)"/>
				</c:catch>
				<c:if test="${e != null}">
					<bean:define id="reportAdmin" value="false"/>
				</c:if>
				<c:if test="${userprofile.isAdmin || userprofile.isOrgUnitAdmin || userprofile.hasEditSubcategoryPermissions || (usingGroupRoles && reportAdmin) || userprofile.canManageBatchReleases}">
					<li class="subholder"><a id="admin_nav_link" class="expanding"  href="#">Admin</a>
   					<ul id="admin_nav">
   				</c:if>
					<c:if test="${userprofile.canManageBatchReleases}">
						<li <c:if test="${!userprofile.isAdmin}">class="last"</c:if>><a href="../action/viewManageBatchReleases" id="nav_batchreleases" class="<logic:equal name='section' value='batchReleases'>current</logic:equal>"><bright:cmsWrite identifier="batch-releases" filter="false" case="mixed" /></a></li>
					</c:if>
   					<c:if test="${userprofile.isAdmin}">
						<bright:refDataList id="showCheckout" componentName="CheckoutManager" methodName="isCheckoutEnabled" />
						<c:if test="${showCheckout}">
							<li><a href="../action/viewCheckedOutAssets" class="<logic:equal name='section' value='view-checked-out'>current</logic:equal>">Checked Out Items</a></li>
						</c:if>
					</c:if>	
   					<c:if test="${userprofile.isAdmin && assetEntitiesEnabled}">
						<li><a href="../action/viewAssetEntities" id="nav_entities" class="<logic:equal name='section' value='assetEntity'>current</logic:equal>">Asset Types</a></li>
					</c:if>	
   					<c:if test="${userprofile.isAdmin || userprofile.isOrgUnitAdmin}">
   					<li><c:choose><c:when test="${userApprovalEnabled}"><a href="../action/viewApproval" id="nav_approval" class="<logic:equal name='section' value='approval'>current</logic:equal>"></c:when><c:otherwise><a href="../action/viewRecentlyRegistered" id="nav_approval" class="<logic:equal name='section' value='approval'>current</logic:equal>"></c:otherwise></c:choose>Approve Users</a></li></c:if>
					
   					
   					<c:if test="${userprofile.isAdmin || userprofile.isOrgUnitAdmin}">
   					<c:if test="${useOrgUnits && !(userprofile.isOrgUnitAdmin && !orgUnitManageOrgUnit)}"><li><a href="../action/listOrgUnits" id="nav_orgunits" class="<logic:equal name='section' value='orgunits'>current</logic:equal>">Org Units</a></li></c:if></c:if>
					
					<c:if test="${userprofile.isAdmin || userprofile.isOrgUnitAdmin}">	
					<c:if test="${useAgreements}"><li><a href="../action/listAgreements" id="nav_agreements" class="<logic:equal name='section' value='agreements'>current</logic:equal>"><bright:cmsWrite identifier="label-agreements" filter="false"/></a></li></c:if></c:if>
   					
					
					
   					<c:if test="${userprofile.isAdmin || userprofile.isOrgUnitAdmin}">
   					<li><a href="../action/viewUserAdmin" id="nav_users" class="<logic:equal name='section' value='users'>current</logic:equal>">Users</a></li></c:if>
   					<c:if test="${userprofile.isAdmin || userprofile.isOrgUnitAdmin}">
   					<c:if test="${!(userprofile.isOrgUnitAdmin && !orgUnitManageGroups)}"><li><a href="../action/listGroups" id="nav_groups" class="<c:if test="${section=='groups' || section=='groupAttributeExclusion' || section=='groupAttributeVisibility' || section=='groupAccessLevels' || section=='groupCategories'}">current</c:if>">Groups</a></li></c:if></c:if>
   						<c:if test="${userprofile.isAdmin && useDivisions}"><li><a href="../action/listDivisions" id="nav_divisions" class="<logic:equal name='section' value='divisions'>current</logic:equal>">Divisions</a></li></c:if>
   						<c:if test="${userprofile.isAdmin}"><li><a href="../action/manageLists" id="nav_content" class="<logic:equal name='section' value='content'>current</logic:equal>">Content</a></li></c:if>
   						<c:if test="${userprofile.isAdmin || (usingGroupRoles && reportAdmin)}"><li><a href="../action/viewReportHome" id="nav_reports" class="<logic:equal name='section' value='reports'>current</logic:equal>">Reports</a></li></c:if>
   						<c:if test="${userprofile.isAdmin}"><li><a href="../action/viewUsageTypes" id="nav_usage" class="<c:if test="${section == 'usage' || section == 'usageformat' || section =='masks'}">current</c:if>">Download Options</a></li></c:if>
  				<c:if test="${userprofile.isAdmin || userprofile.isOrgUnitAdmin || userprofile.hasEditSubcategoryPermissions}">
  					<li <c:if test="${!userprofile.isAdmin && (userprofile.isOrgUnitAdmin || userprofile.hasEditSubcategoryPermissions)}">class="last"</c:if>><a href="../action/viewCategoryAdmin" id="nav_category" class="<logic:equal name='section' value='category'>current</logic:equal>">Categories</a></li>
  				</c:if>
  				<c:if test="${userprofile.isAdmin}">
  					<li><a href="../action/viewPermissionCategories" id="nav_perm_category" class="<logic:equal name='section' value='permcats'>current</logic:equal>">Access Levels</a></li>
  					<c:if test="${marketingEnabled}"><li><a href="../action/viewMarketingGroups" id="nav_marketing" class="<logic:equal name='section' value='marketing'>current</logic:equal>">Marketing</a></li></c:if>
					<c:if test="${usePricing}"><li><a href="../action/viewManagePayment" id="nav_attributes" class="<logic:equal name='section' value='payment'>current</logic:equal>">Payment</a></li></c:if>
					<li><a href="../action/viewManageAttributes" id="nav_attributes" class="<c:if test="${section == 'attributes' || section == 'sorting'}">current</c:if>">Attributes</a></li>
					<c:if test="${useOrders}"><li><a href="../action/viewManageOrders" id="nav_attributes" class="<logic:equal name='section' value='orders'>current</logic:equal>">Orders</a></li></c:if>
					<li><a href="../action/viewPublishing" id="nav_publishing" class="<logic:equal name='section' value='publishing'>current</logic:equal>">Publishing</a></li>									
					<li class="last"><a href="../action/viewSystemAdmin" id="nav_system" class="<logic:equal name='section' value='system'>current</logic:equal>">System</a></li>
				</c:if>
				<c:if test="${userprofile.isAdmin || userprofile.isOrgUnitAdmin || userprofile.hasEditSubcategoryPermissions || userprofile.canManageBatchReleases}">
	   					</ul>
	               </li>
				</c:if>
            
			</logic:equal>
			</ul>
			

         <%-- Light Box panel --%>
			<c:if test="${!userprofile.isFromCms && !hideLightbox && (!hideLightboxOnHomepage || section != 'home') && section != 'lightbox' && section != 'contact-sheet'}">
				
				<div class="lbShadow">

					<div class="lbPanel" id="lbPanel" style="margin-top:2em">
						
						<span class="label"><bright:cmsWrite identifier="snippet-lightbox-panel-label" filter="false"/></span>
						
						<div class="dropHolder singleButton">
							<h3><a href="../action/viewAssetBox" id="lbTitle" title="<bright:cmsWrite identifier="tooltip-go-to-this-lightbox" filter="false"/>" ><c:if test="${userprofile.numAssetBoxes==1}"><bright:cmsWrite identifier="my-lightbox" filter="false"/></c:if><c:if test="${userprofile.numAssetBoxes>1}"><c:out value="${userprofile.assetBox.name}" /></c:if></a></h3>
							<c:if test="${multipleLightboxes && userprofile.isLoggedIn}">
								<a href="#" class="dropLink js-enabled-show" title="<bright:cmsWrite identifier="tooltip-switch-lightbox" filter="false" case="lower"/>">&nbsp;</a>
								<div class="drop <c:if test="${userprofile.numAssetBoxes>10}">scroll</c:if>">
									<ul>
										<logic:iterate name="userprofile" property="assetBoxes" id="assetBox">
											<li <c:if test="${userprofile.assetBox.id==assetBox.id}">class="current"</c:if>><a href="../action/switchAssetBox?currentAssetBoxId=<bean:write name='assetBox' property='id' filter='false'/>" class="lbOption"><bean:write name='assetBox' property='name' filter='false'/> (<span class="count"><bean:write name='assetBox' property='assetBoxSize' filter='false'/></span>)</a></li>
										</logic:iterate>
									</ul>
									<div class="add">
										<input type="text" value="<bright:cmsWrite identifier="snippet-create-new" filter="false"/>" class="text" /> <input type="button" value="<bright:cmsWrite identifier="button-add" filter="false"/>" class="button" /><br />
									
									</div>
								</div>	
							</c:if>	
						</div>

						
						<c:if test="${multipleLightboxes && userprofile.numAssetBoxes>1}">
							<noscript>
							<form action="switchAssetBox" method="get" >
								<select style="margin-left: 10px; margin-top:0.6em; width:88px" name="currentAssetBoxId" id="selectLightbox" onchange="this.form.submit();">
									<logic:iterate name="userprofile" property="assetBoxes" id="assetBox">
										<option value="<bean:write name='assetBox' property='id'/>" <c:if test="${userprofile.assetBox.id==assetBox.id}">selected="selected"</c:if>><bean:write name='assetBox' property='name' filter='false'/></option>
									</logic:iterate>
								</select>
								<input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-go" filter="false"/>" id="SwitchAssetBox"/>
							</form>
							</noscript>
						</c:if>
					
						<c:set var="numItems" value="${userprofile.assetBox.numAssets}" />
							
						<div><!-- putting these hidden fields in a div fixes an IE6 ajax display issue -->
							<c:choose>
								<c:when test="${userprofile.numAssetBoxes>1}">
									<span class="hidden" id="itemsInLightBoxText"><bright:cmsWrite identifier="lb-items-lightboxes" filter="false" replaceVariables="true" /></span>
								</c:when>
								<c:otherwise>
									<span class="hidden" id="itemsInLightBoxText"><bright:cmsWrite identifier="lb-items-lightbox" filter="false" replaceVariables="true" /></span>
								</c:otherwise>
							</c:choose>
								
							<c:choose>
								<c:when test="${userprofile.numAssetBoxes>1}">								
									<span class="hidden" id="itemInLightBoxText"><bright:cmsWrite identifier="lb-item-lightboxes" filter="false" replaceVariables="true"/></span>
								</c:when>
								<c:otherwise>
									<span class="hidden" id="itemInLightBoxText"><bright:cmsWrite identifier="lb-item-lightbox" filter="false" replaceVariables="true"/></span>
								</c:otherwise>	
							</c:choose>
						</div>
						<p id="lbText">
							<c:choose>
								<c:when test="${userprofile.assetBox.numAssets==1}">
									<c:choose>
										<c:when test="${userprofile.numAssetBoxes==1}">
											<span id="itemsInLightbox"><bright:cmsWrite identifier="lb-item-lightbox" filter="false" replaceVariables="true" /></span>						
										</c:when>				
										<c:otherwise>
											<span id="itemsInLightbox"><bright:cmsWrite identifier="lb-item-lightboxes" filter="false" replaceVariables="true" /></span>						
										</c:otherwise>
									</c:choose>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${userprofile.numAssetBoxes==1}">
											<span id="itemsInLightbox"><bright:cmsWrite identifier="lb-items-lightbox" filter="false" replaceVariables="true" /></span>				
										</c:when>				
										<c:otherwise>
											<span id="itemsInLightbox"><bright:cmsWrite identifier="lb-items-lightboxes" filter="false" replaceVariables="true" /></span>				
										</c:otherwise>
									</c:choose>		
								</c:otherwise>
							</c:choose>
							
							<a title="<bright:cmsWrite identifier="tooltip-empty-lightbox" filter="false" case="lower"/>" href="../action/clearAssetBox" class="empty <c:if test="${userprofile.assetBox.numAssets>0 && userprofile.canClearAssetBox}">on</c:if>"><bright:cmsWrite identifier="snippet-empty"/></a>
							
						</p>	

						<c:if test="${ecommerce}">
							
							<bean:size id="numAssetsToPurchase" name="userprofile" property="assetBox.assetsWithPriceBands" />
							<bean:size id="numAssetsToApprove" name="userprofile" property="assetBox.assetsRequiringPurchaseWithoutPriceBands" />
							<c:choose>
								<c:when test="${usePriceBands}">
									<c:set var="num" value="${numAssetsToPurchase}" />
								</c:when>
								<c:otherwise>
									<c:set var="num" value="${numAssetsToApprove}" />
								</c:otherwise>
							</c:choose>
							
							<c:if test="${num>0}">
								<p style="margin-top: 4px">
									<c:choose>
										<c:when test="${usePriceBands}">
											Total price:<br />
											<c:choose>
												<c:when test="${userprofile.totalWithDiscountPriceBands.amount==0}">
													<strong><bright:cmsWrite identifier="e-tbc" filter="false"/></strong>
												</c:when>
												<c:otherwise>
													<strong><bean:write name="userprofile" property="totalWithDiscountPriceBands.displayAmount" filter="false" /></strong>
												</c:otherwise>
											</c:choose>														
										</c:when>
										<c:otherwise>
											<c:choose>
												<c:when test="${userprofile.assetBox.price.excludesTax}">
													Total price (excl VAT):<br />							
												</c:when>
												<c:otherwise>
													Total price (inc VAT): <br />
												</c:otherwise>
											</c:choose>
											<strong><bean:write name="userprofile" property="totalWithDiscount.displayAmount" filter="false" /></strong>														
										</c:otherwise>
									</c:choose>
								</p>
							</c:if>
							
						   
						   <%-- Link to checkout --%>	
							<c:if test="${num gt 0}">
								<p>
									<a href="../action/viewCheckout?checkout=Checkout">Checkout &raquo;</a>
								</p>								
								</c:if>				
																							
						</c:if>

						<p>
							<a href="../action/viewAssetBox" id="lbLink"><bright:cmsWrite identifier="link-view-contents" filter="false"/></a> 
						</p>
											
					</div>

					<%@include file="../customisation/menu_lightbox.jsp"%>

				</div>

			</c:if>
			
			<bright:refDataList id="filterGroups" transactionManagerName="DBTransactionManager" componentName="FilterManager" methodName="getFiltersByGroupFilters" passUserprofile="true"/>

			<logic:notEmpty name="filterGroups">
				<div class="lbShadow">
					<div class="lbPanel">
						<h3><bright:cmsWrite identifier="subhead-current-filter" filter="false"/></h3>
						<div style="margin:0.6em 0 0.5em 10px;">
							<form name="filterForm" action="changeSelectedFilter" id="filterForm" method="get" style="display: inline;">
								<logic:iterate name="filterGroups" id="group" indexId="groupIndex">
									<bean:define id="filters" name="group" property="filters"/>
									<logic:notEmpty name="filters">
										<span class="filterset <c:if test='${groupIndex == 0}'> filterset_first</c:if>">
										<logic:notEmpty name="group" property="name">
											<label for="filterId<bean:write name='group' property='id'/>"><bean:write name="group" property="name"/>:</label>
										</logic:notEmpty> 
										
										<select name="filterId<bean:write name='group' property='id'/>" id="filterId<bean:write name='group' property='id'/>" onchange="document.getElementById('filterForm').submit();" >
											<option value="-1">[<bright:cmsWrite identifier="snippet-no-filtering" filter="false"/>]</option>
											<logic:iterate name="filters" id="currentFilter">
												<bean:define id="filterId" name="currentFilter" property="id"/>
												<bean:define id="selectedFilter" name="userprofile" property='<%= "isSelectedFilter(" + filterId + ")" %>'/>
												<option value="<bean:write name='currentFilter' property='id'/>" <c:if test='${selectedFilter}'>selected="selected"<c:set var="found" value="true"/></c:if>><bean:write name="currentFilter" property="name" filter="false"/></option>
											</logic:iterate>
										</select>
										<input type="submit" name="send" value="Go" class="button flush js-enabled-hide" />
									</logic:notEmpty>
									</span>
								</logic:iterate>
							</form>
						</div>
					</div>
				</div>
			</logic:notEmpty>
			<logic:notPresent name="found">
				<c:set var="found" value="false"/>
			</logic:notPresent>

			<logic:equal name="found" value="false">
				<logic:notEmpty name="userprofile" property="selectedFilters">
					<c:if test="${userprofile.selectedFilterCount > 0}">
						<div class="lbShadow">
							<div class="lbPanel">
								<h3><bright:cmsWrite identifier="subhead-current-filter" filter="false"/></h3>
								<bean:define id="currFilter" name='userprofile' property='firstSelectedFilter.name'/>
								<bright:cmsWrite identifier="snippet-curr-filter-removed" filter="false" replaceVariables="true" /> 
								<p><a href="changeSelectedFilter?id=0"><bright:cmsWrite identifier="link-refresh-view" filter="false"/></a></p>
							</div>
						</div>
					</c:if>
				</logic:notEmpty>
			</logic:equal>

			

			<!-- Batch update warning -->
			<c:set var="bHasBatchUpdateController" value="false"/>
			<c:set var="bHasBulkUpdateController" value="false"/>
			<c:set var="bMoreBatch" value="false"/>
			<c:if test="${!empty userprofile.batchUpdateController && userprofile.batchUpdateController.type == 'BATCHUPDATE' && userprofile.batchUpdateController.showAlertPanel}">
				<c:set var="bHasBatchUpdateController" value="true"/>
				<c:if test="${userprofile.batchUpdateController.hasNext}">
					<c:set var="bMoreBatch" value="true"/>
				</c:if>
			</c:if>	
			<c:if test="${!empty userprofile.batchUpdateController && userprofile.batchUpdateController.type == 'BULKUPDATE'}">
				<c:set var="bHasBulkUpdateController" value="true"/>
			</c:if>	
						
			<c:if test="${bMoreBatch || bHasBulkUpdateController}">
				<div class="lbShadow">

					<div class="infoPanel">
						
						<c:if test="${bMoreBatch}">
							<p class="info"><bright:cmsWrite identifier="snippet-batch-in-progress" filter="false"/></p>
						</c:if>		
						
						<c:if test="${bHasBulkUpdateController}">
							<p class="info"><bright:cmsWrite identifier="snippet-bulk-in-progress" filter="false"/></p>
						</c:if>		
						
					</div>
				</div>						
			</c:if>
						
			<c:if test="${not empty homepageForm}">	
				<!-- Unsubmitted items warning - now only shown on homepage -->
				<c:set var="lNumUnsubmittedAssets" value="${homepageForm.numUnsubmittedAssets}"/>
				<c:set var="lNumUnsubmittedWorkflowedAssets" value="${homepageForm.numUnsubmittedWorkflowedAssets}"/>
				<c:if test="${((lNumUnsubmittedAssets gt 0) || (lNumUnsubmittedWorkflowedAssets gt 0 )) && enableUnsubmittedAssets}">
					<div class="lbShadow">
						<div class="infoPanel">
						
						    <c:if test="${(lNumUnsubmittedAssets gt 0)}">
							    <p class="info">
								    <c:choose>
									    <c:when test="${lNumUnsubmittedAssets == 1}">
										    <bright:cmsWrite identifier="snippet-alert-unsubmitted-asset" filter="false" replaceVariables="true" />
									    </c:when>
									    <c:otherwise>
										    <bright:cmsWrite identifier="snippet-alert-unsubmitted-assets" filter="false" replaceVariables="true" />
									    </c:otherwise>
								    </c:choose>
								    
							    </p>
						    </c:if>
						
						    <c:if test="${lNumUnsubmittedWorkflowedAssets gt 0 }">
								<c:choose>
									<c:when test="${(lNumUnsubmittedAssets gt 0)}">
										<!-- 2 alerts in one panel, therefore divide with a dotted line -->
										<p class="ruled"> 
									</c:when>
									<c:otherwise>
							            <p class="info">
							        </c:otherwise>
								</c:choose>							     
								<c:choose>
                                    <c:when test="${lNumUnsubmittedWorkflowedAssets == 1}">
                                        <bright:cmsWrite identifier="snippet-alert-revised-asset" filter="false" replaceVariables="true" />
                                    </c:when>
                                    <c:otherwise>
                                        <bright:cmsWrite identifier="snippet-alert-revised-assets" filter="false" replaceVariables="true" />
                                    </c:otherwise>
                                </c:choose>	
                                </p>
				            </c:if>
							
						</div>
					</div>
				</c:if>
				<!-- Checked out items warning - only shown on homepage -->
				<c:if test="${homepageForm.numCheckedOutAssets gt 0}">
					<div class="lbShadow">
						<div class="infoPanel">						
							<p class="info">
								<c:set var="numCheckedOutAssets" value="${homepageForm.numCheckedOutAssets}" />	
								<c:choose>
									<c:when test="${homepageForm.numCheckedOutAssets == 1}">
										<bright:cmsWrite identifier="snippet-1-item-checked-out" filter="false" />
									</c:when>
									<c:otherwise>
										<bright:cmsWrite identifier="snippet-x-items-checked-out" filter="false" replaceVariables="true" />
									</c:otherwise>
								</c:choose>								
							</p>
						</div>
					</div>
				</c:if>				
				
			</c:if>

						
         <!-- Subscription panel -->
			<logic:notEqual name='section' value='subscription'>
				<c:if test="${subscription && userprofile.isLoggedIn}">
					<div class="lbShadow">
						<div class="lbPanel">
							<h3>Your Subscription:</h3>
							<c:if test="${userprofile.userHasValidSubscription}">
								<p>
									You have <strong><bean:write name="userprofile" property="downloadsLeft" /></strong> downloads left today.
								</p>						
								<p>
									<a href="../action/viewUserSubscriptions">Subscription details &raquo;</a> 
								</p>	
							</c:if>	
							<c:if test="${!userprofile.userHasValidSubscription}">
								<p>
									Your last subscription has now expired.
								</p>
								<p>
									<a href="../action/viewUserSubscriptions">Subscribe now!</a>
								</p>
							</c:if>			
						</div>
					</div>
				</c:if>
			</logic:notEqual>

		<c:if test="${userprofile.isFromCms}">
			<c:if test="${userprofile.cmsInfo.cancelUrl != null}">
				<div class="menuPanel">
					<p><a href="<bean:write name='userprofile' property='cmsInfo.cancelUrl'/>"><bright:cmsWrite identifier="link-return-cms" filter="false"/></a></p>
				</div>
			</c:if>
			<c:if test="${userprofile.cmsInfo.cancelUrl == null}">
				<div class="menuPanel">
					<p><bright:cmsWrite identifier="snippet-close-browser" filter="false"/></p>
				</div>
			</c:if>
		</c:if>

		</logic:present>
		<%@include file="../customisation/navcol_promobox.jsp"%>
	</div><!-- End of navCol -->
   
   </logic:notPresent>


   
