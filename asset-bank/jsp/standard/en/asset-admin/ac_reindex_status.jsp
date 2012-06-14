<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
 d1	  10-Sep-2009	Francis Devereux	Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


	<head>
		<meta http-equiv="content-type" content="text/html; charset=UTF-8">
		<title><bright:cmsWrite identifier="company-name" filter="false" /> | Auto Complete Reindex</title>
		<%@include file="../inc/head-elements.jsp"%>

		<logic:equal name="acReindexStatusForm" property="reindexInProgress" value="true">
			<meta HTTP-EQUIV="refresh" CONTENT="10;URL=viewACReindexStatus"></meta>
		</logic:equal>
		<bean:define id="section" value="attributes"/>
		<bean:define id="pagetitle" value="Attributes"/>
		<bean:define id="tabId" value="manageAttributes"/>
	</head>

	<body id="adminPage">

		<%@include file="../inc/body_start.jsp"%>
		
		<h1><bean:write name="pagetitle" /></h1> 

		<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
		
		<logic:equal name="acReindexStatusForm" property="reindexInProgress" value="false">
			<p>If you have enabled auto completion for new attributes or restored the Asset Bank database from a backup you should reindex the auto complete index.</p>
			<p><strong>Please note</strong>: auto completion will be disabled at certain stages of the reindex process.</p>
			<p>If you need to rebuild both the auto complete index and the main search index then you should <a href="viewReindexStatus">rebuild the main index</a> first because the auto complete index rebuild uses the main index.</p>
			<form name="reindexForm" action="rebuildACIndex" method="get">
				<input type="submit" name="submit" class="button flush" value="Start Auto Completion Reindex &raquo;" onclick="return confirm('Are you sure you want to rebuild the the auto complete index? This may take a long time.');"/>
			</form>

		</logic:equal>
		
		<logic:equal name="acReindexStatusForm" property="hasErrors" value="true">
			<div class="error">
				The following errors occurred during reindex: <br />			
				<ul>
					<logic:iterate name="acReindexStatusForm" property="errors" id="errorText">
						<li><bright:writeError name="errorText" /></li>
					</logic:iterate>
				</ul>
			</div>
		</logic:equal>
		
		<logic:equal name="acReindexStatusForm" property="reindexInProgress" value="true">
			<p>The auto complete reindex is in progress.</p>
			<p>This page will automatically refresh every 10 seconds until the reindex finishes. If for some reason this does not happen you may <a href="viewACReindexStatus">update the page</a> manually to check the status.</p>
		</logic:equal>

		<logic:notEmpty name="acReindexStatusForm" property="messages">
			<div class="hr"></div>
			<h3>Auto Complete Reindex Log:</h3>
			<ul class="normal">
			<logic:iterate name="acReindexStatusForm" property="messages" id="message">
				<li><bean:write name="message" /></li>
			</logic:iterate>
			</ul>
		</logic:notEmpty>						
		
		<%@include file="../inc/body_end.jsp"%>
		
	</body>
</html>