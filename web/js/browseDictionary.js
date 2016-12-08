$(document).ready(function() {
    $("tr[title='dictionary']").click(function () {
            var path=$(this).children("td:eq(1)").text();
            var currentPath=$("#currentPath").text();
            alert(currentPath);
            var goToPath;
            if(currentPath=="")
            	{
            	goToPath="/"+path;
            	
            	}
            else{
            	 goToPath=currentPath+"/"+path;
            }
            location.href="browseDictionary/browseDictionary/?goToPath="+goToPath;
        }
    );
})
