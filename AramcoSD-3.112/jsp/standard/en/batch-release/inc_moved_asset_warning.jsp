<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bean:parameter name="movedBr" id="movedBr" value="false" />
<c:if test="${movedBr}">
	<div class="warning"><bright:cmsWrite identifier="snippet-changed-batch-release-warning" filter="false" /></div>
</c:if>