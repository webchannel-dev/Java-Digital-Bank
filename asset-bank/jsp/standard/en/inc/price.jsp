
<%-- Need to pass in a price bean --%>

<bright:applicationSetting id="ecommerce" settingName="ecommerce"/>
<bright:applicationSetting id="usePriceBands" settingName="price-bands"/>
<bright:refDataList componentName="PriceBandManager" methodName="getLowestPriceBandPrice" argumentValue="<%= String.valueOf(pageContext.getAttribute(\"typeId\")) %>" id="lowerPrice"/>

<logic:equal name="ecommerce" value="true">
	<br />
	
	<c:choose>
		<c:when test="${usePriceBands}">
			<%-- Check for special case of price < 0, this means free --%>
			<c:choose>
				<c:when test="${price.amount lt 0}">
					Free
				</c:when>
				<c:otherwise>
					From <bright:writeMoney name="lowerPrice" property="displayAmount" />
				</c:otherwise>
			</c:choose>
		</c:when>
		<c:otherwise>
			Price: <bright:writeMoney name="price" property="displayAmount" />
		</c:otherwise>
	</c:choose>
	
</logic:equal>
