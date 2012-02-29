<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>


<ul class="normal">
<c:forEach items="${messages}" var="message">
	<li><c:out value="${message}" /></li>
</c:forEach>
</ul>
	
<c:if test="${!inProgress}">
  <!-- The following marker comment is used when calling ajaxUpdate to indicate completion, and prompts a redirect -->
  <!-- TASK COMPLETE -->
</c:if>