// AJAX Framework Version 2.0
// Copyright 2005 Jason Graves (GodLikeMouse)
// This file is free to use and distribute under the GNU open source
// license so long as this header remains intact.
// For more information please visit http://www.godlikemouse.com
// To have changes incorporated into the AJAX Framework please contact godlikemouse@godlikemouse.com
// Supported Browsers: IE 6.0+, Opera 8+, Mozilla based browsers 

var GLM = {
    DOM:        Object,
    AJAX:       Object,
    Collection: Object
};

/************************************/
/* GLM.DOM                          */
/************************************/

/// <name>GLM.DOM.isInternetExplorer</name>
/// <summary>Boolean variable for determining if the client browser is Internet Explorer.</summary>
/// <returns>True if the client browser is Internet Explorer, otherwise false</returns>
/// <example>if(GLM.DOM.isInternetExplorer){
///     alert("You're using IE");
/// }</example>
GLM.DOM.isInternetExplorer = (navigator.userAgent.indexOf("MSIE") >= 0);

/// <name>GLM.DOM.isMozilla</name>
/// <summary>Boolean variable for determining if the client browser is Mozilla.</summary>
/// <returns>True if the client browser is Mozilla, otherwise false</returns>
/// <example>if(GLM.DOM.isMozilla){
///     alert("You're using Mozilla");
/// }</example>
GLM.DOM.isMozilla = (navigator.userAgent.indexOf("Gecko") >= 0);

/// <name>GLM.DOM.isOpera</name>
/// <summary>Boolean variable for determining if the client browser is Opera.</summary>
/// <returns>True if the client browser is Opera, otherwise false</returns>
/// <example>if(GLM.DOM.isOpera){
///     alert("You're using Opera");
/// }</example>
GLM.DOM.isOpera = (navigator.userAgent.indexOf("Opera") >= 0);


/************************************/
/* GLM.Collection                   */
/************************************/

/// <name>GLM.Collection.Map</name>
/// <summary>Map class for holding key value pairs.</summary>
/// <returns>The new GLM.Collection.Map object.</returns>
/// <example>var m = new GLM.Collection.Map();
/// m.add("name1","value1");
/// m.add("name2","value2");
/// alert( m.get("name1") ); //alerts "value1"</example>
GLM.Collection.Map = function(){
    var len = 0;
    var keys = new Array();
    var values = new Array();

    /// <name type="function">GLM.Collection.Map.get</name>
    /// <summary>Method for returning the value associated with a key.</summary>
    /// <param name="key">The key to return the associated value of.</param>
    /// <returns>The value associated with the key.</returns>
    /// <example>var m = new GLM.Collection.Map();
    /// m.add("name1","value1");
    /// m.add("name2","value2");
    /// alert( m.get("name1") ); //alerts "value1"</example>
    this.get = function(key){
        var val = null;
        for(var i=0; i<len; i++){
            if(keys[i] == key){
                val = values[i];
                break;
            }//end if
        }//end for

        return val;
    }//end get()

    /// <name type="function">GLM.Collection.Map.put</name>
    /// <summary>Method for storing an associated key value.</summary>
    /// <param name="key">The key to associate with the value.</param>
    /// <param name="value">The value associated with the key.</param>
    /// <example>var m = new GLM.Collection.Map();
    /// m.add("name1","value1");
    /// m.add("name2","value2");
    /// alert( m.get("name1") ); //alerts "value1"</example>
    this.put = function(key, value){
        keys[len] = key;
        values[len++] = value;
    }//end put()

    /// <name type="function">GLM.Collection.Map.length</name>
    /// <summary>Method for returning the count of items in the map.</summary>
    /// <returns>The count of items in the map.</returns>
    /// <example>var m = new GLM.Collection.Map();
    /// m.add("name1","value1");
    /// m.add("name2","value2");
    /// alert( m.length ); //alerts 2</example>
    this.length = function(){
        return len;
    }//end length()

    /// <name type="function">GLM.Collection.Map.contains</name>
    /// <summary>Method for determining if the map contains a specific key.</summary>
    /// <param name="key">The key to search for.</param>
    /// <returns>True if the map contains the key, otherwise false.</returns>
    /// <example>var m = new GLM.Collection.Map();
    /// m.add("name1","value1");
    /// m.add("name2","value2");
    /// alert( m.contains("name1") ); //alerts true</example>
    this.contains = function(key){
	var con = false;
        for(var i=0; i<len; i++){
            if(keys[i] == key){
                con = true;
                break;
            }//end if
        }//end for

	return con;
    }//end contains()

    /// <name type="function">GLM.Collection.Map.remove</name>
    /// <summary>Method for removing a key value pair by key name.</summary>
    /// <param name="key">The key to search for.</param>
    /// <example>var m = new GLM.Collection.Map();
    /// m.add("name1","value1");
    /// m.add("name2","value2");
    /// m.remove("name1");
    /// alert( m.contains("name1") ); //alerts false</example>
    this.remove = function(key){
        var keyArr = new Array();
        var valArr = new Array();
        var l = 0;
        for(var i=0; i<len; i++){
            if(keys[i] != key){
                keyArr[l] = keys[i];
                valArr[l++] = values[i];
            }//end if
        }//end for

        keys = keyArr;
        values = valArr;
	len = l;
    }//end remove()        
    
}//end GLM.Collection.Map

/************************************/
/* GLM.AJAX                         */
/************************************/

/// <name>GLM.AJAX</name>
/// <summary>Class for performing AJAX.<br/>
/// Developer Note: .NET WebService Methods must be tagged with [SoapRpcMethod, WebMethod] for parameters to be passed using Mozilla based browsers.</summary>
/// <returns>The new GLM.AJAX object.</returns>
/// <example>var ajax = new GLM.AJAX();
/// 
/// function ajaxCallback(content){
///     alert(content); //displays the contents of the page.
/// }
///
/// ajax.callPage("myPage.html", ajaxCallback); //call myPage.html and pass the contents to ajaxCallback.</example>
GLM.AJAX = function(){

    var nameSpace = "http://tempuri.org/";
    var map = new GLM.Collection.Map();
    
    //private method for returning an ajax enabled
    //object specific to a browser
    var ajaxObject = function(){
        try{return new XMLHttpRequest();}catch(ex){};
        try{return new ActiveXObject("Microsoft.XMLHTTP");}catch(ex){};
        try{return new SOAPCall();}catch(ex){};
    }//end ajaxObject()
    
    /// <name type="function">GLM.AJAX.onError</name>
    /// <summary>Method for handling ajax errors.</summary>
    /// <example>var ajax = new GLM.AJAX();
    /// 
    /// function ajaxCallback(content){
    ///         alert(content); //displays the contents of the page
    /// }
    ///
    /// function ajaxError(error){
    ///         alert(error); //alert the error
    /// }
    ///
    /// ajax.onErrror = ajaxError; //assign error handler
    /// ajax.callPage("myPage.html", ajaxCallback); //call myPage.html and pass the contents to ajaxCallback</example>
    this.onError = function(error){
        alert(error);
    }//end onError()

    /// <name type="function">GLM.AJAX.callPage</name>
    /// <summary>Method for calling a page using AJAX.</summary>
    /// <param name="url">The url of the page to call.</param>
    /// <param name="callbackFunction">The function to call when the AJAX call succeeds.</param>
    /// <example>var ajax = new GLM.AJAX();
    /// 
    /// function ajaxCallback(content){
    ///         alert(content); //displays the contents of the page.
    /// }
    ///
    /// ajax.callPage("myPage.html", ajaxCallback); //call myPage.html and pass the contents to ajaxCallback.</example>
    this.callPage = function(url, callbackFunction){        
        try{
            var ao = ajaxObject();
            ao.onreadystatechange = function(){
                if(ao.readyState==4 || ao.readyState=="complete"){
                    callbackFunction(ao.responseText);
                }
            };
            
            ao.open("GET", url, true);
            ao.send(null);
        }
        catch(ex){
            this.onError(ex);
        }//end tc
    }//end callPage()
	

    /// <name type="function">GLM.AJAX.callService</name>
    /// <summary>Method for calling a webservice using AJAX.</summary>
    /// <param name="serviceUrl">The url of the service to call.</param>
    /// <param name="soapMethod">The method of the service to call.</param>
    /// <param name="callbackFunction">The function to call when the AJAX call succeeds.</param>
    /// <param name="param1, param2 ... paramN">Optional parameters passed to the webservice.  Parameters must be in the 
    /// form of "name=value".</param>
    /// <example>var ajax = new GLM.AJAX();
    /// 
    /// function ajaxCallback(content){
    ///         alert(content); //displays the contents of the page.
    /// }
    ///
    /// ajax.callPage("myService.asmx", "myMethod", ajaxCallback); //call myService.asmx and pass the contents to ajaxCallback.</example>
    this.callService = function(serviceUrl, soapMethod, callbackFunction /*, unlimited params */){
        
        var callServiceError = this.onError;
        
        var ao = ajaxObject();
        
        if(GLM.DOM.isInternetExplorer){
            if(serviceUrl.indexOf("http://") < 0)
                serviceUrl = "http://" + serviceUrl;
            serviceUrl += "?WSDL";
            
            var soapEnvelope = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
            soapEnvelope += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
            soapEnvelope += "<soap:Body>";
            soapEnvelope += "<" + soapMethod + " xmlns=\"" + nameSpace + "\">";
            
            if(arguments.length > 3){
                for (var i = 3; i < arguments.length; i++)
                {
                    var params = arguments[i].split("=");
                    soapEnvelope += "<" + params[0] + ">";
                    soapEnvelope += params[1];
                    soapEnvelope += "</" + params[0] + ">";
                }//end for
            }//end if
			
            soapEnvelope += "</" + soapMethod + ">";
            soapEnvelope += "</soap:Body>";
            soapEnvelope += "</soap:Envelope>";
			
            
            
            ao.onreadystatechange = function(){
                
                if(ao.readyState == 4){
                    
                    if(GLM.DOM.IsOpera){
                        //opera
                        var response = ao.responseXML.getElementsByTagName(soapMethod + "Result")[0];
                        if(!response)
                            response = ao.responseXML.getElementsByTagName(soapMethod + "Response")[0];
                        if(!response){
                            callServiceError("WebService does not contain a Result/Response node");
                            return;
                        }//end if
                        
                        ao.callbackFunction(ao.responseXML.getElementsByTagName(soapMethod + "Result")[0].innerHTML);
                    }
                    else if(GLM.DOM.isInternetExplorer){
                        //IE
                        var responseXml = new ActiveXObject('Microsoft.XMLDOM');
                        responseXml.loadXML(ao.responseText);
                            
                        var responseNode = responseXml.selectSingleNode("//" + soapMethod + "Response");
                        if(!responseNode)
                            responseNode = responseXml.selectSingleNode("//" + soapMethod + "Result");
                        if(!responseNode)
                            callServiceError("Response/Result node not found.\n\nResponse:\n" + ao.responseText);
                            
                        var resultNode = responseNode.firstChild;
                        if (resultNode != null){
                            try{
                                callbackFunction(resultNode.text);
                            }
                            catch(ex){
                                callServiceError(ex);
                            }//end tc
                        }
                        else{
                            try{
                                callbackFunction();
                            }
                            catch(ex){
                                callServiceError(ex);
                            }//end tc
                        }//end if
                    }//end if
                }//end if
            };
            
            ao.open("POST", serviceUrl, true);			
            ao.setRequestHeader("Content-Type", "text/xml");
            ao.setRequestHeader("SOAPAction", nameSpace + soapMethod);
            try{
                ao.send(soapEnvelope);
            }
            catch(ex){
                serviceCallError(ex);
            }//end tc
        }
        else{
            var soapParams = new Array();
            var headers = new Array();
            var soapVersion = 0;
            var object = nameSpace;
            
            if(serviceUrl.indexOf("http://") < 0)
                serviceUrl = document.location + serviceUrl;
            
            ao.transportURI = serviceUrl;
            ao.actionURI = nameSpace + soapMethod;
            
            for(var i=3; i<arguments.length; i++){
                var params = arguments[i].split("=");
                soapParams.push( new SOAPParameter(params[1],params[0]) );
            }//end for
            
            try{
                ao.encode(soapVersion, soapMethod, object, headers.length, headers, soapParams.length, soapParams);
            }
            catch(ex){
                serviceCallError(ex);
            }//end tc
		
            try{
                netscape.security.PrivilegeManager.enablePrivilege("UniversalBrowserRead");
            } 
            catch(ex){
                return false;
            }//end tc
            
            try{
                ao.asyncInvoke(
                    function(resp,call,status){

                    if(resp.fault)
                        return callServiceError(resp.fault);
                    if(!resp.body){
                        callServiceError("Service " + call.transportURI + " not found.");
                    }
                    else{
                        try{
                            callbackFunction(resp.body.firstChild.firstChild.firstChild.data);
                        }
                        catch(ex){
                            callServiceError(ex);
                        }//end tc
                    }//end if
                }
                );
            }
            catch(ex){
                serviceCallError(ex);
            }//end tc
                        
        }//end if
		
    }//end callService()
	
    /// <name type="function">GLM.AJAX.setNameSpace</name>
    /// <summary>Method for setting the name space of the service call.</summary>
    /// <param name="ns">The service name space.</param>
    /// <example>var ajax = new GLM.AJAX();
    /// 
    /// function ajaxCallback(content){
    /// alert(content); //displays the contents of the page.
    /// }
    ///
    /// ajax.setNameSpace("mynamespace");
    /// ajax.callPage("myService.asmx", "myMethod", ajaxCallback); //call myService.asmx and pass the contents to ajaxCallback</example>
    this.setNameSpace = function(ns){
        nameSpace = ns;
    }//end setNameSpace()
	
    /// <name type="function">GLM.AJAX.getNameSpace</name>
    /// <summary>Method for returning the name space of the service call.</summary>
    /// <returns>The namespace of the service call.</returns>
    /// <example>var ajax = new GLM.AJAX();
    /// 
    /// function ajaxCallback(content){
    /// alert(content); //displays the contents of the page.
    /// }
    ///
    /// var ns = ajax.getNameSpace(); //returns the name space
    /// ajax.callPage("myService.asmx", ajaxCallback); //call myService.asmx and pass the contents to ajaxCallback</example>
    this.getNameSpace = function(){
        return ns;
    }//end getNameSpace()
	
}//end GLM.AJAX()

