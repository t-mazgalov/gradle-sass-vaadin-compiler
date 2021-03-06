#Gradle SASS compiler plugin

##How to use the plugin

When the plugin is applied, it creates a task which can be used to compile required SASS file.

```
apply plugin: 'name.mazgalov.vaadin.sass.compiler'

compileSass {
    scssFile = file '...'
    cssFile = file '...'
}
```

Custom task definition of type `CompileSass` can be used as well.

```
apply plugin: 'name.mazgalov.vaadin.sass.compiler'

import name.mazgalov.vaadin.sass.compiler.task.CompileSass

task cssGeneration (type: CompileSass) {
    scssFile = file '...'
    cssFile = file '...'
}
```

The plugin can be directly from the Gradle plugins portal:

```
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.name.mazgalov:gradle-sass-vaadin-compiler:0.1.1"
  }
}
```

##Compilation task configuration

The task allows more sophisticated configuration with the following properties:

* `scssFile` (mandatory) - the file from which will be generated CSS
* `cssFile` (mandatory) - output CSS file
* `sassCompilerConfiguration` (optional) - a custom Gradle configuration can be used, if such is not specified, will be used default one created by the plugin
* `vaadinSassCompilerVersion` (optional) - can be specified a custom Vaadin SASS compiler version, if such is missing, will be used latest.milestone
* `vaadinThemesVersion` (optional) - can be specified a custom Vaadin Themes version, if such is missing, will be used latest.milestone. It will be placed on the classpath of the compiler allowing to use the default themes provided by Vaadin during the compilation.

Example:

```
apply plugin: 'name.mazgalov.vaadin.sass.compiler'

import name.mazgalov.vaadin.sass.compiler.task.CompileSass

configurations {
    customSassCompilerConfiguration
}

dependencies {
    customSassCompilerConfiguration group:'com.vaadin', name: 'vaadin-sass-compiler', version: compilerVersion
    customSassCompilerConfiguration group:'org.exmample', name: 'other-dependency', version: customVersion
}

task cssGeneration (type: CompileSass) {
    scssFile = file '...'
    cssFile = file '...'
    sassCompilerConfiguration = customSassCompilerConfiguration
    vaadinSassCompilerVersion = '1.0.0'
    vaadinThemesVersion = '7.0.0'
}
```

##Helpful links

For more information about Vaadin SASS compilation: [Vaadin Themes Compiling](https://vaadin.com/docs/-/part/framework/themes/themes-compiling.html)