<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<logic:notEmpty name="assetForm" property="errors">
	<div class="errorInline" id="errorMessage<c:out value='${dataLookup.id}'/>">
	<logic:iterate name="assetForm" property="errors" id="error">
		<bean:write name='error'/><br/>
	</logic:iterate>
	</div>     
	<script type="text/javascript">
	/*<![CDATA[*/  
	    var $loaderDiv = $j('#dataLookupLoader' + dataLookupId ); // has "spinner" 
		// hide the "loading" message
		$loaderDiv.hide();     
     /*]]>*/
	</script>  
</logic:notEmpty>
<logic:empty name="assetForm" property="errors">
    <div class="confirmInline" id="confirmMessage<c:out value='${dataLookup.id}'/>">Test</div>
	<form name="dataLookupForm" id="dataLookupForm">
		<logic:iterate name='assetForm' property='dataLookupValues' id="dataLookup">
			<input type="hidden" name="dataLookup<c:out value='${dataLookup.id}'/>" id="dataLookup<c:out value='${dataLookup.id}'/>" value='<c:out value="${dataLookup.value}"/>'/>
		</logic:iterate>
	</form>

	<script type="text/javascript">
	/*<![CDATA[*/
	 
		function updateValues (form)
		{      
			var changeField = false;
			var $loaderDiv = $j('#dataLookupLoader' + dataLookupId ); // has "spinner"
			var $messageDiv = $j('#dataLookupMessage' + dataLookupId ); // plain message
			var $confirmMessage = $j('#confirmMessage'); // sits inside message div
			// hide the "loading" message
			$loaderDiv.hide(); 
			
			//iterate over the elements in the form...
			var sAttributePrefix = 'attribute_';
			for (var i=0; i < form.elements.length; i++)
			{
				var element = form.elements[i];
				
				if (element.name.indexOf(sAttributePrefix) >= 0)
				{
					//this is an attribute element - get the id...
					var sId = element.name.substring(sAttributePrefix.length, element.name.length);
					
					//use id the to check for a value from the map...
					var sValue = dataLookup(sId, document.getElementById('dataLookupForm'));
					
					//asign to the field...
					if (sValue && sValue != '')
					{
						var $thisAttr = $j('#field'+sId);
						if ($thisAttr.hasClass('editor')) {
							tinyMCE.getInstanceById('field'+sId).getWin().focus(); 
							tinyMCE.execCommand('mceInsertContent', false, sValue);
							}
						element.value = sValue;
						
						changeField = true;
					}
				}
			}
 
			// populate the message div
			if( changeField ) 
			{
				$confirmMessage.text("The data lookup has been completed and the fields populated.");
			}
			else 
			{
				$confirmMessage.text("The data lookup has been completed but no data was found.");
			}
		}

		//lookup the value from the hidden fields...
		function dataLookup(sId, form)
		{
			for (var i=0; i < form.elements.length; i++)
			{
				var element = form.elements[i];

				if (element.id == "dataLookup"+sId)
				{
					return element.value;
				}
			}
		}

		updateValues(document.getElementById('updateForm'));
		
		
	/*]]>*/
	</script>
</logic:empty>