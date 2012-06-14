function stripe(id)
{
	//dynamically make the metadata table stripey
	if(document.getElementsByTagName)
	{  
		var table = document.getElementById(id);  
		if(table!=null)
		{
			stripeRows(table);
		}
		
		var tables = document.getElementsByName(id);	
		if(tables!=null)
		{
			for(var i=0; i < tables.length; i++)
			{
				stripeRows(tables[i]);
			}
		}
	}
}

function stripeRows(table)
{
	if(table!=null)
	{
		var rows = table.getElementsByTagName("tr");  
		
		//iterate through table rows
		for(var i = 0; i < rows.length; i++)
		{          
	 		//manipulate rows
			if(i % 2 == 0)
			{
				rows[i].className+=rows[i].className?' even':'even';
			}    
		}
	}
}

function showHide(id,imgId)
{
	var element = document.getElementById(id);
	var image = document.getElementById(imgId);
	
	if(element)
	{
		if(element.style.display!='block')
		{
			element.style.display='block';
			image.src='../images/standard/icon/subtract.gif';
		}
		else
		{
			element.style.display='none';
			image.src='../images/standard/icon/add.gif';
		}
	}
}