<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="googleAnalyticsId" settingName="google-analytics-id"/>

   <!-- Start of footer -->

	

   <div id="footer">      

		
		<%@include file="../customisation/footer_copy.jsp"%>

   </div>   <!-- End of footer -->

<%-- Insert Google Analytics --%>
<c:if test="${googleAnalyticsId!=''}">
	<script type="text/javascript">
		var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
		document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
	</script>
	<script type="text/javascript">
		try {
		var pageTracker = _gat._getTracker("<c:out value='${googleAnalyticsId}' />");
		pageTracker._trackPageview();
		} catch(err) {}
	</script>
</c:if>