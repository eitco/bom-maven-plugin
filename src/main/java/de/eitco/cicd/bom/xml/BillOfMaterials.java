/*
 * Copyright (c) 2023 EITCO GmbH
 * All rights reserved.
 *
 * Created on 12.01.2023
 *
 */
package de.eitco.cicd.bom.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonRootName(value = "project", namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillOfMaterials {

    public static final String MAVEN_XML_NAMESPACE = "http://maven.apache.org/POM/4.0.0";

    @JacksonXmlProperty(isAttribute = true, localName = "xsi:schemaLocation")
    private String schemaLocation = "http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd";

    @JacksonXmlProperty(isAttribute = true, localName = "xmlns:xsi")
    private String xsi = "http://www.w3.org/2001/XMLSchema-instance";

    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String modelVersion = "4.0.0";
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String groupId;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String artifactId;
    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String version;

    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private String packaging = "pom";

    @JacksonXmlProperty(namespace = MAVEN_XML_NAMESPACE)
    private DependencyManagement dependencyManagement = new DependencyManagement();

    public String getModelVersion() {
        return modelVersion;
    }

    public BillOfMaterials setModelVersion(String modelVersion) {
        this.modelVersion = modelVersion;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public BillOfMaterials setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public BillOfMaterials setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public BillOfMaterials setVersion(String version) {
        this.version = version;
        return this;
    }

    public DependencyManagement getDependencyManagement() {
        return dependencyManagement;
    }

    public BillOfMaterials setDependencyManagement(DependencyManagement dependencyManagement) {

        if (dependencyManagement == null) dependencyManagement = new DependencyManagement();

        this.dependencyManagement = dependencyManagement;
        return this;
    }

    public String getPackaging() {
        return packaging;
    }

    public String getSchemaLocation() {
        return schemaLocation;
    }

    public BillOfMaterials setSchemaLocation(String schemaLocation) {
        this.schemaLocation = schemaLocation;
        return this;
    }

    public String getXsi() {
        return xsi;
    }

    public BillOfMaterials setXsi(String xsi) {
        this.xsi = xsi;
        return this;
    }

    public BillOfMaterials setPackaging(String packaging) {
        this.packaging = packaging;
        return this;
    }
}
