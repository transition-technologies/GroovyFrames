import com.tinkerpop.frames.Property
import frames.Frame
import org.junit.Test
import com.tinkerpop.frames.Incidence
import com.tinkerpop.frames.Adjacency
import com.tinkerpop.blueprints.Direction

/**
 * @author Marek Piechut <m.piechut@tt.com.pl>
 */
class PropertyGenerationTest extends GroovyTestCase {

    @Test
    void testGetter() {
        def methods = Vertex1.class.declaredMethods
        assert methods*.name.containsAll('getName', 'getSurname', 'getNumber')
        assert Vertex1.class.getDeclaredMethod("getName").returnType == String.class
        assert Vertex1.class.getDeclaredMethod("getNumber").returnType == Integer.TYPE

        def annotations = Vertex1.class.getDeclaredMethod("getName").annotations

        assert annotations.size() == 1
        assert annotations[0] instanceof Property
        assert annotations[0].value() == "name"
    }

    @Test
    void testSetter() {
        def methods = Vertex1.class.declaredMethods
        assert methods*.name.containsAll('setName', 'setSurname', 'setNumber')
        assert Vertex1.class.getDeclaredMethod("setName", String.class).returnType == Void.TYPE
        assert Vertex1.class.getDeclaredMethod("setNumber", Integer.TYPE).returnType == Void.TYPE

        assert Vertex1.class.getDeclaredMethod("setName", String.class).parameterTypes == [String.class]
        assert Vertex1.class.getDeclaredMethod("setNumber", Integer.TYPE).parameterTypes == [Integer.TYPE]

        def annotations = Vertex1.class.getDeclaredMethod("setName", String.class).annotations

        assert annotations.size() == 1
        assert annotations[0] instanceof Property
        assert annotations[0].value() == "name"
    }
}


@Frame
public interface Vertex1 {

    String name, surname
    int number

    Vertex2 friend
}

@Frame
public interface Vertex2 {
    String name
}
