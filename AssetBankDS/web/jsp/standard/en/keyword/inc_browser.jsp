<logic:notEmpty name="keywordList">
	<div class="categoryList">
		<ul class="left">
			<c:set var="numKeywordsShown" value="0"/>
			<logic:iterate name="keywordList" id="keyword" indexId="index">
				<bright:itemCount id="itemCount" name="keyword"/>
				<c:if test="${itemCount>0}">
					<c:set var="numKeywordsShown" value="${numKeywordsShown+1}"/>
					<li>
						<a href="browseByKeyword?categoryId=<bean:write name='keyword' property='id'/>&categoryTypeId=<bean:write name='categoryTreeId'/>&filter=<bean:write name='filter'/>"><bean:write name="keyword" property="name" /></a>
						(<bean:write name="itemCount" filter="false"/>)
					</li>
					<c:if test="${(numKeywordsShown == colLength) || (numKeywordsShown == (colLength*2))}">
						</ul>
						<ul class="left" >
					</c:if>
				</c:if>
			</logic:iterate>	
		</ul>
	</div>
</logic:notEmpty>