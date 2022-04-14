import br.dev.pedrolamarao.elf.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.nio.ByteBuffer
import java.nio.ByteOrder.LITTLE_ENDIAN
import java.nio.channels.FileChannel
import java.nio.channels.FileChannel.MapMode.READ_ONLY
import java.nio.file.Paths

class ParserTest
{
    @Disabled
    @Test
    fun test32 ()
    {
        FileChannel.open( Paths.get( System.getProperty("br.dev.pedrolamarao.elf.sample.path") ) ).use{
            file ->
            val prologue = ElfFileView( file.map(READ_ONLY,0,16).order(LITTLE_ENDIAN) )
            assertEquals(ByteBuffer.wrap(ElfConstants.MAGIC),prologue.magic())
            assertEquals(1.toByte(),prologue.format())
            assertEquals(1.toByte(),prologue.encoding())
            assertEquals(1.toByte(),prologue.version())
            assertEquals(0.toByte(),prologue.abi())
            assertEquals(0.toByte(),prologue.abiVersion())
            val header = ElfFileView32( file.map(READ_ONLY,0,64).order(LITTLE_ENDIAN) )
            assertEquals(2.toShort(),header.type())
            assertEquals(3.toShort(),header.machine())
            assertEquals(1,header.version())
            assertEquals(0x20000,header.entryAddress())
            assertEquals(52,header.segmentTableOffset())
            header.sectionTableOffset()
            header.flags()
            assertEquals(52,header.size())
            assertEquals(32,header.segmentEntrySize())
            assertEquals(4,header.segmentTableSize())
            assertEquals(40,header.sectionEntrySize())
            assertEquals(6,header.sectionTableSize())
            assertEquals(4,header.sectionNameIndex())
            val sectionTable = ElfSectionTableView32(
                file.map(
                    READ_ONLY,
                    header.sectionTableOffset().toLong(),
                    (header.sectionEntrySize() * header.sectionTableSize()).toLong()
                )
                .order(LITTLE_ENDIAN),
                header.sectionEntrySize().toInt()
            )
            val symbolTables = mutableListOf<ElfSymbolTableView32>()
            for (section in sectionTable) {
                section.name()
                section.type()
                section.flags()
                section.address()
                section.offset()
                section.size()
                section.link()
                section.information()
                section.alignment()
                section.entrySize()
                val bytes = file.map(READ_ONLY, section.offset().toLong(), section.size().toLong()).order(LITTLE_ENDIAN)
                if (section.type() == 2)
                    symbolTables.add( ElfSymbolTableView32(bytes, section.entrySize()) )
            }
            for (symbolTable in symbolTables) {
                for (symbol in symbolTable) {
                    symbol.name()
                    symbol.information()
                    symbol.other()
                    symbol.section()
                    symbol.value()
                    symbol.size()
                }
            }
        }
    }

    @Test
    fun test64 ()
    {
        FileChannel.open( Paths.get( System.getProperty("br.dev.pedrolamarao.elf.sample.path") ) ).use{
            file ->
            val prologue = ElfFileView( file.map(READ_ONLY,0,16).order(LITTLE_ENDIAN) )
            assertEquals(ByteBuffer.wrap(ElfConstants.MAGIC),prologue.magic())
            assertEquals(2.toByte(),prologue.format())
            assertEquals(1.toByte(),prologue.encoding())
            assertEquals(1.toByte(),prologue.version())
            assertEquals(0.toByte(),prologue.abi())
            assertEquals(0.toByte(),prologue.abiVersion())
            val header = ElfFileView64( file.map(READ_ONLY,0,64).order(LITTLE_ENDIAN) )
            assertEquals(2.toShort(),header.type())
            assertEquals(62.toShort(),header.machine())
            assertEquals(1,header.version())
            assertEquals(0x20000,header.entryAddress())
            header.segmentTableOffset()
            header.sectionTableOffset()
            header.flags()
            assertEquals(64,header.size())
            assertEquals(56,header.segmentEntrySize())
            assertEquals(4,header.segmentTableSize())
            assertEquals(64,header.sectionEntrySize())
            assertEquals(6,header.sectionTableSize())
            assertEquals(4,header.sectionNameIndex())
            val sectionTable = ElfSectionTableView64(
                file.map(
                    READ_ONLY,
                    header.sectionTableOffset(),
                    (header.sectionEntrySize() * header.sectionTableSize()).toLong()
                )
                .order(LITTLE_ENDIAN),
                header.sectionEntrySize().toInt()
            )
            val symbolTables = mutableListOf<ElfSymbolTableView64>()
            for (section in sectionTable) {
                section.name()
                section.type()
                section.flags()
                section.address()
                section.offset()
                section.size()
                section.link()
                section.information()
                section.alignment()
                section.entrySize()
                val bytes = file.map(READ_ONLY, section.offset(), section.size()).order(LITTLE_ENDIAN)
                if (section.type() == 2)
                    symbolTables.add( ElfSymbolTableView64(bytes,section.entrySize().toInt()) )
            }
            for (symbolTable in symbolTables) {
                for (symbol in symbolTable) {
                    symbol.name()
                    symbol.information()
                    symbol.other()
                    symbol.section()
                    symbol.value()
                    symbol.size()
                }
            }
        }
    }
}
