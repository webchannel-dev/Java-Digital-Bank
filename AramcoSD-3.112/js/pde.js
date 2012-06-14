/*
	PureDOM explorer 3.0
	written by Christian Heilmann (http://icant.co.uk)
	Please refer to the pde homepage for updates: http://www.onlinetools.org/tools/puredom/
	Free for non-commercial use. Changes welcome, but no distribution without 
	the consent of the author.
*/
pde={
	// CSS classes
	pdeClass:'pde',
	hideClass:'hide',
	showClass:'show',
	parentClass:'parent',
	currentClass:'explcurrent',
	// images added to the parent links
	openImage:'../images/standard/icon/explorer_open.gif',
	closedImage:'../images/standard/icon/explorer_closed.gif',
	folderImage:'../images/standard/icon/explorer_folder.gif',
	openMessage:'close section',
	closedMessage:'open section',
	// boolean to make the parent link collapse the section or not 
	linkParent:false,
	init:function(){
		pde.createClone();
		if(!document.getElementById || !document.createTextNode){return;}
		var uls=document.getElementsByTagName('ul');
		
		for(var i=0;i<uls.length;i++)
		{
			var inneruls,parentLI;
			
			if(!pde.cssjs('check',uls[i],pde.pdeClass))
			{
				continue;
			}
			
			var inneruls = uls[i].getElementsByTagName('ul');
			var listItems = uls[i].getElementsByTagName('li'); 

			//add a folder image if no inner uls...
			for(var j=0;j<listItems.length;j++)
			{
				if (listItems[j].className == 'empty')
				{
					listItems[j].getElementsByTagName('a')[0].insertBefore(document.createElement('img'), listItems[j].getElementsByTagName('p')[0]);
					var img=listItems[j].getElementsByTagName('a')[0].getElementsByTagName('img')[0];
					img.setAttribute('src',pde.folderImage);
					img.setAttribute('alt','');
					img.setAttribute('title','');
					img.setAttribute('class','folder');
				}
			}
			
			for(var j=0;j<inneruls.length;j++)
			{
				parentLI=inneruls[j].parentNode;
				
				if(parentLI.getElementsByTagName('strong')[0])
				{
					pde.cssjs('add',parentLI,pde.currentClass);
					continue;
				}
				
				pde.cssjs('add',parentLI,pde.parentClass);
				parentLI.insertBefore(pde.clone.cloneNode(true),parentLI.firstChild);
				
				//if this isn't the show class make sure the list is hidden...
				if (pde.cssjs('check', inneruls[j],pde.showClass) != true)
				{
					pde.cssjs('add',inneruls[j],pde.hideClass);
				}
				else
				{
					//if this is a shown list - make sure that the parent li has the correct folder image...
					var img=parentLI.getElementsByTagName('img')[0]
					img.setAttribute('src',pde.openImage);
				}

				pde.addEvent(parentLI.getElementsByTagName('a')[0],'click',pde.showhide,false);
				parentLI.getElementsByTagName('a')[0].onclick=function(){return false;} // Safari hack
				if(pde.linkParent){
					pde.addEvent(parentLI.getElementsByTagName('a')[1],'click',pde.showhide,false);
					parentLI.getElementsByTagName('a')[1].onclick=function(){return false;} // Safari hack
				}
			}
		}
	},
	
	
	
	showhide:function(e){
		var image,message;
		var elm=pde.getTarget(e);
		var ul=elm.parentNode.getElementsByTagName('ul')[0];
		var img=elm.parentNode.getElementsByTagName('img')[0];
		if(pde.cssjs('check',ul,pde.hideClass)){
			message=pde.openMessage;
			image=pde.openImage;
			pde.cssjs('remove',elm.parentNode.getElementsByTagName('ul')[0],pde.hideClass);
			pde.cssjs('add',elm.parentNode.getElementsByTagName('ul')[0],pde.showClass);
		} else {
			message=pde.closedMessage;
			image=pde.closedImage;
			pde.cssjs('remove',elm.parentNode.getElementsByTagName('ul')[0],pde.showClass);
			pde.cssjs('add',elm.parentNode.getElementsByTagName('ul')[0],pde.hideClass);
		}
		img.setAttribute('src',image);
		img.setAttribute('alt',message);
		img.setAttribute('title',message);
		pde.cancelClick(e);
	},
	createClone:function(){
		pde.clone=document.createElement('a');
		pde.clone.setAttribute('href','#');
		pde.clone.appendChild(document.createElement('img'));
		pde.clone.getElementsByTagName('img')[0].src=pde.closedImage;
		pde.clone.getElementsByTagName('img')[0].alt=pde.closedMessage;
		pde.clone.getElementsByTagName('img')[0].title=pde.closedMessage;
	},
/* helper methods */
	getTarget:function(e){
		var target = window.event ? window.event.srcElement : e ? e.target : null;
		if (!target){return false;}
		if (target.nodeName.toLowerCase() != 'a'){target = target.parentNode;}
		return target;
	},
	cancelClick:function(e){
		if (window.event){
			window.event.cancelBubble = true;
			window.event.returnValue = false;
			return;
		}
		if (e){
			e.stopPropagation();
			e.preventDefault();
		}
	},
	addEvent: function(elm, evType, fn, useCapture){
		if (elm.addEventListener) 
		{
			elm.addEventListener(evType, fn, useCapture);
			return true;
		} else if (elm.attachEvent) {
			var r = elm.attachEvent('on' + evType, fn);
			return r;
		} else {
			elm['on' + evType] = fn;
		}
	},
	cssjs:function(a,o,c1,c2){
		switch (a){
			case 'swap':
				o.className=!pde.cssjs('check',o,c1)?o.className.replace(c2,c1):o.className.replace(c1,c2);
			break;
			case 'add':
				if(!pde.cssjs('check',o,c1)){o.className+=o.className?' '+c1:c1;}
			break;
			case 'remove':
				var rep=o.className.match(' '+c1)?' '+c1:c1;
				o.className=o.className.replace(rep,'');
			break;
			case 'check':
				return new RegExp("(^|\s)" + c1 + "(\s|$)").test(o.className)
			break;
		}
	}
}
pde.addEvent(window, 'load', pde.init, false);
pde.addEvent(window, 'load', highlightAll, false);

function highlightAll(){
		 	pHighlight = getElementsByClassName(document,'ul','pde');
 		  	ptags = pHighlight[0].getElementsByTagName('p');
 		  	for (var i=0;i<=ptags.length;i++){
 		  		if (ptags[i]){
 					ptags[i].onclick = function(){
						removeHighlight();
 						addClassName(this, 'current');
					}
		  		}
		  	}
}


function removeHighlight(){
			pHighlight = getElementsByClassName(document,'ul','pde');
 		  	ptags = pHighlight[0].getElementsByTagName('p');
 		  	for (var i=0;i<=ptags.length;i++){
		  		if (ptags[i]){
		  		removeClassName(ptags[i], 'current');
		  		}
		  	}
	}


/*
	Copyright Robert Nyman, http://www.robertnyman.com
	Free to use if this text is included
*/
// ---

// ---
function getElementsByClassName(oElm, strTagName, strClassName){
	var arrElements = (strTagName == "*" && oElm.all)? oElm.all : oElm.getElementsByTagName(strTagName);
	var arrReturnElements = new Array();
	strClassName = strClassName.replace(/\-/g, "\\-");
	var oRegExp = new RegExp("(^|\\s)" + strClassName + "(\\s|$)");
	var oElement;
	for(var i=0; i<arrElements.length; i++){
		oElement = arrElements[i];		
		if(oRegExp.test(oElement.className)){
			arrReturnElements.push(oElement);
		}	
	}
	return (arrReturnElements)
}
// ---
function addClassName(oElm, strClassName){
	var strCurrentClass = oElm.className;
	if(!new RegExp(strClassName, "i").test(strCurrentClass)){
		oElm.className = strCurrentClass + ((strCurrentClass.length > 0)? " " : "") + strClassName;
	}
}
// ---
function removeClassName(oElm, strClassName){
	var oClassToRemove = new RegExp((strClassName + "\s?"), "i");
	oElm.className = oElm.className.replace(oClassToRemove, "").replace(/^\s?|\s?$/g, "");
}
// ---

