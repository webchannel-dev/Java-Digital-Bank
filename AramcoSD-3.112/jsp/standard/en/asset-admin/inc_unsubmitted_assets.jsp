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

			<form id="mainForm" name="mainForm" method="post" action="<c:out value='${submitUrl}'/>"  class="approvalState">
				
				<c:if test="${userIdParam gt 0}">
					<input type="hidden" name="userId" value="<c:out value="${userIdParam}" />" />
				</c:if>
				
				

				<logic:iterate name="assets" id="item" indexId="index3">
				
					<c:if test="${ index3 == 0 }">
						<div style="float:right;margin-left:1.5em">
							<ul class="radioButtons" id="workflowActions">
							
								<c:choose>
									<c:when test="${ ! empty optionsList }">
										<c:set var="options" value="${optionsList[item.id]}" />
									</c:when>
									<c:otherwise>
										<c:set var="options" value="${optionsSingle}" />
									</c:otherwise>
								</c:choose>
							
								<logic:equal name="options" property="contains[0]" value="true">
									<li>
										<label>
											<input type="radio" class="radio" id="option_0'/>" name="option" value="0" />	
											<bright:cmsWrite identifier="snippet-submit-option-live" filter="false"/>
										</label>
									</li>
								</logic:equal>
							
								<logic:equal name="options" property="contains[1]" value="true">
									<li>
										<label>
											<input type="radio" class="radio" id="option_1'/>" name="option" value="1" />	
											<bright:cmsWrite identifier="snippet-submit-option-approval" filter="false"/>
										</label>
									</li>
								</logic:equal>
							
								<logic:equal name="options" property="contains[2]" value="true">
									<li>
										<label class="last">
											<input type="radio" class="radio" id="option_2'/>" name="option" value="2" />	
											<bright:cmsWrite identifier="button-delete" filter="false"/>
										</label>
									</li>
								</logic:equal>
							
							</ul>
											
							<input type="submit" value="Submit" class="button flush js-enabled-hide" style="margin:5px 0 0 0; float:left; left:0;" />
						</div>
					</c:if>
				</logic:iterate>
				
				<p>
					<bright:cmsWrite identifier="snippet-select" filter="false"/> 
					<a href="javascript:selectItems( true, 'mainForm' )"><bright:cmsWrite identifier="snippet-all" filter="false"/></a> | 
					<a href="javascript:selectItems( false, 'mainForm' )"><bright:cmsWrite identifier="snippet-none" filter="false"/></a>
				</p>
				<div class="clearing"></div>
				<br />
				<div class="clearfix" id="itemsWrapper">
					<ul class="lightbox">
						<logic:iterate name="assets" id="item" indexId="index3">	

								<c:if test="${item.typeId!=2}">
									<c:set var="resultImgClass" value="icon"/>
								</c:if>
								<c:if test="${item.typeId==2}">
									<c:set var="resultImgClass" value="image"/>
								</c:if>
						
								<c:set var="viewUrlParams" value="id=${item.id}" />															
								<li>
									<div class="selector">
										<label>
											<input type="checkbox" class="checkbox" name="assetId_<c:out value="${item.id}" />" />
											<bright:cmsWrite identifier="label-select-item" filter="false"/>
										</label>	
									</div>
		
									<%@include file="../inc/result_asset.jsp"%>						
	
									<p class="action">
										<%-- View asset details link --%>
										<a href="<c:out value='viewAsset?${viewUrlParams}' />" class="view"><bright:cmsWrite identifier="link-view-edit-details" filter="false" /></a>
									</p>
								</li>	

						</logic:iterate>
					</ul>		
				</div>
				
				
				
					
			</form>		
			
		</c:if>
								
	</c:if>

