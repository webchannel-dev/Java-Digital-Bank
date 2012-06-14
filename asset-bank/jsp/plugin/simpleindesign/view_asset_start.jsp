<%-- Asset view links for Simple InDesign plugin. --%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<c:if test="${simpleindesign_assetHasInddExtension}">
	<li class="link">
		<a href="../action/sInDViewLinkedAssets?assetId=<bean:write name='assetForm' property='asset.id'/>"><bright:cmsWrite identifier="link-view-linked-assets" /></a>
	</li>
</c:if>
<c:if test="${simpleindesign_assetIsReferencedByInddAssets}">
	<li class="link">
		<a href="../action/sInDViewReferencingDocs?assetId=<bean:write name='assetForm' property='asset.id'/>"><bright:cmsWrite identifier="link-view-linking-assets" /></a>
	</li>
</c:if>