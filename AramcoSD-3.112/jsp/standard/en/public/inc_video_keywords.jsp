<table class="">
	<thead>
		<th><bright:cmsWrite identifier="heading-keywords"/></th>     
		<th><bright:cmsWrite identifier="label-time"/></th>        
		<c:if test="${isEdit}"> 
			<th><bright:cmsWrite identifier="label-actions"/></th>
		</c:if>	
	</thead>
	<tbody id="keywordsTbody">
		<c:forEach items ="${videoKeywords}" var="videoKeyword">
			<tr <c:if test="${videoKeyword.edit}"> class="updated"</c:if>> 
			<input type="hidden" id="vkid" name="vkid" value="<c:out value='${videoKeyword.id}'/>"/>     
			<td style="width: 50%">
				<c:if test="${not isEdit}">
					<a href="#" onclick="vidKeywords.seekAndPlay(<c:out value='${videoKeyword.startTime}'/>,<c:out value='${videoKeyword.startTime + videoKeyword.duration}'/>); return false;">
					<c:out value="${videoKeyword.keywords}"/></a>
				</c:if>
				<c:if test="${isEdit}">
					<c:forEach items ="${videoKeyword.languageKeywords}" var="languageKeywords">
					<a href="#" onclick="vidKeywords.seekAndPlay(<c:out value='${videoKeyword.startTime}'/>,<c:out value='${videoKeyword.startTime + videoKeyword.duration}'/>); return false;">
					<c:out value="${languageKeywords.keywords}"/></a> (${languageKeywords.languageNativeName}) <br/>
				 	</c:forEach>
				</c:if>
			</td>
			<td>
				<c:if test="${((videoKeyword.startTime - (videoKeyword.startTime % 3600)) / 3600) > 0}"><fmt:formatNumber value="${(videoKeyword.startTime - (videoKeyword.startTime % 3600)) / 3600}" minIntegerDigits="2" maxFractionDigits="0"/>:</c:if><fmt:formatNumber value="${(videoKeyword.startTime - (videoKeyword.startTime % 60)) / 60}" minIntegerDigits="2" maxFractionDigits="0"/>:<fmt:formatNumber value="${(videoKeyword.startTime % 60)}" minIntegerDigits="2" maxFractionDigits="0"/>
				<c:if test='${videoKeyword.duration > 0 }'>
				    <c:set var="endTime" value="${videoKeyword.startTime + videoKeyword.duration}"/>
					- <c:if test="${((endTime - (endTime % 3600)) / 3600) > 0}"><fmt:formatNumber value="${(endTime - (endTime % 3600)) / 3600}" minIntegerDigits="2" maxFractionDigits="0"/>:</c:if><fmt:formatNumber value="${(endTime - (endTime % 60)) / 60}" minIntegerDigits="2" maxFractionDigits="0"/>:<fmt:formatNumber value="${(endTime % 60)}" minIntegerDigits="2" maxFractionDigits="0"/>
				</c:if>
			</td>
			<c:if test="${isEdit}">
				<td>[<a href="#keywordsWrapper" onclick="vidKeywords.edit('<c:out value="${videoKeyword.id}"/>',{
				<c:forEach items ="${videoKeyword.languageKeywords}" var="languageKeywords" varStatus="keywordstatus">
					'keywords<c:out value="${languageKeywords.languageId}"/>' : '<c:out value="${languageKeywords.jsKeywords}"/>'<c:if test="${not keywordstatus.last}">,</c:if>
				</c:forEach>
				},<c:out value="${videoKeyword.startTime}"/>,<c:out value="${videoKeyword.duration}"/>); return false;">edit</a>]&nbsp;[<a href="#" onclick="vidKeywords.deleteKeyword('<c:out value="${videoKeyword.id}"/>',this); return false;">delete</a>]</td>
			</c:if>						
			</tr>						
		</c:forEach>
	</tbody>  
</table>