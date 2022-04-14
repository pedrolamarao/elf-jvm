package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;

public class ElfSymbolView64
{
    private final ByteBuffer bytes;

    public ElfSymbolView64 (ByteBuffer bytes) { this.bytes = bytes; }

    public int name () { return bytes.getInt(0); }

    public byte information () { return bytes.get(4); }

    public byte other () { return bytes.get(5); }

    public int section () { return bytes.getShort(6); }

    public int value () { return bytes.getInt(8); }

    public int size () { return bytes.getInt(16); }
}
