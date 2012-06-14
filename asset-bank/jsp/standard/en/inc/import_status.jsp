<h1 class="underline"><bean:write name="pagetitle" /></h1> 
	
<logic:equal name="importForm" property="isImportInProgress" value="false">
	<div class="confirm">Your import is complete.</div>
</logic:equal>
<logic:notEqual name="importForm" property="isImportInProgress" value="false">
	<h3 class="js-enabled-show progressLoader"><img src="../images/standard/misc/ajax_loader.gif" width="24" height="24" alt="loading..." />Your import is in progress...</h3>
</logic:notEqual>
	
<logic:notEmpty name="importForm" property="messages">
	<div id="ajaxUpdateContent">
		<!-- If Javascript enabled, ajax fetched progress messages will be shown here. -->
	</div>
	<logic:notEqual name="importForm" property="isImportInProgress" value="false">
		<noscript>
	</logic:notEqual>
		<h3><bright:cmsWrite identifier="subhead-import-log" filter="false"/></h3>
		<ul class="normal">
		<logic:iterate name="importForm" property="messages" id="message">
			<li><bean:write name="message" /></li>
		</logic:iterate>
		</ul>

	<logic:notEqual name="importForm" property="isImportInProgress" value="false">
		</noscript>
	</logic:notEqual>
</logic:notEmpty>