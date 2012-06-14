<%-- 
	Renders the input control for an attribute value for the search builder

	History:
	 d1		James Home		30-Sep-2008		Created.
--%>		

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="databaseSupportsUTF8" settingName="databaseSupportsUTF8"/>

<c:if test="${databaseSupportsUTF8}">
	<% response.setContentType("text/html;charset=utf-8"); %>
</c:if>

<bright:applicationSetting id="daysBeforeMonths" settingName="days-before-months" />
<bright:applicationSetting id="dateFormatHelpString" settingName="date-format-help-string" />
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="mainKeywordChooserId" settingName="main-keyword-chooser-id" />
<bright:applicationSetting id="useKeywordChooser" settingName="keyword-search-chooser" />
<bright:applicationSetting id="autoCompleteEnabled" settingName="auto-complete-enabled"/>
<bright:applicationSetting id="autoCompleteQuickSearchEnabled" settingName="auto-complete-quick-search-enabled"/>

<c:set var="attribute" value="${attributeForm.attribute}"/>
<bean:define id="attributeId" name="attribute" property="id"/>
<bean:parameter id="index" name="index" value="0"/>

<%-- Remove this - not sure why I put it here
<input type="hidden" name="clauses[<c:out value="${index}"/>].attributeId" value="<c:out value="${attributeId}"/>"/>
--%>

<input type="hidden" id="clauses[<c:out value="${index}"/>].valueAttributeId" value="<c:out value="${attributeId}"/>"/>
<input type="hidden" name="clauses[<c:out value="${index}"/>].delimiterIsSpace" value="<c:out value="${attribute.delimiterIsSpace}"/>"/>
<input type="hidden" name="clauses[<c:out value="${index}"/>].tokenDelimiterRegex" value="<c:out value="${attribute.tokenDelimiterRegex}"/>"/>

<c:choose>
	<%-- Agreement type--%>
	<c:when test="${attribute.static && attribute.id==400}">
		<select name="clauses[<c:out value="${index}"/>].value" id="field<bean:write name='attribute' property='id'/>" class="selectValue">
			<option value="" <c:if test="${attributeForm.value == ''}">selected</c:if>>- <bright:cmsWrite identifier="snippet-select" filter="false"/> -</option>	
			<option value="1" <c:if test="${attributeForm.value == '1'}">selected</c:if>><bright:cmsWrite identifier="snippet-unrestricted" filter="false"/></option>	
			<option value="2" <c:if test="${attributeForm.value == '2'}">selected</c:if>><bright:cmsWrite identifier="snippet-agreement-applies" filter="false"/></option>
			<option value="3" <c:if test="${attributeForm.value == '3'}">selected</c:if>><bright:cmsWrite identifier="snippet-restricted" filter="false"/></option>
		</select>
	</c:when>
	<%-- Completeness --%>
	<c:when test="${attribute.static && attribute.id==-3}">
		<select name="clauses[<c:out value="${index}"/>].value" id="field<bean:write name='attribute' property='id'/>" class="selectValue">
			<option value="" <c:if test="${empty attributeForm.value}">selected</c:if>>- <bright:cmsWrite identifier="snippet-select" filter="false"/> -</option>	
			<option value="1" <c:if test="${attributeForm.value == '1'}">selected</c:if>><bright:cmsWrite identifier="snippet-complete" filter="false"/></option>
			<option value="0" <c:if test="${attributeForm.value == '0'}">selected</c:if>><bright:cmsWrite identifier="snippet-incomplete" filter="false"/></option>
		</select>
		<c:if test="${not userprofile.currentLanguage.default}">
			<span style="padding-left: 6px;"><bright:cmsWrite identifier="snippet-applies-to-language" filter="false"/>
		</c:if>
	</c:when>
	<%-- Approval --%>
	<c:when test="${attribute.static && attribute.id==-2}">
		<select name="clauses[<c:out value="${index}"/>].value" id="field<bean:write name='attribute' property='id'/>" class="selectValue">
			<option value="" <c:if test="${empty attributeForm.value}">selected</c:if>>- <bright:cmsWrite identifier="snippet-select" filter="false"/> -</option>	
			<option value="1" <c:if test="${attributeForm.value == '1'}">selected</c:if>><bright:cmsWrite identifier="snippet-unapproved" filter="false"/></option>
			<option value="2" <c:if test="${attributeForm.value == '2'}">selected</c:if>><bright:cmsWrite identifier="snippet-partial-approval" filter="false"/></option>
			<option value="3" <c:if test="${attributeForm.value == '3'}">selected</c:if>><bright:cmsWrite identifier="snippet-approved" filter="false"/></option>
		</select>
	</c:when>
	<%-- Sensitivity --%>
	<c:when test="${attribute.static && attribute.id==300}">
		<select name="clauses[<c:out value="${index}"/>].value" id="field<bean:write name='attribute' property='id'/>" class="selectValue">
			<option value="" <c:if test="${empty attributeForm.value}">selected</c:if>>- <bright:cmsWrite identifier="snippet-select" filter="false"/> -</option>	
			<option value="1" <c:if test="${attributeForm.value == '1'}">selected</c:if>><bright:cmsWrite identifier="snippet-yes" filter="false"/></option>
			<option value="0" <c:if test="${attributeForm.value == '0'}">selected</c:if>><bright:cmsWrite identifier="snippet-no" filter="false"/></option>
		</select>
	</c:when>
	<%-- Rating --%>
	<c:when test="${attribute.static && attribute.id==600}">
		<input type="hidden" name="clauses[<c:out value="${index}"/>].numeric" value="true"/>
		<input type="hidden" name="clauses[<c:out value="${index}"/>].numericIsWholeNumber" value="true"/>
		<bright:applicationSetting id="maxRating" settingName="max-rating"/>
		<select name="clauses[<c:out value="${index}"/>].value" size="1" id="field<bean:write name='attribute' property='id'/>" class="selectValue">
			<option value="-1" <c:if test='${attributeForm.value == ""}'>selected</c:if>>- <bright:cmsWrite identifier="snippet-select" filter="false"/> -</option>
			<c:forEach begin="0" end="${maxRating}" var="ratingValue">
				<option value="<bean:write name='ratingValue'/>" <c:if test='${not empty attributeForm.value && attributeForm.value == ratingValue}'>selected</c:if>><bean:write name='ratingValue'/></option>
			</c:forEach>	
		 </select>
	</c:when>
	<%-- Orientation --%>
	<c:when test="${attribute.static && attribute.id==13}">
		<select name="clauses[<c:out value="${index}"/>].value" id="field<bean:write name='attribute' property='id'/>" class="selectValue">
			<option value="" <c:if test="${empty attributeForm.value}">selected</c:if>>- <bright:cmsWrite identifier="snippet-select" filter="false"/> -</option>
			<option value="1" <c:if test="${attributeForm.value == '1'}">selected</c:if>><bright:cmsWrite identifier="snippet-landscape" filter="false"/></option>
			<option value="2" <c:if test="${attributeForm.value == '2'}">selected</c:if>><bright:cmsWrite identifier="snippet-portrait" filter="false"/></option>
			<option value="3" <c:if test="${attributeForm.value == '3'}">selected</c:if>><bright:cmsWrite identifier="snippet-square" filter="false"/></option>
		</select>
	</c:when>
	<c:when test="${attribute.isTextfield || attribute.isTextarea || attribute.isHyperlink || attribute.isAutoincrement}">
		<%-- Text field --%>
		<input type="text" name="clauses[<c:out value="${index}"/>].value" id="field<bean:write name='attribute' property='id'/>" value="<bean:write name="attributeForm" property="value"/>" size="30"  /><br />
		<c:if test="${attribute.autoComplete && attribute.isVisible && autoCompleteEnabled}">
			<script type="text/javascript">
				<c:set var="searchOrEdit" value="search" />
         		<%@include file="../inc/autocomplete_initialise.jsp"%>
			</script>
		</c:if>
	</c:when>
	<c:when test="${attribute.isDatepicker || attribute.isDateTime}">
		<input type="hidden" name="clauses[<c:out value="${index}"/>].date" value="true"/>
		<input type="text" size="17" maxlength="20" class="small date" name="clauses[<c:out value="${index}"/>].value" value="<bean:write name="attributeForm" property="value"/>">&nbsp;(<c:out value="${dateFormatHelpString}" />)  
		<script type="text/JavaScript">
			initDatePicker();
		</script>
	</c:when>
	<c:when test="${attribute.isDropdownList || attribute.isCheckList || attribute.isOptionList}">
		<%-- Dropdown list--%>
		<select name="clauses[<c:out value="${index}"/>].value" id="field<bean:write name='attribute' property='id'/>" value="<bean:write name="attributeForm" property="value"/>" class="selectValue">
			<option value="">- <bright:cmsWrite identifier="snippet-select" filter="false"/> -</option>
			<logic:iterate name="attribute" property="listOptionValues" id="optionValue">
				<option value="<bean:write name='optionValue' property='value' filter='false'/>" <c:if test="${attributeForm.value == optionValue.value}">selected</c:if>><bean:write name='optionValue' property='value' filter='false'/></option>
			</logic:iterate>
			<option value="isempty">[<bright:cmsWrite identifier="snippet-none" filter="false"/>]</option>
		</select>
		<input type="hidden" name="clauses[<c:out value="${index}"/>].listAttribute" value="true"/>
	</c:when>
	<c:when test="${attribute.isKeywordPicker}">
		<input type="text" name="clauses[<c:out value="${index}"/>].value" id="field<bean:write name='attribute' property='id'/><c:if test="${index>0}">_<c:out value='${index}'/></c:if>" size="30" styleClass="text" value="<bean:write name="attributeForm" property="value"/>" />
		<c:if test="${useKeywordChooser}">
			&nbsp;<a href="javascript:;" onclick="openKeywordChooser(true, <c:out value='${attribute.treeId}'/>, <c:out value='${attribute.id}'/>, <c:out value='${index}'/><c:if test='${attribute.defaultKeywordFilter != null && attribute.defaultKeywordFilter != ""}'>, '<bean:write name="attribute" property="defaultKeywordFilter" />'</c:if>); return false;" onkeypress="openKeywordChooser(true, <c:out value='${attribute.treeId}'/>, <c:out value='${attribute.id}'/>, <c:out value='${index}'/><c:if test='${attribute.defaultKeywordFilter != null && attribute.defaultKeywordFilter != ""}'>, '<bean:write name="attribute" property="defaultKeywordFilter" />'</c:if>); return false;" title="Popup keyword selector"><img src="../images/standard/icon/keyword_chooser.gif" class="help" alt="Choose Keywords" height="15" width="15" border="0" /></a> 
		</c:if>
	</c:when>	
	<c:when test="${attribute.isNumeric}">
		<input type="hidden" name="clauses[<c:out value="${index}"/>].numeric" value="true"/>
		<c:if test="${attribute.isNumeric && attribute.maxDecimalPlaces==0}">
			<input type="hidden" name="clauses[<c:out value="${index}"/>].numericIsWholeNumber" value="true"/>
		</c:if>
		<input type="text" size="17" maxlength="38" id="field<bean:write name='attribute' property='id'/>_<c:out value='${index}'/>" class="small" name="clauses[<c:out value="${index}"/>].value" value="<bean:write name="attributeForm" property="value"/>">
	</c:when>
	<c:when test="${attribute.isSpatialArea}">
		<input type="hidden" name="clauses[<c:out value="${index}"/>].spatialArea" value="true"/>
		<c:if test="${attribute.maxDecimalPlaces==0}">
			<input type="hidden" name="clauses[<c:out value="${index}"/>].numericIsWholeNumber" value="true"/>
		</c:if>
		<input type="text" size="17" maxlength="38" id="field<bean:write name='attribute' property='id'/>_<c:out value='${index}'/>" class="small" name="clauses[<c:out value="${index}"/>].value" value="<bean:write name="attributeForm" property="value"/>">
	</c:when>				
	<c:otherwise>
		<c:if test="${attribute.id==2 || attribute.id==12 || attribute.id==14}">
			<input type="hidden" name="clauses[<c:out value="${index}"/>].numeric" value="true"/>
			<input type="hidden" name="clauses[<c:out value="${index}"/>].numericIsWholeNumber" value="true"/>
		</c:if>
		<c:if test="${attribute.id==14}">&pound;</c:if>
		<input type="text" name="clauses[<c:out value="${index}"/>].value" id="field<bean:write name='attribute' property='id'/>_<c:out value='${index}'/>" value="<bean:write name="attributeForm" property="value"/>" size="30" />
		
		<c:if test="${attribute.id==12}"> Kb</c:if>
		<c:if test="${(attribute.id==0) && autoCompleteQuickSearchEnabled}">
			<script type="text/javascript">
				//BB-AutoComplete - hide this if quicksearch autocomplete disabled!
				initJQAutocompleter($j("#field<bean:write name='attribute' property='id'/>_<c:out value='${index}'/>"),"","","search"); 
			</script>
		
		</c:if>
	</c:otherwise>
</c:choose>





