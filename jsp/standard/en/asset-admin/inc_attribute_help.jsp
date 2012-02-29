<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<logic:notEmpty name="helpText">
	<img src="../images/standard/icon/help_inline.gif" width="15" height="15" alt="<bean:write name='helpText' filter='false'/>" title="<bean:write name='helpText' filter='false'/>" class="hoverHelp" />  
</logic:notEmpty>