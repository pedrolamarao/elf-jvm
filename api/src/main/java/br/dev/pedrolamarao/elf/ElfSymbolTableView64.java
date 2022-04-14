package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;
import java.util.Iterator;

public class ElfSymbolTableView64 implements Iterable<ElfSymbolView64>
{
    private final ByteBuffer bytes;

    private final int size;

    public ElfSymbolTableView64 (ByteBuffer bytes, int size)
    {
        this.bytes = bytes;
        this.size = size;
    }

    @Override
    public Iterator<ElfSymbolView64> iterator ()
    {
        return new ElfSymbolTableViewIterator64(bytes, 0, size);
    }
}
