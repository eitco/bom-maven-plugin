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
import org.jetbrains.annotations.NotNull;

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
    @Parameter(defaultValue = ".asc")
    protected String signatureExtension;

    protected Artifact makeBomArtifact() {

        Artifact result = makeArtifact("pom");

        result.setFile(targetFile);

        return result;
    }

    protected Artifact makeArtifact(String extension) {

        return artifactFactory.createArtifactWithClassifier(groupId, artifactId, version, extension, null);
    }

    protected Artifact makeSignatureArtifact() {

        Artifact result = makeArtifact("pom" + signatureExtension);

        result.setFile(makeSignatureFile());

        return result;
    }

    @NotNull
    protected File makeSignatureFile() {
        return new File(targetFile.getAbsolutePath() + signatureExtension);
    }
}
