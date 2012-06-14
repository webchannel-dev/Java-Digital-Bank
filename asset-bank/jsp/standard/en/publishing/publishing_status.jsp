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

<c:set var="queuedTaskIds" />
<bean:size id="tasksNum" name="taskIds"/>

<c:choose>
	<c:when test="${tasksNum > 1}">
		<logic:iterate name="taskIds" id="taskId">
			<c:if test="${not empty queuedTaskIds}"><c:set var="queuedTaskIds" value="${queuedTaskIds},"/></c:if>
			<c:set var="queuedTaskIds" value="${queuedTaskIds}${taskId}"/>
		</logic:iterate>
	</c:when>
	<c:otherwise>
		<c:set var="queuedTaskIds" value="${taskIds[0]}"/>
	</c:otherwise>
</c:choose>


<head>
	
	<title>Publishing Assets</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="publishing"/>

	<c:if test="${inProgress}">
		<noscript>
			<!-- meta refresh for non js users -->
			<meta HTTP-EQUIV="refresh" CONTENT="5;URL=viewPublishingActionStatus?queuedTaskId=<c:out value="${queuedTaskIds}"/>"></meta>
		</noscript>	
		
		<script type="text/javascript">
			// AJAX based refresh
			$j(function() {
			//  // call the ajax update function every 2 seconds - this periodically reloads (ARG 1 URL) in ajaxUpdateContent, if any text matches (ARG 2 String) then redirect to (ARG 3 URL)
			  setInterval("ajaxUpdate('viewPublishingActionStatusAjax?queuedTaskId=<c:out value="${queuedTaskIds}" />', 'TASK COMPLETE', 'viewPublishingActionStatus?queuedTaskId=<c:out value="${queuedTaskIds}" />')",2000);
			});
		</script>
	</c:if>

</head>

<body id="searchPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1>Publishing Assets</h1>

	<c:if test="${inProgress}">
		
		<!-- message for js users -->
		<h3 class="js-enabled-show progressLoader"><img src="../images/standard/misc/ajax_loader.gif" width="24" height="24" alt="loading..." />Publishing Assets...&nbsp;&nbsp;<span>you can safely navigate away from this page and the publishing will continue.</span></h3>
		
		<!-- message for non js users -->
		<div class="js-enabled-hide"><h3>Publishing Assets...&nbsp;&nbsp;<span>you can safely navigate away from this page and the publishing will continue.</span></h3></div>
		
	
		<logic:iterate name="taskIds" id="taskId">
			<bean:define name="messages_${taskId}" id="messages"/>
			
			<div id="ajaxUpdateContent">
				<!-- If Javascript enabled, ajax fetched progress messages will be shown here. -->
			</div>
	
			<div class="js-enabled-hide">
				<br /> <strong>Publish Action: </strong> <br />
				<ul id="publishing-status-messages-list" class="normal">
				<c:forEach items="${messages}" var="message">
					<li class="publishing-status-message"><c:out value="${message}" /></li>
				</c:forEach>
				</ul>
			</div>
			
		</logic:iterate>
	</c:if>


	<c:if test="${!inProgress}">
		<h3>Publishing Complete</h3>

		<logic:iterate name="taskIds" id="taskId">
		
			<bean:define name="messages_${taskId}" id="messages"/>
			
			<br /> <strong>Publish Action: </strong> <br />
			<ul id="publishing-status-messages-list" class="normal">
			<c:forEach items="${messages}" var="message">
				<li class="publishing-status-message"><c:out value="${message}" /></li>
			</c:forEach>
			</ul>
			
		</logic:iterate>
	</c:if>

	<br/>
	<div>
		<a id="back-to-publishing-items-link" href="viewPublishing">&laquo; Back to all publishing actions</a>
	</div>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>