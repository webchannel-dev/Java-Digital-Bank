<bright:applicationSetting id="supportMultiLanguage" settingName="supportMultiLanguage"/>
<bright:applicationSetting id="maxLanguagesBeforeDropdown" settingName="max-languages-before-dropdown"/>
<div id="topStrip" <c:if test="${supportMultiLanguage}">class="langSelect"</c:if>>
	<!-- coloured strip at top of screen -->
	<div class="pageWrapper clearfix">
		<%@include file="../customisation/top_strip_content.jsp"%>
		
		<c:if test="${supportMultiLanguage}">
		
			<c:if test="${userprofile.isAdmin}">
				<%-- Admin user sees all languages, i.e. suspended and 'default' ones - so they can change the content easily--%>
				<bright:refDataList id="availableLanguages" transactionManagerName="DBTransactionManager" componentName="LanguageManager" methodName="getLanguages"/>
			</c:if>
			<c:if test="${!userprofile.isAdmin}">
				<bright:refDataList id="availableLanguages" transactionManagerName="DBTransactionManager" componentName="LanguageManager" methodName="getLanguagesForUserToSelect"/>
			</c:if>
			<bean:size name="availableLanguages" id="numLanguages"/>
			<c:if test="${not empty availableLanguages && numLanguages>1}">
			
		
				<c:if test="${numLanguages > maxLanguagesBeforeDropdown}">
					<noscript>
				</c:if>	
				<ul class="languageLinks clearfix">
					<logic:iterate name="availableLanguages" id="lang">
						<c:choose>
							<c:when test="${lang.id==userprofile.currentLanguage.id }">	
								<li <c:if test="${not empty lang.iconFilePath}">class="graphic" style="background-image: url(../<c:out value="${lang.iconFilePath}"/>);"</c:if>> <bean:write name="lang" property="nativeName" /></li>								
							</c:when>
							<c:otherwise>
								<li <c:if test="${not empty lang.iconFilePath}">class="graphic" style="background-image: url(../<c:out value="${lang.iconFilePath}"/>);"</c:if> ><a href="../action/viewLanguage?language=<c:out value="${lang.id}"/><c:if test="${section == 'login'}">&login=1</c:if>"><bean:write name="lang" property="nativeName" /></a></li>
							</c:otherwise>
						</c:choose>
					</logic:iterate>	
				</ul>
				<c:if test="${numLanguages > maxLanguagesBeforeDropdown}">
					</noscript>
				
					<div class="dropHolder">
						<a href="" class="dropLink">
							<logic:iterate name="availableLanguages" id="lang">
								<c:if test="${lang.id==userprofile.currentLanguage.id }">
										<span <c:if test="${not empty lang.iconFilePath}">class="graphic" style="background-image: url(../<c:out value="${lang.iconFilePath}"/>);"</c:if>><bean:write name="lang" property="nativeName" /></span>
								</c:if>
							</logic:iterate>
						</a>
						<ul class="dropOptions">
							<logic:iterate name="availableLanguages" id="lang">
								<li <c:if test="${lang.id==userprofile.currentLanguage.id }">class="current"</c:if>>
									<a href="../action/viewLanguage?language=<c:out value="${lang.id}"/><c:if test="${section == 'login'}">&login=1</c:if>" <c:if test="${not empty lang.iconFilePath}">class="graphic" style="background-image: url(../<c:out value="${lang.iconFilePath}"/>);"</c:if>><bean:write name="lang" property="nativeName" /></a>
								</li>
							</logic:iterate>
						</ul>	
					</div>
					
				</c:if>
				
			</c:if>
		
		</c:if>
	</div><!-- end of pageWrapper -->
</div>