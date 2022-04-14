package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;

public class ElfSymbolView32
{
    private final ByteBuffer bytes;

    public ElfSymbolView32 (ByteBuffer bytes) { this.bytes = bytes; }

    public int name () { return bytes.getInt(0); }

    public int value () { return bytes.getInt(4); }

    public int size () { return bytes.getInt(8); }

    public byte information () { return bytes.get(12); }

    public byte other () { return bytes.get(13); }

    public int section () { return bytes.getShort(14); }
}
