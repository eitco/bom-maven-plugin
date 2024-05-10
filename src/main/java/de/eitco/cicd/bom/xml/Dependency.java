/*
 * Copyright (c) 2023 EITCO GmbH
 * All rights reserved.
 *
 * Created on 12.01.2023
 *
 */
package de.eitco.cicd.bom.xml;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Dependency {

    @JacksonXmlProperty(namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    private String groupId;
    @JacksonXmlProperty(namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    private String artifactId;
    @JacksonXmlProperty(namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    private String version;
    @JacksonXmlProperty(namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String type;
    @JacksonXmlProperty(namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String classifier;
    @JacksonXmlProperty(namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String scope;

    @JacksonXmlProperty(namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String optional;

    @JacksonXmlProperty(namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    @JsonInclude(value = JsonInclude.Include.NON_NULL)
    private String systemPath;

    @JacksonXmlElementWrapper(localName = "exclusions", namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    @JacksonXmlProperty(localName = "exclusion", namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
    private List<Exclusion> exclusions = new ArrayList<>();

    @NotNull
    public String dependencyKey() {

        return groupId + ":" + artifactId + ":" + (type != null ? (type) : "") + ":" + (classifier != null ? (classifier) : "");
    }

    public String getGroupId() {
        return groupId;
    }

    public Dependency setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public Dependency setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public Dependency setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getType() {
        return type;
    }

    public Dependency setType(String type) {
        this.type = type;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public Dependency setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public List<Exclusion> getExclusions() {
        return exclusions;
    }

    public Dependency setExclusions(List<Exclusion> exclusions) {
        this.exclusions = exclusions;
        return this;
    }

    public String getClassifier() {
        return classifier;
    }

    public Dependency setClassifier(String classifier) {
        this.classifier = classifier;
        return this;
    }

    public String getOptional() {
        return optional;
    }

    public Dependency setOptional(String optional) {
        this.optional = optional;
        return this;
    }

    public String getSystemPath() {
        return systemPath;
    }

    public Dependency setSystemPath(String systemPath) {
        this.systemPath = systemPath;
        return this;
    }
}
