<bright:applicationSetting id="categoryColumnsOnHomepage" settingName="category-columns-on-homepage"/>	
<bright:applicationSetting id="homepageBrowseTabs" settingName="homepage-browse-tabs"/>
<bean:parameter id="browseType" name="browseType" value="default"/>

<c:choose>
	<c:when test="${homepageBrowseTabs && browserCanHaveTabs}">
		<c:choose>
			<c:when test="${browseType=='popular'}">
				<bean:define id="tabId" value="browseByPopularity"/>
			</c:when>
			<c:when test="${browseType=='keyword'}">
				<c:set var="tabId" value="browse${keywordChooserForm.categoryTreeId}"/>
			</c:when>
			<c:otherwise>
				<bean:define id="tabId" value="browseCategories"/>
			</c:otherwise>
		</c:choose>

		<%@include file="../public/inc_home_browse_tabs.jsp"%>
		<div class="leftShadow tabsAbove">
	</c:when>
	<c:otherwise>
		<div class="leftShadow">
	</c:otherwise>
</c:choose>
		<div class="rightShadow">
			<div class="browsePanel">
				
				<c:if test="${!(homepageBrowseTabs && browserCanHaveTabs)}"><h3><bright:write name="browserTitle" case="mixed" filter="false"/></h3></c:if>
			
				<div class="categoryList">
				
				<c:choose>
				
					<c:when test="${browseType=='popular' && browserCanHaveTabs}">
						<!-- Browse by popularity -->
						
						<c:if test="${numMostPopularAssets>0}">
							<ul>
								<li><a href="viewMostViewedAssets"><bright:cmsWrite identifier="heading-most-viewed" filter="false" case="mixed"/></a></li>
								<li><a href="viewMostDownloadedAssets"><bright:cmsWrite identifier="heading-most-downloaded" filter="false" case="mixed"/></a></li>
							</ul>
						</c:if>
						<c:if test="${numLeastPopularAssets>0}">
							<ul>
								<li><a href="viewLeastViewedAssets"><bright:cmsWrite identifier="heading-least-viewed" filter="false" case="mixed"/></a></li>
								<li><a href="viewLeastDownloadedAssets"><bright:cmsWrite identifier="heading-least-downloaded" filter="false" case="mixed"/></a></li>
							</ul>
						</c:if>
						
					</c:when>
					<c:when test="${browseType=='keyword' && browserCanHaveTabs}">
						<!-- Browse by keywords -->	
						<bean:parameter id="categoryTreeId" name="id" value="3"/>
						<bean:define id="keywordList" name="homepageForm" property="keywords"/>
						<bean:define id="filter" value="all"/>
						<bean:size id="numKeywords" name="keywordList"/>
						<%
							double colLength = Math.ceil((float)numKeywords/(float)3); 
							pageContext.setAttribute("colLength", colLength);
						%>
						<%@include file="../keyword/inc_browser.jsp"%>
					</c:when>
					<c:otherwise>

						<!-- Default browse categories behaviour -->				
				
						<ul class="<c:if test='${browserHasImages}'>catThumbs</c:if> cols<c:out value='${categoryColumnsOnHomepage}'/>" >
					
				   		<%
				   		// Get the number of categories
				    		Integer intNumCats = (Integer)pageContext.getAttribute("browserNumCategories");
				    		int iNumCols = (Integer)Integer.parseInt((String)pageContext.getAttribute("categoryColumnsOnHomepage")); 
							Double dRowCount = new Double(java.lang.Math.ceil(intNumCats.intValue()/(double)iNumCols));
							pageContext.setAttribute("rowCount", dRowCount);
				   
				   		%>
				   
							<c:if test="${browserNumCategories > 0}">
								<logic:iterate name='browserCategories' id='category' indexId='idx'>
									<%-- If required, get the number of items in the category--%>
									<logic:equal name="showCategoryItemCounts" value="true">
										<bright:itemCount id="itemCount" name="category"/>
									</logic:equal>
									<c:choose>
										<c:when test="${not empty category.imageUrl}">
											<li>
												<table cellspacing="0" cellspadding="0">
													<tr>
														<td style="padding-right:5px"><a href="../action/browseItems?categoryId=<bean:write name='category' property='id' />&amp;categoryTypeId=<bean:write name='category' property='categoryTypeId' />"><img src="../<bean:write name='category' property='imageUrl'/>" width="<bean:write name='catImageWidth'/>" height="<bean:write name='catImageHeight'/>" alt="<bean:write name='category' property='name' filter='false'/>" /></a></td>
														<td><a href="../action/browseItems?categoryId=<bean:write name='category' property='id' />&amp;categoryTypeId=<bean:write name='category' property='categoryTypeId' />"><bean:write name='category' property='name' filter="false"/></a><logic:present name="itemCount"> <bdo dir="ltr">(<bean:write name="itemCount"/>)</bdo></logic:present>
														</td>
													</tr>
												</table>
											</li>
										</c:when>
										<c:otherwise>
											<li>
												<a href="../action/browseItems?categoryId=<bean:write name='category' property='id' />&amp;categoryTypeId=<bean:write name='category' property='categoryTypeId' />"><bean:write name='category' property='name' filter="false"/></a><logic:present name="itemCount"> <bdo dir="ltr">(<bean:write name="itemCount"/>)</bdo></logic:present>
											</li>
										</c:otherwise>
									</c:choose>		
									<c:if test="${ (idx+1==rowCount || idx+1==(2*rowCount) || idx+1==(3*rowCount) || idx+1==(4*rowCount) || idx+1==(5*rowCount) || idx+1==(6*rowCount)) }">
										</ul>
										<ul class="<c:if test='${browserHasImages}'>catThumbs</c:if> cols<c:out value='${categoryColumnsOnHomepage}'/>">
											<li class="hidden">&nbsp;</li>
									</c:if>
								</logic:iterate>
							</c:if>
						</ul>
					</c:otherwise>
				</c:choose>	
				</div>
			<div class="clearLeft"><!-- &nbsp; --></div>
			
			<c:if test="${showAllCatsLink == true}">
				<bright:applicationSetting id="allCatsLink" settingName="show-all-categories-link"/>
				<c:if test="${allCatsLink}">
					<p><a href="viewAlphabeticCategories"><bright:cmsWrite identifier="link-view-all-cats" filter="false" /></a></p>
				</c:if>
			</c:if>

		</div>	<!-- end of browsePanel -->
	</div>	<!-- end of rightShadow -->
</div>	<!-- end of leftShadow -->