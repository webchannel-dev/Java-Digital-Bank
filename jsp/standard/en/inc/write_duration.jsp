<c:choose>
	<c:when test="${duration / 1000 > 60}">
		<bean:define id="minutes" value="<%= String.valueOf((((java.lang.Long)duration).longValue()/1000)/60) %>"/>
		<bean:define id="seconds" value="<%= String.valueOf((((java.lang.Long)duration).longValue()/1000)%60) %>"/>
		<bean:write name="minutes" format="0"/> 
		<c:choose>
			<c:when test="${minutes=='1'}"><bright:cmsWrite identifier="snippet-minute" filter="false"/></c:when>
			<c:otherwise><bright:cmsWrite identifier="snippet-minutes" filter="false"/></c:otherwise>
		</c:choose>
	</c:when>
	<c:otherwise>
		<bean:define id="seconds" value="<%= String.valueOf(((java.lang.Long)duration).longValue() / 1000) %>"/>
	</c:otherwise>
</c:choose>
<bean:write name="seconds"/> 
<c:choose><c:when test="${seconds=='1'}"><bright:cmsWrite identifier="snippet-second" filter="false"/></c:when><c:otherwise><bright:cmsWrite identifier="snippet-seconds" filter="false"/></c:otherwise></c:choose>