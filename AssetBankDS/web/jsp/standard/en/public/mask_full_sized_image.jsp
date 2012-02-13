<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Francis Devereux	30-Mar-2009	Created based on crop_full_sized_image.jsp.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bean:parameter name="id" id="imageId" value="0" />
<bean:parameter name="canChooseMask" id="canChooseMask" value="true" />
<bean:parameter name="canChooseMaskColour" id="canChooseMaskColour" value="true" />

<head>
	<%-- BBTODO - head-elements.jsp had to be pasted in here because in IE the prototype library stopped the drag and drop stuff working. Switch back to using head-elements.jsp after jQuery switchover --%>	

	<title><bright:cmsWrite identifier="company-name" filter="false" />| Full-size Image</title> 
	<bean:define id="section" value="download"/>
	<%-- force struts to output xhtml	--%>
	<html:xhtml/>
	
	<meta http-equiv="content-type" content="text/html;charset=UTF-8" />
	
	<%-- Colour scheme --%>
	
	<!-- Stylesheet to sort out display issues of right to left text --> 
	<c:if test="${dirValue == 'rtl'}"><link href="../css/standard/rtl.css" rel="stylesheet" type="text/css" media="all" /></c:if>
   <!-- Link to default stylesheet -->
   <link href="../css/standard/default.css" rel="stylesheet" type="text/css" media="all" />

	<logic:equal name="section" value="home">
		<link href="../css/standard/home.css" rel="stylesheet" type="text/css" media="all" />
	</logic:equal>
		

   <!-- Link to stylesheets for IE/Win -->
	<!--[if IE 5]>
   <link rel="stylesheet" type="text/css" href="../css/standard/ie-5.css" />
   <![endif]-->
	<!--[if IE 6]>
   <link rel="stylesheet" type="text/css" href="../css/standard/ie-6.css" />
   <![endif]-->
	<!--[if IE 7]> 
   <link rel="stylesheet" type="text/css" href="../css/standard/ie-7.css" />
	<![endif]-->
	<!--[if IE 8]> 
   <link rel="stylesheet" type="text/css" href="../css/standard/ie-8.css" />
	<![endif]-->


	<link href="../css/standard/colour-scheme.css" rel="stylesheet" type="text/css" media="all" /> 
		
	<!-- Link to print styesheet -->
	<link href="../css/standard/print.css" rel="stylesheet" type="text/css" media="print" />	
	<script type="text/javascript" src="../js/lib/jquery-1.4.2.min.js"></script>
	<script type="text/javascript">$j = jQuery.noConflict();</script>
	<script src="../js/lib/jquery-ui-1.8.5.custom.min.js" type="text/javascript"></script>
	<script type="text/javascript" src="../tools/colorpicker/farbtastic.js"></script>
	<link type="text/css" href="../css/jquery-ui/jquery-ui-1.8.5.custom.css" rel="Stylesheet" />
	<script src="../js/lib/supersleight.plugin.js" type="text/javascript"></script>
	
	<style type="text/css">
		/* dynamically define bg image and dimensions of wrapper */
		#masks div.wrapper {
			width:<c:out value="${originalWidth +12}" />px;
			height:<c:out value="${originalHeight +12}" />px;
			background: url(../servlet/display?file=<bean:write name='file'/>) 6px 6px no-repeat;
		}
				
		/* check if any position and size parameters have been passed in */
		<c:choose>

			<c:when test="${maskHeight != null && maskWidth != null && maskTop != null && maskLeft != null}">
				/* Parameters have been passed, use passed in values to position mask */	
				img#mask {
					height: <c:out value="${maskHeight}" />px;
					width: <c:out value="${maskWidth}" />px;
					top: <c:out value="${maskTop}" />px;
					left: <c:out value="${maskLeft}" />px;
				}
				* html img#mask {
					height: <c:out value="${maskHeight}" />px;
					width: <c:out value="${maskWidth}" />px;
					top: <c:out value="${maskTop}" />px;
					left: <c:out value="${maskLeft}" />px;
				}
			</c:when>
			
			<c:otherwise>
				/* Parameters have not been passed, so use default values to center the mask (if a mask is selected) */
				<c:if test="${selectedMask != null}">
					img#mask {
						height: <c:out value="${defaultMaskHeight}" />px;
						width: <c:out value="${defaultMaskWidth}" />px;
						top:<c:out value="${defaultMaskTop}" />px;
						left: <c:out value="${defaultMaskLeft}" />px;
					}
					* html img#mask {
						height: <c:out value="${defaultMaskHeight}" />px;
						width: <c:out value="${defaultMaskWidth}" />px;
						top:<c:out value="${defaultMaskTop+6}" />px;
						left: <c:out value="${defaultMaskLeft+6}" />px;
					}
				</c:if>
			</c:otherwise>
	
		</c:choose>		
	</style>
	<script type="text/javascript">
		<!--
			//initialise global variables
			var selectedMask = "<c:out value="${selectedMask.id}"/>";
			var selectedColor = "<c:out value="${maskColour}"/>";
			var maskPosition = {left:0,top:0};
			var maskSize = {width:0,height:0};

			/* check if any position and size parameters have been passed in */
			<c:choose>
				<c:when test="${maskHeight != null && maskWidth != null && maskTop != null && maskLeft != null}">
					/* Parameters have been passed, store them in global variables */	
					maskSize.height = <c:out value="${maskHeight}" />;
					maskSize.width = <c:out value="${maskWidth}" />;
					maskPosition.top = <c:out value="${maskTop}" />;
					maskPosition.left = <c:out value="${maskLeft}" />;
				</c:when>
			
				<c:otherwise>
					/* Parameters have not been passed, so use default values to center the mask (if a mask is selected) */
					<c:if test="${selectedMask != null}">
						maskSize.height = <c:out value="${defaultMaskHeight}" />;
						maskSize.width = <c:out value="${defaultMaskWidth}" />;
						maskPosition.top = <c:out value="${defaultMaskTop}" />;
						maskPosition.left = <c:out value="${defaultMaskLeft}" />;
					</c:if>
				</c:otherwise>
			</c:choose>			
			var customColourFlag = false;
			//check DOM ready
			$j(function() {
			
				//deal with pngs
				$j('body').supersleight({shim: '../images/standard/misc/x.gif',backgrounds: false});
				
				//get reference to mask
				var mask = $j("#mask");
				
				//make mask resizeable and draggable
				mask.resizable({
					aspectRatio: true,
					containment: '#cage',
					stop: function(event,ui) {
						$j('#size').html(mask.width()+", "+mask.height());
						//update global variable with mask position and size (accounting for 6px border)
						maskPosition.left = ui.position.left;
						maskPosition.top = ui.position.top;
						maskSize.width = ui.size.width - 12;	
						maskSize.height = ui.size.height -12;	
						//$j('#output').text('left: '+maskPosition.left+' top: '+maskPosition.top);
					}
				})
				.parent()
				.draggable({
					containment: '#cage',
					stop: function(event,ui) {
						$j('#size').html(mask.width()+", "+mask.height());
						//update global variable with mask position
						//	maskPosition = ui.position;
						maskPosition.left = ui.position.left;
						maskPosition.top = ui.position.top;
						//$j('#output').text('left: '+maskPosition.left+' top: '+maskPosition.top);
					}
				})
				
				//click event when selecting a mask
				$j('#maskOptions li a').click(function() {
					$j('#loading').show();
					
				});
				
				//click event when selecting a colour (from the list of defined colours)
				var colourOptions = $j('#colourOption li a')
				colourOptions.click(function(event) {
					// a predefined colour has been clicked
					colourOptions.removeClass('selected');
					$j(this).addClass('selected');
					//reset custom colour flag and hide custom colour area
					customColourFlag = false;
					toggleCustomColour(event,true);
					// get colour value (which is stored in the alt attribute of the colour option link)
					selectedColor = $j(this).attr('alt');
					return false;
				});
				
				//link to show custom colour field
				$j('#specifyColour').click(function(event) {
					toggleCustomColour(event);
					colourOptions.removeClass('selected');
					return false;
				});
				
				//initialise color picker
				if($j('#colorPicker').length>0){
					//initialise color picker
					$j('#colorPicker').farbtastic('#hexString');
					//initially hide colorpicker
					$j('#pickerHolder').hide();
					$j('a.pickerLink').click(function(){
						$j('#pickerHolder').toggle('fast');
						return false;
				
					});
					//click event on colorpicker close link
					$j('a.closePicker').click(function(){
						$j('#pickerHolder').hide('fast');
						return false;
					});
				}
				//click event on toggle view all masks link (if it exists)
				if ($j('#toggleMasks').length > 0){
					$j('#toggleMasks').click(function(){
						$j('#maskOptions li.hidden').toggle('fast');
						$j(this).toggleClass('collapse_up');
						return false;
					});
				}	
				
			});
			
			//update parent window when window closes
			function applyMaskChanges(){
				//check whether a custom color has been inputted (and the flag is true)
				if (customColourFlag) {
					//update global variable
					selectedColor = $j('#hexString').attr('value');
					//remove #
					selectedColor = selectedColor.replace('#','');
				}
				// call onEndMask function in download-form.js
				this.opener.downloadForm.onEndMask(selectedMask,maskPosition,maskSize,selectedColor);
				//finally, close the popup window
				window.close();
			}
			
			function panelSelectorHighlight(element) {
				// function to highlight a selected panel (either a colour or a mask)
				colourOptions.removeClass('selected');
				$j(element).addClass('selected');
			}
			
			function toggleCustomColour(evt,hide) {
				// function to show or hide the custom colour input field
				if(hide) {
					// called from something other then a click on the expander link, hide content and reset expander link
					$j('#customColour').hide('fast');
					$j('#specifyColour').removeClass('collapse');
				} else {
					// Specify colour link was clicked (evt.target = expandable link)
					$j('#customColour').toggle('fast');
					$j('#specifyColour').toggleClass('collapse');
					//set custom colour flag
					customColourFlag = true;
				}	
				
			}

		-->
	</script>	
</head>

<body id="masks" <c:if test="${!canChooseMask}">class="noSteps"</c:if>>
	<h1><bright:cmsWrite identifier="heading-mask-configuration" filter="false"/></h1>
	<div class="inner">
	<c:if test="${canChooseMask}">
		<h2><span><bright:cmsWrite identifier="snippet-step-1" filter="false"/>&nbsp;</span> <bright:cmsWrite identifier="heading-choose-mask" filter="false"/> </h2>
		<div class="section noRightPadding">
		
			<ul class="panelSelector" id="maskOptions">
				<logic:iterate name="masks" id="mask" type="com.bright.assetbank.usage.bean.Mask" indexId="maskIndex">
					<li <c:if test="${maskIndex > 2}">class="hidden"</c:if>>
						<a href="viewMask?id=<c:out value="${imageId}" />&amp;size=400&amp;maskId=<bean:write name="mask" property="id"/>" <c:if test="${mask.id == selectedMask.id}">class="selected"</c:if>>
							<span>
								<img src="<%= request.getContextPath() %>/<bean:write name="mask" property="webappRelativeThumbnailPath"/>" alt="Mask thumbnail" />
							</span>
							<strong><bean:write name="mask" property="name"/></strong><br />
							<bean:write name="mask" property="width"/> x <bean:write name="mask" property="height"/>px

						</a>
					</li>
				</logic:iterate>
			</ul>
			<c:if test="${maskIndex > 2}">
				<p><a href="#" id="toggleMasks" class="expand" ><bright:cmsWrite identifier="link-show-all" filter="false"/></a></p>
			</c:if>	
		</div>	
	
	</c:if>
	
	<h2><span><bright:cmsWrite identifier="snippet-step-2" filter="false"/>&nbsp;</span> <bright:cmsWrite identifier="heading-position-mask" filter="false"/></h2>
	<div class="section">
		<img src="../images/standard/misc/ajax_loader_grey.gif" width="16" height="16" id="loading" alt="ajax_loader_grey" style="float:right; display: none;" />
		

		<c:choose>
			<c:when test="${selectedMask == null}">
				<p><bright:cmsWrite identifier="snippet-no-mask-selected" filter="false"/></p>
			</c:when>
			<c:otherwise>
				<div class="wrapper" id="cage">
					<img src="<%= request.getContextPath() %>//<c:out value="${selectedMask.webappRelativeImagePath}"/>" alt="mask" id="mask"  />
				</div>
			</c:otherwise>
		</c:choose>	
		
		<!-- <p id="output"></p> -->

	</div>
	
	<c:if test="${canChooseMaskColour}">
		<h2><span><bright:cmsWrite identifier="snippet-step-3" filter="false"/>&nbsp;</span> <bright:cmsWrite identifier="heading-choose-bg-color" filter="false"/></h2>
		<div class="section noRightPadding">
			
			<p><bright:cmsWrite identifier="snippet-if-no-bg" filter="false"/></p>
			
			<%-- initialise flag to tell us whether a stored colour (if any) is a custom colour --%>
			<c:choose>
				<c:when test="${maskColour != null}">
					<bean:define id="colourIsCustom" value="true"/>
				</c:when>
				<c:otherwise>
					<bean:define id="colourIsCustom" value="false"/>
				</c:otherwise>
			</c:choose>
			<logic:notEmpty name="colours">
			
			<ul class="panelSelector" id="colourOption">
				<logic:iterate name="colours" id="colour" type="com.bright.assetbank.usage.bean.NamedColour">
					<li>
						<a href="#" alt="<bean:write name="colour" property="hexString"/>" <c:if test="${maskColour == colour.hexString}"> class="selected" </c:if>>
						<span style="background-color:#<bean:write name="colour" property="hexString"/>">&nbsp;</span>

						<strong><bean:write name="colour" property="name"/></strong><br />
						(#<bean:write name="colour" property="hexString"/>)
						</a>
					</li>
					<c:if test="${maskColour == colour.hexString}">
						<%-- passed in colour matches a preset, therefore not a custom colour --%>
						<bean:define id="colourIsCustom" value="false"/>
					</c:if>
				</logic:iterate>
			</ul>
			</logic:notEmpty>
		
			<a href="#" id="specifyColour" class="expand <c:if test="${colourIsCustom}">collapse</c:if>" ><bright:cmsWrite identifier="link-specify-colour" filter="false"/></a>
			<div id="customColour" <c:if test="${!colourIsCustom}">style="display:none;"</c:if>>
				<input type="text" id="hexString" name="hexString" size="7" maxlength="7" class="small" 
					<c:choose>
						<c:when test="${maskColour != null}">value="#<c:out value="${maskColour}" />" </c:when>
						<c:otherwise>value="#<logic:notEmpty name="colours">ffffff</logic:notEmpty>"</c:otherwise>
					</c:choose>	
					/>
				<a href="#" class="pickerLink"><bright:cmsWrite identifier="link-colour-picker" filter="false"/></a><br />
				<div id="pickerHolder" style="clear:left">
					<div id="colorPicker"></div>
					<a href="#" class="closePicker"><bright:cmsWrite identifier="button-close" filter="false"/></a>
					<br />
				</div>
			</div>
			
			

		</div>
	
	</c:if>
	
	<p><input type="button" value="Apply" class="button flush floated" id="submitButton" onclick="applyMaskChanges();" />
	<a href="#" class="cancelLink" onclick="window.close();">Cancel</a>
	</p>
	<br /><br />
	
	</div>

</body>
</html>