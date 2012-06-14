<%@include file="../inc/doctype_html_admin.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		23-Feb-2006 	 	Created
	 d2		Matt Stevenson		24-Feb-2006		 	Modified links
	 d3    	Ben Browning   	21-Feb-2006    	HTML/CSS tidy up
	 d4    	Matt Woollard	23-Jan-2008    	Omit if image is of lower resolution:
	 d5		Matt Woollard   17-Dec-2008		Added option for selecting color space
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Usage Types</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/javascript" src="../js/usage-type-admin.js"></script>
	<script type="text/javascript" src="../tools/colorpicker/farbtastic.js"></script>
	<bean:define id="section" value="usageformat"/>
	<bean:define id="pagetitle" value="Usage Type Formats"/>
	<script type="text/javascript">
		$j(function()
		{
			// Show/hide form elements based on initial state
			allowMaskingChanged(<c:out value="${usageFormatForm.usageTypeFormat.allowMasking}"/>);
			cropToFitChanged(<c:out value="${usageFormatForm.usageTypeFormat.cropToFit}"/>);
			presetMaskIdChanged(<c:out value="${usageFormatForm.usageTypeFormat.presetMaskId}"/>)
			
			$j("#cropToFit").click(function(event)
			{
				cropToFitChanged(event.target.checked);
			});

			$j("#allowMasking").click(function(event)
			{
				allowMaskingChanged(event.target.checked);
			});

			//initialise color picker
			$j('#colorPicker').farbtastic('#backgroundColour');
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
			
			$j('#fillBackground').click(function(){
				bgColourToggle();
			})
			
			// if color field is empty - give it a default value (farbtastic doesn't work on empty fields bizzarly)
			if ($j('#backgroundColour').val() == ''){
				$j('#backgroundColour').val('#ffffff')
			}
			
			bgColourToggle();

		});
		
		function bgColourToggle() {
			// show hide bgcolour field depending on checkbox value
			if ($j('#fillBackground').is(':checked')) {
				$j('#bgColourRow').show();
			} else {
				$j('#bgColourRow').hide();
			}
		}
	</script>
</head>

<bean:define id="parentId" value="0"/>
<bean:define id="usageTypeId" value="0"/>
<c:if test="${usageFormatForm.usageType!=null}">
	<c:set var="usageTypeId" value="${usageFormatForm.usageType.id}" />  
	<c:set var="parentId" value="${usageFormatForm.usageType.parentId}" />
</c:if> 

<body id="adminPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 

	<logic:present  name="usageFormatForm">
		<logic:equal name="usageFormatForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="usageFormatForm" property="errors" id="error">
						<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>											

	<c:if test="${usageTypeId>0}">
		<h2>Manage Usage Type Formats for '<bean:write name="usageFormatForm" property="usageType.description"/>'</h2>
	</c:if>
	<c:if test="${usageTypeId<=0}">
		<h2>Manage Default Usage Type Formats</h2>
	</c:if>
	
	<logic:notEmpty name="usageFormatForm" property="usageTypeFormats">
	<table cellspacing="0" class="admin" summary="List of Usage Type Formats">
		<logic:iterate name="usageFormatForm" property="usageTypeFormats" id="usageTypeFormat">
			<tr>
				<td><bean:write name="usageTypeFormat" property="description"/></td>
				<td class="action">
					[<a href="viewUsageTypeFormats?id=<bean:write name='usageTypeId'/>&usageTypeFormatId=<bean:write name='usageTypeFormat' property='id'/>">edit</a>]
				</td>				
				<td class="action">
					[<a href="deleteUsageTypeFormat?usageTypeFormatId=<bean:write name='usageTypeFormat' property='id'/>&id=<bean:write name='usageTypeId'/>" onclick="return confirm('Are you sure you want to delete this usage type format?');">X</a>]
				</td>
			</tr>
		</logic:iterate>
	</table>		
	</logic:notEmpty>
	<logic:empty name="usageFormatForm" property="usageTypeFormats">
		<p>There are current no formats for this usage type.</p>
	</logic:empty>
	<div class="hr"></div>
	<p><span class="required">*</span> denotes a mandatory field.</p>
	<form action="../action/saveUsageTypeFormat" method="post">
		<input type="hidden" name="usageTypeFormat.usageTypeId" value="<bean:write name='usageTypeId'/>">
		<html:hidden name="usageFormatForm" property="usageTypeFormat.id"/>
		
		<logic:lessThan name="usageFormatForm" property="usageTypeFormat.id" value="1">
			<p>Add new usage type format:</p>
		</logic:lessThan>
		
		<table class="form" cellspacing="0" cellpadding="0">
			<tr>
				<th><label for="name">Name: <span class="required">*</span></label></th>
				<td><html:text name="usageFormatForm" property="usageTypeFormat.description" styleId="name" /></td>
			</tr>
			<logic:notEmpty name="usageFormatForm" property="usageTypeFormat.translations">
				<logic:iterate name="usageFormatForm" property="usageTypeFormat.translations" id="translation" indexId="index">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="name<bean:write name='index'/>">(<bean:write name="translation" property="language.name"/>):</label>
							</th>
							<td>
								<html:hidden name="usageFormatForm" property="usageTypeFormat.translations[${index}].language.id"/>
								<html:hidden name="usageFormatForm" property="usageTypeFormat.translations[${index}].language.name"/>
								<input type="text" class="text" name="usageTypeFormat.translations[<bean:write name='index'/>].description" maxlength="45" id="name<bean:write name='index'/>" value="<bean:write name="translation" property="description" filter="false"/>" />
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>	
			</logic:notEmpty>
			<tr>
				<th><label for="width">Max Width: <span class="required">*</span></label></th>
				<td><html:text name="usageFormatForm" property="widthString" styleClass="small" styleId="width" /> (pixels)</td>
			</tr>
			<tr>
				<th><label for="height">Max Height: <span class="required">*</span></label></th>
				<td><html:text name="usageFormatForm" property="heightString" styleClass="small" styleId="height" /> (pixels)</td>
			</tr>
			<tr>
				<th><label for="cropToFit">Crop to fit:</label></th>
				<td><html:checkbox name="usageFormatForm" property="usageTypeFormat.cropToFit" styleClass="checkbox" styleId="cropToFit"/></td>
			</tr>
			<c:choose>
				<%-- Dont show mask options when there are no masks defined, just add hidden fields to preserve data --%>
				<c:when test="${empty masks}">
					<html:hidden name="usageFormatForm" property="usageTypeFormat.allowMasking" styleId="allowMasking"/>
					<html:hidden name="usageFormatForm" property="usageTypeFormat.presetMaskId" styleId="presetMaskId"/>
					<html:hidden name="usageFormatForm" property="usageTypeFormat.presetMaskColourId" styleId="presetMaskColourId"/>
				</c:when>
				<c:otherwise>
					<tr>
						<th><label for="allowMasking">Allow masking:</label></th>

						<td><html:checkbox name="usageFormatForm" property="usageTypeFormat.allowMasking" styleClass="checkbox" styleId="allowMasking" /></td>
					</tr>
					<tr class="maskOptions">
						<th><label for="presetMaskId">Preset mask:</label></th>
						<td>
							<html:select name="usageFormatForm" property="usageTypeFormat.presetMaskId" size="1" styleId="presetMaskId" onchange="presetMaskIdChanged(this.value)" style="width:auto">
								<html:option value="0">None</html:option>
								<html:options collection="masks" labelProperty="name" property="id"/>
							</html:select>
						</td>
					</tr>
					<tr class="maskOptions">
						<th><label for="presetMaskColourId">Preset mask colour:</label></th>
						<td>
							<html:select name="usageFormatForm" property="usageTypeFormat.presetMaskColourId" size="1" styleId="presetMaskColourId" style="width:auto">
								<html:option value="0">None</html:option>
								<html:options collection="colours" labelProperty="name" property="id"/>
							</html:select>
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
			<tr>
				<th><label for="format">Convert to:</label></th>
				<td><bright:refDataList componentName="ImageAssetManagerImpl" methodName="getDownloadableImageFormats" id="downloadFormats"/>
					<html:select name="usageFormatForm" property="usageTypeFormat.formatId" size="1" style="width:auto">
						<html:options collection="downloadFormats" property="id" labelProperty="name"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<th><label for="preserveformatlist">Except for:</label></th>
				<td>
					<logic:iterate name="downloadFormats" id="format">
						<bean:define id="usageType" name="format" property="fileExtension" />
						<label for="usage_formats_<bean:write name="format" property="fileExtension" />"><c:out value="${format.name}" /></label>
						<html:multibox name="usageFormatForm" property="preserveFormatList" styleClass="checkbox" styleId="<%=\"usage_type_\"+usageType%>">
							<bean:write name="format" property="fileExtension" />
						</html:multibox>
					</logic:iterate>
				</td>
			</tr>
			<tr>
				<th><label for="density">Density:</label></th>
				<td><html:text name="usageFormatForm" property="usageTypeFormat.density" styleClass="small" styleId="density" /> (DPI)</td>
			</tr>
			<tr>
				<th><label for="jpgquality">JPEG Quality:</label></th>
				<td><html:text name="usageFormatForm" property="usageTypeFormat.jpegQuality" styleClass="small" styleId="jpgquality" /> (1-100, 0=default)</td>
			</tr>
			<tr>
				<th><label for="format">Color space:</label></th>
				<bean:define id="colorSpaces" name="usageFormatForm" property="colorSpaces"/>
				<td>
					<html:select  name="usageFormatForm" property="usageTypeFormat.colorSpace" size="1" style="width:auto">
						<html:option value="0">None</html:option>
						<html:options collection="colorSpaces" labelProperty="description" property="id" filter="false"/>
					</html:select>
				</td>
			</tr>
			<tr>
				<th><label for="scaleUp">Scale up:</label></th>
				<td><html:checkbox name="usageFormatForm" property="usageTypeFormat.scaleUp" styleClass="checkbox" styleId="scaleUp" /></td>
			</tr>
			<tr>
				<th><label for="applyStrip">Strip:</label></th>
				<td><html:checkbox name="usageFormatForm" property="usageTypeFormat.applyStrip" styleClass="checkbox" styleId="applyStrip" /></td>
			</tr>
			<tr>
				<th><label for="watermark">Watermark:</label></th>
				<td><html:checkbox name="usageFormatForm" property="usageTypeFormat.watermark" styleClass="checkbox" styleId="watermark" /></td>
			</tr>
			<tr>
				<th><label for="omitIfLowerRes">Omit if image is of lower resolution:</label></th>
				<td><html:checkbox name="usageFormatForm" property="usageTypeFormat.omitIfLowerRes" styleClass="checkbox" styleId="omitIfLowerRes" /></td>
			</tr>
			<tr>
				<th><label for="fillBackground">Fill background?:</label></th>
				<td>
					<html:checkbox name="usageFormatForm" property="usageTypeFormat.fillBackground" styleClass="checkbox" styleId="fillBackground" /><br />
					
					
				
				</td>
			</tr>
			<tr id="bgColourRow">
				<th><label for="hexString">Background Colour:<br /><span style="font-weight: normal">(Hex value)</span></label></th>
				<td>
					<html:text styleId="backgroundColour" name="usageFormatForm" property="usageTypeFormat.backgroundColour" size="7" maxlength="7" styleClass="small floated" />
					<a href="#" class="pickerLink js-enabled-show" style="margin-top:5px">Colour picker...</a><br />
					<div id="pickerHolder" style="clear:left; padding-top: 5px">
						<label>&nbsp;</label>
						<div id="colorPicker"></div>
						<a href="#" class="closePicker">Close</a>
						<br />
					</div>
				</td>
			</tr>
		</table>	
		
		<div class="hr"></div>	
		<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-save" filter="false" />" />
		<a href="../action/viewUsageTypes?parentId=<bean:write name='parentId' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
	</form>

	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>