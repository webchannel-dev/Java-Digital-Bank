<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1	Tom Christie		10-May-2010		Created.
	d2	Ben Browning		23-June-2010	updated to use new jquery version of ajaxUpdate.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="title-email-in-progress" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="email"/>
	
	<noscript>
		<!-- meta refresh for non js users -->
		<meta HTTP-EQUIV="refresh" CONTENT="20;URL=emailAsset?queuedTaskId=<c:out value="${queueTaskId}" />?assetId=<c:out value="${assetId}" />"></meta>
	</noscript>	
	
	<script type="text/javascript">
		// AJAX based refresh
		$j(function() {
		  // call the ajax update function every 2 seconds - this periodically reloads (ARG 1 URL) in ajaxUpdateContent, if any text matches (ARG 2 String) then redirect to (ARG 3 URL)
		  setInterval("ajaxUpdate('viewRemoteStorageUploadProgress?queuedTaskId=<c:out value="${queuedTaskId}" />', 'TASK COMPLETE', 'emailAsset?queuedTaskId=<c:out value="${queuedTaskId}" />&assetId=<c:out value="${assetId}" />')",2000);
		});
	</script>

</head>

<body id="searchPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-email-in-progress" /></h1>
	
	<c:choose>
		<c:when test="${assetId == 0}">
			<div class="head">
		    	<a href="../action/viewAssetBox"><bright:cmsWrite identifier="link-back-my-lightbox" filter="false" /></a>
		    </div>
		</c:when>
		<c:otherwise>
			<div class="head">
		    	<a href="../action/viewAsset?id=<c:out value="${assetId}"/>"><bright:cmsWrite identifier="link-back-item" filter="false" /></a>
		    </div>
		</c:otherwise>
	</c:choose>
	
	<!-- message for js users -->
	<h3 class="js-enabled-show progressLoader"><img src="../images/standard/misc/ajax_loader.gif" width="24" height="24" alt="loading..." /><bright:cmsWrite identifier="snippet-email-upload-in-progress" />&nbsp;&nbsp;<span><bright:cmsWrite identifier="snippet-email-upload-navigate-away" /></span></h3>

	<!-- message for non js users -->
	<div class="js-enabled-hide"><h3><bright:cmsWrite identifier="snippet-email-upload-in-progress" />&nbsp;&nbsp;<span><bright:cmsWrite identifier="snippet-email-upload-navigate-away" /></span></h3></div>

	<div id="ajaxUpdateContent">
		<!-- If Javascript enabled, ajax fetched progress messages will be shown here. -->
	</div>

	<div class="js-enabled-hide">
		<ul class="normal">
		<c:forEach items="${messages}" var="message">
			<li><c:out value="${message}" /></li>
		</c:forEach>
		</ul>
	</div>
								
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>