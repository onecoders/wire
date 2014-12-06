package com.squareup.wire.compiler.plugin.java;

import com.google.common.collect.ImmutableSet;
import com.squareup.javawriter.ClassName;
import com.squareup.javawriter.ClassWriter;
import java.io.IOException;
import java.util.Set;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public final class ExtensionRegistryWriterTest {
  // TODO better tests!

  @Test public void litmus() throws IOException {
    String expected = ""
        + "package com.example;\n"
        + "\n"
        + "import com.One;\n"
        + "import java.util.Arrays;\n"
        + "import java.util.Collections;\n"
        + "import java.util.List;\n"
        + "import net.Two;\n"
        + "import org.Three;\n"
        + "\n"
        + "public final class Registry {\n"
        + "  public static final List<?> EXTENSIONS = Collections.unmodifiableList(Arrays.asList(One.class, Two.class, Three.class));\n"
        + "\n"
        + "  private Registry() {  \n"
        + "    throw new AssertionError(\"No instances.\");\n"
        + "  }\n"
        + "}\n";

    Set<String> extensions = ImmutableSet.of("com.One", "net.Two", "org.Three");
    ExtensionRegistryWriter writer = ExtensionRegistryWriter.forExtensions(extensions);
    ClassWriter actual = writer.write(ClassName.bestGuessFromString("com.example.Registry"));
    assertThat(actual.toString()).isEqualTo(expected);
  }
}