package de.eitco.cicd.bom.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import static de.eitco.cicd.bom.xml.BillOfMaterials.MAVEN_XML_NAMESPACE;

public class Organization {

    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String name;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String url;


    public String getName() {
        return name;
    }

    public Organization setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Organization setUrl(String url) {
        this.url = url;
        return this;
    }

    public static Organization fromMaven(org.apache.maven.model.Organization organization) {

        Organization result = new Organization();

        result.name = organization.getName();
        result.url = organization.getUrl();

        return result;
    }
}
