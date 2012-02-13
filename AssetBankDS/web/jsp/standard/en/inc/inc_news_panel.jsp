<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/ab-tag.tld" prefix="ab" %>



<bright:applicationSetting id="newsMaxCharacters" settingName="news-max-characters" />
<bright:refDataList id="newsItems" componentName="NewsManager" methodName="getNewsItems" transactionManagerName="DBTransactionManager"/>
<c:if test="${not empty newsItems}">
	<div class="leftShadow" style="margin-top:8px;">
		<div class="rightShadow">
			<div class="newsPanel">
				<h3><bright:cmsWrite identifier="heading-news" filter="false"/></h3>
				<logic:iterate name="newsItems" id="newsItem" indexId="newsItemIndex">
					<div class="newsContent">
						<div class="newsDate"><bean:write name="newsItem" property="createdDate" format="dd MMM, yyyy"/></div>
						<strong class="headline"><bean:write name="newsItem" property="name" filter="false"/></strong>
						<p>
						<bean:write name="newsItem" property="content" filter="false"/>
						<c:if test="${newsItem.isTruncated}">
							<c:if test="${newsMaxCharacters > 0}">... <br /></c:if><a href="viewNewsItem?id=<bean:write name="newsItem" property="id" filter="false"/>"><bright:cmsWrite identifier="link-more" filter="false"/></a>
						</c:if>
						</p>
					</div>
				</logic:iterate>
				<p><a href="viewNewsItems"><bright:cmsWrite identifier="link-view-all-news" filter="false"/></a></p>
			</div>
		</div>
	</div>
	
</c:if>