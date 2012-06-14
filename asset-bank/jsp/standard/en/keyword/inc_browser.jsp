<logic:notEmpty name="keywordList">
	<div class="categoryList">
		<c:set var="visibleKeywords" value="0" />
		<logic:iterate name="keywordList" id="keyword" indexId="index">
			<bright:itemCount id="itemCount" name="keyword"/>
			<c:if test="${itemCount>0}">
				<c:set var="visibleKeywords" value="${visibleKeywords + 1}" />
			</c:if>
		</logic:iterate>
		<%
			String kws = pageContext.getAttribute("visibleKeywords").toString();
			Long numVisibleKeywords = (Long)Long.parseLong(kws);
			int iNumKeywordCols = 3;
			try
			{
				iNumKeywordCols = (Integer)Integer.parseInt((String)pageContext.getAttribute("categoryColumnsOnHomepage")); 
			}
			catch (NumberFormatException e)
			{
				// Do nothing - 3 is the default if not specified
			}
	 		pageContext.setAttribute("colLength", (float)numVisibleKeywords/(float)iNumKeywordCols); 
	 	%>
		<ul class="left">
			<c:set var="keywordsInThisCol" value="0"/>
			<c:set var="totalKeywords" value="0"/>
			<logic:iterate name="keywordList" id="keyword" indexId="index">
				<bright:itemCount id="itemCount" name="keyword"/>
				<c:if test="${itemCount>0}">
					<c:set var="keywordsInThisCol" value="${keywordsInThisCol+1}"/>
					<c:set var="totalKeywords" value="${totalKeywords+1}"/>
					<li>
						<a href="browseByKeyword?categoryId=<bean:write name='keyword' property='id'/>&categoryTypeId=<bean:write name='categoryTreeId'/>&filter=<bean:write name='filter'/>"><bean:write name="keyword" property="name" /></a>
						(<bean:write name="itemCount" />)
					</li>
					<c:if test="${(keywordsInThisCol >= colLength && totalKeywords != visibleKeywords)}">
						<c:set var="keywordsInThisCol" value="0" />
						</ul>
						<ul class="left" >
					</c:if>
				</c:if>
			</logic:iterate>	
		</ul>
	</div>
</logic:notEmpty>