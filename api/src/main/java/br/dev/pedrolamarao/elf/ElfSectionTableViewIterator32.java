package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;
import java.util.Iterator;

public class ElfSectionTableViewIterator32 implements Iterator<ElfSectionView32>
{
    private final ByteBuffer bytes;

    private int index;

    private final int size;

    public ElfSectionTableViewIterator32 (ByteBuffer bytes, int index, int size)
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
    public ElfSectionView32 next ()
    {
        return new ElfSectionView32( bytes.slice(size * index++, size).order(bytes.order()) );
    }
}
