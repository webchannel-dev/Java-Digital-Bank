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
<%@ taglib uri="/WEB-INF/abplugin.tld" prefix="abplugin" %>

<bean:parameter name="queryString" id="queryString" value="" />
<bean:parameter name="brId" id="brId" value="-1" />

<head>
	
	<title><bright:cmsWrite identifier="company-name" /> | <bright:cmsWrite identifier='batch-release' case="mixed" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<%@include file="../inc/set_this_page_url.jsp"%>
	<bean:define id="section" value="batchReleases"/>
	<script type="text/javascript" src="../js/group-edit.js"></script>
	
	<script type="text/JavaScript">
		$j(function() {

			settings = {
				$wrapper : $j('#childAssets'),
				$masterContainer : $j('#childAssets li.first'),
				$copyLink : $j('#copyValuesLink'),
				highlightClass : 'selected'
			};
			
			assetApproval.init(settings);
		});
	</script>
	
</head>

<body id="adminPage" class="assetChangeDescription"> 

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-asset-change-descriptions" filter="false" case="sentence"/></h1> 
	
	<p><bright:cmsWrite identifier="snippet-br-asset-change-intro" filter="false" /></p>


	<logic:notEmpty name="changeDescriptions">
		<form action="saveAssetChangeDescriptions" method="post" class="floated">
			<input type="hidden" name="brId" value="<bean:write name='brId' />" />
			<input type="hidden" name="queryString" value="<bean:write name='queryString' />" />
			<ul class="assetList" id="childAssets">
				<bean:size id="descCount" name="changeDescriptions" />
				<logic:iterate name="changeDescriptions" id="changeDesc" indexId="myIndex">
					<bean:define id="asset" name="changeDesc" property="asset" />
					<li class="selected <c:if test='${myIndex==0}'>first</c:if>">		
						<table cellspacing="0">
							<tr>
								<td class="image">				
									<%@include file="../inc/result-resultimgclass.jsp"%>					
									<bean:define id="disablePreview" value="true"/>	
									<%@include file="../inc/view_thumbnail.jsp"%>		
								</td>
								<td >   
									<c:set var="item" value="${asset}"/>	
									<%@include file="../inc/result_asset_descriptions.jsp"%>  
									<label style="width:125px"><bright:cmsWrite identifier="label-change-description" filter="false"/></label>
									<textarea rows="3" cols="130" name="changeDescription<bean:write name='asset' property='id' />" style="margin-bottom: 0.3em"><bean:write name='changeDesc' property='changeDescription' /></textarea>     <br />  
									<%-- If on the first item and there are more then one then show 'copy to all' link --%>
									<c:if test="${myIndex==0}">
										<c:if test="${descCount gt 1}"> 
											<label style="width:125px">&nbsp;</label>
											<a href="#" id="copyValuesLink"><bright:cmsWrite identifier="link-copy-to-all-items" filter="false" /></a>
										</c:if>	
									</c:if>
								</td>
							<tr>
						</table>	
					</li>
				</logic:iterate>
			</ul>

		<input type="submit" name="submit" value="<bright:cmsWrite identifier='button-save' />" class="button flush" /> 
		<a href="../action/viewBatchRelease?brId=<c:out value='${brId}' />&<c:out value='${queryString}' />" class="cancelLink"><bright:cmsWrite identifier="button-cancel" case="sentence" filter="false" /></a>


		</form>
	</logic:notEmpty>
	<logic:empty name="changeDescriptions">
		<bright:cmsWrite identifier="snippet-no-assets-in-batch-release" filter="false" />
	</logic:empty>

	<p>
		
	</p>

	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>