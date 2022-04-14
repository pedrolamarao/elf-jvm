package br.dev.pedrolamarao.elf;

import java.nio.ByteBuffer;
import java.util.Iterator;

public class ElfSectionTableView64 implements Iterable<ElfSectionView64>
{
    private final ByteBuffer bytes;

    private final int size;

    public ElfSectionTableView64 (ByteBuffer bytes, int size)
    {
        this.bytes = bytes;
        this.size = size;
    }

    @Override
    public Iterator<ElfSectionView64> iterator ()
    {
        return new ElfSectionTableViewIterator64(bytes, 0, size);
    }
}
