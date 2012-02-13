<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		James Home		23-Jan-2008		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="maxSavedSearches" settingName="max-saved-searches"/>
<bright:applicationSetting id="canCreateSearchRss" settingName="can-create-search-rss"/>

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

	<h2><bright:cmsWrite identifier="heading-saved-searches" filter="false"/></h2>
	
	<logic:notEmpty name="savedSearchForm" property="savedSearches">
		<bean:size name="savedSearchForm" property="savedSearches" id="numSavedSearches"/>
		<table class="admin">
	
			<logic:iterate name="savedSearchForm" property="savedSearches" id="search">
				<tr>
					<td><bean:write name="search" property="name" filter="false"/></td>
					<c:if test="${canCreateSearchRss}">
						<td>
							<c:if test="${search.rssFeed}">
								<img src="../images/standard/icon/rss-icon.gif" alt="RSS enabled" height="13" width="13" border="0" align="absmiddle" style="margin-left:6px;">
							</c:if>
							<c:if test="${!search.rssFeed}">
								<img src="../images/standard/icon/rss-off-icon.gif" alt="RSS disabled" height="13" width="13" border="0" align="absmiddle" style="margin-left:6px;">
							</c:if>
						</td>
					</c:if>
					<td style="padding-left: 8px;">
						<c:if test="${not empty search.keywords}">
							"<bright:write name="search" property="keywords" maxLength="80" endString="..." filter="false"/>"
						</c:if>
						<c:if test="${empty search.keywords}">
							<span class="disabled"><bright:cmsWrite identifier="snippet-no-search-term" filter="false"/></span>
						</c:if>
					</td>
					<td style="padding-left: 8px;"><a href="deleteSavedSearchFromSaveSearch?name=<bright:write name="search" property="name" encodeForUrl="true"/>" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-delete-saved-search" filter="false"/>');"><bright:cmsWrite identifier="link-delete" filter="false"/></a></td>
				</tr>
			</logic:iterate>
		</table>
		<br/>
		<c:if test="${numSavedSearches < maxSavedSearches}">
			<c:set var="savedSearchSlots" value="${maxSavedSearches - numSavedSearches}"/>
			<p><bright:cmsWrite identifier="snippet-saved-search-slots" replaceVariables="true" filter="false"/></p>
		</c:if>
		<c:if test="${numSavedSearches >= maxSavedSearches}">
			<p><bright:cmsWrite identifier="snippet-max-saved-searches" filter="false"/></p>
		</c:if>
	</logic:notEmpty>
	<logic:empty name="savedSearchForm" property="savedSearches">
		<p><bright:cmsWrite identifier="snippet-no-saved-searches" filter="false"/></p>
	</logic:empty>
	
	<div class="hr"></div>
	
	<h2><bright:cmsWrite identifier="heading-save-search" filter="false"/></h2>
	
	<c:if test="${not empty savedSearchForm.savedSearch.keywords}">
		<p><bright:cmsWrite identifier="snippet-save-search" filter="false"/> "<bean:write name="savedSearchForm" property="savedSearch.keywords" filter="false"/>"</p>
	</c:if>
	<c:if test="${empty savedSearchForm.savedSearch.keywords}">
		<p><bright:cmsWrite identifier="snippet-save-most-recent-search" filter="false"/></p>
	</c:if>
	
	<p><bright:cmsWrite identifier="snippet-save-search-warning" filter="false"/></p>

	<html:form action="saveSearch" method="post" enctype="multipart/form-data" styleClass="floated">
		<%@include file="inc_saved_search_fields.jsp"%>
		<c:if test="${savedSearchForm.recent}">
			<a href="viewSavedSearches" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		</c:if>
		<c:if test="${not savedSearchForm.recent}">
			<a href="search?cachedCriteria=1" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		</c:if>
	</html:form>
	

	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>