<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	21-Aug-2007		Created
	 d2		Matt Stevenson	28-Aug-2007		Added delimiter field
	 d3		Matt Stevenson	29-Aug-2007		Added edit links
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@page import="org.apache.lucene.search.SortField"%>
<%@page import="com.bright.assetbank.attribute.bean.SortAttribute"%>
<%@page import="com.bright.assetbank.attribute.constant.AttributeConstants"%>


<bright:applicationSetting id="ecommerce" settingName="ecommerce" />
<bright:applicationSetting id="reembedMetadataOnSave" settingName="reembed-metadata-on-save" />

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Embedded Data Mappings</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="attributes"/>
	<bean:define id="helpsection" value="embedded_mappings"/>
	<bean:define id="pagetitle" value="Embedded Data Mappings"/>
	<bean:define id="tabId" value="embeddedData"/>

	<script type="text/javascript">
		<!--
			var lSelectedTypeId = -1;
			
			function showList (listBox)
			{
				showListWithId (listBox.options[listBox.selectedIndex].value);
			}

			function showListWithId (lId)
			{
				if (lSelectedTypeId > 0) 
				{ 
					document.getElementById('typeList'+lSelectedTypeId).style.display = 'none'; 
					document.getElementById('typeList'+lSelectedTypeId).selectedIndex = -1; 
				} 
				lSelectedTypeId = lId; 
				document.getElementById('typeMessage').style.display = 'none'; 
				document.getElementById('typeList'+lId).style.display = 'block';
			}
		-->
	</script>
</head>

<body id="adminPage" onload="<c:if test='${embeddedDataForm.embeddedDataTypeId > 0}'>showListWithId(<bean:write name='embeddedDataForm' property='embeddedDataTypeId'/>);</c:if><c:if test='${embeddedDataForm.embeddedDataMapping.embeddedDataValueId > 0}'>document.getElementById('dvId').value = <bean:write name='embeddedDataForm' property='embeddedDataMapping.embeddedDataValueId'/>;</c:if>">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:parameter id="browse" name="browse" value="0"/>
	<h1><bean:write name="pagetitle" filter="false"/></h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	
	<p>From here you can change the mappings between the embedded data from images and their attributes in <bright:cmsWrite identifier="app-name" filter="false"/>.</p> 

	<p>If you are modifying attributes, then when you have finished you should <a href="../action/viewReindexStatus">reindex existing assets</a>.</p>
	
	<p>If you want to re-run extraction of embedded metadata for ALL assets, <a href="../action/viewRerunEmbeddedMetadataExtractionStatus">rerun metadata extraction for existing assets</a>. 
	NOTE: This will overwrite all current values of attributes that are mapped to embedded metadata!</p>
	
	<p>To add a new embedded data mapping use the form at the bottom of the list below:</p>
	
	<div id="tabContent">
  	<logic:notEmpty name="embeddedDataForm" property="embeddedDataMappings">
		<table cellspacing="0" class="admin" summary="List of Attributes">
			<tr>
				<th>Attribute</th>
				<th>Embedded Data Type</th>
				<th>Embedded Data Value</th>
				<c:if test="${!reembedMetadataOnSave}"><th>Mapping Direction?</th></c:if>
				<th>Delimiter</th>
				<th>Binary Data</th>
				<th colspan="3">&nbsp;</th>
			</tr>
			
			<logic:iterate name="embeddedDataForm" property="embeddedDataMappings" id="embeddedDataMapping" indexId="index">
			
				<tr>
					<td>
						<bean:write name="embeddedDataMapping" property="attribute.label"/>
					</td>
					<td>
						<bean:write name="embeddedDataMapping" property="value.type.name"/>
					</td>
					<td>
						<bean:write name="embeddedDataMapping" property="value.name"/>
					</td>
					<c:if test="${!reembedMetadataOnSave}">
					<td>
						<bean:write name="embeddedDataMapping" property="direction.name"/>
					</td>
					</c:if>
					<td>
						<logic:notEmpty name="embeddedDataMapping" property="delimiter">
							<bean:write name="embeddedDataMapping" property="delimiter"/>
						</logic:notEmpty>
						<logic:empty name="embeddedDataMapping" property="delimiter">
							n/a
						</logic:empty>
					</td>
					<td>
						<c:choose><c:when test="${embeddedDataMapping.binaryData}">Yes</c:when><c:otherwise>No</c:otherwise></c:choose>
					</td>					
					<td class="action">
						[<c:if test="${index > 0}"><a href="moveEmbeddedDataMapping?attributeId=<bean:write name='embeddedDataMapping' property='attribute.id'/>&embeddedDataValueId=<bean:write name='embeddedDataMapping' property='value.id'/>&mappingDirectionId=<bean:write name='embeddedDataMapping' property='direction.id'/>&up=1"></c:if>up<c:if test="${index > 0}"></a></c:if>]&nbsp;[<c:if test="${index < (embeddedDataForm.noOfMappings-1)}"><a href="moveEmbeddedDataMapping?attributeId=<bean:write name='embeddedDataMapping' property='attribute.id'/>&embeddedDataValueId=<bean:write name='embeddedDataMapping' property='value.id'/>&mappingDirectionId=<bean:write name='embeddedDataMapping' property='direction.id'/>&up=0"></c:if>down<c:if test="${index < (embeddedDataForm.noOfMappings-1)}"></a></c:if>]&nbsp;[<a href="viewEditEmbeddedDataMapping?attributeId=<bean:write name='embeddedDataMapping' property='attribute.id'/>&embeddedDataValueId=<bean:write name='embeddedDataMapping' property='value.id'/>&mappingDirectionId=<bean:write name='embeddedDataMapping' property='direction.id'/>&embeddedDataTypeId=<bean:write name='embeddedDataMapping' property='value.type.id'/>" title="Edit this embedded data mapping">edit</a>]&nbsp;[<a href="deleteEmbeddedDataMapping?attributeId=<bean:write name='embeddedDataMapping' property='attribute.id'/>&embeddedDataValueId=<bean:write name='embeddedDataMapping' property='value.id'/>&mappingDirectionId=<bean:write name='embeddedDataMapping' property='direction.id'/>&embeddedDataTypeId=<bean:write name='embeddedDataMapping' property='value.type.id'/>" onclick="return confirm('Are you sure you want to remove this embedded data mapping?');" title="Delete this embedded data mapping">X</a>]
					</td>
				</tr>
			
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<logic:empty name="embeddedDataForm" property="embeddedDataMappings">
		<p>There are currently no embedded data mappings defined.
	</logic:empty>
	</div>
	

	<logic:notEmpty name="embeddedDataForm" property="errors">
		<div class="error">
			<logic:iterate name="embeddedDataForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:notEmpty>
	<p><span class="required">*</span> denotes a required field</p>
		<form name="addEmbeddedDataForm" id="addEmbeddedDataForm" action="saveEmbeddedDataMapping" method="post" class="floated wideLabel">
			
				<label for="attribute">Attribute: <span class="required">*</span></label>
			
				<html:select name="embeddedDataForm" property="embeddedDataMapping.attributeId" size="1" styleId="attribute">
					<option value="0">[Please select]</option>
					<logic:iterate name="embeddedDataForm" property="attributes" id="attribute" indexId="index">
						<option value="<bean:write name='attribute' property='id'/>" <c:if test="${embeddedDataForm.embeddedDataMapping.attributeId == attribute.id}">selected</c:if>>
							<c:choose>
								<c:when test="${attribute.label == ''}">
									[No Label]
								</c:when>
								<c:otherwise>
									<bean:write name="attribute" property="label"/>
								</c:otherwise>
							</c:choose>
						</option>
					</logic:iterate>
				</html:select>
				<br />
				<div class="js-enabled-show">
					<label for="embeddedType">Embedded data type: <span class="required">*</span></label>
					<html:select name="embeddedDataForm" property="embeddedDataTypeId" size="1" onchange="showList(this);" styleId="embeddedType">
						<option value="0">[Please select]</option>
						<logic:iterate name="embeddedDataForm" property="embeddedDataTypes" id="type" indexId="index">
							<option value="<bean:write name='type' property='id'/>" <c:if test="${embeddedDataForm.embeddedDataTypeId == type.id}">selected</c:if>>
								<bean:write name="type" property="name"/>
							</option>
						</logic:iterate>
					</html:select>
					<br />
				</div>
				
		
		

				<label for="embeddedValue">Embedded data value: <span class="required">*</span></label>
	
				<div id="typeMessage" style="display:none;">
					[Select the type above]
					<input type="hidden" name="embeddedDataMapping.embeddedDataValueId" id="dvId" value=""/>
				</div>
				
				<script type="text/JavaScript">
					document.getElementById('typeMessage').style.display = 'block';
				</script>

				<logic:iterate name="embeddedDataForm" property="embeddedDataTypes" id="type" indexId="index">
					<div id="typeList<bean:write name='type' property='id'/>" style="display: none;">
						<bean:define id="typeId" name="type" property="id"/>
						<bean:define id="dataValues" name="embeddedDataForm" property='<%= "embeddedDataValuesForType[" + typeId + "]" %>' />
						<select name="embeddedDataValueId<bean:write name='type' property='id'/>" size="1" onchange="document.getElementById('dvId').value = this.options[this.selectedIndex].value;">
							<option value="0">[Please select]</option>
							<logic:iterate name="dataValues" id="embeddedDataValue" indexId="index">
								<option value="<bean:write name='embeddedDataValue' property='id'/>" <c:if test="${embeddedDataForm.embeddedDataMapping.embeddedDataValueId == embeddedDataValue.id}">selected</c:if>>
									<bean:write name="embeddedDataValue" property="name"/>
								</option>
							</logic:iterate>
						</select>
					</div>
				</logic:iterate>
				


				<noscript>
					<html:select name="embeddedDataForm" property="embeddedDataMapping.embeddedDataValueId" size="1">
						<option value="0">[Please select]</option>
						<logic:iterate name="embeddedDataForm" property="flatEmbeddedDataValues" id="embeddedDataValue" indexId="index">
							<option value="<bean:write name='embeddedDataValue' property='id'/>" <c:if test="${embeddedDataForm.embeddedDataMapping.embeddedDataValueId == embeddedDataValue.id}">selected</c:if>>
								<bean:write name="embeddedDataValue" property="type.name"/>:&nbsp;<bean:write name="embeddedDataValue" property="name"/>
							</option>
						</logic:iterate>
					</html:select>
				</noscript>
				

				<br />
				
				<c:choose>
					<c:when test="${reembedMetadataOnSave}">
						<input type="hidden" name="embeddedDataMapping.mappingDirectionId" value="<%= AttributeConstants.k_lEmbeddedDataMappingDirection_Upload %>" />
					</c:when>
					<c:otherwise>					
						<label for="direction">Mapping direction: <span class="required">*</span></label>
						<html:select name="embeddedDataForm" property="embeddedDataMapping.mappingDirectionId" styleId="direction" size="1">
							<option value="0">[Please select]</option>
							<logic:iterate name="embeddedDataForm" property="mappingDirections" id="mappingDirection" indexId="index">
								<option value="<bean:write name='mappingDirection' property='id'/>" <c:if test="${embeddedDataForm.embeddedDataMapping.mappingDirectionId == mappingDirection.id}">selected</c:if>>
									<bean:write name="mappingDirection" property="name"/>
								</option>
							</logic:iterate>
						</html:select>
						<br />
					</c:otherwise>
				</c:choose>
				
				<label for="delimiter">Delimiter:</label>
				<html:text name="embeddedDataForm" property="embeddedDataMapping.delimiter" size="10" maxlength="10" styleId="delimiter" style="width: 40px;"/><br />
					
				<label for="binary">Binary data:</label>
				<html:checkbox styleClass="checkbox" styleId="binary" name="embeddedDataForm" property="embeddedDataMapping.binaryData" /><br />
				<input type="submit" name="submit" class="button" value="<bright:cmsWrite identifier="button-add-arrow" filter="false" />" style="margin-left: 0px;"/>
		</form>

	

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>