<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%-- If the preview include is the marker 'loadDirectlyInBrowser' (rather than a jsp), redirect to the asset file --%>
<c:if test="${param.viewFileInclude=='loadDirectlyInBrowser' && not empty param.id && not empty param.filename}">
	<% response.sendRedirect( request.getContextPath() + "/assetfile/" + request.getParameter("id") + "/" + request.getParameter("filename") ); %> 
</c:if>

<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson	09-Aug-2006		Created.
	 d2		Matt Stevenson	01-Mar-2007		Modified to use flash player for video
--%>



<head>
	<title><bright:cmsWrite identifier="title-preview-file" filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>
<body style="min-width:0; margin:10px 20px;">

<bean:parameter id="file" name="file"/> 

<div class="centered">

	<c:set scope="request" var="fileUrl"><c:out value="${pageContext.request.contextPath}"/>/servlet/display?file=<c:out value="${param.file}"/></c:set>

	<c:set var="file" value="${param.file}" scope="request"/>
	<jsp:include flush="true" page="<%= pageContext.getRequest().getParameter(\"viewFileInclude\") %>"/>
	
	<div style="text-align:right; margin-top:2em;">
	<script type="text/javascript">
			document.write('<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button" id="submitButton" onclick="window.close();">');
	</script>
	</div>	
	
</div>


</body>
</html>