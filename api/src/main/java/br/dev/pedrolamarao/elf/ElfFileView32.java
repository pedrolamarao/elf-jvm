package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;

public class ElfFileView32
{
    public static final int BYTES = 52;

    private final ByteBuffer bytes;

    public ElfFileView32 (ByteBuffer bytes) { this.bytes = bytes; }

    public int type () { return bytes.getShort(16); }

    public int machine () { return bytes.getShort(18); }

    public int version () { return bytes.getInt(20); }

    public int entryAddress () { return bytes.getInt(24); }

    public int segmentTableOffset () { return bytes.getInt(28); }

    public int sectionTableOffset () { return bytes.getInt(32); }

    public int flags () { return bytes.getInt(36); }

    public int size () { return bytes.getShort(40); }

    public int segmentEntrySize () { return bytes.getShort(42); }

    public int segmentTableSize () { return bytes.getShort(44); }

    public int sectionEntrySize () { return bytes.getShort(46); }

    public int sectionTableSize () { return bytes.getShort(48); }

    public int sectionNameIndex () { return bytes.getShort(50); }
}
