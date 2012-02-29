<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		08-Jan-2010		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<html>

	<head>
		<title><bright:cmsWrite identifier="title-repurposed-slideshow" filter="false"/></title> 
		<%@include file="../inc/head-elements.jsp"%>
		<script type="text/javascript" src="../images/slideshows/common/swfobject.js"></script>
		<style type="text/css">
		<!--
			html {
				height: 100%;
				padding:0; margin:0;
				overflow: auto;
			}
			body {
				height:90%;
			}
		-->
		</style>
		<bean:define id="helpsection" value="repurposedSlideshows"/>
	
	</head>

	<body id="popup">

		<bean:write name='repurposingForm' property='repurposedVersion.embeddableHtml' filter='false'/>
		
		<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button js-enabled-show" id="closeButton" onclick="window.close();" />
		
	</body>

</html>