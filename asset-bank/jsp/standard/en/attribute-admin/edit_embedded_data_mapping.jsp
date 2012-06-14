<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	29-Aug-2007		Created
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
	<bean:define id="section" value="embeddedData"/>
	<bean:define id="pagetitle" value="Attributes"/>
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
	<h1><bean:write name="pagetitle" /></h1> 

	<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
	
	<h2>Edit Embedded Data Mapping</h2>
	<p>Edit the selected mapping below then click 'Save'.</p> 

	<logic:notEmpty name="embeddedDataForm" property="errors">
		<div class="error">
			<logic:iterate name="embeddedDataForm" property="errors" id="error">
				<bean:write name="error"/><br/>
			</logic:iterate>
		</div>
	</logic:notEmpty>
	<p><span class="required">*</span> denotes a required field</p>
	<form name="addEmbeddedDataForm" id="addEmbeddedDataForm" action="editEmbeddedDataMapping" method="post" class="floated">
		<input type="hidden" name="oldAttributeId" value="<bean:write name='embeddedDataForm' property='oldAttributeId'/>"/>
		<input type="hidden" name="oldEmbeddedDataValueId" value="<bean:write name='embeddedDataForm' property='oldValueId'/>"/>
		<input type="hidden" name="oldMappingDirectionId" value="<bean:write name='embeddedDataForm' property='oldDirectionId'/>"/>
		<input type="hidden" name="oldEmbeddedDataTypeId" value="<bean:write name='embeddedDataForm' property='oldTypeId'/>"/>

		<html:hidden name="embeddedDataForm" property="embeddedDataMapping.sequence"/>

			<label for="attribute">Attribute: <span class="required">*</span></label>
			<html:select name="embeddedDataForm" property="embeddedDataMapping.attributeId" size="1">
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
				<label for="">Embedded data type:</label>
				<html:select name="embeddedDataForm" property="embeddedDataTypeId" size="1" onchange="showList(this);">
					<logic:iterate name="embeddedDataForm" property="embeddedDataTypes" id="type" indexId="index">
						<option value="<bean:write name='type' property='id'/>" <c:if test="${embeddedDataForm.embeddedDataMapping.value.type.id == type.id}">selected</c:if>>
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
			<html:checkbox styleClass="checkbox" name="embeddedDataForm" property="embeddedDataMapping.binaryData" styleId="binary"/><br />
			
			<div class="hr"></div>	
			<input type="submit" name="submit" class="button flush" value="Save &raquo;" />
			<a href="../action/viewManageEmbeddedDataMappings" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</form>

	

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>