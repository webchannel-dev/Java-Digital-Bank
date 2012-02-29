<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Woollard	21-Jan-2008 	Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<head>
	
	<title>Validate hyperlinks</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value=""/>
	<bean:define id="pagetitle" value="Usage Details"/>
	<bean:define id="helpsection" value="view_asset"/>	
</head>

<body id="popup">
	
	<div class="copy">
	
	<h2 class="underline">Validate hyperlinks</h2> 
	
	<table class="list" cellspacing="0"  border="0" style="margin:0; width:100%">
		<logic:iterate name="validateHyperlinkForm" property="hyperlinks" id="pair">
			<tr>
				<td>
					<a href="<bean:write name="validateHyperlinkForm" property="baseUrl" filter="false"/><bright:write name="pair" property="value" encodeForUrl="true"/>" target="_blank"><c:if test="${empty pair.name}"><bean:write name="validateHyperlinkForm" property="linkName" filter="false"/>: <bean:write name="hyperlink" filter="false"/></c:if><c:if test="${not empty pair.name}"><bean:write name="pair" property="name"/></c:if></a><br/>
				</td>
			</tr>
		</logic:iterate>
		<tr><td>&nbsp;</td></tr>
	</table>			
	</div>

	<div style="text-align:right;">
		<script type="text/javascript">
			document.write('<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button" id="submitButton" onclick="window.close();">');
		</script>
	</div>
	
</body>
</html>