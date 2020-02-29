package com.github.puce77.java11annotationprocessortest.annotation.impl;

import com.github.puce77.java11annotationprocessortest.annotation.Foo;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;

@SupportedAnnotationTypes({
    "com.github.puce77.java11annotationprocessortest.annotation.Foo"
})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class FooApplicationProcessor extends AbstractProcessor {

    private final List<Element> elements = new ArrayList<>();
    private final List<String> bars = new ArrayList<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(Foo.class)) {
            Foo fooAnnotation = element.getAnnotation(Foo.class);
            if (fooAnnotation != null) {
                bars.add(fooAnnotation.bar());
                elements.add(element);
            }
        }
        if (roundEnv.processingOver()) {
            writeFooFile();
        }
        return false;
    }

    private void writeFooFile() {
        Filer filer = processingEnv.getFiler();
        Messager messager = processingEnv.getMessager();
        try {
            FileObject fo = filer.createResource(StandardLocation.SOURCE_OUTPUT, "", "foo.txt",
                    elements.toArray(new Element[0]));

            try (Writer writer = fo.openWriter()) {
                for (String bar : bars) {
                    writer.append(bar).append("\n");
                }
            }
        } catch (IOException ex) {
            messager.printMessage(Diagnostic.Kind.ERROR, ex.getMessage());
        }

    }

}
