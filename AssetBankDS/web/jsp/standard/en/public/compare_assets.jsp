<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard	12-Jan-2009		Created
	 d2     Matt Woollard       23-Jul-2009     Allow printing from lightbox
	 d3		Ben Browning		26-Apr-2010		jQuery switchover
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="title-contact-sheet" filter="false"/></title> 
	<bean:define id="section" value="contact-sheet"/>
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/JavaScript">
		//when DOM loaded...      
      $j(function() {
      	// initially hide page control panel
	   	$j('#controlInner').hide();
      	// hide unneccesary attributes and show note if current layout is greater than 4
	   	<c:if test="${printImageForm.assetsPerPage>4}">
      		$j('#changeAttributes label.hideable').hide();
      		$j('#atrrNote').show();
      	</c:if>
      	//stripey tables
    		$j('table.stripey').each(function() {
				$j(this).find('tr:even').addClass('even');
			});
      });
      function showAttributes(clickedRadio) {
      	$j('#changeAttributes label.hideable').show();
      	$j('#atrrNote').hide();
      	highlightLabel(clickedRadio);
      }
      function hideAttributes(clickedRadio) {
      	$j('#changeAttributes label.hideable').hide();
      	$j('#atrrNote').show();
      	highlightLabel(clickedRadio);
      }
      function highlightLabel(clickedRadio) {
      	$j('#layoutList li').removeClass('selected');
      	$j(clickedRadio).parents('li:eq(0)').addClass('selected');
      }
	</script>
	

</head>
<body id="compare">
	<%@include file="../inc/body_start.jsp"%>
	
	<bean:define id="action" value="compareAssets"/>
	<c:set var="assetForm" value="${printImageForm}" />
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-contact-sheet" filter="false"/></h1>
	<!-- <p><bright:cmsWrite identifier="intro-compare-images" filter="false"/></p> -->
	<div class="ww printHide">
	<p><a href="viewAssetBox" class="printHide"><bright:cmsWrite identifier="link-back-lightbox" filter="false"/></a></p>
	</div>
	
	<bean:define id="controlPanelForm" name="printImageForm"/>

	<logic:notPresent name="close">
		<bean:define id="close" value="false"/>
	</logic:notPresent>
	<bean:parameter id="returnUrl" name="returnUrl" value="viewHome"/>
	<div id="controlPanel">
		<div class="header">
			<a href="#" onclick="window.print();" class="print js-enabled-show"><bright:cmsWrite identifier="link-print" filter="false"/></a>
			<a href="#" class="expand white" onclick="toggleControls($j('#controlInner'),$j(this)); return false;" id="controlsToggle"><bright:cmsWrite identifier="link-configure-contact-sheet" filter="false"/></a>&nbsp;
			<span>
				<bean:define id="numPerPage" name="controlPanelForm" property="assetsPerPage"/>
				<c:choose>
					<c:when test="${controlPanelForm.assetsPerPage == 1}">
						<bright:cmsWrite identifier="snippet-currently-x-image-pp" filter="false" replaceVariables="true" />
					</c:when>
					<c:otherwise>
						<bright:cmsWrite identifier="snippet-currently-x-images-pp" filter="false" replaceVariables="true" />
					</c:otherwise>
				</c:choose>	
			</span>
		</div>
		<div class="inner" id="controlInner">	

		<form action="<bean:write name='action'/>" >

			<fieldset id="layoutSelector">
				<h3><bright:cmsWrite identifier="subhead-choose-page-layout" filter="false"/></h3>
				<ul id="layoutList">
					<li <c:if test="${controlPanelForm.assetsPerPage == 1}">class="selected"</c:if>><input type="radio" name="assetsPerPage" value="1" id="page1" <c:if test="${controlPanelForm.assetsPerPage == 1}">checked="checked"</c:if> onclick="showAttributes(this)" /><label for="page1" class="layout1"><strong><bright:cmsWrite identifier="snippet-1-image-per-page" filter="false"/></strong><br /><bright:cmsWrite identifier="snippet-with-attributes" filter="false"/><br /></label></li>
					<li <c:if test="${controlPanelForm.assetsPerPage == 2}">class="selected"</c:if>><input type="radio" name="assetsPerPage" value="2" id="page2" <c:if test="${controlPanelForm.assetsPerPage == 2}">checked="checked"</c:if> onclick="showAttributes(this)" /><label for="page2" class="layout2"><strong><bright:cmsWrite identifier="snippet-2-images-per-page" filter="false"/></strong><br /><bright:cmsWrite identifier="snippet-with-attributes" filter="false"/><br /></label></li>
					<li <c:if test="${controlPanelForm.assetsPerPage == 4}">class="selected"</c:if>><input type="radio" name="assetsPerPage" value="4" id="page4" <c:if test="${controlPanelForm.assetsPerPage == 4}">checked="checked"</c:if> onclick="showAttributes(this)" /><label for="page4" class="layout4"><strong><bright:cmsWrite identifier="snippet-4-images-per-page" filter="false"/></strong><br /><bright:cmsWrite identifier="snippet-with-attributes" filter="false"/><br /></label></li>
					<li <c:if test="${controlPanelForm.assetsPerPage == 8}">class="selected"</c:if>><input type="radio" name="assetsPerPage" value="8" id="page8" <c:if test="${controlPanelForm.assetsPerPage == 8}">checked="checked"</c:if> onclick="hideAttributes(this)" /><label for="page8" class="layout8"><strong><bright:cmsWrite identifier="snippet-8-images-per-page" filter="false"/></strong><br /><bright:cmsWrite identifier="snippet-without-attributes" filter="false"/><br /></label></li>
					<li <c:if test="${controlPanelForm.assetsPerPage == 16}">class="selected"</c:if>><input type="radio" name="assetsPerPage" value="16" id="page16" <c:if test="${controlPanelForm.assetsPerPage == 16}">checked="checked"</c:if> onclick="hideAttributes(this)" /><label for="page16" class="layout16"><strong><bright:cmsWrite identifier="snippet-16-images-per-page" filter="false"/></strong><br /><bright:cmsWrite identifier="snippet-without-attributes" filter="false"/><br /></label></li>
					<li <c:if test="${controlPanelForm.assetsPerPage == 20}">class="selected"</c:if>><input type="radio" name="assetsPerPage" value="20" id="page20" <c:if test="${controlPanelForm.assetsPerPage == 20}">checked="checked"</c:if> onclick="hideAttributes(this)" /><label for="page20" class="layout20"><strong><bright:cmsWrite identifier="snippet-20-images-per-page" filter="false"/></strong><br /><bright:cmsWrite identifier="snippet-without-attributes" filter="false"/><br /></label></li>
				</ul>

			</fieldset>

				
				 
			<fieldset  id="changeAttributes">
				<input type="hidden" name="attributesSelected" value="true"/>
						
				<%@include file="inc_control_panel_attributes.jsp"%>
				
				<div class="clearing"></div>
				<p id="atrrNote" style="display:none; margin: 0;"><bright:cmsWrite identifier="Snippet-only-id-shown" filter="false"/></p>
			</fieldset>
	   
			
			<div class="hr"></div>
				
	
			<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-apply-changes" filter="false"/>" />
			<a href="#" class="cancelLink" onclick="toggleControls($j('#controlInner'),$j('#controlsToggle')); return false;"><bright:cmsWrite identifier="button-cancel" filter="false"/></a>
	
		

			<c:if test="${attributeSelected}">
				<input type="hidden" name="attributesSelected" value="true"/>
			</c:if>
			 
		</form> 
			 
		</div><!-- end of div.inner -->
	</div>
	<br />
	<div <c:if test="${!attributesSelected}">class="noAttributes"</c:if>>
	
		<div id="compareLayout<bean:write name="controlPanelForm" property="assetsPerPage" />">
			<bean:size id="numAssets" name="printImageForm" property="assets"/>
	
		<logic:iterate name="printImageForm" property="assets" id="asset" indexId="counter">
			<div class="compare">
		   	<table cellspacing="0" cellpadding=0" class="imageTable">
		   		<tr>
		   			<td class="imageBox">
						<img src="../servlet/display?file=<bean:write name="asset" property="comparisonImageFile.path"/>" alt=""/>
		   			</td>
		   		</tr>
		   		<tr>
		   			<td>
		   				<div class="imageAttributes">
		               	<%-- Display all required attributes --%>
					   		<c:set var="comparingAssets" value="${true}"/>
		               	<c:set var="hideFeatured" value="${true}"/>
		               	<c:set var="hidePromoted" value="${true}"/>
		               	<c:set var="hideApprovalStatus" value="${true}"/>
		               	<c:set var="hideRestricted" value="${true}"/>
			           	<c:set var="hideBrandTemplates" value="${true}"/>
					   	<c:set var="hideAccessLevels" value="${true}"/>
						<c:set var="fileSize" value="${asset.fileSizeInBytes/(1024*1024)}"/>
						
									<%@include file="inc_attribute_fields.jsp"%>	
		
		            		</div>
		   				</td>
		   			</tr>
		   		</table>
		   
				</div>
				<c:choose>
					<c:when test="${printImageForm.assetsPerPage<16}">
						<c:if test="${(counter+1)%2 == 0 && (printImageForm.assetsPerPage%2==0 || (counter+1)<printImageForm.assetsPerPage)}">
							<div class="clearing"></div>
						</c:if>
					</c:when>
					<c:otherwise>
						<c:if test="${(counter+1)%4 == 0 && ((counter+1)<printImageForm.assetsPerPage)}">
							<div class="clearing"></div>
						</c:if>
					</c:otherwise>
				</c:choose>		
			
				<c:if test="${printImageForm.assetsPerPage!=0 && ((counter+1)%printImageForm.assetsPerPage == 0) && counter+1!=numAssets}">

				
					<div class="pageBreak"><bright:cmsWrite identifier="snippet-page-break" filter="false"/></div>

				
				</c:if>
		
			</logic:iterate>
		</div>
	
	</div>
	<div class="clearing"></div>
	<p><a href="viewAssetBox" class="printHide"><bright:cmsWrite identifier="link-back-lightbox" filter="false"/></a></p>
	
	<%@include file="../inc/body_end.jsp"%>		
</body>
</html>