<logic:notEqual name="orderForm" property="order.shippingAddress.id" value="0">
	<table>		
		<tr>
			<th><bright:cmsWrite identifier="e-shipping-address" filter="false"/></th>
		</tr>
		<tr>
			<logic:notEmpty name="orderForm" property="order.recipientName">
				<td>
					<bean:write name="orderForm" property="order.recipientName"/>
				</td>
			</logic:notEmpty>
		</tr>
		<tr>
			<logic:notEmpty name="orderForm" property="order.shippingAddress.addressLine1">
				<td>
					<bean:write name="orderForm" property="order.shippingAddress.addressLine1"/>
				</td>
			</logic:notEmpty>
		</tr>
		<tr>
			<logic:notEmpty name="orderForm" property="order.shippingAddress.addressLine2">
				<td>
					<bean:write name="orderForm" property="order.shippingAddress.addressLine2"/>
				</td>
			</logic:notEmpty>
		</tr>
		<tr>
			<logic:notEmpty name="orderForm" property="order.shippingAddress.town">
				<td>
					<bean:write name="orderForm" property="order.shippingAddress.town"/>
				</td>
			</logic:notEmpty>
		</tr>
		<tr>
			<logic:notEmpty name="orderForm" property="order.shippingAddress.county">
				<td>
					<bean:write name="orderForm" property="order.shippingAddress.county"/>
				</td>
			</logic:notEmpty>
		</tr>
		<tr>
			<logic:notEmpty name="orderForm" property="order.shippingAddress.postcode">
				<td>
					<bean:write name="orderForm" property="order.shippingAddress.postcode"/>
				</td>
			</logic:notEmpty>
		</tr>
		<tr>
			<logic:notEmpty name="orderForm" property="order.shippingAddress.country.name">
				<td>
					<bean:write name="orderForm" property="order.shippingAddress.country.name"/>
				</td>
			</logic:notEmpty>
		</tr>
	</table>
</logic:notEqual>				