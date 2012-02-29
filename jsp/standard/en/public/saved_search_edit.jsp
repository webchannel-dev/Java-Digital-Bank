<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Matt Stevenson	11-Dec-2008		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<head>
	<title><bright:cmsWrite identifier="title-save-search" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="search"/>
	<bean:define id="helpsection" value="saved_searches"/>
	<c:set var="tabId" value="savedSearches" />
</head>
<body id="searchPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<%@include file="inc_search_tabs.jsp"%>
	
	<logic:equal name="savedSearchForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="savedSearchForm" property="errors" id="errorText">
				<bean:write name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>

	<h2><bright:cmsWrite identifier="heading-save-search" filter="false"/></h2>
	<p><bright:cmsWrite identifier="snippet-save-search-warning" filter="false"/></p>

	<bright:applicationSetting id="canCreateSearchRss" settingName="can-create-search-rss"/>
	<html:form action="saveSearch" method="post" enctype="multipart/form-data" styleClass="floated">
		<input type="hidden" name="oldName" value="<bean:write name='savedSearchForm' property='savedSearch.name'/>"/>
		<%@include file="inc_saved_search_fields.jsp"%>
		<a href="viewSavedSearches" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>
	

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>