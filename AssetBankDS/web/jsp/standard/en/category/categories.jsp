<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		14-May-2007		Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>



<head>
	
	<title><bright:cmsWrite identifier="title-all-cats" filter="false"/></title> 
	<bean:define id="section" value="browse"/>
	<%@include file="../inc/head-elements.jsp"%>
</head>
<body id="browsePage">
	
	<%@include file="../inc/body_start.jsp"%>

	<h1><bright:cmsWrite identifier="heading-browse" filter="false"/></h1> 
	
	<bean:define id="tabId" value="browseCategories"/>
	<%@include file="../public/inc_browse_tabs.jsp"%>

	<bright:cmsWrite identifier="intro-all-cats" filter="false" />
	
	<ul>
		<logic:iterate name="categoriesForm" property="categories" id="category">
			<li><a href="browseItems?categoryId=<bean:write name='category' property='id'/>&categoryTypeId=<bean:write name='category' property='categoryTypeId'/>&allCats=1"><bean:write name="category" property="name"/></a></li>
		</logic:iterate>
	</ul>

	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>
