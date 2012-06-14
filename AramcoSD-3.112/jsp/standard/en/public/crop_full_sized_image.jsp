<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Adam Bones		23-Oct-2006		Created.
	 d2		Matt Stevenson	24-Jan-2007		Modified to include fixed ratio
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bean:parameter name="fixedRatioX" id="fixedRatioX" value="0"/>
<bean:parameter name="fixedRatioY" id="fixedRatioY" value="0"/>
<bean:parameter name="x1" id="x1" value="0"/>
<bean:parameter name="x2" id="x2" value="0"/>
<bean:parameter name="y1" id="y1" value="0"/>
<bean:parameter name="y2" id="y2" value="0"/>



<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" />| Full-size Image</title> 
	<bean:define id="section" value="download"/>
	<%@include file="../inc/head-elements.jsp"%>
	<link href="../js/cropper/cropper.css" rel="stylesheet" type="text/css" media="all" /> 
	<script src="../js/cropper/cropper.js" type="text/javascript" ></script>
	<script type="text/javascript">
		

Event.observe( window, 'load', function() { 
	new Cropper.Img('croppable_image', {
		<c:if test="${fixedRatioX > 0 && fixedRatioY > 0}">
			ratioDim: { x: <c:out value="${fixedRatioX}"/>, y: <c:out value="${fixedRatioY}"/> },
		</c:if> 
		onEndCrop: this.opener.downloadForm.onEndCrop.bind(this.opener.downloadForm)<c:if test="${x1 > 0 && x2 > 0 && y1 > 0 && y2 > 0}">, 
		displayOnInit: true, 
		onloadCoords: { 
			x1: <c:out value="${x1}"/>, 
			y1: <c:out value="${y1}"/>, 
			x2: <c:out value="${x2}"/>, 
			y2: <c:out value="${y2}"/> 
			}
		</c:if> 
	});

	self.focus(); 
}); 
	</script>
</head>

<body id="help">

<div class="copy">
<h2>Select crop area</h2>
<p>Drag with your mouse to select the crop area. When you have finished, click 'Crop &amp; Close'.</p>


<p><img id="croppable_image" src="../servlet/display?file=<bean:write name='file'/>" border="0"></p>

<br />
<p style="text-align:right;"><input type="button" value="<bright:cmsWrite identifier="button-crop" filter="false" />" class="button" id="submitButton" onclick="window.close();"></p>

</div>
</body>
</html>