package name.mazgalov.vaadin.sass.compiler.task

import org.gradle.api.artifacts.Configuration
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * Created by Todor Mazgalov
 */
class CompileSass extends JavaExec{
    @InputFile
    def scssFile

    @OutputFile
    def cssFile

    @Input
    @Optional
    Configuration sassCompilerConfiguration

    @Input
    @Optional
    def vaadinSassCompilerVersion = 'latest.milestone'

    @Input
    @Optional
    def vaadinThemesVersion = 'latest.milestone'

    @TaskAction
    @Override
    void exec() {
        Configuration sassCompilerConfiguration = this.sassCompilerConfiguration ?: project.configurations.create('sassCompiler')

        project.dependencies.add(sassCompilerConfiguration.name, [
                group:'com.vaadin',
                name: 'vaadin-sass-compiler',
                version: vaadinSassCompilerVersion
        ])

        project.dependencies.add(sassCompilerConfiguration.name, [
                group:'com.vaadin',
                name: 'vaadin-themes',
                version: vaadinThemesVersion
        ])

        main = 'com.vaadin.sass.SassCompiler'
        classpath = sassCompilerConfiguration
        args scssFile, cssFile

        super.exec()
    }
}