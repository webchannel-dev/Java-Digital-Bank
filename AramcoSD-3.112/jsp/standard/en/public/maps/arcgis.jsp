<%@include file="../../inc/doctype_html.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	 d1		Steve Bryan		21-Mar-2011		Created.
--%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bean:define id="section" value="search"/>

<%-- Check if this is view only mode --%>
<c:set var="viewOnly" value="${!empty param.view && param.view == 'true'}" />


<head>
	<title><bright:cmsWrite identifier="title-map-popup" filter="false"/></title> 
	<%@include file="../../inc/head-elements.jsp"%>
	
	<link rel="stylesheet" type="text/css" href="http://serverapi.arcgisonline.com/jsapi/arcgis/2.1/js/dojo/dijit/themes/tundra/tundra.css">
	<script type="text/javascript" src="http://serverapi.arcgisonline.com/jsapi/arcgis/?v=2.1"></script>
	<script type="text/javascript">
	  	dojo.require("esri.map");
		dojo.require("esri.toolbars.draw");
		
		
		// TO DO -----
		// - Set up initial extents based on stored coordinates.
		
		
		// Cache the coordinates from the parent window:
		var ymin = window.opener.sLat;
		var ymax = window.opener.nLat;
		var xmin = window.opener.wLng;
		var xmax = window.opener.eLng;	
		
		// How many decimal places should the returned data have:
		var decimalPlaces = <c:out value="${mapForm.maxDecimals}" />;
		
		var map;
		var mySpatial = new esri.SpatialReference({wkid:4326}); // set spatial reference to the same one google maps uses
		
		var defaultExtent;
		
		if (ymin != "" && xmin != "" && ymax != "" && xmax != "") {
			// change default extent to match stored values
			defaultExtent  =  new esri.geometry.Extent(+xmin, +ymin, +xmax, +ymax, mySpatial);
		} else {
			// use a default zoomed out view
			defaultExtent = new esri.geometry.Extent(-4.88989442318342, 50.38306360154683, 1.7019025747771, 54.77759493352051, mySpatial);
		}
		
		function init() {
			map = new esri.Map("map_canvas");
			map.setExtent(defaultExtent, true);
			dojo.connect(map, "onLoad", initGraphics);
			//get OS map service
			var basemapURL= "http://lasigpublic.nerc-lancaster.ac.uk/ArcGIS/rest/services/EIDCP2/OSColourRastersTiledCached/MapServer"
			var basemap = new esri.layers.ArcGISTiledMapServiceLayer(basemapURL);
			map.addLayer(basemap);
			
			//var zoomLevel = map.getLevel() +1;
			//map.setLevel(zoomLevel);

		}
		
		function initGraphics(map) {
			//add graphics if coordinates already defined
			//check for first point:
			if (ymin != "" && xmin != "") {
				// Have coordinates for at least one point.
				// Check for second point
				if (ymax != "" && xmax != "") {
					// We have 2 sets of coordinates so can create an extent/area on the map.
					// - draw passed in extent on map
					addExtentToMap(xmin, ymin, xmax, ymax);
					
				} else {
					// only one point
					// - draw point on map
					addPointToMap(window.opener.wLng,window.opener.sLat);
				}
			}
			tb = new esri.toolbars.Draw(map);
			dojo.connect(tb, "onDrawEnd", addGraphic);
		}
		
		function addGraphic(geometry) {
			map.graphics.clear();
			var symbol;
			var type = geometry.type;
			if (type === "point") {
				symbol = tb.markerSymbol;
				xmin = geometry.x;
				ymin = geometry.y;
				xmax = "";
				ymax = "";
				addPointToMap(xmin,ymin);
			}
			else {
				symbol = tb.fillSymbol;
				xmin = geometry.xmin;
				ymin = geometry.ymin;
				xmax = geometry.xmax;
				ymax = geometry.ymax;
				addExtentToMap(xmin, ymin, xmax, ymax);
			}
		}		
		
		function addPointToMap(lon, lat) {
			
			map.graphics.add(
	          	new esri.Graphic(
	            	new esri.geometry.Point(lon, lat, mySpatial),
	            	new esri.symbol.SimpleMarkerSymbol().setColor(new dojo.Color([255,0,0,0.5]))
	          	)
	        )
	    }
	
		function addExtentToMap(xmin, ymin, xmax, ymax) {
			var myExtent = new esri.geometry.Extent(xmin, ymin, xmax, ymax, mySpatial);
			map.graphics.add(
	          	new esri.Graphic(
	            	myExtent,
	            	new esri.symbol.SimpleFillSymbol()
	          	)
	        )
			showExtent(myExtent);
	    }

		function showExtent(extent) {
			var s = "";
			s = "XMin: "+ extent.xmin + ", "
				+"YMin: " + extent.ymin + ", "
				+"XMax: " + extent.xmax + ", "
				+"YMax: " + extent.ymax;
			dojo.byId("coordInfo").innerHTML = s;
		}

		
		function closeMap(){
			
			// Trim values to correct number of decimal places (convert any strings to numbers first)
			ymin = +ymin;
			xmin = +xmin;
			ymin = ymin.toFixed(decimalPlaces);
			xmin = xmin.toFixed(decimalPlaces);
			if (xmax != "" && xmax != "") {
				ymax = +ymax;
				xmax = +xmax;
				ymax = ymax.toFixed(decimalPlaces);
				xmax = xmax.toFixed(decimalPlaces);	
			} 
			
			// Update global variables:
			window.opener.wLng = xmin;
			window.opener.sLat = ymin;
			window.opener.eLng = xmax;
			window.opener.nLat = ymax;
			// Trigger funtion in parent window
			window.opener.setSpatialCoords(window.opener.$whichAttribute);
			
			// Close the popup:
			window.close();
		}
		
		function deactivateOn() {
			$j('a#deactivate').addClass('on');
		}
		
		
		
	  	dojo.addOnLoad(init);
	</script>

	
</head>

<body class="tundra" id="popup">
	
	<div id="wrapper">
		
		<%@include file="inc_map_tabs.jsp"%>
		
		<div id="tabContent" class="noBottomMargin">
			<c:if test="${!viewOnly}">

				<c:if test="${!mapForm.singlePointOnly}" >
					<input type="button" onclick="tb.activate(esri.toolbars.Draw.POINT); deactivateOn();" value="Draw a point" class="button flush"/>
				</c:if>
				&nbsp;OR&nbsp;
				<input type="button" onclick="tb.activate(esri.toolbars.Draw.EXTENT); deactivateOn();" value="Draw an area" class="button flush"/>

		    	<a href="" onclick="tb.deactivate(); $j(this).removeClass('on'); return false;" id="deactivate">Stop drawing</a>	
			</c:if>
  			<div id="map_canvas"></div>
			
			<div id="coordInfo"></div>

			<c:choose>
				<c:when test="${viewOnly}">
					<input type="button" value="<bright:cmsWrite identifier="button-close" filter="false"/>"  onclick="window.close()" class="button  flush"/>
				</c:when>
				<c:otherwise>
					<input type="button" value="<bright:cmsWrite identifier="button-apply-and-close" filter="false"/>" onclick="closeMap();" class="button floated flush"/>
					<a href="#" onclick="window.close()" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false"/></a>
				</c:otherwise>
			</c:choose>
			
			<div class="clearing"></div>
		</div>	
	<div>
	
</body>

</html>