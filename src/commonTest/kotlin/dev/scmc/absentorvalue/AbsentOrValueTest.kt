package dev.scmc.absentorvalue

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class AbsentOrValueTest{

    data class Foo(
        val property: String,
        val updatableProperty: String,
        val nullableUpdatableProperty: String?,
    )

    data class FooPatchModel(
        val updatableProperty: AbsentOrValue<String> = Absent,
        val nullableUpdatableProperty: AbsentOrValue<String?> = Absent,
    )

    @Test
    fun test(){
        val fooInstance = Foo(
            property = "This property is constant and always defined",
            updatableProperty = "There is something",
            nullableUpdatableProperty = null,
        )
        println(fooInstance.toString())

        val patchRequest = FooPatchModel(
            nullableUpdatableProperty = WithValue("There is something now too")
        )
        println(patchRequest.toString())

        val fooInstance2 = fooInstance.copy(
            updatableProperty = if(patchRequest.updatableProperty is WithValue)
                patchRequest.updatableProperty.value else fooInstance.updatableProperty,
            nullableUpdatableProperty = if(patchRequest.nullableUpdatableProperty is WithValue)
                patchRequest.nullableUpdatableProperty.value else fooInstance.nullableUpdatableProperty
        )
        println(fooInstance2.toString())

        assertEquals(fooInstance.property, fooInstance2.property)
        assertEquals(fooInstance.updatableProperty, fooInstance2.updatableProperty)
        assertIs<WithValue<String?>>(patchRequest.nullableUpdatableProperty)
        assertEquals((patchRequest.nullableUpdatableProperty.value), fooInstance2.nullableUpdatableProperty)
    }
}