			<bright:applicationSetting id="subscription" settingName="subscription"/>
			<bright:applicationSetting id="hideLightbox" settingName="hide-lightbox"/>
			
			<bean:parameter name="showadd" id="showadd" value="false"/>								
			
			<%-- Create the forward URL for links --%>
			<c:set var="queryString" value="id=${assetForm.asset.id}&forward=${viewUrl}&index=${pos}&categoryId=${categoryId}&categoryTypeId=${categoryTypeId}&${params}" />
	
			<%-- Only show add to basket if this is not a cms user and if not hiding lightbox and the asset has a file --%>
			<c:if test="${!userprofile.isFromCms && !hideLightbox}">
				
				<c:choose>
					<c:when test="${ecommerce && usePriceBands}">
						
						<%-- If downloadable --%>
						<c:if test="${assetForm.userCanDownloadAsset}">
							
							<%-- show a link so that the user can choose to download --%>
							<c:if test="${!showadd}">
								<li class="link">
									 <a href="<c:out value='../action/addAssetPricesToAssetBox?${queryString}&showadd=true' />" class="lbAdd"><bright:cmsWrite identifier="e-purchase-other-format" filter="false"/></a> 
								</li>								
							</c:if>
							
							<%-- Add link --%>
							<logic:notEqual name="assetForm" property="assetInAssetBox" value="true">									
								<li class="link">
									 <a href="<c:out value='../action/addToAssetBox?${queryString}' />" class="lbAdd"><bright:cmsWrite identifier="link-add-to-lightbox" filter="false" /></a> 
								</li>
							 </logic:notEqual>							
						</c:if>
						
						<%-- Always show a remove link --%>
						 <logic:equal name="assetForm" property="assetInAssetBox" value="true">
							 <li class="link">
								<a href="<c:out value='../action/removeFromAssetBox?${queryString}' />" class="lbRemove"><bright:cmsWrite identifier="link-remove-lightbox" filter="false" /></a> 
							 </li>		
						 </logic:equal>					
						
	
						<%-- Only show price band selector if the image needs to be purchased before download, or if showadd=true --%>
						<c:if test="${ (!assetForm.userCanDownloadAsset && assetForm.userCanDownloadAssetWithApproval) || showadd }">
																				
							<li class="border text pricebands">
								
								<logic:equal name="assetForm" property="hasErrors" value="true"> 
									<div class="error">
										<logic:iterate name="assetForm" property="errors" id="errorText">
											<bright:writeError name="errorText" /><br />
										</logic:iterate>
									</div>
								</logic:equal>
								
								<form action="../action/addAssetPricesToAssetBox" method="get"   >
									<input type="hidden" name="id" value="<c:out value='${assetForm.asset.id}' />" />
									<input type="hidden" name="showadd" value="<c:out value='${showadd}' />" />
									<input type="hidden" name="forward" value="<c:out value='${viewUrl}' />" />
									<input type="hidden" name="index" value="<c:out value='${pos}' />" />
									<input type="hidden" name="categoryId" value="<c:out value='${categoryId}' />" />
									<input type="hidden" name="categoryTypeId" value="<c:out value='${categoryTypeId}' />" />
									
									<c:set var="numDownloadPriceBands" value="${assetForm.numDownloadPriceBands}"/>	
									<c:set var="numPrintPriceBands" value="${assetForm.numPrintPriceBands}"/>	
															
									<%-- Show download price bands --%>
									<c:if test="${numDownloadPriceBands > 0}">
										<table class="priceband-table">
										<tr>
											<th colspan="2"><bright:cmsWrite identifier="e-term-format" filter="false"/>:</th><th><bright:cmsWrite identifier="e-price" filter="false"/>:</th> 
										</tr>
										<c:forEach var='priceband' items='${assetForm.priceBands}'>
											
											<c:if test="${priceband.priceBandType.id == 1}">
												
												<tr>
												
												<%-- Check if already in basket --%>
												<c:set var="bSelected" value="false" />											 
												<c:forEach var="price" items="${userprofile.assetBox.assetPrices[assetForm.asset.id]}">
													<c:if test="${price.priceBand.id == priceband.id}"><c:set var="bSelected" value="true" /></c:if>
												</c:forEach>											 
	
												<td class="first">											
													<input type="checkbox" name="downloadpriceband_<bean:write name='priceband' property='id'/>" class="download-priceband"   <c:if test="${bSelected}">checked="checked"</c:if> />																										
												</td>
												
												<td class="second">
													<c:out value="${priceband.name}" />		
												</td>
												
												<td>
													<c:choose>
														<c:when test="${priceband.isCommercial}">
															<bright:cmsWrite identifier="e-tbc" filter="false"/>
														</c:when>
														<c:otherwise>														
															<bright:writeMoney name="priceband" property="basePrice.displayAmount" />	
														</c:otherwise>
													</c:choose>
												</td>
												 
	 											 </tr>										
													
											</c:if>
												
										</c:forEach>
										</table> 
									</c:if>
									
									<%-- Show print price bands --%>
									<c:if test="${numPrintPriceBands > 0}">
										<h3 class="prints-section" <c:if test="${assetForm.numDownloadPriceBands == 0}">style="border-top: 0px;"</c:if> >Prints</h3>
										<table class="priceband-table">
											<tr><th class="first"><bright:cmsWrite identifier="e-quantity" filter="false"/>:</th><th class="second"><bright:cmsWrite identifier="e-term-format" filter="false"/>:</th><th><bright:cmsWrite identifier="e-term-first-print" filter="false"/>:</th><th><bright:cmsWrite identifier="e-term-additional-prints" filter="false"/>:</th></tr>
											
											<c:forEach var='priceband' items='${assetForm.priceBands}'>
												
												<c:if test="${priceband.priceBandType.id == 2}">
												
													<tr>
												
													<c:set var="iQuantity" value="" />
													<c:forEach var="price" items="${userprofile.assetBox.assetPrices[assetForm.asset.id]}">
														<c:if test="${price.priceBand.id == priceband.id}">
															<c:set var="iQuantity" value="${price.quantity}" />
														</c:if>
													</c:forEach>
													
													<td class="first">
													<input type="text" class="print-priceband" name="printpriceband_<bean:write name='priceband' property='id'/>"    value="<c:out value='${iQuantity}' />" size="2" maxlength="5"  styleClass="text" />
													</td>	
																									 
													 
													<td class="second">
													<c:out value="${priceband.name}" />			
													</td>
													
													<td>
														<bright:writeMoney name="priceband" property="firstUnitPrice.displayAmount" />	
													</td>
													
													<td>
														<bright:writeMoney name="priceband" property="unitPrice.displayAmount" />	
													</td>
													
													</tr>
												</c:if>
															
											</c:forEach>
										
										</table>
									</c:if>

									<div>				
										<%-- Name for button --%>
										<c:choose>
											<c:when test="${assetForm.assetInAssetBox}">
												<c:set var="sButton"><bright:cmsWrite identifier="e-link-update-basket" filter="false"/></c:set>
											</c:when>
											<c:otherwise>
												<c:if test="${numPrintPriceBands + numDownloadPriceBands > 0}">
													<input type="hidden" name="purchaseItem" value="true"/>
												</c:if>
												<c:set var="sButton"><bright:cmsWrite identifier="link-add-to-lightbox" filter="false" /></c:set>
											</c:otherwise>
										</c:choose>
										<c:if test="${!assetForm.assetInAssetBox || numPrintPriceBands + numDownloadPriceBands > 0}">
											<div class="basket-actions">
												<input class="button" type="Submit" value="<c:out value='${sButton}' />" />	
												
												<logic:equal name="assetForm" property="assetInAssetBox" value="true">					
													<%-- Link for go to basket --%>
												 	<a href="<c:out value='../action/viewAssetBox' />" class="view-basket"><bright:cmsWrite identifier="e-view-basket" filter="false" replaceVariables="true" /></a> 
												</logic:equal>		
											</div>
										</c:if>
									</div>
								</form>
							</li>
							<c:choose>
								<c:when test="${numPrintPriceBands + numDownloadPriceBands > 0}">
									<li>
									<%-- Content managed message --%>
										<div id="basket-message"><bright:cmsWrite identifier="add-to-basket-text" filter="false"/></div>
		 							</li>
	 							</c:when>
	 							<c:otherwise>
	 								<c:choose>
			 							<c:when test="${assetForm.assetInAssetBox}">
			 								<li class="link">
			 								<bright:cmsWrite identifier="e-snippet-cannot-purchase" filter="false"/>
			 								</li>
			 							</c:when>
			 							<c:otherwise>
			 								<li class="link">
			 									<bright:cmsWrite identifier="e-snippet-no-price" filter="false"/>
			 								</li>
			 							</c:otherwise>
		 							</c:choose>
	 							</c:otherwise>
	 						</c:choose>
						</c:if>
										
					</c:when>
					<c:otherwise>
					
						<%-- Subscription message --%>
						<c:if test="${!assetForm.userCanDownloadAsset && subscription && !userprofile.isLoggedIn}">
							<li class="border text">
								<bright:cmsWrite identifier="e-subscribe" filter="false"/>
							</li>
						</c:if>
						
						<%-- Message for when user has download with approval --%>
						<c:if test="${!assetForm.userCanDownloadAsset && assetForm.userCanDownloadAssetWithApproval}">					
							<c:choose>
								<c:when test="${ecommerce}">
									<%-- None - Put help text by the buttons --%>
														
								</c:when>
								<c:otherwise>
			
									<c:if test="${!assetForm.assetInAssetBox}">
										<li class="border cms"><bright:cmsWrite identifier="snippet-request-dl-add-lightbox" filter="false"/></li>
									</c:if>
									<c:if test="${assetForm.assetInAssetBox}">
										<li class="border cms"><bright:cmsWrite identifier="snippet-request-dl-via-lightbox" filter="false"/></li>
									</c:if>
									
								</c:otherwise>
							</c:choose>					
						</c:if>
			
									
						<%-- Buttons/links depending on whether asset is in the box or not --%>
						<logic:notEqual name="assetForm" property="assetInAssetBox" value="true">
							<c:choose>
								<c:when test="${ecommerce}">
									
									<%-- Show the add to basket button, unless they can download it --%>
									<c:choose>
										<c:when test="${!assetForm.userCanDownloadAssetWithApproval}">

											<li class="link">
											 <a href="<c:out value='../action/addToAssetBox?${queryString}' />" class="lbAdd"><bright:cmsWrite identifier="link-add-to-lightbox" filter="false" /></a> 
											</li>
											
										</c:when>
										<c:otherwise>
										
											<li class="border text">
												<bright:cmsWrite identifier="e-purchase-add-to-basket" filter="false" replaceVariables="true" />
												<form action="../action/addToAssetBox" method="get">
													<input type="hidden" name="id" value="<c:out value='${assetForm.asset.id}' />" />
													<input type="hidden" name="forward" value="<c:out value='${viewUrl}' />" />
													<input type="hidden" name="index" value="<c:out value='${pos}' />" />
													<input type="hidden" name="categoryId" value="<c:out value='${categoryId}' />" />
													<input type="hidden" name="categoryTypeId" value="<c:out value='${categoryTypeId}' />" />
													<input class="button" type="Submit" value="<bright:cmsWrite identifier='link-add-to-lightbox' filter='false' />" />
												</form>
											</li>
											
										</c:otherwise>
									</c:choose>
									
								</c:when>
								<c:otherwise>
									<c:if test="${!userprofile.assetBox.shared || userprofile.assetBox.editable}">
										<li class="link">
											<a href="<c:out value='../action/addToAssetBox?${queryString}' />" class="lbAdd">
											<c:if test="${userprofile.numAssetBoxes>1}">
												<bright:cmsWrite identifier="link-add-to-lightbox" filter="false" />
											</c:if>	
											<c:if test="${userprofile.numAssetBoxes<=1}">
												<bright:cmsWrite identifier="link-add-to-mylightbox" filter="false" />
											</c:if>
											</a> 
										</li>
									</c:if>
								</c:otherwise>
							</c:choose>
						 </logic:notEqual>
				
						 <logic:equal name="assetForm" property="assetInAssetBox" value="true">
					
							<c:if test="${ecommerce && assetForm.userCanDownloadAssetWithApproval}">
								<%-- Show a link to checkout since this asset is in the basket --%>
								<li class="border text">
									<bright:cmsWrite identifier="e-purchase-item-now" filter="false" replaceVariables="true" />
								</li>
								
							</c:if>
							
							<%-- Link for removing the asset from the basket --%>
							<c:if test="${assetForm.assetInAssetBox && assetForm.canRemoveAssetFromAssetBox}">
								<li class="link">
									<a href="<c:out value='../action/removeFromAssetBox?${queryString}' />" class="lbRemove">
										<c:if test="${userprofile.numAssetBoxes>1}">
											<bright:cmsWrite identifier="link-remove-lightbox" filter="false" />
										</c:if>
										<c:if test="${userprofile.numAssetBoxes<=1}">
											<bright:cmsWrite identifier="link-remove-my-lightbox" filter="false" />
										</c:if>
									</a> 
								</li>
							</c:if>
							
						 </logic:equal>
						
					</c:otherwise>
													
				</c:choose>
																
			</c:if>


