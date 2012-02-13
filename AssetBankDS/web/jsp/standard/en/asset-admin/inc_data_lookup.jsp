<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<logic:notEmpty name="assetForm" property="errors">
	<div class="error">
	<logic:iterate name="assetForm" property="errors" id="error">
		<bean:write name='error'/><br/>
	</logic:iterate>
	</div>
</logic:notEmpty>
<logic:empty name="assetForm" property="errors">
	<form name="dataLookupForm" id="dataLookupForm">
	<logic:iterate name='assetForm' property='dataLookupValues' id="dataLookup">
		<input type="hidden" name="dataLookup<c:out value='${dataLookup.id}'/>" id="dataLookup<c:out value='${dataLookup.id}'/>" value='<c:out value="${dataLookup.value}"/>'/>
	</logic:iterate>
	</form>

	<script type="text/javascript">
	/*<![CDATA[*/
		function updateValues (form)
		{
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
						element.value = sValue;
					}
				}
			}

			alert("The data lookup has been done and the fields populated.");
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