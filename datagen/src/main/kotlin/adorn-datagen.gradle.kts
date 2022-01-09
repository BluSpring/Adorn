import juuxel.adorn.datagen.gradle.DatagenExtension
import juuxel.adorn.datagen.gradle.DeleteDuplicates
import juuxel.adorn.datagen.gradle.GenerateData

plugins {
    java
}

val extension: DatagenExtension = extensions.create("datagen")
val generatedResources = layout.projectDirectory.dir("src/main/generatedResources")

val generateData by tasks.registering(GenerateData::class) {
    woodMaterials.convention(extension.woodMaterials)
    stoneMaterials.convention(extension.stoneMaterials)
    output.convention(generatedResources)
    conditionType.convention(extension.conditionType)
}

val deleteDuplicateResources by tasks.registering(DeleteDuplicates::class) {
    generated.convention(generatedResources)
    main.convention(layout.dir(sourceSets.main.map { it.resources.srcDirs.first() }))
}

sourceSets {
    main {
        resources.srcDir(generatedResources)
    }
}
