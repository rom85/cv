function ShowHidePopup(id){
	 $("#panepop").toggleClass('selected');
	 if ($("#panepop").hasClass('selected')) {
		var urlAnno = "/CVTracker/inbox/getannotation/"+id+"/data";
		$.ajax({  
            url: urlAnno,  
            cache: false,
            dataType: 'json',
            success: function(data){ 
                var Pri = data.priority;
                var Anno = data.anno;
                $("#fmfAnnoId").val(id);
                $("#fmfAnnoP").val(Pri);
                $("#fmfAnno").val(Anno);
            }
        });
	}
};

function clearAnno() {
	$("#fmfAnno").val('');
	$("#fmfAnnoP").val('2');
	UpdAnno();
}
function UpdAnno() {
   var Id  = $("#fmfAnnoId").val();
   var Pri  = $("#fmfAnnoP").val();
   var Anno = $("#fmfAnno").val();
   var urlAction = "/CVTracker/inbox/annotation/"+Id+"/update";
   $.post(
		   urlAction,
		   { anno: Anno, annop: Pri },
		   function(data,textStatus,jqXHR){
			   ShowHidePopup();
			   $("input[name='submitname2']").click();
		   }
   );
//   
}

function ShowHideAnno(id) {
	 $("#viewAnno").toggleClass('selected');
	 if ($("#viewAnno").hasClass('selected')) {
			var Id = id.match(/\d+/);
			var urlAnno = "/CVTracker/inbox/getannotation/"+Id+"/data";
			$.ajax({  
	            url: urlAnno,  
	            cache: false,
	            dataType: 'json',
	            success: function(data){
	            	var msgPri = ['','Low Priority','Middle Priority','High Priority'];
	                var Pri = msgPri[data.priority];
	                var Anno = data.anno;
	                $('#viewAnno').html(
	                 'Priority: <b>'+Pri+'</b><br />Annotation:<br /><i>'+Anno+'</i>'
	                );
	            }
	        });
		}
}

$(document).ready(function(){
	 $('#myExample').dataTable();
	 $('.dummy').mouseover(function(event){
		 ShowHideAnno($(event.target).attr('id')) ;
	 });
	 $('.dummy').mouseout(function(){
		 ShowHideAnno();
	 });
});
