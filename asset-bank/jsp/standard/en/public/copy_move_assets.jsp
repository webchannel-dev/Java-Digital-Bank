<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Vivek		16-Feb-2011		Created.
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
	<title><bright:cmsWrite identifier="title-copy-move" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<c:set var="helpsection" value="manage-lightboxes"/>
	<bean:parameter id="type" name="type" value="copymoveassets"/>
	<script type="text/javascript" charset="utf-8">
		$j(function(){
			// DOM ready
			setCopyMoveButton();
			$j("input[name='rCopyMove']").click(setCopyMoveButton);
		})
		function setCopyMoveButton() {
			var copyOrMove = $j("input[name='rCopyMove']:checked").val();
			var buttonVal = '<bright:cmsWrite identifier="button-copy-items" filter="false"/>'
			if (copyOrMove == '2') {
				buttonVal = '<bright:cmsWrite identifier="button-move-items" filter="false"/>'
			}
			$j('#copyMoveButton').val(buttonVal);
		}
	</script>
</head>

<body id="copyMoveAssetsInLighboxes">

	<%@include file="../inc/body_start.jsp"%>

	<h1 class="underline"><bright:cmsWrite identifier="heading-copy-move" /></h1>
	
	<logic:present name="assetBoxForm">
		<logic:equal name="assetBoxForm" property="hasErrors" value="true"> 
    		<div class="error">
    			<logic:iterate name="assetBoxForm" property="errors" id="errorText">
    				<bright:writeError name="errorText" /><br />
    			</logic:iterate>
    		</div>
    	</logic:equal>
	</logic:present>
	
	<c:set var="numOwnAssetBoxes" value="${userprofile.numOwnAssetBoxes}"/>
	<c:set var="numAssetBoxes" value="${userprofile.numAssetBoxes}"/>
	<input type="hidden" name="id" value="<c:out value='${assetBoxForm.currentAssetBoxId}'/>"/>
	<bean:define id="assetBoxes" name="userprofile" property="assetBoxes"/>
	
	<html:form action="copyOrMoveAssetsAction" method="post" styleClass="floated">
		<label><bright:cmsWrite identifier="label-copymove-identifier"/></label>
		<div class="holder">
		<label class="wrapping"><input type="radio" name="rCopyMove" value="1" id="rCopy" checked class="radio" /> <bright:cmsWrite identifier="label-copy-items"/></label>
		<label class="wrapping"><input type="radio" name="rCopyMove" value="2" id="rMove" class="radio" /> <bright:cmsWrite identifier="label-move-items"/></label>
		</div>
		<br />
		<label><bright:cmsWrite identifier="label-to-lightbox" filter="false"/></label>
		<html:select name="assetBoxForm" property="currentAssetBoxId">
			<html:options collection="assetBoxes" labelProperty="assetNameAndSize" property="id"/>
		</html:select>
		<br />

		<div class="hr"></div>
		<input type="submit" class="button flush" id="copyMoveButton" value="<bright:cmsWrite identifier="button-go" filter="false" />" /> 
							
		<c:choose>
			<c:when test="${url != null && url != ''}">
				<a href="..<bean:write name='url'/>" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			</c:when>
			<c:otherwise>
				<a href="../action/viewAssetBox" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
			</c:otherwise>
		</c:choose>
	</html:form>
	<br/>
   <br/>
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>