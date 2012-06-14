<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="http://jakarta.apache.org/taglibs/unstandard-1.0" prefix="un" %>

<%--  Show Regenerate All PDFs button message when batch release is NOT currently being validated (pending) --%>
<c:if test="${userprofile.canManageBatchReleases && not batchReleaseForm.pending}">
	<div class="group first" style="float:right">
		<a href="inDesignRegenerateAllPDFsInBatchRelease?brId=<bean:write name='batchReleaseForm' property='id'/>" class="linkButton"><bright:cmsWrite identifier="button-regenerate-all-pdfs-in-batch-release"/></a>
	</div>
</c:if>
