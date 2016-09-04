package name.mazgalov.vaadin.sass.compiler

import name.mazgalov.vaadin.sass.compiler.task.CompileSass
import org.gradle.api.Plugin
import org.gradle.api.Project

class VaadinSassCompiler implements Plugin<Project> {
    void apply(Project project) {
        project.tasks.create('compileSass', CompileSass.class)
    }
}