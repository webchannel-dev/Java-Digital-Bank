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
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Bright Interactive</title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script src="../js/category.js" type="text/javascript"></script>
	<script src="../js/calendar.js" type="text/javascript"></script>

	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="metadata-import"/>
	<bean:define id="pagetitle" value="Metadata Import"/>
	<bean:define id="tabId" value="metadataimport"/>
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
		
	<p>The metadata import feature enables you to update the metadata of <bright:cmsWrite identifier="items" filter="false" /> by importing data from a tab-delimited file.</p>
	<p>Note that imported data is assumed to be in 'Excel' format, where tab and newline characters are allowed, but the cell in which they appear must be enclosed by double quotes (i.e. "..."). 
		In this format, each double quote character in the data itself must be 'escaped' by the addition of another double quote, meaning that each " must appear as "".</p>

	<div class="hr"></div>

	<c:choose>
		<c:when test="${metadataImportStatusForm.inProgress}">
			<p>You have a metadata import in progress. <a href="../action/metadataImportViewStatus">View status &raquo;</a></p>
		</c:when>
		<c:otherwise>
			<c:if test="${!emptymetadataImportStatusForm.messages}">	
				<p>Your last metadata import has finished. <a href="../action/metadataImportViewStatus">View status &raquo;</a></p>
			</c:if>
			<p><a href="../action/metadataImportNew">Start new metadata import &raquo;</a></p>			
			</c:otherwise>
	</c:choose>

	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>