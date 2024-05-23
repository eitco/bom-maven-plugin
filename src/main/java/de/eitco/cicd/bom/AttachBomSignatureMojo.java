package de.eitco.cicd.bom;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

import java.io.File;
import java.util.Optional;

/**
 * This goal attaches the signature of the bom file to the current project - provided it exists. It does not generate
 * the signature, use the `maven-gpg-plugin` to do so.
 */
@Mojo(name = "attach-signature", defaultPhase = LifecyclePhase.VERIFY)
public class AttachBomSignatureMojo extends AbstractBillOfMaterialsMojo {

    @Override
    public void execute() {

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
