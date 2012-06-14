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
<bright:applicationSetting id="keywordAnscestorsSelectable" settingName="keyword-anscestors-selectable"/>

<%-- Keywords field name on opening page - do not use 'keywords' since may clash with META tag name --%>
<bean:parameter id="attributeId" name="attributeId" value="-1"/>

<c:choose>
	<c:when test="${attributeId != 'undefined' && attributeId > 0}">
		<c:set var="keywords_field_name" value="field${attributeId}"/>
	</c:when>
	<c:otherwise>
		<c:set var="keywords_field_name" value="keywords_field"/>
	</c:otherwise>
</c:choose>

<head>
	<title><bright:cmsWrite identifier="title-keyword-chooser" filter="false"/></title> 
	<script src="../js/keywordChooser.js" type="text/javascript"></script>
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/javascript" src="../js/lib/jquery.dataTables.js"></script>
</head>
<body id="popup" onload="this.focus(); showAddControls('<bean:write name='keywords_field_name' />', '<bean:write name='keywordDelimiter' />');">

<bean:parameter id="categoryTypeId" name="categoryTypeId" value="-1"/>
<bean:parameter id="filter" name="filter" value="a"/>

	<h1><bright:cmsWrite identifier="heading-keyword-chooser" filter="false"/><c:if test="${categoryAdminForm.categoryName!='Keywords'}"> - <bean:write name='categoryAdminForm' property='categoryName' filter="false"/></c:if></h1> 
	
	<p class="tabHolderPopup">
		<a href="keywordChooser?categoryTypeId=<c:out value='${categoryTypeId}'/>&attributeId=<c:out value='${attributeId}'/>"><bright:cmsWrite identifier="tab-alphabetical" filter="false" /></a>
		<a class="active" href="keywordChooserHierarchy?categoryTypeId=<c:out value='${categoryTypeId}'/>&attributeId=<c:out value='${attributeId}'/>"><bright:cmsWrite identifier="tab-hierarchical" filter="false" /></a>
	</p>
	
	<div id="tabContent">
		
		<p>You are here:&nbsp;
			<logic:equal name="categoryAdminForm" property="root" value="false">
				<strong>
					<a href="keywordChooserHierarchy?categoryId=-1&categoryTypeId=<c:out value='${categoryTypeId}'/>&attributeId=<c:out value='${attributeId}'/>"><bright:cmsWrite identifier="snippet-root" filter="false" /></a>
					<logic:iterate name="categoryAdminForm" property="ancestorCategoryList" id="ancestorCategory"> &raquo;
						<a href="keywordChooserHierarchy?categoryId=<bean:write name='ancestorCategory' property='id' />&categoryTypeId=<c:out value='${categoryTypeId}'/>&attributeId=<c:out value='${attributeId}'/>"><bean:write name="ancestorCategory" property="name" filter="false"/></a>
					</logic:iterate>
					&raquo; <bean:write name="categoryAdminForm" property="categoryName" filter="false"/>
				</strong>
			</logic:equal>
			<logic:equal name="categoryAdminForm" property="root" value="true">
				<strong><bright:cmsWrite identifier="snippet-root" filter="false" /></strong>
			</logic:equal>
		</p>
		
		<div class="hr"></div>

		<logic:equal name="categoryAdminForm" property="subCategoryListIsEmpty" value="false">
			<table id="keywordTable" cellspacing="0" cellpadding="0" class="display admin list" width="100%">											
				<thead>
					<tr>
						<th><bright:cmsWrite identifier="label-keyword" filter="false"/></th>
						<c:if test="${keywordAnscestorsSelectable || categoryAdminForm.subCategoriesContainsLeaves}">
							<th style="width:190px; overflow:hidden"><bright:cmsWrite identifier="label-synonyms" filter="false"/></th>
						</c:if>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<logic:iterate name="categoryAdminForm" property="subCategoryList" id="keyword">
						<tr>
							<td>
								<c:if test="${keyword.numChildCategories>0}">
									<a href="keywordChooserHierarchy?categoryId=<bean:write name='keyword' property='id' />&categoryTypeId=<c:out value='${categoryTypeId}'/>&attributeId=<c:out value='${attributeId}'/>">
								</c:if>
										<bean:write name="keyword" property="name" filter="false"/>
								<c:if test="${keyword.numChildCategories>0}">
									</a>
								</c:if>
							</td>
							<c:if test="${keywordAnscestorsSelectable || categoryAdminForm.subCategoriesContainsLeaves}">
								<td>
									<bright:write outputTokenWrapper="[token]" tokenDelimiter="," tokenDelimiterReplacement=", " name="keyword" property="description"/>
								</td>
							</c:if>
							<td style="padding-right:0;">
								<c:if test="${keywordAnscestorsSelectable || keyword.numChildCategories==0}">
									<a href="#" name="addbutton" onclick="addKeyword('<bean:write name='attributeId' />', '<bean:write name='keyword' property='fullNameWithEscapedQuotes' filter='false' />', '<bean:write name='keywordDelimiter' />'); return false;" id="<bean:write name='keyword' property='fullName' />">[<bright:cmsWrite identifier="link-add" filter="false"/>]</a>&nbsp;
								</c:if>
								<c:if test="${keyword.numChildCategories>0}">
									Keywords: <c:out value="${keyword.numChildCategories}"/>
								</c:if>
							</td>
						</tr>
					</logic:iterate>
				</tbody>
			</table>
		</logic:equal>
		<logic:notEqual name="categoryAdminForm" property="subCategoryListIsEmpty" value="false">
			<p>There are no keywords at this level.</p>
		</logic:notEqual>
		
	</div>

	<div style="text-align:right; margin-top: 8px;">
		<script type="text/javascript">
			
			$j('#keywordTable').dataTable({
	            "bJQueryUI": false,
	            "bPaginate": false,
            	"bFilter": true,
            	"bSort": true,
            	"bInfo": false
	        });
	
			document.write('<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button" id="submitButton" onclick="window.close();">');
		</script>
	</div>

						
</body>
</html>