<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationUrl/>

<head>
	<bean:define id="pagetitle" value="Facebook"/>
	<title><bright:cmsWrite identifier="company-name" filter="false" /> | Facebook Admin</title>
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="section" value="facebook"/>
	<bean:define id="tabId" value="facebook"/>

	
</head>

<body id="adminPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bean:write name="pagetitle" /></h1> 

	<%@include file="../updater/inc_system_tabs.jsp"%>
	
	<h2>Facebook 'like' button</h2>	
	
	<p>Add a Facebook 'like' button to publicly viewable assets and the homepage.</p>

	
	
	<div style=" float: left; width: 350px">

			<h3>Current settings:</h3>

		<table class="form" cellspacing="0" cellpadding="0" summary="facebook" style="width: 400px">
			<tr>
				<th>Enabled:</th>
				<td><input type="checkbox" class="checkbox" disabled="disabled" <c:if test="${facebookLikeButton.enabled}"> checked="checked" </c:if> /> </td>
			</tr>
			<tr>
				<th>Show Faces:</th>
				<td><input type="checkbox" class="checkbox" disabled="disabled" <c:if test="${facebookLikeButton.showFaces}"> checked="checked" </c:if> /></td>
			</tr>
			<tr>
				<th>Show Send button:</th>
				<td><input type="checkbox" class="checkbox" disabled="disabled" <c:if test="${facebookLikeButton.showSendButton}"> checked="checked" </c:if> /></td>
			</tr>
			<tr>
				<th>Button Layout:</th>
				<td class="padded">"<bean:write name='facebookLikeButton' property='buttonLayout.name'/>"</td>
			</tr>
			<tr>
				<th>Verb to Display:</th>
				<td class="padded">"<bean:write name='facebookLikeButton' property='verbToDisplay.name'/>"</td>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${not empty facebookLikeButton.fbAdminIds}">
						<th>Facebook Admin ID(s):</th>
						<td class="padded"><bean:write name='facebookLikeButton' property='fbAdminIds'/></td>
					</c:when>
					<c:otherwise>
						<th>Facebook Platform Application ID:</th>
						<td class="padded"><bean:write name='facebookLikeButton' property='fbApplicationId'/></td>
					</c:otherwise>
				</c:choose>
			</tr>
		</table>
		<a href="viewEditFacebookAdmin" class="action btn" >Edit these settings</a>			
		
	</div>	
	<div style="float:left; margin-left: 2em; padding-left: 2em; border-left: 1px dashed #ccc; width: 50%">
		<h3>Preview:</h3>

		<logic:present name="facebookLikeButton" >
			<c:choose>
				<c:when test="${facebookLikeButton.enabled}">
		        	<c:set var="fbCompleteUrl" scope="request" value=""/>
			        <jsp:include flush="true" page="../public/inc_facebook_like_button.jsp"/>
				</c:when>
				<c:otherwise>
					<em>Not enabled</em>
				</c:otherwise>
			</c:choose>
		</logic:present>


		<div class="clearing"></div>
		<p>
			<h3>Please note: </h3>
			<ul>
				<li>this feature will be visible on <strong>public assets only</strong></li>
				<li>you must set <strong>application-url</strong> setting in <em>ApplicationSettings.properties</em> in order to generate a valid link (which will be used by facebook) to your website</li>
				<li>the <strong>asset name</strong> will be used on facebook.<br/>Use the "Name Attributes" page to change it (Admin > Attributes)</li>
				<li>the <strong>company name</strong> will be used on facebook.<br/>Use the "Content" area to change it (Admin > Content > Common Terms > Company Name)</li>
			</ul>
		</p>
		
	</div>
	<div class="clearing"></div>

	<%@include file="../inc/body_end.jsp"%>

</body>
</html>