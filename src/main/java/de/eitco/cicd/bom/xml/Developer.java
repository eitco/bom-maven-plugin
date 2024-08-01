package de.eitco.cicd.bom.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import static de.eitco.cicd.bom.xml.BillOfMaterials.MAVEN_XML_NAMESPACE;

public class Developer {

    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String name;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String email;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String url;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String organization;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String organizationUrl;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String timezone;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String id;

    public String getName() {
        return name;
    }

    public Developer setName(String name) {
        this.name = name;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Developer setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getOrganization() {
        return organization;
    }

    public Developer setOrganization(String organization) {
        this.organization = organization;
        return this;
    }

    public String getOrganizationUrl() {
        return organizationUrl;
    }

    public Developer setOrganizationUrl(String organizationUrl) {
        this.organizationUrl = organizationUrl;
        return this;
    }

    public String getTimezone() {
        return timezone;
    }

    public Developer setTimezone(String timezone) {
        this.timezone = timezone;
        return this;
    }

    public String getId() {
        return id;
    }

    public Developer setId(String id) {
        this.id = id;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Developer setEmail(String email) {
        this.email = email;
        return this;
    }

    public static Developer fromMaven(org.apache.maven.model.Developer developer) {

        Developer result = new Developer();

        result.name = developer == null ? null : developer.getName();
        result.email = developer == null ? null : developer.getEmail();
        result.url = developer == null ? null : developer.getUrl();
        result.organization = developer == null ? null : developer.getOrganization();
        result.organizationUrl = developer == null ? null : developer.getOrganizationUrl();
        result.timezone = developer == null ? null : developer.getTimezone();
        result.id = developer == null ? null : developer.getId();

        return result;
    }

}
