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




<head>
	<title><bright:cmsWrite identifier="title-map-popup" filter="false"/></title> 
	<%@include file="../../inc/head-elements.jsp"%>
	
	<%@include file="../../customisation/googlemaps_api_key.jsp"%>



	<script type="text/javascript">
		// setup global variables
		var map;
		var marker=[];
		var polygon;
		var sLatLocal = window.opener.sLat;
		var wLngLocal = window.opener.wLng;
		var nLatLocal = "";
		var eLngLocal = "";
		
		if (typeof window.opener.nLat != 'undefined') {
			nLatLocal = window.opener.nLat;
		}
		if (typeof window.opener.eLng != 'undefined') {
			eLngLocal = window.opener.eLng;
		}
		
		// How many decimal places should the returned data have:
		var decimalPlaces = <c:out value="${mapForm.maxDecimals}" />;
 
		function initialize() {
			//set new search control style
			if (GBrowserIsCompatible()) {
				var mapOptions = {
			  	googleBarOptions : {
				 	style : "new"
			  	}
				}  
			}
			// set default map center and zoom level
			var mapCenter = new GLatLng(51.825141,-1.395264);
			var mapZoom = 7;
			
			
			// create map and configure controls
			map = new GMap2(document.getElementById("map_canvas"),mapOptions);
			map.addControl(new GLargeMapControl);
			map.addControl(new GMapTypeControl());
			map.enableGoogleBar();
			map.enableScrollWheelZoom();
			
			
			//add markers if coordinates already defined
			//check for first point:
			if (sLatLocal != "" && wLngLocal != "") {
				// Have coordinates for at least one point.
				var point = new GLatLng(sLatLocal,wLngLocal);
				mapCenter = point;
				handleMarkers(null,point);
				//check for second point
				if (nLatLocal != "" && eLngLocal != "") {
					// Have coordinates for 2 different points
					var point2 = new GLatLng(nLatLocal,eLngLocal);
					handleMarkers(null,point2);
					//define a rectangular boundary
					var latlngbounds = new GLatLngBounds(point,point2);
					// get center point and best fit zoom level
					mapCenter = latlngbounds.getCenter( );
					mapZoom = map.getBoundsZoomLevel(latlngbounds) - 1;
				}
			}
			
			map.setCenter(mapCenter, mapZoom);
			
			// listen for clicks
			GEvent.addListener(map, "click", handleMarkers);
		}
		

		function handleMarkers(overlay, latlng) {
			// get number of markers already set up
			var n = marker.length;
      		var maxMarkers = 2;

			<c:if test="${mapForm.singlePointOnly}" >
				maxMarkers = 1
			</c:if>
			
			// if max num markers already set, reset and start again
			if (n==maxMarkers) {
				clearMap();
				n=0;
			}
      	
			// if less than max markers created, create another marker:
			if (n < maxMarkers ) {
				marker[n] = new GMarker(latlng,{draggable: true});
				map.addOverlay(marker[n]);
				GEvent.addListener(marker[n], "drag", function() {
					// if both markers are defined, redraw box
					if (marker.length == 2) {
						draw();
					}
				});
				GEvent.addListener(marker[n], "dragend", function() {
					// if both markers are defined, redraw box
					if (marker.length == 2) {
						draw();
					}
				});           
			}
      	
			// if the first marker has been added output coords
			if (marker.length==1){
				outputCoordinates(marker[0].getLatLng());
			}
      	
			// if a second marker has been added draw a polygon
			if (marker.length==2){
				draw();
			}
  
		}
      
		function draw() {
			//If there is already a polygon drawn, clear it first
			if (typeof polygon != 'undefined') {
				map.removeOverlay(polygon);
			}	
			//get marker coords
			var point1 = marker[0].getLatLng();
			var point2 = marker[1].getLatLng();    
			// set up polygon
			polygon = new GPolygon([
				new GLatLng(point1.lat(), point1.lng()),
				new GLatLng(point1.lat(), point2.lng()),
				new GLatLng(point2.lat(), point2.lng()),
				new GLatLng(point2.lat(), point1.lng()),
				new GLatLng(point1.lat(), point1.lng())
			], "#f33f00", 3, 1, "#ff0000", 0.2);

			//draw polygon
			map.addOverlay(polygon);
			//update text boxes
			outputCoordinates(point1,point2);
		}

		function outputCoordinates(point1,point2){
			
			if (typeof point2 != 'undefined') {
				// area
				// Work out bounding box
				if (point1.lng() > point2.lng()){
					wLngLocal = point2.lng();
					eLngLocal = point1.lng();
				} else {
					wLngLocal = point1.lng();
					eLngLocal = point2.lng();
				}

				if (point1.lat() < point2.lat()){
					sLatLocal = point1.lat();
					nLatLocal = point2.lat();
				} else {
					sLatLocal = point2.lat();
					nLatLocal = point1.lat();
				}
				var s = "";
				s = "XMin: "+ wLngLocal + ", "
					+"YMin: " + sLatLocal + ", "
					+"XMax: " + eLngLocal + ", "
					+"YMax: " + nLatLocal;
				document.getElementById("coordInfo").innerHTML = s;
			} else {
				// point
				wLngLocal = point1.lng();
				sLatLocal = point1.lat();
				
				// update input boxes
				var s = "";
				s = "long: "+ wLngLocal + ", "
					+"lat: " + sLatLocal 
				document.getElementById("coordInfo").innerHTML = s;
			}
			
		}     
	
		function clearMap(){
			map.clearOverlays();
			//empty marker array
			marker = [];
		}
		
		function closeMap(){
			
			// Trim values to correct number of decimal places:
			sLatLocal = sLatLocal.toFixed(decimalPlaces);
			wLngLocal = wLngLocal.toFixed(decimalPlaces);
			if (nLatLocal != "" && eLngLocal != "") {
				nLatLocal = nLatLocal.toFixed(decimalPlaces);
				eLngLocal = eLngLocal.toFixed(decimalPlaces);	
			} 
			// If user has only defined a single point, empty the second set of coords:
			if (marker.length == 1) {
				nLatLocal = "";
				eLngLocal = "";
			}
			
			// Update global variables:
			window.opener.sLat = sLatLocal;
			window.opener.wLng = wLngLocal;
			window.opener.nLat = nLatLocal;
			window.opener.eLng = eLngLocal;
			
			// Trigger funtion in parent window:
			window.opener.setSpatialCoords(window.opener.$whichAttribute);
			
			// Close the popup:
			window.close();
		}
      
	</script>

	
</head>

<body onload="initialize()" onunload="GUnload();" id="popup">

	<div id="wrapper">
		
		<%@include file="inc_map_tabs.jsp"%>
		<div id="tabContent" class="noBottomMargin">
			
			<c:choose>
				<c:when test="${mapForm.singlePointOnly}" >
					<p><bright:cmsWrite identifier="snippet-click-map-define-point" filter="false"/></p>
				</c:when>
				<c:otherwise>
					<p><bright:cmsWrite identifier="snippet-click-once-point-twice-area" filter="false"/></p>
				</c:otherwise>	
			</c:choose>
			<div id="map_canvas" style=""></div>
			<div id="coordInfo"></div>
			<input type="button" value="<bright:cmsWrite identifier="button-apply-and-close" filter="false"/>" onclick="closeMap();" class="button floated flush"/>
			<a href="#" onclick="window.close()" class="cancelLink"><bright:cmsWrite identifier="button-cancel" filter="false"/></a>
			
			<div class="clearing"></div>
			
		</div>	
	<div>
	
</body>

</html>