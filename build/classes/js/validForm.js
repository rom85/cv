function validDate(form) {
						
			var dateFrom = form.elements["dateFrom"].value;
			var dateTo = form.elements["dateTo"].value;
			
			if ((dateFrom == "") || (dateTo == "")) {
				alert("Date field is empty");
				return false;
			}
						
			// dateFrom generator for object Date
			var dateFromArr = dateFrom.split('.');
			var dateFromStr = dateFromArr[1] + "/"+dateFromArr[0]+"/"+dateFromArr[2];
			var dateFromObj = new Date();
			dateFromObj.setTime(Date.parse(dateFromStr));
			
			// dateTo generator for object Date
			var dateToArr = dateTo.split('.');
			var dateToStr = dateToArr[1] + "/"+dateToArr[0]+"/"+dateToArr[2];
			var dateToObj = new Date();
			dateToObj.setTime(Date.parse(dateToStr));
			
			// Validation
			
			// start date is bigger then finish one
			if (dateFromObj > dateToObj) {
				alert("Incorrect input! You have entered start date "+dateFromStr+" bigger then finish date "+dateToStr+". Change it please!");
				return false;
			}
			
						
			var s = "eMails";
			var k = 0;
			
			for (var i = 1;i < 6;i++){
			if (!form.elements[s+i].checked) k++; 
			}
						
			if (k == 5) {
			alert("No e-mails are choosen. You should choose some e-mail address.");
			return false;
			}
			
			return true;
		}
		