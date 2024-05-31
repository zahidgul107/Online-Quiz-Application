 function displayErrorAlert(message) {
        var alertContainer = document.getElementById('error-alert-container');
        alertContainer.innerHTML = '<div class="alert alert-danger alert-bs-dismissible fade show" role="alert">' +
            '<strong>Error:</strong> ' + message +
            '<button type="button" class="close" data-bs-dismiss="alert"aria-label="Close"><span aria-hidden="true">&times;</span></button>' +
            '</div>';
    }
    
    $(document).ready(function() {
	    $("form").submit(function(event) {
	        var fromDate = $("#fromDate").val();
	        var toDate = $("#toDate").val();

	        if (fromDate === ""  ) {
	        	displayErrorAlert("Please fill in From Date(Start Date)");
	        	alertClose();
	            event.preventDefault(); 
	        }else if(toDate === ""){
	        displayErrorAlert("Please fill in To Date(End Date).");
	        alertClose();
	            event.preventDefault(); 
	        }
	    });
	});