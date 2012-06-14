
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<logic:notPresent name="findUsersForm">
	<bean:define id="findUsersForm" name="userForm" />
</logic:notPresent>

<bean:parameter name="ajaxVersion" id="ajaxVersion" value="false"/>

<bean>
			<table cellspacing="0" class="list" style="margin-bottom:12px;" summary="List of users">		
				<thead>
				<tr>
					<th>
						<bright:cmsWrite identifier="label-name-nc" filter="false"/>&nbsp;
						<logic:equal name="orderId" value="1">
							<img src="../images/standard/arrow/up_arrow_off.gif" border="0" width="5" height="9" alt="">
						</logic:equal>
						<logic:notEqual name="orderId" value="1">
							<a href="../action/<c:out value='${reorderingAction}' />?searchCriteria.forename=<bean:write name='findUsersForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='findUsersForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='findUsersForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='findUsersForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='findUsersForm' property='searchCriteria.groupId'/>&amp;orderId=1"><img src="../images/standard/arrow/up_arrow.gif" border="0" width="5" height="9" alt="up"></a>
						</logic:notEqual>
						<logic:equal name="orderId" value="2">
							<img src="../images/standard/arrow/down_arrow_off.gif" border="0" width="5" height="9" alt="">
						</logic:equal>
						<logic:notEqual name="orderId" value="2">
							<a href="../action/<c:out value='${reorderingAction}' />?searchCriteria.forename=<bean:write name='findUsersForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='findUsersForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='findUsersForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='findUsersForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='findUsersForm' property='searchCriteria.groupId'/>&amp;orderId=2"><img src="../images/standard/arrow/down_arrow.gif" border="0" width="5" height="9" alt="down"></a>
						</logic:notEqual>
					</th>
					<th>
						<bright:cmsWrite identifier="label-username-nc" filter="false"/>&nbsp;
						<logic:equal name="orderId" value="3">
							<img src="../images/standard/arrow/up_arrow_off.gif" border="0" width="5" height="9" alt="">
						</logic:equal>
						<logic:notEqual name="orderId" value="3">
							<a href="../action/<c:out value='${reorderingAction}' />?searchCriteria.forename=<bean:write name='findUsersForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='findUsersForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='findUsersForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='findUsersForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='findUsersForm' property='searchCriteria.groupId'/>&amp;orderId=3"><img src="../images/standard/arrow/up_arrow.gif" border="0" width="5" height="9" alt="up"></a>
						</logic:notEqual>
						<logic:equal name="orderId" value="4">
							<img src="../images/standard/arrow/down_arrow_off.gif" border="0" width="5" height="9" alt="">
						</logic:equal>
						<logic:notEqual name="orderId" value="4">
							<a href="../action/<c:out value='${reorderingAction}' />?searchCriteria.forename=<bean:write name='findUsersForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='findUsersForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='findUsersForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='findUsersForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='findUsersForm' property='searchCriteria.groupId'/>&amp;orderId=4"><img src="../images/standard/arrow/down_arrow.gif" border="0" width="5" height="9" alt="down"></a>
						</logic:notEqual>
					</th>
					<th>
						<bright:cmsWrite identifier="label-email-nc" filter="false"/>&nbsp;
						<logic:equal name="orderId" value="5">
							<img src="../images/standard/arrow/up_arrow_off.gif" border="0" width="5" height="9" alt="">
						</logic:equal>
						<logic:notEqual name="orderId" value="5">
							<a href="../action/<c:out value='${reorderingAction}' />?searchCriteria.forename=<bean:write name='findUsersForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='findUsersForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='findUsersForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='findUsersForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='findUsersForm' property='searchCriteria.groupId'/>&amp;orderId=5"><img src="../images/standard/arrow/up_arrow.gif" border="0" width="5" height="9" alt="up"></a>
						</logic:notEqual>
						<logic:equal name="orderId" value="6">
							<img src="../images/standard/arrow/down_arrow_off.gif" border="0" width="5" height="9" alt="">
						</logic:equal>
						<logic:notEqual name="orderId" value="6">
							<a href="../action/<c:out value='${reorderingAction}' />?searchCriteria.forename=<bean:write name='findUsersForm' property='searchCriteria.forename'/>&amp;searchCriteria.surname=<bean:write name='findUsersForm' property='searchCriteria.surname'/>&amp;searchCriteria.username=<bean:write name='findUsersForm' property='searchCriteria.username'/>&amp;searchCriteria.emailAddress=<bean:write name='findUsersForm' property='searchCriteria.emailAddress'/>&amp;searchCriteria.groupId=<bean:write name='findUsersForm' property='searchCriteria.groupId'/>&amp;orderId=6"><img src="../images/standard/arrow/down_arrow.gif" border="0" width="5" height="9" alt="down"></a>
						</logic:notEqual>
					</th>
					<th>
						<bright:cmsWrite identifier="snippet-select" filter="false"/>
					</th>
					
				</tr>
				</thead>
				<c:set var="tests" value="${findUsersForm.users}"/>
				<tbody>
				<logic:iterate name="tests" id="user">
					<tr>
						<td>
							<bean:write name="user" property="surname" />,														
							<bean:write name="user" property="forename" />
						</td>
						<td>
							<bean:write name="user" property="username" />
						</td>
						<td>
							<c:if test="${empty user.emailAddress}"><span class="disabled">-</span></c:if><c:if test="${not empty user.emailAddress}"><bean:write name="user" property="emailAddress" /></c:if>
						</td>
						<c:choose>
							<c:when test="${ajaxVersion}">
								<td class="checkbox">
									<input type="checkbox" name="selectedUsers" value="<c:out value="${user.id}"/>"  class="checkbox js-enabled-hide" />
									<a href="find-users-results" class="js-enabled addUser" rel="<bean:write name="user" property="username" />">Add this user</a>
								</td>	
							</c:when>
							<c:otherwise>
								<td class="checkbox">
									<input type="checkbox" name="selectedUsers" value="<c:out value="${user.id}"/>"  class="checkbox" />
								</td>
							</c:otherwise>
						</c:choose>														
					</tr>
				</logic:iterate>
				</tbody>
			</table>