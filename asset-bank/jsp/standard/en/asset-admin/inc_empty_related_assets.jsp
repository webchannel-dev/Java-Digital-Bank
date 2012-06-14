<logic:notEmpty name="relationships">
	<table class="list noTopMargin highlight" cellspacing="0">
		<thead>
			<tr>
				<th><bright:cmsWrite identifier='label-description' filter='false' /></th>
				<th></th>
				<th><bright:cmsWrite identifier='label-quantity' filter='false' /></th>
				<th><bright:cmsWrite identifier='label-name-nc' filter='false' /></th>
			</tr>	
		</thead>
		<logic:iterate name="relationships" id="relationship" indexId="index">
			<c:set var="relationship" value="${relationship}" scope="request" />
			<c:set var="quantityName" value="${prefix}quantity${relationship.id}" />
			<c:set var="nameName" value="${prefix}name${relationship.id}" />
			<tr>
				<td>
					<bean:write name='relationship' property='value' /> 
				</td>
				<td>
					<%-- Include form start JSPs from extensions --%>
					<abplugin:include-form-extensions extensibleEntity="emptyAssets" verb="add" position="iteration" />
				</td>
				<td class="spinner">
					<input type="text" id="<c:out value='${quantityName}'/>" name="<c:out value='${quantityName}'/>" value="0" class="vsmall" onchange="validate(this.id);" /> 
					<div class="buttons js-enabled-show"> 
						<a href="#" class="up" onclick="increment('<c:out value='${quantityName}'/>'); return false;">+</a>
						<a href="#" class="down" onclick="decrement('<c:out value='${quantityName}'/>'); return false;">-</a>
					</div>
				</td>	
				<td>
					<input type="text" name="<c:out value='${nameName}'/>" id="<c:out value='${nameName}'/>" size="30" style="height:13px;"/>
				</td>	
			</tr>	
		</logic:iterate>
	</table>	
</logic:notEmpty>
<logic:empty name="relationships">
	<bean:write name='noneFoundText' filter="false" />
	<div class="hr"></div>
</logic:empty>