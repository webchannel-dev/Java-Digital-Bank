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
	    $j('#dataLookupLoader' + dataLookupId ).hide(); // has "spinner" 
	 /*]]>*/
	</script>  
</logic:notEmpty>
<logic:empty name="assetForm" property="errors">
    <div class="confirmInline" id="confirmMessage<c:out value='${dataLookup.id}'/>"></div>
	<form name="dataLookupForm" id="dataLookupForm">
		<logic:iterate name='assetForm' property='dataLookupValues' id="dataLookup">
			<input type="hidden" name="dataLookup<c:out value='${dataLookup.id}'/>" id="dataLookup<c:out value='${dataLookup.id}'/>" value='<c:out value="${dataLookup.value}"/>'/>
		</logic:iterate>
	</form>

	<script type="text/javascript">
	/*<![CDATA[*/
	 
		function updateValues ()
		{   
			//hide the loading div...
			$j('#dataLookupLoader' + dataLookupId ).hide(); 
			
			var changeField = false;
			var sAttributePrefix = 'attribute_';
			var sDataLookupPrefix = "dataLookup";
			var hiddenFieldsToInsert = '';
			
			//iterate over the data lookup elements...
			$j.each($j("input[id^='dataLookup']"), function () 
			{
				var numericId = getNumericId ($j(this).attr("id"));
				if (numericId > 0)
				{
					var attributeName = sAttributePrefix + numericId;
					var attributeElement = $j('[name='+attributeName+']');
					if (attributeElement.length > 0 && $j(this).val().length > 0)
					{
						var $thisAttr = $j('#field'+numericId);
						if ($thisAttr.hasClass('editor')) 
						{
							tinyMCE.getInstanceById('field'+numericId).execCommand('mceInsertContent', false, $j(this).val());
						}
						attributeElement.val($j(this).val());
						changedField = true;
					}
					else
					{
						//check if an encrypted value is already present...
						var encAttributeElement = $j('input[name='+attributeName+'_encrypted]');
						if (encAttributeElement.val() == undefined || encAttributeElement.val().length <= 0)
						{
							//if not, add a hidden field with the value...
							hiddenFieldsToInsert  += '<input type="hidden" name="' + attributeName + '" value="' + $j(this).val() + '">';
						}
					}
				}
			});
			$j('#updateForm').append(hiddenFieldsToInsert);
			
			// populate the message div
			var $confirmMessage = $j('#confirmMessage');
			if( changeField ) 
			{
				$confirmMessage.text("The data lookup has been completed and the fields populated.");
			}
			else 
			{
				$confirmMessage.text("The data lookup has been completed.");
			}
		}

		function getNumericId (sId)
		{
			var iLen = sId.length;
			
			//10 because the prefix of the datalookup id is 'dataLookup'
			var numericId = sId.substring(10, iLen);
				
			//only return the id if it is a number...
			if (!isNaN(parseFloat(numericId)) && isFinite(numericId))
			{
				return numericId;
			}
			return -1;
		}
		
		updateValues();
		
		
	/*]]>*/
	</script>
</logic:empty>