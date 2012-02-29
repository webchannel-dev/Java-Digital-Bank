<table>
	<tr>
		<th><bright:cmsWrite identifier="e-vat-receipt-address" filter="false"/></th>
	</tr>
	<tr>
		<logic:notEmpty name="orderForm" property="order.user.fullName">
			<td>
				<bean:write name="orderForm" property="order.user.fullName"/>
			</td>
		</logic:notEmpty>
	</tr>
	<tr>
		<logic:notEmpty name="orderForm" property="order.user.organisation">
			<td>
				<bean:write name="orderForm" property="order.user.organisation"/>
			</td>
		</logic:notEmpty>
	</tr>
	<bean:define id="vatReceiptAddress" name="orderForm" property="order.user.homeAddress"/>
	<logic:notEqual name="vatReceiptAddress" property="id" value="0">			
		<tr>
			<logic:notEmpty name="vatReceiptAddress" property="addressLine1">
				<td>
					<bean:write name="vatReceiptAddress" property="addressLine1"/>
				</td>
			</logic:notEmpty>
		</tr>
		<tr>
			<logic:notEmpty name="vatReceiptAddress" property="addressLine2">
				<td>
					<bean:write name="vatReceiptAddress" property="addressLine2"/>
				</td>
			</logic:notEmpty>
		</tr>
		<tr>
			<logic:notEmpty name="vatReceiptAddress" property="town">
				<td>
					<bean:write name="vatReceiptAddress" property="town"/>
				</td>
			</logic:notEmpty>
		</tr>
		<tr>
			<logic:notEmpty name="vatReceiptAddress" property="county">
				<td>
					<bean:write name="vatReceiptAddress" property="county"/>
				</td>
			</logic:notEmpty>
		</tr>
		<tr>
			<logic:notEmpty name="vatReceiptAddress" property="postcode">
				<td>
					<bean:write name="vatReceiptAddress" property="postcode"/>
				</td>
			</logic:notEmpty>
		</tr>
		<tr>
			<logic:notEmpty name="vatReceiptAddress" property="country.name">
				<td>
					<bean:write name="vatReceiptAddress" property="country.name"/>
				</td>
			</logic:notEmpty>
		</tr>
	</logic:notEqual>				
	<logic:equal name="vatReceiptAddress" property="id" value="0">			
		<tr>
			<td>
				<bean:write name="orderForm" property="order.user.address"/>
			</td>
		</tr>
	</logic:equal>
</table>