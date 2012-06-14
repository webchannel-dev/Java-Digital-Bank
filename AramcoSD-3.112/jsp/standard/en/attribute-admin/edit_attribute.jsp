<%@ page import="java.util.Iterator" %>
<%@ page import="com.bright.assetbank.attribute.constant.ACAttributeConstants" %>
<%@ page import="com.bright.assetbank.attribute.constant.AttributeConstants" %>
<%@ page import="com.bright.assetbank.application.constant.AssetBankConstants" %>
<%@ page import="com.bright.assetbank.application.constant.AssetBankSettings" %>
<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		25-Oct-2005		Created
	 d2		Ben Browning		22-Feb-2006		HTML/CSS tidy up
	 d3		James Home			02-Feb-2007		Added ability to choose initial visibility
	 d4		Matt Stevenson		14-Mar-2007		Modified to hide unwanted fields for keyword picker
	 d5		Matt Stevenson		01-Nov-2007		Added DefaultKeywordFilter
	 d6		Matt Woollard		14-Jan-2008		Added ishtml
	 d7		Francis Devereux	30-Jan-2009		Allow attributes to be mandatory when doing bulk upload
	 d8		Steve Bryan			28-May-2009		Add prefix and width for autoincrement
	 d9     Matt Woollard       14-Jul-2009     Added help text for new default date attribute options
	 d10    Francis Devereux	08-Sep-2009     Added auto complete flag to attributes
	 d11    Francis Devereux	02-Oct-2009     Show Keyword delimiter regex for keyword pickers, and don't hide it when "Keyword search links on view" is off
	 d12    Francis Devereux	02-Oct-2009     Don't show Keyword delimiter regex for keyword pickers
	 d13    Francis Devereux	13-Oct-2009     Use attribute type constants instead of hard coded numbers
	 d14    Francis Devereux	02-Nov-2009     JavaScript refactoring - created attributeTypeChanged()
	 d15	Francis Devereux	29-Oct-2009		Added new attribute types: short textfield and textarea
	 d16		Steve Bryan			22-Feb-2011		Add Spatial Area attribute and factor out use of nonKeywordsFields more nicely 
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="hideIncompleteAssets" settingName="hide-incomplete-assets"/>
<bright:applicationSetting id="allowSearchByCompleteness" settingName="allow-search-by-completeness"/>
<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>
<bright:applicationSetting id="searchBuilderEnabled" settingName="search-builder-enabled"/>
<bright:applicationSetting id="assetEntitiesEnabled" settingName="asset-entities-enabled"/>
<bright:applicationSetting id="useParentMetadata" settingName="include-metadata-from-parents-for-search"/>
<bright:applicationSetting id="autoCompleteEnabled" settingName="auto-complete-enabled"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Attributes</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="attributes"/>
	<bean:define id="pagetitle" value="Attributes"/>
	<script type="text/javascript">
	<!--
	<%--
	 * Called when the attribute type is changed.
	 * ctrl should be the select element containing the attribute type options
	 --%>
	function attributeTypeChanged(ctrl)
	{
		var attributeTypeId = ctrl.value;
		updateDefaultValues(attributeTypeId);
		setDefaultVisibility(attributeTypeId);
		
		showRelevantFieldsForType(attributeTypeId);
	}
	
	
	<%--
	 * Called on every page load - may be new or edited attribute
	 --%>
	function showRelevantFieldsOnLoad()
	{
		var ctrl = document.getElementById('type');
		
		<%-- If we are editing then get the type id from the form, else from the control --%>
		<c:choose>
			<c:when test='${attributeForm.attribute.id gt 0}'>
				<%--  Editing attribute --%>
				showRelevantFieldsForType(<c:out value='${attributeForm.attribute.typeId}'/>);
			</c:when>
			<c:otherwise>
				<%--  Page loaded for new attribute --%>
				setDefaultVisibility(ctrl.value);
				showRelevantFieldsForType(ctrl.value);
			</c:otherwise>
		</c:choose>
	}
	
	
	// Called from both attributeTypeChanged and body.onload 
	function showRelevantFieldsForType(attributeTypeId)
	{	
		showMaxDisplay(attributeTypeId);
		showDefault(attributeTypeId);
		showInputMask(attributeTypeId);
		showIncrementDisplay(attributeTypeId);
		showNumDecimalPlacesDisplay(attributeTypeId);
		showSinglePointOnlyDisplay(attributeTypeId);
		showExternalDataSource(attributeTypeId);
		showGeneralAttributeFields(attributeTypeId);
		showAutoCompletePlugin(attributeTypeId);
		showGloballyUnique(attributeTypeId);
		showDateFormatType(attributeTypeId);
	}
	
	// lots of errors were being caused by changing the style on a non-existant element 
	// this method ensured the element has been created before changing it's visibility. 
	function changeStyleDisplay(elementName, state)
	{
		if(document.getElementById(elementName))
		{
			document.getElementById(elementName).style.display = state;
		}
	}
	
	function showDateFormatType(attributeTypeId)
	{
		if (attributeTypeId == <%= AttributeConstants.k_lAttributeType_DatePicker %>)
		{
			changeStyleDisplay('dateFormatRow', "block");
		}
		else 
		{
			changeStyleDisplay('dateFormatRow', "none");
		}
	}
	
	function showAutoCompletePlugin( attributeTypeId )
	{
		<%-- the element autoCompletePluginRow will not be present if there is not options available --%>
		
		if( document.getElementById('autoCompletePluginRow') != null )
		{
			var isTextField = ! attributeTypeId ||
							  ( attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextField_Long %> ||
						        attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextArea_Long %> ||
						        attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextField_Short %> ||
						        attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextArea_Short %> );
			
			if( document.getElementById('autoComplete').checked == true &&
				isTextField )
			{
				changeStyleDisplay('autoCompletePluginRow', "block");
			}
			else 
			{
				changeStyleDisplay('autoCompletePluginRow', "none");
			}
		}
	}

	function showGloballyUnique(attributeTypeId)
	{
		if (attributeTypeId == <%= AttributeConstants.k_lAttributeType_KeywordPicker %>)
		{
			changeStyleDisplay('isGloballyUnique', "block");
		}
		else 
		{
			changeStyleDisplay('isGloballyUnique', "none");
		}
	}

	<%--
	 * Changes field values that have different defaults depending on the attribute type
	 --%>
 	function updateDefaultValues(attributeTypeId)
	{
	    <%--
		 * If attribute type is changed to textarea (short) and the max length
		 * is set to the default for textarea (long) then change the max
		 * length to the maximum for textarea (short).
		 *
		 * If we didn't do this then users who selected textarea (short) and
		 * didn't change the max length would get a validation error because the
		 * max length would be the default for textarea (long), 4000, which is
		 * more than the maximum length of a textarea (short).
		 *
		 * We don't change the maxSize if it is not the default so that if the
		 * user has explicitly changed it their changes will not be overwritten. 
		 --%>
		if (attributeTypeId == <%= AttributeConstants.k_lAttributeType_TextArea_Short %>)
		{
			var maxSizeCtrl = document.getElementById('maxSize');
			if (maxSizeCtrl.value == "<%= AssetBankConstants.k_iDefaultMaxTextareaLength %>")
			{
				maxSizeCtrl.value = "<%= AssetBankSettings.getMaxVarcharAttributeLength() %>";
			}
		}
		<%--
		 * Do the opposite of the above, i.e. change the max length back to
		 * 4000 if the type is changed to textarea (long) after being changed
		 * to textarea (short).
		 --%>
		else if (attributeTypeId == <%= AttributeConstants.k_lAttributeType_TextArea_Long %>)
		{
			var maxSizeCtrl = document.getElementById('maxSize');
			if (maxSizeCtrl.value == "<%= AssetBankSettings.getMaxVarcharAttributeLength() %>")
			{
				maxSizeCtrl.value = "<%= AssetBankConstants.k_iDefaultMaxTextareaLength %>";
			}
		}

		// Numeric and spatial area are not keywordSearchable by default, rest are
		if(attributeTypeId == <%= AttributeConstants.k_lAttributeType_Numeric %> ||
			attributeTypeId == <%= AttributeConstants.k_lAttributeType_SpatialArea %> )
		{
			document.getElementById('keywordSearchable').checked='';
		}
		else
		{
			document.getElementById('keywordSearchable').checked='checked';
		}
		
		// Hide for sort set true for data lookup button and spatial area (then row is hidden), so effectively forced true
		if(attributeTypeId == <%= AttributeConstants.k_lAttributeType_DataLookupButton %> || 
			attributeTypeId == <%= AttributeConstants.k_lAttributeType_SpatialArea %> )
		{
			document.getElementById('hideForSort').checked='checked';
		}
		else
		{
			document.getElementById('hideForSort').checked='';
		}
		
		// Also for Spatial Area, set the default max decimals to 4, otherwise 0
		// 4 decimal places at the equator is accurate to 11m at the equator. Accuracy increases away from the equator.
		if(attributeTypeId == <%= AttributeConstants.k_lAttributeType_SpatialArea %> )
		{
			document.getElementById('maxDecimals').selectedIndex = 4;
		}
		else
		{
			document.getElementById('maxDecimals').selectedIndex = 0;
		}
		
	}

	// This function is only called for new attributes
	function setDefaultVisibility(attributeTypeId)
	{
		if(attributeTypeId==<%= AttributeConstants.k_lAttributeType_Dropdown %> ||
		   attributeTypeId==<%= AttributeConstants.k_lAttributeType_CheckList %> ||
		   attributeTypeId==<%= AttributeConstants.k_lAttributeType_OptionList %>)
		{
			changeStyleDisplay('notVisibleField', "none");
			changeStyleDisplay('notVisibleTranslation', "none");
			changeStyleDisplay('defaultField', "none");
			changeStyleDisplay('defaultValueTranslation', "none");
			changeStyleDisplay('defaultText', "block");
			changeStyleDisplay('notVisibleText', "block");
		}
		else
		{
			changeStyleDisplay('notVisibleField', "block");
			changeStyleDisplay('notVisibleTranslation', "block");
			changeStyleDisplay('defaultField', "block");
			changeStyleDisplay('defaultValueTranslation', "block");
			changeStyleDisplay('defaultText', "none");
			changeStyleDisplay('notVisibleText', "none");
		}
		
		if(document.getElementById('translationRequiredField')!=null)
		{
			if(attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextField_Long %> ||
			   attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextArea_Long %> ||
			   attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextField_Short %> ||
			   attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextArea_Short %>)
			{
				changeStyleDisplay('translationRequiredField', "block");
				changeStyleDisplay('translationRequiredText', "none");
			}
			else
			{
				changeStyleDisplay('translationRequiredField', "none");
				changeStyleDisplay('translationRequiredText', "block");
			}
		}
		
		if(document.getElementById('searchTokenFields')!=null)
		{
			if(attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextField_Long %> ||
			   attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextArea_Long %> ||
			   attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextField_Short %> ||
			   attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextArea_Short %> ||
			   attributeTypeId==<%= AttributeConstants.k_lAttributeType_DatePicker %> ||
			   attributeTypeId==<%= AttributeConstants.k_lAttributeType_Dropdown %> ||
			   attributeTypeId==<%= AttributeConstants.k_lAttributeType_CheckList %> ||
			   attributeTypeId==<%= AttributeConstants.k_lAttributeType_OptionList %>)
			{
				changeStyleDisplay('searchTokenFields', "block");
				<c:if test="${assetEntitiesEnabled}">
					changeStyleDisplay('dataPropagation', "block");
				</c:if>
			}
			else
			{
				document.getElementById('searchTokenFields', "none");
				<c:if test="${assetEntitiesEnabled}">
					changeStyleDisplay('dataPropagation', "none");
				</c:if>
			}
		}
		
	<c:if test="${autoCompleteEnabled}">
		if(
		<%
		for(Iterator<Long> it = ACAttributeConstants.k_allowedAttributeTypeIds.iterator(); it.hasNext(); )
		{
  			long attributeTypeId = it.next().longValue();
  			out.print("attributeTypeId==" + attributeTypeId);
  			if (it.hasNext())
  			{
  				out.print(" || ");
			}
		}
		%>
		)
		{
			changeStyleDisplay('autoCompleteField',"block");
		}
		else
		{
			changeStyleDisplay('autoCompleteField',"none");
		}
	</c:if>

		// TODO - not needed to call from here? showIncrementDisplay(attributeTypeId);

		// show token delimiter regex for text fields (inc hyperlinks)
		if(attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextField_Long %> ||
		   attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextArea_Long %> ||
		   attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextField_Short %> ||
		   attributeTypeId==<%= AttributeConstants.k_lAttributeType_TextArea_Short %> ||
		   attributeTypeId==<%= AttributeConstants.k_lAttributeType_Hyperlink %>)
		{
			changeStyleDisplay('tokenDelimiterFields',"block");
		}
		else
		{
			changeStyleDisplay('tokenDelimiterFields',"none");
		}

	}


	function showMaxDisplay (value)
	{
		changeStyleDisplay('maxDisplayRow', 'none'); 
		changeStyleDisplay('isHtml', 'none'); 
		changeStyleDisplay('onchangeField', 'block');
		changeStyleDisplay('keywordFields', 'none');
		changeStyleDisplay('displayName', 'none');
		changeStyleDisplay('baseUrl', 'none');
		changeStyleDisplay('altText', 'none');
		changeStyleDisplay('textAreaFields', 'none');
		changeStyleDisplay('textAreaOrKPFields', 'none');
		changeStyleDisplay('extendableListField', "none");
		changeStyleDisplay('defaultDateInstructions', "none");
		
		
		if (value == <%= AttributeConstants.k_lAttributeType_TextArea_Long %> ||
			value == <%= AttributeConstants.k_lAttributeType_TextArea_Short %>)
		{
			changeStyleDisplay('maxDisplayRow', 'block'); 
			changeStyleDisplay('isHtml', 'block'); 
			changeStyleDisplay('textAreaFields', 'block');
			changeStyleDisplay('textAreaOrKPFields', 'block');
		} 
		else if (value == <%= AttributeConstants.k_lAttributeType_KeywordPicker %>)
		{
			changeStyleDisplay('keywordFields', 'block');
			changeStyleDisplay('onchangeField', 'none');
			changeStyleDisplay('textAreaOrKPFields', 'block');
		}
		else if (value == <%= AttributeConstants.k_lAttributeType_Hyperlink %>)
		{
			changeStyleDisplay('displayName', 'block');
			changeStyleDisplay('baseUrl', 'block');
			changeStyleDisplay('altText', 'block');
		}
		else if(value==<%= AttributeConstants.k_lAttributeType_Dropdown %> ||
				value==<%= AttributeConstants.k_lAttributeType_CheckList %> || 
				value==<%= AttributeConstants.k_lAttributeType_OptionList %>)
		{
			document.getElementById('extendableListField', "block");
		}
		else if (value == <%= AttributeConstants.k_lAttributeType_DatePicker %> ||
				 value==<%= AttributeConstants.k_lAttributeType_DateTime %>)
		{
			changeStyleDisplay('defaultDateInstructions', "block");
		}
	}
	
		
	function showDefault (value)
	{
		var bShowDefaultRow = true;
		var bShowValueNotVisisbleRow = true;
		
		// Steve 28-Feb-2011: I made a change here so default is always shown on lists
		
		// Following types do not have either value not visible or default
		if (value == <%= AttributeConstants.k_lAttributeType_KeywordPicker %> ||
			value == <%= AttributeConstants.k_lAttributeType_ExternalDictionary %> ||
			value == <%= AttributeConstants.k_lAttributeType_Autoincrement %> ||
			value == <%= AttributeConstants.k_lAttributeType_DataLookupButton %> ||
			value == <%= AttributeConstants.k_lAttributeType_SpatialArea %> )
		{
			bShowDefaultRow = false;
			bShowValueNotVisisbleRow = false;
		}
		
		
		if (bShowDefaultRow)
		{
			changeStyleDisplay('defaultValueRow','block');
		}
		else
		{
			changeStyleDisplay('defaultValueRow','none');
		}
		
		if (bShowValueNotVisisbleRow)
		{
			changeStyleDisplay('valueNotVisibleRow','block');
		}
		else
		{
			changeStyleDisplay('valueNotVisibleRow','none');
		}
		
	}


	function showIncrementDisplay (value)
	{
		if (value == <%= AttributeConstants.k_lAttributeType_Autoincrement %>)
		{
			changeStyleDisplay('incrementDisplay',"block");
		}
		else
		{
			changeStyleDisplay('incrementDisplay',"none");
		}
	}


	function showNumDecimalPlacesDisplay (value)
	{
		if(value == <%= AttributeConstants.k_lAttributeType_Numeric %> ||
			value == <%= AttributeConstants.k_lAttributeType_SpatialArea %> )
		{
			changeStyleDisplay('numDecimalPlacesRow',"block");
		}
		else
		{
			changeStyleDisplay('numDecimalPlacesRow',"none");
		}
	}


	function showSinglePointOnlyDisplay (value)
	{
		if( value == <%= AttributeConstants.k_lAttributeType_SpatialArea %> )
		{
			changeStyleDisplay('singlePointOnlyRow',"block");
		}
		else
		{
			changeStyleDisplay('singlePointOnlyRow',"none");
		}
	}


	function showExternalDataSource (value)
	{
		changeStyleDisplay('externalDataLookupPluginClass','none');
		
		if (value == <%= AttributeConstants.k_lAttributeType_DataLookupButton %>)
		{
			changeStyleDisplay('externalDataLookupPluginClass','block');
			changeStyleDisplay('onchangeField','none');
			changeStyleDisplay('autoCompleteField','none');
			changeStyleDisplay('searchTokenFields','none');
			changeStyleDisplay('tokenDelimiterFields','none');
		}
	}
	
	
	function showGeneralAttributeFields (value)
	{
		// Always show the general attributes rows except for ext data source
		if (value == <%= AttributeConstants.k_lAttributeType_DataLookupButton %>)
		{
			changeStyleDisplay('generalAttributeDetails', 'none');
		}
		else
		{
			changeStyleDisplay('generalAttributeDetails', 'block');
		}
		
		// Always show keyword searchable and hide for sort except for data lookup and spatial area
		if (value == <%= AttributeConstants.k_lAttributeType_DataLookupButton %> || 
				value == <%= AttributeConstants.k_lAttributeType_SpatialArea %> )
		{
			changeStyleDisplay('keywordSearchableRow', 'none');
			changeStyleDisplay('hideForSortRow', 'none');
		}
		else
		{
			changeStyleDisplay('keywordSearchableRow', 'block');
			changeStyleDisplay('hideForSortRow', 'block');
		}		
					
	}
	

	function showInputMask (value)
	{
		changeStyleDisplay('validationMaskField','none');

		if (value==<%= AttributeConstants.k_lAttributeType_TextField_Long %> ||
			value==<%= AttributeConstants.k_lAttributeType_TextField_Short %>)
		{
			changeStyleDisplay('validationMaskField', 'block');
		}
	}

	function synchroniseRequiredField(updatedControl)
	{
		<c:if test="${ hideIncompleteAssets || allowSearchByCompleteness}">
			if(updatedControl==document.getElementById('mandatoryBulkUpload') && updatedControl.checked)
			{
				document.getElementById('required').checked = true;
				document.getElementById('mandatory').checked = true;
			}
			else if(updatedControl==document.getElementById('mandatory'))
			{
				if (updatedControl.checked)
				{
					document.getElementById('required').checked = true;
				}
				else
				{
					document.getElementById('mandatoryBulkUpload').checked = false;
				}
			}
			else if(updatedControl==document.getElementById('required') && !updatedControl.checked)
			{
				document.getElementById('mandatory').checked = false;
				document.getElementById('mandatoryBulkUpload').checked = false;
				
				<c:if test="${supportMultiLanguage}">
					document.getElementById('translationRequired').checked = false;
				</c:if>
			}
			
			<c:if test="${supportMultiLanguage}">
				if(updatedControl==document.getElementById('translationRequired') && updatedControl.checked)
				{
					document.getElementById('required').checked = true;
				}
			</c:if>
		</c:if>
	}

	//-->
	</script>
	<style type="text/css">
		th { width: 120px; }
	</style>
		
	<logic:equal name="attributeForm" property="attribute.id" value="0">	
		<bean:define id="pagetitle" value="Add Attribute"/>
	</logic:equal>
	<logic:notEqual name="attributeForm" property="attribute.id" value="0">	
		<bean:define id="pagetitle" value="Edit Attribute"/>
	</logic:notEqual>

</head>

<body id="adminPage" onload="showRelevantFieldsOnLoad();">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	
	<bright:refDataList componentName="AttributeManager" methodName="getAttributeTypeListExcludingGroupHeading" id="attTypeListExcGH"/>
	
	<logic:equal name="attributeForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="attributeForm" property="errors" id="errorText">
				<bean:write name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form action="saveAttribute" method="post">
		<html:hidden name="attributeForm" property="attribute.id"/>
		<html:hidden name="attributeForm" property="attribute.sequence"/>
		<html:hidden name="attributeForm" property="attribute.treeId"/>
		<html:hidden name="attributeForm" property="attribute.valueColumnName"/>
		<c:if test="${ not (hideIncompleteAssets || allowSearchByCompleteness)}">
			<html:hidden name="attributeForm" property="attribute.requiredForCompleteness"/>
		</c:if>
		
		<table cellspacing="0" border="0" class="form" summary="Form for attribute details" style="margin:0; width: auto;">
			<tr>
				<th style="width:120px;"><label for="type">Type:</label><span class="required">*</span></th>
				<td>
					<c:if test="${attributeForm.attribute.id <=0}">
						<html:select name="attributeForm" property="attribute.typeId" onchange="attributeTypeChanged(this)" styleId="type">
							<html:options collection="attTypeListExcGH" property="id" labelProperty="name"/>
						</html:select>
					</c:if>
					<c:if test="${attributeForm.attribute.id >0}">
						<logic:iterate name="attTypeListExcGH" id="attType">
							<c:if test="${attType.id==attributeForm.attribute.typeId}">
								<c:out value="${attType.name}"/>
							</c:if>
						</logic:iterate>
						<html:hidden name="attributeForm" property="attribute.typeId"/>
					</c:if>
				</td>
			</tr>
			<tr>
				<th style="width:120px;">
					<label for="label">Label:</label> 
				</th>
				<td>
					<html:text styleClass="text" name="attributeForm" property="attribute.label" maxlength="255" styleId="label" />
				</td>
			</tr>
		</table>
		<table cellspacing="0" border="0" class="form" summary="Form for num decimal places" id="numDecimalPlacesRow" style="display: none; margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="maxDisplayLength">Max decimal places:</label> 
				</th>
				<td>
					<c:choose>
						<c:when test="${attributeForm.attribute.id<=0}">
							<html:select name="attributeForm" property="attribute.maxDecimalPlaces" styleId="maxDecimals" style="width: auto;" onchange="populate">
								<c:forEach begin="0" end="20" var="num">
									<html:option value='<%= String.valueOf(pageContext.getAttribute("num")) %>'/>
								</c:forEach>
							</html:select>
							&nbsp;&nbsp;<span class="comment">*Choose this carefully, as it cannot be altered later on</span>
						</c:when>
						<c:otherwise>	
							<bean:write name="attributeForm" property="attribute.maxDecimalPlaces"/>
							<html:hidden name="attributeForm" property="attribute.maxDecimalPlaces"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th style="width:120px;">
					<label for="maxDisplayLength">Min decimal places:</label> 
				</th>
				<td>
					<c:choose>
						<c:when test="${attributeForm.attribute.id<=0}">
							<html:select name="attributeForm" property="attribute.minDecimalPlaces" style="width: auto;">
								<c:forEach begin="0" end="20" var="num">
									<html:option value='<%= String.valueOf(pageContext.getAttribute("num")) %>'/>
								</c:forEach>
							</html:select>
							&nbsp;&nbsp;<span class="comment">*When displayed. Users will not be required to enter trailing zeros during data entry</span>
						</c:when>
						<c:otherwise>	
							<bean:write name="attributeForm" property="attribute.minDecimalPlaces"/>
							<html:hidden name="attributeForm" property="attribute.minDecimalPlaces"/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>
		<table cellspacing="0" border="0" class="form" summary="Form for attribute details" id="singlePointOnlyRow" style="display: none; margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="singlePointOnly">Single point only:</label> 
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.singlePointOnly" styleId="singlePointOnly" />
				</td>
			</tr>
		</table>
		<table cellspacing="0" border="0" class="form" summary="Form for attribute details" id="maxDisplayRow" style="display: none; margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="maxDisplayLength">Max display length:</label> 
				</th>
				<td>
					<input type="text" class="text" name="maxDisplayLengthString" size="5" id="maxDisplayLength" <c:if test="${attributeForm.attribute.maxDisplayLength >= 0}">value="<bean:write name='attributeForm' property='attribute.maxDisplayLength'/>"</c:if>/>
					&nbsp;&nbsp;<span class="comment">Leave blank to show all content for this attribute</span>
				</td>
			</tr>
		</table>
		<table cellspacing="0" border="0" class="form" summary="Form for attribute details" id="isHtml" style="display: none; margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="isHtml">Edit/Show as HTML:</label> 
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.isHtml" styleId="isHtml" />
				</td>
			</tr>
		</table>
		<table cellspacing="0" border="0" class="form" summary="Form for attribute details" id="textAreaOrKPFields" style="display: none; margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="width">Columns:</label> 
				</th>
				<td>
					<html:text styleClass="text" name="attributeForm" property="attribute.width" styleId="width" />
					&nbsp;&nbsp;<span class="comment">Enter '0' for the standard width dictated by css</span>
				</td>
			</tr>
			<tr>
				<th style="width:120px;">
					<label for="height">Rows:</label> 
				</th>
				<td>
					<html:text styleClass="text" name="attributeForm" property="attribute.height" styleId="height"  maxlength="3"/>
				</td>
			</tr>
		</table>
		<table cellspacing="0" border="0" class="form" summary="Form for attribute details" id="textAreaFields" style="display: none; margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="maxSize">Max characters:</label> 
				</th>
				<td>
					<html:text styleClass="text" name="attributeForm" property="attribute.maxSize" styleId="maxSize" maxlength="9"/>
				</td>
			</tr>
			<tr>
				<th style="width:120px;">
					<label for="isFiltered">Filtered?</label> 
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.filtered" styleId="isFiltered" />
				</td>
			</tr>
		</table>
		<table cellspacing="0" border="0" class="form" summary="Form for attribute details" style="margin:0; width: auto;">
			
			<logic:notEmpty name="attributeForm" property="attribute.translations">
				<logic:iterate name="attributeForm" property="attribute.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th style="width:120px;" class="translation">
								<label for="label<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<html:hidden name="attributeForm"  property="attribute.translations[${tIndex}].language.id"/>
								<html:hidden name="attributeForm"  property="attribute.translations[${tIndex}].language.name"/>
								<input type="text" class="text" name="attribute.translations[<bean:write name='tIndex'/>].label" maxlength="255" id="label<bean:write name='tIndex'/>" value="<bean:write name="translation" property="label" filter="false"/>" />
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
		</table>
		<div id="extendableListField">
			<c:if test="${attributeForm.attribute.id==0 || attributeForm.attribute.isDropdownList || attributeForm.attribute.isCheckList || attributeForm.attribute.isOptionList}">
				<table cellspacing="0" class="form" summary="Form for attribute details" style="margin:0;">
					<tr>
						<th style="width:120px;">
							<label for="extendable">Extendable list:</label> 
						</th>
						<td>
							<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.isExtendableList" styleId="extendable"/>
						</td>
					</tr>
				</table>
			</c:if>
		</div>
		<c:if test="${autoCompleteEnabled &&
		 				  (attributeForm.attribute.id==0 || attributeForm.attribute.autoCompleteAllowed)}">
			<div id="autoCompleteField">
				<table cellspacing="0" class="form" summary="Form for attribute details" style="margin:0;">
					<tr>
						<th style="width:120px;">
							<label for="autoComplete">Auto complete:</label>
						</th>
						<td>
							<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.autoComplete" styleId="autoComplete" onchange="showAutoCompletePlugin(false)"/>
						</td>
					</tr>
				</table>
			</div>
			
			<c:if test="${ not empty attributeForm.autoCompleteParticipants }">
				<table cellspacing="0" class="form" summary="" id="autoCompletePluginRow" style="margin:0;">
					<tr>
						<th style="width:120px;">
							<label for="dateFormatType">Auto complete Plugin:</label>
						</th>
						<td>
							<html:select name="attributeForm" property="attribute.autoCompletePlugin">
								<html:option value="">- None -</html:option>
								<html:optionsCollection name="attributeForm" property="autoCompleteParticipants" label="label" value="class.name"/>
							</html:select>
						</td>
					</tr>
				</table>
			</c:if>
			
		</c:if>
		
		<table cellspacing="0" class="form" summary="Form for date format row" id="dateFormatRow" style="margin:0;">
			<tr>
				<th style="width:120px;">
					<label for="dateFormatType">Date Format</label>
				</th>
				<td>
					<html:radio styleClass="checkbox" styleId="fullDate" property="attribute.dateFormatTypeId" value="0"><label for="fullDate">Full Date <em>(dd/mm/yyyy)</em></html:radio></label><br/>
					<html:radio styleClass="checkbox" styleId="yearAndMonth" property="attribute.dateFormatTypeId" value="1"><label for="yearAndMonth">Year and Month <em>(mm/yyyy)</em></html:radio></label><br/>
					<html:radio styleClass="checkbox" styleId="yearOnly" property="attribute.dateFormatTypeId" value="2"><label for="yearOnly">Year Only <em>(yyyy)</em></html:radio></label>
				</td>
			</tr>
		</table>
		
		<table cellspacing="0" class="form" summary="Form for attribute details" id="keywordSearchableRow" style="margin:0;">
			<tr>
				<th style="width:120px;">
					<label for="keywordSearchable">Include in keyword searches:</label>
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.keywordSearchable" styleId="keywordSearchable" />
				</td>
			</tr>
		</table>
		<table cellspacing="0" class="form" summary="Form for attribute details" id="generalAttributeDetails" style="margin:0;">
			<tr>	
				<th style="width:120px;">
					<label for="mandatory">Mandatory:</label> 
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.mandatory" styleId="mandatory" onclick="synchroniseRequiredField(this);"/>
				</td>
			</tr>
			<tr>
				<th style="width:120px;">
					<label for="mandatoryBulkUpload">Mandatory on Bulk Upload:</label>
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.mandatoryBulkUpload" styleId="mandatoryBulkUpload" onclick="synchroniseRequiredField(this);"/>
				</td>
			</tr>
			<c:if test="${hideIncompleteAssets || allowSearchByCompleteness}">
				<tr>	
					<th style="width:120px;">
						<label for="required">Required for completeness:</label>
					</th>
					<td>
						<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.requiredForCompleteness" styleId="required" onclick="synchroniseRequiredField(this);"/>
						<c:if test="${hideIncompleteAssets}">
							(Incomplete assets will be hidden from users with no upload permissions)
						</c:if>
					</td>
				</tr>
				<c:if test="${supportMultiLanguage && (attributeForm.attribute.id<=0 || attributeForm.attribute.isTextfield || attributeForm.attribute.isTextarea)}">
					<tr>	
						<th style="width:120px;">
							<label for="translationRequired">Translation required for completeness:</label>
						</th>
						<td>
							<div id="translationRequiredField" style="display:block;">
								<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.requiresTranslation" styleId="translationRequired" onclick="synchroniseRequiredField(this);"/>
								<c:if test="${hideIncompleteAssets}">
									(If checked, assets without this attribute translated will be hidden to users with no upload permissions)
								</c:if>
							</div>
							<div id="translationRequiredText" style="display:none;">
								N/A
							</div>
						</td>
					</tr>
				</c:if>
			</c:if>
			<tr>
				<th style="width:120px;">
					<label for="highlight">Highlight on view:</label> 
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.highlight" styleId="highlight" />
				</td>
			</tr>
			<tr>
				<th style="width:120px;">
					<label for="showOnDownload">Show on download:</label> 
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.showOnDownload" styleId="showOnDownload" />
				</td>
			</tr>
			<c:if test="${assetEntitiesEnabled && useParentMetadata}">
				<tr>
					<th style="width:120px;">
						<label for="showOnChild">Show on child:</label> 
					</th>
					<td>
						<html:checkbox name="attributeForm" property="attribute.showOnChild" styleClass="checkbox" styleId="showOnChild" />
					</td>
				</tr>
			</c:if>
		</table>
		<c:if test="${assetEntitiesEnabled}">
			<table cellspacing="0" border="0" class="form" summary="Form for data propagation" id="dataPropagation" style="margin:0; width: auto;">		
				<c:if test="${useParentMetadata}">
					<tr>
						<th style="width:120px;">
							<label for="includeInSearchForChild">Include in search for child:</label> 
						</th>
						<td>
							<html:checkbox name="attributeForm" property="attribute.includeInSearchForChild" styleClass="checkbox" styleId="includeInSearchForChild" />
						</td>
					</tr>
				</c:if>
				<tr>
					<th style="width:120px;">
						<label for="dataFromChildren">Populate from child data:</label> 
					</th>
					<td>
						<html:checkbox name="attributeForm" property="attribute.dataFromChildren" styleClass="checkbox" styleId="dataFromChildren" />
					</td>
				</tr>
			</table>
		</c:if>
		<table cellspacing="0" class="form" summary="Form for attribute details" id="hideForSortRow" style="margin:0;">
			<tr>
				<th style="width:120px;">
					<label for="hideForSort">Hide from sort:</label> 
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.hideForSort" styleId="hideForSort" />
				</td>
			</tr>
		</table>
		<table cellspacing="0" border="0" class="form" summary="Form for help text" id="helpText" style="margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="helpText">Help text:</label> 
				</th>
				<td>
					<html:text styleClass="text" name="attributeForm" property="attribute.helpText" maxlength="100" styleId="helpText" />
				</td>
			</tr>
			<logic:notEmpty name="attributeForm" property="attribute.translations">
				<logic:iterate name="attributeForm" property="attribute.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th style="width:120px;" class="translation">
								<label for="helpText<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<input type="text" class="text" name="attribute.translations[<bean:write name='tIndex'/>].helpText" maxlength="100" id="helpText<bean:write name='tIndex'/>" value="<bean:write name="translation" property="helpText" filter="false"/>" />
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
		</table>
		<table cellspacing="0" border="0" class="form" summary="Form for increment details" id="incrementDisplay" style="display: none; margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="seed">Start value:</label> 
				</th>
				<td>
					<c:choose>
						<c:when test="${attributeForm.attribute.id > 0}">
							<html:hidden name="attributeForm" property="attribute.seed"/>
							<bean:write name="attributeForm" property="attribute.seed"/>
						</c:when>
						<c:otherwise>
							<html:text styleClass="text" name="attributeForm" property="attribute.seed" styleId="seed" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th style="width:120px;">
					<label for="increment">Increment amount:</label> 
				</th>
				<td>
					<c:choose>
						<c:when test="${attributeForm.attribute.id > 0}">
							<html:hidden name="attributeForm" property="attribute.increment"/>
							<bean:write name="attributeForm" property="attribute.increment"/>
						</c:when>
						<c:otherwise>
							<html:text styleClass="text" name="attributeForm" property="attribute.increment" styleId="increment" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<th style="width:120px;">
					<label for="prefix">Prefix:</label> 
				</th>
				<td>
					<html:text styleClass="text" name="attributeForm" property="attribute.prefix" styleId="prefix" />
				</td>
			</tr>
			<tr>
				<th style="width:120px;">
					<label for="padwidth">Width for padding:</label> 
				</th>
				<td>
					<html:text styleClass="text" name="attributeForm" property="attribute.padWidth" styleId="padwidth" />
				</td>
			</tr>
			<tr>	
				<th style="width:120px;">
					<label for="mandatory">Read only:</label> 
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.readOnly" styleId="readOnly"/>
				</td>
			</tr>
		</table>

		<table cellspacing="0" border="0" class="form" summary="Form for external data lookup details" id="externalDataLookupPluginClass" style="display: none; margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="seed">Plugin class:</label> 
				</th>
				<td>
					<html:text styleClass="text" name="attributeForm" property="attribute.pluginClass" styleId="seed" />
				</td>
			</tr>
			<tr>
				<th style="width:120px;">
					<label for="seed">Attributes for parameters (comma separated):</label> 
				</th>
				<td>
					<html:text styleClass="text" name="attributeForm" property="attribute.attributeIdsForPluginParams" styleId="seed" />
				</td>
			</tr>
		</table>

		<c:if test="${attributeForm.attribute.id==0 || attributeForm.attribute.isTextfield || attributeForm.attribute.isTextarea || attributeForm.attribute.isHyperlink || attributeForm.attribute.isOptionList || attributeForm.attribute.isCheckList || attributeForm.attribute.isDropdownList || attributeForm.attribute.isDatepicker}">
			<div id="searchTokenFields">
				<table cellspacing="0" class="form" summary="Form for attribute details" style="margin:0;">
					<tr>
						<th style="width:120px;">
							<label for="tokenize">Keyword search links on view:</label> 
						</th>
						<td>
							<html:checkbox styleClass="checkbox" name="attributeForm" property="attribute.hasSearchTokens" styleId="tokenize"/>
						</td>
					</tr>
				</table>
			</div>
		</c:if>
		<c:if test="${attributeForm.attribute.id==0 || attributeForm.attribute.isTextfield || attributeForm.attribute.isTextarea || attributeForm.attribute.isHyperlink }">
			<div id="tokenDelimiterFields">
				<table cellspacing="0" class="form" summary="Form for attribute details" style="margin:0;">
					<tr>
						<th style="width:120px;">
							<label for="tokenDelimiter">Keyword delimiter (see help):</label>
						</th>
						<td>
							<html:text styleClass="text" style="width:auto;" name="attributeForm" property="attribute.tokenDelimiterRegex"  styleId="tokenDelimiter" size="8" maxlength="20"/>
							&nbsp;
							<a href="../action/viewHelp?helpsection=keyword-token-delimiter-help" target="_blank" title="Click for search help in a new window" class="help-popup"><img src="../images/standard/icon/help.gif" alt="Help" width="15" height="15" class="help" border="0" /></a>
						</td>
					</tr>
				</table>
			</div>
		</c:if>
		<div id="keywordFields">
			<table cellspacing="0" class="form" summary="Form for attribute details" style="margin:0;">
				<tr>
					<th style="width:120px;">
						<label for="defaultFilter">Default keyword filter:</label> 
					</th>
					<td>
						<html:select name="attributeForm" property="attribute.defaultKeywordFilter" styleId="defaultFilter">
							<option value="all" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'all'}">selected</c:if>>All</option>
							<option value="a" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'a'}">selected</c:if>>A</option>
							<option value="b" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'b'}">selected</c:if>>B</option>
							<option value="c" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'c'}">selected</c:if>>C</option>
							<option value="d" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'd'}">selected</c:if>>D</option>
							<option value="e" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'e'}">selected</c:if>>E</option>
							<option value="f" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'f'}">selected</c:if>>F</option>
							<option value="g" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'g'}">selected</c:if>>G</option>
							<option value="h" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'h'}">selected</c:if>>H</option>
							<option value="i" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'i'}">selected</c:if>>I</option>
							<option value="j" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'j'}">selected</c:if>>J</option>
							<option value="k" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'k'}">selected</c:if>>K</option>
							<option value="l" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'l'}">selected</c:if>>L</option>
							<option value="m" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'm'}">selected</c:if>>M</option>
							<option value="n" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'n'}">selected</c:if>>N</option>
							<option value="o" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'o'}">selected</c:if>>O</option>
							<option value="p" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'p'}">selected</c:if>>P</option>
							<option value="q" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'q'}">selected</c:if>>Q</option>
							<option value="r" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'r'}">selected</c:if>>R</option>
							<option value="s" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 's'}">selected</c:if>>S</option>
							<option value="t" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 't'}">selected</c:if>>T</option>
							<option value="u" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'u'}">selected</c:if>>U</option>
							<option value="v" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'v'}">selected</c:if>>V</option>
							<option value="w" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'w'}">selected</c:if>>W</option>
							<option value="x" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'x'}">selected</c:if>>X</option>
							<option value="y" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'y'}">selected</c:if>>Y</option>
							<option value="z" <c:if test="${attributeForm.attribute.defaultKeywordFilter == 'z'}">selected</c:if>>Z</option>
						</html:select>
					</td>
				</tr>
				</table>
			</div>

			<table cellspacing="0" class="form" summary="Form for is globally unique" id="isGloballyUnique" style="margin:0;">
			<c:if test="${attributeForm.attribute.id <=0}">
			<tr>
				<th style="width:120px;">
					<label for="singlePointOnly">Enforce globally unique keywords:</label> 
				</th>
				<td>
					<html:checkbox styleClass="checkbox" name="attributeForm" property="categoriesGloballyUnique" styleId="singlePointOnly" />
					&nbsp;(note: this can't be changed later)
				</td>
			</tr>
			</c:if>
			</table>
			
			<table cellspacing="0" border="0" class="form" summary="Form for attribute details" id="displayName" style="display: none; margin:0; width: auto;">
				<tr>
				<th style="width:120px;">
					<label for="display">Display name:</label> 
				</th>
				<td>
					<html:text styleClass="text" name="attributeForm" property="attribute.displayName" maxlength="255" styleId="display" />
				</td>
			</tr>
			<tr><td colspan="2" style="padding:0px;"><table id="displayNameTranslation" border="0" cellspacing="0" cellpadding="0" style="margin:0px;">
				<c:if test="${not empty attributeForm.attribute.translations && attributeForm.listAttributeValues==null}">
					<logic:iterate name="attributeForm" property="attribute.translations" id="translation" indexId="tIndex">
						<logic:greaterThan name="translation" property="language.id" value="0">
							<tr>
								<th  style="width:120px;" class="translation">
									<label for="default<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
								</th>
								<td>
									<input type="text" class="text" name="attribute.translations[<bean:write name='tIndex'/>].displayName" maxlength="255" id="default<bean:write name='tIndex'/>" value="<bean:write name="translation" property="displayName" filter="false"/>" />
								</td>
							</tr>
						</logic:greaterThan>
					</logic:iterate>
				</c:if></table></td>
			</tr>
			</table>
			
			<table cellspacing="0" border="0" class="form" summary="Form for attribute details" id="baseUrl" style="display: none; margin:0; width: auto;">
				<tr>
				<th style="width:120px;">
					<label for="base">Base URL:</label> 
				</th>
				<td>
					<html:text styleClass="text" name="attributeForm" property="attribute.baseUrl" maxlength="255" styleId="base" />
				</td>
			</tr>	
			</table>
					
			<table cellspacing="0" border="0" class="form" summary="Form for attribute details" id="altText" style="display: none; margin:0; width: auto;">
				<tr>
				<th style="width:120px;">
					<label for="alt">Alt text:</label> 
				</th>
				<td>
					<html:text styleClass="text" name="attributeForm" property="attribute.altText" maxlength="255" styleId="alt" />
				</td>
			</tr>
			<tr><td colspan="2" style="padding:0px;"><table id="altTextTranslation" border="0" cellspacing="0" cellpadding="0" style="margin:0px;">
				<c:if test="${not empty attributeForm.attribute.translations && attributeForm.listAttributeValues==null}">
					<logic:iterate name="attributeForm" property="attribute.translations" id="translation" indexId="tIndex">
						<logic:greaterThan name="translation" property="language.id" value="0">
							<tr>
								<th  style="width:120px;" class="translation">
									<label for="default<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
								</th>
								<td>
									<input type="text" class="text" name="attribute.translations[<bean:write name='tIndex'/>].altText" maxlength="255" id="default<bean:write name='tIndex'/>" value="<bean:write name="translation" property="altText" filter="false"/>" />
								</td>
							</tr>
						</logic:greaterThan>
					</logic:iterate>
				</c:if></table></td>
			</tr>	
		</table>
			
			
	<div id="valueNotVisibleRow">
		<table cellspacing="0" border="0" class="form" summary="Form for attribute details" style="margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="notvisible">Value if not visible:</label> 
				</th>
				<td>
					<div id="notVisibleField">
					<c:choose>
						<c:when test="${attributeForm.attribute.id>0 && attributeForm.listAttributeValues != null}">
							<bean:define id="attributeValueList" name="attributeForm" property="listAttributeValues"/>
							<html:select name="attributeForm" property="attribute.valueIfNotVisible" styleId="notvisible">
								<html:option value="0">- None -</html:option>
								<html:options collection="attributeValueList" property="value" labelProperty="value"/>
							</html:select>					
						</c:when>
						<c:otherwise>
							<html:text styleClass="text" name="attributeForm" property="attribute.valueIfNotVisible" maxlength="255" styleId="notvisible" />
						</c:otherwise>	
					</c:choose>
					</div>
					<div id="notVisibleText" style="display: none">
						N/A (until you have added some values)
					</div>
				</td>
			</tr>
			<tr><td colspan="2" style="padding:0px;"><table id="notVisibleTranslation" border="0" cellspacing="0" cellpadding="0" style="margin:0px;">
				<c:if test="${not empty attributeForm.attribute.translations && attributeForm.listAttributeValues==null}">
					<logic:iterate name="attributeForm" property="attribute.translations" id="translation" indexId="tIndex">
						<logic:greaterThan name="translation" property="language.id" value="0">
							<tr>
								<th  style="width:120px;" class="translation">
									<label for="notvisible<bean:write name='tIndex'/>"> (<bean:write name="translation" property="language.name"/>):</label> 
								</th>
								<td>
									<input type="text" class="text" name="attribute.translations[<bean:write name='tIndex'/>].valueIfNotVisible" maxlength="255" id="notvisible<bean:write name='tIndex'/>" value="<bean:write name="translation" property="valueIfNotVisible" filter="false"/>" />
								</td>
							</tr>
						</logic:greaterThan>
					</logic:iterate>
				</c:if></table></td>
			</tr>
		</table>
	</div>
		
	<div id="defaultValueRow">
		<table cellspacing="0" border="0" class="form" summary="Form for attribute details" style="margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="default">Default value:</label> 
				</th>
				<td>
					<div id="defaultField">
					<c:choose>
						<c:when test="${attributeForm.attribute.id>0 && attributeForm.listAttributeValues != null}">
							<html:select name="attributeForm" property="attribute.defaultValue" styleId="default">
								<html:option value="">- None -</html:option>
								<html:options collection="attributeValueList" property="value" labelProperty="value"/>
							</html:select>					
						</c:when>
						<c:otherwise>
							<html:text styleClass="text" name="attributeForm" property="attribute.defaultValue" maxlength="255" styleId="default" />
							<div id="defaultDateInstructions" style="display: none">
								You can enter a value such as 'now+2' to mean in 2 days time or 'now-7' to mean 7 days ago
							</div>
						</c:otherwise>	
					</c:choose>
					</div>
					<div id="defaultText" style="display: none">
						N/A (until you have added some values)
					</div>
				</td>
			</tr>
			<tr><td colspan="2" style="padding:0px;"><table id="defaultValueTranslation" border="0" cellspacing="0" cellpadding="0" style="margin:0px;">
				<c:if test="${not empty attributeForm.attribute.translations && attributeForm.listAttributeValues==null}">
					<logic:iterate name="attributeForm" property="attribute.translations" id="translation" indexId="tIndex">
						<logic:greaterThan name="translation" property="language.id" value="0">
							<tr>
								<th style="width:120px;" class="translation">
									<label for="default<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
								</th>
								<td>
									<input type="text" class="text" name="attribute.translations[<bean:write name='tIndex'/>].defaultValue" maxlength="255" id="default<bean:write name='tIndex'/>" value="<bean:write name="translation" property="defaultValue" filter="false"/>" />
								</td>
							</tr>
						</logic:greaterThan>
					</logic:iterate>
				</c:if></table></td>
			</tr>
		</table>
	</div>
		
	<div id="validationMaskField">
		<table cellspacing="0" border="0" class="form" style="margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="mask">Input validation mask:</label> 
				</th>
				<td>
					<div id="maskField">
						<html:text styleClass="text" name="attributeForm" property="attribute.inputMask" maxlength="255" styleId="mask" />
					</div>
				</td>
			</tr>
			<logic:notEmpty name="attributeForm" property="attribute.translations">
			<logic:iterate name="attributeForm" property="attribute.translations" id="translation" indexId="tIndex">
				<logic:greaterThan name="translation" property="language.id" value="0">
					<tr>
						<th  style="width:120px;" class="translation">
							<label for="mask<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
						</th>
						<td>
							<html:hidden name="attributeForm"  property="attribute.translations[${tIndex}].language.id"/>
							<html:hidden name="attributeForm"  property="attribute.translations[${tIndex}].language.name"/>
							<input type="text" class="text" name="attribute.translations[<bean:write name='tIndex'/>].inputMask" maxlength="255" id="mask<bean:write name='tIndex'/>" value="<bean:write name="translation" property="inputMask" filter="false"/>" />
						</td>
					</tr>
				</logic:greaterThan>
			</logic:iterate>
		</logic:notEmpty>
		</table>
	</div>	

	<div id="onchangeField">
		<table cellspacing="0" border="0" class="form" summary="Form for attribute details" style="margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="onchng">OnChange Script:</label> 
				</th>
				<td>
					<html:textarea styleClass="text" name="attributeForm" property="attribute.onChangeScript" rows="4" cols="60" styleId="onchng" />
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					Note, the value of the OnChange Script will be rendered within double quotes. Use single quotes within the script or escape double quotes with a backslash.<br/>
					If you need to refer to another attribute field in this script you can use the element id, which will be "field" plus the attribute id (e.g. "field3"). 
				</td>
			</tr>
		</table>
	</div>

	<%--  Initial attribute visibility, for new attributes --%>
	<logic:lessEqual name="attributeForm" property="attribute.id" value="0">
		<table cellspacing="0" border="0" class="form" summary="Form for attribute details" style="margin:0; width: auto;">
			<tr>
				<th style="width:120px;">
					<label for="visibleToGroup">Initially visible to:</label> 
				</th>
				<td>
				<bean:define id="groups" name="attributeForm" property="visibleToGroups"/>
				<html:select name="attributeForm" property="visibleToGroupId" styleId="visibleToGroup">
					<html:option value="0">- none -</html:option>
					<html:options collection="groups" property="id" labelProperty="name"/>
				</html:select>
				&nbsp;(Visibility to custom groups is managed via the Groups menu)
				</td>
			</tr>
		</table>
	</logic:lessEqual>
		
				
		<div class="hr"></div>
		
		
		<input class="button flush floated" type="submit" value="<bright:cmsWrite identifier="button-save" filter="false" />" />	
		<a href="../action/viewManageAttributes" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
	</html:form>
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>
