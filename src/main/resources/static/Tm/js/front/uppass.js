//密码修改
function tm_update(obj){
        var name=$("#hname").val();
        var password=$("#password").val();
        var newpassword=$("#newpassword").val();
        var oncepassword=$("#oncepassword").val();
        if(isEmpty(password)){
            Tmtip({html:"原密码不能为空!!!",src:"/Tm/images/24.PNG"});
            $("#password").focus();
            return
        }else{
            $.ajax({
                type:"post",
                url:"/user/updatePas",
                data:{"name":name,"password":password},
                success:function(data){
                    if(data="inputerrors"){
                        Tmtip({html:"你输入的密码错误!!!",src:"/Tm/images/24.PNG"});
                        $("#password").select();
                    }
                    return
                }
            })
        }
        if(isEmpty(newpassword)){
            Tmtip({html:"新密码不能为空!!!",src:"/Tm/images/24.PNG"});
            $("#newpassword").focus();
            return
        }
        if(is_passwd_s(newpassword)==false){
            Tmtip({html:"密码强度较弱!",src:"/Tm/images/24.PNG"});
            $("#newpassword").focus();
            return
        }
        if(isEmpty(oncepassword)){
            Tmtip({html:"再次输入的密码不能为空!!!",src:"/Tm/images/24.PNG"});
            $("#oncepassword").focus();
            return
        }
        if(newpassword!=oncepassword){
            Tmtip({html:"两次输入的密码不一致!",src:"/Tm/images/24.PNG"});
            $("#oncepassword").val("");
            $("#newpassword").select();
            return
        }
        $.ajax({
            type:"post",
            url:"/user/updatePas",
            data:{"name":name,"password":password,"newpassword":newpassword,"oncepassword":oncepassword},
            success:function(data){
                if(data=="success"){
                    Tmtip({html:"密码修改成功,请妥善保管!",src:"/Tm/images/2_1.PNG"});
                    $("#password").val("");
                    $("#newpassword").val("");
                    $("#oncepassword").val("");
                }
            }
        })
    }