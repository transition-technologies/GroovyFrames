package frames;

import org.codehaus.groovy.transform.GroovyASTTransformationClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Marek Piechut <m.piechut@tt.com.pl>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@GroovyASTTransformationClass("frames.FramesASTProcessor")
public @interface Frame {
}
