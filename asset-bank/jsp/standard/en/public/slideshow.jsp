	<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		21-Oct-2009
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
	
<head>
	<title><bright:cmsWrite identifier="title-slideshow" filter="false" replaceVariables="true"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/javascript" src="../js/lib/swfobject.js"></script>
	<script type="text/javaScript">
      $j(function() {
      	// initially hide page control panel
      	$j('#controlInner').hide();
      });	
    </script> 
	<style type="text/css">
	<!--
		html {
			height: 100%;
			padding:0; margin:0;
			overflow: hidden;
		}
	-->
	</style>
	<bean:define id="helpsection" value="slideshow"/>
</head>

<body id="slideshow">
 
	<bean:parameter id="type" name="type" value="search"/>
	<bean:parameter id="pageNumber" name="page" value="1"/>
	<bean:parameter id="pageSize" name="pageSize" value="20"/>
	<bean:parameter id="fullList" name="fullList" value="false"/>
	<c:choose>
		<c:when test="${type == 'assetbox'}">
			<c:set var="servlet" value="transformAssetbox"/>
		</c:when>
		<c:when test="${type == 'browse'}">
			<c:set var="servlet" value="transformLastBrowse"/>
		</c:when>
		<c:otherwise>
			<c:set var="servlet" value="transformLastSearch"/>
		</c:otherwise>
	</c:choose>

	<c:set var="captionIds">
		<logic:iterate name='slideshowForm' property='attributeIds' id='attributeId'><bean:write name='attributeId'/>,</logic:iterate>
	</c:set>

	<bean:define id="controlPanelForm" name="slideshowForm"/>
	<bean:define id="action" value="viewSearchSlideshow"/>
	<bright:applicationSetting id="enableSlideshowRepurposing" settingName="slideshow-repurposing-enabled"/>
	<bean:parameter id="returnUrl" name="returnUrl" value="viewHome"/>
	<div class="leftShadow">
		<div class="rightShadow">
	   	<div id="controlPanel">
		   	<div class="header">

					<a href="..<c:out value='${returnUrl}'/>" class="close js-enabled-show">Close slideshow</a>
					<a class="help-popup" href="../action/viewHelp?helpsection=<bean:write name='helpsection'/>" target="_blank" title="View help text in new window"><bright:cmsWrite identifier="menu-help" filter="false"/></a>
						
					<c:if test="${enableSlideshowRepurposing && userprofile.isAdmin}">
						<a href="viewRepurposeSlideshow?type=<bean:write name='type'/>&page=<bean:write name='pageNumber'/>&pageSize=<bean:write name='pageSize'/>&captionIds=<bean:write name='captionIds'/>&rtl=<bean:write name='controlPanelForm' property='rtl'/>&displayTime=<bean:write name='controlPanelForm' property='displayTime'/>&fullList=<c:out value='${fullList}'/>&labels=<bean:write name='controlPanelForm' property='showAttributeLabels'/>" class="js-enabled-show embed">Create embeddable version</a>
					</c:if>

	
					<a href="#" class="expand white" onclick="toggleControls($j('#controlInner'),$j(this)); return false;" id="controlsToggle"><bright:cmsWrite identifier="subhead-options" filter="false"/></a>
		   	</div>
		   	<div class="inner" id="controlInner">	

				<form action="<bean:write name='action'/>" >

					<input type="hidden" name="type" value="<c:out value='${type}'/>"/>
					<input type="hidden" name="page" value="<c:out value='${pageNumber}'/>"/>
					<input type="hidden" name="pageSize" value="<c:out value='${pageSize}'/>"/>
					<input type="hidden" name="returnUrl" value="<c:out value='${returnUrl}'/>"/>
					<input type="hidden" name="fullList" value="<c:out value='${fullList}'/>"/>
				 
				
				
				 
				 	<fieldset  id="changeAttributes">
					 	<input type="hidden" name="attributesSelected" value="true"/>
					
					 	<%@include file="inc_control_panel_attributes.jsp"%>
					 	
					</fieldset>
			   
	   
					<fieldset>
			   			<h3>Slideshow settings:</h3>
						<label for="refreshrate">
							<bright:cmsWrite identifier="snippet-display-time" filter="false"/>
							<html:text name="controlPanelForm" property="displayTime" size="4" style="width:2em" styleId="maxwidth" />
						</label>
						<br/>
						<label for="labels">
							<bright:cmsWrite identifier="snippet-show-attribute-labels" filter="false"/> 
							<html:checkbox name="controlPanelForm" property="showAttributeLabels" styleId="labels" value="true" />
						</label>
						<br/>						
						<label for="rtl">
							<bright:cmsWrite identifier="snippet-text-rtl" />
							<html:checkbox name="controlPanelForm" property="rtl" styleId="rtl" />
						</label>
						<br/>
					</fieldset>	
					
					<div class="hr"></div>
					
					<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-apply" filter="false"/>" />
					<a href="#" class="cancelLink" onclick="toggleControls($j('#controlInner'),$j('#controlsToggle')); return false;">Cancel</a>

		

					<c:if test="${attributeSelected}">
						<input type="hidden" name="attributesSelected" value="true"/>
					</c:if>
			 
				</form> 
			 
		   	</div><!-- end of div.inner -->
	   	</div>
		</div>
	</div>


	 
	
	<div id="flashcontent">The slideshow requires JavaScript and the Flash Player. <a href="http://www.macromedia.com/go/getflashplayer/">Get Flash here.</a> </div>	
		
	<script type="text/javascript">
		var flashvars = {
				data:"../servlet/<c:out value='${servlet}'/>?transformWith=slideshow.xslt%26fullList=<c:out value='${fullList}'/>%26contentType=text%2Fxml%26labels=<c:out value='${slideshowForm.showAttributeLabels}'/>%26page=<bean:write name='pageNumber'/>%26pageSize=<bean:write name='pageSize'/><logic:notEmpty name='slideshowForm' property='attributeIds'>%26captionIds=<bean:write name='captionIds'/></logic:notEmpty>",
				onExternalSite:"false",
				type:"slideshow",
				modules:"AssetBank",
				modulePath:"../images/slideshows/common/modules/",
				textDirection: (${slideshowForm.rtl==true} ? "rtl" : "ltr"),
				displayTime:<c:choose><c:when test="${slideshowForm.displayTime > 0}"><c:out value="${slideshowForm.displayTime}"/></c:when><c:otherwise>5</c:otherwise></c:choose>,
				infoBar:<logic:notEmpty name='slideshowForm' property='attributeIds'>"on"</logic:notEmpty><logic:empty name='slideshowForm' property='attributeIds'>"off"</logic:empty>
			};
		var params = {wmode:"transparent",allowFullScreen:"true"};
		var attributes = {};
		
		swfobject.embedSWF("../images/slideshows/common/gallery.swf?latest", "flashcontent", "100%", "100%", "9.0.0", "../images/slideshows/common/expressinstall.swf", flashvars, params, attributes);
	</script> 

</body>
</html>