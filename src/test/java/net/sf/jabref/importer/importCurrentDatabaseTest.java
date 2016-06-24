package net.sf.jabref.importer;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

import net.sf.jabref.*;
import net.sf.jabref.importer.fileformat.BibtexImporter;
import net.sf.jabref.importer.fileformat.BibtexImporterTest;
import net.sf.jabref.importer.OutputPrinterToNull;
import net.sf.jabref.model.entry.BibEntry;

public class importCurrentDatabaseTest {

    private BibtexImporter importacao;


    @Before
    public void setUp() {
        Globals.prefs = JabRefPreferences.getInstance();
        importacao = new BibtexImporter();
    }

    @Test
    public void TesteFormatoValido() throws IOException {
        try (InputStream arquivoEntrada = BibtexImporterTest.class.getResourceAsStream("nome.bib")) {
            assertTrue(importacao.isRecognizedFormat(arquivoEntrada));
        }
    }

    @Test
    public void TesteNomeFormato() {
        assertEquals("BibTeX", importacao.getFormatName());
    }

    @Test
    public void TesteExtensao() {
        assertEquals("bib", importacao.getExtensions());
    }

    @Test
    public void TesteEntradaValidas() throws IOException {
        try (InputStream arquivoEntrada = BibtexImporterTest.class.getResourceAsStream("nome.bib")) {
            List<BibEntry> entradas = importacao.importEntries(arquivoEntrada, new OutputPrinterToNull());

            assertEquals(2, entradas.size());

            for (BibEntry entry : entradas) {

                if (entry.getCiteKey().equals("Artigo")) {
                    assertEquals("Alexandre", entry.getField("author"));
                    assertEquals("Artigo", entry.getField("bibtexkey"));
                    assertEquals("2016", entry.getField("year"));
                    assertEquals("AACN Clinical Issues", entry.getField("journal"));
                    assertEquals("Título", entry.getField("title"));

                } else if (entry.getCiteKey().equals("Livro")) {
                    assertEquals("Alexandre", entry.getField("author"));
                    assertEquals("Livro", entry.getField("bibtexkey"));
                    assertEquals("Redator", entry.getField("editor"));
                    assertEquals("Editor", entry.getField("publisher"));
                    assertEquals("Título", entry.getField("title"));
                    assertEquals("2016", entry.getField("year"));
                }
            }
        }
    }
}