package br.dev.pedrolamarao.elf

import java.nio.BufferOverflowException
import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets.US_ASCII

object ElfConstants
{
    @JvmStatic
    val MAGIC = arrayOf<Byte>(0x7F, 0x45, 0x4C, 0x46).toByteArray()
}

class ElfFileView (private val bytes : ByteBuffer)
{
    fun magic (): ByteBuffer = bytes.slice(0,4).order(bytes.order())

    fun format () = bytes[4]

    fun encoding () = bytes[5]

    fun version () = bytes[6]

    fun abi () = bytes[7]

    fun abiVersion () = bytes[8]
}

class ElfFileView32 (private val bytes : ByteBuffer)
{
    fun type () = bytes.getShort(16)

    fun machine () = bytes.getShort(18)

    fun version () = bytes.getInt(20)

    fun entryAddress () = bytes.getInt(24)

    fun segmentTableOffset () = bytes.getInt(28)

    fun sectionTableOffset () = bytes.getInt(32)

    fun flags () = bytes.getInt(36)

    fun size () = bytes.getShort(40)

    fun segmentEntrySize () = bytes.getShort(42)

    fun segmentTableSize () = bytes.getShort(44)

    fun sectionEntrySize () = bytes.getShort(46)

    fun sectionTableSize () = bytes.getShort(48)

    fun sectionNameIndex () = bytes.getShort(50)
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

class ElfSectionView32 (private val bytes : ByteBuffer)
{
    fun name () = bytes.getInt(0)

    fun type () = bytes.getInt(4)

    fun flags () = bytes.getInt(8)

    fun address () = bytes.getInt(12)

    fun offset () = bytes.getInt(16)

    fun size () = bytes.getInt(20)

    fun link () = bytes.getInt(24)

    fun information () = bytes.getInt(28)

    fun alignment () = bytes.getInt(32)

    fun entrySize () = bytes.getInt(36)
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

class ElfSymbolView32 (private val bytes : ByteBuffer)
{
    fun name () = bytes.getInt(0)

    fun value () = bytes.getInt(4)

    fun size () = bytes.getInt(8)

    fun information () = bytes.get(12)

    fun other () = bytes.get(13)

    fun section () = bytes.getShort(14)
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

class ElfStringTable (private val bytes : ByteBuffer)
{
    fun get (index : Int) : CharSequence {
        if (index > bytes.remaining()) throw BufferOverflowException()
        var p = index
        while (p < bytes.remaining() && bytes[p] != 0.toByte()) ++p
        return US_ASCII.decode( bytes.slice(index,p-index) )
    }
}

class ElfSectionTableView32 (private val bytes : ByteBuffer, private val size : Int) : Iterable<ElfSectionView32>
{
    override fun iterator() = ElfSectionTableViewIterator32(bytes, size, 0)
}

class ElfSectionTableView64 (private val bytes : ByteBuffer, private val size : Int) : Iterable<ElfSectionView64>
{
    override fun iterator() = ElfSectionTableViewIterator64(bytes, size, 0)
}

class ElfSectionTableViewIterator32 (private val bytes : ByteBuffer, private val size : Int, private var index : Int) : Iterator<ElfSectionView32>
{
    override fun hasNext () = size*index < bytes.remaining()

    override fun next () = ElfSectionView32( bytes.slice(size*index++,size).order(bytes.order()) )
}

class ElfSectionTableViewIterator64 (private val bytes : ByteBuffer, private val size : Int, private var index : Int) : Iterator<ElfSectionView64>
{
    override fun hasNext () = size*index < bytes.remaining()

    override fun next () = ElfSectionView64(
        bytes.slice(size*index++,size).order(bytes.order())
    )
}

class ElfSymbolTableView32 (private val bytes : ByteBuffer, private val size : Int) : Iterable<ElfSymbolView32>
{
    override fun iterator () = ElfSymbolTableViewIterator32(bytes, size, 0)
}

class ElfSymbolTableView64 (private val bytes : ByteBuffer, private val size : Int) : Iterable<ElfSymbolView64>
{
    override fun iterator () = ElfSymbolTableViewIterator64(bytes, size, 0)
}

class ElfSymbolTableViewIterator32 (private val bytes : ByteBuffer, private val size : Int, private var index : Int) :
    Iterator<ElfSymbolView32>
{
    override fun hasNext () = size*index < bytes.remaining()

    override fun next () = ElfSymbolView32( bytes.slice(size*index++,size).order(bytes.order()) )
}

class ElfSymbolTableViewIterator64 (private val bytes : ByteBuffer, private val size : Int, private var index : Int) :
    Iterator<ElfSymbolView64>
{
    override fun hasNext () = size*index < bytes.remaining()

    override fun next () = ElfSymbolView64( bytes.slice(size*index++,size).order(bytes.order()) )
}