<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	19-Nov-2007		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bean:parameter id="startedReindex" name="startedReindex" value="false"/>
<c:set var="anyReindexInProgress" value="${reindexStatusForm.anyReindexInProgress || startedReindex}"/>
<c:set var="myReindexInProgress" value="${reindexStatusForm.myReindexInProgress || startedReindex}"/>

	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
		<title><bright:cmsWrite identifier="company-name" filter="false" /> | Reindex</title> 
		<%@include file="../inc/head-elements.jsp"%>

		<c:if test="${anyReindexInProgress}">
			<meta HTTP-EQUIV="refresh" CONTENT="10;URL=viewReindexStatus"></meta>
		</c:if>
		<bean:define id="section" value="attributes"/>
		<bean:define id="pagetitle" value="Attributes"/>
		<bean:define id="tabId" value="manageAttributes"/>
	</head>

	<body id="adminPage">

		<%@include file="../inc/body_start.jsp"%>
		<h1><bean:write name="pagetitle" filter="false"/></h1> 
		<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
		
		<c:if test="${!anyReindexInProgress}">
			<p>If you are reindexing because you have added or changed attributes or the sort order then you should perform a 'quick' reindex, as this will not affect users while it is in progress. If you are performing an index to fix issues relating to a system error then you should perform a full index.</p>
			<form name="reindexForm" action="reindexImages" method="get" class="floated">
				<input type="checkbox" name="quick" id="quick" value="1" checked="checked" class="checkbox" /><label for="quick" class="after">Quick index?</label><br/><input type="submit" name="submit" class="button flush" value="Start Reindex &raquo;" onclick="return confirm('Are you sure you want to reindex the assets? This may take a long time.');"/>
			</form>

		</c:if>
		
		<logic:equal name="reindexStatusForm" property="hasErrors" value="true">
			<div class="error">
				The following errors occurred during reindex: <br />			
				<ul>
					<logic:iterate name="reindexStatusForm" property="errors" id="errorText">
						<li><bean:write name="errorText" filter="false"/></li>
					</logic:iterate>
				</ul>
			</div>
		</logic:equal>

		<%--  Headings depend on whose reindex and its state --%>	
		<%--  Default heading is for last reindex, if not one in progress --%>	
		<c:choose>
			<c:when test="${empty reindexStatusForm.lastReindexUser}">
				<c:set var="sReindexLogHeading" value="Last reindex log" />	
			</c:when>
			<c:otherwise>
				<c:set var="sReindexLogHeading" value="Last reindex log (${reindexStatusForm.lastReindexUser})" />	
			</c:otherwise>
		</c:choose>
		
		<c:if test="${anyReindexInProgress}">
		
			<c:choose>
				<c:when test="${myReindexInProgress}">
					<c:set var="sReindexHeading" value="Your reindex is in progress" />
					<c:set var="sReindexLogHeading" value="Your current reindex log" />
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${empty reindexStatusForm.otherUserWithReindex}">
							<c:set var="sReindexHeading" value="Another user has a reindex in progress" />
							<c:set var="sReindexLogHeading" value="Reindex log" />						
						</c:when>
						<c:otherwise>
							<c:set var="sReindexHeading" value="${reindexStatusForm.otherUserWithReindex} has a reindex in progress" />						
							<c:set var="sReindexLogHeading" value="Reindex log (${reindexStatusForm.otherUserWithReindex})" />
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>

			<h3 class="loader"><img src="../images/standard/misc/ajax_loader.gif" width="24" height="24" alt="loading..." /><c:out value="${sReindexHeading}" /></h3>		
						
			<p>This page will automatically refresh every 10 seconds until the reindex finishes. If for some reason this does not happen you may <a href="viewReindexStatus">update the page</a> manually to check the status.</p>
		</c:if>

		<logic:notEmpty name="reindexStatusForm" property="messages">
			<div class="logHeading"><c:out value="${sReindexLogHeading}" />:</div>
			<ul class="log stripey">
			<logic:iterate name="reindexStatusForm" property="messages" id="message">
				<li><bean:write name="message" /></li>
			</logic:iterate>
			</ul>
		</logic:notEmpty>						
		
		<%@include file="../inc/body_end.jsp"%>
		
	</body>
</html>