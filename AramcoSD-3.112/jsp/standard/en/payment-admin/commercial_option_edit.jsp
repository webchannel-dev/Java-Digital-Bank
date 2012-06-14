<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Kevin Bennett 04-Jan-2007		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	<logic:equal name="commercialOptionForm" property="commercialOption.id" value="0">	
		<bean:define id="pagetitle" value="Add Commercial Option"/>
	</logic:equal>
	<logic:notEqual name="commercialOptionForm" property="commercialOption.id" value="0">	
		<bean:define id="pagetitle" value="Edit Commercial Option"/>
	</logic:notEqual>

	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bean:write name="pagetitle" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="payment"/>	
	<script type="text/javascript" src="../js/tiny_mce/tiny_mce.js"></script>
	<script type="text/javascript">
		<!-- 
		tinyMCE.init({
			mode : "specific_textareas",
			theme : "advanced",
			theme_advanced_buttons1 : "bold,italic,underline,forecolor,charmap,separator,justifyleft,justifycenter,justifyright, justifyfull,bullist,numlist,undo,redo,link,unlink",
			theme_advanced_buttons2 : "image,cleanup,removeformat,separator,formatselect,",
			theme_advanced_buttons3 : "",
			theme_advanced_toolbar_location : "top",
			theme_advanced_toolbar_align : "left",
			theme_advanced_path_location : "bottom",
			content_css : "../css/standard/global.css",
			editor_selector : "editor"
		}); 
		//-->
	</script>
</head>

<body id="adminPage" >
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 

	<logic:present  name="commercialOptionForm">
		<logic:equal name="commercialOptionForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="commercialOptionForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											
	
	<html:form action="saveCommercialOption" method="post" focus="commercialOption.name">
		
		<%-- Common price band fields --%>
		<html:hidden name="commercialOptionForm" property="commercialOption.id" />

		<%-- Pass thru the id in case of validation error --%>
		<input type="hidden" name="id" value="<c:out value='${commercialOptionForm.commercialOption.id}' />" />		

		<table cellspacing="0" class="form" summary="Form for commercial option details">
			<tr>												
				<th>
					<label for="name">Name:</label>
				</th>
				<td>
					<html:text styleClass="text" styleId="name" name="commercialOptionForm" property="commercialOption.name" />
				</td>													
			</tr>
			<logic:notEmpty name="commercialOptionForm" property="commercialOption.translations">
				<logic:iterate name="commercialOptionForm" property="commercialOption.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="commercialOption<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<html:hidden name="commercialOptionForm" property="commercialOption.translations[${tIndex}].language.id"/>
								<html:hidden name="commercialOptionForm" property="commercialOption.translations[${tIndex}].language.name"/>
								<input type="text" class="text" name="commercialOption.translations[<bean:write name='tIndex'/>].name" maxlength="40" id="commercialOption<bean:write name='tIndex'/>" value="<bean:write name="translation" property="name" filter="false"/>"/>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
			<tr>												
				<th>
					<label for="description">Description:</label>
				</th>
				<td>
					<html:textarea styleClass="text" styleId="description" name="commercialOptionForm" property="commercialOption.description" rows="4" style="width: 200px;"/>
				</td>													
			</tr>
			<logic:notEmpty name="commercialOptionForm" property="commercialOption.translations">
				<logic:iterate name="commercialOptionForm" property="commercialOption.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="commercialOptionDesc<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<textarea class="text" name="commercialOption.translations[<bean:write name='tIndex'/>].description" rows="4" style="width: 200px;" id="commercialOptionDesc<bean:write name='tIndex'/>"><bean:write name="translation" property="description" filter="false"/></textarea>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
			<tr>												
				<th>
					<label for="price">Price:</label>
				</th>
				<td>
					<html:text styleClass="text" styleId="price" name="commercialOptionForm" property="commercialOption.price.formAmount" size="16" maxlength="16"/>
				</td>													
			</tr>
		</table>
		<div class="hr"></div>	
		<table cellspacing="0" class="form" summary="Form for commercial option details">
			<tr>												
				<th>
					<label for="terms">Terms:</label>
				</th>
				<td>
					<html:textarea styleClass="editor" styleId="terms" name="commercialOptionForm" property="commercialOption.terms" rows="16"  style="width: 600px;" />
				</td>													
			</tr>
			<logic:notEmpty name="commercialOptionForm" property="commercialOption.translations">
				<logic:iterate name="commercialOptionForm" property="commercialOption.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="commercialOptionTerms<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<textarea class="editor" name="commercialOption.translations[<bean:write name='tIndex'/>].terms" rows="16" style="width: 600px;" id="commercialOptionTerms<bean:write name='tIndex'/>"><bean:write name="translation" property="terms" filter="false"/></textarea>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>	
		</table>
		
	   <div class="hr"></div>	
		
		<div class="buttonHolder">
			<input type="submit" class="button" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		</div>		
	</html:form>
				
	<form name="cancelForm" action="viewCommercialOptionAdmin" method="get">
		<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button" id="cancelButton">
	</form>

	<%@include file="../inc/body_end.jsp"%>
</body>
</html>