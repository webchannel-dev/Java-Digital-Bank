<%@ page isErrorPage="true" %>
<%@ page import="com.bn2web.common.exception.Bn2Exception" %>
<%@ page import="com.bn2web.common.service.GlobalApplication" %>


<% response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); %>

<%@include file="../inc/doctype_html.jsp" %>

	<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->

	<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
	<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
	<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
	<%@ taglib uri="/WEB-INF/struts-form.tld" prefix="form" %>
	<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
	<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
	<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>


	
	<%-- App is initialised, so we'll show a pretty page --%>
	<head>			
		<title><bright:cmsWrite identifier="company-name" filter="false" /> | UI Reference</title> 
		<%@include file="../inc/head-elements.jsp"%>
		<bean:define id="section" value="ui"/>
	</head>
	<body id="errorPage">
		<%@include file="../inc/body_start.jsp"%>

		<h1 class="underline">UI Reference</h1> 	
		
		
		<p>Inspect the code to see how they are marked up, and copy and paste to reuse.</p>	
		<br />
		
		<h2>System Messages</h2>	
		
		<div class="error">
			<h3>This is an errror. (Use h3 for headings)</h3>
			<p>This is a paragraph. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, <a href="">quis nostrud exercitation</a> ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
		</div>
		
		<div class="warning">
			<h3>This is a warning</h3>
			<p>This is a paragraph. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, <a href="">quis nostrud exercitation</a> ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
		</div>
		
		<div class="info">
			<h3>Something informational</h3>
			<p>This is a paragraph. Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, <a href="">quis nostrud exercitation</a> ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. </p>
			<div class="toolbar">
				<div class="group first"><a href="" class="button">My action</a></div>
				<div class="group"><a href="" class="">Another action</a></div>
				<div class="group">I'm using the toolbar markup for these actions (see below).</div>
			</div>
		
		</div>
		
		<div class="confirm">
			<h3>You have done something <a href="">successfully</a>!</h3>
		</div>
		
		<div class="info noIcon">
			<h3>Look! No icon</h3>
			<p>Just add the class 'noIcon' to the div.</p>
		</div>
		
		<h3>Single line messages:</h3>
		
		<p class="errorInline">Inline versions of system messages</p>
		<p class="warningInline">What the dickens!?</p>
		<p class="infoInline">Something informational</p>
		<p class="confirmInline">Useful for indicating somethings status</p>
		<p class="loadInline">Loading indicator...</p>
		
		<h3>In-line status:</h3>
		<p><span class="status status-wait">Waiting</span> &nbsp;&nbsp;<span class="status status-success">Success!</span>&nbsp;&nbsp; <span class="status status-fail">Failed</span></p>
		<br />
		<br />
		
		<h2 class="underline">Tables</h2>	
		<table class="list highlight" cellspacing="0" summary="Summary of this table">
			<thead>
				<tr>
					<th>Heading 1</th>
					<th>Heading 2</th>
					<th>Heading 3</th>
					<th>Heading 4</th>
					<th>Actions</th>
				</tr>	
			</thead>
			<tbody>
				<tr>
					<td>Content 1</td>
					<td>This is a standard admin table used for listing 'things', properties of 'things', and actions.</td>
					<td>Content 3</td>
					<td>Content 4</td>
					<td class="action">[<a href="#">view</a>] [<a href="#">delete</a>]</td>
				</tr>
				<tr>
					<td>Content 1</td>
					<td>The table has the class <strong>list</strong>. You must use thead and tbody in the markup.</td>
					<td>Content 3</td>
					<td>Content 4</td>
					<td class="action">[<a href="#">view</a>]</td>
				</tr>
				<tr>
					<td>Content 1</td>
					<td>
						<ul>
						<li>Add the class <strong>stripey</strong> to stripe the table rows. </li>
						<li>Add the class <strong>highlight</strong> to highlight a row on hover.</li>
						</ul>
					</td>
					<td>Content 3</td>
					<td>Content 4</td>
					<td class="action">[<a href="#">view</a>]</td>
				</tr>
			</tbody>		
		</table>			
		

		<br />
		<br />
		
		<h2 class="underline">Toolbars / buttons</h2>
		
		
		<div class="toolbar">
			<h3>Toolbar Heading</h3>
			<div class="group"><a href="">Contextual action 1</a></div>
			<div class="group"><a href="">Contextual action 2</a></div>
			<div class="group">
				<a href="" class="button">button 1</a> 
				<div class="dropHolder">
					<a title="More actions on selected items" class="button dropButton last" href="#"><span></span>Dropdown button</a>
					<ul class="dropOptions">
						<li><a title="View the selected items as a contact sheet" href="#">View as contact sheet</a></li>
						<li><a title="Remove the selected items from this Basket" href="#">Remove from Basket</a></li>
						<li><a title="Bulk edit the selected items" href="#">Bulk Update</a></li>
					</ul>
				</div>
			</div>
			
			<div class="group">	
				<div class="dropHolder">
					<a title="More actions on selected items" class="dropLink" href="#">Dropdown link</a>
					<ul class="dropOptions">
						<li><a title="View the selected items as a contact sheet"  href="#">View as contact sheet</a></li>
						<li><a title="Remove the selected items from this Basket" href="#">Remove from Basket</a></li>
						<li><a title="Bulk edit the selected items"  href="#">Bulk Update</a></li>
					</ul>
				</div>
			</div>
			
			<div class="clearing"></div>
		</div>	
		
		
		<p>The js behaviour of the dropdown links and buttons above is now built in to asset bank (-they should just work if you get the markup right). They will not appear at all if a user has js switched off, so you might want to put some kind of fallback in noscript tags.</p>
		
		<a class="multilineButton" href="#">Multiline button 1 <br /><span>Lorem ipsum dolor sit amet</span></a>
		<a class="multilineButton" href="#">Multiline button 2 <br /><span>Lorem ipsum dolor sit amet</span></a>
		
		<ul class="radioButtons">
			<li>
				<label class="multilineButton">
					<input type="radio" class="radio" id="option_1'/>" name="option" value="1" />
					Multiline button<br />
					<span>As applied to radio buttons</span>
				</label>
			</li>
			<li>	
				<label class="multilineButton">
					<input type="radio" class="radio" id="option_1'/>" name="option" value="1" />
					Lorem ipsum<br />
					<span>As applied to radio buttons</span>
				</label>
			</li>
			<li>
				<label class="multilineButton">
					<input type="radio" class="radio" id="option_1'/>" name="option" value="1" />
					Single line
				</label>
			</li>
		</ul>
		
		<br />
		<br />
		
		<h2 class="underline">Navigational aids</h2>
		
		<h3>Big tabs:</h3>
		
		
		<div class="adminTabs">
			<h2><a href="">Big tab</a></h2>
			<h2 class="current"><a href="">Current tab</a></h2>
			<h2><a href="">Another big tab</a></h2>
			<div class="tabClearing">&nbsp;</div>
		</div>
		
		<h3>Smaller tabs:</h3>
		<p class="tabHolder clearfix">
			<a href="#" class="active">Active Tab</a>
			<a href="#">Tab2</a>
			<a href="#">Tab3</a>
			<a href="#">Tab4</a>
		</p>
		<div class="tabContent">
			<p>TODO: Create a generic javascript version of these tabs. (There is a specific version - see a spatial attribute on the search page)</p>
		</div>	
		
		<h3>Link list:</h3>
		<p>Useful when you need an extra level of navigation within tabs</p>
		<ul class="linkList">
			<li><a href="">Link 1</a></li>
			<li class="active"><a href="">Active link</a></li>
			<li><a href="">Link 3</a></li>
			<li><a href="">Link 4</a></li>
		</ul>
		<br /><br />
		
		<h2 class="underline">Misc</h2>
		<div class="log">
			<h3>System Log - Log contents will automatically gain a scrollbar</h3>
			<ul>
				<li>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.  </li>
				<li>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</li>
				<li>Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</li>
				<li>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. </li>
				<li>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.  </li>
				<li>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</li>
				<li>Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</li>
				<li>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. </li>
				<li>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.  </li>
				<li>Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</li>
				<li>Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</li>
				<li>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. </li>
			</ul>
		</div>
		<%@include file="../inc/body_end.jsp"%>		
	</body>

</html>
