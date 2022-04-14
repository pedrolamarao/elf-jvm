package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;

public class ElfFileView64
{
    public static final int BYTES = 64;
    private final ByteBuffer bytes;

    public ElfFileView64 (ByteBuffer bytes) { this.bytes = bytes; }

    public int type () { return bytes.getShort(16); }

    public int machine () { return bytes.getShort(18); }

    public int version () { return bytes.getInt(20); }

    public long entryAddress () { return bytes.getLong(24); }

    public long segmentTableOffset () { return bytes.getLong(32); }

    public long sectionTableOffset () { return bytes.getLong(40); }

    public int flags () { return bytes.getInt(48); }

    public int size () { return bytes.getShort(52); }

    public int segmentEntrySize () { return bytes.getShort(54); }

    public int segmentTableSize () { return bytes.getShort(56); }

    public int sectionEntrySize () { return bytes.getShort(58); }

    public int sectionTableSize () { return bytes.getShort(60); }

    public int sectionNameIndex () { return bytes.getShort(62); }
}
