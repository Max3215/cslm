<#if categoryList??>
      <select name="" id="twoCat" name="twoparentId">
        <option value="" <#if cat?? && cat.parentId?? && cat.parentId==0>selected="selected"</#if>>无父级分类</option>
           <#list categoryList as c>
               <option value="${c.id?c!""}" <#if cat?? && cat.parentTree?? && cat.parentTree?contains("["+c.id?c+"]") || fatherCat?? && fatherCat.id==c.id>selected="selected"</#if>>${c.title!""}</option>
           </#list>
    </select>
</#if>