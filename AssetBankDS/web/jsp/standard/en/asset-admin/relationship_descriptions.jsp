<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	15-Nov-2010		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<c:choose>
	<c:when test="${relationshipDescriptionForm.count == 1}">
		<c:set var="pageTitle"><bright:cmsWrite identifier="title-edit-relationship" filter="false"/></c:set>
		<c:set var="pageHeading"><bright:cmsWrite identifier="heading-edit-relationship" filter="false"/></c:set>
	</c:when>
	<c:otherwise>
		<c:set var="pageTitle"><bright:cmsWrite identifier="title-edit-relationships" filter="false"/></c:set>
		<c:set var="pageHeading"><bright:cmsWrite identifier="heading-edit-relationships" filter="false"/></c:set>
	</c:otherwise>
</c:choose>

<bean:parameter id="edit" name="edit" value="false" />


<head>
	
	<title><bean:write name='pageTitle' /></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<bean:define id="helpsection" value="relationship-descriptions"/>		
	<script type="text/JavaScript">
		$j(function() {
			$j('#copyValuesLink').click(function() {
				var formVal = $j('ul.assetList li.first input.text').val();
				$j('ul.assetList li input.text').val(formVal);
			});
		
		});
	</script>

</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bean:write name='pageHeading' /></h1>

	<logic:equal name="relationshipDescriptionForm" property="hasErrors" value="true"> 
		<div class="error">
			<logic:iterate name="relationshipDescriptionForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>
		</div>
	</logic:equal>
	
	<form action="../action/saveRelationshipDescriptions" method="post">
	
		<input type="hidden" name="id" value="<c:out value='${relationshipDescriptionForm.sourceAsset.id}' />" />
	
		<bean:define id="assetDescriptions" name='relationshipDescriptionForm' property='childAssetDescriptions' />
		<bean:define id="relationshipId" value="2" />
		<%@include file="inc_relationship_descriptions.jsp"%>
	
		<bean:define id="assetDescriptions" name='relationshipDescriptionForm' property='peerAssetDescriptions' />
		<bean:define id="relationshipId" value="1" />
		<%@include file="inc_relationship_descriptions.jsp"%>
	
		<br />
		
		<input type="submit" name="submit" class="button floated flush" value="Save" />
		
		<c:choose>
			<c:when test="${edit}">
				<a class="cancelLink" href="../action/viewAsset?id=<bean:write name='relationshipDescriptionForm' property='sourceAsset.id' />">Cancel</a>		
			</c:when>
			<c:otherwise>
				<a class="cancelLink" href="../action/viewAsset?id=<bean:write name='relationshipDescriptionForm' property='sourceAsset.id' />">Skip this step</a>
			</c:otherwise>	
		</c:choose>		
			
	</form>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>