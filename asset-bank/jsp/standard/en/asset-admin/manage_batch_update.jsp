<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		14-Jun-2005		Created.
	 d2		Ben Browning		17-Feb-2006		HTML/CSS tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="maxSizeForSelection" settingName="max-bulk-update-size-for-selection"/>
<bright:applicationSetting id="maxBatchUpdateResults" settingName="max-batch-update-results"/>




<head>
	
	<title><bright:cmsWrite identifier="title-batch-update" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="batch-batchupdate"/>
	<bean:define id="tabId" value="batchupdate"/>
</head>

<body id="uploadPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<c:set var="bMoreBatch" value="false"/>
	<c:set var="bHasBulkUpdateController" value="false"/>
	<c:if test="${!empty userprofile.batchUpdateController && userprofile.batchUpdateController.type == 'BATCHUPDATE'}">
		<c:if test="${userprofile.batchUpdateController.hasNext}">
			<c:set var="bMoreBatch" value="true"/>
		</c:if>
	</c:if>	
	<c:if test="${!empty userprofile.batchUpdateController && userprofile.batchUpdateController.type == 'BULKUPDATE'}">
		<c:set var="bHasBulkUpdateController" value="true"/>
	</c:if>	

	<h1><bright:cmsWrite identifier="heading-update-assets" filter="false" case="mixed" /></h1>  

	<%@include file="inc_batch_tabs.jsp"%>
	
	<h2><bright:cmsWrite identifier="subhead-batch-update" filter="false"/></h2>
		
	<bright:cmsWrite identifier="intro-batch-update" filter="false" replaceVariables="true" />
	
	<c:choose>
		<c:when test="${bMoreBatch}">
			<div class="info"><strong><bright:cmsWrite identifier="snippet-currently-batch-progress" filter="false"/></strong></div>
			<p><a href="../action/viewUpdateNextImage?resume=true"><bright:cmsWrite identifier="link-return-current-batch" filter="false"/></a></p>
			<p><a href="../action/cancelBatch"><bright:cmsWrite identifier="link-cancel-current-batch" filter="false"/></a> <bright:cmsWrite identifier="snippet-so-items-available" filter="false"/></p>
		</c:when>
		<c:otherwise>
			<p><strong><bright:cmsWrite identifier="snippet-batch-not-in-progress" filter="false"/></strong></p>

			<p><a href="../action/viewBatchUpdate"><bright:cmsWrite identifier="link-start-new-batch-update" filter="false"/></a></p>
			
		</c:otherwise>
	</c:choose>

	<br /><br />
	<div class="hr"></div>
	<p>
		<em><bright:cmsWrite identifier="snippet-batch-bulk-note" filter="false"/></em>
	</p>
		
	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>