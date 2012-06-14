<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		26-Aug-2007		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>

<bright:applicationSetting id="sharedLightboxes" settingName="shared-lightboxes"/>
<bright:applicationSetting id="multipleLightboxes" settingName="multiple-lightboxes"/>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	



<head>
	
	<title><bright:cmsWrite identifier="title-share-lightbox" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="lightbox"/>
	<c:set var="helpsection" value="share-lightbox"/>

	<bean:define id="tabId" value="manageAssetBoxes"/>
	<style type="text/css">
		td,th { padding-right: 7px; }
	</style>
</head>


<body id="shareLightboxPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<%@include file="inc_lightbox_tabs.jsp"%>

	<logic:present name="shareAssetBoxForm">
		<logic:equal name="shareAssetBoxForm" property="hasErrors" value="true"> 
    		<div class="error">
    			<logic:iterate name="shareAssetBoxForm" property="errors" id="errorText">
    				<bright:writeError name="errorText" /><br />
    			</logic:iterate>
    		</div>
    	</logic:equal>
	</logic:present>
	
   <c:set var="lightboxName" value="${shareAssetBoxForm.name}" />      
	<bright:cmsWrite identifier="snippet-share-lb-other-users" filter="false" replaceVariables="true" />

	
	<c:if test="${shareAssetBoxForm.numUsers==0}">
		<p><bright:cmsWrite identifier="snippet-lb-not-shared" filter="false"/></p>
	</c:if>
	<c:if test="${shareAssetBoxForm.numUsers>0}">
		<table class="admin">
			<tr>
				<th><bright:cmsWrite identifier="label-name-nc" filter="false"/></td>
				<th><bright:cmsWrite identifier="label-email-nc" filter="false"/></th>
				<th><bright:cmsWrite identifier="label-can-edit" filter="false"/></th>
				<th><bright:cmsWrite identifier="label-actions" filter="false"/></th>
			</tr>
			<logic:iterate name="shareAssetBoxForm" property="users" id="sharedUser">
				<tr>
					<td>
						<bean:write name="sharedUser" property="user.surname"/>,														
						<bean:write name="sharedUser" property="user.forename"/>
					</td>
					<td><c:if test="${empty sharedUser.user.emailAddress}"><span class="disabled">-</span></c:if><c:if test="${not empty sharedUser.user.emailAddress}"><bean:write name="sharedUser" property="user.emailAddress"/></c:if></td>
					<td>
						<c:if test="${sharedUser.hasEditPermission==true}"><a href="changeAssetBoxSharePermission?assetBoxId=<c:out value="${shareAssetBoxForm.assetBoxId}"/>&userId=<c:out value="${sharedUser.user.id}"/>&canEdit=false"><bright:cmsWrite identifier="link-yes" filter="false"/></a></c:if>
						<c:if test="${sharedUser.hasEditPermission!=true}"><a href="changeAssetBoxSharePermission?assetBoxId=<c:out value="${shareAssetBoxForm.assetBoxId}"/>&userId=<c:out value="${sharedUser.user.id}"/>&canEdit=true"><bright:cmsWrite identifier="link-no" filter="false"/></a></c:if>
					</td>
					<td><a href="removeAssetBoxShare?assetBoxId=<c:out value="${shareAssetBoxForm.assetBoxId}"/>&userId=<c:out value="${sharedUser.user.id}"/>"><bright:cmsWrite identifier="link-remove-lowercase" filter="false" /></a></td>
				</tr>
			</logic:iterate>
		</table>
		<br/>
		<a href="removeAllAssetBoxShares?assetBoxId=<c:out value="${shareAssetBoxForm.assetBoxId}"/>" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-remove-all-users" filter="false"/>');"><bright:cmsWrite identifier="link-remove-all-users" filter="false"/></a> | 
		
	</c:if>
	
	<a href="viewAddAssetBoxUsers?assetBoxId=<c:out value="${shareAssetBoxForm.assetBoxId}"/>">
		<c:choose>
			<c:when test="${shareAssetBoxForm.numUsers>0}">
				<bright:cmsWrite identifier="link-add-more-users" filter="false"/>
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="link-add-users" filter="false"/>
			</c:otherwise>
		</c:choose>
	</a>

	<div class="hr"></div>
	<p>
		<a href="../action/viewManageAssetBoxes"><bright:cmsWrite identifier="link-back" filter="false" /></a>
	</p>
	   
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>