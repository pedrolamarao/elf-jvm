package br.dev.pedrolamarao.elf

import java.nio.ByteBuffer
import java.nio.channels.FileChannel
import java.nio.file.Path

interface ElfPrologue
{
    fun type () : UByte
}

interface ElfImage32 : ElfPrologue
{
    fun entry () : UInt
}

interface ElfImage64 : ElfPrologue
{
    fun entry () : ULong
}

class ElfImageView32 (private val bytes : ByteBuffer) : ElfImage32
{
    override fun type () = bytes[4].toUByte()

    override fun entry () = bytes.getInt(28).toUInt()
}

class ElfImageView64 (private val bytes : ByteBuffer) : ElfImage64
{
    override fun type () = bytes[4].toUByte()

    override fun entry () = bytes.getInt(28).toULong()
}

class ElfParser
{
    fun view (path : Path) : ElfPrologue?
    {
        FileChannel.open(path).use {
            val bytes = it.map(FileChannel.MapMode.READ_ONLY, 0, 16)
            val isElf = (bytes.remaining() == 16)
                && (bytes[0] == 0x7F.toByte())
                && (bytes[1] == 'E'.code.toByte())
                && (bytes[2] == 'L'.code.toByte())
                && (bytes[3] == 'F'.code.toByte())
            if (! isElf) return null
            else return when (bytes[4]) {
                1.toByte() -> ElfImageView32(bytes)
                2.toByte() -> ElfImageView64(bytes)
                else -> null
            }
        }
    }
}