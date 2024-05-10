/*
 * Copyright (c) 2023 EITCO GmbH
 * All rights reserved.
 *
 * Created on 12.01.2023
 *
 */
package de.eitco.cicd.bom;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.deployer.ArtifactDeployer;
import org.apache.maven.artifact.deployer.ArtifactDeploymentException;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

import java.io.File;

@Mojo(name = "deploy", defaultPhase = LifecyclePhase.DEPLOY)
public class DeployBillOfMaterialsMojo extends AbstractBillOfMaterialsMojo {

    @Component
    private ArtifactDeployer deployer;

    @Parameter(defaultValue = "${settings.offline}", readonly = true)
    private boolean offline;

    @Parameter(property = "retryFailedDeploymentCount", defaultValue = "1")
    private int retryFailedDeploymentCount;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        failIfOffline();

        if (!targetFile.exists()) {

            throw new MojoExecutionException(targetFile.getPath() + " not found.");
        }

        ArtifactRepository deploymentRepository =
            project.getDistributionManagementArtifactRepository();

        String protocol = deploymentRepository.getProtocol();

        if (StringUtils.isEmpty(protocol)) {
            throw new MojoExecutionException("No transfer protocol found.");
        }

        // Create the artifact
        Artifact artifact =
            artifactFactory.createArtifactWithClassifier(groupId, artifactId, version, "pom", null);

        if (targetFile.equals(getLocalRepositoryFile(artifact))) {

            throw new MojoFailureException("Cannot deploy artifact from the local repository: " + targetFile);
        }

        // Upload the POM if requested, generating one if need be
        try {

            deploy(targetFile, artifact, deploymentRepository, localRepository, retryFailedDeploymentCount);

        } catch (ArtifactDeploymentException e) {

            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    private void failIfOffline() throws MojoFailureException {

        if (offline) {

            throw new MojoFailureException("Cannot deploy artifacts when Maven is in offline mode");
        }
    }


    private void deploy(
        File source, Artifact artifact, ArtifactRepository deploymentRepository,
        ArtifactRepository localRepository, int retryFailedDeploymentCount
    )
        throws ArtifactDeploymentException {
        int retryFailedDeploymentCounter = Math.max(1, Math.min(10, retryFailedDeploymentCount));
        ArtifactDeploymentException exception = null;

        for (int count = 0; count < retryFailedDeploymentCounter; count++) {

            try {

                if (count > 0) {
                    getLog().info("Retrying deployment attempt " + (count + 1) + " of " + retryFailedDeploymentCounter);
                }

                deployer.deploy(source, artifact, deploymentRepository, localRepository);
                exception = null;
                break;

            } catch (ArtifactDeploymentException e) {

                if (count + 1 < retryFailedDeploymentCounter) {
                    getLog().warn("Encountered issue during deployment: " + e.getLocalizedMessage());
                    getLog().debug(e);
                }

                if (exception == null) {
                    exception = e;
                }
            }
        }

        if (exception != null) {
            throw exception;
        }
    }

}
