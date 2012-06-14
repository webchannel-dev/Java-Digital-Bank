<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>

<option value="0"><bright:cmsWrite identifier="snippet-no-rotation" filter="false"/></option>
<option value="90"><bright:cmsWrite identifier="snippet-90-deg-clockwise" filter="false"/></option>
<option value="180"><bright:cmsWrite identifier="snippet-180-deg" filter="false"/></option>
<option value="270"><bright:cmsWrite identifier="snippet-90-deg-anticlockwise" filter="false"/></option>