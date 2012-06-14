<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<style type="text/css">
	form.floated {
		overflow: visible;
	}
</style>

<div class="warning">
	<bright:cmsWrite identifier="duplicateAssetsInLightboxWarning" filter="false"/>
</div>

<c:forEach items="${duplicatesInLightBox}" var="entry">

	<h3><c:out value="${entry.key}" /></h3>
	
	<ul class="lightbox clearfix " style="margin-top: 1em;" rel="radio">
		<logic:iterate name="entry" property="value" id="item">
			<li>
				<div class="detailWrapper">
					<logic:empty name="item" property="displayHomogenizedImageFile.path">
						<c:set var="resultImgClass" value="icon"/>
					</logic:empty>
					<logic:notEmpty name="item" property="displayHomogenizedImageFile.path">
						<c:set var="resultImgClass" value="image"/>
					</logic:notEmpty>
	
					<%-- There is always a thumbnail for images --%>
					<c:if test="${item.typeId==2}">
						<c:set var="resultImgClass" value="image"/>
					</c:if>
					<a class="thumb">
						<%@include file="../../../standard/en/inc/view_thumbnail.jsp"%>
					</a>
					<div class="metadataWrapper">
						<%@include file="../../../standard/en/inc/result_asset_descriptions.jsp"%>
					</div>
					
				</div>
			</li>
		</logic:iterate>
	</ul>
	
</c:forEach>