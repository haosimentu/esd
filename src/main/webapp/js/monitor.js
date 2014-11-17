var showHtml;
var showId;
$(function(){
	$("#diagram-container img").hover(function(){ShowFloatingImage(this, 320, 320);},
		function(){HideElement(this);}
	);
});

function GetAbsPosition(obj)
{
      var curleft = 0, curtop = 0;
      do {
      curleft += obj.offsetLeft;
      curtop += obj.offsetTop;
      } while (obj = obj.offsetParent);
      return [curleft,curtop];       
}

function ShowFloatingImage(image, width, height)
{
	deltaWidth = width-300;
	deltaHeight = height-300;
    var absPos = GetAbsPosition(image);
    image.style.position = "absolute";        
    image.style.left = (absPos[0] - deltaWidth/2)+"px";
    image.style.top = (absPos[1] - deltaHeight/2)+"px";
    image.style.width=width+"px";
    image.style.height=height+"px";
    image.style.border="2px solid #F1584A";
   
}

function HideElement(image)
{
	image.style.position = "static"; 
	image.style.left="0px";
	image.style.top="0px;";
    image.style.width="300px";
    image.style.height="300px";
    image.style.border="0px";
}

function showPipelineDetail(id)
{
	if(null != showId)
	{
		$(showId).html(showHtml);
	}
	showId="#lineDetail_"+id;
	showHtml=$(showId).html();
	$(showId).html("");
	MU.alert(showHtml,1200,'流水线工位详情');
	refreshPage();
	window.setInterval(refreshPage,6000);
}
function refreshPage()
{
	var data = {};
    $.ajax({
		type:'POST',
		url:basePath+'user/refreshpipeline.html',
		data:data,
		dataType:'json',
		timeout:100000,
		success:function(data){
			for(var i=0; i<data["result"].length; i++)
			{
				var divId="#station_"+ data["result"][i]["pipelineId"]+ "_"+ data["result"][i]["number"];
				var innerHtml;
				if(data["result"][i]["state"]==0)
				{innerHtml="<img src=\""+basePath+"images/gray.png"+"\" />" + data["result"][i]["number"];}
				if(data["result"][i]["state"]==1)
				{innerHtml="<img src=\""+basePath+"images/green.png"+"\" />" + data["result"][i]["number"];}		
				if(data["result"][i]["state"]==2)
				{innerHtml="<img src=\""+basePath+"images/red.png"+"\" />" + data["result"][i]["number"];}								
				$(divId).html(innerHtml);
			}
		},
		error:function(request,status,error){
			window.location.reload();
		}	   
    }); 
}