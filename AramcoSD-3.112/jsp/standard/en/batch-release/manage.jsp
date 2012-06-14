<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		09-Feb-2011		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	
	<title><bright:cmsWrite identifier='title-manage-batch-releases' filter="false" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="batchReleases"/>
		

</head>

<body id="adminPage"> 

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="batch-releases" filter="false" case="mixed" /></h1> 
	
	
	<c:set var="tabId" value="manage" />
	
	<%@include file="inc_manage_tabs.jsp"%>
	
	<logic:present  name="batchReleaseForm">
		<logic:equal name="batchReleaseForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="batchReleaseForm" property="errors" id="error">
					<bean:write name="error" filter="false"/><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present> 

	<h3><bright:cmsWrite identifier="heading-unreleased-batch-releases" filter="false" /></h3>
	<p><bright:cmsWrite identifier="subhead-batch-releases-not-released" case="sentence" /></p>
	<bean:define id="releases" name="unreleased" />
	<%@include file="inc_list.jsp"%>

	<p><a href="viewEditBatchRelease"><bright:cmsWrite identifier="link-add-new-batch-release" filter="false" /></a>&nbsp;|&nbsp;<a href="viewBatchReleaseSearch"><bright:cmsWrite identifier="link-batch-release-search" filter="false" case="sentence" /> &raquo;</a></p>

	<br /><br />

	<h3><bright:cmsWrite identifier="heading-recently-released-batch-releases" filter="false" /></h3>
	<bright:applicationSetting settingName="batch-releases-recent-release-days" id="days" />
	<p><bright:cmsWrite identifier="subhead-batch-releases-released" case="sentence" replaceVariables="true" /></p>
	<bean:define id="releases" name="released" />
	<bean:define id="hideActionLinks" value="true" />
	<bean:define id="showReleaseDate" value="true" />
	<%@include file="inc_list.jsp"%>

	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>