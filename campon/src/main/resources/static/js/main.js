$(".minus").click(function(e) {
    e.preventDefault();
    var input = $("#reservationNop");
    if (input.val() < input.attr('max')) {
        input.val(parseInt(input.val()) - 1);
    }
    if(input.val() <= 1) {
        input.val(1);
    }
});

$(".plus").click(function(e) {
    e.preventDefault();
    var input = $("#reservationNop");
    if (input.val() >= input.attr('min')) {
        input.val(parseInt(input.val()) + 1);
    }
    if(input.val() <= 1) {
        input.val(1);
    }
});