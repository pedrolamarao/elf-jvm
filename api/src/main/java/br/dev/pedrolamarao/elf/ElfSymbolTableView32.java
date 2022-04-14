package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;
import java.util.Iterator;

public class ElfSymbolTableView32 implements Iterable<ElfSymbolView32>
{
    private final ByteBuffer bytes;

    private final int size;

    public ElfSymbolTableView32 (ByteBuffer bytes, int size)
    {
        this.bytes = bytes;
        this.size = size;
    }

    @Override
    public Iterator<ElfSymbolView32> iterator ()
    {
        return new ElfSymbolTableViewIterator32(bytes, 0, size);
    }
}
