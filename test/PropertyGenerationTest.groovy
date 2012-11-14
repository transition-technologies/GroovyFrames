import com.tinkerpop.frames.Property
import frames.Frame
import org.junit.Test

/**
 * @author Marek Piechut <m.piechut@tt.com.pl>
 */
class PropertyGenerationTest extends GroovyTestCase {

    @Test
    void testGetter() {
        def methods = Bean.class.declaredMethods
        assert methods*.name.containsAll('getName', 'getSurname', 'getNumber')
        assert Bean.class.getDeclaredMethod("getName").returnType == String.class
        assert Bean.class.getDeclaredMethod("getNumber").returnType == Integer.TYPE

        def annotations = Bean.class.getDeclaredMethod("getName").annotations

        assert annotations.size() == 1
        assert annotations[0] instanceof Property
        assert annotations[0].value() == "name"
    }

    @Test
    void testSetter() {
        def methods = Bean.class.declaredMethods
        assert methods*.name.containsAll('setName', 'setSurname', 'setNumber')
        assert Bean.class.getDeclaredMethod("setName", String.class).returnType == Void.TYPE
        assert Bean.class.getDeclaredMethod("setNumber", Integer.TYPE).returnType == Void.TYPE

        assert Bean.class.getDeclaredMethod("setName", String.class).parameterTypes == [String.class]
        assert Bean.class.getDeclaredMethod("setNumber", Integer.TYPE).parameterTypes == [Integer.TYPE]

        def annotations = Bean.class.getDeclaredMethod("setName", String.class).annotations

        assert annotations.size() == 1
        assert annotations[0] instanceof Property
        assert annotations[0].value() == "name"
    }
}


@Frame
public interface Bean {

    String name, surname
    int number;
}
