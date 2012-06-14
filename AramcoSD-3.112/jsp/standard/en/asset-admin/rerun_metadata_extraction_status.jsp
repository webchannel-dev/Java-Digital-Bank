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


	<head>
		<meta http-equiv="content-type" content="text/html; charset=iso-8859-1">
		<title><bright:cmsWrite identifier="company-name" filter="false" /> | Rerun embedded metadata extraction</title> 
		<%@include file="../inc/head-elements.jsp"%>

		<logic:equal name="messageStatusForm" property="inProgress" value="true">
			<meta HTTP-EQUIV="refresh" CONTENT="10;URL=viewRerunEmbeddedMetadataExtractionStatus"></meta>
		</logic:equal>
		<bean:define id="section" value="attributes"/>
		<bean:define id="pagetitle" value="Attributes"/>
		<bean:define id="tabId" value="embeddedData"/>
	</head>

	<body id="adminPage">

		<%@include file="../inc/body_start.jsp"%>
		
		<h1><bean:write name="pagetitle" filter="false"/></h1> 

		<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
		
		
		<logic:equal name="messageStatusForm" property="inProgress" value="false">
			<p><a href="viewManageEmbeddedDataMappings">&laquo; Back to Embedded Data Mappings</a> </p>
			<p>Click the button below to run extraction of embedded metadata for ALL assets. 
			Please note this will overwrite all current values of attributes that are mapped to embedded metadata!</p>
			<form name="reindexForm" action="rerunEmbeddedMetadataExtraction" method="get">
				<input type="submit" name="submit" class="button flush" value="Run Extraction &raquo;" onclick="return confirm('Are you sure you want to run the extraction? This may take a long time.');"/>
			</form>
		</logic:equal>
		
		<logic:equal name="messageStatusForm" property="hasErrors" value="true">
			<div class="error">
				The following errors occurred during reindex: <br />			
				<ul>
					<logic:iterate name="messageStatusForm" property="errors" id="errorText">
						<li><bean:write name="errorText" filter="false"/></li>
					</logic:iterate>
				</ul>
			</div>
		</logic:equal>
		
		<logic:equal name="messageStatusForm" property="inProgress" value="true">
			<p>Rerun Embedded Metadata Extraction is in progress.</p>
			<p>This page will automatically refresh every 10 seconds until the process finishes. If for some reason this does not happen you may <a href="viewRerunEmbeddedMetadataExtractionStatus">update the page</a> manually to check the status.</p>
		</logic:equal>
		

		<logic:notEmpty name="messageStatusForm" property="messages">
			<div class="hr"></div>
			<h3>Rerun Embedded Metadata Extraction Log:</h3>
			<ul class="normal">
			<logic:iterate name="messageStatusForm" property="messages" id="message">
				<li><bean:write name="message" /></li>
			</logic:iterate>
			</ul>
		</logic:notEmpty>						
		
		<%@include file="../inc/body_end.jsp"%>
		
	</body>
</html>