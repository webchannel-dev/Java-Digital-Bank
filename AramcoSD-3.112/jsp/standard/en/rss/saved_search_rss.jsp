<?xml version="1.0" encoding="utf-8"?>

<% 
	response.setContentType("text/xml");
	response.setCharacterEncoding("utf-8");
	pageContext.setAttribute("now",new java.util.Date());
%>
	
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="descriptionMaxLength" settingName="rss-description-max-length"/>
<bright:applicationSetting id="hideThumbnails" settingName="hide-thumbnails-on-browse-search"/>
<bright:applicationSetting id="thumbHeight" settingName="image-homogenized-max-height"/>
<bright:applicationSetting id="thumbWidth" settingName="image-homogenized-max-width"/>
<bright:applicationSetting id="appUrl" settingName="application-url"/>
<bright:applicationSetting id="multiLineRssDescriptions" settingName="rss-desciption-multi-line"/>

<c:if test="${empty appUrl}">
	<c:set var="appUrl"><%= request.getRequestURL().toString().substring(0, request.getRequestURL().toString().indexOf(request.getContextPath())) + request.getContextPath() %></c:set>
</c:if>

<bean:parameter name="name" id="name" value="[no name]"/>
<logic:present name="searchBuilderForm">
	<bean:define name="searchBuilderForm" id="searchForm"/>
</logic:present>

<rss version="2.0"
	xmlns:media="http://search.yahoo.com/mrss/"
	xmlns:dc="http://purl.org/dc/elements/1.1/"
	>
	<channel>
		<title><bright:cmsWrite identifier="company-name"/> :: <bright:cmsWrite identifier="app-name"/> - <c:out value="${name}"/></title>
		<link><c:out value="${appUrl}"/>/</link>
 		<description></description>
 		<language><c:out value="${userprofile.currentLanguage.code}"/></language>
		<pubDate><bean:write name="now" format="EEE, dd MMM yyyy hh:mm:ss Z"/></pubDate>
		<lastBuildDate><bean:write name="now" format="EEE, dd MMM yyyy hh:mm:ss Z"/></lastBuildDate>
		<generator><c:out value="${appUrl}"/>/</generator>
		<image>
			<url><c:out value="${appUrl}"/>/images/standard/app_rss_logo.gif</url>
			<title><bright:cmsWrite identifier="company-name"/> :: <bright:cmsWrite identifier="app-name"/></title>
			<link><c:out value="${appUrl}"/>/</link>
		</image>
		<logic:notEmpty name="searchForm" property="searchResults">
			<logic:iterate name="searchForm" property="searchResults.searchResults" id="item" indexId="index">
				<c:set var="title"><bean:write name="item" property="searchName"/></c:set>
				<c:set var="description"><bright:writeWithTruncateTag endString="..." name="item" property="searchDescription" maxLengthBean="descriptionMaxLength"/></c:set>
				<item>
					<title><c:out value="${title}"/></title>
					<link><c:out value="${appUrl}"/>/action/viewAsset?id=<c:out value="${item.id}"/></link>
					<description>
&lt;p&gt;&lt;a href=&quot;<c:out value="${appUrl}"/>/action/viewAsset?id=<c:out value="${item.id}"/>&quot; title=&quot;<c:out value="${title}"/>&quot;&gt;
<c:if test="${not empty item.displayHomogenizedImageFile.path}">
	&lt;img src=&quot;<c:out value="${appUrl}"/>/servlet/display?file=<c:out value="${item.displayHomogenizedImageFile.path}"/>&quot; height=&quot;<c:out value="${thumbHeight}"/>&quot; width=&quot;<c:out value="${thumbWidth}"/>&quot; alt=&quot;<c:out value="${title}"/>&quot; /&gt;&lt;/a&gt;&lt;/p&gt;
</c:if>
<c:if test="${empty item.displayHomogenizedImageFile.path}">
	&lt;img src=&quot;<c:out value="${appUrl}"/>/servlet/display?file=<c:out value="${item.displayThumbnailImageFile.path}"/>&quot; alt=&quot;<c:out value="${title}"/>&quot; /&gt;&lt;/a&gt;&lt;/p&gt;
</c:if>
<c:if test="${not empty description}">
	&lt;p&gt;<c:if test="${multiLineRssDescriptions}"><bright:writeWithCR name="description"/></c:if><c:if test="${!multiLineRssDescriptions}"><bean:write name="description"/></c:if>&lt;/p&gt;
</c:if>
					</description>
					<pubDate><bean:write name="item" property="dateAdded" format="EEE, dd MMM yyyy hh:mm:ss Z"/></pubDate>
					<author><bright:cmsWrite identifier="company-name"/></author>
					<guid><c:out value="${appUrl}"/>/action/viewAsset?id=<c:out value="${item.id}"/></guid>
		         <media:content url="<c:out value="${appUrl}"/>/servlet/display?file=<c:out value="${item.displayPreviewImageFile.path}"/>" 
						       type="image/jpeg"
						       height=""
						       width=""/>
		        <media:title><c:out value="${title}"/></media:title>
		        <media:thumbnail url="<c:out value="${appUrl}"/>/servlet/display?file=<c:out value="${item.displayHomogenizedImageFile.path}"/>" height="<c:out value="${thumbHeight}"/>" width="<c:out value="${thumbWidth}"/>" />
				</item>
			</logic:iterate>
		</logic:notEmpty>
	</channel>
</rss>
