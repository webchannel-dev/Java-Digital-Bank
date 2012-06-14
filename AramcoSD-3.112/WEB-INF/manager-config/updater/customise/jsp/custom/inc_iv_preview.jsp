<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>

<p>
	<object id="photint" data="data:application/x-oleobject;base64,4JZ1e1MXl0KBRq8GDRrrehAHAAADPgAAPyAAAA==" classid="CLSID:7B7596E0-1753-4297-8146-AF060D1AEB7A" width="800" height="600" codebase="http://www.photint.com/iv/activex/photint.cab#version=1,0,0,4">
	[Object not available! Did you install the ActiveX server?]</object>
</p>

<script>
	function init()
	{
		if(document.URL.match('http[s]?://[^/]+/.*'))
		{
			var parts = document.URL.split('/');
			photint.Open(parts[0] + '//' + parts[2] + '<c:out value="${fileUrl}"/>');
		}
	}
	document.body.onload=init;
</script>