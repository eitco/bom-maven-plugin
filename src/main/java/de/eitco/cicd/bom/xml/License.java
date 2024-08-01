package de.eitco.cicd.bom.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import static de.eitco.cicd.bom.xml.BillOfMaterials.MAVEN_XML_NAMESPACE;

public class License {

    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String name;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String url;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String distribution;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String comments;

    public String getName() {
        return name;
    }

    public License setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public License setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDistribution() {
        return distribution;
    }

    public License setDistribution(String distribution) {
        this.distribution = distribution;
        return this;
    }

    public String getComments() {
        return comments;
    }

    public License setComments(String comments) {
        this.comments = comments;
        return this;
    }

    public static License fromMaven(org.apache.maven.model.License license) {

        License result = new License();

        result.name = license == null ? null : license.getName();
        result.url = license == null ? null : license.getUrl();
        result.distribution = license == null ? null : license.getDistribution();
        result.comments = license == null ? null : license.getComments();

        return result;
    }
}
