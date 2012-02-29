<script type="text/javascript">
	/* <![CDATA[ */
	
	var iMaxClauses = 10;

	/*
	 * Prior to a submit remove any hidden clauses on the page.
	 * This helps mitigate a potential issue with some html parsers etc not dealing with url lengths > 2048 bytes.
	 * (This isn't ideal but at least it's consistent with how things currently work on this page.)
	 */
	function removeHiddenClauses()
	{
		for(var i=0;i<iMaxClauses;i++)
		{
			var clause = document.getElementById('clause'+i);
			if(clause.style.display=='none')
			{
				clause.parentNode.removeChild(clause);
			}
		}
		return true;
	}

	/*
	* Displays the fields for a new subclause
	*/
	function newClause()
	{
		for(var i=0;i<iMaxClauses;i++)
		{
			var clause = document.getElementById('clause'+i);
			if(clause.style.display=='none')
			{
				clause.style.display='block';
				document.getElementById('clauseVisible'+i).value='true';
				document.getElementById('removeClause'+i).style.display='inline';

				if(i-1>0)
				{
					document.getElementById('removeClause'+(i-1)).style.display='none';
				}
				
				if(i==iMaxClauses-1)
				{
					document.getElementById('addClauseDiv').style.display='none';
				}
				
				break;
			}
		}
	}

	/*
	* Removes the fields of the last visible subclause
	*/
	function removeClause()
	{
		for(var i=iMaxClauses-1;i>=0;i--)
		{
			var clause = document.getElementById('clause'+i);
			
			if(clause.style.display!='none')
			{
				clause.style.display='none';
				document.getElementById('clauseVisible'+i).value='false';
				document.getElementById('removeClause'+i).style.display='none';

				if(i-1>0)
				{
					document.getElementById('removeClause'+(i-1)).style.display='inline';
				}
				
				document.getElementById('addClauseDiv').style.display='block';
				break;
			}
		}
	}
	
	function addAttributeType(type,id)
	{
		aAttributeTypes[type][aAttributeTypes[type].length]=id;
	}

	var iText=0, iList=1, iDate=2, iNumeric=3;
	var aOperatorOptions = [];
	
	// Create an table of {attribute type x static attribute id}
	var aAttributeTypes = [['0','6','1','-1','10','11','301'],['300','400','13'],['7','8','9','700'],['2','12','14','600']];

	// Build the operator option lists for all 4 of the attribute types
	aOperatorOptions[iText] = [new Option("<bright:cmsWrite identifier="snippet-op-contains" filter="false"/>",1), new Option("<bright:cmsWrite identifier="snippet-op-not-contains" filter="false"/>",2)];
	aOperatorOptions[iList] = [new Option("<bright:cmsWrite identifier="snippet-op-equal-to" filter="false"/>",3), new Option("<bright:cmsWrite identifier="snippet-op-not-equal-to" filter="false"/>",4)];
	aOperatorOptions[iDate] = [new Option("<bright:cmsWrite identifier="snippet-op-before" filter="false"/>",5), new Option("<bright:cmsWrite identifier="snippet-op-after" filter="false"/>",6)];
	aOperatorOptions[iNumeric] = [new Option("<bright:cmsWrite identifier="snippet-op-equal-to" filter="false"/>",3), new Option("<bright:cmsWrite identifier="snippet-op-less-than" filter="false"/>",7),new Option("<bright:cmsWrite identifier="snippet-op-more-than" filter="false"/>",8)];
	
	// Add the flexible attributes to the attribute type table
	<logic:iterate name="attributes" id="attribute">
		<c:choose>
			<c:when test="${attribute.typeId==4 || attribute.typeId==5 || attribute.typeId==6}">
				addAttributeType(iList,'<c:out value="${attribute.id}"/>');
			</c:when>
			<c:when test="${attribute.typeId==3 || attribute.typeId==8}">
				addAttributeType(iDate,'<c:out value="${attribute.id}"/>');
			</c:when>
			<c:when test="${attribute.typeId==16}">
				addAttributeType(iNumeric,'<c:out value="${attribute.id}"/>');
			</c:when>
			<c:otherwise>
				if(aAttributeTypes[1].indexOf('<c:out value="${attribute.id}"/>')<0 && aAttributeTypes[2].indexOf('<c:out value="${attribute.id}"/>')<0 && aAttributeTypes[3].indexOf('<c:out value="${attribute.id}"/>')<0)
				{
					addAttributeType(iText,'<c:out value="${attribute.id}"/>');
				}
			</c:otherwise>
		</c:choose>
	</logic:iterate>

	/*
	* Actions an attribute selection. Adjusts the list of operator options according to the type
	* of the newly chosen attribute
	*/
	function doAttributeSelection(ctrl,index)
	{
		var operator = document.getElementById('operator'+index);
		var newOptions = getOptionsForAttribute(ctrl.value);
		var iType = getTypeForAttributeId(ctrl.value);

		if(iType==iDate || iType==iNumeric)
		{
			for(i=index;i<iMaxClauses;i++)
			{
				if(document.getElementById('conjunction'+i) && document.getElementById('conjunction'+i).selectedIndex!=0)
				{
					document.getElementById('conjunction'+i).selectedIndex=0;
					document.getElementById('conjunctionChangedInfo').style.display='block';
				}
			}
		}

		if(newOptions!=null && !optionsEqual(newOptions,operator.options))
		{
			var i=0;
			
			for(i=0;i<newOptions.length;i++)
			{
				// See if the option we're adding is already in the list
				var iOption = findOption(operator.options,newOptions[i]);
				var bWasSelected = false;
				
				if(iOption>=0)
				{
					bWasSelected = operator.options[iOption].selected;
				}
				
				// Set the option
				operator.options[i] = new Option(newOptions[i].text,newOptions[i].value);
				operator.options[i].selected = bWasSelected;
			}
			operator.options.length=i;
		}

		if(ctrl.value!=null && ctrl.value!='')
		{
			var value = '';
			var ctrlType = getTypeForAttributeId(ctrl.value);
			
			if(document.getElementById('clauses[' + index + '].valueAttributeId')==null ||  
					(ctrlType!=iList &&
					getTypeForAttributeId(parseInt(document.getElementById('clauses[' + index + '].valueAttributeId').value))==ctrlType &&
					document.getElementsByName('clauses[' + index + '].value')))
			{
				value = document.getElementsByName('clauses[' + index + '].value')[0].value;
			}

			var myAjax = new Ajax.Updater('valueContainer'+index, 'viewSearchBuilderAttributeValue?id=' + ctrl.value + "&index=" + index + "&value=" + value, {
				method: 'get',
				evalScripts: true
			});
		}
	}

	/*
	* Check that the conjunctions for the clause are valid - they are not if there is an OR joining any subclauses
	* from a date or numeric field onwards until the end of the clause. If there are any inappropriate ORs they are
	* changed to ANDs and a warning div is displayed.
	*/
	function checkConjunction(index)
	{
		for(var i=0;i<index+1;i++)
		{
			var iType = getTypeForAttributeId(document.getElementById('attributeId'+i).value);
		
			if(iType==iDate || iType==iNumeric)
			{
				if(document.getElementById('conjunction'+index) && document.getElementById('conjunction'+index).selectedIndex!=0)
				{
					document.getElementById('conjunction'+index).selectedIndex=0;
					document.getElementById('conjunctionChangedInfo').style.display='block';
					return;
				}
			}
		}
	}

	/*
	* Returns the type (iText=0, iList=1, iDate=2, iNumeric=3) of an attribute given its id
	*/
	function getTypeForAttributeId(id)
	{
		for(var i=0;i<aAttributeTypes.length;i++)
		{
			if(aAttributeTypes[i].indexOf(id)>=0)
			{
				return i;
			}
		}
	}

	/*
	* Returns the index of an option in an array of options, with equality based on value
	*/
	function findOption(optionArray,optionToFind)
	{
		for(var i=0;i<optionArray.length;i++)
		{
			if(optionArray[i].value==optionToFind.value)
			{
				return i;
			}
		}
		return -1;
	}
	
	/* 
	* Checks for value-based equality in 2 option arrays
	*/
	function optionsEqual(array1,array2)
	{
		if(array1==null && array2==null)
		{
			return true;
		}
		if(array1==null && array2!=null)
		{
			return false;
		}
		if(array1.length!=array2.length)
		{
			return false;
		}
		for(i=0;i<array1.length;i++)
		{
			if(array1[i].value!=array2[i].value)
			{
				return false;
			}
		}
		return true;
	}

	/*
	* Returns the operator option list for a particular attribute type
	*/
	function getOptionsForAttribute(id)
	{
		for(var i=0;i<aAttributeTypes.length;i++)
		{
			if(aAttributeTypes[i].indexOf(id)>=0)
			{
				return aOperatorOptions[i];
			}
		}
	}
	
	/* ]]> */
</script>