<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
    d1      Steve Bryan    31-Oct-2008    Created 	    
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewUnsubmittedAssetsAdmin?selectedUserId=${assetWorksetForm.selectedUserId}"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="heading-unsubmitted-items" filter="false"/></c:set>

<head>
	
	<title><bright:cmsWrite identifier="title-approve-items" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="my-uploads"/>
	<bean:define id="tabId" value="admin"/>

</head>

<body id="importPage">
	<%@include file="../inc/body_start.jsp"%>
	
	<h1>My Uploads</h1> 
	
	<bean:define id="tabId" value="unsubmitted"/>
	<%@include file="../asset-admin/inc_my_uploads_tabs.jsp"%>
	
	<c:if test="${userprofile.isAdmin}">
		<p class="tabHolderPopup clearfix">
				<a href="viewUnsubmittedAssets">My Items</a>
				<a  class="active" href="../action/viewUnsubmittedAssetsAdmin">Other Users</a>
		</p>
		<div id="tabContent">
	</c:if>
	<h3>All unsubmitted items</h3>
	
	<bean:define id="users" name="assetWorksetForm" property="listUsers" />
	<bean:size id="iSizeUsers" name="users" />
	
	<c:choose>
		<c:when test="${iSizeUsers gt 0}">
			<%-- Selector to pick user --%>
			<form action="viewUnsubmittedAssetsAdmin" method="post">
				<label for="user">Select a user:</label>
				<html:select name="assetWorksetForm" property="selectedUserId" styleId="user" onchange="document.getElementById('b_go').click();">
					<html:option value="0">-- Select --</html:option>
					<html:options collection="users" property="id" labelProperty="name"/>
				</html:select>	
				
				<input type="submit" name="b_go" class="button" value="<bright:cmsWrite identifier="button-go-arrow" filter="false" />" style="display: inline;" id="b_go"/>			
				<script type="text/javascript">
				<!--
					// Hide the Go button if JavaScript is available
					document.getElementById('b_go').style.display='none';
				-->
				</script>
			</form>
		</c:when>
		<c:otherwise>
			<p>There are currently no unsubmitted assets.</p>
		</c:otherwise>	
	</c:choose>
	
	<div class="hr"></div>	
	<c:set var="returnSize" value="${assetWorksetForm.listAssets.returnedNumberResults}" />
	<c:set var="maxExceeded" value="${assetWorksetForm.listAssets.maxResultsExceeded}" />
	
	<c:if test="${returnSize gt 0}">
		<h3>
			<c:out value="${returnSize}" /> 
			Unsubmitted
			<c:choose>
				<c:when test="${returnSize == 1}">
					<bright:cmsWrite identifier="item" filter="false"/>
				</c:when>
				<c:otherwise>
					<bright:cmsWrite identifier="items" filter="false"/>
				</c:otherwise>
			</c:choose>	
			<c:if test="${maxExceeded}">(not all shown)</c:if>
		</h3>
	</c:if>
	
	<c:if test="${!empty assetWorksetForm.listAssets && assetWorksetForm.listAssets.returnedNumberResults gt 0}">
	
		<c:set var="userIdParam" value="${assetWorksetForm.selectedUserId}" />
		<c:set var="submitUrl" value="submitUnsubmittedAssetAdmin" />
		<%@include file="../asset-admin/inc_unsubmitted_assets.jsp"%>

	</c:if>
	
	<c:if test="${userprofile.isAdmin}">
		</div> <!-- End of tabContent -->
	</c:if>
	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>