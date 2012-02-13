<logic:iterate name="excludableItems" id="item">
		
	<bean:define id="bSelected" value="false"/>
	<logic:iterate name="excludedList" id="excluded">
		<c:if test="${excluded.itemId==item.id}">
			<bean:define id="bSelected" value="true"/>
		</c:if>
	</logic:iterate>
	
	<input type="checkbox" name="excluded_<bean:write name='item' property='id'/>" id="excluded_<bean:write name='item' property='id'/>" <logic:equal name="bSelected" value="true">checked="checked"</logic:equal> value="<bean:write name="item" property="description"/>" class="checkbox" />

	<label class="after" for="excluded_<bean:write name='item' property='id'/>"><bean:write name="item" property="description"/></label>
	<br />
			
			

</logic:iterate>