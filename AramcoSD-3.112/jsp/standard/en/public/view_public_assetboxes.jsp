<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Vivek		14-Feb-2011		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="displayDatetimeFormat" settingName="display-datetime-format"/>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	 

<head>
	<!-- TODO: Change the Manage titles to Public & Help -->
	<title><bright:cmsWrite identifier="title-public-lightboxes" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="lightbox"/>
	<c:set var="helpsection" value="manage-lightboxes"/>
	<bean:define id="tabId" value="publicAssetBox"/>
</head>

<body id="viewPublicLightboxesPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<%@include file="inc_lightbox_tabs.jsp"%>
	<jsp:include page="inc_assetbox_messages.jsp"/>	
	
	
	<c:set var="numOwnAssetBoxes" value="${userprofile.numOwnAssetBoxes}"/>
	<c:set var="numAssetBoxes" value="${userprofile.numAssetBoxes}"/>
	
	<bean:size id="numberOfAssetBoxes" name="publicUserProfile" property="assetBoxes"/>
	
	<bright:cmsWrite identifier="snippet-shows-most-recent-modified-public-lightboxes"/>
	<br><br>
	<c:choose>
		<c:when test="${ numberOfAssetBoxes > 0 }">
			<table class="list highlight" cellspacing="0">
				<thead>
					<tr>
						<th><bright:cmsWrite identifier="label-name-nc"/> (items)</th>
						<th><bright:cmsWrite identifier="label-owner"/></th>
						<th><bright:cmsWrite identifier="label-created-timestamp"/></th>
						<th><bright:cmsWrite identifier="label-modified-timestamp"/></th>
						<th colspan="2"><bright:cmsWrite identifier="label-actions"/></th>
					</tr>
				</thead>
				<tbody>
				<logic:iterate name="publicUserProfile" property="assetBoxes" id="assetbox" indexId="index">
					<tr>
						<td><bean:write name="assetbox" property="name"/> (<bean:write name="assetbox" property="assetBoxSize"/>)</td>
						<td><bean:write name="assetbox" property="owningUsername"/></td>
						<td><bean:write name="assetbox" property="timeAdded" format="<%= (String)pageContext.getAttribute(\"displayDatetimeFormat\") %>"/></td>
						<td><bean:write name="assetbox" property="lastModified" format="<%= (String)pageContext.getAttribute(\"displayDatetimeFormat\") %>"/></td>
						<td class="action">
							<c:if test="${index>=0}">[<a href="viewPublicAssetBox?<c:out value="assetBoxId=${assetbox.id}&publicPage=1"/>"><bright:cmsWrite identifier="link-view" filter="false"/></a>]</c:if>
							<c:if test="${index<0}">[<strong class="disabled"><bright:cmsWrite identifier="link-view"/></strong>]</c:if>
						</td>
					</tr>
				</logic:iterate>
				</tbody>
			</table>
		</c:when>
		<c:otherwise>
			<bright:cmsWrite identifier="snippet-no-public-lightboxes"/>
		</c:otherwise>
	</c:choose>
	<br />
	<br/>
   <br/>
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>