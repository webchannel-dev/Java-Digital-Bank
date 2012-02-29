	<div class="head">
		<%-- Breadcrumb/return link in order of precedence: breadcrumb trail, return URl, javascript back --%>
		
		<%--  Only show the breadcrumb if there is a category id - we may have left the browser --%>
			
		<c:if test="${!empty breadcrumbTrail && !empty param.categoryId}">
			
			<%-- Browser --%>
			<bean:size id="trailSize" name="breadcrumbTrail" />
			<bright:cmsWrite identifier="you-are-here" filter="false" />&nbsp;
			<logic:iterate name="breadcrumbTrail" id="entry" indexId="id">
					<a href="<c:out value='${entry.value}' />"><c:out value='${entry.key}' /></a>
					&raquo;
			</logic:iterate>   				
			<bean:write name="assetForm" property="asset.name" filter="false"/> 
			
		</c:if>
		
		<c:if test="${empty breadcrumbTrail || empty param.categoryId}">
		
			<logic:present name="imageDetailReturnUrl">	
				
				<logic:equal name="imageDetailReturnName" value="Search Results">
					
					<%-- Search results --%>
					
					<bright:cmsWrite identifier="you-are-here" filter="false" />&nbsp;
					
					<c:choose>
						<c:when test="${searchPage=='savedSearches'}">
							<a href="viewSavedSearches"><bright:cmsWrite identifier="tab-saved-searches" filter="false"/></a>
							&raquo;
						</c:when>
						<c:otherwise>
							<a href="viewLastSearch?newSearch=true"><bright:cmsWrite identifier="tab-advanced-search" filter="false"/></a>
							&raquo;
						</c:otherwise>
					</c:choose>
						
					<a href="..<bean:write name='imageDetailReturnUrl'/>&newSearch=true"><bean:write name="imageDetailReturnName"/></a>
					&raquo;
					<bean:write name="assetForm" property="asset.name"/>					
						
				</logic:equal>
				
				<logic:notEqual name="imageDetailReturnName" value="Search Results">
					
					<%-- Other specified back eg lightbox, home --%>
					<a href="..<bean:write name='imageDetailReturnUrl'/>"><bright:cmsWrite identifier="link-back-to" filter="false"/> <bean:write name="imageDetailReturnName"/></a>					
				
				</logic:notEqual>
			</logic:present>
			
			<logic:notPresent name="imageDetailReturnUrl">
				<a href="javascript:history.back()"><bright:cmsWrite identifier="link-back" filter="false" /></a>
			</logic:notPresent>			
		</c:if>
		
		<%-- Postion in results description --%>
		<bean:parameter id="pos" name="index" value="0" />
		<bean:parameter id="total" name="total" value="0" />
		<bean:parameter id="collection" name="collection" value="" />
		<c:if test="${pos >= 0 && total > 0}">
				(<c:out value="${pos+1}" /> <bright:cmsWrite identifier="snippet-of"/> <c:out value="${total}" /><logic:notEmpty name="collection"> <bright:cmsWrite identifier="snippet-in"/> <c:out value="${collection}" /></logic:notEmpty>)			
		</c:if>
		
		<%-- Prev and next and this links --%>
		
		<%-- Default this link --%>
		<c:set var="viewUrl" value="viewAsset" />
		
		<c:if test="${total > 0}">
			<br /><br />
			<%-- Category browser --%>
			<bean:parameter id="categoryId" name="categoryId" value="0" />
			<bean:parameter id="categoryTypeId" name="categoryTypeId" value="0" />
			<bean:parameter id="sortAttributeId" name="sortAttributeId" value="0" />
			<bean:parameter id="sortDescending" name="sortDescending" value="false" />
			<bean:parameter id="filterId" name="filterId" value="0"/>

			<c:if test="${categoryId != 0 && categoryTypeId > 0}">
				<c:set var="viewUrl" value="viewBrowserItem" />
				<c:set var="params" value="categoryId=${categoryId}&categoryTypeId=${categoryTypeId}&sortAttributeId=${sortAttributeId}&sortDescending=${sortDescending}&filterId=${filterId}" />
			</c:if>
			
			<%-- Search results --%>
			<bean:parameter id="viewAction" name="view" value="" />
			<logic:notEmpty name="viewAction">
				<c:set var="viewUrl" value="${viewAction}" />
				<c:set var="params" value="1=1" />
			</logic:notEmpty>
	
			<%-- arrived from the assetbox --%>
			<bean:parameter id="assetState" name="assetState" value="0"/>
			<c:if test="${assetState != '0'}">
				<c:set var="params" value='total=${total}&assetState=${assetState}&collection=${collection}' />
			</c:if>
			
			<%-- Browse by popularity --%>
			<bean:parameter id="popularityId" name="popularityId" value="0"/>
			<c:if test="${popularityId>0}">
				<c:set var="viewUrl" value="viewPopularAsset" />
				<c:set var="params" value="popularityId=${popularityId}" />
			</c:if>
			
			<%-- Browse by recently added --%>
			<bean:parameter id="recent" name="recent" value="false"/>
			<c:if test="${recent}">
				<c:set var="viewUrl" value="viewRecentAsset" />
				<c:set var="params" value="recent=true" />
			</c:if>
			
			<%-- Browse by promoted assets --%>
			<bean:parameter id="promoted" name="promoted" value="false"/>
			<c:if test="${promoted}">
				<c:set var="viewUrl" value="viewPromotedAsset" />
				<c:set var="params" value="promoted=true" />
			</c:if>
						
			<logic:notEmpty name="viewUrl">
				<c:choose>
					<c:when test="${pos > 0}">
						<a href="<c:out value='${viewUrl}?index=${pos-1}&${params}' />"><bright:cmsWrite identifier="link-prev" filter="false" /></a>
					</c:when>
					<c:otherwise>
						<span class="disabled"><bright:cmsWrite identifier="link-prev" filter="false" /></span>
					</c:otherwise>
				</c:choose>
				&nbsp;|&nbsp;
				<c:choose>
					<c:when test="${pos < total - 1 && total > 0}">
						<a href="<c:out value='${viewUrl}?index=${pos+1}&${params}' />"><bright:cmsWrite identifier="link-next" filter="false" /></a>
					</c:when>
					<c:otherwise>
						<span class="disabled"><bright:cmsWrite identifier="link-next" filter="false" /></span>
					</c:otherwise>
				</c:choose>		
				
				<%-- Info for this link --%>
				<%-- In the view page, use ${viewUrl}?index=${pos}&categoryId=${categoryId}&categoryTypeId=${categoryTypeId} --%>
					
			</logic:notEmpty>	
		</c:if>
		<%@include file="../inc/extension-asset-info.jsp"%>		
	</div>


	<bean:parameter id="wfRemoved" name="wfRemoved" value="false"/>
	<c:if test="${wfRemoved}">
		<div class="info"><bright:cmsWrite identifier="snippet-workflow-removed" filter="false" /></div>
	</c:if>

	
