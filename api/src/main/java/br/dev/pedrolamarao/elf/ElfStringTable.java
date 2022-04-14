package br.dev.pedrolamarao.elf;

import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;

import static java.nio.charset.StandardCharsets.US_ASCII;

public class ElfStringTable
{
    private final ByteBuffer bytes;

    public ElfStringTable(ByteBuffer bytes)
    {
        this.bytes = bytes;
    }

    public CharSequence get (int index)
    {
        if (index > bytes.remaining()) throw new BufferOverflowException();
        var p = index;
        while (p < bytes.remaining() && bytes.get(p) != 0) ++p;
        return US_ASCII.decode( bytes.slice(index, p - index) );
    }
}
