package vlite.digikamweb.domain.services.storage

import vlite.digikamweb.domain.objects.PersonName
import vlite.digikamweb.domain.services.PhotoPersonMetadataA

/**
 *
 * [Kotlin 101: Context Receivers Quickly Explained](https://rockthejvm.com/articles/kotlin-101-context-receivers)
 * [A Guide to Kotlin Context Receivers](https://www.baeldung.com/kotlin/context-receivers)
 * [Comprehensive Guide To Kotlin Context Receiver](https://medium.com/@hzolfagharipour/comprehensive-guide-to-kotlin-context-receiver-f5478eea6b42)
 * TODO Migrate [renamePersonName] to Context Receivers after them stop being experimental feature.
 *
 * context(PhotoPersonMetadataA, PhotoStorageA)
 */
fun PhotoStorageA.renamePersonName(
    photoPersonMetadata: PhotoPersonMetadataA,
    oldPersonName: PersonName,
    newPersonName: PersonName
): Int {
    with(photoPersonMetadata) {
        var count = 0

        allPhotos()
            .forEach { photo ->
                if (oldPersonName in photo.personNames) {
                    addPersonName(photo, newPersonName)
                    removePersonName(photo, oldPersonName)
                    count++
                }
            }

        return count
    }
}

