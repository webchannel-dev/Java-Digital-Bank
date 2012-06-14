<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<%@page import="com.bright.assetbank.application.util.GlobalLicense"%>

		<bright:applicationSetting id="subscription" settingName="subscription"/>		
		<bright:applicationSetting id="userMessagesEnabled" settingName="internal-user-messages-enabled"/>		
        <bright:applicationSetting id="showRegisterLink" settingName="showRegisterLink"/>
        <bright:applicationSetting id="anyAssetEntityAppearsFirst" settingName="any-option-appears-first-in-quick-search"/>
		<bright:applicationSetting id="localHelpEnabled" settingName="local-help-enabled"/>
		<bright:applicationSetting id="showCategoriesOnQuickSearch" settingName="categories-on-quick-search"/>
		
		<c:set var="showCategoriesOnQuickSearch" value="${showCategoriesOnQuickSearch}" scope="request" />
		   
         <logic:notPresent name="helpsection">
            <c:set var="helpsection" value="${section}"/>
         </logic:notPresent>

			 <%--  If user is admin, do max users check --%>
            <c:if test="${userprofile.hasExceededUserLimit}">
				<div class="error top-strip">
					<div class="pageWrapper">
						<p class="error">Your license restricts you to a maximum of <%= GlobalLicense.getInstance().getMaxNumUsers() %> users. You have exceeded this limit.  <a href="http://www.assetbank.co.uk/support/">Contact support</a></p>
					</div><!-- end of pageWrapper -->
				</div>
            </c:if>
			
			
       	  	<c:if test="${userprofile.CMSEditMode}">
	         	<%@include file="edit-mode-strip.jsp"%>
	         </c:if>

			<%@include file="language-strip.jsp"%>
			<div class="headShadow">
	        	<div id="header">
					<div class="pageWrapper">
	         		
						<!-- Include logo -->
						<%@include file="../customisation/logo_link.jsp"%>
						
						<div class="headerContent">	<!-- contains hLinks and search bar -->
							<!-- Header links -->
			            	<ul id="hLinks" class="linkList clearfix">
			               			
				               <bean:define id="bLoggedIn" value="false"/>
				               <logic:present name="userprofile">
				                  <logic:equal name="userprofile" property="isLoggedIn" value="true">
				                     <bean:define id="bLoggedIn" value="true"/>
				                  </logic:equal>	
				               </logic:present>
			               

								<%-- If user is logged in --%>
								<logic:equal name="bLoggedIn" value="true">
									<li class="no-border">
										<bright:cmsWrite identifier="menu-welcome" filter="false"/> 
										<logic:notEmpty name="userprofile" property="user.fullName">
											<bean:write name="userprofile" property="user.fullName" /> 							
										</logic:notEmpty>
										<logic:empty name="userprofile" property="user.fullName">
											<bean:write name="userprofile" property="user.username" /> 							
										</logic:empty>
									</li>

									<c:if test="${userprofile.originalUserId > 0}">
										<li>
											<a id="returnToUserLink" href="../action/returnToOriginalUser"><bright:cmsWrite identifier="link-return-to-original-user" /></a>
										</li>	
									</c:if>

									<%-- Subscription --%>
									<c:if test="${subscription}">
										<li>
											<a id="subscriptionsLink" href="../action/viewUserSubscriptions?start=true"><bright:cmsWrite identifier="menu-subscriptions" filter="false"/></a>
										</li>	
									</c:if>				

									<%-- Internal User Messages link --%>
									<c:if test="${userMessagesEnabled}">
										<li>
											<a id="messagesLink" href="../action/viewUserMessages?mgroup=CURRENT"><bright:cmsWrite identifier="menu-messages" filter="false"/></a>
										</li>	
									</c:if>				

									<li>
										<a id="profileLink" href="../action/viewChangeProfile"><bright:cmsWrite identifier="menu-profile" filter="false"/></a>
									</li> 
									
									<li>
										<a id="logoutLink" href="../action/logout"><bright:cmsWrite identifier="menu-log-out" filter="false"/></a>
									</li>	

								</logic:equal>	
			               
			               		<%-- If user not logged in --%>      
               					<logic:notEqual name="bLoggedIn" value="true">
               
									<%-- Register / Subscription --%>
									<c:choose>
										<c:when test="${subscription}">
											<li class="no-border">
												<a href="../action/viewSubscriptionRegister?start=true"><bright:cmsWrite identifier="menu-subscribe" filter="false"/></a>
											</li>
										</c:when>	
										<c:otherwise>
											<c:if test="${showRegisterLink}">			
												<li class="no-border">
													<a href="../action/viewRegisterUser"><bright:cmsWrite identifier="menu-register" filter="false"/></a>
												</li>	
											</c:if>     
										</c:otherwise>
									</c:choose>	
							
									<li>
										<a href="../action/viewLogin"><bright:cmsWrite identifier="menu-log-in" filter="false"/></a>
									</li>
									
              	 				</logic:notEqual>
               
      		   					<bright:applicationSetting id="emailThisPageEnabled" settingName="email-this-page-enabled"/>
								<c:if test="${emailThisPageEnabled && userprofile.isLoggedIn}">
									<script type="text/javascript">
										<!--
											function emailThisPage ()
											{
												window.location = '../action/viewEmailThisPage?page='+document.title;
											}
										-->
									</script>
									<li>
										<a class="emailLink" id="emailLink" href="" onclick="emailThisPage(); return false;" title="<bright:cmsWrite identifier="tooltip-email-page" filter="false"/>"><bright:cmsWrite identifier="link-email-this-page" filter="false"/></a> 
									</li>	
								</c:if>
								
								<%-- Help link --%> 
								<li class="separator">&nbsp;</li>	
								<li class="help-button">
									<a class="help-popup" id="helpLozenge" href="../action/viewHelp?helpsection=<bean:write name='helpsection'/>" target="_blank" title="<bright:cmsWrite identifier="tooltip-help-link" filter="false"/>"><bright:cmsWrite identifier="menu-help" /></a>
								</li>
								<script type="text/javascript"><!--
									if(document.getElementById("adminPage")) document.getElementById("helpLozenge").href += "&admin=true";
								//--></script>
						
								<c:if test="${userprofile.CMSEditMode && localHelpEnabled && helpsection != ''}">
									<li>
										<a class="topStripLink" href="../action/viewEditListItem?id=<c:out value="${helpsection}"/>&languageId=<c:out value="${userprofile.currentLanguage.id}"/>"><bright:cmsWrite identifier="link-edit-help-text" filter="false"/> (<c:out value="${helpsection}"/>)</a>
									</li>
								</c:if>	

							</ul>  <!-- end of hLinks -->
			            
							<!-- Search box -->
							<form action="quickSearch" method="get">
					  			<input type="text" id="searchKeywords" class="keywords" name="keywords" />
					  			<logic:notEmpty name="quickSearchEntities">
						  			<bean:size name="quickSearchEntities" id="numEntities"/>
						  			<logic:greaterThan name="numEntities" value="1">
							  			<select name="selectedEntities">
							  				<c:if test="${anyAssetEntityAppearsFirst}">
							  					<option value="-999">- <bright:cmsWrite identifier="snippet-any-type" filter="false" case="mixed"/> -</option>
							  				</c:if>
							  				<logic:iterate name="quickSearchEntities" id="entity">
							  					<option value="<bean:write name="entity" property="id"/>"><bean:write name="entity" property="name" /></option>
							  				</logic:iterate>
							  				<c:if test="${not anyAssetEntityAppearsFirst}">
							  					<option value="-999">- <bright:cmsWrite identifier="snippet-any-type" filter="false" case="mixed"/> -</option>
							  				</c:if>
							  			</select>
							  		</logic:greaterThan>
							  		<logic:equal name="numEntities" value="1">
							  			<logic:iterate name="quickSearchEntities" id="entity">
						  					<input type="hidden" name="entityId" value="<bean:write name="entity" property="id"/>" />
						  				</logic:iterate>
							  		</logic:equal>
					  			</logic:notEmpty>
					  			<c:if test="${showCategoriesOnQuickSearch == 'list'}">
					  				<c:set var="browserCategories" value="${quickSearchCategories}" scope="request" />
									<jsp:include page="/jsp/standard/en/public/inc_quick_search_categories_list.jsp" />
								</c:if>
					  			<input type="hidden" name="newSearch" value="true"/>
					  			<input type="hidden" name="quickSearch" value="true"/>
					  			<input class="button flush" type="submit" value="<bright:cmsWrite identifier="button-search" filter="false" />" /> <br />
					  		</form>
							
							
						</div>	<!-- end of headerContent -->
						<!-- Custom content -->
						<%@include file="../customisation/header_content.jsp"%>
					
					</div>	<!-- end of pageWrapper -->
					
	         </div>  <!-- end of header -->
			</div>	<!-- end of dropshadow -->
						