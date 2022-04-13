package br.dev.pedrolamarao.elf

import java.nio.ByteBuffer

class ElfFileView (private val bytes : ByteBuffer)
{
    fun magic (): ByteBuffer = bytes.slice(0,4).order(bytes.order())

    fun format () = bytes[4]

    fun encoding () = bytes[5]

    fun version () = bytes[6]

    fun abi () = bytes[7]

    fun abiVersion () = bytes[8]
}

class ElfFileView64 (private val bytes : ByteBuffer)
{
    fun type () = bytes.getShort(16)

    fun machine () = bytes.getShort(18)

    fun version () = bytes.getInt(20)

    fun entryAddress () = bytes.getLong(24)

    fun segmentTableOffset () = bytes.getLong(32)

    fun sectionTableOffset () = bytes.getLong(40)

    fun flags () = bytes.getInt(48)

    fun size () = bytes.getShort(52)

    fun segmentEntrySize () = bytes.getShort(54)

    fun segmentTableSize () = bytes.getShort(56)

    fun sectionEntrySize () = bytes.getShort(58)

    fun sectionTableSize () = bytes.getShort(60)

    fun sectionNameIndex () = bytes.getShort(62)
}

class ElfSectionView64 (private val bytes : ByteBuffer)
{
    fun name () = bytes.getInt(0)

    fun type () = bytes.getInt(4)

    fun flags () = bytes.getLong(8)

    fun address () = bytes.getLong(16)

    fun offset () = bytes.getLong(24)

    fun size () = bytes.getLong(32)

    fun link () = bytes.getInt(40)

    fun information () = bytes.getInt(44)

    fun alignment () = bytes.getLong(48)

    fun entrySize () = bytes.getLong(56)
}

class ElfSymbolView64 (private val bytes : ByteBuffer)
{
    fun name () = bytes.getInt(0)

    fun information () = bytes.get(4)

    fun other () = bytes.get(5)

    fun section () = bytes.getShort(6)

    fun value () = bytes.getLong(8)

    fun size () = bytes.getLong(16)
}

class ElfSectionTableView64 (private val bytes : ByteBuffer, private val size : Int) : Iterable<ElfSectionView64>
{
    override fun iterator() = ElfSectionTableViewIterator64(bytes, size, 0)
}

class ElfSectionTableViewIterator64 (private val bytes : ByteBuffer, private val size : Int, private var index : Int) : Iterator<ElfSectionView64>
{
    override fun hasNext () = size*index < bytes.remaining()

    override fun next () = ElfSectionView64(
        bytes.slice(size*index++,size).order(bytes.order())
    )
}

class ElfSymbolTableView64 (private val bytes : ByteBuffer, private val size : Int) : Iterable<ElfSymbolView64>
{
    override fun iterator() = ElfSymbolTableViewIterator64(bytes, size, 0)
}

class ElfSymbolTableViewIterator64 (private val bytes : ByteBuffer, private val size : Int, private var index : Int) : Iterator<ElfSymbolView64>
{
    override fun hasNext () = size*index < bytes.remaining()

    override fun next () = ElfSymbolView64(
        bytes.slice(size*index++,size).order(bytes.order())
    )
}