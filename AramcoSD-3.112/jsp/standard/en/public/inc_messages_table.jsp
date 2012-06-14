
<logic:notPresent name="hideActions">
	<bean:define id="hideActions" value="false" />
</logic:notPresent>

<table cellspacing="0" class="message list highlight"  summary="List of messageSummaries">
	<logic:empty name="mgroup">
		<bean:define id="mgroup" value="CURRENT"/>
	</logic:empty>
	<thead>
		<tr>
			<logic:notEmpty name="showFrom">
				<th><bright:cmsWrite identifier="label-from" filter="false"/></th>
			</logic:notEmpty>
			<th><bright:cmsWrite identifier="label-subject" filter="false"/></th>
			<!-- <th>References</th> -->
			<th><bright:cmsWrite identifier="label-date-time" filter="false"/></th>
			<c:if test="${!hideActions}"><th colspan="2"><bright:cmsWrite identifier="label-actions" filter="false"/></th></c:if>
		</tr>
	</thead>
	<tbody>
		<logic:iterate name="messageSummaries" id="summary">
			<c:set var="readLink" value="readMessage?mgroup=${mgroup}&id=${summary.messageId}"/>

			<tr>
				<logic:notEmpty name="showFrom">
					<td><bean:write name="summary" property="from"/></td>
				</logic:notEmpty>	
				<td>
					<a href="<c:out value='${readLink}' />"><bean:write name="summary" property="subject"/><c:if test="${not empty summary.referenceName}"> - <bean:write name="summary" property="referenceName"/></c:if></a>
				</td>

				<!-- <td><c:choose><c:when test="${not empty summary.referenceName}"><a href="<bean:write name='summary' property='referenceLink'/>"><bean:write name="summary" property="referenceName"/></a></c:when><c:otherwise>-</c:otherwise></c:choose></td> -->
				
				<td>
					<fmt:formatDate type="both" dateStyle="default" timeStyle="short" value="${summary.dateCreated}"/>
				</td>

				<c:if test="${!hideActions}">
			
					<td class="action">
						[<a href="<c:out value='${readLink}' />"><bright:cmsWrite identifier="link-read" filter="false"/></a>]
					</td>
					
					<c:if test='${mgroup == "ARCHIVE"}'>
						<td class="action">
							[<a href="deleteMessage?mgroup=<bean:write name="mgroup"/>&id=<bean:write name="summary" property="messageId"/>&refLink=<bean:write name="summary" property="referenceLink"/>&refName=<bean:write name="summary" property="referenceName"/>"><bright:cmsWrite identifier="link-delete" filter="false"/></a>]
						</td>
					</c:if>
				
				</c:if>
			</tr>
		</logic:iterate>
	</tbody>
</table>