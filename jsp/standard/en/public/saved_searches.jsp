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
	
	<h2><bright:cmsWrite identifier="heading-saved-searches" filter="false"/></h2>
	
	<logic:notEmpty name="savedSearchForm" property="savedSearches">
		<table>
			<logic:iterate name="savedSearchForm" property="savedSearches" id="search">
				<tr>
					<td><bean:write name="search" property="name" filter="false"/></td>
					<c:if test="${canCreateSearchRss}">
						<td>
							<c:if test="${search.rssFeed}">
								<a title="<bright:cmsWrite identifier="link-view-rss" filter="false"/>" target="_blank" href="savedSearchRss.xml?userId=<c:out value="${userprofile.user.id}"/>&name=<c:out value="${search.name}"/>&numAssets=<c:out value="${numAssetsInRssFeed}"/>"><img src="../images/standard/icon/rss-icon.gif" alt="RSS enabled" height="13" width="13" border="0" align="absmiddle" style="margin-left:6px;"></a>
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
					<td style="padding-left: 8px;">
						<a href="runSavedSearch?name=<bright:write name='search' property='name'/>&id=<bean:write name='search' property='userId'/>"><bright:cmsWrite identifier="link-run" filter="false"/></a>
						<c:if test="${canCreateSearchRss}">
							|
							<c:if test="${search.rssFeed}">
								<a href="enableSavedSearchRss?name=<c:out value="${search.name}"/>&disable=true">disable RSS</a>
							</c:if>
							<c:if test="${!search.rssFeed}">
								<a href="enableSavedSearchRss?name=<c:out value="${search.name}"/>">enable RSS</a>
							</c:if>
						</c:if>
						| <a href="viewSavedSearch?name=<bright:write name='search' property='name' encodeForUrl='true'/>"><bright:cmsWrite identifier="link-edit" filter="false"/></a>
						| <a href="deleteSavedSearch?name=<bright:write name="search" property="name" encodeForUrl="true"/>" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-delete-saved-search" filter="false"/>');"><bright:cmsWrite identifier="link-delete" filter="false"/></a>
					</td>
				</tr>
			</logic:iterate>
		</table>
	</logic:notEmpty>
	<logic:empty name="savedSearchForm" property="savedSearches">
		<p><bright:cmsWrite identifier="snippet-no-saved-searches" filter="false"/></p>
	</logic:empty>
	
	<div class="hr"></div>
	
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