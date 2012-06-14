<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Publishing Admin</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="publishing"/>
	<bean:define id="pagetitle" value="Publishing"/>
</head>
<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
 
	<h2>Delete Publishing Action</h2> 
 
	<div class="warning" >
		<strong>Note:</strong>
		<ul>
			<li>All stored data about this publish action will be deleted.</li>
			<li>The published assets will not be deleted from your publish location. If required, you will have to delete them manually.</li>
		</ul>
	</div>

	<div class="hr"></div> 
	<bean:parameter id="publishingActionId" name="publishingActionId" />
	<a href="deletePublishingAction?publishingActionId=${publishingActionId}" class="button" style="float: left;" >Confirm</a>
	<a href="viewPublishing" class="cancelLink" >Cancel</a> 
	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>