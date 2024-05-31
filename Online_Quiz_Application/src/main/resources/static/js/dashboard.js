$(document).ready(function() {
	var select = $('#yearpicker');
	var currentYear = new Date().getFullYear();
	var financialYearStart;
	var financialYearEnd;

	// Determine financial year start and end dates based on the current date
	if (new Date().getMonth() >= 3) {
		financialYearStart = currentYear;
		financialYearEnd = currentYear + 1;
	} else {
		financialYearStart = currentYear - 1;
		financialYearEnd = currentYear;
	}

	// Loop through the financial years and append options
	for (var year = 2000; year <= currentYear; year++) {
		var nextYear = year + 1;
		var optionText = year + '-' + nextYear;
		var option = $('<option>', { value: optionText, text: optionText });
		select.append(option);

		// Select the option corresponding to the financial year
		if (year === financialYearStart) {
			select.val(optionText);
		}
	}

	$('#yearpicker').flatpickr({
		wrap: true,
		disableMobile: true
	});

	// Get selected value on change
	select.on('change', function() {
		var financialYear = select.val();
		getDashboardData(financialYear);
	});
});

function getDashboardData(financialYear) {

	var base_url = window.location.origin;

	var host = window.location.host.split(':');

	var pathArray = window.location.pathname.split('/');
	if (host[0] == "localhost") {
		url2 = "/dashboard/getDashboardData?financialYear=" + financialYear;
		getData(url2);
	}
	else {
		url2 = '/' + pathArray[1] + '/dashboard/getDashboardData?financialYear=' + financialYear;
		getData(url2);
	}

	function getData(url2) {
		$.getJSON(url2, function(data) {
			$('#invoice_count').text(data.invoiceCount);
			$('#total_amount_rec').text(data.totalAmountReceived);
			$('#service_tax_bill_count').text(data.ServiceTaxBillCount);
			$('#performa_bill_count').text(data.PerformaBillCount);
			$('#expenses').text(data.expenses);
			$('#po_count').text(data.poCount);
			$('#employees').text(data.employees);
		});
	}
}