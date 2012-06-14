<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/ab-tag.tld" prefix="ab" %>

<bright:refDataList componentName="BatchReleaseReportManager" transactionManagerName="DBTransactionManager"  methodName="getUnacknowledgedSummary" argumentValue="${userprofile.user.id}" id="summary"/>
						
<c:if test="${not empty summary && (summary.userCount > 0 || summary.totalCount > 0)}">
	<div class="info">
		
		<%-- variables used by the content snippets --%>
		<bean:define id="userCount" name="summary" property="userCount" />
		<bean:define id="totalCount" name="summary" property="totalCount" />
		<bean:define id="userId" name="userprofile" property="user.id" />

		<c:if test="${userCount > 0}">
			<c:choose>
				<c:when test="${userCount == 1}">
					<bright:cmsWrite identifier="snippet-outstanding-acknowledgement-user" filter="false" replaceVariables="true" />
				</c:when>
				<c:otherwise>
					<bright:cmsWrite identifier="snippet-outstanding-acknowledgements-user" filter="false" replaceVariables="true" />
				</c:otherwise>
			</c:choose>
		</c:if>

		<c:if test="${totalCount > 0 && totalCount > userCount}">
			<c:choose>
				<c:when test="${totalCount == 1}">
					<bright:cmsWrite identifier="snippet-outstanding-acknowledgement-total" filter="false" replaceVariables="true" />
				</c:when>
				<c:otherwise>
					<bright:cmsWrite identifier="snippet-outstanding-acknowledgements-total" filter="false" replaceVariables="true" />
				</c:otherwise>
			</c:choose>
		</c:if>
	</div>
</c:if>