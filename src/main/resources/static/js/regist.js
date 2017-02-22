(function () {
    var rule = {
        rules: {
            username: {
                required: true,
                remote: {//remote:"remote-valid.jsp"   使用ajax方法调用remote-valid.jsp验证输入值,使用ajax方式进行验证
                    url: "/train/ajax-valide-username",//后台处理程序
                    type: "post",                       //数据发送方式
                    data: {                            //需要传送的数据
                        username: function () {
                            return $("#username").val();
                        }
                    },
                    dataType: "json"                   //传送的数据格式
                }
            },
            name: {
                required: true
            },
            cardNo: {
                required: true,
            },
            phone: {
                required: true,
                maxlength: 15
            },
            email: {
                required: true,
                email: true
            },
            password: {
                required: true
            },
            repeatPassword: {
                required: true,
                equalTo: "#password"
            }
        },
        messages: {
            repeatPassword: {
                equalTo: "两次密码不一致"
            },
            username: {
                remote: "该用户已经被注册"
            },
            email: {
                email: "请输入正确格式的邮箱"
            }
        }
    };

    $("#js-register").validate($.extend(rule, custom));
})();