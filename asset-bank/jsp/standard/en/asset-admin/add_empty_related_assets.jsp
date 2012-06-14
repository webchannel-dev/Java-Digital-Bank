<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	06-Sep-2010		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/abplugin.tld" prefix="abplugin" %>


<head>
	
	<title><bright:cmsWrite identifier="title-add-empty-related-assets" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<script type="text/javascript">
		<!-- 
			function decrement (sId)
			{
				var iValue = document.getElementById(sId).value;
				
				if (validate(sId) && iValue > 0)
				{
					document.getElementById(sId).value = iValue - 1;
				}
			}
			
			function increment (sId)
			{
				if (validate(sId))
				{
					document.getElementById(sId).value = document.getElementById(sId).value - 1 + 2;
				}
			}

			function validate (sId)
			{
				var iValue = document.getElementById(sId).value;
				if (isNaN(iValue))
				{
					document.getElementById(sId).value = 0;
					return (false);
				}
				return (true);
			}
		-->
	</script>

	<bean:define id="helpsection" value="add-empty-related-assets"/>		

</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-add-empty-related-assets" filter="false"/></h1>

	<bright:cmsWrite identifier="add-empty-related-assets-main" filter="false"/>

	<logic:equal name="addEmptyRelatedAssetForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="addEmptyRelatedAssetForm" property="errors" id="errorText">
				<bright:writeError name="errorText" /><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<div class="hr"></div>
	
	<h2><bright:cmsWrite identifier="label-child-assets" filter="false"/></h2>
		
		
	<html:form action="addEmptyRelatedAssets" method="post">
		
		<input type="hidden" name="id" value="<bean:write name='addEmptyRelatedAssetForm' property='asset.id' />" />

		<bean:define id="prefix" value="child" />
		<bean:define id="relationships" name="addEmptyRelatedAssetForm" property="potentialChildren" />
		<c:set var="noneFoundText">
			<bright:cmsWrite identifier='no-empty-related-assets-child' filter='false' />
		</c:set>
		
		<%@include file="inc_empty_related_assets.jsp"%>

		<h2><bright:cmsWrite identifier="label-peer-assets" filter="false"/></h2>

		<bean:define id="prefix" value="peer" />
		<bean:define id="relationships" name="addEmptyRelatedAssetForm" property="potentialPeers" />
		<c:set var="noneFoundText">
			<bright:cmsWrite identifier='no-empty-related-assets-peer' filter='false' />
		</c:set>
		<%@include file="inc_empty_related_assets.jsp"%>
		
		<input type="submit" name="saveButton" class="button flush floated" id="submitButton" value="<bright:cmsWrite identifier="button-save" filter="false" />" />
		
		<a href="viewAsset?id=<bean:write name='addEmptyRelatedAssetForm' property='asset.id'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
		
	
			
	</html:form>



	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>