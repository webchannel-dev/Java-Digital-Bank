<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		15-Jul-2008		Created 
	 d2     Matt Woollard   20-Jul-2009     Removed character limit for news body
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
	<bean:define id="helpsection" value="news"/>
	<bean:define id="pagetitle" value="Edit News Item"/>
	<bean:define id="tabId" value="news"/>
	
	<%@include file="../inc/inc_mce_editor.jsp"%>
	<script type="text/JavaScript">
		// give the name field the focus once the dom is ready
		$j(function () {
  			$j('#name').focus();
 		});
	</script>		
</head>

<bean:parameter id="url" name="url" value=""/>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>

	<logic:equal name="newsForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="newsForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>
	<p><span class="required">*</span> denotes a mandatory field.</p>
	<html:form action="saveNewsItem" method="post">
		
		<c:if test="${newsForm.newsItem.id>0}">
			<html:hidden name="newsForm" property="newsItem.id"/>
		</c:if>
		<table cellspacing="0" class="form" summary="Form for list item details">
			<tr>
				<th><label for="name">Title: <span class="required">*</span></label></th>
				<td>
					<html:text name="newsForm" property="newsItem.name" styleId="name" maxlength="255"/>
					<input type="hidden" name="mandatory_newsItem.name" value="Please enter a title for the news item."/>
				</td>
			</tr>
			<logic:notEmpty name="newsForm" property="newsItem.translations">
				<logic:iterate name="newsForm" property="newsItem.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="itemTitle<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<html:hidden name="newsForm" property="newsItem.translations[${tIndex}].language.id"/>
								<html:hidden name="newsForm" property="newsItem.translations[${tIndex}].language.name"/>
								<input type="text" class="text" name="newsItem.translations[<bean:write name='tIndex'/>].name" maxlength="40" id="itemTitle<bean:write name='tIndex'/>" value="<bean:write name="translation" property="name" filter="false"/>"/>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
			<tr>
				<th><label for="createdDate">Date/time: </label></th>
				<td>
					<html:text name="newsForm" property="createdDate" maxlength="255" styleId="createdDate" />
					<span class="disabled" style="margin-left: 6px;">Note, the time will not be shown to users, but will affect the ordering of news items.</span>
				</td>
			</tr>
			<tr>
				<th><label for="published">Published? </label></th>
				<td>
					<html:checkbox name="newsForm" property="newsItem.published" styleClass="checkbox" styleId="published" />
				</td>
			</tr>
			<tr>
				<input type="hidden" name="mandatory_newsItem.content" value="Please enter some content for the news item."/>
				<th><label for="content">Content: <span class="required">*</span></label></th>
				<td>
					<textarea style="width:80%;" class="editor" name="newsItem.content" cols="90" rows="20"><bean:write name="newsForm" property="newsItem.content" filter="false"/></textarea>						
				</td>
			</tr>
			<logic:notEmpty name="newsForm" property="newsItem.translations">
				<logic:iterate name="newsForm" property="newsItem.translations" id="translation" indexId="tIndex">
					<logic:greaterThan name="translation" property="language.id" value="0">
						<tr>
							<th class="translation">
								<label for="newsItemContent<bean:write name='tIndex'/>">(<bean:write name="translation" property="language.name"/>):</label> 
							</th>
							<td>
								<html:hidden name="newsForm" property="newsItem.translations[${tIndex}].language.id"/>
								<html:hidden name="newsForm" property="newsItem.translations[${tIndex}].language.name"/>
								<textarea style="width:80%;" class="editor" name="newsItem.translations[<bean:write name='tIndex'/>].content" rows="3" cols="60" id="newsItemContent<bean:write name='tIndex'/>"><bean:write name="translation" property="content" filter="false"/></textarea>
							</td>
						</tr>
					</logic:greaterThan>
				</logic:iterate>
			</logic:notEmpty>
		</table>

		<div class="hr"></div>
		
		
		<input type="submit" class="button flush floated" value="<bright:cmsWrite identifier="button-save" filter="false" />" /> 
		<a href="manageNewsItems" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
	</html:form>

	
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>