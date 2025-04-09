package vlite.digikamweb.unit

import org.junit.jupiter.api.Nested

open class AllAppUnitTests {

	@Nested inner class RegexTestsNested : RegexTests()
	@Nested inner class FileTenantStorageTestNested : FileTenantStorageTest()

}
