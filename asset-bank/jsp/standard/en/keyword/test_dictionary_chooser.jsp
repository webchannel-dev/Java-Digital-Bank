<%@include file="../inc/doctype_html.jsp" %>

<!-- Developed by bright interactive www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Ben Browning	09-Feb-2006		Tidied up html
	 d3     Steve Bryan		18-Jul-2006		Changed id of keywords field to avoid clash with META tags
	 d4		Matt Stevenson	19-Mar-2007		Fixed problem with disabling add controls
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bean:parameter id="id" name="id" value="0"/>
<bean:parameter id="displayId" name="displayId" value="0"/>

<head>
	<title>Test Dictionary Chooser</title> 

<script type="text/javascript">
function fillFields(a_sIds, a_sDisplayId)
{
	// Put the values off this page into the opener elements
	var eltDisplay = opener.document.getElementById(a_sDisplayId);
	var eltIds = opener.document.getElementById(a_sIds);

	eltDisplay.value = document.getElementById('txt_displayids').value;
	eltIds.value = document.getElementById('txt_ids').value;
}

</script>


</head>
<body>

	<input type="text" id="txt_ids" value="1,2,3" />
	<input type="text" id="txt_displayids" value="Nottingham | Derby | Lake District" />
						
	<input type="button" value="Add" onclick="fillFields('<c:out value="${id}"/>', '<c:out value="${displayId}"/>');";					
</body>
</html>