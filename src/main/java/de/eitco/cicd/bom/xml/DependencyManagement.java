/*
 * Copyright (c) 2023 EITCO GmbH
 * All rights reserved.
 *
 * Created on 12.01.2023
 *
 */
package de.eitco.cicd.bom.xml;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.ArrayList;
import java.util.List;

public class DependencyManagement {

    @JacksonXmlElementWrapper(localName = "dependencies", namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    @JacksonXmlProperty(localName = "dependency", namespace = BillOfMaterials.MAVEN_XML_NAMESPACE)
    private List<Dependency> dependencies = new ArrayList<>();

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public DependencyManagement setDependencies(List<Dependency> dependencies) {

        if (dependencies == null) dependencies = new ArrayList<>();

        this.dependencies = dependencies;
        return this;
    }
}
