package name.mazgalov.vaadin.sass.compiler

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec

class VaadinSassCompiler implements Plugin<Project> {
    void apply(Project project) {
        project.extensions.create("compileSass", CompileSassExtension)

        def vaadinThemesConfig = project.configurations.create('vaadinThemes')
        project.dependencies.add(vaadinThemesConfig.name, [
                group:'com.vaadin',
                name: 'vaadin-themes',
                version: '7.6.3'
        ])

        project.task('unpackVaadinThemesJar') {
            doLast {
                def vaadinThemesJar = vaadinThemesConfig.incoming.files.files.find {
                    it.name ==~ /.*vaadin.*themes.*\.jar/
                }
                project.copy {
                    includeEmptyDirs = false
                    into new File(project.compileSass.themeLocation).parent
                    from(project.zipTree(vaadinThemesJar)) {
                        include 'VAADIN/themes/**'
                        eachFile { details ->
                            details.path = details.path.replaceAll(/^VAADIN\/themes\//, '')
                        }
                    }
                }
            }
        }

        def sassCompileConfig = project.configurations.create('sassCompiler')
        project.dependencies.add(sassCompileConfig.name, [
                group:'com.vaadin',
                name: 'vaadin-sass-compiler',
                version: '0.9.13'
        ])

        project.task('compileSass', type: JavaExec) {
            dependsOn 'unpackVaadinThemesJar'
            doFirst {
                main = 'com.vaadin.sass.SassCompiler'
                classpath = sassCompileConfig
                args project.compileSass.scssFile, project.compileSass.cssFile
                println "SASS compiler executing command line:"
                println commandLine.join(' ')
            }
        }
    }
}