package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;
import java.util.Iterator;

public class ElfSymbolTableViewIterator64 implements Iterator<ElfSymbolView64>
{
    private final ByteBuffer bytes;

    private int index;

    private final int size;

    public ElfSymbolTableViewIterator64 (ByteBuffer bytes, int index, int size)
    {
        this.bytes = bytes;
        this.index = index;
        this.size = size;
    }

    @Override
    public boolean hasNext ()
    {
        return size * index < bytes.remaining();
    }

    @Override
    public ElfSymbolView64 next ()
    {
        return new ElfSymbolView64( bytes.slice(size * index++, size).order(bytes.order()) );
    }
}
