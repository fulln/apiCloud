
$(function() {
    $("#register_btn").click(function() {
        $("#register").css("display", "block");
        $("#login").css("display", "none");
    });
    $("#back_btn").click(function() {
        $("#register").css("display", "none");
        $("#login").css("display", "block");
    });
    $("input[name='Login']").click(function () {
        this.disabled = true;
        window.location.href = "/bitCase/show";
    })


});


$().ready(function() {
    $("#login_form").validate({
        rules: {
            username: "required",
            password: {
                required: true,
                minlength: 6
            }
        },
        messages: {
            username: "please enter your name",
            password: {
                required: "please enter your password",
                minlength: jQuery.format("密码不能小于{0}个字 符")
            }
        }
    });
    $("#register_form").validate({
        rules: {
            username: "required",
            password: {
                required: true,
                minlength: 6
            },
            rpassword: {
                equalTo: "#register_password"
            },
            email: {
                required: true,
                email: true
            }
        },
        messages: {
            username: "请输入姓名",
            password: {
                required: "请输入密码",
                minlength: jQuery.format("密码不能小于{0}个字 符")
            },
            rpassword: {
                equalTo: "两次密码不一样"
            },
            email: {
                required: "请输入邮箱",
                email: "请输入有效邮箱"
            }
        }
    });
});