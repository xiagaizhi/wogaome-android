<?xml version="1.0"?>
<globals>
    <#assign Collection=extractLetters(objectKind)>//从输入的title中获取输入字符
          <#assign TitleName=extractLetters(objectTitle)>//从输入的title中获取输入字符
               <#assign PackageId=extractLetters(PackageId)>//从输入的packageName中获取输入字符
              
        <#assign collection_name=Collection?lower_case>//获取到的字符转成小写
           
            <global id="fragment_layout" value="fragment_layout_${Collection?lower_case}" />//作为fragment的layout的命名
             <global id="TitleName" value="${TitleName}" />
             <global id="PackageId" value="${PackageId}" />
            <global id="FragmentName" value="${Collection}Fragment" />//作为presenter类名
            <global id="ContractName" value="${Collection}Contract" />//作为contract类名
            <global id="VuName" value="${Collection}Vu" />//作为vu类名
           
        </globals>
