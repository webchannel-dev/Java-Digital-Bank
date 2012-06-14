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
<bright:applicationSetting id="numAssetsInRssFeed" settingName="default-search-results-via-rss"/>

<bright:applicationSetting id="savedSearchesEnabled" settingName="saved-searches-enabled"/>
<bright:applicationSetting id="batchReleasesEnabled" settingName="batch-releases-enabled"/>

<head>
	<title><bright:cmsWrite identifier="title-recent-searches" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="search"/>
	<bean:define id="helpsection" value="recent_searches"/>
	<c:set var="tabId" value="recentSearches" />
</head>
<body id="searchPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<%@include file="inc_search_tabs.jsp"%>
	
	<h2><bright:cmsWrite identifier="heading-recent-searches" filter="false"/></h2>
	
	<logic:notEmpty name="savedSearchForm" property="recentSearches">
		<table>
			<logic:iterate name="savedSearchForm" property="recentSearches" id="search" indexId="index">
				<tr>
					<td><bean:write name="search" property="timeLastRun" format="HH:mm:ss"/> :</td>
					<td>
						<c:if test="${not empty search.keywords}">
							"<bright:write name="search" property="keywords" maxLength="80" endString="..." filter="false"/>"
						</c:if>
						<c:if test="${empty search.keywords}">
							<span class="disabled"><bright:cmsWrite identifier="snippet-no-search-term" filter="false"/></span>
						</c:if>
					</td>
					<td style="padding-left: 8px;">
						<a href="runRecentSearch?index=<bright:write name="index"/>"><bright:cmsWrite identifier="link-run" filter="false"/></a>
						| <a href="viewSaveSearch?index=<bright:write name="index"/>&recent=true"><bright:cmsWrite identifier="link-save" filter="false"/></a>
					</td>
				</tr>
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<logic:empty name="savedSearchForm" property="recentSearches">
		<p><bright:cmsWrite identifier="snippet-no-recent-searches" filter="false"/></p>
	</logic:empty>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>