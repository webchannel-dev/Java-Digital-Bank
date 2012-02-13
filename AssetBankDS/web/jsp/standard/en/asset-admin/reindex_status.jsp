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
		<title><bright:cmsWrite identifier="company-name" filter="false" /> | Reindex</title> 
		<%@include file="../inc/head-elements.jsp"%>

		<logic:equal name="reindexStatusForm" property="reindexInProgress" value="true">
			<meta HTTP-EQUIV="refresh" CONTENT="10;URL=viewReindexStatus"></meta>
		</logic:equal>
		<bean:define id="section" value="attributes"/>
		<bean:define id="pagetitle" value="Attributes"/>
		<bean:define id="tabId" value="manageAttributes"/>
	</head>

	<body id="adminPage">

		<%@include file="../inc/body_start.jsp"%>
		
		<h1><bean:write name="pagetitle" filter="false"/></h1> 

		<%@include file="../attribute-admin/inc_attribute_tabs.jsp"%>
		
		<logic:equal name="reindexStatusForm" property="reindexInProgress" value="false">
			<p>If you are reindexing because you have added or changed attributes or the sort order then you should perform a 'quick' reindex, as this will not affect users while it is in progress. If you are performing an index to fix issues relating to a system error then you should perform a full index.</p>
			<form name="reindexForm" action="reindexImages" method="get" class="floated">
				<input type="checkbox" name="quick" id="quick" value="1" checked="checked" class="checkbox" /><label for="quick" class="after">Quick index?</label><br/><input type="submit" name="submit" class="button flush" value="Start Reindex &raquo;" onclick="return confirm('Are you sure you want to reindex the assets? This may take a long time.');"/>
			</form>

		</logic:equal>
		
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
		
		<logic:equal name="reindexStatusForm" property="reindexInProgress" value="true">
			<p>The reindex is in progress.</p>
			<p>This page will automatically refresh every 10 seconds until the reindex finishes. If for some reason this does not happen you may <a href="viewReindexStatus">update the page</a> manually to check the status.</p>
		</logic:equal>

		<logic:notEmpty name="reindexStatusForm" property="messages">
			<div class="hr"></div>
			<h3>Reindex Log:</h3>
			<ul class="normal">
			<logic:iterate name="reindexStatusForm" property="messages" id="message">
				<li><bean:write name="message" /></li>
			</logic:iterate>
			</ul>
		</logic:notEmpty>						
		
		<%@include file="../inc/body_end.jsp"%>
		
	</body>
</html>