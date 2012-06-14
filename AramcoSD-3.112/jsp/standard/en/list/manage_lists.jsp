<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d14		Martin Wilson	14-Jun-2006		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:refDataList id="lists" componentName="ListManager" methodName="getLists"/>
<bright:applicationSetting id="iFeaturedImageWidth" settingName="featured-image-width"/>



<head>
	
	<title><bright:cmsWrite identifier="title-manage-content" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="content"/>
	<bean:define id="pagetitle" value="Content"/>
	<bean:define id="tabId" value="content"/>
	<script type="text/javascript" charset="utf-8">
		$j(function(){
			$j('#contentSearch input.text').focus();
		})
	</script>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>

	<h1>
		<bean:write name="pagetitle" filter="false" />
	</h1>
	<%@include file="../public/inc_content_tabs.jsp"%>

	<h2>Manage Content</h2>

	<p>Select the content you would like to edit from the list below or
		switch to cms edit mode using the button below and browse around the
		site to edit the required content:</p>

	<p>
	<form id="cmsEditForm" method="get" action="toggleCMSEditMode">
		<input type="hidden" name="editMode"
			value="<bean:write name='userprofile' property='CMSEditMode'/>" />
		<c:choose>
			<c:when test="${userprofile.CMSEditMode}">
				<input type="submit" name="submit" value="Turn off edit mode"
					class="button flush" />
			</c:when>
			<c:otherwise>
				<input type="submit" name="submit" value="Turn on edit mode"
					class="button flush" />
			</c:otherwise>
		</c:choose>
	</form>
	</p>
	<br />
	
	<div id="contentSearch">
		

		<html:form action="contentSearch" method="post">
			<h3>Can't find the right content item?</h3>
			<div style="margin-bottom: 1em">
				<label>Search for content:</label>
				<html:text name="contentSearchForm" property="searchText" value="${contentSearchForm.searchText}" size="70" maxlength="255" styleClass="text" />
				<html:submit value="Search" styleClass="button flush" /> <br />
			</div>
			<logic:present name="listItems">
				<bean:size id="numListItems" name="listItems" />
				<logic:notEmpty name="listItems">
					<p class="noBottomMargin"><strong><c:out value="${numListItems}"/></strong> results found:</p>
					<table cellspacing="0" class="list" summary="List of groups">
						<thead>
							<tr>
								<th>Content Item</th>
								<c:if test="${supportMultiLanguage}">
									<th>Language</th>
								</c:if>
								<th colspan="2">&nbsp;</th>
							</tr>
						</thead>
						<tbody>
						<logic:iterate name="listItems" id="listItem">
							<tr <c:if test="${listItem.created}">class="disabled"</c:if>>
								<td><bean:write name="listItem" property="title" /></td>
								<c:if test="${supportMultiLanguage}">
									<td><bean:write name="listItem" property="language.name" />
								</c:if>
								<td class="action">
									<c:choose>

										<c:when test="${not listItem.created}">
									[<a
												href="viewEditListItem?id=<bean:write name='listItem' property='identifier'/>&listOrder=2&languageId=<bean:write name='listItem' property='language.id'/>&url=/action/contentSearch">edit</a>]
								</c:when>
										<c:otherwise>
											<span style="color: black">[<a
												href="viewEditListItem?id=<bean:write name='listItem' property='identifier'/>&listOrder=2&languageId=<bean:write name='listItem' property='language.id'/>&url=/action/contentSearch">add</a>]</span>
										</c:otherwise>
									</c:choose></td>
								<td class="action"><c:if
										test="${not listItem.cannotBeDeleted || not listItem.language.default}">
										<c:choose>
											<c:when test="${not listItem.created}">
											[<a
													href="deleteListItem?id=<bean:write name='listItem' property='identifier'/>&listid=<bean:write name='listItem' property='listId'/>&languageId=<bean:write name='listItem' property='language.id'/>&listOrder=2"
													onclick="return confirm('Are you sure you want to delete this content item?');">X</a>]
										</c:when>
											<c:otherwise>
											[<span class="disabled"><strong>X</strong> </span>]
										</c:otherwise>
										</c:choose>
									</c:if></td>
						</logic:iterate>
						</tbody>
					</table>
				</logic:notEmpty>

				<logic:empty name="listItems">
					<br />
					<p><em>No Results found</em></p>
				</logic:empty>

			</logic:present>
		</html:form>

	</div>

	<c:if test="${iFeaturedImageWidth gt 0}">
		<table cellspacing="0" class="list highlight" summary="List of groups">
			<thead>
				<tr>
					<th>Featured Image</th>
					<th>&nbsp;</th>
				</tr>
			</thead>
			<tr>
				<td>Manage featured images</td>
				<td class="action">[<a href="viewFeaturedAssets">edit</a>]</td>
			</tr>

		</table>
	</c:if>

	<table class="list highlight" cellspacing="0">
		<thead>
			<tr>

				<th>Content Areas</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<logic:iterate name="lists" id="list">
			<tr>
				<td><bean:write name="list" property="name" /></td>
				<td class="action">[<a
					href="manageList?id=<bean:write name='list' property='id'/>&listOrder=2">edit</a>]
				</td>
			</tr>
		</logic:iterate>
		<tr>
			<td>Email templates</td>
			<td class="action">[<a href="manageEmailTemplates?typeId=1">edit</a>]
			</td>
		</tr>
		<tr>
			<td>Message templates</td>
			<td class="action">[<a href="manageMessageTemplateTypes">edit</a>]
			</td>
		</tr>
	</table>

	<%@include file="../inc/body_end.jsp"%>


</body>
</html>