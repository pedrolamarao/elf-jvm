package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;

public class ElfSectionView64
{
    private final ByteBuffer bytes;

    public ElfSectionView64 (ByteBuffer bytes) { this.bytes = bytes; }
    
    public int name () { return bytes.getInt(0); }

    public int type () { return bytes.getInt(4); }

    public long flags () { return bytes.getLong(8); }

    public long address () { return bytes.getLong(16); }

    public long offset () { return bytes.getLong(24); }

    public long size () { return bytes.getLong(32); }

    public int link () { return bytes.getInt(40); }

    public int information () { return bytes.getInt(44); }

    public long alignment () { return bytes.getLong(48); }

    public long entrySize () { return bytes.getLong(56); }
}
