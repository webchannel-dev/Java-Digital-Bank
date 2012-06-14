<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>

<bright:applicationSetting id="databaseSupportsUTF8" settingName="databaseSupportsUTF8"/>

<%-- Set the content type to utf-8 if required. Note, we cannot conditionally do this with the @page directive, so it has to be inline java --%>
<c:if test="${databaseSupportsUTF8}">
	<% response.setContentType("text/html;charset=utf-8"); %>
</c:if>

<!DOCTYPE html>

<html>


<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%-- History:
	d1		Steve Bryan		21-Mar-2011		Created.
	d2		Ben Browning	12-Apr-2011		Rewrote in google maps API v3
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
	

	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"></script>
	

	<script type="text/javascript">
		// setup global variables
		var map;
		var geocoder;
		var markerArray = [];
		
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
			
			geocoder = new google.maps.Geocoder();
			
			// set default map center and zoom level
			var mapCenter = new google.maps.LatLng(51.825141,-1.395264);
			var mapZoom = 7;
			
			var mapOptions = {
				zoom: mapZoom,
			  	center: mapCenter,
			  	mapTypeId: google.maps.MapTypeId.ROADMAP,
				panControl: true,
				zoomControl: true,
				mapTypeControl: true
			}
			
			// create map and configure controls
			map = new google.maps.Map(document.getElementById("map_canvas"),mapOptions);

			
			
			//add markers if coordinates already defined
			//check for first point:
			if (sLatLocal != "" && wLngLocal != "") {
				// Have coordinates for at least one point.
				var point = new google.maps.LatLng(sLatLocal,wLngLocal);
				handleMarkers(point);
				//check for second point
				if (nLatLocal != "" && eLngLocal != "") {
					// Have coordinates for 2 different points
					var point2 = new google.maps.LatLng(nLatLocal,eLngLocal);
					handleMarkers(point2);
					// define a rectabgular boundary
					var latlngbounds = new google.maps.LatLngBounds(point,point2);
					map.fitBounds(latlngbounds)		// polygone exists  - fit map to bounds
				} else {
					map.setCenter(point);	// only a point  - center map on point
				}
			} 
			
			
			map.setZoom(mapZoom);
			
			<c:if test="${!viewOnly}">
				// listen for clicks
				google.maps.event.addListener(map, "click", function(event) {
					handleMarkers(event.latLng);
				});
			</c:if>	

			
			
			// keydown event on text field - handle user hitting enter to submit the form
			$j('#address').keyup(function(event){
				if(event.keyCode == 13){
					$j('#address').blur();	
					searchAddress();
				}
			});
			
		}
		

		function handleMarkers(latLng) {
			
			// get number of markers already set up
			var n = markerArray.length;
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
				marker = new google.maps.Marker({
					position: latLng,
					<c:if test="${!viewOnly}">
						draggable: true,
					</c:if>	
					map: map
				});
				markerArray.push(marker);
				
				
				google.maps.event.addListener(markerArray[n], "drag", function() {
					// if both markers are defined, redraw box
					if (markerArray.length == 2) {
						draw();
					}
				});
				google.maps.event.addListener(markerArray[n], "dragend", function() {
					// if both markers are defined, redraw box
					if (markerArray.length == 2) {
						draw();
					} else {
						// if only one marker update cordinates
						outputCoordinates(marker.getPosition());
					}
				});           
			}
      	
			// if the first marker has been added output coords
			if (markerArray.length==1){
				outputCoordinates(marker.getPosition());
			}
      	
			// if a second marker has been added draw a polygon
			if (markerArray.length==2){
				draw();
			}
  
		}
      
		function draw() {
			//If there is already a polygon drawn, clear it first
			if (typeof polygon != 'undefined') {
				polygon.setMap(null);
			}	
			//get marker coords
			var point1 = markerArray[0].getPosition();
			var point2 = markerArray[1].getPosition();  
			var polygonCoords = [
				new google.maps.LatLng(point1.lat(), point1.lng()),
				new google.maps.LatLng(point1.lat(), point2.lng()),
				new google.maps.LatLng(point2.lat(), point2.lng()),
				new google.maps.LatLng(point2.lat(), point1.lng()),
				new google.maps.LatLng(point1.lat(), point1.lng())
			]
			  
			// set up polygon
			polygon = new google.maps.Polygon({
				paths: polygonCoords,
				strokeColor: "#FF0000",
				strokeOpacity: 0.8,
				strokeWeight: 2,
				fillColor: "#FF0000",
				fillOpacity: 0.35

			});

			//draw polygon
			polygon.setMap(map);
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
				s = "WLng: "+ wLngLocal + ", "
					+"SLat: " + sLatLocal + ", "
					+"ELng: " + eLngLocal + ", "
					+"NLat: " + nLatLocal;
				document.getElementById("coordInfo").innerHTML = s;
			} else {
				// point
				wLngLocal = point1.lng();
				sLatLocal = point1.lat();
				// update input boxes
				var s = "";
				s = "Lng: "+ wLngLocal + ", "
					+"Lat: " + sLatLocal 
				document.getElementById("coordInfo").innerHTML = s;
			}
			
		}     
	
		function clearMap(){
			
			var len=markerArray.length;
			for (var i=0; i<len; i++) {
				markerArray[i].setMap(null);
			}

			if (typeof polygon != 'undefined'){
				polygon.setMap(null);
			}

			//empty marker array
			markerArray = [];
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
			if (markerArray.length == 1) {
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
		
		function searchAddress() {
		    var address = document.getElementById("address").value;
		    geocoder.geocode( { 'address': address}, function(results, status) {
		      if (status == google.maps.GeocoderStatus.OK) {
		        map.setCenter(results[0].geometry.location);
				map.setZoom(11);

		      } else {
		        alert("<bright:cmsWrite identifier="js-google-map-search-fail" filter="false"/> " + status);
		      }
		    });
		}
		
      
	</script>

	
</head>

<body onload="initialize()" id="popup">

	<div id="wrapper">

		<%@include file="inc_map_tabs.jsp"%>
		<div id="tabContent" class="noBottomMargin">
			
			<div class="warning ie6Only"><bright:cmsWrite identifier="snippet-ie6-not-supported" filter="false"/></div>	
			
			<c:if test="${!viewOnly}">
				<div class="mapSearch">
					<input type="text" id="address" />
					<input type="button" value="<bright:cmsWrite identifier="button-search" filter="false"/>" class="button flush" onclick="searchAddress()" />
				</div>	
				<c:choose>
					<c:when test="${mapForm.singlePointOnly}" >
						<p><bright:cmsWrite identifier="snippet-click-map-define-point" filter="false"/></p>
					</c:when>
					<c:otherwise>
						<p><bright:cmsWrite identifier="snippet-click-once-point-twice-area" filter="false"/></p>
					</c:otherwise>	
				</c:choose>
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