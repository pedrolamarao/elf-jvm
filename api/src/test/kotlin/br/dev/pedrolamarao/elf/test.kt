import br.dev.pedrolamarao.elf.ElfFileView
import br.dev.pedrolamarao.elf.ElfFileView64
import br.dev.pedrolamarao.elf.ElfSectionTableView64
import br.dev.pedrolamarao.elf.ElfSymbolTableView64
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.nio.ByteBuffer
import java.nio.ByteOrder.LITTLE_ENDIAN
import java.nio.channels.FileChannel
import java.nio.channels.FileChannel.MapMode.READ_ONLY
import java.nio.file.Paths

private val magic = arrayOf<Byte>(0x7F, 0x45, 0x4C, 0x46).toByteArray()

class ParserTest
{
    @Test
    fun smoke ()
    {
        FileChannel.open( Paths.get( System.getProperty("br.dev.pedrolamarao.elf.sample.path") ) ).use{
            file ->
            val prologue = ElfFileView( file.map(READ_ONLY,0,16).order(LITTLE_ENDIAN) )
            assertEquals(ByteBuffer.wrap(magic),prologue.magic())
            assertEquals(2.toByte(),prologue.format())
            assertEquals(1.toByte(),prologue.encoding())
            assertEquals(1.toByte(),prologue.version())
            assertEquals(0.toByte(),prologue.abi())
            assertEquals(0.toByte(),prologue.abiVersion())
            val header = ElfFileView64( file.map(READ_ONLY,0,64).order(LITTLE_ENDIAN) )
            assertEquals(2.toShort(),header.type())
            assertEquals(62.toShort(),header.machine())
            assertEquals(1,header.version())
            header.entryAddress()
            header.segmentTableOffset()
            header.sectionTableOffset()
            header.flags()
            header.size()
            header.segmentEntrySize()
            header.segmentTableSize()
            header.sectionEntrySize()
            header.sectionTableSize()
            header.sectionNameIndex()
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
