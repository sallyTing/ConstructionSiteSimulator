package org.example.constructionsitesimulator;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class FileReaderTest {
    @Test
    public void shouldReadTestSiteMapFromTheTestResourcesDirectory() throws FileNotFoundException {
        // Given
        FileReader reader = new FileReader();

        // When
        List<String> siteMap = reader.readFile("src/test/resources/input.txt");

        // Then
        assertThat(siteMap.size()).isEqualTo(5);
        assertThat(siteMap.get(0)).isEqualTo("o o t o o o o o o o");
        assertThat(siteMap.get(1)).isEqualTo("o o o o o o o T o o");
        assertThat(siteMap.get(2)).isEqualTo("r r r o o o o T o o");
        assertThat(siteMap.get(3)).isEqualTo("r r r r o o o o o o");
        assertThat(siteMap.get(4)).isEqualTo("r r r r r t o o o o");
    }
}
