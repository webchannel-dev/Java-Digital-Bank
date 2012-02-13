<%--
	Displays the attribute field values and plugin extension data for an asset.

	Include this JSP if you are doing a standard view of a single asset.
	If you are viewing multiple assets per page or doing something else
	non-standard then include inc_attribute_fields.jsp instead so that
	generic plugin modifications are not applied.
--%>
<%@ taglib uri="http://www.assetbank.co.uk/taglib/abplugin" prefix="abplugin" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<abplugin:include-view-extensions extensibleEntity="asset" position="dataStart"/>

<%@include file="inc_attribute_fields.jsp"%>