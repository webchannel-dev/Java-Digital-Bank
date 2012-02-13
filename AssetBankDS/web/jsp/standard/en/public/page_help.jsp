<%@ page import="com.bright.assetbank.application.constant.AssetBankSettings" %>
<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		James Home		02-Feb-2004		Created.
	 d2		Tamora James	12-May-2005		Integrated with UI design for Demo
	 d3		Ben Browning	22-Feb-2006		HTML/CSS tidy up
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="keywordAutoAdd" settingName="keyword-auto-add" />
<bean:define id="keywordDelimiter" value="<%= AssetBankSettings.getKeywordDelimiter() %>"/>
<bright:applicationSetting id="canRequestPermissionsUpdate" settingName="users-can-request-permission-update"/>
<bright:applicationSetting id="useOrgUnits" settingName="orgunit-use" />
<bright:applicationSetting id="showUseDropdownBeforeDownload" settingName="showUseDropdownBeforeDownload"/>
<bright:applicationSetting id="mustReadTsCsBeforeDownload" settingName="mustReadTsCsBeforeDownload"/>
<bright:applicationSetting id="enableGroupUsageExclusions" settingName="enable-group-usage-exclusions"/>
<bright:applicationSetting id="showUseDropdownBeforeDownload" settingName="showUseDropdownBeforeDownload"/>
<bright:applicationSetting id="simpleConvertOptionsForDownload" settingName="simpleConvertOptionsForDownload"/>
<bright:applicationSetting id="notUsingActiveDirectory" settingName="suspend-ad-authentication"/>
<bright:applicationSetting id="usePriceBands" settingName="price-bands"/>
<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="localHelpEnabled" settingName="local-help-enabled"/>

<bean:parameter id="helpsection" name="helpsection" />
<bean:parameter name="admin" id="adminTab" value="false"/>
<c:set var="adminTab" value="${adminTab && userprofile.isAdmin}"/>
<bright:refDataList id="itemCopy" componentName="ListManager" methodName="getListItem" argumentValue="helpsection" argsAreBeans="true" />

<head>
	
	<title><bright:cmsWrite identifier="title-help"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<script type="text/javascript">
		//When DOM ready...
		$j(function() {
			// set click events on tabs
			
			<c:if test="${localHelpEnabled && not empty itemCopy}">
				//custom help content tab
				$j('#helpTab').click(function() {
        			//switch active tab
        			switchTab(this);
        			//show correct content
        			$j('#oHelpContent').hide();
        			$j('#aHelpContent').hide();
        			$j('#helpContent').show();
        			return false;
        		});
			</c:if>
			
			//general online help tab
        	$j('#oHelpTab').click(function() {
        		//switch active tab
        		switchTab(this);
        		//show correct content
        		$j('#helpContent').hide();
        		$j('#aHelpContent').hide();
        		$j('#oHelpContent').show();
        		return false;
        	});
        	
        	//admin online help tab
        	$j('#aHelpTab').click(function() {
        		//switch active tab
        		switchTab(this);
        		//show correct content
        		$j('#helpContent').hide();
        		$j('#oHelpContent').hide();
        		$j('#aHelpContent').show();
        		return false;
        	});
        	
        	window.onresize = iframeHeight;

        	
		});
		
		function iframeHeight() {
			var newHeight = $j(window).height();
			var buffer = 180; 
			var newIframeHeight = newHeight - buffer;
			$j('iframe').css('height',newIframeHeight);
		}
		
	</script>
	<style type="text/css">
		/* Initially hide online content in help popup */
		<c:if test="${localHelpEnabled && not empty itemCopy}">
			#oHelpContent, #aHelpContent {
				display:none;
			}
		</c:if>
		<c:if test="${!localHelpEnabled || empty itemCopy}">
			#helpContent, #helpTab{
				display:none;
			}
			<c:if test="${adminTab}">
				#oHelpContent {
					display:none;
				}
			</c:if>
			<c:if test="${not adminTab}">
				#aHelpContent {
					display:none;
				}
			</c:if> 
		</c:if>
	</style>
</head>



<body id="help">

	<div class="copy">
		<p id="tabsContainer" class="tabHolderPopup clearfix" style="margin-top:0;">
			<a id="helpTab" href="#" class="active"><bright:cmsWrite identifier="heading-help" filter="false"/> </a>
			<a id="oHelpTab" <c:if test="${(!localHelpEnabled || empty itemCopy) && not adminTab}">class="active"</c:if> href="http://www.assetbank.co.uk/go/help/<bean:write name="helpsection" />/&template=popup_help"><bright:cmsWrite identifier="heading-online-help" filter="false"/></a>
			<c:if test="${userprofile.isAdmin}">
				<a id="aHelpTab" <c:if test="${(!localHelpEnabled || empty itemCopy) && adminTab}">class="active"</c:if> href="http://www.assetbank.co.uk/go/adminhelp/<bean:write name="helpsection" />/&template=popup_help"><bright:cmsWrite identifier="heading-admin-help" filter="false"/></a>
			</c:if>
		</p>
		<div id="tabContent">
			<div id="helpContent">
					
			
				<c:choose>
					<c:when test="${not empty itemCopy}">
						<bean:write name="itemCopy" property="body" filter="false"/>
	
						<%-- Check for extra admin text --%>
						<c:if test="${userprofile.isAdmin}">
							<c:set var="helpsectionadmin" value="${helpsection}_admin" />
							<bright:refDataList id="itemCopy" componentName="ListManager" methodName="getListItem" argumentValue="helpsectionadmin" argsAreBeans="true"/>	
							<c:if test="${not empty itemCopy}">
								<bean:write name="itemCopy" property="body" filter="false"/>		
							</c:if>
						</c:if>						
					</c:when>
					
					<c:otherwise>
						<h2><bright:cmsWrite identifier="heading-help" filter="false"/></h2> 
						<p><bright:cmsWrite identifier="snippet-no-help" filter="false"/></p>						
					</c:otherwise>
				</c:choose>					
			</div>

			<div id="oHelpContent">
				<h2><bright:cmsWrite identifier="heading-online-help" filter="false"/></h2>
				<iframe frameborder="0" style="border: none;" src="http://www.assetbank.co.uk/go/help/<bean:write name="helpsection" />/&template=popup_help" width="100%" height="370px"></iframe>
			</div>
			<div id="aHelpContent">
				<h2><bright:cmsWrite identifier="heading-admin-help" filter="false"/></h2>
				<iframe frameborder="0" style="border: none;" src="http://www.assetbank.co.uk/go/adminhelp/<bean:write name="helpsection" />/&template=popup_help" width="100%" height="370px"></iframe>
			</div>
		</div>
		<br />

		<input type="button" value="<bright:cmsWrite identifier='button-close' filter='false' />" class="button js-enabled-show" id="closeButton" onclick="window.close();" />

	</div>

</body>
</html>