<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson	09-Aug-2006		Created.
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
	<meta http-equiv="content-type" content="text/html;charset=iso-8859-1">
	<title><bean:write name="companyName" /> | Play Audio</title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>
<body style="min-width: 0px; margin:20px;">

	<bean:parameter id="file" name="file"/> 
	<bean:parameter id="contentType" name="contentType"/> 

	<div class="centered">
			
				<object id="MediaPlayer" style="height:46px" classid="CLSID:22D6f312-B0F6-11D0-94AB-0080C74C7E95" standby="Loading Windows Media Player components..." type="application/x-oleobject" codebase="http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=6,4,7,1112"> 

					<param name="filename" value="../servlet/display?file=<bean:write name='file'/>"/>
					<param name="Showcontrols" value="True"/>
					<param name="autoStart" value="True"/>
					<param name="WindowlessVideo" value="1"/>
					<param name="AudioStream" value="1"/>
					<param name="ShowDisplay" value="0"/> 

					<embed src="../servlet/display?file=<bean:write name='file'/>&contentType=<bean:write name='contentType'/>" windowlessVideo="true" audioStream="true" showDisplay="false" style="height:46px"></embed>
				</object>

			<p style="text-align:right;">
				<br/>
				<script type="text/javascript">
					document.write('<input type="button" value="Close" class="button" id="submitButton" onClick="window.close();">');
				</script>
			</p>
	
	</div>
</body>
</html>