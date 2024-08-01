package de.eitco.cicd.bom;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.install.DualDigester;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This goal generates check sums for the bill-of-materials generated.
 */
@Mojo(name = "checksums", defaultPhase = LifecyclePhase.PROCESS_SOURCES)
public class CreateBomChecksumsMojo extends AbstractBillOfMaterialsMojo {

    private final DualDigester digester = new DualDigester();

    @Override
    public void execute() throws MojoExecutionException {

        File bomFile = makeBomArtifact().getFile();

        digester.calculate(bomFile);

        attachChecksum(bomFile, ".md5", digester.getMd5());
        attachChecksum(bomFile, ".sha1", digester.getSha1());
    }

    private void attachChecksum(File bomFile, String extension, String checksum) throws MojoExecutionException {

        try {

            File checksumFile = new File(bomFile.getAbsolutePath() + extension);

            Files.writeString(checksumFile.toPath(), checksum);

            Artifact artifact = makeArtifact("pom" + extension);

            artifact.setFile(checksumFile);
            project.addAttachedArtifact(artifact);

        } catch (IOException e) {

            throw new MojoExecutionException(e);
        }

    }


}
