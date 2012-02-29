<tr>
	<th><bright:cmsWrite identifier="e-subtotal" filter="false"/>:</th>
	<td><bean:write name="orderForm" property="order.subtotal.displayAmount" filter="false" /></td>
</tr>
<tr>
	<th><bright:cmsWrite identifier="e-tax" filter="false"/>:</th>
	<td><bean:write name="orderForm" property="order.vatPercent.displayNumber" />%</td>
</tr>
<tr>
	<th><bright:cmsWrite identifier="e-total" filter="false"/>:</th>
	<td><bean:write name="orderForm" property="order.total.displayAmount" filter="false"/></td>
</tr>