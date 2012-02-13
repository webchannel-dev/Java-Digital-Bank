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
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>

	<h2>Manage Content</h2>	

		<p>Select the content you would like to edit from the list below or switch to cms edit mode using the button below and browse around the site to edit the required content:</p>

		<p>
			<form id="cmsEditForm" method="get" action="toggleCMSEditMode">
				<input type="hidden" name="editMode" value="<bean:write name='userprofile' property='CMSEditMode'/>"/>
				<c:choose>
					<c:when test="${userprofile.CMSEditMode}">
						<input type="submit" name="submit" value="Turn off edit mode" class="button flush" />
					</c:when>
					<c:otherwise>
						<input type="submit" name="submit" value="Turn on edit mode" class="button flush" />
					</c:otherwise>
				</c:choose>
			</form>
		</p>
		<br/>
	
		
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
					<td class="action">
						[<a href="viewFeaturedAssets">edit</a>]
					</td>
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
				<td><bean:write name="list" property="name"/></td>
				<td class="action">
					[<a href="manageList?id=<bean:write name='list' property='id'/>&listOrder=2">edit</a>]
				</td>
			</tr>
		</logic:iterate>		
		<td>Email templates</td>
		<td class="action">
			[<a href="manageEmailTemplates?typeId=1">edit</a>]
		</td>
				
			
		</table>		

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>