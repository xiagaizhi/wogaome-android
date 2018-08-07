<?xml version="1.0"?>
<recipe>
 
    
    <instantiate from="root/src/app_package/MVPFragment.java.ftl"
    to="${escapeXmlAttribute(srcOut)}/${FragmentName}.java" />
    <instantiate from="root/src/app_package/MVPVu.java.ftl"
    to="${escapeXmlAttribute(srcOut)}/${VuName}.java" />
    <instantiate from="root/src/app_package/MVPContract.java.ftl"
    to="${escapeXmlAttribute(srcOut)}/${ContractName}.java" />
    
    
</recipe>
