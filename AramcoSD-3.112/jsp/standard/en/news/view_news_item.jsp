<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matthew Woollard      Created
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
</head>

<body>
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-news" filter="false"/></h1> 
	


	<div class="newsDate"><bean:write name="newsForm" property="newsItem.createdDate" format="dd MMM, yyyy"/></div>
	<h2><bean:write name="newsForm" property="newsItem.name" filter="false"/></h2>
	<bean:write name="newsForm" property="newsItem.content" filter="false"/>


	<div class="hr"></div>
	<p><a href="viewNewsItems"><bright:cmsWrite identifier="link-view-all-news" filter="false"/></a></p>
	


	
	<br />

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>