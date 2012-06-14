<%@include file="../inc/doctype_html.jsp" %>
<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
    d1      Steve Bryan    31-Oct-2008    Created 	    
--%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<%-- Set the return url and clear breadcrumb trail --%>
<c:set scope="session" var="breadcrumbTrail" value="" />	
<c:set scope="session" var="imageDetailReturnUrl" value="/action/viewUnsubmittedAssets"/>
<c:set scope="session" var="imageDetailReturnName"><bright:cmsWrite identifier="heading-unsubmitted-items" filter="false"/></c:set>

<head>
	
	<title><bright:cmsWrite identifier="heading-unsubmitted-items" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="my-uploads"/>
	<bean:define id="helpsection" value="unsubmitted_assets"/>
	<bean:define id="tabId" value="user"/>
	<script type="text/javascript" charset="utf-8">
		$j(function(){
			

			//Set up some variables
			var $actionRadios = $j('#workflowActions input[type="radio"]');
			var $actionButtons = $j('#workflowActions label');
			var $checkArray = $j('#itemsWrapper input.checkbox');		//get reference to all checkboxes on the page
			var $checkedboxes = $j('#itemsWrapper li input:checked');	//get reference to all initially checked checkboxes on the page

			
			// Click event on radioButtons
			$actionRadios.click(function(e){
				// First check some assets have actually been selected:
				if ($j('#itemsWrapper li input:checked').length > 0) {
					$actionButtons.removeClass('on');				// button highlighting
					if ($j(this).val() == 2) {
						if (!confirm('<bright:cmsWrite identifier="js-confirm-delete-asset" filter="false"/>')){
							return false;
						}
					}
					$j(this).addClass('on');
					$j('#mainForm').submit();
				} else {
					alert('<bright:cmsWrite identifier="js-make-sure-items-selected" filter="false"/>')
				}
			})
			
	


			//highlight wrapping li of any already selected checkboxes
			$checkedboxes.parents('li').addClass('selected');

			// click event on checkboxes
			$checkArray.click(function(event) {
				event.stopPropagation();	
				//highlight surrounding li
				$j(this).parents('li:eq(0)').toggleClass('selected');

			});
			
		})
		
		
	</script>
</head>

<body id="importPage" class="assetSelectPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1>My Uploads</h1> 

	<bean:define id="tabId" value="unsubmitted"/>
	<%@include file="../asset-admin/inc_my_uploads_tabs.jsp"%>
	

	<c:if test="${userprofile.isAdmin}">
		<p class="tabHolderPopup clearfix">
			<a class="active" href="viewUnsubmittedAssets">My Items</a>
			<a href="../action/viewUnsubmittedAssetsAdmin">Other Users</a>
		</p>
		<div id="tabContent">
	</c:if>
	
	<c:set var="returnSize" value="${assetWorksetForm.listAssets.returnedNumberResults}" />
	<c:set var="maxExceeded" value="${assetWorksetForm.listAssets.maxResultsExceeded}" />
	<c:set var="lNumUnsubmittedAssets" value="${returnSize}" />

	<p>
		<c:choose>
			<c:when test="${lNumUnsubmittedAssets == 1}">
				<bright:cmsWrite identifier="snippet-unsubmitted-asset" filter="false" replaceVariables="true" />
			</c:when>
			<c:otherwise>
				<bright:cmsWrite identifier="snippet-unsubmitted-assets" filter="false" replaceVariables="true" />
			</c:otherwise>
		</c:choose>
	</p>

	<c:set var="userIdParam" value="0" />
	<c:set var="submitUrl" value="submitUnsubmittedAsset" />
	<%@include file="inc_unsubmitted_assets.jsp"%>
	
	<c:if test="${userprofile.isAdmin}">
		</div> <!-- End of tabContent -->
	</c:if>	
	<%@include file="../inc/body_end.jsp"%>
</body>
</html>