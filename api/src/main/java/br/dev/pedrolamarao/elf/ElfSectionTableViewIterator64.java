package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;
import java.util.Iterator;

public class ElfSectionTableViewIterator64 implements Iterator<ElfSectionView64>
{
    private final ByteBuffer bytes;

    private int index;

    private final int size;

    public ElfSectionTableViewIterator64 (ByteBuffer bytes, int index, int size)
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
    public ElfSectionView64 next ()
    {
        return new ElfSectionView64( bytes.slice(size * index++, size).order(bytes.order()) );
    }
}
