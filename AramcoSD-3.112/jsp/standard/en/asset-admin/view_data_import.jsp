<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Chris Preager		24-May-2005		Imported from Image Manager
	 d2		Ben Browning		17-Feb-2006		HTML/CSS tidy up
	 d3 	Matt Woollard		10-Dec-2007		Added upload tool options
	 d4		Matt Stevenson		15-Mar-2006		Added switch to setup asset links
	 d5		James Home			02-Feb-2007		Added flag for using filename as title
	 d6     Matthew Woollard    17-Sep-2007     Added upload applet
	 d7     Matt Woollard       26-Nov-2007     Directories can be processed after bulk upload
	 d8     Matt Woollard       10-Dec-2007     Added flash uploader
	 d9 	Matt Woollard		29-Jun-2009		Allow files to be uploaded to existing assets
	 d10    Steve Bryan         01-Jul-2009		Merged in add placeholders
	 d11	Ben Browning		01-Feb-2011		UI overhaul
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<bright:applicationSetting id="ftpUsername" settingName="ftp-username"/>
<bright:applicationSetting id="ftpPassword" settingName="ftp-password"/>
<bright:applicationSetting id="ftpHost" settingName="ftp-host"/>
<bright:applicationSetting id="useFtp" settingName="use-ftp"/>
<bright:applicationSetting id="showFtpHelp" settingName="start-import-show-ftp-help"/>
<bright:applicationSetting id="flashEnabled" settingName="bulk-upload-use-flash"/>
<bright:applicationSetting id="appletEnabled" settingName="bulk-upload-use-applet"/>
<bright:applicationSetting id="ftpEnabled" settingName="bulk-upload-use-ftp-or-unc"/>
<bright:applicationSetting id="assetTypesEnabled" settingName="asset-entities-enabled"/>
<bright:applicationSetting id="hideIncompleteAssets" settingName="hide-incomplete-assets"/>
<bright:applicationSetting id="allowSearchByCompleteness" settingName="allow-search-by-completeness"/>

<bean:parameter id="entityId" name="entityId" value="0"/>
<%@include file="../inc/set_this_page_url.jsp"%>

<head>
	
	<title><bright:cmsWrite identifier="title-bulk-upload" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>

	<logic:equal name="importForm" property="isImportInProgress" value="true">
		<noscript>
			<!-- meta refresh for non js users -->
			<meta HTTP-EQUIV="refresh" CONTENT="20;URL=viewDataImport<c:if test="${importForm.parentId>0}">?parentId=<c:out value="${importForm.parentId}"/></c:if>"></meta>
		</noscript>	
	</logic:equal>		
	<script type="text/javascript">
		
		// WHen DOM is ready
		$j(function() {
			
			<logic:equal name="importForm" property="isImportInProgress" value="true">				
				// call the ajax update function every 2 seconds - this periodically reloads (ARG 1 URL) in ajaxUpdateContent, if any text matches (ARG 2 String) then redirect to (ARG 3 URL)
			  	setInterval("ajaxUpdate('viewDataImportProgress<c:if test="${importForm.parentId>0}">?parentId=<c:out value="${importForm.parentId}"/></c:if>', 'Import Complete', 'viewDataImport<c:if test="${importForm.parentId>0}">?parentId=<c:out value="${importForm.parentId}"/></c:if>')",2000);
			</logic:equal>
			
		
		});

	</script>


	<bean:define id="section" value="import"/>
	
	<script type="text/javascript">
		function hideContent() {
			document.getElementById('expandLink').style.display = 'block';
			document.getElementById('ftpHelp').style.display = 'none';
		}
	</script>
</head>

<body id="importPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 <c:if test="${userprofile.batchImportTimestamp == 0 || importForm.isImportInProgress}">class="underline"</c:if>><bright:cmsWrite identifier="heading-bulk-upload" filter="false" /></h1> 
	
	<logic:equal name="importForm" property="hasErrors" value="true">
		<div class="error">
			<bright:cmsWrite identifier="snippet-errors-during-import" filter="false"/> 				
			<ul>
			<logic:iterate name="importForm" property="errors" id="errorText">
				<li><bean:write name="errorText" filter="false"/></li>
			</logic:iterate>
			</ul>
		</div>
	</logic:equal>	
	
	
	
	<%-- An import is in progress --%>
	<logic:equal name="importForm" property="isImportInProgress" value="true">
		
		<%-- Steve 23-jun-2011: Added cancel link --%>
		<c:choose>		
			<c:when test="${empty param.cancel}">
				<p><a href="cancelDataImport" onclick="return confirm('<bright:cmsWrite identifier="snippet-bulk-upload-cancel-areyousure" />');"><bright:cmsWrite identifier="snippet-bulk-upload-cancel" /></a> <em><bright:cmsWrite identifier="snippet-bulk-upload-cancel-note" /></em></p>
			</c:when>
			<c:otherwise>
				<p><em><bright:cmsWrite identifier="job-cancelling" /></em></p>
			</c:otherwise>
		</c:choose>	
		
		<c:set var="url">viewDataImport<c:if test="${importForm.parentId>0}">?parentId=<c:out value="${importForm.parentId}"/></c:if></c:set>
		
		<!-- message for js users -->
		<h3 class="js-enabled-show progressLoader"><img src="../images/standard/misc/ajax_loader.gif" width="24" height="24" alt="loading..." /><bright:cmsWrite identifier="snippet-import-in-progress" filter="false"/>&nbsp;&nbsp;<span><bright:cmsWrite identifier="snippet-navigate-away" filter="false"/></span></h3>
		
		
		<!-- message for non js users -->
		<div class="js-enabled-hide">
			<bright:cmsWrite identifier="bulk-upload-in-progress" filter="false" replaceVariables="true"/>
		</div>	
	</logic:equal>	
	
	
	
	<%-- There is no import in progress --%>
	<logic:equal name="importForm" property="isImportInProgress" value="false">
		
		<c:choose>
			<c:when test="${userprofile.batchImportTimestamp gt 0}">
					<div class="confirm noIcon">
						<h3 class="confirm"><bright:cmsWrite identifier="subhead-bulk-upload-complete" filter="false"/></h3>
						<c:if test="${!hideIncompleteAssets || !allowSearchByCompleteness}">
							<c:if test="${userprofile.canUpdateAtAll && importForm.numIncompleteAssets==0}">
								<h3 class="confirm" style="margin-top:1em"><bright:cmsWrite identifier="subhead-all-uploaded-complete-metadata" filter="false" replaceVariables="true" /></h3>
							</c:if>	
						</c:if>
					</div>
				    <%-- Show a warning if there are incomplete assets and there's something that the user can do about it (i.e. they can update them). --%>
					<c:if test="${userprofile.canUpdateAtAll && showUpdateLinks && importForm.numIncompleteAssets>0}">
						<div class="warning warningTopAlign">
							<c:set var="numIncompleteItems"><bean:write name="importForm" property="numIncompleteAssets"/></c:set>
							<h3>
								<c:choose>
									<c:when test="${importForm.numIncompleteAssets==1}">
										<bright:cmsWrite identifier="snippet-1-item-incomplete-metadata" filter="false" replaceVariables="true" />
									</c:when>
									<c:otherwise>
										<bright:cmsWrite identifier="snippet-num-items-incomplete-metadata" filter="false" replaceVariables="true" />
									</c:otherwise>
								</c:choose>	
								
							</h3>

							<a href="createNewBatchFromImport?batchid=<c:out value='${userprofile.batchImportTimestamp}' />&incomplete=true&finishedUrl=<c:out value="${thisUrl}"/>" class="multilineButton">
								<strong><bright:cmsWrite identifier="link-batch-update-these-items" filter="false"/></strong><br />
								<span><bright:cmsWrite identifier="snippet-batch-update-summary" filter="false"/></span>
							</a>
							<a class="multilineButton" href="createNewBulkUpdateFromImport?batchid=<c:out value='${userprofile.batchImportTimestamp}' />&incomplete=true&cancelUrl=<c:out value="${thisUrl}"/>">
								<strong><bright:cmsWrite identifier="link-bulk-update-these-items" filter="false"/></strong><br />
								<span><bright:cmsWrite identifier="snippet-bulk-update-summary" filter="false"/></span>
							</a>


						</div>
					</c:if>	
				
					
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="intro-bulk-upload" filter="false" />
			</c:otherwise>	
		</c:choose>
		
		<c:if test="${importForm.noUploadableTypes }">
			<div class="error"><bright:cmsWrite identifier="snippet-no-uploadable-types" filter="false"/></div>
		</c:if>
		
	</logic:equal>
	
	
	<c:if test="${importForm.isImportInProgress}">
		<div id="ajaxUpdateContent">
			<!-- If Javascript enabled, ajax fetched progress messages will be shown here. -->
		</div>
	</c:if>
	<%-- Upload log --%>
	<logic:notEmpty name="importForm" property="messages">

		
		
		<c:if test="${userprofile.batchImportTimestamp gt 0}">
			<div <c:if test="${importForm.isImportInProgress}">class="js-enabled-hide"</c:if>>

				<c:if test="${!importForm.isImportInProgress}">
					<div class="toolbar" style="margin-top:1.5em; margin-bottom:0;">
						<c:set var="numUploadedItems"><c:out value="${importForm.numUploadedAssets}" /></c:set>
						<h3><bright:cmsWrite identifier="subhead-num-uploaded-items" filter="false" replaceVariables="true" /></h3>
						<c:if test="${userprofile.canUpdateAtAll}">
							<div class="group">	<a href="createNewBatchFromImport?batchid=<c:out value='${userprofile.batchImportTimestamp}'/>&finishedUrl=<c:out value="${thisUrl}"/>" title="<bright:cmsWrite identifier="snippet-batch-update-summary" filter="false"/>"><bright:cmsWrite identifier="link-batch-update-these-items" filter="false"/></a></div>
							<div class="group"><a href="createNewBulkUpdateFromImport?batchid=<c:out value='${userprofile.batchImportTimestamp}'/>&cancelUrl=<c:out value="${thisUrl}"/>" title="<bright:cmsWrite identifier="snippet-bulk-update-summary" filter="false"/>"><bright:cmsWrite identifier="link-bulk-update-these-items" filter="false"/></a></div>
						</c:if>
						<div class="clearing"></div>
					</div>
				</c:if>

				<div class="logHeading"><bright:cmsWrite identifier="subhead-bulk-upload-log" filter="false"/></div>
				<ul class="log stripey">
				<logic:iterate name="importForm" property="messages" id="message" indexId="count">
					<li <c:if test="${count% 2 != 0}">class="even"</c:if>><bean:write name="message" /></li>
				</logic:iterate>
				</ul>
			
				<c:if test="${importForm.parentId>0}">
					<br/>
					<a href="viewAsset?id=<bean:write name="importForm" property="parentId"/>#children"><bright:cmsWrite identifier="link-back-to" filter="false"/> <bright:cmsWrite identifier="snippet-parent" filter="false"/> <bright:cmsWrite identifier="item" filter="false"/></a>
				</c:if>
			</div>
		</c:if>
	</logic:notEmpty>
	
	<script type="text/javascript" charset="utf-8">
		$j(function(){
			// jump to bottom of log
			var $log = $j('ul.log');
			if ($log.length) {
				$log.scrollTop($log[0].scrollHeight);
			}
		})
		
	</script>	
		
	<%-- There is no import in progress --%>
	<logic:equal name="importForm" property="isImportInProgress" value="false">
		
		<c:if test="${userprofile.batchImportTimestamp gt 0}">
			<p><bright:cmsWrite identifier="snippet-finished-items-new-upload" filter="false"/></p>
		</c:if>	

		<html:form action="viewStartImport" method="get" styleId="startImportId">
			<input type="hidden" name="start" value="1"/>
			<bright:applicationSetting id="readTsCs" settingName="mustReadTsCsBeforeUpload"/>
			
			<c:choose>
				<c:when test="${readTsCs=='true'}">
					<p><input type="checkbox" name="conditionsAccepted" id="accept" style="width: auto; display: inline; margin-bottom: 0px; " value="true"/>
					<bright:refDataList id="copyItem" componentName="ListManager" methodName="getListItem" argumentValue="tsandcs-checkbox-upload" /> 
					<label for="accept"><bean:write	name="copyItem" property="body" filter="false" /></label></p>
				</c:when>
				<c:otherwise>
					<input type="hidden" name="conditionsAccepted" value="1" />
				</c:otherwise>
			</c:choose>

			<c:set var="startImportLink"><bright:cmsWrite identifier="link-start-data-import" filter="false"/></c:set>
			
			<c:choose> 
				<c:when test="${(ftpEnabled && !appletEnabled && !flashEnabled)}">
					<input type="hidden" name="uploadToolOption" value="ftp"/>
				</c:when> 
			
				<c:when test="${(!ftpEnabled && appletEnabled && flashEnabled)}">
					<input type="hidden" name="uploadToolOption" value="browser"/>
				</c:when> 
			
				<c:otherwise> 
					<c:if test="${!importForm.noUploadableTypes || (importForm.entitiesWithMatchAttributeExist && importForm.parentId<1)}">
						<!-- <p><bright:cmsWrite identifier="snippet-choose-upload-method" filter="false"/></p> BB - hidden this as it seems uneccessary -->
						<ul class="radioList">
							<c:if test="${appletEnabled || flashEnabled}">
								<li><html:radio name="importForm" property="uploadToolOption" value="browser" styleId="browser" styleClass="radio" /><span><label for="browser"><bright:cmsWrite identifier="snippet-upload-browser" filter="false"/></label></span></li></c:if>
							<c:if test="${ftpEnabled}"><li><html:radio name="importForm" property="uploadToolOption" value="ftp" styleId="ftp" styleClass="radio" /><span><label for="ftp"><bright:cmsWrite identifier="snippet-upload-ftp" filter="false"/></label></span></li></c:if>
						</ul>
					</c:if>
				</c:otherwise>
			</c:choose> 
				
			<%-- check to see if asset types are enabled and there is a type with a match attribute - if so display the extra options --%>
			<c:if test="${assetTypesEnabled && (importForm.childEntitiesWithMatchAttributeExist || importForm.entitiesWithMatchAttributeExist || (importForm.entitiesWithMatchAttributeExist && userprofile.userCanAddEmptyAssets)) && (!importForm.noUploadableTypes || (importForm.entitiesWithMatchAttributeExist && (importForm.parentId<1 || userprofile.userCanAddEmptyAssets)))}">
				<p><bright:cmsWrite identifier="snippet-choose-what-to-upload" filter="false"/></p>	
				<ul class="radioList">
					<c:if test="${!importForm.noUploadableTypes}">
						<li><input type="radio" class="radio" id="importNewAssets" name="whatToUpload" value="importNewAssets" checked/><span><label for="importNewAssets"><bright:cmsWrite identifier="snippet-new-assets-with-metadata" filter="false"/></label></span></li>
					</c:if>
			</c:if>
				
			<c:if test="${importForm.childEntitiesWithMatchAttributeExist && importForm.parentId<1}">
				<li><input type="radio" class="radio" id="importChildAssets" name="whatToUpload" value="importChildAssets" <c:if test="${importForm.noUploadableTypes}">checked</c:if> /><span><label for="importChildAssets"><bright:cmsWrite identifier="snippet-add-new-child-assets-to-parent" filter="false"/></label></span></li>
			</c:if>					
				
			<c:if test="${importForm.entitiesWithMatchAttributeExist && userprofile.userCanAddEmptyAssets && !importForm.noUploadableTypes}">
				<li><input type="radio" class="radio" id="addPlaceholders" name="whatToUpload" value="addPlaceholders" /><span><label for="addPlaceholders"><bright:cmsWrite identifier="snippet-add-placeholders" filter="false"/></label></span></li>
			</c:if>
				
			<c:if test="${importForm.entitiesWithMatchAttributeExist && importForm.parentId<1 && userprofile.userCanAddEmptyAssets}">
				<li><input type="radio" class="radio" id="importFilesToExistingAssets" name="whatToUpload" value="importFilesToExistingAssets" /><span><label for="importFilesToExistingAssets"><bright:cmsWrite identifier="snippet-add-files-to-existing-assets" filter="false"/></label></span></li>
				
			</c:if>
			<c:if test="${importForm.entitiesWithMatchAttributeExist && (importForm.parentId<1 || userprofile.userCanAddEmptyAssets)}">
			</ul>	
			</c:if>
			<p><input type="submit" name="submit" class="button flush" value="<bright:cmsWrite identifier="button-start-new-bulk-upload" filter="false"/>"></p>
				
			<html:hidden name="importForm" property="parentId"/>
			<input type="hidden" name="entityId" value="<bean:write name="entityId"/>"/>
				
		</html:form>
		
	</logic:equal>
	
	<bean:parameter name="queuefailure" id="queuefailure" value="false"/>
	<logic:equal name="queuefailure"value="true">
		<div class="error"><bright:cmsWrite identifier="snippet-import-not-started" filter="false"/></div>				
	</logic:equal>
	
	<%-- Upload log included again at bottom of page for case when you have no bulk upload in your session but there is still an old log hanging around
		BB - I am commenting this out to simplify the UI
	<logic:notEmpty name="importForm" property="messages">
		<c:if test="${userprofile.batchImportTimestamp == 0}">
			<br />
			<div class="logHeading"><bright:cmsWrite identifier="subhead-bulk-upload-log" filter="false"/></div>
			<ul class="log stripey">
				<logic:iterate name="importForm" property="messages" id="message" indexId="count">
					<li <c:if test="${count% 2 != 0}">class="even"</c:if>><bean:write name="message" /></li>
				</logic:iterate>
			</ul>
		</c:if>	
	</logic:notEmpty>
	--%>
	
	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>