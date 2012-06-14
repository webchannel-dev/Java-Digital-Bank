<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<logic:present name="editorPrepException">
<p>
	<bright:cmsWrite identifier="snippet-error-occurred"/> <bean:write name="editorPrepException" property="message"/>
</p>
</logic:present>
<logic:notPresent name="editorPrepException">

<h3 class="js-enabled-show progressLoader"><img src="../images/standard/misc/ajax_loader.gif" width="24" height="24" alt="Ajax Loader" /><bright:cmsWrite identifier="title-preparing-file-wait" filter="false"/></h3>


<p>
	<bright:cmsWrite identifier="snippet-indesign-data-intro"/>
</p>

<script type="text/javascript" charset="utf-8">
	function clickNext(){
	    $j('#btnNext').click();
	}
	setInterval( clickNext, 3000 );		// Click the next button every 2 seconds
</script>


</logic:notPresent>