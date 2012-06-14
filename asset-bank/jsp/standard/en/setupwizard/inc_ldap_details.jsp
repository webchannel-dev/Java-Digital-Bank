<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>

	<c:choose>
		<c:when test="${!config.enabled}">
			<p><em>LDAP authentication is not enabled.</em></p>
		</c:when>
		<c:otherwise>
		
			<%--  Test connection --%>
			<c:choose>
				<c:when test="${empty config.error}">
					<div class="confirm">
						LDAP authentication is enabled.The Setup Wizard can connect to the LDAP server with these settings.
					</div>
				</c:when>
				<c:otherwise>
					<p><em>LDAP authentication is enabled.</em></p>
					<div class="error">
						The Setup Wizard cannot connect to the LDAP server with these settings. 
						<c:out value="${config.error}" />
					</div>
				</c:otherwise>
			</c:choose>
			

			<%--  Show the details --%>	
			<p>
				Synchronisation:
				<c:choose>
					<c:when test="${config.importUsersOnTheFly}">
						Import users on the fly (ie when they first log in)
					</c:when>
					<c:otherwise>
						Period (minutes) = <c:out value="${config.synchPeriod}" />
					</c:otherwise>
				</c:choose>
				<br/>
				Server URL: <c:out value="${config.serverUrl}" /> <br/>
				Admin Username: <c:out value="${config.adminUsername}" /> <br/>
				Admin Password: <c:out value="${config.adminPassword}" /> <br/>
				Base List: <c:out value="${config.baseList}" /> <br/>
			</p>													
			
		</c:otherwise>
	</c:choose>
