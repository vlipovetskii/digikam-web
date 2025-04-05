package vlite.core

import org.junit.jupiter.api.DynamicTest
import java.util.*

open class KTestFactoryBuilder {

    private val mutableList = LinkedList<DynamicTest>()
    val list get() = mutableList.toList()

    operator fun String.invoke(testBlock: (displayName: String) -> Unit) {
        mutableList += DynamicTest.dynamicTest(this) { testBlock(this) }
    }

}

fun kTestFactory(block: KTestFactoryBuilder.() -> Unit): List<DynamicTest> =
    KTestFactoryBuilder().apply(block).list
