package io.doov.gen;

import org.apache.commons.lang3.StringUtils;
import org.gradle.api.NamedDomainObjectContainer;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class GeneratorPlugin implements Plugin<Project> {
    @Override
    public void apply(Project target) {
        NamedDomainObjectContainer<ModelMapGenerator> container = target.container(ModelMapGenerator.class,
                name -> new ModelMapGenerator(name, target));
        target.getExtensions().add("doovCodeGen", container);
        container.all(modelMap -> {
            ModelMapGenTask task = target.getTasks().create(modelMap.getName(), ModelMapGenTask.class);

            target.afterEvaluate(project -> {
                task.setClasspath(project.getConfigurations().getByName("compile"));
                task.getBaseClassProperty().set(modelMap.getBaseClass());
                task.getOutputDirectory().set(modelMap.getOutputDirectory());
                task.getOutputResourceDirectory().set(modelMap.getOutputResourceDirectory());
                task.getSourceClassProperty().set(modelMap.getSourceClass());
                task.getFieldClassProperty().set(modelMap.getFieldClass());
                task.getPackageFilter().set(modelMap.getPackageFilter());
                task.getEnumFieldInfo().set(modelMap.getEnumFieldInfo());
                task.getFieldPathProviderProperty().set(modelMap.getFieldPathProvider());
                task.getTypeAdaptersProperty().set(modelMap.getTypeAdapters());
                task.getDslModelPackage().set(modelMap.getDslModelPackage());
                task.getWrapperPackage().set(modelMap.getWrapperPackage());
                task.getFieldInfoPackage().set(modelMap.getFieldInfoPackage());
                target.getTasks().getByName("clean").dependsOn("clean" + StringUtils.capitalize(task.getName()));
                target.getTasks().getByName("compileGeneratedJava").dependsOn(task);
            });

        });

    }

}