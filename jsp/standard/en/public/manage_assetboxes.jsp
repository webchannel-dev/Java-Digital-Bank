<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		14-Aug-2007		Created.
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>

<bright:applicationSetting id="sharedLightboxes" settingName="shared-lightboxes"/>
<bright:applicationSetting id="sharedLightboxesAdminOnly" settingName="shared-lightboxes-admin-only"/>
<bright:applicationSetting id="multipleLightboxes" settingName="multiple-lightboxes"/>

<c:set var="sharedLightboxes" value="${sharedLightboxes && (userprofile.isAdmin || not sharedLightboxesAdminOnly)}"/>

<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	 



<head>
	
	<title><bright:cmsWrite identifier="title-manage-lightboxes" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="lightbox"/>
	<c:set var="helpsection" value="manage-lightboxes"/>
	<bean:define id="tabId" value="manageAssetBoxes"/>
</head>

<body id="manageLightboxesPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<%@include file="inc_lightbox_tabs.jsp"%>
	
	<logic:present name="assetBoxForm">
		<logic:equal name="assetBoxForm" property="hasErrors" value="true"> 
    		<div class="error">
    			<logic:iterate name="assetBoxForm" property="errors" id="errorText">
    				<bean:write name="errorText" filter="false"/><br />
    			</logic:iterate>
    		</div>
    	</logic:equal>
	</logic:present>
         
	<c:choose>
		<c:when test="${sharedLightboxes}">
			<bright:cmsWrite identifier="intro-manage-lightboxes-shared" filter="false"/>
		</c:when>
		<c:otherwise>
			<bright:cmsWrite identifier="intro-manage-lightboxes" filter="false"/>
		</c:otherwise>
	</c:choose>
	
	<c:set var="numOwnAssetBoxes" value="${userprofile.numOwnAssetBoxes}"/>
	<c:set var="numAssetBoxes" value="${userprofile.numAssetBoxes}"/>
		
	<table class="list highlight" cellspacing="0">
		<thead>
			<tr>
				<th><bright:cmsWrite identifier="label-name-nc" filter="false"/></th>
				<c:if test="${sharedLightboxes && numAssetBoxes!=numOwnAssetBoxes}">
					<th><bright:cmsWrite identifier="label-my-alias" filter="false"/></th>
					<th><bright:cmsWrite identifier="label-owner" filter="false"/></th>
					<th><bright:cmsWrite identifier="label-editable" filter="false"/></th>
				</c:if>
				<c:if test="${sharedLightboxes}">
					<th><bright:cmsWrite identifier="label-num-sharing" filter="false"/></th>
				</c:if>
				<th colspan="2"><bright:cmsWrite identifier="label-actions" filter="false"/></th>
			</tr>
		</thead>
		<tbody>
		<logic:iterate name="userprofile" property="assetBoxes" id="assetbox" indexId="index">
			<tr>
				<c:if test="${assetbox.shared}">
					<td><bean:write name="assetbox" property="originalName" filter="false"/></td>
					<c:if test="${sharedLightboxes && numAssetBoxes!=numOwnAssetBoxes}">
						<td>
							<c:if test="${empty assetbox.alias}">
								<em>none</em>
							</c:if>
							<c:if test="${assetbox.alias!=null}">
								<bean:write name="assetbox" property="alias"/>
							</c:if>
						</td>
						<td>
							<bean:write name="assetbox" property="owningUsername"/>
							<c:if test="${not empty assetbox.owningEmailAddress && assetbox.owningEmailAddress!=assetbox.owningUsername}">
								(<bean:write name="assetbox" property="owningEmailAddress"/>)
							</c:if>
						</td>
						<td><c:if test="${assetbox.editable}">yes</c:if><c:if test="${!assetbox.editable}">no</c:if></td>
					</c:if>
					<c:if test="${sharedLightboxes}">
						<td><span class="disabled">n/a</span></td>
					</c:if>					
					<td class="action">
						<c:if test="${index>0}">[<a href="reorderAssetBoxes?id=<c:out value="${assetbox.id}"/>&up=true"><bright:cmsWrite identifier="link-up" filter="false"/></a>]</c:if>
						<c:if test="${index<=0}">[<strong class="disabled"><bright:cmsWrite identifier="link-up" filter="false"/></strong>]</c:if>
					</td>
					<td class="action"> 
						<c:if test="${index<numAssetBoxes-1}">[<a href="reorderAssetBoxes?id=<c:out value="${assetbox.id}"/>&up=false"><bright:cmsWrite identifier="link-down" filter="false"/></a>]</c:if>
						<c:if test="${index>=numAssetBoxes-1}">[<strong class="disabled"><bright:cmsWrite identifier="link-down" filter="false"/></strong>]</c:if>
					</td>
					<td class="action">
						[<a href="viewRenameAssetBox?id=<c:out value="${assetbox.id}"/>&name=<c:out value="${assetbox.name}"/>&shared=true"><bright:cmsWrite identifier="link-rename" filter="false"/></a> ]
					</td>
					<c:if test="${multipleLightboxes}">
						<td class="action"> 
							[<a href="viewCopyAssetBox?id=<c:out value="${assetbox.id}"/>&name=<c:out value="${assetbox.name}"/>">copy</a>]
						</td>
					</c:if>
					<td class="action"> 
						[<a href="removeSharedAssetBox?id=<c:out value="${assetbox.id}"/>" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-remove-lightbox" filter="false" />');"><bright:cmsWrite identifier="link-remove" filter="false" /></a>]
					</td>
				</c:if>
				<c:if test="${!assetbox.shared}">
					<td><bean:write name="assetbox" property="name" filter="false"/></td>
					<c:if test="${sharedLightboxes && numAssetBoxes!=numOwnAssetBoxes}">
						<td><span class="disabled">n/a</span></td>
						<td>me</td>
						<td>yes</td>
					</c:if>
					<c:if test="${sharedLightboxes}">
						<td><c:out value="${assetbox.numShares}"/></td>
					</c:if>
					<td class="action">
						<c:if test="${index>0}">[<a href="reorderAssetBoxes?id=<c:out value="${assetbox.id}"/>&up=true"><bright:cmsWrite identifier="link-up" filter="false"/></a>]</c:if>
						<c:if test="${index<=0}">[<strong class="disabled"><bright:cmsWrite identifier="link-up" filter="false"/></strong>]</c:if>
					</td>
					<td class="action">
						<c:if test="${index<numAssetBoxes-1}">[<a href="reorderAssetBoxes?id=<c:out value="${assetbox.id}"/>&up=false"><bright:cmsWrite identifier="link-down" filter="false"/></a>]</c:if>
						<c:if test="${index>=numAssetBoxes-1}">[<strong class="disabled"><bright:cmsWrite identifier="link-down" filter="false"/></strong>]</c:if>
					</td>
					<td class="action"> 
						[<a href="viewRenameAssetBox?id=<c:out value="${assetbox.id}"/>&name=<c:out value="${assetbox.name}"/>&shared=false"><bright:cmsWrite identifier="link-rename" filter="false"/></a>]
					</td>
					<c:if test="${multipleLightboxes}">
						<td class="action"> 
							[<a href="viewCopyAssetBox?id=<c:out value="${assetbox.id}"/>&name=<c:out value="${assetbox.name}"/>">copy</a>]
						</td>
					</c:if>
					<td class="action"> 
						<c:if test="${numOwnAssetBoxes>1}">
							[<a href="deleteAssetBox?id=<c:out value="${assetbox.id}"/>" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-delete-lightbox" filter="false"/>');"><bright:cmsWrite identifier="link-delete" filter="false"/></a>]
						</c:if>
						<c:if test="${numOwnAssetBoxes<=1}">
							[<strong class="disabled"><bright:cmsWrite identifier="link-delete" filter="false"/></strong>]
						</c:if>
						<c:if test="${sharedLightboxes}">
							</td>
							<td  class="action"> [<a href="viewShareAssetBox?assetBoxId=<c:out value="${assetbox.id}"/>"><bright:cmsWrite identifier="link-share" filter="false"/></a>]
						</c:if>
					</td>
				</c:if>
			</tr>
		</logic:iterate>
		</tbody>
	</table>
	<br />
	<br/>
   <html:form action="addAssetBox" method="post">      
   	<label for="addNew"><bright:cmsWrite identifier="label-add-lightbox" filter="false"/> </label>
   	<input type="text" name="name" maxlength="50" size="20" id="addNew" />
   	<html:submit styleClass="button" value="Add &raquo;"/>
   </html:form>
   <br/>
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>