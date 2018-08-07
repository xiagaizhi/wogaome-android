<?xml version="1.0"?>
<recipe>
    <instantiate from="root/res/layout/fragment_layout.xml.ftl"
    to="${escapeXmlAttribute(resOut)}/layout/${fragment_layout}.xml" />
    
    <instantiate from="root/src/app_package/MVPFragment.java.ftl"
    to="${escapeXmlAttribute(srcOut)}/${FragmentName}.java" />
    <instantiate from="root/src/app_package/MVPVu.java.ftl"
    to="${escapeXmlAttribute(srcOut)}/${VuName}.java" />
    <instantiate from="root/src/app_package/MVPContract.java.ftl"
    to="${escapeXmlAttribute(srcOut)}/${ContractName}.java" />
    
    
</recipe>
