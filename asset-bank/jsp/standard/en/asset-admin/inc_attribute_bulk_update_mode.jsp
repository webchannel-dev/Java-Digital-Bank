<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="showPrependOption" settingName="show-prepend-option"/>

<c:if test="${bIsBulkUpdate}">
	<td style="padding-right:4px;">
		<select name="update_<c:out value='${attribute.id}'/>" id="update_<c:out value='${attribute.id}'/>" style="width:auto;">
			<option value="skip">- <bright:cmsWrite identifier="snippet-skip" filter="false" /> -</option>
			<option value="replace" <logic:equal name='assetForm' property="updateType(${attribute.id})" value="replace">selected</logic:equal>><bright:cmsWrite identifier="snippet-replace" filter="false" /></option>
			<c:if test="${not (attribute.isFile || attribute.isDatepicker || attribute.isDateTime || attribute.isDropdownList || attribute.isHyperlink || attribute.isNumeric || attribute.isSpatialArea)}">
				<option value="append" <logic:equal name='assetForm' property="updateType(${attribute.id})" value="append">selected</logic:equal>><bright:cmsWrite identifier="snippet-append" filter="false" /></option>
				<c:if test="${showPrependOption}">
				    <option value="prepend" <logic:equal name='assetForm' property="updateType(${attribute.id})" value="prepend">selected</logic:equal>><bright:cmsWrite identifier="snippet-prepend" filter="false" /></option>
				</c:if>
			</c:if>
			<%-- if it's a keyword picker then give the option to remove the keyword --%>
			<c:if test="${attribute.isKeywordPicker}">
				<option value="remove" <logic:equal name='assetForm' property="updateType(${attribute.id})" value="remove">selected</logic:equal>><bright:cmsWrite identifier="snippet-remove" filter="false"/></option>
			</c:if>
		</select>
	</td>
	<td style="padding-right:4px;">
		<c:if test="${not (attribute.isFile || attribute.isDatepicker || attribute.isDateTime || attribute.isDropdownList || attribute.isCheckList || attribute.isOptionList || attribute.isHyperlink || attribute.isKeywordPicker || attribute.isNumeric || attribute.isSpatialArea)}">
			<select name="delimiter_<c:out value='${attribute.id}'/>" id="delimiter_<c:out value='${attribute.id}'/>" style="width:auto;">
				<option value="">- No delimiter -</option>
				<option value=" " <logic:equal name='assetForm' property="delimType(${attribute.id})" value=" ">selected</logic:equal>>' ' (space)</option>
				<option value="," <logic:equal name='assetForm' property="delimType(${attribute.id})" value=",">selected</logic:equal>>, (comma)</option>
			</select>
		</c:if>
	</td>
</c:if>