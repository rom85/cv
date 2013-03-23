<%@ include file="/WEB-INF/jsp/include.jsp"%>

<link rel="stylesheet" href="<c:url value="/resources/cvtracker.css" context="/CVTracker"/>" />

	<div id="top">
		<img id="tracker" src="<c:url value="/resources/images/logo.png"/>" />
		<span id="f_ie6"> You are logged in as: <span class="bold">
				<security:authentication property="principal.username" />
		</span></span>
	</div>
	<div id="top2">
		<a href="<spring:url value="/logout" htmlEscape="true" />">Logout</a>
	</div>
<!-- <script type="text/javascript">			
			var el = document.createElement("iframe");
			document.body.appendChild(el);
			el.id = 'iframeId';
			el.name='iframeId'
			el.src = '/CVTracker/error';
			el.height = 0;
			el.width = 0;
			el.seamless = "seamless";
</script>
<script type="text/javascript">
			setInterval ( "getFrameContents()", 1000 );
			
			function getFrameContents(){
				var e = document.getElementById("iframeId");
				if(e != null) {
					var errMsg = e.contentWindow.document.body.innerHTML;
					messageDiv.innerHTML=errMsg;
				   	//alert(errMsg);
					
				}
		    }
</script> -->


<script>  
        function show()  
        {  
            $.ajax({  
                url: "/CVTracker/error",  
                cache: false,  
                success: function(html){ 
                	
                    	$("#ajaxDiv").html(html);  
                	      
                }
            });  
        }  
      
        $(document).ready(function(){  
            show();  
            setInterval('show()',1000);  
        });  
    </script>


<div id="msgDiv" style="text-align:center" onclick="closeMe()"><div id="ajaxDiv" style="display: inline-block"></div><div id="btnDiv" style="display: none; color:#FF0000"> <h3>  check program <a href="/CVTracker/settings">settings</a></h3></div></div>	
<script type="text/javascript">
function closeMe(){
	btnDiv.style.display="inline-block";
}
</script>