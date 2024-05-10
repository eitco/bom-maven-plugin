/*
 * Copyright (c) 2023 EITCO GmbH
 * All rights reserved.
 *
 * Created on 12.01.2023
 *
 */
package de.eitco.cicd.bom.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Exclusion {

    @JacksonXmlProperty(namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    private String groupId;
    @JacksonXmlProperty(namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    private String artifactId;

    public String getGroupId() {
        return groupId;
    }

    public Exclusion setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getArtifactId() {
        return artifactId;
    }

    public Exclusion setArtifactId(String artifactId) {
        this.artifactId = artifactId;
        return this;
    }
}
