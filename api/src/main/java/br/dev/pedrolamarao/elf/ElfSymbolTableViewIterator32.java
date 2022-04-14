package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;
import java.util.Iterator;

public class ElfSymbolTableViewIterator32 implements Iterator<ElfSymbolView32>
{
    private final ByteBuffer bytes;

    private int index;

    private final int size;

    public ElfSymbolTableViewIterator32 (ByteBuffer bytes, int index, int size)
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
    public ElfSymbolView32 next ()
    {
        return new ElfSymbolView32( bytes.slice(size * index++, size).order(bytes.order()) );
    }
}
