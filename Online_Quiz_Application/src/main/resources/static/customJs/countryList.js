$(document).ready(function() {
		
		  var select = $("#countryList");

		  $.ajax({
		    url: "https://restcountries.com/v2/all",
		    type: "GET",
		    success: function(data) {
		      $.each(data, function(index, country) {
		        select.append($(`<option value="${country.name}" ></option>`).text(country.name));
		        console.log(country.name);
		      });
		    },
		    error: function() {
		      console.log("Error occurred while fetching country data.");
		    }
		  });
		});
		
		
	/*	$(document).ready(function() {

  var select = $("#countryList");

  $.ajax({
    url: "https://api.first.org/data/v1/countries",
    type: "GET",
    success: function(response) {
      if (response && response.data) {
        $.each(response.data, function(code, countryData) {
          select.append($("<option></option>").val(code).text(countryData.country));
        });
      }
    },
    error: function() {
      console.log("Error occurred while fetching country data.");
    }
  });
});*/