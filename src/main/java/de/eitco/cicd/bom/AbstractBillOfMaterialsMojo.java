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

    /**
     * The name of the bill-of-materials file to generate.
     */
    @Parameter(defaultValue = "${project.build.directory}/bom.xml")
    protected File targetFile;

    /**
     * The groupId of the bill-of-materials artifact to generate.
     */
    @Parameter(defaultValue = "${project.groupId}")
    protected String groupId;

    /**
     * The artifactId of the bill-of-materials artifact to generate.
     */
    @Parameter(defaultValue = "${project.artifactId}-bom")
    protected String artifactId;

    /**
     * The version of the bill-of-materials artifact to generate.
     */
    @Parameter(defaultValue = "${project.version}")
    protected String version;

    /**
     * The extension to add to the signature file generated.
     */
    @Parameter(defaultValue = ".asc")
    protected String signatureExtension;


    @Component
    protected ArtifactFactory artifactFactory;

    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject project;

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
