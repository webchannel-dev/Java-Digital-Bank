<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		04-Jan-2006		Created
	 d2		Ben Browning		17-Feb-2006		HTML/CSS Tidy up
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>





<head>
	
	<title><bright:cmsWrite identifier="company-name" filter="false" />  | Move Assets</title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="asset-approval"/>
	<bean:define id="pagetitle" value="Approve Assets"/>
</head>

<body>
	
	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name="pagetitle" filter="false"/></h1> 
		
	<p>Move <bright:cmsWrite identifier="items" filter="false" /> from <strong><bean:write name="moveForm" property="fromCategory.name"/></strong> to <strong><bean:write name="moveForm" property="toCategory.name"/></strong>:
	</p>
	
	<logic:equal name="moveForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="moveForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<html:form action="moveAssets" method="post">
		<input type="hidden" name="categoryTypeId" value="<bean:write name='moveForm' property='fromCatgeory.categoryTypeId' />" />
		<input type="hidden" name="fromCatId" value="<bean:write name='moveForm' property='fromCategory.id'/>" />
		<input type="hidden" name="toCatId" value="<bean:write name='moveForm' property='toCategory.id'/>" />
		
		<ul class="results">
	
		<logic:iterate name="moveForm" property="assetsToMove" id="asset" indexId="index">
			
			<li class="clearfix">		
				<c:if test="${asset.typeId!=2}">
					<c:set var="resultImgClass" value="icon"/>
				</c:if>
				<c:if test="${asset.typeId==2}">
					<c:set var="resultImgClass" value="image"/>
				</c:if>
	
				<a href="../action/viewAssetInPopup?id=<bean:write name='asset' property='id' />" target="_blank">
					<bean:define id="disablePreview" value="true"/>	
					<%@include file="../inc/view_thumbnail.jsp"%>
				</a>
			
				<select class="action" name="action_<bean:write name='asset' property='id'/>">
					<option value='0'>Reject</option>
					<option value='1' selected>Pending</option>
					<option value='2'>Approve</option>
				</select>
			
				<h3>
					<a href="viewAssetInPopup?id=<bean:write name='asset' property='id' />" target="_blank">
						<bean:write name="asset" property="id"/>: <bean:write name="asset" property="name"/>
					</a>
				</h3>
			</li>		
		</logic:iterate>
	
		</ul>
	
		<div class="hr"></div>
	
		<div class="buttonHolder">
			<input type="submit" class="button" value="<bright:cmsWrite identifier="button-submit" filter="false" />" /> 
		</div>
	</html:form>
	
	<form name="cancelForm" action="viewAssetApproval" method="get">
		<input type="hidden" name="fromCatId" value="<bean:write name='moveForm' property='fromCategory.id'/>">
		<input type="hidden" name="toCatId" value="<bean:write name='moveForm' property='toCategory.id'/>">
		<input type="submit" name="cancel" value="<bright:cmsWrite identifier="button-cancel" filter="false" />" class="button" id="cancelButton" />
	</form>
	
	<%@include file="../inc/body_end.jsp"%>

</body>
</html>