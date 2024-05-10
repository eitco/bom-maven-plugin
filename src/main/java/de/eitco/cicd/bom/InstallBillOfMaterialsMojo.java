/*
 * Copyright (c) 2023 EITCO GmbH
 * All rights reserved.
 *
 * Created on 12.01.2023
 *
 */
package de.eitco.cicd.bom;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.installer.ArtifactInstallationException;
import org.apache.maven.artifact.installer.ArtifactInstaller;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.install.DualDigester;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.shared.utils.io.FileUtils;

import java.io.File;
import java.io.IOException;

@Mojo(name = "install", defaultPhase = LifecyclePhase.INSTALL)
public class InstallBillOfMaterialsMojo extends AbstractBillOfMaterialsMojo {

    @Component
    protected ArtifactInstaller installer;

    protected final DualDigester digester = new DualDigester();

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        try {

            final Artifact billOfMaterials = artifactFactory.createArtifact(groupId, artifactId, version, null, "pom");

            installer.install(targetFile, billOfMaterials, localRepository);

            File installedFile = getLocalRepositoryFile(billOfMaterials);

            getLog().debug("Calculating checksums for " + installedFile);
            digester.calculate(installedFile);
            installChecksum(installedFile, ".md5", digester.getMd5());
            installChecksum(installedFile, ".sha1", digester.getSha1());

        } catch (ArtifactInstallationException e) {

            throw new MojoExecutionException(e.getMessage(), e);
        }

    }

    private void installChecksum(File installedFile, String ext, String checksum) throws MojoExecutionException {

        File checksumFile = new File(installedFile.getAbsolutePath() + ext);
        getLog().debug("Installing checksum to " + checksumFile);

        try {
            //noinspection ResultOfMethodCallIgnored
            checksumFile.getParentFile().mkdirs();

            FileUtils.fileWrite(checksumFile.getAbsolutePath(), "UTF-8", checksum);

        } catch (IOException e) {

            throw new MojoExecutionException("Failed to install checksum to " + checksumFile, e);
        }
    }

}
