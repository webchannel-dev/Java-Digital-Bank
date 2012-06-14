<%--
	Displays the attribute field values for an asset.

	Include this JSP directly if you are displaying multiple assets per page or
	doing something else non-standard which means that generic plugin
	modifications shouldnt be applied.	If you are doing a standard view
	of a single asset then include inc_attribute_fields_with_extensions.jsp instead
	so that plugin modifications are applied.


	History:
	 d1		Martin Wilson		24-Oct-2005		Created.
	 d2      Steve Bryan		20-Dec-2005		Added permissions categories.
	 d3		Matt Stevenson		08-Dec-2006		Changed browse links to allow for explorer
	 d4      Steve Bryan       22-Feb-2007    Changed empty check to work for all static fields
	 d5     Matt Woollard		02-May-2008		Added audit info link
	 d6     Matt Woollard       23-Jul-2009     Changes for printing assets
	 d7     Matt Woollard       12-Aug-2009      Moved image size calculations to an include
--%>

<%@ page import="com.bright.framework.category.bean.Category" %>
<%@ page import="com.bright.assetbank.application.bean.Asset" %>

<bright:applicationSetting id="dateFormat" settingName="standard-date-format" />
<bright:applicationSetting id="dateFormatYearAndMonth" settingName="year-and-month-date-format" />
<bright:applicationSetting id="dateFormatYearOnly" settingName="year-only-date-format" />
<bright:applicationSetting id="numPromoted" settingName="num-promoted-images-homepage"/>
<bright:applicationSetting id="iFeaturedImageWidth" settingName="featured-image-width"/>
<bright:applicationSetting id="usePriceBands" settingName="price-bands" />
<bright:applicationSetting id="hideEmptyAtts" settingName="hide-empty-attributes" />
<bright:applicationSetting id="canRestrictAssetPreview" settingName="can-restrict-assets"/>
<bright:applicationSetting id="enableAudit" settingName="enable-audit-logging"/>
<bright:applicationSetting id="showSensitivityFields" settingName="show-sensitivity-fields"/>
<bright:applicationSetting id="firstAttributePosition" settingName="first-attribute-position"/>
<bright:applicationSetting id="descriptiveCategoryRoot" settingName="browsing-root-category-id1"/>
<bright:applicationSetting id="dlFromFileSystemEnabled" settingName="download-from-filesystem"/>
<bright:applicationSetting id="enableAgreements" settingName="agreements-enabled"/>
<bright:applicationSetting id="rating" settingName="ratings" />
<bright:applicationSetting id="ratingsAreVotes" settingName="ratings-are-votes"/>
<bright:applicationSetting id="useBrands" settingName="multiple-brands"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>


<%-- rating information --%>
<logic:present name="userprofile" property="user">
	<bean:define id="userId" name="userprofile" property="user.id"/>
	<bean:define id="userGivenFeedback" name="assetForm" property="<%= \"asset.userGivenFeedback(\" + userId + \")\" %>"/>
	<bean:define id="visibleEntries" name="assetForm" property="<%= \"visibleRatingCount(\" + userId + \")\" %>"/>
</logic:present>
<logic:notPresent name="userGivenFeedback">
	<bean:define id="userGivenFeedback" value="true"/>
</logic:notPresent>
<logic:notPresent name="visibleEntries">
	<bean:define id="visibleEntries" value="1"/>
</logic:notPresent>
<c:set var="userGivenFeedback" scope="request" value="${userGivenFeedback}"/>
<c:set var="visibleEntries" scope="request" value="${visibleEntries}"/>

<logic:notPresent name="asset">
	<bean:define id="asset" name="assetForm" property="asset"/>
</logic:notPresent>
<c:set var="section" value="${section}" scope="request"/>
<c:set var="hideEmptyAtts" value="${hideEmptyAtts && section != 'contact-sheet'}" />
<%-- Go through all the attributes: --%>
<c:set var="reqLastAttId" scope="request" value="0"/>
<c:set var="visibleAttIndex" value="0"/>
<c:set var="outputStart" value="false"/>
<logic:iterate name="asset" property="attributeValues" id="attributeValue" indexId="attributeIndex">
	<c:if test="${!attributeValue.attribute.hidden}">
		<c:if test="${firstAttributePosition == 'default' || (attributeValue.attribute.id != asset.firstAttributeId)}">
			<c:if test="${attributeValue.attribute.isVisible || attributeValue.attribute.fieldName == 'rating'}">
				<%-- Build or output a group header, or just the start of the table if the first attribute is not a group header --%>
				<c:choose>
					<%-- If this attribute is a group header, store the output for the header in a page variable called groupHeaderContent --%>
					<c:when test="${attributeValue.attribute.typeId == 10}">
						<c:set var="groupHeaderContent">
							<c:if test="${visibleAttIndex>0}">
								</table>
								</div>
							</c:if>
							<div class="attributeGroupHeading" >
								<h3 <c:if test="${!attributeValue.attribute.highlight}">class="collapsed"</c:if>><bean:write name="attributeValue" property="attribute.label" filter="false"/><span class="ellipsis">...</span></h3>
							</div>
							<div class="attributeGroupPanel <c:if test="${!attributeValue.attribute.highlight}">initially-collapsed</c:if>" id="attributeGroup<c:out value="${attributeValue.attribute.id}"/>">
							<table class="form stripey" cellspacing="0" cellpadding="0">
						</c:set>
					</c:when>
					<%-- If the first attribute is not a group header, output the start of the div & table (that the attribute(s) will be placed in) with no header --%>
					<c:when test="${attributeValue.attribute.typeId != 10 && visibleAttIndex==0}">
						<c:set var="outputStart" value="true"/>
						<div class="attributeGroupPanel">
						<table class="form stripey" cellspacing="0" cellpadding="0">
					</c:when>
					<%-- If the attribute is not a group header and is not empty, output the contents of the variable groupHeaderContent that we build in a previous iteration, then reset it --%>
					<c:when test="${not empty groupHeaderContent && (not empty attributeValue.attribute.fieldName || not empty attributeValue.value || not empty attributeValue.keywordCategories) && attributeValue.attribute.typeId != 10}">
						<bean:write name="groupHeaderContent" filter="false"/>
						<c:set var="groupHeaderContent" value=""/>
					</c:when>
				</c:choose>
				<logic:notEmpty name="attributeValue" property="attribute.fieldName">
					<%-- Static attributes --%>
					<c:choose>		
						<%-- Id field --%>
						
						<c:when test="${attributeValue.attribute.fieldName == 'assetId'}">
							<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
								<th>
									<bean:write name="attributeValue" property="attribute.label" filter="false"/>: 
								</th>
								<td class="padded">
									<bean:write name="asset" property="code"/>
								</td>
							</tr>
						</c:when>
						<%-- Version field --%>
						<c:when test="${attributeValue.attribute.fieldName == 'version' && (asset.versionNumber>1 || asset.currentVersionId>0)}">
							<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
								<th>
									<bean:write name="attributeValue" property="attribute.label" filter="false"/>: 
								</th>
								<td class="padded">
									<bean:write name="asset" property="versionNumber"/>
									&nbsp;&nbsp;
									<c:if test="${ asset.currentVersionId>0 && canViewLatestVersion}">
										<a href="viewAsset?id=<bean:write name="asset" property="currentVersionId"/>"><bright:cmsWrite identifier="link-current-version" filter="false"/></a>
									</c:if>
									<c:if test="${ asset.versionNumber>1}">
										<c:if test="${ asset.currentVersionId>0}">
											| <a href="viewAssetVersions?id=<bean:write name="asset" property="currentVersionId"/>"><bright:cmsWrite identifier="link-all-versions" filter="false"/></a>
										</c:if>
										<c:if test="${ asset.currentVersionId<=0}">
											<a href="viewAssetVersions?id=<bean:write name="asset" property="id"/>"><bright:cmsWrite identifier="link-all-versions" filter="false"/></a>
											<c:if test="${userprofile.isAdmin}">
												| <a href="viewDiscardCurrentAssetVersion?id=<bean:write name="asset" property="id"/>">Discard this version</a>
											</c:if>
										</c:if>
									</c:if>
								</td>
							</tr>
						</c:when>
						<%-- Size field --%>
						<c:when test="${attributeValue.attribute.fieldName == 'size' && asset.hasFile}">
							<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
								<th>
									<bean:write name="attributeValue" property="attribute.label" filter="false"/>: 
								</th>
								<td class="padded">
									<c:if test="${asset.typeId==2}">							
										<c:set var="height" value="${asset.height}" scope="request"/>
										<c:set var="width" value="${asset.width}" scope="request"/>
										<jsp:include page="../inc/image_size_calculations.jsp"/>
									</c:if>
									<c:if test="${asset.typeId==2 || asset.typeId==3}">
										<bean:write name="asset" property="width"/> x
										<bean:write name="asset" property="height"/> <bright:cmsWrite identifier="pixels" filter="false"/>;
									</c:if>
									<c:if test="${asset.typeId==2 && asset.numPages > 1}">
										<bean:write name="asset" property="numPages" filter="false"/> <bright:cmsWrite identifier="snippet-layers" filter="false"/>;
									</c:if>
									<c:if test="${(asset.typeId==3 || asset.typeId==4) && asset.duration>0 }">
										<bean:define id="duration" name="asset" property="duration" type="java.lang.Long"/>
										<%@include file="../inc/write_duration.jsp" %>;
									</c:if>
									<logic:greaterEqual name="fileSize" value="1">
										<bean:write name="fileSize" format="0.00"/><bright:cmsWrite identifier="snippet-mb" filter="false"/>
									</logic:greaterEqual>
									<logic:lessThan name="fileSize" value="1">
										<c:set var="fileSize" value="${fileSize*1024}"/>
										<bean:write name="fileSize" format="0.00"/><bright:cmsWrite identifier="snippet-kb" filter="false"/>
									</logic:lessThan>
									
								</td>
							</tr>
						</c:when>
						<%-- Original file name --%>
						<c:when test="${attributeValue.attribute.fieldName == 'originalFilename' && asset.hasFile}">
						<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
							<th>
								<bean:write name="attributeValue" property="attribute.label" filter="false"/>:  
							</th>
							<td class="padded">
								<bean:write name="asset" property="originalFilename"/>
								<c:if test="${ dlFromFileSystemEnabled && userprofile.userCanSeeSourcePath && !comparingAssets && not empty assetForm.fileInStorageDevice}">
									| <%@include file="../customisation/inc_view_full_asset_path.jsp"%>
								</c:if>
							</td>
						</tr>
						</c:when>

						<%-- Average Rating --%>
						<c:when test="${rating && (assetForm.canBeRated || feedbackCount > 0) && attributeValue.attribute.fieldName == 'rating' && (attributeValue.attribute.isVisible || assetForm.userCanReviewAsset)}">
						<bright:applicationSetting id="multipleComments" settingName="multiple-comments-per-asset"/>
						
						<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
							<th style="vertical-align:middle">
								<bean:write name="attributeValue" property="attribute.label" filter="false"/>:
							</th>
							<td style="vertical-align:middle;">
								<c:if test="${attributeValue.attribute.isVisible}">
									<c:choose>
										<c:when test="${ratingsAreVotes}">
											<c:out value="${asset.assetFeedbackCount}"/>
											<c:choose>
												<c:when test="${asset.assetFeedbackCount == 1}">
													<bright:cmsWrite identifier="snippet-vote" filter="false"/>
												</c:when>
												<c:otherwise>
													<bright:cmsWrite identifier="snippet-votes" filter="false"/>
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>
											<c:set var="ratedItem" value="${asset}"/>
											<%@include file="../inc/inc_average_rating_stars.jsp"%>
										</c:otherwise>
									</c:choose>
								</c:if>
								<logic:present name='userprofile' property='user'>
									<bean:define id="userId" name="userprofile" property="user.id"/>
									<bean:define id="userRatingCount" name="assetForm" property="<%= \"userRatingCount(\" + userId + \")\" %>"/>
									<bean:define id="previousLink" value="false"/>
									<c:if test="${(ratingsAreVotes && userRatingCount > 0) || (!ratingsAreVotes && visibleEntries > 0)}">
										<c:if test="${attributeValue.attribute.isVisible}">&nbsp;|&nbsp;</c:if>
										<%@include file="inc_view_review_link.jsp"%>
										<c:set var="previousLink" value="true"/>
									</c:if>
									<c:if test="${(userprofile.isAdmin || assetForm.userCanReviewAsset) && (multipleComments || !userGivenFeedback)}">
										<c:if test="${attributeValue.attribute.isVisible || previousLink}">&nbsp;|&nbsp;</c:if>
										<%@include file="inc_submit_review_link.jsp"%>
									</c:if>
								</logic:present>
							</td>
						</tr>
						</c:when>
						
						<%-- Added by user --%>
						<c:when test="${attributeValue.attribute.fieldName == 'addedBy'}">
						<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
							<th>
								<bean:write name="attributeValue" property="attribute.label" filter="false"/>: 
							</th>
							<td class="padded">
								<logic:notEmpty name="asset" property="addedByUser">
									<bean:write name="asset" property="addedByUser.fullName" filter="false"/>&nbsp;
									<logic:notEmpty name="asset" property="addedByUser.emailAddress">
										(<a href="mailto:<bean:write name="asset" property="addedByUser.emailAddress"/>"/>
											<bean:write name="asset" property="addedByUser.emailAddress" filter="false"/>
										</a>)
									</logic:notEmpty>
								</logic:notEmpty>
							</td>
						</tr>
						</c:when>
						<%-- Modified by user --%>
						<c:when test="${attributeValue.attribute.fieldName == 'lastModifiedBy' && (!hideEmptyAtts || !empty asset.lastModifiedByUser.fullName || !empty asset.lastModifiedByUser.emailAddress)}">
						<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
							<th>
								<bean:write name="attributeValue" property="attribute.label" filter="false"/>: 
							</th>
							<td class="padded">
								<logic:notEmpty name="asset" property="lastModifiedByUser">
									<bean:write name="asset" property="lastModifiedByUser.fullName" filter="false"/>&nbsp;
									<logic:notEmpty name="asset" property="lastModifiedByUser.emailAddress">
										(<a href="mailto:<bean:write name="asset" property="lastModifiedByUser.emailAddress"/>"/>
											<bean:write name="asset" property="lastModifiedByUser.emailAddress" filter="false"/>
										</a>)
									</logic:notEmpty>
								</logic:notEmpty>
							</td>
						</tr>
						</c:when>
						<%-- Date Added --%>
						<c:when test="${attributeValue.attribute.fieldName == 'dateAdded'}">
						<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
							<th>
								<bean:write name="attributeValue" property="attribute.label" filter="false"/>:
							</th>
							<td class="padded">
								<c:choose>
									<c:when test="${attributeValue.attribute.dateFormatTypeId==1}">
										<fmt:formatDate value="${asset.dateAdded}" pattern="${dateFormatYearAndMonth}" />
									</c:when>
									<c:when test="${attributeValue.attribute.dateFormatTypeId==2}">
										<fmt:formatDate value="${asset.dateAdded}" pattern="${dateFormatYearOnly}" />
									</c:when>
									<c:otherwise>
										<fmt:formatDate value="${asset.dateAdded}" pattern="${dateFormat}" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						</c:when>
						<%-- Date Last Modified --%>
						<c:when test="${attributeValue.attribute.fieldName == 'dateLastModified' && (!hideEmptyAtts || !empty asset.dateLastModified)}">
						<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
							<th>
								<bean:write name="attributeValue" property="attribute.label" filter="false"/>:
							</th>
							<td class="padded">
								<c:choose>
									<c:when test="${attributeValue.attribute.dateFormatTypeId==1}">
										<fmt:formatDate value="${asset.dateLastModified}" pattern="${dateFormatYearAndMonth}" />
									</c:when>
									<c:when test="${attributeValue.attribute.dateFormatTypeId==2}">
										<fmt:formatDate value="${asset.dateLastModified}" pattern="${dateFormatYearOnly}" />
									</c:when>
									<c:otherwise>
										<fmt:formatDate value="${asset.dateLastModified}" pattern="${dateFormat}" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						</c:when>
						<%-- Date Last Downloaded --%>
						<c:when test="${attributeValue.attribute.fieldName == 'dateLastDownloaded' && (!hideEmptyAtts || (not empty asset.dateLastDownloaded && asset.dateLastDownloaded.time>1))}">
						<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
							<th>
								<bean:write name="attributeValue" property="attribute.label" filter="false"/>:
							</th>
							<td class="padded">
								<c:choose>
									<c:when test="${attributeValue.attribute.dateFormatTypeId==1}">
										<fmt:formatDate value="${asset.dateLastDownloaded}" pattern="${dateFormatYearAndMonth}" />
									</c:when>
									<c:when test="${attributeValue.attribute.dateFormatTypeId==2}">
										<fmt:formatDate value="${asset.dateLastDownloaded}" pattern="${dateFormatYearOnly}" />
									</c:when>
									<c:otherwise>
										<fmt:formatDate value="${asset.dateLastDownloaded}" pattern="${dateFormat}" />
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						</c:when>
						<%-- Price - not HTML encoded. Only show if ecommerce is turn on: --%>
						<c:when test="${ecommerce && attributeValue.attribute.fieldName == 'price'}">
						<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
							<th>
								<bean:write name="attributeValue" property="attribute.label" filter="false"/>:
							</th>
							<c:choose>
								<c:when test="${usePriceBands}">
									<td class="padded">
									<c:choose>
										<c:when test='${asset.price.amount ge 0}'>
											Use price bands
										</c:when>
										<c:otherwise>
											Free
										</c:otherwise>
									</c:choose>
									</td>
								</c:when>
								<c:otherwise>
									<td class="padded">
										<bean:write name="asset" property="price.displayAmount" filter="false" /><c:if test="${userprofile.maxDiscount > 0}"> (A discount of <bean:write name='userprofile' property='maxDiscount'/>% will be applied to this when ordered)</c:if>
									</td>
								</c:otherwise>
							</c:choose>
						</tr>
						</c:when>
						<%-- Orientation: --%>
						<c:when test="${attributeValue.attribute.fieldName == 'orientation' && asset.surrogateAssetId<=0 && asset.orientation>0 }">
						<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
							<th>
								<bean:write name="attributeValue" property="attribute.label" filter="false"/>:
							</th>
							<td class="padded">
								<logic:equal name="asset" property="orientation" value="1">
									<bright:cmsWrite identifier="snippet-landscape" filter="false" />
								</logic:equal>
								<logic:equal name="asset" property="orientation" value="2">
									<bright:cmsWrite identifier="snippet-portrait" filter="false" />
								</logic:equal>
								<logic:equal name="asset" property="orientation" value="3">
									<bright:cmsWrite identifier="snippet-square" filter="false" />
								</logic:equal>
							</td>
						</tr>
						</c:when>

						<%-- Embedded metadata: --%>
						<c:when test="${attributeValue.attribute.fieldName == 'embeddedData' && asset.surrogateAssetId<=0}">
						<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
							<th>
								<bean:write name="attributeValue" property="attribute.label" filter="false"/>:
							</th>
							<td class="padded">
								<a href="viewEmbeddedMetadata?id=<bean:write name='asset' property='id'/>"><bright:cmsWrite identifier="link-view-embedded" filter="false" /></a>
							</td>
						</tr>
						</c:when>
						<%-- Usage: --%>
						<c:when test="${attributeValue.attribute.fieldName == 'usage' && asset.hasFile}">
						<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
							<th>
								<bean:write name="attributeValue" property="attribute.label" filter="false"/>:
							</th>
							<td class="padded">
								<a href="../action/viewAssetUsage?id=<bean:write name='asset' property='id'/>" target="_blank"  onclick="popupAssetUse(<bean:write name='asset' property='id'/>); return false;" title="<bright:cmsWrite identifier="tooltip-view-asset-usage-popup" filter="false"/>"><bright:cmsWrite identifier="link-view-asset-usage" filter="false" /></a>
							</td>
						</tr>
						</c:when>
						<%-- Audit logging --%>
						<c:when test="${attributeValue.attribute.fieldName == 'audit'}">
							<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
								<th>
									<bean:write name="attributeValue" property="attribute.label" filter="false"/>:
								</th>
								
								<td class="padded">
									<c:if test="${enableAudit}">
										<c:choose>
											<c:when test="${(asset.versionNumber>1 || asset.currentVersionId>0)}">
												<c:choose>
													<c:when test="${(asset.currentVersionId<=0)}">
														<a href="../action/viewAssetAudit?id=<bean:write name='asset' property='id'/>&amp;versionNumber=0" target="_blank"  onclick="popupAssetAudit(<bean:write name='asset' property='id'/>,0); return false;" title="<bright:cmsWrite identifier="tooltip-view-asset-audit-popup" filter="false"/>"><bright:cmsWrite identifier="link-view-asset-audit" filter="false"/></a>
													</c:when>
													<c:otherwise>	
														<a href="../action/viewAssetAudit?id=<bean:write name="asset" property="currentVersionId"/>&amp;versionNumber=<bean:write name="asset" property="versionNumber"/>" target="_blank"  onclick="popupAssetAudit(<bean:write name="asset" property="currentVersionId"/>,<bean:write name="asset" property="versionNumber"/>); return false;" title="<bright:cmsWrite identifier="tooltip-view-asset-audit-popup" filter="false"/>"><bright:cmsWrite identifier="link-view-asset-audit" filter="false"/></a>
													</c:otherwise>
												</c:choose>
											</c:when>	
											<c:otherwise>
												<a href="../action/viewAssetAudit?id=<bean:write name='asset' property='id'/>&amp;versionNumber=0" target="_blank"  onclick="popupAssetAudit(<bean:write name='asset' property='id'/>,0); return false;" title="<bright:cmsWrite identifier="tooltip-view-asset-audit-popup" filter="false"/>"><bright:cmsWrite identifier="link-view-asset-audit" filter="false"/></a>
											</c:otherwise>	
										</c:choose>
										|
									</c:if>
										
									<%--  Workflow audit link visibility is the same as asset audit for now (but does not have to be) --%>
									<a href="../action/viewWorkflowAudit?id=<bean:write name='asset' property='id'/>" target="_blank"  onclick="popupAssetWorkflowAudit(<bean:write name='asset' property='id'/>,0); return false;" title="<bright:cmsWrite identifier="tooltip-workflow-audit-popup" filter="false"/>"><bright:cmsWrite identifier="link-view-asset-workflow-audit" filter="false"/></a>	
								</td>
							</tr>
						</c:when>
						
						<%-- Agreements --%>
						<c:when test="${attributeValue.attribute.fieldName == 'agreements'}">
							<c:if test="${enableAgreements && asset.agreementTypeId>0}">
									<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
										<th>
											<bright:cmsWrite identifier="label-agreement-type" filter="false"/>
										</th>		
										<td class="padded">		
											<c:if test='${asset.agreementTypeId==1}'>
												<bright:cmsWrite identifier="snippet-unrestricted" filter="false"/>
											</c:if>	
											<c:if test='${asset.agreementTypeId==2}'>
												<bright:cmsWrite identifier="snippet-agreement-applies" filter="false"/>
											</c:if>	
											<c:if test='${asset.agreementTypeId==3}'>
												<bright:cmsWrite identifier="snippet-restricted" filter="false"/>
											</c:if>	
										</td>	
									</tr>
							</c:if>		
							<c:if test="${enableAgreements}">
								<c:if test="${asset.agreementTypeId == 2 && asset.agreement.id>0}">
									<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
										<th>
											<bright:cmsWrite identifier="label-agreement" filter="false"/>
										</th>
										<td class="padded">
											<c:choose>
												<c:when test='${asset.agreement.id>0}'>
													<bean:write name='asset' property='agreement.title'/>
													<c:choose>
														<c:when test="${agreementsForm.agreement.bodyHtml}">	
															<bean:write name="asset" property="agreement.body" filter="false"/>
														</c:when>
														<c:otherwise>
															<bright:write name="asset" property="agreement.body" filter="false" formatCR="true"/>
														</c:otherwise>
													</c:choose>
												</c:when>
												<c:otherwise>
													None
												</c:otherwise>
											</c:choose>
											<c:if test="${userprofile.isAdmin}">
												<logic:notEmpty name="asset" property="previousAgreements">
													<br/><br/>
													<bright:cmsWrite identifier="label-previous-agreements" filter="false"/><br/>
													<logic:iterate name="asset" property="previousAgreements" id="agreement">
														<a href="../action/viewAgreementPopup?id=<c:out value="${agreement.id}" />" target="_blank" onclick="popupViewAgreement(<c:out value="${agreement.id}" />); return false;" /><c:out value="${agreement.title}" /></a> (<bean:write name="agreement" property="dateActivated" format="dd/MM/yyyy HH:mm"/>)<br />
													</logic:iterate> 
												</logic:notEmpty>
											</c:if>
										</td>
									</tr>
								</c:if>
							</c:if>
						</c:when>
						
						<%-- Categories --%>
						<c:when test="${attributeValue.attribute.fieldName == 'categories' && ((!hideEmptyAtts && !empty asset.descriptiveCategories) || !assetForm.hideCategoriesBecauseEmpty)}">
							<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
								<th>
									<bean:write name="attributeValue" property="attribute.label" filter="false"/>:
								</th>
								<td class="padded">
									<logic:empty name="asset" property="descriptiveCategories">
										<bright:cmsWrite identifier="snippet-none" filter="false"/>&nbsp;
									</logic:empty>
									<logic:notEmpty name="asset" property="descriptiveCategories">
										<bean:size name="asset" property="descriptiveCategories" id="noOfCategories"/>
										<logic:iterate name="asset" property="descriptiveCategories" id="category" indexId="index">
											<c:set var="foundRoot" value="false"/>
											<c:set var="matchType" value="categories" />
											<logic:notEmpty name="category" property="ancestors">
												<logic:iterate name="category" property="ancestors" id="ancestor">
													
													<c:set var="showAncestor" value="false"/>
													<c:choose>
														<c:when test="${!foundRoot}">
															<c:choose>
																<c:when test="${descriptiveCategoryRoot == ''}">
																	<c:set var="foundRoot" value="true"/>
																	<c:set var="showAncestor" value="true"/>
																</c:when>
																<c:otherwise>
																	<c:if test="${descriptiveCategoryRoot > 0 && descriptiveCategoryRoot == ancestor.id}">
																		<c:set var="foundRoot" value="true"/>
																	</c:if>
																</c:otherwise>
															</c:choose>
														</c:when>
														<c:otherwise>
															<c:set var="showAncestor" value="true"/>
														</c:otherwise>
													</c:choose>

													<c:if test="${showAncestor}">
													<c:set var="linkCategory" value="${ancestor}" />
													<%@include file="inc_attribute_browse_link.jsp"%>
													<bean:write name="ancestor" property="name" filter="false"/></a>/
													</c:if>
												</logic:iterate>
											</logic:notEmpty>
											<c:set var="linkCategory" value="${category}" />
											<%@include file="inc_attribute_browse_link.jsp"%>
											<bean:write name="category" property="name" filter="false"/></a><c:if test="${(index+1) < noOfCategories}">, </c:if>
										</logic:iterate>&nbsp;
									</logic:notEmpty>
								</td>
							</tr>
						</c:when>
						<%-- Access Levels --%>
						<c:when test="${attributeValue.attribute.fieldName == 'accessLevels'}">
							<c:if test="${!hideAccessLevels}">
								<c:choose>
									<c:when test="${userprofile.isAdmin}">
										<bean:define id="catsIterator" name="asset" property="permissionCategories"/>
									</c:when>
									<c:otherwise>
										<bean:define id="catsIterator" name="asset" property="approvedPermissionCategories"/>
									</c:otherwise>
								</c:choose>
							</c:if>
							<c:if test="${!hideEmptyAtts || !empty catsIterator}">
								<tr <c:if test='${attributeValue.attribute.highlight}'>class="highlight"</c:if>>
									<th>
										<bright:cmsWrite identifier="access-level-root" filter="false" />:
									</th>
									<td class="padded">
										<c:set var="matchType" value="accesslevels" />
										<logic:empty name="catsIterator">
											<bright:cmsWrite identifier="snippet-none" filter="false"/>&nbsp;
										</logic:empty>
										<logic:notEmpty name="catsIterator">
											<bean:size name="catsIterator" id="noOfCategories"/>
											<logic:iterate name="catsIterator" id="category" indexId="index">
												<logic:notEmpty name="category" property="ancestors">
													<logic:iterate name="category" property="ancestors" id="ancestor">
														<c:set var="linkCategory" value="${ancestor}" />
														<%@include file="inc_attribute_browse_link.jsp"%>
															<bean:write name="ancestor" property="name" filter="false"/>
														</a>/
													</logic:iterate>
												</logic:notEmpty>
												<c:set var="linkCategory" value="${category}" />
												<%@include file="inc_attribute_browse_link.jsp"%>
												<bean:write name="category" property="name" filter="false"/></a>
												<%
													Category c = (Category)pageContext.findAttribute("category");
													Asset a = (Asset)pageContext.findAttribute("asset");
													if (!a.isApproved(c.getId()))
													{
												%>
												(<bright:cmsWrite identifier="snippet-not-approved" filter="false" />)
												<%
													}
												%><c:if test="${(index+1) < noOfCategories}">, </c:if>
											</logic:iterate>&nbsp;
										</logic:notEmpty>
									</td>
								</tr>
							</c:if>
						</c:when>
						<c:when test="${attributeValue.attribute.fieldName == 'sensitive' && attributeValue.attribute.isVisible}">
							<c:set var="sensitiveFieldVisible" value="true"/>
						</c:when>
					</c:choose>
				</logic:notEmpty>
				<%-- Dynamic attributes --%>					
				<c:if test="${empty attributeValue.attribute.fieldName}">
					<%--  Pass attributeValue, reqHideLabels and reqLastAttId into request scope --%>			 
					<c:set var="attributeValue" scope="request" value="${attributeValue}"/> 
					<c:set var="reqHideLabels" scope="request" value="${hideLabels}"/>
					<c:choose>
						<c:when test="${not empty asset}">
							<c:set var="assetIdForAttributes" scope="request" value="${asset.id}"/>
						</c:when>
						<c:otherwise>
							<c:set var="assetIdForAttributes" scope="request" value="${asset.id}"/>
						</c:otherwise>
					</c:choose>
					<jsp:include flush="true" page="../public/inc_view_attribute_field.jsp"/> 
					
					<c:set var="reqLastAttId" scope="request" value="${attributeValue.attribute.id}"/>
				</c:if>		
			</c:if>
			<c:if test="${attributeValue.attribute.isVisible}"><c:set var="visibleAttIndex" value="${visibleAttIndex+1}"/></c:if>
		</c:if>
	</c:if>
</logic:iterate>

<c:if test="${userprofile.isAdmin}">

	<c:if test="${not empty groupHeaderContent}">
		<bean:write name="groupHeaderContent" filter="false"/>
	</c:if>
	
		<%-- Is Promoted field --%>						
		<c:if test="${ !hidePromoted && numPromoted gt 0 }">
			<tr>
				<th>
					<bright:cmsWrite identifier="label-is-promoted" filter="false"/> 
				</th>
				<td class="padded">
					<logic:equal name="asset" property="isPromoted" value="true">
						<bright:cmsWrite identifier="snippet-yes" filter="false"/>
					</logic:equal>
					<logic:equal name="asset" property="isPromoted" value="false">
						<bright:cmsWrite identifier="snippet-no" filter="false"/>
					</logic:equal>
				</td>
			</tr>		
		</c:if>
		
		<%-- Is Featured field --%>						
		<c:if test="${ !hideFeatured && asset.typeId==2 && asset.surrogateAssetId<=0 && iFeaturedImageWidth gt 0}">
			<tr>
				<th>
					<bright:cmsWrite identifier="label-is-featured" filter="false"/> 
				</th>
				<td class="padded">
					<c:choose>
						<c:when test="${useBrands}">
	
							<%-- Show the list of names of brands selected --%>
							<logic:iterate name="assetForm" property="brandList" id="brand">
							
								<logic:iterate name="assetForm" property="brandSelectedList" id="brandSelectedId">
									<c:if test="${brand.id == brandSelectedId}">
										<c:if test="${bHadOne}">,</c:if>
										<bean:write name="brand" property="name" filter="false"/>
										<c:set var="bHadOne" value="true" />
									</c:if>
								</logic:iterate>
								
							</logic:iterate>
							<c:if test="${empty assetForm.brandSelectedList}">
								--
							</c:if>
													
						</c:when>
						<c:otherwise>
			
							<logic:equal name="asset" property="isFeatured" value="true">
								<bright:cmsWrite identifier="snippet-yes" filter="false"/>
							</logic:equal>
							<logic:equal name="asset" property="isFeatured" value="false">
								<bright:cmsWrite identifier="snippet-no" filter="false"/>
							</logic:equal>
															
						</c:otherwise>
					</c:choose>
				</td>
			</tr>		
		</c:if>
												
		<%-- Is Preview Restricted Field --%>						
		<c:if test="${!comparingAssets && canRestrictAssetPreview && !enableAgreements}">
			<tr>
				<th>
					<bright:cmsWrite identifier="label-restricted" filter="false"/> 
				</th>
				<td class="padded">
					<logic:equal name="asset" property="isRestricted" value="true">
						<bright:cmsWrite identifier="snippet-yes" filter="false"/> 
					</logic:equal>
					<logic:equal name="asset" property="isRestricted" value="false">
						<bright:cmsWrite identifier="snippet-no" filter="false"/> 
					</logic:equal>
				</td>
			</tr>		
		</c:if>
		
		<%-- Approval status --%>							
		<c:if test="${!hideApprovalStatus}">
			<tr>
				<th>
					<bright:cmsWrite identifier="label-approval-status" filter="false"/>
				</th>
				<td class="padded">
					<c:choose>
						<c:when test="${asset.isPartiallyApproved || asset.isUnnapproved}">
							<bright:cmsWrite identifier="snippet-awaiting-approval" filter="false"/><logic:notEmpty name="assetForm" property="workflows"><bean:size name="assetForm" property="workflows" id="noOfWorkflows"/></logic:notEmpty><logic:empty name="assetForm" property="workflows"><bean:define id="noOfWorkflows" value="0"/></logic:empty><c:if test="${noOfWorkflows > 1}"><logic:notEmpty name="asset" property="unapprovedPermissionCategories"><bean:size name="asset" property="unapprovedPermissionCategories" id="noOfCategories"/> <bright:cmsWrite identifier="snippet-for" filter="false"/>  <logic:iterate indexId="index" name="asset" property="unapprovedPermissionCategories" id="cat"><bean:write name='cat' property='name'/><c:if test="${(index+1) < noOfCategories}">, </c:if></logic:iterate></logic:notEmpty></c:if>
						</c:when>
						<c:when test="${asset.isFullyApproved}">
						<bright:cmsWrite identifier="snippet-approved" filter="false"/>
						</c:when>
					</c:choose>
				</td>
			</tr>	
		</c:if>	

		<%-- Is Brand Template Field --%>
		<bright:applicationSetting id="brandTemplatesEnabled" settingName="brand-templates-enabled"/>
		<c:if test="${brandTemplatesEnabled && !hideBrandTemplates}">
			<tr>
				<th>
					<bright:cmsWrite identifier="label-is-brand-template" filter="false"/>
				</th>
				<td class="padded">
					<logic:equal name="asset" property="isBrandTemplate" value="true">
						<bright:cmsWrite identifier="snippet-yes" filter="false"/>
					</logic:equal>
					<logic:equal name="asset" property="isBrandTemplate" value="false">
						<bright:cmsWrite identifier="snippet-no" filter="false"/>
					</logic:equal>
				</td>
			</tr>
		</c:if>

		
	</c:if>
	<%-- Is Sensitive Field (note, only admins see this field since it does not affect the image) --%>
	<c:if test="${(userprofile.isAdmin || (sensitiveFieldVisible && asset.isSensitive && empty asset.fileLocation)) && showSensitivityFields && !hideRestricted}">
		<tr>
			<th>
				<bright:cmsWrite identifier="label-sensitive" filter="false"/> 
			</th>
			<td class="padded">
				<logic:equal name="asset" property="isSensitive" value="true">
					<bright:cmsWrite identifier="snippet-yes" filter="false"/> 
				</logic:equal>
				<logic:equal name="asset" property="isSensitive" value="false">
					<bright:cmsWrite identifier="snippet-no" filter="false"/> 
				</logic:equal>
			</td>
		</tr>		
	</c:if>
	<c:set var="copyright" ><bright:cmsWrite identifier="contact-sheet-extra-label" /></c:set>
	<c:set var="copyrightValue" ><bright:cmsWrite identifier="contact-sheet-extra-value" /></c:set>
		<c:if test="${copyright!='' || copyrightValue!=''}">
			<tr>
				<th>
					<bright:cmsWrite identifier="contact-sheet-extra-label"/>
				</th>
				<td class="padded">
					<bright:cmsWrite identifier="contact-sheet-extra-value"/>
				</td>
			</tr>
		</c:if>
	
			
<c:if test="${visibleAttIndex>0 || outputStart}">
	</table>
	</div>
</c:if>
