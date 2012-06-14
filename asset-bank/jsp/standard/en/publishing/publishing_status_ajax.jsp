<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>


<logic:iterate name="taskIds" id="taskId">

	<bean:define name="messages_${taskId}" id="messages"/>

	<strong>Publish Action: </strong> <br />
	<ul id="publishing-status-messages-list" class="normal">
	<c:forEach items="${messages}" var="message">
		<li class="publishing-status-message"><c:out value="${message}" /></li>
	</c:forEach>
	</ul>
	<br />	

</logic:iterate>
	
<c:if test="${!inProgress}">
  <!-- The following marker comment is used when calling ajaxUpdate to indicate completion, and prompts a redirect -->
  <!-- TASK COMPLETE -->
</c:if>