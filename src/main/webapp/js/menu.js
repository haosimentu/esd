$(function(){
	var checkedId="#" + checkedIndex;
	$(checkedId).addClass("li_mouseover");

	$(".ny-header-z .ny-menu li").each(function(){
		$(this).mouseenter(function(){
			if($(this).attr("id") != checkedIndex)
				$(this).addClass("li_mouseover");
		});
		
		$(this).mouseleave(function(){
			if($(this).attr("id") != checkedIndex )
				$(this).removeClass("li_mouseover");
		});
	});
});