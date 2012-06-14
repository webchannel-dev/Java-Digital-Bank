<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>

	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Mask Images</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/javascript" src="../tools/colorpicker/farbtastic.js"></script>
	<bean:define id="section" value="masks"/>
	<bean:define id="pagetitle" value="Download Options"/>
	<bean:define id="tabId" value="masks"/>
	<script type="text/JavaScript">
		$j(document).ready(function (){  
			//initialise color picker
			$j('#colorPicker').farbtastic('#hexString');
			//initially hide colorpicker
			$j('#pickerHolder').hide();
			$j('a.pickerLink').click(function(){
				$j('#pickerHolder').toggle('fast');
				return false;
				
			});
			$j('a.closePicker').click(function(){
				$j('#pickerHolder').hide('fast');
				return false;
			});
		});  
	</script>

	
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>


	<h1><bean:write name="pagetitle" filter="false"/></h1>
	<%@include file="inc_download_options_tabs.jsp"%>

	<logic:present name="maskForm">
		<logic:equal name="maskForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="maskForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>

	<h2>Mask Images</h2>


	<p>
	Mask images can be overlaid onto asset images when assets are downloaded.
	Mask images must be in a format that supports transparency, for example PNG.
	The transparent area of the mask image should correspond to the area of the
	asset image that you want to show through when the mask is applied. 
	</p>

	<logic:notEmpty name="masks">
	<table cellspacing="0" class="admin" summary="List of Mask Images">
		<tr>
			<th></th>
			<th>Name</th>
			<th>Size (pixels)</th>
			<th>Actions</th>
		</tr>
		<logic:iterate name="masks" id="mask" type="com.bright.assetbank.usage.bean.Mask">
			<tr>
				<td><img src="<%= request.getContextPath() %>/<bean:write name="mask" property="webappRelativeThumbnailPath"/>" alt="Mask thumbnail" style="border:1px solid #aaa; background-color: #a0a0a0"/></td>
				<td><bean:write name="mask" property="name"/></td>
				<td><bean:write name="mask" property="width"/> x <bean:write name="mask" property="height"/></td>
				<td>
					[<a id="deleteMask<bean:write name="mask" property="id"/>" href="deleteMask?maskId=<bean:write name="mask" property="id"/>"
					   onclick="return confirm('Are you sure you want to delete this mask image?');" title="Delete this mask image">X</a>]
				</td>
			</tr>
		</logic:iterate>
	</table>
	</logic:notEmpty>



	<c:choose>
		<c:when test="${maskForm.maskId == 0}">
			<form action="../action/addMask" method="post" enctype="multipart/form-data" class="floated">
				<h3>Add new mask:</h3>

				<input type="hidden" name="mandatory_file" value="Please choose an image file." />
				<%@include file="inc_mask_fields.jsp"%>

				<input id="addMask" type="submit" class="button flush" value="<bright:cmsWrite identifier="button-add" filter="false" />" /><br />
			</form>
		</c:when>
		<c:otherwise>
			<%-- Exit existing mask form will go here, if implemented --%>
		</c:otherwise>
	</c:choose>

	<div class="hr"></div>

	<h2>Colours</h2>
	<p>Preset colours, for example your company's brand colours, that can be chosen when applying masks.</p>

	<logic:present name="namedColourForm">
		<logic:equal name="namedColourForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="namedColourForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>

	<table cellspacing="0" class="admin" summary="List of Colours" style="margin-bottom:0">

		<logic:iterate name="colours" id="colour" type="com.bright.assetbank.usage.bean.NamedColour">
			<tr>
				<td><div style="background-color: #<bean:write name="colour" property="hexString" />; height:13px; width:13px;">&nbsp;</div></td>
				<td><bean:write name="colour" property="name"/></td>
				<td>
					[<a href="viewEditNamedColour?namedColourId=<bean:write name="colour" property="id"/>" title="Edit this colour">edit</a>]
					[<a href="deleteNamedColour?namedColourId=<bean:write name="colour" property="id"/>"
					   onclick="return confirm('Are you sure you want to delete this colour?');" title="Delete this colour">X</a>]
				</td>
			</tr>
		</logic:iterate>
	</table>

	<c:choose>
		<c:when test="${namedColourForm == null || namedColourForm.id <= 0}">
			<html:form action="addNamedColour" method="post" styleClass="floated">
				<h3>Add new colour:</h3>
				<%@include file="inc_named_colour_fields.jsp" %>
				<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-add" filter="false" />" />
			</html:form>
		</c:when>
		<c:otherwise>
			<html:form action="editNamedColour" method="post" styleClass="floated">
				<h3>Edit colour:</h3>
				<html:hidden property="id"/>
				<%@include file="inc_named_colour_fields.jsp" %>
				<input type="submit" class="button flush" value="<bright:cmsWrite identifier="button-save" filter="false" />" />
			</html:form>
		</c:otherwise>
	</c:choose>




	<%@include file="../inc/body_end.jsp"%>
</body>
</html>
