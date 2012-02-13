<logic:notPresent name="sequencing">
	<bean:define id="sequencing" value="false"/>
</logic:notPresent>

<tr>
	<td style="width: 200px;">
		<bean:write name="filter" property="name"/>
	</td>
	<c:if test="${!isCategory || sequencing}">
		<td class="action">
			[<c:if test="${index > 0}"><a href="moveFilter?up=true&id=<bean:write name='filter' property='id'/>&type=1<c:if test='${isCategory}'>&categoryId=<bean:write name='category' property='id'/></c:if>"></c:if>up<c:if test="${index > 0}"></a></c:if>]
		</td>
		<td class="action">
			[<c:if test="${index < (count-1)}"><a href="moveFilter?up=false&id=<bean:write name='filter' property='id'/>&type=1<c:if test='${isCategory}'>&categoryId=<bean:write name='category' property='id'/></c:if>"></c:if>down<c:if test="${index < (count-1)}"></a></c:if>]
		</td>
	</c:if>
	<c:if test="${!sequencing}">
		<td class="action">
			[<a href="viewFilter?id=<bean:write name='filter' property='id'/>&type=1">edit</a>]
		</td>
		<td class="action">
			[<a href="deleteFilter?id=<bean:write name='filter' property='id'/>&type=1" onclick="return confirm('Are you sure you want to remove this filter?');" title="Delete this filter">X</a>]
		</td>
	</c:if>
	<c:if test="${isCategory}">
		<td colspan="2"></td>
	</c:if>
</tr>