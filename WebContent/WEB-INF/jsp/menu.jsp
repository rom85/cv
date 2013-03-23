
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<script type="text/javascript">
		
			/*Function for page navigation through the leftbar menu.*/
		
			$(document).ready(function(){
			$("#leftbar").css({"color":"#000659","background-color":"#c8c8c8"})
			$("#inboxkey").click(function() {
					$(".leftba").css({"color":"#000659","background-color":"#c8c8c8"});
				$("#inboxkey").css({"color":"#FFFFFF","background-color":"#0000FF"});
			});
			
			$("#vacancykey").click(function() {
				$(".leftba").css({"color":"#000659","background-color":"#c8c8c8"});
			$("#vacancykey").css({"color":"#FFFFFF","background-color":"#0000FF"});
		});
			
			$("#settingskey").click(function() {
				$(".leftba").css({"color":"#000659","background-color":"#c8c8c8"});
				$("#settingskey").css({"color":"#FFFFFF","background-color":"#0000FF"});
			});
			
			$("#statisticskey").click(function() {
				$(".leftba").css({"color":"#000659","background-color":"#c8c8c8"});
				$("#statisticskey").css({"color":"#FFFFFF","background-color":"#0000FF"});
			});
			});
			
		</script>


	<div id="leftbar" >
	
		<ul id="list">
			<li><a id="inboxkey" href="/CVTracker" class="leftba"><span>Resume</span></a></li>
			
			<li><a id="vacancykey" href="/CVTracker/vacancy" class="leftba"><span>Vacancy</span></a></li>
			
			<li><a id="settingskey" href="/CVTracker/settings" class="leftba"><span>Settings</span></a></li>
			
			<li><a id="statisticskey" href="/CVTracker/statistic" class="leftba"><span>Statistics</span></a></li>
			
			<!-- <li><a href="/CVTracker/resources/CVTracker - Manual.doc" class="leftba">Get Manual</a></li>-->
		
		</ul>
	</div>
	
