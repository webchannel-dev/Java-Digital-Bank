<%-- Requires:      	             																									     --%>
<%--	'formBean'		- the form      																									 --%>
<%--	'linkUrl'		- the Url, excluding the page parameters but including at least one parameter (as it is appended by &page=...)       --%>
<%--	'styleClass'	- the CSS style name to be used for the paging text															       	 --%>

<c:if test="${formBean.searchResults.numPages>1}">
	<div class="<c:out value="${styleClass}"/> clearfix floatRight">	
		<c:if test="${formBean.searchResults.pageIndex > 0}">
			<a class="bf" href="../action/<c:out value="${linkUrl}"/>&amp;page=<c:out value='${formBean.searchResults.pageIndex-1}'/>&amp;pageSize=<c:out value='${formBean.searchResults.pageSize}'/>"><bright:cmsWrite identifier="link-back" filter="false" /></a>
		</c:if>
		<c:if test="${formBean.searchResults.pageIndex == 0}">
			<span class="bf inactive"><bright:cmsWrite identifier="link-back" filter="false" /></span>
		</c:if>

		
		<%-- This code sets the begin and end indices so that maximum number of page links is shown --%>
		
		<c:set var="numPageLinks" value="10"/> <%-- This determines how many links are shown --%>

		<%-- If the page index >= 99, reduce the number of pages displayed by 2 --%>
		<c:if test="${formBean.searchResults.pageIndex>=99}">
			<c:set var="numPageLinks" value="${numPageLinks-2}"/>
		</c:if>
	
		<c:set var="beginIndex" value="1"/>
		<c:set var="endIndex" value="${formBean.searchResults.numPages}"/>
		<c:set var="prevFiller" value=""/>
		<c:set var="nextFiller" value=""/>	
		
		<c:if test="${formBean.searchResults.numPages > numPageLinks}">
			<c:if test="${formBean.searchResults.pageIndex > (numPageLinks/2)}">
				<c:set var="beginIndex" value="${(formBean.searchResults.pageIndex-(numPageLinks/2))+1}"/>
				<c:set var="prevFiller" value="...&nbsp;"/>
				<c:set var="endIndex" value="0"/>
			</c:if>
			<c:if test="${formBean.searchResults.pageIndex <= (numPageLinks/2)}">
				<c:set var="endIndex" value="${(numPageLinks/2) - formBean.searchResults.pageIndex}"/>
			</c:if>

			<c:if test="${((formBean.searchResults.numPages - formBean.searchResults.pageIndex) + endIndex) > (numPageLinks/2)+1}">
				<c:set var="endIndex" value="${endIndex + formBean.searchResults.pageIndex + (numPageLinks/2) + 1}"/>
				<c:set var="nextFiller" value="| ..."/>
			</c:if>
			<c:if test="${((formBean.searchResults.numPages - formBean.searchResults.pageIndex) + endIndex) <= (numPageLinks/2)+1}">		
				<c:set var="beginIndex" value="${beginIndex - ((numPageLinks/2) - (formBean.searchResults.numPages - formBean.searchResults.pageIndex)) - 1}"/>
				<c:set var="endIndex" value="${formBean.searchResults.numPages}"/>
			</c:if>
		</c:if>

		<%-- end --%>


		<span><bean:write name="prevFiller" filter="false"/></span>
		<c:forEach var="iPage" begin="${beginIndex}" end="${endIndex}">
			<c:if test="${iPage>1}">
				<span class="pipe">|</span>
			</c:if>
			<c:if test="${iPage==formBean.searchResults.pageIndex+1}">
				<span><c:out value="${iPage}"/></span>
			</c:if>
			<c:if test="${iPage!=formBean.searchResults.pageIndex+1}">
				<a href="../action/<c:out value="${linkUrl}"/>&amp;page=<c:out value='${iPage-1}'/>&amp;pageSize=<c:out value='${formBean.searchResults.pageSize}'/>"><c:out value="${iPage}"/></a>
			</c:if>
		</c:forEach>
		<span><bean:write name="nextFiller"/></span>
		

		<c:if test="${formBean.searchResults.pageIndex < formBean.searchResults.numPages-1}">
			<a class="bf" href="../action/<c:out value="${linkUrl}"/>&amp;page=<c:out value='${formBean.searchResults.pageIndex+1}'/>&amp;pageSize=<c:out value='${formBean.searchResults.pageSize}'/>"><bright:cmsWrite identifier="link-next" filter="false" /></a>
		</c:if>
		<c:if test="${formBean.searchResults.pageIndex >= formBean.searchResults.numPages-1}">
			<span class="bf"><bright:cmsWrite identifier="link-next" filter="false" /></span>
		</c:if>
	</div>
	
</c:if>
