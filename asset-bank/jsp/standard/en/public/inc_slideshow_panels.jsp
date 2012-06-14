<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/ab-tag.tld" prefix="ab" %>

<bright:applicationSetting id="slideshowRepurposingEnabled" settingName="slideshow-repurposing-enabled"/>
<bright:applicationSetting id="slideshowEnabled" settingName="enable-slideshow"/>
<bright:applicationSetting id="categoryColumnsOnHomepage" settingName="category-columns-on-homepage"/>	

<c:if test="${slideshowRepurposingEnabled && slideshowEnabled && not empty slideShowList}">
	
	<%-- if requested slideshow has been deleted show a message --%>
	<c:if test="${slideShowMissing}">
		<div class="error"><bright:cmsWrite identifier="snippet-slideshow-not-found"/></div>
	</c:if>
	
	<%-- display the default slideshow if there is one --%>
	<c:if test="${not empty slideShow.homepageEmbeddableHtml}">
		<div class="slideshowPanel">
			<c:out value="${slideShow.homepageEmbeddableHtml}" escapeXml="false"/>

		</div>
	</c:if>
	
	<%-- display the list of slideshows available on the homepage --%>
	
	<c:set var="numSlideShows" value="${numSlideShows}" />
	
	<%
	// Get the number of categories
	Integer intNumSlideshows = (Integer)pageContext.getAttribute("numSlideShows");
	int iNumCols = (Integer)Integer.parseInt((String)pageContext.getAttribute("categoryColumnsOnHomepage")); 
	Double dRowCount = new Double(java.lang.Math.ceil(intNumSlideshows.intValue()/(double)iNumCols));
	pageContext.setAttribute("rowCount", dRowCount);
	%>
	
	<div class="leftShadow">
		<div class="rightShadow">
			<div class="browsePanel">
				<div class="categoryList">
					<h3><bright:cmsWrite identifier="snippet-slideshows-hopepage"/></h3>
					<ul class="cols<c:out value='${categoryColumnsOnHomepage}'/>" >	
						<logic:iterate name='slideShowList' id='item' indexId='idx'>
							<li>
								<c:if test="${slideShow.id!=item.id}">
									<a href="../action/viewHome?slideshowId=<bean:write name='item' property='id' />">
								</c:if>
									<bean:write name='item' property='description' filter="false"/>
								<c:if test="${slideShow.id!=item.id}">	
									</a>
								</c:if>
							</li>
							<c:if test="${ (idx+1==rowCount || idx+1==(2*rowCount) || idx+1==(3*rowCount) || idx+1==(4*rowCount) || idx+1==(5*rowCount) || idx+1==(6*rowCount)) }">
								</ul>
								<ul class="cols<c:out value='${categoryColumnsOnHomepage}'/>">
									<li class="hidden">&nbsp;</li>
							</c:if>
						</logic:iterate>
					</ul>
				</div>
				<div class="clearLeft"><!-- &nbsp; --></div>
			</div>
		</div>
	</div>
</c:if>