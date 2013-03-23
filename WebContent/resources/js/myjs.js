$(document).ready(function(){
	$("a[href='#select_all']").click( function() {
         $("#" + $(this).attr('rel') + " input:checkbox:enabled").attr('checked', true);
          return false;
    });
    $("a[href='#select_none']").click( function() {
           $("#" + $(this).attr('rel') + " input:checkbox").attr('checked', false);
          return false;
    });
	$("#checkaccounts").click(function(){
		startLoadingAnimation();
	});
	$("#checkall").click(function(){
		toggleAll();
	});
    $(".btn-slide1").click(function(){
        $("#panel1").slideToggle("slow");
        $(".btn-slide1").toggle();
        $(this).toggleClass("active");
    });
    $(".btn-slide2").click(function(){
        $("#panel2").slideToggle("slow");
        $(".btn-slide2").toggle();
        $(this).toggleClass("active");
    });
    $(".btn-slide3").click(function(){
        $("#panel3").slideToggle("slow");
        $(".btn-slide3").toggle();
        $(this).toggleClass("active");
    });
});
function startLoadingAnimation()
{
  var imgObj = $("#loadImg");
  imgObj.show();
};
function stopLoadingAnimation()
{
  $("#loadImg").hide();
};
function toggleAll() {
    var allCheckboxes = $("#checkboxes input:checkbox:enabled");
    var notChecked = allCheckboxes.not(':checked');
    allCheckboxes.removeAttr('checked');
    notChecked.attr('checked', 'checked');
}


//right menu inbox and statistic

