<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson		02-Feb-2011		Created
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bean:parameter id="copy" name="copy" value="false" />
	
<head>
	
	<title><c:choose><c:when test="${copy}"><bright:cmsWrite identifier="title-add-copy-to-batch-release" filter="false" /></c:when><c:otherwise><bright:cmsWrite identifier="title-add-asset-to-batch-release" filter="false" /></c:otherwise></c:choose></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="helpsection" value="batchReleases" />
	<script type="text/javascript" charset="utf-8">
		$j(function(){
			//DOM ready
			$j('#addToBatchForm').submit(function(){
				var selectVal = $j('#brId').val()
				if (selectVal=="none") {
					alert('Please select a batch release to add this item to.')
					return false;
				}
			});
		})
	</script>
</head>

<body> 

	<%@include file="../inc/body_start.jsp"%>
	
	
	<%-- see if this is a copy we are adding to a batch release --%>
	<c:choose>
		<c:when test="${copy}">
			<c:set var="formAction" value="viewCopyAsset" />
			<h1 class="underline"><bright:cmsWrite identifier='heading-add-copy-to-batch-release' filter="false" /></h1>
			<bright:cmsWrite identifier='snippet-add-copy-to-batch-release' filter="false" />
		</c:when>
		<c:otherwise>
			<c:set var="formAction" value="addAssetToBatchRelease" />
			<h1 class="underline"><bright:cmsWrite identifier='heading-add-asset-to-batch-release' filter="false" /></h1>
			<bright:cmsWrite identifier='snippet-add-asset-to-batch-release' filter="false" />
		</c:otherwise>
	</c:choose>
	
	
	

	<logic:notEmpty name="error">
		<div class="error"><bean:write name='error' /></div>
	</logic:notEmpty>

	<bright:refDataList componentName="BatchReleaseManager" transactionManagerName="DBTransactionManager" methodName="getBatchReleasesBeingCreated" id="releases"/>

	<bean:parameter id="id" name="id" />

	<bean:parameter id="returnUrl" name="returnUrl" value="/action/viewAsset?id=${id}" />

	<logic:notEmpty name="releases">
		

		<form action="<c:out value='${formAction}' />" method="post" id="addToBatchForm" class="floated">
			<input type="hidden" name="id" value="<bean:write name='id' />" />
			<input type="hidden" name="returnUrl" value="<bean:write name='returnUrl' />" />
			<c:if test="${copy}">
				<input type="hidden" name="assetId" value="<bean:write name='id' />" />
			</c:if>

			<label for="brId" style="width:160px"><bright:cmsWrite identifier='link-add-asset-to-batch-release' filter="false" />:</label>
			<select name="brId" id="brId" size="1">
				<option value="none">- <bright:cmsWrite identifier="snippet-please-select" filter="false" /> -</option>
				<logic:iterate name="releases" id="release">
					<option value="<bean:write name='release' property='id' />"><bean:write name='release' property='name' /></option>
				</logic:iterate>
			</select>
			<br />
			<div class="hr"></div>
			<input type="submit" name="add" class="button flush" value="<bright:cmsWrite identifier='button-add' filter='false' />" />
			<a href="..<bean:write name='returnUrl' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a> <br />
		</form>

	
	</logic:notEmpty>


	<logic:empty name="releases">
		<bright:cmsWrite identifier='snippet-no-batch-releases-to-add-to' filter="false" />
		<p><a href="..<bean:write name='returnUrl' />" ><bright:cmsWrite identifier="link-back" filter="false" /></a></p>
	</logic:empty>
 

	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>