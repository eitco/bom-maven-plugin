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
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.artifact.DefaultArtifact;
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
    @Parameter(property = "localRepository", required = true, readonly = true)
    protected ArtifactRepository localRepository;

    @Parameter(defaultValue = "${project}", readonly = true)
    protected MavenProject project;
    @Parameter(defaultValue = "${repositorySystemSession}", readonly = true)
    private RepositorySystemSession repoSession;


    @NotNull
    protected File getLocalRepositoryFile(Artifact billOfMaterials) {

        String pathForLocalArtifact = repoSession.getLocalRepositoryManager().getPathForLocalArtifact(
                new DefaultArtifact(
                        billOfMaterials.getGroupId(),
                        billOfMaterials.getArtifactId(),
                        billOfMaterials.getClassifier(),
                        billOfMaterials.getType(),
                        billOfMaterials.getVersion()
                ));

        getLog().info("path of repo for artifact " + billOfMaterials + " is " + pathForLocalArtifact);

        return new File(localRepository.getBasedir(), pathForLocalArtifact);
    }
}
