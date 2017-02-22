$(function () {
    $('.dropdown').hover(function () {
        if (!$(this).hasClass('open')) {
            $('.dropdown-toggle', this).trigger('click');
        }
    }, function () {
        $(this).removeClass('open');
    });
});