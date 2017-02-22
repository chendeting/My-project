$(function () {
    var heigth = $(window).height();
    //左边列表与右边content区域的高度占满屏，需要减去nav的高度
    $('#js-aside').height(heigth - 50);
    $('#js-right-content').height(heigth - 50);
   //当调用 hide 实例方法时立即触发该事件。这些事件可在函数中当钩子使用.
    $('.js-collapse').on('hidden.bs.collapse', function () {
        var i = $(this).parent().find('span.pull-right').find('i');
        i.removeClass('fa-angle-down');
        i.addClass('fa-angle-right');
        $(this).parent().find('.panel-heading').removeClass('header-active');
    }).on('show.bs.collapse', function () {
        var i = $(this).parent().find('span.pull-right').find('i');
        i.removeClass('fa-angle-right');
        i.addClass('fa-angle-down');
        $(this).parent().find('.panel-heading').addClass('header-active');
    });

    $('#init').trigger('click');

    //乱码肯定是页面编码造成的。把汉化属性的初始化放调用页面中,解决日期空间的乱码问题
    $.fn.datetimepicker.dates['zh-CN'] = {
        days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
        daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
        daysMin: ["日", "一", "二", "三", "四", "五", "六", "日"],
        months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
        today: "今天",
        suffix: [],
        meridiem: ["上午", "下午"],
        rtl: false // 从右向左书写的语言你可以使用 rtl: true 来设置
    };
});

function goTo(url, target) {
    $('.js-menu-a').removeClass('white');
    $(target).addClass('white');
    setTimeout(function () {
        $('#js-message').find('.alert').alert('close');
    }, 2000);

    $.get(url, {}, function (data) {
        $('#js-right-content').html(data);
        $('#begin').datetimepicker({
            minView: 'month',
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            autoclose: true
        });
        $('#begin').datetimepicker('setStartDate', new Date());
        var myRules = {
            rules: {
                departure: {
                    required: true
                },
                destination: {
                    required: true
                },
                begin: {
                    required: true
                }
            }
        };
        $("#query-ticket-form").validate($.extend(myRules, custom));
    }, 'html');
};

//查询车票，向后台发送请求

function queryTicket() {
    //jQuery的serialize()方法通过序列化表单值，创建URL编码文本字符串，我们就可以选择一个或多个表单元素，我们就可以把序列化的值传给ajax()作为url的参数
    if ($("#query-ticket-form").valid()) {
        $.post('/train/query-ticket', $('#query-ticket-form').serialize(), function (data) {
            $('#result').html(data);
        }, 'html');
    }
}


function orderTicket(target) {
    $("#myModal").modal();
    $("#js-train").text($(target).attr("tripNo"));
    $("#js-departureDate").text($(target).attr("departureDate"));
    $("#js-departure").text($(target).attr("departure"));
    $("#js-destination").text($(target).attr("destination"));
    $("#js-id-input").val($(target).attr("id"));
}
//提交订单
function submitOrderTicket() {
    $.post('/train/order', $('#js-order-ticket-form').serialize(), function (data) {
        if (data.status == 'success') {
            alert('订票成功');
            $("#myModal").modal('hide');
        } else {
            alert('订票失败：' + data.reason)
        }
        $('#myModal').on('hidden.bs.modal', function (e) {
            queryTicket();
        })
    }, 'json');
}
//取消预订订单
function cancelBooking(target) {
    $.post('/train/cancel-booking', {id: $(target).attr("id")}, function (data) {
        if (data == 'success') {
            alert('操作成功');
        } else {
            alert('操作失败');
        }
        $.get('/train/order', {}, function (data) {
            $('#js-right-content').html(data);
        }, 'html');
    }, 'text');
}
//修改信息
function updateInfomation() {
    $.post('/train/info', $('#userinfo-form').serialize(), function (data) {
        if (data == '0') {
            alert('操作成功，您已修改密码，请重新登录');
            window.location.href = '/train/logout';
            return;
        }else if(data == "1"){
            alert("操作成功");
        }else {
            alert("操作失败");
        }
        $.get('/train/info', {}, function (data) {
            $('#js-right-content').html(data);
        }, 'html');
    }, 'text');
}