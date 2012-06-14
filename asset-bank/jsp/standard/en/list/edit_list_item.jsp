<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Martin Wilson	14-Jun-2006		Created
	 d2		Matt Stevenson	02-Nov-2007		Added bounce url parameter
	 d3		Matt Stevenson	05-Nov-2007		Fixed problem with cancel url
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>

<head>
	
	<title><bright:cmsWrite identifier="title-edit-content-item" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="content"/>
	<bean:define id="pagetitle" value="Edit Content Item"/>
	<bean:define id="tabId" value="content"/>
	
	<%@include file="../inc/inc_mce_editor.jsp"%>


</head>

<bean:parameter id="url" name="url" value=""/>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>

	<logic:equal name="listForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="listForm" property="errors" id="errorText">
				<bright:writeError name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form action="saveListItem" method="post">
		<html:hidden name="listForm" property="listItem.listId"/>
		<html:hidden name="listForm" property="listItem.listItemTextTypeId"/>
		<html:hidden name="listForm" property="listItem.identifier"/>
		<html:hidden name="listForm" property="listItem.cannotBeDeleted"/>
		<html:hidden name="listForm" property="listItem.language.id"/>
		<html:hidden name="listForm" property="listItem.title"/>
		<input type="hidden" name="url" value="<bean:write name='url'/>"/>
		<input type="hidden" name="listOrder" value="2"/>
		<input type="hidden" name="maxlength_listItem.body" value="4000" />
		<table cellspacing="0" class="form" summary="Form for list item details">
			<c:if test="${listForm.listItem.identifier != null && listForm.listItem.identifier != ''}">
			<tr>
				<th>Title:</th>
				<td>
					<bean:write name="listForm" property="listItem.title"/>
				</td>
			</tr>
			</c:if>
			<c:if test="${supportMultiLanguage}">
				<tr>
					<th>Language:</th>
					<td><bean:write name="listForm" property="listItem.language.name"/></td>
				</tr>
			</c:if>
			<c:if test="${listForm.listItem.identifier != null && listForm.listItem.identifier != ''}">
			<tr>
				<th>Identifier:</th>
				<td>
					<bean:write name="listForm" property="listItem.identifier"/>
				</td>
			</tr>
			</c:if>
			<c:if test="${listForm.listItem.identifier != null && listForm.listItem.identifier != ''}">
			<%-- <tr>
				<th>Summary:</th>
				<td>
					<textarea class="text" name="listItem.summary"><bean:write name="listForm" property="listItem.summary" filter="false"/></textarea>
				</td>
			</tr> --%>
			<html:hidden property="listItem.summary"/>
			</c:if>
			<tr>
				<th>Content:</th>
				<td>
					<c:choose>
						<c:when test="${listForm.listItem.listItemTextTypeId == 1}">
							<textarea style="width:80%;" class="editor" name="listItem.body" cols="90" rows="20"/><bean:write name="listForm" property="listItem.body" filter="false"/></textarea>	
						</c:when>
						<c:when test="${listForm.listItem.listItemTextTypeId == 3}">
							<textarea class="text" name="listItem.body" cols="90" rows="20"/><bean:write name="listForm" property="listItem.body" filter="false"/></textarea>
						</c:when>
						<c:otherwise>
							<input type="text" name="listItem.body" size="35" maxlength="250" value="<bean:write name="listForm" property="listItem.body"/>" />		
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</table>

		<div class="hr"></div>
		
		<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		
		<c:choose>
		<c:when test="${url != null && url != ''}">
			<a href="..<bean:write name='url'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		</c:when>
		<c:otherwise>
			<a href="manageList?id=<bean:write name='listForm' property='listItem.listId'/>&listOrder=2" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		</c:otherwise>
	</c:choose>
	
	</html:form>

	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>