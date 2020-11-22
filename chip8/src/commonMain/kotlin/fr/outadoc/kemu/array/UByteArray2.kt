package fr.outadoc.kemu.array

/*
 * Copyright 2010-2020 JetBrains s.r.o. and Kotlin Programming Language contributors.
 * Use of this source code is governed by the Apache 2.0 license that can be found in the license/LICENSE.txt file.
 */
class UByteArray2 constructor(private val storage: ByteArray) : Collection<UByte> {

    /** Creates a new array of the specified [size], with all elements initialized to zero. */
    constructor(size: Int) : this(ByteArray(size))

    /**
     * Returns the array element at the given [index]. This method can be called using the index operator.
     *
     * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except in Kotlin/JS
     * where the behavior is unspecified.
     */
    operator fun get(index: Int): UByte = storage[index].toUByte()

    /**
     * Sets the element at the given [index] to the given [value]. This method can be called using the index operator.
     *
     * If the [index] is out of bounds of this array, throws an [IndexOutOfBoundsException] except in Kotlin/JS
     * where the behavior is unspecified.
     */
    operator fun set(index: Int, value: UByte) {
        storage[index] = value.toByte()
    }

    /** Returns the number of elements in the array. */
    override val size: Int get() = storage.size

    /** Creates an iterator over the elements of the array. */
    override operator fun iterator(): Iterator<UByte> = UByteIterator(storage)

    private class UByteIterator(private val array: ByteArray) : Iterator<UByte> {
        private var index = 0
        override fun hasNext() = index < array.size
        override fun next() =
            if (index < array.size) array[index++].toUByte() else throw NoSuchElementException(index.toString())
    }

    override fun contains(element: UByte): Boolean {
        // TODO: Eliminate this check after KT-30016 gets fixed.
        // Currently JS BE does not generate special bridge method for this method.
        @Suppress("USELESS_CAST")
        if ((element as Any?) !is UByte) return false

        return storage.contains(element.toByte())
    }

    override fun containsAll(elements: Collection<UByte>): Boolean {
        return (elements as Collection<*>).all { it is UByte && storage.contains(it.toByte()) }
    }

    override fun isEmpty(): Boolean = this.storage.size == 0

    override fun toString(): String {
        return joinToString()
    }

    fun copyOf(): UByteArray2 = UByteArray2(storage)

    infix fun contentEquals(other: UByteArray2?): Boolean {
        return storage.contentEquals(other?.storage)
    }

    fun contentHashCode(): Int {
        return storage.contentHashCode()
    }
}

/**
 * Creates a new array of the specified [size], where each element is calculated by calling the specified
 * [init] function.
 *
 * The function [init] is called for each array element sequentially starting from the first one.
 * It should return the value for an array element given its index.
 */
inline fun UByteArray2(size: Int, init: (Int) -> UByte): UByteArray2 {
    return UByteArray2(ByteArray(size) { index -> init(index).toByte() })
}

inline fun ubyteArray2Of(vararg elements: UByte): UByteArray2 =
    UByteArray2(elements.map { it.toByte() }.toByteArray())
