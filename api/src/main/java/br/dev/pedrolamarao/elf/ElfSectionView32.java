package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;

public class ElfSectionView32
{
    public static final int BYTES = 40;

    private final ByteBuffer bytes;

    public ElfSectionView32 (ByteBuffer bytes) { this.bytes = bytes; }
    
    public int name () { return bytes.getInt(0); }

    public int type () { return bytes.getInt(4); }

    public int flags () { return bytes.getInt(8); }

    public int address () { return bytes.getInt(12); }

    public int offset () { return bytes.getInt(16); }

    public int size () { return bytes.getInt(20); }

    public int link () { return bytes.getInt(24); }

    public int information () { return bytes.getInt(28); }

    public int alignment () { return bytes.getInt(32); }

    public int entrySize () { return bytes.getInt(36); }
}
