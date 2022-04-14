package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;

public class ElfFileView
{
    private final ByteBuffer bytes;

    public ElfFileView (ByteBuffer bytes)
    {
        this.bytes = bytes;
    }

    public ByteBuffer magic () { return bytes.slice(0,4).order(bytes.order()); }

    public byte format () { return bytes.get(4); }

    public byte encoding () { return bytes.get(5); }

    public byte version () { return bytes.get(6); }

    public byte abi () { return bytes.get(7); }

    public byte abiVersion () { return bytes.get(8); }
}