<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

	<c:if test="${returnSize == 0}">
		
	</c:if>

	
	<c:if test="${returnSize gt 0}">
		<p>
			<a href="worksetBatch<c:if test='${userIdParam gt 0}'>?userId=<c:out value='${userIdParam}'/></c:if>"><bright:cmsWrite identifier="link-batch-update-assets" filter="false"/></a>
		</p>
	
	
		<div class="hr"></div>	
	
		<c:set var="assets" value="${assetWorksetForm.listAssets.listAssets}" />
		<c:set var="optionsList" value="${assetWorksetForm.listAssets.assetSubmitOptions}" />
		<c:set var="optionsSingle" value="${assetWorksetForm.listAssets.singleSubmitOptions}" />

		<c:if test="${!empty assets}">						

			<logic:iterate name="assets" id="item" indexId="index3">
				
				<ul class="lightbox">
							
					<c:if test="${item.typeId!=2}">
						<c:set var="resultImgClass" value="icon"/>
					</c:if>
					<c:if test="${item.typeId==2}">
						<c:set var="resultImgClass" value="image"/>
					</c:if>
					
					<c:set var="viewUrlParams" value="id=${item.id}" />															
					<li>
						<%@include file="../inc/result_asset.jsp"%>						

						<p class="action">
							<%-- View asset details link --%>
							<a href="<c:out value='viewAsset?${viewUrlParams}' />" class="view"><bright:cmsWrite identifier="link-view-edit-details" filter="false" /></a>
						</p>
					</li>	
				
				</ul>		
				
				
				<form name="form_<c:out value='${item.id}' />" method="post" action="<c:out value='${submitUrl}'/>"  class="approvalState">
					<input type="hidden" name="id" value="<c:out value="${item.id}" />" />
					<c:if test="${userIdParam gt 0}">
						<input type="hidden" name="userId" value="<c:out value="${userIdParam}" />" />
					</c:if>
					
					<ul class="radioList">
					
						<c:choose>
							<c:when test="${!empty optionsList}">
								<c:set var="options" value="${optionsList[item.id]}" />
							</c:when>
							<c:otherwise>
								<c:set var="options" value="${optionsSingle}" />
							</c:otherwise>
						</c:choose>
						
						<logic:equal name="options" property="contains[0]" value="true">
							<li>
								<input type="radio" class="radio" id="option_0_<c:out value='${index3}'/>" name="option" value="0" />	
								<label for="option_0_<c:out value='${index3}'/>"><bright:cmsWrite identifier="snippet-submit-option-live" filter="false"/></label>
							</li>
						</logic:equal>
						
						<logic:equal name="options" property="contains[1]" value="true">
							<li>
								<input type="radio" class="radio" id="option_1_<c:out value='${index3}'/>" name="option" value="1" />	
								<label for="option_1_<c:out value='${index3}'/>"><bright:cmsWrite identifier="snippet-submit-option-approval" filter="false"/></label>
							</li>
						</logic:equal>
						
						<logic:equal name="options" property="contains[2]" value="true">
							<li>
								<input type="radio" class="radio" id="option_2_<c:out value='${index3}'/>" name="option" value="2" />	
								<label for="option_2_<c:out value='${index3}'/>"><bright:cmsWrite identifier="button-delete" filter="false"/></label>
							</li>
						</logic:equal>
						
					</ul>
										
					<input type="submit" value="Submit" class="button flush" style="margin:0; float:left; left:0;" />
				</form>
				<div class="clearing"><!-- &nbsp; --></div>
				
									
			</logic:iterate>
						
		</c:if>
								
	</c:if>

