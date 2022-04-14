package br.dev.pedrolamarao.elf;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.util.ArrayList;

import static java.nio.ByteOrder.LITTLE_ENDIAN;
import static java.nio.channels.FileChannel.MapMode.READ_ONLY;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest
{
    @Disabled
    @Test
    void test32 () throws Exception
    {
        try (var file = FileChannel.open( Paths.get( System.getProperty("br.dev.pedrolamarao.elf.sample.path") ) ))
         {
            final var prologue = new ElfFileView( file.map(READ_ONLY,0,16).order(LITTLE_ENDIAN) );
            assertEquals(ByteBuffer.wrap(Elf.MAGIC),prologue.magic());
            assertEquals(1,prologue.format());
            assertEquals(1,prologue.encoding());
            assertEquals(1,prologue.version());
            assertEquals(0,prologue.abi());
            assertEquals(0,prologue.abiVersion());
            final var header = new ElfFileView32( file.map(READ_ONLY,0,64).order(LITTLE_ENDIAN) );
            assertEquals(2,header.type());
            assertEquals(3,header.machine());
            assertEquals(1,header.version());
            assertEquals(0x20000,header.entryAddress());
            assertEquals(52,header.segmentTableOffset());
            header.sectionTableOffset();
            header.flags();
            assertEquals(52,header.size());
            assertEquals(32,header.segmentEntrySize());
            assertEquals(4,header.segmentTableSize());
            assertEquals(40,header.sectionEntrySize());
            assertEquals(6,header.sectionTableSize());
            assertEquals(4,header.sectionNameIndex());
            final var sectionTable = new ElfSectionTableView32(
                file.map(
                    READ_ONLY,
                    header.sectionTableOffset(),
                    (long) header.sectionEntrySize() * header.sectionTableSize()
                )
                .order(LITTLE_ENDIAN),
                header.sectionEntrySize()
            );
            final var symbolTables = new ArrayList<ElfSymbolTableView32>();
            for (var section : sectionTable) {
                section.name();
                section.type();
                section.flags();
                section.address();
                section.offset();
                section.size();
                section.link();
                section.information();
                section.alignment();
                section.entrySize();
                final var bytes = file.map(READ_ONLY, section.offset(), section.size()).order(LITTLE_ENDIAN);
                if (section.type() == 2)
                    symbolTables.add( new ElfSymbolTableView32(bytes, section.entrySize()) );
            }
            for (var symbolTable : symbolTables) {
                for (var symbol : symbolTable) {
                    symbol.name();
                    symbol.information();
                    symbol.other();
                    symbol.section();
                    symbol.value();
                    symbol.size();
                }
            }
        }
    }

    @Test
    void test64 () throws Exception
    {
        try (var file = FileChannel.open( Paths.get( System.getProperty("br.dev.pedrolamarao.elf.sample.path") ) ))
        {
            final var prologue = new ElfFileView( file.map(READ_ONLY,0,16).order(LITTLE_ENDIAN) );
            assertEquals(ByteBuffer.wrap(Elf.MAGIC),prologue.magic());
            assertEquals(2,prologue.format());
            assertEquals(1,prologue.encoding());
            assertEquals(1,prologue.version());
            assertEquals(0,prologue.abi());
            assertEquals(0,prologue.abiVersion());
            final var header = new ElfFileView64( file.map(READ_ONLY,0,64).order(LITTLE_ENDIAN) );
            assertEquals(2,header.type());
            assertEquals(62,header.machine());
            assertEquals(1,header.version());
            assertEquals(0x20000,header.entryAddress());
            header.segmentTableOffset();
            header.sectionTableOffset();
            header.flags();
            assertEquals(64,header.size());
            assertEquals(56,header.segmentEntrySize());
            assertEquals(4,header.segmentTableSize());
            assertEquals(64,header.sectionEntrySize());
            assertEquals(6,header.sectionTableSize());
            assertEquals(4,header.sectionNameIndex());
            final var sectionTable = new ElfSectionTableView64(
                file.map(
                    READ_ONLY,
                    header.sectionTableOffset(),
                    (long) header.sectionEntrySize() * header.sectionTableSize()
                )
                .order(LITTLE_ENDIAN),
                header.sectionEntrySize()
            );
            final var symbolTables = new ArrayList<ElfSymbolTableView64>();
            for (var section : sectionTable) {
                section.name();
                section.type();
                section.flags();
                section.address();
                section.offset();
                section.size();
                section.link();
                section.information();
                section.alignment();
                section.entrySize();
                final var bytes = file.map(READ_ONLY, section.offset(), section.size()).order(LITTLE_ENDIAN);
                if (section.type() == 2)
                    symbolTables.add( new ElfSymbolTableView64(bytes, (int) section.entrySize()) );
            }
            for (var symbolTable : symbolTables) {
                for (var symbol : symbolTable) {
                    symbol.name();
                    symbol.information();
                    symbol.other();
                    symbol.section();
                    symbol.value();
                    symbol.size();
                }
            }
        }
    }
}
