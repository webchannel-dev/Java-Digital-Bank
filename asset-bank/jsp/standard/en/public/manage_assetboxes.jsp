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
<bright:applicationSetting id="displayDatetimeFormat" settingName="display-datetime-format"/>

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
    				<bright:writeError name="errorText" /><br />
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
				<th><bright:cmsWrite identifier="label-name-nc"/> (items)</th>

				<c:if test="${sharedLightboxes && numAssetBoxes!=numOwnAssetBoxes}">
					<th><bright:cmsWrite identifier="label-my-alias"/></th>
					<th><bright:cmsWrite identifier="label-owner"/></th>
					<th><bright:cmsWrite identifier="label-editable"/></th>
				</c:if>
				<c:if test="${sharedLightboxes}">
					<th><bright:cmsWrite identifier="label-num-sharing"/></th>
				</c:if>
				<th><bright:cmsWrite identifier="label-created-timestamp"/></th>
				<th><bright:cmsWrite identifier="label-modified-timestamp"/></th>
				<th colspan="2"><bright:cmsWrite identifier="label-actions"/></th>
			</tr>
		</thead>
		<tbody>
		<logic:iterate name="userprofile" property="assetBoxes" id="assetbox" indexId="index">
			<tr>
				<c:if test="${assetbox.shared}">
					<td><bean:write name="assetbox" property="originalName" /> (<bean:write name="assetbox" property="assetBoxSize" />)</td>
					<c:if test="${sharedLightboxes && numAssetBoxes!=numOwnAssetBoxes}">
						<td>
							<c:if test="${empty assetbox.alias}">
								<em><bright:cmsWrite identifier="snippet-none"/></em>
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
						<td><c:if test="${assetbox.editable}"><bright:cmsWrite identifier="snippet-yes"/></c:if><c:if test="${!assetbox.editable}"><bright:cmsWrite identifier="snippet-no"/></c:if></td>
					</c:if>
					<c:if test="${sharedLightboxes}">
						<td><span class="disabled"><bright:cmsWrite identifier="snippet-not-applicable"/></span></td>
					</c:if>
					<td><bean:write name="assetbox" property="timeAdded" format="<%= (String)pageContext.getAttribute(\"displayDatetimeFormat\") %>"/></td>
					<td><bean:write name="assetbox" property="lastModified" format="<%= (String)pageContext.getAttribute(\"displayDatetimeFormat\") %>"/></td>
					<td class="action">
						<c:if test="${index>0}">[<a href="reorderAssetBoxes?id=<c:out value="${assetbox.id}"/>&up=true"><bright:cmsWrite identifier="link-up"/></a>]</c:if>
						<c:if test="${index<=0}">[<strong class="disabled"><bright:cmsWrite identifier="link-up"/></strong>]</c:if>
					</td>
					<td class="action"> 
						<c:if test="${index<numAssetBoxes-1}">[<a href="reorderAssetBoxes?id=<c:out value="${assetbox.id}"/>&up=false"><bright:cmsWrite identifier="link-down"/></a>]</c:if>
						<c:if test="${index>=numAssetBoxes-1}">[<strong class="disabled"><bright:cmsWrite identifier="link-down"/></strong>]</c:if>
					</td>
					<td class="action">
						[<a href="viewEditAssetBox?id=<c:out value="${assetbox.id}"/>&name=<c:out value="${assetbox.name}"/>&shared=true&publicAvailability=<c:out value="${assetbox.publicAvailability}"/>"><c:choose><c:when test="${!sharedLightboxes}"><bright:cmsWrite identifier="link-edit"/></c:when><c:otherwise><bright:cmsWrite identifier="link-rename"/></c:otherwise></c:choose></a>]
					</td>
					<c:if test="${multipleLightboxes}">
						<td class="action"> 
							[<a href="viewCopyAssetBox?id=<c:out value="${assetbox.id}"/>&name=<c:out value="${assetbox.name}"/>"><bright:cmsWrite identifier="link-copy"/></a>]
						</td>
					</c:if>
					<td class="action">-</td>
					<td class="action"> 
						[<a href="removeSharedAssetBox?id=<c:out value="${assetbox.id}"/>" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-remove-lightbox" filter="false" />');"><bright:cmsWrite identifier="link-remove-share"/></a>]
					</td>
				</c:if>
				<c:if test="${!assetbox.shared}">
					<td><bean:write name="assetbox" property="name" /> (<bean:write name="assetbox" property="assetBoxSize" />)</td>
					<c:if test="${sharedLightboxes && numAssetBoxes!=numOwnAssetBoxes}">
						<td><span class="disabled"><bright:cmsWrite identifier="snippet-not-applicable"/></span></td>
						<td><bright:cmsWrite identifier="snippet-me"/></td>
						<td><bright:cmsWrite identifier="snippet-yes"/></td>
					</c:if>
					<c:if test="${sharedLightboxes}">
						<td><c:out value="${assetbox.numShares}"/></td>
					</c:if>
					<td><bean:write name="assetbox" property="timeAdded" format="<%= (String)pageContext.getAttribute(\"displayDatetimeFormat\") %>"/></td>
					<td><bean:write name="assetbox" property="lastModified" format="<%= (String)pageContext.getAttribute(\"displayDatetimeFormat\") %>"/></td>
					
					<td class="action">
						<c:if test="${index>0}">[<a href="reorderAssetBoxes?id=<c:out value="${assetbox.id}"/>&up=true"><bright:cmsWrite identifier="link-up"/></a>]</c:if>
						<c:if test="${index<=0}">[<strong class="disabled"><bright:cmsWrite identifier="link-up"/></strong>]</c:if>
					</td>
					<td class="action">
						<c:if test="${index<numAssetBoxes-1}">[<a href="reorderAssetBoxes?id=<c:out value="${assetbox.id}"/>&up=false"><bright:cmsWrite identifier="link-down"/></a>]</c:if>
						<c:if test="${index>=numAssetBoxes-1}">[<strong class="disabled"><bright:cmsWrite identifier="link-down"/></strong>]</c:if>
					</td>
					<td class="action"> 
						[<a href="viewEditAssetBox?id=<c:out value="${assetbox.id}"/>&name=<c:out value="${assetbox.name}"/>&shared=false&publicAvailability=<c:out value="${assetbox.publicAvailability}"/>"><bright:cmsWrite identifier="link-edit"/></a>]
					</td>
					<c:if test="${multipleLightboxes}">
						<td class="action"> 
							[<a href="viewCopyAssetBox?id=<c:out value="${assetbox.id}"/>&name=<c:out value="${assetbox.name}"/>"><bright:cmsWrite identifier="link-copy"/></a>]
						</td>
					</c:if>
					<td class="action"> 
						<c:if test="${numOwnAssetBoxes>1}">
							[<a href="deleteAssetBox?id=<c:out value="${assetbox.id}"/>" onclick="return confirm('<bright:cmsWrite identifier="js-confirm-delete-lightbox" filter="false"/>');"><bright:cmsWrite identifier="link-delete"/></a>]
						</c:if>
						<c:if test="${numOwnAssetBoxes<=1}">
							[<strong class="disabled"><bright:cmsWrite identifier="link-delete"/></strong>]
						</c:if>
						<c:if test="${sharedLightboxes}">
							</td>
							<td  class="action"> [<a href="viewShareAssetBox?assetBoxId=<c:out value="${assetbox.id}"/>"><bright:cmsWrite identifier="link-share"/></a>]
						</c:if>
					</td>
				</c:if>
			</tr>
		</logic:iterate>
		</tbody>
	</table>
	<br />
	<c:if test="${numAssetBoxes > 1}"><p><a href="../action/alphabetiseAssetBoxes"><bright:cmsWrite identifier="link-alphabetise-lightboxes" /></a></p></c:if>
	<br/>
	<h3><bright:cmsWrite identifier="label-add-lightbox"/></h3>
	<form id="createLightbox" name="createLightbox" action="../action/addAssetBox" method="post">  
  		<label for="addNew"><bright:cmsWrite identifier="label-name"/></label>
		<input type="text" name="name" maxlength="50" size="20" id="addNew" />
		<input type="submit" class="button" value="<bright:cmsWrite identifier="button-add"/> ">
   </form>
   <br/>
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>
