<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home	15-Jul-2008		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>
<bright:applicationSetting id="newsPanelPosition" settingName="news-panel-position"/>

<head>
	<title><bright:cmsWrite identifier="title-manage-content" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="news"/>
	<bean:define id="pagetitle" value="News"/>
	<bean:define id="tabId" value="news"/>
</head>

<body>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-news" filter="false"/></h1> 

	<logic:iterate name="newsForm" property="newsItems" id="newsItem" indexId="newsItemIndex">

		<div class="newsDate"><bean:write name="newsItem" property="createdDate" format="dd MMM, yyyy"/></div>
		<h2><bean:write name="newsItem" property="name" /></h2>
		<p>
			<bean:write name="newsItem" property="content" filter="false"/>
			<c:if test="${newsItem.isTruncated}">
				...
				<br />
				<a href="viewNewsItem?id=<bean:write name="newsItem" property="id" />"><bright:cmsWrite identifier="link-more" filter="false"/></a>
			</c:if>
		</p>
		<div class="hr"></div>
	</logic:iterate>

	<br/><br />

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>