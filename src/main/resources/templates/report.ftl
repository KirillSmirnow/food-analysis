<#-- @ftlvariable name="report" type="foodanalysis.analysis.Report" -->

<#if report.substances?size = 0>
    <#compress>
        Вещества не выявлены
    </#compress>
<#else>
    <#compress>
        Обнаружены вещества:
    </#compress>


    <#list report.substances as substance>
        <#compress>
            <#if substance.code??>
                &#128312; ${substance.code} — ${substance.names[0]}
            <#else>
                &#128312; ${substance.names[0]}
            </#if>
            <b>${substance.healthImpact!}</b>
        </#compress>


    </#list>
</#if>
