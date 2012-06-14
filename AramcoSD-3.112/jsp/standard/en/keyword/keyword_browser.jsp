<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		15-May-2007		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<logic:present parameter="page">
	<bean:parameter id="p_page" name="page"/>
</logic:present>
<logic:notPresent parameter="page">
	<bean:define id="p_page" value="0"/>
</logic:notPresent>

<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="titleMaxLength" settingName="browse-title-max-length"/>
<bright:applicationSetting id="descriptionMaxLength" settingName="browse-description-max-length"/>

<bright:applicationSetting id="showCategoryItemCounts" settingName="showCategoryItemCounts"/>
<bright:applicationSetting id="hideThumbnails" settingName="hide-thumbnails-on-browse-search"/>
<c:set var="browseableName" value="keyword" />
<c:set var="browseablesName" value="${keywordChooserForm.browserName}" />

<bean:define id="section" value="browse"/>


<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | <bright:cmsWrite identifier="heading-browse-keywords" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
</head>
<body id="browsePage">
	
	<%@include file="../inc/body_start.jsp"%>

	<h1>
		<bright:cmsWrite identifier="heading-browse" filter="false"/> 
	</h1> 

	
	<c:set var="tabId" value="browse${keywordChooserForm.categoryTreeId}"/>
	<%@include file="../public/inc_browse_tabs.jsp"%>

	<bean:define id="browseItemsForm" name="keywordChooserForm" />
	<c:if test="${browseItemsForm.searchResults.numResults>0}">	
	
		<p id="expandLink"><a href="#" onclick="expand_content('keywordSelect'); return false;"><bright:cmsWrite identifier="link-select-keyword" filter="false"/></a></p>
		
		<script type="text/javascript">
			<!--
			document.getElementById('expandLink').style.display = 'block';
			-->
		</script>
		
	</c:if>	
	
	<div id="keywordSelect">
		<p id="collapseLink" ><a href="#" onclick="collapse_content('keywordSelect');"><bright:cmsWrite identifier="link-hide-keywords" filter="false"/></a></p>
		
		<bean:parameter id="keywordId" name="categoryId" value="0"/>
		<c:if test="${browseItemsForm.searchResults.numResults<1 && keywordId>0}"><div class="warning"><bright:cmsWrite identifier="snippet-no-items-for-keyword" filter="false"/></div></c:if>
		
		
		<div class="hr"></div>
	
		<c:set var='sUrl' value='keywordBrowser?categoryTypeId=${keywordChooserForm.categoryTreeId}&filter=' />
	
		<a href="<c:out value='${sUrl}' />a">A</a>&nbsp;
		<a href="<c:out value='${sUrl}' />b">B</a>&nbsp;
		<a href="<c:out value='${sUrl}' />c">C</a>&nbsp;
		<a href="<c:out value='${sUrl}' />d">D</a>&nbsp;
		<a href="<c:out value='${sUrl}' />e">E</a>&nbsp;
		<a href="<c:out value='${sUrl}' />f">F</a>&nbsp;
		<a href="<c:out value='${sUrl}' />g">G</a>&nbsp;
		<a href="<c:out value='${sUrl}' />h">H</a>&nbsp;
		<a href="<c:out value='${sUrl}' />i">I</a>&nbsp;
		<a href="<c:out value='${sUrl}' />j">J</a>&nbsp;
		<a href="<c:out value='${sUrl}' />k">K</a>&nbsp;
		<a href="<c:out value='${sUrl}' />l">L</a>&nbsp;
		<a href="<c:out value='${sUrl}' />m">M</a>&nbsp;
		<a href="<c:out value='${sUrl}' />n">N</a>&nbsp;
		<a href="<c:out value='${sUrl}' />o">O</a>&nbsp;
		<a href="<c:out value='${sUrl}' />p">P</a>&nbsp;
		<a href="<c:out value='${sUrl}' />q">Q</a>&nbsp;
		<a href="<c:out value='${sUrl}' />r">R</a>&nbsp;
		<a href="<c:out value='${sUrl}' />s">S</a>&nbsp;
		<a href="<c:out value='${sUrl}' />t">T</a>&nbsp;
		<a href="<c:out value='${sUrl}' />u">U</a>&nbsp;
		<a href="<c:out value='${sUrl}' />v">V</a>&nbsp;
		<a href="<c:out value='${sUrl}' />w">W</a>&nbsp;
		<a href="<c:out value='${sUrl}' />x">X</a>&nbsp;
		<a href="<c:out value='${sUrl}' />y">Y</a>&nbsp;
		<a href="<c:out value='${sUrl}' />z">Z</a>&nbsp;
		<a href="<c:out value='${sUrl}' />all">All</a>&nbsp;
			
		<br/>
	
		<logic:iterate name="keywordChooserForm" property="errors" id="errorText">
			<bean:write name="errorText" filter="false"/><br />
		</logic:iterate>
		<div class="hr"></div>
		
		
		<bean:define id="categoryTreeId" name="keywordChooserForm" property="categoryTreeId"/>
		<bean:define id="keywordList" name="keywordChooserForm" property="keywords"/>
		<bean:define id="filter" name="keywordChooserForm" property="filter"/>
		<bean:define id="colLength" name="keywordChooserForm" property="colLength"/>
		<%@include file="../keyword/inc_browser.jsp"%>
				
	</div><!-- end of #keywordSelect -->
	
	<c:if test="${browseItemsForm.searchResults.numResults>0}">	
		<script type="text/javascript">
			<!--
			document.getElementById('keywordSelect').style.display = 'none';
			-->
		</script>	
	</c:if>
	
	
	<c:set var="browseUrl" value="/action/browseByKeyword"/>
	<c:set var="browseParams" value="categoryId=${keywordId}&categoryTypeId=${keywordChooserForm.categoryTreeId}&filter=${keywordChooserForm.filter}"/>
	<c:set var="forwardParams" value="forward=${browseUrl}&${browseParams}" />
	<c:set var="linkUrl" value="browseByKeyword?${browseParams}"/>
	<div class="clearing"></div>
	
	
	<c:set scope="session" var="breadcrumbTrail" value="${keywordChooserForm.breadcrumbTrail}" />	
	<logic:notEmpty name="keywordChooserForm" property="searchResults">
		<div class="hr"></div>
		
		<c:set var="browseAction" value="browseByKeyword"/>
		<c:set var="noLinks" value="true" />
		<bean:define id="preposition" value="for"/>
		<%@include file="../category/inc_browse_category_controls.jsp" %>
		<%@include file="../inc/browse.jsp"%>
	</logic:notEmpty>

	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>



