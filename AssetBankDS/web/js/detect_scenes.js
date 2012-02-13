/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


function displyDSMessage(msg){
    var headPanel = document.getElementById("errorDs");
    
    if(headPanel !=null ){
            while(headPanel.childNodes.length >= 1) {
                headPanel.removeChild(headPanel.firstChild);
            }
            headPanel.appendChild(headPanel.ownerDocument.createTextNode(msg));
           headPanel.style.display="block";
    }
   
}

function callAsynchronusly(url){
    var file = document.detectSceneForm.file.value;
    var id = document.detectSceneForm.id.value;
    var fileLocation = document.detectSceneForm.fileLocation.value;
    var qstring = "?file="+file+"&id="+id+"&fileLocation="+fileLocation;
    //alert(qstring);
    displyDSMessage("The video is being processed for scene detection");
  if (window.XMLHttpRequest)
  {// code for IE7+, Firefox, Chrome, Opera, Safari
  xmlhttp=new XMLHttpRequest();
  }
else
  {// code for IE6, IE5
    xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  }
    
    xmlhttp.onreadystatechange = 
            function () { 
            //alert(xmlhttp.readyState);
                 if (xmlhttp.readyState == 4){
                     var msg="";
                     var result= xmlhttp.responseText;
                     //alert(xmlhttp.responseText); 
                     if(result.indexOf("processed")!=-1)
                        msg = "The video is already processed";
                     else if(result.indexOf("success")!=-1)
                        msg = "The scene detection is completed";
                     else if(result.indexOf("error")!=-1)
                        msg = "An error occured";
                     else
                         msg ="...";
                     displyDSMessage(msg);
                     //setTimeout(hideMessageBar(),5000);
                 } 
                     
            };
    xmlhttp.open("GET",url+qstring,true);
    xmlhttp.send();
    //xmlDoc=xmlhttp.responseXML; 
   // alert(xmlDoc);
    return false;
}

function hideMessageBar(){
    var headPanel = document.getElementById("errorDs");
    headPanel.style.display="none";
}