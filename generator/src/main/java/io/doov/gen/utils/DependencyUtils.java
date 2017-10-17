package io.doov.gen.utils;

import java.io.File;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.ProjectBuildingRequest;

class DependencyUtils {

    /**
     * @param artifact the artifact to resolve
     * @param project  the current Maven project
     * @return the resolved artifact from the current local repository
     */
    static File resolve(Artifact artifact, MavenProject project) {
        ProjectBuildingRequest projectBuildingRequest = project.getProjectBuildingRequest();
        ArtifactRepository localRepository = projectBuildingRequest.getLocalRepository();
        Artifact resolvedArtifact = localRepository.find(artifact);
        return resolvedArtifact != null ? resolvedArtifact.getFile() : null;
    }
}
