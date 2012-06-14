<tr>
	<th><bright:cmsWrite identifier="e-order-id" filter="false"/>:</th>
	<td><bean:write name="orderForm" property="order.displayPurchaseId"/></td>
	<logic:notEmpty name="orderForm" property="order.pspTransId">
		<th><bright:cmsWrite identifier="e-psp-id" filter="false"/>:</th>
		<td><bean:write name="orderForm" property="order.pspTransId"/></td>
	</logic:notEmpty>
</tr>
<tr>																																								
	<th><bright:cmsWrite identifier="e-date-placed" filter="false"/>:</th>
	<td><fmt:formatDate value="${orderForm.order.datePlaced}" pattern="${dateFormat}" /></td>
</tr>
<tr>
	<th><bright:cmsWrite identifier="e-user" filter="false"/>:</th>
	<td><bean:write name="orderForm" property="order.user.fullName"/> (<bean:write name="orderForm" property="order.user.username"/>)</td>
	<logic:notEmpty name="orderForm" property="order.userTelephone">
		<th><bright:cmsWrite identifier="e-label-contact-tel" filter="false"/></th>
		<td><bean:write name="orderForm" property="order.userTelephone"/> </td>
	</logic:notEmpty>
</tr>