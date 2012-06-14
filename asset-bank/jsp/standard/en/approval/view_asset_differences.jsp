<%@include file="../inc/doctype_html.jsp" %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%@include file="../inc/asset_title.jsp"%>

<head>
	<title>Asset Bank | Asset Differences</title> 
	<jsp:include flush="true" page="../inc/head-elements.jsp"/>
	<bean:define id="helpsection" value="view_asset"/>	
</head>

<body id="asset_difference_popup">

	<logic:notPresent name="resultImgClass">
		<bean:define id="resultImgClass" value="image"/>
	</logic:notPresent>
	

	<jsp:include flush="true" page="../public/inc_view_asset_heading.jsp"/>
	
	<jsp:include flush="true" page="../batch-release/inc_moved_asset_warning.jsp"/>


	<div class="wrapper_thumbs 
		<c:if test="${!attributeDifferences.arefilesDifferent}">
			single
		</c:if>
	">
		
		<div class="center_margin">
		
			<table width="100%">
				<tr>
					<td>
					
						<div class="imageAndActions <c:if test="${attributeDifferences.arefilesDifferent}">captioned</c:if>">
							<c:if test="${attributeDifferences.arefilesDifferent && not empty previousAsset.displayPreviewImageFile.path}">
								
								<c:set var="thumbSrc"
									value="../servlet/display?file=${previousAsset.displayPreviewImageFile.path}" />
								<img class="<bean:write name='resultImgClass'/> no_margin "
									src="<bean:write name='thumbSrc'/>" alt="Asset Preview" />
							</c:if>
							
						</div><!-- imageAndActione end -->
						
						<c:if test="${attributeDifferences.arefilesDifferent}">
						</td><td>
						</c:if>						
						
						<div class="imageAndActions <c:if test="${attributeDifferences.arefilesDifferent}">captioned</c:if>">
								
							<logic:present name="latestAsset">
									<c:if test="${not empty latestAsset.displayPreviewImageFile.path}">
										<c:set var="thumbSrc"
											value="../servlet/display?file=${latestAsset.displayPreviewImageFile.path}" />
									<img class="<bean:write name='resultImgClass'/>"
										src="<bean:write name='thumbSrc'/>" alt="Asset Preview" />
									</c:if>				
							</logic:present>
							
						</div><!-- imageAndActione end -->				
													
					</td>						
					
				</tr>
				<c:if test="${attributeDifferences.arefilesDifferent}">
				<tr class="captioned">			
					<td>
						<span class="caption">Old Image</span>	
					</td>
					
					<td>				
						<c:if test="${attributeDifferences.arefilesDifferent}">
						<span class="caption">New Image</span></c:if>										
					</td>						
				</tr>
				</c:if>	

			</table>
				
		</div><!-- center_margin end -->

	</div><!-- wrapper_thumbs end -->
		
	<div id="wrapper_changes">
		
		<div class="center_margin">
		
			<h2><bean:write name="attributeDifferences" property="assetName"/></h2>
			
			<h3>Edits by <bean:write name="attributeDifferences" property="user.forename"/> <bean:write name="attributeDifferences" property="user.surname"/>  - <bean:write name="attributeDifferences" property="dateEdited"/></h3>
		
			<table class="diff form stripey" cellspacing="0" cellpadding="0">
				<logic:iterate id="assetLog" name="attributeDifferences" property="assetLog" indexId="attributeIndex">
					<tr>
						<th><bean:write name="assetLog" property="name" /></th>
						<td>
							<c:choose>
								<c:when test="${assetLog.showAsOldNew}">
									<c:if test="${not empty assetLog.oldValue}">
										<div class="diff">
											<span class="diff-deleted"><bean:write name="assetLog" property="oldValue" /></span> <span class="diff-type">(old value)</span></
										</div>
									</c:if>
									<c:if test="${not empty assetLog.newValue}">
										<div class="diff">
											<span class="diff-added"><bean:write name="assetLog" property="newValue" /></span> <span class="diff-type">(new value)</span>
										</div>
									</c:if>
								</c:when>
								<c:otherwise>
									<c:if test="${not empty assetLog.oldValue}">
										<div class="diff">
											<span class="diff-deleted"><bean:write name="assetLog" property="oldValue" /></span> <span class="diff-type">(deleted)</span>
										</div>
									</c:if>
									<c:if test="${not empty assetLog.unchangedValue}">
										<div class="diff">
											<span class="diff-unchanged"><bean:write name="assetLog" property="unchangedValue" /></span> <span class="diff-type">(unchanged)</span>
										</div>
									</c:if>
									<c:if test="${not empty assetLog.newValue}">
										<div class="diff">
											<span class="diff-added"><bean:write name="assetLog" property="newValue" /></span> <span class="diff-type">(added)</span>
										</div>
									</c:if>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</logic:iterate>
				<c:if test="${!attributeDifferences.arefilesDifferent && !attributeDifferences.differencesToShow}">
					<tr>
						<td> There are no differences to display.</td>
					</tr>
				</c:if>
			</table>
			
			<input style="margin-right: 0px;" type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button js-enabled-show" id="closeButton" onclick="window.close();" />
		
		</div><!-- center_margin end -->
		
	</div><!-- wrapper_changed end -->
	
	

</body>
</html>