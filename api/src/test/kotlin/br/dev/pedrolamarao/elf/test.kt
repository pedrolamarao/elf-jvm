import br.dev.pedrolamarao.elf.ElfImage64
import br.dev.pedrolamarao.elf.ElfParser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.fail
import java.nio.file.Paths

class ParserTest
{
    @Test
    fun smoke ()
    {
        val prologue = ElfParser().view( Paths.get( System.getProperty("br.dev.pedrolamarao.elf.sample.path") ) )
        if (prologue !is ElfImage64) { fail("not an ElfImage32") }
        assertEquals(2.toUByte(),prologue.type())
    }
}