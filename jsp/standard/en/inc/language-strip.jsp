<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>
<div id="topStrip" <c:if test="${supportMultiLanguage}">class="langSelect"</c:if>>
	<!-- coloured strip at top of screen -->
	<div class="pageWrapper">
		<%@include file="../customisation/top_strip_content.jsp"%>
		
		<c:if test="${supportMultiLanguage}">
		
			<bright:refDataList id="availableLanguages" transactionManagerName="DBTransactionManager" componentName="LanguageManager" methodName="getLanguages"/>	
			<bean:size name="availableLanguages" id="numLanguages"/>
			<c:if test="${not empty availableLanguages && numLanguages>1}">
				<ul class="languageLinks">
					<logic:iterate name="availableLanguages" id="lang">
						<c:if test="${userprofile.isAdmin or not lang.suspended}">
							<c:choose>
								<c:when test="${lang.id==userprofile.currentLanguage.id }">
									<c:if test="${not empty lang.iconFilePath}">
										<li class="graphic" style="background-image: url(../<c:out value="${lang.iconFilePath}"/>);"> <bean:write name="lang" property="nativeName" filter="false"/></li>
									</c:if>
									<c:if test="${empty lang.iconFilePath}">
										<li><bean:write name="lang" property="nativeName" filter="false"/></li>
									</c:if>											
								</c:when>
								<c:otherwise>
									<c:if test="${not empty lang.iconFilePath}">
										<li class="graphic" style="background-image: url(../<c:out value="${lang.iconFilePath}"/>);" ><a href="../action/viewLanguage?language=<c:out value="${lang.id}"/><c:if test="${section == 'login'}">&login=1</c:if>"><bean:write name="lang" property="nativeName" filter="false"/></a></li>
									</c:if>
									<c:if test="${empty lang.iconFilePath}">
										<li><a href="../action/viewLanguage?language=<c:out value="${lang.id}"/><c:if test="${section == 'login'}">&login=1</c:if>"><bean:write name="lang" property="nativeName" filter="false"/></a></li>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:if>
					</logic:iterate>	
				</ul>	
			</c:if>
		
		</c:if>
	</div><!-- end of pageWrapper -->
</div>