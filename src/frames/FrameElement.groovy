package frames;


import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target
import com.tinkerpop.blueprints.Direction

/**
 * @author Marek Piechut <m.piechut@tt.com.pl>
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface FrameElement {
    Direction value() default Direction.OUT
}
