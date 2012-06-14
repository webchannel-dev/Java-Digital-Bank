<%@include file="../inc/doctype_html.jsp" %>
 
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan		09-May-2007		Created
	 d2		Ben Browning	17-Oct-2008		Used javascript to improve UI
	 d3 		Ben Browning	26-Apr-2010		jQuery switch over 
	 														- removed unneccessary confirimation dialog on select all or none actions
	 														- simplified js
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<bright:applicationSetting id="titleMaxLength" settingName="results-title-max-length"/>


<head>
	
	<title><bright:cmsWrite identifier="title-bulk-update" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	
	<bean:define id="section" value="batch"/>
	<bean:define id="helpsection" value="batch-bulkupdate"/>
		


</head>

<body id="batchPage" class="assetSelectPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-bulk-update-batch" filter="false" /></h1> 

	<c:set var="displayAttributeGroup" value="1" scope="request" />	
	<bean:define id="batchUpdate" name="userprofile" property="batchUpdateController.batchUpdate" />
	
	<logic:equal name="bulkUpdateBatchSelectForm" property="hasErrors" value="true">
		<div class="error">
			<logic:iterate name="bulkUpdateBatchSelectForm" property="errors" id="errorText">
				<bean:write name="errorText" filter="false"/><br />
			</logic:iterate>		
		</div>
	</logic:equal>
	
	<c:choose>
		<c:when test="${empty bulkUpdateBatchSelectForm.listNotUpdated && empty bulkUpdateBatchSelectForm.listAlreadyUpdated}">
			<p><bright:cmsWrite identifier="snippet-no-items-batch" filter="false"/></p>
		</c:when>
		<c:otherwise>
			
			

			
								
			<html:form action="bulkUpdateBatchSelect" styleId="selectableAssets" method="post">
				<div class="clearfix">
					<input type="submit" class="button floated flush" id="submitButton" value="<bright:cmsWrite identifier="button-next" filter="false" />" /> 
					<a href="viewManageBulkUpdate" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>
				</div>	
				<div class="hr"></div>	

				<c:if test="${!empty bulkUpdateBatchSelectForm.listNotUpdated}">

					<h2><bright:cmsWrite identifier="subhead-not-yet-updated" filter="false"/></h2>
					<p><bright:cmsWrite identifier="snippet-check-item-update" filter="false"/></p>

					<p class="js-enabled-show">
						<bright:cmsWrite identifier="snippet-select" filter="false"/>: 
						<a href="#" onclick="selectItems(true,'notUpdated'); return false;"><bright:cmsWrite identifier="snippet-all-keywords" filter="false"/></a> | 
						<a href="#" onclick="selectItems(false,'notUpdated'); return false;"><bright:cmsWrite identifier="snippet-none" filter="false" case="mixed"/></a>
					</p>
					<ul class="lightbox clearfix" id="notUpdated">
						<logic:iterate name="bulkUpdateBatchSelectForm" property="listNotUpdated" id="asset" indexId="index">
							
							<li class="selectable">						
					
								<div class="detailWrapper">
									<%@include file="../inc/result-resultimgclass.jsp"%>					
									<bean:define id="disablePreview" value="true"/>	
									<%@include file="../inc/view_thumbnail.jsp"%>					
									<br />		
									<c:set var="item" value="${asset}"/>	
									<%@include file="../inc/result_asset_descriptions.jsp"%>
									<%@include file="../inc/result-video-audio-icon.jsp"%>					
								</div>		
								
								<input type="checkbox" class="checkbox" name="update_1_<c:out value='${asset.id}' />"  />						
							</li>
										
						</logic:iterate>
					</ul>				
					<div class="hr"></div>

				</c:if>
																								
				<c:if test="${!empty bulkUpdateBatchSelectForm.listAlreadyUpdated}">
					
					
					<h2><bright:cmsWrite identifier="subhead-already-updated" filter="false"/></h2>
					<p><bright:cmsWrite identifier="snippet-check-item-update-again" filter="false"/></p>
					<p class="js-enabled-show">
						<bright:cmsWrite identifier="snippet-select" filter="false"/>: 
						<a href="#" onclick="selectItems(true,'alreadyUpdated'); return false;"><bright:cmsWrite identifier="snippet-all-keywords" filter="false"/></a> | 
						<a href="#" onclick="selectItems(false,'alreadyUpdated'); return false;"><bright:cmsWrite identifier="snippet-none" filter="false" case="mixed"/></a>
					</p>
					<ul class="lightbox clearfix" id="alreadyUpdated">
						<logic:iterate name="bulkUpdateBatchSelectForm" property="listAlreadyUpdated" id="asset" indexId="index">
							
							<li class="selectable">							
					
								<div class="detailWrapper">
									<%@include file="../inc/result-resultimgclass.jsp"%>					
									<%@include file="../inc/view_thumbnail.jsp"%>					
									<br />		
									<c:set var="item" value="${asset}"/>	
									<%@include file="../inc/result_asset_descriptions.jsp"%>
									<%@include file="../inc/result-video-audio-icon.jsp"%>											
								</div>
								
								<input type="checkbox" class="checkbox" name="update_2_<c:out value='${asset.id}' />"  />								
							</li>
										
						</logic:iterate>
					</ul>				
					<div class="hr"></div>
										
				</c:if>
											
				<input type="submit" class="button flush floated" id="submitButton" value="<bright:cmsWrite identifier="button-next" filter="false" />" /> 
				<a href="viewManageBulkUpdate" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false" /></a>		
			
			</html:form>
		
			
			
		</c:otherwise>
	</c:choose>


	<%@include file="../inc/body_end.jsp"%>

</body>
</html>