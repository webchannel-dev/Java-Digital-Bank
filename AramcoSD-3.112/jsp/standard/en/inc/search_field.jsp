<%-- 
	Displays the form field for an attribute, according to the attribute type

	History:
	 d1		Martin Wilson		24-Oct-2005		Created.
	 d2		Matt Stevenson		01-Nov-2007		Added filter to keyword popup
	 d3		Matt Stevenson		23-Nov-2007		Added refine your search functionality
	 d4		Francis Devereux	27-Apr-2009		Added "Added By" field
--%>		

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%-- The attribute must be in a page variable called 'attribute'--%>

<bean:define id="currentFilter" name="searchForm" property="selectedFilter"/>
<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />
<bright:applicationSetting id="dateFormatYearMonthHelpString" settingName="date-format-year-month-help-string" />
<bright:applicationSetting id="dateFormatYearOnlyHelpString" settingName="date-format-year-only-help-string" />
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="ratingsAreVotes" settingName="ratings-are-votes"/>
<bright:applicationSetting id="autoCompleteEnabled" settingName="auto-complete-enabled"/>

	<c:choose>
		<c:when test="${attribute.static}">
			
			<%-- Explicitly test the static ones --%>
			<c:choose>		
				
				<%-- File field --%>
				<c:when test="${attribute.fieldName == 'originalFilename'}">
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<c:set var="refineFilename" value="${userprofile.searchCriteria.filename}"/>
						</c:when>
						<c:otherwise>
							<c:set var="refineFilename" value="${param.filename}"/>
						</c:otherwise>
					</c:choose>
					<tr>
						<th style="padding-top: 8px;">
							<label for="filename_field"><bean:write name="attribute" property="label"/>:</label> 
						</th>
						<td style="padding-top: 8px;">
							<input type="text" name="filename" size="55" class="text" id="filename_field" value="<c:out value='${refineFilename}' />" />
						</td>
					</tr>
				</c:when>
				
				<%-- Id field --%>
				<c:when test="${attribute.fieldName == 'assetId'}">		
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<c:set var="refineIds" value="${userprofile.searchCriteria.originalAssetIdsString}"/>
						</c:when>
						<c:otherwise>
							<c:set var="refineIds" value="${param.assetIds}"/>
						</c:otherwise>
					</c:choose>
					<tr>
						<th>
							<label for="ids"><bean:write name="attribute" property="label" filter="false"/>:</label> 
						</th>
						<td>
							<div style="position: relative">
							<input type="text" name="assetIds" size="6" class="text" id="ids" value="<c:out value='${refineIds}' />"/>
							</div>
		
						</td>
					</tr>
				</c:when>

				<%-- Average Rating field --%>
				<c:when test="${attribute.fieldName == 'rating'}">		
				<tr>
					<th>
						<label for="ids"><bean:write name="attribute" property="label" filter="false"/>:</label> 
					</th>
					<td>
						<c:choose>
							<c:when test="${ratingsAreVotes}">
								between <input type="text" class="small text" name="minimumVotes" value="<c:choose><c:when test='${searchForm.minimumVotes >= 0}'><c:out value='${searchForm.minimumVotes}'/></c:when><c:when test='${searchForm.refineSearch && userprofile.searchCriteria.minimumVotes >= 0}'><c:out value='${userprofile.searchCriteria.minimumVotes}'/></c:when></c:choose>"/> and <input type="text" class="small text" name="maximumVotes" value="<c:choose><c:when test='${searchForm.maximumVotes > 0}'><c:out value='${searchForm.maximumVotes}'/></c:when><c:when test='${searchForm.refineSearch && userprofile.searchCriteria.maximumVotes > 0}'><c:out value='${userprofile.searchCriteria.maximumVotes}'/></c:when></c:choose>"/>
							</c:when>
							<c:otherwise>
								<c:choose>
									<c:when test="${searchForm.refineSearch}">
										<c:set var="refineAverageRating" value="${userprofile.searchCriteria.averageRating}"/>
									</c:when>
									<c:otherwise>
										<c:set var="refineAverageRating" value="${searchForm.averageRating}"/>
									</c:otherwise>
								</c:choose>
								<bright:applicationSetting id="maxRating" settingName="max-rating"/>
								<html:select name="searchForm" property="averageRating" size="1">
									<option value="-1" <c:if test='${refineAverageRating == "-1"}'>selected</c:if>>[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</option>
									<c:forEach begin="0" end="${maxRating-1}" var="value">
										<c:choose>
											<c:when test="${value == 0}">
												<option value="0" <c:if test='${refineAverageRating == 0}'>selected</c:if>>&gt;=&nbsp;
											</c:when>
											<c:otherwise>
												<option value="<bean:write name='value'/>.01" <c:if test='${refineAverageRating == value+".01"}'>selected</c:if>>&gt;&nbsp;
											</c:otherwise>
										</c:choose>
										<bean:write name='value'/></option>
									</c:forEach>	
								 </html:select>
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
				</c:when>

				<%-- Date added --%>
				<c:when test="${attribute.fieldName == 'dateAdded'}">
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<fmt:formatDate var="refineDateAddedLower" value="${userprofile.searchCriteria.dateImageAddedLower}" pattern="dd/MM/yyyy" />
							<fmt:formatDate var="refineDateAddedUpper" value="${userprofile.searchCriteria.dateImageAddedUpper}" pattern="dd/MM/yyyy" />
						</c:when>
						<c:otherwise>
							<c:set var="refineDateAddedLower" value="${param.dateAddedLower}"/>
							<c:set var="refineDateAddedUpper" value="${param.dateAddedUpper}"/>
						</c:otherwise>
					</c:choose>
					<tr>
						<%-- Get the label for date added --%>
						<th>
							<bean:write name="attribute" property="label"/>: 
						</th>
						<td>
							<span class="inline"><bright:cmsWrite identifier="snippet-between" filter="false"/></span>
							<input type="text" size="17" maxlength="20" class="small date" id="addedLower" name="dateAddedLower" value="<c:out value='${refineDateAddedLower}'/>" />&nbsp;
							<span class="inline"><bright:cmsWrite identifier="snippet-and" filter="false"/></span>
							<input type="text" size="17" maxlength="20" class="small date" id="addedUpper" name="dateAddedUpper" value="<c:out value='${refineDateAddedUpper}'/>" />&nbsp;
							<span class="inline">(<c:out value="${dateFormatHelpString}" />)</span>
						</td>
					</tr>
				</c:when>

				<%-- Date last mod --%>
				<c:when test="${attribute.fieldName == 'dateLastModified'}">
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<fmt:formatDate var="refineDateModLower" value="${userprofile.searchCriteria.dateImageModLower}" pattern="dd/MM/yyyy" />
							<fmt:formatDate var="refineDateModUpper" value="${userprofile.searchCriteria.dateImageModUpper}" pattern="dd/MM/yyyy" />
						</c:when>
						<c:otherwise>
							<c:set var="refineDateModLower" value="${param.dateModLower}"/>
							<c:set var="refineDateModUpper" value="${param.dateModUpper}"/>
						</c:otherwise>
					</c:choose>
					<tr>
						<%-- Get the label for date added --%>
						<th>
							<bean:write name="attribute" property="label"/>: 
						</th>
						<td>
							<span class="inline"><bright:cmsWrite identifier="snippet-between" filter="false"/></span>
							<input type="text" size="17" maxlength="20" class="small date" id="modLower" name="dateModLower" value="<c:out value='${refineDateModLower}'/>" />&nbsp;
							<span class="inline"><bright:cmsWrite identifier="snippet-and" filter="false"/></span>
							<input type="text" size="17" maxlength="20" class="small date" id="modUpper" name="dateModUpper" value="<c:out value='${refineDateModUpper}'/>" />&nbsp;
							<span class="inline">(<c:out value="${dateFormatHelpString}" />)</span>
						</td>
					</tr>
				</c:when>

				<%-- Date last downloaded --%>
				<c:when test="${userprofile.isAdmin && attribute.fieldName == 'dateLastDownloaded'}">
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<fmt:formatDate var="refineDateDownloadedLower" value="${userprofile.searchCriteria.dateDownloadedLower}" pattern="dd/MM/yyyy" />
							<fmt:formatDate var="refineDateDownloadedUpper" value="${userprofile.searchCriteria.dateDownloadedUpper}" pattern="dd/MM/yyyy" />
						</c:when>
						<c:otherwise>
							<c:set var="refineDateDownloadedLower" value="${param.dateDownloadedLower}"/>
							<c:set var="refineDateDownloadedUpper" value="${param.dateDownloadedUpper}"/>
						</c:otherwise>
					</c:choose>
					<tr>
						<%-- Get the label for date downloaded --%>
						<th>
							<bean:write name="attribute" property="label"/>: 
						</th>
						<td>
							<span class="inline"><bright:cmsWrite identifier="snippet-between" filter="false"/></span>
							<input type="text" size="17" maxlength="20" class="date" id="startDate_downloaded" name="dateDownloadedLower" value="<c:out value='${refineDateDownloadedLower}'/>" />&nbsp;
							<span class="inline"><bright:cmsWrite identifier="snippet-and" filter="false"/></span>
							<input type="text" size="17" maxlength="20" class="date" id="endDate_downloaded" name="dateDownloadedUpper" value="<c:out value='${refineDateDownloadedUpper}'/>" />&nbsp;
							<span class="inline">(<c:out value="${dateFormatHelpString}" />)</span>
						</td>
					</tr>
				</c:when>

				<%-- Added By - text search against users name and username --%>
				<c:when test="${attribute.fieldName == 'addedBy'}">
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<c:set var="refineAddedBy" value="${userprofile.searchCriteria.addedBy}"/>
						</c:when>
						<c:otherwise>
							<c:set var="refineAddedBy" value="${param.addedBy}"/>
						</c:otherwise>
					</c:choose>
					<tr>
						<th>
							<label for="addedBy"><bean:write name="attribute" property="label"/>:</label>
						</th>
						<td>
							<input type="text" name="addedBy" id="addedBy" value="<c:out value='${refineAddedBy}'/>" />
						</td>
					</tr>
				</c:when>

				<%-- Orientation field --%>
				<c:when test="${attribute.fieldName == 'orientation'}">		
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<c:set var="refineOrientation" value="${userprofile.searchCriteria.orientation}"/>
						</c:when>
						<c:otherwise>
							<c:set var="refineOrientation" value="${param.orientation}"/>
						</c:otherwise>
					</c:choose>
					<tr>
						<th>
							<label for="orientation"><bean:write name="attribute" property="label" filter="false"/>:</label> 
						</th>
						<td>
							<html:select name="searchForm" property="orientation" styleId="orientation">
								<option value="0" <c:if test="${refineOrientation == 0}">selected</c:if>>[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</option>
								<option value="1" <c:if test="${refineOrientation == 1}">selected</c:if>><bright:cmsWrite identifier="snippet-landscape" filter="false"/></option>
								<option value="2" <c:if test="${refineOrientation == 2}">selected</c:if>><bright:cmsWrite identifier="snippet-portrait" filter="false"/></option>
								<option value="3" <c:if test="${refineOrientation == 3}">selected</c:if>><bright:cmsWrite identifier="snippet-square" filter="false"/></option>
							</html:select>
						</td>
					</tr>
				</c:when>

				<%-- Price. Only show if ecommerce is turned on: --%>
				<c:when test="${ecommerce && attribute.fieldName == 'price'}">
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<c:set var="refinePriceLower" value="${userprofile.searchCriteria.priceLower.formAmount}"/>
							<c:set var="refinePriceUpper" value="${userprofile.searchCriteria.priceUpper.formAmount}"/>
						</c:when>
						<c:otherwise>
							<c:set var="refinePriceLower" value="${param.priceLower.formAmount}"/>
							<c:set var="refinePriceUpper" value="${param.priceUpper.formAmount}"/>
						</c:otherwise>
					</c:choose>
					<tr>
						<%-- Get the label --%>
						<th>
							<label for="price"><bean:write name="attribute" property="label" filter="false"/> (<bean:write name="searchForm" property="priceLower.currencySymbol" filter="false" />):</label> 
						</th>
						<td>
							<label for="priceLower"><bright:cmsWrite identifier="snippet-between" filter="false"/></label>
							<input type="text" size="17" maxlength="10" class="small text" id="priceLower" name="priceLower.formAmount" value="<c:out value='${refinePriceLower}'/>" />
							<label for="priceUpper"><bright:cmsWrite identifier="snippet-and" filter="false"/></label>
							<input type="text" size="17" maxlength="10" class="small text" id="priceUpper" name="priceUpper.formAmount" value="<c:out value='${refinePriceUpper}'/>" />
						</td>
					</tr>
				</c:when>
				
				<%-- Size --%>
				<c:when test="${userprofile.isAdmin && attribute.fieldName == 'size'}">
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<c:set var="refineFilesizeLower" value="${userprofile.searchCriteria.filesizeLower}"/>
							<c:set var="refineFilesizeUpper" value="${userprofile.searchCriteria.filesizeUpper}"/>
						</c:when>
						<c:otherwise>
							<c:set var="refineFilesizeLower" value="${param.filesizeLower}"/>
							<c:set var="refineFilesizeUpper" value="${param.filesizeUpper}"/>
						</c:otherwise>
					</c:choose>
					<tr>
						<%-- Get the label for size --%>
						<th>
							<bean:write name="attribute" property="label"/>: 
						</th>
						<td>
							<bright:cmsWrite identifier="snippet-between" filter="false" case="sentence"/>
							<input type="text" size="17" maxlength="10" class="small text" id="filesizeLower" name="filesizeLower" value="<c:out value='${refineFilesizeLower}'/>" /> <bright:cmsWrite identifier="snippet-kb" filter="false"/> &nbsp;<bright:cmsWrite identifier="snippet-and" filter="false"/>
							<input type="text" size="17" maxlength="10" class="small text" id="filesizeUpper" name="filesizeUpper" value="<c:out value='${refineFilesizeUpper}'/>" /> <bright:cmsWrite identifier="snippet-kb" filter="false"/>
						</td>
					</tr> 
				</c:when>

				<%-- Agreement status --%>
				<c:when test="${agreementsEnabled && attribute.fieldName == 'agreements'}">
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<c:set var="refineAgreementType" value="${userprofile.searchCriteria.agreementType}"/>
							<c:set var="refineAgreementText" value="${userprofile.searchCriteria.agreementText}"/>
						</c:when>
						<c:otherwise>
							<c:set var="refineAgreementType" value="${searchForm.agreementType}"/>
							<c:set var="refineAgreementText" value="${param.agreementText}"/>
						</c:otherwise>
					</c:choose>
					<tr>
						<th><label for="agreement"><bright:cmsWrite identifier="label-agreement-type" filter="false" /></label></th>
						<td>
							<html:select name="searchForm" property="agreementType" styleId="agreementType">
								<option value="0" <c:if test="${refineAgreementType == 0}">selected</c:if>>[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</option>	
								<option value="1" <c:if test="${refineAgreementType == 1}">selected</c:if>><bright:cmsWrite identifier="snippet-unrestricted" filter="false"/></option>	
								<option value="2" <c:if test="${refineAgreementType == 2}">selected</c:if>><bright:cmsWrite identifier="snippet-agreement-applies" filter="false"/></option>
								<option value="3" <c:if test="${refineAgreementType == 3}">selected</c:if>><bright:cmsWrite identifier="snippet-restricted" filter="false"/></option>
							</html:select>
						</td>
					</tr>
					<%-- Agreement content --%>
					<c:if test="${canSearchAgreements=='true' || (canSearchAgreements=='admin' && (userprofile.isAdmin || userprofile.isOrgUnitAdmin))}">
						<tr>
							<th><label for="agreement"><bright:cmsWrite identifier="label-agreement-text" filter="false" /></label></th>
							<td><input type="text" name="agreementText" value="<c:out value='${refineAgreementText}' />" />
							</td>
						</tr>
					</c:if>
				</c:when>
				
				<c:when test="${attribute.fieldName == 'sensitive'}">		
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<c:set var="refineSensitive" value="${userprofile.searchCriteria.isSensitive}"/>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${empty param.isSensitive}"><c:remove var="refineSensitive" /></c:when>
								<c:otherwise><c:set var="refineSensitive" value="${param.isSensitive}"/></c:otherwise>
							</c:choose>							
						</c:otherwise>
					</c:choose>
					<tr>
						<th>
							<label for="sensitive"><bean:write name="attribute" property="label" filter="false"/></label> 
						</th>
						<td>
							<html:select name="searchForm" property="isSensitive" styleId="sensitive">
								<option value="" <c:if test="${refineSensitive == null}">selected</c:if>>[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</option>
								<option value="true" <c:if test="${refineSensitive == true}">selected</c:if>><bright:cmsWrite identifier="snippet-yes" filter="false"/></option>
								<option value="false" <c:if test="${refineSensitive == false}">selected</c:if>><bright:cmsWrite identifier="snippet-no" filter="false"/></option>
							</html:select>
						</td>
					</tr>
				</c:when>

				<c:otherwise>
					<%-- Static attribute not recognised! --%>
				</c:otherwise>
				
			</c:choose>
		</c:when>
		
		<c:otherwise>
			
			<%-- A dynamic field --%>

			<%-- see if we have a matching filter field --%>
			<bean:define id="attributeId" name="attribute" property="id"/>
			<bean:define id="filterValue" name="searchForm" property="<%= \"selectedFilter.attributeValueForAttribute(\" + attributeId + \")\" %>"/>

			<c:set var="attIdString"><c:out value="${attributeId}"/></c:set>				
			
			<%--  Get raw value from refine search, filter or request (for validation failure --%>
			<c:choose>
				<c:when test="${searchForm.refineSearch}">
					<logic:present name="userprofile" property="<%= \"searchCriteria.attributeValueForAttribute(\" + attributeId + \")\" %>">
						<bean:define id="valueObj" name="userprofile" property="<%= \"searchCriteria.attributeValueForAttribute(\" + attributeId + \")\" %>"/>
					</logic:present>
					<c:if test="${!empty valueObj}">
						<c:set var="valueToRefine" value="${valueObj.value}"/>
					</c:if>
				</c:when>
				<c:when test="${filterValue != null && filterValue.value != null}">
					<c:set var="valueToRefine" value="${filterValue.value}" />
				</c:when>
				<c:otherwise>					
					<%-- Steve: Have to use cset to generate the param name - get cast exception if we try to concat the string inside param[] --%>
					<c:set var="name" value="attribute_${attIdString}" />
					<c:set var="valueToRefine" value="${param[name]}"/>
				</c:otherwise>
			</c:choose>
			
			<%-- Dynamic label --%>
			<c:choose>
				<c:when test="${attribute.typeId == 10}">
					</tbody>
					<tbody class="attributeGroupHeading">
						<tr>
							<td colspan="3" style="padding-right:0px">
								<h3 <c:if test="${!attribute.highlight}">class="collapsed"</c:if> title="Show or hide these fields"><bean:write name="attribute" property="label" filter="false"/><span class="ellipsis">...</span></h3>
							
								
							</td>
						</tr>
					</tbody>
					<tbody class="attributeGroupPanel <c:if test="${!attribute.highlight}">initially-collapsed</c:if>">
				</c:when>
				<c:otherwise>
					<tr>
							<th>
								<label for="field<bean:write name='attribute' property='id'/>"><bean:write name="attribute" property="label" filter="false"/><c:if test="${attribute.label != null && attribute.label != '' && attribute.label != ' '}">:</c:if></label>
								<c:if test="${attribute.isKeywordPicker && attribute.autoComplete && attribute.isVisible && autoCompleteEnabled}">
									<br /><span style="font-weight: normal"><bright:cmsWrite identifier="snippet-keyword-autocomplete-hint" filter="false"/></span>
								</c:if>
							</th>
						<td>
				</c:otherwise>
			</c:choose>
			
			<%-- Dynamc field --%>
			<c:choose>
			
				<c:when test="${attribute.isTextfield || attribute.isTextarea || attribute.isHyperlink || attribute.isAutoincrement || attribute.isFile}">
					<%-- Text field --%>
					<div style="position: relative;">
						<input type="text" name="attribute_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>" value="<c:out value='${valueToRefine}' />" />
					</div>	
					<c:if test="${attribute.autoComplete && attribute.isVisible && autoCompleteEnabled}">
						<script type="text/javascript">
							<c:set var="searchOrEdit" value="search" />
							<%@include file="../inc/autocomplete_initialise.jsp"%>
						</script>
					</c:if>
				</c:when>
				
				<c:when test="${attribute.isDatepicker || attribute.isDateTime}">
					<%-- Date picker / Date time --%>
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<c:choose>
								<c:when test="${not empty attribute.dateFormatType.dateFormatString}">
									<fmt:formatDate var="refineDateLower" value="${userprofile.searchCriteria.lowerDateToRefine[attIdString]}" pattern="${attribute.dateFormatType.dateFormatString}" />
									<fmt:formatDate var="refineDateUpper" value="${userprofile.searchCriteria.upperDateToRefine[attIdString]}" pattern="${attribute.dateFormatType.dateFormatString}" />
								</c:when>
								<c:otherwise>
									<fmt:formatDate var="refineDateLower" value="${userprofile.searchCriteria.lowerDateToRefine[attIdString]}" pattern="dd/MM/yyyy" />
									<fmt:formatDate var="refineDateUpper" value="${userprofile.searchCriteria.upperDateToRefine[attIdString]}" pattern="dd/MM/yyyy" />
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${filterValue != null && filterValue.value != null}">
							<c:set var="refineDateLower" value="${filterValue.value}"/>
							<c:set var="refineDateUpper" value="${filterValue.value}"/>
						</c:when>
						<c:otherwise>
							<c:set var="name" value="attribute_${attIdString}_lower" />
							<c:set var="refineDateLower" value="${param[name]}"/>
							<c:set var="name" value="attribute_${attIdString}_upper" />
							<c:set var="refineDateUpper" value="${param[name]}"/>
						</c:otherwise>
					</c:choose>
					
					<%-- check date format type --%>
					<c:choose>
						<c:when test="${ attribute.dateFormatType == 'YEAR_AND_MONTH' }">
							<bright:cmsWrite identifier="snippet-between" filter="false"/>
							<input type="text" size="17" maxlength="20" class="small monthPicker" name="attribute_<bean:write name='attribute' property='id'/>_lower" value="<c:out value='${refineDateLower}'/>" /> 
							<bright:cmsWrite identifier="snippet-and" filter="false"/>
							<input type="text" size="17" maxlength="20" class="small monthPicker" name="attribute_<bean:write name='attribute' property='id'/>_upper" value="<c:out value='${refineDateUpper}'/>" /> 
							(<c:out value="${dateFormatYearMonthHelpString}" />) 
						</c:when>
						
						<c:when test="${ attribute.dateFormatType == 'YEAR_ONLY' }">
							<bright:cmsWrite identifier="snippet-between" filter="false"/>
							<input type="text" size="17" maxlength="20" class="small" name="attribute_<bean:write name='attribute' property='id'/>_lower" value="<c:out value='${refineDateLower}'/>" /> 
							<bright:cmsWrite identifier="snippet-and" filter="false"/>
							<input type="text" size="17" maxlength="20" class="small" name="attribute_<bean:write name='attribute' property='id'/>_upper" value="<c:out value='${refineDateUpper}'/>" /> 
							(<c:out value="${dateFormatYearOnlyHelpString}" />) 
						</c:when>
						
						<%-- standard for Date-Time(s) and AttributeDateFormatType.FULL for datepicker --%>
						<c:otherwise>
							<bright:cmsWrite identifier="snippet-between" filter="false"/>
							<input type="text" size="17" maxlength="20" class="date" id="startDate_<bean:write name='attribute' property='id'/>" name="attribute_<bean:write name='attribute' property='id'/>_lower" value="<c:out value='${refineDateLower}'/>" />&nbsp; 
							<bright:cmsWrite identifier="snippet-and" filter="false"/>
							<input type="text" size="17" maxlength="20" class="date" id="endDate_<bean:write name='attribute' property='id'/>" name="attribute_<bean:write name='attribute' property='id'/>_upper" value="<c:out value='${refineDateUpper}'/>" />&nbsp;
							(<c:out value="${dateFormatHelpString}" />) 
						</c:otherwise>
					</c:choose>
				</c:when>
				
				<c:when test="${attribute.isDropdownList}">
					<%-- Dropdown list--%>
					<select name="attribute_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>">
						<option value="">[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</option>
						<logic:iterate name="attribute" property="listOptionValues" id="optionValue">
							<option <c:if test="${valueToRefine == optionValue.value}">selected</c:if>><bean:write name='optionValue' property='value' filter='false'/></option>
						</logic:iterate>
						<option value="isempty">[<bright:cmsWrite identifier="snippet-none" filter="false"/>]</option>
					</select>
				</c:when>
				
				<c:when test="${attribute.isCheckList}">
					<%-- Check list--%>
					<c:set var="totalChecks" value="${attribute.numberOfOptions}"/>
					<bright:applicationSetting id="columns" settingName="checkbox-attribute-columns"/>
					<bright:applicationSetting id="limit" settingName="checkbox-attribute-single-list-limit"/>
					<c:set var="rowCount" value="${totalChecks/columns}"/>
					<c:set var="nextBreakPoint" value="${index+rowCount-1}"/>
					<table cellspacing="0" cellpadding="0" class="checklistCol" border="0"> 
						<logic:iterate name="attribute" property="listOptionValues" id="optionValue" indexId="index">
							<%@include file="../inc/inc_value_checker.jsp"%>
							<tr>
								<td style="padding:0; width:15px">
									<input type="checkbox"  class="checkbox" name="attribute_<bean:write name='attribute' property='id'/>" id="<bean:write name='attribute' property='id'/>_<bean:write name='optionValue' property='id'/>" value="<bean:write name='optionValue' property='value'/>" <c:if test='${optionValue.isSelected || hasValue}'>checked="checked"</c:if> />
								</td>
								<td style="padding: 3px 0 0 2px;">
									<label for="<bean:write name='attribute' property='id'/>_<bean:write name='optionValue' property='id'/>"><bean:write name='optionValue' property='value' filter='false'/></label>
								</td>
							</tr>
							<c:if test="${(totalChecks > limit) && (nextBreakPoint > 0 && index >= nextBreakPoint)}">
							<c:set var="nextBreakPoint" value="${index+rowCount}"/>
							</table>
							<table cellspacing="0" cellpadding="0" class="checklistCol" border="0">
							</c:if>
						</logic:iterate>	
					</table>
					<div class="clearing"></div>
				</c:when>
				
				<c:when test="${attribute.isOptionList}">
					<%-- Option list--%>
					<select name="attribute_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>" size="5" multiple="multiple">
						<logic:iterate name="attribute" property="listOptionValues" id="optionValue">
							<%@include file="../inc/inc_value_checker.jsp"%>
							<option <c:if test="${hasValue}">selected</c:if>><bean:write name='optionValue' property='value'/></option>
						</logic:iterate>
					</select>
				</c:when>
				
				<c:when test="${attribute.isKeywordPicker}">
					<input type="text" name="attribute_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>" size="55" class="text" <logic:present name='valueToRefine'>value="<c:out value='${valueToRefine}'/>"</logic:present> />
					<c:if test="${useKeywordChooser}">
						<script type="text/javascript">
							document.write('&nbsp;<a href="javascript:;" onclick="openKeywordChooser(true, <c:out value='${attribute.treeId}'/>, <c:out value='${attribute.id}'/>, 0<c:if test='${attribute.defaultKeywordFilter != null && attribute.defaultKeywordFilter != ""}'>, \'<bean:write name="attribute" property="defaultKeywordFilter" filter="false"/>\'</c:if>); return false;" onkeypress="openKeywordChooser(true, <c:out value='${attribute.treeId}'/>, <c:out value='${attribute.id}'/>, 0<c:if test='${attribute.defaultKeywordFilter != null && attribute.defaultKeywordFilter != ""}'>, \'<bean:write name="attribute" property="defaultKeywordFilter" filter="false"/>\'</c:if>); return false;" title="Popup keyword selector"><img src="../images/standard/icon/keyword_chooser.gif" class="help" alt="Choose Keywords" height="15" width="15" border="0" /><\/a>'); 
						</script>
					</c:if>
					<br />
					<c:if test="${attribute.autoComplete && attribute.isVisible && autoCompleteEnabled}">
						<script type="text/javascript">
							<c:set var="searchOrEdit" value="search" />
							<%@include file="../inc/autocomplete_initialise.jsp"%>
						</script>
					</c:if>
				</c:when>
				
				<c:when test="${attribute.isExternalDictionary}">
					<table cellpadding="0" cellspacing="0" style="margin:0">
						<tr>
							<td>
								<textarea readonly="readonly" onclick="<c:out value='${attribute.buttonScript}' escapeXml='false' />" name="attribute_additional_<bean:write name='attribute' property='id'/>" cols="40" rows="4" id="field_additional<bean:write name='attribute' property='id'/>" ><c:out value='${valueToRefine.additionalValue}'/></textarea><br/> 
								<input type="hidden" name="attribute_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>" size="55" class="text" <logic:present name='valueToRefine'>value="<bean:write name='valueToRefine' property='value'/>"</logic:present>/>
							</td>
							<td>
								<input type="button" class="button" style="margin-bottom:5px; display:block; width:120px;" value="<bright:cmsWrite identifier='button-dictionary-chooser' filter='false' />" onclick="<c:out value='${attribute.buttonScript}' escapeXml='false' />" onkeypress="<c:out value='${attribute.buttonScript}' escapeXml='false' />" /> 
							</td>
						</tr>
					</table>
				</c:when>
				
				<c:when test="${attribute.isNumeric}">
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<c:set var="refineNumberLower" value="${userprofile.searchCriteria.lowerNumbersToRefine[attIdString]}"/>
							<c:set var="refineNumberUpper" value="${userprofile.searchCriteria.upperNumbersToRefine[attIdString]}"/>
						</c:when>						
						<c:when test="${filterValue != null && filterValue.value != null}">
							<c:set var="refineNumberLower" value="${filterValue.value}"/>
							<c:set var="refineNumberUpper" value="${filterValue.value}"/>
						</c:when>
						<c:otherwise>
							<c:set var="name" value="attribute_${attIdString}_lower" />
							<c:set var="refineNumberLower" value="${param[name]}"/>
							<c:set var="name" value="attribute_${attIdString}_upper" />
							<c:set var="refineNumberUpper" value="${param[name]}"/>
						</c:otherwise>
					</c:choose>
					<bright:cmsWrite identifier="snippet-between" filter="false"/>
					<input type="text" size="17" maxlength="<c:if test="${attribute.isNumeric}">38</c:if>" class="small" name="attribute_<bean:write name='attribute' property='id'/>_lower" value="<c:out value='${refineNumberLower}'/>" /> 
					<bright:cmsWrite identifier="snippet-and" filter="false"/>
					<input type="text" size="17" maxlength="<c:if test="${attribute.isNumeric}">38</c:if>" class="small" name="attribute_<bean:write name='attribute' property='id'/>_upper" value="<c:out value='${refineNumberUpper}'/>" /> 
				</c:when>
				
				<c:when test="${attribute.isSpatialArea}">
					<c:choose>
						<c:when test="${searchForm.refineSearch}">
							<c:set var="refineAreaWest" value="${userprofile.searchCriteria.westsToRefine[attIdString]}"/>
							<c:set var="refineAreaEast" value="${userprofile.searchCriteria.eastsToRefine[attIdString]}"/>
							<c:set var="refineAreaSouth" value="${userprofile.searchCriteria.southsToRefine[attIdString]}"/>
							<c:set var="refineAreaNorth" value="${userprofile.searchCriteria.northsToRefine[attIdString]}"/>
							<c:set var="refineAreaLat" value="${userprofile.searchCriteria.latsToRefine[attIdString]}"/>
							<c:set var="refineAreaLong" value="${userprofile.searchCriteria.longsToRefine[attIdString]}"/>
							<c:set var="refineAreaRadius" value="${userprofile.searchCriteria.radiusesToRefine[attIdString]}"/>
							
							<%-- Radio button: if radius populated assume it is a point/radius search --%>
							<c:choose>
								<c:when test="${!empty userprofile.searchCriteria.radiusesToRefine[attIdString]}">
									<c:set var="refineAreaRadio" value="searchPoint" />
								</c:when>
								<c:otherwise>
									<c:set var="refineAreaRadio" value="searchArea" />
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<%-- Steve: Have to use cset to generate the param name - get cast exception if we try to concat the string inside param[] --%>
							<c:set var="name" value="attribute_${attIdString}_west" />
							<c:set var="refineAreaWest" value="${param[name]}"/>
							<c:set var="name" value="attribute_${attIdString}_east" />
							<c:set var="refineAreaEast" value="${param[name]}"/>
							<c:set var="name" value="attribute_${attIdString}_south" />
							<c:set var="refineAreaSouth" value="${param[name]}"/>
							<c:set var="name" value="attribute_${attIdString}_north" />
							<c:set var="refineAreaNorth" value="${param[name]}"/>
							<c:set var="name" value="attribute_${attIdString}_lat" />
							<c:set var="refineAreaLat" value="${param[name]}"/>
							<c:set var="name" value="attribute_${attIdString}_long" />
							<c:set var="refineAreaLong" value="${param[name]}"/>
							<c:set var="name" value="attribute_${attIdString}_radius" />
							<c:set var="refineAreaRadius" value="${param[name]}"/>
							<c:set var="name" value="choose_search_area_${attIdString}" />
							<c:set var="refineAreaRadio" value="${param[name]}"/>
						</c:otherwise>
					</c:choose>
					
					<div class="spatialAreaWrap">
					
						<p class="noTopMargin areaPointToggle  jsTabs">
							<label rel="searchArea" <c:if test="${refineAreaRadio != 'searchPoint'}"> class="active" </c:if>>
								<input type="radio" name="choose_search_area_<bean:write name='attribute' property='id'/>" value="searchArea" class="radio" <c:if test="${refineAreaRadio != 'searchPoint'}"> checked="checked" </c:if> />
								<bright:cmsWrite identifier="snippet-define-search-area" filter="false"/>
							</label>
							<label rel="searchPoint" <c:if test="${refineAreaRadio == 'searchPoint'}"> class="active" </c:if>>
								<input type="radio" name="choose_search_area_<bean:write name='attribute' property='id'/>" value="searchPoint" class="radio" <c:if test="${refineAreaRadio == 'searchPoint'}"> checked="checked" </c:if> />
								<bright:cmsWrite identifier="snippet-define-point-radius" filter="false"/>
							</label>
						</p>
						<div class="tabContent noBottomMargin">
					
							<table border="0" cellspacing="0" cellpadding="0" class="nested searchArea">
								<tr>
									<td><label for=""><bright:cmsWrite identifier="label-spatial-area-west"/>:</label><br />
									<input type="text" class="text wLng" name="attribute_<bean:write name='attribute' property='id'/>_west" value="<c:out value='${refineAreaWest}'/>" /></td>

									<td><label for=""><bright:cmsWrite identifier="label-spatial-area-south"/>:</label><br />
									<input type="text" class="text sLat" name="attribute_<bean:write name='attribute' property='id'/>_south" value="<c:out value='${refineAreaSouth}'/>" /></td>
									<td><a href="viewSpatialSearchMap?attributeId=<c:out value='${attribute.id}' />" target="_new" class="mapPopup js-enabled-show"><bright:cmsWrite identifier="link-map" filter="false"/></a></td>
								</tr> 
								<tr>
									<td><label for=""><bright:cmsWrite identifier="label-spatial-area-east"/>:</label><br />
									<input type="text" class="text eLng"name="attribute_<bean:write name='attribute' property='id'/>_east" value="<c:out value='${refineAreaEast}'/>" /></td>

									<td><label for=""><bright:cmsWrite identifier="label-spatial-area-north"/>:</label><br />
									<input type="text" class="text nLat" name="attribute_<bean:write name='attribute' property='id'/>_north" value="<c:out value='${refineAreaNorth}'/>" /></td>
									<td></td>
								</tr>	
							</table>
					
					
					
							<table border="0" cellspacing="0" cellpadding="0" class="nested searchPoint">
								<tr>
									<td><label for="field<bean:write name='attribute' property='id'/>_lat"><bright:cmsWrite identifier="label-latitude"/>:</label><br />
									<input type="text" class="text sLat" name="attribute_<bean:write name='attribute' property='id'/>_lat" value="<c:out value='${refineAreaLat}' />" id="field<bean:write name='attribute' property='id'/>_lat" /></td>			
								
									<td><label for="field<bean:write name='attribute' property='id'/>_long"><bright:cmsWrite identifier="label-longitude"/>:</label><br />
									<input type="text" class="text wLng" name="attribute_<bean:write name='attribute' property='id'/>_long" value="<c:out value='${refineAreaLong}' />" id="field<bean:write name='attribute' property='id'/>_long" /></td>
									<td><a href="viewSpatialSearchMap?attributeId=<c:out value='${attribute.id}' />&point=true" target="_new" class="mapPopup js-enabled-show"><bright:cmsWrite identifier="link-map" filter="false"/></a></td>
								</tr>
								<tr>	
									<td>
										<label for="field<bean:write name='attribute' property='id'/>_radius"><bright:cmsWrite identifier="label-search-radius"/>:</label><br />
										<input type="text" class="text small" name="attribute_<bean:write name='attribute' property='id'/>_radius" value="<c:out value='${refineAreaRadius}' />" id="field<bean:write name='attribute' property='id'/>_radius" /> km
									</td>
									<td>&nbsp;</td><td>&nbsp;</td>
								</tr>
							</table>
						</div>						

						<script type="text/JavaScript">
							<c:choose>
								<c:when test="${refineAreaRadio == 'searchPoint'}">
									$j('table.searchArea').hide()
									$j('table.searchPoint').show()
								</c:when>
								<c:otherwise>
									$j('table.searchPoint').hide()
									$j('table.searchArea').show()
								</c:otherwise>
							</c:choose>
						</script>	
					
					</div>
				</c:when>	
							
			</c:choose>
				</td>
			</tr>
			
		</c:otherwise>		
			
	</c:choose>





