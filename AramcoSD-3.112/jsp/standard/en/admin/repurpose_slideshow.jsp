<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		27-Nov-2009		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bean:parameter name="type" id="type" value=""/>
<bean:parameter name="page" id="pageNumber" value="-1"/>
<bean:parameter name="pageSize" id="pageSize" value="-1"/>
<bean:parameter name="captionIds" id="captionIds" value=""/>
<bean:parameter name="displayTime" id="displayTime" value="5"/>
<bean:parameter name="labels" id="labels" value="false"/>
<bean:parameter name="fullList" id="fullList" value="false"/>
<bean:parameter name="sswidth" id="sswidth" value=""/>
<bean:parameter name="ssheight" id="ssheight" value=""/>
<bean:parameter name="dimensions" id="dimensions" value="pixel"/>
<bean:parameter name="advanced" id="advanced" value="0"/>
<bright:applicationSetting id="defaultSize" settingName="view-unwatermarked-large-image-size"/>
<bright:applicationSetting id="defaultJpgConversionQuality" settingName="jpeg-conversion-quality"/>
<bright:applicationSetting id="refreshPeriod" settingName="repurposed-slideshow-refresh-period"/>
<bright:applicationSetting id="applicationUrl" settingName="application-url"/>

<head>

	<bean:define id="section" value="repurpose"/>
	<bean:define id="helpsection" value="repurposedSlideshows"/>
	
	<title><bright:cmsWrite identifier="title-repurpose-slideshow" filter="false"/></title> 
	
	<%@include file="../inc/head-elements.jsp"%>	
	
	<script type="text/javascript" charset="utf-8">
		$j(function() {
			//Dom ready
			// listen for changes to the type of slideshow
			$j('input[name=displayType]').click(function(){
				var $this = $j(this)
				switchSlideshowType($this);
			});
			
			// initial slideshow fieds visibility
			switchSlideshowType($j('input[name=displayType]:checked'));
			
			// set status of checkbox 'default on homepage'
			defaultHomeStatus();
			
			$j('#showInListOnHomepage').click(defaultHomeStatus);
		})
		
		function switchSlideshowType($radio) {
			var thisVal = $radio.val();
			//alert(thisVal);
			if (thisVal == 1) {
				// Standard slideshow
				$j('#standardFields').show();
				$j('#photoEssayFields').hide();
			} 
			if (thisVal == 2) {
				// Photo essay
				$j('#photoEssayFields').show();
				$j('#standardFields').hide();
			}
		}
		
		function defaultHomeStatus() {
			var showHome = $j('#showInListOnHomepage').is(":checked");
			if (showHome) {
				$j('#defaultOnHomepage').removeAttr('disabled');
			} else {
				$j('#defaultOnHomepage').attr('disabled','disabled');
			}
		}
	</script>

</head>

<body <c:if test="${repurposeSlideshowForm.convertImages}">onload="document.getElementById('conversionDiv').style.display = 'block';"</c:if>>
		
	<%@include file="../inc/body_start.jsp"%> 

	<h1 class="underline"><bright:cmsWrite identifier="heading-repurpose-slideshow" filter="false" /></h1> 
	
	<logic:notEmpty name="repurposeSlideshowForm" property="errors">
		<div class="error">
			<logic:iterate name="repurposeSlideshowForm" property="errors" id="error">
				- <bean:write name="error" /><br />
			</logic:iterate>
		</div>
	</logic:notEmpty>

	<p>Select the size of the flash slideshow player you want to embed on an HTML page.</p> 
	<p>After setting the size of the slideshow itself, decide on the size of the images you want to display in the slideshow. You can either select to use the images exactly as the appear on the Asset Bank slideshow or you can convert the images by specifying the size and quality of images manually.</p>
	<p>When sizing the images they need to be appropriate for the size of slideshow you create (for instance, having 1000px tall images in a 500px tall slideshow would result in unnecessarily large images being loaded). If you are specifying the slideshow size in 'percentage of the page' the a rule of thumb for the image sizes is to take the same percentage of the maximum screen resolution you expect users viewing the slideshow to have:</p>

	

	<form action="repurposeSlideshow" method="get" id="usageForm" class="floated">
		<input type="hidden" name="type" id="type" value="<bean:write name='type'/>"/>
		<input type="hidden" name="pageSize" id="pageSize" value="<bean:write name='pageSize'/>"/>
		<input type="hidden" name="page" id="page" value="<bean:write name='pageNumber'/>"/>
		<input type="hidden" name="captionIds" id="captionIds" value="<bean:write name='captionIds'/>"/>
		<input type="hidden" name="displayTime" id="displayTime" value="<bean:write name='displayTime'/>"/>
		<input type="hidden" name="labels" id="labels" value="<bean:write name='labels'/>"/>
		<input type="hidden" name="fullList" id="fullList" value="<bean:write name='fullList'/>"/>

		<h2>Slideshow details</h2>
		
		<label for="displayType">Type:</label> 
		<ul class="radioList narrow">

			<li><input type="radio" class="radio" id="displayTypeStandard" name="displayType" value="1" <c:if test="${displayType!='2'}">checked</c:if>/><label for="displayTypeStandard">Standard</label></li>
			<li><input type="radio" class="radio" id="displayTypePhotoEssay" name="displayType" value="2" <c:if test="${displayType=='2'}">checked</c:if>/><label for="displayTypePhotoEssay">Photo Essay</label></li>
		
		</ul>	
		<br/>

		<label for='description'>Name:</label> <input type="text" name="description" id="description" length="100" value="<bean:write name='repurposeSlideshowForm' property='description'/>"/><br/>
		<label for="dimensions">Dimensions are in:</label> 
		<ul class="radioList narrow">	
			<li><input type="radio" class="radio" id="dimensionsPixel" name="dimensions" value="pixel" <c:if test="${dimensions=='pixel'}">checked</c:if>/><label for="dimensionsPixel"><bright:cmsWrite identifier="pixels" /></label></li>
			<li><input type="radio" class="radio" id="dimensionsPercent" name="dimensions" value="percent" <c:if test="${dimensions!='pixel'}">checked</c:if>/><label for="dimensionsPercent">percentage (of page)</label></li>
		</ul>
		<br/>


		<label for="ssWidth"><bright:cmsWrite identifier="label-width" /></label> <html:text name="repurposeSlideshowForm" property="slideshowWidth" size="10" styleClass="small" styleId="ssWidth" /><br/>
		
		<label for="ssHeight"><bright:cmsWrite identifier="label-height" /></label> <html:text name="repurposeSlideshowForm" property="slideshowHeight" size="10" styleClass="small" styleId="ssHeight" /><br/>
		
		<label for="showInListOnHomepage">Show in list on homepage:</label><html:checkbox name="repurposeSlideshowForm" property="showInListOnHomepage" value="true" styleId="showInListOnHomepage" styleClass="checkbox" /><br/>
		
		<label for="defaultOnHomepage">Default on homepage:</label><html:checkbox name="repurposeSlideshowForm" property="defaultOnHomepage" value="true" styleId="defaultOnHomepage" styleClass="checkbox" /><br/>
		
		<div id="photoEssayFields" class="js-enabledr-hide">
			<label for="credit">Credit: </label> 
			<html:select name="repurposeSlideshowForm" property="creditAttributeId">
				<html:option value="-1">[none]</html:option>
				<html:optionsCollection name="repurposeSlideshowForm" property="textAttributes" label="label" value="id"/>
			</html:select><br />
		
			<label for="caption">Caption: </label> 
			<html:select name="repurposeSlideshowForm" property="captionAttributeId">
				<html:option value="-1">[none]</html:option>
				<html:optionsCollection name="repurposeSlideshowForm" property="textAttributes" label="label" value="id"/>
			</html:select>
			<br/>
		</div>
		<div id="standardFields" class="js-enarbled-hide">
			<label>Select attributes to display:</label>
			<div class="holder" style="width: 600px">
			<logic:iterate name="repurposeSlideshowForm" property="attributes" id="attribute">
				<c:if test="${attribute.fieldName!='file'}">
					<c:if test="${(attribute.fieldName != 'price' || ecommerce) && attribute.isVisible}">
						<label for="requiredAttribute_<bean:write name='attribute' property='id'/>" class="after <c:if test="${attribute.fieldName != 'assetId'}">hideable</c:if>">
						<input type="checkbox" class="checkbox" name="requiredAttribute_<bean:write name='attribute' property='id'/>" value="true" id="requiredAttribute_<bean:write name='attribute' property='id'/>" <c:if test="${attribute.selected}">checked="checked"<c:set var="attributesSelected" value="true" /></c:if>/> <bean:write name="attribute" property="label"/> </label>
		
					</c:if>
				</c:if>
			</logic:iterate>
			</div>
			<br/>
		</div>		
		
		
		<div class="hr"></div>

		<h3>Image sizes</h3>

		<p>If you want to convert the images to a more suitable size for your slideshow as opposed to using the images at their full size, check the box below then fill in your desired sizes for the images in the form below:</p>
		<c:if test="${type == 'search' && refreshPeriod > 0}">
			<logic:notEmpty name='applicationUrl'>
				<label for="refresh">Refresh: </label>
				<bean:parameter name='refresh' id='refresh' value='0' />
				<input type="checkbox" name="refresh" value="1" id="refresh" class="checkbox" <c:if test="${refresh > 0}">checked</c:if> />
				<label for="refresh" class="after" style="width: 300px !important">Refresh this slideshow with new search results every <c:if test="${refreshPeriod>1}"><bean:write name='refreshPeriod'/> </c:if>hour<c:if test="${refreshPeriod>1}">s</c:if></label>
				<br />
			</logic:notEmpty>
		</c:if>
		<label>Convert: </label>
		<html:checkbox name="repurposeSlideshowForm" property="convertImages" value="true" onclick="if (this.checked) { document.getElementById('conversionDiv').style.display = 'block'; } else { document.getElementById('conversionDiv').style.display = 'none'; }" styleId="convert" styleClass="checkbox" />
		<label for="convert" class="after">Convert image sizes</label>
		<br />
		<div id="conversionDiv" style="display: none;">
			<label for="width"><bright:cmsWrite identifier="label-width" filter="false"/> </label>
			<input type="text" name="width" size="8" value="<c:choose><c:when test='${repurposeSlideshowForm.width > 0}'><bean:write name='repurposeSlideshowForm' property='width' /></c:when><c:otherwise><bean:write name='defaultSize'/></c:otherwise></c:choose>" class="small text"  id="width" /><span>px</span>
			<br />
			<label for="height"><bright:cmsWrite identifier="label-height" filter="false"/></label> 
			<input type="text" name="height" size="8" value="<c:choose><c:when test='${repurposeSlideshowForm.height > 0}'><bean:write name='repurposeSlideshowForm' property='height' /></c:when><c:otherwise><bean:write name='defaultSize'/></c:otherwise></c:choose>" class="small text"  id="height" /><span>px</span>
			<br />
			<label for="aspect">Aspect Ratio: </label>
			<bean:parameter name='maintainAspectRatio' id='maintainAspectRatio' value='0' />
			<input type="checkbox" name="maintainAspectRatio" value="1" id="aspect" class="checkbox" <c:if test="${maintainAspectRatio == 1 || !repurposeSlideshowForm.hasErrors}">checked</c:if> />
			<label for="aspect" class="after">Lock Aspect Ratio (recommended)</label>
			<br />
			<label for="jpegQuality"><bright:cmsWrite identifier="label-jpeg-quality" filter="false"/> </label>
			<input type="text" name="jpegQuality" size="8" value="<c:choose><c:when test='${repurposeSlideshowForm.jpegQuality > 0}'><bean:write name='repurposeSlideshowForm' property='jpegQuality' /></c:when><c:otherwise><c:out value='${defaultJpgConversionQuality*100}'/></c:otherwise></c:choose>" class="small text"  id="jpegQuality" /> <span>(Scale is from 1 to 100, where 100 is maximum quality)</span>
			<br />
		</div>

		<div class="hr"></div>

		<input type="submit" name="submitButton" value="Create embedded slideshow &raquo;" class="button flush"/>

	</form>
	

	<!-- end of left col -->
	<div class="clearing">&nbsp;</div>

   	  
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>
