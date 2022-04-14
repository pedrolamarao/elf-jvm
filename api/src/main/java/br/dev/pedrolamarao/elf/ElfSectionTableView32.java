package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;
import java.util.Iterator;

public class ElfSectionTableView32 implements Iterable<ElfSectionView32>
{
    private final ByteBuffer bytes;

    private final int size;

    public ElfSectionTableView32 (ByteBuffer bytes, int size)
    {
        this.bytes = bytes;
        this.size = size;
    }

    @Override
    public Iterator<ElfSectionView32> iterator ()
    {
        return new ElfSectionTableViewIterator32(bytes, 0, size);
    }
}
