<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
            "http://www.w3.org/TR/html4/loose.dtd">
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson	09-Aug-2006		Created.
	 d2		Matt Stevenson	01-Mar-2007		Modified to use flash player for video
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html-el.tld" prefix="html-el" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%-- Set the company name for use in the page title --%>
<bright:applicationSetting id="companyName" settingName="companyName"/>

<html>
<head>
	<meta http-equiv="content-type" content="text/html;charset=iso-8859-1" />
	<title><bean:write name="companyName" /> | Play Video</title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>
<body style="min-width:0; margin:10px 20px;">

<bean:parameter id="file" name="file"/> 

<div class="centered">

	<%-- embed src="../servlet/display?file=<bean:write name='file'/>&contentType=<bean:write name='contentType'/>&filename=<bean:write name='filename'/>" width="600" height="400"></embed --%>
	
	<object type="application/x-shockwave-flash" data="../tools/flow-player/player.swf" width="350" height="300" id="FlowPlayer">
		<param name="allowScriptAccess" value="sameDomain" />
		<param name="movie" value="../tools/flow-player/player.swf" />
		<param name="quality" value="high" />
		<param name="scale" value="noScale" />
		<param name="wmode" value="transparent" />
		<param name="flashvars" value="config={videoFile: '../../servlet/display?file=<bean:write name='file'/>', noVideoClip: '../../images/standard/misc/no-video.gif', loop: false}" />
	</object>
	<div style="text-align:right; margin-top:2em;">
	<script type="text/javascript">
			document.write('<input type="button" value="Close" class="button" id="submitButton" onclick="window.close();">');
	</script>
	</div>	
	
</div>


</body>
</html>