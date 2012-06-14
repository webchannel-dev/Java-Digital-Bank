<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	09-May-2005		Created
	 d2		Matt Stevenson	10-May-2005		Added link to add group
	 d3		Tamora James	18-May-2005		Integrated with UI design for Demo
	 d4		Ben Browning	22-Feb-2006		HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Attributes</title>
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="attributes"/>
	<bean:define id="pagetitle" value="Edit Attribute Values"/>
	<script type="text/JavaScript">
		// give the listValue field the focus once the dom is ready
		$j(function () {
  			$j('#listValue').focus();
 		});
	</script>	
</head>

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:present  name="listAttributeForm">
		<logic:equal name="listAttributeForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="listAttributeForm" property="errors" id="error">
						<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<h2>Manage '<bean:write name="listAttributeForm" property="value.attribute.label"/>' Attribute</h2>
	
	<table cellspacing="0" class="admin" summary="List of Usage Types">
		<logic:iterate name="listAttributeForm" property="values" id="value">
			<tr>
				<td><bean:write name="value" property="value"/></td>
				<logic:equal name="value" property="editable" value="true">			
					<td class="action">
						[<a href="moveAttributeListValue?id=<bean:write name='value' property='id'/>&amp;up=true">up</a>]
					</td>
					<td class="action">
						[<a href="moveAttributeListValue?id=<bean:write name='value' property='id'/>&amp;up=false">down</a>]
					</td>
					<td class="action">
						[<a href="viewUpdateAttributeListValue?id=<bean:write name='value' property='id'/>">edit</a>]
					</td>	
					<td class="action">
						[<a href="deleteAttributeListValue?id=<bean:write name='value' property='id'/>&amp;attributeId=<bean:write name='listAttributeForm' property='value.attribute.id'/>" onclick="var msg='Are you sure?\nThis will remove this value from all images that currently reference it.'; return confirm(msg);" title="Delete this attribute value">X</a>]
					</td>
				</logic:equal>
				<logic:notEqual name="value" property="editable" value="true">
					<td class="action disabled">
						[up]
					</td>
					<td class="action disabled">
						[down]
					</td>
					<td class="action disabled">
						[<a href="viewUpdateAttributeListValue?id=<bean:write name='value' property='id'/>">edit</a>]
					</td>
					<td class="action disabled">
						[X]
					</td>
				</logic:notEqual>
			</tr>
		</logic:iterate>
	</table>	

	<div class="hr"></div>
		
	<%-- Link to reorder attributes --%>
	<h2>Reorder alphabetically</h2>
	<p>
		If you want you can reorder the attribute values so that they appear to users in alphabetical order:
	</p>
	<p>
		<a href="reorderAttributeValues?attributeId=<bean:write name='listAttributeForm' property='value.attribute.id'/>" onclick="return confirm('Are you sure you want to reorder the attribute values list?\nThis action cannot be undone.');" title="Reorder attribute values" >Reorder now &raquo;</a>
	</p>	
	
	<div class="hr"></div>

	<logic:equal name="listAttributeForm" property="value.id" value="0">
		<html:form enctype="multipart/form-data" action="addAttributeListValue" method="post">
			<html:hidden name="listAttributeForm" property="value.attribute.id"  />
         <h2>Add new <bean:write name="listAttributeForm" property="value.attribute.label"/></h2>
	      <table cellspacing="0" cellpadding="0" class="form">
            <tr>
               <th><label for="listValue">Name:</label></th>
               <td><html:text styleClass="text" name="listAttributeForm" styleId="listValue" property="value.value" size="30"/></td>
            </tr>
            <c:if test="${not empty listAttributeForm.value.translations}">
				<logic:iterate name="listAttributeForm" property="value.translations" id="translation" indexId="tIndex">	
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="value<bean:write name="tIndex"/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<html:hidden name="listAttributeForm" property="value.translations[${tIndex}].language.id"/>
								<html:hidden name="listAttributeForm" property="value.translations[${tIndex}].language.name"/>
								<input type="text" class="text" id="value<bean:write name="tIndex"/>" name="value.translations[<bean:write name="tIndex"/>].value" maxlength="255" value="<bean:write name="translation" property="value" filter="false"/>"/>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</c:if>
			<tr>
			   <th>Additional Value:<br /><em>(shown on asset detail page)</em></th>
			   <td><html:textarea name="listAttributeForm" property="value.additionalValue" rows="7"/></td>
			</tr>
			<c:if test="${not empty listAttributeForm.value.translations}">
				<logic:iterate name="listAttributeForm" property="value.translations" id="translation" indexId="tIndex">	
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="additionalValue<bean:write name="tIndex"/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<html:hidden name="listAttributeForm" property="value.translations[${tIndex}].language.id"/>
								<html:hidden name="listAttributeForm" property="value.translations[${tIndex}].language.name"/>
								<textarea id="additionalValue<bean:write name="tIndex"/>" name="value.translations[<bean:write name="tIndex"/>].additionalValue" rows="7"><bean:write name="translation" property="additionalValue" filter="false"/></textarea>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</c:if>
			<tr>
			   <th>Embedded data mapping value:</th>
			   <td><html:text  styleClass="text" name="listAttributeForm" property="value.mapToFieldValue" size="30" maxlength="200"/></td>
			</tr>
			<tr>
				<th nowrap="nowrap">Action when set:</th>
				<td>
					<html:select name="listAttributeForm" property="value.actionOnAssetId" styleId="actions">
						<html:option value="0">[none]</html:option>
						<html:optionsCollection name="listAttributeForm" property="actionsOnAssets" value="id" label="name"/>
					</html:select>
				</td>
			</tr>
			<tr>
			   <th>Icon File:<br /></th>
			   <td><html:file name="listAttributeForm" property="file" /></td>
			</tr>
         </table>
			<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-add" filter="false" />">
		</html:form>
	</logic:equal>
	<logic:greaterThan name="listAttributeForm" property="value.id" value="0">
		<form enctype="multipart/form-data" action="../action/updateAttributeListValue" method="post" >
			<html:hidden name="listAttributeForm" property="value.id"/>
			<html:hidden name="listAttributeForm" property="value.editable"/>
			<html:hidden name="listAttributeForm" property="value.attribute.id"/>
			<c:if test="${not listAttributeForm.value.editable}">
				<html:hidden name="listAttributeForm" property="value.value"/>
			</c:if>
			<h2>Edit <bean:write name="listAttributeForm" property="value.attribute.label"/>:</h2>
         
	      <table cellspacing="0" cellpadding="0" class="form">
            <tr>
               <th>Name:</th>
               <td>
               	<c:if test="${listAttributeForm.value.editable}">
               		<html:text styleClass="text" name="listAttributeForm" property="value.value" size="30" />
               	</c:if>
               	<c:if test="${not listAttributeForm.value.editable}">
               		<bean:write name="listAttributeForm" property="value.value"/>
               	</c:if>	
               </td>                                   
            </tr>
            <c:if test="${not empty listAttributeForm.value.translations}">
					<logic:iterate name="listAttributeForm" property="value.translations" id="translation" indexId="tIndex">
						<logic:greaterThan name="translation" property="language.id" value="0">
							<tr>
								<th class="translation">
									<label for="label<bean:write name="tIndex"/>">(<bean:write name="translation" property="language.name"/>):</label> 
								</th>
								<td>
									<html:hidden name="listAttributeForm" property="value.translations[${tIndex}].language.id"/>
									<html:hidden name="listAttributeForm" property="value.translations[${tIndex}].language.name"/>
									<input type="text" class="text" id="value<bean:write name="tIndex"/>" name="value.translations[<bean:write name="tIndex"/>].value" maxlength="255" value="<bean:write name="translation" property="value" filter="false"/>"/>
								</td>
							</tr>
						</logic:greaterThan>
					</logic:iterate>
				</c:if>
			<tr>
			   <th>Additional Value:<br /><em>(shown on asset detail page)</em></th>
			   <td>
					<c:if test="${listAttributeForm.value.editable}">
						<html:textarea name="listAttributeForm" property="value.additionalValue" rows="7"/>
					</c:if>
					<c:if test="${not listAttributeForm.value.editable}">
						<bean:write name="listAttributeForm" property="value.additionalValue"/>
					</c:if>
				</td>
			</tr>
			<c:if test="${not empty listAttributeForm.value.translations}">
					<logic:iterate name="listAttributeForm" property="value.translations" id="translation" indexId="tIndex">
						<logic:greaterThan name="translation" property="language.id" value="0">
							<tr>
								<th class="translation">
									<label for="additionalValue<bean:write name="tIndex"/>">(<bean:write name="translation" property="language.name"/>):</label> 
								</th>
								<td>
									<html:hidden name="listAttributeForm" property="value.translations[${tIndex}].language.id"/>
									<html:hidden name="listAttributeForm" property="value.translations[${tIndex}].language.name"/>
									<textarea id="additionalValue<bean:write name="tIndex"/>" name="value.translations[<bean:write name="tIndex"/>].additionalValue" rows="7"><bean:write name="translation" property="additionalValue" filter="false"/></textarea>
								</td>
							</tr>
						</logic:greaterThan>
					</logic:iterate>
				</c:if>
            <tr>
               <th>IPTC/EXIF Mapping:</th>
               <td><html:text  styleClass="text" name="listAttributeForm" property="value.mapToFieldValue" size="30" maxlength="200"/></td>
            </tr>
            <tr>
					<th nowrap="nowrap">Action when set:</th>
					<td>
						<html:select name="listAttributeForm" property="value.actionOnAssetId" styleId="actions">
							<html:option value="0">[none]</html:option>
							<html:optionsCollection name="listAttributeForm" property="actionsOnAssets" value="id" label="name"/>
						</html:select>
					</td>
				</tr>
				
				<tr>
				   	<th>Icon File:<br /></th>
				   	<td>
				   		<html:file name="listAttributeForm" property="file" /><br />
				   		
				   		<c:if test="${not empty listAttributeForm.value.iconFileName}">
							(<bright:cmsWrite identifier="current-file"/>: <c:out value="${listAttributeForm.value.iconFileName}"/>)
						</c:if>
						
						<c:if test="${ not empty listAttributeForm.value.iconFileName }">
							<br/>
							<label class="wrapping"><input type="checkbox" class="checkbox" name="removeFile" /> <bright:cmsWrite identifier="label-remove-working-file" filter="false"/></label>
						</c:if>
				   	</td>
				</tr>
			</table>
			<div style="text-align:left; ">
				<html:submit property="b_save" styleClass="button flush"><bright:cmsWrite identifier="button-save" filter="false"/></html:submit>
				<html:submit property="b_cancel" styleClass="button flush"><bright:cmsWrite identifier="button-cancel" filter="false"/></html:submit>
			</div>
		</form>
	</logic:greaterThan>

	
	<div class="hr"></div>
	
	<p><a href="../action/viewManageAttributes">&laquo; Back to attributes</a></p>
			
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>