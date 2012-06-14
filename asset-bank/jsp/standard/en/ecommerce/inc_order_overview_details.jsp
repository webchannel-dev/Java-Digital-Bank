<table class="admin" cellspacing="0" border="0">
	<tr>
		<th><bright:cmsWrite identifier="e-order-id" filter="false"/></th>
		<th><bright:cmsWrite identifier="e-date-placed" filter="false"/></th>
		<logic:equal name="orderForm" property="orderWorkflow" value="1">
		<th><bright:cmsWrite identifier="e-date-fulfilled" filter="false"/></th>
		</logic:equal>
		<th><bright:cmsWrite identifier="e-user" filter="false"/></th>
		<th><bright:cmsWrite identifier="e-order-status" filter="false"/></th>
		<th><bright:cmsWrite identifier="e-total-cost" filter="false"/></th>
		<th colspan="1">&nbsp;</th>
	</tr>
	<logic:iterate name="orderForm" property="orderList" id="order">
		<tr>
			<td><bean:write name="order" property="displayPurchaseId"/></td>
			<td><fmt:formatDate value="${order.datePlaced}" pattern="${dateFormat}" /></td>
			<logic:equal name="orderForm" property="orderWorkflow" value="1">
				<td>				
					<c:choose>
						<c:when test="${order.hasPrints && order.status.id==1}">
							<fmt:formatDate value="${order.dateFulfilled}" pattern="${dateFormat}" />
						</c:when>
						<c:when test="${order.hasPrints && order.status.id==2}">
							<bright:cmsWrite identifier="e-awaiting" filter="false"/>
						</c:when>
						<c:otherwise>
							<bright:cmsWrite identifier="e-not-applicable" filter="false"/>
						</c:otherwise>			
					</c:choose>
				</td>
			</logic:equal>
			<td><bean:write name="order" property="user.fullName"/>(<bean:write name="order" property="user.username"/>)</td>
			<td><bean:write name="order" property="status.name"/></td>
			<td><bright:writeMoney name="order" property="total.displayAmount" /></td>
			<td class="action">
				<logic:equal name="order" property="personal" value="true">
					[<a href="viewPersonalOrderDetails?orderId=<bean:write name='order' property='id'/>"><bright:cmsWrite identifier="e-view" filter="false"/></a>]
				</logic:equal>
				<logic:equal name="order" property="personal" value="false">
					[<a href="viewCommercialOrderDetails?orderId=<bean:write name='order' property='id'/>"><bright:cmsWrite identifier="e-view" filter="false"/></a>]
				</logic:equal>
			</td>
		</tr>
	</logic:iterate>						
</table>