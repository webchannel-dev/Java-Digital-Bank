<%@include file="../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Matt Stevenson	27-Oct-2010		Created
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


<head>
	
	<title><bright:cmsWrite identifier="title-copy-asset-step1" filter="false"/></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="helpsection" value="copy-assets"/>		
	<script type="text/JavaScript">
		
		function copyMasterValues() {
			var $masterCheckBox = $j('#childAssets li.first input[type="checkbox"]');
			// does this page have checkboxes
			if ($masterCheckBox.length) {
				//get status of master checkbox and copy to other checkboxes
				var $masterCheckboxStatus = $masterCheckBox.is(':checked');
				var $otherCheckboxes = $j('#childAssets li:not(.first) input[type="checkbox"]');
				//$otherCheckboxes.each(function() {
				//	maintainRelationship(this);
				//});
				if ($masterCheckboxStatus) {
					// if the master is checked, check all the others
					$otherCheckboxes
						.attr('checked', true)
						.each( function() {	
							// highlight li 
							$j(this).parents('li').addClass('selected');
							
						});
					//show all radio divs
					$j('#childAssets div.radioGroup').show();	
				} else {
					$otherCheckboxes
						.removeAttr('checked')
						.each( function() {	
							$j(this).parents('li').removeClass('selected');
						});
					//hide all radio divs
					$j('#childAssets div.radioGroup').hide();		
				}
			};	
			
			var masterRadioVal = $j('#childAssets li.first input:radio:checked').val();
			//alert(masterRadioVal);
			
			$j('#childAssets input:radio').each(function() {
				if ($j(this).val() == masterRadioVal) {
					$j(this).attr('checked', true)
				}
			});

		}
	
		function maintainRelationship(thisCheck) {
			var $this = $j(thisCheck);
			var $checkboxStatus = $this.is(':checked');
			$this.parents('li.selectable').toggleClass('selected');
			if($checkboxStatus) {
				$this.parent().next('div').show();
			} else {
				$this.parent().next('div').hide();
			}	
		}	
	
	
		$j(function() {
			$j('#copyValuesLink').click(function() {
				copyMasterValues();
				return false;
			});
		});
	</script>
</head>

<body id="detailsPage">

	<%@include file="../inc/body_start.jsp"%>
	
	<h1 class="underline"><bright:cmsWrite identifier="heading-copy-asset-step1" filter="false"/></h1>

	<bean:size id="childAssetsSize" name="assetForm" property="childAssets"/>
	
	<div class="warning"><bright:cmsWrite identifier="copy-copy-asset-step1-warning" filter="false"/></div> 
	<bright:cmsWrite identifier="copy-copy-asset-step1" filter="false"/>

	<html:form action="copyAsset" method="post">
		<input type="hidden" name="assetId" value="<bean:write name='assetForm' property='asset.id' />" />

		<logic:notEmpty name="assetForm" property="childAssets">
			<h2><bright:cmsWrite identifier="label-child-assets" filter="false"/>:</h2>
			<ul class="assetList" id="childAssets">
				<logic:iterate name="assetForm" property="childAssets" id="asset" indexId="myIndex">
					<bean:define id="assetId" name="asset" property="id" />
					<li class="selectable <c:if test="${myIndex==0}">first</c:if>">		
						<table cellspacing="0">
							<tr>
								<td class="image">				
	
									<%@include file="../inc/result-resultimgclass.jsp"%>					
									<bean:define id="disablePreview" value="true"/>	
									<%@include file="../inc/view_thumbnail.jsp"%>		
								</td>
								<td style="width: 48%;">				
									<c:set var="item" value="${asset}"/>	
									<%@include file="../inc/result_asset_descriptions.jsp"%>
									<%@include file="../inc/result-video-audio-icon.jsp"%>
							
										<c:set var="identifier" value="relate${asset.id}" />
										<label for="should<bean:write name='identifier' />" class="wrapping"><input type="checkbox" class="checkbox" name="should<bean:write name='identifier' />" id="should<bean:write name='identifier' />" value="1" onclick="maintainRelationship(this, <bean:write name='identifier' />)"/> Maintain a relationship...</label>
										<div id="radios<bean:write name='identifier' />" class="radioGroup">
											<label for="<bean:write name='identifier' />" class="wrapping"><input type="radio" class="checkbox" name="<bean:write name='identifier' />" id="<bean:write name='identifier' />" value="1" checked /> ...to the existing asset</label>
											<label for="<bean:write name='identifier' />copy" class="wrapping"><input type="radio" class="checkbox" name="<bean:write name='identifier' />" id="<bean:write name='identifier' />copy" value="2" />  ...to a copy of the existing asset</label>
										</div>
						
						
								</td>
								<td <c:if test="${childAssetsSize gt 1}">class="copyAction"</c:if>>
									<%-- If on the first item and there are more then one then show 'copy to all' link --%>
									<c:if test="${myIndex==0}">
										<c:if test="${childAssetsSize gt 1}">
												<a href="#" id="copyValuesLink">Copy these values to all items &raquo;</a>
										</c:if>	
									</c:if>	
									&nbsp;
								</td>
							<tr>
						</table>	
					</li>
				</logic:iterate>
			</ul>
	
		</logic:notEmpty>

		<input type="submit" name="submit" value="<bright:cmsWrite identifier="button-next" filter="false" />" class="button flush floated" />
		<a href="../action/viewAsset?id=<bean:write name='assetForm' property='asset.id' />" class="cancelLink">Cancel</a>
	</html:form>

	<%@include file="../inc/body_end.jsp"%>
	
</body>
</html>