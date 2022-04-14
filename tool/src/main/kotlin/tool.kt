import br.dev.pedrolamarao.elf.*
import java.lang.System.err
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel
import java.nio.channels.FileChannel.MapMode.READ_ONLY
import java.nio.file.Path
import kotlin.system.exitProcess

fun find32 (file : FileChannel, byteOrder : ByteOrder, target : String) : Int
{
    val header = ElfFileView32( file.map(READ_ONLY,0,52).order(byteOrder) )

    val sectionTable = ElfSectionTableView32(
        file.map(
            READ_ONLY, header.sectionTableOffset().toLong(), (header.sectionEntrySize() * header.sectionTableSize()).toLong()
        )
        .order(byteOrder),
        header.sectionEntrySize()
    )

    var stringTable : ElfStringTable? = null
    val symbolTables = mutableMapOf<Int,ElfSymbolTableView32>()

    for ((index,section) in sectionTable.withIndex()) {
        val bytes = file.map(READ_ONLY, section.offset().toLong(), section.size().toLong()).order(byteOrder)
        when (section.type()) {
            2 -> symbolTables[index] = ElfSymbolTableView32(bytes, section.entrySize())
            3 -> stringTable = ElfStringTable(bytes)
        }
    }

    if (stringTable == null) return -1

    for ((_,symbolTable) in symbolTables) {
        for (symbol in symbolTable) {
            val name = stringTable.get(symbol.name())
            if (name != target) continue
            err.printf("name: %s%n", name)
            err.printf("section: %d%n", symbol.section())
            err.printf("value: 0x%X%n", symbol.value())
            err.printf("size: %d%n", symbol.size())
            err.printf("information: %d%n", symbol.information())
            err.printf("other: %d%n", symbol.other())
            return 0
        }
    }

     return 1
}

fun find64 (file : FileChannel, byteOrder : ByteOrder, target : String) : Int
{
    val header = ElfFileView64( file.map(READ_ONLY,0,64).order(byteOrder) )

    val sectionTable = ElfSectionTableView64(
        file.map(
            READ_ONLY, header.sectionTableOffset(), (header.sectionEntrySize() * header.sectionTableSize()).toLong()
        )
        .order(byteOrder),
        header.sectionEntrySize()
    )

    var stringTable : ElfStringTable? = null
    val symbolTables = mutableMapOf<Int,ElfSymbolTableView64>()

    for ((index,section) in sectionTable.withIndex()) {
        val bytes = file.map(READ_ONLY, section.offset(), section.size()).order(byteOrder)
        when (section.type()) {
            2 -> symbolTables[index] = ElfSymbolTableView64(bytes,section.entrySize().toInt())
            3 -> stringTable = ElfStringTable(bytes)
        }
    }

    if (stringTable == null) return -1

    for ((_,symbolTable) in symbolTables) {
        for (symbol in symbolTable) {
            val name = stringTable.get(symbol.name())
            if (! name.contentEquals(target)) continue
            err.printf("name: %s%n", name)
            err.printf("section: %d%n", symbol.section())
            err.printf("value: 0x%X%n", symbol.value())
            err.printf("size: %d%n", symbol.size())
            err.printf("information: %d%n", symbol.information())
            err.printf("other: %d%n", symbol.other())
            return 0
        }
    }

    return 1
}

fun main (args : Array<String>)
{
    if (args.size != 2) {
        err.printf("usage: tool symbol file%n")
        exitProcess(-1)
    }

    val symbol = args[0]
    val file = Path.of(args[1])

    FileChannel.open(file).use {
        val elf = ElfFileView(it.map(READ_ONLY,0,16))
        if (elf.magic() != ByteBuffer.wrap(Elf.MAGIC)) throw RuntimeException("not ELF")
        val byteOrder = when (elf.encoding().toInt()) {
            1 -> ByteOrder.LITTLE_ENDIAN
            2 -> ByteOrder.BIG_ENDIAN
            else -> throw RuntimeException("unknown encoding")
        }
        when (elf.format().toInt()) {
            1 -> exitProcess( find32(it,byteOrder,symbol) )
            2 -> exitProcess( find64(it,byteOrder,symbol) )
            else -> throw RuntimeException("unknown format")
        }
    }
}