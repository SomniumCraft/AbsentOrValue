package dev.scmc

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = AbsentOrValueSerializer::class)
sealed interface AbsentOrValue<out T>

data object Absent : AbsentOrValue<Nothing>

class WithValue<T>(val value: T) : AbsentOrValue<T> {
    override fun toString(): String = value.toString()
}

open class AbsentOrValueSerializer<T>(private val valueSerializer: KSerializer<T>) :
    KSerializer<AbsentOrValue<T>> {

    override val descriptor: SerialDescriptor = valueSerializer.descriptor

    override fun deserialize(decoder: Decoder): AbsentOrValue<T> {
        return WithValue(valueSerializer.deserialize(decoder))
    }

    override fun serialize(encoder: Encoder, value: AbsentOrValue<T>) {
        when (value) {
            is Absent -> {}
            is WithValue -> valueSerializer.serialize(encoder, value.value)
        }
    }
}
