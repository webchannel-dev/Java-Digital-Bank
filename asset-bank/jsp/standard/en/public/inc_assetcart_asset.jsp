<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

 			<c:if test="${asset.typeId!=2}">
				<c:set var="resultImgClass" value="icon"/>
			</c:if>
			<c:if test="${asset.typeId==2}">
				<c:set var="resultImgClass" value="image"/>
			</c:if>
	
			<li>
				<div class="asset-thumb">
					<a class="<bean:write name='resultImgClass'/>" href="viewAsset?id=<bean:write name='asset' property='id'/>">
						<bean:define id="disablePreview" value="true"/>	
						<%@include file="../inc/view_thumbnail.jsp"%>								
					</a><br />
            		<%-- <a href="viewAsset?id=<bean:write name='asset' property='id'/>">ID: <bean:write name="asset" property="idWithPadding"/></a><br /> --%>
				</div>
				
				<div class="asset-name">
             		<h3><a href="viewAsset?id=<bean:write name='asset' property='id'/>"><bean:write name='asset' property='name'/></a></h3>
				</div>
				
				
				<div class="asset-details">
									
				<c:forEach var="price" items="${userprofile.assetBox.assetPrices[asset.id]}">
					<div id="asset-item-details">
					<span class="asset-format">
					<c:out value="${price.priceBand.name}" />
					<c:if test="${price.priceBand.priceBandType.id == 2}">(x <c:out value="${price.quantity}" />)</c:if>
					</span>	
						
						
					<span class="asset-price">	
						<c:choose>
							<c:when test="${price.priceBand.priceBandType.id == 1 && price.priceBand.isCommercial}">
								tbc
							</c:when>
							<c:otherwise>
								<bright:writeMoney name="price" property="assetPrice.displayAmount" />		
							</c:otherwise>
						</c:choose>
					</span>	
					<br />
					</div>
				</c:forEach>
									
				</div>		
						
										
					
				<p class="action">
					<a class="view" href="viewAsset?id=<bean:write name='asset' property='id'/>&showadd=true">Change</a>
					<a class="remove" href="../action/removeFromAssetBox?id=<bean:write name='asset' property='id'/>&amp;forward=/action/viewAssetBox"><bright:cmsWrite identifier="link-remove" filter="false" /></a>
				</p>
			</li>
		
