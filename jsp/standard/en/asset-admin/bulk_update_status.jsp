<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson		06-Jul-2006	Created.
	 d2     Matt Woollard       20-Jul-2009 Added option for bulk delete
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="maxSizeForSelection" settingName="max-bulk-update-size-for-selection"/>



<head>
	
	<title><bright:cmsWrite identifier="title-bulk-update" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="batch-bulkupdate"/>

	<logic:equal name="bulkUpdateStatusForm" property="inProgress" value="true">
		<meta HTTP-EQUIV="refresh" CONTENT="10;URL=bulkUpdateViewStatus"></meta>
	</logic:equal>
</head>

<c:set var="bHasBulkUpdateController" value="false"/>
<c:if test="${!empty userprofile.batchUpdateController && userprofile.batchUpdateController.type == 'BULKUPDATE'}">
	<c:set var="bHasBulkUpdateController" value="true"/>
</c:if>	
		
<body>
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-bulk-update-status" filter="false" /></h1> 

	<logic:equal name="bulkUpdateStatusForm" property="inProgress" value="true">
		<div class="info">
			<bright:cmsWrite identifier="intro-bulk-update-in-progress" filter="false"/>
		</div>	
	</logic:equal>
	<logic:equal name="bulkUpdateStatusForm" property="inProgress" value="false">
		<div class="confirm">
			<bright:cmsWrite identifier="intro-bulk-update-finished" filter="false"/>
		</div>	
	</logic:equal>


	<%-- Links to continue working with the batch --%>
	<c:if test="${bHasBulkUpdateController && !bulkUpdateStatusForm.inProgress && !userprofile.batchUpdateController.delete}"> 
		<c:if test="${userprofile.batchUpdateController.numberInBatch le maxSizeForSelection}">
			<p>
				<a href="bulkUpdateViewBatch"><bright:cmsWrite identifier="link-select-items-bulk-again" filter="false"/></a>
			</p>	
		</c:if>	
		<p><a href="../action/bulkUpdateEnterMetadata"><bright:cmsWrite identifier="link-run-another-update" filter="false"/></a></p>
		<p><a href="../action/cancelBulk"><bright:cmsWrite identifier="link-finish-batch" filter="false"/></a> <bright:cmsWrite identifier="snippet-so-items-available" filter="false"/></p>
		<br />
	</c:if>
	
	
	<%-- If this is a deletion that's finished just show link to do a new bulk update --%>
	<c:if test="${bulkUpdateStatusForm.delete}">
		<p><a href="../action/bulkUpdateViewSearch"><bright:cmsWrite identifier="link-start-new-bulk-update" filter="false"/></a></p>
	</c:if>
	
		
	<h3><bright:cmsWrite identifier="subhead-log" filter="false"/></h3>
	<logic:notEmpty name="bulkUpdateStatusForm" property="messages">
		<ul class="normal">
		<logic:iterate name="bulkUpdateStatusForm" property="messages" id="message">
			<li><bean:write name="message" /></li>
		</logic:iterate>
		</ul>
	</logic:notEmpty>

	

<%@include file="../inc/body_end.jsp"%>
</body>
</html>