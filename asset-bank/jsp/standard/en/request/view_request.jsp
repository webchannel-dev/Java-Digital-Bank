<%@include file="../inc/doctype_html_admin.jsp" %>

<!-- Website designed and developed by Bright Interactive, http://www.bright-interactive.com -->
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/bright-tag.tld" prefix="bright" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<head>
	<title><bright:cmsWrite identifier="title-request-details" /></title> 
	<%@include file="../inc/head-elements.jsp"%>
	<bean:define id="section" value="requests"/>
	
	<script type="text/javascript" src="../js/workflow-transitions/confirm-transition.js"></script>
	<script type="text/javascript" src="../js/group-edit.js"></script>
	 
	<script type="text/javascript" charset="utf-8">
	
	var bulkApprove = {
			
			config : {
					messages : true
			},

		
			init : function(options) {
				
				// provide for custom configuration via init()
				$j.extend(bulkApprove.config, options);
				
				//Set up some variables
				bulkApprove.$actionRadios = $j('#workflowActions input[type="radio"]');
				bulkApprove.$actionButtons = $j('#workflowActions label');
				
				if (bulkApprove.config.messages) {
					// Setup up dialogue box containing message for user form:	
					$j("#userMessageForm").dialog({
						autoOpen: false,
						height: 330,
						width: 380,
						modal: true,
						buttons: {
							"Submit": function() {
								$j(this).parent().prependTo($j("#bottomOfForm"));	//move fields back into main form so fields get included.
								$j('#mainForm').submit();							//submit form
							},
							Cancel: function() {
								$j(this).dialog( "close" );
								bulkApprove.$actionButtons.removeClass('on');
							}
						}
					});
				
				}
				
				bulkApprove.$actionRadios.click(function() {
					bulkApprove.clickActionButton($j(this));
				});

			},
			
			clickActionButton : function($this) {
				bulkApprove.$actionButtons.removeClass('on');				// button highlighting
				$this.addClass('on');
				
				var confirmText = $this.parent().prev().val();				// get confirmation text
				var optionalMessage = $j('#confirmationText').text();		// get "optional" property before epmtying the confirmationText
				
				$j('#confirmationText').empty().append(confirmText);		// add message to popup message box
				$j('#confirmationText').append(optionalMessage);			// add "optional" property
				
				if ($this.hasClass('showMessage')) {
					$j("#userMessageForm").dialog("open");				// open 'message for user' dialogue
				} 
				else {
					$j('#mainForm').submit();
				}
				
			}
		}

		$j(function(){
			
			// Initialise approval actions:
			<c:choose>
				<c:when test="${requestDetails.workflowInfo.state.transitionsHaveMessages}">
					var baOptions = {messages : true};
				</c:when>
				<c:otherwise>
					var baOptions = {messages : false};
				</c:otherwise>
			</c:choose>	
			bulkApprove.init(baOptions);
			
		})
	
	</script>
</head>


<body> 

	<%@include file="../inc/body_start.jsp"%>
	
	<h1><bright:cmsWrite identifier="heading-request-details" /></h1> 
	<bean:parameter id="managing" name="managing" value="false" />
	
	<div class="head">
		<c:choose>
			<c:when test="${managing}">
				<bean:parameter id="stateName" name="stateName"/>
					<c:choose>
						<c:when test="${stateName == 'fulfilled'}">
							<a href="viewFulfilledRequests?stateName=fulfilled" ><bright:cmsWrite identifier="link-back-to-fulfilled-requests" filter="false"/></a>
						</c:when>
						<c:when test="${stateName == 'rejected'}">
							<a href="viewRejectedRequests?stateName=rejected" ><bright:cmsWrite identifier="link-back-to-rejected-requests" filter="false"/></a>
						</c:when>
						<c:otherwise>
							<a href="viewManageRequests?stateName=<c:out value='${stateName}' />"><bright:cmsWrite identifier="link-back-to-all-requests" filter="false"/></a>
						</c:otherwise>
					</c:choose>
			</c:when>
			<c:otherwise>
				<a href="viewMyRequests"><bright:cmsWrite identifier="link-back-to-my-requests" filter="false" /></a>
			</c:otherwise>
		</c:choose>
	</div>
	<logic:present name="requestForm">
		<logic:equal name="requestForm" property="hasErrors" value="true">
			<div class="error">
				<logic:iterate name="requestForm" property="errors" id="error">
					<bright:writeError name="error" /><br />
				</logic:iterate>
			</div>
		</logic:equal>
	</logic:present>
	
	
	<%@include file="inc_request_fields.jsp"%>

	
	<logic:notPresent name="userCanSeeTransitions">
		<c:set var="userCanSeeTransitions" value="false" />
	</logic:notPresent>
	
	
	<c:if test="${managing && userCanSeeTransitions}">
	
		<form name="form" id="mainForm" method="post" action="transitionRequest" class="approvalActions">
			<html:hidden property="managing" 		value="${managing}" />
			<html:hidden property="id" 				value="${requestDetails.id}" />
			<html:hidden property="workflowName" 	value="${requestDetails.workflowInfo.workflowName}" />
			<html:hidden property="stateName" 		value="${requestDetails.workflowInfo.state.name}" />
			
			<%-- fulfillers dropdown (if any) --%>
			
				<logic:present name="fulfillers">
					<logic:notEmpty name="fulfillers">
			
						<strong><label for="assignTo"><bright:cmsWrite identifier="label-assign-to" />:</label></strong>
				
						<select name="userId" size="1">
							<logic:iterate name="fulfillers" id="fulfiller">
								<option value="<bean:write name='fulfiller' property='id' />"><bean:write name='fulfiller' property='forename' /> <bean:write name='fulfiller' property='surname' /> (<bean:write name='fulfiller' property='username' />)</option>
							</logic:iterate>
						</select><br />
					
					</logic:notEmpty>
					<logic:empty name="fulfillers">
						<div class="warning"><bright:cmsWrite identifier="snippet-no-fulfillers-defined" filter="false" /></div>
					</logic:empty>
					
					
				</logic:present>

				
			<%-- action buttons and message field --%>

				<logic:notEmpty name="requestDetails" property="workflowInfo.state.transitionList">
					<bean:define id="state" name="requestDetails" property="workflowInfo.state" />
					<ul class="radioButtons" id="workflowActions" >
						<bean:size id="numActions" name="state" property="transitionList"/>
						<logic:iterate id="transition" name="requestDetails" property="workflowInfo.state.transitionList" indexId="transIndex">
							<c:if test="${ ! transition.hidden }">
								<li>
									<input type="hidden" id="text_<c:out value='${requestDetails.id}' />_<c:out value='${transition.transitionNumber}' />" value="<c:out value='${transition.confirmationText}' />" />
									<label class="multilineButton <c:if test="${transIndex+1 == numActions}">last</c:if> <c:if test="${transition.messageMandatory}">message</c:if>">
										<input type="radio" name="transition" id="radio_<c:out value='${requestDetails.id}' />_<c:out value='${transition.transitionNumber}' />" <c:if test="${transition.hasMessage}">class="showMessage"</c:if> value="<c:out value="${transition.transitionNumber}" />" <c:if test="${transIndex == 0}">checked="checked"</c:if> />
										
										<c:out value="${transition.description}" /><br />
										<span><c:if test="${state.transitionsHaveHelpText}"><c:out value="${transition.helpText}" /></c:if></span>
									</label>
								</li>
							</c:if>
						</logic:iterate>
					</ul>
				</logic:notEmpty>


				<%--  Show message if any of the transitions have one --%>
				<c:if test="${requestDetails.workflowInfo.state.transitionsHaveMessages}">
					<div class="js-enabled-hide">
						<div id="userMessageForm" title="Message">
							<p>
							<div id="confirmationText" class="info">
								<c:choose>
									<c:when test="${transition.messageMandatory}">
											<span class="required">*</span>(<bright:cmsWrite identifier="snippet-message-required" />)
									</c:when>
									<c:otherwise>
										<c:if test="${transition.hasMessage}"><span>(<bright:cmsWrite identifier="snippet-optional" filter="false"/>)</span></c:if> 
									</c:otherwise>
								</c:choose>
							</div>
							</p>
							<textarea name="message" class="group_edit" rows="8" id="newMessage_<c:out value='${requestDetails.id}'/>"></textarea>
							
						</div>
					</div>
				</c:if>
			
				<input type="submit" value="<bright:cmsWrite identifier="button-submit" filter="false" />" class="button flush js-enabled-hide" style="clear:right; float:right"/>
	
			
			<div id="bottomOfForm"></div>
		</form>
	</c:if>
	

	

	
	<%@include file="../inc/body_end.jsp"%>
				
</body>
</html>