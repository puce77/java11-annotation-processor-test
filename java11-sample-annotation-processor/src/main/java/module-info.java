
import com.github.puce77.java11annotationprocessortest.annotation.impl.FooApplicationProcessor;

module com.github.puce77.java11annotationprocessortest.annotation {
    exports com.github.puce77.java11annotationprocessortest.annotation;

    provides javax.annotation.processing.Processor with FooApplicationProcessor;

    requires java.compiler;
}
