<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson	09-Aug-2006		Created.
	 d2		Matt Stevenson	01-Mar-2007		Modified to use flash player for video
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="flashPlayerHeight" settingName="flash-player-height"/>
<bright:applicationSetting id="flashPlayerWidth" settingName="flash-player-width"/>

<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Play Media</title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>
<body style="min-width:0; margin:10px 20px;">

<div class="centered">

	<logic:present parameter="height">
		<bean:parameter id="height" name="height"/>
	</logic:present>
	<logic:notPresent parameter="height">		
		<c:set var="height" value="${flashPlayerHeight}"/>
	</logic:notPresent>
	<logic:present parameter="width">
		<bean:parameter id="width" name="width"/>
	</logic:present>
	<logic:notPresent parameter="width">		
		<c:set var="width" value="${flashPlayerWidth}"/>
	</logic:notPresent>

	<%@include file="inc_flash_player.jsp"%>
	
	<div style="text-align:right; margin-top:2em;">
	<script type="text/javascript">
			document.write('<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button" id="submitButton" onclick="window.close();">');
	</script>
	</div>	
	
</div>


</body>
</html>