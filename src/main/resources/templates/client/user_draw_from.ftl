<form id="form1" action="/user/drwa2" method="post">
  <p class="tit">提现余额到银行卡</p>
  <table>
    <tr>
      <th>输入银行卡号：</th>
      <td><input type="text" name="card" value="<#if bank??>${bank.bankCard!''}<#elseif user??>${user.bankCardCode!''}</#if>" class="text long" placeholder="请输入您的银行卡号" datatype="/^(\d{16}|\d{19})$/" errormsg="请输入正确的银行卡号" sucmsg=" " nullmsg="请输入账号"/>&nbsp;<lable style="color:red;">*</lable></td>
    </tr>
    <tr>
      <th>开户行：</th>
      <td><input type="text" name="bank" value="<#if bank??>${bank.bankName!''}<#elseif user??>${user.bankTitle!''}</#if>" class="text short" placeholder="请输入开户行" datatype="*" errormsg="请输入开户行" sucmsg=" " nullmsg="请输入开户行"/>&nbsp;<lable style="color:red;">*</lable></td>
    </tr>
    <tr>
      <th>开户姓名：</th>
      <td><input type="text" name="name" value="<#if bank??>${bank.name!''}<#elseif user??>${user.bankName!''}</#if>" class="text short" placeholder="请输入账号姓名" datatype="*2-6" errormsg="请输入姓名" sucmsg=" " nullmsg="请输入姓名"/>&nbsp;<lable style="color:red;">*</lable></td>
    </tr>
    <tr>
      <th>提现金额：</th>
      <td><input type="text" name="price" class="text short" datatype="/(^[-+]?[1-9]\d*(\.\d{1,2})?$)|(^[-+]?[0]{1}(\.\d{1,2})?$)/" sucmsg=" " nullmsg="请填写提现金额"/></td>
    </tr>
    <tr>
      <th>输入支付密码：</th>
      <td><input type="password" name="payPassword" class="text long" datatype="s6-20" errormsg="请输入密码！" sucmsg=" " nullmsg = "请输入密码"/></td>
    </tr>
    <tr>
      <th></th>
      <td><span style="color:red;">*每次提现最小金额100元，提交前请仔细核对信息是否有误</span></td>
    </tr>
    <tr>
      <th></th>
      <td><input type="submit" class="sub" value="确定" /></td>
    </tr>
  </table>
  </form>
 <script>
 $(document).ready(function(){

    //初始化表单验证
    $("#form1").Validform({
        tiptype:4, 
        ajaxPost:true,
        callback:function(data){
            if(data.code==1){
                layer.msg(data.msg, {icon: 1 ,time:4000});
                window.location.href="/user/account";
            }else{
                layer.msg(data.msg, {icon: 2 ,time: 2000});
            }
        }
    });
 })
 </script>