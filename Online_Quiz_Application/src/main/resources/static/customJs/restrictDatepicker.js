   document.addEventListener('DOMContentLoaded', function () {
        var fromDateInput = document.getElementById('fromDate');
        var toDateInput = document.getElementById('toDate');

        var today = new Date();

        var fromDateFlatpickr = flatpickr(fromDateInput, {
            dateFormat: 'Y-m-d',
            maxDate: today,
            onChange: function (selectedDates, dateStr) {
                toDateFlatpickr.set('minDate', dateStr);
            }
        });

        var toDateFlatpickr = flatpickr(toDateInput, {
            dateFormat: 'Y-m-d',
            minDate: today
        });
    });