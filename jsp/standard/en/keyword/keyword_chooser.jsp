<%@ page import="com.bright.assetbank.application.constant.AssetBankSettings" %>
<%@include file="../inc/doctype_html.jsp" %>

<!-- Developed by bright interactive www.bright-interactive.com -->
<%-- History:
	 d1		James Home		19-Jan-2004		Created.
	 d2		Ben Browning	09-Feb-2006		Tidied up html
	 d3     Steve Bryan		18-Jul-2006		Changed id of keywords field to avoid clash with META tags
	 d4		Matt Stevenson	19-Mar-2007		Fixed problem with disabling add controls
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bean:define id="keywordDelimiter" value="<%= AssetBankSettings.getKeywordDelimiter() %>"/>
<bright:applicationSetting id="keywordSelectByHierarchy" settingName="keywords-select-by-hierarchy"/>

<%-- Keywords field name on opening page - do not use 'keywords' since may clash with META tag name --%>
<bean:parameter id="attributeId" name="attributeId" value="-1"/>
<bean:parameter id="index" name="index" value="0"/>
<c:choose>
	<c:when test="${attributeId != 'undefined' && attributeId > 0}">
		<c:set var="keywords_field_name" value="field${attributeId}"/>
		<c:if test="${index>0}">
			<c:set var="keywords_field_name" value="${keywords_field_name}_${index}"/>
		</c:if>
	</c:when>
	<c:otherwise>
		<c:set var="keywords_field_name" value="keywords_field"/>
	</c:otherwise>
</c:choose>

<head>
	<title><bright:cmsWrite identifier="title-keyword-chooser" filter="false"/></title> 
	<script src="../js/keywordChooser.js" type="text/javascript"></script>
	<%@include file="../inc/head-elements.jsp"%>

</head>
<body id="popup" onload="this.focus(); showAddControls('<bean:write name='keywords_field_name' />', '<bean:write name='keywordDelimiter' />');">

<bean:parameter id="categoryTypeId" name="categoryTypeId" value="-1"/>
<bean:parameter id="filter" name="filter" value="a"/>

	<h1 <c:if test="${!keywordSelectByHierarchy}">class="underline"</c:if>><bright:cmsWrite identifier="heading-keyword-chooser" filter="false"/> - <span style="text-transform: uppercase"><bean:write name="keywordChooserForm" property="filter"/></span></h1> 
	
	<c:if test="${keywordSelectByHierarchy}">
		<p class="tabHolderPopup">
			<a class="active" href="keywordChooser?categoryTypeId=<c:out value='${categoryTypeId}'/>&attributeId=<c:out value='${attributeId}'/>"><bright:cmsWrite identifier="tab-alphabetical" filter="false" /></a>
			<a href="keywordChooserHierarchy?categoryTypeId=<c:out value='${categoryTypeId}'/>&attributeId=<c:out value='${attributeId}'/>"><bright:cmsWrite identifier="tab-hierarchical" filter="false" /></a>
		</p>
		
		<div id="tabContent">
	</c:if>
	
		<c:set var='sUrl' value='keywordChooser?categoryTypeId=${categoryTypeId}&attributeId=${attributeId}&index=${index}&filter=' />

		<c:if test="${userprofile.currentLanguage.usesLatinAlphabet}">
			<%@include file="inc_a_to_z.jsp"%>
		</c:if>
		
		<logic:iterate name="keywordChooserForm" property="errors" id="errorText">
			<bean:write name="errorText" filter="false"/><br />
		</logic:iterate>
		
		<div class="hr"></div>
		<logic:notEmpty name="keywordChooserForm" property="keywordList">
		<table cellspacing="0" cellpadding="0" class="admin list" width="100%">
			<tr>
				<th><bright:cmsWrite identifier="label-keyword" filter="false"/></th>
				<th style="width:220px; overflow:hidden"><bright:cmsWrite identifier="label-synonyms" filter="false"/></th>
				<th style="width:220px; overflow:hidden"><bright:cmsWrite identifier="label-num-uses" filter="false"/></th>
				<th></th>
			</tr>
			<logic:iterate name="keywordChooserForm" property="keywordList" id="keyword">
				<bright:itemCount id="itemCount" name="keyword"/>
				<c:if test="${!browse || itemCount>0}">
					<tr>
						<td>
							<bean:write name="keyword" property="name" filter="false"/>
							<c:if test="${keyword.expired}">
								<span class="disabled" style="font-style: italic;">&nbsp;&nbsp;(<bright:cmsWrite identifier="snippet-expired" filter="false"/>)</span>
							</c:if>
						</td>
						<td>
							<bean:write name="keyword" property="synonymsForDisplay" filter="false"/>&nbsp;
						</td>
						<td>
							<bean:write name="itemCount" filter="false"/>&nbsp;
						</td>
						<td style="text-align:right; padding-right:0;">
							<a href="#" name="addbutton" onclick="addKeyword('<bean:write name='attributeId' />', '<bean:write name='keyword' property='nameWithEscapedQuotes' filter='false' />', '<bean:write name='keywordDelimiter' />'<c:if test="${index>0}">, <c:out value="${index}"/></c:if>); return false;" id="<bean:write name='keyword' property='name' />">[<bright:cmsWrite identifier="link-add" filter="false"/>]</a>
						</td>
					</tr>
				</c:if>
			</logic:iterate>	
		</table>
		</logic:notEmpty>
		<logic:empty name="keywordChooserForm" property="keywordList">
			<p><bright:cmsWrite identifier="snippet-no-keywords-for-letter" filter="false"/> <strong><span style="text-transform:uppercase"><bean:write name="filter"/></span></strong></p>
		</logic:empty>
		
	<c:if test="${keywordSelectByHierarchy}">
		</div>
	</c:if>
	<c:if test="${!keywordSelectByHierarchy}">
		<div class="hr"></div>
	</c:if>

	<div style="text-align:right; margin-top: 8px;">
		<script type="text/javascript">
			document.write('<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button" id="submitButton" onclick="window.close();">');
		</script>
	</div>

						
</body>
</html>