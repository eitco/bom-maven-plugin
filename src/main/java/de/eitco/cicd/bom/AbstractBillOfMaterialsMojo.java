/*
 * Copyright (c) 2023 EITCO GmbH
 * All rights reserved.
 *
 * Created on 12.01.2023
 *
 */
package de.eitco.cicd.bom;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.factory.ArtifactFactory;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;

public abstract class AbstractBillOfMaterialsMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.directory}/bom.xml")
    protected File targetFile;

    @Parameter(defaultValue = "${project.groupId}")
    protected String groupId;

    @Parameter(defaultValue = "${project.artifactId}-bom")
    protected String artifactId;

    @Parameter(defaultValue = "${project.version}")
    protected String version;

    @Component
    protected ArtifactFactory artifactFactory;

    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject project;

    protected Artifact makeBomArtifact() {
        return artifactFactory.createArtifactWithClassifier(groupId, artifactId, version, "pom", null);
    }
}
