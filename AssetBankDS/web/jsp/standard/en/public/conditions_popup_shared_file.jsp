<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		09-Jun-2005		Created.
	 d2		Ben Browning	22-Feb-2006		Tidied up html
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Terms &amp; Conditions</title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>

<body id="conditionsPopup">

<div class="copy">

	<bright:refDataList id="conditions" componentName="ListManager" methodName="getListItem" argumentValue="conditions-shared-file"/>	
	<bean:write name="conditions" property="body" filter="false"/>
	<br />

	<div style="text-align:right;">
		<script type="text/javascript">
			document.write('<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button" id="submitButton" onclick="window.close();">');
		</script>
	</div>

	<br />
		
</div>

</body>
</html>