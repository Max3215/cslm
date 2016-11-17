<p class="tit">提现历史账户</p>
  <ul>
    <#if bankList??>
    <#list bankList as bank>
    <li>
        <#assign len = bank.bankCard?length>
      <div class="con">
        <p class="num">${bank.bankCard?substring(0,4)} **** **** ${bank.bankCard?substring(len-4,len)}</p>
        <p class="text">${bank.bankName!''}</p>
        <p class="text">${bank.name!''}</p>
      </div>
      <menu class="btns">
          <a onclick="drawFrom(${bank.id?c});">使用</a>
          <a onclick="deleteBank(${bank.id?c});">删除</a>
      </menu>
    </li>
    </#list>
    </#if>
  </ul>