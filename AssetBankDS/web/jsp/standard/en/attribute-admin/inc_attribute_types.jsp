<c:choose>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_TextField_Long}">
	Text (long)
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_TextArea_Long}">
	Text area (long)
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_TextField_Short}">
	Text
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_TextArea_Short}">
	Text area
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_DatePicker}">
	Date
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_Dropdown}">
	Dropdown
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_CheckList}">
	Check list
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_OptionList}">
	Option list
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_KeywordPicker}">
	Keyword
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_DateTime}">
	Datetime
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_Hyperlink}">
	Hyperlink
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_GroupHeading}">
	Group Heading
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_Autoincrement}">
	Autoincrement
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_ExternalDictionary}">
	External dictionary 
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_DataLookupButton}">
	Data lookup button  
</c:when>
<c:when test="${attribute.typeId==attributeConstants.k_lAttributeType_Numeric}">
	Numeric
</c:when>
<c:otherwise>
	Standard
</c:otherwise>
</c:choose>