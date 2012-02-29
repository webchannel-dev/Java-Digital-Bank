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
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="ratingsAreVotes" settingName="ratings-are-votes"/>
<bright:applicationSetting id="autoCompleteEnabled" settingName="auto-complete-enabled"/>

	<c:choose>
		<c:when test="${attribute.static}">
			
			<%-- Explicitly test the static ones --%>
			<c:choose>		
				
				<%-- Id field --%>
				<c:when test="${attribute.fieldName == 'assetId'}">		
				<tr>
					<th>
						<label for="ids"><bean:write name="attribute" property="label" filter="false"/>:</label> 
					</th>
					<td>
						<div style="position: relative">
						<input type="text" name="assetIds" size="6" class="text" id="ids" <c:if test='${searchForm.refineSearch}'>value='<bean:write name='userprofile' property='searchCriteria.originalAssetIdsString'/>'</c:if>/>
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
						<bright:applicationSetting id="maxRating" settingName="max-rating"/>
						<html:select name="searchForm" property="averageRating" size="1">
							<option value="-1" <c:if test='${searchForm.refineSearch && userprofile.searchCriteria.averageRating == "-1"}'>selected</c:if>>[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</option>
							<c:forEach begin="0" end="${maxRating-1}" var="value">
								<c:choose>
									<c:when test="${value == 0}">
										<option value="0" <c:if test='${searchForm.refineSearch && userprofile.searchCriteria.averageRating == 0}'>selected</c:if>>&gt;=&nbsp;
									</c:when>
									<c:otherwise>
										<option value="<bean:write name='value'/>.01" <c:if test='${searchForm.refineSearch && userprofile.searchCriteria.averageRating == value+".01"}'>selected</c:if>>&gt;&nbsp;
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
				<tr>
					<%-- Get the label for date added --%>
					<th>
						<bean:write name="attribute" property="label"/>: 
					</th>
					<td>
						<span class="inline"><bright:cmsWrite identifier="snippet-between" filter="false"/></span>
						<input type="text" size="17" maxlength="20" class="small text" id="addedLower" name="dateAddedLower" <c:if test="${searchForm.refineSearch}">value="<bean:write name='userprofile' property='searchCriteria.dateImageAddedLower' format='dd/MM/yyyy'/>"</c:if>/>&nbsp;<a href="#" title="Date selector popup"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Calendar" onclick="openDatePickerSupportUS(document.getElementById('addedLower'), <bean:write name='daysBeforeMonths' />);" width="16" height="15"></a>
						<span class="inline"><bright:cmsWrite identifier="snippet-and" filter="false"/></span>
						<input type="text" size="17" maxlength="20" class="small text" id="addedUpper" name="dateAddedUpper"  <c:if test="${searchForm.refineSearch}">value="<bean:write name='userprofile' property='searchCriteria.dateImageAddedUpper' format='dd/MM/yyyy'/>"</c:if>/>&nbsp;<a href="#" title="Date selector popup"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Calendar" onclick="openDatePickerSupportUS(document.getElementById('addedUpper'), <bean:write name='daysBeforeMonths' />);" width="16" height="15"></a> 
						<span class="inline">(<c:out value="${dateFormatHelpString}" />)</span>
					</td>
				</tr>
				</c:when>

				<%-- Date last mod --%>
				<c:when test="${attribute.fieldName == 'dateLastModified'}">
				<tr>
					<%-- Get the label for date added --%>
					<th>
						<bean:write name="attribute" property="label"/>: 
					</th>
					<td>
						<span class="inline"><bright:cmsWrite identifier="snippet-between" filter="false"/></span>
						<input type="text" size="17" maxlength="20" class="small text" id="modLower" name="dateModLower" <c:if test="${searchForm.refineSearch}">value="<bean:write name='userprofile' property='searchCriteria.dateImageModLower' format='dd/MM/yyyy'/>"</c:if>/>&nbsp;<a href="#" title="Date selector popup"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Calendar" onclick="openDatePickerSupportUS(document.getElementById('modLower'), <bean:write name='daysBeforeMonths' />);" width="16" height="15"></a>
						<span class="inline"><bright:cmsWrite identifier="snippet-and" filter="false"/></span>
						<input type="text" size="17" maxlength="20" class="small text" id="modUpper" name="dateModUpper" <c:if test="${searchForm.refineSearch}">value="<bean:write name='userprofile' property='searchCriteria.dateImageModUpper' format='dd/MM/yyyy'/>"</c:if>/>&nbsp;<a href="#" title="Date selector popup"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Calendar" onclick="openDatePickerSupportUS(document.getElementById('modUpper'), <bean:write name='daysBeforeMonths' />);" width="16" height="15"></a> 
						<span class="inline">(<c:out value="${dateFormatHelpString}" />)</span>
					</td>
				</tr>
				</c:when>

				<%-- Date last downloaded --%>
				<c:when test="${userprofile.isAdmin && attribute.fieldName == 'dateLastDownloaded'}">
				<tr>
					<%-- Get the label for date downloaded --%>
					<th>
						<bean:write name="attribute" property="label"/>: 
					</th>
					<td>
						<span class="inline"><bright:cmsWrite identifier="snippet-between" filter="false"/></span>
						<input type="text" size="17" maxlength="20" class="small text" id="downloadedLower" name="dateDownloadedLower" <c:if test="${searchForm.refineSearch}">value="<bean:write name='userprofile' property='searchCriteria.dateDownloadedLower' format='dd/MM/yyyy'/>"</c:if>/>&nbsp;<a href="#" title="Date selector popup"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Calendar" onclick="openDatePickerSupportUS(document.getElementById('downloadedLower'), <bean:write name='daysBeforeMonths' />);" width="16" height="15" /></a>
						<span class="inline"><bright:cmsWrite identifier="snippet-and" filter="false"/></span>
						<input type="text" size="17" maxlength="20" class="small text" id="downloadedUpper" name="dateDownloadedUpper" <c:if test="${searchForm.refineSearch}">value="<bean:write name='userprofile' property='searchCriteria.dateDownloadedUpper' format='dd/MM/yyyy'/>"</c:if>/>&nbsp;<a href="#" title="Date selector popup"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Calendar" onclick="openDatePickerSupportUS(document.getElementById('downloadedUpper'), <bean:write name='daysBeforeMonths' />);" width="16" height="15"></a> 
						<span class="inline">(<c:out value="${dateFormatHelpString}" />)</span>
					</td>
				</tr>
				</c:when>

				<%-- Added By - text search against user's name and username --%>
				<c:when test="${attribute.fieldName == 'addedBy'}">
				<tr>
				<th>
					<label for="addedBy"><bean:write name="attribute" property="label"/>:</label>
				</th>
				<td>
					<input type="text" name="addedBy" id="addedBy" <c:if test='${searchForm.refineSearch}'>value='<bean:write name='userprofile' property='searchCriteria.addedBy'/>'</c:if>/>
				</c:when>

				<%-- Orientation field --%>
				<c:when test="${attribute.fieldName == 'orientation'}">		
				<tr>
					<th>
						<label for="orientation"><bean:write name="attribute" property="label" filter="false"/>:</label> 
					</th>
					<td>
						<html:select name="searchForm" property="orientation" styleId="orientation">
							<option value="0" <c:if test="${searchForm.refineSearch && userprofile.searchCriteria.orientation == 0}">selected</c:if>>[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</option>
							<option value="1" <c:if test="${searchForm.refineSearch && userprofile.searchCriteria.orientation == 1}">selected</c:if>><bright:cmsWrite identifier="snippet-landscape" filter="false"/></option>
							<option value="2" <c:if test="${searchForm.refineSearch && userprofile.searchCriteria.orientation == 2}">selected</c:if>><bright:cmsWrite identifier="snippet-portrait" filter="false"/></option>
							<option value="3" <c:if test="${searchForm.refineSearch && userprofile.searchCriteria.orientation == 3}">selected</c:if>><bright:cmsWrite identifier="snippet-square" filter="false"/></option>
						</html:select>
					</td>
				</tr>
				</c:when>

				<%-- Price. Only show if ecommerce is turned on: --%>
				<c:when test="${ecommerce && attribute.fieldName == 'price'}">
				<tr>
					<%-- Get the label --%>
					<th>
						<label for="price"><bean:write name="attribute" property="label" filter="false"/> (<bean:write name="searchForm" property="priceLower.currencySymbol" filter="false" />):</label> 
					</th>
					<td>
						<label for="priceLower"><bright:cmsWrite identifier="snippet-between" filter="false"/></label>
						<input type="text" size="17" maxlength="10" class="small text" id="priceLower" name="priceLower.formAmount" <c:if test="${searchForm.refineSearch}">value="<bean:write name='userprofile' property='searchCriteria.priceLower.formAmount'/>"</c:if> />
						<label for="priceUpper"><bright:cmsWrite identifier="snippet-and" filter="false"/></label>
						<input type="text" size="17" maxlength="10" class="small text" id="priceUpper" name="priceUpper.formAmount"  <c:if test="${searchForm.refineSearch}">value="<bean:write name='userprofile' property='searchCriteria.priceUpper.formAmount'/>"</c:if> />
					</td>
				</tr>
				</c:when>
				
				<%-- Size --%>
				<c:when test="${userprofile.isAdmin && attribute.fieldName == 'size'}">
				<tr>
					<%-- Get the label for size --%>
					<th>
						<bean:write name="attribute" property="label"/>: 
					</th>
					<td>
						<span class="inline"><bright:cmsWrite identifier="snippet-between" filter="false"/></span>
						<input type="text" size="17" maxlength="10" class="small text" id="filesizeLower" name="filesizeLower" <c:if test="${searchForm.refineSearch}">value="<bean:write name='userprofile' property='searchCriteria.filesizeLower'/>"</c:if> />
						<span class="inline"><bright:cmsWrite identifier="snippet-and" filter="false"/></span>
						<input type="text" size="17" maxlength="10" class="small text" id="filesizeUpper" name="filesizeUpper" <c:if test="${searchForm.refineSearch}">value="<bean:write name='userprofile' property='searchCriteria.filesizeUpper'/>"</c:if> />&nbsp;(Kb)
					</td>
				</tr>
				</c:when>

				<%-- Agreement status --%>
				<c:when test="${agreementsEnabled && attribute.fieldName == 'agreements'}">
					<tr>
						<th><label for="agreement"><bright:cmsWrite identifier="label-agreement-type" filter="false" /></label></th>
						<td>
							<html:select name="searchForm" property="agreementType" styleId="agreementType">
								<option value="0" <c:if test="${searchForm.refineSearch && userprofile.searchCriteria.agreementType == 0}">selected</c:if>>[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</option>	
								<option value="1" <c:if test="${searchForm.refineSearch && userprofile.searchCriteria.agreementType == 1}">selected</c:if>><bright:cmsWrite identifier="snippet-unrestricted" filter="false"/></option>	
								<option value="2" <c:if test="${searchForm.refineSearch && userprofile.searchCriteria.agreementType == 2}">selected</c:if>><bright:cmsWrite identifier="snippet-agreement-applies" filter="false"/></option>
								<option value="3" <c:if test="${searchForm.refineSearch && userprofile.searchCriteria.agreementType == 3}">selected</c:if>><bright:cmsWrite identifier="snippet-restricted" filter="false"/></option>
							</html:select>
						</td>
					</tr>
					<%-- Agreement content --%>
					<c:if test="${canSearchAgreements=='true' || (canSearchAgreements=='admin' && (userprofile.isAdmin || userprofile.isOrgUnitAdmin))}">
						<tr>
							<th><label for="agreement"><bright:cmsWrite identifier="label-agreement-text" filter="false" /></label></th>
							<td><input type="text" name="agreementText" <c:if test='${searchForm.refineSearch}'>value='<bean:write name='userprofile' property='searchCriteria.agreementText' filter="false"/>'</c:if> />
							</td>
						</tr>
					</c:if>
				</c:when>
				
				<c:when test="${attribute.fieldName == 'sensitive'}">		
					<tr>
						<th>
							<label for="sensitive"><bean:write name="attribute" property="label" filter="false"/></label> 
						</th>
						<td>
							<html:select name="searchForm" property="isSensitive" styleId="sensitive">
								<option value="" <c:if test="${searchForm.refineSearch && userprofile.searchCriteria.isSensitive == null}">selected</c:if>>[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</option>
								<option value="true" <c:if test="${searchForm.refineSearch && userprofile.searchCriteria.isSensitive == true}">selected</c:if>><bright:cmsWrite identifier="snippet-yes" filter="false"/></option>
								<option value="false" <c:if test="${searchForm.refineSearch && userprofile.searchCriteria.isSensitive == false}">selected</c:if>><bright:cmsWrite identifier="snippet-no" filter="false"/></option>
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
			
			<%-- see if we have a matching filter field --%>
			<bean:define id="attributeId" name="attribute" property="id"/>
			<bean:define id="filterValue" name="searchForm" property="<%= \"selectedFilter.attributeValueForAttribute(\" + attributeId + \")\" %>"/>
			
			<logic:equal name="searchForm" property="refineSearch" value="true">
				<logic:present name="userprofile" property="<%= \"searchCriteria.attributeValueForAttribute(\" + attributeId + \")\" %>">
					<bean:define id="valueToRefine" name="userprofile" property="<%= \"searchCriteria.attributeValueForAttribute(\" + attributeId + \")\" %>"/>
				</logic:present>
			</logic:equal>

			<%-- A dynamic field --%>
			<tr>
				<th>
					<label for="field<bean:write name='attribute' property='id'/>"><bean:write name="attribute" property="label" filter="false"/><c:if test="${attribute.label != null && attribute.label != '' && attribute.label != ' '}">:</c:if></label>
				</th>
				<td>
			<c:choose>
				<c:when test="${attribute.isTextfield || attribute.isTextarea || attribute.isHyperlink || attribute.isAutoincrement}">
					<%-- Text field --%>
					<div style="position: relative;">
						<input type="text" name="attribute_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>" <logic:present name='valueToRefine'>value="<bean:write name='valueToRefine' property='value' filter="false"/>"</logic:present><c:if test="${!searchForm.refineSearch && filterValue != null && filterValue.value != null}">value="<bean:write name='filterValue' property='value' filter="false"/>"</c:if> />
					</div>	
					<c:if test="${attribute.autoComplete && attribute.isVisible && autoCompleteEnabled}">
						<div id="autocomplete_field<bean:write name='attribute' property='id'/>" class="autocomplete"></div>
						<script type="text/javascript">
							<c:set var="searchOrEdit" value="search" />
							<%@include file="../inc/autocomplete_initialise.jsp"%>
						</script>
					</c:if>
				</c:when>
				<c:when test="${attribute.isDatepicker || attribute.isDateTime}">
					<c:if test="${searchForm.refineSearch}">
						<logic:present name="userprofile" property="<%= \"searchCriteria.lowerDateToRefine(\" + attributeId + \")\" %>">
							<bean:define id="refineDateLower" name="userprofile" property="<%= \"searchCriteria.lowerDateToRefine(\" + attributeId + \")\" %>"/>
						</logic:present>
						<logic:present name="userprofile" property="<%= \"searchCriteria.upperDateToRefine(\" + attributeId + \")\" %>">
							<bean:define id="refineDateUpper" name="userprofile" property="<%= \"searchCriteria.upperDateToRefine(\" + attributeId + \")\" %>"/>
						</logic:present>
					</c:if>
					<%-- Date picker --%>
					<bright:cmsWrite identifier="snippet-between" filter="false"/>
					<input type="text" size="17" maxlength="20" class="small" name="attribute_<bean:write name='attribute' property='id'/>_lower" <logic:present name="refineDateLower">value="<bean:write name='refineDateLower' format='dd/MM/yyyy'/>"</logic:present><c:if test="${filterValue != null && filterValue.value != null}">value="<bean:write name='filterValue' property='value'/>"</c:if> />&nbsp;<a href="#" title="Date selector popup"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Calendar" onclick="openDatePickerSupportUS(document.getElementsByName('attribute_<bean:write name='attribute' property='id'/>_lower')[0], <bean:write name='daysBeforeMonths' />)" width="16" height="15"></a> 
					<bright:cmsWrite identifier="snippet-and" filter="false"/>
					<input type="text" size="17" maxlength="20" class="small" name="attribute_<bean:write name='attribute' property='id'/>_upper" <logic:present name="refineDateUpper">value="<bean:write name='refineDateUpper' format='dd/MM/yyyy'/>"</logic:present><c:if test="${filterValue != null && filterValue.value != null}">value="<bean:write name='filterValue' property='value'/>"</c:if>>&nbsp;<a href="#" title="Date selector popup"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Calendar" onclick="openDatePickerSupportUS(document.getElementsByName('attribute_<bean:write name='attribute' property='id'/>_upper')[0], <bean:write name='daysBeforeMonths' />)" width="16" height="15"></a> 
					(<c:out value="${dateFormatHelpString}" />) 
				</c:when>
				<c:when test="${attribute.isDropdownList}">
					<%-- Dropdown list--%>
					<select name="attribute_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>">
						<option value="">[<bright:cmsWrite identifier="snippet-any" filter="false"/>]</option>
						<logic:iterate name="attribute" property="listOptionValues" id="optionValue">
							<option <c:if test="${searchForm.refineSearch && valueToRefine != null && valueToRefine.value != null && valueToRefine.value == optionValue.value}">selected</c:if><c:if test="${!searchForm.refineSearch && filterValue != null && filterValue.value != null && filterValue.value == optionValue.value}">selected</c:if>><bean:write name='optionValue' property='value' filter='false'/></option>
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
							<%@include file="../attribute-admin/inc_value_checker.jsp"%>
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
							<%@include file="../attribute-admin/inc_value_checker.jsp"%>
							<option <c:if test="${hasValue}">selected</c:if>><bean:write name='optionValue' property='value'/></option>
						</logic:iterate>
					</select>
				</c:when>
				<c:when test="${attribute.isKeywordPicker}">
					<input type="text" name="attribute_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>" size="55" class="text" <logic:present name='valueToRefine'>value="<bean:write name='valueToRefine' property='value'/>"</logic:present> />
					<c:if test="${useKeywordChooser}">
						<script type="text/javascript">
							document.write('&nbsp;<a href="javascript:;" onclick="openKeywordChooser(true, <c:out value='${attribute.treeId}'/>, <c:out value='${attribute.id}'/>, 0<c:if test='${attribute.defaultKeywordFilter != null && attribute.defaultKeywordFilter != ""}'>, \'<bean:write name="attribute" property="defaultKeywordFilter" filter="false"/>\'</c:if>); return false;" onkeypress="openKeywordChooser(true, <c:out value='${attribute.treeId}'/>, <c:out value='${attribute.id}'/>, 0<c:if test='${attribute.defaultKeywordFilter != null && attribute.defaultKeywordFilter != ""}'>, \'<bean:write name="attribute" property="defaultKeywordFilter" filter="false"/>\'</c:if>); return false;" title="Popup keyword selector"><img src="../images/standard/icon/keyword_chooser.gif" class="help" alt="Choose Keywords" height="15" width="15" border="0" /><\/a>'); 
						</script>
					</c:if>
					<br />
					<c:if test="${attribute.autoComplete && attribute.isVisible && autoCompleteEnabled}">
						<div id="autocomplete_field<bean:write name='attribute' property='id'/>" class="autocomplete"></div>
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
					<c:if test="${searchForm.refineSearch}">
						<c:set var="attIdString"><c:out value="${attributeId}"/></c:set>
						<c:set var="refineNumberLower" value="${userprofile.searchCriteria.lowerNumbersToRefine[attIdString]}"/>
						<c:set var="refineNumberUpper" value="${userprofile.searchCriteria.upperNumbersToRefine[attIdString]}"/>
					</c:if>
					<bright:cmsWrite identifier="snippet-between" filter="false"/>
					<input type="text" size="17" maxlength="<c:if test="${attribute.isNumeric}">38</c:if>" class="small" name="attribute_<bean:write name='attribute' property='id'/>_lower" <logic:present name="refineNumberLower">value="<bean:write name='refineNumberLower'/>"</logic:present><c:if test="${filterValue != null && filterValue.value != null}">value="<bean:write name='filterValue' property='value'/>"</c:if> /> 
					<bright:cmsWrite identifier="snippet-and" filter="false"/>
					<input type="text" size="17" maxlength="<c:if test="${attribute.isNumeric}">38</c:if>" class="small" name="attribute_<bean:write name='attribute' property='id'/>_upper" <logic:present name="refineNumberUpper">value="<bean:write name='refineNumberUpper'/>"</logic:present><c:if test="${filterValue != null && filterValue.value != null}">value="<bean:write name='filterValue' property='value'/>"</c:if>> 
				</c:when>
			</c:choose>
				</td>
			</tr>
			
		</c:otherwise>		
			
	</c:choose>





