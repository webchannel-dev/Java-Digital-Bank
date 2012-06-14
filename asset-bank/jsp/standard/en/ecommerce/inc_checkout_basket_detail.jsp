<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

		<%-- Show basket detail --%>
			<div class="hr"></div>
			<h2><bright:cmsWrite identifier="e-heading-puchase-details" filter="false"/></h2>	
			<p>
				<bright:cmsWrite identifier="e-purchasing-items" filter="false"/>
			</p>
			
			<ul class="lightbox clearfix cart">
			<c:if test="${usePriceBands}">
				<li class="headers">
	         	<div class="asset-thumb">&nbsp;</div>
	         	<div class="asset-name"><bright:cmsWrite identifier="e-asset" filter="false"/></div>
	         		
	         	<div class="asset-details">
	         	<span class="asset-format"><bright:cmsWrite identifier="e-format" filter="false"/></span>
	         	<span class="asset-price"><bright:cmsWrite identifier="e-price" filter="false"/></span>
	         			
	         	</div>
	         </li>
			</c:if>
			<logic:iterate name="checkoutForm" property="approvalList" id="assetapproval" indexId="index">
				<bean:define id="asset" name="assetapproval" property="asset" />
				<bean:define id="assetid" name="asset" property="id" />
					
				
				<li class="clearfix">		
					<c:if test="${asset.typeId!=2}">
						<c:set var="resultImgClass" value="icon"/>
					</c:if>
					<c:if test="${asset.typeId==2}">
						<c:set var="resultImgClass" value="image"/>
					</c:if>
		
					<div class="asset-thumb">
					<a class="<bean:write name='resultImgClass'/>" href="viewAsset?id=<bean:write name='asset' property='id'/>">
						<bean:define id="disablePreview" value="true"/>	
						<%@include file="../inc/view_thumbnail.jsp"%>
					</a>
					<br />
            		<%-- <a href="viewAsset?id=<bean:write name='asset' property='id'/>"><bright:cmsWrite identifier="e-id" filter="false"/>: <bean:write name="asset" property="idWithPadding"/></a><br /> --%>
					</div>
					
					<div class="asset-name">
	             		<h3><a href="viewAsset?id=<bean:write name='asset' property='id'/>"><bean:write name='asset' property='name'/></a></h3>
					</div>
					<div class="asset-details">
					<c:choose>
						<c:when test="${usePriceBands}">
							
							<c:forEach var="price" items="${userprofile.assetBox.assetPrices[asset.id]}">
								<div id="asset-item-details">
								<span class="asset-format">
								<c:out value="${price.priceBand.name}" />
								<c:if test="${price.priceBand.priceBandType.id == 2}">(x <c:out value="${price.quantity}" />)</c:if>
								</span>	
									
									
								<span class="asset-price">	
									<c:choose>
										<c:when test="${price.priceBand.priceBandType.id == 1 && price.priceBand.isCommercial}">
											<bright:cmsWrite identifier="e-tbc" filter="false"/>
										</c:when>
										<c:otherwise>
											<bright:writeMoney name='price' property='assetPrice.displayAmount' />
										</c:otherwise>
									</c:choose>
								</span>	
								<br />
								</div>
							</c:forEach>
							
							
						</c:when>
						<c:otherwise>
							<bright:cmsWrite identifier="e-price" filter="false"/>: <bright:writeMoney name='asset' property='price.displayAmount' /><br />				
						</c:otherwise>
					</c:choose>		
					</div>
				</li>		
			</logic:iterate>
			
			</ul>
