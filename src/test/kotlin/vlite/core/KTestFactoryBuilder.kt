package vlite.core

import org.junit.jupiter.api.DynamicTest
import java.util.*

open class KTestFactoryBuilder(private val prefix: String) {

    private val mutableList = LinkedList<DynamicTest>()
    val list get() = mutableList.toList()

    operator fun String.invoke(testBlock: (displayName: String) -> Unit) {
        mutableList += DynamicTest.dynamicTest("$prefix$this") { testBlock(this) }
    }

    fun testGroup(groupName: String, block: KTestFactoryBuilder.() -> Unit) {
        mutableList += DynamicTest.dynamicTest("~~~ $groupName") { }
        mutableList += kTestFactory(".   ", block)
        mutableList += DynamicTest.dynamicTest("~~~") { }
    }

}

fun kTestFactory(prefix: String = "", block: KTestFactoryBuilder.() -> Unit): List<DynamicTest> =
    KTestFactoryBuilder(prefix).apply(block).list
