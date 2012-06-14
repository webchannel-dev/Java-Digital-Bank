					<div class="toolbar">
					 	
						<h3><bright:cmsWrite identifier="subhead-select-attributes-display" filter="false"/></h3>
		
						<div class="group js-enabled-show" >
 								<bright:cmsWrite identifier="snippet-select" filter="false"/>: <a href="#"  onclick="selectCheckboxes('all', '#changeAttributes input.checkbox'); return false;"><bright:cmsWrite identifier="snippet-all-keywords" filter="false"/></a>, <a href="#" onclick="selectCheckboxes('none', '#changeAttributes input.checkbox'); return false;"><bright:cmsWrite identifier="snippet-none" filter="false" case="mixed"/></a>.
						</div>
					
						
						<logic:notPresent name="extraText">
							<c:set var="extraText" value="false" />
						</logic:notPresent>
						<c:if test="${extraText}">
							<div class="clearing"></div>
							<span><bright:cmsWrite identifier="snippet-compare-assets-text" filter="false"/></span>
						</c:if>
						<div class="clearing"></div>
				   	</div>
				
					<c:set var="attributesSelected" value="false" />
				   	<logic:iterate name="controlPanelForm" property="attributeList" id="attribute">
					  	<c:if test="${attribute.fieldName!='file'}">
						 	<c:if test="${(attribute.fieldName != 'price' || ecommerce) && attribute.isVisible}">
							   	<label for="requiredAttribute_<bean:write name='attribute' property='id'/>" class="after <c:if test="${attribute.fieldName != 'assetId'}">hideable</c:if>">
							   	<input type="checkbox" class="checkbox" name="requiredAttribute_<bean:write name='attribute' property='id'/>" value="true" id="requiredAttribute_<bean:write name='attribute' property='id'/>" <c:if test="${attribute.selected}">checked="checked"<c:set var="attributesSelected" value="true" /></c:if> <c:if test="${attribute.mandatoryAttribute}">disabled="disabled"</c:if>/> <bean:write name="attribute" property="label"/> </label>
						 		<c:if test="${attribute.mandatoryAttribute}">
						 			<input type="hidden" name="requiredAttribute_<bean:write name='attribute' property='id'/>" id="requiredAttribute_<bean:write name='attribute' property='id'/>" value="true"/>
						 		</c:if>
						 	</c:if>
					  	</c:if>
				   	</logic:iterate>
				   	<div class="clearing"></div>