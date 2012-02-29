<%@ page import="com.bright.assetbank.application.constant.AssetBankSettings" %> 
<%--
	Displays the form field for an attribute, according to the attribute type

	History:
	 d1		Martin Wilson		24-Oct-2005		Created.
	 d2		Matt Stevenson		09-Jan-2006		Changed empty check
	 d3		Matt Stevenson		07-Mar-2007		Modified to work with keyword picker
	 d4		Matt Stevenson		09-Mar-2007		Made type checks more readable
	 d5		Matt Stevenson		01-Nov-2007		Modified keyword picker popup to take a filter
	 d6     Matt Woollard       14-Jan-2008     Added dropdown to give option to append or replace
	 d7     Matt Woollard       14-Jan-2008     Added html text area
	 d8		Steve Bryan			23-Dec-2008		Added External Dictionary type
	--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<bright:applicationSetting id="bOnlyAdminCanEditKeywordList" settingName="admin-only-can-edit-keyword-list"/>
<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />
<bright:applicationSetting id="timeFormatHelpString" settingName="form-time-format" />
<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>
<bean:define id="keywordDelimiter" value="<%= AssetBankSettings.getKeywordDelimiter() %>"/>
<bright:applicationSetting id="keywordAncestorDelimiter" settingName="keyword-anscestor-delimiter"/>
<bright:applicationSetting id="autoCompleteEnabled" settingName="auto-complete-enabled"/>
<bright:applicationSetting id="maxVarcharAttributeLength" settingName="max-varchar-attribute-length"/>

<%-- The attribute must be in a page variable called 'attribute'--%>

<logic:notPresent name="setAttributeValue">
	<bean:define id="setAttributeValue" value="true"/>
</logic:notPresent>

<logic:notPresent name="editingFilter">
	<bean:define id="editingFilter" value="false"/>
</logic:notPresent>

<logic:notPresent name="bulkUploadFirst">
	<bean:define id="bulkUploadFirst" value="false"/>
</logic:notPresent>

<c:if test="${setAttributeValue}">
	<c:set var="attributeValue" value="${attribute.value}"/>
</c:if>

<bean:define id="attributeId" name="attribute" property="id"/>

<c:set var="showNativeLanguages" value="${supportMultiLanguage && !userprofile.currentLanguage.default}"/>
<c:choose>
	<%-- If this attribute is a group header, store the output for the header in a page variable called groupHeaderContent --%>
	<c:when test="${attributeValue.attribute.typeId == 10}">
		<c:set var="groupHeaderContent">
			<tr>
				<td colspan="3" style="padding-right:0px">
						<h3 class="attributeGroupHeading"><bean:write name="attribute" property="label" filter="false"/></h3>
				</td>
			</tr>
		</c:set>
	</c:when>
	<%-- If the attribute is not a group header, output the contents of the variable groupHeaderContent that we build in a previous iteration, then reset it --%>
	<c:when test="${not empty groupHeaderContent && attributeValue.attribute.typeId != 10}">
		<bean:write name="groupHeaderContent" filter="false"/>
		<c:set var="groupHeaderContent" value=""/>
	</c:when>
</c:choose>
<c:choose>
	<c:when test="${attribute.isTextfield && (!supportMultiLanguage || !editingFilter)}">
		<%-- Text field --%>
		<tr>
			<th>
				<label for="field<bean:write name='attribute' property='id'/>"><bean:write name="attribute" property="label" filter="false"/><c:if test="${attribute.label != null && attribute.label != '' && attribute.label != ' '}">:</c:if> </label>
				<c:if test="${showNativeLanguages}"><span style="font-weight: normal;">(<bright:cmsWrite identifier="term-for-english" filter="false" />)</span></c:if>
				<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
				<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
				<%@include file="inc_attribute_required.jsp"%>
			</th>
			<td></td>
         	<%@include file="inc_attribute_bulk_update_mode.jsp"%>
			<td>
				<div style="position: relative">
				   <input type="text" class="text" name="attribute_<bean:write name='attribute' property='id'/>" value='<%@include file="inc_display_value.jsp"%>' id="field<bean:write name='attribute' property='id'/>" <c:if test="${attribute.isTextfieldShort}"> maxlength="<c:out value="${maxVarcharAttributeLength}"/>"</c:if> <%@include file="inc_attribute_onchange.jsp"%>>
				</div>
				
				<c:if test="${attribute.autoComplete && attribute.isVisible && autoCompleteEnabled}">
					<div id="autocomplete_field<bean:write name='attribute' property='id'/>" class="autocomplete"></div>
					<script type="text/javascript">
						<c:set var="searchOrEdit" value="edit" />
         			<%@include file="../inc/autocomplete_initialise.jsp"%>
         		</script>
				</c:if>
			</td>
		</tr>
		
		<%-- Output any translations specific to this attribute value if they exist --%>
		<c:if test="${not empty attributeValue.translations}">
			<logic:iterate name="attributeValue" property="translations" id="translation" indexId="tIndex">
				<tr>
					<th class="translation">
						<%@include file="inc_translated_attribute_label.jsp"%>
						<c:set var="helpText" value="${translation.attributeTranslation.helpText}" scope="request"/>
						<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
					</th>
					<td>&nbsp;</td>
					<c:if test="${bIsBulkUpdate}"><td>&nbsp;</td><td>&nbsp;</td></c:if>
					<td>
						<div style="position: relative">
							<input type="text" class="text" name="attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" value="<bean:write name='translation' property='value' filter='false'/>" id="field<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" />
						</div>
						<c:if test="${attribute.autoComplete && attribute.isVisible && autoCompleteEnabled}">
							<div id="autocomplete_field<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" class="autocomplete"></div>
							<script type="text/javascript">
								<c:set var="searchOrEdit" value="edit" />
         					<%@include file="../inc/autocomplete_initialise_multi_language.jsp"%>
         				</script>
						</c:if>
					</td>
				</tr>
			</logic:iterate>
		</c:if>
		<%-- If the value doesn't have any translations (because it doesn't really exist yet) use translation in the attribute itself --%>
		<c:if test="${empty attributeValue.translations && not empty attribute.translations}">
			<logic:iterate name="attribute" property="translations" id="translation" indexId="tIndex">
				<tr>
					<th class="translation">
						<%@include file="inc_translated_attribute_label.jsp"%>
						<c:set var="helpText" value="${translation.helpText}" scope="request"/>
						<jsp:include page="../asset-admin/inc_attribute_help.jsp"/> 
					</th>
					<td>&nbsp;</td>
					<c:if test="${bIsBulkUpdate}"><td>&nbsp;</td><td>&nbsp;</td></c:if>
					<td>
						<input type="text" class="text" name="attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" value="" id="field<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>">
					</td>
				</tr>
			</logic:iterate>
		</c:if>
		
	</c:when>
	<c:when test="${attribute.isTextarea && (!supportMultiLanguage || !editingFilter)}">
		<%-- Text area --%>
		<c:set var="rows" value="${attribute.height}"/>
		<c:set var="cols"><c:if test="${attribute.width>0}"><c:out value="${attribute.width}"/></c:if><c:if test="${attribute.width<=0}">60</c:if></c:set>
		<c:set var="maxSize" value="${attribute.maxSize}"/>
		<tr>
			<th class="textarea">
				<label for="field<bean:write name='attribute' property='id'/>"><bean:write name="attribute" property="label" filter="false"/><logic:notEmpty name="attribute" property="label">:</logic:notEmpty> </label>
				<c:if test="${showNativeLanguages}"><span style="font-weight: normal;">(<bright:cmsWrite identifier="term-for-english" filter="false" />)</span></c:if>
				<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
				<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
				<%@include file="inc_attribute_required.jsp"%>
			</th>
			<td></td>
			<%@include file="inc_attribute_bulk_update_mode.jsp"%>
			<td class="textarea">
				<input type="hidden" name="maxlength_attribute_<bean:write name='attribute' property='id'/>" value="<bean:write name='maxSize'/>">
				<input type="hidden" name="maxlengthmessage_attribute_<bean:write name='attribute' property='id'/>" value="<bean:write name='attribute' property='label'/> has exceeded its maximum length of <bean:write name='attribute' property='maxSize'/> characters"/>
				<%-- HTML text area --%>
				<c:if test="${attribute.isHtml}">
					<textarea class="editor" style="width:<c:if test="${attribute.width<=0}">80%;</c:if><c:if test="${attribute.width>0}">auto;</c:if>" name="attribute_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>" cols="<bean:write name='attribute' property='width'/>" rows="<bean:write name='attribute' property='height'/>" <%@include file="inc_attribute_onchange.jsp"%>><%@include file="inc_display_value.jsp"%></textarea>
				</c:if>
				<c:if test="${!attribute.isHtml}">
					<textarea <c:if test="${attribute.width>0}">style="width:auto;"</c:if> name="attribute_<bean:write name='attribute' property='id'/>" cols="<bean:write name='cols'/>" rows="<bean:write name='rows'/>" id="field<bean:write name='attribute' property='id'/>" <%@include file="inc_attribute_onchange.jsp"%>><%@include file="inc_display_value.jsp"%></textarea>
				</c:if>
				<c:if test="${attribute.autoComplete && attribute.isVisible && autoCompleteEnabled}">
					<div id="autocomplete_field<bean:write name='attribute' property='id'/>" class="autocomplete"></div>
					<script type="text/javascript">
						<c:set var="searchOrEdit" value="edit" />
         			<%@include file="../inc/autocomplete_initialise.jsp"%>
         		</script>
				</c:if>
			</td>
		</tr>
		
		<%-- Output any translations specific to this attribute value if they exist --%>
		<c:if test="${not empty attributeValue.translations}">
			<logic:iterate name="attributeValue" property="translations" id="translation" indexId="tIndex">
				<tr>
					<th class="translation">
						<%@include file="inc_translated_attribute_label.jsp"%>
						<c:set var="helpText" value="${translation.attributeTranslation.helpText}" scope="request"/>
						<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
					</th>
					<td>&nbsp;</td>
					<c:if test="${bIsBulkUpdate}"><td>&nbsp;</td><td>&nbsp;</td></c:if>
					<td>
						<input type="hidden" name="maxlength_attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" value="<bean:write name='maxSize'/>">
						<input type="hidden" name="maxlengthmessage_attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" value="<bean:write name='attribute' property='label'/> (<bean:write name="translation" property="language.name"/>) has exceeded its maximum length of <bean:write name='attribute' property='maxSize'/> characters"/>
						<%-- HTML text area --%>
						<c:if test="${attribute.isHtml}">
							<textarea class="editor" style="width:<c:if test="${attribute.width<=0}">80%;</c:if><c:if test="${attribute.width>0}">auto;</c:if>" name="attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" id="field<bean:write name='attribute' property='id'/>__<bean:write name="translation" property="language.id"/>" cols="<bean:write name='attribute' property='width'/>" rows="<bean:write name='attribute' property='height'/>"><bean:write name='translation' property='value' filter='false'/></textarea>
						</c:if>
						<c:if test="${!attribute.isHtml}">
							<textarea <c:if test="${attribute.width>0}">style="width:auto;"</c:if> name="attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" cols="<bean:write name='cols'/>" rows="<bean:write name='rows'/>" id="field<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>"><bean:write name='translation' property='value' filter='false'/></textarea>
						</c:if>
						<c:if test="${attribute.autoComplete && attribute.isVisible && autoCompleteEnabled}">
							<div id="autocomplete_field<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" class="autocomplete"></div>
							<script type="text/javascript">
								<c:set var="searchOrEdit" value="edit" />
         					<%@include file="../inc/autocomplete_initialise_multi_language.jsp"%>
         				</script>
						</c:if>
					</td>
				</tr>
			</logic:iterate>
		</c:if>
		<%-- If the value doesn't have any translations (because it doesn't really exist yet) use translation in the attribute itself --%>
		<c:if test="${empty attributeValue.translations && not empty attribute.translations}">
			<logic:iterate name="attribute" property="translations" id="translation" indexId="tIndex">
				<tr>
					<th class="translation">
						<%@include file="inc_translated_attribute_label.jsp"%> 
						<c:set var="helpText" value="${translation.helpText}" scope="request"/>
						<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
					</th>
					<td>&nbsp;</td>
					<c:if test="${bIsBulkUpdate}"><td>&nbsp;</td><td>&nbsp;</td></c:if>
					<td>
						<input type="hidden" name="maxlength_attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" value="<bean:write name='maxSize'/>">
						<input type="hidden" name="maxlengthmessage_attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" value="<bean:write name='attribute' property='label'/> (<bean:write name="translation" property="language.name"/>) has exceeded its maximum length of <bean:write name='attribute' property='maxSize'/> characters"/>
						
						<%-- HTML text area --%>
						<c:if test="${attribute.isHtml}">
							<textarea class="editor" style="width:<c:if test="${attribute.width<=0}">80%;</c:if><c:if test="${attribute.width>0}">auto;</c:if>" name="attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" id="field<bean:write name='attribute' property='id'/>__<bean:write name="translation" property="language.id"/>" cols="<bean:write name='attribute' property='width'/>" rows="<bean:write name='attribute' property='height'/>"></textarea>
						</c:if>
						<c:if test="${!attribute.isHtml}">
							<textarea <c:if test="${attribute.width>0}">style="width:auto;"</c:if> name="attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" cols="<bean:write name='cols'/>" rows="<bean:write name='rows'/>" id="field<bean:write name='attribute' property='id'/>__<bean:write name="translation" property="language.id"/>"></textarea>
						</c:if>	
					</td>
				</tr>
			</logic:iterate>
		</c:if>
		
	</c:when>
	<c:when test="${attribute.isDatepicker}">
		<%-- Date picker --%>
		<tr>
			<th>
				<label for="field<bean:write name='attribute' property='id'/>"><bean:write name="attribute" property="label" filter="false"/><logic:notEmpty name="attribute" property="label">:</logic:notEmpty> </label>
				<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
				<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
				<%@include file="inc_attribute_required.jsp"%>
			</th>
			<td></td>
			<%@include file="inc_attribute_bulk_update_mode.jsp"%>
			<td style="padding-bottom: 8px;">
				<input type="text" class="small" name="attribute_<bean:write name='attribute' property='id'/>" value="<%@include file="inc_display_value.jsp"%>" id="field<bean:write name='attribute' property='id'/>" <%@include file="inc_attribute_onchange.jsp"%>/>
				<script type="text/javascript">
					document.write('<a href="javascript:;" title="Date selector popup"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Calendar" onclick="openDatePickerSupportUS(document.getElementsByName(\'attribute_<bean:write name="attribute" property="id"/>\')[0], <bean:write name='daysBeforeMonths' />)" width="16" height="15" style="padding-right: 0;" /><\/a>');
				</script>
				<span class="inline">(<c:out value="${dateFormatHelpString}" />)</span>
			</td>
		</tr>	
	</c:when>
	<c:when test="${attribute.isDataLookupButton && !editingFilter}">
		<%-- Data lookup button --%>
		<tr>
			<th>
				<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
				<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
			</th>
			<td></td>
			<c:if test="${bIsBulkUpdate}">
			<td>&nbsp;</td>
			<td>&nbsp;</td>
			</c:if>
			<td style="padding-bottom: 8px;">
				<input type="submit" name="dataLookupAttribute:<c:out value='${attribute.id}'/>" id="dataLookupAttribute:<c:out value='${attribute.id}'/>" value="<c:out value='${attribute.label}'/>" class="button flush" onclick="performDataLookup('<c:out value='${attribute.id}'/>', document.getElementById('updateForm')); return false;" />
			</td>
		</tr>	
	</c:when>
	<c:when test="${attribute.isDateTime}">
		<%-- Datetime --%>
		<tr>
			<th>
				<label for="field<bean:write name='attribute' property='id'/>"><bean:write name="attribute" property="label" filter="false"/><logic:notEmpty name="attribute" property="label">:</logic:notEmpty> </label>
				<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
				<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
				<%@include file="inc_attribute_required.jsp"%>
			</th>
			<td></td>
			<%@include file="inc_attribute_bulk_update_mode.jsp"%>
			<td style="padding-bottom: 8px;">
				<input type="text" class="text" name="attribute_<bean:write name='attribute' property='id'/>" value="<%@include file="inc_display_value.jsp"%>" id="field<bean:write name='attribute' property='id'/>" <%@include file="inc_attribute_onchange.jsp"%>/>
				<script type="text/javascript">
					document.write('<a href="javascript:;" title="Date selector popup"><img class="icon" src="../images/standard/icon/calendar.gif" alt="Calendar" onclick="openDatePickerSupportUS(document.getElementsByName(\'attribute_<bean:write name="attribute" property="id"/>\')[0], <bean:write name='daysBeforeMonths' />)" width="16" height="15" style="padding-right: 0;" /><\/a>');
				</script>
				<span class="inline">(<c:out value="${dateFormatHelpString}" /> <c:out value="${timeFormatHelpString}" />)</span>
			</td>
		</tr>	
	</c:when>
	<c:when test="${attribute.isDropdownList}">
		<%-- Dropdown list--%>
		<tr>
			<th>
				<label for="field<bean:write name='attribute' property='id'/>"><bean:write name="attribute" property="label" filter="false"/><logic:notEmpty name="attribute" property="label">:</logic:notEmpty> </label>
				<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
				<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
				<%@include file="inc_attribute_required.jsp"%>
			</th>
			<td></td>
			<%@include file="inc_attribute_bulk_update_mode.jsp"%>
			<td>
				<select name="attribute_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>" <%@include file="inc_attribute_onchange.jsp"%>>
					<option value="">- <bright:cmsWrite identifier="snippet-please-select" filter="false"/> -</option>
					<logic:iterate name="attribute" property="listOptionValues" id="optionValue">
						 <option <c:if test="${(attributeValue!=null && optionValue.id==attributeValue.id)}">selected="selected"</c:if> value="<bean:write name='optionValue' property='id'/>"><bean:write name='optionValue' property='value' filter="false"/></option>	
					</logic:iterate>
				</select>
				<c:if test="${attribute.isExtendableList}">
					<br/>
					<label style="display:block;margin:3px 0;" for="field_additional<bean:write name='attribute' property='id'/>"><bright:cmsWrite identifier="add-new-value" filter="false"/></label>
					<input type="text" class="text" name="attribute_additional_<bean:write name='attribute' property='id'/>" id="field_additional<bean:write name='attribute' property='id'/>" value="<c:if test="${assetForm.hasErrors}"><bean:write name='attribute' property='value.value'/></c:if>">
				</c:if>
			</td>
		</tr>
	</c:when>
	<c:when test="${attribute.isCheckList}">
		<%-- Checklist --%>
		<tr>
			<th style="vertical-align: top;">
				<bean:write name="attribute" property="label" filter="false"/><logic:notEmpty name="attribute" property="label">:</logic:notEmpty> 
				<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
				<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
				<%@include file="inc_attribute_required.jsp"%>
				<bean:write name="attributeValue" property="value"/>
			</th>
			<td></td>
			<%@include file="inc_attribute_bulk_update_mode.jsp"%>
			<td>
				<c:set var="totalChecks" value="${attribute.numberOfOptions}"/>
				<bright:applicationSetting id="columns" settingName="checkbox-attribute-columns"/>
				<bright:applicationSetting id="limit" settingName="checkbox-attribute-single-list-limit"/>
				<c:set var="rowCount" value="${totalChecks/columns}"/>
				<c:set var="nextBreakPoint" value="${index+rowCount-1}"/>
				<table cellspacing="0" cellpadding="0" class="checklistCol"> 
				<logic:iterate name="attribute" property="listOptionValues" id="optionValue" indexId="checkIndex">
					<%@include file="../attribute-admin/inc_value_checker.jsp"%>
					<tr>
						<td style="padding:0; width:15px;">
							<input type="checkbox"  class="checkbox" name="attribute_<bean:write name='attribute' property='id'/>" id="<bean:write name='attribute' property='id'/>_<bean:write name='optionValue' property='id'/>" value="<bean:write name='optionValue' property='id'/>" <c:if test='${(optionValue.isSelected || hasValue)}'>checked="checked"</c:if>>
						</td>
						<td style="padding: 3px 0 0 2px">
							<label for="<bean:write name='attribute' property='id'/>_<bean:write name='optionValue' property='id'/>"><bean:write name='optionValue' property='value'/></label>
						</td>
					</tr>
					<c:if test="${(totalChecks > limit) && (nextBreakPoint > 0 && checkIndex >= nextBreakPoint)}">
					<c:set var="nextBreakPoint" value="${checkIndex+rowCount}"/>
					</table>
					<table cellspacing="0" cellpadding="0" class="checklistCol"> 
					</c:if>
				</logic:iterate>	
				</table>
				<c:if test="${attribute.isExtendableList}">
					<div style="clear:both; margin-bottom:1em; ">
						<label style="display:block; margin-bottom:0.2em;" for="field<bean:write name='attribute' property='id'/>"><bright:cmsWrite identifier="add-new-value" filter="false"/></label>
						<input type="text" class="text" name="attribute_additional_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>" value="<c:if test="${assetForm.hasErrors}"><bean:write name='attribute' property='value.value'/></c:if>">
					</div>
				</c:if>
			</td>
		</tr>
	</c:when>
	<c:when test="${attribute.isOptionList}">
		<%-- Option list, i.e. multi-select list--%>
		<tr>
			<th>
				<label for="field<bean:write name='attribute' property='id'/>"><bean:write name="attribute" property="label" filter="false"/><logic:notEmpty name="attribute" property="label">:</logic:notEmpty> </label>
				<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
				<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
				<%@include file="inc_attribute_required.jsp"%>
			</th>
			<td></td>
			<%@include file="inc_attribute_bulk_update_mode.jsp"%>
			<td>
				<select name="attribute_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>" size="5" multiple="multiple">
					<logic:iterate name="attribute" property="listOptionValues" id="optionValue">
						<%@include file="../attribute-admin/inc_value_checker.jsp"%>
						<option <c:if test="${(optionValue.isSelected || hasValue)}">selected="selected"</c:if> value="<bean:write name='optionValue' property='id'/>"><bean:write name='optionValue' property='value'/></option>	
					</logic:iterate>
				</select>
				<c:if test="${attribute.isExtendableList}">
					<br/>
					<bright:cmsWrite identifier="add-new-value" filter="false"/><br/>
					<input type="text" class="text" name="attribute_additional_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>" value="<c:if test="${assetForm.hasErrors}"><bean:write name='attribute' property='value.value'/></c:if>">
				</c:if>
			</td>
		</tr>
	</c:when>
	<%-- Keyword picker field --%>
	<c:when test="${attribute.isKeywordPicker && !editingFilter}">
		<tr>
			<th class="textarea">
				<label for="keywords_field"><bean:write name="attribute" property="label" filter="false"/>:</label>
				<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
				<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
				<%@include file="inc_attribute_required.jsp"%> 
			</th>
			<td></td>
			<%@include file="inc_attribute_bulk_update_mode.jsp"%>
			<td class="textarea">
				<table cellpadding="0" cellspacing="0" style="margin:0">
					<tr>
						<td>
							<input type="hidden" name="maxlength_attribute_<bean:write name='attribute' property='id'/>" value="4000">
							<input type="hidden" name="maxlengthmessage_attribute_<bean:write name='attribute' property='id'/>" value="<bean:write name='attribute' property='label'/> has exceeded its maximum length of 4000 characters"/>
							<textarea name="attribute_<bean:write name='attribute' property='id'/>" cols="40" rows="4" id="field<bean:write name='attribute' property='id'/>" <%@include file="inc_attribute_onchange.jsp"%>><logic:notEmpty name="attribute" property="value"><logic:notEmpty name="attribute" property="value.keywordCategories"><bean:size name="attribute" property="value.keywordCategories" id="noOfCategories"/><logic:iterate name="attribute" property="value.keywordCategories" id="category" indexId="index"><logic:notEmpty name="category" property="ancestors"><logic:iterate name="category" property="ancestors" id="ancestor"><bean:write name="ancestor" property="name" filter="false"/><c:out value="${keywordAncestorDelimiter}"/></logic:iterate></logic:notEmpty><bean:write name="category" property="name" filter="false"/><c:if test="${(index+1) < noOfCategories}"><bean:write name="keywordDelimiter"/></c:if></logic:iterate></logic:notEmpty></logic:notEmpty></textarea><br />
							
							<c:if test="${attribute.autoComplete && attribute.isVisible && autoCompleteEnabled}">
								<div id="autocomplete_field<bean:write name='attribute' property='id'/>" class="autocomplete"></div>
								<script type="text/javascript">
									<c:set var="searchOrEdit" value="edit" />
         						<%@include file="../inc/autocomplete_initialise.jsp"%>
         					</script>
							</c:if>
			
						</td>
						<td>
							<script type="text/javascript">
								<!-- 
									document.write('<input type="button" class="button" style="margin-bottom:5px; display:block; width:120px;" value="<bright:cmsWrite identifier='button-keyword-chooser' filter='false' />" onclick="openKeywordChooser(false, <c:out value='${attribute.treeId}'/>, <c:out value='${attribute.id}'/>, 0<c:if test='${attribute.defaultKeywordFilter != null && attribute.defaultKeywordFilter != ""}'>, \'<bean:write name="attribute" property="defaultKeywordFilter" filter="false"/>\'</c:if>); return false;" onkeypress="openKeywordChooser(false, <c:out value='${attribute.treeId}'/>, <c:out value='${attribute.id}'/>, 0); return false;" /> <c:if test="${!bOnlyAdminCanEditKeywordList || userprofile.isAdmin}"><input type="button" class="button" style="width:120px;" value="<bright:cmsWrite identifier='button-manage-master' filter='false' />" onclick="openMasterList(<c:out value='${attribute.treeId}'/><c:if test='${attribute.defaultKeywordFilter != null && attribute.defaultKeywordFilter != ""}'>, \'<bean:write name="attribute" property="defaultKeywordFilter" filter="false"/>\'</c:if>); return false;" onkeypress="openMasterList(<c:out value='${attribute.treeId}'/>); return false;" /></c:if>');
								-->
							</script>
						</td>
					</tr>
				</table>					
			</td>
		</tr>
	</c:when>
	
	<%-- Hyperlink field --%>
	<c:when test="${attribute.isHyperlink && (!supportMultiLanguage || !editingFilter)}">
		<tr>
			<th>
				<label for="field<bean:write name='attribute' property='id'/>"><bean:write name="attribute" property="label" filter="false"/><c:if test="${attribute.label != null && attribute.label != '' && attribute.label != ' '}">:</c:if> </label>
				<input type="hidden" id="baseUrl<bean:write name='attribute' property='id'/>" value="<bean:write name='attribute' property='baseUrl'/>"/>
				<input type="hidden" id="displayName<bean:write name='attribute' property='id'/>" value="<bean:write name='attribute' property='displayName'/>"/>
				<c:if test="${showNativeLanguages}"><span style="font-weight: normal;">(<bright:cmsWrite identifier="term-for-english" filter="false" />)</span></c:if>
				<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
				<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
				<%@include file="inc_attribute_required.jsp"%>
			</th>
			<td></td>
			<%@include file="inc_attribute_bulk_update_mode.jsp"%>
			<td>
				<textarea <c:if test="${attribute.width>0}">style="width:auto;"</c:if> class="text" rows="<c:out value="${attribute.height}"/>" cols="<c:out value="${attribute.width}"/>" name="attribute_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>" onkeyup="syncVerifyButton<bean:write name='attribute' property='id'/>(this);" onblur="syncVerifyButton<bean:write name='attribute' property='id'/>(this);" <%@include file="inc_attribute_onchange.jsp"%>><bean:write name='attribute' property='value.value' filter='false'/></textarea>
				<script language="JavaScript" type="text/javascript">
					//<!--
						function syncVerifyButton<c:out value='${attribute.id}'/>(ctrl) { document.getElementById('verify<c:out value='${attribute.id}'/>').disabled=!(ctrl.value.length>0); }
						document.write('<br/><input class="button flush" type="button" id="verify<c:out value='${attribute.id}'/>" value="<bright:cmsWrite identifier="label-verify-links" filter="false"/>" onclick="validateUrl(field<bean:write name='attribute' property='id'/>,baseUrl<bean:write name='attribute' property='id'/>,displayName<bean:write name='attribute' property='id'/>)">');
						document.getElementById('verify<c:out value='${attribute.id}'/>').disabled=!(document.getElementById('field<bean:write name='attribute' property='id'/>').value.length>0);
					//-->
				</script>
			</td>
		</tr>
		
		<%-- Output any translations specific to this attribute value if they exist --%>
		<c:if test="${not empty attributeValue.translations}">
			<logic:iterate name="attributeValue" property="translations" id="translation" indexId="tIndex">
				<tr>
					<th class="translation">
						<%@include file="inc_translated_attribute_label.jsp"%>
						<c:set var="helpText" value="${translation.attributeTranslation.helpText}" scope="request"/>
						<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
					</th>
					<td>&nbsp;</td>
					<td>
						<input type="text" class="text" name="attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" value="<bean:write name='translation' property='value' filter='false'/>" id="field<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>">
					</td>
				</tr>
			</logic:iterate>
		</c:if>
		<%-- If the value doesn't have any translations (because it doesn't really exist yet) use translation in the attribute itself --%>
		<c:if test="${empty attributeValue.translations && not empty attribute.translations}">
			<logic:iterate name="attribute" property="translations" id="translation" indexId="tIndex">
				<tr>
					<th class="translation">
						<%@include file="inc_translated_attribute_label.jsp"%>
						<c:set var="helpText" value="${translation.helpText}" scope="request"/>
						<jsp:include page="../asset-admin/inc_attribute_help.jsp"/> 
					</th>
					<td>&nbsp;</td>
					<td>
						<input type="text" class="text" name="attribute_<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>" value="" id="field<bean:write name='attribute' property='id'/>_<bean:write name="translation" property="language.id"/>">
					</td>
				</tr>
			</logic:iterate>
		</c:if>
		
	</c:when>


	<c:when test="${attribute.isAutoincrement && (!supportMultiLanguage || !editingFilter)}">
		<%-- Autoincrement field --%>
		<c:choose>
			<c:when test="${bIsBulkUpdate}">
				<input type="hidden" name="update_<c:out value='${attribute.id}'/>" value="skip"/>
			</c:when>
			<c:when test="${uploading && sIsImport}">
				<input type="hidden" name="attribute_<bean:write name='attribute' property='id'/>" value="-1"/>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${!attribute.readOnly}">
						<tr>
							<th>
								<label for="field<bean:write name='attribute' property='id'/>"><bean:write name="attribute" property="label" filter="false"/><c:if test="${attribute.label != null && attribute.label != '' && attribute.label != ' '}">:</c:if> </label>
								<c:if test="${showNativeLanguages}"><span style="font-weight: normal;">(<bright:cmsWrite identifier="term-for-english" filter="false" />)</span></c:if>
								<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
								<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
								<%@include file="inc_attribute_required.jsp"%>
							</th>
							<td></td>
							<%@include file="inc_attribute_bulk_update_mode.jsp"%>
							<td>
								<%--  Show the value: this will either be the default next autoinc for new assets, or current for existing --%>
								<input type="text" class="text" name="attribute_<bean:write name='attribute' property='id'/>" value="<bean:write name='attributeValue' property='value'/>" id="field<bean:write name='attribute' property='id'/>" <%@include file="inc_attribute_onchange.jsp"%>>	
							</td>
						</tr>
					</c:when>
					<c:when test="${not empty attribute.value.value}">
						<input type="hidden" name="attribute_<bean:write name='attribute' property='id'/>" value="<bean:write name='attribute' property='value.value'/>"/>
						<tr>
							<th>
								<label for="field<bean:write name='attribute' property='id'/>"><bean:write name="attribute" property="label" filter="false"/><c:if test="${attribute.label != null && attribute.label != '' && attribute.label != ' '}">:</c:if> </label>
							</th>
							<td></td>
							<c:if test="${bIsBulkUpdate}"><td></td><td></td></c:if>
							<td style="vertical-align: middle;"><bean:write name='attributeValue' property='value'/></td>
						</tr>		
					</c:when>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:when>

	<%-- External dictionary field --%>
	<c:when test="${attribute.isExternalDictionary && !editingFilter && !supportMultiLanguage}">
		<tr>
			<th class="textarea">
				<label for="field_additional<bean:write name='attribute' property='id'/>"><bean:write name="attribute" property="label" filter="false"/>:</label>
				<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
				<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
				<%@include file="inc_attribute_required.jsp"%> 
			</th>
			<td></td>
			<%@include file="inc_attribute_bulk_update_mode.jsp"%>
			<td class="textarea">
				<table cellpadding="0" cellspacing="0" style="margin:0">
					<tr>
						<td>
							<input type="hidden" name="maxlength_attribute_<bean:write name='attribute' property='id'/>" value="4000">
							<input type="hidden" name="maxlengthmessage_attribute_<bean:write name='attribute' property='id'/>" value="<bean:write name='attribute' property='label'/> has exceeded its maximum length of 4000 characters"/>
							<textarea readonly="true" onclick="<c:out value='${attribute.buttonScript}' escapeXml='false' />" name="attribute_additional_<bean:write name='attribute' property='id'/>" cols="40" rows="4" id="field_additional<bean:write name='attribute' property='id'/>" ><c:out value='${attributeValue.additionalValue}'/></textarea><br/>
							<input type="hidden" name="attribute_<bean:write name='attribute' property='id'/>" id="field<bean:write name='attribute' property='id'/>" value="<c:out value='${attributeValue.value}'/>" />
						</td>
						<td>
							<input type="button" class="button" style="margin-bottom:5px; display:block; width:120px;" value="<bright:cmsWrite identifier='button-dictionary-chooser' filter='false' />" onclick="<c:out value='${attribute.buttonScript}' escapeXml='false' />" onkeypress="<c:out value='${attribute.buttonScript}' escapeXml='false' />" /> 
						</td>
					</tr>
				</table>					
			</td>
		</tr>
	</c:when>
	
	<c:when test="${attribute.isNumeric && (!supportMultiLanguage || !editingFilter)}">
		<tr>
			<th>
				<label for="field<bean:write name='attribute' property='id'/>"><bean:write name="attribute" property="label" filter="false"/><logic:notEmpty name="attribute" property="label">:</logic:notEmpty> </label>
				<c:set var="helpText" value="${attribute.helpText}" scope="request"/>
				<jsp:include page="../asset-admin/inc_attribute_help.jsp"/>
				<%@include file="inc_attribute_required.jsp"%>
			</th>
			<td></td>
			<%@include file="inc_attribute_bulk_update_mode.jsp"%>
			<td>
				<input type="text" class="text" name="attribute_<bean:write name='attribute' property='id'/>" value='<%@include file="inc_display_value.jsp"%>' id="field<bean:write name='attribute' property='id'/>" maxlength="<c:if test="${attribute.isNumeric}">38</c:if>">
			</td>
		</tr>
	</c:when>
	
	
</c:choose>
