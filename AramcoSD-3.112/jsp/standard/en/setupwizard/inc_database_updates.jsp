				<c:choose>
					<c:when test="${setupWizardForm.datasourceConfig.isNewConfig}">
						<c:set var="newConfig" value="${setupWizardForm.datasourceConfig.friendlyConfig}" />
						<p>
							Database Type: <c:out value="${newConfig.displayType}" /><br/>
							
							IP: <c:out value="${newConfig.ip}" /> <br/>
							Port: <c:out value="${newConfig.port}" /> <br/>
							
							<c:if test="${code != 'oracle'}">
								Schema: <c:out value="${newConfig.schema}" /> <br/>
							</c:if>
							
							<c:if test="${code != 'mysql'}">
								Instance: <c:out value="${newConfig.instance}" /> 	<br/>	
							</c:if>
							
							User: <c:out value="${newConfig.user}" />  <br/>
							Password: <c:out value="${newConfig.password}" /> 
						</p>
					</c:when>
					<c:otherwise>
						<p>
							Driver: <c:out value="${setupWizardForm.datasourceConfig.driverParam}" /> <br/>
							DB URL: <c:out value="${setupWizardForm.datasourceConfig.urlParam}" /> <br/>
							User: <c:out value="${setupWizardForm.datasourceConfig.userParam}" /> <br/>
							Password: <c:out value="${setupWizardForm.datasourceConfig.passwordParam}" /> <br/>
						</p>
					</c:otherwise>
				</c:choose>
