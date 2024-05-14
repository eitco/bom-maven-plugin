package de.eitco.cicd.bom;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.util.Optional;

@Mojo(name = "attach-signature", defaultPhase = LifecyclePhase.VERIFY)
public class AttachBomSignatureMojo extends AbstractBillOfMaterialsMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        File signatureFile = makeSignatureFile();

        if (!signatureFile.exists()) {

            getLog().debug("signature file not found - nothing to attach");
            return;
        }

        Optional<Artifact> signatureArtifactOptional = project.getAttachedArtifacts()
                .stream().filter(artifact -> artifact.getFile().equals(signatureFile)).findFirst();

        if (signatureArtifactOptional.isPresent()) {

            Artifact signatureArtifact = signatureArtifactOptional.get();

            project.getAttachedArtifacts().remove(signatureArtifact);
        }

        project.addAttachedArtifact(makeSignatureArtifact());
    }

}
