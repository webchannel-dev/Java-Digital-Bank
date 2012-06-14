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
	<bean:define id="section" value="content"/>
	<bean:define id="helpsection" value="news"/>
	<bean:define id="pagetitle" value="News"/>
	<bean:define id="tabId" value="news"/>
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	<%@include file="../public/inc_content_tabs.jsp"%>
	
	<h2>Edit News</h2>
	
	<logic:empty name="newsForm" property="newsItems">
		<p>There are no news items.</p>
		
		<div class="hr"></div>
		<p><a href="viewEditNewsItem">Add news item &raquo;</a></p>
		
	</logic:empty>
	<logic:notEmpty name="newsForm" property="newsItems">
		<table cellspacing="0" cellpadding="0">
			<tr>
				<td style="vertical-align: top;">
					<table cellspacing="0" class="list highlight"  summary="List of news items">
						<thead>
							<tr>
								<th>Title</th>
								<th>Date/time</th>
								<th>Published?</th>
								<th colspan="2">Actions</th>
							</tr>
						</thead>
						<tbody>
							<logic:iterate name="newsForm" property="newsItems" id="item">
								<tr>
									<td><bean:write name="item" property="name"/></td>
									<td><bean:write name="item" property="createdDate" format="dd/MM/yyyy kk:mm"/></td>
									<td><c:choose><c:when test="${item.published}"><a href="publishNewsItem?id=<bean:write name='item' property='id'/>" onclick="return confirm('Are you sure you want to unpublish this news item?');">yes</a></c:when><c:otherwise><a href="publishNewsItem?id=<bean:write name='item' property='id'/>" onclick="return confirm('Are you sure you want to publish this news item?');">no</a></c:otherwise></c:choose></td>
									<td class="action">
										[<a href="viewEditNewsItem?id=<bean:write name='item' property='id'/>">edit</a>]
									</td>
									<td class="action">
										[<a href="deleteNewsItem?id=<bean:write name='item' property='id'/>" onclick="return confirm('Are you sure you want to delete this news item?');">X</a>]
									</td>
								</tr>
							</logic:iterate>
						</tbody>	
					</table>
					
					<p><a href="viewEditNewsItem">Add news item &raquo;</a></p>
				</td>
				<td style="padding-left: 40px; width: 284px">			
					<c:if test="${newsPanelPosition=='right'}">
						<%@include file="../inc/inc_news_panel.jsp"%>
					</c:if>
				</td>
			</tr>
		</table>
		
		<c:if test="${newsPanelPosition=='middle'}">
			
			<br/>
			<div style="width:475px">
				<%@include file="../inc/inc_news_panel.jsp"%>
			</div>
		</c:if>

	</logic:notEmpty>
	
	<br/>	
	
	<br />

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>