	<style type="text/css" media="screen"> 
		body { font:75%/1.5 tahoma, arial,sans-serif; 
			background: #eee;
		} 
		div.wrapper {
			width: 65%;
			margin: 50px auto;    
			border: 1px solid #ccc;  
			padding: 30px;   
			background: #fff;          
			-moz-border-radius: 12px; 
			-webkit-border-radius: 12px; 
			border-radius: 12px; 
			-moz-box-shadow: 4px 4px 10px #aaa; 
			-webkit-box-shadow: 4px 4px 10px #aaa; 
			box-shadow: 4px 4px 10px #aaa;                      
		}   
		h1 {
			margin: 0.5em 0 0.5em 0; 
		} 
		h2 {
			font-size: 1.2em;
			margin: 1em 0 0.5em 0;
		}   
		h2 a {
			text-decoration: none;
			color: #000;
		}
		p {
			margin: 0 0 1em 0;
		}
		div.errorBox {  
			padding: 1em;
			border: 1px dashed #aaa;  
			font-family: courier, monospace;  
			background: #FCE4E4; 
			word-wrap: break-word;
		}     
		div.infoBox {  
			padding: 1em;
			border: 1px dashed #aaa;  
			font-family: courier, monospace;  
			background: #fdfdfd; 
			word-wrap: break-word;
		}     
		div.infoBox {  
			padding: 1em;
			border: 1px dashed #aaa;  
			font-family: courier, monospace;  
			background: #fdfdfd; 
			word-wrap: break-word;
		}
		
		/* New style of system message */
		div.error, div.confirm, div.warning, div.info, div.edit-mode-strip{
			border:1px solid #aaa;
			padding:1.2em 1em 1.2em 4.8em;
			margin-bottom:1em;
			line-height:1.3em;
			background-position: 1em center;
			background-repeat: no-repeat;
			overflow: hidden;
			/* Do not insert display : block or some unwanted messages will start showing up */
		}	
		div.error {
			font-size:1.1em;	 	/* error messages more dominant */
			border-color: #CF5A5A;
			background-color: #FCE4E4;
			background-image: url(../images/standard/icon/error.gif);
			color:#8F1111;
			}
		div.confirm {
			border-color: #61A52B;
			background-color: #E9F9D0;
			background-image: url(../images/standard/icon/confirm.gif);
			}
		div.info {
			border-color: #77A5BF;
			background-color: #e7f0f3;
			background-image:  url(../images/standard/icon/info.gif);
			}	
		div.warning {
		/*	font-size:1.1em;*/		/* warning messages more dominant */
			border-color:#DFB90D;
			background-color: #fdfecd;
			background-image: url(../images/standard/icon/warning.gif);
			background-position: 0.8em center;
			}
			
			
		div.confirm ul{
			margin:0 0 0 10px;
			padding:0;
		}
			
		
		div.section {
			margin: 0 0 20px 0;
		}	
		
		div.section.subtle,
		div.section.subtle h2 a {
			color: #666;
		}
			
		p.loading {
			background: url('../images/standard/misc/ajax_loader_small.gif') left center no-repeat;
			line-height: 20px;
			padding-left: 21px;
		}
		ul.wizard-menu {
			overflow:hidden;
			height:auto;
			list-style: none;
			margin:0; padding:0;
			border-bottom: 1px solid #ccc;
			padding-bottom: 15px;
		}
		ul.wizard-menu li {
			float:left;
			margin-right:20px;
			color:#bbb;
			font-weight: bold;
		}
		
		ul.wizard-menu li a {
			color:#bbb;
			text-decoration:none;
		}
		ul.wizard-menu li a:hover {
			color: #333;
		}
		ul.wizard-menu li.active,
		ul.wizard-menu li.active a{
			color:#333;
		}
		ul.wizard-menu li.cancel {
			float:right;
			margin-right: 0;
		}
		ul.wizard-menu li.cancel a {
			color: #333;
		}
		
		div.wizard-bottom-nav {
			overflow:hidden;
			height: 1%;
			border-top: 1px solid #ccc;
			padding-top: 15px;
			margin-top: 20px;
		}
		a.button,
		input.button {
			background: #333;
			color: #fff;
			padding: 3px 6px;
			display:inline-block;
			text-decoration: none;
			margin-right: 10px;
			border:0;
			font-size: 12px;
			cursor:pointer;
		}
		input.confirm {
			background: #076311;
		}
		.right {
			float:right;
		}
		
		form label,
		form input[type=text] {
			float:left;
			margin: 0 10px 10px 0;
			
		}
		form label {
			width: 80px;
			text-align:right;
		}
		form br {
			clear:left;
		}
		span.hint {
			color: #888;
		}
		
		div.toolbar {
			overflow:hidden;
			height: 1%;
			margin: 1em 0 0.5em 0;
		}
		div.toolbar h2 {
			margin:0;
			float:left;
		}
		div.toolbar a {
			float:left;
			margin-left: 10px;
			padding-left: 10px;
			border-left: 1px solid #ccc;
		}
		
		
	</style>
