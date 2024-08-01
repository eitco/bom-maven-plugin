package de.eitco.cicd.bom.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import static de.eitco.cicd.bom.xml.BillOfMaterials.MAVEN_XML_NAMESPACE;

public class Scm {

    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String connection;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String developerConnection;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String tag;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String url;

    public String getConnection() {
        return connection;
    }

    public Scm setConnection(String connection) {
        this.connection = connection;
        return this;
    }

    public String getDeveloperConnection() {
        return developerConnection;
    }

    public Scm setDeveloperConnection(String developerConnection) {
        this.developerConnection = developerConnection;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public Scm setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Scm setUrl(String url) {
        this.url = url;
        return this;
    }

    public static Scm fromMaven(org.apache.maven.model.Scm scm) {

        Scm result = new Scm();

        result.connection = scm == null ? null : scm.getConnection();
        result.developerConnection = scm == null ? null : scm.getDeveloperConnection();
        result.tag = scm == null ? null : scm.getTag();
        result.url = scm == null ? null : scm.getUrl();

        return result;
    }

}
