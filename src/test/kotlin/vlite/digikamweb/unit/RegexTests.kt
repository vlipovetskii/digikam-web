package vlite.digikamweb.unit

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import vlite.digikamweb.backend.base.storage.BaseFileStorageA
import java.util.regex.Pattern
import kotlin.streams.asStream

open class RegexTests {

    companion object {
        val VALID_REGEX_PATTERN: Pattern = Pattern.compile(BaseFileStorageA.Companion.NAME_VALID_REGEX)
    }

    @TestFactory
    fun testValidNamesFactory() = sequenceOf(
        "validName",
        "NameWithSpaces",
        "NameWith-Hyphen",
        "NameWith_Underscore",
        "12345",
        "a",
        "A",
        "你好世界"
    ).map { input ->
        DynamicTest.dynamicTest("'$input' should be valid") {
            Assertions.assertTrue(VALID_REGEX_PATTERN.matcher(input).matches())
        }
    }.asStream()

    @TestFactory
    fun testInvalidNamesWithSlashFactory() = sequenceOf(
        "invalid/Name",
        "Name/",
        "/Name",
        "part1/part2/part3"
    ).map { input ->
        DynamicTest.dynamicTest("'$input' should be invalid due to slash") {
            Assertions.assertFalse(VALID_REGEX_PATTERN.matcher(input).matches())
        }
    }.asStream()

    @TestFactory
    fun testInvalidNamesWithNullCharacterFactory() = sequenceOf(
        "invalid\u0000Name",
        "Name\u0000",
        "\u0000Name",
        "part1\u0000part2"
    ).map { input ->
        DynamicTest.dynamicTest("'$input' should be invalid due to null character") {
            Assertions.assertFalse(VALID_REGEX_PATTERN.matcher(input).matches())
        }
    }.asStream()

    @TestFactory
    fun testStringWithOtherSpecialCharactersFactory() = sequenceOf(
        "NameWith.Dot",
        "NameWith@Symbol",
        "NameWith\$Dollar",
        "NameWith%Percent",
        "NameWith^Caret",
        "NameWith&Ampersand",
        "NameWith*Asterisk",
        "NameWith()Parentheses",
        "NameWith+Plus",
        "NameWith=Equals",
        "NameWith{}",
        "NameWith[]Brackets",
        "NameWith;Semicolon",
        "NameWith'Apostrophe",
        "NameWith`Grave",
        "NameWith~Tilde",
        "NameWith!Exclamation",
        "NameWith#Hash",
        "NameWith:Colon",
        "NameWith\"Quote",
        "NameWith<LessThan",
        "NameWith>GreaterThan",
        "NameWith?Question",
        "NameWith\\Backslash"
    ).map { input ->
        DynamicTest.dynamicTest("'$input' with other special characters should be valid") {
            Assertions.assertTrue(VALID_REGEX_PATTERN.matcher(input).matches())
        }
    }.asStream()

}